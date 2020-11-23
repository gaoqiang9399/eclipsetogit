package app.component.collateral.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.MfRequestPayoutBill;
import app.component.collateral.entity.MfRequestPayoutDetail;
import app.component.collateral.entity.MfRequestPayoutHis;
import app.component.collateral.feign.MfCollateralClassFeign;
import app.component.collateral.feign.MfRequestPayoutBillFeign;
import app.component.collateral.feign.MfRequestPayoutDetailFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.oa.dimission.entity.MfOaDimission;
import app.component.oa.dimission.entity.MfOaDimissionHis;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
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
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;


/**
 * Title: MfBusApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat May 21 10:40:47 CST 2016
 **/
@Controller
@RequestMapping("/mfRequestPayoutBill")
public class MfRequestPayoutBillController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusApplyBo
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfRequestPayoutBillFeign mfRequestPayoutBillFeign;
	@Autowired
	private MfRequestPayoutDetailFeign mfRequestPayoutDetailFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfCollateralClassFeign mfCollateralClassFeign;
	//全局变量
	/**
	 *
	 * 方法描述： 跳转至列表页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-11-1 上午11:19:25
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/collateral/MfRequestPayoutBill_List";
	}
	/**
	 *
	 * 方法描述：进件列表
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-11-1 上午11:18:51
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Ipage ipage,Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		try {
			mfRequestPayoutBill.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRequestPayoutBill.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfRequestPayoutBill.setCriteriaList(mfRequestPayoutBill, ajaxData);//我的筛选
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfRequestPayoutBill", mfRequestPayoutBill));
			ipage = mfRequestPayoutBillFeign.findByPage(ipage);
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
	 * 保存请款基本信息
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/saveBaseAjax")
	@ResponseBody
	public Map<String, Object> saveBaseAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
		FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");
		getFormValue(formrequestpayoutbillbase, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestpayoutbillbase)){
				//实体，可以进行加值页面初始值显示
				MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
				setObjValue(formrequestpayoutbillbase, mfRequestPayoutBill);
				//保存
				mfRequestPayoutBill = mfRequestPayoutBillFeign.insert(mfRequestPayoutBill);

				dataMap.put("msg", "保存成功！");
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * AJAX新增 并提交申请并审批
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
		FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");
		getFormValue(formrequestpayoutbillbase, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestpayoutbillbase)){
				//实体，可以进行加值页面初始值显示
				MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
				setObjValue(formrequestpayoutbillbase, mfRequestPayoutBill);

				//封装需要更新的字段
				MfRequestPayoutBill mfRequestPayoutBillUpdate = new MfRequestPayoutBill();
				mfRequestPayoutBillUpdate.setRequestId(mfRequestPayoutBill.getRequestId());
				mfRequestPayoutBillUpdate.setRelationCusNo(mfRequestPayoutBill.getRelationCusNo());
				mfRequestPayoutBillUpdate.setCusName(mfRequestPayoutBill.getCusName());
				mfRequestPayoutBillUpdate.setAssetName(mfRequestPayoutBill.getAssetName());
				mfRequestPayoutBillUpdate.setAssetType(mfRequestPayoutBill.getAssetType());
				mfRequestPayoutBillUpdate.setRelationRequestId(mfRequestPayoutBill.getRelationRequestId());

				mfRequestPayoutBillUpdate.setCurrentSessionRegNo(mfRequestPayoutBill.getCurrentSessionRegNo());
				mfRequestPayoutBillUpdate.setCurrentSessionRegName(mfRequestPayoutBill.getCurrentSessionRegName());
				mfRequestPayoutBillUpdate.setCurrentSessionOrgNo(mfRequestPayoutBill.getCurrentSessionOrgNo());
				mfRequestPayoutBillUpdate.setCurrentSessionOrgName(mfRequestPayoutBill.getCurrentSessionOrgName());


				//判断是否有变动 两种情况，1.原来没有关联请款单的，现在关联了。2，原来关联了请款单的，更换了关联
				MfRequestPayoutBill mfRequestPayoutBillOld = new MfRequestPayoutBill();
				mfRequestPayoutBillOld.setRequestId(mfRequestPayoutBill.getRequestId());
				mfRequestPayoutBillOld  = mfRequestPayoutBillFeign.getById(mfRequestPayoutBillOld);
				String relationRequestIdold = mfRequestPayoutBillOld.getRelationRequestId();
				String relationRequestIdnew = mfRequestPayoutBill.getRelationRequestId();
				if(relationRequestIdold==null){
					relationRequestIdold="";
				}
				if(relationRequestIdnew==null||"null".equals(relationRequestIdnew)){
					relationRequestIdnew="";
				}
				if(relationRequestIdold.equals(relationRequestIdnew)){
					//设置审批历史中展示的值
					mfRequestPayoutBillUpdate.setRequestCount(mfRequestPayoutBillOld.getRequestCount());
					mfRequestPayoutBillUpdate.setPayoutTotalAmount(mfRequestPayoutBillOld.getPayoutTotalAmount());
					//更新并提交审批
					mfRequestPayoutBill = mfRequestPayoutBillFeign.submitProcess(mfRequestPayoutBillUpdate);

				}else{
//					先更新，让updatePayoutAmount 拿到最新的关联id
					mfRequestPayoutBillFeign.update(mfRequestPayoutBillUpdate);
					//有变动时需要先改变请款单的金额和请款次数信息
					mfRequestPayoutBillFeign.updateRequestCount(mfRequestPayoutBill);
					mfRequestPayoutBillFeign.updatePayoutAmount(mfRequestPayoutBill);
					//先查出最新的再设置审批历史中展示的值
					MfRequestPayoutBill mfRequestPayoutBillnew = new MfRequestPayoutBill();
					mfRequestPayoutBillnew.setRequestId(mfRequestPayoutBill.getRequestId());
					mfRequestPayoutBillnew  = mfRequestPayoutBillFeign.getById(mfRequestPayoutBillnew);

					mfRequestPayoutBillUpdate.setRequestCount(mfRequestPayoutBillnew.getRequestCount());
					mfRequestPayoutBillUpdate.setPayoutTotalAmount(mfRequestPayoutBillnew.getPayoutTotalAmount());
					//更新并提交审批
					mfRequestPayoutBill = mfRequestPayoutBillFeign.submitProcess(mfRequestPayoutBillUpdate);
				}

				//返回提示审批人员
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfRequestPayoutBill.getApproveNodeName());
				paramMap.put("opNo", mfRequestPayoutBill.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 明细AJAX新增或更新
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/insertDetailAjax")
	@ResponseBody
	public Map<String, Object> insertDetailAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData formrequestpayoutdetailbase = formService.getFormData("requestpayoutdetailbase");
			getFormValue(formrequestpayoutdetailbase, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestpayoutdetailbase)){
				//实体，可以进行加值页面初始值显示
				MfRequestPayoutDetail mfRequestPayoutDetail = new MfRequestPayoutDetail();
				setObjValue(formrequestpayoutdetailbase, mfRequestPayoutDetail);
				MfRequestPayoutDetail mfRequestPayoutDetailOld = mfRequestPayoutDetailFeign.getById(mfRequestPayoutDetail);
				if (mfRequestPayoutDetailOld == null){
					mfRequestPayoutDetail.setId(WaterIdUtil.getWaterId());
					mfRequestPayoutDetailFeign.insert(mfRequestPayoutDetail);
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					mfRequestPayoutDetailFeign.update(mfRequestPayoutDetail);
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}
				// 根据requestId获取相关详情数据
				List<MfRequestPayoutDetail>  detailList = mfRequestPayoutDetailFeign.getByRequestId(mfRequestPayoutDetail.getRequestId());
				JsonTableUtil jtu = new JsonTableUtil();
				String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
						detailList, null, true);
				//计算总金额
//				Double payoutTotalAmount = 0.00d;
//				for(int i =0 ; detailList!=null && i< detailList.size() ;i++){
//					payoutTotalAmount = MathExtend.add(payoutTotalAmount,detailList.get(i).getCostAmount());
//				}
				MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
				mfRequestPayoutBill.setRequestId(mfRequestPayoutDetail.getRequestId());
				mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);

				dataMap.put("payoutTotalAmount", mfRequestPayoutBill.getPayoutTotalAmount());
				dataMap.put("detailTableHtml", detailTableHtml);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： ajax 异步 单个字段或多个字段更新(单子段编辑)
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-11-1 上午11:24:19
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 初始化请款单基本信息表单
		FormData formrequestpayoutbilldetail = formService.getFormData("requestpayoutbilldetail");
		getFormValue(formrequestpayoutbilldetail, getMapByJson(ajaxData));
		// 新建对象
		MfRequestPayoutBill mfRequestPayoutBillJsp = new MfRequestPayoutBill();
		setObjValue(formrequestpayoutbilldetail, mfRequestPayoutBillJsp);
		MfRequestPayoutBill mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBillJsp);
		if (mfRequestPayoutBill != null) {
			try {
				mfRequestPayoutBill = (MfRequestPayoutBill) EntityUtil.reflectionSetVal(mfRequestPayoutBill, mfRequestPayoutBillJsp,
						getMapByJson(ajaxData));
				mfRequestPayoutBillFeign.update(mfRequestPayoutBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "保存失败");
				throw e;
			}
		} else {
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");
			getFormValue(formrequestpayoutbillbase, getMapByJson(ajaxData));
			if(this.validateFormData(formrequestpayoutbillbase)){
				//实体，可以进行加值页面初始值显示
				MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
				setObjValue(formrequestpayoutbillbase, mfRequestPayoutBill);
				mfRequestPayoutBillFeign.update(mfRequestPayoutBill);

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDetailAjax")
	@ResponseBody
	public Map<String, Object> deleteDetailAjax(String ajaxData,String requestId,String id) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRequestPayoutDetail mfRequestPayoutDetail = new MfRequestPayoutDetail();
		mfRequestPayoutDetail.setId(id);
		try {
			mfRequestPayoutDetailFeign.delete(mfRequestPayoutDetail);
			// 根据requestId获取相关详情数据
			List<MfRequestPayoutDetail>  detailList = mfRequestPayoutDetailFeign.getByRequestId(requestId);
			JsonTableUtil jtu = new JsonTableUtil();
			String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
					detailList, null, true);
			dataMap.put("detailTableHtml", detailTableHtml);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 新增请款申请
	 * @return
	 * @throws Exception
	 * String
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//实体，可以进行加值页面初始值显示
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");
		String requestId = WaterIdUtil.getWaterId();
		mfRequestPayoutBill.setRequestId(requestId);
		if (formrequestpayoutbillbase.getFormId() == null) {
		} else {
			getObjValue(formrequestpayoutbillbase,mfRequestPayoutBill);
			//没有设值，暂时不允许下列方法
//			getObjValue(formrequestpayoutbillbase, MfRequestPayoutBill);
			model.addAttribute("formrequestpayoutbillbase", formrequestpayoutbillbase);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
				new ArrayList(), null, true);

		//资产类别组件
		MfCollateralClass mfCollateralClass = new MfCollateralClass();
		//先预留类别，默认查全部
		String classFirstNo = null;
		if(StringUtil.isEmpty(classFirstNo)){
			classFirstNo="A,B,C,D";
		}

		mfCollateralClass.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));

		mfCollateralClass.setUseFlag(CollateralConstant.USED);
		List<MfCollateralClass> collateralClasslist = mfCollateralClassFeign.getAll(mfCollateralClass);

		JSONArray collClass = JSONArray.fromObject(collateralClasslist);
		JSONArray collClassNew = new JSONArray();
		for (int i = 0; i < collClass.size(); i++) {
			collClass.getJSONObject(i).put("id", collClass.getJSONObject(i).getString("classId"));
			collClass.getJSONObject(i).put("name",collClass.getJSONObject(i).getString("classSecondName"));
			collClassNew.add(collClass.getJSONObject(i));
		}

		model.addAttribute("collClass", collClassNew.toString());

		//关联请款单
		JSONArray relationRequestIdMap = new JSONArray();
		//获取可关联的请款单
		List<String> relationRequestIdList = mfRequestPayoutBillFeign.getRelationRequestIdList();

		if(relationRequestIdList!=null&&relationRequestIdList.size()>0){
			for (String relationRequestId : relationRequestIdList) {
				JSONObject obj = new JSONObject();
				obj.put("id", relationRequestId);
				obj.put("name", relationRequestId);
				relationRequestIdMap.add(obj);
			}
		}
		//用于区分update页面
		model.addAttribute("detailTableHtml", detailTableHtml);
//		model.addAttribute("saveSign", "insert");
		model.addAttribute("relationRequestIdMap", relationRequestIdMap.toString());
		model.addAttribute("requestId", requestId);
		model.addAttribute("query", "");
		return "/component/collateral/MfRequestPayoutBill_Insert";
	}

	/**
	 * 详情页面需要更改请款单基本信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputUpdate")
	public String inputUpdate(Model model, String requestId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");

		//实体，可以进行加值页面初始值显示
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		mfRequestPayoutBill.setRequestId(requestId);
		mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);
		getFormValue(formrequestpayoutbillbase);
		// 实体对象放到表单对象中
		getObjValue(formrequestpayoutbillbase, mfRequestPayoutBill);

		model.addAttribute("requestId", requestId);
		model.addAttribute("formrequestpayoutbillbase", formrequestpayoutbillbase);
		//用于区分insert页面
//		model.addAttribute("saveSign", "update");

		model.addAttribute("query", "");
		return  "/component/collateral/MfRequestPayoutBill_Insert";
	}
	/**
	 * 查询详情页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String requestId,String approveFlag) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		mfRequestPayoutBill.setRequestId(requestId);
		mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);
		String requestState =mfRequestPayoutBill.getRequestState();
		model.addAttribute("requestState", requestState);
		//如果没有提交过申请则还是显示新增页面
		if("0".equals(requestState) || StringUtil.isEmpty(requestState)) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			//实体，可以进行加值页面初始值显示
			FormData formrequestpayoutbillbase = formService.getFormData("requestpayoutbillbase");
			if (formrequestpayoutbillbase.getFormId() == null) {
			} else {
				getObjValue(formrequestpayoutbillbase,mfRequestPayoutBill);
				model.addAttribute("formrequestpayoutbillbase", formrequestpayoutbillbase);
			}
			// 根据requestId获取相关详情数据
			List<MfRequestPayoutDetail>  detailList = mfRequestPayoutDetailFeign.getByRequestId(requestId);
			JsonTableUtil jtu = new JsonTableUtil();
			String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
					detailList, null, true);

			//资产类别组件
			MfCollateralClass mfCollateralClass = new MfCollateralClass();
			//先预留类别，默认查全部
			String classFirstNo = null;
			if(StringUtil.isEmpty(classFirstNo)){
				classFirstNo="A,B,C,D";
			}
			mfCollateralClass.setClassFirstNoList(Arrays.asList(classFirstNo.split(",")));
			mfCollateralClass.setUseFlag(CollateralConstant.USED);
			List<MfCollateralClass> collateralClasslist = mfCollateralClassFeign.getAll(mfCollateralClass);
			JSONArray collClass = JSONArray.fromObject(collateralClasslist);
			JSONArray collClassNew = new JSONArray();
			for (int i = 0; i < collClass.size(); i++) {
				collClass.getJSONObject(i).put("id", collClass.getJSONObject(i).getString("classId"));
				collClass.getJSONObject(i).put("name",collClass.getJSONObject(i).getString("classSecondName"));
				collClassNew.add(collClass.getJSONObject(i));
			}

			//关联请款单
			JSONArray relationRequestIdMap = new JSONArray();
			//获取可关联的请款单
			List<String> relationRequestIdList = mfRequestPayoutBillFeign.getRelationRequestIdList();

			if(relationRequestIdList!=null&&relationRequestIdList.size()>0){
				for (String relationRequestId : relationRequestIdList) {
					JSONObject obj = new JSONObject();
					obj.put("id", relationRequestId);
					obj.put("name", relationRequestId);
					relationRequestIdMap.add(obj);
				}
			}

			model.addAttribute("collClass", collClassNew.toString());
			model.addAttribute("relationRequestIdMap", relationRequestIdMap.toString());
			model.addAttribute("detailTableHtml", detailTableHtml);
			model.addAttribute("requestId", requestId);
			model.addAttribute("query", "");
			return "/component/collateral/MfRequestPayoutBill_Insert";
		}else {
			//默认不可编辑
			String query = "query";
			// 初始化请款单基本信息表单
			FormData formrequestpayoutbilldetail = formService.getFormData("requestpayoutbilldetail");
			// 根据requestId获取相关详情数据
			List<MfRequestPayoutDetail>  detailList = mfRequestPayoutDetailFeign.getByRequestId(requestId);
			//设置状态用于操作按钮的判断
			for(int i= 0;i<detailList.size();i++){
				detailList.get(i).setRequestState(requestState);
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
					detailList, null, true);

			// 实体对象放到表单对象中
			getObjValue(formrequestpayoutbilldetail, mfRequestPayoutBill);
			// 转成json串，在jsp中转成json对象，在js中使用。
			String requestPayoutBillStr = JSONObject.fromObject(mfRequestPayoutBill).toString();
			//判断是否可编辑
//			if ( "0".equals(requestState) && approveFlag == null ) {
//				request.setAttribute("ifBizManger", "3");
//				query = "";
//			}
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String requestpayoutbilldetailhtml = jsonFormUtil.getJsonStr(formrequestpayoutbilldetail, "propertySeeTag", query);
			model.addAttribute("requestpayoutbilldetailhtml", requestpayoutbilldetailhtml);
			model.addAttribute("detailTableHtml", detailTableHtml);
			model.addAttribute("requestPayoutBillStr", requestPayoutBillStr);
			model.addAttribute("requestId", requestId);
			model.addAttribute("approveFlag", approveFlag);
			model.addAttribute("mfRequestPayoutBill", mfRequestPayoutBill);
			model.addAttribute("query", query);
			return "/component/collateral/MfRequestPayoutBill_Detail";
		}
	}

	/**
	 *
	 * 方法描述：打开请款明细新增页面
	 * @return
	 * @throws Exception
	 * String
	 * @author
	 * @date 2017-8-21 下午3:51:50
	 */
	@RequestMapping(value = "/inputDetail")
	public String inputDetail(Model model, String requestId,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//实体，可以进行加值页面初始值显示
		MfRequestPayoutDetail mfRequestPayoutDetail = new MfRequestPayoutDetail();
		if(id==null||"".equals(id)||"null".equals(id)){
			mfRequestPayoutDetail.setRequestId(requestId);
		}else{
			mfRequestPayoutDetail.setId(id);
			mfRequestPayoutDetail = mfRequestPayoutDetailFeign.getById(mfRequestPayoutDetail);
		}
		FormData formrequestpayoutdetailbase = formService.getFormData("requestpayoutdetailbase");
		if (formrequestpayoutdetailbase.getFormId() == null) {

		} else {
			getFormValue(formrequestpayoutdetailbase);
			//设值
			getObjValue(formrequestpayoutdetailbase, mfRequestPayoutDetail);
			model.addAttribute("formrequestpayoutdetailbase", formrequestpayoutdetailbase);
		}
		model.addAttribute("query", "");
		return "/component/collateral/MfRequestPayoutDetail_Insert";
	}

	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApplyFeign.delete(mfBusApply);
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
		FormData  formapply0002 = formService.getFormData("apply0002");
		 getFormValue(formapply0002);
		 boolean validateFlag = this.validateFormData(formapply0002);
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
		FormData  formapply0002 = formService.getFormData("apply0002");
		 getFormValue(formapply0002);
		 boolean validateFlag = this.validateFormData(formapply0002);
	}
	/**
	 *
	 * 方法描述： 打开请款申请审批页面
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String requestId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		// 新建对象
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		// 设置requestId
		mfRequestPayoutBill.setRequestId(requestId);

		mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);
		mfRequestPayoutBill.setApprovePartNo(null);
		mfRequestPayoutBill.setApprovePartName(null);
		// 当前审批节点task
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(requestId, null);
		// 初始化请款基本信息
		FormData formrequestpayoutbillapprove001 = formService.getFormData("requestpayoutbillapprove001");
		// 实体对象放到表单对象中
		getObjValue(formrequestpayoutbillapprove001, mfRequestPayoutBill);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		//重新赋值
		this.changeFormProperty(formrequestpayoutbillapprove001, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));

		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("mfRequestPayoutBill", mfRequestPayoutBill);
		model.addAttribute("formrequestpayoutbillapprove001", formrequestpayoutbillapprove001);
		model.addAttribute("requestId", requestId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/collateral/WkfRequestPayoutViewPoint";
	}
	/**
	 *
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,String nextUser)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		//审批页表单
		FormData formrequestpayoutbillapprove001 = formService.getFormData("requestpayoutbillapprove001");

		getFormValue(formrequestpayoutbillapprove001, formDataMap);
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		MfRequestPayoutHis mfRequestPayoutHis = new MfRequestPayoutHis();
		setObjValue(formrequestpayoutbillapprove001, mfRequestPayoutBill);
		setObjValue(formrequestpayoutbillapprove001, mfRequestPayoutHis);
		Result res;

		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRequestPayoutBill);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfRequestPayoutHis);
			formDataMap.put("mfRequestPayoutBill", mfRequestPayoutBill);
			formDataMap.put("mfRequestPayoutHis", mfRequestPayoutHis);

			//替换金额的逗号
			String payoutTotalAmount = formDataMap.get("payoutTotalAmount").toString().replaceAll(",", "");
			formDataMap.put("payoutTotalAmount",payoutTotalAmount );

			res = mfRequestPayoutBillFeign.doCommit(taskId, transition, nextUser,formDataMap);
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

	/**
	 *
	 * 方法描述： 还没提交的在详情页面继续点击提交审批流程
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String requestId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
			mfRequestPayoutBill.setRequestId(requestId);
			mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);
			//提交审批
			mfRequestPayoutBill = mfRequestPayoutBillFeign.submitProcess(mfRequestPayoutBill);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfRequestPayoutBill.getApproveNodeName());
			paramMap.put("opNo", mfRequestPayoutBill.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

	@Override
	public Map<String, Object> getMapByJson(String ajaxData) {
		return super.getMapByJson(ajaxData);
	}

	/**
	 *
	 * 方法描述： 付款登记页面
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/paymentRegPage")
	public String paymentRegPage(Model model,String requestId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
		mfRequestPayoutBill.setRequestId(requestId);
		mfRequestPayoutBill = mfRequestPayoutBillFeign.getById(mfRequestPayoutBill);
		String requestState =mfRequestPayoutBill.getRequestState();
		model.addAttribute("requestState", requestState);
		//默认不可编辑
		String query = "";
		// 初始化请款单基本信息表单
		FormData formrequestpayoutbilldetail = formService.getFormData("requestpayoutbilldetail");
		// 根据requestId获取相关详情数据
		List<MfRequestPayoutDetail>  detailList = mfRequestPayoutDetailFeign.getByRequestId(requestId);
		//设置状态用于操作按钮的判断
		for(int i= 0;i<detailList.size();i++){
			detailList.get(i).setRequestState(requestState);
		}
		JsonTableUtil jtu = new JsonTableUtil();
		String detailTableHtml = jtu.getJsonStr("tablerequestpayoutdetaillist", "tableTag",
				detailList, null, true);

		// 实体对象放到表单对象中
		getObjValue(formrequestpayoutbilldetail, mfRequestPayoutBill);

		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String requestpayoutbilldetailhtml = jsonFormUtil.getJsonStr(formrequestpayoutbilldetail, "propertySeeTag", query);

		//设置付款登记表单数据
		FormData formbillpaymentregpage = formService.getFormData("billpaymentregpage");
		getObjValue(formbillpaymentregpage,mfRequestPayoutBill);
		model.addAttribute("formbillpaymentregpage", formbillpaymentregpage);

		model.addAttribute("requestpayoutbilldetailhtml", requestpayoutbilldetailhtml);
		model.addAttribute("detailTableHtml", detailTableHtml);
		model.addAttribute("requestId", requestId);
		model.addAttribute("mfRequestPayoutBill", mfRequestPayoutBill);
		model.addAttribute("query", query);
		return "/component/collateral/MfRequestPayoutBill_PayReg";
	}
	/**
	 *
	 * 方法描述： 付款登记
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/paymentReg")
	@ResponseBody
	public Map<String, Object> paymentReg(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formbillpaymentregpage = formService.getFormData("billpaymentregpage");
			getFormValue(formbillpaymentregpage, getMapByJson(ajaxData));
			if(this.validateFormData(formbillpaymentregpage)){
				//实体，可以进行加值页面初始值显示
				MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
				setObjValue(formbillpaymentregpage, mfRequestPayoutBill);
				//保存
				mfRequestPayoutBillFeign.paymentReg(mfRequestPayoutBill);
				dataMap.put("msg", "付款登记成功！");
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg","付款登记异常！");
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 结束请款
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/finishPayment")
	@ResponseBody
	public Map<String, Object> finishPayment(String requestId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfRequestPayoutBill mfRequestPayoutBill = new MfRequestPayoutBill();
			mfRequestPayoutBill.setRequestId(requestId);

			Map<String, Object> resultMap = mfRequestPayoutBillFeign.finishPayment(mfRequestPayoutBill);

			if((Boolean) resultMap.get("falg")){
				dataMap.put("flag", "success");
				dataMap.put("msg", "结束请款成功！");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", resultMap.get("msg").toString());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "结束请款异常！");
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 结束请款
	 *
	 * @return
	 * @throws Exception
	 * @author
	 * @date
	 */
	@RequestMapping("/getInfoById")
	@ResponseBody
	public Map<String, Object> getInfoById(String requestId,String relationRequestId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfRequestPayoutBill mfRequestPayoutBillInfo = new MfRequestPayoutBill();
			mfRequestPayoutBillInfo.setRequestId(relationRequestId);
			mfRequestPayoutBillInfo = mfRequestPayoutBillFeign.getById(mfRequestPayoutBillInfo);

			//设置当前请款单的总金额
			Double totalCostAmount = mfRequestPayoutBillFeign.getTotalCostAmount(requestId);
			if(totalCostAmount==null){
				totalCostAmount=0.00d;
			}
			mfRequestPayoutBillInfo.setPayoutTotalAmount(totalCostAmount);

			dataMap.put("mfRequestPayoutBillInfo", mfRequestPayoutBillInfo);
			dataMap.put("msg", "查询成功！");
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询异常！");
		}
		return dataMap;
	}
}
