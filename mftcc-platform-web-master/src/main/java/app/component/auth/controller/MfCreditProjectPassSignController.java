package  app.component.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.auth.entity.MfCreditProjectPassSign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.feign.MfCreditProjectPassSignFeign;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.common.BizPubParm;
import app.component.common.BizPubParm.WKF_NODE;
import app.component.prdctinterface.PrdctInterfaceFeign;
import app.component.sysInterface.SysInterfaceFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.WorkflowDwrFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;

/**
* Title: MfCreditProjectPassSignBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/
@Controller
@RequestMapping("/mfCreditProjectPassSign")
public class MfCreditProjectPassSignController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCreditProjectPassSignFeign mfCreditProjectPassSignFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired 
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private WorkflowDwrFeign  workflowDwrFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;
	/**
	 * 
	 * 方法描述：打开立项传签页面 
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2018年5月18日 上午8:43:12
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String query = "";
		String cusNo = request.getParameter("cusNo");
		String creditAppId = request.getParameter("creditAppId");
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		MfCreditProjectPassSign mfCreditProjectPassSign = new MfCreditProjectPassSign();
		mfCreditProjectPassSign.setCreditAppId(creditAppId);
		List<MfCreditProjectPassSign> mfCreditProjectPassSignList = mfCreditProjectPassSignFeign.getMfCreditProjectPassSignList(mfCreditProjectPassSign);
		mfCusCreditApply.setCreditAppId(creditAppId);
		//获得打开授信页面所需要的参数
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		/**
		 * 根据获得的初始化参数，处理授信页面使用的授信类型及表单
		 */
		//获得授信申请表单
		String kindNo = "credit"+mfCusCreditApply.getCreditModel();
		if ("2".equals(mfCusCreditApply.getProjectType())) {//非风险
			kindNo = "credit"+mfCusCreditApply.getCreditModel()+"-"+mfCusCreditApply.getProjectType();
		}
		String formId = prdctInterfaceFeign.getFormId(kindNo, WKF_NODE.pass_sign, null, null, User.getRegNo(request));
		FormData formpassSignBase = formService.getFormData(formId);//原始String.valueOf(dataMap.get("formId"))
		mfCreditProjectPassSign.setPassSignBrNo(User.getOrgNo(request));
		mfCreditProjectPassSign.setPassSignBrName(User.getOrgName(request));
		List<MfCreditProjectPassSign> projectPassSignList = mfCreditProjectPassSignFeign.getMfCreditProjectPassSignList(mfCreditProjectPassSign);
		if (projectPassSignList!=null&&projectPassSignList.size()>0) {
			mfCreditProjectPassSign = projectPassSignList.get(0);
		}else{
			query = "query";
		}
		getObjValue(formpassSignBase, mfCusCreditApply);
		getObjValue(formpassSignBase, mfCreditProjectPassSign);
		if ("query".equals(query)) {
			//设置表单元素不可编辑
			FormActive[] list = formpassSignBase.getFormActives();
			for (int i = 0; i < list.length; i++) {
				FormActive formActive = list[i];
				formActive.setReadonly("1");
			}
			query="";
		}
		//request.setAttribute("currbrNo", User.getOrgNo(request));
		/*SysUser sysUser = new SysUser();
		sysUser.setBrNo(User.getOrgNo(request));
		List<SysUser>  sysUserList = sysInterfaceFeign.getAllUser(sysUser);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < sysUserList.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", sysUserList.get(i).getOpNo());
			jsonObject.put("name", sysUserList.get(i).getOpName());
			jsonArray.add(jsonObject);
		}*/
		//model.addAttribute("sysUserList", jsonArray.toString());
		model.addAttribute("formpassSignBase", formpassSignBase);
		model.addAttribute("mfCreditProjectPassSignList", mfCreditProjectPassSignList);
		model.addAttribute("creditAppId", mfCusCreditApply.getCreditAppId());
		model.addAttribute("wkfAppId", mfCusCreditApply.getWkfAppId());
		model.addAttribute("currBrNo", User.getOrgNo(request));
		model.addAttribute("cusNo", mfCusCreditApply.getCusNo());
		model.addAttribute("query", query);
		model.addAttribute("nodeNo", WKF_NODE.pass_sign.getNodeNo());
		return "/component/auth/MfCreditProjectPassSign_Insert";
	}
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData,String passSignList) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditProjectPassSign mfCreditProjectPassSign = new MfCreditProjectPassSign();
		try {
			dataMap=getMapByJson(ajaxData);
			//String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.credit_apply, null, null, User.getRegNo(request));
			FormData formpassSignBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formpassSignBase, getMapByJson(ajaxData));
			if(this.validateFormData(formpassSignBase)){
				setObjValue(formpassSignBase, mfCreditProjectPassSign);
				JSONArray passSignListArray = JSONArray.fromObject(passSignList);
				for (int i = 0; i < passSignListArray.size(); i++) {
					
				}
				
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			mfCreditProjectPassSign = mfCreditProjectPassSignFeign.insert(mfCreditProjectPassSign);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/delete")
	public void delete(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws Exception{
		mfCreditProjectPassSignFeign.delete(mfCreditProjectPassSign);
	}

	
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String passSignList,String creditAppId,String wkfAppId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditProjectPassSign mfCreditProjectPassSign = new MfCreditProjectPassSign();
		try {
			dataMap=getMapByJson(ajaxData);
			//String formId = prdctInterfaceFeign.getFormId("credit", WKF_NODE.pass_sign, null, null, User.getRegNo(request));
			FormData formpassSignBase = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formpassSignBase, getMapByJson(ajaxData));
			if(this.validateFormData(formpassSignBase)){
				setObjValue(formpassSignBase, mfCreditProjectPassSign);
				dataMap = mfCreditProjectPassSignFeign.updateProjectPassSign(mfCreditProjectPassSign);
				boolean finishFlag = Boolean.valueOf((boolean) dataMap.get("finishFlag"));
				String passSignResult = String.valueOf(dataMap.get("passSignResult"));
				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(creditAppId);
				//全部传签且通过。更新授信申请表中传签结果字段，提交业务流程
				if (finishFlag&&"1".equals(passSignResult)) {
					//业务进入下一个流程
					TaskImpl task= wkfInterfaceFeign.getTask(wkfAppId, null);
					String transition=workflowDwrFeign.findNextTransition(task.getId());
					Result result= wkfInterfaceFeign.doCommit(task.getId(),AppConstant.OPINION_TYPE_ARREE, "", transition, User.getRegNo(request), "");
					if(result!=null && result.isSuccess()){
						task= wkfInterfaceFeign.getTask(wkfAppId, null);
						mfCusCreditApply.setBusStage(task.getDescription());
						mfCusCreditApply.setNodeNo(task.getActivityName());
						mfCusCreditApply.setPassSignResult(passSignResult);
						mfCusCreditApply.setPassSignFinishTime(DateUtil.getDateTime());
						mfCusCreditApply.setLstModTime(mfCusCreditApply.getPassSignFinishTime());
						mfCusCreditApplyFeign.update(mfCusCreditApply);
						result.setMsg(MessageEnum.SUCCEED_APPROVAL_TONEXT.getMessage(task.getDescription()));
						dataMap.put("flag", "success");
					}else{
						dataMap.put("flag", "error");
					}
					dataMap.put("msg",result.getMsg());
				}else if (finishFlag&&"2".equals(passSignResult)){
					//全部传签且不通过。终止申请
					String appSts=BizPubParm.CREDIT_VETO_STS;
					mfCusCreditApply.setCreditSts(appSts);
					mfCusCreditApply.setLstModTime(DateUtil.getDateTime());
					mfCusCreditApplyFeign.updateByAppIdCreditSts(mfCusCreditApply);
					dataMap.put("flag", "error");
					dataMap.put("msg", MessageEnum.FAILD_APPROVAL_REJECT.getMessage());
				}else{
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
					dataMap.put("flag", "success");
				}
				/*JSONArray passSignListArray = JSONArray.fromObject(passSignList);
				if (passSignListArray.size()>0) {
					JSONObject jsonObject = passSignListArray.getJSONObject(0);
					mfCreditProjectPassSign.setPassSignId(jsonObject.getString("passSignId"));
					mfCreditProjectPassSign.setCreditAppId(creditAppId);
					mfCreditProjectPassSign.setContactsOpNo(jsonObject.getString("contactsOpNo"));
					mfCreditProjectPassSign.setContactsOpName(jsonObject.getString("contactsOpName"));
					mfCreditProjectPassSign.setOpinionType(jsonObject.getString("opinionType"));
					mfCreditProjectPassSign.setPassSignReason(jsonObject.getString("passSignReason"));
					
				}*/
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	
	@RequestMapping(value = "/getById")
	public MfCreditProjectPassSign getById(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws Exception{
		return mfCreditProjectPassSignFeign.getById(mfCreditProjectPassSign);
	}

	
	@RequestMapping(value = "/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCreditProjectPassSign")MfCreditProjectPassSign mfCreditProjectPassSign) throws Exception{
		return mfCreditProjectPassSignFeign.findByPage(ipage, mfCreditProjectPassSign);
	}
}
