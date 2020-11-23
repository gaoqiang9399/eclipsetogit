package app.component.auth.controller;

import app.base.User;
import app.component.app.makepolicymeeting.entity.MfReviewMember;
import app.component.app.makepolicymeeting.feign.MfReviewMemberFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assure.feign.MfAssureInfoFeign;
import app.component.auth.entity.*;
import app.component.auth.feign.*;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.common.ApplyEnum;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.prdct.entity.MfKindForm;
import app.component.prdct.entity.MfSysKind;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import config.YmlConfig;
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
 * Title: MfCusCreditApproveInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 26 09:56:51 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditApproveInfo")
public class MfCusCreditApproveInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfCusCreditApproveInfoBo
	@Autowired
	private MfCusCreditApproveInfoFeign mfCusCreditApproveInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private MfCusPorductCreditFeign mfCusPorductCreditFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfCusCreditAdjustApplyFeign mfCusCreditAdjustApplyFeign;
	@Autowired
	private MfCusCreditApplyHisFeign mfCusCreditApplyHisFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;

	@Autowired
	private MfCusCreditContractFeign mfCusCreditContractFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfCusCreditConfigFeign mfCusCreditConfigFeign;
	@Autowired
	private MfReviewMemberFeign mfReviewMemberFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfAssureInfoFeign mfAssureInfoFeign;
	@Autowired
	private SysUserFeign sysUserFeign;

	@Autowired
	private MfBusAgenciesPledgeRelFeign mfBusAgenciesPledgeRelFeign;


	// 全局变量
	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/auth/MfCusCreditApproveInfo_List";
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
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		try {
			mfCusCreditApproveInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusCreditApproveInfo.setCriteriaList(mfCusCreditApproveInfo, ajaxData);// 我的筛选
			// mfCusCreditApproveInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfCusCreditApproveInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfCusCreditApproveInfoFeign.findByPage(ipage, mfCusCreditApproveInfo);
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
			getFormValue(formcreditapprinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditapprinfo0002)) {
				MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
				setObjValue(formcreditapprinfo0002, mfCusCreditApproveInfo);
				mfCusCreditApproveInfoFeign.insert(mfCusCreditApproveInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		getFormValue(formcreditapprinfo0002, getMapByJson(ajaxData));
		MfCusCreditApproveInfo mfCusCreditApproveInfoJsp = new MfCusCreditApproveInfo();
		setObjValue(formcreditapprinfo0002, mfCusCreditApproveInfoJsp);
		MfCusCreditApproveInfo mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfoJsp);
		if (mfCusCreditApproveInfo != null) {
			try {
				mfCusCreditApproveInfo = (MfCusCreditApproveInfo) EntityUtil.reflectionSetVal(mfCusCreditApproveInfo,
						mfCusCreditApproveInfoJsp, getMapByJson(ajaxData));
				mfCusCreditApproveInfoFeign.update(mfCusCreditApproveInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		try {
			FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
			getFormValue(formcreditapprinfo0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditapprinfo0002)) {
				mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
				setObjValue(formcreditapprinfo0002, mfCusCreditApproveInfo);
				mfCusCreditApproveInfoFeign.update(mfCusCreditApproveInfo);
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
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param creditApproveId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String creditApproveId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		mfCusCreditApproveInfo.setCreditApproveId(creditApproveId);
		mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfo);
		getObjValue(formcreditapprinfo0002, mfCusCreditApproveInfo, formData);
		if (mfCusCreditApproveInfo != null) {
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
	 * @param creditApproveId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String creditApproveId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		mfCusCreditApproveInfo.setCreditApproveId(creditApproveId);
		try {
			mfCusCreditApproveInfoFeign.delete(mfCusCreditApproveInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		model.addAttribute("formcreditapprinfo0002", formcreditapprinfo0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditApproveInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		getFormValue(formcreditapprinfo0002);
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		setObjValue(formcreditapprinfo0002, mfCusCreditApproveInfo);
		mfCusCreditApproveInfoFeign.insert(mfCusCreditApproveInfo);
		getObjValue(formcreditapprinfo0002, mfCusCreditApproveInfo);
		this.addActionMessage(model, "保存成功");
		List<MfCusCreditApproveInfo> mfCusCreditApproveInfoList = (List<MfCusCreditApproveInfo>) mfCusCreditApproveInfoFeign
				.findByPage(this.getIpage(), mfCusCreditApproveInfo).getResult();
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditApproveInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param creditApproveId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String creditApproveId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditapprinfo0001 = formService.getFormData("creditapprinfo0001");
		getFormValue(formcreditapprinfo0001);
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		mfCusCreditApproveInfo.setCreditApproveId(creditApproveId);
		mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfo);
		getObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0001);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditApproveInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param creditApproveId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String creditApproveId) throws Exception {
		ActionContext.initialize(request, response);
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		mfCusCreditApproveInfo.setCreditApproveId(creditApproveId);
		mfCusCreditApproveInfoFeign.delete(mfCusCreditApproveInfo);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		getFormValue(formcreditapprinfo0002);
		boolean validateFlag = this.validateFormData(formcreditapprinfo0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcreditapprinfo0002 = formService.getFormData("creditapprinfo0002");
		getFormValue(formcreditapprinfo0002);
		boolean validateFlag = this.validateFormData(formcreditapprinfo0002);
	}

	/**
	 * 
	 * 方法描述： 打开授信审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param cusNo
	 * @param creditApproveId
	 * @date 2017-6-26 上午10:45:56
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String cusNo, String creditApproveId, String taskId,
			String hideOpinionType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		dataMap.put("creditApproveId", creditApproveId);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(creditApproveId, null);
		dataMap = mfCusCreditApplyFeign.getCreditApproveDataMap(dataMap);
		String creditAppId = String.valueOf(dataMap.get("creditAppId"));
		String creditType = String.valueOf(dataMap.get("creditType"));
		String formId = null;
		String nodeNo = taskAppro.getName();
		String defFalg = ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode();
		String creditModel = String.valueOf(dataMap.get("creditModel"));
		String creditId = String.valueOf(dataMap.get("creditId"));
		String busModel = "";
		String ifFirstReviewMeet = BizPubParm.YES_NO_Y;
		JSONArray jsonArray = new JSONArray();
		if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
            creditId = "creditRenew" + creditModel;
		}
		MfKindForm mfKindForm = prdctInterfaceFeign.getInitKindForm(creditId, nodeNo, defFalg);
		if (mfKindForm == null) {
			formId = prdctInterfaceFeign.getFormId(creditId, WKF_NODE.credit_approval, null, null,
					User.getRegNo(request));
		} else {
			formId = mfKindForm.getAddModel();
		}
		//获取流程信息
		MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
		mfCusCreditConfig.setCreditId(creditId);
		mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
		if (mfCusCreditConfig != null) {
			busModel = mfCusCreditConfig.getBusModel();
		}
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		FormData formcreditapprinfo0001 = formService.getFormData(formId);// String.valueOf(dataMap.get("formId"))
		getFormValue(formcreditapprinfo0001);
		Gson gson = new Gson();
		MfCusCreditApproveInfo mfCusCreditApproveInfo = gson.fromJson(gson.toJson(dataMap.get("mfCusCreditApproveInfo")),MfCusCreditApproveInfo.class);
		mfCusCreditApproveInfo.setApproveRemark(null);
		mfCusCreditApproveInfo.setApproveResult(null);
		JSONObject json = new JSONObject();
		if ("secretary_arrange".equals(nodeNo)) {// 秘书排审
			mfCusCreditApproveInfo.setOpinionType(AppConstant.OPINION_TYPE_ARREE);
		}
		if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
			mfCusCreditApply = gson.fromJson(gson.toJson(dataMap.get("mfCusCreditApply")),MfCusCreditApply.class);
            mfCusCreditApproveInfo.setProjectNo(mfCusCreditApply.getProjectNo());
            mfCusCreditApproveInfo.setProjectName(mfCusCreditApply.getProjectName());
			getObjValue(formcreditapprinfo0001, mfCusCreditApply);
        } else {
            mfCusCreditAdjustApply = gson.fromJson(gson.toJson(dataMap.get("mfCusCreditAdjustApply")),MfCusCreditAdjustApply.class);
			getObjValue(formcreditapprinfo0001, mfCusCreditAdjustApply);
            String creditPactId = mfCusCreditAdjustApply.getCreditPactId();
			MfCusCreditContract creditContract = new MfCusCreditContract();
            creditContract.setId(creditPactId);
            creditContract = mfCusCreditContractFeign.getById(creditContract);
			mfCusCreditApproveInfo.setCreditSum(creditContract.getCreditSum());
			mfCusCreditApproveInfo.setCreditTerm(creditContract.getCreditTerm().toString());
		}

        if("dep_manager".equals(nodeNo)){//部门经理岗位展示决策会审批意见
            MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
            mfCusCreditApplyHis.setCreditAppId(creditAppId);
			mfCusCreditApplyHis.setApproveNodeNo("signtask");
            List<MfCusCreditApplyHis> mfCusCreditApplyHisList = mfCusCreditApplyHisFeign.getByCreditAppId(mfCusCreditApplyHis);
            for(int i = 0;mfCusCreditApplyHisList.size()>i;i++){
				mfCusCreditApplyHis = mfCusCreditApplyHisList.get(i);
				if("signtask".equals(mfCusCreditApplyHis.getApproveNodeNo())){
					this.changeFormProperty(formcreditapprinfo0001, "approveRemark"+i, "initValue", mfCusCreditApplyHis.getApproveRemark());
					this.changeFormProperty(formcreditapprinfo0001, "approveResult"+i, "initValue", mfCusCreditApplyHis.getApproveResult());
					this.changeFormProperty(formcreditapprinfo0001, "approvePartName"+i, "initValue", mfCusCreditApplyHis.getApprovePartName());
				}
			}

        }
		//风险组长岗位选择
		if("riskGroupLeader".equals(nodeNo)&&BizPubParm.BUS_MODEL_12.equals(busModel)) {
			String firstApprovalUser = "";
			String firstApprovalUserName = "";
			MfCusCreditApproveInfo mfCusCreditApproveInfoQuery = new MfCusCreditApproveInfo();
			mfCusCreditApproveInfoQuery.setCusNo(mfCusCreditApproveInfo.getCusNo());
			List<MfCusCreditApproveInfo> mfCusCreditApproveInfoList = mfCusCreditApproveInfoFeign.getMfCusCreditApproveInfoList(mfCusCreditApproveInfoQuery);
			if(mfCusCreditApproveInfoList.size()>1){
				for(int i=1;i<mfCusCreditApproveInfoList.size();i++){
                    mfCusCreditApproveInfoQuery = mfCusCreditApproveInfoList.get(i);
                    if (mfCusCreditApproveInfoQuery!=null&&StringUtil.isNotEmpty(mfCusCreditApproveInfoQuery.getRiskApprovalPer())) {
                        firstApprovalUser =  mfCusCreditApproveInfoQuery.getRiskApprovalPer();
                        break;
                    }
                }
			}
			if (StringUtil.isNotEmpty(firstApprovalUser)) {
				SysUser sysUser = new SysUser();
				sysUser.setOpNo(firstApprovalUser);
				sysUser = sysUserFeign.getById(sysUser);
				firstApprovalUserName = sysUser.getOpName();
			} else {
				firstApprovalUser = null;
				firstApprovalUserName = null;
			}
			mfCusCreditApproveInfo.setFirstApprovalUser(firstApprovalUser);
			mfCusCreditApproveInfo.setFirstApprovalUserName(firstApprovalUserName);
			this.changeFormProperty(formcreditapprinfo0001, "firstApprovalUser", "initValue", firstApprovalUser);
			this.changeFormProperty(formcreditapprinfo0001, "firstApprovalUserName", "initValue", firstApprovalUserName);
		}
		//风险审核人
		if(("riskApproval".equals(nodeNo)||"riskAgainApproval".equals(nodeNo))&&BizPubParm.BUS_MODEL_12.equals(busModel)) {
			mfCusCreditApproveInfo.setFirstApprovalUser(mfCusCreditApproveInfo.getRiskManageNo());
			mfCusCreditApproveInfo.setFirstApprovalUserName(mfCusCreditApproveInfo.getRiskManageName());
			this.changeFormProperty(formcreditapprinfo0001, "firstApprovalUser", "initValue", mfCusCreditApproveInfo.getRiskManageNo());
			this.changeFormProperty(formcreditapprinfo0001, "firstApprovalUserName", "initValue", mfCusCreditApproveInfo.getRiskManageName());
			// 反担保方案
			CodeUtils cu = new CodeUtils();
			StringBuilder assamtSB = new StringBuilder();
			String reverseGuaPlan = mfCusCreditApproveInfo.getReverseGuaPlan();
			if(StringUtil.isEmpty(reverseGuaPlan)){
				Map<String,String> cusPersRelMap = cu.getMapByKeyName("CUS_PERS_REL");
				StringBuilder reverseGuaPlanSB = new StringBuilder();
				StringBuilder perSB = new StringBuilder();
				StringBuilder corpSB = new StringBuilder();
				MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
				mfBusCollateralRel.setAppId(mfCusCreditApply.getCreditAppId());
				mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
				if(mfBusCollateralRel != null){
					MfBusCollateralDetailRel mfBusCollateralDetailRelQuery = new MfBusCollateralDetailRel();
					mfBusCollateralDetailRelQuery.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
					List<MfBusCollateralDetailRel> mfBusCollateralDetailRelList = mfBusCollateralDetailRelFeign.getCollateralDetailRel(mfBusCollateralDetailRelQuery);
					for (MfBusCollateralDetailRel mfBusCollateralDetailRel: mfBusCollateralDetailRelList) {
						MfAssureInfo mfAssureInfo = new MfAssureInfo();
						mfAssureInfo.setId(mfBusCollateralDetailRel.getCollateralId());
						mfAssureInfo = mfAssureInfoFeign.getById(mfAssureInfo);
						if(mfAssureInfo != null){
							String assureType = mfAssureInfo.getAssureType();
							if("1".equals(assureType)){//企业法人担保
								perSB.append(cusPersRelMap.get(mfAssureInfo.getRelation())).append(mfAssureInfo.getAssureName()).append("、");
							}else if("2".equals(assureType)){
								corpSB.append(mfAssureInfo.getAssureName()).append("、");
							}else if("4".equals(assureType)){//保证金
								assamtSB.append(mfAssureInfo.getAssureName()).append("、");
							}
						}
					}
					if(StringUtil.isNotEmpty(perSB.toString())){
						perSB.deleteCharAt(perSB.length()-1);
						perSB.append("个人连带责任保证。");
					}
					if(StringUtil.isNotEmpty(corpSB.toString())){
						corpSB.deleteCharAt(corpSB.length()-1);
						corpSB.append("连带责任保证。");
					}
					reverseGuaPlanSB.append(perSB).append(corpSB);
					mfCusCreditApproveInfo.setReverseGuaPlan(reverseGuaPlanSB.toString());
				}
			}
			// 担保方案
			String guaPlan = mfCusCreditApproveInfo.getGuaPlan();
			String assamt = "";
			if(StringUtil.isEmpty(guaPlan)){
				if(StringUtil.isEmpty(assamtSB.toString())){
					assamt = "保函无保证金。";
				}
				StringBuilder guaPlanSB = new StringBuilder();
				guaPlanSB.append("同意提供").append(MathExtend.divide(mfCusCreditApproveInfo.getCreditSum(),10000.00)).append("万元内部授信担保，有效期")
						.append(mfCusCreditApproveInfo.getCreditTerm()).append("个月。其中：\n").append(mfCusCreditApply.getAgenciesName())
						.append(MathExtend.divide(mfCusCreditApproveInfo.getCreditSum(),10000.00)).append("万元综合授信担保，期限").append(mfCusCreditApproveInfo.getCreditTerm()).append("个月(")
						.append(mfCusCreditApply.getPutoutTerm()).append("+").append(mfCusCreditApply.getExtendTerm()).append("),全部为保函，单笔期限不超过2年，保函品种为")
						.append(mfCusCreditApply.getBreedName()).append("保函,").append(assamt).append("授信项下单笔业务按照现行制度单独审批。\n仅限提供一般保证责任担保，开具独立保函则需要增加反担保措施。");
				guaPlan = guaPlanSB.toString();
				mfCusCreditApproveInfo.setGuaPlan(guaPlan);
			}
		}
		getObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);

		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		//工程担保
		if(BizPubParm.BUS_MODEL_12.equals(busModel)){
			opinionTypeMap.put("opinionType", "CREDIT_OPINION_TYPE");
			//opinionTypeMap.put("opinionType","APPLY_OPINION_TYPE");
			if("comRiskCheck".equals(nodeNo)){
				opinionTypeMap.put("opinionType","RISK_OPINION_TYPE");
			}
			if("reviewMeet".equals(nodeNo)){
				hideOpinionType = hideOpinionType + "@6@4";//隐藏发回重审/退回补充资料
			}else {
				hideOpinionType = hideOpinionType + "@6.4@6.5";//隐藏有条件同意/复议
			}
			if( "riskApproval".equals(nodeNo) && BizPubParm.YES_NO_Y.equals(mfCusCreditApproveInfo.getIfChargeback())){//退单回来的只有否决选项
				hideOpinionType = hideOpinionType + "@6@4@1";//隐藏发回重审/退回补充资料/同意
			}
		}
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		if("signtask".equals(nodeNo)){//决策会节点审批意见发回重审、退回补充资料、复议
			OptionsList op = new OptionsList();
			op.setOptionLabel("发回重审");
			op.setOptionValue(AppConstant.OPINION_TYPE_RESTART);
			opinionTypeList.add(op);
			OptionsList op1 = new OptionsList();
			op1.setOptionLabel("退回补充资料");
			op1.setOptionValue(AppConstant.OPINION_TYPE_DEALER);
			opinionTypeList.add(op1);
			OptionsList op2 = new OptionsList();
			op2.setOptionLabel("复议");
			op2.setOptionValue(AppConstant.OPINION_TYPE_RETCONTINUE);
			opinionTypeList.add(op2);
		}
		this.changeFormProperty(formcreditapprinfo0001, "opinionType", "optionArray", opinionTypeList);

		// 查询授信产品信息
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setCreditAppId(creditAppId);
		List<MfCusPorductCredit> mfCusPorductCredits = mfCusPorductCreditFeign.getByCreditAppIdDesc(mfCusPorductCredit);

		JSONArray array = JSONArray.fromObject(mfCusPorductCredits);
		json.put("mfCusPorductCreditList", array);
		// 获取所有产品
		List<MfSysKind> mfSysKinds = new ArrayList<MfSysKind>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setUseFlag("1");
		mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
		array = JSONArray.fromObject(mfSysKinds);
		json.put("mfSysKinds", array);
		request.setAttribute("creditType", creditType);
		request.setAttribute("json", json);
		request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		if(befNodesjsonArray != null && befNodesjsonArray.size() > 0){
			for(int i=0;i<befNodesjsonArray.size();i++){
				JSONObject js = (JSONObject)befNodesjsonArray.get(i);
				String nodeName = js.getString("name");
				if("busTravel".equals(nodeName) || "riskAgainApproval".equals(nodeName)||"riskGroupLeader".equals(nodeName)){
					befNodesjsonArray.remove(js);
				}
				if(nodeNo.equals("riskAgainApproval") && "riskApproval".equals(nodeName)){
					befNodesjsonArray.remove(js);
				}
			}
		}
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0001);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("baseType", mfCusCustomer.getCusBaseType());
		model.addAttribute("creditApproveId", creditApproveId);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("busModel", busModel);
		model.addAttribute("processId", mfCusCreditApply.getCreditProcessId());
		model.addAttribute("cusCreditAddPro", new CodeUtils().getSingleValByKey("CUS_CREDIT_ADD_PRO"));
		return "/component/auth/MfCusCreditApprove";
	}
	/**
	 *
	 * 方法描述： 打开授信审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author ywh
	 * @param cusNo
	 * @param primaryAppId
	 * @date 2019-4-18 上午10:45:56
	 */
	@RequestMapping(value = "/getViewPointForPrimary")
	public String getViewPointForPrimary(Model model, String ajaxData, String cusNo, String taskId,
							   String hideOpinionType,String primaryAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		dataMap.put("primaryAppId", primaryAppId);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(primaryAppId, null);
		dataMap = mfCusCreditApplyFeign.getCreditPrimaryApproveDataMap(dataMap);
		String creditAppId = String.valueOf(dataMap.get("creditAppId"));
		String creditType = String.valueOf(dataMap.get("creditType"));
		String kindNo = String.valueOf(dataMap.get("creditId"));
		String formId = null;
		String nodeNo = taskAppro.getName();
		String defFalg = ApplyEnum.BUDGET_DEFFLAG_TYPE.DEFFLAG1.getCode();
		String creditModel = String.valueOf(dataMap.get("creditModel"));
		if (BizPubParm.CREDIT_TYPE_RENEW.equals(creditType)) {
			kindNo = "creditRenew" + creditModel;
		}
		MfKindForm mfKindForm = prdctInterfaceFeign.getInitKindForm(kindNo, nodeNo, defFalg);
		if (mfKindForm == null){
			formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.credit_primary_approval, null, null,
					User.getRegNo(request));
		}else {
			formId = mfKindForm.getAddModel();
		}

		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		MfCusCreditAdjustApply mfCusCreditAdjustApply = new MfCusCreditAdjustApply();
		FormData formcreditapprinfo0001 = formService.getFormData(formId);// String.valueOf(dataMap.get("formId"))
		getFormValue(formcreditapprinfo0001);
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
			mfCusCreditApproveInfo.setCreditAppId(creditAppId);
		} else {
			mfCusCreditApproveInfo.setAdjustAppId(creditAppId);
		}
		mfCusCreditApproveInfo = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfo);
		mfCusCreditApproveInfo.setCreditType(creditType);
		mfCusCreditApproveInfo.setApproveRemark(null);
		mfCusCreditApproveInfo.setApproveResult(null);
		JSONObject json = new JSONObject();
		if ("secretary_arrange".equals(nodeNo)) {// 秘书排审
			mfCusCreditApproveInfo.setOpinionType(AppConstant.OPINION_TYPE_ARREE);
		}
		if (BizPubParm.CREDIT_TYPE_ADD.equals(creditType)) {
			mfCusCreditApply.setCreditAppId(creditAppId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			getObjValue(formcreditapprinfo0001, mfCusCreditApply);
		} else {
			mfCusCreditAdjustApply.setAdjustAppId(creditAppId);
			mfCusCreditAdjustApply = mfCusCreditAdjustApplyFeign.getById(mfCusCreditAdjustApply);
			mfCusCreditApply.setCreditAppId(mfCusCreditAdjustApply.getCreditAppId());
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			getObjValue(formcreditapprinfo0001, mfCusCreditAdjustApply);

			MfCusCreditContract creditContract = mfCusCreditContractFeign
					.getNewestCusCreditContrac(mfCusCreditAdjustApply.getCusNo());

			mfCusCreditApproveInfo.setCreditSum(creditContract.getCreditSum());
			mfCusCreditApproveInfo.setCreditTerm(creditContract.getCreditTerm().toString());
		}
		if (mfCusCreditApply != null) {
			mfCusCreditApproveInfo.setProjectNo(mfCusCreditApply.getProjectNo());
			mfCusCreditApproveInfo.setProjectName(mfCusCreditApply.getProjectName());
		}
		getObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);

		String activityType = taskAppro.getActivityType();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formcreditapprinfo0001, "opinionType", "optionArray", opinionTypeList);

		// 查询授信产品信息
		MfCusPorductCredit mfCusPorductCredit = new MfCusPorductCredit();
		mfCusPorductCredit.setCreditAppId(creditAppId);
		List<MfCusPorductCredit> mfCusPorductCredits = mfCusPorductCreditFeign.getByCreditAppIdDesc(mfCusPorductCredit);


		JSONArray array = JSONArray.fromObject(mfCusPorductCredits);
		json.put("mfCusPorductCreditList", array);
		// 获取所有产品
		List<MfSysKind> mfSysKinds = new ArrayList<MfSysKind>();
		MfSysKind mfSysKind = new MfSysKind();
		mfSysKind.setUseFlag("1");
		mfSysKinds = prdctInterfaceFeign.getSysKindList(mfSysKind);
		array = JSONArray.fromObject(mfSysKinds);
		json.put("mfSysKinds", array);
		request.setAttribute("creditType", creditType);
		request.setAttribute("json", json);
		request.setAttribute("cusType", String.valueOf(dataMap.get("cusType")));
		// 获取客户业务身份
		MfCusType mfCusType = new MfCusType();
		mfCusType.setTypeNo(String.valueOf(dataMap.get("cusType")));
		mfCusType = mfCusTypeFeign.getById(mfCusType);
		if (mfCusType != null) {
			model.addAttribute("baseType", mfCusType.getBaseType());
		}
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formcreditapprinfo0001", formcreditapprinfo0001);
		model.addAttribute("query", "");
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("primaryAppId", primaryAppId);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("cusCreditAddPro", new CodeUtils().getSingleValByKey("CUS_CREDIT_ADD_PRO"));
		return "/component/auth/MfCusCreditApprove_primary";
	}
	/**
	 * 
	 * 方法描述： 授信审批保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param taskId
	 * @param creditApproveId
	 * @param transition
	 * @param nextUser
	 * @date 2017-6-26 下午2:49:49
	 */
	@RequestMapping(value = "/submitApproveAjax")
	@ResponseBody
	public Map<String, Object> submitApproveAjax(String ajaxData, String taskId, String creditApproveId,
			String transition, String nextUser) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
		FormData formcreditapprinfo0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
		getFormValue(formcreditapprinfo0001, getMapByJson(ajaxData));
		MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
		setObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);
		dataMap.put("mfCusCreditApproveInfo", mfCusCreditApproveInfo);
		dataMap.put("isCeilingLoop", mfCusCreditApproveInfo.getIsCeilingLoop());
		dataMap.put("taskId", taskId);
		dataMap.put("creditApproveId", creditApproveId);
		dataMap.put("transition", transition);
		dataMap.put("opNo", User.getRegNo(request));
		dataMap.put("nextUser", nextUser);
		dataMap.put("opinionType", opinionType);
		dataMap.put("approvalOpinion", approvalOpinion);
		dataMap.put("orgNo", User.getOrgNo(request));
		dataMap.put("regName", User.getRegName(request));
		dataMap.put("regNo", User.getRegNo(request));
		MfCusCreditApproveInfo mfCusCreditApproveInfoQuery =new MfCusCreditApproveInfo();
		mfCusCreditApproveInfoQuery.setCreditApproveId(creditApproveId);
		mfCusCreditApproveInfoQuery = mfCusCreditApproveInfoFeign.getById(mfCusCreditApproveInfoQuery);
		if(mfCusCreditApproveInfoQuery!=null){
			if(StringUtil.isNotEmpty(mfCusCreditApproveInfoQuery.getCreditAppId())){
				MfCusCreditConfig mfCusCreditConfig = new MfCusCreditConfig();
				mfCusCreditConfig.setCreditId(mfCusCreditApproveInfoQuery.getCreditId());
				mfCusCreditConfig = mfCusCreditConfigFeign.getById(mfCusCreditConfig);
				String busModel = mfCusCreditConfig.getBusModel();
				if(!"12".equals(busModel)) {
					//校验合作银行和业务品种
					MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
					mfCusAgenciesCredit.setCreditAppId(mfCusCreditApproveInfoQuery.getCreditAppId());
					List<MfCusAgenciesCredit> mfCusAgenciesCreditList = mfCusCreditApplyFeign.getByCreditAppId(mfCusAgenciesCredit);
					MfBusBreedCredit mfBusBreedCredit = new MfBusBreedCredit();
					mfBusBreedCredit.setCreditAppId(mfCusCreditApproveInfoQuery.getCreditAppId());
					List<MfBusBreedCredit> mfBusBreedCreditList = mfCusCreditApplyFeign.getBreedByCreditAppId(mfBusBreedCredit);
					if (mfCusAgenciesCreditList.size() > 0) {
						for (int i = 0; i < mfCusAgenciesCreditList.size(); i++) {
							mfCusAgenciesCredit = mfCusAgenciesCreditList.get(i);
							String agenciesId = mfCusAgenciesCredit.getAgenciesId();
							//判断合作银行下是否含有业务品种
							boolean agenciesHaveBreed = false;
							if (mfBusBreedCreditList.size() < 0) {
								dataMap.put("flag", "error");
								dataMap.put("msg", "该授信下未录入业务品种！");
								return dataMap;
							} else {
								for (int j = 0; j < mfBusBreedCreditList.size(); j++) {
									mfBusBreedCredit = mfBusBreedCreditList.get(j);
									if (agenciesId.equals(mfBusBreedCredit.getBreedAgenciesId())) {
										agenciesHaveBreed = true;
									}
								}
								if (!agenciesHaveBreed) {
									dataMap.put("flag", "error");
									dataMap.put("msg", "合作银行:" + mfCusAgenciesCredit.getAgenciesName() + "下未添加业务品种！");
									return dataMap;
								}
							}


						}
					} else {
						dataMap.put("flag", "error");
						dataMap.put("msg", "该授信下未录入合作银行！");
						return dataMap;
					}
					MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel = new MfBusAgenciesPledgeRel();
					mfBusAgenciesPledgeRel.setAppId(mfCusCreditApproveInfoQuery.getCreditAppId());
					Map<String,String> 	checkMap = mfBusAgenciesPledgeRelFeign.checkAgenciesPledgeRel(mfBusAgenciesPledgeRel);
					if("error".equals(checkMap.get("flag"))){
						dataMap.put("flag", "error");
						dataMap.put("msg", "该授信下反担保需关联合作银行！");
						return dataMap;
					}
				}
				//工程担保评审会秘书获取表单信息
				TaskImpl taskApprove = wkfInterfaceFeign.getTaskWithUser(mfCusCreditApproveInfoQuery.getCreditApproveId(), "",User.getRegNo(request));
				if(BizPubParm.BUS_MODEL_12.equals(busModel) && "reviewMeet".equals(taskApprove.getActivityName())){
					//工程担保评审会秘书审批意见
					dataMap.put("approvalOpinion",dataMap.get("meetingOpinions"));
					Gson gson = new Gson();
					try {
						mfCusCreditApproveInfo.setReviewMemberType(gson.fromJson(gson.toJson(dataMap.get("reviewMemberType")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberNo(gson.fromJson(gson.toJson(dataMap.get("reviewMemberNo")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberName(gson.fromJson(gson.toJson(dataMap.get("reviewMemberName")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberOpinion(gson.fromJson(gson.toJson(dataMap.get("reviewMemberOpinion")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberOtherContent(gson.fromJson(gson.toJson(dataMap.get("reviewMemberOtherContent")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberReason(gson.fromJson(gson.toJson(dataMap.get("reviewMemberReason")), new TypeToken<List<String>>() {
						}.getType()));
						mfCusCreditApproveInfo.setReviewMemberOtherReason(gson.fromJson(gson.toJson(dataMap.get("reviewMemberOtherReason")), new TypeToken<List<String>>() {
						}.getType()));
					}catch(Exception e){
						List<String> reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberType"));
						mfCusCreditApproveInfo.setReviewMemberType(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberNo"));
						mfCusCreditApproveInfo.setReviewMemberNo(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberName"));
						mfCusCreditApproveInfo.setReviewMemberName(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberOpinion"));
						mfCusCreditApproveInfo.setReviewMemberOpinion(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberOtherContent"));
						mfCusCreditApproveInfo.setReviewMemberOtherContent(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberReason"));
						mfCusCreditApproveInfo.setReviewMemberReason(reviewList);
						reviewList = new ArrayList<>();
						reviewList.add((String) dataMap.get("reviewMemberOtherReason"));
						mfCusCreditApproveInfo.setReviewMemberOtherReason(reviewList);
					}
				}
			}
		}
		Result res = mfCusCreditApproveInfoFeign.doCommit(dataMap);
		if (res.isSuccess()) {
			dataMap.put("flag", "success");
			if (res.isEndSts()) {
				Result result = mfCusCreditApproveInfoFeign.doCommitNextStep(mfCusCreditApproveInfo);
				if (!result.isSuccess()) {
					dataMap.put("flag", "error");
				}
				CodeUtils cu = new CodeUtils();
				Map<String,String> ifEndCreditMap = cu.getMapByKeyName("IF_END_CREDIT");
				String creditApproval = ifEndCreditMap.get("credit_approval");
				if(creditApproval != null && BizPubParm.YES_NO_Y.equals(creditApproval)){
					dataMap.put("msg", "授信审批完成");
				}else{
					dataMap.put("msg", result.getMsg());
				}
			} else {
				dataMap.put("msg", res.getMsg());
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
    /**
     *
     * 方法描述： 授信立项审批保存提交
     *
     * @return
     * @throws Exception
     *             String
     * @author 沈浩兵
     * @param taskId
     * @param creditApproveId
     * @param transition
     * @param nextUser
     * @date 2017-6-26 下午2:49:49
     */
    @RequestMapping(value = "/submitApproveForPrimaryAjax")
    @ResponseBody
    public Map<String, Object> submitApproveForPrimaryAjax(String ajaxData, String taskId, String creditApproveId,String primaryAppId,
                                                 String transition, String nextUser) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        ActionContext.initialize(request, response);
        FormService formService = new FormService();
        dataMap = getMapByJson(ajaxData);
        String opinionType = String.valueOf(dataMap.get("opinionType"));
        String approvalOpinion = String.valueOf(dataMap.get("approveRemark"));
        FormData formcreditapprinfo0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
        getFormValue(formcreditapprinfo0001, getMapByJson(ajaxData));
        MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
        setObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);
        dataMap.put("mfCusCreditApproveInfo", mfCusCreditApproveInfo);
        dataMap.put("isCeilingLoop", mfCusCreditApproveInfo.getIsCeilingLoop());
        dataMap.put("primaryAppId", primaryAppId);
        dataMap.put("taskId", taskId);
        dataMap.put("creditApproveId", mfCusCreditApproveInfo.getCreditApproveId());
        dataMap.put("transition", transition);
        dataMap.put("opNo", User.getRegNo(request));
        dataMap.put("nextUser", nextUser);
        dataMap.put("opinionType", opinionType);
        dataMap.put("approvalOpinion", approvalOpinion);
        dataMap.put("orgNo", User.getOrgNo(request));
        dataMap.put("regName", User.getRegName(request));
        dataMap.put("regNo", User.getRegNo(request));
        Result res = mfCusCreditApproveInfoFeign.doCommitForPrimary(dataMap);
        if (res.isSuccess()) {
            dataMap.put("flag", "success");
            if (res.isEndSts()) {
                Result result = mfCusCreditApproveInfoFeign.doCommitNextStep(mfCusCreditApproveInfo);
                if (!result.isSuccess()) {
                    dataMap.put("flag", "error");
                }
                CodeUtils cu = new CodeUtils();
                Map<String,String> ifEndCreditMap = cu.getMapByKeyName("IF_END_CREDIT");
                String creditApproval = ifEndCreditMap.get("credit_approval");
                if(creditApproval != null && BizPubParm.YES_NO_Y.equals(creditApproval)){
                    dataMap.put("msg", "授信审批完成");
                }else{
                    dataMap.put("msg", result.getMsg());
                }
            } else {
                dataMap.put("msg", res.getMsg());
            }
        } else {
            dataMap.put("flag", "error");
            dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
        }
        return dataMap;
    }
	/**
	 * 
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitApproveValidateAjax")
	@ResponseBody
	public Map<String, Object> submitApproveValidateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formcreditapprinfo0001 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcreditapprinfo0001, dataMap);
			MfCusCreditApproveInfo mfCusCreditApproveInfo = new MfCusCreditApproveInfo();
			setObjValue(formcreditapprinfo0001, mfCusCreditApproveInfo);

			String creditAppId = mfCusCreditApproveInfo.getCreditAppId();
			MfCusCreditApplyHis mfCusCreditApplyHis = new MfCusCreditApplyHis();
			mfCusCreditApplyHis.setCreditAppId(creditAppId);
			mfCusCreditApplyHis = mfCusCreditApplyHisFeign.getLastByCreditAppId(mfCusCreditApplyHis);
			Double applyCreditSum = 0d;// 申请授信总额
			Double applyDepositRate = 0d;// 申请保证金比例
			Double applyCreFincRate = 0d;// 申请利率
			if (null == mfCusCreditApplyHis) {// 第一个审批人时
				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(creditAppId);
				mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
				applyCreditSum = mfCusCreditApply.getCreditSum();// 申请授信总额
				applyDepositRate = mfCusCreditApply.getDepositRate();
				applyCreFincRate = mfCusCreditApply.getCreFincRate();
			} else {
				applyCreditSum = mfCusCreditApplyHis.getCreditSum();// 上一笔审批授信总额
				applyDepositRate = mfCusCreditApplyHis.getDepositRate();
				applyCreFincRate = mfCusCreditApplyHis.getCreFincRate();
			}

			Double approveCreditSum = mfCusCreditApproveInfo.getCreditSum();// 审批授信总额
			if (!"1".equals(mfCusCreditApproveInfo.getCreditType())) {
				approveCreditSum = mfCusCreditApproveInfo.getAdjCreditSum();
			}

			Double approveDepositRate = mfCusCreditApproveInfo.getDepositRate();// 审批保证金比例
			Double approveCreFincRate = mfCusCreditApproveInfo.getCreFincRate();// 审批利率
			dataMap = new HashMap<String, Object>();
			if (applyCreditSum < approveCreditSum) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "审批授信总额不能大于申请授信总额(上个环节审批授信总额)");
				return dataMap;
			}
			if (applyDepositRate != null && approveDepositRate != null && applyDepositRate > approveDepositRate) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "审批保证金比例不能小于申请保证金比例(上个环节审批保证金比例)");
				return dataMap;
			}
			if (applyCreFincRate != null && approveCreFincRate != null && applyCreFincRate > approveCreFincRate) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "审批授信利率不能小于申请授信利率(上个环节审批授信利率)");
				return dataMap;
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
