package app.component.oa.trainingneeds.controller;

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
import app.component.oa.trainingneeds.entity.MfOaTrainingNeeds;
import app.component.oa.trainingneeds.entity.MfOaTrainingNeedsHis;
import app.component.oa.trainingneeds.feign.MfOaTrainingNeedsFeign;
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
@RequestMapping("/mfOaTrainingNeeds")
public class MfOaTrainingNeedsController extends BaseFormBean {
	@Autowired
	private MfOaTrainingNeedsFeign mfOaTrainingNeedsFeign;
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
		return "/component/oa/trainingneeds/MfOaTrainingNeeds_List";
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
	public Map<String, Object> findByPageAjax(String ajaxData, Ipage ipage, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		try {
			mfOaTrainingNeeds.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaTrainingNeeds.setCriteriaList(mfOaTrainingNeeds, ajaxData);// 我的筛选
		 	mfOaTrainingNeeds.setCustomSorts(ajaxData);// 自定义排序
//			Ipage ipage = this.getIpage();
//			ipage.setPageNo(pageNo);// 异步传页面翻页参数
//			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaTrainingNeeds", mfOaTrainingNeeds));
			ipage = mfOaTrainingNeedsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			FormData formtrainingneeds0002 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaTrainingNeedsAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formtrainingneeds0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtrainingneeds0002)) {
				MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
				setObjValue(formtrainingneeds0002, mfOaTrainingNeeds);
				mfOaTrainingNeeds.setOpName(User.getRegName(request));
				mfOaTrainingNeedsFeign.insert(mfOaTrainingNeeds);
				mfOaTrainingNeeds = mfOaTrainingNeedsFeign.submitProcess(mfOaTrainingNeeds);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaTrainingNeeds.getApproveNodeName());
				paramMap.put("opNo", mfOaTrainingNeeds.getApprovePartName());
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
	 * AJAX提交审批,如果第一次进入审批流程失败，在详情再次提交时进入此方法；
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String trainingNeedsId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		try {
			mfOaTrainingNeeds.setTrainingNeedsId(trainingNeedsId);
			mfOaTrainingNeeds = mfOaTrainingNeedsFeign.getById(mfOaTrainingNeeds);
			mfOaTrainingNeeds = mfOaTrainingNeedsFeign.submitProcess(mfOaTrainingNeeds);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaTrainingNeeds.getApproveNodeName());
			paramMap.put("opNo", mfOaTrainingNeeds.getApprovePartName());
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
	 * 
	 * 方法描述： 打开全职转兼职申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String trainingNeedsId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		mfOaTrainingNeeds.setTrainingNeedsId(trainingNeedsId);
		mfOaTrainingNeeds = mfOaTrainingNeedsFeign.getById(mfOaTrainingNeeds);
		mfOaTrainingNeeds.setApprovePartNo(null);
		mfOaTrainingNeeds.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(trainingNeedsId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formTrainingNeedsApprove = formService.getFormData("trainingNeeds0003");
		// 实体对象放到表单对象中
		getObjValue(formTrainingNeedsApprove, mfOaTrainingNeeds);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formTrainingNeedsApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("processId", BizPubParm.TRAINING_NEEDS_NO);
		model.addAttribute("formTrainingNeedsApprove", formTrainingNeedsApprove);
		model.addAttribute("trainingNeedsId", trainingNeedsId);
		model.addAttribute("mfOaTrainingNeeds", mfOaTrainingNeeds);
		model.addAttribute("query", "");
		return "/component/oa/trainingneeds/WkfTrainingNeedsViewPoint";
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
		FormData formTrainingNeedsApprove = formService.getFormData("trainingNeeds0003");
		getFormValue(formTrainingNeedsApprove, formDataMap);
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		MfOaTrainingNeedsHis mfOaTrainingNeedsHis = new MfOaTrainingNeedsHis();
		setObjValue(formTrainingNeedsApprove, mfOaTrainingNeeds);
		setObjValue(formTrainingNeedsApprove, mfOaTrainingNeedsHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaTrainingNeeds);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaTrainingNeedsHis);
			formDataMap.put("mfOaTrainingNeeds", mfOaTrainingNeeds);
			formDataMap.put("mfOaTrainingNeedsHis", mfOaTrainingNeedsHis);
			res = mfOaTrainingNeedsFeign.doCommit(taskId, transition, nextUser,
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrainingneeds0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaTrainingNeedsAction", BizPubParm.SHOW_TYPE1));
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		mfOaTrainingNeeds.setOpName(User.getRegName(request));
		mfOaTrainingNeeds.setApplyDate(DateUtil.getDate());
		String trainingNeedsId = WaterIdUtil.getWaterId();
		JSONArray resultMap = new JSONArray();
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("TRAINING_NEEDS_TYPE");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", map.getJSONObject(i).getString("optCode"));
			obj.put("name", map.getJSONObject(i).getString("optName"));
			resultMap.add(obj);
		}
		getObjValue(formtrainingneeds0002, mfOaTrainingNeeds);
		model.addAttribute("processId", BizPubParm.TRAINING_NEEDS_NO);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("trainingNeedsId", trainingNeedsId);
		model.addAttribute("formtrainingneeds0002", formtrainingneeds0002);
		model.addAttribute("query", "");
		return "/component/oa/trainingneeds/MfOaTrainingNeeds_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String trainingNeedsId, String applySts,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formtrainingneeds = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaTrainingNeedsAction", BizPubParm.SHOW_TYPE2));
		getFormValue(formtrainingneeds);
		MfOaTrainingNeeds mfOaTrainingNeeds = new MfOaTrainingNeeds();
		mfOaTrainingNeeds.setApplySts(applySts);
		mfOaTrainingNeeds.setTrainingNeedsId(trainingNeedsId);
		mfOaTrainingNeeds = mfOaTrainingNeedsFeign.getById(mfOaTrainingNeeds);
		getObjValue(formtrainingneeds, mfOaTrainingNeeds);
		model.addAttribute("applySts", applySts);
		model.addAttribute("trainingNeedsId", trainingNeedsId);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("formtrainingneeds", formtrainingneeds);
		model.addAttribute("query", "");
		return "/component/oa/trainingneeds/MfOaTrainingNeeds_Detail";
	}

}
