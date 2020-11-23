package app.component.oa.dimission.controller;

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
import app.component.oa.dimission.entity.MfOaDimission;
import app.component.oa.dimission.entity.MfOaDimissionHis;
import app.component.oa.dimission.feign.MfOaDimissionFeign;
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
@RequestMapping("/mfOaDimission")
public class MfOaDimissionController extends BaseFormBean {
	@Autowired
	private MfOaDimissionFeign mfOaDimissionFeign;
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
		return "/component/oa/dimission/MfOaDimission_List";
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
		MfOaDimission mfOaDimission = new MfOaDimission();
		try {
			mfOaDimission.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaDimission.setCriteriaList(mfOaDimission, ajaxData);// 我的筛选
			mfOaDimission.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaDimission", mfOaDimission));
			ipage = mfOaDimissionFeign.findByPage(ipage);
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
			FormData formdimission0002 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaDimissionAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formdimission0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdimission0002)) {
				MfOaDimission mfOaDimission = new MfOaDimission();
				setObjValue(formdimission0002, mfOaDimission);
				mfOaDimission.setDimissionId(WaterIdUtil.getWaterId());
				mfOaDimissionFeign.insert(mfOaDimission);
				mfOaDimission = mfOaDimissionFeign.submitProcess(mfOaDimission);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaDimission.getApproveNodeName());
				paramMap.put("opNo", mfOaDimission.getApprovePartName());
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
	public String getViewPoint(Model model, String dimissionId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaDimission mfOaDimission = new MfOaDimission();
		mfOaDimission.setDimissionId(dimissionId);
		mfOaDimission = mfOaDimissionFeign.getById(mfOaDimission);
		mfOaDimission.setApprovePartNo(null);
		mfOaDimission.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(dimissionId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formDimissionApprove = formService.getFormData("dimission0003");
		// 实体对象放到表单对象中
		getObjValue(formDimissionApprove, mfOaDimission);
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
		model.addAttribute("dimissionId", dimissionId);
		model.addAttribute("mfOaDimission", mfOaDimission);
		model.addAttribute("formDimissionApprove", formDimissionApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/oa/dimission/WkfDimissionViewPoint";
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
		FormData formDimissionApprove = formService.getFormData("dimission0003");
		getFormValue(formDimissionApprove, formDataMap);
		MfOaDimission mfOaDimission = new MfOaDimission();
		MfOaDimissionHis mfOaDimissionHis = new MfOaDimissionHis();
		setObjValue(formDimissionApprove, mfOaDimission);
		setObjValue(formDimissionApprove, mfOaDimissionHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaDimission);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaDimissionHis);
			formDataMap.put("mfOaDimission", mfOaDimission);
			formDataMap.put("mfOaDimissionHis", mfOaDimissionHis);
			res = mfOaDimissionFeign.doCommit(taskId, transition, nextUser,
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
		FormData formdimission0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaDimissionAction", BizPubParm.SHOW_TYPE1));
		MfOaDimission mfOaDimission = new MfOaDimission();
		mfOaDimission.setOpNo(User.getRegNo(request));
		mfOaDimission.setOpName(User.getRegName(request));
		mfOaDimission.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formdimission0002, mfOaDimission);
		model.addAttribute("processId", BizPubParm.DIMISSION_NO);
		model.addAttribute("formdimission0002", formdimission0002);
		model.addAttribute("query", "");
		return "/component/oa/dimission/MfOaDimission_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String dimissionId, Model model,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaDimission mfOaDimission = new MfOaDimission();
		mfOaDimission.setDimissionId(dimissionId);
		mfOaDimission = mfOaDimissionFeign.getById(mfOaDimission);
		mfOaDimission.setRegTime(DateUtil.getShowDateTime(mfOaDimission.getRegTime()));
		FormData formdimission0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaDimissionAction", BizPubParm.SHOW_TYPE2));
		getObjValue(formdimission0001, mfOaDimission);
		model.addAttribute("formdimission0001", formdimission0001);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("mfOaDimission", mfOaDimission);
		model.addAttribute("dimissionId", dimissionId);
		model.addAttribute("query", "");
		return "/component/oa/dimission/MfOaDimission_Detail";
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
	public Map<String, Object> submitProcessAjax(String dimissionId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaDimission mfOaDimission = new MfOaDimission();
		mfOaDimission.setDimissionId(dimissionId);
		mfOaDimission = mfOaDimissionFeign.getById(mfOaDimission);
		try {
			mfOaDimission = mfOaDimissionFeign.submitProcess(mfOaDimission);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaDimission.getApproveNodeName());
			paramMap.put("opNo", mfOaDimission.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

}