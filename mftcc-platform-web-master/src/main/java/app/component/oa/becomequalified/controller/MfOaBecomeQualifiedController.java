package app.component.oa.becomequalified.controller;

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
import app.component.oa.becomequalified.entity.MfOaBecomeQualified;
import app.component.oa.becomequalified.entity.MfOaBecomeQualifiedHis;
import app.component.oa.becomequalified.feign.MfOaBecomeQualifiedFeign;
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
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mfOaBecomeQualified")
public class MfOaBecomeQualifiedController extends BaseFormBean {
	@Autowired
	private MfOaBecomeQualifiedFeign mfOaBecomeQualifiedFeign;
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
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("opNo", User.getRegNo(request));
		return "/component/oa/becomequalified/MfOaBecomeQualified_List";
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
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		try {
			mfOaBecomeQualified.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaBecomeQualified.setCriteriaList(mfOaBecomeQualified, ajaxData);// 我的筛选
			mfOaBecomeQualified.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaBecomeQualified", mfOaBecomeQualified));
			ipage = mfOaBecomeQualifiedFeign.findByPage(ipage);
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
			FormData formbecomequalified0002 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaBecomeQualifiedAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formbecomequalified0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbecomequalified0002)) {
				MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
				setObjValue(formbecomequalified0002, mfOaBecomeQualified);
				mfOaBecomeQualified.setBecomeQualifiedId(WaterIdUtil.getWaterId());
				mfOaBecomeQualifiedFeign.insert(mfOaBecomeQualified);
				mfOaBecomeQualified = mfOaBecomeQualifiedFeign.submitProcess(mfOaBecomeQualified);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaBecomeQualified.getApproveNodeName());
				paramMap.put("opNo", mfOaBecomeQualified.getApprovePartName());
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formbecomequalified0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaBecomeQualifiedAction", BizPubParm.SHOW_TYPE1));
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		mfOaBecomeQualified.setOpNo(User.getRegNo(request));
		mfOaBecomeQualified.setOpName(User.getRegName(request));
		mfOaBecomeQualified.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formbecomequalified0002, mfOaBecomeQualified);
		model.addAttribute("processId", BizPubParm.BECOME_QUALIFIEDID_NO);
		model.addAttribute("formbecomequalified0002", formbecomequalified0002);
		model.addAttribute("query", "");
		return "/component/oa/becomequalified/MfOaBecomeQualified_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String becomeQualifiedId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		mfOaBecomeQualified.setBecomeQualifiedId(becomeQualifiedId);
		mfOaBecomeQualified = mfOaBecomeQualifiedFeign.getById(mfOaBecomeQualified);
		mfOaBecomeQualified.setRegTime(DateUtil.getShowDateTime(mfOaBecomeQualified.getRegTime()));
		FormData formbecomequalified0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaBecomeQualifiedAction", BizPubParm.SHOW_TYPE2));
		getObjValue(formbecomequalified0001, mfOaBecomeQualified);
		model.addAttribute("formbecomequalified0001", formbecomequalified0001);
		model.addAttribute("becomeQualifiedId", becomeQualifiedId);
		model.addAttribute("mfOaBecomeQualified", mfOaBecomeQualified);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/becomequalified/MfOaBecomeQualified_Detail";
	}

	/**
	 * 
	 * 方法描述： 打开转正申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model,String becomeQualifiedId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		mfOaBecomeQualified.setBecomeQualifiedId(becomeQualifiedId);
		mfOaBecomeQualified = mfOaBecomeQualifiedFeign.getById(mfOaBecomeQualified);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(becomeQualifiedId, null);// 当前审批节点task
		JSONObject js = JSONObject.fromObject(taskAppro.getForms());
		// 初始化基本信息表单、工作经历表单
		FormData formBecomeQualifiedApprove = formService.getFormData(js.get("name").toString());
		// 实体对象放到表单对象中
		getObjValue(formBecomeQualifiedApprove, mfOaBecomeQualified);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formBecomeQualifiedApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("formBecomeQualifiedApprove", formBecomeQualifiedApprove);
		model.addAttribute("mfOaBecomeQualified", mfOaBecomeQualified);
		model.addAttribute("becomeQualifiedId", becomeQualifiedId);
		model.addAttribute("query", "");
		return "/component/oa/becomequalified/WkfBecomeQualifiedViewPoint";
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
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(appNo, null);// 当前审批节点task
		JSONObject js = JSONObject.fromObject(taskAppro.getForms());
		// 初始化基本信息表单、工作经历表单
		FormData formBecomeQualifiedApprove = formService.getFormData(js.get("name").toString());
		getFormValue(formBecomeQualifiedApprove, formDataMap);
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		MfOaBecomeQualifiedHis mfOaBecomeQualifiedHis = new MfOaBecomeQualifiedHis();
		setObjValue(formBecomeQualifiedApprove, mfOaBecomeQualified);
		setObjValue(formBecomeQualifiedApprove, mfOaBecomeQualifiedHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaBecomeQualified);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaBecomeQualifiedHis);
			formDataMap.put("mfOaBecomeQualified", mfOaBecomeQualified);
			formDataMap.put("mfOaBecomeQualifiedHis", mfOaBecomeQualifiedHis);
			res = mfOaBecomeQualifiedFeign.doCommit(taskId, transition, nextUser,
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
	public Map<String, Object> submitProcessAjax(String becomeQualifiedId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaBecomeQualified mfOaBecomeQualified = new MfOaBecomeQualified();
		mfOaBecomeQualified.setBecomeQualifiedId(becomeQualifiedId);
		mfOaBecomeQualified = mfOaBecomeQualifiedFeign.getById(mfOaBecomeQualified);
		try {
			mfOaBecomeQualified = mfOaBecomeQualifiedFeign.submitProcess(mfOaBecomeQualified);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaBecomeQualified.getApproveNodeName());
			paramMap.put("opNo", mfOaBecomeQualified.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

}
