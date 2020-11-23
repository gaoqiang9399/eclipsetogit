package  app.component.checkoff.controller;
import app.base.User;
import app.component.checkoff.entity.MfBusCheckoffHis;
import app.component.checkoff.entity.MfBusCheckoffs;
import app.component.checkoff.feign.MfBusCheckoffHisFeign;
import app.component.checkoff.feign.MfBusCheckoffsFeign;
import app.component.common.EntityUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
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
 * Title: MfBusCheckoffHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 18 11:26:41 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCheckoffHis")
public class MfBusCheckoffHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusCheckoffHisBo
	@Autowired
	private MfBusCheckoffHisFeign mfBusCheckoffHisFeign;
	@Autowired
	private MfBusCheckoffsFeign mfBusCheckoffsFeign;
	//全局变量
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/checkoff/MfBusCheckoffHis_List";
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
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
		try {
			mfBusCheckoffHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusCheckoffHis.setCriteriaList(mfBusCheckoffHis, ajaxData);//我的筛选
			//mfBusCheckoffHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusCheckoffHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfBusCheckoffHisFeign.findByPage(ipage, mfBusCheckoffHis);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
			getFormValue(formcheckoffhisdetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcheckoffhisdetail)){
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
				setObjValue(formcheckoffhisdetail, mfBusCheckoffHis);
				mfBusCheckoffHisFeign.insert(mfBusCheckoffHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	
	
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData,String taskId,String checkoffId,String transition,String nextUser) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcheckoffhisdetail = formService.getFormData("checkoffhisadd");
		getFormValue(formcheckoffhisdetail, getMapByJson(ajaxData));
		MfBusCheckoffHis mfBusCheckoffHisJsp = new MfBusCheckoffHis();
		setObjValue(formcheckoffhisdetail, mfBusCheckoffHisJsp);
	
		Result res;
		try{
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfBusCheckoffHis", mfBusCheckoffHisJsp);

			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = mfBusCheckoffHisJsp.getApproveRemark();
			dataMap.put("orgNo", User.getOrgNo(request));
			res = mfBusCheckoffHisFeign.doCommit(taskId,checkoffId,opinionType,approvalOpinion,transition,User.getRegNo(this.getHttpRequest()),nextUser,dataMap);
			if(res.isSuccess()){
				dataMap.put("flag", "success");
				if(res.isEndSts()){
					dataMap.put("msg", res.getMsg());
				}else{
					dataMap.put("msg", res.getMsg());
				}
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SUBMIT.getMessage());
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
		FormData formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
		getFormValue(formcheckoffhisdetail, getMapByJson(ajaxData));
		MfBusCheckoffHis mfBusCheckoffHisJsp = new MfBusCheckoffHis();
		setObjValue(formcheckoffhisdetail, mfBusCheckoffHisJsp);
		MfBusCheckoffHis mfBusCheckoffHis = mfBusCheckoffHisFeign.getById(mfBusCheckoffHisJsp);
		if(mfBusCheckoffHis!=null){
			try{
				mfBusCheckoffHis = (MfBusCheckoffHis)EntityUtil.reflectionSetVal(mfBusCheckoffHis, mfBusCheckoffHisJsp, getMapByJson(ajaxData));
				mfBusCheckoffHisFeign.update(mfBusCheckoffHis);
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
		try{
		FormData 	formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
			getFormValue(formcheckoffhisdetail, getMapByJson(ajaxData));
			if(this.validateFormData(formcheckoffhisdetail)){
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
				setObjValue(formcheckoffhisdetail, mfBusCheckoffHis);
				mfBusCheckoffHisFeign.update(mfBusCheckoffHis);
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
	public Map<String, Object> getByIdAjax(String hisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
		mfBusCheckoffHis.setHisId(hisId);
		mfBusCheckoffHis = mfBusCheckoffHisFeign.getById(mfBusCheckoffHis);
		getObjValue(formcheckoffhisdetail, mfBusCheckoffHis,formData);
		if(mfBusCheckoffHis!=null){
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
	public Map<String, Object> deleteAjax(String hisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
		mfBusCheckoffHis.setHisId(hisId);
		try {
			mfBusCheckoffHisFeign.delete(mfBusCheckoffHis);
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
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
		model.addAttribute("formcheckoffhisdetail", formcheckoffhisdetail);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffHis_Insert";
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
		FormData  formcheckoffhisdetail = formService.getFormData("checkoffhisdetail");
		 getFormValue(formcheckoffhisdetail);
		MfBusCheckoffHis  mfBusCheckoffHis = new MfBusCheckoffHis();
		 setObjValue(formcheckoffhisdetail, mfBusCheckoffHis);
		 mfBusCheckoffHisFeign.insert(mfBusCheckoffHis);
		 getObjValue(formcheckoffhisdetail, mfBusCheckoffHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfBusCheckoffHis>  mfBusCheckoffHisList = (List<MfBusCheckoffHis>)mfBusCheckoffHisFeign.findByPage(this.getIpage(), mfBusCheckoffHis).getResult();
		model.addAttribute("formcheckoffhisdetail", formcheckoffhisdetail);
		model.addAttribute("mfBusCheckoffHisList", mfBusCheckoffHisList);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffHis_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String checkoffId,String hisId,String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		
		
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(checkoffId, null);// 当前审批节点task
	    String scNo = taskAppro.getActivityName();// 要件场景号，使用审批节点具体编号
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

		
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray=wkfInterfaceFeign.getBefNodes(checkoffId, null);
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		FormData  formcheckoffhisadd = formService.getFormData("checkoffhisadd");
		 getFormValue(formcheckoffhisadd);
		MfBusCheckoffHis  mfBusCheckoffHis = new MfBusCheckoffHis();
		mfBusCheckoffHis.setHisId(hisId);
		mfBusCheckoffHis.setCheckoffId(checkoffId);
		List<MfBusCheckoffHis>appHisList=mfBusCheckoffHisFeign.findByCheckOffId(mfBusCheckoffHis);
		
		MfBusCheckoffs mfBusCheckoffs=new MfBusCheckoffs();
		mfBusCheckoffs.setCheckoffId(checkoffId);
		mfBusCheckoffs=mfBusCheckoffsFeign.getById(mfBusCheckoffs);
		if(appHisList!=null){
			int len=appHisList.size();
			mfBusCheckoffHis.setFincShowId(mfBusCheckoffs.getFincShowId());
			if(len>0){
				mfBusCheckoffHis =appHisList.get(len-1);
				mfBusCheckoffHis.setApproveRemark("");
				mfBusCheckoffHis.setFincShowId(mfBusCheckoffs.getFincShowId());
				getObjValue(formcheckoffhisadd, mfBusCheckoffHis);
			}else{
				getObjValue(formcheckoffhisadd, mfBusCheckoffs);
			}
		}else {
			getObjValue(formcheckoffhisadd, mfBusCheckoffs);
		}
		 
		 
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		List<OptionsList> opinionTypeListTmpList=new ArrayList<OptionsList>();
		for(OptionsList bean:opinionTypeList){
			//只保留同意 ，否决 ，发回重审
			if("1".equals(bean.getOptionValue())||"2".equals(bean.getOptionValue())||"4".equals(bean.getOptionValue())){
				opinionTypeListTmpList.add(bean);
			}
		}
		
		this.changeFormProperty(formcheckoffhisadd, "opinionType", "optionArray", opinionTypeListTmpList);
		model.addAttribute("formcheckoffhisadd", formcheckoffhisadd);
		model.addAttribute("query", "");
		return "/component/checkoff/MfBusCheckoffHis_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String hisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusCheckoffHis mfBusCheckoffHis = new MfBusCheckoffHis();
		mfBusCheckoffHis.setHisId(hisId);
		mfBusCheckoffHisFeign.delete(mfBusCheckoffHis);
		return getListPage(model);
	}
}
