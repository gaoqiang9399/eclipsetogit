package app.component.oa.leave.controller;

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
import app.component.common.EntityUtil;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.component.oa.leave.entity.MfOaLeave;
import app.component.oa.leave.entity.MfOaLeaveWkf;
import app.component.oa.leave.feign.MfOaLeaveFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkf.feign.TaskFeign;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/mfOaLeave")
public class MfOaLeaveController extends BaseFormBean {
	@Autowired
	private MfOaLeaveFeign mfOaLeaveFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private ModelInterfaceFeign modelInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TaskFeign taskFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String opNo = User.getRegNo(request);
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setOpNo(opNo);
		dataMap = mfOaLeaveFeign.getTotalById(opNo);
		List<MfOaLeave> mfOaLeaveList = mfOaLeaveFeign.findAllList(mfOaLeave);
		for (MfOaLeave mfOaLeave1 : mfOaLeaveList) {
			mfOaLeave1.setStartTime(DateUtil.getShowDateTime(mfOaLeave1.getStartTime()));
			mfOaLeave1.setEndTime(DateUtil.getShowDateTime(mfOaLeave1.getEndTime()));
		}
		String fileName = modelInterfaceFeign.getTemplateFileNameById(BizPubParm.OA_TEMPLATE_NO6);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("fileName", fileName);
		model.addAttribute("opNo", opNo);
		model.addAttribute("mfOaLeaveList", mfOaLeaveList);
		return "/component/oa/leave/MfOaLeave_List";
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
		MfOaLeave mfOaLeave = new MfOaLeave();
		try {
			mfOaLeave.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaLeave.setCriteriaList(mfOaLeave, ajaxData);// 我的筛选
			mfOaLeave.setCustomSorts(ajaxData);
			// this.getRoleConditions(mfOaLeave,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaLeave", mfOaLeave));
			ipage = mfOaLeaveFeign.findByPage(ipage);
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
			FormData formoaleave0001 = formService.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaLeaveAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formoaleave0001, getMapByJson(ajaxData));
			if (this.validateFormData(formoaleave0001)) {
				MfOaLeave mfOaLeave = new MfOaLeave();
				setObjValue(formoaleave0001, mfOaLeave);
				String[] strs = mfOaLeave.getTimeSum().split("天");// 将不想要的字符过滤掉，生成字符串数组形式的
				mfOaLeave.setTimeSum(strs[0]);
				String startTime = mfOaLeave.getStartTime();
				mfOaLeave.setStartTime(startTime.substring(0, 4) + startTime.substring(5, 7)
						+ startTime.substring(8, 10) + startTime.substring(10, 19));
				String endTime = mfOaLeave.getEndTime();
				mfOaLeave.setEndTime(endTime.substring(0, 4) + endTime.substring(5, 7) + endTime.substring(8, 10)
						+ endTime.substring(10, 19));
				mfOaLeave.setLeaveSts("1");
				mfOaLeave = mfOaLeaveFeign.insertForSubmit(mfOaLeave);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaLeave.getApprovalNodeName());
				paramMap.put("opNo", mfOaLeave.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				mfOaLeaveFeign.insert(mfOaLeave);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
		}
		return dataMap;
	}

	/**
	 * 点击操作按钮跳转到审批页面
	 * 
	 * @return
	 * @throw Exception
	 */
	@RequestMapping("/applyProcess")
	public String applyProcess(Model model,String leaveNo, String taskId, String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();

		FormData formoaleave0003 = formService.getFormData("oaleave0003");
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setLeaveNo(leaveNo);
		mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
		getObjValue(formoaleave0003, mfOaLeave);
		if (taskId.indexOf(",") != -1) {
			taskId = taskId.substring(0, taskId.indexOf(","));
		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(null, taskId);
		// 处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), null);
		this.changeFormProperty(formoaleave0003, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formoaleave0003", formoaleave0003);
		model.addAttribute("query", "");
		return "/component/oa/leave/MfOaLeave_ApplyDetail";
	}

	/**
	 * 请假单的审批处理
	 * 
	 * @return
	 * @throw Exception
	 */
	@RequestMapping("/submitForUpdate")
	@ResponseBody
	public Map<String, Object> submitForUpdate(String ajaxData, String appNo, String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formoaleave0004 = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formoaleave0004, dataMap);
			MfOaLeaveWkf mfOaLeaveWkf = new MfOaLeaveWkf();
			String taskId = (String) dataMap.get("taskId");
			setObjValue(formoaleave0004, mfOaLeaveWkf);
			// 判断审批意见是否 为空
			String approvalOpinionNew = mfOaLeaveWkf.getApprovalOpinion();
			if (StringUtil.isEmpty(approvalOpinionNew)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.NOT_FORM_EMPTY.getMessage("审批意见"));
				return dataMap;
			}

			String opinionType = mfOaLeaveWkf.getOpinionType();
			MfOaLeave mfOaLeave = new MfOaLeave();
			mfOaLeave.setLeaveNo(appNo);
			mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
			String transition = "";
			if (AppConstant.OPINION_TYPE_ARREE.equals(opinionType)) {// 同意
				transition = taskFeign.getTransitionsStr(taskId);
				/* appAuth.setAppSts(BizPubParm.APP_STS_PASS); */
			} else if (AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)) {// 不同意（否决
				transition = taskFeign.getTransitionsStr(taskId);
			} else if (AppConstant.OPINION_TYPE_ROLLBACK.equals(opinionType)) {// 退回上一环节

			} else if (AppConstant.OPINION_TYPE_RESTART.equals(opinionType)) {// 发回初审
				transition = null;
			}else {
			}
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaLeave);
			dataMap.put("mfOaLeave", mfOaLeave);
			// this.appAuthBo.updateForSubmit(appAuth);
			Result res = mfOaLeaveFeign.updateForSubmit1(taskId, appNo, mfOaLeaveWkf.getOpinionType(),
					mfOaLeaveWkf.getApprovalOpinion(), transition, User.getRegNo(this.getHttpRequest()), nextUser,
					dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoaleave0002 = formService.getFormData("oaleave0002");
		getFormValue(formoaleave0002, getMapByJson(ajaxData));
		MfOaLeave mfOaLeaveJsp = new MfOaLeave();
		setObjValue(formoaleave0002, mfOaLeaveJsp);
		MfOaLeave mfOaLeave = mfOaLeaveFeign.getById(mfOaLeaveJsp);
		if (mfOaLeave != null) {
			try {
				mfOaLeave = (MfOaLeave) EntityUtil.reflectionSetVal(mfOaLeave, mfOaLeaveJsp, getMapByJson(ajaxData));
				mfOaLeaveFeign.update(mfOaLeave);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String leaveSts) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaLeave mfOaLeave = new MfOaLeave();
		try {
			FormData formoaleave0001 = formService.getFormData("oaleave0001");
			getFormValue(formoaleave0001, getMapByJson(ajaxData));
			if (this.validateFormData(formoaleave0001)) {
				mfOaLeave = new MfOaLeave();
				setObjValue(formoaleave0001, mfOaLeave);
				// leaveSts=0 只更新请假单的信息，leaveSts=1 保存请假单的信息，并且启动请假流程。
				if ("0".equals(leaveSts)) {
					mfOaLeave.setLeaveSts("0");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				} else {
					mfOaLeave.setLeaveSts("1");
					mfOaLeave = mfOaLeaveFeign.insertForSubmit(mfOaLeave);
					dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL_LEAVE.getMessage());
				}
				mfOaLeaveFeign.update(mfOaLeave);
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 重新提交请假申请或者撤销申请
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2017-2-18 下午4:29:33
	 */
	@RequestMapping("/updateLeaveStsAjax")
	@ResponseBody
	public Map<String, Object> updateLeaveStsAjax(String leaveNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaLeave mfOaLeave = new MfOaLeave();
		try {
			mfOaLeave.setLeaveNo(leaveNo);
			mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
			if ("1".equals(mfOaLeave.getLeaveSts())) {
				mfOaLeave.setLeaveSts("0");
				mfOaLeave = mfOaLeaveFeign.deletForSubmit(mfOaLeave);
				dataMap.put("msg", "申请已撤销!");
			} else {
				MfOaLeave mfOaLeavenew = mfOaLeave;
				mfOaLeaveFeign.delete(mfOaLeave);
				mfOaLeavenew.setLeaveNo(WaterIdUtil.getWaterId());
				mfOaLeavenew.setApprovalNodeName(null);
				mfOaLeavenew.setLstModTime(null);
				mfOaLeavenew.setLeaveSts("1");
				mfOaLeaveFeign.insert(mfOaLeavenew);
				mfOaLeave = mfOaLeaveFeign.insertForSubmit(mfOaLeavenew);
				dataMap.put("msg", MessageEnum.SUCCEED_AGAIN_LEAVE.getMessage());
			}
			mfOaLeaveFeign.update(mfOaLeave);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 催办请假审批
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lcl
	 * @date 2017-2-20 下午8:55:31
	 */
	@RequestMapping("/urgedDoAjax")
	@ResponseBody
	public Map<String, Object> urgedDoAjax(String leaveNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaLeave mfOaLeave = new MfOaLeave();
		try {
			mfOaLeave.setLeaveNo(leaveNo);
			mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
			mfOaLeaveFeign.urgedDo(mfOaLeave);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("催办"));
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String leaveNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoaleave0002 = formService.getFormData("oaleave0002");
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setLeaveNo(leaveNo);
		mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
		getObjValue(formoaleave0002, mfOaLeave, formData);
		if (mfOaLeave != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String leaveNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setLeaveNo(leaveNo);
		try {
			mfOaLeaveFeign.delete(mfOaLeave);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setBrName(User.getOrgName(request));
		mfOaLeave.setBrNo(User.getOrgNo(request));
		mfOaLeave.setOpName(User.getRegName(request));
		mfOaLeave.setOpNo(User.getRegNo(request));
		String showDateTime = DateUtil.getShowDateTime(DateUtil.getDateTime());
		mfOaLeave.setCreateTime(showDateTime);
		String leaveNo = WaterIdUtil.getWaterId();
		mfOaLeave.setLeaveNo(leaveNo);
		FormData formoaleave0001 = formService.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaLeaveAction", BizPubParm.SHOW_TYPE1));
		getObjValue(formoaleave0001, mfOaLeave);
		model.addAttribute("formoaleave0001", formoaleave0001);
		model.addAttribute("mfOaLeave", mfOaLeave);
		model.addAttribute("leaveNo", leaveNo);
		model.addAttribute("query", "");
		return "/component/oa/leave/MfOaLeave_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formoaleave0001 = formService.getFormData("oaleave0001");
		getFormValue(formoaleave0001);
		MfOaLeave mfOaLeave = new MfOaLeave();
		setObjValue(formoaleave0001, mfOaLeave);
		mfOaLeaveFeign.insert(mfOaLeave);
		getObjValue(formoaleave0001, mfOaLeave);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaLeave", mfOaLeave));
		List<MfOaLeave> mfOaLeaveList = (List<MfOaLeave>) mfOaLeaveFeign.findByPage(ipage).getResult();
		model.addAttribute("mfOaLeaveList", mfOaLeaveList);
		model.addAttribute("formoaleave0001", formoaleave0001);
		model.addAttribute("query", "");
		return "/component/oa/leave/MfOaLeave_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String leaveNo,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaLeave mfOaLeave = new MfOaLeave();
		mfOaLeave.setLeaveNo(leaveNo);
		mfOaLeave = mfOaLeaveFeign.getById(mfOaLeave);
		mfOaLeave.setStartTime(DateUtil.getShowDateTime(mfOaLeave.getStartTime()));
		mfOaLeave.setEndTime(DateUtil.getShowDateTime(mfOaLeave.getEndTime()));
		mfOaLeave.setCreateTime(DateUtil.getShowDateTime(mfOaLeave.getCreateTime()));
		FormData formoaleave0001 = formService.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaLeaveAction", BizPubParm.SHOW_TYPE2));
		getObjValue(formoaleave0001, mfOaLeave);
		model.addAttribute("mfOaLeave", mfOaLeave);
		model.addAttribute("formoaleave0001", formoaleave0001);
		model.addAttribute("leaveNo", leaveNo);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/leave/MfOaLeave_DetailleaveSts";
	}

	/**
	 * 方法描述：进入审批历史页面（获取审批信息）
	 * 
	 * @return
	 * @throws Exception
	 *             string
	 * @author ph
	 * 
	 */
	@RequestMapping("/approvalHis")
	public String approvalHis() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/oa/leave/MfOaLeave_ApprovalHis";
	}

	/**
	 * 业务概况中的审批意见
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getApplyApprovalOpinionList")
	@ResponseBody
	public Map<String, Object> getApplyApprovalOpinionList(String leaveNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		@SuppressWarnings("unchecked")
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("APPROVAL_RESULT");
		List<WkfApprovalOpinion> ideaList = wkfInterfaceFeign.getWkfTaskHisList(leaveNo);
		JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
		for (int i = 0; i < zTreeNodes.size(); i++) {
			zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
			zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
			zTreeNodes.getJSONObject(i).put("pId", "0");
			for (ParmDic parmDic : pdList) {
				if (parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))) {
					zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
					break;
				}
			}
		}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}
}
