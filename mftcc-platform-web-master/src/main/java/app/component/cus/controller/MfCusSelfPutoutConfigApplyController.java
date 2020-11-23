package  app.component.cus.controller;
import app.base.User;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusSelfPutoutConfigApply;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersBaseInfoFeign;
import app.component.cus.feign.MfCusSelfPutoutConfigApplyFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
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
 * Title: MfCusSelfPutoutConfigApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Dec 27 11:46:54 CST 2017
 **/
@Controller
@RequestMapping("/mfCusSelfPutoutConfigApply")
public class MfCusSelfPutoutConfigApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusSelfPutoutConfigApplyBo
	@Autowired
	private MfCusSelfPutoutConfigApplyFeign mfCusSelfPutoutConfigApplyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfCusPersBaseInfoFeign mfCusPersBaseInfoFeign;
	//全局变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusSelfPutoutConfigApply_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		try {
			mfCusSelfPutoutConfigApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusSelfPutoutConfigApply.setCriteriaList(mfCusSelfPutoutConfigApply, ajaxData);//我的筛选
			//mfCusSelfPutoutConfigApply.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusSelfPutoutConfigApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusSelfPutoutConfigApply", mfCusSelfPutoutConfigApply));
			ipage = mfCusSelfPutoutConfigApplyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 *  阳光银行放款渠道可否自助放款设置
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model,String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formcommon = formService.getFormData("cusselfputoutchannelset");
		MfCusCustomer mfCusCustomer  = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		MfCusPersBaseInfo mfCusPersBaseInfo = new MfCusPersBaseInfo();
		mfCusPersBaseInfo.setCusNo(cusNo);
		mfCusPersBaseInfo = mfCusPersBaseInfoFeign.getById(mfCusPersBaseInfo);
		if(StringUtil.isEmpty(mfCusCustomer.getPostalCode()) || mfCusCustomer.getPostalCode() == null){
			mfCusCustomer.setPostalCode(mfCusPersBaseInfo.getPostalCode());
		}
		if(!StringUtil.isEmpty(mfCusCustomer.getSelfPutoutChannelSet())){
			
			Map map = new Gson().fromJson(mfCusCustomer.getSelfPutoutChannelSet(),Map.class);
			getObjValue(formcommon, map);
		}
		getObjValue(formcommon, mfCusCustomer);
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSelfPutoutConfigApply_Insert";
	}
	
	/**
	 * 
	 * 方法描述： 保存自助放款渠道设置 
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-12-27 下午12:03:46
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap = getMapByJson(ajaxData);
			String cusNo =  (String)dataMap.get("cusNo");
			//查询该客户是否有正在审批的参数调整申请
		MfCusSelfPutoutConfigApply 	mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
			mfCusSelfPutoutConfigApply.setCusNo(cusNo);
			mfCusSelfPutoutConfigApply.setAppSts("1");//流程中
			mfCusSelfPutoutConfigApply = mfCusSelfPutoutConfigApplyFeign.getById(mfCusSelfPutoutConfigApply);
			if(mfCusSelfPutoutConfigApply!=null){
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.WAIT_OPERATION.getMessage("进行审批"));
			}else{
				String formId = (String)dataMap.get("formId");
		FormData formcommon = formService.getFormData(formId);
				getFormValue(formcommon, dataMap);
				if(this.validateFormData(formcommon)){
					MfCusSelfPutoutConfigApply 	mfCusSelfPutoutConfigApply1 = new MfCusSelfPutoutConfigApply();
					setObjValue(formcommon, mfCusSelfPutoutConfigApply1);
//					mfCusSelfPutoutConfigApply1.setCusNo(cusNo);
//					mfCusSelfPutoutConfigApply1.setAppSts("1");//流程中
//					mfCusSelfPutoutConfigApply1.setCurrentSessionOrgNo(User.getOrgNo(request));
					Map<String, String> resMap = mfCusSelfPutoutConfigApplyFeign.insert(mfCusSelfPutoutConfigApply1);
					dataMap.put("flag", "success");
					dataMap.put("msg", resMap.get("msg"));
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg",this.getFormulavaliErrorMsg());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 调整至审批页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-12-27 下午5:24:56
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String id,String taskId,String hideOpinionType,String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcommon = formService.getFormData("cusselfputoutchannelsetapproval");
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		mfCusSelfPutoutConfigApply.setId(id);
		mfCusSelfPutoutConfigApply = mfCusSelfPutoutConfigApplyFeign.getById(mfCusSelfPutoutConfigApply);
		getObjValue(formcommon, mfCusSelfPutoutConfigApply);
		
		MfCusCustomer mfCusCustomer  = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfCusSelfPutoutConfigApply.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		getObjValue(formcommon, mfCusCustomer);
		//20191024 333
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(User.getRegNo(request), taskId);// 当前审批节点task
		String nodeNo = taskAppro.getActivityName();//审批流程中当前审批节点编号
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formcommon, "opinionType", "optionArray", opinionTypeList);
		
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("query", "");
		model.addAttribute("id", id);
		return "/component/cus/MfCusSelfPutoutConfigApply_WkfViewPoint";
	}
	/**
	 * 
	 * 方法描述： 变更自助端允许自助放款控制时，审批提交
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-12-27 下午3:51:28
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData,String id,String taskId,String transition,String nextUser) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map map = getMapByJson(ajaxData);
		FormData formcommon = formService.getFormData((String) map.get("formId"));
		getFormValue(formcommon,map);
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		setObjValue(formcommon, mfCusSelfPutoutConfigApply);
		dataMap=getMapByJson(ajaxData);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try{
			dataMap.put("taskId", taskId);
			dataMap.put("regNo", User.getRegNo(this.getHttpRequest()));
			dataMap.put("id", id);
			dataMap.put("opinionType", opinionType);
			dataMap.put("approvalOpinion", approvalOpinion);
			dataMap.put("transition", transition);
			dataMap.put("nextUser", nextUser);
			dataMap.put("mfCusSelfPutoutConfigApply", mfCusSelfPutoutConfigApply);
			dataMap.put("opNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res = mfCusSelfPutoutConfigApplyFeign.doCommit(dataMap);
//			Result res = mfCusSelfPutoutConfigApplyBo.doCommit(taskId,id,opinionType,approvalOpinion,transition,User.getRegNo(this.getHttpRequest()),nextUser,mfCusSelfPutoutConfigApply,dataMap);
			if(res.isSuccess()){
				dataMap.put("flag", "success");
				dataMap.put("msg",res.getMsg());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",res.getMsg());
			}
		}catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcommon = formService.getFormData("common");
		getFormValue(formcommon, getMapByJson(ajaxData));
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApplyJsp = new MfCusSelfPutoutConfigApply();
		setObjValue(formcommon, mfCusSelfPutoutConfigApplyJsp);
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = mfCusSelfPutoutConfigApplyFeign.getById(mfCusSelfPutoutConfigApplyJsp);
		if(mfCusSelfPutoutConfigApply!=null){
			try{
				mfCusSelfPutoutConfigApply = (MfCusSelfPutoutConfigApply)EntityUtil.reflectionSetVal(mfCusSelfPutoutConfigApply, mfCusSelfPutoutConfigApplyJsp, getMapByJson(ajaxData));
				mfCusSelfPutoutConfigApplyFeign.update(mfCusSelfPutoutConfigApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		try{
		FormData 	formcommon = formService.getFormData("common");
			getFormValue(formcommon, getMapByJson(ajaxData));
			if(this.validateFormData(formcommon)){
				setObjValue(formcommon, mfCusSelfPutoutConfigApply);
				mfCusSelfPutoutConfigApplyFeign.update(mfCusSelfPutoutConfigApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcommon = formService.getFormData("common");
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		mfCusSelfPutoutConfigApply.setId(id);
		mfCusSelfPutoutConfigApply = mfCusSelfPutoutConfigApplyFeign.getById(mfCusSelfPutoutConfigApply);
		getObjValue(formcommon, mfCusSelfPutoutConfigApply,formData);
		if(mfCusSelfPutoutConfigApply!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		mfCusSelfPutoutConfigApply.setId(id);
		try {
			mfCusSelfPutoutConfigApplyFeign.delete(mfCusSelfPutoutConfigApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcommon = formService.getFormData("common");
		 getFormValue(formcommon);
		MfCusSelfPutoutConfigApply  mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		 setObjValue(formcommon, mfCusSelfPutoutConfigApply);
		 mfCusSelfPutoutConfigApplyFeign.insert(mfCusSelfPutoutConfigApply);
		 getObjValue(formcommon, mfCusSelfPutoutConfigApply);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("mfCusSelfPutoutConfigApply", mfCusSelfPutoutConfigApply));

		 List<MfCusSelfPutoutConfigApply> mfCusSelfPutoutConfigApplyList = (List<MfCusSelfPutoutConfigApply>)mfCusSelfPutoutConfigApplyFeign.findByPage(ipage).getResult();
		model.addAttribute("formcommon", formcommon);
		model.addAttribute("mfCusSelfPutoutConfigApplyList", mfCusSelfPutoutConfigApplyList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSelfPutoutConfigApply_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusselfputoutchannelset = formService.getFormData("cusselfputoutchannelset");
		 getFormValue(formcusselfputoutchannelset);
		MfCusSelfPutoutConfigApply  mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		mfCusSelfPutoutConfigApply.setId(id);
		 mfCusSelfPutoutConfigApply = mfCusSelfPutoutConfigApplyFeign.getById(mfCusSelfPutoutConfigApply);
		 getObjValue(formcusselfputoutchannelset, mfCusSelfPutoutConfigApply);
		model.addAttribute("formcusselfputoutchannelset", formcusselfputoutchannelset);
		model.addAttribute("query", "");
		return "/component/cus/MfCusSelfPutoutConfigApply_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusSelfPutoutConfigApply mfCusSelfPutoutConfigApply = new MfCusSelfPutoutConfigApply();
		mfCusSelfPutoutConfigApply.setId(id);
		mfCusSelfPutoutConfigApplyFeign.delete(mfCusSelfPutoutConfigApply);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcommon = formService.getFormData("common");
		 getFormValue(formcommon);
		 boolean validateFlag = this.validateFormData(formcommon);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcommon = formService.getFormData("common");
		 getFormValue(formcommon);
		 boolean validateFlag = this.validateFormData(formcommon);
	}
	

}
