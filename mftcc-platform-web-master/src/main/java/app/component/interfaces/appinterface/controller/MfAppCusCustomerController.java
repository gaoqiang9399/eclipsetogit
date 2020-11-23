package app.component.interfaces.appinterface.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.pact.entity.MfBusFincApp;
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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.api.task.Task;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.bizinterface.BizInterfaceFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusAssets;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusBankAcceptanceBill;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCorpMajorChange;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusGoods;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.component.cus.entity.MfCusHighInfo;
import app.component.cus.entity.MfCusLegalEmployInfo;
import app.component.cus.entity.MfCusLegalEquityInfo;
import app.component.cus.entity.MfCusLegalMember;
import app.component.cus.entity.MfCusListedInfo;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.component.cus.entity.MfCusPersonCreditInfo;
import app.component.cus.entity.MfCusPersonDebtInfo;
import app.component.cus.entity.MfCusPersonIncExpe;
import app.component.cus.entity.MfCusPersonJob;
import app.component.cus.entity.MfCusSellInfo;
import app.component.cus.entity.MfCusShareholder;
import app.component.cus.entity.MfCusStaff;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusWarehouse;
import app.component.cus.relation.entity.Child;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.evalinterface.EvalInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONObject;

/**
 * 客户信息管理的Action类
 * 
 * @author zhang_dlei
 * @date 2017-06-15 下午5:58:30
 */
