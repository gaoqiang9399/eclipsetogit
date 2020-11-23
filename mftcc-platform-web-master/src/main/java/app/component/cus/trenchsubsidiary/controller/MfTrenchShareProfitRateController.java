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

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfBusTrenchFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.trenchsubsidiary.entity.MfShareProfitConfig;
import app.component.cus.trenchsubsidiary.entity.MfTrenchShareProfitRate;
import app.component.cus.trenchsubsidiary.feign.MfShareProfitConfigFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchShareProfitRateFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfTrenchShareProfitRateAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 06 15:00:20 CST 2018
 **/
@Controller
@RequestMapping("/mfTrenchShareProfitRate")
public class MfTrenchShareProfitRateController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfTrenchShareProfitRateFeign mfTrenchShareProfitRateFeign;
	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;
	@Autowired
	private MfShareProfitConfigFeign mfShareProfitConfigFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);

		return "/component/cus/trenchsubsidiary/MfTrenchShareProfitRate_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		try {
			mfTrenchShareProfitRate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfTrenchShareProfitRate.setCriteriaList(mfTrenchShareProfitRate, ajaxData);// 我的筛选
			// mfTrenchShareProfitRate.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfTrenchShareProfitRate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfTrenchShareProfitRate", mfTrenchShareProfitRate));
			ipage = mfTrenchShareProfitRateFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = (String)map.get("formId");
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			String cusNo = (String)map.get("trenchUid");
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			if(StringUtil.isEmpty(formId)){
				String cusType = mfCusCustomer.getCusType();
				formId = mfCusFormConfigFeign.getByCusType(cusType, "MfTrenchShareProfitRateAction").getAddModel();
			}
			FormData formTrenchProfitRate0001 = formService.getFormData(formId);
			getFormValue(formTrenchProfitRate0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formTrenchProfitRate0001)) {
				MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
				setObjValue(formTrenchProfitRate0001, mfTrenchShareProfitRate);
				mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign.insert(mfTrenchShareProfitRate);
				
				String infIntegrity = mfCusCustomerFeign.updateInfIntegrity(mfTrenchShareProfitRate.getTrenchUid(),mfTrenchShareProfitRate.getTrenchUid());//更新客户信息完整度
								
				String tableFormId = "TrenchProfitRateList";//放款表单
				MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
				mfShareProfitConfig.setCusNo(cusNo);
				mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
				if(mfShareProfitConfig != null){
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
							
				}
				Ipage ipage = this.getIpage();
				JsonTableUtil jtu = new JsonTableUtil();
				ipage.setParams(this.setIpageParams("mfTrenchShareProfitRate", mfTrenchShareProfitRate));
				String htmlStr = jtu.getJsonStr("table" + tableFormId, "tableTag",
						(List<MfTrenchShareProfitRate>) mfTrenchShareProfitRateFeign.findByPage(ipage).getResult(),
						null, true);
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("infIntegrity", infIntegrity);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formTrenchProfitRate0002 = new FormService().getFormData("TrenchProfitRate0002");
		getFormValue(formTrenchProfitRate0002, getMapByJson(ajaxData));
		MfTrenchShareProfitRate mfTrenchShareProfitRateJsp = new MfTrenchShareProfitRate();
		setObjValue(formTrenchProfitRate0002, mfTrenchShareProfitRateJsp);
		MfTrenchShareProfitRate mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign
				.getById(mfTrenchShareProfitRateJsp);
		if (mfTrenchShareProfitRate != null) {
			try {
				mfTrenchShareProfitRate = (MfTrenchShareProfitRate) EntityUtil.reflectionSetVal(mfTrenchShareProfitRate,
						mfTrenchShareProfitRateJsp, getMapByJson(ajaxData));
				mfTrenchShareProfitRateFeign.update(mfTrenchShareProfitRate);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}

		return dataMap;
	}

	@RequestMapping(value = "/updateByOneAjax")
	@ResponseBody
	public Map<String, Object> updateByOneAjax(String formId, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		// 这里得到的formId是带form字符串的，比如formcuscorp0001
		if (StringUtil.isEmpty(formId)) {
			formId = mfCusFormConfigFeign.getByCusType("base", "MfTrenchShareProfitRateAction").getShowModel();
		} else {
			if (formId.indexOf("form") == -1) {
			} else {
				formId = formId.substring(4);
			}
		}
		FormData formTrenchProfitRate0001 = new FormService().getFormData(formId);
		getFormValue(formTrenchProfitRate0001, getMapByJson(ajaxData));
		MfTrenchShareProfitRate trenchShareProfitRate = new MfTrenchShareProfitRate();
		setObjValue(formTrenchProfitRate0001, trenchShareProfitRate);
		mfTrenchShareProfitRate.setId(trenchShareProfitRate.getId());
		mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign.getById(trenchShareProfitRate);
		if (mfTrenchShareProfitRate != null) {
			try {
				mfTrenchShareProfitRate = (MfTrenchShareProfitRate) EntityUtil.reflectionSetVal(mfTrenchShareProfitRate,
						trenchShareProfitRate, getMapByJson(ajaxData));
				mfTrenchShareProfitRateFeign.update(mfTrenchShareProfitRate);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		try {
			FormData formTrenchProfitRate0002 = new FormService().getFormData("TrenchProfitRate0002");
			getFormValue(formTrenchProfitRate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formTrenchProfitRate0002)) {
				mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
				setObjValue(formTrenchProfitRate0002, mfTrenchShareProfitRate);
				mfTrenchShareProfitRateFeign.update(mfTrenchShareProfitRate);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}

		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formTrenchProfitRate0002 = new FormService().getFormData("TrenchProfitRate0002");
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		mfTrenchShareProfitRate.setId(id);
		mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign.getById(mfTrenchShareProfitRate);
		getObjValue(formTrenchProfitRate0002, mfTrenchShareProfitRate, formData);
		if (mfTrenchShareProfitRate != null) {
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		mfTrenchShareProfitRate.setId(id);
		try {
			mfTrenchShareProfitRateFeign.delete(mfTrenchShareProfitRate);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
	public String input(Model model, String cusNo,String relNo,String baseType) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfTrenchShareProfitRateController.class);
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formTrenchProfitRate0001 = null;
		String cusType = "";
		String formId = "";
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(cusType, "MfTrenchShareProfitRateAction");
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getAddModelDef();
		}
		if(StringUtil.isEmpty(formId)){
			logger.error("客户类型为"+cusType+"的MfTrenchShareProfitRateAction表单信息没有查询到");
		}else{
			formTrenchProfitRate0001 = formService.getFormData(formId);
			if(formTrenchProfitRate0001.getFormId() == null){
				logger.error("客户类型为"+cusType+"的MfTrenchShareProfitRateAction表单form"+formId+".xml文件不存在");
			}else{
				MfBusTrench mfBusTrench = new MfBusTrench();
				mfBusTrench.setTrenchUid(cusNo);
				switch(baseType){
				case BizPubParm.CUS_BASE_TYPE_QUDAO ://渠道商
					mfBusTrench.setTrenchUid(cusNo);
					mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);	
					break;
				case BizPubParm.CUS_BASE_TYPE_QUDAOB ://个人渠道
					mfBusTrench.setTrenchUid(cusNo);
					mfBusTrench = mfBusTrenchFeign.getByUId(mfBusTrench);	
					break;
				case BizPubParm.CUS_BASE_TYPE_ZIJIN://资金机构
					MfBusAgencies mfBusAgencies = new MfBusAgencies();
					mfBusAgencies.setAgenciesUid(cusNo);
					mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
					mfBusTrench.setTrenchName(mfBusAgencies.getAgenciesName());
					break;
				default :
					break;
				}
				MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
				mfShareProfitConfig.setCusNo(cusNo);
				mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
				if(mfShareProfitConfig == null){
					logger.error("缺少分润配置信息");
				}
				MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
				mfTrenchShareProfitRate.setMatchType(mfShareProfitConfig.getMatchType());
				mfTrenchShareProfitRate.setCalcCoefficient(mfShareProfitConfig.getCalcCoefficient());
				mfTrenchShareProfitRate.setRelNo(cusNo);
				mfTrenchShareProfitRate.setTrenchUid(cusNo);
				List<MfTrenchShareProfitRate> mfTrenchShareProfitRateList = mfTrenchShareProfitRateFeign.getListBytrenchUid(mfTrenchShareProfitRate);
				if(mfTrenchShareProfitRateList == null || mfTrenchShareProfitRateList.size() == 0){
					mfTrenchShareProfitRate.setBaseStartAmt(0.00);
					mfTrenchShareProfitRate.setBaseStartCount(0);
					mfTrenchShareProfitRate.setSort(1);
				}else{
					mfTrenchShareProfitRate.setBaseStartAmt(mfTrenchShareProfitRateList.get(mfTrenchShareProfitRateList.size() - 1).getBaseEndAmt());
					mfTrenchShareProfitRate.setBaseStartCount(mfTrenchShareProfitRateList.get(mfTrenchShareProfitRateList.size() - 1).getBaseEndCount());
					mfTrenchShareProfitRate.setSort(mfTrenchShareProfitRateList.get(mfTrenchShareProfitRateList.size() - 1).getSort() + 1);
				}
				getObjValue(formTrenchProfitRate0001, mfBusTrench);
				getObjValue(formTrenchProfitRate0001, mfTrenchShareProfitRate);
			}
		}

		model.addAttribute("formTrenchProfitRate0001", formTrenchProfitRate0001);
		model.addAttribute("relNo", relNo);
		model.addAttribute("baseType", baseType);
		model.addAttribute("query", "");
		return "/component/cus/trenchsubsidiary/MfTrenchShareProfitRate_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);

		FormData formTrenchProfitRate0001 = new FormService().getFormData("TrenchProfitRate0001");
		getFormValue(formTrenchProfitRate0001);
		MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
		mfTrenchShareProfitRate.setId(id);
		mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign.getById(mfTrenchShareProfitRate);
		getObjValue(formTrenchProfitRate0001, mfTrenchShareProfitRate);

		model.addAttribute("formTrenchProfitRate0001", formTrenchProfitRate0001);
		model.addAttribute("mfTrenchShareProfitRate", mfTrenchShareProfitRate);

		return "/component/cus/trenchsubsidiary/MfTrenchShareProfitRate_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formTrenchProfitRate0002 = new FormService().getFormData("TrenchProfitRate0002");
		getFormValue(formTrenchProfitRate0002);
		this.validateFormData(formTrenchProfitRate0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formTrenchProfitRate0002 = new FormService().getFormData("TrenchProfitRate0002");
		getFormValue(formTrenchProfitRate0002);
		this.validateFormData(formTrenchProfitRate0002);
	}

	// 列表展示详情，单字段编辑
	@RequestMapping(value = "/listShowDetailAjax")
	@ResponseBody
	public Map<String, Object> listShowDetailAjax(String cusNo, String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String query = "";
		Logger logger = LoggerFactory.getLogger(MfTrenchShareProfitRateController.class);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(),
				"MfTrenchShareProfitRateAction");
		String formId = null;
		if (mfCusFormConfig == null) {

		} else {
			formId = mfCusFormConfig.getShowModelDef();
		}
		if (StringUtil.isEmpty(formId)) {
			 logger.error("客户类型为" + mfCusCustomer.getCusType() +
			 "的MfTrenchShareProfitRateAction表单信息没有查询到");
			dataMap.put("msg", "客户类型为" + mfCusCustomer.getCusType() + "的MfTrenchShareProfitRateAction表单信息没有查询到");
			dataMap.put("flag", "error");
		} else {
			Map<String, Object> formData = new HashMap<String, Object>();
			request.setAttribute("ifBizManger", "3");
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			FormData formTrenchProfitRate0001 = new FormService().getFormData(formId);
			MfTrenchShareProfitRate mfTrenchShareProfitRate = new MfTrenchShareProfitRate();
			mfTrenchShareProfitRate.setId(id);
			mfTrenchShareProfitRate = mfTrenchShareProfitRateFeign.getById(mfTrenchShareProfitRate);
			MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
			mfShareProfitConfig.setCusNo(cusNo);
			mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
			if(mfShareProfitConfig == null){
				logger.error("缺少分润配置信息");
			}
			mfTrenchShareProfitRate.setMatchType(mfShareProfitConfig.getMatchType());
			mfTrenchShareProfitRate.setCalcCoefficient(mfShareProfitConfig.getCalcCoefficient());
			getObjValue(formTrenchProfitRate0001, mfTrenchShareProfitRate, formData);
			query = sysInterfaceFeign.getQueryResult(User.getRegNo(request));
			if(query == null){
				query = "";
			}
			String htmlStrCorp = jsonFormUtil.getJsonStr(formTrenchProfitRate0001, "propertySeeTag", query);
			dataMap.put("formHtml", htmlStrCorp);
			dataMap.put("flag", "success");
			dataMap.put("formData", mfTrenchShareProfitRate);
		}

		return dataMap;
	}

}
