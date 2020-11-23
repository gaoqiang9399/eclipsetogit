package  app.component.oa.badge.controller;
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
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.badge.entity.MfOaBadge;
import app.component.oa.badge.entity.MfOaBadgeWkf;
import app.component.oa.badge.feign.MfOaBadgeFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfOaBadgeAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 01 09:00:01 CST 2017
 **/
@Controller
@RequestMapping("/mfOaBadge")
public class MfOaBadgeController extends BaseFormBean{
	//注入mfOaBadgeFeign
	@Autowired
	private MfOaBadgeFeign mfOaBadgeFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private TaskFeign taskFeign;
	/**
	 * 打开审批页面
	 */
	@RequestMapping("/approvalHis")
	public String approvalHis(Model model) throws Exception{
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/badge/MfOaBadgeMst_ApprovalHis";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		//加载字典选项,用于自定义查询
		JSONArray consAppTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("BADGE_STYLE");
		model.addAttribute("busiTypeJsonArray", consAppTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/badge/MfOaBadge_List";
	}
	
	@RequestMapping("/getListPage2")
	public String getListPage2(Model model) throws Exception {
		ActionContext.initialize(request, response);
		//加载字典选项,用于自定义查询
		JSONArray consAppTypeJsonArray = new CodeUtils().getJSONArrayByKeyName("BADGE_STYLE");
		model.addAttribute("busiTypeJsonArray", consAppTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/oa/badge/MfOaBadge_List2";
	}
	
	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,String tableId,String tableType,Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfOaBadge.setCriteriaList(mfOaBadge, ajaxData);//我的筛选
			//mfOaBadge.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfOaBadge,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			Map<String, Object> params = new HashMap<>();
			params.put("mfOaBadge", mfOaBadge);
			ipage.setParams(params );
			//自定义查询Bo方法
			ipage = mfOaBadgeFeign.findByPage(ipage);
			
			//对时间进行格式化
			
				/*
				   lst_mod_time         varchar(17) comment '修改时间',
   					revert_time          varchar(17) comment '归还时间',
   					out_time             varchar(17) comment '出章时间',
				 */
			
			
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
	 * 查询管理列表所需要的数据
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjaxManage")
	public Map<String, Object> findByPageAjaxManage(String ajaxData,String tableId,String tableType,Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfOaBadge.setCriteriaList(mfOaBadge, ajaxData);//我的筛选
			//mfOaBadge.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfOaBadge,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			Map<String, Object> params = new HashMap<>();
			params.put("mfOaBadge", mfOaBadge);
			ipage.setParams(params );
			//自定义查询Bo方法
			ipage = mfOaBadgeFeign.findByPageManage(ipage);
			
			//对时间进行格式化
			
				/*
				   lst_mod_time         varchar(17) comment '修改时间',
   					revert_time          varchar(17) comment '归还时间',
   					out_time             varchar(17) comment '出章时间',
				 */
			
			
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formbadge0002 = new FormService().getFormData("badge0002");
			getFormValue(formbadge0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbadge0002)){
				MfOaBadge mfOaBadge = new MfOaBadge();
				setObjValue(formbadge0002, mfOaBadge);
				String insertStr = mfOaBadgeFeign.insert(mfOaBadge);
				dataMap.put("flag", "success");
				dataMap.put("msg", insertStr);
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbadge0002 = new FormService().getFormData("badge0002");
		getFormValue(formbadge0002, getMapByJson(ajaxData));
		MfOaBadge mfOaBadgeJsp = new MfOaBadge();
		setObjValue(formbadge0002, mfOaBadgeJsp);
		MfOaBadge mfOaBadge = mfOaBadgeFeign.getById(mfOaBadgeJsp);
		if(mfOaBadge!=null){
			try{
				mfOaBadge = (MfOaBadge)EntityUtil.reflectionSetVal(mfOaBadge, mfOaBadgeJsp, getMapByJson(ajaxData));
				mfOaBadgeFeign.update(mfOaBadge);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				dataMap.put("flag", "error");
				dataMap.put("msg", "修改失败");
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
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			MfOaBadge mfOaBadge = new MfOaBadge();
			FormData formbadge0002 = new FormService().getFormData("badge0002");
			getFormValue(formbadge0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbadge0002)){
				mfOaBadge = new MfOaBadge();
				setObjValue(formbadge0002, mfOaBadge);
				mfOaBadgeFeign.update(mfOaBadge);
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> formData = new HashMap<String,Object>();
		FormData formbadge0002 = new FormService().getFormData("badge0002");
		MfOaBadge mfOaBadge = new MfOaBadge();
		mfOaBadge.setBadgeNo(badgeNo);
		mfOaBadge = mfOaBadgeFeign.getById(mfOaBadge);
		getObjValue(formbadge0002, mfOaBadge,formData);
		if(mfOaBadge!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	
	/**
	 * 处理处理出去章操作
	 * 
	 */
	@ResponseBody
	@RequestMapping("/discussOutBadge")
	public Map<String,Object> discussOutBadge(String badgeNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setBadgeNo(badgeNo);
			mfOaBadgeFeign.discussOutBadge(mfOaBadge);
			//dataMap.put("flag", MessageEnum.SUCCEED.getMessage());
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	@ResponseBody
	@RequestMapping("/discussRevertBadge")
	public Map<String,Object> discussRevertBadge(String badgeNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setBadgeNo(badgeNo);
			mfOaBadgeFeign.discussRevertBadge(mfOaBadge);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String badgeNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setBadgeNo(badgeNo);
			mfOaBadgeFeign.delete(mfOaBadge);
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
	 * 
	 * 
	 */
	@ResponseBody
	@RequestMapping("/confirmAjax")
	public Map<String, Object> confirmAjax(String badgeNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setBadgeNo(badgeNo);
			String result = mfOaBadgeFeign.confirm(mfOaBadge);
			if("1234".equals(result)){
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", result);
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
		FormData formbadge0002 = new FormService().getFormData("badge0002");
		MfOaBadge mfOaBadge = new MfOaBadge();
		mfOaBadge.setCust(User.getRegName(this.getHttpRequest()));
		//mfOaBadge.setApplyTime(DateUtil.getYear()+"-"+DateUtil.getMonth()+"-"+DateUtil.getDay()+" "+DateUtil.getTime());
		mfOaBadge.setApplyTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		//mfOaBadge.setApplyTime(DateUtil.getTime());

		 getFormValue(formbadge0002);
		 getObjValue(formbadge0002, mfOaBadge);
		 model.addAttribute("formbadge0002", formbadge0002);
		 model.addAttribute("mfOaBadge", mfOaBadge);
		 model.addAttribute("query", "");
		return "/component/oa/badge/MfOaBadge_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String badgeNo, Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormData formbadge0001 = new FormService().getFormData("badge0001");
		getFormValue(formbadge0001);
		 MfOaBadge mfOaBadge = new MfOaBadge();
		 mfOaBadge.setBadgeNo(badgeNo);
		 mfOaBadge = mfOaBadgeFeign.getById(mfOaBadge);
		 String time = mfOaBadge.getApplyTime();
		 //20170612 09:13:09
		 String time2 = DateUtil.StringToString(time, "yyyyMMdd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
		 //HH:mm:ss yyyyMMdd
		 mfOaBadge.setApplyTime(time2);
		 if((!"".equals(mfOaBadge.getRevertTime()))&&mfOaBadge.getRevertTime()!=null){
			  mfOaBadge.setRevertTime(DateUtil.getShowDateTime(mfOaBadge.getRevertTime()));
		 }
		if((!"".equals(mfOaBadge.getOutTime()))&&mfOaBadge.getOutTime()!=null){
			mfOaBadge.setOutTime(DateUtil.getShowDateTime(mfOaBadge.getOutTime()));
		}
		 getObjValue(formbadge0001, mfOaBadge);
		 model.addAttribute("formbadge0001", formbadge0001);
		 model.addAttribute("mfOaBadge", mfOaBadge);
		 model.addAttribute("query", "");
		return "/component/oa/badge/MfOaBadge_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormData formbadge0002 = new FormService().getFormData("badge0002");
		getFormValue(formbadge0002);
		boolean validateFlag = this.validateFormData(formbadge0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormData formbadge0002 = new FormService().getFormData("badge0002");
		getFormValue(formbadge0002);
		boolean validateFlag = this.validateFormData(formbadge0002);
	}
	
	/**
	 * 进入审批视角,审批页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(String badgeNo,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaBadge mfOaBadge = new MfOaBadge();
		mfOaBadge.setBadgeNo(badgeNo);
		mfOaBadge = mfOaBadgeFeign.getById(mfOaBadge);
		/*
		//如果该记录审批通过
		if("2".equals(mfOaBadge.getState())){
			otMap.put("4", "已经归还");
		//如果该记录正在审批
		}else if("1".equals(mfOaBadge.getState())){
			otMap.put("2", "同意");
			otMap.put("3","拒绝");
		}
		*/
		Map<String,Object> otMap=new HashMap<>();
		otMap.put("1", "通过");
		otMap.put("2", "拒绝");
		model.addAttribute("mfOaBadge", mfOaBadge);
		model.addAttribute("otMap",otMap );
		model.addAttribute("query","" );
		return "/component/oa/badge/MfOaBadge_ViewPoint";
	}
	
	
	//处理经理的下滑式待处理
	@ResponseBody
	@RequestMapping("/submitForUpdate1")
	public Map<String, Object> submitForUpdate1(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData badge0006 = new FormService().getFormData("badge0006");
		    getFormValue(badge0006, getMapByJson(ajaxData));			
		    MfOaBadgeWkf mfOaBadgeWkf = new MfOaBadgeWkf();
		    setObjValue(badge0006, mfOaBadgeWkf);	
		    String opinionType = mfOaBadgeWkf.getOpinionType();
			MfOaBadge mfOaBadge = new MfOaBadge();
			String appNo = mfOaBadgeWkf.getAppNo();
			mfOaBadge.setBadgeNo(appNo);
			mfOaBadge = mfOaBadgeFeign.getById(mfOaBadge);
			String taskId = mfOaBadgeWkf.getTaskId();
			if(mfOaBadgeWkf.getIsChairman()!=null&&(!"".equals(mfOaBadgeWkf.getIsChairman()))){
				Map<String,Object> thisMap = new HashMap<String,Object>();
				thisMap.put("isChairman",mfOaBadgeWkf.getIsChairman());
				taskFeign.setVariables(taskId,thisMap);
			}
			
			String transition=null;
			if(AppConstant.OPINION_TYPE_ARREE.equals(opinionType)){//同意
				transition=taskFeign.getTransitionsStr(taskId);
				/*appAuth.setAppSts(BizPubParm.APP_STS_PASS);*/
			}else if(AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)){//不同意（否决
				transition=taskFeign.getTransitionsStr(taskId);					
			}else {
			}
			mfOaBadge.setIsChairman(mfOaBadgeWkf.getIsChairman());
			
			Result res = mfOaBadgeFeign.updateForSubmit(taskId, appNo, opinionType, mfOaBadgeWkf.getOpinionType(), transition, User.getRegNo(this.request), "", mfOaBadge, mfOaBadgeWkf.getBadgeNodeType());
			//this.appAuthBo.updateForSubmit(appAuth);
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());				
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	
	//处理下滑式的处理
	@ResponseBody
	@RequestMapping("/submitForUpdate")
	public Map<String, Object> submitForUpdate(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData badge0003 = new FormService().getFormData("badge0003");
		    getFormValue(badge0003, getMapByJson(ajaxData));			
		    MfOaBadgeWkf mfOaBadgeWkf = new MfOaBadgeWkf();
		    setObjValue(badge0003, mfOaBadgeWkf);	
		    String opinionType = mfOaBadgeWkf.getOpinionType();
			MfOaBadge mfOaBadge = new MfOaBadge();
			String appNo = mfOaBadgeWkf.getAppNo();
			mfOaBadge.setBadgeNo(appNo);
			mfOaBadge = mfOaBadgeFeign.getById(mfOaBadge);
			String taskId = mfOaBadgeWkf.getTaskId();
			if(mfOaBadgeWkf.getIsChairman()!=null&&(!"".equals(mfOaBadgeWkf.getIsChairman()))){
				Map<String,Object> thisMap = new HashMap<String,Object>();
				thisMap.put("isChairman",mfOaBadgeWkf.getIsChairman());
				taskFeign.setVariables(taskId,thisMap);
			}
			
			String transition = null;
			if(AppConstant.OPINION_TYPE_ARREE.equals(opinionType)){//同意
				transition =taskFeign.getTransitionsStr(taskId);
				/*appAuth.setAppSts(BizPubParm.APP_STS_PASS);*/
			}else if(AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)){//不同意（否决
				transition=taskFeign.getTransitionsStr(taskId);					
			}else {
			}
			mfOaBadge.setIsChairman(mfOaBadgeWkf.getIsChairman());
			Result res = mfOaBadgeFeign.updateForSubmit(taskId, appNo, opinionType, mfOaBadgeWkf.getOpinionType(), transition, User.getRegNo(this.request), "", mfOaBadge, mfOaBadgeWkf.getBadgeNodeType());
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());				
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	//处理流程提交
	@ResponseBody
	@RequestMapping("/submitProcess")
	public Map<String, Object> submitProcess(String badgeNo) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			MfOaBadge mfOaBadge = new MfOaBadge();
			mfOaBadge.setBadgeNo(badgeNo);
			mfOaBadge  = mfOaBadgeFeign.getById(mfOaBadge);
			if(!"0".equals(mfOaBadge.getBadgeSts())){
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage()+" 只有状态为未提交才能进行提交操作!");
			}
			
			mfOaBadgeFeign.submitProcess(mfOaBadge);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	//转账开户页面
	
}
