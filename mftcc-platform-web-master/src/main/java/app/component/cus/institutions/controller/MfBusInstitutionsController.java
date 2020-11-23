package app.component.cus.institutions.controller;

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
import app.component.common.BizPubParm;
import app.component.cus.institutions.entity.MfBusInstitutions;
import app.component.cus.institutions.feign.MfBusInstitutionsFeign;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/mfBusInstitutions")
public class MfBusInstitutionsController extends BaseFormBean {
	@Autowired
	private MfBusInstitutionsFeign mfBusInstitutionsFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/cus/institutions/MfBusInstitutions_List";
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
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		try {
			mfBusInstitutions.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusInstitutions.setCriteriaList(mfBusInstitutions, ajaxData);// 我的筛选
			mfBusInstitutions.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfBusInstitutions", mfBusInstitutions));
			ipage = mfBusInstitutionsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmfbusinstitutions = formService.getFormData("InstitutionsBase");
			getFormValue(formmfbusinstitutions, getMapByJson(ajaxData));
			if (this.validateFormData(formmfbusinstitutions)) {
				MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
				setObjValue(formmfbusinstitutions, mfBusInstitutions);
				mfBusInstitutions.setAgenciesId(WaterIdUtil.getWaterId());
				mfBusInstitutions.setUseFlag(BizPubParm.YES_NO_N);
				mfBusInstitutions.setDelFlag(BizPubParm.YES_NO_N);
				if("0".equals(getMapByJson(ajaxData).get("ext15"))){
					mfBusInstitutionsFeign.insert(mfBusInstitutions);
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}else{
					mfBusInstitutionsFeign.insert(mfBusInstitutions);
					mfBusInstitutions = mfBusInstitutionsFeign.submitProcess(mfBusInstitutions);
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("userRole", mfBusInstitutions.getApproveNodeName());
					paramMap.put("opNo", mfBusInstitutions.getApprovePartName());
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
					dataMap.put("flag", "success");
				}
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
	 * 
	 * 方法描述： 打开机构申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String agenciesId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		mfBusInstitutions = mfBusInstitutionsFeign.getById(mfBusInstitutions);
		mfBusInstitutions.setApprovePartNo(null);
		mfBusInstitutions.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(agenciesId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formDimissionApprove = formService.getFormData("InstitutionsDetail");
		// 实体对象放到表单对象中
		getObjValue(formDimissionApprove, mfBusInstitutions);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formDimissionApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("agenciesId", agenciesId);
		model.addAttribute("mfBusInstitutions", mfBusInstitutions);
		model.addAttribute("formDimissionApprove", formDimissionApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/cus/institutions/WkfDimissionViewPointIns";
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
			String nextUser,String opinionType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		formDataMap.put("opinionType", opinionType);
		// 初始化基本信息表单、工作经历表单
		FormData formDimissionApprove = formService.getFormData("InstitutionsDetail");
		getFormValue(formDimissionApprove, formDataMap);
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		setObjValue(formDimissionApprove, mfBusInstitutions);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusInstitutions);
			formDataMap.put("mfBusInstitutions", mfBusInstitutions);
			res = mfBusInstitutionsFeign.doCommit(taskId, transition, nextUser,
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

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmfbusinstitutions = formService.getFormData("InstitutionsBase");
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setOpNo(User.getRegNo(request));
		mfBusInstitutions.setOpName(User.getRegName(request));
		mfBusInstitutions.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formmfbusinstitutions, mfBusInstitutions);
		model.addAttribute("processId", BizPubParm.INSTITUTION_NO);
		model.addAttribute("formmfbusinstitutions", formmfbusinstitutions);
		model.addAttribute("query", "");
		return "/component/cus/institutions/MfBusInstitutions_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String agenciesId, Model model,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		mfBusInstitutions = mfBusInstitutionsFeign.getById(mfBusInstitutions);
		mfBusInstitutions.setRegTime(DateUtil.getShowDateTime(mfBusInstitutions.getRegTime()));
		FormData formmfbusinsdetail = formService.getFormData("InstitutionsDetail");
		getObjValue(formmfbusinsdetail, mfBusInstitutions);
		model.addAttribute("formmfbusinsdetail", formmfbusinsdetail);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("mfBusInstitutions", mfBusInstitutions);
		model.addAttribute("agenciesId", agenciesId);
		model.addAttribute("query", "");
		return "/component/institutions/MfBusInstitutions_Detail";
	}

	/**
	 * 
	 * 方法描述： 提交审批流程
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String agenciesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		mfBusInstitutions = mfBusInstitutionsFeign.getById(mfBusInstitutions);
		try {
			mfBusInstitutions = mfBusInstitutionsFeign.submitProcess(mfBusInstitutions);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfBusInstitutions.getApproveNodeName());
			paramMap.put("opNo", mfBusInstitutions.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

	/**
	 * 机构管理删除操作
	 * @param agenciesId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> delete(String agenciesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		try{
			mfBusInstitutionsFeign.delete(mfBusInstitutions);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 机构管理禁用操作
	 * @param agenciesId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeNoUseFlagAjax")
	@ResponseBody
	public Map<String, Object> changeNoUseFlag(String agenciesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		mfBusInstitutions.setUseFlag(BizPubParm.YES_NO_Y);
		try{
			mfBusInstitutionsFeign.update(mfBusInstitutions);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 机构管理启用操作
	 * @param agenciesId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/changeYesUseFlagAjax")
	@ResponseBody
	public Map<String, Object> changeYesUseFlag(String agenciesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusInstitutions mfBusInstitutions = new MfBusInstitutions();
		mfBusInstitutions.setAgenciesId(agenciesId);
		mfBusInstitutions.setUseFlag(BizPubParm.YES_NO_N);
		try{
			mfBusInstitutionsFeign.update(mfBusInstitutions);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		}catch (Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
			e.printStackTrace();
		}
		return dataMap;
	}
    @RequestMapping("/getAgenciesDataSourse")
    @ResponseBody
	public Map<String,Object> getAgenciesDataSourse(int pageNo,MfBusInstitutions mfBusInstitutions)throws Exception{
        ActionContext.initialize(request,response);
        Map<String,Object> dataMap = new HashMap<String, Object>();
        try{
            Ipage ipage = this.getIpage();
            ipage.setPageNo(pageNo);// 异步传页面翻页参数
            ipage.setParams(this.setIpageParams("sysOrg",mfBusInstitutions));
            ipage = mfBusInstitutionsFeign.getAgenciesDataSourse(ipage);
            dataMap.put("ipage", ipage);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataMap;
    }

}