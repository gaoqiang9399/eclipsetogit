package  app.component.calc.fee.controller;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.calc.core.entity.MfBusFeePlan;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.feign.MfBusAppFeeFeign;
import app.component.calccoreinterface.CalcFeePlanInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusFincApp;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfKindNodeFee;
import app.component.prdct.feign.MfKindNodeFeeFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusAppFeeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jul 28 10:07:52 CST 2016
 **/
@Controller
@RequestMapping("/mfBusAppFee")
public class MfBusAppFeeController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusAppFeeBo
	@Autowired
	private MfBusAppFeeFeign mfBusAppFeeFeign;
	@Autowired
	private CalcFeePlanInterfaceFeign calcFeePlanInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfKindNodeFeeFeign mfKindNodeFeeFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	//全局变量
	//异步参数
	//表单变量


	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(appId);
		Ipage ipage= new Ipage();
		ipage.setParams(this.setIpageParams("mfBusAppFee", mfBusAppFee));
		ipage = mfBusAppFeeFeign.findByPage(ipage);
		Gson gson = new Gson();
		String result =gson.toJson(ipage.getResult());
		List<MfBusAppFee> mfBusAppFeeList = gson.fromJson(result,new TypeToken<List<MfBusAppFee>>(){}.getType());
		List<MfBusAppFee> mfBusAppFeeListNew = new ArrayList<MfBusAppFee>();
		//处理费率指单位只有 利率为百分比时 才处理
		String takeType;
		Map<String,String>  dicMap;
		CodeUtils cu = new CodeUtils();
		String tableId = "tablebusfee0001";
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		String busModel = mfBusApply.getBusModel();
		if(BizPubParm.BUS_MODEL_12.equals(busModel)){
			tableId = "tablebusfeeguarantee";
		}
		for (MfBusAppFee busAppFee : mfBusAppFeeList) {
			if("14".equals(busAppFee.getItemNo())||"15".equals(busAppFee.getItemNo()) || "13".equals(busAppFee.getItemNo())){
				continue;
			}
			takeType=busAppFee.getTakeType();
			if(BizPubParm.FEETAKETYPE_1.equals(takeType)){//百分比
				String  rateType=busAppFee.getRateType();
				//处理利率单位
				dicMap = cu.getMapByKeyName("FEE_RATE_TYPE");
				String feeRateType=dicMap.get(rateType);//单位
				String rateScaleStr=String.valueOf(busAppFee.getRateScale());
				rateScaleStr=rateScaleStr+feeRateType;
				busAppFee.setRateScaleStr(rateScaleStr);
			}else{
				busAppFee.setRateScaleStr(busAppFee.getRateScale()+"");
			}

			mfBusAppFeeListNew.add(busAppFee);
		}
		model.addAttribute("query", "");
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeListNew);
		model.addAttribute("ipage", ipage);
		model.addAttribute("tableId", tableId);
		return "/component/calc/fee/MfBusAppFee_List";
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
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		try {
			mfBusAppFee.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAppFee.setCriteriaList(mfBusAppFee, ajaxData);//我的筛选
			//this.getRoleConditions(mfBusAppFee,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusAppFee", mfBusAppFee));
			ipage = mfBusAppFeeFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData 	formbusfee0002 = formService.getFormData("busfee0002");
			getFormValue(formbusfee0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusfee0002)){
				MfBusAppFee mfBusAppFee = new MfBusAppFee();
				setObjValue(formbusfee0002, mfBusAppFee);
				mfBusAppFeeFeign.insert(mfBusAppFee);
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
		FormData formbusfee0002 = formService.getFormData("busfee0002");
		getFormValue(formbusfee0002, getMapByJson(ajaxData));
		MfBusAppFee mfBusAppFeeJsp = new MfBusAppFee();
		setObjValue(formbusfee0002, mfBusAppFeeJsp);
		MfBusAppFee mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFeeJsp);
		if(mfBusAppFee!=null){
			try{
				mfBusAppFee = (MfBusAppFee)EntityUtil.reflectionSetVal(mfBusAppFee, mfBusAppFeeJsp, getMapByJson(ajaxData));
				mfBusAppFeeFeign.update(mfBusAppFee);
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
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		try{
			FormData 	formbusfee0001 = formService.getFormData("busfee0001");
			getFormValue(formbusfee0001, getMapByJson(ajaxData));
			if(this.validateFormData(formbusfee0001)){
				MfBusAppFee mfBusAppFee1 = new MfBusAppFee();
				setObjValue(formbusfee0001, mfBusAppFee1);
				mfBusAppFeeFeign.update(mfBusAppFee1);
				String appId = mfBusAppFee1.getAppId();
				mfBusAppFee.setAppId(appId);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfBusAppFee", mfBusAppFee));
				String  tableHtml = jtu.getJsonStr("tablebusfee0001","tableTag", (List<MfBusAppFee>)mfBusAppFeeFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");



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
		FormData formbusfee0002 = formService.getFormData("busfee0002");
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(id);
		mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
		getObjValue(formbusfee0002, mfBusAppFee,formData);
		if(mfBusAppFee!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByAppIdAjax")
	@ResponseBody
	public Map<String, Object> getByAppIdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> map = getMapByJson(ajaxData);
		MfKindNodeFee mfKindNodeFee = new MfKindNodeFee();
		try{
			String appId = (String) map.get("appId");
			//获取去产品信息
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			String kindNo = mfBusApply!=null?mfBusApply.getKindNo():BizPubParm.EXTENSION_BUSS;
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);// 当前审批节点task
			//自定义查询Bo方法
			mfKindNodeFee.setAppId(appId);
			mfKindNodeFee.setNodeNo(taskAppro.getActivityName());
			mfKindNodeFee.setKindNo(kindNo);
			List<MfKindNodeFee> mfKindNodeFeeList = mfKindNodeFeeFeign.getMfBusAppFeeList(mfKindNodeFee);
			for (MfKindNodeFee mfKindNodeFee1 : mfKindNodeFeeList) {
//				if ((mfKindNodeFee1.getFeeMainName() == null || mfKindNodeFee1.getFeeMainNo() == null) && mfKindNodeFee1.getRateScale() != null && mfKindNodeFee1.getRateScale() != 0.00) {
//					dataMap.put("flag", "error");
//					dataMap.put("msg","费用标准中"+mfKindNodeFee1.getItemName()+"信息不完善");
//					break;
//				}
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
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(id);
		try {
			mfBusAppFeeFeign.delete(mfBusAppFee);
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
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formbusfee0002 = formService.getFormData("busfee0002");
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_Insert";
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
		FormData  formbusfee0002 = formService.getFormData("busfee0002");
		getFormValue(formbusfee0002);
		MfBusAppFee  mfBusAppFee = new MfBusAppFee();
		setObjValue(formbusfee0002, mfBusAppFee);
		mfBusAppFeeFeign.insert(mfBusAppFee);
		getObjValue(formbusfee0002, mfBusAppFee);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfBusAppFee", mfBusAppFee));
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>)mfBusAppFeeFeign.findByPage(ipage).getResult();
		model.addAttribute("formbusfee0002", formbusfee0002);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id,String fincSts) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formbusfee0001 = formService.getFormData("busfee0001");
		getFormValue(formbusfee0001);
		MfBusAppFee  mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(id);
		mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
		getObjValue(formbusfee0001, mfBusAppFee);
		// 贷后阶段表单不可编辑
		if(fincSts != null && fincSts.compareTo("5") >= 0){
			//设置表单元素不可编辑
			FormActive[] list = formbusfee0001.getFormActives();
			for (int i = 0; i < list.length; i++) {
				FormActive formActive = list[i];
				formActive.setReadonly("1");
			}
		}
		model.addAttribute("formbusfee0001", formbusfee0001);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_Detail";
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
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(id);
		mfBusAppFeeFeign.delete(mfBusAppFee);
		return getListPage(model, id);
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
		FormData  formbusfee0002 = formService.getFormData("busfee0002");
		getFormValue(formbusfee0002);
		boolean validateFlag = this.validateFormData(formbusfee0002);
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
		FormData  formbusfee0002 = formService.getFormData("busfee0002");
		getFormValue(formbusfee0002);
		boolean validateFlag = this.validateFormData(formbusfee0002);
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppFeeByAppIdAjax")
	@ResponseBody
	public Map<String, Object> getAppFeeByAppIdAjax(String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		try {
			mfBusAppFee.setAppId(appId);
			List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getMfBusAppFeeList(mfBusAppFee);
			JSONArray jsonArray = new JSONArray();
			if (mfBusAppFeeList!=null&&mfBusAppFeeList.size()>0) {
				JSONObject jsonObject = null;
				for (int i = 0; i < mfBusAppFeeList.size(); i++) {
					jsonObject = new JSONObject();
					jsonObject.put("id", mfBusAppFeeList.get(i).getItemNo());
					jsonObject.put("name", mfBusAppFeeList.get(i).getItemName());
					jsonArray.add(jsonObject);
				}
			}
			dataMap.put("items",jsonArray.toString());
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
	 * 方法描述： 打开借据生成费用计划页面
	 * @param model
	 * @param ajaxData
	 * @param appId
	 * @param fincId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月17日 下午1:11:13
	 */
	@RequestMapping(value = "/getFincBusAppFeeByAppId")
	public String getFincBusAppFeeListByAppId(Model model, String ajaxData,String appId,String fincId) throws Exception{
		ActionContext.initialize(request,response);
		MfBusAppFee  mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(appId);
		List<MfBusAppFee> mfBusAppFeeList  = mfBusAppFeeFeign.getFincBusAppFeeList(mfBusAppFee,fincId);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_FincBusAppFee";
	}
	/**
	 *
	 * 方法描述： 打开收取借据费用页面
	 * @param model
	 * @param ajaxData
	 * @param appId
	 * @param fincId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月17日 上午11:51:36
	 */
	@RequestMapping(value = "/getFincBusFeeCollect")
	public String getFincBusFeeCollect(Model model, String ajaxData,String appId,String fincId) throws Exception{
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request,response);
		String itemsNo = "";
		dataMap = calcFeePlanInterfaceFeign.getFincBusFeeCollectData(appId,fincId,itemsNo);
		List<MfBusAppFee> mfBusAppFeeList = (List<MfBusAppFee>) dataMap.get("mfBusAppFees");
		List<MfBusFeePlan> mfBusFeePlanList = (List<MfBusFeePlan>) dataMap.get("mfBusFeePlanList");
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		model.addAttribute("mfBusFeePlanList", mfBusFeePlanList);
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_FincBusFeeCollect";
	}
	/**
	 *
	 * 方法描述： 收取借据费用保存提交
	 * @param ajaxData
	 * @param ajaxDataList
	 * @param appId
	 * @param fincId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月26日 下午2:39:55
	 */
	@RequestMapping(value = "/doFincBusFeeCollectAjax")
	@ResponseBody
	public Map<String, Object> doFincBusFeeCollectAjax(String ajaxData,String ajaxDataList,String appId,String fincId) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("appId", appId);
			map.put("fincId", fincId);
			map.put("feeList", ajaxDataList);
			calcFeePlanInterfaceFeign.doFincBusFeeCollect(map);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("收费"));
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("返费操作失败，",e);
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("返费"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFeeListPage")
	public String getFeeListPage(Model model,String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(appId);
		List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getList(mfBusAppFee);
		for (int i = 0; i < mfBusAppFeeList.size(); i++) {
			mfBusAppFeeList.get(i).setExt1(mfBusAppFeeList.get(i).getRelationPactNo());//临时存放关联合同号
		}
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		return "/component/calc/fee/MfBusAppFee_CriterionList";
	}

	/**
	 * 方法描述： 修改费用合同号
	 * @return
	 * @throws Exception
	 * String
	 * @author Javelin
	 * @date 2018-5-8 下午17:55:47
	 */
	@RequestMapping(value = "/updateByIdAjax")
	@ResponseBody
	public Map<String, Object> updaterateScaleByIdAjax(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			JSONObject json = JSONObject.fromObject(dataMap);
			MfBusAppFee mfBusAppFee = (MfBusAppFee) JSONObject.toBean(json, MfBusAppFee.class);
			mfBusAppFeeFeign.update(mfBusAppFee);
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
	 * 费用合同查询中完善信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFeeList")
	public Map<String, Object> getFeeList(String appId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setAppId(appId);
		List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getList(mfBusAppFee);
		if (mfBusAppFeeList!=null&&mfBusAppFeeList.size()>0) {
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("mfBusAppFeeList", mfBusAppFeeList);
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 预收费用信息登记
	 * @param model
	 * @param ajaxData
	 * @param id
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月10日 上午11:23:24
	 */
	@RequestMapping(value = "/perfectBusAppFee")
	public String perfectBusAppFee(Model model, String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formbusfee0001 = formService.getFormData("advanceCollectFee");
		getFormValue(formbusfee0001);
		MfBusAppFee  mfBusAppFee = new MfBusAppFee();
		mfBusAppFee.setId(id);
		mfBusAppFee = mfBusAppFeeFeign.getById(mfBusAppFee);
		getObjValue(formbusfee0001, mfBusAppFee);
		model.addAttribute("formbusfee0001", formbusfee0001);
		model.addAttribute("appId", mfBusAppFee.getAppId());
		model.addAttribute("itemNo", mfBusAppFee.getItemNo());
		model.addAttribute("query", "");
		return "/component/calc/fee/perfectBusAppFee";
	}
	/**
	 *
	 * 方法描述： 预收费用信息登记保存
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年5月10日 上午11:23:40
	 */
	@RequestMapping(value = "/savePerfectBusAppFeeAjax")
	@ResponseBody
	public Map<String, Object> savePerfectBusAppFeeAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData 	formbusfee0001 = formService.getFormData((String) dataMap.get("formId"));
			getFormValue(formbusfee0001, getMapByJson(ajaxData));
			if(this.validateFormData(formbusfee0001)){
				MfBusAppFee mfBusAppFee1 = new MfBusAppFee();
				setObjValue(formbusfee0001, mfBusAppFee1);
				mfBusAppFeeFeign.update(mfBusAppFee1);
				String appId = mfBusAppFee1.getAppId();
				mfBusAppFee.setAppId(appId);
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfBusAppFee", mfBusAppFee));
				String  tableHtml = jtu.getJsonStr("tablebusfee0004","tableTag", (List<MfBusAppFee>)mfBusAppFeeFeign.findByPage(ipage).getResult(), null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag","1");

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

	@RequestMapping(value = "/getBusAppFeeListHtmlStrAjax")
	@ResponseBody
	public Map<String, Object> getBusAppFeeListHtmlStrAjax(String appId,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		try{
			mfBusAppFee.setAppId(appId);
			List<MfBusAppFee> mfBusAppFeeList = mfBusAppFeeFeign.getList(mfBusAppFee);
			if (mfBusAppFeeList!=null&&mfBusAppFeeList.size()>0) {
				JsonTableUtil jtu = new JsonTableUtil();
				String  tableHtml = jtu.getJsonStr(tableId,"tableTag", mfBusAppFeeList, null,true);
				dataMap.put("htmlStr", tableHtml);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
			}

		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getFincBusAppFee")
	public String getFincBusAppFee(Model model, String ajaxData,String appId) throws Exception{
		ActionContext.initialize(request,response);
		MfBusAppFee mfBusAppFee = new MfBusAppFee();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setAppId(appId);
		MfBusFincApp busFincApp = pactInterfaceFeign.getByIdNewFinc(mfBusFincApp);
		String fincId = busFincApp.getFincId();
		mfBusAppFee.setAppId(appId);
		List<MfBusAppFee> mfBusAppFeeList  = mfBusAppFeeFeign.getFincBusAppFeeList(mfBusAppFee,fincId);
		model.addAttribute("mfBusAppFeeList", mfBusAppFeeList);
		model.addAttribute("appId", appId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("query", "");
		return "/component/calc/fee/MfBusAppFee_mfBusAppFee";
	}
	/**
	 *
	 * 方法描述： 提交费用生成流程到下一步
	 * @param mfBusAppFee
	 * @return
	 * @throws ServiceException
	 * Result
	 * @author 段泽宇
	 * @date 2018年7月2日 下午19:22:22
	 */
	@RequestMapping(value = "/submitForm")
	@ResponseBody
	public Map<String, Object> submitForm(Model model, String ajaxData,String appId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfBusAppFee mfBusAppFee = new MfBusAppFee();
			mfBusAppFee.setAppId(appId);
			Result result = mfBusAppFeeFeign.doCommit(mfBusAppFee);
			if (result.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_SUBMIT_TONEXT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
}
