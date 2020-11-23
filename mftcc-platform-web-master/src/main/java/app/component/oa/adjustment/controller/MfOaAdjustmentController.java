package app.component.oa.adjustment.controller;

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
import app.component.oa.adjustment.entity.MfOaAdjustment;
import app.component.oa.adjustment.entity.MfOaAdjustmentHis;
import app.component.oa.adjustment.feign.MfOaAdjustmentFeign;
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
@RequestMapping("/mfOaAdjustment")
public class MfOaAdjustmentController extends BaseFormBean {
	@Autowired
	private MfOaAdjustmentFeign mfOaAdjustmentFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/oa/adjustment/MfOaAdjustment_List";
	}

	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		try {
			mfOaAdjustment.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaAdjustment.setCriteriaList(mfOaAdjustment, ajaxData);// 我的筛选
			mfOaAdjustment.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaAdjustment", mfOaAdjustment));
			ipage = mfOaAdjustmentFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formadjustment0002 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaAdjustmentAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formadjustment0002, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formadjustment0002)) {
				MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
				setObjValue(formadjustment0002, mfOaAdjustment);
				mfOaAdjustment.setAdjustmentId(WaterIdUtil.getWaterId());
				mfOaAdjustmentFeign.insert(mfOaAdjustment);
				mfOaAdjustment = mfOaAdjustmentFeign.submitProcess(mfOaAdjustment);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaAdjustment.getApproveNodeName());
				paramMap.put("opNo", mfOaAdjustment.getApprovePartName());
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

	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String adjustmentId, String hideOpinionType, String activityType,
			String taskId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		mfOaAdjustment.setAdjustmentId(adjustmentId);
		mfOaAdjustment = mfOaAdjustmentFeign.getById(mfOaAdjustment);
		mfOaAdjustment.setApprovePartNo(null);
		mfOaAdjustment.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(adjustmentId, null);// 当前审批节点task
		JSONObject js = JSONObject.fromObject(taskAppro.getForms());
		// 初始化基本信息表单、工作经历表单
		FormData formAdjustmentApprove = formService.getFormData(js.get("name").toString());
		// 实体对象放到表单对象中
		getObjValue(formAdjustmentApprove, mfOaAdjustment);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formAdjustmentApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("adjustmentId", adjustmentId);
		model.addAttribute("mfOaAdjustment", mfOaAdjustment);
		model.addAttribute("formAdjustmentApprove", formAdjustmentApprove);
		model.addAttribute("query", "");
		return "/component/oa/adjustment/WkfAdjustmentViewPoint";
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
		FormData formAdjustmentApprove = formService.getFormData(js.get("name").toString());
		getFormValue(formAdjustmentApprove, formDataMap);
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		MfOaAdjustmentHis mfOaAdjustmentHis = new MfOaAdjustmentHis();
		setObjValue(formAdjustmentApprove, mfOaAdjustment);
		setObjValue(formAdjustmentApprove, mfOaAdjustmentHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaAdjustment);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaAdjustmentHis);
			formDataMap.put("mfOaAdjustment", mfOaAdjustment);
			formDataMap.put("mfOaAdjustmentHis", mfOaAdjustmentHis);
			res = mfOaAdjustmentFeign.doCommit(taskId, transition, nextUser,
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
		FormData formadjustment0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaAdjustmentAction", BizPubParm.SHOW_TYPE1));
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		mfOaAdjustment.setOpNo(User.getRegNo(request));
		mfOaAdjustment.setOpName(User.getRegName(request));
		mfOaAdjustment.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formadjustment0002, mfOaAdjustment);
		model.addAttribute("processId", BizPubParm.ADJUSTMENT_NO);
		model.addAttribute("formadjustment0002", formadjustment0002);
		model.addAttribute("query", "");
		return "/component/oa/adjustment/MfOaAdjustment_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String adjustmentId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		mfOaAdjustment.setAdjustmentId(adjustmentId);
		mfOaAdjustment = mfOaAdjustmentFeign.getById(mfOaAdjustment);
		mfOaAdjustment.setRegTime(DateUtil.getShowDateTime(mfOaAdjustment.getRegTime()));
		FormData formadjustment0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaAdjustmentAction", BizPubParm.SHOW_TYPE2));
		getObjValue(formadjustment0001, mfOaAdjustment);
		model.addAttribute("formadjustment0001", formadjustment0001);
		model.addAttribute("adjustmentId", adjustmentId);
		model.addAttribute("mfOaAdjustment", mfOaAdjustment);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("formadjustment0001", formadjustment0001);
		model.addAttribute("query", "");
		return "/component/oa/adjustment/MfOaAdjustment_Detail";
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
	public Map<String, Object> submitProcessAjax(String adjustmentId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaAdjustment mfOaAdjustment = new MfOaAdjustment();
		mfOaAdjustment.setAdjustmentId(adjustmentId);
		mfOaAdjustment = mfOaAdjustmentFeign.getById(mfOaAdjustment);
		try {
			mfOaAdjustment = mfOaAdjustmentFeign.submitProcess(mfOaAdjustment);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaAdjustment.getApproveNodeName());
			paramMap.put("opNo", mfOaAdjustment.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
}
