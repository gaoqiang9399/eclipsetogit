package app.component.cus.controller;

import app.base.Criteria;
import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.entity.MfCusCreditConfig;
import app.component.auth.entity.MfCusCreditModel;
import app.component.auth.entity.MfCusCreditUseHis;
import app.component.auth.feign.MfCusCreditConfigFeign;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.common.PasConstant;
import app.component.common.ViewUtil;
import app.component.cus.contract.entity.MfCusContract;
import app.component.cus.contract.feign.MfCusContractFeign;
import app.component.cus.cooperating.entity.MfCusCooperativeAgency;
import app.component.cus.cooperating.feign.MfCusCooperativeAgencyFeign;
import app.component.cus.courtinfo.entity.MfCusCourtInfo;
import app.component.cus.courtinfo.feign.MfCusCourtInfoFeign;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.component.cus.cusinvoicemation.feign.MfCusInvoiceMationFeign;
import app.component.cus.cuslevel.entity.MfCusClassify;
import app.component.cus.cuslevel.feign.MfCusClassifyFeign;
import app.component.cus.cusmemanage.entity.MfCusMeManage;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.*;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.*;
import app.component.cus.gualoan.feign.MfCusGuaLoanOuterSumFeign;
import app.component.cus.intangibleassets.entity.MfCusIntangibleAssets;
import app.component.cus.intangibleassets.feign.MfCusIntangibleAssetsFeign;
import app.component.cus.judgment.entity.MfCusJudgment;
import app.component.cus.judgment.feign.MfCusJudgmentFeign;
import app.component.cus.memeanage.feign.MfCusMeManageFeign;
import app.component.cus.relation.entity.Child;
import app.component.cus.relation.feign.MfCusRelationFeign;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cus.trenchsubsidiary.feign.MfTrenchUserFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.eval.entity.AppEval;
import app.component.eval.entity.MfEvalGradeCard;
import app.component.evalinterface.EvalInterfaceFeign;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.component.nmd.entity.ParmDic;
import app.component.oa.trans.entity.MfOaTrans;
import app.component.oa.trans.feign.MfOaTransFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pfs.entity.CusFinMain;
import app.component.pfsinterface.PfsInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfKindTableConfig;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.risk.entity.RiskBizItemRel;
import app.component.riskinterface.RiskInterfaceFeign;
import app.component.sms.feign.SMSUtilFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysUserFeign;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.component.thirdservice.lmxd.entity.MfThirdServiceRecord;
import app.component.thirdservice.lmxd.feign.MfThirdServiceRecordFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.CacheUtil;
import cn.mftcc.util.HttpClientUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;


/**
 * Title: MfCusCustomerAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Fri May 20 09:13:03 CST 2016
 **/
@Controller
@RequestMapping("/mfCusCustomer")
public class MfCusCustomerController extends BaseFormBean {

	private static Logger log = LoggerFactory.getLogger(MfCusCustomerController.class);

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private RiskInterfaceFeign riskInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	private JSONObject json;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private EvalInterfaceFeign evalInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusWarehouseFeign mfCusWarehouseFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfCusSellInfoFeign mfCusSellInfoFeign;
	@Autowired
	private MfCusRelationFeign mfCusRelationFeign;
	@Autowired
	private MfCusCreditInvestigateInfoFeign mfCusCreditInvestigateInfoFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfThirdServiceRecordFeign mfThirdServiceRecordFeign;
	@Autowired
	private MfCusAssureCompanyFeign mfCusAssureCompanyFeign;
	@Autowired
	private MfCusPersonIncomeSurveyFeign mfCusPersonIncomeSurveyFeign;
	@Autowired
	private MfCusApplyPersonSurveyFeign mfCusApplyPersonSurveyFeign;
	@Autowired
	private MfCusApplySpouseSurveyFeign mfCusApplySpouseSurveyFeign;
	@Autowired
	private MfCusAccountDebtorFeign mfCusAccountDebtorFeign;
	@Autowired
	private MfCusAccountDetailFeign mfCusAccountDetailFeign;
	@Autowired
	private MfCusListedInfoFeign mfCusListedInfoFeign;
	@Autowired
	private MfCusPersonFlowAssetsInfoFeign mfCusPersonFlowAssetsInfoFeign;
	@Autowired
	private MfCusClassifyFeign mfCusClassifyFeign;

	@Autowired
	private MfCusAssureOutsideFeign mfCusAssureOutsideFeign;

	@Autowired
	private MfCusCorpBaseInfoFeign mfCusCorpBaseInfoFeign;
	@Autowired
	private MfCusPersonLiabilitiesFeign mfCusPersonLiabilitiesFeign;
	@Autowired
	private MfCusShareholderFeign mfCusShareholderFeign;
	@Autowired
	private MfCusHighInfoFeign mfCusHighInfoFeign;
	@Autowired
	private MfCusFarmerEconoInfoFeign mfCusFarmerEconoInfoFeign;//
	@Autowired
	private MfCusFarmerIncExpeFeign mfCusFarmerIncExpeFeign;//
	@Autowired
	private MfCusReputationInfoFeign mfCusReputationInfoFeign;//
	@Autowired
	private MfCusSurveySocialCreditFeign mfCusSurveySocialCreditFeign;//
	@Autowired
	private MfCusAssetsFeign mfCusAssetsFeign;
	@Autowired
	private MfCusEquityInfoFeign mfCusEquityInfoFeign;
	@Autowired
	private MfCusBankAccManageFeign mfCusBankAccManageFeign;
	@Autowired
	private MfCusLegalMemberFeign mfCusLegalMemberFeign;
	@Autowired
	private MfCusStaffFeign mfCusStaffFeign;
	@Autowired
	private MfCusPersonJobFeign mfCusPersonJobFeign;
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	@Autowired
	private MfCusCooperativeAgencyFeign mfCusCooperativeAgencyFeign;
	@Autowired
	private MfCusCapitalInfoFeign mfCusCapitalInfoFeign;
	@Autowired
	private MfCusCashEnumFeign mfCusCashEnumFeign;
	@Autowired
	private MfCusProfitInfoFeign mfCusProfitInfoFeign;
	@Autowired
	private MfCusBankAcceptanceBillFeign mfCusBankAcceptanceBillFeign;
	@Autowired
	private MfCusGuaranteeOuterFeign mfCusGuaranteeOuterFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusGoodsFeign mfCusGoodsFeign;
	@Autowired
	private MfCusLegalEmployInfoFeign mfCusLegalEmployInfoFeign;
	@Autowired
	private MfCusLegalEquityInfoFeign mfCusLegalEquityInfoFeign;
	@Autowired
	private MfCusCorpMajorChangeFeign mfCusCorpMajorChangeFeign;
	@Autowired
	private MfCusPersonCorpFeign mfCusPersonCorpFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	@Autowired
	private MfCusPersonIncExpeFeign mfCusPersonIncExpeFeign;
	@Autowired
	private MfCusPersonAssetsInfoFeign mfCusPersonAssetsInfoFeign;
	@Autowired
	private MfCusPersonDebtInfoFeign mfCusPersonDebtInfoFeign;
	@Autowired
	private MfCusPersonCreditInfoFeign mfCusPersonCreditInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;
	@Autowired
	private PfsInterfaceFeign pfsInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;

	@Autowired
	private MfBusTrenchFeign mfBusTrenchFeign;

	@Autowired
	private MfTrenchUserFeign mfTrenchUserFeign;
	@Autowired
	private SysOrgFeign sysOrgFeign;
	@Autowired
	private MfCusBranchOrganizationFeign mfCusBranchOrganizationFeign;
	@Autowired
	private MfCusGuaLoanOuterSumFeign mfCusGuaLoanOuterSumFeign;
	@Autowired
	private MfCusExecNoticeFeign mfCusExecNoticeFeign;
	@Autowired
	private MfCusJudgmentFeign mfCusJudgmentFeign;
	@Autowired
	private MfCusCourtInfoFeign mfCusCourtInfoFeign;
	@Autowired
	private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
	@Autowired
	private MfCusLogisticsInformationFeign mfCusLogisticsInformationFeign;
	@Autowired
	private MfCusChattelMortgageFeign mfCusChattelMortgageFeign;
	@Autowired
	private MfCusBorrowerInfoFeign mfCusBorrowerInfoFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private MfCusCorpLoanFeign mfCusCorpLoanFeign;
	@Autowired
	private MfCusCultureFeign mfCusCultureFeign;
	@Autowired
	private MfCusShedFeign mfCusShedFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusSaleProductFeign mfCusSaleProductFeign;
	@Autowired
	private MfCusPlantBreedFeign mfCusPlantBreedFeign;
	@Autowired
	private MfCusBusServiceFeign mfCusBusServiceFeign;
	@Autowired
	private MfCusProfitLossFeign mfCusProfitLossFeign;
	@Autowired
	private SysTaskInfoInterfaceFeign sysTaskInfoInterfaceFeign;
	@Autowired
	private MfCusAssetsDebtsFeign mfCusAssetsDebtsFeign;
	@Autowired
	private MfOaTransFeign mfOaTransFeign;
	@Autowired
	private MfCusLegalinfoFeign mfCusLegalinfoFeign;
	@Autowired
	private BusiCacheInterface busiCacheInterface;
	@Autowired
	private MfCusSharePledgeFeign mfCusSharePledgeFeign;

	@Autowired
	private SMSUtilFeign smsUtilFeign;

	@Autowired
	private MfCusDesignatedRecipientFeign mfCusDesignatedRecipientFeign;

	@Autowired
	private MfCusMeManageFeign mfCusMeManageFeign;

	@Autowired
	private MfCusInvoiceMationFeign mfCusInvoiceMationFeign;
	@Autowired
	private MfCusMainBusinessFeign mfCusMainBusinessFeign;

	@Autowired
	private MfCusIntangibleAssetsFeign mfCusIntangibleAssetsFeign;

