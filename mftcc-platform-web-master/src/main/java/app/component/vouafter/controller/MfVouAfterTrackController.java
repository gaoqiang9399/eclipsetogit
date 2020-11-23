package app.component.vouafter.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizManageParam;
import app.component.docinterface.DocInterfaceFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.modelinterface.ModelInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.guarantee.entity.MfGuaranteeRegistration;
import app.component.pact.guarantee.feign.MfGuaranteeRegistrationFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.vouafter.entity.MfVouAfterTrack;
import app.component.vouafter.entity.MfVouAfterTrackHis;
import app.component.vouafter.feign.MfVouAfterTrackFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfVouAfterTrackController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfVouAfterTrack")
public class MfVouAfterTrackController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfVouAfterTrackFeign mfVouAfterTrackFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private ModelInterfaceFeign modelInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MfGuaranteeRegistrationFeign mfGuaranteeRegistrationFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfVouAfterTrack_List";
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
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		try {
			mfVouAfterTrack.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfVouAfterTrack.setCriteriaList(mfVouAfterTrack, ajaxData);//我的筛选
			//mfVouAfterTrack.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfVouAfterTrack,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfVouAfterTrack", mfVouAfterTrack));
			//自定义查询Feign方法
			ipage = mfVouAfterTrackFeign.findByPage(ipage);
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
		Map<String, Object> paramMap = getMapByJson(ajaxData);
		try {
			FormData formvouaftertrackbase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formvouaftertrackbase, paramMap);
			if (this.validateFormData(formvouaftertrackbase)) {
				MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
				setObjValue(formvouaftertrackbase, mfVouAfterTrack);
				mfVouAfterTrackFeign.insert(mfVouAfterTrack);
				mfVouAfterTrack = mfVouAfterTrackFeign.submitProcess(mfVouAfterTrack);
				paramMap.put("userRole", mfVouAfterTrack.getApproveNodeName());
				paramMap.put("opNo", mfVouAfterTrack.getApprovePartName());
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase, getMapByJson(ajaxData));
		MfVouAfterTrack mfVouAfterTrackJsp = new MfVouAfterTrack();
		setObjValue(formvouafterbase, mfVouAfterTrackJsp);
		MfVouAfterTrack mfVouAfterTrack = mfVouAfterTrackFeign.getById(mfVouAfterTrackJsp);
		if(mfVouAfterTrack!=null){
			try{
				mfVouAfterTrack = (MfVouAfterTrack)EntityUtil.reflectionSetVal(mfVouAfterTrack, mfVouAfterTrackJsp, getMapByJson(ajaxData));
				mfVouAfterTrackFeign.update(mfVouAfterTrack);
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
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		try{
			FormData formvouafterbase = formService.getFormData("vouafterbase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfVouAfterTrack = new MfVouAfterTrack();
				setObjValue(formvouafterbase, mfVouAfterTrack);
				mfVouAfterTrackFeign.update(mfVouAfterTrack);
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		mfVouAfterTrack.setId(id);
		mfVouAfterTrack = mfVouAfterTrackFeign.getById(mfVouAfterTrack);
		getObjValue(formvouafterbase, mfVouAfterTrack,formData);
		if(mfVouAfterTrack!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		mfVouAfterTrack.setId(id);
		try {
			mfVouAfterTrackFeign.delete(mfVouAfterTrack);
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
	public String input(Model model,String pactId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = pactInterfaceFeign.getById(mfBusPact);
		MfBusApply mfBusApply = new MfBusApply();
        mfBusApply.setAppId(mfBusPact.getAppId());
        mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		MfGuaranteeRegistration mfGuaranteeRegistration = new MfGuaranteeRegistration();
        mfGuaranteeRegistration.setAppId(mfBusPact.getAppId());
        mfGuaranteeRegistration = mfGuaranteeRegistrationFeign.getById(mfGuaranteeRegistration);
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		String id = WaterIdUtil.getWaterId();
        mfVouAfterTrack.setId(id);
        mfVouAfterTrack.setAppId(mfBusApply.getAppId());
        mfVouAfterTrack.setAppName(mfBusApply.getAppName());
        mfVouAfterTrack.setCusName(mfBusApply.getCusName());
        mfVouAfterTrack.setCusNo(mfBusApply.getCusNo());
        mfVouAfterTrack.setManageOpNo1(mfBusPact.getManageOpNo1());
        mfVouAfterTrack.setManageOpName1(mfBusPact.getManageOpName1());
        mfVouAfterTrack.setKindName(mfBusApply.getKindName());
        mfVouAfterTrack.setKindNo(mfBusApply.getKindNo());
        mfVouAfterTrack.setBreedNo(mfBusApply.getBreedNo());
        mfVouAfterTrack.setBreedName(mfBusApply.getBreedName());
        mfVouAfterTrack.setTermType(mfBusApply.getTermType());
        mfVouAfterTrack.setTerm(mfBusApply.getTerm());
        mfVouAfterTrack.setBeneficiary(mfBusApply.getExt4());
        mfVouAfterTrack.setProjectName(mfBusApply.getProjectName());
        mfVouAfterTrack.setOpenDate(mfGuaranteeRegistration.getOpenDate());
        mfVouAfterTrack.setGuaranteeEndDate(mfGuaranteeRegistration.getGuaranteeEndDate());
        mfVouAfterTrack.setPactNo(mfBusPact.getPactNo());
        mfVouAfterTrack.setPactId(mfBusPact.getPactId());
        mfVouAfterTrack.setPactAmt(mfBusPact.getPactAmt());
		mfVouAfterTrack.setTermShow(mfBusPact.getTermShow());
        //节点要件关联编号
        String relNo = id;
        String nodeNo = "loanAfterExamine";
        String kindNo = "20070810054353510";

        // 要件初始化
        DocBizManageParam dbmp = new DocBizManageParam();
        dbmp.setScNo(nodeNo);// 场景编号
        dbmp.setRelNo(relNo);// 业务编号
        dbmp.setDime(kindNo);//
        dbmp.setCusNo(mfBusPact.getCusNo());
        dbmp.setRegNo(User.getRegNo(request));
        dbmp.setOrgNo(User.getOrgNo(request));
        docInterfaceFeign.initiaBiz(dbmp);
        // 模板初始化
        MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
        templateBizConfig.setTemBizNo(id);
        templateBizConfig.setNodeNo(nodeNo);
        templateBizConfig.setKindNo(kindNo);
        modelInterfaceFeign.doVouAfterTrackInit(templateBizConfig);

		FormData formvouafterbase = formService.getFormData("vouafterbase");
        getObjValue(formvouafterbase, mfVouAfterTrack);
		model.addAttribute("formvouafterbase", formvouafterbase);
		model.addAttribute("id", id);
		model.addAttribute("nodeNo", nodeNo);
        model.addAttribute("query", "");

        return "/component/vouafter/MfVouAfterTrack_Insert";
    }
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initBusInfo")
	public String initBusInfo(Model model) throws Exception{
		ActionContext.initialize(request, response);
        FormService formService = new FormService();
        FormData formvouafterinit = formService.getFormData("vouafterinit");
        model.addAttribute("formvouafterinit", formvouafterinit);
        model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterTrack_InitBusInfo";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id,String from) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterdetail = formService.getFormData("vouafterdetail");
		getFormValue(formvouafterdetail);
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		mfVouAfterTrack.setId(id);
		mfVouAfterTrack = mfVouAfterTrackFeign.getById(mfVouAfterTrack);
		getObjValue(formvouafterdetail, mfVouAfterTrack);
		//节点要件关联编号
		String relNo = id;
		String nodeNo = "loanAfterExamine";
		model.addAttribute("id", id);
		model.addAttribute("relNo", id);
		model.addAttribute("mfVouAfterTrack", mfVouAfterTrack);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("formvouafterdetail", formvouafterdetail);
		model.addAttribute("query", "query");
		model.addAttribute("from", from);
		return "/component/vouafter/MfVouAfterTrack_Detail";
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
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
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
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
	public String getViewPoint(Model model, String id, String hideOpinionType, String taskId,
							   String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		mfVouAfterTrack.setId(id);
		mfVouAfterTrack = mfVouAfterTrackFeign.getById(mfVouAfterTrack);
		mfVouAfterTrack.setApprovePartNo(null);
		mfVouAfterTrack.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formVouAfterTrackApprove = formService.getFormData("VouAfterTrackApprove");
		// 实体对象放到表单对象中
		getObjValue(formVouAfterTrackApprove, mfVouAfterTrack);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		if("riskApproval".equals(nodeNo)){
			hideOpinionType = hideOpinionType + "@4@6";
		}else{
			hideOpinionType = hideOpinionType + "@6";
		}
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formVouAfterTrackApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("id", id);
		model.addAttribute("mfVouAfterTrack", mfVouAfterTrack);
		model.addAttribute("formVouAfterTrackApprove", formVouAfterTrackApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/vouafter/WkfVouAfterViewPoint";
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
		FormData formVouAfterTrackApprove = formService.getFormData("VouAfterTrackApprove");
		getFormValue(formVouAfterTrackApprove, formDataMap);
		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		setObjValue(formVouAfterTrackApprove, mfVouAfterTrack);
		setObjValue(formVouAfterTrackApprove, mfVouAfterTrackHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfVouAfterTrack);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfVouAfterTrackHis);
			formDataMap.put("mfVouAfterTrack", mfVouAfterTrack);
			formDataMap.put("mfVouAfterTrackHis", mfVouAfterTrackHis);
			res = mfVouAfterTrackFeign.doCommit(taskId, transition, nextUser,
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
	@RequestMapping(value = "/getMultiBusList")
	public String getMultiBusList(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);

		MfVouAfterTrack mfVouAfterTrack = new MfVouAfterTrack();
		mfVouAfterTrack.setCusNo(cusNo);
		List<MfVouAfterTrack> mfVouAfterTrackProjectList = mfVouAfterTrackFeign.getMultiBusList(mfVouAfterTrack);
		if(mfVouAfterTrackProjectList != null && mfVouAfterTrackProjectList.size() > 0){
			// 申请金额总和
			Double appAmt = 0d;
			for (MfVouAfterTrack mbfa : mfVouAfterTrackProjectList) {
				appAmt = DataUtil.add(appAmt, mbfa.getAppAmt(), 20);
			}
			appAmt = DataUtil.retainDigit(appAmt, 2);

			MfVouAfterTrack mfVouAfterTrackTmp1 = new MfVouAfterTrack();
			mfVouAfterTrackTmp1.setAppAmt(appAmt);
			mfVouAfterTrackTmp1.setBreedName("总金额");
			mfVouAfterTrackProjectList.add(mfVouAfterTrackTmp1);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String projectTableHtml = jtu.getJsonStr( "tablevouaftertrack_GCDB", "tableTag", mfVouAfterTrackProjectList, null, true);
		model.addAttribute("mfVouAfterTrackProjectList", mfVouAfterTrackProjectList);
		model.addAttribute("mfVouAfterTrackProjectListSize", mfVouAfterTrackProjectList.size());
		model.addAttribute("projectTableHtml", projectTableHtml);


		model.addAttribute("busEntrance", "pact");
		model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterTrack_multiBusList";
	}
}
