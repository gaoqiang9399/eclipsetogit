package app.component.collateral.controller;

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
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.MfReceivablesPledgeInfo;
import app.component.collateral.entity.MfReceivablesTransferInfo;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.collateral.feign.MfReceivablesTransferInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfReceivablesPledgeInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 11:00:47 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesPledgeInfo")
public class MfReceivablesPledgeInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	private WkfInterfaceFeign wkfInterfaceFeign;
	private AppInterfaceFeign appInterfaceFeign;
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfReceivablesTransferInfoFeign mfReceivablesTransferInfoFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "MfReceivablesPledgeInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		try {
			mfReceivablesPledgeInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesPledgeInfo.setCriteriaList(mfReceivablesPledgeInfo, ajaxData);// 我的筛选
			// mfReceivablesPledgeInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesPledgeInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesPledgeInfoFeign.findByPage(ipage, mfReceivablesPledgeInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formreceple0002 = formService.getFormData("pledgeRegisterBase");
			getFormValue(formreceple0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreceple0002)) {
				MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
				setObjValue(formreceple0002, mfReceivablesPledgeInfo);
				mfReceivablesPledgeInfoFeign.insertAndSubmitBussProcess(mfReceivablesPledgeInfo, appId);
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
	 * 
	 * 方法描述： 融资业务流程中业务提交应收账款质押或转让审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-5 下午3:16:57
	 */
	@RequestMapping("/processSubmitAjax")
	@ResponseBody
	public Map<String, Object> processSubmitAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfReceivablesPledgeInfo mfReceivablesPledgeInfo = mfReceivablesPledgeInfoFeign.submitProcess(appId,User.getRegNo(request),User.getRegName(request),User.getOrgNo(request));
			dataMap.put("node", "processaudit");
			dataMap.put("flag", "success");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfReceivablesPledgeInfo.getApproveNodeName());
			paramMap.put("opNo", mfReceivablesPledgeInfo.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 打开应收账款质押或转让审批视角页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-6 上午9:59:02
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String receivablesPledgeId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecepleappro0001 = formService.getFormData("recepleappro0001");
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		mfReceivablesPledgeInfo.setReceivablesPledgeId(receivablesPledgeId);
		mfReceivablesPledgeInfo = mfReceivablesPledgeInfoFeign.getById(mfReceivablesPledgeInfo);
		getObjValue(formrecepleappro0001, mfReceivablesPledgeInfo);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfReceivablesPledgeInfo.getBusCollateralId());
		dataMap.put("receivablesPledgeId", receivablesPledgeId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(receivablesPledgeId, null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formrecepleappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formrecepleappro0001", formrecepleappro0001);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "WkfRecePleViewPoint";
	}

	/**
	 * 
	 * 方法描述： 应收账款质押或转让审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-5 下午8:41:01
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appId, String taskId, String transition,
			String nextUser, String approvalOpinion, String receivablesPledgeId, String opinionType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecepleappro0001 = formService.getFormData("recepleappro0001");
			getFormValue(formrecepleappro0001, getMapByJson(ajaxData));
			MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
			setObjValue(formrecepleappro0001, mfReceivablesPledgeInfo);
			mfReceivablesPledgeInfo.setCurrentSessionOrgNo(User.getOrgNo(request));
			Result res = mfReceivablesPledgeInfoFeign.doCommit(taskId, appId, receivablesPledgeId, opinionType,
					approvalOpinion, transition, User.getRegNo(this.getHttpRequest()), nextUser,
					mfReceivablesPledgeInfo);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreceple0002 = formService.getFormData("receple0002");
		getFormValue(formreceple0002, getMapByJson(ajaxData));
		MfReceivablesPledgeInfo mfReceivablesPledgeInfoJsp = new MfReceivablesPledgeInfo();
		setObjValue(formreceple0002, mfReceivablesPledgeInfoJsp);
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = mfReceivablesPledgeInfoFeign
				.getById(mfReceivablesPledgeInfoJsp);
		if (mfReceivablesPledgeInfo != null) {
			try {
				mfReceivablesPledgeInfo = (MfReceivablesPledgeInfo) EntityUtil.reflectionSetVal(mfReceivablesPledgeInfo,
						mfReceivablesPledgeInfoJsp, getMapByJson(ajaxData));
				mfReceivablesPledgeInfoFeign.update(mfReceivablesPledgeInfo);
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formreceple0002 = formService.getFormData("receple0002");
			getFormValue(formreceple0002, getMapByJson(ajaxData));
			if (this.validateFormData(formreceple0002)) {
				MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
				setObjValue(formreceple0002, mfReceivablesPledgeInfo);
				mfReceivablesPledgeInfoFeign.update(mfReceivablesPledgeInfo);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String receivablesPledgeId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formreceple0002 = formService.getFormData("receple0002");
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		mfReceivablesPledgeInfo.setReceivablesPledgeId(receivablesPledgeId);
		mfReceivablesPledgeInfo = mfReceivablesPledgeInfoFeign.getById(mfReceivablesPledgeInfo);
		getObjValue(formreceple0002, mfReceivablesPledgeInfo, formData);
		if (mfReceivablesPledgeInfo != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String receivablesPledgeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		mfReceivablesPledgeInfo.setReceivablesPledgeId(receivablesPledgeId);
		try {
			mfReceivablesPledgeInfoFeign.delete(mfReceivablesPledgeInfo);
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
	@RequestMapping("/input")
	public String input(Model model, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		String nodeNo = WKF_NODE.storage_confirm.getNodeNo();// 功能节点编号
		model.addAttribute("nodeNo", nodeNo);
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		if (BizPubParm.VOU_TYPE_1.equals(mfBusApply.getVouType())) {
			model.addAttribute("vouType", BizPubParm.VOU_TYPE_1);
			model.addAttribute("query", "");
			return "MfReceivablesPledgeInfo_Insert";
		}

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.storage_confirm, null, null, User.getRegNo(request));
		FormData formreceple0002 = formService.getFormData(formId);

		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(appId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		mfReceivablesPledgeInfo.setBusCollateralId(mfBusCollateralRel.getBusCollateralId());
		mfReceivablesPledgeInfo.setDealType("1");
		if (BizPubParm.BUS_MODEL_13.equals(mfBusApply.getBusModel())) {// 保理业务
			mfReceivablesPledgeInfo.setDealType("2");
		}
		mfReceivablesPledgeInfo.setRegisterProveType("0");
		getObjValue(formreceple0002, mfReceivablesPledgeInfo);
		model.addAttribute("formreceple0002", formreceple0002);
		model.addAttribute("query", "");
		return "MfReceivablesPledgeInfo_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formreceple0002 = formService.getFormData("receple0002");
		getFormValue(formreceple0002);
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		setObjValue(formreceple0002, mfReceivablesPledgeInfo);
		mfReceivablesPledgeInfoFeign.insert(mfReceivablesPledgeInfo);
		getObjValue(formreceple0002, mfReceivablesPledgeInfo);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesPledgeInfo> mfReceivablesPledgeInfoList = (List<MfReceivablesPledgeInfo>) mfReceivablesPledgeInfoFeign
				.findByPage(this.getIpage(), mfReceivablesPledgeInfo).getResult();
		model.addAttribute("mfReceivablesPledgeInfoList", mfReceivablesPledgeInfoList);
		model.addAttribute("formreceple0002", formreceple0002);
		model.addAttribute("query", "");
		return "MfReceivablesPledgeInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String busCollateralId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formreceple0001 = formService.getFormData("receple0001");
		getFormValue(formreceple0001);
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		mfReceivablesPledgeInfo.setBusCollateralId(busCollateralId);
		mfReceivablesPledgeInfo = mfReceivablesPledgeInfoFeign.getById(mfReceivablesPledgeInfo);
		if (mfReceivablesPledgeInfo != null) {
			MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
			mfReceivablesTransferInfo.setBusCollateralId(mfReceivablesPledgeInfo.getBusCollateralId());
			mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.getById(mfReceivablesTransferInfo);
		}
		getObjValue(formreceple0001, mfReceivablesPledgeInfo);
		model.addAttribute("formreceple0001", formreceple0001);
		model.addAttribute("query", "");
		return "MfReceivablesPledgeInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String receivablesPledgeId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesPledgeInfo mfReceivablesPledgeInfo = new MfReceivablesPledgeInfo();
		mfReceivablesPledgeInfo.setReceivablesPledgeId(receivablesPledgeId);
		mfReceivablesPledgeInfoFeign.delete(mfReceivablesPledgeInfo);
		return getListPage();
	}

}
