package app.component.oa.mattersreported.controller;

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
import app.component.oa.feign.MfOaFormConfigFeign;
import app.component.oa.mattersreported.entity.MfOaMattersReported;
import app.component.oa.mattersreported.entity.MfOaMattersReportedHis;
import app.component.oa.mattersreported.feign.MfOaMattersReportedFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mfOaMattersReported")
public class MfOaMattersReportedController extends BaseFormBean {
	@Autowired
	private MfOaMattersReportedFeign mfOaMattersReportedFeign;
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
		return "/component/oa/mattersreported/MfOaMattersReported_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		try {
			mfOaMattersReported.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaMattersReported.setCriteriaList(mfOaMattersReported, ajaxData);// 我的筛选
			mfOaMattersReported.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfOaMattersReported,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaMattersReported", mfOaMattersReported));
			ipage = mfOaMattersReportedFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String mattersReportedId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmattersreported0002 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaMattersReportedAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formmattersreported0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmattersreported0002)) {
				MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
				setObjValue(formmattersreported0002, mfOaMattersReported);
				mfOaMattersReportedFeign.insert(mfOaMattersReported);// 防止流程提交失败，信息丢失的方法
				mfOaMattersReported = mfOaMattersReportedFeign.submitProcess(mfOaMattersReported);// 提交流程
				mfOaMattersReported.setMattersReportedId(mattersReportedId);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaMattersReported.getApproveNodeName());
				paramMap.put("opNo", mfOaMattersReported.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX提交审批,如果第一次进入审批流程失败，在详情再次提交时进入此方法；
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String mattersReportedId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		try {
			mfOaMattersReported = new MfOaMattersReported();
			mfOaMattersReported.setMattersReportedId(mattersReportedId);
			mfOaMattersReported = mfOaMattersReportedFeign.getById(mfOaMattersReported);
			mfOaMattersReported = mfOaMattersReportedFeign.submitProcess(mfOaMattersReported);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaMattersReported.getApproveNodeName());
			paramMap.put("opNo", mfOaMattersReported.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "提交失败");
			throw new Exception(e.getMessage());
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
		FormData formmattersreported0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaMattersReportedAction", BizPubParm.SHOW_TYPE1));
		String mattersReportedId = WaterIdUtil.getWaterId();// 在页面使用上传文件的附件时需要传入编号(传入主键即可)
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		mfOaMattersReported.setMattersReportedId(mattersReportedId);
		mfOaMattersReported.setReportingTime(DateUtil.getDate());
		mfOaMattersReported.setSubmitJobs(User.getRegName(request));
		getObjValue(formmattersreported0002, mfOaMattersReported);
		JSONArray resultMap = new JSONArray();// 获取新增事项呈报类型--start
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("ITEM_CATEGORY");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", map.getJSONObject(i).getString("optCode"));
			obj.put("name", map.getJSONObject(i).getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("processId", BizPubParm.MATTERS_REPORTED_NO);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("mattersReportedId", mattersReportedId);
		model.addAttribute("regNo", User.getRegNo(request));
		model.addAttribute("formmattersreported0002", formmattersreported0002);
		model.addAttribute("query", "");
		return "/component/oa/mattersreported/MfOaMattersReported_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String mattersReportedId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmattersreported0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaMattersReportedAction", BizPubParm.SHOW_TYPE2));
		getFormValue(formmattersreported0001);
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		mfOaMattersReported.setMattersReportedId(mattersReportedId);
		mfOaMattersReported = mfOaMattersReportedFeign.getById(mfOaMattersReported);
		getObjValue(formmattersreported0001, mfOaMattersReported);
		model.addAttribute("formmattersreported0001", formmattersreported0001);
		model.addAttribute("mattersReportedId", mattersReportedId);
		model.addAttribute("mfOaMattersReported", mfOaMattersReported);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/mattersreported/MfOaMattersReported_Detail";
	}

	/**
	 * 
	 * 方法描述： 打开事项呈报申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String mattersReportedId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		mfOaMattersReported.setMattersReportedId(mattersReportedId);
		mfOaMattersReported = mfOaMattersReportedFeign.getById(mfOaMattersReported);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(mattersReportedId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formmattersreportedapprove = formService.getFormData("mattersreportedapprove");
		// 实体对象放到表单对象中
		getObjValue(formmattersreportedapprove, mfOaMattersReported);
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formmattersreportedapprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formmattersreportedapprove", formmattersreportedapprove);
		model.addAttribute("mattersReportedId", mattersReportedId);
		model.addAttribute("mfOaMattersReported", mfOaMattersReported);
		model.addAttribute("query", "");
		return "/component/oa/mattersreported/WkfMattersReportedViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批意见保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author
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
		FormData formmattersreportedapprove = formService.getFormData("mattersreportedapprove");
		getFormValue(formmattersreportedapprove, formDataMap);
		MfOaMattersReported mfOaMattersReported = new MfOaMattersReported();
		MfOaMattersReportedHis mfOaMattersReportedHis = new MfOaMattersReportedHis();
		setObjValue(formmattersreportedapprove, mfOaMattersReported);
		setObjValue(formmattersreportedapprove, mfOaMattersReportedHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaMattersReported);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaMattersReportedHis);
			formDataMap.put("mfOaMattersReported", mfOaMattersReported);
			formDataMap.put("mfOaMattersReportedHis", mfOaMattersReportedHis);
			res = mfOaMattersReportedFeign.doCommit(taskId, transition, nextUser, 
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
			throw e;
		}
		return dataMap;
	}
}
