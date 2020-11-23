package  app.component.app.makepolicymeeting.controller;

import app.base.User;
import app.component.app.makepolicymeeting.entity.MfMakePolicyMeeting;
import app.component.app.makepolicymeeting.entity.MfReviewMember;
import app.component.app.makepolicymeeting.feign.MfMakePolicyMeetingFeign;
import app.component.app.makepolicymeeting.feign.MfReviewMemberFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfMakePolicyMeetingController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 23 10:29:15 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfMakePolicyMeeting")
public class MfMakePolicyMeetingController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfMakePolicyMeetingFeign mfMakePolicyMeetingFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfReviewMemberFeign mfReviewMemberFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/app/makepolicymeeting/MfMakePolicyMeeting_List";
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
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		try {
			mfMakePolicyMeeting.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMakePolicyMeeting.setCriteriaList(mfMakePolicyMeeting, ajaxData);//我的筛选
			//mfMakePolicyMeeting.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMakePolicyMeeting,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfMakePolicyMeeting", mfMakePolicyMeeting));
			//自定义查询Feign方法
			ipage = mfMakePolicyMeetingFeign.findByPage(ipage);
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
		try{
			FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
			getFormValue(formmakepolicymeetingAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formmakepolicymeetingAdd)){
				MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
				setObjValue(formmakepolicymeetingAdd, mfMakePolicyMeeting);
				mfMakePolicyMeetingFeign.insert(mfMakePolicyMeeting);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
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
		FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
		getFormValue(formmakepolicymeetingAdd, getMapByJson(ajaxData));
		MfMakePolicyMeeting mfMakePolicyMeetingJsp = new MfMakePolicyMeeting();
		setObjValue(formmakepolicymeetingAdd, mfMakePolicyMeetingJsp);
		MfMakePolicyMeeting mfMakePolicyMeeting = mfMakePolicyMeetingFeign.getById(mfMakePolicyMeetingJsp);
		if(mfMakePolicyMeeting!=null){
			try{
				mfMakePolicyMeeting = (MfMakePolicyMeeting)EntityUtil.reflectionSetVal(mfMakePolicyMeeting, mfMakePolicyMeetingJsp, getMapByJson(ajaxData));
				mfMakePolicyMeetingFeign.update(mfMakePolicyMeeting);
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
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		try{
			FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
			getFormValue(formmakepolicymeetingAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formmakepolicymeetingAdd)){
				mfMakePolicyMeeting = new MfMakePolicyMeeting();
				setObjValue(formmakepolicymeetingAdd, mfMakePolicyMeeting);
				mfMakePolicyMeetingFeign.update(mfMakePolicyMeeting);
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
		FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		mfMakePolicyMeeting.setId(id);
		mfMakePolicyMeeting = mfMakePolicyMeetingFeign.getById(mfMakePolicyMeeting);
		getObjValue(formmakepolicymeetingAdd, mfMakePolicyMeeting,formData);
		if(mfMakePolicyMeeting!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLastedByMakePolicyMeetingAppId")
	@ResponseBody
	public Map<String, Object> getLastedByMakePolicyMeetingAppId(String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		mfMakePolicyMeeting.setAppId(appId);
		mfMakePolicyMeeting = mfMakePolicyMeetingFeign.getLastedByMakePolicyMeetingAppId(mfMakePolicyMeeting);
		if(mfMakePolicyMeeting == null){
			dataMap.put("ifShowMakePolicyMeeting", BizPubParm.YES_NO_N);
		}else{
			dataMap.put("ifShowMakePolicyMeeting", BizPubParm.YES_NO_Y);
			dataMap.put("mfMakePolicyMeeting", mfMakePolicyMeeting);
			FormService formService = new FormService();
			FormData formcusCreditContractDetail = formService.getFormData("makepolicymeetingDetail");
			getObjValue(formcusCreditContractDetail, mfMakePolicyMeeting);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formcusCreditContractDetail, "propertySeeTag",  "query");
			dataMap.put("cusMakePolicyMeetingDetail", htmlStr);

			MfReviewMember mfReviewMember = new MfReviewMember();
			mfReviewMember.setMakePolicyMeetingId(mfMakePolicyMeeting.getMakePolicyMeetingAppId());
			List<MfReviewMember> mfReviewMemberList = mfReviewMemberFeign.getAllByMakePolicyMeetingId(mfReviewMember);
			JSONArray jsonArray = new JSONArray();
			CodeUtils cu = new CodeUtils();
			for (MfReviewMember mfReviewMember1: mfReviewMemberList ) {
				JSONObject js =new JSONObject();
				js.put("reviewMemberType",cu.getMapByKeyName("POINT_ROLE").get(mfReviewMember1.getReviewMemberType()));
				js.put("reviewMemberNo",mfReviewMember1.getReviewMemberNo());
				js.put("reviewMemberName",mfReviewMember1.getReviewMemberName());
				js.put("reviewMemberOpinion",cu.getMapByKeyName("POINT_OPINION").get(mfReviewMember1.getReviewMemberOpinion()));
				js.put("reviewMemberOtherContent",mfReviewMember1.getReviewMemberOtherContent());
				js.put("reviewMemberOtherReason",cu.getMapByKeyName("POINT_OTHER_OPINION").get(mfReviewMember1.getReviewMemberOtherReason()));
				js.put("reviewMemberReason",mfReviewMember1.getReviewMemberReason());
				jsonArray.add(js);
			}
			dataMap.put("mfReviewMemberList", jsonArray);
		}
		dataMap.put("flag", "success");
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
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		mfMakePolicyMeeting.setId(id);
		try {
			mfMakePolicyMeetingFeign.delete(mfMakePolicyMeeting);
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
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
		model.addAttribute("formmakepolicymeetingAdd", formmakepolicymeetingAdd);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakePolicyMeeting_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formmakepolicymeetingDetail = formService.getFormData("makepolicymeetingDetail");
		getFormValue(formmakepolicymeetingDetail);
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		mfMakePolicyMeeting.setId(id);
		mfMakePolicyMeeting = mfMakePolicyMeetingFeign.getById(mfMakePolicyMeeting);
		getObjValue(formmakepolicymeetingDetail, mfMakePolicyMeeting);
		model.addAttribute("formmakepolicymeetingDetail", formmakepolicymeetingDetail);
		model.addAttribute("query", "");
		return "/component/app/makepolicymeeting/MfMakePolicyMeeting_Detail";
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
		FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
		getFormValue(formmakepolicymeetingAdd);
		boolean validateFlag = this.validateFormData(formmakepolicymeetingAdd);
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
		FormData formmakepolicymeetingAdd = formService.getFormData("makepolicymeetingAdd");
		getFormValue(formmakepolicymeetingAdd);
		boolean validateFlag = this.validateFormData(formmakepolicymeetingAdd);
	}

	/**
	 *
	 * 方法描述： 打开申请审批页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String makePolicyMeetingAppId, String hideOpinionType, String taskId,
							   String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		mfMakePolicyMeeting.setMakePolicyMeetingAppId(makePolicyMeetingAppId);
		mfMakePolicyMeeting = mfMakePolicyMeetingFeign.getLastedByMakePolicyMeetingAppId(mfMakePolicyMeeting);
		List<MfMakePolicyMeeting> mfMakePolicyMeetingList = mfMakePolicyMeetingFeign.getListByAppId(mfMakePolicyMeeting);
		int count = 1;
		if(mfMakePolicyMeetingList != null){
			count = mfMakePolicyMeetingList.size();
		}

		mfMakePolicyMeeting.setApprovePartNo(null);
		mfMakePolicyMeeting.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(makePolicyMeetingAppId, null);// 当前审批节点task
		String formId = "makePolicyMeetingReviewApprove";
		String nodeNo = taskAppro.getActivityName();
		if(nodeNo.equals("reviewMeetingSecretary")){
			formId = "makePolicyMeetingReviewApproveSup";
		}
		MfReviewMember mfReviewMember = new MfReviewMember();
		mfReviewMember.setMakePolicyMeetingId(makePolicyMeetingAppId);
		List<MfReviewMember> mfReviewMemberList = mfReviewMemberFeign.getAllByMakePolicyMeetingId(mfReviewMember);
		JSONArray jsonArray = new JSONArray();
		for (MfReviewMember mfReviewMember1: mfReviewMemberList ) {
			JSONObject js =new JSONObject();
			js.put("reviewMemberType",mfReviewMember1.getReviewMemberType());
			js.put("reviewMemberNo",mfReviewMember1.getReviewMemberNo());
			js.put("reviewMemberName",mfReviewMember1.getReviewMemberName());
			js.put("reviewMemberOpinion",mfReviewMember1.getReviewMemberOpinion());
			js.put("reviewMemberOtherContent",mfReviewMember1.getReviewMemberOtherContent());
			js.put("reviewMemberOtherReason",mfReviewMember1.getReviewMemberOtherReason());
			js.put("reviewMemberReason",mfReviewMember1.getReviewMemberReason());
			jsonArray.add(js);
		}
		model.addAttribute("mfReviewMemberList", jsonArray);

		//授信决策会审批时传递参数
		String cusNo = "";
		if("credit".equals(mfMakePolicyMeeting.getEntrFlag())){
			MfCusCreditApply mfCusCreditApplyQuery = new MfCusCreditApply();
			mfCusCreditApplyQuery.setCreditAppId(mfMakePolicyMeeting.getAppId());
			mfCusCreditApplyQuery = mfCusCreditApplyFeign.getById(mfCusCreditApplyQuery);
			cusNo = mfCusCreditApplyQuery.getCusNo();
		}
		FormData formMakePolicyMeetingApprove = formService.getFormData(formId);
		// 实体对象放到表单对象中
		getObjValue(formMakePolicyMeetingApprove, mfMakePolicyMeeting);
		// 处理审批意见类型
		hideOpinionType = hideOpinionType + "@2";//隐藏否决
		if("riskManage".equals(nodeNo)){
			hideOpinionType = hideOpinionType + "@4";
		}
		if("reviewMeetingSecretary".equals(nodeNo)){
			hideOpinionType = hideOpinionType + "@6.4";
		}
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formMakePolicyMeetingApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		JSONArray befNodesjsonArray1 = JSONArray.fromObject(befNodesjsonArray);
		if(befNodesjsonArray != null && befNodesjsonArray.size() > 0){
			for(int i=0;i<befNodesjsonArray.size();i++){
				JSONObject js = (JSONObject)befNodesjsonArray.get(i);
				String nodeName = js.getString("name");
				if("reviewMeetingSecretary".equals(nodeName)){
					befNodesjsonArray1.remove(js);
				}
			}
		}
		request.setAttribute("befNodesjsonArray", befNodesjsonArray1);
		model.addAttribute("makePolicyMeetingAppId", makePolicyMeetingAppId);
		model.addAttribute("appId", mfMakePolicyMeeting.getAppId());
		model.addAttribute("formMakePolicyMeetingApprove", formMakePolicyMeetingApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("count", count);
		model.addAttribute("from", "reviewMeeting");
        model.addAttribute("entrFlag", mfMakePolicyMeeting.getEntrFlag());
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("mfMakePolicyMeeting", mfMakePolicyMeeting);
		return "/component/app/makepolicymeeting/WkfMakePolicyMeetingViewPoint";
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
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formMakePolicyMeetingApprove = formService.getFormData("makePolicyMeetingReviewApprove");
		getFormValue(formMakePolicyMeetingApprove, formDataMap);
		MfMakePolicyMeeting mfMakePolicyMeeting = new MfMakePolicyMeeting();
		setObjValue(formMakePolicyMeetingApprove, mfMakePolicyMeeting);
		Gson gson = new Gson();
		try {
			mfMakePolicyMeeting.setReviewMemberType(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberType")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberNo(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberNo")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberName(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberName")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberOpinion(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberOpinion")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberOtherContent(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberOtherContent")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberReason(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberReason")), new TypeToken<List<String>>() {
			}.getType()));
			mfMakePolicyMeeting.setReviewMemberOtherReason(gson.fromJson(gson.toJson(formDataMap.get("reviewMemberOtherReason")), new TypeToken<List<String>>() {
			}.getType()));
		}catch(Exception e){
			List<String> reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberType"));
			mfMakePolicyMeeting.setReviewMemberType(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberNo"));
			mfMakePolicyMeeting.setReviewMemberNo(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberName"));
			mfMakePolicyMeeting.setReviewMemberName(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberOpinion"));
			mfMakePolicyMeeting.setReviewMemberOpinion(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberOtherContent"));
			mfMakePolicyMeeting.setReviewMemberOtherContent(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberReason"));
			mfMakePolicyMeeting.setReviewMemberReason(reviewList);
			reviewList = new ArrayList<>();
			reviewList.add((String) formDataMap.get("reviewMemberOtherReason"));
			mfMakePolicyMeeting.setReviewMemberOtherReason(reviewList);
		}
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfMakePolicyMeeting);
			formDataMap.put("mfMakePolicyMeeting", mfMakePolicyMeeting);
			res = mfMakePolicyMeetingFeign.doCommit(taskId, transition, nextUser,
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
