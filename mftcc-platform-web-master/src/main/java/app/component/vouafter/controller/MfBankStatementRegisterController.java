package app.component.vouafter.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.vouafter.entity.MfBankStatementRegister;
import app.component.vouafter.entity.MfBankStatementRegisterHis;
import app.component.vouafter.entity.MfRefundManage;
import app.component.vouafter.feign.MfBankStatementRegisterFeign;
import app.component.vouafter.feign.MfRefundManageFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBankStatementRegisterController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfBankStatementRegister")
public class MfBankStatementRegisterController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBankStatementRegisterFeign mfBankStatementRegisterFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfRefundManageFeign mfRefundManageFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfBankStatementRegister_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		try {
			mfBankStatementRegister.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBankStatementRegister.setCriteriaList(mfBankStatementRegister, ajaxData);//我的筛选
			mfBankStatementRegister.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBankStatementRegister,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBankStatementRegister", mfBankStatementRegister));
			//自定义查询Feign方法
			ipage = mfBankStatementRegisterFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = getMapByJson(ajaxData);
		try {
			FormData formBankStatementRegisterbase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formBankStatementRegisterbase, paramMap);
			if (this.validateFormData(formBankStatementRegisterbase)) {
				MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
				setObjValue(formBankStatementRegisterbase, mfBankStatementRegister);
				mfBankStatementRegisterFeign.update(mfBankStatementRegister);
				mfBankStatementRegister = mfBankStatementRegisterFeign.submitProcess(mfBankStatementRegister);
				paramMap.put("userRole", mfBankStatementRegister.getApproveNodeName());
				paramMap.put("opNo", mfBankStatementRegister.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase, getMapByJson(ajaxData));
		MfBankStatementRegister mfBankStatementRegisterJsp = new MfBankStatementRegister();
		setObjValue(formvouafterbase, mfBankStatementRegisterJsp);
		MfBankStatementRegister mfBankStatementRegister = mfBankStatementRegisterFeign.getById(mfBankStatementRegisterJsp);
		if(mfBankStatementRegister!=null){
			try{
				mfBankStatementRegister = (MfBankStatementRegister)EntityUtil.reflectionSetVal(mfBankStatementRegister, mfBankStatementRegisterJsp, getMapByJson(ajaxData));
				mfBankStatementRegisterFeign.update(mfBankStatementRegister);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		try{
			FormData formvouafterbase = formService.getFormData("vouafterbase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfBankStatementRegister = new MfBankStatementRegister();
				setObjValue(formvouafterbase, mfBankStatementRegister);
				mfBankStatementRegisterFeign.update(mfBankStatementRegister);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		mfBankStatementRegister.setId(id);
		mfBankStatementRegister = mfBankStatementRegisterFeign.getById(mfBankStatementRegister);
		getObjValue(formvouafterbase, mfBankStatementRegister,formData);
		if(mfBankStatementRegister!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		mfBankStatementRegister.setId(id);
		try {
			mfBankStatementRegisterFeign.delete(mfBankStatementRegister);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
        mfBankStatementRegister.setId(id);
		mfBankStatementRegister = mfBankStatementRegisterFeign.getById(mfBankStatementRegister);

		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(mfBankStatementRegister.getRefundId());
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);
		mfRefundManage.setApproveNodeName(null);
		mfRefundManage.setApprovePartName(null);
		mfRefundManage.setRemark(null);

		FormData formBankStatementRegisterbase = formService.getFormData("BankStatementRegisterbase");
        getObjValue(formBankStatementRegisterbase, mfRefundManage);
		mfBankStatementRegister.setApproveNodeName(null);
		mfBankStatementRegister.setApprovePartName(null);
        getObjValue(formBankStatementRegisterbase, mfBankStatementRegister);
		model.addAttribute("formBankStatementRegisterbase", formBankStatementRegisterbase);
		model.addAttribute("id", id);
		model.addAttribute("refundId", mfRefundManage.getId());
        model.addAttribute("query", "");
        model.addAttribute("refundQueryFile", "query");
		String nodeNo = "refundManage";
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("mfBankStatementRegister", mfBankStatementRegister);
		model.addAttribute("relNo", mfBankStatementRegister.getRefundId());
		String temParm1 = "cusNo=" + mfBankStatementRegister.getCusNo() +
				"&appId=" + mfBankStatementRegister.getAppId() + "&pactId=" +
				mfBankStatementRegister.getPactId() + "&refundId=" + mfRefundManage.getId() + "&nodeNo=refundManage";
		model.addAttribute("temParm1", temParm1);
        return "/component/vouafter/MfBankStatementRegister_Insert";
    }
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initBusInfo")
	public String initBusInfo(Model model) throws Exception{
		ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formBankStatementRegisterinit = formService.getFormData("BankStatementRegisterinit");
        model.addAttribute("formBankStatementRegisterinit", formBankStatementRegisterinit);
        model.addAttribute("query", "");
		return "/component/vouafter/MfBankStatementRegister_InitBusInfo";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id,String from,String nodeNo) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formBankStatementRegisterdetail = formService.getFormData("BankStatementRegisterdetail");
		getFormValue(formBankStatementRegisterdetail);
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		mfBankStatementRegister.setId(id);
		mfBankStatementRegister = mfBankStatementRegisterFeign.getById(mfBankStatementRegister);

		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(mfBankStatementRegister.getRefundId());
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);
		mfRefundManage.setApproveNodeName(null);
		mfRefundManage.setApprovePartName(null);
		mfRefundManage.setRemark(null);
		getObjValue(formBankStatementRegisterdetail, mfRefundManage);
		getObjValue(formBankStatementRegisterdetail, mfBankStatementRegister);
		//节点要件关联编号
		model.addAttribute("id", id);
		model.addAttribute("relNo", mfRefundManage.getId());
		model.addAttribute("mfBankStatementRegister", mfBankStatementRegister);
		model.addAttribute("formBankStatementRegisterdetail", formBankStatementRegisterdetail);
		model.addAttribute("query", "query");
		model.addAttribute("refundId", mfRefundManage.getId());
		String refundQueryFile = "query";
//		if("supplement_data".equals(nodeNo)){
//			refundQueryFile = "";
//		}
		model.addAttribute("refundQueryFile", refundQueryFile);
		model.addAttribute("from", from);
		model.addAttribute("nodeNo", "refundManage");
		String temParm1 = "cusNo=" + mfBankStatementRegister.getCusNo() +
				"&appId=" + mfBankStatementRegister.getAppId() + "&pactId=" +
				mfBankStatementRegister.getPactId() + "&refundId=" + mfRefundManage.getId() + "&nodeNo=refundManage";
		model.addAttribute("temParm1", temParm1);
		return "/component/vouafter/MfBankStatementRegister_Detail";
	}

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBankStatementRegisterListPage")
	public String getBankStatementRegisterListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfBankStatementRegister_SelectList";
	}

	/***
	 * 列表数据查询
	 *
	 * @param ajaxData
	 * @param pageNo
	 * @param tableId
	 * @param tableType
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/findBankStatementRegisterByPageAjax")
	@ResponseBody
	public Map<String, Object> findBankStatementRegisterByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
											  String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		try {
			mfBankStatementRegister.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBankStatementRegister.setCriteriaList(mfBankStatementRegister, ajaxData);// 我的筛选
			mfBankStatementRegister.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBankStatementRegister", mfBankStatementRegister));
			ipage = mfBankStatementRegisterFeign.findBankStatementRegisterByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}

	/**
	 *
	 * 方法描述： 打开调薪调岗申请审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String id, String hideOpinionType, String taskId,
							   String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		mfBankStatementRegister.setId(id);
		mfBankStatementRegister = mfBankStatementRegisterFeign.getById(mfBankStatementRegister);
		mfBankStatementRegister.setApprovePartNo(null);
		mfBankStatementRegister.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		String formId = "BankStatementRegisterApprove";
		if("supplement_data".equals(nodeNo)){
			formId = "BankStatementRegistersupplementApprove";
		}
		// 初始化基本信息表单、工作经历表单
		FormData formBankStatementRegisterApprove = formService.getFormData(formId);
		MfRefundManage mfRefundManage = new MfRefundManage();
		mfRefundManage.setId(mfBankStatementRegister.getRefundId());
		mfRefundManage = mfRefundManageFeign.getById(mfRefundManage);

		getObjValue(formBankStatementRegisterApprove, mfRefundManage);
		// 实体对象放到表单对象中
		getObjValue(formBankStatementRegisterApprove, mfBankStatementRegister);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		if("agencyManage".equals(nodeNo)){
			hideOpinionType = hideOpinionType + "@4";
		}
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formBankStatementRegisterApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("id", id);
		model.addAttribute("mfBankStatementRegister", mfBankStatementRegister);
		model.addAttribute("formBankStatementRegisterApprove", formBankStatementRegisterApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/vouafter/WkfBankStatementRegisterViewPoint";
	}

	/**
	 *
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formBankStatementRegisterApprove = formService.getFormData("BankStatementRegisterApprove");
		getFormValue(formBankStatementRegisterApprove, formDataMap);
		MfBankStatementRegister mfBankStatementRegister = new MfBankStatementRegister();
		MfBankStatementRegisterHis mfBankStatementRegisterHis = new MfBankStatementRegisterHis();
		setObjValue(formBankStatementRegisterApprove, mfBankStatementRegister);
		setObjValue(formBankStatementRegisterApprove, mfBankStatementRegisterHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBankStatementRegister);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBankStatementRegisterHis);
			formDataMap.put("mfBankStatementRegister", mfBankStatementRegister);
			formDataMap.put("mfBankStatementRegisterHis", mfBankStatementRegisterHis);
			res = mfBankStatementRegisterFeign.doCommit(taskId, transition, nextUser,
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
