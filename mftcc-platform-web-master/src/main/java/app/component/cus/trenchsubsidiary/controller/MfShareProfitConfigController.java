package app.component.cus.trenchsubsidiary.controller;

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
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.trenchsubsidiary.entity.MfShareProfitConfig;
import app.component.cus.trenchsubsidiary.entity.MfTrenchShareProfitRate;
import app.component.cus.trenchsubsidiary.feign.MfShareProfitConfigFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchShareProfitRateFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * 类名： MfShareProfitConfigController 描述：分润配置
 * 
 * @author 仇招
 * @date 2018年9月3日 上午10:47:57
 */
@Controller
@RequestMapping("/mfShareProfitConfig")
public class MfShareProfitConfigController extends BaseFormBean {
	@Autowired
	private MfShareProfitConfigFeign mfShareProfitConfigFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfTrenchShareProfitRateFeign mfTrenchShareProfitRateFeign;
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
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/trenchsubsidiary/MfShareProfitConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		try {
			mfShareProfitConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfShareProfitConfig.setCriteriaList(mfShareProfitConfig, ajaxData);// 我的筛选
			mfShareProfitConfig.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfShareProfitConfig", mfShareProfitConfig));
			ipage = mfShareProfitConfigFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
			FormData formMfShareProfitConfigBase = formService.getFormData(ajaxDataMap.get("formId").toString());
			getFormValue(formMfShareProfitConfigBase, getMapByJson(ajaxData));
			if (this.validateFormData(formMfShareProfitConfigBase)) {
				MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
				setObjValue(formMfShareProfitConfigBase, mfShareProfitConfig);
				mfShareProfitConfig.setId(WaterIdUtil.getWaterId("spc"));
				mfShareProfitConfigFeign.insert(mfShareProfitConfig);

				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(mfShareProfitConfig.getCusNo());
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				// 更新资料完整度
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfShareProfitConfig.getCusNo(),
						mfShareProfitConfig.getCusNo());// 更新客户信息完整度
				String formId = "MfShareProfitConfigDetail";
				MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
						"MfShareProfitConfigAction");
				if (mfCusFormConfig != null) {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfShareProfitConfig);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				request.setAttribute("ifBizManger", "3");
				String htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
		FormData formMfShareProfitConfigBase = formService.getFormData((String) ajaxDataMap.get("formId"));
		getFormValue(formMfShareProfitConfigBase, getMapByJson(ajaxData));
		MfShareProfitConfig mfShareProfitConfigJsp = new MfShareProfitConfig();
		setObjValue(formMfShareProfitConfigBase, mfShareProfitConfigJsp);
		if (StringUtil.isEmpty(mfShareProfitConfigJsp.getBaseConfigId())) {
			dataMap.put("flag", "success");
			dataMap.put("msg", "保存成功");
			return dataMap;
		}
		MfShareProfitConfig mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfigJsp);
		// 获取基础分润配置信息
		MfShareProfitConfig mfShareProfitConfig1 = new MfShareProfitConfig();
		mfShareProfitConfig1.setId(mfShareProfitConfigJsp.getBaseConfigId());
		mfShareProfitConfig1 = mfShareProfitConfigFeign.getById(mfShareProfitConfig1);

		if (mfShareProfitConfig != null && mfShareProfitConfig1 != null) {
			try {
				// 将基础分润配置信息赋值给mfShareProfitConfig
				mfShareProfitConfig.setCalcCoefficient(mfShareProfitConfig1.getCalcCoefficient());
				mfShareProfitConfig.setCalcFormula(mfShareProfitConfig1.getCalcFormula());
				mfShareProfitConfig.setCalcMethod(mfShareProfitConfig1.getCalcMethod());
				mfShareProfitConfig.setCalcBase(mfShareProfitConfig1.getCalcBase());
				mfShareProfitConfig.setMatchType(mfShareProfitConfig1.getMatchType());
				mfShareProfitConfig.setConfigName(mfShareProfitConfig1.getConfigName());
				mfShareProfitConfig = (MfShareProfitConfig) EntityUtil.reflectionSetVal(mfShareProfitConfig,
						mfShareProfitConfigJsp, getMapByJson(ajaxData));
				mfShareProfitConfigFeign.update(mfShareProfitConfig);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		try {
			FormData formMfShareProfitConfigBase = formService.getFormData("MfShareProfitConfigBase");
			getFormValue(formMfShareProfitConfigBase, getMapByJson(ajaxData));
			if (this.validateFormData(formMfShareProfitConfigBase)) {
				mfShareProfitConfig = new MfShareProfitConfig();
				setObjValue(formMfShareProfitConfigBase, mfShareProfitConfig);
				mfShareProfitConfigFeign.update(mfShareProfitConfig);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setId(id);
		mfShareProfitConfig.setCusNo(cusNo);
		mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
		if (mfShareProfitConfig != null) {
			dataMap.put("mfShareProfitConfig", mfShareProfitConfig);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取分润规则配置信息失败");
		}
		return dataMap;
	}

	/**
	 * 方法描述： 根据客户号查找分润配置
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月3日 下午7:14:27
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setCusNo(cusNo);
		mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (mfShareProfitConfig != null) {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			String formId = "MfShareProfitConfigDetail";
			MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
					"MfShareProfitConfigAction");
			if (mfCusFormConfig != null) {
				formId = mfCusFormConfig.getShowModelDef();
			}
			FormData formcommon = formService.getFormData(formId);
			getFormValue(formcommon);
			getObjValue(formcommon, mfShareProfitConfig);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			request.setAttribute("ifBizManger", "3");
			String htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", "");
			dataMap.put("htmlStr", htmlStr);
			
			String tableFormId = "TrenchProfitRateList";//放款表单
			String calcBase = mfShareProfitConfig.getCalcBase();
			String calcCoefficient = mfShareProfitConfig.getCalcCoefficient();
			if(BizPubParm.MATCH_TYPE_1.equals(calcBase) || BizPubParm.MATCH_TYPE_3.equals(calcBase)){
				tableFormId = "TrenchProfitRateList_Count";//放款次数，客户数
				if(BizPubParm.CALC_COEFFICIENT_1.equals(calcCoefficient)){
					tableFormId = "TrenchProfitRateList_Count_Fixed";//放款次数，客户数
				}
			}else{
				if(BizPubParm.CALC_COEFFICIENT_1.equals(calcCoefficient)){
					tableFormId = "TrenchProfitRateList_Fixed";//放款次数，客户数
				}
			}
			Ipage ipage = this.getIpage();
			JsonTableUtil jtu = new JsonTableUtil();
			MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
			mfTrenchShareProfitRate.setTrenchUid(cusNo);
			ipage.setParams(this.setIpageParams("mfTrenchShareProfitRate", mfTrenchShareProfitRate));
			String shareProfitRateHtmlStr = jtu.getJsonStr("table" + tableFormId, "tableTag",
					(List<MfTrenchShareProfitRate>) mfTrenchShareProfitRateFeign.findByPage(ipage).getResult(),
					null, true);
			dataMap.put("shareProfitRateHtmlStr", shareProfitRateHtmlStr);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取分润规则配置信息失败");
		}
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setId(id);
		try {
			mfShareProfitConfigFeign.delete(mfShareProfitConfig);
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
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setCusNo(cusNo);
		mfShareProfitConfig.setCusName(mfCusCustomer.getCusName());
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfShareProfitConfigAction");
		FormData formMfShareProfitConfigBase = formService.getFormData(mfCusFormConfig.getAddModelDef());
		getObjValue(formMfShareProfitConfigBase, mfShareProfitConfig);
		model.addAttribute("formMfShareProfitConfigBase", formMfShareProfitConfigBase);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		return "/component/cus/trenchsubsidiary/MfShareProfitConfig_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setId(id);
		mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
		FormData formMfShareProfitConfigDetail = formService.getFormData("MfShareProfitConfigShow");
		getFormValue(formMfShareProfitConfigDetail);
		getObjValue(formMfShareProfitConfigDetail, mfShareProfitConfig);
		model.addAttribute("formMfShareProfitConfigDetail", formMfShareProfitConfigDetail);
		model.addAttribute("query", "");
		return "/component/cus/trenchsubsidiary/MfShareProfitConfig_Detail";
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
		FormData formMfShareProfitConfigBase = formService.getFormData("MfShareProfitConfigBase");
		getFormValue(formMfShareProfitConfigBase);
		boolean validateFlag = this.validateFormData(formMfShareProfitConfigBase);
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
		FormData formMfShareProfitConfigBase = formService.getFormData("MfShareProfitConfigBase");
		getFormValue(formMfShareProfitConfigBase);
		boolean validateFlag = this.validateFormData(formMfShareProfitConfigBase);
	}

	/**
	 * 方法描述： 检查分润配置
	 * 
	 * @param id
	 * @param cusNo
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月4日 上午11:36:05
	 */
	@RequestMapping(value = "/checkShareProfitConfigAjax")
	@ResponseBody
	public Map<String, Object> checkShareProfitConfigAjax(String cusNo) {
		Logger logger = LoggerFactory.getLogger(MfShareProfitConfigController.class);
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setCusNo(cusNo);
		try {
			dataMap = mfShareProfitConfigFeign.checkShareProfitConfig(mfShareProfitConfig);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取分润规则配置信息失败");
			logger.error("获取分润规则配置信息失败",e);
		}
		return dataMap;
	}
}
