package app.component.pact.assetmanage.controller;

import app.base.User;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.pact.assetmanage.entity.MfAssetManage;
import app.component.pact.assetmanage.feign.MfAssetManageFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
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
 * 
 * @ClassName MfAssetManageController
 * @Description 
 * @Author Liu Haobin
 * @Date 2018年5月30日 下午3:10:55
 */
@Controller
@RequestMapping("/mfAssetManage")
public class MfAssetManageController extends BaseFormBean{

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetManageFeign mfAssetManageFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private TaskFeign taskFeign;
	/**
	 * 总产管理列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAssetManageListPage")
	public String getAssetManageListPage(Model model, String ajaxData,String applyFlag) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray classMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("ASSET_APPLY_STATUS");
		model.addAttribute("classMethodJsonArray", classMethodJsonArray);
		model.addAttribute("query", "");
		model.addAttribute("applyFlag", applyFlag);
		return "component/pact/assetmanage/MFAssetManage_List";
	}

	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String applyFlag) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setRegDate(DateUtil.getDate());
		mfAssetManage.setAssetId(WaterIdUtil.getWaterId("ASE"));
		mfAssetManage.setProposer(User.getRegName(request));
		mfAssetManage.setApplyFlag(applyFlag);
		FormData formassetmanagebase = formService.getFormData("assetmanagebase");
		getObjValue(formassetmanagebase, mfAssetManage);
		String isFlag=mfAssetManageFeign.isApproval();//是否开启审批标志  用于展示不同的提交提示信息
		model.addAttribute("formassetmanagebase", formassetmanagebase);
		model.addAttribute("mfAssetManage", mfAssetManage);
		model.addAttribute("query", "");
		model.addAttribute("isFlag", isFlag);
		return "/component/pact/assetmanage/MfAssetManage_Insert";
	}
	/**
	 * 获取逾期客户列表
	 */

