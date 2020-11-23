package app.component.risk.risklevelmanage.controller;

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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
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
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 类名： MfRiskLevelManageController 描述：风险管理
 * 
 * @author 仇招
 * @date 2018年5月19日 下午8:14:44
 */
@Controller
@RequestMapping("/mfRiskLevelManage")
public class MfRiskLevelManageController extends BaseFormBean {
	@Autowired
	private MfRiskLevelManageFeign mfRiskLevelManageFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfRiskLevelAdjustFeign mfRiskLevelAdjustFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model, String comeFrom) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("comeFrom", comeFrom);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray risllevelJsonArray = codeUtils.getJSONArrayByKeyName("EXAM_RISK_LEVEL");
		model.addAttribute("risllevelJsonArray", risllevelJsonArray);
		return "/component/risk/risklevelmanage/MfRiskLevelManage_List";
	}
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRiskRgtListPage")
	public String getRiskRgtListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray risllevelJsonArray = codeUtils.getJSONArrayByKeyName("EXAM_RISK_LEVEL");
		model.addAttribute("risllevelJsonArray", risllevelJsonArray);
		return "/component/risk/risklevelmanage/MfRiskRegistration_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageNo
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String tableId, String tableType, Integer pageNo, Integer pageSize,
			String ajaxData, String comeFrom) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setComeFrom(comeFrom);
		try {
			mfRiskLevelManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRiskLevelManage.setCriteriaList(mfRiskLevelManage, ajaxData);// 我的筛选
			mfRiskLevelManage.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfRiskLevelManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRiskLevelManage", mfRiskLevelManage));
			ipage = mfRiskLevelManageFeign.findByPage(ipage);
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
	/***
	 * 列表数据查询
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageNo
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findRiskRgtByPageAjax")
	@ResponseBody
	public Map<String, Object> findRiskRgtByPageAjax(String tableId, String tableType, Integer pageNo, Integer pageSize,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		try {
			mfRiskLevelManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRiskLevelManage.setCriteriaList(mfRiskLevelManage, ajaxData);// 我的筛选
			mfRiskLevelManage.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfRiskLevelManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRiskLevelManage", mfRiskLevelManage));
			ipage = mfRiskLevelManageFeign.findRiskRgtByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
			getFormValue(formrisklevelmanagebase, getMapByJson(ajaxData));
			if (this.validateFormData(formrisklevelmanagebase)) {
				MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
				MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
				setObjValue(formrisklevelmanagebase, mfRiskLevelManage);
				setObjValue(formrisklevelmanagebase, mfRiskLevelAdjust);
				mfRiskLevelManage.setComeFrom(BizPubParm.RISK_COME_FROM1);
				mfRiskLevelManageFeign.insert(mfRiskLevelManage);
				mfRiskLevelAdjust.setOperateType(BizPubParm.RISK_LEVEL_ADJUST_TYPE4);
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
	@RequestMapping("/insertRiskRgtAjax")
	@ResponseBody
	public Map<String, Object> insertRiskRgtAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrisklevelmanagebase = formService.getFormData("riskregistrationbase");
			getFormValue(formrisklevelmanagebase, getMapByJson(ajaxData));
			if (this.validateFormData(formrisklevelmanagebase)) {
				MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
				setObjValue(formrisklevelmanagebase, mfRiskLevelManage);
				mfRiskLevelManage.setApplySts(BizPubParm.APP_STS_UN_SUBMIT);
				mfRiskLevelManageFeign.insert(mfRiskLevelManage);
				mfRiskLevelManage = mfRiskLevelManageFeign.submitProcess(mfRiskLevelManage);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfRiskLevelManage.getApproveNodeName());
				paramMap.put("opNo", mfRiskLevelManage.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
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
		FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
		getFormValue(formrisklevelmanagebase, getMapByJson(ajaxData));
		MfRiskLevelManage mfRiskLevelManageJsp = new MfRiskLevelManage();
		setObjValue(formrisklevelmanagebase, mfRiskLevelManageJsp);
		MfRiskLevelManage mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManageJsp);
		if (mfRiskLevelManage != null) {
			try {
				mfRiskLevelManage = (MfRiskLevelManage) EntityUtil.reflectionSetVal(mfRiskLevelManage,
						mfRiskLevelManageJsp, getMapByJson(ajaxData));
				mfRiskLevelManageFeign.update(mfRiskLevelManage);
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
			FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
			getFormValue(formrisklevelmanagebase, getMapByJson(ajaxData));
			if (this.validateFormData(formrisklevelmanagebase)) {
				MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
				setObjValue(formrisklevelmanagebase, mfRiskLevelManage);
				mfRiskLevelManageFeign.update(mfRiskLevelManage);
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
	 * @param riskId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String riskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		getObjValue(formrisklevelmanagebase, mfRiskLevelManage, formData);
		if (mfRiskLevelManage != null) {
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
	 * @param riskId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String riskId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		try {
			mfRiskLevelManageFeign.delete(mfRiskLevelManage);
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
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
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
		
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(WaterIdUtil.getWaterId());
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setRiskId(mfRiskLevelManage.getRiskId());
		mfRiskLevelAdjust.setOperateType(BizPubParm.RISK_LEVEL_ADJUST_TYPE4);
		String id = WaterIdUtil.getWaterId();
		mfRiskLevelAdjust.setId(id);
		getObjValue(formrisklevelmanagebase, mfRiskLevelManage);
		getObjValue(formrisklevelmanagebase, mfRiskLevelAdjust);
		model.addAttribute("formrisklevelmanagebase", formrisklevelmanagebase);
		model.addAttribute("id", id);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskLevelManage_Insert";
	}
	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputRiskRgt")
	public String inputRiskRgt(Model model,String cusNo,String fincShowId,String pactNo,String appId,String comeFrom) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrisklevelmanagebase = formService.getFormData("riskregistrationbase");
		
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		String riskId = WaterIdUtil.getWaterId();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage.setComeFrom(comeFrom);
		mfRiskLevelManage.setCusNo(cusNo);
		mfRiskLevelManage.setPactNo(pactNo);
		mfRiskLevelManage.setFincShowId(fincShowId);
		mfRiskLevelManage.setAppId(appId);
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelManage.setCusName(mfCusCustomer.getCusName());
		mfRiskLevelManage.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelManage.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelManage.setIdType(mfCusCustomer.getIdType());
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfRiskLevelManage.setPactAmt(mfBusPact.getPactAmt());
		mfRiskLevelManage.setAppName(mfBusPact.getAppName());
		mfRiskLevelManage.setBeginDate(mfBusPact.getBeginDate());
		mfRiskLevelManage.setEndDate(mfBusPact.getEndDate());
		mfRiskLevelManage.setVouType(mfBusPact.getVouType());
		// 借据信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfRiskLevelManage.setFincShowId(mfBusFincApp.getFincShowId());
		mfRiskLevelManage.setPutoutAmt(mfBusFincApp.getPactAmt());
		mfRiskLevelManage.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
		mfRiskLevelManage.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		//
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		if (mfBusApply!=null) {
			mfRiskLevelManage.setAuxiliaryPersonnel(mfBusApply.getManageOpNo2());
		}
		
		getObjValue(formrisklevelmanagebase, mfRiskLevelManage);
		model.addAttribute("formrisklevelmanagebase", formrisklevelmanagebase);
		model.addAttribute("riskId", riskId);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskLevelManage_RiskRgtInsert";
	}

	/**
	 * 查询
	 * 
	 * @param riskId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String riskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		mfRiskLevelManage.setRegTime(DateUtil.getShowDateTime(mfRiskLevelManage.getRegTime()));
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelManage.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelManage.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelManage.setIdType(mfCusCustomer.getIdType());
		String comeFrom = mfRiskLevelManage.getComeFrom();
		String formId = "cusrisklevelmanagedetail";
		if (BizPubParm.RISK_COME_FROM2.equals(comeFrom)) {
			formId = "risklevelmanagedetail";
			// 合同信息
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
			mfBusPact = mfBusPactFeign.getById(mfBusPact);
			mfRiskLevelManage.setPactAmt(mfBusPact.getPactAmt());
			mfRiskLevelManage.setAppName(mfBusPact.getAppName());
			mfRiskLevelManage.setBeginDate(mfBusPact.getBeginDate());
			mfRiskLevelManage.setEndDate(mfBusPact.getEndDate());
			mfRiskLevelManage.setVouType(mfBusPact.getVouType());
			// 借据信息
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
			mfBusFincApp.setFincId(mfRiskLevelManage.getFincId());
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
			mfRiskLevelManage.setPutoutAmt(mfBusFincApp.getPactAmt());
			mfRiskLevelManage.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
			mfRiskLevelManage.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		}else{
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setCusNo(mfRiskLevelManage.getCusNo());
			List<MfBusFincApp> mfBusFincAppList  = mfBusFincAppFeign.getByCusNo(mfBusFincApp);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablecusfinclist", "thirdTableTag", mfBusFincAppList , null, true);
			model.addAttribute("tableHtml", tableHtml);
			model.addAttribute("mfBusFincAppListSize", mfBusFincAppList.size());
		}
		FormData formrisklevelmanagedetail = formService.getFormData(formId);
		getFormValue(formrisklevelmanagedetail);
		getObjValue(formrisklevelmanagedetail, mfRiskLevelManage);

		Map<String, Object> dataMap = getByRiskId(riskId);
		model.addAttribute("formrisklevelmanagedetail", formrisklevelmanagedetail);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("riskId", riskId);
		model.addAttribute("comeFrom", comeFrom);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskLevelManage_Detail";
	}
	
	/**
	 * 查询
	 * 
	 * @param riskId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRiskRgtById")
	public String getRiskRgtById(Model model, String riskId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		mfRiskLevelManage.setRegTime(DateUtil.getShowDateTime(mfRiskLevelManage.getRegTime()));
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelManage.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelManage.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelManage.setIdType(mfCusCustomer.getIdType());
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfRiskLevelManage.setPactAmt(mfBusPact.getPactAmt());
		mfRiskLevelManage.setAppName(mfBusPact.getAppName());
		mfRiskLevelManage.setBeginDate(mfBusPact.getBeginDate());
		mfRiskLevelManage.setEndDate(mfBusPact.getEndDate());
		mfRiskLevelManage.setVouType(mfBusPact.getVouType());
		// 借据信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfRiskLevelManage.setPutoutAmt(mfBusFincApp.getPactAmt());
		mfRiskLevelManage.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
		mfRiskLevelManage.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		FormData formrisklevelmanagedetail = formService.getFormData("riskRegistrationDetail");
		getFormValue(formrisklevelmanagedetail);
		getObjValue(formrisklevelmanagedetail, mfRiskLevelManage);
		
		model.addAttribute("formrisklevelmanagedetail", formrisklevelmanagedetail);
		model.addAttribute("riskId", riskId);
		model.addAttribute("mfRiskLevelManage", mfRiskLevelManage);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/MfRiskRegistration_Detail";
	}
	
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String riskId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		
		// 风险等级
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		// 客户信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfRiskLevelManage.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		mfRiskLevelManage.setCusName(mfCusCustomer.getCusName());
		mfRiskLevelManage.setCusType(mfCusCustomer.getCusType());
		mfRiskLevelManage.setIdNum(mfCusCustomer.getIdNum());
		mfRiskLevelManage.setIdType(mfCusCustomer.getIdType());
		// 合同信息
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactNo(mfRiskLevelManage.getPactNo());
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		mfRiskLevelManage.setPactAmt(mfBusPact.getPactAmt());
		mfRiskLevelManage.setAppName(mfBusPact.getAppName());
		mfRiskLevelManage.setBeginDate(mfBusPact.getBeginDate());
		mfRiskLevelManage.setEndDate(mfBusPact.getEndDate());
		mfRiskLevelManage.setVouType(mfBusPact.getVouType());
		// 借据信息
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincShowId(mfRiskLevelManage.getFincShowId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);	
		mfRiskLevelManage.setPutoutAmt(mfBusFincApp.getPactAmt());
		mfRiskLevelManage.setIntstBeginDate(mfBusFincApp.getIntstBeginDate());
		mfRiskLevelManage.setIntstEndDate(mfBusFincApp.getIntstEndDate());
		mfRiskLevelManage.setApprovePartNo(null);
		mfRiskLevelManage.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(riskId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formRiskLevelManageApprove = formService.getFormData("riskLevelManageApprove");
		// 实体对象放到表单对象中
		getObjValue(formRiskLevelManageApprove, mfRiskLevelManage);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formRiskLevelManageApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("riskId", riskId);
		model.addAttribute("mfRiskLevelManage", mfRiskLevelManage);
		model.addAttribute("formRiskLevelManageApprove", formRiskLevelManageApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/risk/risklevelmanage/WkfRiskLevelManageViewPoint";
	}
	@RequestMapping("/getByRiskId")
	@ResponseBody
	public Map<String, Object> getByRiskId(String riskId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelAdjust mfRiskLevelAdjust = new MfRiskLevelAdjust();
		mfRiskLevelAdjust.setRiskId(riskId);
		List<MfRiskLevelAdjust> mfRiskLevelAdjustList = mfRiskLevelAdjustFeign.getByRiskId(mfRiskLevelAdjust);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tableriskleveladjustlist", "thirdTableTag", mfRiskLevelAdjustList, null,
				true);
		dataMap.put("tableHtml", tableHtml);
		if (mfRiskLevelAdjustList == null || mfRiskLevelAdjustList.size() == 0) {
			dataMap.put("size", BizPubParm.YES_NO_N);
		} else {
			dataMap.put("size", BizPubParm.YES_NO_Y);
		}
		return dataMap;
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
		FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
		getFormValue(formrisklevelmanagebase);
		boolean validateFlag = this.validateFormData(formrisklevelmanagebase);
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
		FormData formrisklevelmanagebase = formService.getFormData("risklevelmanagebase");
		getFormValue(formrisklevelmanagebase);
		boolean validateFlag = this.validateFormData(formrisklevelmanagebase);
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
		// 初始化基本信息表单、工作经历表单
		FormData formRiskLevelManageApprove = formService.getFormData("riskLevelManageApprove");
		getFormValue(formRiskLevelManageApprove, formDataMap);
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		setObjValue(formRiskLevelManageApprove, mfRiskLevelManage);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRiskLevelManage);
			formDataMap.put("mfRiskLevelManage", mfRiskLevelManage);
			res = mfRiskLevelManageFeign.doCommit(taskId, transition, nextUser, formDataMap);
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
	public Map<String, Object> submitProcessAjax(String riskId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRiskLevelManage mfRiskLevelManage = new MfRiskLevelManage();
		mfRiskLevelManage.setRiskId(riskId);
		mfRiskLevelManage = mfRiskLevelManageFeign.getById(mfRiskLevelManage);
		try {
			mfRiskLevelManage = mfRiskLevelManageFeign.submitProcess(mfRiskLevelManage);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfRiskLevelManage.getApproveNodeName());
			paramMap.put("opNo", mfRiskLevelManage.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

}
