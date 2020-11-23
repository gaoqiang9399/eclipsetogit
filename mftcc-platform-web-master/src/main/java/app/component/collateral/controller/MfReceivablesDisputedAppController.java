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
import app.component.collateral.entity.MfReceivablesDisputedApp;
import app.component.collateral.entity.MfReceivablesDisputedAppHis;
import app.component.collateral.feign.MfReceivablesDisputedAppFeign;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfReceivablesDisputedAppController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 15 18:27:45 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesDisputedApp")
public class MfReceivablesDisputedAppController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesDisputedAppFeign mfReceivablesDisputedAppFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
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
	public String getListPage(Model model, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setBusPleId(busPleId);
		List<MfReceivablesDisputedApp> mfReceivablesDisputedAppList = mfReceivablesDisputedAppFeign
				.getReceDisputedList(mfReceivablesDisputedApp);
		if (mfReceivablesDisputedAppList.size() > 0) {
			mfReceivablesDisputedApp = mfReceivablesDisputedAppList.get(0);
		}
		model.addAttribute("mfReceivablesDisputedApp", mfReceivablesDisputedApp);
		model.addAttribute("mfReceivablesDisputedAppList", mfReceivablesDisputedAppList);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedApp_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		try {
			mfReceivablesDisputedApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesDisputedApp.setCriteriaList(mfReceivablesDisputedApp, ajaxData);// 我的筛选
			mfReceivablesDisputedApp.setBusPleId(busPleId);
			// mfReceivablesDisputedApp.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesDisputedApp,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesDisputedAppFeign.findByPage(ipage, mfReceivablesDisputedApp);
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
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecedisputed0002 = formService.getFormData("recedisputed0002");
			getFormValue(formrecedisputed0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecedisputed0002)) {
				MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
				setObjValue(formrecedisputed0002, mfReceivablesDisputedApp);
				mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign.insert(mfReceivablesDisputedApp);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfReceivablesDisputedApp.getApproveNodeName());
				paramMap.put("opNo", mfReceivablesDisputedApp.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
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
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecedisputed0002 = formService.getFormData("recedisputed0002");
		getFormValue(formrecedisputed0002, getMapByJson(ajaxData));
		MfReceivablesDisputedApp mfReceivablesDisputedAppJsp = new MfReceivablesDisputedApp();
		setObjValue(formrecedisputed0002, mfReceivablesDisputedAppJsp);
		MfReceivablesDisputedApp mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign
				.getById(mfReceivablesDisputedAppJsp);
		if (mfReceivablesDisputedApp != null) {
			try {
				mfReceivablesDisputedApp = (MfReceivablesDisputedApp) EntityUtil.reflectionSetVal(
						mfReceivablesDisputedApp, mfReceivablesDisputedAppJsp, getMapByJson(ajaxData));
				mfReceivablesDisputedAppFeign.update(mfReceivablesDisputedApp);
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
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecedisputed0002 = formService.getFormData("recedisputed0002");
			getFormValue(formrecedisputed0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecedisputed0002)) {
				MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
				setObjValue(formrecedisputed0002, mfReceivablesDisputedApp);
				mfReceivablesDisputedAppFeign.update(mfReceivablesDisputedApp);
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
	 * 
	 * 方法描述： 争议解除保存，更新争议
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-16 下午8:35:25
	 */
	@RequestMapping("/updateRelieveAjax")
	@ResponseBody public Map<String, Object> updateRelieveAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrelievedisputed0002 = formService.getFormData("relievedisputed0002");
			getFormValue(formrelievedisputed0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrelievedisputed0002)) {
				MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
				setObjValue(formrelievedisputed0002, mfReceivablesDisputedApp);
				mfReceivablesDisputedAppFeign.update(mfReceivablesDisputedApp);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_RELIEVE_DISPUTED.getMessage());
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
	@ResponseBody public Map<String, Object> getByIdAjax(String disputedAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecedisputed0002 = formService.getFormData("recedisputed0002");
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setDisputedAppId(disputedAppId);
		mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign.getById(mfReceivablesDisputedApp);
		getObjValue(formrecedisputed0002, mfReceivablesDisputedApp, formData);
		if (mfReceivablesDisputedApp != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData,String disputedAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setDisputedAppId(disputedAppId);
		try {
			mfReceivablesDisputedAppFeign.delete(mfReceivablesDisputedApp);
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
	public String input(Model model,String busPleId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData  formrecedisputed0002 = formService.getFormData("recedisputed0002");
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setBusPleId(busPleId);
		getObjValue(formrecedisputed0002, mfReceivablesDisputedApp);
		MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
		model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("formrecedisputed0002", formrecedisputed0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedApp_Insert";
	}

	/**
	 * 
	 * 方法描述： 应收账款争议解除
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-16 下午7:10:34
	 */
	@RequestMapping("/relieveInput")
	public String relieveInput(Model model,String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrelievedisputed0002 = formService.getFormData("relievedisputed0002");
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setBusPleId(busPleId);
		mfReceivablesDisputedApp.setAppSts("2");
		mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign.getById(mfReceivablesDisputedApp);
		getObjValue(formrelievedisputed0002, mfReceivablesDisputedApp);
		model.addAttribute("formrelievedisputed0002", formrelievedisputed0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedApp_relieveInput";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		FormData  formrecedisputed0002 = formService.getFormData("recedisputed0002");
		getFormValue(formrecedisputed0002);
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		setObjValue(formrecedisputed0002, mfReceivablesDisputedApp);
		mfReceivablesDisputedAppFeign.insert(mfReceivablesDisputedApp);
		getObjValue(formrecedisputed0002, mfReceivablesDisputedApp);
		this.addActionMessage(model,"保存成功");
		@SuppressWarnings("unchecked")
		List<MfReceivablesDisputedApp> mfReceivablesDisputedAppList = (List<MfReceivablesDisputedApp>) mfReceivablesDisputedAppFeign
				.findByPage(this.getIpage(), mfReceivablesDisputedApp).getResult();
		model.addAttribute("mfReceivablesDisputedAppList", mfReceivablesDisputedAppList);
		model.addAttribute("formrecedisputed0002", formrecedisputed0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedApp_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String disputedAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrecedisputed0003 = formService.getFormData("recedisputed0003");
		getFormValue(formrecedisputed0003);
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setDisputedAppId(disputedAppId);
		mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign.getById(mfReceivablesDisputedApp);
		getObjValue(formrecedisputed0003, mfReceivablesDisputedApp);
		model.addAttribute("formrecedisputed0003", formrecedisputed0003);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesDisputedApp_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String disputedAppId,Model model, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setDisputedAppId(disputedAppId);
		mfReceivablesDisputedAppFeign.delete(mfReceivablesDisputedApp);
		return getListPage(model,busPleId);
	}

	/**
	 * 
	 * 方法描述：跳转争议审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-16 上午11:05:51
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model,String disputedAppId,String activityType) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formrecedispappro0002 = formService.getFormData("recedispappro0002");
		MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
		mfReceivablesDisputedApp.setDisputedAppId(disputedAppId);
		mfReceivablesDisputedApp = mfReceivablesDisputedAppFeign.getById(mfReceivablesDisputedApp);
		getObjValue(formrecedispappro0002, mfReceivablesDisputedApp);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfReceivablesDisputedApp.getBusPleId());
		dataMap.put("disputedAppId", disputedAppId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(disputedAppId, null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formrecedispappro0002, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formrecedispappro0002", formrecedispappro0002);
		model.addAttribute("appId", appId);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/collateral/WkfReceDisputedViewPoint";
	}

	/**
	 * 
	 * 方法描述： 争议审批意见保存提交。
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-16 上午11:00:30
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appId, String taskId, String transition,
			String nextUser,String disputedAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecedispappro0002 = formService.getFormData("recedispappro0002");
			getFormValue(formrecedispappro0002, getMapByJson(ajaxData));
			MfReceivablesDisputedApp mfReceivablesDisputedApp = new MfReceivablesDisputedApp();
			MfReceivablesDisputedAppHis disputedAppHis = new MfReceivablesDisputedAppHis();
			setObjValue(formrecedispappro0002, mfReceivablesDisputedApp);
			setObjValue(formrecedispappro0002, disputedAppHis);
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			String opNo = String.valueOf(dataMap.get("opNo"));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res = mfReceivablesDisputedAppFeign.doCommit(taskId, appId, disputedAppId, opinionType,
					approvalOpinion, transition, opNo, nextUser, mfReceivablesDisputedApp, disputedAppHis, dataMap);
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
			throw e;
		}
		return dataMap;
	}

}
