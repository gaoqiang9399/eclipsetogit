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
import app.component.collateral.entity.MfReceivablesRebateApp;
import app.component.collateral.entity.MfReceivablesRebateAppHis;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.collateral.feign.MfReceivablesRebateAppFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfReceivablesRebateAppController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 12 19:59:25 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesRebateApp")
public class MfReceivablesRebateAppController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesRebateAppFeign mfReceivablesRebateAppFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setBusPleId(busPleId);
		List<MfReceivablesRebateApp> mfReceivablesRebateAppList = mfReceivablesRebateAppFeign
				.getReceRebateList(mfReceivablesRebateApp);
		if (mfReceivablesRebateAppList.size() > 0) {
			mfReceivablesRebateApp = mfReceivablesRebateAppList.get(0);
		}
		model.addAttribute("mfReceivablesRebateApp", mfReceivablesRebateApp);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateApp_List";
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
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		try {
			mfReceivablesRebateApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesRebateApp.setCriteriaList(mfReceivablesRebateApp, ajaxData);// 我的筛选
			mfReceivablesRebateApp.setBusPleId(busPleId);
			// mfReceivablesRebateApp.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesRebateApp,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesRebateAppFeign.findByPage(ipage, mfReceivablesRebateApp);
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
			FormData formrecerebate0002 = formService.getFormData("recerebate0002");
			getFormValue(formrecerebate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrecerebate0002)) {
				MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
				setObjValue(formrecerebate0002, mfReceivablesRebateApp);
				mfReceivablesRebateApp = mfReceivablesRebateAppFeign.insert(mfReceivablesRebateApp);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfReceivablesRebateApp.getApproveNodeName());
				paramMap.put("opNo", mfReceivablesRebateApp.getApprovePartName());
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
		FormData formrecerebate0002 = formService.getFormData("recerebate0002");
		getFormValue(formrecerebate0002, getMapByJson(ajaxData));
		MfReceivablesRebateApp mfReceivablesRebateAppJsp = new MfReceivablesRebateApp();
		setObjValue(formrecerebate0002, mfReceivablesRebateAppJsp);
		MfReceivablesRebateApp mfReceivablesRebateApp = mfReceivablesRebateAppFeign.getById(mfReceivablesRebateAppJsp);
		if (mfReceivablesRebateApp != null) {
			try {
				mfReceivablesRebateApp = (MfReceivablesRebateApp) EntityUtil.reflectionSetVal(mfReceivablesRebateApp,
						mfReceivablesRebateAppJsp, getMapByJson(ajaxData));
				mfReceivablesRebateAppFeign.update(mfReceivablesRebateApp);
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
			FormData formrebateaffirm0001 = formService.getFormData("rebateaffirm0001");
			getFormValue(formrebateaffirm0001, getMapByJson(ajaxData));
			if (this.validateFormData(formrebateaffirm0001)) {
				MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
				setObjValue(formrebateaffirm0001, mfReceivablesRebateApp);
				mfReceivablesRebateAppFeign.updateRebateAffirm(mfReceivablesRebateApp);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_REBATE_AFFIRM.getMessage());
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
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData, String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecerebate0002 = formService.getFormData("recerebate0002");
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setRebateAppId(rebateAppId);
		mfReceivablesRebateApp = mfReceivablesRebateAppFeign.getById(mfReceivablesRebateApp);
		getObjValue(formrecerebate0002, mfReceivablesRebateApp, formData);
		if (mfReceivablesRebateApp != null) {
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
	@ResponseBody public Map<String, Object> deleteAjax(String ajaxData, String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setRebateAppId(rebateAppId);
		try {
			mfReceivablesRebateAppFeign.delete(mfReceivablesRebateApp);
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
	public String input(Model model, String busPleId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrecerebate0002 = formService.getFormData("recerebate0002");
		MfReceivablesRebateApp mfReceivablesRebateApp = mfReceivablesRebateAppFeign
				.getReceivablesRebateAppInit(busPleId, appId);
		getObjValue(formrecerebate0002, mfReceivablesRebateApp);
		model.addAttribute("formrecerebate0002", formrecerebate0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateApp_Insert";
	}

	/**
	 * 
	 * 方法描述： 跳转折让确认页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-23 下午12:36:06
	 */

	@RequestMapping("/inputAffirm")
	public String inputAffirm(Model model, String busPleId, String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrebateaffirm0001 = formService.getFormData("rebateaffirm0001");
		MfReceivablesRebateApp mfReceivablesRebateApp = mfReceivablesRebateAppFeign.getRebateAffirmAppInit(busPleId,
				appId);
		getObjValue(formrebateaffirm0001, mfReceivablesRebateApp);
		model.addAttribute("formrebateaffirm0001", formrebateaffirm0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateAffirm_Insert";
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
		FormData formrecerebate0002 = formService.getFormData("recerebate0002");
		getFormValue(formrecerebate0002);
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		setObjValue(formrecerebate0002, mfReceivablesRebateApp);
		mfReceivablesRebateAppFeign.insert(mfReceivablesRebateApp);
		getObjValue(formrecerebate0002, mfReceivablesRebateApp);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesRebateApp> mfReceivablesRebateAppList = (List<MfReceivablesRebateApp>) mfReceivablesRebateAppFeign
				.findByPage(this.getIpage(), mfReceivablesRebateApp).getResult();
		model.addAttribute("mfReceivablesRebateAppList", mfReceivablesRebateAppList);
		model.addAttribute("formrecerebate0002", formrecerebate0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateApp_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrecerebate0001 = formService.getFormData("recerebate0001");
		getFormValue(formrecerebate0001);
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setRebateAppId(rebateAppId);
		mfReceivablesRebateApp = mfReceivablesRebateAppFeign.getById(mfReceivablesRebateApp);
		getObjValue(formrecerebate0001, mfReceivablesRebateApp);
		model.addAttribute("mfReceivablesRebateApp", mfReceivablesRebateApp);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateApp_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String busPleId ,String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setRebateAppId(rebateAppId);
		mfReceivablesRebateAppFeign.delete(mfReceivablesRebateApp);
		return getListPage( model,  busPleId);
	}

	/**
	 * 
	 * 方法描述： 打开账款折让审批表单
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-15 上午11:20:10
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model,String rebateAppId,String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrecerebateappro0002 = formService.getFormData("recerebateappro0002");
		MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
		mfReceivablesRebateApp.setRebateAppId(rebateAppId);
		mfReceivablesRebateApp = mfReceivablesRebateAppFeign.getById(mfReceivablesRebateApp);
		getObjValue(formrecerebateappro0002, mfReceivablesRebateApp);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfReceivablesRebateApp.getBusPleId());
		dataMap.put("rebateAppId", rebateAppId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(rebateAppId, null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formrecerebateappro0002, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("appId", appId);
		model.addAttribute("formrecerebateappro0002", formrecerebateappro0002);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		return "/component/collateral/WkfReceRebateViewPoint";
	}

	/**
	 * 
	 * 方法描述： 折让审批保存提交审批意见
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-15 上午11:26:56
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appId, String taskId, String transition,
			String nextUser,String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrecerebateappro0002 = formService.getFormData("recerebateappro0002");
			getFormValue(formrecerebateappro0002, getMapByJson(ajaxData));
			MfReceivablesRebateApp mfReceivablesRebateApp = new MfReceivablesRebateApp();
			MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
			setObjValue(formrecerebateappro0002, mfReceivablesRebateApp);
			setObjValue(formrecerebateappro0002, mfReceivablesRebateAppHis);
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			String opNo = String.valueOf(dataMap.get("opNo"));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res = mfReceivablesRebateAppFeign.doCommit(taskId, appId, rebateAppId, opinionType, approvalOpinion,
					transition, opNo, nextUser, mfReceivablesRebateApp, mfReceivablesRebateAppHis, dataMap);
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
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT);
			throw e;
		}
		return dataMap;
	}
}
