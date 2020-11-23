 package app.component.collateral.controller;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import app.component.collateral.entity.CollateralConstant;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.MfBusCollateral;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.feign.EvalInfoFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.query.entity.MfQueryItem;
import app.component.queryinterface.QueryInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfBusCollateralController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 10:33:09 CST 2017
 **/
@Controller
@RequestMapping("/mfBusCollateral")
public class MfBusCollateralController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusCollateralFeign mfBusCollateralFeign;
	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private EvalInfoFeign evalInfoFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private QueryInterfaceFeign queryInterfaceFeign;

	@RequestMapping("/getEntrance")
	public String getEntrance(Model model,String type) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new  HashMap<String,Object>();
		//获取当前操作员的我的关注列表
		MfQueryItem mfQueryItem = new MfQueryItem();
		mfQueryItem.setFuncType(type);
		mfQueryItem.setIsBase("1");
		mfQueryItem.setAttentionFlag("0");
		List<MfQueryItem> mfQueryItemList = queryInterfaceFeign.getMfQueryItemList(mfQueryItem);
		JSONArray jsonArray = JSONArray.fromObject(mfQueryItemList);
		dataMap.put("mfQueryItemList", jsonArray);
		String ajaxData = jsonArray.toString();
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfQueryItemList", mfQueryItemList);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/collateral/MfAssetsEntrance";
	}


	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId, MfBusCollateral mfBusCollateral)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusCollateralFeign.getAll(mfBusCollateral), null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model,String entranceType,String subEntrance) throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray keepStatusJsonArray = new JSONArray();
		keepStatusJsonArray = mfBusCollateralFeign.removeStatus();
		this.getHttpRequest().setAttribute("keepStatusJsonArray", keepStatusJsonArray);
		JSONArray pledgeMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		this.getHttpRequest().setAttribute("pledgeMethodJsonArray", pledgeMethodJsonArray);
		model.addAttribute("entranceType",entranceType);
		model.addAttribute("subEntrance",subEntrance);
		if("account".equals(entranceType)){//应收账款
			return "/component/collateral/MfBusCollateral_AccountList";
		}else if("lease".equals(entranceType)){//租赁物
			return "/component/collateral/MfBusCollateral_LeaseList";
		}else {
			return "/component/collateral/MfBusCollateral_List";
		}
	}
	/**
	 * @方法描述： 押品管理列表（业务相关）
	 * @param model
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月22日 上午10:46:41
	 */
	@RequestMapping("/getPledgeListPage")
	public String getPledgeListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray keepStatusJsonArray = new JSONArray();
		keepStatusJsonArray = mfBusCollateralFeign.removeStatus();
		this.getHttpRequest().setAttribute("keepStatusJsonArray", keepStatusJsonArray);
		JSONArray pledgeMethodJsonArray = new CodeUtils().getJSONArrayByKeyName("VOU_TYPE");
		this.getHttpRequest().setAttribute("pledgeMethodJsonArray", pledgeMethodJsonArray);
		return "/component/collateral/MfBusCollateral_PledgeList";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax1")
	@ResponseBody
	public Map<String, Object> findByPageAjax1(String ajaxData, String tableId, String tableType, Integer pageSize,
			Integer pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateral mfBusCollateral = new MfBusCollateral();
		try {
			mfBusCollateral.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCollateral.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusCollateral.setCriteriaList(mfBusCollateral, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusCollateral,"1000000065");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfBusCollateralFeign.findByPage(ipage, mfBusCollateral);
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

	@RequestMapping("/refreshPageHeadAjax")
	@ResponseBody
	public Map<String, Object> refreshPageHeadAjax(String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateral mbc = new MfBusCollateral();
		mbc.setCollateralId(collateralId);
		MfBusCollateral mfBusCollateral = mfBusCollateralFeign.getById(mbc);
		String collateralCusNo = mfBusCollateral.getCollateralCusNo();
		MfCusCustomer mfcc = new MfCusCustomer();
		mfcc.setCusNo(collateralCusNo);
		CodeUtils cu = new CodeUtils();
		Map<String, String> cusTypeMap = cu.getMapByKeyName("KEEP_STATUS");
		String keepStatusName = cusTypeMap.get(mfBusCollateral.getKeepStatus());
		dataMap.put("keepStatusName", keepStatusName);
		EvalInfo ei = new EvalInfo();
		ei.setCollateralId(collateralId);
		EvalInfo evalInfo = evalInfoFeign.getLatest(ei);
		if (evalInfo != null) {
			String displayColRate = Double.toString(evalInfo.getMortRate());
			String displayColValue = Double.toString(evalInfo.getConfirmAmount() / 10000);
			dataMap.put("displayColRate", displayColRate);
			dataMap.put("displayColValue", displayColValue);
		} else {
			dataMap.put("displayColRate", "0.0");
			dataMap.put("displayColValue", "0.0");
		}
		return dataMap;
	}

	// @RequestMapping("/certiInfo") public String throws Exception {
	// ActionContext.initialize(request,
	// response);
	//
	// model.addAttribute("query", ""); return
	// "/component/collateral/MfBusCollateral_detail";
	//
	// }

	@RequestMapping("/getByCollateralId")
	public String getByCollateralId()
			throws Exception {/*
								 * ActionContext.initialize(request, response);
								 * FormService formService = new FormService();
								 * MfBusCollateral mbc = new MfBusCollateral();
								 * mbc.setCollateralId(collateralId);
								 * mfBusCollateral =
								 * mfBusCollateralFeign.getById(mbc);
								 * collateralCusNo =
								 * mfBusCollateral.getCollateralCusNo();
								 * MfCusCustomer mfcc=new MfCusCustomer();
								 * mfcc.setCusNo(collateralCusNo); mfCusCustomer
								 * = mfCusCustomerFeign.getById(mfcc); String
								 * colType =
								 * mfBusCollateral.getCollateralMethod(); String
								 * colId = mfBusCollateral.getCollateralId();
								 * CodeUtils cu = new CodeUtils(); Map<String,
								 * String> cusTypeMap =
								 * cu.getMapByKeyName("KEEP_STATUS");
								 * keepStatusName =
								 * cusTypeMap.get(mfBusCollateral.getKeepStatus(
								 * )); if (StringUtil.isEmpty(colType)) {
								 * 
								 * } else if (colType.equals("3")) { PledgeBase
								 * pb = new PledgeBase(); pb.setPledgeId(colId);
								 * pledgeBase = pledgeBaseFeign.getById(pb); }
								 * else if (colType.equals("4")) { ImpawnBase ib
								 * = new ImpawnBase(); ib.setImpawnId(colId);
								 * impawnBase = impawnBaseFeign.getById(ib); }
								 * else {
								 * 
								 * } // 获得前五条历史信息 PledgeImpawnLog
								 * pledgeImpawnLog = new PledgeImpawnLog();
								 * pledgeImpawnLog.setCollateralId(colId);
								 * List<PledgeImpawnLog> pledgeImpawnLogList =
								 * pledgeImpawnLogFeign.getLatest5(
								 * pledgeImpawnLog);
								 * 
								 * EvalInfo ei = new EvalInfo();
								 * ei.setCollateralId(colId); evalInfo =
								 * evalInfoFeign.getLatest(ei);
								 * if(evalInfo!=null){
								 * displayColRate=Double.toString(evalInfo.
								 * getMortRate());
								 * displayColValue=Double.toString(evalInfo.
								 * getConfirmAmount()/10000); } List<EvalInfo>
								 * evalInfoList = evalInfoFeign.getAll(ei);
								 * 
								 * ChkInfo ci = new ChkInfo();
								 * ci.setCollateralId(colId); List<ChkInfo>
								 * chkInfoList = chkInfoFeign.getAll(ci);
								 * 
								 * 
								 * //查询已经录入信息的表单 MfCollateralTable
								 * mfCollateralTable = new MfCollateralTable();
								 * mfCollateralTable.setCollateralNo(
								 * collateralId); List<MfCollateralTable>
								 * collateralTableList =
								 * mfCollateralTableFeign.getList(
								 * mfCollateralTable); JsonFormUtil jsonFormUtil
								 * = new JsonFormUtil(); JsonTableUtil jtu = new
								 * JsonTableUtil(); String tableId = ""; String
								 * formId = "";
								 * 
								 * 当query为"query"或者ifBizManger为"0"时，
								 * 解析的表单中不可单字段编辑；
								 * 当ifBizManger为"1"或""时，解析的表单中设置的可编辑的字段可以单字段编辑；
								 * 当ifBizManger为"2"时，解析的表单中所有非只读的字段可以单字段编辑；
								 * 
								 * request.setAttribute("ifBizManger", "3");
								 * for(int
								 * i=0;i<collateralTableList.size();i++){
								 * if(collateralTableList.get(i).getDataFullFlag
								 * ().equals("0")){ continue; } String action =
								 * collateralTableList.get(i).getController();
								 * String htmlStr = ""; tableId = "table" +
								 * collateralTableList.get(i).getShowModelDef();
								 * formId =
								 * collateralTableList.get(i).getShowModelDef();
								 * if(action.equals("PledgeBaseAction")){
								 * pledgeBase = new PledgeBase();
								 * pledgeBase.setPledgeId(collateralId);
								 * pledgeBase =
								 * pledgeBaseFeign.getById(pledgeBase);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, pledgeBase); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("PledgeBldgAction")){
								 * pledgeBldg = new PledgeBldg();
								 * pledgeBldg.setPledgeId(collateralId);
								 * pledgeBldg =
								 * pledgeBldgFeign.getById(pledgeBldg);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, pledgeBldg); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnBaseAction")){
								 * impawnBase = new ImpawnBase();
								 * impawnBase.setImpawnId(collateralId);
								 * impawnBase =
								 * impawnBaseFeign.getById(impawnBase);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnBase); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnStubAction")){
								 * impawnStub = new ImpawnStub();
								 * impawnStub.setImpawnId(collateralId);
								 * impawnStub =
								 * impawnStubFeign.getById(impawnStub);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnStub); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnSavingAction")){/
								 * / impawnSaving = new ImpawnSaving();
								 * impawnSaving.setImpawnId(collateralId);
								 * impawnSaving =
								 * impawnSavingFeign.getById(impawnSaving);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnSaving);
								 * htmlStr = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnNdAction")){
								 * impawnNd = new ImpawnNd();
								 * impawnNd.setImpawnId(collateralId); impawnNd
								 * = impawnNdFeign.getById(impawnNd); formcommon
								 * = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnNd); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnInsAction")){
								 * impawnIns = new ImpawnIns();
								 * impawnIns.setImpawnId(collateralId);
								 * impawnIns =
								 * impawnInsFeign.getById(impawnIns); formcommon
								 * = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnIns); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnInvAction")){
								 * impawnInv = new ImpawnInv();
								 * impawnInv.setImpawnId(collateralId);
								 * impawnInv =
								 * impawnInvFeign.getById(impawnInv); formcommon
								 * = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnInv); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnInvDetailAction")
								 * ){ impawnInvDetail = new ImpawnInvDetail();
								 * impawnInvDetail.setImpawnId(collateralId);
								 * Ipage ipage = this.getIpage(); htmlStr =
								 * jtu.getJsonStr(tableId,"tableTag",
								 * (List<ImpawnInvDetail>)impawnInvDetailFeign.
								 * findByPage(ipage,
								 * impawnInvDetail).getResult(), null,true);
								 * }else
								 * if(action.equals("ImpawnDoAction")){
								 * impawnDo = new ImpawnDo();
								 * impawnDo.setImpawnId(collateralId); impawnDo
								 * = impawnDoFeign.getById(impawnDo); formcommon
								 * = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnDo); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnFeignndAction")){
								 * impawnFeignnd = new ImpawnFeignnd();
								 * impawnFeignnd.setImpawnId(collateralId);
								 * impawnFeignnd =
								 * impawnFeignndFeign.getById(impawnFeignnd);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnFeignnd);
								 * htmlStr = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnIpoIntAction")){
								 * impawnIpoInt = new ImpawnIpoInt();
								 * impawnIpoInt.setImpawnId(collateralId);
								 * impawnIpoInt =
								 * impawnIpoIntFeign.getById(impawnIpoInt);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnIpoInt);
								 * htmlStr = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnNonIpoIntAction")
								 * ){ impawnNonIpoInt = new ImpawnNonIpoInt();
								 * impawnNonIpoInt.setImpawnId(collateralId);
								 * impawnNonIpoInt =
								 * impawnNonIpoIntFeign.getById(impawnNonIpoInt)
								 * ; formcommon =
								 * formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnNonIpoInt);
								 * htmlStr = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnAccRecAction")){
								 * impawnAccRec = new ImpawnAccRec();
								 * impawnAccRec.setImpawnId(collateralId);
								 * impawnAccRec =
								 * impawnAccRecFeign.getById(impawnAccRec);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnAccRec);
								 * htmlStr = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnCvrgAction")){
								 * impawnCvrg = new ImpawnCvrg();
								 * impawnCvrg.setImpawnId(collateralId);
								 * impawnCvrg =
								 * impawnCvrgFeign.getById(impawnCvrg);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnCvrg); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnAccpAction")){
								 * impawnAccp = new ImpawnAccp();
								 * impawnAccp.setImpawnId(collateralId);
								 * impawnAccp =
								 * impawnAccpFeign.getById(impawnAccp);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnAccp); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnBillAction")){
								 * impawnBill = new ImpawnBill();
								 * impawnBill.setImpawnId(collateralId);
								 * impawnBill =
								 * impawnBillFeign.getById(impawnBill);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnBill); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("ImpawnOtherAction")){
								 * impawnOther = new ImpawnOther();
								 * impawnOther.setImpawnId(collateralId);
								 * impawnOther =
								 * impawnOtherFeign.getById(impawnOther);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, impawnOther); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }else
								 * if(action.equals("EvalInfoAction")){//
								 * table evalInfo = new EvalInfo();
								 * evalInfo.setCollateralId(collateralId); Ipage
								 * ipage = this.getIpage(); htmlStr =
								 * jtu.getJsonStr(tableId,"tableTag",
								 * (List<EvalInfo>)evalInfoFeign.findByPage(
								 * ipage, evalInfo).getResult(), null,true);
								 * 
								 * }else
								 * if(action.equals("FairInfoAction")){
								 * fairInfo = new FairInfo();
								 * fairInfo.setCollateralId(collateralId);
								 * fairInfo =
								 * fairInfoFeign.getByCollateralId(fairInfo);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, fairInfo); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query);
								 * 
								 * }else if(action.equals("InsInfoAction")){
								 * insInfo = new InsInfo();
								 * insInfo.setCollateralId(collateralId);
								 * insInfo =
								 * insInfoFeign.getByCollateralId(insInfo);
								 * formcommon = formService.getFormData(formId);
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, insInfo); htmlStr =
								 * jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query);
								 * 
								 * }else
								 * if(action.equals("CertiInfoAction")){//
								 * table certiInfo = new CertiInfo();
								 * certiInfo.setCollateralId(collateralId);
								 * Ipage ipage = this.getIpage(); htmlStr =
								 * jtu.getJsonStr(tableId,"tableTag",
								 * (List<CertiInfo>)certiInfoFeign.findByPage(
								 * ipage, certiInfo).getResult(), null,true);
								 * 
								 * }else
								 * if(action.equals("KeepInfoAction")){ //
								 * keepInfo = new KeepInfo(); //
								 * keepInfo.setCollateralId(collateralId); //
								 * keepInfo =
								 * keepInfoFeign.getByCollateralId(keepInfo); //
								 * formcommon = formService.getFormData(formId);
								 * // getFormValue(formcommon); //
								 * getObjValue(formcommon, keepInfo); // htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query);
								 * 
								 * }else
								 * if(action.equals("ChkInfoAction")){//
								 * table chkInfo = new ChkInfo();
								 * chkInfo.setCollateralId(collateralId); Ipage
								 * ipage = this.getIpage(); htmlStr =
								 * jtu.getJsonStr(tableId,"tableTag",
								 * (List<ChkInfo>)chkInfoFeign.findByPage(ipage,
								 * chkInfo).getResult(), null,true); }
								 * 
								 * collateralTableList.get(i).setHtmlStr(htmlStr
								 * ); }
								 * 
								 * // mfBusApply = new MfBusApply(); //
								 * appId="app16102211524039916"; //
								 * if(StringUtil.isNotEmpty(appId)){ //
								 * mfBusApply=appInterfaceFeign.
								 * getMfBusApplyByAppId(appId); // if
								 * (mfBusApply!=null) { //
								 * mfBusApply.setFincAmt(MathExtend.moneyStr(
								 * mfBusApply.getAppAmt()/10000)); //
								 * //获取该客户除当前业务之外的其他的业务列表 // MfBusApply
								 * mfBusApplyTmp = new MfBusApply(); //
								 * mfBusApplyTmp.setCusNo(cusNo); //
								 * mfBusApplyTmp.setAppId(mfBusApply.getAppId())
								 * ; // List<MfBusApply> mfBusApplyList =
								 * appInterfaceFeign.getOtherApplyList(
								 * mfBusApplyTmp); // JSONArray array =
								 * JSONArray.fromObject(mfBusApplyList); // json
								 * = new JSONObject(); //
								 * json.put("mfBusApplyList", array); // //
								 * if(mfBusApply.getAppSts().equals(BizPubParm.
								 * APP_STS_PASS)){ // mfBusPact = new
								 * MfBusPact(); // mfBusPact =
								 * pactInterfaceFeign.getByAppId(mfBusApply.
								 * getAppId()); //
								 * mfBusPact.setFincAmt(MathExtend.moneyStr(
								 * mfBusPact.getUsableFincAmt()/10000)); //
								 * //获取该客户除当前业务之外的其他合同列表 // MfBusPact
								 * mfBusPactTmp = new MfBusPact(); //
								 * mfBusPactTmp.setCusNo(cusNo); //
								 * mfBusPactTmp.setAppId(mfBusApply.getAppId());
								 * // List<MfBusPact> mfBusPactList =
								 * pactInterfaceFeign.getOtherPactList(
								 * mfBusPactTmp); // } // } // } Map<String,
								 * Object> dataMap = new HashMap<String,
								 * Object>(); dataMap.put("collateralTableList",
								 * collateralTableList); JSONObject jb =
								 * JSONObject.fromObject(dataMap); dataMap = jb;
								 * getCollateralByCusNoAjax();
								 */

		return "/component/collateral/MfBusCollateral_detail";

	}

	/**
	 * 查询客户名下押品
	 * 
	 * @return
	 * @throws Exception
	 */
	public void getCollateralByCusNoAjax(String collateralCusNo) throws Exception {
		ActionContext.initialize(request, response);
		@SuppressWarnings("unused")
		List<MfBusCollateral> mfBusCollateralList = new ArrayList<MfBusCollateral>();
		MfBusCollateral mcl = new MfBusCollateral();
		mcl.setCollateralCusNo(collateralCusNo);
		mfBusCollateralList = mfBusCollateralFeign.getCollateralByCusNo(mcl);
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlmfbuscollateral0002 = formService.getFormData("dlmfbuscollateral0002");
		MfBusCollateral mfBusCollateral = new MfBusCollateral();
		List<MfBusCollateral> mfBusCollateralList = mfBusCollateralFeign.getAll(mfBusCollateral);
		model.addAttribute("formdlmfbuscollateral0002", formdlmfbuscollateral0002);
		model.addAttribute("mfBusCollateralList", mfBusCollateralList);
		model.addAttribute("query", "");
		return "/component/collateral/MfBusCollateral_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlmfbuscollateral0002 = formService.getFormData("dlmfbuscollateral0002");
			getFormValue(formdlmfbuscollateral0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlmfbuscollateral0002)) {
				MfBusCollateral mfBusCollateral = new MfBusCollateral();
				setObjValue(formdlmfbuscollateral0002, mfBusCollateral);
				mfBusCollateralFeign.insert(mfBusCollateral);
				getTableData(dataMap, tableId, mfBusCollateral);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/inStockAjax")
	@ResponseBody
	public Map<String, Object> inStockAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*
		 * * ActionContext.initialize(request, response); FormService
		 * formService = new FormService(); Map<String, Object> dataMap = new
		 * HashMap<String, Object>(); dataMap.put("collateralId", collateralId);
		 * 
		 * if(StringUtil.isEmpty(collateralMethod)||
		 * StringUtil.isEmpty(collateralId)){
		 * 
		 * }else{ switch (Integer.parseInt(collateralMethod)) { case 3:
		 * pledgeBase = new PledgeBase(); pledgeBase.setPledgeId(collateralId);
		 * pledgeBase.setKeepStatus(CollateralConstant. INSTOCK);
		 * pledgeBaseFeign.updateKeepStatus(pledgeBase); break; case 4:
		 * impawnBase=new ImpawnBase(); impawnBase.setImpawnId(collateralId);
		 * impawnBase.setKeepStatus(CollateralConstant. INSTOCK);
		 * impawnBaseFeign.updateKeepStatus(impawnBase); break; default: break;
		 * } }
		 */
		return dataMap;
	}

	@RequestMapping("/outStockAjax")
	@ResponseBody
	public Map<String, Object> outStockAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*
		 * ActionContext.initialize(request, response); FormService formService
		 * = new FormService(); Map<String, Object> dataMap = new
		 * HashMap<String, Object>(); dataMap.put("collateralId", collateralId);
		 * 
		 * if(StringUtil.isEmpty(collateralMethod)||
		 * StringUtil.isEmpty(collateralId)){
		 * 
		 * }else{ switch (Integer.parseInt(collateralMethod)) { case 3:
		 * pledgeBase = new PledgeBase(); pledgeBase.setPledgeId(collateralId);
		 * pledgeBase.setKeepStatus(CollateralConstant. OUTSTOCK);
		 * pledgeBaseFeign.updateKeepStatus(pledgeBase); break; case 4:
		 * impawnBase=new ImpawnBase(); impawnBase.setImpawnId(collateralId);
		 * impawnBase.setKeepStatus(CollateralConstant. OUTSTOCK);
		 * impawnBaseFeign.updateKeepStatus(impawnBase); break; default: break;
		 * } }
		 */
		return dataMap;
	}

	@RequestMapping("/handleCollateralAjax")
	@ResponseBody
	public Map<String, Object> handleCollateralAjax() throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		/*
		 * ActionContext.initialize(request, response); FormService formService
		 * = new FormService(); Map<String, Object> dataMap = new
		 * HashMap<String, Object>(); dataMap.put("collateralId", collateralId);
		 * 
		 * if(StringUtil.isEmpty(collateralMethod)||
		 * StringUtil.isEmpty(collateralId)){
		 * 
		 * }else{ switch (Integer.parseInt(collateralMethod)) { case 3:
		 * pledgeBase = new PledgeBase(); pledgeBase.setPledgeId(collateralId);
		 * pledgeBase.setKeepStatus(CollateralConstant. HANDLECOLLATERAL);
		 * pledgeBaseFeign.updateKeepStatus(pledgeBase); break; case 4:
		 * impawnBase=new ImpawnBase(); impawnBase.setImpawnId(collateralId);
		 * impawnBase.setKeepStatus(CollateralConstant. HANDLECOLLATERAL);
		 * impawnBaseFeign.updateKeepStatus(impawnBase); break; default: break;
		 * } }
		 */
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateral mfBusCollateral = new MfBusCollateral();
		try {
			FormData formdlmfbuscollateral0002 = formService.getFormData("dlmfbuscollateral0002");
			getFormValue(formdlmfbuscollateral0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlmfbuscollateral0002)) {
				mfBusCollateral = new MfBusCollateral();
				setObjValue(formdlmfbuscollateral0002, mfBusCollateral);
				mfBusCollateralFeign.update(mfBusCollateral);
				getTableData(dataMap, tableId, mfBusCollateral);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	// @RequestMapping("/certiInfo") @ResponseBody public Map<String, Object>
	// getByIdAjax()
	// throws Exception {
	// ActionContext.initialize(request,
	// response);
	// Map<String,Object> formData = new HashMap<String,Object>();
	// Map<String, Object> dataMap = new HashMap<String, Object>();
	// FormData formdlmfbuscollateral0002 =
	// formService.getFormData("dlmfbuscollateral0002");
	// mfBusCollateral = new MfBusCollateral();
	// mfBusCollateral.setId(id);
	// mfBusCollateral = mfBusCollateralFeign.getById(mfBusCollateral);
	// getObjValue(formdlmfbuscollateral0002, mfBusCollateral,formData);
	// if(mfBusCollateral!=null){
	// dataMap.put("flag", "success");
	// }else{
	// dataMap.put("flag", "error");
	// }
	// dataMap.put("formData", formData);
	// return dataMap;
	// }
	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCollateral mfBusCollateral = new MfBusCollateral();
		// mfBusCollateral.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfBusCollateral = (MfBusCollateral) JSONObject.toBean(jb, MfBusCollateral.class);
			mfBusCollateralFeign.delete(mfBusCollateral);
			getTableData(dataMap, tableId, mfBusCollateral);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 方法描述： 押品新增时跳转到弹框选择押品类型
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPledgeImpanwnTypeList")
	public String getPledgeImpanwnTypeList() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/getPledgeImpanwnTypeList";
	}

	/**
	 * 
	 * 方法描述： 解除押品关联关系
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-14 上午9:23:34
	 */
	@RequestMapping("/releaseCollateralAjax")
	@ResponseBody
	public Map<String, Object> releaseCollateralAjax(String appId, String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 删除押品与业务关联关系数据
			MfBusCollateralRel busCollateralRel = new MfBusCollateralRel();
			busCollateralRel.setAppId(appId);
			busCollateralRel = mfBusCollateralRelFeign.getById(busCollateralRel);
			MfBusCollateralDetailRel collateralDetailRel = new MfBusCollateralDetailRel();
			collateralDetailRel.setBusCollateralId(busCollateralRel.getBusCollateralId());
			collateralDetailRel.setCollateralId(collateralId);
			mfBusCollateralDetailRelFeign.delete(collateralDetailRel);
			// 修改业务押品关联关系状态为解除
			MfBusCollateral mfBusCollateral = new MfBusCollateral();
			mfBusCollateral.setCollateralId(collateralId);
			mfBusCollateral.setIsUsed(CollateralConstant.NOUSED);
			// mfBusCollateralFeign.update(mfBusCollateral);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 跳转选择客户押品弹框
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 上午9:43:02
	 */
	@RequestMapping("/getCollateralPageForSelect")
	public String getCollateralPageForSelect() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/mfCollateral_select";
	}

	/**
	 * 
	 * 方法描述： 获得选择押品分页列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-4-13 上午9:54:34
	 */
	/*
	 * @RequestMapping("/certiInfo") public String
	 * getCollateralListByCusNoPage() throws Exception{
	 * ActionContext.initialize(request, response); FormService formService =
	 * new FormService(); Map<String, Object> dataMap = new HashMap<String,
	 * Object>(); MfBusCollateral mfBusCollateral = new MfBusCollateral(); try {
	 * mfBusCollateral.setCustomQuery(ajaxData);// 自定义查询参数赋值
	 * mfBusCollateral.setCustomSorts(ajaxData);// 自定义排序参数赋值
	 * mfBusCollateral.setCriteriaList(mfBusCollateral, ajaxData);// 我的筛选
	 * mfBusCollateral.setCollateralCusNo(cusNo);
	 * mfBusCollateral.setCollateralMethod(vouType); //
	 * this.getRoleConditions(mfBusCollateral,"1000000065");//记录级权限控制方法 Ipage
	 * ipage = this.getIpage(); ipage.setPageNo(pageNo);
	 * ipage.setPageSize(pageSize);// 异步传页面翻页参数 // 自定义查询Feign方法 ipage =
	 * mfBusCollateralFeign.getCollateralListByCusNoPage(ipage,
	 * mfBusCollateral,appId); JsonTableUtil jtu = new JsonTableUtil(); String
	 * tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(),
	 * ipage, true); ipage.setResult(tableHtml); dataMap.put("ipage", ipage); }
	 * catch (Exception e) { e.printStackTrace(); dataMap.put("flag", "error");
	 * dataMap.put("msg", e.getMessage()); throw e; } return dataMap;
	 * 
	 * }
	 */
	/***
	 * Ajax抵质押品类型列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/selectPledgeImpanwnTypeAjax")
	@ResponseBody
	public Map<String, Object> selectPledgeImpanwnTypeAjax(String ajaxData, Integer pageNo, Integer pageSize,
			String tableId, String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ParmDic parmDic = new ParmDic();
		try {
			parmDic.setCustomQuery(ajaxData);// 自定义查询参数赋值
			parmDic.setCriteriaList(parmDic, ajaxData);// 我的筛选
			// this.getRoleConditions(mfPledgeClass,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			String optCode = parmDic.getOptCode();
			if ("4".equals(optCode)) {
				parmDic.setKeyName("IMPAWN_TYPE");
			} else {
				parmDic.setKeyName("PLEDGE_TYPE");
			}
			parmDic.setSts("1");
			ipage = nmdInterfaceFeign.findByPage(ipage, parmDic);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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

	/***
	 * 方法描述： 押品新增主页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/collateralClassInput")
	public String collateralClassInput()
			throws Exception {/*
								 * ActionContext.initialize(request, response);
								 * FormService formService = new FormService();
								 * 
								 * //查询已经录入信息的表单 MfCollateralTable
								 * mfCollateralTable = new MfCollateralTable();
								 * mfCollateralTable.setCollateralNo(
								 * collateralNo); List<MfCollateralTable>
								 * collateralTableList =
								 * mfCollateralTableFeign.getList(
								 * mfCollateralTable); JsonFormUtil jsonFormUtil
								 * = new JsonFormUtil(); JsonTableUtil jtu = new
								 * JsonTableUtil(); for(int
								 * i=0;i<collateralTableList.size();i++){
								 * if(collateralTableList.get(i).getDataFullFlag
								 * ().equals("0")){ continue; } String action =
								 * collateralTableList.get(i).getController();
								 * String htmlStr = "";
								 * if(action.equals("PledgeBaseAction")){
								 * pledgeBase = new PledgeBase();
								 * pledgeBase.setPledgeId(collateralNo);
								 * pledgeBase =
								 * pledgeBaseFeign.getById(pledgeBase);
								 * formcommon =
								 * formService.getFormData(collateralTableList.
								 * get(i).getShowModelDef());
								 * getFormValue(formcommon);
								 * getObjValue(formcommon, pledgeBase); htmlStr
								 * = jsonFormUtil.getJsonStr(formcommon,
								 * "propertySeeTag", query); }
								 * 
								 * 
								 * collateralTableList.get(i).setHtmlStr(htmlStr
								 * ); }
								 * 
								 * Map<String, Object> dataMap = new
								 * HashMap<String, Object>();
								 * dataMap.put("collateralTableList",
								 * collateralTableList); JSONObject jb =
								 * JSONObject.fromObject(dataMap); dataMap = jb;
								 * 
								 * System.out.println(jb);
								 */
		return "/component/collateral/collateralClassInput";
	}

	public int dateDiff(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long result = 0;
		try {
			long time1 = sdf.parse(date1).getTime();
			long time2 = sdf.parse(date2).getTime();
			result = time1 - time2;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) (result / (24 * 60 * 60 * 1000));
	}

	public Map<String, Object> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}

}
