package  app.component.calc.fee.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.calc.fee.feign.MfSysFeeItemFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import app.util.JsonStrHandling;

/**
 * Title: MfSysFeeItemAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri May 20 18:04:05 CST 2016
 **/
@Controller
@RequestMapping("/mfSysFeeItem")
public class MfSysFeeItemController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfSysFeeItemBo
	@Autowired
	private MfSysFeeItemFeign mfSysFeeItemFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;

	//全局变量
	//异步参数
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String feeStdNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setFeeStdNo(feeStdNo);
		Ipage ipage = this.getIpage();
		List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>)mfSysFeeItemFeign.getAll(mfSysFeeItem);
		
		FormData formfeeitem0002 = formService.getFormData("feeitem0002");
		 getFormValue(formfeeitem0002);
		getObjValue(formfeeitem0002, mfSysFeeItem);
		model.addAttribute("formfeeitem0002", formfeeitem0002);
		model.addAttribute("mfSysFeeItemList", mfSysFeeItemList);
		model.addAttribute("query", "");
		return "MfSysFeeItem_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		try {
			mfSysFeeItem.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfSysFeeItem.setCriteriaList(mfSysFeeItem, ajaxData);//我的筛选
			//this.getRoleConditions(mfSysFeeItem,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfSysFeeItemFeign.findByPage(ipage, mfSysFeeItem);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得费用项html
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param pageSize 
	 * @date 2017-3-28 下午4:02:29
	 */
	@RequestMapping(value = "/getFeeItemListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getFeeItemListHtmlAjax(String ajaxData,int pageNo,String feeStdNo, Integer pageSize) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			mfSysFeeItem.setFeeStdNo(feeStdNo);
			//自定义查询Bo方法
			ipage = mfSysFeeItemFeign.findByPage(ipage, mfSysFeeItem);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr("tablefeeitem0001","tableTag",(List)ipage.getResult(), ipage,true);
			dataMap.put("flag", "success");
			dataMap.put("tableHtml",tableHtml);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-3-29 下午2:45:59
	 */
	@RequestMapping(value = "/getFeeItemListAjax")
	@ResponseBody
	public Map<String, Object> getFeeItemListAjax(String ajaxData,String feeStdNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		try {
			mfSysFeeItem.setFeeStdNo(feeStdNo);
			//自定义查询Bo方法
			List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
			if(mfSysFeeItemList!=null&&mfSysFeeItemList.size()>0){
				dataMap.put("flag", "success");
				dataMap.put("mfSysFeeItemList",mfSysFeeItemList);
			}else{
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formfeeitem0002 = formService.getFormData("feeitem0002");
			getFormValue(formfeeitem0002, getMapByJson(ajaxData));
			if(this.validateFormData(formfeeitem0002)){
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
				setObjValue(formfeeitem0002, mfSysFeeItem);
				mfSysFeeItemFeign.insert(mfSysFeeItem);
				List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
				CodeUtils cu = new CodeUtils();
				for (MfSysFeeItem mfSysFeeItem1:mfSysFeeItemList) {
					//收费基准项
					Map<String,String>  dicMap = cu.getMapByKeyName("FEE_STANDARD");
					mfSysFeeItem1.setExt1(dicMap.get(mfSysFeeItem1.getStandard()));
					//收费时间
					dicMap = cu.getMapByKeyName("FEE_COLLECT_TIME_TYPE");
					mfSysFeeItem1.setExt2(dicMap.get(mfSysFeeItem1.getFeeCollectTime()));
					//费率两位
					mfSysFeeItem1.setExt3(MathExtend.round(String.valueOf(mfSysFeeItem1.getRateScale()), 2));
					//取费用收取方式
					dicMap = cu.getMapByKeyName("FEE_COLLECT_TYPE");
					mfSysFeeItem1.setFeeType(dicMap.get(mfSysFeeItem1.getFeeType()));
					//退款对象
					dicMap = cu.getMapByKeyName("REFUND_CUS_TYPE");
					String refundCusType=mfSysFeeItem1.getRefundCusType();
					if(StringUtil.isNotEmpty(refundCusType)){//说明是退
						refundCusType=dicMap.get(refundCusType);
					}
					//收还是退
					String itemType=mfSysFeeItem1.getItemType();
					if(BizPubParm.FEE_ITEM_TYPE_SHOU.equals(itemType)){
						itemType="收";
					}else if(BizPubParm.FEE_ITEM_TYPE_TUI.equals(itemType)){
						itemType="退"+refundCusType;
					}else if(BizPubParm.FEE_ITEM_TYPE_CHONG.equals(itemType)){
						itemType="冲抵";
					}else {
					}
					mfSysFeeItem1.setItemType(itemType);
				}
				dataMap.put("mfSysFeeItemList",mfSysFeeItemList);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 节点上配置费用（后台产品配置使用）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 下午2:32:49
	 */
	@RequestMapping(value = "/insertForPrdctAjax")
	@ResponseBody
	public Map<String, Object> insertForPrdctAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			Map<String,Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			FormData formfeeitem0002 = formService.getFormData(formId);
			getFormValue(formfeeitem0002, map);
			if(this.validateFormData(formfeeitem0002)){
				MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
				setObjValue(formfeeitem0002, mfSysFeeItem);
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfSysFeeItem);
				map.put("mfSysFeeItem", mfSysFeeItem);
				dataMap = mfSysFeeItemFeign.insertForPrdct(map);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 产品下节点费用项编辑方法
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 下午4:58:31
	 */
	@RequestMapping(value = "/updateForPrdctAjax")
	@ResponseBody
	public Map<String, Object> updateForPrdctAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			FormData formfeeitem0003 = formService.getFormData(formId);
			getFormValue(formfeeitem0003, map);
			if(this.validateFormData(formfeeitem0003)){
				MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
				setObjValue(formfeeitem0003, mfSysFeeItem);
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfSysFeeItem);
				map.put("mfSysFeeItem", mfSysFeeItem);
				dataMap = mfSysFeeItemFeign.updateForPrdct(map);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得基础费用项
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-3-31 上午10:06:51
	 */
	@RequestMapping(value = "/getPageFeeItem")
	public String getPageFeeItem(Model model, String ajaxData,String feeStdNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("FEE_ITEM");
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		if(parmDicList!=null&&parmDicList.size()>0){
		MfSysFeeItem 	mfSysFeeItem = new MfSysFeeItem();
			mfSysFeeItem.setFeeStdNo(feeStdNo);
			List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
			parmDicList = getParmDicList(parmDicList, mfSysFeeItemList);
		}
		JSONArray array = JSONArray.fromObject(parmDicList);
		JSONObject json = new JSONObject();
		json.put("baseFeeItemList", array);
		
		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "MfSysFeeItem_select";
	}
		
	public List<ParmDic> getParmDicList(List<ParmDic> parmDicList,List<MfSysFeeItem> mfSysFeeItemList) throws Exception{
		List<String> list=new ArrayList<String>();
		if(mfSysFeeItemList!=null&&mfSysFeeItemList.size()>0){//此产品已配置的费用项
			for(int i=0;i<mfSysFeeItemList.size();i++){
				MfSysFeeItem msf = (MfSysFeeItem)JsonStrHandling.handlingStrToBean(mfSysFeeItemList.get(i), MfSysFeeItem.class);
				list.add(msf.getItemNo());
			}
		}
		List<ParmDic> resultList=new ArrayList<ParmDic>();
		for(int i=0;i<parmDicList.size();i++){
			ParmDic pd = (ParmDic)JsonStrHandling.handlingStrToBean(parmDicList.get(i), ParmDic.class);
			if(!list.contains(pd.getOptCode())){//如果此费用项未配置
				resultList.add(pd);
			}
		}
		return resultList;
	}
	
	@RequestMapping(value = "/getPageFeeItemAjax")
	@ResponseBody
	public Map<String, Object> getPageFeeItemAjax(String ajaxData,String feeStdNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("FEE_ITEM");
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		if(parmDicList!=null&&parmDicList.size()>0){
		MfSysFeeItem 	mfSysFeeItem = new MfSysFeeItem();
			mfSysFeeItem.setFeeStdNo(feeStdNo);
			List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
			parmDicList = getParmDicList(parmDicList, mfSysFeeItemList);
		}
		JSONArray array = JSONArray.fromObject(parmDicList);
		for (int i = 0; i < array.size(); i++) {
			array.getJSONObject(i).put("id",
					array.getJSONObject(i).getString("optCode"));
			array.getJSONObject(i).put("name",
					array.getJSONObject(i).getString("optName"));
		}
		dataMap.put("items", array.toString());		
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfeeitem0002 = formService.getFormData("feeitem0002");
		getFormValue(formfeeitem0002, getMapByJson(ajaxData));
		MfSysFeeItem mfSysFeeItemJsp = new MfSysFeeItem();
		setObjValue(formfeeitem0002, mfSysFeeItemJsp);
		MfSysFeeItem mfSysFeeItem = mfSysFeeItemFeign.getById(mfSysFeeItemJsp);
		if(mfSysFeeItem!=null){
			try{
				mfSysFeeItem = (MfSysFeeItem)EntityUtil.reflectionSetVal(mfSysFeeItem, mfSysFeeItemJsp, getMapByJson(ajaxData));
				mfSysFeeItemFeign.update(mfSysFeeItem);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		try{
		FormData 	formfeeitem0003 = formService.getFormData("feeitem0003");
			getFormValue(formfeeitem0003, getMapByJson(ajaxData));
			if(this.validateFormData(formfeeitem0003)){
		MfSysFeeItem mfSysFeeItem1 = new MfSysFeeItem();
				setObjValue(formfeeitem0003, mfSysFeeItem1);
				mfSysFeeItemFeign.update(mfSysFeeItem1);
				CodeUtils cu = new CodeUtils();
				//收费基准项
				Map<String,String>  dicMap = cu.getMapByKeyName("FEE_STANDARD");
				mfSysFeeItem1.setExt1(dicMap.get(mfSysFeeItem1.getTakeNode()));
				//收费时间
				dicMap = cu.getMapByKeyName("FEE_COLLECT_TIME_TYPE");
				mfSysFeeItem1.setExt2(dicMap.get(mfSysFeeItem1.getStandard()));
				
				dataMap.put("mfSysFeeItem",mfSysFeeItem1);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/updateAjax1")
	@ResponseBody
	public Map<String, Object> updateAjax1(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData  formfeeitem0001 = formService.getFormData("feeitem0001");
		 getFormValue(formfeeitem0001);
		MfSysFeeItem  mfSysFeeItem = new MfSysFeeItem();
		 setObjValue(formfeeitem0001, mfSysFeeItem);
		 mfSysFeeItemFeign.update(mfSysFeeItem);
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("flag", "success");
		dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
		/*MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		try{
		FormData 	formfeeitem0001 = formService.getFormData("feeitem0001");
			getFormValue(formfeeitem0001, getMapByJson(ajaxData));
			if(this.validateFormData(formfeeitem0001)){
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
				setObjValue(formfeeitem0001, mfSysFeeItem);
				mfSysFeeItemFeign.update(mfSysFeeItem);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}*/
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formfeeitem0002 = formService.getFormData("feeitem0002");
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setId(id);
		mfSysFeeItem = mfSysFeeItemFeign.getById(mfSysFeeItem);
		getObjValue(formfeeitem0002, mfSysFeeItem,formData);
		if(mfSysFeeItem!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String id,String feeStdNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setId(id);
		mfSysFeeItem.setFeeStdNo(feeStdNo);
		try {
			mfSysFeeItemFeign.deleteFeeAndNodeFee(mfSysFeeItem);
			List<MfSysFeeItem> mfSysFeeItemList = mfSysFeeItemFeign.getAll(mfSysFeeItem);
			CodeUtils cu = new CodeUtils();
			for (MfSysFeeItem mfSysFeeItem1:mfSysFeeItemList) {
				//收费基准项
				Map<String,String>  dicMap = cu.getMapByKeyName("FEE_STANDARD");
				mfSysFeeItem1.setExt1(dicMap.get(mfSysFeeItem1.getStandard()));
				//收费时间
				dicMap = cu.getMapByKeyName("FEE_COLLECT_TIME_TYPE");
				mfSysFeeItem1.setExt2(dicMap.get(mfSysFeeItem1.getFeeCollectTime()));
				//费率两位
				mfSysFeeItem1.setExt3(MathExtend.round(String.valueOf(mfSysFeeItem1.getRateScale()), 2));
				//取费用收取方式
				dicMap = cu.getMapByKeyName("FEE_COLLECT_TYPE");
				mfSysFeeItem1.setFeeType(dicMap.get(mfSysFeeItem1.getFeeType()));
				//退款对象
				dicMap = cu.getMapByKeyName("REFUND_CUS_TYPE");
				String refundCusType=mfSysFeeItem1.getRefundCusType();
				if(StringUtil.isNotEmpty(refundCusType)){//说明是退
					refundCusType=dicMap.get(refundCusType);
				}
				//收还是退
				String itemType=mfSysFeeItem1.getItemType();
				if(BizPubParm.FEE_ITEM_TYPE_SHOU.equals(itemType)){
					itemType="收";
				}else if(BizPubParm.FEE_ITEM_TYPE_TUI.equals(itemType)){
					itemType="退"+refundCusType;
				}else if(BizPubParm.FEE_ITEM_TYPE_CHONG.equals(itemType)){
					itemType="冲抵";
				}else {
				}
				mfSysFeeItem1.setItemType(itemType);
			}
			dataMap.put("mfSysFeeItemList",mfSysFeeItemList);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String feeStdNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setFeeStdNo(feeStdNo);
		FormData formfeeitem0002 = formService.getFormData("feeitem0002");
		getObjValue(formfeeitem0002, mfSysFeeItem);
		model.addAttribute("formfeeitem0002", formfeeitem0002);
		model.addAttribute("query", "");
		model.addAttribute("feeStdNo", feeStdNo);
		return "/component/calc/fee/MfSysFeeItem_Insert";
	}
	
	/**
	 * 
	 * 方法描述：配置节点上的费用项 
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 上午10:51:49
	 */
	@RequestMapping(value = "/inputForPrdct")
	public String inputForPrdct(Model model,String feeStdNo,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setFeeStdNo(feeStdNo);
		mfSysFeeItem.setNodeNo(nodeNo);
		FormData formfeeitem0002 = formService.getFormData("kindnodefeeitem");
		getObjValue(formfeeitem0002, mfSysFeeItem);
		model.addAttribute("formfeeitem0002", formfeeitem0002);
		model.addAttribute("query", "");
		model.addAttribute("feeStdNo", feeStdNo);
		model.addAttribute("nodeNo", nodeNo);
		return "/component/calc/fee/MfSysFeeItem_InsertForPrdct";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formfeeitem0002 = formService.getFormData("feeitem0002");
		 getFormValue(formfeeitem0002);
		MfSysFeeItem  mfSysFeeItem = new MfSysFeeItem();
		 setObjValue(formfeeitem0002, mfSysFeeItem);
		 mfSysFeeItemFeign.insert(mfSysFeeItem);
		 getObjValue(formfeeitem0002, mfSysFeeItem);
		 this.addActionMessage(model, "保存成功");
		 List<MfSysFeeItem> mfSysFeeItemList = (List<MfSysFeeItem>)mfSysFeeItemFeign.findByPage(this.getIpage(), mfSysFeeItem).getResult();
		model.addAttribute("formfeeitem0002", formfeeitem0002);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfSysFeeItem_List";
	}
	
	/**
	 * @author czk
	 * @Description: 获得费用项详情
	 * date 2016-8-29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formfeeitem0003 = formService.getFormData("feeitem0003");
		 getFormValue(formfeeitem0003);
		MfSysFeeItem  mfSysFeeItem = new MfSysFeeItem();
		 mfSysFeeItem.setId(id);
		 mfSysFeeItem = mfSysFeeItemFeign.getById(mfSysFeeItem);
		 getObjValue(formfeeitem0003, mfSysFeeItem);
		 
		model.addAttribute("formfeeitem0003", formfeeitem0003);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfSysFeeItem_Detail";
	}
	
	/**
	 * 
	 * 方法描述： 编辑产品节点上的费用项（后台产品配置使用）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 上午11:29:54
	 */
	@RequestMapping(value = "/getByIdForPrdct")
	public String getByIdForPrdct(Model model, String nodeNo,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formfeeitem0003 = formService.getFormData("kindnodefeeitemedit");
		getFormValue(formfeeitem0003);
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setId(id);
		mfSysFeeItem = mfSysFeeItemFeign.getById(mfSysFeeItem);
		//获取节点上配置的权限信息
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		mfKindNodeFee.setKindNo(mfSysFeeItem.getFeeStdNo());
		mfKindNodeFee.setNodeNo(nodeNo);
		mfKindNodeFee.setItemNo(mfSysFeeItem.getItemNo());
		mfKindNodeFee = prdctInterfaceFeign.getKindNodeFee(mfKindNodeFee);
		getObjValue(formfeeitem0003, mfSysFeeItem);
		this.changeFormProperty(formfeeitem0003, "nodeNo", "initValue", nodeNo);
		this.changeFormProperty(formfeeitem0003, "optPower", "initValue", mfKindNodeFee.getOptPower());
		model.addAttribute("formfeeitem0003", formfeeitem0003);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfSysFeeItem_DetailForPrdct";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		mfSysFeeItem.setId(id);
		mfSysFeeItemFeign.delete(mfSysFeeItem);
		return getListPage(model, id);
	}
	/**
	 * 更新之后获取属性
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdUpdatedAjax")
	@ResponseBody
	public Map<String, Object> getByIdUpdatedAjax(String ajaxData,String id) throws Exception{
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
			mfSysFeeItem.setId(id);
			mfSysFeeItem=mfSysFeeItemFeign.getByIdUpdated(mfSysFeeItem);
			dataMap.put("bean", mfSysFeeItem);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			//logger.error("更新费用项之后查询出错",e);
		}
		return dataMap;
	}
	private void getTableData(String tableId, MfSysFeeItem mfSysFeeItem) throws Exception {
		Map<String, Object> dataMap = new HashMap<String,Object>();
		JsonTableUtil jtu = new JsonTableUtil();
		/*MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setPactId(pactId);*/
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfSysFeeItemFeign.getAll(mfSysFeeItem), null,true);
	
		dataMap.put("tableData",tableHtml);
	}
	
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formfeeitem0002 = formService.getFormData("feeitem0002");
		 getFormValue(formfeeitem0002);
		 boolean validateFlag = this.validateFormData(formfeeitem0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formfeeitem0002 = formService.getFormData("feeitem0002");
		 getFormValue(formfeeitem0002);
		 boolean validateFlag = this.validateFormData(formfeeitem0002);
	}
	
	
	/**
	 * 
	 * 方法描述：更新产品下配置的费用项顺序 
	 * @return
	 * String
	 * @author zhs
	 * @date 2018-1-2 上午9:26:24
	 */
	@RequestMapping(value = "/updateSortAjax")
	@ResponseBody
	 public Map<String, Object> updateSortAjax(String ajaxData,String[] itemIds,String feeStdNo){
		FormService formService = new FormService();
    	ActionContext.initialize(request,
				response);
    	Map<String, Object> dataMap = new HashMap<String, Object>();
    	if(itemIds != null){
    		try {
    			mfSysFeeItemFeign.updateSort(feeStdNo,itemIds);
	    		dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
    		} catch (Exception e) {
    			dataMap.put("flag", "error");
    			dataMap.put("msg",MessageEnum.ERROR.getMessage());
    			e.printStackTrace();
    		}
    	}else{
    		dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
    	}
    	return dataMap;
    }
	
}
