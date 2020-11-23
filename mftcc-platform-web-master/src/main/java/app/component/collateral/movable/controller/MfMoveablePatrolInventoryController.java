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
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.collateral.movable.entity.MfMoveablePatrolInvenApproHis;
import app.component.collateral.movable.entity.MfMoveablePatrolInventory;
import app.component.collateral.movable.feign.MfMoveablePatrolInventoryFeign;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveablePatrolInventoryAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 20:28:17 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveablePatrolInventory")
public class MfMoveablePatrolInventoryController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveablePatrolInventoryBo
	@Autowired
	private MfMoveablePatrolInventoryFeign mfMoveablePatrolInventoryFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
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
		 return "/component/collateral/movable/MfMoveablePatrolInventory_List";
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
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		try {
			mfMoveablePatrolInventory.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveablePatrolInventory.setCriteriaList(mfMoveablePatrolInventory, ajaxData);//我的筛选
			//mfMoveablePatrolInventory.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveablePatrolInventory,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveablePatrolInventoryFeign.findByPage(ipage, mfMoveablePatrolInventory);
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
		FormData 	formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
			getFormValue(formmovablepatrol0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablepatrol0002)){
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
				setObjValue(formmovablepatrol0002, mfMoveablePatrolInventory);
				mfMoveablePatrolInventory=mfMoveablePatrolInventoryFeign.insert(mfMoveablePatrolInventory);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfMoveablePatrolInventory.getApproveNodeName());
				paramMap.put("opNo", mfMoveablePatrolInventory.getApprovePartName());
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
		FormData formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		getFormValue(formmovablepatrol0002, getMapByJson(ajaxData));
		MfMoveablePatrolInventory mfMoveablePatrolInventoryJsp = new MfMoveablePatrolInventory();
		setObjValue(formmovablepatrol0002, mfMoveablePatrolInventoryJsp);
		MfMoveablePatrolInventory mfMoveablePatrolInventory = mfMoveablePatrolInventoryFeign.getById(mfMoveablePatrolInventoryJsp);
		if(mfMoveablePatrolInventory!=null){
			try{
				mfMoveablePatrolInventory = (MfMoveablePatrolInventory)EntityUtil.reflectionSetVal(mfMoveablePatrolInventory, mfMoveablePatrolInventoryJsp, getMapByJson(ajaxData));
				mfMoveablePatrolInventoryFeign.update(mfMoveablePatrolInventory);
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
		FormData 	formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
			getFormValue(formmovablepatrol0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablepatrol0002)){
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
				setObjValue(formmovablepatrol0002, mfMoveablePatrolInventory);
				mfMoveablePatrolInventoryFeign.update(mfMoveablePatrolInventory);
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
	 * @param patrolId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String patrolId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setPatrolId(patrolId);
		mfMoveablePatrolInventory = mfMoveablePatrolInventoryFeign.getById(mfMoveablePatrolInventory);
		getObjValue(formmovablepatrol0002, mfMoveablePatrolInventory,formData);
		if(mfMoveablePatrolInventory!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param patrolId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String patrolId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setPatrolId(patrolId);
		try {
			mfMoveablePatrolInventoryFeign.delete(mfMoveablePatrolInventory);
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
		FormData formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setBusPleId(busPleId);
		getObjValue(formmovablepatrol0002, mfMoveablePatrolInventory);
		model.addAttribute("formmovablepatrol0002", formmovablepatrol0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInventory_Insert";
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
		FormData  formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		 getFormValue(formmovablepatrol0002);
		MfMoveablePatrolInventory  mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		 setObjValue(formmovablepatrol0002, mfMoveablePatrolInventory);
		 mfMoveablePatrolInventoryFeign.insert(mfMoveablePatrolInventory);
		 getObjValue(formmovablepatrol0002, mfMoveablePatrolInventory);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveablePatrolInventory> mfMoveablePatrolInventoryList = (List<MfMoveablePatrolInventory>)mfMoveablePatrolInventoryFeign.findByPage(this.getIpage(), mfMoveablePatrolInventory).getResult();
		model.addAttribute("formmovablepatrol0002", formmovablepatrol0002);
		model.addAttribute("mfMoveablePatrolInventoryList", mfMoveablePatrolInventoryList);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInventory_Insert";
	}
	/**
	 * 查询
	 * @param patrolId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String patrolId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablepatrol0001 = formService.getFormData("movablepatrol0001");
		 getFormValue(formmovablepatrol0001);
		MfMoveablePatrolInventory  mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setPatrolId(patrolId);
		 mfMoveablePatrolInventory = mfMoveablePatrolInventoryFeign.getById(mfMoveablePatrolInventory);
		 getObjValue(formmovablepatrol0001, mfMoveablePatrolInventory);
		model.addAttribute("formmovablepatrol0001", formmovablepatrol0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInventory_Detail";
	}
	/**
	 * 删除
	 * @param patrolId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String patrolId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setPatrolId(patrolId);
		mfMoveablePatrolInventoryFeign.delete(mfMoveablePatrolInventory);
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
		FormData  formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		 getFormValue(formmovablepatrol0002);
		 boolean validateFlag = this.validateFormData(formmovablepatrol0002);
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
		FormData  formmovablepatrol0002 = formService.getFormData("movablepatrol0002");
		 getFormValue(formmovablepatrol0002);
		 boolean validateFlag = this.validateFormData(formmovablepatrol0002);
	}
	/**
	 * 
	 * 方法描述： 打开巡库审批页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param patrolId 
	 * @param scNo 
	 * @param activityType 
	 * @date 2017-6-14 上午9:45:41
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String patrolId, Object scNo, String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmovablepatrolappro0002 = formService.getFormData("movablepatrolappro0002");
		MfMoveablePatrolInventory mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
		mfMoveablePatrolInventory.setPatrolId(patrolId);
		mfMoveablePatrolInventory=mfMoveablePatrolInventoryFeign.getById(mfMoveablePatrolInventory);
		getObjValue(formmovablepatrolappro0002, mfMoveablePatrolInventory);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfMoveablePatrolInventory.getBusPleId());
		dataMap.put("patrolId", patrolId);
		//scNo=BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(patrolId, null);
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		this.changeFormProperty(formmovablepatrolappro0002, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formmovablepatrolappro0002", formmovablepatrolappro0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/WkfMoveabledPatrolViewPoint";
	}
	/**
	 * 
	 * 方法描述： 巡库审批意见保存提交
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param taskId 
	 * @param appId 
	 * @param patrolId 
	 * @param transition 
	 * @param opNo 
	 * @param nextUser 
	 * @date 2017-6-14 下午3:01:59
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String patrolId, String transition, String opNo, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formmovablepatrolappro0002 = formService.getFormData("movablepatrolappro0002");
			getFormValue(formmovablepatrolappro0002, getMapByJson(ajaxData));
		MfMoveablePatrolInventory 	mfMoveablePatrolInventory = new MfMoveablePatrolInventory();
			MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis=new MfMoveablePatrolInvenApproHis();
			setObjValue(formmovablepatrolappro0002, mfMoveablePatrolInventory);
			setObjValue(formmovablepatrolappro0002, mfMoveablePatrolInvenApproHis);
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfMoveablePatrolInvenApproHis", mfMoveablePatrolInvenApproHis);
			dataMap.put("mfMoveablePatrolInventory", mfMoveablePatrolInventory);
			dataMap.put("taskId", taskId);
			dataMap.put("appId", appId);
			dataMap.put("patrolId", patrolId);
			dataMap.put("transition", transition);
			dataMap.put("opNo", opNo);
			dataMap.put("nextUser", nextUser);
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res=mfMoveablePatrolInventoryFeign.doCommit(dataMap);
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
}
