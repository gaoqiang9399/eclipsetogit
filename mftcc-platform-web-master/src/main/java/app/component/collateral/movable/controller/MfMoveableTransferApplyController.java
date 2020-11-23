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
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.collateral.movable.entity.MfMoveableTransferApply;
import app.component.collateral.movable.entity.MfMoveableTransferApproHis;
import app.component.collateral.movable.feign.MfMoveableTransferApplyFeign;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableTransferApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 09 16:32:52 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableTransferApply")
public class MfMoveableTransferApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableTransferApplyBo
	@Autowired
	private MfMoveableTransferApplyFeign mfMoveableTransferApplyFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private PledgeBaseInfoBillFeign pledgeBaseInfoBillFeign;
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
		 return "/component/collateral/movable/MfMoveableTransferApply_List";
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
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		try {
			mfMoveableTransferApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableTransferApply.setCriteriaList(mfMoveableTransferApply, ajaxData);//我的筛选
			//mfMoveableTransferApply.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableTransferApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableTransferApplyFeign.findByPage(ipage, mfMoveableTransferApply);
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
		FormData 	formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
			getFormValue(formmovabletransfer0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovabletransfer0002)){
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
				setObjValue(formmovabletransfer0002, mfMoveableTransferApply);
				mfMoveableTransferApply=mfMoveableTransferApplyFeign.insert(mfMoveableTransferApply);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfMoveableTransferApply.getApproveNodeName());
				paramMap.put("opNo", mfMoveableTransferApply.getApprovePartName());
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
		FormData formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		getFormValue(formmovabletransfer0002, getMapByJson(ajaxData));
		MfMoveableTransferApply mfMoveableTransferApplyJsp = new MfMoveableTransferApply();
		setObjValue(formmovabletransfer0002, mfMoveableTransferApplyJsp);
		MfMoveableTransferApply mfMoveableTransferApply = mfMoveableTransferApplyFeign.getById(mfMoveableTransferApplyJsp);
		if(mfMoveableTransferApply!=null){
			try{
				mfMoveableTransferApply = (MfMoveableTransferApply)EntityUtil.reflectionSetVal(mfMoveableTransferApply, mfMoveableTransferApplyJsp, getMapByJson(ajaxData));
				mfMoveableTransferApplyFeign.update(mfMoveableTransferApply);
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
		FormData 	formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
			getFormValue(formmovabletransfer0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovabletransfer0002)){
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
				setObjValue(formmovabletransfer0002, mfMoveableTransferApply);
				mfMoveableTransferApplyFeign.update(mfMoveableTransferApply);
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
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String transferId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply.setTransferId(transferId);
		mfMoveableTransferApply = mfMoveableTransferApplyFeign.getById(mfMoveableTransferApply);
		getObjValue(formmovabletransfer0002, mfMoveableTransferApply,formData);
		if(mfMoveableTransferApply!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String transferId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply.setTransferId(transferId);
		try {
			mfMoveableTransferApplyFeign.delete(mfMoveableTransferApply);
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
		FormData formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply = mfMoveableTransferApplyFeign.initTransferApply(busPleId, appId);
		getObjValue(formmovabletransfer0002, mfMoveableTransferApply);
		model.addAttribute("formmovabletransfer0002", formmovabletransfer0002);
		model.addAttribute("busPleId", busPleId);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApply_Insert";
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
		FormData  formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		 getFormValue(formmovabletransfer0002);
		MfMoveableTransferApply  mfMoveableTransferApply = new MfMoveableTransferApply();
		 setObjValue(formmovabletransfer0002, mfMoveableTransferApply);
		 mfMoveableTransferApplyFeign.insert(mfMoveableTransferApply);
		 getObjValue(formmovabletransfer0002, mfMoveableTransferApply);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableTransferApply> mfMoveableTransferApplyList = (List<MfMoveableTransferApply>)mfMoveableTransferApplyFeign.findByPage(this.getIpage(), mfMoveableTransferApply).getResult();
		model.addAttribute("mfMoveableTransferApplyList", mfMoveableTransferApplyList);
		model.addAttribute("formmovabletransfer0002", formmovabletransfer0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApply_Insert";
	}
	/**
	 * 查询
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String transferId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		 getFormValue(formmovabletransfer0002);
		MfMoveableTransferApply  mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply.setTransferId(transferId);
		 mfMoveableTransferApply = mfMoveableTransferApplyFeign.getById(mfMoveableTransferApply);
		 getObjValue(formmovabletransfer0002, mfMoveableTransferApply);
		model.addAttribute("formmovabletransfer0002", formmovabletransfer0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableTransferApply_Detail";
	}
	/**
	 * 删除
	 * @param transferId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String transferId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply.setTransferId(transferId);
		mfMoveableTransferApplyFeign.delete(mfMoveableTransferApply);
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
		FormData  formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		 getFormValue(formmovabletransfer0002);
		 boolean validateFlag = this.validateFormData(formmovabletransfer0002);
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
		FormData  formmovabletransfer0002 = formService.getFormData("movabletransfer0002");
		 getFormValue(formmovabletransfer0002);
		 boolean validateFlag = this.validateFormData(formmovabletransfer0002);
	}
	/**
	 * 
	 * 方法描述：打开移库审批页面 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param transferId 
	 * @param scNo 
	 * @param activityType 
	 * @date 2017-6-10 上午9:45:10
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String transferId, Object scNo, String activityType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtransferappro0001 = formService.getFormData("transferappro0001");
		MfMoveableTransferApply mfMoveableTransferApply = new MfMoveableTransferApply();
		mfMoveableTransferApply.setTransferId(transferId);
		mfMoveableTransferApply=mfMoveableTransferApplyFeign.getById(mfMoveableTransferApply);
		List<PledgeBaseInfoBill> pledgeBaseInfoBillList = mfMoveableTransferApplyFeign.getBillListByselected(mfMoveableTransferApply.getPledgeDetail());
		getObjValue(formtransferappro0001, mfMoveableTransferApply);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfMoveableTransferApply.getBusPleId());
		dataMap.put("transferId", transferId);
		//scNo=BizPubParm.SCENCE_TYPE_DOC_RECEPLE;
		dataMap.put("scNo", scNo);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(transferId, null);
		//处理审批意见类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),null);
		this.changeFormProperty(formtransferappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formtransferappro0001", formtransferappro0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/WkfMoveabledTransferViewPoint";
	}
	/**
	 * 
	 * 方法描述： 审批意见提交
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param taskId 
	 * @param transferId 
	 * @param transition 
	 * @param nextUser 
	 * @param opNo 
	 * @param appId 
	 * @date 2017-6-10 下午3:59:50
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String transferId, String transition, String nextUser, String opNo, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formtransferappro0001 = formService.getFormData("transferappro0001");
			getFormValue(formtransferappro0001, getMapByJson(ajaxData));
		MfMoveableTransferApply 	mfMoveableTransferApply = new MfMoveableTransferApply();
			MfMoveableTransferApproHis mfMoveableTransferApproHis =new MfMoveableTransferApproHis();
			setObjValue(formtransferappro0001, mfMoveableTransferApply);
			setObjValue(formtransferappro0001, mfMoveableTransferApproHis);
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfMoveableTransferApproHis", mfMoveableTransferApproHis);
			dataMap.put("mfMoveableTransferApply", mfMoveableTransferApply);
			dataMap.put("taskId", taskId);
			dataMap.put("appId", appId);
			dataMap.put("transferId", transferId);
			dataMap.put("transition", transition);
			dataMap.put("opNo", opNo);
			dataMap.put("nextUser", nextUser);
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res=mfMoveableTransferApplyFeign.doCommit(dataMap);
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
