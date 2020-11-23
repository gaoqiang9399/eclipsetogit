package  app.component.prdct.controller;
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
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calcinterface.CalcInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.nmd.entity.ParmDic;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdct.feign.MfKindNodeFeeFeign;
import app.component.prdct.feign.MfKindNodeFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfKindNodeFeeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 29 15:43:10 CST 2017
 **/
@Controller
@RequestMapping("/mfKindNodeFee")
public class MfKindNodeFeeController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfKindNodeFeeBo
	@Autowired
	private MfKindNodeFeeFeign mfKindNodeFeeFeign;
	@Autowired
	private MfKindNodeFeign mfKindNodeFeign;
	@Autowired
	private CalcInterfaceFeign calcInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfBusAppFeeFeign mfBusAppFeeFeign;
	//全局变量
	//异步参数
	//表单变量

	/**
	 * 方法描述： 获取利率标准列表
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-22 下午2:33:30
	 */
	@RequestMapping(value = "/getBusFeeListAjax")
	@ResponseBody
	public Map<String, Object> getBusFeeListAjax(String appId,String nodeNo,Integer pageNo,String tableId,String tableType,String feePower) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		try {
			//获取去产品信息
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String kindNo = mfBusApply!=null?mfBusApply.getKindNo():BizPubParm.EXTENSION_BUSS;
			//自定义查询Bo方法
			mfKindNodeFee.setAppId(appId);
			mfKindNodeFee.setNodeNo(nodeNo);
			mfKindNodeFee.setKindNo(kindNo);
			List<MfKindNodeFee> mfKindNodeFeeList = mfKindNodeFeeFeign.getMfBusAppFeeList(mfKindNodeFee);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setResult(mfKindNodeFeeList);
			ipage.setPageSum(1);
			ipage.setPageCounts(mfKindNodeFeeList.size()-1);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("flag", "success");
			dataMap.put("mfKindNodeFeeList", mfKindNodeFeeList);
			dataMap.put("ipage",ipage);
			dataMap.put("feePower", feePower);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 方法描述： 修改利率
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2017-6-23 上午9:49:47
	 */
	@RequestMapping(value = "/updaterateScaleByIdAjax")
	@ResponseBody
	public Map<String, Object> updaterateScaleByIdAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			JSONObject json = JSONObject.fromObject(dataMap);
			MfKindNodeFee mfKindNodeFee = (MfKindNodeFee) JSONObject.toBean(json, MfKindNodeFee.class);
//			mfBusAppFee = (MfBusAppFee) CwPublicUtil.convertMap(MfBusAppFee.class, dataMap);
			mfKindNodeFeeFeign.updateFeeRate(mfKindNodeFee);
			MfBusAppFee mfBusAppFee = new MfBusAppFee();
			mfBusAppFee.setId(mfKindNodeFee.getFeeId());
			mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
			dataMap.put("mfBusAppFee", mfBusAppFee);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeFee_List";
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
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		try {
			mfKindNodeFee.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfKindNodeFee.setCriteriaList(mfKindNodeFee, ajaxData);//我的筛选
			//this.getRoleConditions(mfKindNodeFee,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfKindNodeFeeFeign.findByPage(ipage, mfKindNodeFee);
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
	 * 方法描述： 节点配置费用信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-6 下午6:41:56
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formnodefee0001 = formService.getFormData("nodefee0001");
			getFormValue(formnodefee0001, getMapByJson(ajaxData));
			if(this.validateFormData(formnodefee0001)){
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
				setObjValue(formnodefee0001, mfKindNodeFee);
				dataMap = mfKindNodeFeeFeign.insert(mfKindNodeFee);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String kindNodeFeeId,String kindNo,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		mfKindNodeFee.setKindNodeFeeId(kindNodeFeeId);
		mfKindNodeFee.setKindNo(kindNo);
		mfKindNodeFee.setNodeNo(nodeNo);
		try {
			dataMap = mfKindNodeFeeFeign.delete(mfKindNodeFee);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 节点上费用配置的删除
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 下午3:52:59
	 */
	@RequestMapping(value = "/deleteForPrdctAjax")
	@ResponseBody
	public Map<String, Object> deleteForPrdctAjax(String kindNodeFeeId,String kindNo,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		mfKindNodeFee.setKindNodeFeeId(kindNodeFeeId);
		mfKindNodeFee.setKindNo(kindNo);
		mfKindNodeFee.setNodeNo(nodeNo);
		try {
			dataMap = mfKindNodeFeeFeign.deleteForPrdct(mfKindNodeFee);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
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
	public String input(Model model,String kindNo,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formnodefee0001 = formService.getFormData("nodefee0001");
		MfKindNodeFee  mfKindNodeFee = new MfKindNodeFee();
		 mfKindNodeFee.setKindNo(kindNo);
		 mfKindNodeFee.setNodeNo(nodeNo);
		 mfKindNodeFee.setOptPower("2");//默认查看
		 getObjValue(formnodefee0001, mfKindNodeFee);
		//获取产品下配置的费用项列表
		List<MfSysFeeItem> mfSysFeeItemList = calcInterfaceFeign.getFeeItemList(kindNo);
//		JSONArray array = JSONArray.fromObject(mfSysFeeItemList);
//		for (int i = 0; i < array.size(); i++) {
//			array.getJSONObject(i).put("id",array.getJSONObject(i).getString("itemNo"));
//			array.getJSONObject(i).put("name",array.getJSONObject(i).getString("itemName"));
//		}
//		JSONObject json = new JSONObject();
//		json.put("items", array);
//		ajaxData=json.toString();
		List<OptionsList> opinionList = new ArrayList<OptionsList>();
		for(MfSysFeeItem feeObj:mfSysFeeItemList){
			OptionsList s= new OptionsList();
			s.setOptionLabel(feeObj.getItemName());
			s.setOptionValue(feeObj.getItemNo());
			opinionList.add(s);
		}
		this.changeFormProperty(formnodefee0001, "itemNo", "optionArray", opinionList);
		model.addAttribute("formnodefee0001", formnodefee0001);
		model.addAttribute("opinionList", opinionList);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeFee_Insert";
	}
	/**
	 * 
	 * 方法描述： 节点下费用配置
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-7-7 上午10:00:49
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNodeFeeId,String kindNo,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formnodefee0001 = formService.getFormData("nodefee0001");
		 getFormValue(formnodefee0001);
		MfKindNodeFee  mfKindNodeFee = new MfKindNodeFee();
		 mfKindNodeFee.setKindNodeFeeId(kindNodeFeeId);
		 mfKindNodeFee.setKindNo(kindNo);
		 mfKindNodeFee.setNodeNo(nodeNo);
		 mfKindNodeFee = mfKindNodeFeeFeign.getById(mfKindNodeFee);
		 MfSysFeeItem mfSysFeeItem = new MfSysFeeItem();
		 mfSysFeeItem.setItemNo(mfKindNodeFee.getItemNo());
		 mfSysFeeItem.setFeeStdNo(kindNo);
		 mfSysFeeItem = calcInterfaceFeign.getMfSysFeeItem(mfSysFeeItem);
		 getObjValue(formnodefee0001, mfKindNodeFee);
		 getObjValue(formnodefee0001, mfSysFeeItem);
		//获取产品下配置的费用项列表
		List<MfSysFeeItem> mfSysFeeItemList = calcInterfaceFeign.getFeeItemList(kindNo);
//		JSONArray array = JSONArray.fromObject(mfSysFeeItemList);
//		for (int i = 0; i < array.size(); i++) {
//			array.getJSONObject(i).put("id",array.getJSONObject(i).getString("itemNo"));
//			array.getJSONObject(i).put("name",array.getJSONObject(i).getString("itemName"));
//		}
//		JSONObject json = new JSONObject();
//		json.put("items", array);
//		ajaxData=json.toString();
		List<OptionsList> opinionList = new ArrayList<OptionsList>();
		for(MfSysFeeItem feeObj:mfSysFeeItemList){
			OptionsList s= new OptionsList();
			s.setOptionLabel(feeObj.getItemName());
			s.setOptionValue(feeObj.getItemNo());
			opinionList.add(s);
		}
		this.changeFormProperty(formnodefee0001, "itemNo", "optionArray", opinionList);
		model.addAttribute("formnodefee0001", formnodefee0001);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeFee_Insert";
	}
	
	/**
	 * 
	 * 方法描述：获取配置节点费用时新增表单中的基础费用项（过滤掉该节点上所配置的费用项）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-6 上午11:53:06
	 */
	@RequestMapping(value = "/getPageFeeItemForPrdctAjax")
	@ResponseBody
	public Map<String, Object>  getPageFeeItemForPrdctAjax(String kindNo,String nodeNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> parmDicList = (List<ParmDic>)cu.getCacheByKeyName("FEE_ITEM");
		if(parmDicList!=null&&parmDicList.size()>0){
			MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
			mfKindNodeFee.setKindNo(kindNo);
			mfKindNodeFee.setNodeNo(nodeNo);
			List<MfKindNodeFee> mfKindNodeFeeList = mfKindNodeFeeFeign.getKindNodeFeeList(mfKindNodeFee);
			parmDicList = getParmDicList(parmDicList, mfKindNodeFeeList);
		}
		JSONArray array = JSONArray.fromObject(parmDicList);
		for (int i = 0; i < array.size(); i++) {
			array.getJSONObject(i).put("id",array.getJSONObject(i).getString("optCode"));
			array.getJSONObject(i).put("name",array.getJSONObject(i).getString("optName"));
		}
		dataMap.put("items", array);		
		return dataMap;
	}
	
	public List<ParmDic> getParmDicList(List<ParmDic> parmDicList,List<MfKindNodeFee> mfKindNodeFeeList) throws Exception{
		List<String> list=new ArrayList<String>();
		if(mfKindNodeFeeList!=null&&mfKindNodeFeeList.size()>0){//此产品该节点已配置的费用项
			for(MfKindNodeFee msf:mfKindNodeFeeList){
				list.add(msf.getItemNo());
			}
		}
		List<ParmDic> resultList=new ArrayList<ParmDic>();
		for(ParmDic pd:parmDicList){//总的费用项
			if(!list.contains(pd.getOptCode())){//如果此费用项未配置
				resultList.add(pd);
			}
		}
		return resultList;			
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
		FormData  formnodefee0002 = formService.getFormData("nodefee0002");
		 getFormValue(formnodefee0002);
		 boolean validateFlag = this.validateFormData(formnodefee0002);
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
		FormData  formnodefee0002 = formService.getFormData("nodefee0002");
		 getFormValue(formnodefee0002);
		 boolean validateFlag = this.validateFormData(formnodefee0002);
	}
	
}
