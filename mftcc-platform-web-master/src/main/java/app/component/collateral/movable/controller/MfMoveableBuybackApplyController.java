package  app.component.collateral.movable.controller;
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
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.movable.entity.MfMoveableBuybackApply;
import app.component.collateral.movable.entity.MfMoveableBuybackApproHis;
import app.component.collateral.movable.entity.MfMoveableBuybackConfirm;
import app.component.collateral.movable.feign.MfMoveableBuybackApplyFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableBuybackApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 19 11:37:03 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableBuybackApply")
public class MfMoveableBuybackApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableBuybackApplyBo
	@Autowired
	private MfMoveableBuybackApplyFeign mfMoveableBuybackApplyFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		 return "/component/collateral/movable/MfMoveableBuybackApply_List";
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
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
		try {
			mfMoveableBuybackApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableBuybackApply.setCriteriaList(mfMoveableBuybackApply, ajaxData);//我的筛选
			//mfMoveableBuybackApply.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableBuybackApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableBuybackApplyFeign.findByPage(ipage, mfMoveableBuybackApply);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
			getFormValue(formmovablebuyback0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablebuyback0002)){
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
				setObjValue(formmovablebuyback0002, mfMoveableBuybackApply);
				mfMoveableBuybackApplyFeign.insert(mfMoveableBuybackApply);
				dataMap.put("flag", "success");
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfMoveableBuybackApply.getApproveNodeName());
				paramMap.put("opNo", mfMoveableBuybackApply.getApprovePartName());
				dataMap.put("msg",MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
		getFormValue(formmovablebuyback0002, getMapByJson(ajaxData));
		MfMoveableBuybackApply mfMoveableBuybackApplyJsp = new MfMoveableBuybackApply();
		setObjValue(formmovablebuyback0002, mfMoveableBuybackApplyJsp);
		MfMoveableBuybackApply mfMoveableBuybackApply = mfMoveableBuybackApplyFeign.getById(mfMoveableBuybackApplyJsp);
		if(mfMoveableBuybackApply!=null){
			try{
				mfMoveableBuybackApply = (MfMoveableBuybackApply)EntityUtil.reflectionSetVal(mfMoveableBuybackApply, mfMoveableBuybackApplyJsp, getMapByJson(ajaxData));
				mfMoveableBuybackApplyFeign.update(mfMoveableBuybackApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
			getFormValue(formmovablebuyback0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablebuyback0002)){
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
				setObjValue(formmovablebuyback0002, mfMoveableBuybackApply);
				mfMoveableBuybackApplyFeign.update(mfMoveableBuybackApply);
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
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @param backId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String backId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
		mfMoveableBuybackApply.setBackId(backId);
		mfMoveableBuybackApply = mfMoveableBuybackApplyFeign.getById(mfMoveableBuybackApply);
		getObjValue(formmovablebuyback0002, mfMoveableBuybackApply,formData);
		if(mfMoveableBuybackApply!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param backId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String backId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
		mfMoveableBuybackApply.setBackId(backId);
		try {
			mfMoveableBuybackApplyFeign.delete(mfMoveableBuybackApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String , Object> map  =mfBusCollateralDetailRelFeign.getPledgeListData(busPleId);
	
		MfMoveableBuybackApply  mfMoveableBuybackApply = new MfMoveableBuybackApply();
		 mfMoveableBuybackApply.setPledgeNo((String )map.get("pledgeNo"));
		 mfMoveableBuybackApply.setPledgeName((String )map.get("pledgeName"));
		 mfMoveableBuybackApply.setBusPleId(busPleId);
		 Map<String,Object> dataMap= mfMoveableBuybackApplyFeign.getBuybackFlag(mfMoveableBuybackApply);
		 String conflag=(String) dataMap.get("flag");
		 if ("1".equals(conflag)) {
		FormData 	 formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
			 getObjValue(formmovablebuyback0002, mfMoveableBuybackApply);
		model.addAttribute("formmovablebuyback0002", formmovablebuyback0002);
		model.addAttribute("query", "");
			 return "/component/collateral/movable/MfMoveableBuybackApply_Insert";
		}else {
			MfMoveableBuybackConfirm mfMoveableBuybackConfirm = new MfMoveableBuybackConfirm();
			mfMoveableBuybackConfirm = (MfMoveableBuybackConfirm) dataMap.get("mfMoveableBuybackConfirm");
		FormData 	formbuybackconf0002 = formService.getFormData("buybackconf0002");
			 getObjValue(formbuybackconf0002, mfMoveableBuybackConfirm);
		model.addAttribute("formbuybackconf0002", formbuybackconf0002);
		model.addAttribute("query", "");
			 return "/component/collateral/movable/MfMoveableBuybackConfirm_Insert";
		}
	
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
		FormData  formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
		 getFormValue(formmovablebuyback0002);
		MfMoveableBuybackApply  mfMoveableBuybackApply = new MfMoveableBuybackApply();
		 setObjValue(formmovablebuyback0002, mfMoveableBuybackApply);
		 mfMoveableBuybackApplyFeign.insert(mfMoveableBuybackApply);
		 getObjValue(formmovablebuyback0002, mfMoveableBuybackApply);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableBuybackApply> mfMoveableBuybackApplyList = (List<MfMoveableBuybackApply>)mfMoveableBuybackApplyFeign.findByPage(this.getIpage(), mfMoveableBuybackApply).getResult();
		model.addAttribute("formmovablebuyback0002", formmovablebuyback0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableBuybackApply_Insert";
	}
	/**
	 * 查询
	 * @param backId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String backId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablebuyback0001 = formService.getFormData("movablebuyback0001");
		 getFormValue(formmovablebuyback0001);
		MfMoveableBuybackApply  mfMoveableBuybackApply = new MfMoveableBuybackApply();
		mfMoveableBuybackApply.setBackId(backId);
		 mfMoveableBuybackApply = mfMoveableBuybackApplyFeign.getById(mfMoveableBuybackApply);
		 getObjValue(formmovablebuyback0001, mfMoveableBuybackApply);
		model.addAttribute("formmovablebuyback0001", formmovablebuyback0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableBuybackApply_Detail";
	}
	/**
	 * 删除
	 * @param backId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String backId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
		mfMoveableBuybackApply.setBackId(backId);
		mfMoveableBuybackApplyFeign.delete(mfMoveableBuybackApply);
		return getListPage(model);
	}
	/**
	 * 
	 * 方法描述：打开回购审批页面 
	 * @return
	 * @throws Exception
	 * String
	 * @author ywh
	 * @param backId 
	 * @param activityType 
	 * @date 2017-6-19 下午5:21:10
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String backId, String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbuybackappro0001 = formService.getFormData("buybackappro0001");
		MfMoveableBuybackApply mfMoveableBuybackApply = new MfMoveableBuybackApply();
		mfMoveableBuybackApply.setBackId(backId);
		mfMoveableBuybackApply=mfMoveableBuybackApplyFeign.getById(mfMoveableBuybackApply);
		getObjValue(formbuybackappro0001, mfMoveableBuybackApply);
		dataMap = mfMoveableBuybackApplyFeign.getViewDataMap(mfMoveableBuybackApply.getBusPleId());
		dataMap.put("backId", backId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(backId, null);
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		this.changeFormProperty(formbuybackappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formbuybackappro0001", formbuybackappro0001);
		model.addAttribute("appId", appId);
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/WkfMoveabledBuybackViewPoint";
	}
	/**
	 * 
	 * 方法描述： 
	 * @return
	 * @throws Exception
	 * String
	 * @author 姚文豪
	 * @param taskId 
	 * @param appId 
	 * @param backId 
	 * @param transition 
	 * @param opNo 
	 * @param nextUser 
	 * @date 2017-6-12下午20:06:50
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String backId, String transition, String opNo, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formbuybackappro0001 = formService.getFormData("buybackappro0001");
			getFormValue(formbuybackappro0001, getMapByJson(ajaxData));
		MfMoveableBuybackApply 	mfMoveableBuybackApply = new MfMoveableBuybackApply();
			MfMoveableBuybackApproHis mfMoveableBuybackApproHis =new MfMoveableBuybackApproHis();
			setObjValue(formbuybackappro0001, mfMoveableBuybackApply);
			setObjValue(formbuybackappro0001, mfMoveableBuybackApproHis);
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfMoveableBuybackApproHis", mfMoveableBuybackApproHis);
			dataMap.put("mfMoveableBuybackApply", mfMoveableBuybackApply);
			dataMap.put("taskId", taskId);
			dataMap.put("appId", appId);
			dataMap.put("backId", backId);
			dataMap.put("transition", transition);
			dataMap.put("opNo", opNo);
			dataMap.put("nextUser", nextUser);
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res=mfMoveableBuybackApplyFeign.doCommit(dataMap);
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
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT);
			throw e;
		}
		return dataMap;
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
		FormData  formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
		 getFormValue(formmovablebuyback0002);
		 boolean validateFlag = this.validateFormData(formmovablebuyback0002);
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
		FormData  formmovablebuyback0002 = formService.getFormData("movablebuyback0002");
		 getFormValue(formmovablebuyback0002);
		 boolean validateFlag = this.validateFormData(formmovablebuyback0002);
	}
}
