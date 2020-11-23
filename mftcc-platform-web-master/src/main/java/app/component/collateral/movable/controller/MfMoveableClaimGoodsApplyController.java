package  app.component.collateral.movable.controller;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.PledgeBaseInfoBillFeign;
import app.component.common.BizPubParm;
import app.component.prdct.entity.MfKindFlow;
import app.component.prdctinterface.PrdctInterfaceFeign;
import cn.mftcc.util.StringUtil;
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
import app.component.collateral.movable.entity.MfMoveableClaimGoodsApply;
import app.component.collateral.movable.entity.MfMoveableClaimGoodsApproHis;
import app.component.collateral.movable.feign.MfMoveableClaimGoodsApplyFeign;
import app.component.collateral.movable.feign.MfMoveableTransferApplyFeign;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;

/**
 * Title: MfMoveableClaimGoodsApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 10 18:33:41 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableClaimGoodsApply")
public class MfMoveableClaimGoodsApplyController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableClaimGoodsApplyBo
	@Autowired
	private MfMoveableClaimGoodsApplyFeign mfMoveableClaimGoodsApplyFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfMoveableTransferApplyFeign mfMoveableTransferApplyFeign;
	@Autowired
	private PrdctInterfaceFeign prdctInterfaceFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
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
		 return "/component/collateral/movable/MfMoveableClaimGoodsApply_List";
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
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		try {
			mfMoveableClaimGoodsApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableClaimGoodsApply.setCriteriaList(mfMoveableClaimGoodsApply, ajaxData);//我的筛选
			//mfMoveableClaimGoodsApply.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableClaimGoodsApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableClaimGoodsApplyFeign.findByPage(ipage, mfMoveableClaimGoodsApply);
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
	public Map<String, Object> insertAjax(String ajaxData,String pledgeBills) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
		FormData formclaim0002 = formService.getFormData("claim0002");
			getFormValue(formclaim0002, getMapByJson(ajaxData));
			dataMap =getMapByJson(ajaxData);
			if(this.validateFormData(formclaim0002)){
				MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
				setObjValue(formclaim0002, mfMoveableClaimGoodsApply);
				mfMoveableClaimGoodsApply.setPledgeBills(pledgeBills);
				dataMap.put("mfMoveableClaimGoodsApply",mfMoveableClaimGoodsApply);
				mfMoveableClaimGoodsApply=mfMoveableClaimGoodsApplyFeign.insert(dataMap);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfMoveableClaimGoodsApply.getApproveNodeName());
				paramMap.put("opNo", mfMoveableClaimGoodsApply.getApprovePartName());
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
		FormData formclaim0002 = formService.getFormData("claim0002");
		getFormValue(formclaim0002, getMapByJson(ajaxData));
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApplyJsp = new MfMoveableClaimGoodsApply();
		setObjValue(formclaim0002, mfMoveableClaimGoodsApplyJsp);
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = mfMoveableClaimGoodsApplyFeign.getById(mfMoveableClaimGoodsApplyJsp);
		if(mfMoveableClaimGoodsApply!=null){
			try{
				mfMoveableClaimGoodsApply = (MfMoveableClaimGoodsApply)EntityUtil.reflectionSetVal(mfMoveableClaimGoodsApply, mfMoveableClaimGoodsApplyJsp, getMapByJson(ajaxData));
				mfMoveableClaimGoodsApplyFeign.update(mfMoveableClaimGoodsApply);
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
		FormData 	formclaim0002 = formService.getFormData("claim0002");
			getFormValue(formclaim0002, getMapByJson(ajaxData));
			if(this.validateFormData(formclaim0002)){
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
				setObjValue(formclaim0002, mfMoveableClaimGoodsApply);
				mfMoveableClaimGoodsApplyFeign.update(mfMoveableClaimGoodsApply);
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
	 * @param claimId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String claimId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formclaim0002 = formService.getFormData("claim0002");
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(claimId);
		mfMoveableClaimGoodsApply = mfMoveableClaimGoodsApplyFeign.getById(mfMoveableClaimGoodsApply);
		getObjValue(formclaim0002, mfMoveableClaimGoodsApply,formData);
		if(mfMoveableClaimGoodsApply!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param claimId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String claimId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(claimId);
		try {
			mfMoveableClaimGoodsApplyFeign.delete(mfMoveableClaimGoodsApply);
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
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String busPleId, String cusNo,String appId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formclaim0002 = formService.getFormData("claim0002");
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = mfMoveableClaimGoodsApplyFeign.initClaimGoodsApply(busPleId, cusNo);
		mfMoveableClaimGoodsApply.setAppId(appId);
		getObjValue(formclaim0002, mfMoveableClaimGoodsApply);
		String processId=BizPubParm.MOVEABLED_CLAIM_APPROVE_WKF_ID;
		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setAppId(appId);
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		Double ableOutValue = 0.00;
		if (mfBusCollateralRel!=null&&mfBusCollateralRel.getReceTransBal()!=null){
			ableOutValue = mfBusCollateralRel.getReceTransBal();
		}
		mfMoveableClaimGoodsApply.setAbleOutValue(ableOutValue);

		model.addAttribute("processId", processId);
		model.addAttribute("formclaim0002", formclaim0002);
		model.addAttribute("claimId", mfMoveableClaimGoodsApply.getClaimId());
		model.addAttribute("busPleId", mfMoveableClaimGoodsApply.getBusPleId());
		model.addAttribute("appId", appId);
		if(mfMoveableClaimGoodsApply.getClaimGoodsBal()!=null){
			model.addAttribute("claimGoodsBal",new BigDecimal(mfMoveableClaimGoodsApply.getClaimGoodsBal()));
		}
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApply_Insert";
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
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData  formclaim0002 = formService.getFormData("claim0002");
		 getFormValue(formclaim0002);
		MfMoveableClaimGoodsApply  mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		 setObjValue(formclaim0002, mfMoveableClaimGoodsApply);
		dataMap.put("mfMoveableClaimGoodsApply",mfMoveableClaimGoodsApply);
		 mfMoveableClaimGoodsApplyFeign.insert(dataMap);
		 getObjValue(formclaim0002, mfMoveableClaimGoodsApply);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableClaimGoodsApply> mfMoveableClaimGoodsApplyList = (List<MfMoveableClaimGoodsApply>)mfMoveableClaimGoodsApplyFeign.findByPage(this.getIpage(), mfMoveableClaimGoodsApply).getResult();
		model.addAttribute("formclaim0002", formclaim0002);
		model.addAttribute("mfMoveableClaimGoodsApplyList", mfMoveableClaimGoodsApplyList);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApply_Insert";
	}
	/**
	 * 查询
	 * @param claimId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String claimId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formclaim0001 = formService.getFormData("claim0001");
		 getFormValue(formclaim0001);
		MfMoveableClaimGoodsApply  mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(claimId);
		 mfMoveableClaimGoodsApply = mfMoveableClaimGoodsApplyFeign.getById(mfMoveableClaimGoodsApply);
		 getObjValue(formclaim0001, mfMoveableClaimGoodsApply);
		model.addAttribute("formclaim0001", formclaim0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableClaimGoodsApply_Detail";
	}
	/**
	 * 删除
	 * @param claimId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String claimId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(claimId);
		mfMoveableClaimGoodsApplyFeign.delete(mfMoveableClaimGoodsApply);
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
		FormData  formclaim0002 = formService.getFormData("claim0002");
		 getFormValue(formclaim0002);
		 boolean validateFlag = this.validateFormData(formclaim0002);
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
		FormData  formclaim0002 = formService.getFormData("claim0002");
		 getFormValue(formclaim0002);
		 boolean validateFlag = this.validateFormData(formclaim0002);
	}
	/**
	 * 
	 * 方法描述：打开提货审批页面 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param claimId 
	 * @param activityType 
	 * @date 2017-6-12 下午6:14:26
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String claimId, String activityType, String hideOpinionType) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formclaimappro0001 = formService.getFormData("claimappro0001");
		MfMoveableClaimGoodsApply mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
		mfMoveableClaimGoodsApply.setClaimId(claimId);
		mfMoveableClaimGoodsApply=mfMoveableClaimGoodsApplyFeign.getById(mfMoveableClaimGoodsApply);

		MfBusCollateralRel mfBusCollateralRel = new MfBusCollateralRel();
		mfBusCollateralRel.setBusCollateralId(mfMoveableClaimGoodsApply.getBusPleId());
		mfBusCollateralRel = mfBusCollateralRelFeign.getById(mfBusCollateralRel);
		Double ableOutValue = 0.00;
		if (mfBusCollateralRel!=null&&mfBusCollateralRel.getReceTransBal()!=null){
			ableOutValue = mfBusCollateralRel.getReceTransBal();
		}

		mfMoveableClaimGoodsApply.setAbleOutValue(ableOutValue);

		PledgeBaseInfoBill pledgeBaseInfoBill = new PledgeBaseInfoBill();
		pledgeBaseInfoBill.setAppId(mfMoveableClaimGoodsApply.getClaimId());
		List<PledgeBaseInfoBill> pledgeBaseInfoBillList = pledgeBaseInfoBillFeign.getBillListForClaim(pledgeBaseInfoBill);

		getObjValue(formclaimappro0001, mfMoveableClaimGoodsApply);
		dataMap = mfReceivablesPledgeInfoFeign.getViewDataMap(mfMoveableClaimGoodsApply.getBusPleId());
		dataMap.put("claimId", claimId);
		String appId = String.valueOf(dataMap.get("appId"));
		ViewUtil.setViewPointParm(request, dataMap);
		//获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(claimId, null);

		// 处理审批意见类型
        Map<String, String> opinionTypeMap = new HashMap<String, String>();
        opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType, taskAppro.getCouldRollback(),opinionTypeMap);
		this.changeFormProperty(formclaimappro0001, "opinionType", "optionArray", opinionTypeList);
		model.addAttribute("formclaimappro0001", formclaimappro0001);
		model.addAttribute("mfMoveableClaimGoodsApply",mfMoveableClaimGoodsApply);
		model.addAttribute("claimId",mfMoveableClaimGoodsApply.getClaimId());
		model.addAttribute("appId", appId);
		model.addAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("cusNo", String.valueOf(dataMap.get("cusNo")));
		model.addAttribute("pactId", String.valueOf(dataMap.get("pactId")));
		model.addAttribute("pledgeBaseInfoBillList", pledgeBaseInfoBillList);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/WkfMoveabledClaimViewPoint";
	}
	/**
	 * 
	 * 方法描述： 审批意见提交
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param taskId 
	 * @param appId 
	 * @param claimId 
	 * @param transition 
	 * @param opNo 
	 * @param nextUser 
	 * @date 2017-6-12 下午6:35:25
	 */
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String claimId, String transition, String opNo, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formclaimappro0001 = formService.getFormData("claimappro0001");
			getFormValue(formclaimappro0001, getMapByJson(ajaxData));
		MfMoveableClaimGoodsApply 	mfMoveableClaimGoodsApply = new MfMoveableClaimGoodsApply();
			MfMoveableClaimGoodsApproHis mfMoveableClaimGoodsApproHis =new MfMoveableClaimGoodsApproHis();
			setObjValue(formclaimappro0001, mfMoveableClaimGoodsApply);
			setObjValue(formclaimappro0001, mfMoveableClaimGoodsApproHis);
			dataMap=getMapByJson(ajaxData);
			dataMap.put("mfMoveableClaimGoodsApproHis", mfMoveableClaimGoodsApproHis);
			dataMap.put("mfMoveableClaimGoodsApply", mfMoveableClaimGoodsApply);
			dataMap.put("taskId", taskId);
			dataMap.put("appId", appId);
			dataMap.put("claimId", claimId);
			dataMap.put("transition", transition);
			dataMap.put("opNo",User.getRegNo(request));
			dataMap.put("nextUser", nextUser);
			dataMap.put("regName", User.getRegName(request));
			dataMap.put("regNo", User.getRegNo(request));
			dataMap.put("orgNo", User.getOrgNo(request));
			Result res=mfMoveableClaimGoodsApplyFeign.doCommit(dataMap);
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
	 * 方法描述： 根据已选择的押品
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param claimId 
	 * @date 2017-6-12 下午9:34:21
	 */
	@RequestMapping(value = "/getPledgeDataBySelectedAjax")
	@ResponseBody
	public Map<String, Object> getPledgeDataBySelectedAjax(String ajaxData, String claimId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
			 JSONArray dataArray = mfMoveableClaimGoodsApplyFeign.getPledgeDataBySelected(claimId);
			dataMap.put("items", dataArray);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}
}
