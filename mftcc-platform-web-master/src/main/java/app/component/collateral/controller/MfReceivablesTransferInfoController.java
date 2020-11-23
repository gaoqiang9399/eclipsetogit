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
import app.component.collateral.entity.MfReceivablesTransferApproHis;
import app.component.collateral.entity.MfReceivablesTransferInfo;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.collateral.feign.MfReceivablesTransferInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfBusInterface.WkfBusInterfaceFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfReceivablesTransferInfoController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu May 11 11:22:45 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesTransferInfo")
public class MfReceivablesTransferInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesTransferInfoFeign mfReceivablesTransferInfoFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private WkfBusInterfaceFeign wkfBusInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfReceivablesTransferInfo_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		try {
			mfReceivablesTransferInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesTransferInfo.setCriteriaList(mfReceivablesTransferInfo, ajaxData);// 我的筛选
			// mfReceivablesTransferInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesTransferInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesTransferInfoFeign.findByPage(ipage, mfReceivablesTransferInfo);
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecetran0002 = formService.getFormData("recetran0002");
			getFormValue(formrecetran0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecetran0002)) {
				MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
				setObjValue(formrecetran0002, mfReceivablesTransferInfo);
				mfReceivablesTransferInfoFeign.insertAndSubmitBussProcess(mfReceivablesTransferInfo, appId);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
		FormData formrecetran0002 = formService.getFormData("recetran0002");
		getFormValue(formrecetran0002, getMapByJson(ajaxData));
		MfReceivablesTransferInfo mfReceivablesTransferInfoJsp = new MfReceivablesTransferInfo();
		setObjValue(formrecetran0002, mfReceivablesTransferInfoJsp);
		MfReceivablesTransferInfo mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign
				.getById(mfReceivablesTransferInfoJsp);
		if (mfReceivablesTransferInfo != null) {
			try {
				mfReceivablesTransferInfo = (MfReceivablesTransferInfo) EntityUtil.reflectionSetVal(
						mfReceivablesTransferInfo, mfReceivablesTransferInfoJsp, getMapByJson(ajaxData));
				mfReceivablesTransferInfoFeign.update(mfReceivablesTransferInfo);
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
			FormData formrecetran0002 = formService.getFormData("recetran0002");
			getFormValue(formrecetran0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecetran0002)) {
				MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
				setObjValue(formrecetran0002, mfReceivablesTransferInfo);
				mfReceivablesTransferInfoFeign.update(mfReceivablesTransferInfo);
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
	public Map<String, Object> getByIdAjax(String ajaxData, String receTranAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecetran0002 = formService.getFormData("recetran0002");
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		mfReceivablesTransferInfo.setReceTranAppId(receTranAppId);
		mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.getById(mfReceivablesTransferInfo);
		getObjValue(formrecetran0002, mfReceivablesTransferInfo, formData);
		if (mfReceivablesTransferInfo != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String receTranAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		mfReceivablesTransferInfo.setReceTranAppId(receTranAppId);
		try {
			mfReceivablesTransferInfoFeign.delete(mfReceivablesTransferInfo);
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

		String nodeNo = WKF_NODE.rece_transfer.getNodeNo();// 功能节点编号
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);

		String formId = prdctInterfaceFeign.getFormId(mfBusApply.getKindNo(), WKF_NODE.rece_transfer, null, null, User.getRegNo(request));
		FormData formrecetran0002 = formService.getFormData(formId);

		MfReceivablesTransferInfo mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.setReceTransferInfo(appId);
		getObjValue(formrecetran0002, mfReceivablesTransferInfo);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("appId", appId);
		model.addAttribute("formrecetran0002", formrecetran0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferInfo_Insert";
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
		FormData formrecetran0002 = formService.getFormData("recetran0002");
		getFormValue(formrecetran0002);
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		setObjValue(formrecetran0002, mfReceivablesTransferInfo);
		mfReceivablesTransferInfoFeign.insert(mfReceivablesTransferInfo);
		getObjValue(formrecetran0002, mfReceivablesTransferInfo);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesTransferInfo> mfReceivablesTransferInfoList = (List<MfReceivablesTransferInfo>) mfReceivablesTransferInfoFeign
				.findByPage(this.getIpage(), mfReceivablesTransferInfo).getResult();
		model.addAttribute("mfReceivablesTransferInfoList", mfReceivablesTransferInfoList);
		model.addAttribute("formrecetran0002", formrecetran0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String receTranAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrecetran0001 = formService.getFormData("recetran0001");
		getFormValue(formrecetran0001);
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		mfReceivablesTransferInfo.setReceTranAppId(receTranAppId);
		mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.getById(mfReceivablesTransferInfo);
		getObjValue(formrecetran0001, mfReceivablesTransferInfo);
		model.addAttribute("formrecetran0001", formrecetran0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesTransferInfo_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String receTranAppId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		mfReceivablesTransferInfo.setReceTranAppId(receTranAppId);
		mfReceivablesTransferInfoFeign.delete(mfReceivablesTransferInfo);
		return getListPage();
	}

	/**
	 * 
	 * 方法描述： 保理业务流程中业务提交应收账款转让审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:32:18
	 */
	@RequestMapping("/processSubmitAjax")
	@ResponseBody
	public Map<String, Object> processSubmitAjax(String ajaxData, String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfReceivablesTransferInfo mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.submitProcess(appId,User.getRegNo(request));
			dataMap.put("node", "processaudit");
			dataMap.put("flag", "success");
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfReceivablesTransferInfo.getApproveNodeName());
			paramMap.put("opNo", mfReceivablesTransferInfo.getApprovePartName());
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
	 * 方法描述： 打开应收账款转让审批视角页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:33:31
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String receTranAppId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecetranappro0001 = formService.getFormData("recetranappro0001");
		MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
		mfReceivablesTransferInfo.setReceTranAppId(receTranAppId);
		mfReceivablesTransferInfo = mfReceivablesTransferInfoFeign.getById(mfReceivablesTransferInfo);
		getObjValue(formrecetranappro0001, mfReceivablesTransferInfo);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfReceivablesTransferInfo.getBusCollateralId());
		dataMap.put("receTranAppId", receTranAppId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(receTranAppId, null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formrecetranappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formrecetranappro0001", formrecetranappro0001);
		model.addAttribute("appId", appId);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/collateral/WkfReceTranViewPoint";
	}

	/**
	 * 
	 * 方法描述： 应收账款转让审批提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-11 下午5:34:05
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
			String nextUser, String opNo, String receTranAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecetranappro0001 = formService.getFormData("recetranappro0001");
			getFormValue(formrecetranappro0001, getMapByJson(ajaxData));
			MfReceivablesTransferInfo mfReceivablesTransferInfo = new MfReceivablesTransferInfo();
			MfReceivablesTransferApproHis transferApproHis = new MfReceivablesTransferApproHis();
			setObjValue(formrecetranappro0001, transferApproHis);
			setObjValue(formrecetranappro0001, mfReceivablesTransferInfo);
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			opNo = String.valueOf(dataMap.get("opNo"));
			dataMap.put("orgNo", User.getOrgNo(request));
			dataMap.put("mfReceivablesTransferInfo", mfReceivablesTransferInfo);
			dataMap.put("transferApproHis", transferApproHis);
			Result res = mfReceivablesTransferInfoFeign.doCommit(taskId, appNo, receTranAppId, opinionType,
					approvalOpinion, transition, opNo, nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					// 审批通过之后提交业务到下一个阶段
					MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appNo);
					Result result = wkfBusInterfaceFeign.doCommitNextStepWithUser(mfBusApply.getAppId(),mfBusApply.getWkfAppId(),User.getRegNo(request));
					if (!result.isSuccess()) {
						dataMap.put("flag", "error");
					}
					dataMap.put("msg", result.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