@Controller
@RequestMapping("/mfAppCusCustomer")
public class MfAppCusCustomerController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign workflowDwrFeign;

	// 财务报表对外接口
	// 抵质押品信息
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private EvalInterfaceFeign evalInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;

	// 之后删除
	// 表单配置
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private BizInterfaceFeign bizInterfaceFeign;

	/**
	 * 客户进件界面 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		List<ParmDic> parmList = (List<ParmDic>) codeUtils.getCacheByKeyName("CUS_TYPE");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (cusType == null) {
			if (parmList.size() > 0) {
				cusType = parmList.get(0).getOptCode();
			} else {
				cusType = "100";
			}
		}
		FormData formcus00002 = null;
		if (cusType.startsWith("2")) {
			// 个人客户新增表单
			formcus00002 = formService.getFormData("personCus00002");
		} else {
			// 企业客户新增表单
			formcus00002 = formService.getFormData("cus00002");
		}
		FormActive[] formActives = formcus00002.getFormActives();
		for (int i = 0; i < formActives.length; i++) {
			String parmKey = formActives[i].getFieldSize();// 字典项
			if (!StringUtil.isBlank(parmKey)) {
				List<ParmDic> pList = (List<ParmDic>) codeUtils.getCacheByKeyName(parmKey);
				formActives[i].setOptionArray(pList);
			}
		}
		this.changeFormProperty(formcus00002, "cusType", "initValue", cusType);// 重新给客户类型赋值
		String formcus00002Json = new Gson().toJson(formcus00002);
		// mfCusCustomer = new MfCusCustomer();
		// mfCusCustomer.setCusType(cusType);
		// getObjValue(formcus00002, mfCusCustomer);
		// JSONObject jb = JSONObject.fromObject(dataMap);
		// dataMap = jb;
		model.addAttribute("formcus00002Json", formcus00002Json);
		model.addAttribute("formcus00002", formcus00002);
		model.addAttribute("query", "");
		return "/component/interfaces/appinterface/MfAppCusCustomer_Insert";
	}

	/**
	 * 
	 * 方法描述： 获得客户详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author MaHao
	 * @date 2017-07-30 上午14:18:07
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getById")
	public String getById(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// String opType = request.getParameter("opType"); // 表示是授信业务请求

		// formcusuploadhead0001 = formService.getFormData("cusuploadhead0001");
		String cusCompleteFlag = "0";// 客户是否完善信息标识根据表MfCusTable.dataFullFlag有数据返回
		// 职业信息等级标志
		String jobFlag = "0";
		String societyFlag = "0";
		String assetFlag = "0";
		// 查询已经录入信息的表单
		List<MfCusTable> cusTableList = cusInterfaceFeign.getMfCusTableList1(cusNo);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			if ("0".equals(cusTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = cusTableList.get(i).getAction();
			String htmlStr = "";
			if ("MfCusCorpBaseInfoAction".equals(action)) {
				cusCompleteFlag = cusTableList.get(i).getDataFullFlag();
				MfCusCorpBaseInfo mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
				Child child = cusInterfaceFeign.getLoanUseById(mfCusCorpBaseInfo);
				if (child != null) {
					mfCusCorpBaseInfo.setWayClassName(child.getName());
				}

			} else if ("MfCusPersBaseInfoAction".equals(action)) {
				cusCompleteFlag = cusTableList.get(i).getDataFullFlag();
				MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
				mfCusPersBaseInfo.setCusNo(cusNo);
				mfCusPersBaseInfo = cusInterfaceFeign.getByCusNo(mfCusPersBaseInfo);

			} else if ("MfCusShareholderAction".equals(action)) {
				String shareholderFlag = cusTableList.get(i).getDataFullFlag();
				MfCusShareholder mfCusShareholder = new MfCusShareholder();
				mfCusShareholder.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				List<MfCusShareholder> mfCusShareholderList = cusInterfaceFeign
						.findMfCusShareholderListByCusNo(mfCusShareholder);

				model.addAttribute("shareholderFlag", shareholderFlag);
				model.addAttribute("mfCusShareholderList", mfCusShareholderList);
			} else if ("MfCusHighInfoAction".equals(action)) {// 高管信息
				String hightManagerFlag = cusTableList.get(i).getDataFullFlag();
				MfCusHighInfo mfCusHighInfo = new MfCusHighInfo();
				mfCusHighInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				List<MfCusHighInfo> mfCusHighInfoList = cusInterfaceFeign.findMfCusHighInfoListByCusNo(mfCusHighInfo);
				model.addAttribute("hightManagerFlag", hightManagerFlag);
				model.addAttribute("mfCusHighInfoList", mfCusHighInfoList);
			} else if ("MfCusAssetsAction".equals(action)) {
				MfCusAssets mfCusAssets = new MfCusAssets();
				mfCusAssets.setCusNo(cusNo);

			} else if ("MfCusEquityInfoAction".equals(action)) {// 对外投资
				String equityFlag = cusTableList.get(i).getDataFullFlag();
				MfCusEquityInfo mfCusEquityInfo = new MfCusEquityInfo();
				mfCusEquityInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				List<MfCusEquityInfo> cusEquityInfoList = cusInterfaceFeign
						.findMfCusEquityInfoListByCusNo(mfCusEquityInfo);
				model.addAttribute("equityFlag", equityFlag);
				model.addAttribute("cusEquityInfoList", cusEquityInfoList);
			} else if ("MfCusStaffAction".equals(action)) {
				MfCusStaff mfCusStaff = new MfCusStaff();
				mfCusStaff.setCusNo(cusNo);
			} else if ("MfCusLegalMemberAction".equals(action)) {
				MfCusLegalMember mfCusLegalMember = new MfCusLegalMember();
				mfCusLegalMember.setCusNo(cusNo);

			} else if ("MfCusBankAccManageAction".equals(action)) {
				String bankAccFlag = cusTableList.get(i).getDataFullFlag();
				MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
				mfCusBankAccManage.setCusNo(cusNo);
				List<MfCusBankAccManage> bankAccManageList = cusInterfaceFeign
						.getMfCusBankAccListByCusNo(mfCusBankAccManage);
				model.addAttribute("bankAccFlag", bankAccFlag);
				model.addAttribute("bankAccManageList", bankAccManageList);
			} else if ("MfCusWarehouseAction".equals(action)) {
				MfCusWarehouse mfCusWarehouse = new MfCusWarehouse();
				mfCusWarehouse.setCusNo(cusNo);
			} else if ("MfCusBankAcceptanceBillAction".equals(action)) {
				MfCusBankAcceptanceBill mfCusBankAcceptanceBill = new MfCusBankAcceptanceBill();
				mfCusBankAcceptanceBill.setCusNo(cusNo);

			} else if ("MfCusGuaranteeOuterAction".equals(action)) {
				MfCusGuaranteeOuter mfCusGuaranteeOuter = new MfCusGuaranteeOuter();
				mfCusGuaranteeOuter.setCusNo(cusNo);

			} else if ("MfCusGoodsAction".equals(action)) {
				MfCusGoods mfCusGoods = new MfCusGoods();
				mfCusGoods.setCusNo(cusNo);

			} else if ("MfCusListedInfoAction".equals(action)) {
				MfCusListedInfo mfCusListedInfo = new MfCusListedInfo();
				mfCusListedInfo.setCusNo(cusNo);

			} else if ("MfCusSellInfoAction".equals(action)) {
				MfCusSellInfo mfCusSellInfo = new MfCusSellInfo();
				mfCusSellInfo.setCusNo(cusNo);
			} else if ("MfCusPersonJobAction".equals(action)) {// 职业信息
				jobFlag = cusTableList.get(i).getDataFullFlag();
				MfCusPersonJob mfCusPersonJob = new MfCusPersonJob();
			} else if ("MfCusFamilyInfoAction".equals(action)) {// 个人客户社会关系
				societyFlag = cusTableList.get(i).getDataFullFlag();
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setCusNo(cusNo);
				List<MfCusFamilyInfo> mfCusFamilyInfoList = cusInterfaceFeign.getFamilyList(mfCusFamilyInfo);
				model.addAttribute("mfCusFamilyInfoList", mfCusFamilyInfoList);
			} else if ("MfCusPersonAssetsInfoAction".equals(action)) {// 资产信息
				assetFlag = cusTableList.get(i).getDataFullFlag();
				MfCusPersonAssetsInfo mfCusPersonAssetsInfo = new MfCusPersonAssetsInfo();
				mfCusPersonAssetsInfo.setCusNo(cusNo);
				Ipage ipage = this.getIpage();
				ipage.setParams(this.setIpageParams("mfCusPersonAssetsInfo", mfCusPersonAssetsInfo));
				List<MfCusPersonAssetsInfo> mfCusPersonAssetsInfoList = (List<MfCusPersonAssetsInfo>) cusInterfaceFeign
						.findMfCusPersonAssetsInfoByPage(ipage).getResult();
				model.addAttribute("mfCusPersonAssetsInfoList", mfCusPersonAssetsInfoList);
			} else if ("MfCusPersonDebtInfoAction".equals(action)) {
				MfCusPersonDebtInfo mfCusPersonDebtInfo = new MfCusPersonDebtInfo();
				mfCusPersonDebtInfo.setCusNo(cusNo);
				// Ipage ipage = this.getIpage();
				// htmlStr = jtu.getJsonStr("tablecuspersdebt0001", "tableTag",
				// (List<MfCusPersonDebtInfo>)
				// mfCusPersonDebtInfoFeign.findByPage(ipage,
				// mfCusPersonDebtInfo).getResult(), null, true);
			} else if ("MfCusPersonCreditInfoAction".equals(action)) {// 个人客户信用情况
				MfCusPersonCreditInfo mfCusPersonCreditInfo = new MfCusPersonCreditInfo();
				mfCusPersonCreditInfo.setCusNo(cusNo);
			} else if ("MfCusPersonIncExpeAction".equals(action)) {// 收支情况
				MfCusPersonIncExpe mfCusPersonIncExpe = new MfCusPersonIncExpe();
				mfCusPersonIncExpe.setCusNo(cusNo);
			} else if ("MfCusLegalEquityInfoAction".equals(action)) {// 法人对外投资
				MfCusLegalEquityInfo mfCusLegalEquityInfo = new MfCusLegalEquityInfo();
				mfCusLegalEquityInfo.setCusNo(cusNo);

			} else if ("MfCusLegalEmployInfoAction".equals(action)) {// 法人对外任职情况
				MfCusLegalEmployInfo mfCusLegalEmployInfo = new MfCusLegalEmployInfo();
				mfCusLegalEmployInfo.setCusNo(cusNo);

			} else if ("MfCusCorpMajorChangeAction".equals(action)) {// 企业重大变更
				MfCusCorpMajorChange mfCusCorpMajorChange = new MfCusCorpMajorChange();
				mfCusCorpMajorChange.setCusNo(cusNo);

			}else {
			}
			cusTableList.get(i).setHtmlStr(htmlStr);
		}
		// LJW 获取wkfAppId 20170301 授信相关 钉 不需要
		Map<String, Object> dataMap = new HashMap<String, Object>();

		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String cusBaseType = mfCusCustomer.getCusBaseType();
		String cusBaseFlag = "";
		String gradeModel = "";
		String gradeModelUseFlag = "";
		String gradeModelName = "";
		// 客户基本信息
		if ("2".equals(mfCusCustomer.getCusBaseType())) {// 个人客户 获取个人客户基本信息
			MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
			mfCusPersBaseInfo.setCusNo(cusNo);
			mfCusPersBaseInfo = cusInterfaceFeign.getByCusNo(mfCusPersBaseInfo);
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
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
				mfCusCustomer.setContactsTel(mfCusCustomer.getCusTel());
			}

		} else {// 企业客户 获取企业客户基本信息
			MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfo.setCusNo(cusNo);
			MfCusCorpBaseInfo mfCusCorpBaseInfoTmp = new MfCusCorpBaseInfo();
			mfCusCorpBaseInfoTmp = cusInterfaceFeign.getCusCorpByCusNo(cusNo);
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
				// 获取邮政编码
				mfCusCustomer.setPostalCode(mfCusCorpBaseInfoTmp.getPostalCode());
				// request.setAttribute("postalCode",
				// mfCusCorpBaseInfoTmp.getPostalCode());
			}
		}
		model.addAttribute("cusBaseType", cusBaseType);
		model.addAttribute("cusBaseFlag", cusBaseFlag);
		model.addAttribute("gradeModel", gradeModel);
		model.addAttribute("gradeModelUseFlag", gradeModelUseFlag);
		model.addAttribute("gradeModelName", gradeModelName);
		// 申请信息
		// 转化客户类型信息
		CodeUtils cu = new CodeUtils();
		Map<String, String> cusTypeMap = cu.getMapByKeyName("CUS_TYPE");
		String cusTypeName = cusTypeMap.get(mfCusCustomer.getCusType());
		model.addAttribute("cusTypeName", cusTypeName);
		String query = "cus";
		// 获取产品号 默认取线下
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setKindProperty("2");
		mfSysKind.setUseFlag("1");
		mfSysKind.setCusType(mfCusCustomer.getCusType());
		mfSysKind.setBrNo(User.getOrgNo(request));
		mfSysKind.setRoleNoArray(User.getRoleNo(request));
		List<MfSysKind> mfSysKindList = prdctInterfaceFeign.getSysKindList(mfSysKind);
		if (mfSysKindList != null && mfSysKindList.size() > 0) {
			String firstKindNo = mfSysKindList.get(0).getKindNo();
			model.addAttribute("firstKindNo", firstKindNo);
		}

		// 获取该客户下所有贷款余额 和 本期账单
		String allLoanbal = pactInterfaceFeign.getAllLoanbalByCusNo(cusNo);
		String curTermYingShouAmt = calcRepaymentInterfaceFeign.getCurTermYingShouAmtByCusNo(cusNo);
		model.addAttribute("allLoanbal", allLoanbal);
		model.addAttribute("curTermYingShouAmt", curTermYingShouAmt);
		model.addAttribute("query", query);
		return "/component/interfaces/appinterface/MfAppCusCustomer_Detail";
	}

	/**
	 * 
	 * 方法描述： 客户登记新增（进件）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author mahao
	 * @date 2017-7-30 下午2:46:24
	 */
	@RequestMapping(value = "/insertForBusAjax")
	@ResponseBody
	public Map<String, Object> insertForBusAjax(String ajaxData,String cusNo, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String cusType = String.valueOf(getMapByJson(ajaxData).get("cusType"));
			String busFlag = "0";
			String cusBaseType = cusType.substring(0, 1);
			FormData formcuscorpbase0001 = null;
			if (appId == null) {
				if ("2".equals(cusBaseType)) {
					formcuscorpbase0001 = formService.getFormData("personCus00002");
				} else {
					formcuscorpbase0001 = formService.getFormData("cus00002");
				}
			} else {
				formcuscorpbase0001 = formService.getFormData("cus00005");
				busFlag = "1";
			}
			getFormValue(formcuscorpbase0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcuscorpbase0001)) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				setObjValue(formcuscorpbase0001, mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				cusType = mfCusCustomer.getCusType();
				if (busFlag == "1") {// 增加资金机构
					cusNo = mfCusCustomer.getCusNo();
					mfCusCustomer.setCusNo(cusNo);
					mfCusCustomer = cusInterfaceFeign.insert(mfCusCustomer);
				} else {// 进件新增客户
					if (("2".equals(cusBaseType))) {// 进件新增个人客户
						MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
						setObjValue(formcuscorpbase0001, mfCusPersBaseInfo);
						mfCusCustomer.setCusNo(cusNo);
						mfCusCustomer.setContactsName(mfCusPersBaseInfo.getCusName());
						mfCusCustomer.setContactsTel(mfCusPersBaseInfo.getCusTel());
						mfCusCustomer = cusInterfaceFeign.insert(mfCusCustomer);

						mfCusPersBaseInfo.setCusNo(mfCusCustomer.getCusNo());
						cusInterfaceFeign.insertMfCusPersBaseInfo(mfCusPersBaseInfo);
					} else { // 进件新增企业客户
						mfCusCustomer.setCusNo(cusNo);
						mfCusCustomer.setIdType("B");// 表单录入的是社会信用代码证号，所以类型这里默认为B
						mfCusCustomer = cusInterfaceFeign.insert(mfCusCustomer);
					}
				}

				if (appId == null) {// 进件
					if ("2".equals(cusBaseType)) {
					} else {
						if (StringUtil.isEmpty(mfCusCustomer.getExt2())) {// 新增客户未进行联网核查
																			// 新增基本信息
							MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
							setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
							mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
							cusInterfaceFeign.insertMfCusCorpBaseInfo(mfCusCorpBaseInfo);
							MfCusTable mfCusTable = new MfCusTable();
							mfCusTable.setCusNo(mfCusCorpBaseInfo.getCusNo());
							mfCusTable.setTableName("mf_cus_corp_base_info");
							mfCusTable.setDataFullFlag("0");
							cusInterfaceFeign.updateCusTable(mfCusTable);
						} else {// 新增客户 已进行联网核查客户 更新基本信息表
							MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
							mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
							mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(mfCusCustomer.getCusNo());
							// mfCusCorpBaseInfo =
							// mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
							setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
							mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
							cusInterfaceFeign.updateMfCusCorpBaseInfo(mfCusCorpBaseInfo);
						}
					}

				} else if (StringUtil.isEmpty(cusNo)) {// 资金机构不是选的已有客户
					if (StringUtil.isEmpty(mfCusCustomer.getExt2())) {// 新增客户未进行联网核查
																		// 新增基本信息
						MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
						setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
						mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
						cusInterfaceFeign.insertMfCusCorpBaseInfo(mfCusCorpBaseInfo);
						MfCusTable mfCusTable = new MfCusTable();
						mfCusTable.setCusNo(mfCusCorpBaseInfo.getCusNo());
						mfCusTable.setTableName("mf_cus_corp_base_info");
						mfCusTable.setDataFullFlag("0");
						cusInterfaceFeign.updateCusTable(mfCusTable);
					} else {// 新增客户 已进行联网核查客户 更新基本信息表
						MfCusCorpBaseInfo mfCusCorpBaseInfo = new MfCusCorpBaseInfo();
						mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
						mfCusCorpBaseInfo = cusInterfaceFeign.getCusCorpByCusNo(mfCusCustomer.getCusNo());
						// mfCusCorpBaseInfo =
						// mfCusCorpBaseInfoFeign.getById(mfCusCorpBaseInfo);
						setObjValue(formcuscorpbase0001, mfCusCorpBaseInfo);
						mfCusCorpBaseInfo.setCusNo(mfCusCustomer.getCusNo());
						cusInterfaceFeign.updateMfCusCorpBaseInfo(mfCusCorpBaseInfo);
					}
				} else {
				}

				if (busFlag == "1" && StringUtil.isEmpty(cusNo)) {

					MfBusApply mfBusApply = new MfBusApply();
					mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
					Task task1 = wkfInterfaceFeign.getTask(mfBusApply.getWkfAppId(), null);
					String title = task1.getDescription();
					Map<String, String> map = new HashMap<String, String>();
					map.put("cusNo", mfBusApply.getCusNo());
					List<String> infoList = new ArrayList<String>();
					infoList.add("新增" + title + ",企业名称：" + mfCusCustomer.getCusName());
					bizInterfaceFeign.insertInfo(BizPubParm.CHANGE_TYPE_INFO, title, map, BizPubParm.BIZ_TYPE_APP,
							infoList);

				}

				if (busFlag == "1") {
					cusType = mfCusCustomer.getCusType();
					cusNo = mfCusCustomer.getCusNo();
					MfBusApply mfBusApply = new MfBusApply();
					mfBusApply.setAppId(appId);

					MfBusApply mfBusApply1 = appInterfaceFeign.getMfBusApplyByAppId(appId);
					// 更新申请表中参与方的信息，如果已经生成合同，则更新合同中的参与方的信息
					if (mfBusApply1 != null) {
						MfBusPact mfBusPact = new MfBusPact();
						if (StringUtil.isNotEmpty(mfBusApply1.getPactId())) {
							mfBusPact.setPactId(mfBusApply1.getPactId());
						}

						// if(cusType.equals("103")){//仓储方
						// mfBusApply.setCusNoWarehouse(cusNo);
						// mfBusApply.setCusNameWarehouse(mfCusCustomer.getCusName());
						//
						// if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){
						// mfBusPact.setCusNoWarehouse(cusNo);
						// mfBusPact.setCusNameWarehouse(mfCusCustomer.getCusName());
						// }
						// }else
						// if(cusType.equals("101")||cusType.equals("108")||cusType.equals("105")){//核心企业/保理买方
						mfBusApply.setCusNoCore(cusNo);
						mfBusApply.setCusNameCore(mfCusCustomer.getCusName());

						if (StringUtil.isNotEmpty(mfBusApply1.getPactId())) {
							mfBusPact.setCusNoCore(cusNo);
							mfBusPact.setCusNameCore(mfCusCustomer.getCusName());
						}
						// }else if(cusType.equals("109")){
						// mfBusApply.setCusNoFund(cusNo);
						// mfBusApply.setCusNameFund(mfCusCustomer.getCusName());
						//
						// if(StringUtil.isNotEmpty(mfBusApply1.getPactId()) ){
						// mfBusPact.setCusNoFund(cusNo);
						// mfBusPact.setCusNameFund(mfCusCustomer.getCusName());
						// }
						// }
						// 业务进入下一个流程
						TaskImpl task = wkfInterfaceFeign.getTask(mfBusApply1.getWkfAppId(), null);
						// String url =
						// "1#MfBusFincAppAction_getFincApp.action?fincId="+mfBusFincApp.getFincId();
						// TaskImpl task1 = new TaskImpl();
						String url = "";
						
						String transition = workflowDwrFeign.findNextTransition(task.getId());
						wkfInterfaceFeign.doCommit(task.getId(), AppConstant.OPINION_TYPE_ARREE, url, transition,
								User.getRegNo(request), "");

						appInterfaceFeign.updateApply(mfBusApply);
						if (StringUtil.isNotEmpty(mfBusApply1.getPactId())) {
							pactInterfaceFeign.updatePact(mfBusPact);
						}
					}

				}

				cusNo = mfCusCustomer.getCusNo();
				dataMap.put("cusNo", cusNo);
				dataMap.put("cusType", cusType);
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
	 * double类型转字符串
	 */
	@Override
	@RequestMapping(value = "/formatDouble")
	public String formatDouble(double s) {
		DecimalFormat fmt = new DecimalFormat("##0.00");
		return fmt.format(s);
	}

	/**
	 * 格式化金额为万元
	 * 
	 * @param amt
	 * @return
	 * @author MaHao
	 * @date 2017-8-30 下午5:39:55
	 */
	@RequestMapping(value = "/formatAmtWan")
	public String formatAmtWan(String amt) {
		return MathExtend.divide(amt, "10000");
	}

	/**
	 * yyyyMMdd 格式化为 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @author MaHao
	 * @date 2017-8-24 下午7:05:29
	 */
	@RequestMapping(value = "/formatDate")
	public String formatDate(String date) {
		return DateUtil.getShowDateTime(date);
	}

	/**
	 * 获取展示出资方式
	 * 
	 * @param pushCapitalType
	 * @return
	 * @author MaHao
	 * @date 2017-8-26 下午4:12:28
	 */
	@ResponseBody
	@RequestMapping(value = "/getPushCapitalType")
	public String getPushCapitalType( String pushCapitalType) {
		String result = "";
		if (StringUtil.isNotBlank(pushCapitalType)) {
			Map<String, ParmDic> pushCapitalTypeMap;
			try {
				pushCapitalTypeMap = new CodeUtils().getMapObjByKeyName("PUSH_CAPITAL_TYPE");
				String[] pushCapitalTypeArray = pushCapitalType.split("\\|");
				if (null != pushCapitalTypeArray && pushCapitalTypeArray.length > 0) {
					for (int i = 0; i < pushCapitalTypeArray.length; i++) {
						String vouItem = pushCapitalTypeArray[i];
						if (StringUtil.isNotBlank(vouItem)) {
							String vouItemStr = pushCapitalTypeMap.get(vouItem).getOptName();
							result = result + vouItemStr + "|";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
//				logger.error("钉钉展示股东信息，从数据字典缓存中获取股东出资方式字符串出错", e);
			}
		}
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	@RequestMapping(value = "/goCusCustomerCompleteInfo")
	public String goCusCustomerCompleteInfo(Model model) throws Exception {
		return "/component/interfaces/appinterface/DingCusCustomer_CompleteInfo";
	}

}
