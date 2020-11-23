package app.component.pact.advanceloan.controller;

import app.base.User;
import app.component.nmd.entity.ParmDic;
import app.component.pact.advanceloan.entity.MfBusAdvanceLoan;
import app.component.pact.advanceloan.feign.MfBusAdvanceLoanFeign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
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
 * 提前放款
 */
@Controller
@RequestMapping("/mfBusAdvanceLoan")
public class MfBusAdvanceLoanController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private MfBusAdvanceLoanFeign mfBusAdvanceLoanFeign;

	@Autowired
	private MfBusPactFeign mfBusPactFeign;

	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	@RequestMapping(value = "/openAdvanceLoan")
	public String openAdvanceLoan(Model model, String appId, String pactId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData busAdvanceLoan = formService.getFormData("busAdvanceLoan");
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		MfBusAdvanceLoan mfBusAdvanceLoan = new MfBusAdvanceLoan();
		mfBusAdvanceLoan.setAppId(mfBusPact.getAppId());
		mfBusAdvanceLoan.setPactId(mfBusPact.getPactId());
		mfBusAdvanceLoan.setPactNo(mfBusPact.getPactNo());
		mfBusAdvanceLoan.setKindNo(mfBusPact.getKindNo());
		mfBusAdvanceLoan.setKindName(mfBusPact.getKindName());
		mfBusAdvanceLoan.setCusName(mfBusPact.getCusName());
		mfBusAdvanceLoan.setCusNo(mfBusPact.getCusNo());
		mfBusAdvanceLoan.setPactAmt(mfBusPact.getPactAmt());
		mfBusAdvanceLoan.setPutoutAmt(mfBusPact.getPactAmt());
		getObjValue(busAdvanceLoan, mfBusAdvanceLoan);
		model.addAttribute("busAdvanceLoan", busAdvanceLoan);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("pactId", pactId);
		model.addAttribute("cusNo", mfBusAdvanceLoan.getCusNo());
		return "/component/pact/advanceloan/MfBusAdvanceLoan_input";
	}

	/**
	 * AJAX新增
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
			Map map = getMapByJson(ajaxData);
			FormData formcusAdvanceLoan = formService.getFormData((String)map.get("formId"));
			getFormValue(formcusAdvanceLoan, map);
			if (this.validateFormData(formcusAdvanceLoan)) {
				MfBusAdvanceLoan mfBusAdvanceLoan = new MfBusAdvanceLoan();
				setObjValue(formcusAdvanceLoan, mfBusAdvanceLoan);
				MfBusAdvanceLoan mfBusAdvanceLoanTemp = mfBusAdvanceLoanFeign.getByPactId(mfBusAdvanceLoan.getPactId());
				if(null != mfBusAdvanceLoanTemp){
					dataMap.put("flag", "error");
					dataMap.put("msg", "此合同已经发起过提前放款，不能重复发起！");
					return dataMap;
				}
				if(mfBusAdvanceLoan.getPutoutAmt() > mfBusAdvanceLoan.getPactAmt()){
					dataMap.put("flag", "error");
					dataMap.put("msg", "放款金额不能大于合同金额！");
					return dataMap;
				}
				dataMap = mfBusAdvanceLoanFeign.insert(mfBusAdvanceLoan);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	 *
	 * 方法描述： 提前放款审核页面
	 * @param model
	 * @param advanceLoanId
	 * @return
	 * @throws Exception
	 * String
	 * @author 贾磊
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String advanceLoanId,String taskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusAdvanceLoan mfBusAdvanceLoan = mfBusAdvanceLoanFeign.getById(advanceLoanId);
		String formId = "busAdvanceLoanApprove";
		FormData busAdvanceLoanApprove = formService.getFormData(formId);
		getObjValue(busAdvanceLoanApprove, mfBusAdvanceLoan);
		TaskImpl taskApprove = wkfInterfaceFeign.getTask(advanceLoanId, null);
		String activityType = taskApprove.getActivityType();
		// 处理审批意见类型
		List<OptionsList> opinionTypeListNew = new ArrayList<OptionsList>();
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskApprove.getCouldRollback(), null);
		for (int i = 0; i < opinionTypeList.size(); i++) {
			OptionsList optionsList = opinionTypeList.get(i);
			if("1".equals(optionsList.getOptionValue())||"2".equals(optionsList.getOptionValue())||"5".equals(optionsList.getOptionValue())){
				opinionTypeListNew.add(optionsList);
			}
		}
		this.changeFormProperty(busAdvanceLoanApprove, "opinionType", "optionArray", opinionTypeListNew);
		//发起人，补充资料
		if("1586336951990".equals(taskApprove.getName())){
			this.changeFormProperty(busAdvanceLoanApprove, "putoutAmt", "readOnly", false);
			this.changeFormProperty(busAdvanceLoanApprove, "remark", "readOnly", false);
		}
		model.addAttribute("busAdvanceLoanApprove", busAdvanceLoanApprove);
		model.addAttribute("query", "");
		model.addAttribute("advanceLoanId", advanceLoanId);
		model.addAttribute("taskId", taskId);
		///component/pact/advanceloan/MfBusChargeFee_approve
		return "/component/pact/advanceloan/MfBusAdvanceLoan_approve";
	}

	/**
	 *
	 * 方法描述： 审批提交（审批意见保存）
	 * @return
	 * @throws Exception
	 * String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doSubmitAjax")
	@ResponseBody
	public Map<String, Object> doSubmitAjax(String ajaxData, String ajaxDataList, String taskId, String advanceLoanId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		advanceLoanId = (String) map.get("advanceLoanId");
		MfBusAdvanceLoan mfBusAdvanceLoan = mfBusAdvanceLoanFeign.getById(advanceLoanId);
		FormData busAdvanceLoanApprove = formService.getFormData((String) map.get("formId"));
		getFormValue(busAdvanceLoanApprove, map);
		setObjValue(busAdvanceLoanApprove, mfBusAdvanceLoan);
		dataMap = getMapByJson(ajaxData);
		if(mfBusAdvanceLoan.getPutoutAmt() > mfBusAdvanceLoan.getPactAmt()){
			dataMap.put("flag", "error");
			dataMap.put("msg", "放款金额不能大于合同金额！");
			return dataMap;
		}
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
		try {
			dataMap.put("mfBusAdvanceLoan", mfBusAdvanceLoan);
			dataMap.put("approvalOpinion", approvalOpinion);
			Result res = mfBusAdvanceLoanFeign.doCommit(taskId, advanceLoanId, opinionType,approvalOpinion ,transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;

	}

}