	@RequestMapping(value = "/getOverdueCus")
	@ResponseBody
	public Map<String, Object> getOverdueCus(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp=new MfBusFincApp();
		try {
			mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
			ipage=mfAssetManageFeign.getOverdueCus(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		
		return dataMap;
	}
	/**
	 * 获取逾期客户贷款相关信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getInfo")
	@ResponseBody
	public Map<String, Object> getInfo(Model model,String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		try {
			MfAssetManage mfAssetManage=new MfAssetManage();
			mfAssetManage=mfAssetManageFeign.getOverdueCusInfo(cusNo);
			mfAssetManage.setRegDate(DateUtil.getDate());
			mfAssetManage.setProposer(User.getRegName(request));
			mfAssetManage.setCusNo(cusNo);
			FormData formassetmanagebase = formService.getFormData("assetmanagebase");
			
			getObjValue(formassetmanagebase, mfAssetManage,formData);
			dataMap.put("formData", formData); 
			dataMap.put("bean", mfAssetManage); 
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formassetmanagebase,"bootstarpTag","");
			dataMap.put("formData", formData); 
			dataMap.put("bean", mfAssetManage); 
			dataMap.put("flag", "success");
			dataMap.put("htmlStr", htmlStr);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String applyFlag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetManage mfAssetManage=new MfAssetManage();
		try {
			mfAssetManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAssetManage.setCriteriaList(mfAssetManage, ajaxData);// 我的筛选
			mfAssetManage.setApplyFlag(applyFlag);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfAssetManage",mfAssetManage));
			ipage = mfAssetManageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 依法收贷申请新增
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * 20180602
	 */
	@RequestMapping(value = "/insertApplyAjax")
	@ResponseBody
	public Map<String, Object> insertApplyAjax(String ajaxData,String flag) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		//FormData formcreditapply0001 = formService.getFormData("assetmanagebase");
		MfAssetManage mfAssetManage=new MfAssetManage();
		try {
			dataMap=getMapByJson(ajaxData);
			if("1".equals(flag)){
				FormData formassetmanage0001 = formService.getFormData("assetmanage0001");
				getFormValue(formassetmanage0001, getMapByJson(ajaxData));
				if(this.validateFormData(formassetmanage0001)){
					setObjValue(formassetmanage0001, mfAssetManage);
					mfAssetManageFeign.update(mfAssetManage);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
					
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}else{
				FormData formassetmanagebase = formService.getFormData("assetmanagebase");
				getFormValue(formassetmanagebase, getMapByJson(ajaxData));
				if(this.validateFormData(formassetmanagebase)){
					setObjValue(formassetmanagebase, mfAssetManage);
					mfAssetManageFeign.mfAssetManageApply(mfAssetManage);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getApplyDetailInfo")
	public String getApplyDetailInfo(Model model,String assetId,String ajaxData,String busEntrance) throws Exception{
		ActionContext.initialize(request, response);
		request.setAttribute("ifBizManger", "3");
		Map<String, Object> dataMap = new  HashMap<String,Object>();
		String query="";
		FormService formService = new FormService();
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setAssetId(assetId);
		mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
		FormData formassetmanageinfo = formService.getFormData("assetmanageinfo");
		getObjValue(formassetmanageinfo, mfAssetManage);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formassetmanageinfo,"propertySeeTag",query);
		dataMap=new HashMap<String,Object>();
		dataMap.put("htmlStr", htmlStr);
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("cusNo", mfAssetManage.getCusNo());
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfAssetManage", mfAssetManage);
		model.addAttribute("wkfAppId", mfAssetManage.getWkfAppId());
		model.addAttribute("formassetmanageinfo", formassetmanageinfo);
		model.addAttribute("query", query);
		return "/component/pact/assetmanage/AssetApplyDetail";
	}
	
	@RequestMapping(value = "/getTaskInfoAjax")
	@ResponseBody
	public Map<String, Object> getTaskInfoAjax(Model model,String assetId,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String,Object>();
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setAssetId(assetId);
		mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
		if(mfAssetManage.getWkfAppId() != null && mfAssetManage.getWkfAppId() != ""){
			TaskImpl task= mfAssetManageFeign.getTask(mfAssetManage.getWkfAppId());
			if(task == null){//表示业务已经结束
				dataMap.put("wkfFlag", "0");
				dataMap.put("mfAssetManage", mfAssetManage);
			}else{//表示业务尚未结束
				dataMap.put("wkfFlag", "1");
				//String url=SysTaskInfoUtil.openApproveUrl(task.getId());
				String title=task.getDescription();
				dataMap.put("result", task.getResult());
				dataMap.put("url", task.getApproveUrl());
				dataMap.put("title", title);
				dataMap.put("nodeName", task.getActivityName());
				
			}
		}
		dataMap.put("mfAssetManage", mfAssetManage);
		return dataMap;
	}
	/**
	 * 申请确认
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPointView")
	public String getPointView(Model model,String assetId,String wfkAppId,String taskId,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		//String taskId = request.getParameter("taskId");
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setAssetId(assetId);
		mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
		mfAssetManage.setTaskId(taskId);
		TaskImpl taskAppro = mfAssetManageFeign.getTask(mfAssetManage.getWkfAppId());
		String activityType = taskAppro.getActivityType();
		FormData formapplyapproval = formService.getFormData("applyapproval");
		getObjValue(formapplyapproval, mfAssetManage);
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		List<OptionsList> opinionTypeList1=new ArrayList<OptionsList>();
		for(int i=0;i<opinionTypeList.size();i++){
			if(("同意").equals(opinionTypeList.get(i).getOptionLabel()) || ("否决").equals(opinionTypeList.get(i).getOptionLabel())){
				opinionTypeList1.add(opinionTypeList.get(i));
			}
		}

		this.changeFormProperty(formapplyapproval, "opinionType", "optionArray", opinionTypeList1);
		model.addAttribute("formapplyapproval", formapplyapproval);
		model.addAttribute("mfAssetManage", mfAssetManage);
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("query", "");
		return "/component/pact/assetmanage/AssetManage_ViewPoint";
	}
	/**
	 * 申请保存和开启审批流程
	 * @param assetId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyUpdate")
	@ResponseBody
	public Map<String, Object> applyUpdate(String assetId,String ajaxData,String applyStatus) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new  HashMap<String,Object>();
		MfAssetManage mfAssetManage=new MfAssetManage();
		FormService formService = new FormService();
		try {
			dataMap=getMapByJson(ajaxData);
			if("0".equals(applyStatus)){
				FormData formassetmanage0001 = formService.getFormData("assetmanage0001");
				getFormValue(formassetmanage0001, getMapByJson(ajaxData));
				if(this.validateFormData(formassetmanage0001)){
					setObjValue(formassetmanage0001, mfAssetManage);
					mfAssetManageFeign.update(mfAssetManage);
					mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
					if(("0").equals(mfAssetManage.getApplyFlag())){
						mfAssetManage=mfAssetManageFeign.submitProcess(mfAssetManage, null);
					}else{
						mfAssetManage=mfAssetManageFeign.submitProcess1(mfAssetManage, null);
					}
					dataMap.put("flag", "success");
					//dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					//dataMap.put("flag", "success");
					dataMap.put("mfAssetManage", mfAssetManage);
					dataMap.put("msg", mfAssetManage.getMsg());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}else{
				FormData formassetmanagebase = formService.getFormData("assetmanagebase");
				getFormValue(formassetmanagebase, getMapByJson(ajaxData));
				if(this.validateFormData(formassetmanagebase)){
					setObjValue(formassetmanagebase, mfAssetManage);
					mfAssetManageFeign.mfAssetManageApply(mfAssetManage);
					mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
					if(("0").equals(mfAssetManage.getApplyFlag())){
						mfAssetManage=mfAssetManageFeign.submitProcess(mfAssetManage, null);
					}else{
						mfAssetManage=mfAssetManageFeign.submitProcess1(mfAssetManage, null);
					}

					dataMap.put("flag", "success");
					//dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					//dataMap.put("flag", "success");
					dataMap.put("mfAssetManage", mfAssetManage);
					dataMap.put("msg", mfAssetManage.getMsg());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		
		return dataMap;
	}
	/**
	 * 审批流程处理
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateForSubmit")
	@ResponseBody
	public Map<String, Object> updateForSubmit(String ajaxData,String appNo,String assetId,String nextUser) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		MfAssetManage mfAssetManage=new MfAssetManage();
		nextUser = request.getParameter("nextUser");
		try {
			mfAssetManage.setAssetId(assetId);
			mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
			dataMap = getMapByJson(ajaxData);
			FormData formapplyapproval = new FormService().getFormData("applyapproval");
			getFormValue(formapplyapproval, getMapByJson(ajaxData));
			String taskId = (String) dataMap.get("taskId");
			setObjValue(formapplyapproval, mfAssetManage);
			dataMap.put("mfAssetManage", mfAssetManage);
			Result res = mfAssetManageFeign.updateForSubmit(taskId, mfAssetManage.getWkfAppId(), mfAssetManage.getOpinionType(), mfAssetManage.getApprovalOpinion(), taskFeign.getTransitionsStr(taskId), User.getRegNo(this.getHttpRequest()), "",dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
		}
		return dataMap;
	}
	/**
	 * 获取客户借据信息
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getMfBusFinAppList")
	@ResponseBody
	public Map<String, Object> getMfBusFinAppList(String cusNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp=new MfBusFincApp();
		mfBusFincApp.setCusNo(cusNo);
		List<MfAssetManage> mfBusFinAppList=mfAssetManageFeign.getDetailByCusNo(cusNo);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr("tablemfbusfinapplist", "tableTag", mfBusFinAppList, null, true);
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	/**
	 * 点击申请状态查看详情和审批历史
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/showInfo")
	public String showInfo(Model model,String assetId,String applyStatus) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new  HashMap<String,Object>();
		String query="";
		FormService formService = new FormService();
		MfAssetManage mfAssetManage=new MfAssetManage();
		mfAssetManage.setAssetId(assetId);
		mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
		if("0".equals(applyStatus)){
			FormData formassetmanage0001 = formService.getFormData("assetmanage0001");
			getObjValue(formassetmanage0001, mfAssetManage);
			model.addAttribute("mfAssetManage", mfAssetManage);
			model.addAttribute("formassetmanage0001", formassetmanage0001);
			model.addAttribute("query", query);
			return "/component/pact/assetmanage/MfAssetManage_Update";
		}else{
			FormData formassetmanageinfo = formService.getFormData("assetmanageinfo");
			getObjValue(formassetmanageinfo, mfAssetManage);
			model.addAttribute("mfAssetManage", mfAssetManage);
			model.addAttribute("wkfAppId", mfAssetManage.getWkfAppId());
			model.addAttribute("formassetmanageinfo", formassetmanageinfo);
			model.addAttribute("query", query);
			return "/component/pact/assetmanage/MfAssetAanage_Detail";
		}
		
	}
	@RequestMapping(value = "/openJsp")
	public String openJsp(Model model,String applyFlag) throws Exception{
		applyFlag=request.getParameter("applyFlag");
		model.addAttribute("applyFlag", applyFlag);
		return "/component/pact/assetmanage/AssetManageApply_getList";
	}
	
	
	
	/**
	 * 审批流程处理
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateLawForSubmit")
	@ResponseBody
	public Map<String, Object> updateLawForSubmit(String ajaxData,String appNo,String assetId,String nextUser) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		MfAssetManage mfAssetManage=new MfAssetManage();
		nextUser = request.getParameter("nextUser");
		try {
			mfAssetManage.setAssetId(assetId);
			mfAssetManage=mfAssetManageFeign.getById(mfAssetManage);
			dataMap = getMapByJson(ajaxData);
			FormData formapplyapproval = new FormService().getFormData("applyapproval");
			getFormValue(formapplyapproval, getMapByJson(ajaxData));
			String taskId = (String) dataMap.get("taskId");
			setObjValue(formapplyapproval, mfAssetManage);
			dataMap.put("mfAssetManage", mfAssetManage);
			Result res = mfAssetManageFeign.updateLawForSubmit(taskId, mfAssetManage.getWkfAppId(), mfAssetManage.getOpinionType(), mfAssetManage.getApprovalOpinion(), taskFeign.getTransitionsStr(taskId), User.getRegNo(this.getHttpRequest()), "",dataMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
}
