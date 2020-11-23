package app.component.oa.human.controller;

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
import app.component.oa.human.entity.MfOaHumanResources;
import app.component.oa.human.entity.MfOaHumanResourcesHis;
import app.component.oa.human.feign.MfOaHumanResourcesFeign;
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
@RequestMapping("/mfOaHumanResources")
public class MfOaHumanResourcesController extends BaseFormBean {
	@Autowired
	private MfOaHumanResourcesFeign mfOaHumanResourcesFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
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
		return "/component/oa/human/MfOaHumanResources_List";
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
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		try {
			mfOaHumanResources.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaHumanResources.setCriteriaList(mfOaHumanResources, ajaxData);// 我的筛选
			mfOaHumanResources.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfOaHumanResources,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaHumanResources", mfOaHumanResources));
			ipage = mfOaHumanResourcesFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
			FormData formhuman0002 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaHumanResourcesAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formhuman0002, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formhuman0002)) {
				MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
				setObjValue(formhuman0002, mfOaHumanResources);
				mfOaHumanResources.setAppId(WaterIdUtil.getWaterId());
				mfOaHumanResourcesFeign.insert(mfOaHumanResources);
				mfOaHumanResources = mfOaHumanResourcesFeign.submitProcess(mfOaHumanResources);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaHumanResources.getApproveNodeName());
				paramMap.put("opNo", mfOaHumanResources.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
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
	public Map<String, Object> submitProcessAjax(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		try {
			mfOaHumanResources = new MfOaHumanResources();
			mfOaHumanResources.setAppId(appId);
			mfOaHumanResources = mfOaHumanResourcesFeign.getById(mfOaHumanResources);
			mfOaHumanResources = mfOaHumanResourcesFeign.submitProcess(mfOaHumanResources);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaHumanResources.getApproveNodeName());
			paramMap.put("opNo", mfOaHumanResources.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "提交失败");
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
		FormData formhuman0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaHumanResourcesAction", BizPubParm.SHOW_TYPE1));
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		mfOaHumanResources.setAppDate(DateUtil.getDate());
		mfOaHumanResources.setOpName(User.getRegName(request));
		getObjValue(formhuman0002, mfOaHumanResources);
		model.addAttribute("processId", BizPubParm.HUMAN_RESOURCES_NO);
		model.addAttribute("formhuman0002", formhuman0002);
		model.addAttribute("query", "");
		return "/component/oa/human/MfOaHumanResources_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String appId, String applySts,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formhuman0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaHumanResourcesAction", BizPubParm.SHOW_TYPE2));
		getFormValue(formhuman0001);
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		mfOaHumanResources.setAppId(appId);
		mfOaHumanResources.setApplySts(applySts);
		mfOaHumanResources = mfOaHumanResourcesFeign.getById(mfOaHumanResources);
		getObjValue(formhuman0001, mfOaHumanResources);
		model.addAttribute("mfOaHumanResources", mfOaHumanResources);
		model.addAttribute("formhuman0001", formhuman0001);
		model.addAttribute("appId", appId);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/human/MfOaHumanResources_Detail";
	}

	/**
	 * 
	 * 方法描述： 打开人力需求申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String appId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		mfOaHumanResources.setAppId(appId);
		mfOaHumanResources = mfOaHumanResourcesFeign.getById(mfOaHumanResources);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formhumanapprove = formService.getFormData("humanapprove");
		// 实体对象放到表单对象中
		getObjValue(formhumanapprove, mfOaHumanResources);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formhumanapprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formhumanapprove", formhumanapprove);
		model.addAttribute("formhumanapprove", formhumanapprove);
		model.addAttribute("mfOaHumanResources", mfOaHumanResources);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/oa/human/WkfHumanResourcesViewPoint";
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
		FormData formhumanapprove = formService.getFormData("humanapprove");
		getFormValue(formhumanapprove, formDataMap);
		MfOaHumanResources mfOaHumanResources = new MfOaHumanResources();
		MfOaHumanResourcesHis mfOaHumanResourcesHis = new MfOaHumanResourcesHis();
		setObjValue(formhumanapprove, mfOaHumanResources);
		setObjValue(formhumanapprove, mfOaHumanResourcesHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaHumanResources);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaHumanResourcesHis);
			formDataMap.put("mfOaHumanResources", mfOaHumanResources);
			formDataMap.put("mfOaHumanResourcesHis", mfOaHumanResourcesHis);
			res = mfOaHumanResourcesFeign.doCommit(taskId, transition, nextUser, 
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