	@Autowired
	private MfCusContractFeign mfCusContractFeign;
	@Autowired
	private MfCusReportAcountFeign mfCusReportAcountFeign;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCustomer_List";
	}

	/**
	 * 列表打开黑名单页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBlickListPage")
	public String getBlickListPage() throws Exception {
		ActionContext.initialize(request, response);

		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);

		return "/component/cus/MfBlackList_List";
	}

	/**
	 * 列表打开优质客户页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	// 优质用户请求
	@RequestMapping(value = "/getQualityListPage")
	public String getQualityListPage() throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取
		JSONArray jsonArrayByKeyName = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("jsonArrayByKeyName", jsonArrayByKeyName);

		return "/component/cus/MfQualityList_List";
	}

	/**
	 * 打开合作机构列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCoAgencyListPage")
	public String getCoAgencyListPage() throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray.toString());
		//业务身份
		JSONArray cusTypeSetJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE_SET");
		this.getHttpRequest().setAttribute("cusTypeSetJsonArray", cusTypeSetJsonArray.toString());
		return "/component/institutions/MfBusAgencies_List";
	}

	/**
	 * 方法描述： 选择客户和客户联系人的弹窗，selectType为1选择客户，selectType为2选择客户联系人
	 *
	 * @return MfCusCustomer_ListForSelect
	 * @throws Exception String
	 * @author lcl
	 * @date 2017-1-18 上午9:59:45
	 */
	@RequestMapping(value = "/getListPageForSelect")
	public String getListPageForSelect(Model model,String cusType,String selectType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusType", cusType);
		model.addAttribute("selectType", selectType);
		return "/component/cus/MfCusCustomer_ListForSelect";
	}

	/***
	 * 客户分类（优质客户/黑名单）列表数据查询
	 * @author GuoJian
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @date 2017-8-7 15:31:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCusClassifyListByPageAjax")
	@ResponseBody
	public Map<String, Object> findCusClassifyListByPageAjax(String ajaxData,String classifyType, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusAndClassify cusAndClassify = new CusAndClassify();
		try {
			cusAndClassify.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusAndClassify.setCustomSorts(ajaxData);
			cusAndClassify.setCriteriaList(cusAndClassify, ajaxData);// 我的筛选
			cusAndClassify.setClassifyType(classifyType);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("cusAndClassify",cusAndClassify));

			// 自定义查询Feign方法
			ipage =mfCusCustomerFeign.getCusAndClassifyPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法 dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 查询合作机构 ：核心企业、资金机构、仓储机构
	 *
	 * @author LiuAo
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCoAgencyByPageAjax")
	@ResponseBody
	public Map<String, Object> findCoAgencyByPageAjax(String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);// 智能搜索
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 业务身份为1,2,3,4,5,6是合作机构 0是借款客户
			mfCusCustomer.setTypeNos(new String []{"0"});//sql查询的时候，将业务身份为0 的给排除即可
			// this.getRoleConditions(mfCusCustomer,"1000000001");//记录级权限控制方法
//			mfCusCustomer.setCusType("0");
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findCoAgencyByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法 dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 * @param ajaxData
	 * @param removeCusId
	 * @param cusType
	 * @param cusBaseType
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, String removeCusId, String cusType, String cusBaseType, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 关联关系查询时要移除关联主体客户id 显示所有其他客户
			if (!StringUtil.isBlank(removeCusId)) {
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> outerList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_no not in ");
				c.setValue(new String[] { removeCusId });
				outerList.add(c);
				clist.add(outerList);
			}
			if(StringUtil.isNotEmpty(cusBaseType)){
				mfCusCustomer.setCusBaseType(cusBaseType);
			}
			if(StringUtil.isNotEmpty(cusType)){
				mfCusCustomer.setCusType(cusType);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/findByBusPage")
	@ResponseBody
	public Map<String, Object> findByBusPage(String ajaxData, String removeCusId, String cusType, String cusBaseType, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 关联关系查询时要移除关联主体客户id 显示所有其他客户
			if (!StringUtil.isBlank(removeCusId)) {
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> outerList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_no not in ");
				c.setValue(new String[] { removeCusId });
				outerList.add(c);
				clist.add(outerList);
			}
			if(StringUtil.isNotEmpty(cusBaseType)){
				mfCusCustomer.setCusBaseType(cusBaseType);
			}
			if(StringUtil.isNotEmpty(cusType)){
				mfCusCustomer.setCusType(cusType);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findByBusPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findPerAndCoreByPageAjax")
	@ResponseBody
	public Map<String, Object> findPerAndCoreByPageAjax(String ajaxData,Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findPerAndCoreByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findPerAndCoreHaveLoanByPageAjax")
	@ResponseBody
	public Map<String, Object> findPerAndCoreHaveLoanByPageAjax(String ajaxData,Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findPerAndCoreHaveLoanByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findPerAndCoreResultByPageAjax")
	@ResponseBody
	public Map<String, Object> findPerAndCoreResultByPageAjax(String ajaxData,Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findPerAndCoreByPage(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获取待清理的客户列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCleanListPage")
	public String getCleanListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCustomer_cleanList";
	}


	@RequestMapping(value = "/findByPageForSelectAjax")
	@ResponseBody
	public Map<String, Object> findByPageForSelectAjax(String ajaxData, String cusBaseType, Integer pageNo,String removeCusId) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 关联关系查询时要移除关联主体客户id 显示所有其他客户
			if (!StringUtil.isBlank(removeCusId)) {
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> outerList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_no not in ");
				c.setValue(new String[] { removeCusId });
				outerList.add(c);
				clist.add(outerList);
			}
			if(StringUtil.isNotEmpty(cusBaseType)&&cusBaseType.indexOf(",")>0){
				//说明cusBaseType为多个
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> cusBaseTypeList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_base_type in ");
				c.setValue(cusBaseType.split(","));
				cusBaseTypeList.add(c);
				clist.add(cusBaseTypeList);
			}else{
				mfCusCustomer.setCusBaseType(cusBaseType);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findByPage(ipage);
			dataMap.put("ipage", ipage);
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
	 * 方法描述： 待清理客户列表查询
	 *
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @date 2017-4-7 下午2:31:39
	 */
	@RequestMapping(value = "/findCleanListByPageAjax")
	@ResponseBody
	public Map<String, Object> findCleanListByPageAjax(String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setCustomSorts(ajaxData);// 数据库级别自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 查询与指定合同有关的所有客户
	 *
	 * @return String
	 * @throws Exception
	 * @author LiuAo
	 * @param ajaxData
	 * @param cusType
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 */
	@RequestMapping(value = "/findByPactAjax")
	@ResponseBody
	public Map<String, Object> findByPactAjax(String ajaxData, String cusType, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			// mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			// mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setCustomSorts(ajaxData);// 排序参数
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(cusType);// cusType记录着合同编号
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			// 自定义查询Feign方法
			ipage = mfCusCustomerFeign.findCusByPact(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法 dataMap.put("tableData",tableHtml);
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
	 * 方法描述：
	 *
	 * @return
	 * @throws Exception String
	 * @author 谢静霞
	 * @date 2017-1-17 下午4:17:38
	 */
	@RequestMapping(value = "/findCusPhone")
	public String findCusPhone(Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		JSONArray cusPhoneArray = JSONArray.fromObject(mfCusCustomerFeign.findByPageTmp(mfCusCustomer));
		for (int i = 0; i < cusPhoneArray.size(); i++) {
			cusPhoneArray.getJSONObject(i).put("id", cusPhoneArray.getJSONObject(i).getString("cusNo"));
			cusPhoneArray.getJSONObject(i).put("name", cusPhoneArray.getJSONObject(i).getString("cusName") + "(" + cusPhoneArray.getJSONObject(i).getString("contactsTel") + ")");
			cusPhoneArray.getJSONObject(i).put("pId", "0");
			cusPhoneArray.getJSONObject(i).put("open", true);
		}
		model.addAttribute("cusPhoneArray", cusPhoneArray);
		return "/component/cus/MfCusCustomer_cusPhone";
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @param ajaxData
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcus00002 = formService.getFormData("cus00002");
		getFormValue(formcus00002, getMapByJson(ajaxData));
		MfCusCustomer mfCusCustomerJsp = new MfCusCustomer();
		setObjValue(formcus00002, mfCusCustomerJsp);
		MfCusCustomer mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomerJsp);
		if (mfCusCustomer != null) {
			try {
				mfCusCustomer = (MfCusCustomer) EntityUtil.reflectionSetVal(mfCusCustomer, mfCusCustomerJsp, getMapByJson(ajaxData));
				mfCusCustomerFeign.update(mfCusCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * @param ajaxData
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcus00002 = formService.getFormData("cus00002");
			getFormValue(formcus00002, getMapByJson(ajaxData));
			if (this.validateFormData(formcus00002)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formcus00002, mfCusCustomer);
				mfCusCustomerFeign.update(mfCusCustomer);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @param cusNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcus00002 = formService.getFormData("cus00002");
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		getObjValue(formcus00002, mfCusCustomer, formData);
		if (mfCusCustomer != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		dataMap.put("contacts_name", mfCusCustomer.getContactsName());
		dataMap.put("contacts_tel", mfCusCustomer.getContactsTel());
		dataMap.put("id_num", mfCusCustomer.getIdNum());
		dataMap.put("comm_address", mfCusCustomer.getCommAddress());
		return dataMap;
	}

	/**
	 * @author czk
	 * @param cusNo
	 * @Description: 根据客户号获得客户信息 date 2016-8-25
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCusByIdAjax")
	@ResponseBody
	public Map<String, Object> getCusByIdAjax(String cusNo,String idNum) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		if(StringUtil.isNotEmpty(cusNo)){
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		}else{
			mfCusCustomer.setIdNum(idNum);
			List<MfCusCustomer> mfCusCustomerTemp =mfCusCustomerFeign.getByIdNum(mfCusCustomer);
			if(mfCusCustomerTemp.size()>0){
				mfCusCustomer = mfCusCustomerTemp.get(0);
			}else{
				dataMap.put("flag", "error");
			}
		}
		if (mfCusCustomer != null&&StringUtil.isNotEmpty(cusNo)) {
			dataMap.put("flag", "success");
			//添加客户是否重名判断
			List<MfCusCustomer> listCus = cusInterfaceFeign.getByCusName(mfCusCustomer.getCusName());
			if(listCus.size()>1){
				dataMap.put("cusName", listCus.get(0).getCusName());
				dataMap.put("listCus", "1");
			}else{
				dataMap.put("listCus", "0");
			}
			dataMap.put("cusInfo", mfCusCustomer);
		} else {
			dataMap.put("flag", "error");
		}
		//客户分类信息
		dataMap.put("rankType", "3");//默认
		dataMap.put("rankTypeName", "普通客户");//默认
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setCusNo(cusNo);
		List<MfCusClassify> mfCusClassifyList = mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
		if (mfCusClassifyList.size() > 0) {
			mfCusClassify = mfCusClassifyList.get(0);
			dataMap.put("rankType", mfCusClassify.getRankType());
			dataMap.put("rankTypeName", mfCusClassify.getRankTypeName());
		}
		return dataMap;
	}
	@RequestMapping(value = "/checkCusProSizeInfo")
	@ResponseBody
	public Map<String, Object> checkCusProSizeInfo(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		if(StringUtil.isNotEmpty(cusNo)){
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			if(mfCusCustomer != null && "1".equals(mfCusCustomer.getCusBaseType())){
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				if(mfCusCorpBaseInfo != null && StringUtil.isNotEmpty(mfCusCorpBaseInfo.getProjSize())){
					dataMap.put("flag", "success");
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.FIRST_COMPLETE_INFORMAATION.getMessage("企业划型"));
				}
			}else{
				//个人客户信息不控制
				dataMap.put("flag", "success");
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("客户信息"));
		}

		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param cusNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String cusNo) throws Exception {
		ActionContext.initialize(request, response);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		try {
			mfCusCustomerFeign.delete(mfCusCustomer);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * @param cusType
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/input")
	public String input(Model model,String cusType,String from) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		List<ParmDic> parmList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		for (Iterator iterator = parmList.iterator(); iterator.hasNext();) {
			ParmDic parmDic = (ParmDic) iterator.next();
			// 保留个人和企业,remark 中存放的是客户基础类型（base_type）
			if ( ! (BizPubParm.CUS_BASE_TYPE_CORP.equals(parmDic.getRemark()) || BizPubParm.CUS_BASE_TYPE_PERSON.equals(parmDic.getRemark()))) {
				iterator.remove();
			}
		}
		json = new JSONObject();
		if (cusType == null) {
			if(parmList.size()>0){
				cusType = parmList.get(1).getOptCode();//
			}else{
				cusType = "202";
			}
		}
		MfKindForm mfKindForm = new MfKindForm();
		String formId;
		if (cusType.startsWith("2")) {//进件表单也从mf_kind_form表中取   个人客户node_no=cusperson   企业客户node_no=cuscorp
			// 个人客户新增表单
			mfKindForm.setNodeNo("cusPersonAdd");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		} else {
			// 企业客户新增表单
			mfKindForm.setNodeNo("cusCorpAdd");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		}
		FormData formcus00002 = formService.getFormData(formId);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		if (cusType.startsWith("2")) {

		}else{
			mfCusCustomer.setIdType(BizPubParm.ID_TYPE_CREDIT);
		}
		mfCusCustomer.setCusType(cusType);
		mfCusCustomer.setCusMngNo(User.getRegNo(request));
		mfCusCustomer.setCusMngName(User.getRegName(request));
		String brNo = User.getOrgNo(request);
		if(StringUtil.isNotEmpty(brNo)){
			SysOrg sysOrg = sysOrgFeign.getByBrNo(brNo);
			if(sysOrg!=null&&"12".equals(sysOrg.getBizType())){
				mfCusCustomer.setCusSubType("B");
			}
		}

		getObjValue(formcus00002, mfCusCustomer);

		//明细类别
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(cusType);
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		/*String[] subTypes = mfCusType.getSubType().split("\\|");
		JSONArray subTypeArray = new JSONArray();
		JSONObject tmpObject=null;
		Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
		for (int i = 0; i < subTypes.length; i++) {
			 tmpObject = new JSONObject();
			 tmpObject.put("id", subTypes[i]);
			 if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
				 continue;
			 }
			 tmpObject.put("name", dicMap.get(subTypes[i]));
			 subTypeArray.add(tmpObject);
		}
		json.put("cusSubType", subTypeArray);*/
		//信息来源
		List<ParmDic> infoOfferDicList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("INFO_OFFER");
		JSONArray infoOfferArray = new JSONArray();
		for(ParmDic infoOfferDic:infoOfferDicList){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("id", infoOfferDic.getOptCode());
			jsonObj.put("name", infoOfferDic.getOptName());
			infoOfferArray.add(jsonObj);
		}
		json.put("infoOffer", infoOfferArray);
		String ajaxData = json.toString();
		String orgNo = User.getOrgNo(this.getHttpRequest());
		//boolean isEngineeringDev = getIsEngineeringDep(orgNo);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("from", from);
		model.addAttribute("formcus00002", formcus00002);
		model.addAttribute("query", "");
		//model.addAttribute("isEngineeringDev", isEngineeringDev);
		model.addAttribute("netCheck", ymlConfig.getCloud().get("netCheck"));//读取联网核查收费标准
		model.addAttribute("idCheck",  ymlConfig.getCloud().get("idCheck"));//读取身份核查收费标准
		model.addAttribute("projectName",ymlConfig.getSysParams().get("sys.project.name"));
		return "/component/cus/MfCusCustomer_Insert";
	}

	/**
	 * 获取当前用户部门是否所属工程担保部门
	 * @param brNo
	 * @return
	 * @throws Exception
	 */
	private boolean getIsEngineeringDep(String brNo) throws Exception{
		SysOrg sysOrg = sysOrgFeign.getByBrNo(brNo);
		//判断当前部门是否是工程担保部门
		if("100019".equals(sysOrg.getBrNo()) ||"100019".equals(sysOrg.getUpOne())){
			return true;
		//如果上级机构已经是顶级返回false
		}else if("0".equals(sysOrg.getUpOne())){
			return false;
		}else{
			//递归上级部门是否是工程担保部门
			getIsEngineeringDep(sysOrg.getUpOne());
		}
		return false;
	}

	/**
	 *
	 * 方法描述： 跳转至新增共同借款人页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param cusType
	 * @date 2018-1-9 下午6:13:44
	 */
	@RequestMapping(value = "/inputCoborr")
	public String inputCoborr(Model model,String cusType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		List<ParmDic> parmList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		json = new JSONObject();
		if (cusType == null) {
			if(parmList.size()>0){
				cusType = parmList.get(0).getOptCode();
			}else{
				cusType = "100";
			}
		}
		MfKindForm mfKindForm = new MfKindForm();
		String formId;
		if (cusType.startsWith("2")) {//进件表单也从mf_kind_form表中取   个人客户node_no=cusperson   企业客户node_no=cuscorp
			// 个人客户新增表单
//			FormData formcus00002 = formService.getFormData("personCus00002");
			mfKindForm.setNodeNo("cusPersonAdd");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		} else {
			// 企业客户新增表单
//			FormData formcus00002 = formService.getFormData("cus00002");
			mfKindForm.setNodeNo("cusCorpAdd");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
		}
		FormData formcus00002 = formService.getFormData(formId);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusType(cusType);
		mfCusCustomer.setCusMngNo(User.getRegNo(request));
		mfCusCustomer.setCusMngName(User.getRegName(request));
		getObjValue(formcus00002, mfCusCustomer);

		//明细类别
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(cusType);
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String[] subTypes = mfCusType.getSubType().split("\\|");
		JSONArray subTypeArray = new JSONArray();
		JSONObject tmpObject=null;
		Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
		for (int i = 0; i < subTypes.length; i++) {
			tmpObject = new JSONObject();
			tmpObject.put("id", subTypes[i]);
			if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
				continue;
			}
			tmpObject.put("name", dicMap.get(subTypes[i]));
			subTypeArray.add(tmpObject);
		}
		json.put("cusSubType", subTypeArray);
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		model.addAttribute("formcus00002", formcus00002);
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		return "/component/cus/MfCusCustomer_InsertCoborr";
	}

	/**
	 *
	 * 方法描述： 跳转编辑表单
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param cusType
	 * @date 2016-12-2 下午1:50:04
	 */
	@RequestMapping(value = "/toUpdate")
	public String toUpdate(Model model,String cusNo, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		String editFlag = "";
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusType = mfCusCustomer.getCusType();
		String cusBaseType = mfCusCustomer.getCusBaseType();
		MfKindForm mfKindForm = new MfKindForm();
		String formId;
		if ("2".equals(cusBaseType)) {
			//FormData formcus00004 = formService.getFormData("personCus00002");
//			FormData formcus00004 = formService.getFormData("personCusDetail00002");
			mfKindForm.setNodeNo("cusPersonEdit");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
			FormData formcus00004 = formService.getFormData(formId);
			getFormValue(formcus00004);
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
			getObjValue(formcus00004, mfCusCustomer);
			getObjValue(formcus00004, mfCusPersBaseInfo);
			model.addAttribute("formcus00004", formcus00004);

		} else {
//			FormData formcus00004 = formService.getFormData("cus00004");说明：带*号的为必填项信息，请填写完整。
			mfKindForm.setNodeNo("cusCorpEdit");
			formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
			FormData formcus00004 = formService.getFormData(formId);
			getFormValue(formcus00004);
			MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
			getObjValue(formcus00004, mfCusCustomer);
			getObjValue(formcus00004, mfCusCorpBaseInfo);
			model.addAttribute("formcus00004", formcus00004);
		}
		//获取客户类别
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(cusBaseType);
		mfCusType.setUseFlag("1");
		List<MfCusType> cusTypeList = mfCusTypeFeign.getAllList(mfCusType);
		if (cusTypeList!=null&&cusTypeList.size()>0) {
			JSONArray cusTypeArray = JSONArray.fromObject(cusTypeList);
			for (int i = 0; i < cusTypeArray.size(); i++) {
				cusTypeArray.getJSONObject(i).put("id",
						cusTypeArray.getJSONObject(i).getString("typeNo"));
				cusTypeArray.getJSONObject(i).put("name",
						cusTypeArray.getJSONObject(i).getString("typeName"));
			}
			JSONObject json = new JSONObject();

			//明细类别
			mfCusType = new MfCusType();
			mfCusType.setTypeNo(cusType);
			mfCusType = mfCusTypeFeign.getById(mfCusType);
			String[] subTypes = mfCusType.getSubType().split("\\|");
			JSONArray subTypeArray = new JSONArray();
			JSONObject tmpObject=null;
			Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
			for (int i = 0; i < subTypes.length; i++) {
				tmpObject = new JSONObject();
				tmpObject.put("id", subTypes[i]);
				if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
					continue;
				}
				tmpObject.put("name", dicMap.get(subTypes[i]));
				subTypeArray.add(tmpObject);
			}

			//判断客户名下是否存在申请
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setCusNo(cusNo);
			int appNum = mfBusApplyFeign.getBusSubmitCntByCusNo(mfBusApply);
			if (appNum!=0) {
				Map<String,String>  cusTypeMap = new CodeUtils().getMapByKeyName("CUS_TYPE");
				json.put("cusTypeName", cusTypeMap.get(cusType));
				json.put("cusSubTypeName", dicMap.get(mfCusCustomer.getCusSubType()));
				cusTypeArray =new JSONArray();
				tmpObject = new JSONObject();
				tmpObject.put("id", cusType);
				tmpObject.put("name", cusTypeMap.get(cusType));
				cusTypeArray.add(tmpObject);
				subTypeArray =new JSONArray();
				tmpObject = new JSONObject();
				tmpObject.put("id", mfCusCustomer.getCusSubType());
				tmpObject.put("name", dicMap.get(mfCusCustomer.getCusSubType()));
				subTypeArray.add(tmpObject);
			}
			json.put("cusType", cusTypeArray);
			json.put("cusSubType", subTypeArray);
			json.put("appNum", appNum);
			String ajaxData = json.toString();
			model.addAttribute("ajaxData", ajaxData);
		}
		if(!User.getRegNo(request).equals(mfCusCustomer.getCusMngNo())){
			editFlag = "query";
		}
		model.addAttribute("netCheck", ymlConfig.getCloud().get("netCheck"));//读取联网核查收费标准
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		model.addAttribute("editFlag", editFlag);
		model.addAttribute("projectName", ymlConfig.getSysParams().get("sys.project.name"));
		return "/component/cus/MfCusCustomer_Update";
	}
	/**
	 *
	 * 方法描述：根据客户类别获取客户明细类别
	 * @return
	 * @throws Exception
	 * String
	 * @author YaoWenHao
	 * @param cusNo
	 * @param cusType
	 * @date 2017-12-7 下午5:31:48
	 */
	@RequestMapping(value = "/getCusSubTypeByCusTypeAjax")
	@ResponseBody
	public Map<String, Object> getCusSubTypeByCusTypeAjax(String cusNo, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		//明细类别
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(cusType);
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		String[] subTypes = mfCusType.getSubType().split("\\|");
		JSONArray subTypeArray = new JSONArray();
		JSONObject tmpObject=null;
		Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
		boolean flag = true;
		for (int i = 0; i < subTypes.length; i++) {
			if (mfCusCustomer.getCusSubType().equals(subTypes[i])) {
				flag = false;
			}
		}
		for (int i = 0; i < subTypes.length; i++) {
			tmpObject = new JSONObject();
			tmpObject.put("id", subTypes[i]);
			if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
				continue;
			}
			tmpObject.put("name", dicMap.get(subTypes[i]));
			if (mfCusCustomer.getCusSubType().equals(subTypes[i])) {
				tmpObject.put("selected", true);
			}
			subTypeArray.add(tmpObject);
		}

		try {
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			dataMap.put("cusSubType", subTypeArray);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 跳转到上传头像页面
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @date 2016-9-19 上午11:17:22
	 */
	@RequestMapping(value = "/uploadHeadImg")
	public String uploadHeadImg(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String headImg = mfCusCustomer.getHeadImg();
		String ifUploadHead = mfCusCustomer.getIfUploadHead();
		FormData formcusuploadhead0001 = formService.getFormData("cusuploadhead0001");
		model.addAttribute("ifUploadHead", ifUploadHead);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", "");
		model.addAttribute("headImg", headImg);
		model.addAttribute("formcusuploadhead0001", formcusuploadhead0001);
		return "component/cus/MfCusCustomer_uploadHeadImg";
	}

	/**
	 *
	 * 方法描述： 跳转到黑名单/优质客户详情页面
	 *
	 * @return
	 * @throws Exception String
	 * @author GuoJian
	 * @param classifyInfoId
	 * @param classifyInfoId
	 * @date 2017-8-7 15:29:51
	 */
	@RequestMapping(value = "/getCusAndClassifyById")
	public String getCusAndClassifyById(Model model, String classifyInfoId) throws Exception {
		ActionContext.initialize(request, response);
		CusAndClassify cusAndClassify = new CusAndClassify();
		cusAndClassify.setClassifyInfoId(classifyInfoId);
		cusAndClassify = mfCusCustomerFeign.getCusAndClassifyById(cusAndClassify);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusAndClassify", cusAndClassify);

		// 获取当前客户的历史分类信息
		List<CusAndClassify> cusAndClassifylist = mfCusCustomerFeign.getCusAndClassifyByCusId(cusAndClassify);
		dataMap.put("cusAndClassifylist", cusAndClassifylist);
		model.addAttribute("dataMap", dataMap);
		return "/component/cus/MfCusAndClassify_Detail";
	}

	/**
	 *
	 * 方法描述： 保存客户头像名到客户登记表中
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param headImg
	 * @date 2016-9-19 下午4:12:23
	 */
	@RequestMapping(value = "/submitUploadHeadImg")
	@ResponseBody
	public Map<String, Object> submitUploadHeadImg(String cusNo, String headImg) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer.setHeadImg(headImg);
		mfCusCustomer.setIfUploadHead("1");
		try {
			mfCusCustomerFeign.update(mfCusCustomer);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}


	/**
	 *
	 * 方法描述： 客户动态表单在业务视角登记展示
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param cusNo
	 * @param relNo
	 * @param ipage
	 * @date 2017-9-22 上午11:44:12
	 */
	@RequestMapping(value = "/getApplyCusInfoAjax")
	@ResponseBody
	public Map<String, Object> getApplyCusInfoAjax(String cusNo, String relNo, Ipage ipage) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		request.setAttribute("ifBizManger", "3");
		try {
			String query = mfCusCustomerFeign.validateCusFormModify(cusNo, relNo, BizPubParm.FORM_EDIT_FLAG_CUS,User.getRegNo(request));
			//要件的权限
			String queryFile = mfCusCustomerFeign.validateCusFormModify(cusNo, relNo, BizPubParm.FORM_EDIT_FLAG_FILE,User.getRegNo(request));
			List<MfCusTable> cusTableList = getDyFormHtml(cusNo,relNo, query, ipage);
			dataMap.put("cusTableList", cusTableList);
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
	 * 方法描述： 获取动态表单html
	 * @param cusNo
	 * @param relNo
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author zhs
	 * @param query
	 * @date 2017-9-22 上午11:24:50
	 */
	@SuppressWarnings("unchecked")
	public List<MfCusTable> getDyFormHtml(String cusNo,String relNo, String query,Ipage ipage) throws Exception{
		if(StringUtil.isEmpty(relNo)){
			relNo = cusNo;
		}
		FormService formService = new FormService();
		MfCusNetworkConfig mfCusNetworkConfig = null;
		List<MfCusTable> cusTableList = new ArrayList<MfCusTable>();
		// 查询已经录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(relNo);
		cusTableList = mfCusTableFeign.getList(mfCusTable);

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			//中关村默认全部显示出来  并且处于折叠状态
			if ("0".equals(cusTableList.get(i).getDataFullFlag())&&"mf_cus_main_business".equals(cusTableList.get(i).getTableName())) {
                cusTableList.remove(i);
                i--;
				continue;
			}
			String action = cusTableList.get(i).getAction();
			mfCusNetworkConfig = new MfCusNetworkConfig();
			mfCusNetworkConfig.setNodeNo(action);
			mfCusNetworkConfig.setUseFlag(BizPubParm.YES_NO_Y);
			mfCusNetworkConfig = mfCusCustomerFeign.getMfCusNetworkConfig(mfCusNetworkConfig);
			if(mfCusNetworkConfig != null){
				cusTableList.get(i).setIfNetwork(BizPubParm.YES_NO_Y);
			}else{
				cusTableList.get(i).setIfNetwork(BizPubParm.YES_NO_N);
			}
			String htmlStr = "";
			FormData formcommon;
			if ("MfCusCorpBaseInfoAction".equals(action)) {
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);

				// 结束日期显示处理
				String endDate=mfCusCorpBaseInfo.getEndDate();
				if(endDate==null) {
					endDate="";
				}
				endDate = mfCusCorpBaseInfoFeign.dealEndDate(endDate,"2");
				mfCusCorpBaseInfo.setEndDate(endDate);

				Child child = mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
				if(child!=null){
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			} else if ("MfCusPersBaseInfoAction".equals(action)) {
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
//				logger.error("个人客户基本信息展示，信息来源：{},推荐者编号：{}，推荐者名称：{}",mfCusPersBaseInfo.getInfoOffer(),mfCusPersBaseInfo.getRecommenderNo(),mfCusPersBaseInfo.getRecommenderName());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusPersBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if ("MfCusAccountDebtorAction".equals(action)) {
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusAccountDebtor",mfCusAccountDebtor));
				String tableFormId="tableAccountDebtorList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAccountDebtor>) mfCusAccountDebtorFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfCusAccountDetailAction".equals(action)) {
				MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
				mfCusAccountDetail.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusAccountDetail",mfCusAccountDetail));
				String tableFormId="tableAccountDebtorList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAccountDetail>) mfCusAccountDetailFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfCusShareholderAction".equals(action)) {

				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				mfCusShareholder.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusShareholder",mfCusShareholder));
				String tableFormId="tablecusShareholderListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusHighInfoAction".equals(action)) {

				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				mfCusHighInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusHighInfo",mfCusHighInfo));
				String tableFormId="tablecusExecutiveListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusAssetsAction".equals(action)) {

				MfCusAssets mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				mfCusAssets.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusAssets",mfCusAssets));
				String tableFormId="tablecusFixedAssetsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAssets>) mfCusAssetsFeign.findByPage(ipage).getResult(), null, true);

			}  else if ("MfCusEquityInfoAction".equals(action)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(cusNo);
				mfCusEquityInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusEquityInfo",mfCusEquityInfo));
				htmlStr = jtu.getJsonStr("tablecusequ00001", "tableTag", (List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);

			}  else if ("MfCusLegalMemberAction".equals(action)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(cusNo);

				ipage.setParams(this.setIpageParams("mfCusLegalMember",mfCusLegalMember));
				htmlStr = jtu.getJsonStr("tablecuslegm00001", "tableTag", (List<MfCusLegalMember>) mfCusLegalMemberFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusBankAccManageAction".equals(action)) {
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(cusNo);
				mfCusBankAccManage.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusBankAccManage",mfCusBankAccManage));
				String tableFormId="tablecusAccountListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusPersonCorpAction".equals(action)) {//个人名下企业
				MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
				mfCusPersonCorp.setCusNo(cusNo);
				mfCusPersonCorp.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonCorp",mfCusPersonCorp));
				String tableFormId="tablecusPersonCorpBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonCorp>) mfCusPersonCorpFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusPersonLiabilitiesAction".equals(action)) {//个人资产负债表
				MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				mfCusPersonLiabilities.setCusNo(cusNo);
				mfCusPersonLiabilities.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities",mfCusPersonLiabilities));
				String tableFormId="tablecusliabilitiesBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonLiabilities>) mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusWarehouseAction".equals(action)) {
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				mfCusWarehouse.setCusNo(cusNo);
				mfCusWarehouse.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusWarehouse",mfCusWarehouse));
				String tableFormId="tablecusWarehouseListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusBankAcceptanceBillAction".equals(action)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);

				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill",mfCusBankAcceptanceBill));
				htmlStr = jtu.getJsonStr("tablecusbankbill0001", "tableTag", (List<MfCusBankAcceptanceBill>) mfCusBankAcceptanceBillFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusGuaranteeOuterAction".equals(action)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				mfCusGuaranteeOuter.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter",mfCusGuaranteeOuter));
				String tableFormId="tablecusGuaranteeountListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfCusGoodsAction".equals(action)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				mfCusGoods.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusGoods",mfCusGoods));
				String tableFormId="tablecusGoodsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusGoodsFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusListedInfoAction".equals(action)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				mfCusListedInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusListedInfo",mfCusListedInfo));
				String tableFormId="tablecusListListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusSellInfoAction".equals(action)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				mfCusSellInfo.setCusNo(cusNo);
				mfCusSellInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusSellInfo",mfCusSellInfo));
				String tableFormId="tablecusSellListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonJobAction".equals(action)) {// 职业信息
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(cusNo);
				mfCusPersonJob.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonJob",mfCusPersonJob));
				String tableFormId="tablecusJobListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusFamilyInfoAction".equals(action)) {// 个人客户社会关系
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo));
				String tableFormId="tablecusRelationListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusTopContactsInfoAction".equals(action)) {// 常用联系人
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo));
				String tableFormId="tablecusTopContactsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonAssetsInfoAction".equals(action)) {
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				mfCusPersonAssetsInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo",mfCusPersonAssetsInfo));
				String tableFormId="tablecusAssetsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonDebtInfoAction".equals(action)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo",mfCusPersonDebtInfo));
				String tableFormId="tablecusDebtListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonCreditInfoAction".equals(action)) {// 个人客户信用情况
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo.setRelNo(relNo);
				mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
				// formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonCreditInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusPersonIncExpeAction".equals(action)) {// 收支情况
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
				mfCusPersonIncExpe.setRelNo(relNo);
				mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
				// formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusLegalEquityInfoAction".equals(action)) {// 法人对外投资
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);
				mfCusLegalEquityInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo",mfCusLegalEquityInfo));
				String tableFormId="tablecusInvestmentListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusLegalEmployInfoAction".equals(action)) {// 法人对外任职情况
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);
				mfCusLegalEmployInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo",mfCusLegalEmployInfo));
				String tableFormId="tablecusCorpRepServeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusCorpMajorChangeAction".equals(action)) {// 企业重大变更
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);
				mfCusCorpMajorChange.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange",mfCusCorpMajorChange));
				String tableFormId="tablecusMajorChangeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusCorpMajorChange>) mfCusCorpMajorChangeFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusFarmerEconoInfoAction".equals(action)) {// 农户经济情况

				String showType = cusTableList.get(i).getShowType();
				if("2".equals(showType)){
					MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
					mfCusFarmerEconoInfo.setCusNo(cusNo);
					ipage.setParams(this.setIpageParams("mfCusFarmerEconoInfo",mfCusFarmerEconoInfo));
					String tableFormId="tablecusEconoList";
					if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
						tableFormId="table"+cusTableList.get(i).getListModelDef();
					}
					htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusFarmerEconoInfo>)mfCusFarmerEconoInfoFeign.findByPage(ipage).getResult(), null,true);
				}else if("1".equals(showType)){
					MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
					mfCusFarmerEconoInfo.setCusNo(cusNo);
					mfCusFarmerEconoInfo = mfCusFarmerEconoInfoFeign.getById(mfCusFarmerEconoInfo);
					formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
					getFormValue(formcommon);
					getObjValue(formcommon, mfCusFarmerEconoInfo);
					htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
				}else {
				}
			}else if ("MfCusFarmerIncExpeAction".equals(action)) {// 农户收支情况
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				mfCusFarmerIncExpe.setCusNo(cusNo);
				mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpe);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusFarmerIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if ("MfCusReputationInfoAction".equals(action)) {// 农户信誉情况
				MfCusReputationInfo mfCusReputationInfo = new MfCusReputationInfo();
				mfCusReputationInfo.setCusNo(cusNo);
				mfCusReputationInfo = mfCusReputationInfoFeign.getById(mfCusReputationInfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusReputationInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if ("MfCusPersonFlowAssetsInfoAction".equals(action)) {// 流动资产
				MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
				mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
				mfCusPersonFlowAssetsInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo",mfCusPersonFlowAssetsInfo));
				String tableFormId="tablecusflowassetsBaseListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusPersonFlowAssetsInfo>)mfCusPersonFlowAssetsInfoFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusSurveySocialCreditAction".equals(action)){
				MfCusSurveySocialCredit mfCusSurveySocialCredit = new MfCusSurveySocialCredit();
				mfCusSurveySocialCredit.setCusNo(cusNo);
				mfCusSurveySocialCredit = mfCusSurveySocialCreditFeign.getById(mfCusSurveySocialCredit);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusSurveySocialCredit);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if("MfCusCreditInvestigateInfoAction".equals(action)){//征信信息
				MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo = new MfCusCreditInvestigateInfo();
				mfCusCreditInvestigateInfo.setCusNo(cusNo);
				mfCusCreditInvestigateInfo = mfCusCreditInvestigateInfoFeign.getById(mfCusCreditInvestigateInfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCreditInvestigateInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusAssureCompanyAction".equals(action)) {// 担保公司
				MfCusAssureCompany mfCusAssureCompany = new MfCusAssureCompany();
				mfCusAssureCompany.setAssureCompanyId(cusNo);
				mfCusAssureCompany = mfCusAssureCompanyFeign.getById(mfCusAssureCompany);

				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusAssureCompany);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if ("MfHangZhouCusPersonDebtInfoAction".equals(action)) {//杭州微溪个人负债
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo",mfCusPersonDebtInfo));
				htmlStr = jtu.getJsonStr("tableHangZhouCuspersdebt0001", "tableTag", (List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
			}else if("MfCusAssureOutsideAction".equals(action)){//外访-保证人信息
				MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
				mfCusAssureOutside.setCusNo(cusNo);
				mfCusAssureOutside.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusAssureOutside",mfCusAssureOutside));
				htmlStr = jtu.getJsonStr("tablecusAssOutsideList","tableTag", (List<MfCusAssureOutside>)mfCusAssureOutsideFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusPersonIncomeSurveyAction".equals(action)){//收入流水调查
				MfCusPersonIncomeSurvey mfCusPersonIncomeSurvey=new MfCusPersonIncomeSurvey();
				mfCusPersonIncomeSurvey.setCusNo(cusNo);
				mfCusPersonIncomeSurvey.setRelNo(relNo);
				mfCusPersonIncomeSurvey = mfCusPersonIncomeSurveyFeign.getById(mfCusPersonIncomeSurvey);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonIncomeSurvey);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if("MfCusApplyPersonSurveyAction".equals(action)){//申请人调查
				MfCusApplyPersonSurvey mfCusApplyPersonSurvey=new MfCusApplyPersonSurvey();
				mfCusApplyPersonSurvey.setCusNo(cusNo);
				mfCusApplyPersonSurvey.setRelNo(relNo);
				mfCusApplyPersonSurvey = mfCusApplyPersonSurveyFeign.getById(mfCusApplyPersonSurvey);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusApplyPersonSurvey);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if("MfCusApplySpouseSurveyAction".equals(action)){//申请人配偶调查
				MfCusApplySpouseSurvey mfCusApplySpouseSurvey=new MfCusApplySpouseSurvey();
				mfCusApplySpouseSurvey.setCusNo(cusNo);
				mfCusApplySpouseSurvey.setRelNo(relNo);
				mfCusApplySpouseSurvey = mfCusApplySpouseSurveyFeign.getById(mfCusApplySpouseSurvey);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusApplySpouseSurvey);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfTrenchUserAction".equals(action)) {// 渠道操作员
				WebCusLineReg webCusLineReg = new WebCusLineReg();
				webCusLineReg.setChannelSourceNo(cusNo);
				htmlStr = jtu.getJsonStr("tableTrenchUserList", "tableTag", mfTrenchUserFeign.getCusLineRegList(webCusLineReg), null, true);
			}else if("MfCusBranchOrganizationAction".equals(action)){//分支机构
				MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
				mfCusBranchOrganization.setCusNo(cusNo);
				mfCusBranchOrganization.setRelNo(relNo);
				mfCusBranchOrganization.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusBranchOrganization",mfCusBranchOrganization));
				htmlStr = jtu.getJsonStr("tablemfcusbranchorganization0001","tableTag", (List<MfCusBranchOrganization>)mfCusBranchOrganizationFeign.findByPage(ipage).getResult(), null,true);
			}else if ("MfCusGuaLoanOuterSumAction".equals(action)) {

				MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
				mfCusGuaLoanOuterSum.setCusNo(cusNo);
				mfCusGuaLoanOuterSum = mfCusGuaLoanOuterSumFeign.getById(mfCusGuaLoanOuterSum);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusGuaLoanOuterSum);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if ("MfCusExecNoticeAction".equals(action)) {//执行公告

				MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
				mfCusExecNotice.setCusNo(cusNo);
				mfCusExecNotice.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusExecNotice",mfCusExecNotice));
				String tableFormId="tablecusExecNoticeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusExecNotice>)mfCusExecNoticeFeign.findByPage(ipage).getResult(), null,true);
			}else if ("MfCusJudgmentAction".equals(action)) {//裁判文书
				MfCusJudgment mfCusJudgment = new MfCusJudgment();
				mfCusJudgment.setCusNo(cusNo);
				mfCusJudgment.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusJudgment",mfCusJudgment));
				String tableFormId="tablecusJudgmentListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusJudgmentFeign.getAllList(mfCusJudgment), null,true);

			}else if ("MfCusCourtInfoAction".equals(action)) {//法院信息

				MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCusNo(cusNo);
//				mfCusCourtInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusCourtInfo",mfCusCourtInfo));
				String tableFormId="tablecusCourtInfoListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusCourtInfo>)mfCusCourtInfoFeign.findByPage(ipage).getResult(), null,true);

			}else if ("MfCusDishonestInfoAction".equals(action)) {//失信公告

				MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCusNo(cusNo);
//				mfCusDishonestInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo",mfCusDishonestInfo));
				String tableFormId="tablecusDishonestInfoListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				List<MfCusDishonestInfo> list = mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo);
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo), null,true);

			}else if ("MfCusLogisticsInformationAction".equals(action)) {//物流信息
				MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
				mfCusLogisticsInformation.setCusNo(cusNo);
				mfCusLogisticsInformation.setRelNo(relNo);
				mfCusLogisticsInformation.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusLogisticsInformation",mfCusLogisticsInformation));
				String tableFormId="tablecusLogisticsInformationList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusLogisticsInformation>)mfCusLogisticsInformationFeign.findByPage(ipage).getResult(), null,true);
			}else if ("MfCusChattelMortgageAction".equals(action)) {//动产抵押
				MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
				mfCusChattelMortgage.setCusNo(cusNo);
				mfCusChattelMortgage.setRelNo(relNo);
				mfCusChattelMortgage.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusChattelMortgage",mfCusChattelMortgage));
				String tableFormId="tableCusChattelMortgageList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusChattelMortgage>)mfCusChattelMortgageFeign.findByPage(ipage).getResult(), null,true);

			}else if ("MfCusStaffAction".equals(action)) {//员工信息
				MfCusStaff mfCusStaff = new MfCusStaff();
				mfCusStaff.setCusNo(cusNo);
				mfCusStaff.setRelNo(relNo);
				mfCusStaff.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusStaff",mfCusStaff));
				String tableFormId="tablecusStaffList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusStaff>)mfCusStaffFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusBorrowerInfoAction".equals(action)){//共同借款人信息
				MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusBorrowerInfo",mfCusBorrowerInfo));
				String tableFormId="tablecusBorrowerList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusBorrowerInfo>)mfCusBorrowerInfoFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusCorpLoanAction".equals(action)){//对外融资
				MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
				mfCusCorpLoan.setCusNo(cusNo);
				//mfCusCorpLoan.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusCorpLoan",mfCusCorpLoan));
				String tableFormId="tablecusCorpLoanListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				formcommon = formService.getFormData(tableFormId);
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusCorpLoan>)mfCusCorpLoanFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusCultureAction".equals(action)){//养殖概况
				MfCusCulture mfCusCulture = new MfCusCulture();
				mfCusCulture.setCusNo(cusNo);
				mfCusCulture.setRelNo(relNo);
				mfCusCulture = mfCusCultureFeign.getById(mfCusCulture);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());

				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCulture);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if("MfCusShedAction".equals(action)){//棚舍信息
				MfCusShed mfCusShed = new MfCusShed();
				mfCusShed.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusShed",mfCusShed));
				String tableFormId="tablecusShedList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusShed>)mfCusShedFeign.findByPage(ipage).getResult(), null,true);


			}else if("MfCusSaleProductAction".equals(action)){//销售产品
				MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
				mfCusSaleProduct.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusSaleProduct",mfCusSaleProduct));
				String tableFormId="tablecussaleproductBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusSaleProduct>)mfCusSaleProductFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusPlantBreedAction".equals(action)){//种植养殖信息
				MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
				mfCusPlantBreed.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusPlantBreed",mfCusPlantBreed));
				String tableFormId="tablecusplantbreedBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusPlantBreed>)mfCusPlantBreedFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusBusServiceAction".equals(action)){//商业服务业信息
				MfCusBusService mfCusBusService = new MfCusBusService();
				mfCusBusService.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusBusService",mfCusBusService));
				String tableFormId="tablecuscusbusserviceBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusBusService>)mfCusBusServiceFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusProfitLossAction".equals(action)) {//个人损益表
				MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
				mfCusProfitLoss.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
				String tableFormId = "tablecusprofitlossBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusProfitLoss>) mfCusProfitLossFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusAssetsDebtsAction".equals(action)) {//资产负债信息
				MfCusAssetsDebts mfCusAssetsDebts = new MfCusAssetsDebts();
				mfCusAssetsDebts.setCusNo(cusNo);
				mfCusAssetsDebts = mfCusAssetsDebtsFeign.getById(mfCusAssetsDebts);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusAssetsDebts);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if("MfCusLegalinfoAction".equals(action)){//法人基本信息
				MfCusLegalinfo mfCusLegalinfo = new MfCusLegalinfo();
				mfCusLegalinfo.setCusNo(cusNo);
				mfCusLegalinfo = mfCusLegalinfoFeign.getById(mfCusLegalinfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusLegalinfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusDesignatedRecipientAction".equals(action)) {// 指定收件人
				MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
				recipient.setRelNo(cusNo);
				recipient.setIsDelete("0");

				ipage.setParams(this.setIpageParams("mfCusDesignatedRecipient", recipient));
				String tableFormId = "tablerecipientListBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusDesignatedRecipient>) mfCusDesignatedRecipientFeign.findByPage(ipage).getResult(), null, true);
			}else if("MfCusSharePledgeAction".equals(action)){//股权质押信息
				MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
				mfCusSharePledge.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
				String tableFormId="tablecusSharePledgeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusSharePledge>)mfCusSharePledgeFeign.findByPage(ipage).getResult(), null,true);

			}else if (action.equals("MfCusBusinessInfoAction")) {//工商信息
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if (action.equals("MfCusLegalRepresentativeInfoAction")) {//法人信息
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if (action.equals("MfCusDotInfoAction")) {//网点信息
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if (action.equals("MfCusPersonalProfileInfoAction")) {//个人概况
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if (action.equals("MfCusMeManageAction")) {//成员单位
				MfCusMeManage mfCusMeManage = new MfCusMeManage();
				mfCusMeManage.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusMeManage", mfCusMeManage));
				String tableFormId="tablemfCusMeManage";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusMeManage>)mfCusMeManageFeign.findByPage(ipage).getResult(), null,true);

			}else if (action.equals("MfCusInvoiceMationAction")) {//开票信息
				MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
				mfCusInvoiceMation.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
				String tableFormId="tablemfCusInvoiceMation";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusInvoiceMation>)mfCusInvoiceMationFeign.findByPage(ipage).getResult(), null,true);
			}else if (action.equals("MfCusMainBusinessAction")) {//主营业务
				MfCusMainBusiness mfCusMainBusiness = new MfCusMainBusiness();
				mfCusMainBusiness.setCusNo(cusNo);
				mfCusMainBusiness.setMainType(BizPubParm.YES_NO_Y);
				mfCusMainBusiness = mfCusMainBusinessFeign.getByMainType(mfCusMainBusiness);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusMainBusiness);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
				System.out.println("主营业务的信息块");
			}else if (action.equals("MfCusIntangibleAssetsAction")) {//无形资产
				String tableFormId="tablecusIntangibleAssetsBaseList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag",mfCusIntangibleAssetsFeign.getByCusNo(cusNo), null,true);
			}else if (action.equals("MfCusContractAction")) {//合同订单
				String tableFormId="tablecusContractBaseList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag",mfCusContractFeign.getByCusNo(cusNo), null,true);
			}
			cusTableList.get(i).setHtmlStr(htmlStr);
		}
		return cusTableList;
	}


	/**
	 *
	 * 方法描述： 获得客户详情
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param appId
	 * @param cusNo
	 * @param busEntrance
	 * @param relNo
	 * @param ipage
	 * @param formId
	 * @param formcusper00003
	 * @param evalAppNo
	 * @param evalCredit
	 * @date 2016-5-31 上午9:18:07
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/getById")
	public String getById(String appId,String fincId,String pactId, String cusNo, String busEntrance, String relNo, Ipage ipage, String formId, FormData formcusper00003, String evalAppNo, String evalCredit,Model model,String scNo,String creditAppId,String fincMainId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String opType = request.getParameter("opType"); // 表示是授信业务请求
		String firstKindNo="";
		/*
		 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑； 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑； 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
		 */
		request.setAttribute("ifBizManger", "3");
		//判断客户表单信息是否允许编辑
		MfBusApply mba=new MfBusApply();
		mba.setAppId(appId);
		mba = mfBusApplyFeign.getById(mba);
		String query = "";
		query = mfCusCustomerFeign.validateCusFormModify(cusNo,appId,BizPubParm.FORM_EDIT_FLAG_CUS,User.getRegNo(request));
		if(query == null){
			query = "";
		}
		//要件的权限
		String queryFile = mfCusCustomerFeign.validateCusFormModify(cusNo,appId,BizPubParm.FORM_EDIT_FLAG_FILE,User.getRegNo(request));
		if(queryFile == null){
			queryFile = "";
		}

		FormData formcusuploadhead0001 = formService.getFormData("cusuploadhead0001");
		model.addAttribute("formcusuploadhead0001", formcusuploadhead0001);
		List<MfCusTable> cusTableList = getDyFormHtml(cusNo,relNo, query, ipage);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusTableList", cusTableList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;

		String	cusFullFlag = "1";// 客户所有表单信息已经全部录入
		model.addAttribute("cusFullFlag", cusFullFlag);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		String cusBaseFlag;
		String gradeModel;
		String cusAccountStatus = null;
		MfCusFormConfig mfCusFormConfig;
		String effectFlag = "0";
		// 客户基本信息
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人客户 获取个人客户基本信息
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
			cusBaseFlag = "0";
			if (mfCusPersBaseInfo != null) {
				gradeModel = mfCusPersBaseInfo.getGradeModel();
				if(StringUtil.isNotEmpty(gradeModel)){
					Object obj = evalInterfaceFeign.getEvalScenceConfigById(gradeModel);
					if (obj != null) {
						JSONObject jobj = JSONObject.fromObject(obj);
					}
				}
				cusBaseFlag = "1";
				mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersBaseInfoAction");
				if (mfCusFormConfig == null) {
				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				formcusper00003 = formService.getFormData(formId);
				if (formcusper00003.getFormId() == null) {
					//logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusPersBaseInfoAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formcusper00003);
				getObjValue(formcusper00003, mfCusPersBaseInfo);
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
				mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
			}

		} else {// 企业客户 获取企业客户基本信息
			MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfoTmp = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
			cusBaseFlag = "0";
			if (mfCusCorpBaseInfoTmp != null) {
				cusAccountStatus = mfCusCorpBaseInfoTmp.getAccountSts();
				gradeModel = mfCusCorpBaseInfoTmp.getGradeModel();
				Object obj=null;
				if(gradeModel!=null) {
					obj = evalInterfaceFeign.getEvalScenceConfigById(gradeModel);
				}
				if (obj != null) {
					JSONObject jobj = JSONObject.fromObject(obj);
				}
				cusBaseFlag = "1";
				mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
				if (mfCusFormConfig == null) {
				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				FormData formcuscorp00004 = formService.getFormData(formId);
				if (formcuscorp00004.getFormId() == null) {
//					logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCorpBaseInfoAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formcuscorp00004);
				getObjValue(formcuscorp00004, mfCusCorpBaseInfoTmp);
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfoTmp.getPostalCode());
				String accountSts = mfCusCorpBaseInfoTmp.getAccountSts();
				if(accountSts == null || "".equals(accountSts)){
					effectFlag = "0";
				}else if("02".equals(accountSts)){//销户
					effectFlag = "2";
				}else{
					effectFlag = "1";
				}
			}
		}
		model.addAttribute("effectFlag", effectFlag);

		model.addAttribute("cusBaseFlag", cusBaseFlag);
		MfBusApply mfBusApply = null;
		if (StringUtil.isNotEmpty(appId)) {
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			if (mfBusApply!=null) {
				//该笔业务只保存并未提交流程
				if(BizPubParm.APP_STS_UN_COMPLETE.equals(mfBusApply.getAppSts())){
					dataMap.put("completeFlag", "0");
					firstKindNo = mfBusApply.getKindNo();
				}
			}
		}
		// 申请信息
		String pleFlag = "0";
		String busFlag = "0";
		if (!"CREDIT_APPLY".equals(opType)) {
			if (mfBusApply == null) {
				mfBusApply=appInterfaceFeign.getRecentAppByCusNo(cusNo);
			}
			if (mfBusApply != null) {
				appId=mfBusApply.getAppId();
				String busModel = mfBusApply.getBusModel();
				busFlag = "1";
				// 仓储方信息
				busModel = mfBusApply.getBusModel();
				String storageFlag = "0";
				if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2) || busModel.equals(BizPubParm.BUS_MODEL_4)) {// 1-动产质押 2-仓单 4-保兑仓
					MfCusCustomer cusWarehouseInfo = new MfCusCustomer();
					cusWarehouseInfo.setCusNo(mfBusApply.getCusNoWarehouse());
					cusWarehouseInfo = mfCusCustomerFeign.getById(cusWarehouseInfo);
					if (cusWarehouseInfo != null) {
						storageFlag = "1";
						request.setAttribute("cusWarehouseInfo", cusWarehouseInfo);
					}
				}
			}
		}

		// 转化客户类型信息
		CodeUtils cu = new CodeUtils();
		Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
		String cusTypeName = cusTypeMap.get(mfCusCustomer.getCusType());
		model.addAttribute("cusTypeName", cusTypeName);
		String cusType = mfCusCustomer.getCusType();
		model.addAttribute("cusType", cusType);
		String cusBaseType = mfCusCustomer.getCusBaseType();
		model.addAttribute("cusBaseType", cusBaseType);
		MfCusClassify mfCusClassify = new MfCusClassify();
		mfCusClassify.setCusNo(cusNo);
		List<MfCusClassify> mfCusClassifyList = mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
        if(mfCusClassifyList.size() >0){
            mfCusClassify = mfCusClassifyList.get(0);
        }else{
            Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CLASSIFY_TYPE");
            mfCusClassify.setClassifyType(mfCusCustomer.getClassifyType());
            mfCusClassify.setRankType(mfCusCustomer.getClassifyType());
            mfCusClassify.setRankTypeName(dicMap.get(mfCusCustomer.getClassifyType()));
        }

		// 获取财务报表信息 LJW
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		List<CusFinMain> cusFinMainList = pfsInterfaceFeign.getAll(cusFinMain);
		if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
			for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
				cFinMain.setFinCapFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "1"));
				cFinMain.setFinProFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "2"));
				cFinMain.setFinCashFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "3"));
 				cFinMain.setFinSubjectFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "5"));
			}
		}
		dataMap.put("cusFinMainList", cusFinMainList);
		model.addAttribute("cusFinMainList", cusFinMainList);

		// 获取产品号 默认取线下
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindProperty("2");
		mfSysKind.setUseFlag("1");
		mfSysKind.setCusType(mfCusCustomer.getCusType());
		mfSysKind.setBrNo(User.getOrgNo(request));
		mfSysKind.setRoleNoArray(User.getRoleNo(request));
		List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
		if (mfSysKindList != null && mfSysKindList.size() > 0) {
			firstKindNo = mfSysKindList.get(0).getKindNo();
		}

		// 2、风险级别信息
		List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(cusNo);
		String riskLevel = "-1";
		for (int i = 0; i < riskBizItemRelList.size(); i++) {
			String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
			// 风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
			if ("99".equals(riskLevelThis)) {
				riskLevel = riskLevelThis;
				break;
			} else {
				if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
					riskLevel = riskLevelThis;
				}
			}
		}
		String riskName = "风险检查通过";
		if (!"-1".equals(riskLevel) && !"0".equals(riskLevel)) {
			Map<String, String> dicMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
			riskName = dicMap.get(riskLevel);
		}
		dataMap.put("riskLevel", riskLevel);
		dataMap.put("riskName", riskName);

		// 获取该客户是否有已经提交的业务，有的话，客户详情页面中未完善的表单块要隐藏掉
		int busSubmitCnt = appInterfaceFeign.getBusSubmitCnt(cusNo);
		dataMap.put("busSubmitCnt", busSubmitCnt);
		boolean relation = mfCusRelationFeign.getRelationByCusNo(cusNo);
		dataMap.put("relation", relation);
		//客户评级评分卡信息
		if(evalAppNo!=null&&("evalCredit".equals(evalCredit)||"evalApp".equals(evalCredit))){
			List<MfEvalGradeCard> evalGradeCardList = evalInterfaceFeign.getEvalGradeCardByNo(cusNo, evalAppNo);
			model.addAttribute("evalGradeCardList", evalGradeCardList);
		}
		model.addAttribute("evalAppNo", evalAppNo);

		//6、多笔业务处理
		MfBusApply  mfBusApplyTmp = new MfBusApply();
		mfBusApplyTmp.setCusNo(cusNo);
		List<MfBusApply> mfBusApplyList=appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
		dataMap.put("moreApplyCount", mfBusApplyList.size());
		MfBusPact  mfBusPactTmp = new MfBusPact();
		mfBusPactTmp.setCusNo(cusNo);
		List<MfBusPact> mfBusPacts = pactInterfaceFeign.getMultiBusList(mfBusPactTmp);
		dataMap.put("morePactCount", mfBusPacts.size());
		MfBusFincApp  mfBusFincAppTmp = new MfBusFincApp();
		mfBusFincAppTmp.setCusNo(cusNo);
		mfBusFincAppTmp.setFincSts("('5','6')");
		List<MfBusFincApp> mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);
		dataMap.put("moreFincCount", mfBusFincApps.size());
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setAssureNo(cusNo);
		List<MfBusApplyAssureInfo> mfBusApplyAssureInfos = assureInterfaceFeign.getMultiBusList(mfAssureInfo);
		dataMap.put("moreAssureCount", mfBusApplyAssureInfos.size());

		//处理已完结的借据
		List<MfBusFincApp> mfBusFincAppFinishs = pactInterfaceFeign.getMultiBusListFinish(mfBusFincAppTmp);
		dataMap.put("moreFincFinishCount", mfBusFincAppFinishs.size());

		//处理已还历史
		MfRepayHistory mfRepayHistory = new MfRepayHistory();
		mfRepayHistory.setCusNo(cusNo);
		List<MfRepayHistory> mfRepayHistoryList = pactInterfaceFeign.getRepayHistoryListByCusNo(mfRepayHistory);
		dataMap.put("moreRepayCount", mfRepayHistoryList.size());

		//获取视图
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("cusNo", cusNo);
		paramMap.put("appId", appId);
		paramMap.put("fincMainId", fincMainId);
		String generalClass ="cus";//视角所属大类为客户
		//String busClass = mfCusCustomer.getCusBaseType();//业务分类,默认为个人客户

		//如果没有传场景号默认走基础场景
		if(StringUtil.isEmpty(busEntrance)){
			busEntrance="apply";
		}
		paramMap.put("busEntrance", busEntrance);
		paramMap.put("creditAppId", creditAppId);
		Map<String, Object> busViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, paramMap);
		dataMap.putAll(busViewMap);
		if(StringUtil.isEmpty(fincId)) {
			fincId = "";
		}
		if(mfBusApply!=null){
			model.addAttribute("mfBusApply", mfBusApply);
		}
		//要件的展示方式：0块状1列表
		List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
		for (Object o :parmDics){
			ParmDic p = (ParmDic) o;
			String docShowType = p.getOptCode();
			model.addAttribute("docShowType", docShowType);
		}
		//model.addAttribute("cusCreditAddPro", cu.getSingleValByKey("CUS_CREDIT_ADD_PRO"));
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusBaseType", mfCusCustomer.getCusBaseType());
		model.addAttribute("mfCusClassify", mfCusClassify);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("fincMainId", fincMainId);
		model.addAttribute("fincId", fincId);
		model.addAttribute("scNo", scNo);
		model.addAttribute("relNo", relNo);
		model.addAttribute("firstKindNo", firstKindNo);
		model.addAttribute("evalCredit", evalCredit);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("query", query);
		model.addAttribute("queryFile", queryFile);
		model.addAttribute("cusAccountStatus", cusAccountStatus);
		model.addAttribute("cusSubType", mfCusCustomer.getCusSubType());
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("filterInInput", "1");//用于展示筛选框
		model.addAttribute("operable", query);
		if(cusAccountStatus != null && !"".equals(cusAccountStatus)){
			Map<String, String> dicMap = new CodeUtils().getMapByKeyName("HM_ACCOUNT_STS");
			String cusAccountStatusName = dicMap.get(cusAccountStatus);
			model.addAttribute("cusAccountStatusName", cusAccountStatusName);
		}
		/**
		 * 暂时逻辑，防止空白页面。但是在场景基础数据整理好后会删除
		 */
		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
		model.addAttribute("reportConfirmFlag", reportConfirmFlag);
		String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
		model.addAttribute("sysProjectName", sysProjectName);
		//判断跳转认证报告页面  1:网信; 0:杭州恩义 2:小风策 3:杭州微溪同盾
		String comReportFlag = new CodeUtils().getSingleValByKey("COM_REPORT_FLAG");
		model.addAttribute("comReportFlag", comReportFlag);
		if(!busViewMap.isEmpty()){
			return "/component/cus/MfCusCustomer_DynaDetail";
		}
		if ("2".equals(mfCusCustomer.getCusBaseType())) {
			return "/component/cus/MfCusPersonCustomer_Detail";
		} else {
			return "/component/cus/MfCusCustomer_Detail";
		}
	}

	/**
	 *
	 * 方法描述： 获取客户详情页面（仅供查看，如业务审批页面）
	 *
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @param cusNo
	 * @date 2017-6-14 下午4:22:40
	 */
	@RequestMapping(value = "/getByIdForView")
	public String getByIdForView(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		if (mfCusCorpBaseInfo != null) {
			FormData formcuscorp00001 = formService.getFormData("cuscorp00001");
			getFormValue(formcuscorp00001);
			getObjValue(formcuscorp00001, mfCusCorpBaseInfo);
		}
		// if()
		return "MfCusCustomer_InfoForView";
	}

	/**
	 * @author czk
	 * @param cusNo
	 * @param cusType
	 * @Description: 展示客户信息，得到的jsp中去掉了所有操作，信息只能查看 date 2016-8-19
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdForShow")
	public String getByIdForShow(Model model ,String cusNo, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String resultPage = "MfCoreCustomer_DetailForShow";
		// 客户基本信息
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		// cusType=mfCusCustomer.getCusType();
		// 转化客户类型信息
		CodeUtils cu = new CodeUtils();
		Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
		String cusTypeName = cusTypeMap.get(mfCusCustomer.getCusType());
		// 历史完结业务统计
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap = pactInterfaceFeign.getBusStatisticalData(cusNo, cusType);
		dataMap.putAll(resMap);
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(cusType);
		mfCusType = cusInterfaceFeign.getMfCusTypeByTypeNo(mfCusType);
		String baseType = "";
		if(mfCusType != null){
			baseType = mfCusType.getBaseType();
		}
		if (BizPubParm.CUS_BASE_TYPE_WAERHOUSE.equals(baseType)) {// 仓储机构
			resultPage = "/component/cus/MfWareHouseCustomer_DetailForShow";
		}else if (BizPubParm.CUS_BASE_TYPE_ZIJIN.equals(baseType)) {// 资金机构
			resultPage = "/component/cus/MfFundCustomer_DetailForShow";
		}else{// 核心企业(保险机构，物流企业，渠道商暂用核心企业的jsp页面)
			// 评级信息
			AppEval appEval = new AppEval();
			String evalAssess = "";// 评级描述
			String evalLevel = "";// 评级级别
			appEval.setCusNo(mfCusCustomer.getCusNo());
			appEval.setEvalSts("4");// 审批通过
			List<AppEval> appEvalList = evalInterfaceFeign.getListByCusNoAndSts(appEval);
			if (appEvalList.size() > 0) {
				appEval = appEvalList.get(0);
				Map<String, String> evalMap = cu.getMapByKeyName("EVAL_LEVEL");
				Map<String, ParmDic> evalMapObj = cu.getMapObjByKeyName("EVAL_LEVEL");
				evalAssess = evalMapObj.get(appEval.getMangGrade()).getRemark();
				evalLevel = evalMap.get(appEval.getApprovalGrade());
			} else {
				appEval = null;
				evalLevel = "未评级";
			}
			dataMap.put("appEval", appEval);
			dataMap.put("evalAssess", evalAssess);
			dataMap.put("evalLevel", evalLevel);
			model.addAttribute("appEval", appEval);
			// 授信信息
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCusNo(mfCusCustomer.getCusNo());
			mfCusCreditApply.setCreditSts(BizPubParm.CREDIT_SIGN_STS);
			Map<String, Object> resultMap = creditApplyInterfaceFeign.getCreditParmMap(mfCusCreditApply);
			mfCusCreditApply = (MfCusCreditApply) resultMap.get("mfCusCreditApply");
			if (mfCusCreditApply != null) {
				if("1".equals(mfCusCreditApply.getCreditSts())|| "2".equals(mfCusCreditApply.getCreditSts()) ||"3".equals(mfCusCreditApply.getCreditSts())){
					dataMap.put("creditSum", "授信中");
				}else{
					MfCusCreditUseHis mfCusCreditUseHis = (MfCusCreditUseHis) resultMap.get("mfCusCreditUseHis");
					dataMap.put("creditSum", mfCusCreditUseHis.getApplySum());
				}
			} else {
				dataMap.put("creditSum", "未授信");
			}
			resultPage = "/component/cus/MfCoreCustomer_DetailForShow";
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		return resultPage;
	}

	/**
	 *
	 * 方法描述： 获得客户是否上传过头像
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @date 2016-9-19 下午6:21:28
	 */
	@RequestMapping(value = "/getIfUploadHeadImg")
	@ResponseBody
	public Map<String, Object> getIfUploadHeadImg(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		try {
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			dataMap.put("flag", mfCusCustomer.getIfUploadHead());
			dataMap.put("headImg", mfCusCustomer.getHeadImg());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得客户经济档案资料信息
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusType
	 * @param cusNo
	 * @param ipage
	 * @date 2016-6-3 下午4:16:02
	 */
	@RequestMapping(value = "/getEconomyById")
	public String getEconomyById(Model model,String cusType, String cusNo, Ipage ipage) throws Exception {
		MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setFormType(cusType);
		String cusForms = mfCusFormConfigFeign.getCusForms(mfCusFormConfig);
		// 资产负债表（企业客户）
		MfCusCapitalInfo mfCusCapitalInfo = new MfCusCapitalInfo();
		mfCusCapitalInfo.setCusNo(cusNo);
		ipage.setParams(this.setIpageParams("mfCusCapitalInfo",mfCusCapitalInfo));
		List<MfCusCapitalInfo> mfCusCapitalInfoList = (List<MfCusCapitalInfo>) mfCusCapitalInfoFeign.findByPage(ipage).getResult();
		// 利润分配
		MfCusCashEnum mfCusCashEnum = new MfCusCashEnum();
		mfCusCashEnum.setCusNo(cusNo);
		ipage.setParams(this.setIpageParams("mfCusCashEnum",mfCusCashEnum));
		List<MfCusCashEnum> mfCusCashEnumList = (List<MfCusCashEnum>) mfCusCashEnumFeign.findByPage(ipage).getResult();
		// 现金流量
		MfCusProfitInfo mfCusProfitInfo = new MfCusProfitInfo();
		mfCusProfitInfo.setCusNo(cusNo);
		ipage.setParams(this.setIpageParams("mfCusProfitInfo",mfCusProfitInfo));
		List<MfCusProfitInfo> mfCusProfitInfoList = (List<MfCusProfitInfo>) mfCusProfitInfoFeign.findByPage(ipage).getResult();
		model.addAttribute("mfCusCapitalInfoList", mfCusCapitalInfoList);
		model.addAttribute("mfCusCashEnumList", mfCusCashEnumList);
		model.addAttribute("mfCusProfitInfoList", mfCusProfitInfoList);
		return "/component/cus/MfCusCustomer_EconomyDetail";
	}

	/**
	 *
	 * 方法描述：
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @date 2016-5-20 上午11:44:21
	 */
	@RequestMapping(value = "/getUpdateById")
	public String getUpdateById(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcus00001 = formService.getFormData("cus00001");
		getFormValue(formcus00001);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		getObjValue(formcus00001, mfCusCustomer);
		return "/component/cus/MfCusCustomer_Tab";
	}

	/**
	 *
	 * 方法描述：客户详情
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param cusType
	 * @date 2016-5-21 下午5:00:59
	 */
	@RequestMapping(value = "/getCusViewPoint")
	public String getCusViewPoint(String cusNo, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, String> map = new HashMap<String, String>();
		map.put("cusNo", cusNo);
		map.put("cusType", cusType);
		ViewUtil.setViewPointParm(request, map);
		// request.getSession().setAttribute("pageInfo", "业务申请号：" + appNo + " "
		// + appProject.getCusName() + " 融资额："
		// + appProject.getActualAmt() + " " + appProject.getProdName() + " " +
		// appProject.getLeaseType() + " "
		// + appProject.getAppSts());
		//

		return "/component/cus/CusViewPoint";
	}

	/**
	 *
	 * 方法描述： 客户信息大表单 ,此方法已废弃
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param cusName
	 * @param mfCusFormConfig
	 * @param cusType
	 * @param cusForms
	 * @date 2016-5-21 下午5:00:47
	 */

	@RequestMapping(value = "/getBigForm")
	public String getBigForm(Model model,String cusNo, String cusName, MfCusFormConfig mfCusFormConfig, String cusType, String cusForms) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		FormService formService = new FormService();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		cusNo = mfCusCustomer.getCusNo();
		cusName = mfCusCustomer.getCusName();
		mfCusFormConfig = new MfCusFormConfig();
		mfCusFormConfig.setFormType(cusType);
		cusForms = mfCusFormConfigFeign.getCusForms(mfCusFormConfig);

		FormData formcuscorp00002 = formService.getFormData("cuscorp00002");
		FormData formcusreg00002 = formService.getFormData("cusreg00002");
		FormData formcusper00002 = formService.getFormData("cusper00002");
		FormData formcusinc00002 = formService.getFormData("cusinc00002");
		FormData formcusfina00002 = formService.getFormData("cusfina00002");
		FormData formcusstaff00002 = formService.getFormData("cusstaff00002");
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		// 企业客户基本信息
		MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
		mfCusCorpBaseInfo.setCusNo(cusNo);
		mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
		if (mfCusCorpBaseInfo == null) {
			mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			mfCusCorpBaseInfo.setCusName(cusName);
		}
		// 员工信息
		MfCusStaff mfCusStaff = new MfCusStaff();
		mfCusStaff.setCusNo(cusNo);
		mfCusStaff = mfCusStaffFeign.getById(mfCusStaff);
		if (mfCusStaff == null) {
			mfCusStaff = new MfCusStaff();
			mfCusStaff.setCusNo(cusNo);
			mfCusStaff.setCusName(cusName);
		}
		getObjValue(formcuscorp00002, mfCusCorpBaseInfo);
		getObjValue(formcusstaff00002, mfCusStaff);
		// 个人客户基本信息
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
		if (mfCusPersBaseInfo == null) {
			mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo.setCusName(cusName);
			mfCusPersBaseInfo.setIdType(mfCusCustomer.getIdType());
			mfCusPersBaseInfo.setIdNum(mfCusCustomer.getIdNum());
			mfCusPersBaseInfo.setCusTel(mfCusCustomer.getCusTel());
		}
		// 收支信息
		MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
		mfCusPersonIncExpe.setCusNo(cusNo);
		mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
		if (mfCusPersonIncExpe == null) {
			mfCusPersonIncExpe = new MfCusPersonIncExpe();
			mfCusPersonIncExpe.setCusNo(cusNo);
			mfCusPersonIncExpe.setCusName(cusName);
		}
		getObjValue(formcusper00002, mfCusPersBaseInfo);
		getObjValue(formcusinc00002, mfCusPersonIncExpe);
		// 合作机构基本信息
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(cusNo);
		mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
		if (mfCusCooperativeAgency == null) {
			mfCusCooperativeAgency = new MfCusCooperativeAgency();
			mfCusCooperativeAgency.setOrgaNo(cusNo);
			mfCusCooperativeAgency.setOrgaName(cusName);
		}
		getObjValue(formcuscoop00002, mfCusCooperativeAgency);
		model.addAttribute("formcuscoop00002", formcuscoop00002);
		if (BizPubParm.CUS_TYPE_CORP.equals(cusType)) {
			return "/component/cus/CusCorpInfo_Insert";
		} else {
			return "/component/cus/CusViewPoint";
		}

	}

	/**
	 * 删除
	 * @param cusNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomerFeign.delete(mfCusCustomer);
		return getListPage();
	}

	/**
	 * 新增页面（进件）
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input1")
	public String input1() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcuscorpbase0002 = formService.getFormData("cuscorpbase0002");
		return "/component/cus/MfCusCustomer_Insert1";
	}

	/**
	 * @author czk
	 * @param appId
	 * @param cusType
	 * @Description: 新增客户，可放大镜选择 date 2016-9-14
	 * @return
	 * @throws Exception
	 * @param nodeNo core_reg 登记核心企业, buyinfo_reg 登记买方信息, findorg_reg 登记资金机构, warehouse_reg 登记仓储方
	 */
	/**
	 * @author czk
	 * @param appId
	 * @Description: 新增客户，可放大镜选择 date 2016-9-14
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputCoopAgency")
	public String inputCoopAgency(Model model,String appId, String baseType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		json = new JSONObject();
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
 		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);

		WKF_NODE node = null;
		if (task.getActivityName().equals(WKF_NODE.core_reg.getNodeNo())) {
			node = WKF_NODE.core_reg;
		} else if (task.getActivityName().equals(WKF_NODE.buyinfo_reg.getNodeNo())) {
			node = WKF_NODE.buyinfo_reg;
		} else if (task.getActivityName().equals(WKF_NODE.findorg_reg.getNodeNo())) {
			node = WKF_NODE.findorg_reg;
		} else if (task.getActivityName().equals(WKF_NODE.warehouse_reg.getNodeNo())) {
			node = WKF_NODE.warehouse_reg;
		}else {
		}
		String nodeNo = node.getNodeNo();// 功能节点编号

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), node, null, null, User.getRegNo(request));
		FormData formcus00005 = formService.getFormData(formId);

		List<OptionsList> oList = new java.util.ArrayList<OptionsList>();
		List<MfCusType> cusTypeList = new ArrayList<MfCusType>();
		@SuppressWarnings("unchecked")
		List<ParmDic> parmList = (List<ParmDic>) new CodeUtils().getCacheByKeyName("CUS_TYPE");
		MfCusType mfCusType = new MfCusType();
		mfCusType.setBaseType(baseType);
		cusTypeList = mfCusTypeFeign.getListByBaseType(mfCusType);//获取该业务身份的客户类型的集合
		for (MfCusType cus : cusTypeList) {//将所需的客户类型放入选择组件下拉列表
			for (ParmDic parmDic : parmList) {
				if (cus.getTypeNo().equals(parmDic.getOptCode())) {
					OptionsList op = new OptionsList();
					op.setOptionLabel(parmDic.getOptName());
					op.setOptionValue(parmDic.getOptCode());
					oList.add(op);
				}
			}
		}
		this.changeFormProperty(formcus00005, "cusType", "optionArray", oList);
		if(oList.size()>0 && oList!=null){
			this.changeFormProperty(formcus00005, "cusType", "initValue", oList.get(0).getOptionValue());//给选择组件赋初值

		}
		model.addAttribute("formcus00005", formcus00005);
		model.addAttribute("query", "");
		model.addAttribute("baseType", baseType);
		model.addAttribute("appId", appId);
		return "/component/cus/MfCusCustomer_InsertCoopAgency";
	}

	/**
	 *
	 * 方法描述： 关联核心企业，仓储机构等保存方法，新增核心企业，仓储机构等不要再走该方法
	 *
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @param ajaxData
	 * @param appId
	 * @date 2016-7-21 下午6:06:24
	 */
	@RequestMapping(value = "/selectForBusAjax")
	@ResponseBody
	public Map<String, Object> selectForBusAjax(String ajaxData, String appId , String baseType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = (String) getMapByJson(ajaxData).get("formId");
			FormData formcuscorpbase0001= formService.getFormData(formId);
			getFormValue(formcuscorpbase0001, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formcuscorpbase0001)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formcuscorpbase0001, mfCusCustomer);
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				// 更新申请表中参与方的信息，暂时添加核心企业，需要自己加
				switch(baseType){
					case BizPubParm.CUS_BASE_TYPE_HEXIAN://核心企业
						mfBusApply.setCusNoCore(mfCusCustomer.getCusNo());
						mfBusApply.setCusNameCore(mfCusCustomer.getCusName());
						break;
					case BizPubParm.CUS_BASE_TYPE_CORP://核心企业
						mfBusApply.setCusNoCore(mfCusCustomer.getCusNo());
						mfBusApply.setCusNameCore(mfCusCustomer.getCusName());
						break;
					default://找不到关联方
						dataMap.put("flag", "error");
						dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
						return dataMap;
				}
				// 业务进入下一个流程
				TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
				String transition = workflowDwrFeign.findNextTransition(task.getId());
				wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
				mfBusApplyFeign.update(mfBusApply);
				creditApplyInterfaceFeign.creditTakedUp(mfBusApply,baseType,mfCusCustomer.getCusNo(),"buyinfo_reg");

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw e;
		}
		return dataMap;

	}
	/**
	 *
	 * 方法描述： 客户登记新增（进件）
	 *
	 * @return
	 * @throws Exception String
	 * @author zhs
	 * @param ajaxData
	 * @param appId
	 * @date 2016-7-21 下午6:06:24
	 */
	@RequestMapping(value = "/insertForBusAjax")
	@ResponseBody
	public Map<String, Object> insertForBusAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCorpBaseInfo mfCusCorpBaseInfo = null;
		MfCusPersBaseInfo mfCusPersBaseInfo = null;
		try {
			Map<String, Object> jsonData = getMapByJson(ajaxData);
			String cusType = String.valueOf(jsonData.get("cusType"));
			String recordNo = String.valueOf(jsonData.get("recordNo"));//身份核查记录编号
			String busFlag = "0";
			String cusBaseType = cusType.substring(0, 1);
			FormData formcuscorpbase0001;
			String formId;

			String projectName = ymlConfig.getSysParams().get("sys.project.name");
			String verificationCode = (String) jsonData.get("verificationCode");// 手机验证码
			String cusTel = String.valueOf(jsonData.get("cusTel"));// 手机号
			String verifyNum = (String) CacheUtil.getMapByKey(CacheUtil.CACHE_KEY.telStoreMap).get(cusTel);
			if (StringUtil.isNotEmpty(verificationCode)) {
				if (!verificationCode.equals(verifyNum)) {
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("验证码校验"));
					return dataMap;
				} else {
					CacheUtil.remMapKeyCache(cusTel, CacheUtil.CACHE_KEY.telStoreMap);
				}
			}

			if (appId == null) {
				MfKindForm mfKindForm = new MfKindForm();
				if ("2".equals(cusBaseType)) {
					mfKindForm.setNodeNo("cusPersonAdd");
					formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
					formcuscorpbase0001 = formService.getFormData(formId);
				} else {
					mfKindForm.setNodeNo("cusCorpAdd");
					formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
					formcuscorpbase0001 = formService.getFormData(formId);
				}
			} else {
				formId = String.valueOf(jsonData.get("formId"));
				if(formId == null||formId ==""){

					formcuscorpbase0001 = formService.getFormData("cus00005");
				}else{
					formcuscorpbase0001= formService.getFormData(formId);
				}
				busFlag = "1";
			}
			getFormValue(formcuscorpbase0001, jsonData);
			if (this.validateFormDataAnchor(formcuscorpbase0001)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formcuscorpbase0001, mfCusCustomer);
				//新增客户处理改为同一个方法，同一个事务
				if ("2".equals(cusBaseType)) {// 进件新增个人客户
					mfCusPersBaseInfo = new MfCusPersBaseInfo();
					setObjValue(formcuscorpbase0001, mfCusPersBaseInfo);
					mfCusCustomer.setContactsName(mfCusPersBaseInfo.getCusName());
					mfCusCustomer.setContactsTel(mfCusPersBaseInfo.getCusTel());
				}else { // 进件新增企业客户
					mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
					setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
					mfCusCustomer.setIdType("B");//表单录入的是社会信用代码证号，所以类型这里默认为B
				}
				Map<String,Object> parmMap = new HashMap<>();
				parmMap.put("busFlag",busFlag);
				parmMap.put("cusBaseType",cusBaseType);
				parmMap.put("appId",appId);
				parmMap.put("recordNo",recordNo);
				parmMap.put("mfCusCustomer", mfCusCustomer);
				parmMap.put("mfCusPersBaseInfo", mfCusPersBaseInfo);
				parmMap.put("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
				parmMap = mfCusCustomerFeign.insetCustomer_new(parmMap);
				if("success".equals(parmMap.get("flag"))){
					dataMap.put("cusNo", parmMap.get("cusNo"));
					dataMap.put("cusType", parmMap.get("cusType"));
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else{
					return parmMap;
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");

			Map<String, String> paramMap = new HashMap<String, String>();
			// paramMap.put("content","客户");
			paramMap.put("reason", e.getMessage());
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage(paramMap));
		}
		return dataMap;

	}
	/**
	 *
	 * 方法描述： 编辑客户登记信息保存
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @param ajaxData
	 * @param cusNo
	 * @param query
	 * @date 2016-12-2 下午2:29:50
	 */
	@RequestMapping(value = "/updateForBusAjax")
	@ResponseBody
	public Map<String, Object> updateForBusAjax(String ajaxData, String cusNo, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String busFlag = "0";
			Map map = getMapByJson(ajaxData);
			FormData formcuscorpbase0001 = formService.getFormData((String)map.get("formId"));
			getFormValue(formcuscorpbase0001, map);
			if (this.validateFormDataAnchor(formcuscorpbase0001)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formcuscorpbase0001, mfCusCustomer);
				mfCusCustomer.setCusNo(cusNo);
				//数据库中的客户数据
				MfCusCustomer mfCusCustomerTmp = new MfCusCustomer();
				mfCusCustomerTmp.setCusNo(cusNo);
				mfCusCustomerTmp = mfCusCustomerFeign.getById(mfCusCustomerTmp);
				Map<String,Object> paramMapTemp = new HashMap<String,Object>();
				String opNo = User.getRegNo(request);
				String opName = User.getRegName(request);
				mfCusCustomer.setCurrentSessionRegNo(opNo);
				mfCusCustomer.setCurrentSessionRegName(opName);
				if (StringUtil.isNotEmpty(mfCusCustomer.getCusTel())) {
					mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
				}
				if (StringUtil.isNotEmpty(mfCusCustomer.getContactsTel())) {
					mfCusCustomer.setCusTel(mfCusCustomer.getContactsTel());
				}
				if ("2".equals(mfCusCustomer.getCusBaseType())) {
					mfCusCustomer.setContactsName(mfCusCustomer.getCusName());
				}
				paramMapTemp.put("mfCusCustomer", mfCusCustomer);
				paramMapTemp.put("mfCusCustomerTmp", mfCusCustomerTmp);
				mfCusCustomer = mfCusCustomerFeign.updateCustomter(paramMapTemp);
				String formId;
				FormData formcuscorp00004;
				//如果是个人客户，处理客户基本信息表中的信息
				if ("2".equals(mfCusCustomer.getCusBaseType())) {
					MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
					setObjValue(formcuscorpbase0001, mfCusPersBaseInfo);
					mfCusPersBaseInfoFeign.update(mfCusPersBaseInfo);
					mfCusCustomer.setContactsName(mfCusCustomer.getCusName());
					//处理客户基本信息表单的数据异步刷新
					MfCusPersBaseInfo mfCusPersBaseInfoTmp = new MfCusPersBaseInfo();
					mfCusPersBaseInfoTmp.setCusNo(cusNo);
					mfCusPersBaseInfoTmp = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfoTmp);

					MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersBaseInfoAction");
					if (mfCusFormConfig != null) {
						formId = mfCusFormConfig.getShowModelDef();
					} else{
						mfCusFormConfig = mfCusFormConfigFeign.getByCusType("base", "MfCusPersBaseInfoAction");
						formId = mfCusFormConfig.getShowModelDef();
					}
					formcuscorp00004 = formService.getFormData(formId);
					if (formcuscorp00004.getFormId() == null) {
//						logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusPersBaseInfoAction表单form" + formId + ".xml文件不存在");
					}
					getFormValue(formcuscorp00004);
					getObjValue(formcuscorp00004, mfCusPersBaseInfoTmp);
					JsonFormUtil jsonFormUtil = new JsonFormUtil();
					String htmlStr = jsonFormUtil.getJsonStr(formcuscorp00004, "propertySeeTag", query);
					dataMap.put("htmlStr", htmlStr);
					dataMap.put("mfCusPersBaseInfo", mfCusPersBaseInfoTmp);

				} else {//如果是企业客户，处理客户基本信息表中的信息

                    if (StringUtil.isEmpty(mfCusCustomer.getExt2())) {// 企业客户未进行联网核查 处理基本信息表中信息
                        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                        setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
                        mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);

                        MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
                        mfCusCorpBaseInfoTmp.setCusNo(cusNo);
                        mfCusCorpBaseInfoTmp = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfoTmp);
                        String postalCode = mfCusCorpBaseInfoTmp.getPostalCode();
                        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
                        if (mfCusFormConfig != null) {
                            formId = mfCusFormConfig.getShowModelDef();
                        } else {
                            mfCusFormConfig = mfCusFormConfigFeign.getByCusType("base", "MfCusCorpBaseInfoAction");
                            formId = mfCusFormConfig.getShowModelDef();
                        }
                        formcuscorp00004 = formService.getFormData(formId);
                        if (formcuscorp00004.getFormId() == null) {
//						logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCorpBaseInfoAction表单form" + formId + ".xml文件不存在");
                        }
                        getFormValue(formcuscorp00004);
                        getObjValue(formcuscorp00004, mfCusCorpBaseInfoTmp);
                        JsonFormUtil jsonFormUtil = new JsonFormUtil();
                        String htmlStr = jsonFormUtil.getJsonStr(formcuscorp00004, "propertySeeTag", query);
                        dataMap.put("postalCode", postalCode);
                        dataMap.put("htmlStr", htmlStr);
                    }else{
                        MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
                        setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
                        mfCusCorpBaseInfoFeign.update(mfCusCorpBaseInfo);
						mfCusCorpBaseInfo.setCurrentSessionBizType(mfCusCustomer.getCurrentSessionBizType());
						mfCusCorpBaseInfo.setCurrentSessionOrgName(mfCusCustomer.getCurrentSessionOrgName());
						mfCusCorpBaseInfo.setCurrentSessionOrgNo(mfCusCustomer.getCurrentSessionOrgNo());
						mfCusCorpBaseInfo.setCurrentSessionRegName(mfCusCustomer.getCurrentSessionRegName());
						mfCusCorpBaseInfo.setCurrentSessionRegNo(mfCusCustomer.getCurrentSessionRegNo());
						mfCusCorpBaseInfo.setCurrentSessionRoleNo(mfCusCustomer.getCurrentSessionRoleNo());
						mfCusCorpBaseInfo.setCurrentSessionSysDate(mfCusCustomer.getCurrentSessionSysDate());
                        MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
                        mfCusCorpBaseInfoTmp.setCusNo(cusNo);
                        mfCusCorpBaseInfoTmp = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfoTmp);
                        String postalCode = mfCusCorpBaseInfoTmp.getPostalCode();
                        MfCusFormConfig mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
                        if (mfCusFormConfig != null) {
                            formId = mfCusFormConfig.getShowModelDef();
                        } else {
                            mfCusFormConfig = mfCusFormConfigFeign.getByCusType("base", "MfCusCorpBaseInfoAction");
                            formId = mfCusFormConfig.getShowModelDef();
                        }
                        formcuscorp00004 = formService.getFormData(formId);
                        if (formcuscorp00004.getFormId() == null) {
//						logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCorpBaseInfoAction表单form" + formId + ".xml文件不存在");
                        }
                        getFormValue(formcuscorp00004);
                        getObjValue(formcuscorp00004, mfCusCorpBaseInfoTmp);
                        JsonFormUtil jsonFormUtil = new JsonFormUtil();
                        String htmlStr = jsonFormUtil.getJsonStr(formcuscorp00004, "propertySeeTag", query);

                        //进行联网核查的企业客户在保存后提取相应信息并更新数据库
                        String queryId = "";
                        FormActive[] activeArray = formcuscorpbase0001.getFormActiveArray();
                        for(FormActive active :activeArray)
                        {
                            if("id".equals(active.getFieldName()))
                            {
                                queryId = active.getInitValue();
                            }
                        }

                        //如果没有点击联网核查就不会有queryId则以最近一条三方记录为核查依据

						/* 没联网核查不进行历史查询
							if("".equals(queryId)){
							 MfThirdServiceRecord mfThirdServiceRecord=new MfThirdServiceRecord();
							 mfThirdServiceRecord.setCusNo(cusNo);
							 mfThirdServiceRecord=mfThirdServiceRecordFeign.getThirdRecodLastOneByCusNo(mfThirdServiceRecord);
							 queryId=mfThirdServiceRecord.getId();
						}*/
						Map<String, Object> resultMap = new HashMap<String, Object>();
						String str ="";
						if(StringUtils.isNotEmpty(queryId)){
							resultMap = mfThirdServiceRecordFeign.getHtmlContent(queryId);//查询联网核查返回结果
							str = resultMap.get("content").toString();
						}
						Map<String,Object> parmMap=new HashMap<>();
						//修改客户的时候，checksave方法不做insert处理，暂用ex10字段作为标识。
						mfCusCorpBaseInfo.setExt10("update");
						parmMap.put("str",str);
						parmMap.put("mfCusCorpBaseInfo",mfCusCorpBaseInfo);
                        dataMap = mfCusCustomerFeign.checkSave(parmMap);
                        dataMap.put("postalCode", postalCode);
                        dataMap.put("htmlStr", htmlStr);
                    }
				}
				CodeUtils cu = new CodeUtils();
				Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_SUB_TYPE");
				String cusTypeName = cusTypeMap.get(mfCusCustomer.getCusSubType());
				dataMap.put("cusSubTypeName", cusTypeName);
				dataMap.put("htmlStrFlag", "1");
				dataMap.put("mfCusCustomer", mfCusCustomer);
				dataMap.put("cusNo", cusNo);
				dataMap.put("cusType", mfCusCustomer.getCusType());
				dataMap.put("cusBaseType", mfCusCustomer.getCusBaseType());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
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
	 * 2016-08-26 此方法已废弃，改成调用MfBusApplyAction中的insertBusPartAjax方法
	 *
	 * @author czk
	 * @Description: 在业务详情信息中，添加业务参与方的信息 date 2016-8-2
	 * @return
	 * @throws Exception
	 *
	 *             public String insertInBusDetail() throws Exception{ ActionContext.initialize(request, response); Map<String, Object> dataMap = new HashMap<String, Object>(); try { formcuscorpbase0003 = formService.getFormData("cuscorpbase0003"); getFormValue(formcuscorpbase0003, getMapByJson(ajaxData)); if (this.validateFormData(formcuscorpbase0003)) { MfCusCustomer mfCusCustomer = new MfCusCustomer(); setObjValue(formcuscorpbase0003, mfCusCustomer); cusName = mfCusCustomer.getCusName(); cusType = mfCusCustomer.getCusType(); mfCusCustomer = mfCusCustomerFeign.insert1(mfCusCustomer); cusNo = mfCusCustomer.getCusNo();
	 *
	 *             cusType = mfCusCustomer.getCusType(); MfBusApply mfBusApply = new MfBusApply(); mfBusApply.setAppId(appId); MfBusApply mfBusApply1 = appInterface.getMfBusApplyByAppId(appId); //更新申请表中参与方的信息，如果已经生成合同，则更新合同中的参与方的信息 if(mfBusApply1 != null){ MfBusPact mfBusPact = new MfBusPact(); if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){ mfBusPact.setPactId(mfBusApply1.getPactId()); }
	 *
	 *             if(cusType.equals("103")){//仓储方 mfBusApply.setCusNoWarehouse(cusNo); mfBusApply.setCusNameWarehouse(mfCusCustomer.getCusName());
	 *
	 *             if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){ mfBusPact.setCusNoWarehouse(cusNo); mfBusPact.setCusNameWarehouse(mfCusCustomer.getCusName()); } //业务进入下一个流程 TaskImpl task= wkfInterface.getTask(mfBusApply1.getWkfAppId(), null); //String url = "1#MfBusFincAppAction_getFincApp.action?fincId="+mfBusFincApp.getFincId(); //TaskImpl task1 = new TaskImpl(); String url = ""; WorkflowDwr workflowDwr= new WorkflowDwr(); String transition=workflowDwr.findNextTransition(task.getId()); wkfInterface.doCommit(task.getId(),AppConstant.OPINION_TYPE_ARREE, url, transition, User.getRegNo(request), ""); }else if(cusType.equals("101")){//核心企业 mfBusApply.setCusNoCore(cusNo); mfBusApply.setCusNameCore(mfCusCustomer.getCusName());
	 *
	 *             if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){ mfBusPact.setCusNoCore(cusNo); mfBusPact.setCusNameCore(mfCusCustomer.getCusName()); } } appInterface.updateApply(mfBusApply); if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){ pactInterface.updatePact(mfBusPact); } }
	 *
	 *
	 *             dataMap.put("cusNo", cusNo); dataMap.put("cusType", cusType); dataMap.put("flag", "success"); dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage()); } else { dataMap.put("flag", "error"); dataMap.put("msg", this.getFormulavaliErrorMsg()); } } catch (Exception e) { e.printStackTrace(); dataMap.put("flag", "error"); dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage()); throw e; } return dataMap;
	 *
	 *             }
	 */

	/**
	 * 发起授信前置条件检查检查
	 *
	 * @author LJW date 2017-3-9
	 * @param cusType
	 * @param cusNo
	 */

	@RequestMapping(value = "/checkCusBus")
	@ResponseBody
	public Map<String, Object> checkCusBus(String cusType, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		StringBuilder msg = new StringBuilder();
		Gson gson = new Gson();
		String projectName = ymlConfig.getSysParams().get("sys.project.name");
		String faUrl = ymlConfig.getSysParams().get("sys.project.fa.url");
		try {
			// 发起授信时先检查后台是否配置了授信模型，没有的话不允许进行授信操作
			MfCusCreditModel mfCusCreditModel = new MfCusCreditModel();
			// 搜索页面发起授信，参数没有cus_type 需要再次获取cus_type的值
			if ("search".equals(cusType)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				if("HNDHZS".equals(projectName)){
					String mfCusCustomeJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusCustomer), faUrl+"/mfCusCustomer/getById");
					mfCusCustomer = gson.fromJson(mfCusCustomeJson,MfCusCustomer.class);
				}else{
					mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				}
				cusType = mfCusCustomer.getCusType();
			}
			mfCusCreditModel.setCusTypeNo(cusType);
			//惠农贷项目
			if("HNDHZS".equals(projectName)){
				String mfCusCreditModelJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusCreditModel), faUrl+"/creditApplyInterface/getByCusTypeNo");
				mfCusCreditModel = gson.fromJson(mfCusCreditModelJson,MfCusCreditModel.class);
			}else{
				mfCusCreditModel = creditApplyInterfaceFeign.getByCusTypeNo(mfCusCreditModel);
			}
			if (mfCusCreditModel == null) {
				dataMap.put("msg", MessageEnum.NO_CREDIT_MODEL.getMessage());
				dataMap.put("fullFlag", "0");
				return dataMap;
			}
			// 调用规则引擎
			dataMap.put("baseInfoStatus", this.checkCusBaseInfoIsFull(cusNo, cusType));
			int cwReportStatus = 0;
			if("HNDHZS".equals(projectName)){
				String cusNoJson = HttpClientUtil.sendPostJson(cusNo, faUrl+"/evalInterface/checkFinanceStatement");
				cwReportStatus = gson.fromJson(cusNoJson,Integer.class);
			}else{
				cwReportStatus = evalInterfaceFeign.checkFinanceStatement(cusNo);
			}
			dataMap.put("cwReportStatus",cwReportStatus);
			dataMap.put("rateStatus", this.checkCusRate(cusNo));
			Map<String, Object> resultMap = new HashMap<String,Object>();
			if("HNDHZS".equals(projectName)){
				String dataMapJson = HttpClientUtil.sendPostJson(gson.toJson(dataMap), faUrl+"/mfCusCustomer/getCreditRules");
				resultMap = gson.fromJson(dataMapJson,Map.class);
			}else{
				resultMap = mfCusCustomerFeign.getCreditRules(dataMap);
			}
			boolean keyFlag = !resultMap.containsKey("baseInfoStatus_res") || !resultMap.containsKey("cwReportStatus_res") || !resultMap.containsKey("rateStatus_res");
			if (resultMap == null || resultMap.isEmpty() || keyFlag) {
				dataMap.put("msg", MessageEnum.ERROR_CHECK_PREFIX.getMessage());
				dataMap.put("fullFlag", "0");
				return dataMap;
			}
			String baseInfoStatusRes = (String) resultMap.get("baseInfoStatus_res");
			String cwReportStatusRes = (String) resultMap.get("cwReportStatus_res");
			String rateStatusRes = (String) resultMap.get("rateStatus_res");
			if ("0".equals(baseInfoStatusRes)) {
				msg.append("客户基本信息、");
			}
			if ("0".equals(cwReportStatusRes)) {
				if (!cusType.startsWith("2")) {
					msg.append("客户财务报表信息、");
				}
			}
			if ("0".equals(rateStatusRes)) {
				msg.append("评级信息、");
			}
			String errMsg = msg.toString();
			if (!"".equals(errMsg)) {
				dataMap.put("msg", errMsg.substring(0, errMsg.length() - 1));
				dataMap.put("fullFlag", "0");
				return dataMap;
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_DATA_CREDIT.getMessage("客户基本信息、财务报表信息和评级信息"));
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 检查客户是否完善了基本信息
	 *
	 * @return
	 * @throws Exception String
	 * @author LJW
	 * @param cusNo
	 * @param cusType
	 * @date 2016-7-29 下午2:47:08
	 *  找不到调用
	 */
	public int checkCusBaseInfoIsFull(String cusNo, String cusType) throws Exception {
		ActionContext.initialize(request, response);
		int isFlag = 0;
		String projectName = ymlConfig.getSysParams().get("sys.project.name");
		Gson gson = new Gson();
		try {
			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setCusNo(cusNo);
			mfCusTable.setDataFullFlag("1");
			if ("202".equals(cusType)) {
				mfCusTable.setTableName("mf_cus_pers_base_info");// 个人客户基本信息表
			} else {
				mfCusTable.setTableName("mf_cus_corp_base_info");// 基本信息表
			}
			// 获得表单尚未填写的表单
			List<MfCusTable> mfCusTableList = new ArrayList<MfCusTable>();
			if("HNDHZS".equals(projectName)){
				String faUrl = ymlConfig.getSysParams().get("sys.project.fa.url");
				String mfCusTableListJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusTable), faUrl+"/mfCusTable/getList");
				mfCusTableList = gson.fromJson(mfCusTableListJson, new TypeToken<List<MfCusTable>>(){}.getType());
			}else{
				mfCusTableList = mfCusTableFeign.getList(mfCusTable);
			}
			if (!mfCusTableList.isEmpty()) {
				isFlag = 1;
			}
			return isFlag;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 方法描述：工程担保业务检查客户必填信息块是否已经完善，是否上传了财务报表信息
	 * @return boolean;true已完善，false未完善
	 * @throws Exception String
	 * @author jialei
	 * @param cusNo
	 * @param creditId
	 * @date 2020-06-19 下午2:47:08
	 */
	@RequestMapping(value = "/checkCusReportByKind")
	@ResponseBody
	public boolean checkCusReportByKind(String cusNo, String creditId) throws Exception {
		ActionContext.initialize(request, response);
		boolean isFlag = true;
		List<MfCusTable> mfCusTableList = null;
		try {
			//只校验工程担保
			MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
			mfCusCreditConfig.setCreditId(creditId);
			mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
			if(!BizPubParm.BUS_MODEL_12.equals(mfCusCreditConfig.getBusModel())){
				return isFlag;
			}
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

			MfCusFormConfig mfCusFormConfig = new MfCusFormConfig();
			mfCusFormConfig.setFormType(mfCusCustomer.getCusBaseType());
			mfCusFormConfig.setUseFlag(BizPubParm.YES_NO_Y);
			mfCusFormConfig.setIsBase(BizPubParm.YES_NO_Y);
			mfCusFormConfig.setIsMust(BizPubParm.YES_NO_Y);
			mfCusFormConfig.setRegView("1");
			List<MfCusFormConfig> configList = mfCusFormConfigFeign.getAll(mfCusFormConfig);
			if(configList!=null&&configList.size()>0){
				for(int i=0;i<configList.size();i++){
					MfCusTable mfCusTable = new MfCusTable();
					mfCusTable.setCusNo(cusNo);
					mfCusTable.setDataFullFlag("1");
					mfCusTable.setTableName(configList.get(i).getTableName());
					mfCusTableList = mfCusTableFeign.getList(mfCusTable);
					if(mfCusTableList!=null&&mfCusTableList.size()>0){
					}else {
						isFlag = false;
					}
				}
			}

			//查询财报
			/*MfCusReportAcount mfCusReportAcount=new MfCusReportAcount();
			mfCusReportAcount.setCusNo(cusNo);
			List<MfCusReportAcount> accountList = mfCusReportAcountFeign.getList(mfCusReportAcount);
			if(accountList!=null&&accountList.size()>0){
			}else {
				isFlag = false;
			}*/
			return  isFlag;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping(value = "/getCusInfIntegrityList")
	public String getCusInfIntegrityList(Model model,String cusNo, String cusType,String formEditFlag) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String baseType=ActionContext.getActionContext().getRequest().getParameter("baseType");
			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setCusNo(cusNo);
			mfCusTable.setCusType(cusType);
			List<MfCusTable> mfCusTableList = mfCusTableFeign.getList(mfCusTable);
			model.addAttribute("mfCusTableList", mfCusTableList);
			if(StringUtil.isEmpty(baseType)|| BizPubParm.CUS_BASE_TYPE_CORP.equals(baseType) || BizPubParm.CUS_BASE_TYPE_PERSON.equals(baseType)){//yht：除普通客户外不需要财务报表
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				if (mfCusCustomer!=null&&"1".equals(mfCusCustomer.getCusBaseType())) {
					CusFinMain cusFinMain = new CusFinMain();
					cusFinMain.setCusNo(cusNo);
					List<CusFinMain> cusFinMainList = pfsInterfaceFeign.getAll(cusFinMain);
					model.addAttribute("cusFinMainList", cusFinMainList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("formEditFlag", formEditFlag);
		return "/component/cus/CusInfIntegrityList";
	}

	/**
	 * 检查客户是否评级
	 *
	 * @author LJW date 2017-2-27
	 * @param cusNo
	 */
	public int checkCusRate(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		int isFlag = 0;// 否
		try {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			String cusLevelId = mfCusCustomer.getCusLevelId();
			if (cusLevelId != null && !"".equals(cusLevelId)) {
				isFlag = 1;// 是
			}
			return isFlag;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 新增校验
	 *
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcus00002 = formService.getFormData("cus00002");
		getFormValue(formcus00002);
		boolean validateFlag = this.validateFormData(formcus00002);
	}

	/**
	 * 修改校验
	 *
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcus00002 = formService.getFormData("cus00002");
		getFormValue(formcus00002);
		boolean validateFlag = this.validateFormData(formcus00002);
	}

	/**
	 * 自动下拉菜单
	 * @param ajaxData
	 * @param query
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/prodAutoMenu")
	@ResponseBody
	public Map<String, Object> prodAutoMenu(String ajaxData, String query) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		String parm = "";
		try {
			EntityUtil entityUtil = new EntityUtil();
			dataMap.put("data", entityUtil.prodAutoMenu(mfCusCustomer, ajaxData, query, parm, null));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	// 跳转到增值服务页面
	@RequestMapping(value = "/addedService")
	public String addedService(Model model ,String cusNo, String showType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("showType", showType);
		model.addAttribute("dataMap", dataMap);
		return "/component/cus/MfAddedServices";
	}

	/**
	 * 增值服务，联网核查
	 * @param cusNo
	 * @param showType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAddServicePage")
	@ResponseBody
	public Map<String, Object> toAddServicePage(String cusNo, String showType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String url = "";
		try {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			if (mfCusCustomer != null) {
				// 个人客户
				// url = PropertiesUtil.getCloudProperty("cloud.ip") + "/servicemanage";
				url = "/servicemanage";
				if ("202".equals(mfCusCustomer.getCusType())) {
					url += "/personal/personServiceListForSC.html";
				} else {// 企业客户
					url += "/enterprise/enterpriseServiceListForSC.html";
				}
				dataMap.put("mfCusCustomer", mfCusCustomer);
			}
			dataMap.put("url", url);
			dataMap.put("showType", showType);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：
	 *
	 * @return
	 * @throws Exception String
	 * @author YuShuai
	 * @param cusType
	 * @date 2017-5-23 下午12:03:03
	 */
	@RequestMapping(value = "/getFormCusNo")
	@ResponseBody
	public Map<String, Object> getFormCusNo(String cusType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusNo = mfCusCustomerFeign.getFormCusNo(cusType);
		try {
			dataMap.put("cusNo", cusNo);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增资金机构页面
	 * @param mfBusApply
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fundForm")
	public String fundForm(Model model, MfBusApply mfBusApply) throws Exception {
		ActionContext.initialize(request, response);
		// 企业客户新增表单
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		FormService formService = new FormService();
		FormData formcus00002 = formService.getFormData("cus00002");
		getFormValue(formcus00002);
		getObjValue(formcus00002, mfBusApply);
		model.addAttribute("formcus00002", formcus00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCustomer_fundForm";
	}

	@RequestMapping(value = "/updateCusTableAndIntegrityAjax")
	@ResponseBody
	public Map<String, Object> updateCusTableAndIntegrityAjax(String relNo, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			String tableName = request.getParameter("tableName");
			String infIntegrity = mfCusCustomerFeign.updateCusTableAndIntegrity(cusNo,relNo, tableName);
			dataMap.put("flag", "success");
			dataMap.put("infIntegrity", infIntegrity);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取共同借款人数据源
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param pageNo
	 * @param ajaxData
	 * @param cusNo
	 * @date 2017-7-21 下午4:22:02
	 */
	@RequestMapping(value = "/getCobBoowerAjax")
	@ResponseBody
	public Map<String, Object> getCobBoowerAjax(Integer pageNo, String ajaxData, String cusNo)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		Ipage ipage = this.getIpage();
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		if(ajaxData != null){
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
		}
		mfCusCustomer.setCusNo(cusNo);
		ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
		ipage = mfCusCustomerFeign.getCobBoowerAjax(ipage);
		dataMap.put("ipage", ipage);
		return dataMap;
	}

	/**
	 * @Description:修改客户经理
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @param cusNo
	 * @param cusMngNo
	 * @param cusMngName
	 * @date: 2017-9-21 下午5:27:36
	 */
	@RequestMapping(value = "/updateCusManageAjax")
	@ResponseBody
	public Map<String, Object> updateCusManageAjax(String cusNo, String cusMngNo, String cusMngName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer.setCusMngNo(cusMngNo);
			mfCusCustomer.setCusMngName(cusMngName);
			mfCusCustomerFeign.updateCusManage(mfCusCustomer);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_OPERATION.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获取客户反欺诈报告查询授权码
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-27 上午9:39:35
	 */
	@RequestMapping(value = "/getEntityAuthCode")
	public String getEntityAuthCode() throws Exception{
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusCustomer_getEntityAuthCode";
	}
	/**
	 *
	 * 方法描述： 获取客户反欺诈报告查询授权码（异步）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-9-30 下午5:23:46
	 */
	@RequestMapping(value = "/getEntityAuthCodeAjax")
	@ResponseBody
	public Map<String, Object> getEntityAuthCodeAjax() throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			Map<String, String> parmMap= new HashMap<String, String>();
			parmMap.put("itemNo", BizPubParm.ANTIFRAUD_ITEM_NO);
			dataMap = mfCusCustomerFeign.getEntityAuthCode(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 获取客户反欺诈报告信息
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param cusNo
	 * @date 2017-9-27 上午9:39:35
	 */
	@RequestMapping(value = "/getCusAntiFraudReport")
	public String getCusAntiFraudReport(String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		dataMap = mfCusCustomerFeign.getAntiFraudParam(cusNo);
		return "/component/cus/MfCusCustomer_AntiFraudReport";
	}
	/**
	 *
	 * 方法描述：异步获取最新的反欺诈
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param idNum
	 * @date 2017-9-30 下午4:59:40
	 */
	@RequestMapping(value = "/getCusAntiFraudReportAjax")
	@ResponseBody
	public Map<String, Object> getCusAntiFraudReportAjax(String idNum) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			//获取报告查询历史记录

			Map<String, String> parmMap = new HashMap<String,String>();
			parmMap.put("idCardNum", idNum);
			parmMap.put("itemType", BizPubParm.ANTIFRAUD_ITEM_TYPE);
			parmMap.put("itemNo", BizPubParm.ANTIFRAUD_ITEM_NO);
			dataMap = mfCusCustomerFeign.getCusAntiFraudReport(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 异步获取查询历史
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @param idNum
	 * @date 2017-9-29 下午8:33:09
	 */
	@RequestMapping(value = "/getReportHistoryAjax")
	@ResponseBody
	public Map<String, Object> getReportHistoryAjax(String idNum) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			//获取报告查询历史记录

			Map<String, String> parmMap = new HashMap<String,String>();
			parmMap.put("idCardNum", idNum);
			parmMap.put("itemType", BizPubParm.ANTIFRAUD_ITEM_TYPE);
			parmMap.put("itemNo", BizPubParm.ANTIFRAUD_ITEM_NO);
			dataMap = mfCusCustomerFeign.getReportHistory(parmMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}


	/**
	 * 方法描述：
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param appId
	 * @param cusNo
	 * @param ipage
	 * @param query
	 * @date 2017-11-25 下午5:22:04
	 */
	@RequestMapping(value = "/mfCusKindTableListPage")
	public String mfCusKindTableListPage(Model model,String appId, String cusNo, Ipage ipage, String query)throws Exception{
		MfBusApply mfBusApply = new MfBusApply();
		if (query == null) {
			query = "";// 默认可编辑
		}
		try {
			String nodeNo = WKF_NODE.kind_table_val.getNodeNo();
			request.setAttribute("ifBizManger", "3");
			request.setAttribute("query", query);
			//判断客户表单信息是否允许编辑
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			MfKindTableConfig mfKindTableConfig = new MfKindTableConfig();
			mfKindTableConfig.setKindNo(mfBusApply.getKindNo());
			List<MfKindTableConfig> list = prdctInterfaceFeign.getMfKindTableConfigList(mfKindTableConfig);
			List<MfCusTable> cusTableList = getDyFormHtml(cusNo, null, query, ipage);
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("list", list);
			paramMap.put("cusTableList", cusTableList);
			cusTableList = mfCusCustomerFeign.getCusKindTableList(paramMap);
			String ajaxData = new Gson().toJson(cusTableList);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("appId", appId);
			//model.addAttribute("query", query);
			model.addAttribute("ajaxData", ajaxData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/cus/MfCusKindTableListPage";
	}

	/**
	 * 方法描述：
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param appId
	 * @param cusNo
	 * @param ipage
	 * @param query
	 * @date 2017-11-25 下午5:22:04
	 */
	@RequestMapping(value = "/mfCusKindTableListPageForFK")
	public String mfCusKindTableListPageForFK(Model model,String appId, String cusNo, Ipage ipage, String query)throws Exception{
		MfBusApply mfBusApply = new MfBusApply();
		try {
			String nodeNo = WKF_NODE.kind_table_val.getNodeNo();
			request.setAttribute("ifBizManger", "3");
			request.setAttribute("query", query);
			//判断客户表单信息是否允许编辑
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			MfKindTableConfig mfKindTableConfig = new MfKindTableConfig();
			mfKindTableConfig.setKindNo(mfBusApply.getKindNo());
			List<MfKindTableConfig> list1 = prdctInterfaceFeign.getMfKindTableConfigList(mfKindTableConfig);
			List<MfKindTableConfig> list = new ArrayList<MfKindTableConfig>();
			for (int i = 0; i < list1.size(); i++) {
				if("mf_cus_farmer_inc_expe".equals(list1.get(i).getTableName())||"mf_cus_pers_base_info".equals(list1.get(i).getTableName())){

				}else {
					list.add(list1.get(i));
				}

			}
			List<MfCusTable> cusTableList = getDyFormHtmlForApply(mfBusApply.getCusNo(), null,appId, query, ipage);
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("list", list);
			paramMap.put("cusTableList", cusTableList);
			cusTableList = mfCusCustomerFeign.getCusKindTableList(paramMap);
			String ajaxData = new Gson().toJson(cusTableList);
			model.addAttribute("cusNo", mfBusApply.getCusNo());
			model.addAttribute("appId", appId);
			//model.addAttribute("query", query);
			model.addAttribute("ajaxData", ajaxData);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/cus/MfCusKindTableListPageForFK";
	}
	/**
	 * 方法描述：校验客户信息是否完整
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param appId
	 * @param cusNo
	 * @param ipage
	 * @param query
	 * @date 2017-11-26 下午4:46:07
	 */
	@RequestMapping(value = "/validateCusInfo")
	@ResponseBody
	public Map<String, Object> validateCusInfo(String appId, String cusNo, Ipage ipage, String query)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
			//获取报告查询历史记录
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			MfKindTableConfig mfKindTableConfig = new MfKindTableConfig();
			mfKindTableConfig.setKindNo(mfBusApply.getKindNo());
			List<MfKindTableConfig> list = prdctInterfaceFeign.getMfKindTableConfigList(mfKindTableConfig);
			List<MfCusTable> cusTableList = getDyFormHtml(cusNo, null, query, ipage);
			Map<String,Object> paramMap = new HashMap<>();
			paramMap.put("list", list);
			paramMap.put("cusTableList", cusTableList);
			dataMap = mfCusCustomerFeign.validateCusInfo(paramMap);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			e.printStackTrace();
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
	 * @author lzshuai
	 * @date 2017-11-27 下午3:36:19
	 */
	@RequestMapping(value = "/getCusTelList")
	public String getCusTelList() throws Exception{

		ActionContext.initialize(request, response);

		//签约
		JSONArray jArray = new JSONArray();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();



		json1.put("optName", "放款");
		json1.put("optCode", "5,6,7");
		json2.put("optName", "未放款");
		json2.put("optCode", "1,2,3,4");
		jArray.add(json1);
		jArray.add(json2);
		//借款失败
		JSONArray jArray0 = new JSONArray();
		JSONObject json0 = new JSONObject();
		json0.put("optName", "借款失败");
		json0.put("optCode", "5");
		jArray0.add(json0);
		this.getHttpRequest().setAttribute("qianyueJsonArray", jArray);
		this.getHttpRequest().setAttribute("laonfaileJsonArray", jArray0);

		/*JSONObject json = new JSONObject();
		json=(JSONObject) dataMap.get("json");
		ajaxData = json.toString();*/

		return "/component/cus/MfSmsCusTel_getCusTelList";
	}
	/**
	 *
	 * 方法描述： 查询客户电话号码
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @param ajaxData
	 * @param removeCusId
	 * @param cusType
	 * @param cusBaseType
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @date 2017-11-27 下午8:22:19
	 */
	@RequestMapping(value = "/findCusTelListAjax")
	@ResponseBody
	public Map<String, Object> findCusTelListAjax(String ajaxData, String removeCusId, String cusType, String cusBaseType, Integer pageNo, String tableId, String tableType) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 关联关系查询时要移除关联主体客户id 显示所有其他客户
			if (!StringUtil.isBlank(removeCusId)) {
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> outerList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_no not in ");
				c.setValue(new String[] { removeCusId });
				outerList.add(c);
				clist.add(outerList);
			}
			mfCusCustomer.setCusType(cusType);
			mfCusCustomer.setCusBaseType(cusBaseType);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.findCusTelList(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	 * 方法描述： 获取客户列表
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @param ajaxData
	 * @param removeCusId
	 * @param cusType
	 * @param cusBaseType
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @date 2017-12-28 下午7:23:14
	 */
	@RequestMapping(value = "/getCusListAjax")
	@ResponseBody
	public Map<String, Object> getCusListAjax(String ajaxData, String removeCusId, String cusType, String cusBaseType, Integer pageNo, String tableId, String tableType) throws Exception{

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			// 关联关系查询时要移除关联主体客户id 显示所有其他客户
			if (!StringUtil.isBlank(removeCusId)) {
				List<List<Criteria>> clist = mfCusCustomer.getCriteriaLists();
				List<Criteria> outerList = new java.util.ArrayList<Criteria>();
				Criteria c = new Criteria();
				c.setAndOr("  ");
				c.setListValue(true);
				c.setCondition(" cus_no not in ");
				c.setValue(new String[] { removeCusId });
				outerList.add(c);
				clist.add(outerList);
			}
			mfCusCustomer.setCusType(cusType);
			mfCusCustomer.setCusBaseType(cusBaseType);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.getCusList(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	 * 方法描述： 根据客户证件号码获得客户信息
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param idNum
	 * @param query
	 * @date 2017-12-7 上午11:03:05
	 */
	@RequestMapping(value = "/getCusInfoByIdNumAjax")
	@ResponseBody
	public Map<String, Object> getCusInfoByIdNumAjax(String idNum, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			if(StringUtil.isEmpty(idNum)){
				dataMap.put("existFlag", "0");
				dataMap.put("flag", "success");
				return dataMap;
			}
			mfCusCustomer.setIdNum(idNum);
			List<MfCusCustomer> mfCusCustomerList = mfCusCustomerFeign.getByIdNum(mfCusCustomer);
			if(mfCusCustomerList!=null&&mfCusCustomerList.size()>0){
				mfCusCustomer=mfCusCustomerList.get(0);
				String cusBaseType = mfCusCustomer.getCusBaseType();
				String cusNo = mfCusCustomer.getCusNo();
				String cusType = mfCusCustomer.getCusType();
				dataMap.put("existFlag", "1");
				dataMap.put("cusInfo", mfCusCustomer);
				String cusMngNo = mfCusCustomer.getCusMngNo();
				if(StringUtil.isEmpty(cusMngNo)){
					cusMngNo = mfCusCustomer.getOpNo();
				}
				SysUser sysUser = new SysUser();
				sysUser.setOpNo(cusMngNo);
				sysUser = sysUserFeign.getById(sysUser);
				if(sysUser!=null&&StringUtil.isNotEmpty(sysUser.getMobile())){
					dataMap.put("cusMngPhone", sysUser.getMobile());
				}else{
					dataMap.put("cusMngPhone", "未登记");
				}

				MfKindForm mfKindForm = new MfKindForm();
				String formId;
				FormData formcommon = null;
				if(BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)){
					MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
					mfCusPersBaseInfo.setCusNo(cusNo);
					mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
					mfKindForm.setNodeNo("cusPersonEdit");
					formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
					formcommon = formService.getFormData(formId);
					getFormValue(formcommon);
					getObjValue(formcommon, mfCusCustomer);
					getObjValue(formcommon, mfCusPersBaseInfo);
				}else if(BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)){
					MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
					mfCusCorpBaseInfo.setCusNo(cusNo);
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
					mfKindForm.setNodeNo("cusCorpEdit");
					formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
					formcommon = formService.getFormData(formId);
					getFormValue(formcommon);
					getObjValue(formcommon, mfCusCustomer);
					getObjValue(formcommon, mfCusCorpBaseInfo);
					this.changeFormProperty(formcommon, "beginDate", "labelStyle", "startRang");
					this.changeFormProperty(formcommon, "endDate", "labelStyle", "endRang");
				}else {
				}
				//获取客户类别
				MfCusType mfCusType = new MfCusType();
				mfCusType.setBaseType(cusBaseType);
				List<MfCusType> cusTypeList = mfCusTypeFeign.getAllList(mfCusType);
				if (cusTypeList!=null&&cusTypeList.size()>0) {
					JSONArray cusTypeArray = JSONArray.fromObject(cusTypeList);
					for (int i = 0; i < cusTypeArray.size(); i++) {
						cusTypeArray.getJSONObject(i).put("id",
								cusTypeArray.getJSONObject(i).getString("typeNo"));
						cusTypeArray.getJSONObject(i).put("name",
								cusTypeArray.getJSONObject(i).getString("typeName"));
					}
					dataMap.put("cusType", cusTypeArray);
					//明细类别
					mfCusType.setTypeNo(cusType);
					mfCusType = mfCusTypeFeign.getById(mfCusType);
					String[] subTypes = mfCusType.getSubType().split("\\|");
					JSONArray subTypeArray = new JSONArray();
					JSONObject tmpObject=null;
					Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
					for (int i = 0; i < subTypes.length; i++) {
						tmpObject = new JSONObject();
						tmpObject.put("id", subTypes[i]);
						if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
							continue;
						}
						tmpObject.put("name", dicMap.get(subTypes[i]));
						subTypeArray.add(tmpObject);
					}
					dataMap.put("cusSubType", subTypeArray);
				}
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcommon, "bootstarpTag", query);
				dataMap.put("htmlStr", htmlStr);
			}else{
				dataMap.put("existFlag", "0");
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			//throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 完善信息保存
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param idNum
	 * @param formId
	 * @param query
	 * @date 2017-12-9 上午9:53:29
	 */
	@RequestMapping(value = "/savePerfectCusInfoAjax")
	@ResponseBody
	public Map<String, Object> savePerfectCusInfoAjax(String idNum, String formId, String query) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setIdNum(idNum);
			List<MfCusCustomer> mfCusCustomerList = mfCusCustomerFeign.getByIdNum(mfCusCustomer);
			if(mfCusCustomerList!=null&&mfCusCustomerList.size()>0){
				mfCusCustomer=mfCusCustomerList.get(0);
				String cusBaseType = mfCusCustomer.getCusBaseType();
				String cusNo = mfCusCustomer.getCusNo();
				String cusType = mfCusCustomer.getCusType();
				dataMap.put("existFlag", "1");
				dataMap.put("cusInfo", mfCusCustomer);
				FormData formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				if(BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)){
					MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
					mfCusPersBaseInfo.setCusNo(cusNo);
					mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
					getObjValue(formcommon, mfCusPersBaseInfo);
				}else if(BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)){
					MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
					mfCusCorpBaseInfo.setCusNo(cusNo);
					mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
					//getObjValue(formcommon, mfCusCorpBaseInfo);
				}else {
				}
				//明细类别
				MfCusType mfCusType = new MfCusType();
				mfCusType.setTypeNo(cusType);
				mfCusType = mfCusTypeFeign.getById(mfCusType);
				String[] subTypes = mfCusType.getSubType().split("\\|");
				JSONArray subTypeArray = new JSONArray();
				JSONObject tmpObject=null;
				Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
				for (int i = 0; i < subTypes.length; i++) {
					tmpObject = new JSONObject();
					tmpObject.put("id", subTypes[i]);
					if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
						continue;
					}
					tmpObject.put("name", dicMap.get(subTypes[i]));
					subTypeArray.add(tmpObject);
				}
				dataMap.put("cusSubType", subTypeArray);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formcommon, "bootstarpTag", query);
				dataMap.put("htmlStr", htmlStr);
			}else{
				dataMap.put("existFlag", "0");
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 跳转完善信息页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param idNum
	 * @date 2017-12-14 下午6:48:39
	 */
	@RequestMapping(value = "/inputUpdate")
	public String inputUpdate(String idNum,Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		json = new JSONObject();
		MfCusCustomer mfCusCustomer=new MfCusCustomer();
//		if(StringUtil.isEmpty(idNum)){
//			dataMap.put("existFlag", "0");
//			dataMap.put("flag", "success");
//			return SUCCESS;
//		}
		mfCusCustomer.setIdNum(idNum);
		List<MfCusCustomer> mfCusCustomerList = mfCusCustomerFeign.getByIdNum(mfCusCustomer);
		if(mfCusCustomerList!=null&&mfCusCustomerList.size()>0){
			mfCusCustomer=mfCusCustomerList.get(0);
			String cusBaseType = mfCusCustomer.getCusBaseType();
			String cusNo = mfCusCustomer.getCusNo();
			String cusType = mfCusCustomer.getCusType();
			String cusMngNo = mfCusCustomer.getCusMngNo();
			dataMap.put("existFlag", "1");
			dataMap.put("cusInfo", mfCusCustomer);
			MfKindForm mfKindForm = new MfKindForm();
			String formId;
			FormData formcommon = null;
			if(BizPubParm.CUS_TYPE_PERS.equals(cusBaseType)){
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				if(StringUtil.isEmpty(mfCusPersBaseInfo.getInfoOffer())){
					mfCusPersBaseInfo.setInfoOffer("1");//客户推荐
				}
				mfKindForm.setNodeNo("cusPersonAdd");
				formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
				formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusPersBaseInfo);
			}else if(BizPubParm.CUS_TYPE_CORP.equals(cusBaseType)){
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
				if(StringUtil.isEmpty(mfCusCorpBaseInfo.getInfoOffer())){
					mfCusCorpBaseInfo.setInfoOffer("1");//客户推荐
				}
				mfKindForm.setNodeNo("cusCorpAdd");
				formId = prdctInterfaceFeign.getMfkindForm(mfKindForm).getAddModel();
				formcommon = formService.getFormData(formId);
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusCorpBaseInfo);
			}else {
			}
			//设置表单元素不可编辑
			FormActive[] list = formcommon.getFormActives();
			for (int i = 0; i < list.length; i++) {
				FormActive formActive = list[i];
				formActive.setReadonly("1");
			}
			//获取客户类别
			MfCusType mfCusType = new MfCusType();
			mfCusType.setBaseType(cusBaseType);
			List<MfCusType> cusTypeList = mfCusTypeFeign.getAllList(mfCusType);
			if (cusTypeList!=null&&cusTypeList.size()>0) {
				JSONArray cusTypeArray = JSONArray.fromObject(cusTypeList);
				for (int i = 0; i < cusTypeArray.size(); i++) {
					cusTypeArray.getJSONObject(i).put("id",
							cusTypeArray.getJSONObject(i).getString("typeNo"));
					cusTypeArray.getJSONObject(i).put("name",
							cusTypeArray.getJSONObject(i).getString("typeName"));
				}
				json.put("cusType", cusTypeArray);
				//明细类别
				mfCusType.setTypeNo(cusType);
				mfCusType = mfCusTypeFeign.getById(mfCusType);
				String[] subTypes = mfCusType.getSubType().split("\\|");
				JSONArray subTypeArray = new JSONArray();
				JSONObject tmpObject=new JSONObject();
				tmpObject.put("id", mfCusCustomer.getCusSubType());
				CodeUtils code=new CodeUtils();
				Map<String,String> codeMap=code.getMapByKeyName("CUS_SUB_TYPE");
				tmpObject.put("name", codeMap.get(mfCusCustomer.getCusSubType()));
				subTypeArray.add(tmpObject);
				Map<String,String>  dicMap = new CodeUtils().getMapByKeyName("CUS_SUB_TYPE");
				for (int i = 0; i < subTypes.length; i++) {
					tmpObject = new JSONObject();
					if(!subTypes[i].equals(mfCusCustomer.getCusSubType())){
						tmpObject.put("id", subTypes[i]);
						if(StringUtil.isEmpty(dicMap.get(subTypes[i]))){//数据字典禁用
							continue;
						}
						tmpObject.put("name", dicMap.get(subTypes[i]));
						subTypeArray.add(tmpObject);
					}
				}
				json.put("cusSubType", subTypeArray);
			}
			model.addAttribute("formcommon", formcommon);
			//判断客户名下是否存在申请
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setCusNo(cusNo);
			int appNum = mfBusApplyFeign.getBusSubmitCntByCusNo(mfBusApply);
			json.put("appNum", appNum);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("cusType", cusType);
			model.addAttribute("cusMngNo", cusMngNo);
		}
		String ajaxData = json.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/MfCusCustomer_InputUpdate";
	}

	/**
	 * 方法描述： 跳转到根据渠道相关信息客户列表页面
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2018-3-8 上午11:21:31
	 */
	@RequestMapping(value = "/getCusListByTrenchPage")
	public String getCusListByTrenchPage() throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);
		JSONArray classifyTypeJsonArray = codeUtils.getJSONArrayByKeyName("CLASSIFY_TYPE");
		this.getHttpRequest().setAttribute("classifyTypeJsonArray", classifyTypeJsonArray);
		return "/component/cus/trenchsubsidiary/MfCusCustomer_trenchCusList";
	}

	/**
	 * 方法描述： 根据渠道相关信息客户列表
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2018-3-8 上午11:28:28
	 */
	@RequestMapping(value = "/findCusBytrenchPageAjax")
	@ResponseBody
	public Map<String, Object> findCusBytrenchPageAjax(String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer = setCusCustomer(mfCusCustomer);
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCustomer", mfCusCustomer));
			ipage = mfCusCustomerFeign.findCusBytrenchPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	/**
	 * 方法描述： 设置客户属性
	 *
	 * @param mfCusCustomer
	 * @return
	 * @throws Exception MfCusCustomer
	 * @author 沈浩兵
	 * @date 2018-3-8 上午11:53:07
	 */
	private MfCusCustomer setCusCustomer(MfCusCustomer mfCusCustomer) throws Exception {
		String dataRang = (String) this.getHttpRequest().getSession().getAttribute("dataRang");
		String trenchOpNo = (String) this.getHttpRequest().getSession().getAttribute("trenchOpNo");
		String channelSourceNo = (String) this.getHttpRequest().getSession().getAttribute("channelSourceNo");
		if (BizPubParm.TRENCH_USER_DATA_RANG_1.equals(dataRang)) {// 本人
			mfCusCustomer.setTrenchOpNo(trenchOpNo);
			mfCusCustomer.setChannelSourceNo(channelSourceNo);
		} else if (BizPubParm.TRENCH_USER_DATA_RANG_2.equals(dataRang)) {// 本渠道
			mfCusCustomer.setChannelSourceNo(channelSourceNo);
		} else if (BizPubParm.TRENCH_USER_DATA_RANG_3.equals(dataRang)) {// 本渠道及其子渠道
			MfBusTrench mfBusTrench = new MfBusTrench();
			mfBusTrench.setTrenchUid(channelSourceNo);
			String trenchChildStr = mfBusTrenchFeign.getTrenchChildStr(mfBusTrench);
			mfCusCustomer.setChannelSourceStr(trenchChildStr);
		}else {
		}
		return mfCusCustomer;
	}

	/**
	 * 方法描述： 跳转渠道客户详情页面
	 *
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2018-3-9 上午8:53:15
	 */
	@RequestMapping(value = "/getTrenchCusById")
	public String getTrenchCusById(Model model, String appId, String cusNo, String relNo, String evalAppNo, String evalCredit) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		FormData formcommon = null;
		String authFlag = null;
		Map<String, Object> dataMap = null;
		String cusFullFlag = null;
		MfCusCustomer mfCusCustomer = null;
		MfCusPersBaseInfo mfCusPersBaseInfo = null;
		String cusBaseFlag = null;
		String gradeModel = null;
		String gradeModelUseFlag = null;
		String gradeModelName = null;
		MfCusFormConfig mfCusFormConfig = null;
		String formId = null;
		FormData formcusper00003 = null;
		MfCusCorpBaseInfo mfCusCorpBaseInfo = null;
		FormData formcuscorp00004 = null;
		MfBusApply mfBusApply = null;
		String firstKindNo = null;
		String pleFlag = null;
		String busFlag = null;
		String storageFlag = null;
		String cusTypeName = null;
		MfCusClassify mfCusClassify = null;
		String busModel = null;
		List<CusFinMain> cusFinMainList;
		MfSysKind mfSysKind = null;
		boolean relation = false;
		List<MfEvalGradeCard> evalGradeCardList = null;

		String opType = request.getParameter("opType"); // 表示是授信业务请求
		String scNo = BizPubParm.SCENCE_TYPE_DOC_CUS;
		/*
		 * 当query为"query"或者ifBizManger为"0"时，解析的表单中不可单字段编辑； 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑； 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
		 */
		request.setAttribute("ifBizManger", "3");
		// query = "query";
		// 判断客户表单信息是否允许编辑
		MfBusApply mba = new MfBusApply();
		mba.setAppId(appId);
		mba = mfBusApplyFeign.getById(mba);
		String query;
		if (mba != null && "26".equals(mba.getBusModel())) {
			query = "";
		} else {
			query = mfCusCustomerFeign.validateCusFormModify(cusNo, appId, BizPubParm.FORM_EDIT_FLAG_CUS);
		}

		FormData formcusuploadhead0001 = formService.getFormData("cusuploadhead0001");

		List<MfCusTable> cusTableList = getDyFormHtml(cusNo, relNo, query);

		// LJW 获取wkfAppId 20170301
		String wkfAppId = null;
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCusNo(cusNo);
		mfCusCreditApply.setCreditSts(BizPubParm.CREDIT_SIGN_STS);
		Map<String, Object> resultMap = creditApplyInterfaceFeign.getCreditParmMap(mfCusCreditApply);
		mfCusCreditApply = (MfCusCreditApply) resultMap.get("mfCusCreditApply");
		if (mfCusCreditApply != null) {
			MfCusCreditUseHis mfCusCreditUseHis = (MfCusCreditUseHis) resultMap.get("mfCusCreditUseHis");
			String creditType = String.valueOf(resultMap.get("creditType"));
			String creditAppId = String.valueOf(resultMap.get("appId"));
			request.setAttribute("ifBizManger", "0"); // 表单项不可编辑
			request.setAttribute("creditType", creditType);
			request.setAttribute("creditAppId", creditAppId);
			request.setAttribute("mfCusCreditUseHis", mfCusCreditUseHis);
			formcommon = formService.getFormData("creditapply0001_new");
			getFormValue(formcommon);
			getObjValue(formcommon, mfCusCreditApply);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			MfCusTable mfCusTable = new MfCusTable();
			mfCusTable.setShowType("1");
			mfCusTable.setTableDes("授信信息");
			mfCusTable.setAction("MfCusCreditApplyAction");
			mfCusTable.setDataFullFlag(BizPubParm.YES_NO_Y);
			mfCusTable.setHtmlStr(htmlStr);
			cusTableList.add(mfCusTable);
			authFlag = "1";
			wkfAppId = String.valueOf(resultMap.get("wkfAppId"));
		} else {
			authFlag = "0";
		}
		dataMap = new HashMap<String, Object>();
		dataMap.put("cusTableList", cusTableList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;

		// 查询尚未录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(cusNo);
		mfCusTable.setDataFullFlag("0");
		cusTableList = mfCusTableFeign.getList(mfCusTable);
		cusFullFlag = "0";
		if (cusTableList == null || cusTableList.size() == 0) {
			cusFullFlag = "1";// 客户所有表单信息已经全部录入
		}

		mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);

		// 客户基本信息
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人客户 获取个人客户基本信息
			mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
			cusBaseFlag = "0";
			if (mfCusPersBaseInfo != null) {
				gradeModel = mfCusPersBaseInfo.getGradeModel();
				Object obj = evalInterfaceFeign.getEvalScenceConfigById(gradeModel);
				if (obj != null) {
					JSONObject jobj = JSONObject.fromObject(obj);
					gradeModelUseFlag = jobj.getString("useFlag");
					gradeModelName = jobj.getString("evalScenceName");
				}
				cusBaseFlag = "1";
				mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusPersBaseInfoAction");
				if (mfCusFormConfig == null) {
				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				formcusper00003 = formService.getFormData(formId);
				if (formcusper00003.getFormId() == null) {
					// logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusPersBaseInfoAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formcusper00003);
				getObjValue(formcusper00003, mfCusPersBaseInfo);
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
				mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
			}

		} else {// 企业客户 获取企业客户基本信息
			mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfoTmp = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
			cusBaseFlag = "0";
			if (mfCusCorpBaseInfoTmp != null) {
				gradeModel = mfCusCorpBaseInfoTmp.getGradeModel();
				Object obj = evalInterfaceFeign.getEvalScenceConfigById(gradeModel);
				if (obj != null) {
					JSONObject jobj = JSONObject.fromObject(obj);
					gradeModelUseFlag = jobj.getString("useFlag");
					gradeModelName = jobj.getString("evalScenceName");
				}
				cusBaseFlag = "1";
				mfCusFormConfig = mfCusFormConfigFeign.getByCusType(mfCusCustomer.getCusType(), "MfCusCorpBaseInfoAction");
				if (mfCusFormConfig == null) {
				} else {
					formId = mfCusFormConfig.getShowModelDef();
				}
				formcuscorp00004 = formService.getFormData(formId);
				if (formcuscorp00004.getFormId() == null) {
					// logger.error("客户类型为" + mfCusCustomer.getCusType() + "的MfCusCorpBaseInfoAction表单form" + formId + ".xml文件不存在");
				}
				getFormValue(formcuscorp00004);
				getObjValue(formcuscorp00004, mfCusCorpBaseInfoTmp);
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfoTmp.getPostalCode());
			}
		}

		if (StringUtil.isNotEmpty(appId)) {
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			// 该笔业务只保存并未提交流程
			if (BizPubParm.APP_STS_UN_COMPLETE.equals(mfBusApply.getAppSts())) {
				dataMap.put("completeFlag", "0");
				firstKindNo = mfBusApply.getKindNo();
			}
		}
		// 申请信息
		pleFlag = "0";
		busFlag = "0";
		if (!"CREDIT_APPLY".equals(opType)) {
			if (mfBusApply == null) {
				mfBusApply = appInterfaceFeign.getRecentAppByCusNo(cusNo);
			}
			if (mfBusApply != null) {
				appId = mfBusApply.getAppId();
				busModel = mfBusApply.getBusModel();
				busFlag = "1";
				// 仓储方信息
				storageFlag = "0";
				if (busModel.equals(BizPubParm.BUS_MODEL_1) || busModel.equals(BizPubParm.BUS_MODEL_2) || busModel.equals(BizPubParm.BUS_MODEL_4)) {// 1-动产质押 2-仓单 4-保兑仓
					MfCusCustomer cusWarehouseInfo = new MfCusCustomer();
					cusWarehouseInfo.setCusNo(mfBusApply.getCusNoWarehouse());
					cusWarehouseInfo = mfCusCustomerFeign.getById(cusWarehouseInfo);
					if (cusWarehouseInfo != null) {
						storageFlag = "1";
						request.setAttribute("cusWarehouseInfo", cusWarehouseInfo);
					}
				}
			}
		}

		// 转化客户类型信息
		CodeUtils cu = new CodeUtils();
		Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_SUB_TYPE");
		cusTypeName = cusTypeMap.get(mfCusCustomer.getCusSubType());

		mfCusClassify = new MfCusClassify();
		mfCusClassify.setCusNo(cusNo);
		List<MfCusClassify> mfCusClassifyList = mfCusClassifyFeign.getNewByCusNo(mfCusClassify);
		if (mfCusClassifyList.size() > 0) {
			mfCusClassify = mfCusClassifyList.get(0);
		}

		// 获取财务报表信息 LJW
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		cusFinMainList = pfsInterfaceFeign.getAll(cusFinMain);
		if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
			for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
				cFinMain.setFinCapFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "1"));
				cFinMain.setFinProFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "2"));
				cFinMain.setFinCashFlag(pfsInterfaceFeign.doCheckFinData(cFinMain, "3"));
			}
		}

		// 获取产品号 默认取线下
		mfSysKind = new MfSysKind();
		mfSysKind.setKindProperty("2");
		mfSysKind.setUseFlag("1");
		mfSysKind.setCusType(mfCusCustomer.getCusSubType());
		mfSysKind.setBrNo(User.getOrgNo(request));
		// mfSysKind.setRoleNoArray(User.getRoleNo(request));
		List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
		if (mfSysKindList != null && mfSysKindList.size() > 0) {
			firstKindNo = mfSysKindList.get(0).getKindNo();
		}

		// 2、风险级别信息
		List<RiskBizItemRel> riskBizItemRelList = riskInterfaceFeign.findByRelNo(cusNo);
		String riskLevel = "-1";
		for (int i = 0; i < riskBizItemRelList.size(); i++) {
			String riskLevelThis = riskBizItemRelList.get(i).getRiskLevel();
			// 风险级别越高，riskLevel越大；如果是风险级别是业务拒绝级，riskLevel为99
			if ("99".equals(riskLevelThis)) {
				riskLevel = riskLevelThis;
				break;
			} else {
				if (Integer.valueOf(riskLevelThis) > Integer.valueOf(riskLevel)) {
					riskLevel = riskLevelThis;
				}
			}
		}
		String riskName = "风险检查通过";
		if (!"-1".equals(riskLevel) && !"0".equals(riskLevel)) {
			Map<String, String> dicMap = new CodeUtils().getMapByKeyName("RISK_PREVENT_LEVEL");
			riskName = dicMap.get(riskLevel);
		}
		dataMap.put("riskLevel", riskLevel);
		dataMap.put("riskName", riskName);

		// 获取该客户是否有已经提交的业务，有的话，客户详情页面中未完善的表单块要隐藏掉
		int busSubmitCnt = appInterfaceFeign.getBusSubmitCnt(cusNo);
		dataMap.put("busSubmitCnt", busSubmitCnt);
		relation = mfCusRelationFeign.getRelationByCusNo(cusNo);
		dataMap.put("relation", relation);
		// 客户评级评分卡信息
		if (evalAppNo != null && "evalCredit".equals(evalCredit)) {
			evalGradeCardList = evalInterfaceFeign.getEvalGradeCardByNo(cusNo, evalAppNo);
		}

		// 6、多笔业务处理
		MfBusApply mfBusApplyTmp = new MfBusApply();
		mfBusApplyTmp.setCusNo(cusNo);
		List<MfBusApply> mfBusApplyList = appInterfaceFeign.getMultiBusList(mfBusApplyTmp);
		dataMap.put("moreApplyCount", mfBusApplyList.size());
		MfBusPact mfBusPactTmp = new MfBusPact();
		mfBusPactTmp.setCusNo(cusNo);
		List<MfBusPact> mfBusPacts = pactInterfaceFeign.getMultiBusList(mfBusPactTmp);
		dataMap.put("morePactCount", mfBusPacts.size());
		MfBusFincApp mfBusFincAppTmp = new MfBusFincApp();
		mfBusFincAppTmp.setCusNo(cusNo);
		List<MfBusFincApp> mfBusFincApps = pactInterfaceFeign.getMultiBusList(mfBusFincAppTmp);
		dataMap.put("moreFincCount", mfBusFincApps.size());
		MfAssureInfo mfAssureInfo = new MfAssureInfo();
		mfAssureInfo.setAssureNo(cusNo);
		List<MfBusApplyAssureInfo> mfBusApplyAssureInfos = assureInterfaceFeign.getMultiBusList(mfAssureInfo);
		dataMap.put("moreAssureCount", mfBusApplyAssureInfos.size());
		// 当前登录系统标识
		String sysFlag = (String) request.getSession().getAttribute("sysFlag");
		dataMap.put("sysFlag", sysFlag);

		model.addAttribute("scNo", scNo);
		model.addAttribute("query", query);
		model.addAttribute("formcusuploadhead0001", formcusuploadhead0001);
		model.addAttribute("mfCusCreditApply", mfCusCreditApply);
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("authFlag", authFlag);


		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusFullFlag", cusFullFlag);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("mfCusPersBaseInfo", mfCusPersBaseInfo);
		model.addAttribute("cusBaseFlag", cusBaseFlag);
		model.addAttribute("gradeModel", gradeModel);
		model.addAttribute("gradeModelUseFlag", gradeModelUseFlag);
		model.addAttribute("gradeModelName", gradeModelName);
		model.addAttribute("mfCusFormConfig", mfCusFormConfig);
		model.addAttribute("formId", formId);
		model.addAttribute("formcusper00003", formcusper00003);
		model.addAttribute("mfCusCorpBaseInfo", mfCusCorpBaseInfo);
		model.addAttribute("formcuscorp00004", formcuscorp00004);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("firstKindNo", firstKindNo);
		model.addAttribute("pleFlag", pleFlag);
		model.addAttribute("storageFlag", storageFlag);
		model.addAttribute("busFlag", busFlag);
		model.addAttribute("cusTypeName", cusTypeName);
		model.addAttribute("mfCusClassify", mfCusClassify);
		model.addAttribute("busModel", busModel);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("mfSysKind", mfSysKind);
		model.addAttribute("relation", relation);
		model.addAttribute("evalGradeCardList", evalGradeCardList);




		if ("2".equals(mfCusCustomer.getCusBaseType())) {
			return "/component/cus/MfCusPersonCustomer_TrenchCusDetail";
		} else {
			return "/component/cus/MfCusCustomer_TrenchCusDetail";
		}
	}



	/**
	 *
	 * 方法描述： 获取动态表单html
	 * @param cusNo
	 * @param relNo
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author zhs
	 * @date 2017-9-22 上午11:24:50
	 */
	private List<MfCusTable> getDyFormHtml(String cusNo,String relNo, String query) throws Exception{
		MfCusCorpBaseInfo mfCusCorpBaseInfo = null;
		FormData formcommon = null;
		FormService formService = new FormService();

		if(StringUtil.isEmpty(relNo)){
			relNo = cusNo;
		}
		List<MfCusTable> cusTableList = new ArrayList<MfCusTable>();
		// 查询已经录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(relNo);
		cusTableList = mfCusTableFeign.getList(mfCusTable);

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			if ("0".equals(cusTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = cusTableList.get(i).getAction();
			String htmlStr = "";
			if ("MfCusCorpBaseInfoAction".equals(action)) {
				mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);

				// 结束日期显示处理
				String endDate = mfCusCorpBaseInfoFeign.dealEndDate(mfCusCorpBaseInfo.getEndDate(),"2");
				mfCusCorpBaseInfo.setEndDate(endDate);

				Child child = mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
				if(child!=null){
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			} else if ("MfCusPersBaseInfoAction".equals(action)) {
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				// logger.error("个人客户基本信息展示，信息来源：{},推荐者编号：{}，推荐者名称：{}",mfCusPersBaseInfo.getInfoOffer(),mfCusPersBaseInfo.getRecommenderNo(),mfCusPersBaseInfo.getRecommenderName());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else {
			}
			cusTableList.get(i).setHtmlStr(htmlStr);
		}

		return cusTableList;
	}
	/**
	 *
	 * 方法描述： 跳转客户入口列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-2-11 下午2:35:35
	 */
	@RequestMapping(value = "/getEntListPage")
	public String getEntListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray cusTypeJsonArray = codeUtils.getJSONArrayByKeyName("CUS_TYPE");
		model.addAttribute("cusTypeJsonArray", cusTypeJsonArray);
		JSONArray classifyTypeJsonArray = codeUtils.getJSONArrayByKeyName("CLASSIFY_TYPE");
		model.addAttribute("classifyTypeJsonArray", classifyTypeJsonArray);
		return "/component/cus/MfCusCustomer_EntList";
	}

	/**
	 *
	 * 方法描述： 客户入口列表
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018-2-11 下午2:37:36
	 */
	@RequestMapping(value = "/findEntByPageAjax")
	@ResponseBody
	public Map<String, Object> findEntByPageAjax(Ipage ipage,String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer	mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			//Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法findEntByPageAjax
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.findEntByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	//变更渠道客户是否有效
	@RequestMapping(value = "/updategroomflag")
	@ResponseBody
	public Map<String, Object> updategroomflag(String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer	mfCusCustomer = new MfCusCustomer();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			String cusNo = (String)jobj.get("cusNo");
			mfCusCustomer.setCusNo(cusNo);
			String groomflag = (String)jobj.get("groomflag");
			mfCusCustomer.setGroomflag(groomflag);
			mfCusCustomerFeign.update(mfCusCustomer);
			dataMap.put("flag", "success");
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	//客户转单更新
	@RequestMapping(value = "/cusslipAjax")
	@ResponseBody
	public Map<String, Object> cusslipAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try{
			FormData formcusslip = formService.getFormData("cusslip");
			getFormValue(formcusslip, getMapByJson(ajaxData));
			if(this.validateFormData(formcusslip)) {
				MfOaTrans mfOaTrans = new MfOaTrans();
				setObjValue(formcusslip, mfOaTrans);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mfOaTrans", mfOaTrans);
				mfOaTrans = mfOaTransFeign.cusinsert(map);
				Map<String, String> paramMap = new HashMap<String, String>();
				String msg=User.getRegName(request)+"将客户移交给"+mfOaTrans.getRecOpName()+"客户经理。";
				sysTaskInfoInterfaceFeign.insertMessage(PasConstant.PAS_SUB_TYPE_SYS_MSG,"转单通知",msg,mfOaTrans.getRecOpNo(),PasConstant.PAS_IMPORT_LEV_3,PasConstant.PAS_TASK_STS_1,User.getRegName(request));
				dataMap.put("flag", "success");
				dataMap.put("msg","转单成功");

			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "转单失败");
			throw e;
		}
		return dataMap;
	}
	//打开获取未入业务客户信息界面
	@RequestMapping(value = "/getNotCusInfoList")
	public String getNotCusInfoList() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/MfCusGetNotCusInfo";
	}
	//打開选择客户页面
	@RequestMapping(value = "/getCusmngList")
	public  String getCusmngList(Model model,String cusmugno) throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("cusmngno", cusmugno);
		return "/component/cus/Mfcusslipinfo";
	}
	//打开选择客户转单页面
	@RequestMapping(value = "/getnotCusInfo")
	public String getnotCusInfo(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formcusslip = formService.getFormData("cusslip");
		MfCusCustomer	mfCusCustomer = new MfCusCustomer();
		MfCusCustomer	mfCusCustomer1 = new MfCusCustomer();
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setRelationId(cusNo);
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer1=mfCusCustomerFeign.getById(mfCusCustomer);
		if(mfCusCustomer1.getCusMngNo()!=null&&mfCusCustomer1.getCusMngName()!=null){
			String cusmngno=mfCusCustomer1.getCusMngNo();
			String cusmnname=mfCusCustomer1.getCusMngName();
			mfOaTrans.setTransOpName(User.getRegName(request));
			mfOaTrans.setTransOpNo(cusmngno);
			mfOaTrans.setTransContent(cusmnname);
			model.addAttribute("cusmngno", cusmngno);
		}
		getObjValue(formcusslip, mfOaTrans);
		model.addAttribute("formcusslip", formcusslip);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSlip";
	}
	//获取未入业务客户信息
	@RequestMapping(value = "/getNotCusInfoPageAjax")
	@ResponseBody
	public Map<String, Object> GetNotCusInfoPageAjax(Ipage ipage,String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer	mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.GetNotCusInfo(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	//打开渠道客户总数详情页面
	@RequestMapping(value = "/getEntCusListPage")
	public String getEntCusListPage() throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray CLASSIFY_TYPE = new CodeUtils().getJSONArrayByKeyName("CLASSIFY_TYPE");
		JSONArray RECOMMEND_CUS_TYPE = new CodeUtils().getJSONArrayByKeyName("RECOMMEND_CUS_TYPE");
		JSONArray RECOMMEND_CUS_TST = new CodeUtils().getJSONArrayByKeyName("RECOMMEND_CUS_TST");
		this.getHttpRequest().setAttribute("RECOMMEND_CUS_TST", RECOMMEND_CUS_TST);
		this.getHttpRequest().setAttribute("RECOMMEND_CUS_TYPE", RECOMMEND_CUS_TYPE);
		this.getHttpRequest().setAttribute("CLASSIFY_TYPE", CLASSIFY_TYPE);
		this.getHttpRequest().setAttribute("cusNo",this.getHttpRequest().getParameter("cusNo"));
		return "/component/app/MfCusApply_List";
	}
	//查询渠道客户信息
	@RequestMapping(value = "/getcusInfoByTrenchPageAjax")
	@ResponseBody
	public Map<String, Object> getcusInfoByTrenchPageAjax(Ipage ipage,String ajaxData, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer	mfCusCustomer = new MfCusCustomer();
		String cusNo=request.getParameter("cusNo");
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setChannelSourceNo(cusNo);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.getcusInfoByTrenchPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	 * 方法描述： 打开共享客户列表
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018-3-9 下午4:33:21
	 */
	@RequestMapping(value = "/getCusShareListPage")
	public String getCusShareListPage() throws Exception {
		ActionContext.initialize(request, response);

		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray cusTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("CUS_TYPE");
		this.getHttpRequest().setAttribute("cusTypeJsonArray", cusTypeJsonArray);

		return "/component/cus/MfCusShare_List";
	}

	/**
	 *
	 * 方法描述： 客户共享数据源
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2018-3-9 下午4:41:35
	 */
	@RequestMapping(value = "/findCusShareListByPageAjax")
	@ResponseBody
	public Map<String, Object> findCusShareListByPageAjax(String ajaxData,String classifyType, Integer pageNo, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setShareCusStats("2");
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.findShareCusByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述： 根据渠道编号查找关联客户编号
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月7日 上午9:37:35
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByTrenchAjax")
	@ResponseBody
	public Map<String, Object> findByTrenchAjax(String ajaxData,Integer pageNo, String tableId, String tableType,String trenchUid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setChannelSourceNo(trenchUid);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.findByTrench(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 方法描述：  根据资金机构查找关联客户编号
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 * @param agenciesUid
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月7日 下午4:38:02
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByAgenciesAjax")
	@ResponseBody
	public Map<String, Object> findByAgenciesAjax(String ajaxData,Integer pageNo, String tableId, String tableType,String agenciesUid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);// 我的筛选
			mfCusCustomer.setCusNoFund(agenciesUid);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage = mfCusCustomerFeign.findByAgencies(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getCusNoByIdNum")
	@ResponseBody
	public String getCusNoByIdNum(String idNum)throws Exception{
		return mfCusCustomerFeign.getCusNoByIdNum(idNum);
	}
	/**
	 *
	 * 方法描述： 获取动态表单html
	 * @param cusNo
	 * @param relNo
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author zhs
	 * @param query
	 * @date 2017-9-22 上午11:24:50
	 */
	@SuppressWarnings("unchecked")
	public List<MfCusTable> getDyFormHtmlForApply(String cusNo,String relNo,String appId, String query,Ipage ipage) throws Exception{
		if(StringUtil.isEmpty(relNo)){
			relNo = cusNo;
		}
		FormService formService = new FormService();
		List<MfCusTable> cusTableList = new ArrayList<MfCusTable>();
		// 查询已经录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(relNo);
		cusTableList = mfCusTableFeign.getList(mfCusTable);

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			if ("0".equals(cusTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = cusTableList.get(i).getAction();
			String htmlStr = "";
			FormData formcommon;
			if ("MfCusCorpBaseInfoAction".equals(action)) {
				MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
				mfCusCorpBaseInfo.setCusNo(cusNo);
				mfCusCorpBaseInfo = mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);

				// 结束日期显示处理
				String endDate=mfCusCorpBaseInfo.getEndDate();
				if(endDate==null) {
					endDate="";
				}
				endDate = mfCusCorpBaseInfoFeign.dealEndDate(endDate,"2");
				mfCusCorpBaseInfo.setEndDate(endDate);

				Child child = mfCusCorpBaseInfoFeign.getLoanUseById(mfCusCorpBaseInfo);
				if(child!=null){
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusCorpBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			} else if ("MfCusPersBaseInfoAction".equals(action)) {
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
//				logger.error("个人客户基本信息展示，信息来源：{},推荐者编号：{}，推荐者名称：{}",mfCusPersBaseInfo.getInfoOffer(),mfCusPersBaseInfo.getRecommenderNo(),mfCusPersBaseInfo.getRecommenderName());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusCustomer);
				getObjValue(formcommon, mfCusPersBaseInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if ("MfCusAccountDebtorAction".equals(action)) {
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusAccountDebtor",mfCusAccountDebtor));
				String tableFormId="tableAccountDebtorList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAccountDebtor>) mfCusAccountDebtorFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfCusAccountDetailAction".equals(action)) {
				MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
				mfCusAccountDetail.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusAccountDetail",mfCusAccountDetail));
				String tableFormId="tableAccountDebtorList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAccountDetail>) mfCusAccountDetailFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusShareholderAction".equals(action)) {

				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				mfCusShareholder.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusShareholder",mfCusShareholder));
				String tableFormId="tablecusShareholderListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusShareholder>) mfCusShareholderFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusHighInfoAction".equals(action)) {

				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				mfCusHighInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusHighInfo",mfCusHighInfo));
				String tableFormId="tablecusExecutiveListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusHighInfo>) mfCusHighInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusAssetsAction".equals(action)) {

				MfCusAssets mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				mfCusAssets.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusAssets",mfCusAssets));
				String tableFormId="tablecusFixedAssetsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusAssets>) mfCusAssetsFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusEquityInfoAction".equals(action)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(cusNo);
				mfCusEquityInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusEquityInfo",mfCusEquityInfo));
				htmlStr = jtu.getJsonStr("tablecusequ00001", "tableTag", (List<MfCusEquityInfo>) mfCusEquityInfoFeign.findByPage(ipage).getResult(), null, true);

			}  else if ("MfCusBankAccManageAction".equals(action)) {
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(cusNo);
				mfCusBankAccManage.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusBankAccManage",mfCusBankAccManage));
				String tableFormId="tablecusAccountListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusBankAccManage>) mfCusBankAccManageFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusPersonCorpAction".equals(action)) {//个人名下企业
				MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
				mfCusPersonCorp.setCusNo(cusNo);
				mfCusPersonCorp.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonCorp",mfCusPersonCorp));
				String tableFormId="tablecusPersonCorpBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonCorp>) mfCusPersonCorpFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusPersonLiabilitiesAction".equals(action)) {//个人资产负债表
				MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				mfCusPersonLiabilities.setCusNo(cusNo);
				mfCusPersonLiabilities.setRelNo(relNo);
				mfCusPersonLiabilities.setAppId(appId);
				mfCusPersonLiabilities.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities",mfCusPersonLiabilities));
				String tableFormId="tablecusliabilitiesBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonLiabilities>) mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult(), null, true);
			}else if ("MfCusWarehouseAction".equals(action)) {
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				mfCusWarehouse.setCusNo(cusNo);
				mfCusWarehouse.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusWarehouse",mfCusWarehouse));
				String tableFormId="tablecusWarehouseListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusWarehouse>) mfCusWarehouseFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusBankAcceptanceBillAction".equals(action)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);

				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill",mfCusBankAcceptanceBill));
				htmlStr = jtu.getJsonStr("tablecusbankbill0001", "tableTag", (List<MfCusBankAcceptanceBill>) mfCusBankAcceptanceBillFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusGuaranteeOuterAction".equals(action)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				mfCusGuaranteeOuter.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter",mfCusGuaranteeOuter));
				String tableFormId="tablecusGuaranteeountListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGuaranteeOuter>) mfCusGuaranteeOuterFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusGoodsAction".equals(action)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				mfCusGoods.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusGoods",mfCusGoods));
				String tableFormId="tablecusGoodsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusGoodsFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusListedInfoAction".equals(action)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				mfCusListedInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusListedInfo",mfCusListedInfo));
				String tableFormId="tablecusListListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusListedInfo>) mfCusListedInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusSellInfoAction".equals(action)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				mfCusSellInfo.setCusNo(cusNo);
				mfCusSellInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusSellInfo",mfCusSellInfo));
				String tableFormId="tablecusSellListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusSellInfo>) mfCusSellInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonJobAction".equals(action)) {// 职业信息
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(cusNo);
				mfCusPersonJob.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusPersonJob",mfCusPersonJob));
				String tableFormId="tablecusJobListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusPersonJobFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusFamilyInfoAction".equals(action)) {// 个人客户社会关系
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo));
				String tableFormId="tablecusRelationListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusGoods>) mfCusFamilyInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonAssetsInfoAction".equals(action)) {//资产信息
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				mfCusPersonAssetsInfo.setRelNo(relNo);
				mfCusPersonAssetsInfo.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo",mfCusPersonAssetsInfo));
				String tableFormId="tablecusAssetsListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonAssetsInfo>) mfCusPersonAssetsInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonDebtInfoAction".equals(action)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(relNo);
				mfCusPersonDebtInfo.setOpNo(User.getRegNo(request));

				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo",mfCusPersonDebtInfo));
				String tableFormId="tablecusDebtListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusPersonDebtInfo>) mfCusPersonDebtInfoFeign.findByPage(ipage).getResult(), null, true);
			} else if ("MfCusPersonCreditInfoAction".equals(action)) {// 个人客户信用情况
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
				mfCusPersonCreditInfo.setRelNo(relNo);
				mfCusPersonCreditInfo = mfCusPersonCreditInfoFeign.getById(mfCusPersonCreditInfo);
				// formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonCreditInfo);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusPersonIncExpeAction".equals(action)) {// 收支情况
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
				mfCusPersonIncExpe.setRelNo(relNo);
				mfCusPersonIncExpe = mfCusPersonIncExpeFeign.getById(mfCusPersonIncExpe);
				// formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusPersonIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			} else if ("MfCusLegalEquityInfoAction".equals(action)) {// 法人对外投资
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);
				mfCusLegalEquityInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo",mfCusLegalEquityInfo));
				String tableFormId="tablecusInvestmentListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusLegalEquityInfo>) mfCusLegalEquityInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusLegalEmployInfoAction".equals(action)) {// 法人对外任职情况
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);
				mfCusLegalEmployInfo.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo",mfCusLegalEmployInfo));
				String tableFormId="tablecusCorpRepServeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusLegalEmployInfo>) mfCusLegalEmployInfoFeign.findByPage(ipage).getResult(), null, true);

			} else if ("MfCusCorpMajorChangeAction".equals(action)) {// 企业重大变更
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);
				mfCusCorpMajorChange.setRelNo(relNo);

				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange",mfCusCorpMajorChange));
				String tableFormId="tablecusMajorChangeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusCorpMajorChange>) mfCusCorpMajorChangeFeign.findByPage(ipage).getResult(), null, true);

			}else if ("MfCusFarmerIncExpeAction".equals(action)) {// 农户收支情况
				MfCusFarmerIncExpe mfCusFarmerIncExpe = new MfCusFarmerIncExpe();
				mfCusFarmerIncExpe.setCusNo(cusNo);
				mfCusFarmerIncExpe = mfCusFarmerIncExpeFeign.getById(mfCusFarmerIncExpe);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusFarmerIncExpe);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);
			}else if ("MfCusPersonFlowAssetsInfoAction".equals(action)) {// 流动资产
				MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
				mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
				mfCusPersonFlowAssetsInfo.setRelNo(relNo);
				mfCusPersonFlowAssetsInfo.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo",mfCusPersonFlowAssetsInfo));
				String tableFormId="tablecusflowassetsBaseListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusPersonFlowAssetsInfo>)mfCusPersonFlowAssetsInfoFeign.findByPage(ipage).getResult(), null,true);
			}else if ("MfTrenchUserAction".equals(action)) {// 渠道操作员
				WebCusLineReg webCusLineReg = new WebCusLineReg();
				webCusLineReg.setChannelSourceNo(cusNo);
				htmlStr = jtu.getJsonStr("tableTrenchUserList", "tableTag", mfTrenchUserFeign.getCusLineRegList(webCusLineReg), null, true);
			}else if ("MfCusGuaLoanOuterSumAction".equals(action)) {

				MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum = new MfCusGuaLoanOuterSum();
				mfCusGuaLoanOuterSum.setCusNo(cusNo);
				mfCusGuaLoanOuterSum = mfCusGuaLoanOuterSumFeign.getById(mfCusGuaLoanOuterSum);
				formcommon = formService.getFormData(cusTableList.get(i).getShowModelDef());
				getFormValue(formcommon);
				getObjValue(formcommon, mfCusGuaLoanOuterSum);
				htmlStr = jsonFormUtil.getJsonStr(formcommon, "propertySeeTag", query);

			}else if("MfCusBorrowerInfoAction".equals(action)){//共同借款人信息
				MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusBorrowerInfo",mfCusBorrowerInfo));
				String tableFormId="tablecusBorrowerList";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusBorrowerInfo>)mfCusBorrowerInfoFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusSaleProductAction".equals(action)){//销售产品
				MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
				mfCusSaleProduct.setCusNo(cusNo);
				mfCusSaleProduct.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusSaleProduct",mfCusSaleProduct));
				String tableFormId="tablecussaleproductBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusSaleProduct>)mfCusSaleProductFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusPlantBreedAction".equals(action)){//种植养殖信息
				MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
				mfCusPlantBreed.setCusNo(cusNo);
				mfCusPlantBreed.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusPlantBreed",mfCusPlantBreed));
				String tableFormId="tablecusplantbreedBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusPlantBreed>)mfCusPlantBreedFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusBusServiceAction".equals(action)){//商业服务业信息
				MfCusBusService mfCusBusService = new MfCusBusService();
				mfCusBusService.setCusNo(cusNo);
				mfCusBusService.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusBusService",mfCusBusService));
				String tableFormId="tablecuscusbusserviceBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", (List<MfCusBusService>)mfCusBusServiceFeign.findByPage(ipage).getResult(), null,true);
			}else if("MfCusProfitLossAction".equals(action)) {//个人损益表
				MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
				mfCusProfitLoss.setCusNo(cusNo);
				mfCusProfitLoss.setAppId(appId);
				mfCusProfitLoss.setOpNo(User.getRegNo(request));
				ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
				String tableFormId = "tablecusprofitlossBase";
				if (StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())) {
					tableFormId = "table" + cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", (List<MfCusProfitLoss>) mfCusProfitLossFeign.findByPage(ipage).getResult(), null, true);
			}else {
			}
			cusTableList.get(i).setHtmlStr(htmlStr);
		}
		return cusTableList;
	}
	//判断是否给已选客户使用客户转单
	@RequestMapping(value = "/judgecusslip")
	@ResponseBody
	public Map<String, Object> judgecusslip(String cusNo) throws Exception{
		Map<String, Object> dataMape = new HashMap<String, Object>();
		List<Map<String,Object>> dataMap = new ArrayList<Map<String,Object>>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		try {
			dataMap = mfCusCustomerFeign.getNotCussilpsList(mfCusCustomer);
			if(dataMap.size()!=0){
				dataMape.put("flag","true");
			}else{
				dataMape.put("flag","flase");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMape;
	}
	//检查当前客户是否录入个人负债信息和个人损益
	@RequestMapping(value = "/checkDebtAndProfit")
	@ResponseBody
	public Map<String,String> checkDebtAndProfit(String appId,Ipage ipage) throws Exception{
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		String debtAndProfitFlag = "0";
		Map<String,String> dataMap = new HashMap<String,String>();
		try {
			mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);

			if (mfBusApply!=null){
				String cusNo = mfBusApply.getCusNo();
				MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				mfCusPersonLiabilities.setCusNo(cusNo);
				mfCusPersonLiabilities.setOpNo(User.getRegNo(request));
				mfCusPersonLiabilities.setAppId(appId);
				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities", mfCusPersonLiabilities));
				List<MfCusPersonLiabilities> mfCusPersonLiabilitiesList = (List<MfCusPersonLiabilities>) mfCusPersonLiabilitiesFeign.findByPage(ipage).getResult();
				MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
				mfCusProfitLoss.setCusNo(cusNo);
				mfCusProfitLoss.setOpNo(User.getRegNo(request));
				mfCusProfitLoss.setAppId(appId);
				ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
				List<MfCusProfitLoss> mfCusProfitLossList = (List<MfCusProfitLoss>) mfCusProfitLossFeign.findByPage(ipage).getResult();
				if (mfCusProfitLossList!=null&&mfCusProfitLossList.size()>0&&mfCusPersonLiabilitiesList!=null&&mfCusPersonLiabilitiesList.size()>0){
					debtAndProfitFlag = "1";
				}
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "success");
			}
			dataMap.put("debtAndProfitFlag",debtAndProfitFlag);
		}catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("debtAndProfitFlag",debtAndProfitFlag);
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/getCusRelationinfo")
	@ResponseBody
	public Map<String, Object> getCusRelationinfo(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfCusCustomer> mfCusCustomerList =new ArrayList<MfCusCustomer>();
		List<MfCusCustomer> mfCusCustomerList2 =new ArrayList<MfCusCustomer>();
		List<MfCusCustomer> mfCusCustomerList3 =new ArrayList<MfCusCustomer>();
		List<MfCusCustomer> mfCusCustomerList4 =new ArrayList<MfCusCustomer>();
		List<MfCusCustomer> mfCusCustomerList5 =new ArrayList<MfCusCustomer>();
		List<MfAssureInfo> mfAssureInfoList=new ArrayList<MfAssureInfo>();
		List<MfCusBorrowerInfo> mfCusBorrowerInfooList=new ArrayList<MfCusBorrowerInfo>();
		List<String> List=new ArrayList<String>();
		MfCusCustomer meCusCustomer = new MfCusCustomer();
		boolean flag = true;
		try {
			meCusCustomer.setCusNo(cusNo);
			meCusCustomer=mfCusCustomerFeign.getById(meCusCustomer);
			mfCusCustomerList2.add(meCusCustomer);
			//给借款人附实体信息
			MfCusCustomer mfCusCustomer3 = new MfCusCustomer();
			String idnum=meCusCustomer.getIdNum();
			String custel=meCusCustomer.getCusTel();
			String cusname=meCusCustomer.getCusName();
			mfCusCustomer3.setIdNum(idnum);
			mfCusCustomer3.setCusNo(idnum);
			mfCusCustomer3.setContactsTel(custel);
			mfCusCustomer3.setCusName(cusname);
			mfCusCustomer3.setCusnexus("1");
			mfCusCustomerList.add(mfCusCustomer3);
			if(!"".equals(appId)) {
				mfAssureInfoList = mfCusCustomerFeign.getAssureListByAppid(appId);
				//遍历保证人重复身份id，获取保证人
				if (mfAssureInfoList.size() != 0) {
					Set set = new  HashSet();
						for(MfAssureInfo mfAssureInfo1: mfAssureInfoList){
                            MfCusCustomer mfCusCustomer2 = new MfCusCustomer();
							mfCusCustomer2.setCusNo(mfAssureInfo1.getAssureNo());
							mfCusCustomer2=mfCusCustomerFeign.getById(mfCusCustomer2);
							if("2".equals(mfCusCustomer2.getCusBaseType())){
							if(set.add(mfAssureInfo1.getIdNum())){
                                MfCusCustomer mfCusCustomer4 = new MfCusCustomer();
                                mfCusCustomer4.setCusNo(mfAssureInfo1.getAssureNo());
                                mfCusCustomer4=mfCusCustomerFeign.getById(mfCusCustomer4);
                                MfCusCustomer mfCusCustomer1 = new MfCusCustomer();
								mfCusCustomer1.setAssurename(mfAssureInfo1.getAssureName());
								mfCusCustomer1.setIdNum(mfAssureInfo1.getIdNum());
								mfCusCustomer1.setContactsTel(mfCusCustomer4.getContactsTel());
								mfCusCustomer1.setCusNo(mfAssureInfo1.getIdNum());
								mfCusCustomerList.add(mfCusCustomer1);
							}
						}
					}
				}
			}
			MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
			mfCusBorrowerInfo.setCusNo(cusNo);
			mfCusBorrowerInfooList = mfCusBorrowerInfoFeign.getAllBorrowerByCusNo(mfCusBorrowerInfo);
			//遍历共借重复身份id，获取共借人
			if(mfCusBorrowerInfooList.size()!=0){
				Set set = new  HashSet();
				for(MfCusBorrowerInfo mfCusBorrower1: mfCusBorrowerInfooList){
						if(set.add(mfCusBorrower1.getBorrowIdNum())){
                            MfCusCustomer mfCusCustomer1 = new MfCusCustomer();
							mfCusCustomer1.setBorrowname(mfCusBorrower1.getBorrowName());
							mfCusCustomer1.setIdNum(mfCusBorrower1.getBorrowIdNum());
							mfCusCustomer1.setContactsTel(mfCusBorrower1.getBorrowTel());
							mfCusCustomer1.setCusNo(mfCusBorrower1.getBorrowIdNum());
							mfCusCustomer1.setCommAddress(mfCusBorrower1.getAddrDetail());
							mfCusCustomerList.add(mfCusCustomer1);
					}
				}
			}
			Set set1 = new  HashSet();
			for(MfCusCustomer mfCusCustomer:mfCusCustomerList){
				if(set1.add(mfCusCustomer.getIdNum())){
					mfCusCustomerList3.add(mfCusCustomer);
				}else{
					mfCusCustomer.setExt6("4");
					mfCusCustomerList3.add(mfCusCustomer);
				}
			}
			for(MfCusCustomer mfCusCustomer:mfCusCustomerList3){
				if(mfCusCustomer.getExt6()=="4"){
					mfCusCustomerList5.add(mfCusCustomer);
				}
			}
			mfCusCustomerList4.add(mfCusCustomer3);
			Set set3 = new  HashSet();
			Set set2 = new  HashSet();
			set2.add(mfCusCustomer3.getIdNum());
			for(MfCusCustomer mfCusCustomer:mfCusCustomerList5){
				if(set3.add(mfCusCustomer.getIdNum())){
					set2.add(mfCusCustomer.getIdNum());
					mfCusCustomerList4.add(mfCusCustomer);
				}
			}
			for (MfCusCustomer mfCusCustomer:mfCusCustomerList){
				if(set2.add(mfCusCustomer.getIdNum())){
					mfCusCustomerList4.add(mfCusCustomer);
				}
			}
			for(MfCusCustomer mfCusCustomer:mfCusCustomerList4){
				if(mfCusCustomer.getBorrowname()!=null){
					mfCusCustomer.setCusnexus("3");
					mfCusCustomer.setCusName(mfCusCustomer.getBorrowname());
				}
				if(mfCusCustomer.getAssurename()!=null){
					mfCusCustomer.setCusnexus("2");
					mfCusCustomer.setCusName(mfCusCustomer.getAssurename());
				}
				if (mfCusCustomer.getExt6() == "4") {
					mfCusCustomer.setCusnexus("4");
			}
			}
			Ipage ipage = this.getIpage();
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,mfCusCustomerList4, ipage,true);
			dataMap.put("ipage",tableHtml);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增客户短信验证码校验-发送短信验证码
	 * @param cusTel
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVerificationCode")
	public Map<String, Object> sendVerificationCode(String cusTel) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			if (StringUtil.isBlank(cusTel)) {
				resultMap.put("flag", "error");
				resultMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("手机号码"));
				return resultMap;
			}

			String verfiyNum = "";// 验证码

			// 先验证是否有验证码
			if (CacheUtil.getMapByKey(CacheUtil.CACHE_KEY.telStoreMap).containsKey(cusTel)) {
				verfiyNum = CacheUtil.getMapByKey(CacheUtil.CACHE_KEY.telStoreMap).get(cusTel).toString();
			} else {
				verfiyNum = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));// 随机6位
			}

			Map<String, String> parMap = new HashMap<String, String>();
			parMap.put("验证码", verfiyNum);
			Map<String, String> resMap = smsUtilFeign.sendSMS_Normal(parMap, "sendVerificationCode", cusTel);

			/**
			 * 封装返回结果
			 */
			if ("0000".equals(resMap.get("code"))) {
				CacheUtil.putMapkeyInCache(cusTel, verfiyNum, CacheUtil.CACHE_KEY.telStoreMap);
				resultMap.put("flag", "success");
				resultMap.put("msg", resMap.get("message"));
			} else {
				resultMap.put("flag", "error");
				resultMap.put("msg", resMap.get("message"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "error");
			resultMap.put("msg", e.getMessage());
		}

		return resultMap;
	}

	@RequestMapping(value = "/getRefreshDataIntegrity")
	@ResponseBody
	public  Map<String, Object> getRefreshDataIntegrity() throws Exception{
		   Map<String, Object> resultMap = new HashMap<>();
            String result=mfCusCustomerFeign.getRefreshDataIntegrity();
            if("success".equals(result)){
				resultMap.put("flag","success");
			}else{
				resultMap.put("flag","faile");
			}
			return resultMap;
	}
	@RequestMapping("/getCusListByName")
	public String getCusListByName(Model model,String cusNo,String sign) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("sign", sign);
		return "/component/cus/MfCusCustomer_ListByCusName";
	}
	@RequestMapping(value = "/getByCusName")
	@ResponseBody
	public  Map<String, Object> getByCusName(String cusNo) throws Exception{
		Map<String, Object> resultMap = new HashMap<>();
		MfCusCustomer mfCusCustomerTmp =new MfCusCustomer();
		mfCusCustomerTmp.setCusNo(cusNo);
		//当前选择的客户
		mfCusCustomerTmp =  mfCusCustomerFeign.getById(mfCusCustomerTmp);
		List<MfCusCustomer> list = new ArrayList<MfCusCustomer>();
		list.add(mfCusCustomerTmp);
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr("tablerepeatCus","tableTag",list, null,true);
		if(mfCusCustomerTmp!=null){
			//其他重名客户
			List<MfCusCustomer> cusList = cusInterfaceFeign.getByCusName(mfCusCustomerTmp.getCusName());
			if(cusList.size()>1){//有重复的
				for(int i=0;i<cusList.size();i++){
					if(mfCusCustomerTmp.getCusNo().equals(cusList.get(i).getCusNo())){
						cusList.remove(cusList.get(i));
					}
				}
				String  cusHtml = jtu.getJsonStr("tablerepeatCus","tableTag",cusList, null,true);
				resultMap.put("flag","1");
				resultMap.put("cusList",cusHtml);
				resultMap.put("customer",tableHtml);
				resultMap.put("mfCusCustomer",mfCusCustomerTmp);
			}else{
				resultMap.put("flag","2");
			}
		}
		return resultMap;
	}


	/**
	 * 方法描述：查询客户分类
	 *
	 * @return
	 * @throws Exception String
	 * @author fuchen
	 * @param cusType
	 * @date 2020-3-09 下午12:03:03
	 */
	@RequestMapping(value = "/getCusSort")
	@ResponseBody
	public Map<String, Object> getCusSort(String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
        mfCusCustomer.setCusNo(cusNo);
		try {
            mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			dataMap.put("classifyType", mfCusCustomer.getClassifyType());
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/checkCusNetwork")
	@ResponseBody
	public Map<String, Object> checkCusNetwork(String cusNo,String cusName,String nodeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("cusNo", cusNo);
			dataMap.put("cusName", cusName);
			dataMap.put("nodeNo", nodeNo);
			dataMap = mfCusCustomerFeign.checkCusNetwork(dataMap);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


	@RequestMapping(value = "/getDyFormHtmlPageAjax")
	@ResponseBody
	public Map<String, Object> getDyFormHtmlPageAjax(Integer pageNo, Integer pageSize,
											  String tableType, String ajaxData,String action,String formEditFlag,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String htmlStr = null;
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		try {
			Ipage ipage = this.getIpage();
			if(pageNo != null){
				ipage.setPageNo(pageNo);
			}
			if(pageSize != null){
				ipage.setPageSize(pageSize);
			}
			if(StringUtil.isEmpty(tableType)){
				tableType = "thirdTableTag";
			}
			//初始化客户信息时暂时没有往cusTable表中存入数据，咱是主视角的列表都写在action中
			if ("MfCusAccountDebtorAction".equals(action)) {
				MfCusAccountDebtor mfCusAccountDebtor = new MfCusAccountDebtor();
				mfCusAccountDebtor.setCusNo(cusNo);
				mfCusAccountDebtor.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusAccountDebtor",mfCusAccountDebtor));
				String tableFormId="tableAccountDebtorList";
				ipage = mfCusAccountDebtorFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusAccountDebtor>) ipage.getResult(), ipage, true);

			}else if ("MfCusAccountDetailAction".equals(action)) {
				MfCusAccountDetail mfCusAccountDetail = new MfCusAccountDetail();
				mfCusAccountDetail.setCusNo(cusNo);
				mfCusAccountDetail.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusAccountDetail",mfCusAccountDetail));
				String tableFormId="tableAccountDebtorList";
				ipage = mfCusAccountDetailFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusAccountDetail>) ipage.getResult(), ipage, true);

			}else if ("MfCusShareholderAction".equals(action)) {

				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				mfCusShareholder.setRelNo(cusNo);
				mfCusShareholder.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusShareholder",mfCusShareholder));
				String tableFormId="tablecusShareholderListBase";
				ipage = mfCusShareholderFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusShareholder>) ipage.getResult(), ipage, true);

			} else if ("MfCusHighInfoAction".equals(action)) {

				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				mfCusHighInfo.setRelNo(cusNo);
				mfCusHighInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusHighInfo",mfCusHighInfo));
				String tableFormId="tablecusExecutiveListBase";
				ipage = mfCusHighInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusHighInfo>) ipage.getResult(), ipage, true);

			} else if ("MfCusAssetsAction".equals(action)) {

				MfCusAssets mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);
				mfCusAssets.setRelNo(cusNo);
				mfCusAssets.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusAssets",mfCusAssets));
				String tableFormId="tablecusFixedAssetsListBase";
				ipage = mfCusAssetsFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusAssets>) ipage.getResult(), ipage, true);

			}  else if ("MfCusEquityInfoAction".equals(action)) {
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(cusNo);
				mfCusEquityInfo.setRelNo(cusNo);
				mfCusEquityInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusEquityInfo",mfCusEquityInfo));
				ipage = mfCusEquityInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tablecusequ00001", tableType, (List<MfCusEquityInfo>) ipage.getResult(), ipage, true);

			}  else if ("MfCusLegalMemberAction".equals(action)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(cusNo);
				mfCusLegalMember.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusLegalMember",mfCusLegalMember));
				ipage = mfCusLegalMemberFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tablecuslegm00001", tableType, (List<MfCusLegalMember>) ipage.getResult(), ipage, true);

			} else if ("MfCusBankAccManageAction".equals(action)) {
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(cusNo);
				mfCusBankAccManage.setRelNo(cusNo);
				mfCusBankAccManage.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusBankAccManage",mfCusBankAccManage));
				String tableFormId="tablecusAccountListBase";
				ipage = mfCusBankAccManageFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusBankAccManage>) ipage.getResult(), ipage, true);

			} else if ("MfCusPersonCorpAction".equals(action)) {//个人名下企业
				MfCusPersonCorp mfCusPersonCorp = new MfCusPersonCorp();
				mfCusPersonCorp.setCusNo(cusNo);
				mfCusPersonCorp.setRelNo(cusNo);
				mfCusPersonCorp.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonCorp",mfCusPersonCorp));
				String tableFormId="tablecusPersonCorpBase";
				ipage = mfCusPersonCorpFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusPersonCorp>) ipage.getResult(), ipage, true);
			}else if ("MfCusPersonLiabilitiesAction".equals(action)) {//个人资产负债表
				MfCusPersonLiabilities mfCusPersonLiabilities = new MfCusPersonLiabilities();
				mfCusPersonLiabilities.setCusNo(cusNo);
				mfCusPersonLiabilities.setRelNo(cusNo);
				mfCusPersonLiabilities.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonLiabilities",mfCusPersonLiabilities));
				String tableFormId="tablecusliabilitiesBase";
				ipage = mfCusPersonLiabilitiesFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusPersonLiabilities>) ipage.getResult(), ipage, true);
			}else if ("MfCusWarehouseAction".equals(action)) {
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				mfCusWarehouse.setCusNo(cusNo);
				mfCusWarehouse.setRelNo(cusNo);
				mfCusWarehouse.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusWarehouse",mfCusWarehouse));
				String tableFormId="tablecusWarehouseListBase";
				ipage = mfCusWarehouseFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusWarehouse>) ipage.getResult(), ipage, true);
			} else if ("MfCusBankAcceptanceBillAction".equals(action)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);
				mfCusBankAcceptanceBill.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusBankAcceptanceBill",mfCusBankAcceptanceBill));
				ipage = mfCusBankAcceptanceBillFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tablecusbankbill0001", tableType, (List<MfCusBankAcceptanceBill>) ipage.getResult(), ipage, true);

			} else if ("MfCusGuaranteeOuterAction".equals(action)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);
				mfCusGuaranteeOuter.setRelNo(cusNo);
				mfCusGuaranteeOuter.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusGuaranteeOuter",mfCusGuaranteeOuter));
				String tableFormId="tablecusGuaranteeountListBase";
				ipage = mfCusGuaranteeOuterFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusGuaranteeOuter>) ipage.getResult(), ipage, true);

			}else if ("MfCusGoodsAction".equals(action)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);
				mfCusGoods.setRelNo(cusNo);
				mfCusGoods.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusGoods",mfCusGoods));
				String tableFormId="tablecusGoodsListBase";
				ipage = mfCusGoodsFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusGoods>) ipage.getResult(), ipage, true);

			} else if ("MfCusListedInfoAction".equals(action)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);
				mfCusListedInfo.setRelNo(cusNo);
				mfCusListedInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusListedInfo",mfCusListedInfo));
				String tableFormId="tablecusListListBase";
				ipage =  mfCusListedInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusListedInfo>)ipage.getResult(), ipage, true);

			} else if ("MfCusSellInfoAction".equals(action)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				mfCusSellInfo.setCusNo(cusNo);
				mfCusSellInfo.setRelNo(cusNo);
				mfCusSellInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusSellInfo",mfCusSellInfo));
				String tableFormId="tablecusSellListBase";
				ipage = mfCusSellInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusSellInfo>) ipage.getResult(), ipage, true);
			} else if ("MfCusPersonJobAction".equals(action)) {// 职业信息
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
				mfCusPersonJob.setCusNo(cusNo);
				mfCusPersonJob.setRelNo(cusNo);
				mfCusPersonJob.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonJob",mfCusPersonJob));
				String tableFormId="tablecusJobListBase";
				ipage =  mfCusPersonJobFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusGoods>)ipage.getResult(), ipage, true);
			} else if ("MfCusFamilyInfoAction".equals(action)) {// 个人客户社会关系
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(cusNo);
				mfCusFamilyInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo));
				String tableFormId="tablecusRelationListBase";
				ipage = mfCusFamilyInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusGoods>) ipage.getResult(), ipage, true);
			}else if ("MfCusTopContactsInfoAction".equals(action)) {// 常用联系人
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				mfCusFamilyInfo.setRelNo(cusNo);
				mfCusFamilyInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusFamilyInfo",mfCusFamilyInfo));
				String tableFormId="tablecusTopContactsListBase";
				ipage = mfCusFamilyInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusGoods>) ipage.getResult(), ipage, true);
			} else if ("MfCusPersonAssetsInfoAction".equals(action)) {
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				mfCusPersonAssetsInfo.setRelNo(cusNo);
				mfCusPersonAssetsInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo",mfCusPersonAssetsInfo));
				String tableFormId="tablecusAssetsListBase";
				ipage =  mfCusPersonAssetsInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusPersonAssetsInfo>)ipage.getResult(), ipage, true);
			} else if ("MfCusPersonDebtInfoAction".equals(action)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(cusNo);
				mfCusPersonDebtInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo",mfCusPersonDebtInfo));
				String tableFormId="tablecusDebtListBase";
				ipage = mfCusPersonDebtInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusPersonDebtInfo>) ipage.getResult(), ipage, true);
			}else if ("MfCusLegalEquityInfoAction".equals(action)) {// 法人对外投资
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);
				mfCusLegalEquityInfo.setRelNo(cusNo);
				mfCusLegalEquityInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusLegalEquityInfo",mfCusLegalEquityInfo));
				String tableFormId="tablecusInvestmentListBase";
				ipage = mfCusLegalEquityInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusLegalEquityInfo>) ipage.getResult(), ipage, true);

			} else if ("MfCusLegalEmployInfoAction".equals(action)) {// 法人对外任职情况
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);
				mfCusLegalEmployInfo.setRelNo(cusNo);
				mfCusLegalEmployInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusLegalEmployInfo",mfCusLegalEmployInfo));
				String tableFormId="tablecusCorpRepServeListBase";
				ipage = mfCusLegalEmployInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusLegalEmployInfo>) ipage.getResult(), ipage, true);

			} else if ("MfCusCorpMajorChangeAction".equals(action)) {// 企业重大变更
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);
				mfCusCorpMajorChange.setRelNo(cusNo);
				mfCusCorpMajorChange.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusCorpMajorChange",mfCusCorpMajorChange));
				String tableFormId="tablecusMajorChangeListBase";
				ipage = mfCusCorpMajorChangeFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusCorpMajorChange>) ipage.getResult(), ipage, true);

			} else if ("MfCusFarmerEconoInfoAction".equals(action)) {// 农户经济情况

				MfCusFarmerEconoInfo mfCusFarmerEconoInfo = new MfCusFarmerEconoInfo();
				mfCusFarmerEconoInfo.setCusNo(cusNo);
				mfCusFarmerEconoInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusFarmerEconoInfo",mfCusFarmerEconoInfo));
				String tableFormId="tablecusEconoList";
				ipage = mfCusFarmerEconoInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusFarmerEconoInfo>)ipage.getResult(), ipage,true);

			}else if ("MfCusPersonFlowAssetsInfoAction".equals(action)) {// 流动资产
				MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo = new MfCusPersonFlowAssetsInfo();
				mfCusPersonFlowAssetsInfo.setCusNo(cusNo);
				mfCusPersonFlowAssetsInfo.setRelNo(cusNo);
				mfCusPersonFlowAssetsInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonFlowAssetsInfo",mfCusPersonFlowAssetsInfo));
				ipage = mfCusPersonFlowAssetsInfoFeign.findByPage(ipage);
				String tableFormId="tablecusflowassetsBaseListBase";
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusPersonFlowAssetsInfo>)ipage.getResult(), ipage,true);
			}else if ("MfHangZhouCusPersonDebtInfoAction".equals(action)) {//杭州微溪个人负债
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				mfCusPersonDebtInfo.setRelNo(cusNo);
				mfCusPersonDebtInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPersonDebtInfo",mfCusPersonDebtInfo));
				ipage = mfCusPersonDebtInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tableHangZhouCuspersdebt0001", tableType, (List<MfCusPersonDebtInfo>) ipage.getResult(), ipage, true);
			}else if("MfCusAssureOutsideAction".equals(action)){//外访-保证人信息
				MfCusAssureOutside mfCusAssureOutside = new MfCusAssureOutside();
				mfCusAssureOutside.setCusNo(cusNo);
				mfCusAssureOutside.setRelNo(cusNo);
				mfCusAssureOutside.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusAssureOutside",mfCusAssureOutside));
				ipage = mfCusAssureOutsideFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tablecusAssOutsideList",tableType, (List<MfCusAssureOutside>)ipage.getResult(), ipage,true);
				WebCusLineReg webCusLineReg = new WebCusLineReg();
			}else if("MfCusBranchOrganizationAction".equals(action)){//分支机构
				MfCusBranchOrganization mfCusBranchOrganization = new MfCusBranchOrganization();
				mfCusBranchOrganization.setCusNo(cusNo);
				mfCusBranchOrganization.setRelNo(cusNo);
				mfCusBranchOrganization.setDelFlag(BizPubParm.YES_NO_N);
				mfCusBranchOrganization.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusBranchOrganization",mfCusBranchOrganization));
				ipage = mfCusBranchOrganizationFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr("tablemfcusbranchorganization0001",tableType, (List<MfCusBranchOrganization>)ipage.getResult(), ipage,true);
			}else if ("MfCusExecNoticeAction".equals(action)) {//执行公告

				MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
				mfCusExecNotice.setCusNo(cusNo);
				mfCusExecNotice.setRelNo(cusNo);
				mfCusExecNotice.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusExecNotice",mfCusExecNotice));
				ipage = mfCusExecNoticeFeign.findByPage(ipage);
				String tableFormId="tablecusExecNoticeListBase";
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusExecNotice>)ipage.getResult(), ipage,true);
			}else if ("MfCusJudgmentAction".equals(action)) {//裁判文书
				MfCusJudgment mfCusJudgment = new MfCusJudgment();
				mfCusJudgment.setCusNo(cusNo);
				mfCusJudgment.setRelNo(cusNo);
				mfCusJudgment.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusJudgment",mfCusJudgment));
				String tableFormId="tablecusJudgmentListBase";
				ipage = mfCusJudgmentFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusJudgment>)ipage.getResult(), ipage,true);

			}else if ("MfCusCourtInfoAction".equals(action)) {//法院信息

				MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCusNo(cusNo);
//				mfCusCourtInfo.setRelNo(relNo);
				mfCusCourtInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusCourtInfo",mfCusCourtInfo));
				String tableFormId="tablecusCourtInfoListBase";
				ipage = mfCusCourtInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusCourtInfo>)ipage.getResult(), ipage,true);

			}else if ("MfCusDishonestInfoAction".equals(action)) {//失信公告

				MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCusNo(cusNo);
//				mfCusDishonestInfo.setRelNo(relNo);
				mfCusDishonestInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo",mfCusDishonestInfo));
				String tableFormId="tablecusDishonestInfoListBase";
				ipage = mfCusDishonestInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusDishonestInfo>)ipage.getResult(), ipage,true);

			}else if ("MfCusLogisticsInformationAction".equals(action)) {//物流信息
				MfCusLogisticsInformation mfCusLogisticsInformation = new MfCusLogisticsInformation();
				mfCusLogisticsInformation.setCusNo(cusNo);
				mfCusLogisticsInformation.setRelNo(cusNo);
				mfCusLogisticsInformation.setCustomQuery(ajaxData);
				mfCusLogisticsInformation.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusLogisticsInformation",mfCusLogisticsInformation));
				String tableFormId="tablecusLogisticsInformationList";
				ipage = mfCusLogisticsInformationFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusLogisticsInformation>)ipage.getResult(), ipage,true);
			}else if ("MfCusChattelMortgageAction".equals(action)) {//动产抵押
				MfCusChattelMortgage mfCusChattelMortgage = new MfCusChattelMortgage();
				mfCusChattelMortgage.setCusNo(cusNo);
				mfCusChattelMortgage.setRelNo(cusNo);
				mfCusChattelMortgage.setCustomQuery(ajaxData);
				mfCusChattelMortgage.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusChattelMortgage",mfCusChattelMortgage));
				String tableFormId="tableCusChattelMortgageList";
				ipage = mfCusChattelMortgageFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusChattelMortgage>)ipage.getResult(), ipage,true);

			}else if ("MfCusStaffAction".equals(action)) {//员工信息
				MfCusStaff mfCusStaff = new MfCusStaff();
				mfCusStaff.setCusNo(cusNo);
				mfCusStaff.setRelNo(cusNo);
				mfCusStaff.setCustomQuery(ajaxData);
				mfCusStaff.setDelFlag(BizPubParm.YES_NO_N);
				ipage.setParams(this.setIpageParams("mfCusStaff",mfCusStaff));
				String tableFormId="tablecusStaffList";
				ipage = mfCusStaffFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusStaff>)ipage.getResult(), ipage,true);
			}else if("MfCusBorrowerInfoAction".equals(action)){//共同借款人信息
				MfCusBorrowerInfo mfCusBorrowerInfo = new MfCusBorrowerInfo();
				mfCusBorrowerInfo.setCusNo(cusNo);
				mfCusBorrowerInfo.setRelNo(cusNo);
				mfCusBorrowerInfo.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusBorrowerInfo",mfCusBorrowerInfo));
				String tableFormId="tablecusBorrowerList";
				ipage = mfCusBorrowerInfoFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusBorrowerInfo>)ipage.getResult(), ipage,true);
			}else if("MfCusCorpLoanAction".equals(action)){//对外融资
				MfCusCorpLoan mfCusCorpLoan = new MfCusCorpLoan();
				mfCusCorpLoan.setCusNo(cusNo);
				//mfCusCorpLoan.setRelNo(relNo);
				mfCusCorpLoan.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusCorpLoan",mfCusCorpLoan));
				String tableFormId="tablecusCorpLoanListBase";
				ipage = mfCusCorpLoanFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusCorpLoan>)ipage.getResult(), ipage,true);
			}else if("MfCusShedAction".equals(action)){//棚舍信息
				MfCusShed mfCusShed = new MfCusShed();
				mfCusShed.setCusNo(cusNo);
				mfCusShed.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusShed",mfCusShed));
				String tableFormId="tablecusShedList";
				ipage = mfCusShedFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusShed>)ipage.getResult(), ipage,true);

			}else if("MfCusSaleProductAction".equals(action)){//销售产品
				MfCusSaleProduct mfCusSaleProduct = new MfCusSaleProduct();
				mfCusSaleProduct.setCusNo(cusNo);
				mfCusSaleProduct.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusSaleProduct",mfCusSaleProduct));
				String tableFormId="tablecussaleproductBase";
				ipage= mfCusSaleProductFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusSaleProduct>)ipage.getResult(), ipage,true);
			}else if("MfCusPlantBreedAction".equals(action)){//种植养殖信息
				MfCusPlantBreed mfCusPlantBreed = new MfCusPlantBreed();
				mfCusPlantBreed.setCusNo(cusNo);
				mfCusPlantBreed.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusPlantBreed",mfCusPlantBreed));
				String tableFormId="tablecusplantbreedBase";
				ipage = mfCusPlantBreedFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusPlantBreed>)ipage.getResult(), ipage,true);
			}else if("MfCusBusServiceAction".equals(action)){//商业服务业信息
				MfCusBusService mfCusBusService = new MfCusBusService();
				mfCusBusService.setCusNo(cusNo);
				mfCusBusService.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusBusService",mfCusBusService));
				String tableFormId="tablecuscusbusserviceBase";
				ipage = mfCusBusServiceFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusBusService>)ipage.getResult(), ipage,true);
			}else if("MfCusProfitLossAction".equals(action)) {//个人损益表
				MfCusProfitLoss mfCusProfitLoss = new MfCusProfitLoss();
				mfCusProfitLoss.setCusNo(cusNo);
				mfCusProfitLoss.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusProfitLoss", mfCusProfitLoss));
				String tableFormId = "tablecusprofitlossBase";
				ipage = mfCusProfitLossFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusProfitLoss>) ipage.getResult(), ipage, true);
			} else if ("MfCusDesignatedRecipientAction".equals(action)) {// 指定收件人
				MfCusDesignatedRecipient recipient = new MfCusDesignatedRecipient();
				recipient.setRelNo(cusNo);
				recipient.setIsDelete("0");
				recipient.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusDesignatedRecipient", recipient));
				String tableFormId = "tablerecipientListBase";
				ipage = mfCusDesignatedRecipientFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId, tableType, (List<MfCusDesignatedRecipient>) ipage.getResult(), ipage, true);
			}else if("MfCusSharePledgeAction".equals(action)){//股权质押信息
				MfCusSharePledge mfCusSharePledge = new MfCusSharePledge();
				mfCusSharePledge.setCusNo(cusNo);
				mfCusSharePledge.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusSharePledge", mfCusSharePledge));
				String tableFormId="tablecusSharePledgeListBase";
				ipage = mfCusSharePledgeFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusSharePledge>)ipage.getResult(), ipage,true);

			}else if (action.equals("MfCusMeManageAction")) {//成员单位
				MfCusMeManage mfCusMeManage = new MfCusMeManage();
				mfCusMeManage.setCusNo(cusNo);
				mfCusMeManage.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusMeManage", mfCusMeManage));
				String tableFormId="tablemfCusMeManage";
				ipage = mfCusMeManageFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusMeManage>)ipage.getResult(), ipage,true);

			}else if (action.equals("MfCusInvoiceMationAction")) {//开票信息
				MfCusInvoiceMation mfCusInvoiceMation = new MfCusInvoiceMation();
				mfCusInvoiceMation.setCusNo(cusNo);
				mfCusInvoiceMation.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusInvoiceMation", mfCusInvoiceMation));
				String tableFormId="tablemfCusInvoiceMation";
				ipage = mfCusInvoiceMationFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType, (List<MfCusInvoiceMation>)ipage.getResult(), ipage,true);
			}else if (action.equals("MfCusIntangibleAssetsAction")) {//无形资产
				MfCusIntangibleAssets mfCusIntangibleAssets = new MfCusIntangibleAssets();
				mfCusIntangibleAssets.setCusNo(cusNo);
				mfCusIntangibleAssets.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusIntangibleAssets", mfCusIntangibleAssets));
				String tableFormId="tablecusIntangibleAssetsBaseList";
				ipage = mfCusIntangibleAssetsFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType,(List<MfCusIntangibleAssets>)ipage.getResult(), ipage,true);
			}else if (action.equals("MfCusContractAction")) {//合同订单
				MfCusContract mfCusContract = new MfCusContract();
				mfCusContract.setCusNo(cusNo);
				mfCusContract.setCustomQuery(ajaxData);
				ipage.setParams(this.setIpageParams("mfCusContract", mfCusContract));
				String tableFormId="tablecusContractBaseList";
				ipage = mfCusContractFeign.findByPage(ipage);
				htmlStr = jtu.getJsonStr(tableFormId,tableType,(List<MfCusContract>)ipage.getResult(), ipage,true);
			}

			ipage.setResult(htmlStr);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
			dataMap.put("htmlStr", htmlStr);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getCusBlockList")
	public String getCusBlockList (Model model,String action,String formEditFlag,String cusNo,String title){
		model.addAttribute("action", action);
		model.addAttribute("formEditFlag", formEditFlag);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("title", title);
		return "component/cus/MfCusCustomerMore_List";
	}
}
