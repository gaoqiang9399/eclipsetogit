package app.component.eval.controller;

import java.util.ArrayList;
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
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.ViewUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.eval.entity.AppEval;
import app.component.eval.entity.AppEvalWkf;
import app.component.eval.entity.EvalCompreVal;
import app.component.eval.entity.EvalScenceRestrictVal;
import app.component.eval.entity.EvalScoreGradeConfig;
import app.component.eval.feign.AppEvalFeign;
import app.component.eval.feign.AppEvalWkfFeign;
import app.component.eval.feign.EvalCompreValFeign;
import app.component.eval.feign.EvalScenceRestrictValFeign;
import app.component.eval.feign.EvalScoreGradeConfigFeign;
import app.component.eval.util.EvalUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;;
@Controller
@RequestMapping("/appEvalWkf")
public class AppEvalWkfController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AppEvalFeign appEvalFeign;
	@Autowired
	private AppEvalWkfFeign appEvalWkfFeign;
	@Autowired
	private EvalScoreGradeConfigFeign evalScoreGradeConfigFeign;
	@Autowired
	private EvalCompreValFeign evalCompreValFeign;
	@Autowired
	private EvalScenceRestrictValFeign evalScenceRestrictValFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;

	/**
	 * 
	 * 方法描述： 打开评级审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-13 下午9:55:17
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String evalAppNo, String activityType,String hideOpinionType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataGradeCardMap = new HashMap<String, Object>();
		FormData formevalapp1001 = formService.getFormData("evalapp1001");
		AppEval appEval = new AppEval();
		appEval.setEvalAppNo(evalAppNo);
		appEval = appEvalFeign.getById(appEval);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(appEval.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {
			formevalapp1001 = formService.getFormData("evalappropers1001");
		}
		String grade = getEvalLevel(appEval.getEvalScenceNo(), appEval.getTotalScore(), appEval.getGrade());// 获取机评等级
		appEval.setGrade(grade);
		appEval.setTotalScoreTmp(appEval.getTotalScore());
		if (StringUtil.isEmpty(appEval.getEvalType())||"1".equals(appEval.getEvalType())) {
			// 综合查询
			EvalCompreVal evalCompreVal = new EvalCompreVal();
			evalCompreVal.setEvalAppNo(evalAppNo);
			evalCompreVal.setEvalScenceNo(appEval.getEvalScenceNo());
			evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
			evalCompreVal.setFinScorePercent(MathExtend.multiply(evalCompreVal.getFinScorePercent(), 100));
			evalCompreVal.setDlScorePercent(MathExtend.multiply(evalCompreVal.getDlScorePercent(), 100));
			evalCompreVal.setDxScorePercent(MathExtend.multiply(evalCompreVal.getDxScorePercent(), 100));
			EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
			evalScenceRestrictVal.setEvalAppNo(evalAppNo);
			evalScenceRestrictVal = evalScenceRestrictValFeign.getById(evalScenceRestrictVal);
			if (evalScenceRestrictVal != null) {
				evalCompreVal.setRestrictLevel(evalScenceRestrictVal.getEvalRestrictLevel());
			}
			getObjValue(formevalapp1001, evalCompreVal);
			dataGradeCardMap = appEvalFeign.getEvalListData(evalAppNo, appEval.getEvalScenceNo());
			dataGradeCardMap = JSONObject.fromObject(dataGradeCardMap);
			model.addAttribute("dataGradeCardMap", dataGradeCardMap);
		}
		//第一个审批岗位默认给定客户经理建议评级
		if(StringUtil.isEmpty(appEval.getApprovalGrade())){
			appEval.setApprovalGrade(appEval.getMangGrade());
		}
		getObjValue(formevalapp1001, appEval);
		// 检查审批的场景号
		String scNo = BizPubParm.SCENCE_TYPE_DOC_EXAM;

		dataMap.put("cusNo", appEval.getCusNo());
		dataMap.put("evalAppNo", appEval.getEvalAppNo());
		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appEval.getEvalAppNo(), null);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formevalapp1001, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(evalAppNo, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("scNo", scNo);
		model.addAttribute("formevalapp1001", formevalapp1001);
		model.addAttribute("evalAppNo", evalAppNo);
		model.addAttribute("query", "");
		model.addAttribute("appEval", appEval);
		return "/component/eval/WkfEvalViewPoint";
	}
	/**
	 * 
	 * 方法描述： 打开评级审批页面(提交给指定人)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-13 下午9:55:17
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getViewPoint2")
	public String getViewPoint2(Model model, String evalAppNo, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataGradeCardMap = new HashMap<String, Object>();
		FormData formevalapp1001 = formService.getFormData("evalapp1002");
		AppEval appEval = new AppEval();
		appEval.setEvalAppNo(evalAppNo);
		appEval = appEvalFeign.getById(appEval);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(appEval.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		if (BizPubParm.CUS_TYPE_PERS.equals(mfCusCustomer.getCusBaseType())) {
			formevalapp1001 = formService.getFormData("evalappropers1003");
		}
		String grade = getEvalLevel(appEval.getEvalScenceNo(), appEval.getTotalScore(), appEval.getGrade());// 获取机评等级
		appEval.setGrade(grade);
		appEval.setTotalScoreTmp(appEval.getTotalScore());
		if (StringUtil.isEmpty(appEval.getEvalType())||"1".equals(appEval.getEvalType())) {
			// 综合查询
			EvalCompreVal evalCompreVal = new EvalCompreVal();
			evalCompreVal.setEvalAppNo(evalAppNo);
			evalCompreVal.setEvalScenceNo(appEval.getEvalScenceNo());
			evalCompreVal = evalCompreValFeign.getById(evalCompreVal);
			evalCompreVal.setFinScorePercent(MathExtend.multiply(evalCompreVal.getFinScorePercent(), 100));
			evalCompreVal.setDlScorePercent(MathExtend.multiply(evalCompreVal.getDlScorePercent(), 100));
			evalCompreVal.setDxScorePercent(MathExtend.multiply(evalCompreVal.getDxScorePercent(), 100));
			EvalScenceRestrictVal evalScenceRestrictVal = new EvalScenceRestrictVal();
			evalScenceRestrictVal.setEvalAppNo(evalAppNo);
			evalScenceRestrictVal = evalScenceRestrictValFeign.getById(evalScenceRestrictVal);
			if (evalScenceRestrictVal != null) {
				evalCompreVal.setRestrictLevel(evalScenceRestrictVal.getEvalRestrictLevel());
			}
			getObjValue(formevalapp1001, evalCompreVal);
			dataGradeCardMap = appEvalFeign.getEvalListData(evalAppNo, appEval.getEvalScenceNo());
			dataGradeCardMap = JSONObject.fromObject(dataGradeCardMap);
			model.addAttribute("dataGradeCardMap", dataGradeCardMap);
		}
		//第一个审批岗位默认给定客户经理建议评级
		if(StringUtil.isEmpty(appEval.getApprovalGrade())){
			appEval.setApprovalGrade(appEval.getMangGrade());
		}
		getObjValue(formevalapp1001, appEval);
		// 检查审批的场景号
		String scNo = BizPubParm.SCENCE_TYPE_DOC_EXAM;

		dataMap.put("cusNo", appEval.getCusNo());
		dataMap.put("evalAppNo", appEval.getEvalAppNo());
		dataMap.put("scNo", scNo);
		ViewUtil.setViewPointParm(request, dataMap);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appEval.getEvalAppNo(), null);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formevalapp1001, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes(evalAppNo, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("scNo", scNo);
		model.addAttribute("formevalapp1001", formevalapp1001);
		model.addAttribute("evalAppNo", evalAppNo);
		model.addAttribute("query", "");
		model.addAttribute("appEval", appEval);
		return "/component/eval/WkfEvalViewPoint";
	}
	/**
	 * 
	 * 方法描述： 评级审批历史
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-3-14 下午2:56:32
	 */
	@RequestMapping("/appEvalApprovalHis")
	public String appEvalApprovalHis() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/eval/appEval_ApprovalHis";
	}

	private String getEvalLevel(String scenceNo, double totalScore, String grade) {
		List<EvalScoreGradeConfig> evalScoreGradeConfigList = new ArrayList<EvalScoreGradeConfig>();
		EvalScoreGradeConfig gradeConfig = new EvalScoreGradeConfig();
		evalScoreGradeConfigList = evalScoreGradeConfigFeign.getAll(gradeConfig);
		String evalLevel = "1";
		// 获取评级级别
		evalLevel = EvalUtil.JudgeComputingEvalLevel(evalScoreGradeConfigList, totalScore, evalLevel);
		if (Integer.parseInt(grade) > Integer.parseInt(evalLevel)) {
			evalLevel = grade;
		}
		return evalLevel;
	}

	/**
	 * 流程提交
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitUpdate")
	@ResponseBody
	public Map<String, Object> submitUpdate(String taskId,String ajaxData,String appNo,String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formevalapp1001 = formService.getFormData("evalapp1001");
		getFormValue(formevalapp1001, getMapByJson(ajaxData));
		Map<String, Object> dataMap = new HashMap<String, Object>();
		AppEvalWkf appEvalWkf = new AppEvalWkf();
		setObjValue(formevalapp1001, appEvalWkf);
		if (taskId != null) {
			if (taskId.indexOf(",") != -1) {
				taskId = taskId.substring(0, taskId.indexOf(","));
			}
		}
		AppEval appEval = new AppEval();
		setObjValue(formevalapp1001, appEval);
		dataMap = getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		
		
		/*if(StringUtil.isNotEmpty((String)dataMap.get("nextUser"))){
			String nextUser = String.valueOf(dataMap.get("nextUser"));
			appEvalWkf.setNextUser(nextUser);
		}*/
		Result res;
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("orgNo", User.getOrgNo(request));
		new FeignSpringFormEncoder().addParamsToBaseDomain(appEval);
		dataMap.put("appEval", appEval);
		res = appEvalWkfFeign.doCommit(taskId, appNo, opinionType, approvalOpinion, appEvalWkf.getTransition(),
				User.getRegNo(request),nextUser, dataMap);
		if (!res.isSuccess()) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		} else {
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		}
		return dataMap;
	}

}
