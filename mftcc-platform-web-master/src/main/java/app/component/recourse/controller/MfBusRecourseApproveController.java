package  app.component.recourse.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.recourse.entity.MfBusRecourseApply;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.recourse.entity.MfBusRecourseApprove;
import app.component.recourse.entity.MfBusRecourseApproveHis;
import app.component.recourse.feign.MfBusRecourseApproveFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusRecourseApproveAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:41:30 CST 2018
 **/
@Controller
@RequestMapping("/mfBusRecourseApprove")
public class MfBusRecourseApproveController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusRecourseApproveFeign mfBusRecourseApproveFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
	@Autowired
	private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String id,String hideOpinionType) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formrecourseApprove0001 = formService.getFormData("recourseApprove0001");
		MfBusRecourseApprove mfBusRecourseApprove = new MfBusRecourseApprove();
		mfBusRecourseApprove.setId(id);
		mfBusRecourseApprove = mfBusRecourseApproveFeign.getById(mfBusRecourseApprove);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);
		String fincId = mfBusRecourseApprove.getFincId();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setRecourseId(mfBusRecourseApprove.getRecourseId());
		mfBusRecourseApply = mfBusRecourseApplyFeign.getById(mfBusRecourseApply);
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(mfBusRecourseApply.getCompensatoryId());
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		mfBusRecourseApprove.setApproveRemark("");
		getObjValue(formrecourseApprove0001, mfBusRecourseApprove);
		getObjValue(formrecourseApprove0001, mfBusFincApp);
		getObjValue(formrecourseApprove0001, mfBusRecourseApply);
		getObjValue(formrecourseApprove0001, mfBusCompensatoryApply);
		//获得代偿详情
		String activityType = taskAppro.getActivityType();
		//处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),opinionTypeMap);
		this.changeFormProperty(formrecourseApprove0001, "approveResult", "optionArray", opinionTypeList);
		model.addAttribute("formrecourseApprove0001", formrecourseApprove0001);
		model.addAttribute("query", "");
		model.addAttribute("mfBusRecourseApprove", mfBusRecourseApprove);
		return "/component/recourse/MfBusRecourseApprove";
	}
	
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData,String appNo, String taskId, String transition,
			String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formRecourseApprove0001 = formService.getFormData("recourseApprove0001");
		getFormValue(formRecourseApprove0001, formDataMap);
		MfBusRecourseApprove mfBusRecourseApprove = new MfBusRecourseApprove();
		MfBusRecourseApproveHis mfBusRecourseApproveHis = new MfBusRecourseApproveHis();
		setObjValue(formRecourseApprove0001, mfBusRecourseApprove);
		setObjValue(formRecourseApprove0001, mfBusRecourseApproveHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusRecourseApprove);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusRecourseApproveHis);
			formDataMap.put("mfBusRecourseApprove", mfBusRecourseApprove);
			formDataMap.put("mfBusRecourseApproveHis", mfBusRecourseApproveHis);
			res = mfBusRecourseApproveFeign.doCommit(taskId, transition, nextUser,
					formDataMap);
			dataMap = new HashMap<String, Object>();
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
		}
		return dataMap;
	}
	
}
