package app.component.risk.risklevelmanage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.risk.risklevelmanage.entity.MfRiskLevelAdjust;
import app.component.risk.risklevelmanage.entity.MfRiskLevelManage;
import app.component.risk.risklevelmanage.feign.MfRiskLevelAdjustFeign;
import app.component.risk.risklevelmanage.feign.MfRiskLevelManageFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类名： MfRiskLevelAdjustController 描述：风险等级调整
 * 
 * @author 仇招
 * @date 2018年5月19日 下午8:15:00
 */
@Controller
@RequestMapping("/mfRiskLevelAdjust")
public class MfRiskLevelAdjustController extends BaseFormBean {
	@Autowired
	private MfRiskLevelAdjustFeign mfRiskLevelAdjustFeign;
	@Autowired
	private MfRiskLevelManageFeign mfRiskLevelManageFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		return "/component/risk/risklevelmanage/MfRiskLevelAdjust_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageNo
	 * @param pageSize
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String tableId, String riskId, String tableType, Integer pageNo,
			Integer pageSize, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setRiskId(riskId);
		try {
			mfRiskLevelAdjust.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRiskLevelAdjust.setCriteriaList(mfRiskLevelAdjust, ajaxData);// 我的筛选
//			mfRiskLevelAdjust.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfRiskLevelAdjust,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRiskLevelAdjust", mfRiskLevelAdjust));
			// 自定义查询Bo方法
			ipage = mfRiskLevelAdjustFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String manageSts) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfRiskLevelAdjustController.class);
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = "riskleveladjustbasemanage";
			if(BizPubParm.RISK_MANAGE_STS4.equals(manageSts) || BizPubParm.RISK_MANAGE_STS5.equals(manageSts) || BizPubParm.RISK_MANAGE_STS6.equals(manageSts) || BizPubParm.RISK_MANAGE_STS7.equals(manageSts) ){
				formId = "riskleveladjustbaseadjust";
			}
			FormData formriskleveladjustbase = formService.getFormData(formId);
			getFormValue(formriskleveladjustbase, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskleveladjustbase)) {
				MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
				setObjValue(formriskleveladjustbase, mfRiskLevelAdjust);
				Map<String, Object> resultMap = mfRiskLevelAdjustFeign.insert(mfRiskLevelAdjust);
				dataMap.put("flag", "success");
				dataMap.put("msg", resultMap.get("msg"));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			logger.error(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formriskleveladjustbase = formService.getFormData("riskleveladjustbase");
		getFormValue(formriskleveladjustbase, getMapByJson(ajaxData));
		MfRiskLevelAdjust mfRiskLevelAdjustJsp = new MfRiskLevelAdjust();
		setObjValue(formriskleveladjustbase, mfRiskLevelAdjustJsp);
		MfRiskLevelAdjust mfRiskLevelAdjust = mfRiskLevelAdjustFeign.getById(mfRiskLevelAdjustJsp);
		if (mfRiskLevelAdjust != null) {
			try {
				mfRiskLevelAdjust = (MfRiskLevelAdjust) EntityUtil.reflectionSetVal(mfRiskLevelAdjust,
						mfRiskLevelAdjustJsp, getMapByJson(ajaxData));
				mfRiskLevelAdjustFeign.update(mfRiskLevelAdjust);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formriskleveladjustbase = formService.getFormData("riskleveladjustbase");
			getFormValue(formriskleveladjustbase, getMapByJson(ajaxData));
			if (this.validateFormData(formriskleveladjustbase)) {
				MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
				setObjValue(formriskleveladjustbase, mfRiskLevelAdjust);
				mfRiskLevelAdjustFeign.update(mfRiskLevelAdjust);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formriskleveladjustbase = formService.getFormData("riskleveladjustbase");
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setId(id);
		mfRiskLevelAdjust = mfRiskLevelAdjustFeign.getById(mfRiskLevelAdjust);
		getObjValue(formriskleveladjustbase, mfRiskLevelAdjust, formData);
		if (mfRiskLevelAdjust != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setId(id);
		try {
			mfRiskLevelAdjustFeign.delete(mfRiskLevelAdjust);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String riskId,String manageSts) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		// 风险等级
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		mfRiskLevelAdjust.setRiskId(riskId);
		mfRiskLevelAdjust.setRiskLevel(mfRiskLevelManage.getRiskLevel());
		mfRiskLevelAdjust.setRiskLevelReason(mfRiskLevelManage.getRiskLevelReason());
		
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelAdjust.setCusName(mfCusCustomer.getCusName());
		mfRiskLevelAdjust.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelAdjust.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelAdjust.setIdType(mfCusCustomer.getIdType());
		String comeFrom = mfRiskLevelManage.getComeFrom();
		String formId = "cusriskleveladjustbase";
		if(BizPubParm.RISK_COME_FROM2.equals(comeFrom)){
			formId = "riskleveladjustbase";
			// 合同信息
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			mfRiskLevelAdjust.setPactAmt(mfBusPact.getPactAmt());
			mfRiskLevelAdjust.setAppName(mfBusPact.getAppName());
			mfRiskLevelAdjust.setBeginDate(mfBusPact.getBeginDate());
			mfRiskLevelAdjust.setEndDate(mfBusPact.getEndDate());
			mfRiskLevelAdjust.setVouType(mfBusPact.getVouType());
			// 借据信息
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
			mfRiskLevelAdjust.setFincShowId(mfBusFincApp.getFincShowId());
			mfRiskLevelAdjust.setPutoutAmt(mfBusFincApp.getPactAmt());
			mfRiskLevelAdjust.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
			mfRiskLevelAdjust.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		}else{
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setCusNo(mfRiskLevelManage.getCusNo());
			List<MfBusFincApp> mfBusFincAppList  = mfBusFincAppFeign.getByCusNo(mfBusFincApp);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablecusfinclist", "thirdTableTag", mfBusFincAppList , null, true);
			model.addAttribute("tableHtml", tableHtml);
			model.addAttribute("mfBusFincAppListSize", mfBusFincAppList.size());
		}
		
		mfRiskLevelAdjust.setId(WaterIdUtil.getWaterId());
		mfRiskLevelAdjust.setManageSts(manageSts);
		CodeUtils cu = new CodeUtils();
		
		JSONArray resultMap = new JSONArray();// 获取新增处置方案
		JSONArray map = cu.getJSONArrayByKeyName("manage_type");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("managePlan", resultMap.toString());
		
		JSONArray jsonArray = cu.getJSONArrayByKeyName("RISK_LEVEL_ADJUST_TYPE");
		JSONArray jsArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String adjustType = jsonObject.getString("optCode");
			if (BizPubParm.RISK_LEVEL_ADJUST_TYPE4.equals(adjustType)) {

			} else {
				jsonObject.put("id", jsonObject.get("optCode"));
				jsonObject.put("name", jsonObject.get("optName"));
				jsArray.add(jsonObject);
			}
		}
		model.addAttribute("adjustType", jsArray.toString());

		if(BizPubParm.RISK_MANAGE_STS4.equals(manageSts) || BizPubParm.RISK_MANAGE_STS5.equals(manageSts) || BizPubParm.RISK_MANAGE_STS6.equals(manageSts) || BizPubParm.RISK_MANAGE_STS7.equals(manageSts) ){
			MfRiskLevelAdjust mfRiskLevelAdjust1 = new MfRiskLevelAdjust();
			mfRiskLevelAdjust1.setRiskId(riskId);
			mfRiskLevelAdjust1 = mfRiskLevelAdjustFeign.getManageTypeByRiskId(mfRiskLevelAdjust);
			mfRiskLevelAdjust.setManageType(mfRiskLevelAdjust1.getManageType());
			mfRiskLevelAdjust.setManageContent(mfRiskLevelAdjust1.getManageContent());
			formId = formId + "adjust";
		}else{
			mfRiskLevelAdjust.setOperateType(BizPubParm.RISK_LEVEL_ADJUST_TYPE4);
			formId = formId + "manage";
		}
		FormData formriskleveladjustbase = formService.getFormData(formId);

		getObjValue(formriskleveladjustbase, mfRiskLevelAdjust);
		model.addAttribute("formriskleveladjustbase", formriskleveladjustbase);
		model.addAttribute("mfRiskLevelAdjust", mfRiskLevelAdjust);
		model.addAttribute("manageSts", manageSts);
		model.addAttribute("comeFrom", comeFrom);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskLevelAdjust_Insert";
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String id, String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setId(id);
		mfRiskLevelAdjust = mfRiskLevelAdjustFeign.getById(mfRiskLevelAdjust);
		if(BizPubParm.RISK_LEVEL_ADJUST_TYPE4.equals(mfRiskLevelAdjust.getOperateType())){
			mfRiskLevelAdjust.setManageOrAdjust(BizPubParm.YES_NO_Y);
		}else{
			mfRiskLevelAdjust.setManageOrAdjust(BizPubParm.YES_NO_N);
		}
		// 风险等级
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(mfRiskLevelAdjust.getRiskId());
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		mfRiskLevelAdjust.setRiskLevel(mfRiskLevelManage.getRiskLevel());
		mfRiskLevelAdjust.setRiskLevelReason(mfRiskLevelManage.getRiskLevelReason());
		mfRiskLevelAdjust.setManageSts(mfRiskLevelManage.getManageSts());
		
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelAdjust.setCusName(mfCusCustomer.getCusName());
		mfRiskLevelAdjust.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelAdjust.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelAdjust.setIdType(mfCusCustomer.getIdType());
		
		String comeFrom = mfRiskLevelManage.getComeFrom();
		String formId = "cusriskleveladjustdetail";
		if(BizPubParm.RISK_COME_FROM2.equals(comeFrom)){
			formId = "riskleveladjustdetail";
			// 合同信息
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			mfRiskLevelAdjust.setPactAmt(mfBusPact.getPactAmt());
			mfRiskLevelAdjust.setAppName(mfBusPact.getAppName());
			mfRiskLevelAdjust.setBeginDate(mfBusPact.getBeginDate());
			mfRiskLevelAdjust.setEndDate(mfBusPact.getEndDate());
			mfRiskLevelAdjust.setVouType(mfBusPact.getVouType());
			// 借据信息
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
			mfRiskLevelAdjust.setFincShowId(mfBusFincApp.getFincShowId());
			mfRiskLevelAdjust.setPutoutAmt(mfBusFincApp.getPactAmt());
			mfRiskLevelAdjust.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
			mfRiskLevelAdjust.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		}
		FormData formriskleveladjustdetail = formService.getFormData(formId);
		getFormValue(formriskleveladjustdetail);
		getObjValue(formriskleveladjustdetail, mfRiskLevelAdjust);
		model.addAttribute("formriskleveladjustdetail", formriskleveladjustdetail);
		model.addAttribute("mfRiskLevelAdjust", mfRiskLevelAdjust);
		model.addAttribute("comeFrom", comeFrom);
		model.addAttribute("id", id);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskLevelAdjust_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formriskleveladjustbase = formService.getFormData("riskleveladjustbase");
		getFormValue(formriskleveladjustbase);
		boolean validateFlag = this.validateFormData(formriskleveladjustbase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formriskleveladjustbase = formService.getFormData("riskleveladjustbase");
		getFormValue(formriskleveladjustbase);
		boolean validateFlag = this.validateFormData(formriskleveladjustbase);
	}

	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String id, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setId(id);
		mfRiskLevelAdjust = mfRiskLevelAdjustFeign.getById(mfRiskLevelAdjust);
		
		// 风险等级
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(mfRiskLevelAdjust.getRiskId());
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		mfRiskLevelAdjust.setRiskLevel(mfRiskLevelManage.getRiskLevel());
		mfRiskLevelAdjust.setRiskLevelReason(mfRiskLevelManage.getRiskLevelReason());
		mfRiskLevelAdjust.setManageSts(mfRiskLevelManage.getManageSts());
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelAdjust.setCusName(mfCusCustomer.getCusName());
		mfRiskLevelAdjust.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelAdjust.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelAdjust.setIdType(mfCusCustomer.getIdType());
		String comeFrom = mfRiskLevelManage.getComeFrom();
		String formId = "CusRiskLevelAdjustApprove";
		if(BizPubParm.RISK_COME_FROM2.equals(comeFrom)){
			formId = "RiskLevelAdjustApprove";
			// 合同信息
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			mfRiskLevelAdjust.setPactAmt(mfBusPact.getPactAmt());
			mfRiskLevelAdjust.setAppName(mfBusPact.getAppName());
			mfRiskLevelAdjust.setBeginDate(mfBusPact.getBeginDate());
			mfRiskLevelAdjust.setEndDate(mfBusPact.getEndDate());
			mfRiskLevelAdjust.setVouType(mfBusPact.getVouType());
			// 借据信息
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);	
			mfRiskLevelAdjust.setPutoutAmt(mfBusFincApp.getPactAmt());
			mfRiskLevelAdjust.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
			mfRiskLevelAdjust.setIntstEndDate(mfBusFincApp.getIntstEndDate());
			mfRiskLevelAdjust.setApprovePartNo(null);
			mfRiskLevelAdjust.setApprovePartName(null);
		}
		if(BizPubParm.RISK_LEVEL_ADJUST_TYPE4.equals(mfRiskLevelAdjust.getOperateType())){
			mfRiskLevelAdjust.setManageOrAdjust(BizPubParm.YES_NO_Y);
			formId = formId + "manage";
		}else{
			mfRiskLevelAdjust.setManageOrAdjust(BizPubParm.YES_NO_N);
			formId = formId + "adjust";
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formRiskLevelAdjustApprove = formService.getFormData(formId);
		// 实体对象放到表单对象中
		getObjValue(formRiskLevelAdjustApprove, mfRiskLevelAdjust);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formRiskLevelAdjustApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		CodeUtils cu = new CodeUtils();
		JSONArray resultMap = new JSONArray();// 获取新增处置方案
		JSONArray map = cu.getJSONArrayByKeyName("manage_type");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("managePlan", resultMap.toString());
		model.addAttribute("id", id);
		model.addAttribute("mfRiskLevelAdjust", mfRiskLevelAdjust);
		model.addAttribute("formRiskLevelAdjustApprove", formRiskLevelAdjustApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/WkfRiskLevelAdjustViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批意见保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
			String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		String formId = "RiskLevelAdjustApprove";
		if(BizPubParm.YES_NO_Y.equals(formDataMap.get("manageOrAdjust"))){
			formId = formId + "manage";
		}else{
			formId = formId + "adjust";
		}
		// 初始化基本信息表单、工作经历表单
		FormData formRiskLevelAdjustApprove = formService.getFormData(formId);
		getFormValue(formRiskLevelAdjustApprove, formDataMap);
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		setObjValue(formRiskLevelAdjustApprove, mfRiskLevelAdjust);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRiskLevelAdjust);
			formDataMap.put("mfRiskLevelAdjust", mfRiskLevelAdjust);
			res = mfRiskLevelAdjustFeign.doCommit(taskId, transition, nextUser, formDataMap);
			dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 提交审批流程
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setId(id);
		mfRiskLevelAdjust = mfRiskLevelAdjustFeign.getById(mfRiskLevelAdjust);
		try {
			Map<String, Object> resultMap = mfRiskLevelAdjustFeign.insert(mfRiskLevelAdjust);
			dataMap.put("flag", "success");
			dataMap.put("msg", resultMap.get("msg"));
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

}
