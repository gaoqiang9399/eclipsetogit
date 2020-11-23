package app.component.oa.entrymanagement.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.oa.archive.entity.MfOaArchivesBase;
import app.component.oa.archive.entity.MfOaArchivesEducation;
import app.component.oa.archive.entity.MfOaArchivesFamily;
import app.component.oa.archive.entity.MfOaArchivesRewards;
import app.component.oa.archive.entity.MfOaArchivesWork;
import app.component.oa.archive.feign.MfOaArchivesBaseFeign;
import app.component.oa.archive.feign.MfOaArchivesEducationFeign;
import app.component.oa.archive.feign.MfOaArchivesFamilyFeign;
import app.component.oa.archive.feign.MfOaArchivesRewardsFeign;
import app.component.oa.archive.feign.MfOaArchivesWorkFeign;
import app.component.oa.entrymanagement.entity.MfOaEntryManagement;
import app.component.oa.entrymanagement.feign.MfOaEntryManagementFeign;
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
@RequestMapping("/mfOaEntryManagement")
public class MfOaEntryManagementController extends BaseFormBean {
	@Autowired
	private MfOaEntryManagementFeign mfOaEntryManagementFeign;
	@Autowired
	private MfOaArchivesBaseFeign mfOaArchivesBaseFeign;
	@Autowired
	private MfOaArchivesWorkFeign mfOaArchivesWorkFeign;
	@Autowired
	private MfOaArchivesEducationFeign mfOaArchivesEducationFeign;
	@Autowired
	private MfOaArchivesRewardsFeign mfOaArchivesRewardsFeign;
	@Autowired
	private MfOaArchivesFamilyFeign mfOaArchivesFamilyFeign;
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
		return "/component/oa/entrymanagement/MfOaEntryManagement_List";
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
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		try {
			mfOaEntryManagement.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaEntryManagement.setCriteriaList(mfOaEntryManagement, ajaxData);// 我的筛选
			mfOaEntryManagement.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaEntryManagement", mfOaEntryManagement));
			ipage = mfOaEntryManagementFeign.findByPage(ipage);
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
			FormData formentrymanagement0002 = formService.getFormData(
					mfOaFormConfigFeign.getFormByAction("MfOaEntryManagementAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formentrymanagement0002, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formentrymanagement0002)) {
				MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
				MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
				setObjValue(formentrymanagement0002, mfOaEntryManagement);
				setObjValue(formentrymanagement0002, mfOaArchivesBase);
				MfOaEntryManagement mfOaEntryManagement1 = new MfOaEntryManagement();
				mfOaEntryManagement1 = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
				if (mfOaEntryManagement1 == null) {
					mfOaArchivesBase.setBaseId(WaterIdUtil.getWaterId());
					mfOaArchivesBase.setRegTime(DateUtil.getDateTime());
					mfOaArchivesBase.setUseFlag(BizPubParm.YES_NO_N);
					mfOaArchivesBaseFeign.insert(mfOaArchivesBase);
					mfOaEntryManagement.setBaseId(mfOaArchivesBase.getBaseId());
					mfOaEntryManagement.setEntryManagementId(WaterIdUtil.getWaterId());
					mfOaEntryManagement.setRegTime(DateUtil.getDateTime());
					mfOaEntryManagement.setOpNo(User.getRegNo(request));
					mfOaEntryManagement.setOpNoName(User.getRegName(request));
					mfOaEntryManagement.setApplySts(BizPubParm.APP_STS_UN_SUBMIT);
					mfOaEntryManagementFeign.insert(mfOaEntryManagement);
				} else {
					mfOaArchivesBase.setLstModTime(DateUtil.getDateTime());
					mfOaArchivesBaseFeign.update(mfOaArchivesBase);
					mfOaEntryManagement.setLstModTime(DateUtil.getDateTime());
					mfOaEntryManagementFeign.update(mfOaEntryManagement);
				}

				FormData formentrymanagement = formService.getFormData(
						mfOaFormConfigFeign.getFormByAction("MfOaEntryManagementAction", BizPubParm.SHOW_TYPE2));
				getFormValue(formentrymanagement);
				getObjValue(formentrymanagement, mfOaArchivesBase);
				getObjValue(formentrymanagement, mfOaEntryManagement);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				String htmlStr = jsonFormUtil.getJsonStr(formentrymanagement, "propertySeeTag", "");
				dataMap.put("htmlStr", htmlStr);
				dataMap.put("mfOaEntryManagement", mfOaEntryManagement);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/submitApplyAjax")
	@ResponseBody
	public Map<String, Object> submitApplyAjax(String entryManagementId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		try {
			mfOaEntryManagement = new MfOaEntryManagement();
			mfOaEntryManagement.setEntryManagementId(entryManagementId);
			mfOaEntryManagement = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
			mfOaEntryManagement = mfOaEntryManagementFeign.submitEntryApplyProcess(mfOaEntryManagement);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaEntryManagement.getApproveNodeName());
			paramMap.put("opNo", mfOaEntryManagement.getApprovePartName());
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
	public String input(Model model, String entryManagementId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formentrymanagement0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaEntryManagementAction", BizPubParm.SHOW_TYPE1));
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		mfOaEntryManagement.setEntryManagementId(entryManagementId);
		mfOaEntryManagement = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
		model.addAttribute("insertOrUpdate", BizPubParm.YES_NO_Y);
		if (mfOaEntryManagement != null) {
			// 初始化基本信息表单
			MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
			mfOaArchivesBase.setBaseId(baseId);
			mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
			// 实体对象放到表单对象中
			getObjValue(formentrymanagement0002, mfOaArchivesBase);
			getObjValue(formentrymanagement0002, mfOaEntryManagement);
			model.addAttribute("insertOrUpdate", BizPubParm.YES_NO_N);
		}
		model.addAttribute("processId", BizPubParm.ENTRY_APPLY_NO);
		model.addAttribute("formentrymanagement0002", formentrymanagement0002);
		model.addAttribute("query", "");
		return "/component/oa/entrymanagement/MfOaEntryManagement_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String entryManagementId, String baseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		// 初始化基本信息表单、工作经历表单
		FormData formentrymanagement = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaEntryManagementAction", BizPubParm.SHOW_TYPE2));
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		mfOaEntryManagement.setEntryManagementId(entryManagementId);
		mfOaEntryManagement = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
		// 新建对象
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		// 设置baseId
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesWork.setBaseId(baseId);
		mfOaArchivesEducation.setBaseId(baseId);
		mfOaArchivesRewards.setBaseId(baseId);
		mfOaArchivesFamily.setBaseId(baseId);
		// 根据baseId获取相关数据
		List<MfOaArchivesWork> workList = mfOaArchivesWorkFeign.getByBaseId(mfOaArchivesWork);
		List<MfOaArchivesEducation> eduList = mfOaArchivesEducationFeign.getByBaseId(mfOaArchivesEducation);
		List<MfOaArchivesRewards> redList = mfOaArchivesRewardsFeign.getByBaseId(mfOaArchivesRewards);
		List<MfOaArchivesFamily> famList = mfOaArchivesFamilyFeign.getByBaseId(mfOaArchivesFamily);

		CodeUtils codeUtils = new CodeUtils();
		if (eduList != null) {
			for (int i = 0, len = eduList.size(); i < len; i++) {
				eduList.get(i).setEducation((codeUtils.getMapByKeyName("EDU")).get(eduList.get(i).getEducation()));
			}
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String familyTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE3), "tableTag",
				famList, null, true);
		String workTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE3), "tableTag",
				workList, null, true);
		String rewardTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE3), "tableTag",
				redList, null, true);
		String educationTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE3), "tableTag",
				eduList, null, true);

		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		mfOaArchivesBase = getDic(mfOaArchivesBase);
		mfOaArchivesBase.setBirthday(DateUtil.getShowDateTime(mfOaArchivesBase.getBirthday()));

		// 实体对象放到表单对象中
		getObjValue(formentrymanagement, mfOaArchivesBase);
		getObjValue(formentrymanagement, mfOaEntryManagement);

		// 转成json串，在jsp中转成json对象，在js中使用。
		String archivesbase = JSONObject.fromObject(mfOaArchivesBase).toString();
		Map<String, String> mapNation = codeUtils.getMapByKeyName("NATION");
		Map<String, Object> parmdicMap = new HashMap<String, Object>();
		parmdicMap.put("mapNation", mapNation);
		parmdicMap.put("workTableHtml", workTableHtml);
		parmdicMap.put("educationTableHtml", educationTableHtml);
		parmdicMap.put("rewardTableHtml", rewardTableHtml);
		parmdicMap.put("familyTableHtml", familyTableHtml);
		model.addAttribute("parmdicMap", parmdicMap);
		model.addAttribute("mfOaArchivesBase", mfOaArchivesBase);
		model.addAttribute("baseId", baseId);
		model.addAttribute("formentrymanagement", formentrymanagement);
		model.addAttribute("entryManagementId", entryManagementId);
		model.addAttribute("archivesbase", archivesbase);
		model.addAttribute("query", "");
		return "/component/oa/entrymanagement/MfOaEntryManagement_Detail";
	}

	// 得到数据字典
	public MfOaArchivesBase getDic(MfOaArchivesBase mfOaArchivesBase) throws Exception {
		CodeUtils codeUtils = new CodeUtils();
		mfOaArchivesBase.setNation((codeUtils.getMapByKeyName("NATION")).get(mfOaArchivesBase.getNation()));
		mfOaArchivesBase.setMarriageSts((codeUtils.getMapByKeyName("MARRIGE")).get(mfOaArchivesBase.getMarriageSts()));
		mfOaArchivesBase.setEducation((codeUtils.getMapByKeyName("EDU")).get(mfOaArchivesBase.getEducation()));
		mfOaArchivesBase.setIfParty((codeUtils.getMapByKeyName("IFPARTY")).get(mfOaArchivesBase.getIfParty()));
		mfOaArchivesBase.setSex((codeUtils.getMapByKeyName("SEX")).get(mfOaArchivesBase.getSex()));
		mfOaArchivesBase.setOpSts((codeUtils.getMapByKeyName("JOB_STS")).get(mfOaArchivesBase.getOpSts()));
		mfOaArchivesBase.setPosition((codeUtils.getMapByKeyName("DUTIES")).get(mfOaArchivesBase.getPosition()));
		return mfOaArchivesBase;
	}

	/**
	 * 进入详情页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEntryApplyInfo")
	public String getEntryApplyInfo(Model model, String entryManagementId, String baseId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		mfOaEntryManagement.setEntryManagementId(entryManagementId);
		mfOaEntryManagement = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
		baseId = mfOaEntryManagement.getBaseId();
		// 初始化基本信息表单、工作经历表单
		FormData formentrymanagement = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaEntryManagementAction", BizPubParm.SHOW_TYPE2));
		// 新建对象
		MfOaArchivesBase mfOaArchivesBase = new MfOaArchivesBase();
		MfOaArchivesWork mfOaArchivesWork = new MfOaArchivesWork();
		MfOaArchivesEducation mfOaArchivesEducation = new MfOaArchivesEducation();
		MfOaArchivesRewards mfOaArchivesRewards = new MfOaArchivesRewards();
		MfOaArchivesFamily mfOaArchivesFamily = new MfOaArchivesFamily();
		// 设置baseId
		mfOaArchivesBase.setBaseId(baseId);
		mfOaArchivesWork.setBaseId(baseId);
		mfOaArchivesEducation.setBaseId(baseId);
		mfOaArchivesRewards.setBaseId(baseId);
		mfOaArchivesFamily.setBaseId(baseId);
		// 根据baseId获取相关数据
		List<MfOaArchivesWork> workList = mfOaArchivesWorkFeign.getByBaseId(mfOaArchivesWork);
		List<MfOaArchivesEducation> eduList = mfOaArchivesEducationFeign.getByBaseId(mfOaArchivesEducation);
		List<MfOaArchivesRewards> redList = mfOaArchivesRewardsFeign.getByBaseId(mfOaArchivesRewards);
		List<MfOaArchivesFamily> famList = mfOaArchivesFamilyFeign.getByBaseId(mfOaArchivesFamily);

		CodeUtils codeUtils = new CodeUtils();
		if (eduList != null) {
			for (int i = 0, len = eduList.size(); i < len; i++) {
				eduList.get(i).setEducation((codeUtils.getMapByKeyName("EDU")).get(eduList.get(i).getEducation()));
			}
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String familyTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesFamilyAction", BizPubParm.SHOW_TYPE3), "tableTag",
				famList, null, true);
		String workTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesWorkAction", BizPubParm.SHOW_TYPE3), "tableTag",
				workList, null, true);
		String rewardTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesRewardsAction", BizPubParm.SHOW_TYPE3), "tableTag",
				redList, null, true);
		String educationTableHtml = jtu.getJsonStr(
				mfOaFormConfigFeign.getFormByAction("MfOaArchivesEducationAction", BizPubParm.SHOW_TYPE3), "tableTag",
				eduList, null, true);

		mfOaArchivesBase = mfOaArchivesBaseFeign.getById(mfOaArchivesBase);
		mfOaArchivesBase = getDic(mfOaArchivesBase);
		mfOaArchivesBase.setBirthday(DateUtil.getShowDateTime(mfOaArchivesBase.getBirthday()));

		// 实体对象放到表单对象中
		getObjValue(formentrymanagement, mfOaArchivesBase);
		getObjValue(formentrymanagement, mfOaEntryManagement);

		// 转成json串，在jsp中转成json对象，在js中使用。
		String archivesbase = JSONObject.fromObject(mfOaArchivesBase).toString();
		Map<String, String> mapNation = codeUtils.getMapByKeyName("NATION");
		Map<String, Object> parmdicMap = new HashMap<String, Object>();
		parmdicMap.put("mapNation", mapNation);
		parmdicMap.put("workTableHtml", workTableHtml);
		parmdicMap.put("educationTableHtml", educationTableHtml);
		parmdicMap.put("rewardTableHtml", rewardTableHtml);
		parmdicMap.put("familyTableHtml", familyTableHtml);
		model.addAttribute("parmdicMap", parmdicMap);
		model.addAttribute("mfOaArchivesBase", mfOaArchivesBase);
		model.addAttribute("baseId", baseId);
		model.addAttribute("formentrymanagement", formentrymanagement);
		model.addAttribute("entryManagementId", entryManagementId);
		model.addAttribute("archivesbase", archivesbase);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/entrymanagement/MfOaEntryManagement_Find";
	}

	/**
	 * 
	 * 方法描述： 打开入职申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model,String entryManagementId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		mfOaEntryManagement.setEntryManagementId(entryManagementId);
		mfOaEntryManagement = mfOaEntryManagementFeign.getById(mfOaEntryManagement);
		// 初始化基本信息表单、工作经历表单
		FormData formEntryApplyApprove = formService.getFormData("EntryApplyApprove");
		// 实体对象放到表单对象中
		getObjValue(formEntryApplyApprove, mfOaEntryManagement);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(entryManagementId, null);// 当前审批节点task
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formEntryApplyApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("mfOaEntryManagement", mfOaEntryManagement);
		model.addAttribute("entryManagementId", entryManagementId);
		model.addAttribute("formEntryApplyApprove", formEntryApplyApprove);
		model.addAttribute("query", "");
		return "/component/oa/entrymanagement/WkfEntryManagementViewPoint";
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
		FormData formentrymanagement = formService.getFormData("entrymanagement");
		getFormValue(formentrymanagement, formDataMap);
		MfOaEntryManagement mfOaEntryManagement = new MfOaEntryManagement();
		setObjValue(formentrymanagement, mfOaEntryManagement);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaEntryManagement);
			formDataMap.put("mfOaEntryManagement", mfOaEntryManagement);
			res = mfOaEntryManagementFeign.doCommit(taskId, transition, nextUser, formDataMap);
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
