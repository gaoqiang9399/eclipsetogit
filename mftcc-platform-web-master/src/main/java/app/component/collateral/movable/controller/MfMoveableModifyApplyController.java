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
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.collateral.movable.entity.MfMoveableModifyApply;
import app.component.collateral.movable.entity.MfMoveableModifyApproHis;
import app.component.collateral.movable.feign.MfMoveableModifyApplyFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableModifyApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 12 16:12:17 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableModifyApply")
public class MfMoveableModifyApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableModifyApplyBo
	@Autowired
	private MfMoveableModifyApplyFeign mfMoveableModifyApplyFeign;
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
		 return "/component/collateral/movable/MfMoveableModifyApply_List";
	}
	/***
	 * 列表数据查询
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String busPleId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply.setBusPleId(busPleId);
		try {
			mfMoveableModifyApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableModifyApply.setCriteriaList(mfMoveableModifyApply, ajaxData);//我的筛选
			//mfMoveableModifyApply.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableModifyApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableModifyApplyFeign.findByPage(ipage, mfMoveableModifyApply);
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
		FormData 	formmodify0002 = formService.getFormData("modify0002");
			getFormValue(formmodify0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmodify0002)){
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
				setObjValue(formmodify0002, mfMoveableModifyApply);
				mfMoveableModifyApply=mfMoveableModifyApplyFeign.insert(mfMoveableModifyApply);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfMoveableModifyApply.getApproveNodeName());
				paramMap.put("opNo", mfMoveableModifyApply.getApprovePartName());
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
		FormData formmodify0002 = formService.getFormData("modify0002");
		getFormValue(formmodify0002, getMapByJson(ajaxData));
		MfMoveableModifyApply mfMoveableModifyApplyJsp = new MfMoveableModifyApply();
		setObjValue(formmodify0002, mfMoveableModifyApplyJsp);
		MfMoveableModifyApply mfMoveableModifyApply = mfMoveableModifyApplyFeign.getById(mfMoveableModifyApplyJsp);
		if(mfMoveableModifyApply!=null){
			try{
				mfMoveableModifyApply = (MfMoveableModifyApply)EntityUtil.reflectionSetVal(mfMoveableModifyApply, mfMoveableModifyApplyJsp, getMapByJson(ajaxData));
				mfMoveableModifyApplyFeign.update(mfMoveableModifyApply);
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
		FormData 	formmodify0002 = formService.getFormData("modify0002");
			getFormValue(formmodify0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmodify0002)){
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
				setObjValue(formmodify0002, mfMoveableModifyApply);
				mfMoveableModifyApplyFeign.update(mfMoveableModifyApply);
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
	 * @param modifyId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String modifyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmodify0002 = formService.getFormData("modify0002");
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply.setModifyId(modifyId);
		mfMoveableModifyApply = mfMoveableModifyApplyFeign.getById(mfMoveableModifyApply);
		getObjValue(formmodify0002, mfMoveableModifyApply,formData);
		if(mfMoveableModifyApply!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param modifyId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String modifyId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply.setModifyId(modifyId);
		try {
			mfMoveableModifyApplyFeign.delete(mfMoveableModifyApply);
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
	 * @param appId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId, String appId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formmodify0002 = formService.getFormData("modify0002");
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply = mfMoveableModifyApplyFeign.initTransferApply(busPleId, appId);
		getObjValue(formmodify0002, mfMoveableModifyApply);
		model.addAttribute("formmodify0002", formmodify0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApply_Insert";
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
		FormData  formmodify0002 = formService.getFormData("modify0002");
		 getFormValue(formmodify0002);
		MfMoveableModifyApply  mfMoveableModifyApply = new MfMoveableModifyApply();
		 setObjValue(formmodify0002, mfMoveableModifyApply);
		 mfMoveableModifyApplyFeign.insert(mfMoveableModifyApply);
		 getObjValue(formmodify0002, mfMoveableModifyApply);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableModifyApply> mfMoveableModifyApplyList = (List<MfMoveableModifyApply>)mfMoveableModifyApplyFeign.findByPage(this.getIpage(), mfMoveableModifyApply).getResult();
		model.addAttribute("formmodify0002", formmodify0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApply_Insert";
	}
	/**
	 * 查询
	 * @param modifyId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String modifyId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmodify0001 = formService.getFormData("modify0001");
		 getFormValue(formmodify0001);
		MfMoveableModifyApply  mfMoveableModifyApply = new MfMoveableModifyApply();
		 mfMoveableModifyApply.setModifyId(modifyId);
		 mfMoveableModifyApply = mfMoveableModifyApplyFeign.getById(mfMoveableModifyApply);
		 getObjValue(formmodify0001, mfMoveableModifyApply);
		model.addAttribute("formmodify0001", formmodify0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApply_Detail";
	}
	/**
	 * 删除
	 * @param modifyId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String modifyId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply.setModifyId(modifyId);
		mfMoveableModifyApplyFeign.delete(mfMoveableModifyApply);
		return getListPage(model);
	}
	/**
	 * 
	 * 方法描述：打开调价审批页面 
	 * @return
	 * @throws Exception
	 * String
	 * @author ywh
	 * @param modifyId 
	 * @param activityType 
	 * @date 2017-6-12 下午5:21:10
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String modifyId, String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmodifyappro0001 = formService.getFormData("modifyappro0001");
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		mfMoveableModifyApply.setModifyId(modifyId);
		mfMoveableModifyApply=mfMoveableModifyApplyFeign.getById(mfMoveableModifyApply);
		getObjValue(formmodifyappro0001, mfMoveableModifyApply);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfMoveableModifyApply.getBusPleId());
		dataMap.put("modifyId", modifyId);
		String scNo = BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(modifyId, null);
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		this.changeFormProperty(formmodifyappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formmodifyappro0001", formmodifyappro0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/WkfMoveabledModifyViewPoint";
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
	 * @param modifyId 
	 * @param transition 
	 * @param nextUser 
	 * @date 2017-6-12下午20:06:50
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String modifyId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formmodifyappro0001 = formService.getFormData("modifyappro0001");
			getFormValue(formmodifyappro0001, getMapByJson(ajaxData));
		MfMoveableModifyApply 	mfMoveableModifyApply = new MfMoveableModifyApply();
			MfMoveableModifyApproHis mfMoveableModifyApproHis =new MfMoveableModifyApproHis();
			setObjValue(formmodifyappro0001, mfMoveableModifyApply);
			setObjValue(formmodifyappro0001, mfMoveableModifyApproHis);
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfMoveableModifyApproHis", mfMoveableModifyApproHis);
			dataMap.put("mfMoveableModifyApply", mfMoveableModifyApply);
			dataMap.put("taskId", taskId);
			dataMap.put("appId", appId);
			dataMap.put("modifyId", modifyId);
			dataMap.put("transition", transition);
			dataMap.put("nextUser", nextUser);
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res=mfMoveableModifyApplyFeign.doCommit(dataMap);
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
	 * 
	 * 方法描述： 根据押品编号获得关联的货物明细
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param modifyId 
	 * @param tableId 
	 * @date 2017-6-14 下午8:31:50
	 */
	@RequestMapping(value = "/getModifyTableDataByPledgeNoAjax")
	@ResponseBody
	public Map<String, Object> getModifyTableDataByPledgeNoAjax(String ajaxData, String modifyId, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableModifyApply mfMoveableModifyApply = new MfMoveableModifyApply();
		try {
			mfMoveableModifyApply.setModifyId(modifyId);
			JsonTableUtil jtu = new JsonTableUtil();
			List<PledgeBaseInfoBill> resultList = mfMoveableModifyApplyFeign.getModifyBillListByPledgeNo(mfMoveableModifyApply);
			String  tableHtml = jtu.getJsonStr(tableId,"tableTag", resultList, null,true);
			dataMap.put("tableData",tableHtml);
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
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formmodify0002 = formService.getFormData("modify0002");
		 getFormValue(formmodify0002);
		 boolean validateFlag = this.validateFormData(formmodify0002);
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
		FormData  formmodify0002 = formService.getFormData("modify0002");
		 getFormValue(formmodify0002);
		 boolean validateFlag = this.validateFormData(formmodify0002);
	}
	
}
