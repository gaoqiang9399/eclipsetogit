package app.component.interfaces.appinterface.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.cus.entity.BankIdentify;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.entity.MfCusShareholder;
import app.component.cusinterface.CusInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * 钉钉 企业完善
 * 
 * @author MaHao
 * @date 2017-8-24 上午10:09:11
 */
@Controller
@RequestMapping("/dingCusBusiness")
public class DingCusBusinessController extends BaseFormBean {
	/**
	 * 
	 */
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	private Gson gson = new Gson();

	/**
	 * 股东信息 新增页面 {@link app.component.cus.action.MfCusShareholderAction#input()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputShareholder")
	public String inputShareholder(Model model, String cusNo, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusShareholder mfCusShareholder = new MfCusShareholder();
		mfCusShareholder.setCusNo(cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		mfCusShareholder.setCusName(mfCusCustomer.getCusName());
		mfCusShareholder.setCusNo(mfCusCustomer.getCusNo());
		mfCusShareholder.setShareholderId(WaterIdUtil.getWaterId("sha"));
		MfCusFormConfig mfCusFormConfig = cusInterfaceFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusShareholderAction");

		if (mfCusFormConfig == null) {
		} else {
			formId = mfCusFormConfig.getAddModelDef();
			model.addAttribute("formId", formId);
		}
		FormData formcussha00003 = null;
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusShareholderAction表单信息没有查询到");
		} else {
			formcussha00003 = formService.getFormData(formId);
			if (formcussha00003.getFormId() == null) {
				// logger.error( "客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusShareholderAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcussha00003);
				getObjValue(formcussha00003, mfCusShareholder);
			}
		}
		if (null != formcussha00003) {
			JSONArray gdCusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("GD_CUS_TYPE");
			this.changeFormProperty(formcussha00003, "shareholderType", "optionArray", gdCusTypeJsonArray);
			JSONArray idTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("ID_TYPE");
			this.changeFormProperty(formcussha00003, "idType", "optionArray", idTypeJsonArray);
			JSONArray pushCapitalTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
			this.changeFormProperty(formcussha00003, "pushCapitalType", "optionArray", pushCapitalTypeJsonArray);
			sortFormDataActives(formcussha00003);
			String formcusJson = gson.toJson(formcussha00003);
			model.addAttribute("formcusJson", formcusJson);
		}
		// 返回出资方式json
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		String ajaxData = items;
		model.addAttribute("formcussha00003", formcussha00003);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusShareholder_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertShareholderAjax")
	@ResponseBody
	public Map<String, Object> insertShareholderAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = cusInterfaceFeign.getByCusType("base", "MfCusShareholderAction").getAddModel();
			}
			FormData formcussha00003 = formService.getFormData(formId);
			getFormValue(formcussha00003, map);
			if (this.validateFormData(formcussha00003)) {
				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				setObjValue(formcussha00003, mfCusShareholder);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusShareholder.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusShareholder.setCusName(cusName);
				cusInterfaceFeign.insertMfCusShareholder(mfCusShareholder);

				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusShareholder.getCusNo());// 更新客户信息完整度
				String cusNo = mfCusShareholder.getCusNo();
				mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				// String tableHtml =
				// jtu.getJsonStr("tablecussha00002","tableTag",
				// (List<MfCusShareholder>)mfCusShareholderFeign.findByPage(ipage,
				// mfCusShareholder).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 单字段编辑股东信息 ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShareholderByOneAjax")
	@ResponseBody
	public Map<String, Object> updateShareholderByOneAjax(String formId, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusShareholder mfCusShareholder = new MfCusShareholder();

		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusShareholderAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcussha00002 = formService.getFormData(formId);
		getFormValue(formcussha00002, getMapByJson(ajaxData));
		MfCusShareholder mfCusShareholderNew = new MfCusShareholder();
		setObjValue(formcussha00002, mfCusShareholderNew);
		mfCusShareholder.setShareholderId(mfCusShareholderNew.getShareholderId());
		mfCusShareholder = cusInterfaceFeign.getMfCusShareholderById(mfCusShareholder);
		if (mfCusShareholder != null) {
			try {
				mfCusShareholder = (MfCusShareholder) EntityUtil.reflectionSetVal(mfCusShareholder, mfCusShareholderNew,
						getMapByJson(ajaxData));
				cusInterfaceFeign.updateMfCusShareholder(mfCusShareholder);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 新增页面 高管信息 {@link app.component.cus.action.MfCusHighInfoAction#input()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputHighManager")
	public String inputHighManager(Model model, String cusNo, String formId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
		mfCusHighInfo.setCusNo(cusNo);
		mfCusHighInfo.setHighId(WaterIdUtil.getWaterId());
		String cusType = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();

		MfCusFormConfig mfCusFormConfig = cusInterfaceFeign.getByCusType(cusType, "MfCusHighInfoAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcushigh00003 = null;
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusHighInfoAction表单信息没有查询到");
		} else {
			formcushigh00003 = formService.getFormData(formId);
			this.changeFormProperty(formcushigh00003, "cusNo", "initValue", mfCusHighInfo.getCusNo());
			this.changeFormProperty(formcushigh00003, "highId", "initValue", mfCusHighInfo.getHighId());
			if (formcushigh00003.getFormId() == null) {
				// logger.error(
				// "客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusHighInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcushigh00003);
			}
		}
		if (null != formcushigh00003) {
			CodeUtils codeUtils = new CodeUtils();
			JSONArray highTypeJsonArray = codeUtils.getJSONArrayByKeyName("HIGH_TYPE");
			this.changeFormProperty(formcushigh00003, "highCusType", "optionArray", highTypeJsonArray);// 重新给客户类型赋值
			JSONArray persIdTypeJsonArray = codeUtils.getJSONArrayByKeyName("PERS_ID_TYPE");
			this.changeFormProperty(formcushigh00003, "idType", "optionArray", persIdTypeJsonArray);// 重新给客户类型赋值
			JSONArray sexJsonArray = codeUtils.getJSONArrayByKeyName("SEX");
			this.changeFormProperty(formcushigh00003, "sex", "optionArray", sexJsonArray);// 重新给客户类型赋值
			JSONArray eduJsonArray = codeUtils.getJSONArrayByKeyName("EDU");
			this.changeFormProperty(formcushigh00003, "education", "optionArray", eduJsonArray);// 重新给客户类型赋值

			sortFormDataActives(formcushigh00003);
			String formcusJson = gson.toJson(formcushigh00003);
			model.addAttribute("formcusJson", formcusJson);
		}
		model.addAttribute("formcushigh00003", formcushigh00003);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusHighManagerInfo_Insert";
	}

	/**
	 * AJAX新增
	 * 高管新增{@link app.component.cus.action.MfCusHighInfoAction#insertAjax()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertHighManagerAjax")
	@ResponseBody
	public Map<String, Object> insertHighManagerAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = cusInterfaceFeign.getByCusType("base", "MfCusHighInfoAction").getAddModel();
			}
			FormData formcushigh00003 = formService.getFormData(formId);
			getFormValue(formcushigh00003, map);
			if (this.validateFormData(formcushigh00003)) {
				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				setObjValue(formcushigh00003, mfCusHighInfo);
				String cusType = "";
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusHighInfo.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusHighInfo.setCusName(cusName);
				cusInterfaceFeign.insertMfCusHighInfo(mfCusHighInfo);
				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusHighInfo.getCusNo());// 更新客户信息完整度
				String cusNo = mfCusCustomer.getCusNo();
				mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				// String tableHtml =
				// jtu.getJsonStr("tablecushigh00001","tableTag",
				// (List<MfCusHighInfo>)mfCusHighInfoFeign.findByPage(ipage,
				// mfCusHighInfo).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHighInfoByOneAjax")
	@ResponseBody
	public Map<String, Object> updateHighInfoByOneAjax(String formId, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();

		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusHighInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcushigh00004 = formService.getFormData(formId);
		getFormValue(formcushigh00004, getMapByJson(ajaxData));
		MfCusHighInfo mfCusHighInfoNew = new MfCusHighInfo();
		setObjValue(formcushigh00004, mfCusHighInfoNew);
		mfCusHighInfo.setHighId(mfCusHighInfoNew.getHighId());
		mfCusHighInfo = cusInterfaceFeign.getMfCusHighInfoById(mfCusHighInfo);
		if (mfCusHighInfo != null) {
			try {
				mfCusHighInfo = (MfCusHighInfo) EntityUtil.reflectionSetVal(mfCusHighInfo, mfCusHighInfoNew,
						getMapByJson(ajaxData));
				cusInterfaceFeign.updateMfCusHighInfo(mfCusHighInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 新增页面 对外投资 {@link app.component.cus.action.MfCusEquityInfoAction#input()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputEquityInfo")
	public String inputEquityInfo(String formId, Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		mfCusEquityInfo.setCusNo(cusNo);

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);

		mfCusEquityInfo.setCusName(mfCusCustomer.getCusName());
		mfCusEquityInfo.setCusNo(mfCusCustomer.getCusNo());
		MfCusFormConfig mfCusFormConfig = cusInterfaceFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfCusEquityInfoAction");

		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusequ00003 = null;
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + mfCusCustomer.getCusType() +
			// "的MfCusEquityInfoAction表单信息没有查询到");
		} else {
			formcusequ00003 = formService.getFormData(formId);
			if (formcusequ00003.getFormId() == null) {
				// logger.error(
				// "客户类型为" + mfCusCustomer.getCusType() +
				// "的MfCusEquityInfoAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusequ00003);
				getObjValue(formcusequ00003, mfCusEquityInfo);
			}
		}
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
		String items = map.toString().replaceAll("optName", "name").replace("optCode", "id");
		if (null != formcusequ00003) {
			JSONArray pushCapitalTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("PUSH_CAPITAL_TYPE");
			this.changeFormProperty(formcusequ00003, "pushCapitalType", "optionArray", pushCapitalTypeJsonArray);
			sortFormDataActives(formcusequ00003);
			String formcusJson = gson.toJson(formcusequ00003);
			model.addAttribute("formcusJson", formcusJson);
		}
		String ajaxData = items;
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("formcusequ00003", formcusequ00003);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusEquityInfo_Insert";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertEquityInfoAjax")
	@ResponseBody
	public Map<String, Object> insertEquityInfoAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = cusInterfaceFeign.getByCusType("base", "MfCusEquityInfoAction").getAddModel();
			}
			FormData formcusequ00003 = formService.getFormData(formId);

			getFormValue(formcusequ00003, map);
			if (this.validateFormData(formcusequ00003)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				setObjValue(formcusequ00003, mfCusEquityInfo);
				if (mfCusEquityInfo.getPushCapitalType().length() == 1) {
					mfCusEquityInfo.setPushCapitalType(mfCusEquityInfo.getPushCapitalType() + "|");
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusEquityInfo.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusEquityInfo.setCusName(cusName);
				cusInterfaceFeign.insertMfCusEquityInfo(mfCusEquityInfo);
				// getTableData();
				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusEquityInfo.getCusNo());// 更新客户信息完整度
				mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(mfCusCustomer.getCusNo());
				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				// String tableHtml =
				// jtu.getJsonStr("tablecusequ00001","tableTag",
				// (List<MfCusEquityInfo>)mfCusEquityInfoFeign.findByPage(ipage,
				// mfCusEquityInfo).getResult(), null,true);
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateEquityInfoByOneAjax")
	@ResponseBody
	public Map<String, Object> updateEquityInfoByOneAjax(String formId, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusEquityInfoAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcusequ00004 = formService.getFormData(formId);
		getFormValue(formcusequ00004, getMapByJson(ajaxData));
		MfCusEquityInfo mfCusEquityInfoNew = new MfCusEquityInfo();
		setObjValue(formcusequ00004, mfCusEquityInfoNew);
		mfCusEquityInfo.setEquityInfoId(mfCusEquityInfoNew.getEquityInfoId());
		mfCusEquityInfo = cusInterfaceFeign.getMfCusEquityInfoById(mfCusEquityInfo);
		if (mfCusEquityInfo != null) {
			try {
				mfCusEquityInfo = (MfCusEquityInfo) EntityUtil.reflectionSetVal(mfCusEquityInfo, mfCusEquityInfoNew,
						getMapByJson(ajaxData));
				cusInterfaceFeign.updateMfCusEquityInfo(mfCusEquityInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * 新增页面 {@link app.component.cus.action.MfCusBankAccManageAction#input()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBankAccManage")
	public String inputBankAccManage(Model model, String formId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		mfCusBankAccManage.setCusNo(cusNo);

		String cusType = "";
		String cusName = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();
		cusName = mfCusCustomer.getCusName();

		MfCusFormConfig mfCusFormConfig = cusInterfaceFeign.getByCusType(cusType, "MfCusBankAccManageAction");

		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		FormData formcusbank00003 = null;
		if (StringUtil.isEmpty(formId)) {
			// logger.error("客户类型为" + cusType +
			// "的MfCusBankAccManageAction表单信息没有查询到");
		} else {
			formcusbank00003 = formService.getFormData(formId);
			if (formcusbank00003.getFormId() == null) {
				// logger.error("客户类型为" + cusType +
				// "的MfCusBankAccManageAction表单form" + formId + ".xml文件不存在");
			} else {
				getFormValue(formcusbank00003);
				mfCusBankAccManage.setCusName(cusName);
				mfCusBankAccManage.setUseFlag("1");// 默认启用
				getObjValue(formcusbank00003, mfCusBankAccManage);
			}
		}
		if (null != formcusbank00003) {
			CodeUtils codeUtils = new CodeUtils();
			JSONArray fundAccTypeJsonArray = codeUtils.getJSONArrayByKeyName("FUND_ACC_TYPE");
			this.changeFormProperty(formcusbank00003, "useType", "optionArray", fundAccTypeJsonArray);
			JSONArray useFlagJsonArray = codeUtils.getJSONArrayByKeyName("USE_FLAG");
			this.changeFormProperty(formcusbank00003, "useFlag", "optionArray", useFlagJsonArray);
			sortFormDataActives(formcusbank00003);
			String formcusJson = gson.toJson(formcusbank00003);
			model.addAttribute("formcusJson", formcusJson);
		}
		model.addAttribute("formcusbank00003", formcusbank00003);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/DingCusBankAccManage_Insert";
	}

	/**
	 * AJAX新增{@link app.component.cus.action.MfCusBankAccManageAction#insertAjax()
	 * throws Exception}
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertBankAccManageAjax")
	@ResponseBody
	public Map<String, Object> insertBankAccManageAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map map = getMapByJson(ajaxData);
			String formId = (String) map.get("formId");
			if (StringUtil.isEmpty(formId)) {
				formId = cusInterfaceFeign.getByCusType("base", "MfCusBankAccManageAction").getAddModel();
			}
			FormData formcusbank00003 = formService.getFormData(formId);
			getFormValue(formcusbank00003, map);
			if (this.validateFormData(formcusbank00003)) {
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				setObjValue(formcusbank00003, mfCusBankAccManage);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfCusBankAccManage.getCusNo());
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusBankAccManage.setCusName(cusName);
				mfCusBankAccManage = cusInterfaceFeign.insertMfCusBankAccManage(mfCusBankAccManage);
				dataMap.put("mfCusBankAccManage", mfCusBankAccManage);
				String infIntegrity = cusInterfaceFeign.updateInfIntegrity(mfCusBankAccManage.getCusNo());// 更新客户信息完整度
				// getTableData();
				mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(mfCusCustomer.getCusNo());
				// Ipage ipage = this.getIpage();
				// JsonTableUtil jtu = new JsonTableUtil();
				// String tableHtml =
				// jtu.getJsonStr("tablecusbank00003","tableTag",
				// (List<MfCusBankAccManage>)mfCusBankAccManageFeign.findByPage(ipage,
				// mfCusBankAccManage).getResult(), null,true);
				//
				// dataMap.put("htmlStr", tableHtml);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBankAccByOneAjax")
	@ResponseBody
	public Map<String, Object> updateBankAccByOneAjax(String formId, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = cusInterfaceFeign.getByCusType("base", "MfCusBankAccManageAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formcusbank00002 = formService.getFormData(formId);
		getFormValue(formcusbank00002, getMapByJson(ajaxData));
		MfCusBankAccManage mfCusBankAccManageNew = new MfCusBankAccManage();
		setObjValue(formcusbank00002, mfCusBankAccManageNew);
		mfCusBankAccManage.setId(mfCusBankAccManageNew.getId());
		mfCusBankAccManage = cusInterfaceFeign.getMfCusBankAccManageById(mfCusBankAccManage);
		if (mfCusBankAccManage != null) {
			try {
				mfCusBankAccManage = (MfCusBankAccManage) EntityUtil.reflectionSetVal(mfCusBankAccManage,
						mfCusBankAccManageNew, getMapByJson(ajaxData));
				cusInterfaceFeign.updateCusOtherInfo("mf_cus_bank_acc_manage", mfCusBankAccManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBankInfoByIdAjax")
	@ResponseBody
	public Map<String, Object> getBankInfoByIdAjax(String identifyNumber) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BankIdentify bankIdentify = new BankIdentify();
		bankIdentify.setIdentifyNumber(identifyNumber);
		dataMap = (HashMap<String, Object>) cusInterfaceFeign.getDataMapById(bankIdentify);
		return dataMap;
	}

	/**
	 * 按照表单设计器设计的xml表单排序字段，先横排再竖排，从上到下
	 * 
	 * @param formData
	 * @author MaHao
	 * @date 2017-8-24 上午11:07:47
	 */
	private void sortFormDataActives(FormData formData) {
		if (null != formData) {
			FormActive[] formActives = formData.getFormActives();
			// 给表单里的字段排序
			Arrays.sort(formActives, new Comparator<FormActive>() {
				@Override
				public int compare(FormActive o1, FormActive o2) { // 先比较行，行相同比较列
					if (o1.getRow() == o2.getRow()) {
						if (o1.getFieldCol() == o2.getFieldCol()) {
							return 0;
						} else if (o1.getFieldCol() <= o2.getFieldCol()) {
							return -1;
						} else {
							return 1;
						}
					}
					if (o1.getRow() <= o2.getRow()) {
						return -1;
					} else {
						return 1;
					}
				}
			});
		}
	}

}
