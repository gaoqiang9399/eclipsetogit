package  app.component.compensatory.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
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

import app.component.compensatory.entity.MfBusCompensatoryApplyDetail;
import app.component.compensatory.entity.MfBusCompensatoryApprove;
import app.component.compensatory.entity.MfBusCompensatoryApproveHis;
import app.component.compensatory.feign.MfBusCompensatoryApplyDetailFeign;
import app.component.compensatory.feign.MfBusCompensatoryApproveFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusCompensatoryApproveAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:00:11 CST 2018
 **/
@Controller
@RequestMapping("/mfBusCompensatoryApprove")
public class MfBusCompensatoryApproveController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusCompensatoryApplyDetailFeign mfBusCompensatoryApplyDetailFeign;
	@Autowired
	private MfBusCompensatoryApproveFeign mfBusCompensatoryApproveFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
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
		FormData formcompensatoryApprove0001 = formService.getFormData("compensatoryApprove0001");
		MfBusCompensatoryApprove mfBusCompensatoryApprove = new MfBusCompensatoryApprove();
		mfBusCompensatoryApprove.setId(id);
		mfBusCompensatoryApprove = mfBusCompensatoryApproveFeign.getById(mfBusCompensatoryApprove);
		//获取代偿类型
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(mfBusCompensatoryApprove.getCompensatoryId());
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);
		String fincId = mfBusCompensatoryApprove.getFincId();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		mfBusCompensatoryApprove.setApproveRemark("");
		getObjValue(formcompensatoryApprove0001, mfBusFincApp);
		getObjValue(formcompensatoryApprove0001, mfBusCompensatoryApprove);
		getObjValue(formcompensatoryApprove0001, mfBusCompensatoryApply);

		//获得代偿详情
		MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail = new MfBusCompensatoryApplyDetail();
		mfBusCompensatoryApplyDetail.setDetailedType("2");
		mfBusCompensatoryApplyDetail.setFincId(fincId);
		List<MfBusCompensatoryApplyDetail> mfBusCompensatoryApplyDetailList = mfBusCompensatoryApplyDetailFeign.getDetailList(mfBusCompensatoryApplyDetail);
		String activityType = taskAppro.getActivityType();
		//处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),opinionTypeMap);
		this.changeFormProperty(formcompensatoryApprove0001, "approveResult", "optionArray", opinionTypeList);
		model.addAttribute("formcompensatoryApprove0001", formcompensatoryApprove0001);
		model.addAttribute("query", "");
		model.addAttribute("mfBusCompensatoryApprove", mfBusCompensatoryApprove);
		model.addAttribute("mfBusCompensatoryApplyDetailList", mfBusCompensatoryApplyDetailList);
		return "/component/compensatory/MfBusCompensatoryApprove";
	}
	
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData,String appNo, String taskId, String transition,
			String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String [] jsonlsit = ajaxData.split("],");
		String ajaxDataList ="";
		for(int i = 0; i<jsonlsit.length; i++){
			if(i<jsonlsit.length-1){
				ajaxDataList+=jsonlsit[i];
			}else{
				ajaxData=jsonlsit[i];
			}
		}
		ajaxDataList+="]";
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formCompensatoryApprove0001 = formService.getFormData("compensatoryApprove0001");
		getFormValue(formCompensatoryApprove0001, formDataMap);
		MfBusCompensatoryApprove mfBusCompensatoryApprove = new MfBusCompensatoryApprove();
		MfBusCompensatoryApproveHis mfBusCompensatoryApproveHis = new MfBusCompensatoryApproveHis();
		setObjValue(formCompensatoryApprove0001, mfBusCompensatoryApprove);
		setObjValue(formCompensatoryApprove0001, mfBusCompensatoryApproveHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusCompensatoryApprove);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusCompensatoryApproveHis);
			mfBusCompensatoryApprove.setApproveDetailStr(ajaxDataList);
			formDataMap.put("mfBusCompensatoryApprove", mfBusCompensatoryApprove);
			formDataMap.put("mfBusCompensatoryApproveHis", mfBusCompensatoryApproveHis);
			res = mfBusCompensatoryApproveFeign.doCommit(taskId, transition, nextUser,
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
