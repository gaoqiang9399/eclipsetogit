package app.component.pfs.controller;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.SpringUtil;
import app.component.archives.entity.Constant;
import app.component.common.ZipCompressing;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersonCorp;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersonCorpFeign;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.entity.MfCusReportCalc;
import app.component.cus.report.entity.MfCusReportData;
import app.component.cus.report.entity.MfCusReportErrorInfo;
import app.component.cus.report.feign.*;
import app.component.doc.feign.DocFeign;
import app.component.eval.entity.AppEval;
import app.component.eval.feign.AppEvalFeign;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.nmd.entity.ParmDic;
import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.CusFinSubjectData;
import app.component.pfs.entity.CusFinRatioData;
import app.component.pfs.entity.CusFinParm;
import app.component.pfs.feign.CusFinMainFeign;
import app.component.pfs.feign.CusFinCapDataFeign;
import app.component.pfs.feign.CusFinCashDataFeign;
import app.component.pfs.feign.CusFinProDataFeign;
import app.component.pfs.feign.CusFinRatioDataFeign;
import app.component.pfs.feign.CusFinParmFeign;
import app.component.pfs.feign.CusFinFormulaFeign;
import app.component.pfs.feign.CusFinSubjectDataFeign;
import app.tech.upload.UploadUtil;
import app.util.ZipTool;
import cn.mftcc.util.*;
import config.YmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.ServiceException;
import app.component.common.BizPubParm;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pfs.util.PoiExportExcelUtil;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: CusFinMainAction.java Description:财务报表主表视图控制
 *
 * @author:LJW
 * @Mon May 09 05:23:55 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinMain")
public class CusFinMainController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinMainBo
	@Autowired
	private CusFinMainFeign cusFinMainFeign;
	@Autowired
	private CusFinCapDataFeign cusFinCapDataFeign;
	@Autowired
	private CusFinCashDataFeign cusFinCashDataFeign;
	@Autowired
	private CusFinProDataFeign cusFinProDataFeign;
	@Autowired
	private CusFinRatioDataFeign cusFinRatioDataFeign;
	@Autowired
	private CusFinParmFeign cusFinParmFeign;
	@Autowired
	private CusFinFormulaFeign cusFinFormulaFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CusFinSubjectDataFeign cusFinSubjectDataFeign;
	@Autowired
	private MfCusReportAcountFeign mfCusReportAcountFeign;
	@Autowired
	private MfCusReportItemFeign mfCusReportItemFeign;
	@Autowired
	private MfCusReportDataFeign mfCusReportDataFeign;
	@Autowired
	private MfCusReportCalcFeign  mfCusReportCalcFeign;
	@Autowired
	private AppEvalFeign  appEvalFeign;
	@Autowired
	private MfCusCustomerFeign  mfCusCustomerFeign;
	@Autowired
	private MfCusPersonCorpFeign mfCusPersonCorpFeign;
	@Autowired
	private MfCusReportErrorInfoFeign mfCusReportErrorInfoFeign;
	@Autowired
	private DocFeign docFeign;

	// 全局变量
	// 名下企业
	// 异步参数
	// 表单变量

//	/**
//	 * 列表打开页面请求
//	 *
//	 * @param cusNo
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getListPage")
//	public String getListPage(Model model, String cusNo) throws Exception {
//		ActionContext.initialize(request, response);
//		CusFinMain cusFinMain = new CusFinMain();
//		cusFinMain.setCusNo(cusNo);
//		cusFinMain.setAccRule("1");
//		List<CusFinMain> cusFinMainList = cusFinMainFeign.getListByAccRule(cusFinMain);
//		model.addAttribute("cusFinMainList", cusFinMainList);
//		model.addAttribute("query", "");
//		return "/component/pfs/CusFinMain_ListForSelect";
//	}

	/**
	 * 列表打开页面请求-peng
	 *
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo,String gradeType,String evalClass) throws Exception {
		ActionContext.initialize(request, response);
		MfCusReportAcount mfCusReportAcount=new MfCusReportAcount();
		mfCusReportAcount.setCusNo(cusNo);
		mfCusReportAcount.setAccRule("1");
		if(BizPubParm.GRADE_TYPE_4.equals(gradeType) && StringUtil.isNotEmpty(evalClass) && evalClass.equals(BizPubParm.EVAL_CLASS_1)){
			mfCusReportAcount.setReportType("1");
		}
		List<MfCusReportAcount> cusFinMainList = new ArrayList<>();
		cusFinMainList=mfCusReportAcountFeign.getListForEval(mfCusReportAcount);
		Ipage ipage = this.getIpage();
		model.addAttribute("ipage", ipage);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinMain_ListForSelect";
	}

//	/**
////	 * 打开财务列表页面
////	 *
////	 * @author LJW date 2017-4-14
////	 * @param cusNo
////	 */
////	@RequestMapping(value = "/getListPage1")
////	public String getListPage1(Model model, String cusNo) throws Exception {
////		ActionContext.initialize(request, response);
////		CusFinMain cusFinMain = new CusFinMain();
////		cusFinMain.setCusNo(cusNo);
////		List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
////		if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
////			for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
////				cFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cFinMain, "1"));
////				cFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cFinMain, "2"));
////				cFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cFinMain, "3"));
////				cFinMain.setFinSubjectFlag(cusFinMainFeign.doCheckFinData(cFinMain, "5"));
////			}
////		}
////		String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
////		model.addAttribute("sysProjectName", sysProjectName);
////		model.addAttribute("cusFinMainList", cusFinMainList);
////		model.addAttribute("query", "");
////		model.addAttribute("cusNo",cusNo);
////		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
////		model.addAttribute("reportConfirmFlag", reportConfirmFlag);
////		return "/component/pfs/CusFinMain_List";
////	}

	/**
	 * 打开财务列表页面 20190514-pengchong
	 *
	 * @author LJW date 2017-4-14
	 * @param cusNo
	 */
	@RequestMapping(value = "/getListPage1")
	public String getListPage1(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusReportAcount mfCusReportAccount=new MfCusReportAcount();
		mfCusReportAccount.setCusNo(cusNo);
		List<MfCusReportAcount> cusFinMainList=mfCusReportAcountFeign.getList(mfCusReportAccount);
		cusFinMainList=setReportIsUsed(cusFinMainList);
//		CusFinMain cusFinMain = new CusFinMain();
//		cusFinMain.setCusNo(cusNo);
//		List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
//		if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
//			for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
//				cFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cFinMain, "1"));
//				cFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cFinMain, "2"));
//				cFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cFinMain, "3"));
//			}
//		}
		//查询报表自动处理结果
		MfCusReportErrorInfo mfCusReportErrorInfo = new MfCusReportErrorInfo();
		mfCusReportErrorInfo.setCusNo(cusNo);
		List<MfCusReportErrorInfo> mfCusReportErrorInfoList = mfCusReportErrorInfoFeign.getList(mfCusReportErrorInfo);
		String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
		model.addAttribute("sysProjectName", sysProjectName);
		model.addAttribute("mfCusReportErrorInfoList", mfCusReportErrorInfoList);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");

		model.addAttribute("reportConfirmFlag", reportConfirmFlag);
		return "/component/pfs/CusFinMain_List";
	}
	//判断财报是否被引用
	public List<MfCusReportAcount>  setReportIsUsed(List<MfCusReportAcount> list) throws Exception {
		Iterator it=list.iterator();
		while(it.hasNext()){
			MfCusReportAcount mfCusReportAccunt=(MfCusReportAcount)it.next();
			AppEval appEval=new AppEval();
			appEval.setCusNo(mfCusReportAccunt.getCusNo());
			appEval.setRptDate(mfCusReportAccunt.getWeeks());
			appEval.setRptType(mfCusReportAccunt.getReportType());
			List appEvalList=appEvalFeign.getListByCusNoAndRptDateAndRptType(appEval);
			if(appEvalList!=null&&appEvalList.size()>0){
				mfCusReportAccunt.setIsUsed(BizPubParm.YES_NO_Y);
			}else{
				mfCusReportAccunt.setIsUsed(BizPubParm.YES_NO_N);
			}
		}
		return list;
	}


//	/**
//	 * 打开个人财务财务列表页面
//	 *
//	 * @author ywh date 2017-8-2
//	 * @param cusNo
//	 */
//	@RequestMapping(value = "/getListPageForPerson")
//	public String getListPageForPerson(Model model, String ajaxData, String cusNo) throws Exception {
//		ActionContext.initialize(request, response);
//		CusFinMain cusFinMain = new CusFinMain();
//		cusFinMain.setCusNo(cusNo);
//		List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
//		if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
//			for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
//				cFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cFinMain, "1"));
//				cFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cFinMain, "2"));
//				cFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cFinMain, "3"));
//				cFinMain.setFinSubjectFlag(cusFinMainFeign.doCheckFinData(cFinMain, "5"));
//			}
//		}
//		model.addAttribute("cusFinMainList", cusFinMainList);
//		model.addAttribute("query", "");
//		model.addAttribute("cusNo",cusNo);
//		return "/component/pfs/CusFinMain_ListForPerson";
//	}

	/**
	 * 打开财务列表页面 20190514-pengchong
	 *
	 * @author LJW date 2017-4-14
	 * @param cusNo
	 */
	@RequestMapping(value = "/getListPageForPerson")
	public String getListPageForPerson(Model model, String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfCusReportAcount mfCusReportAccount=new MfCusReportAcount();
		mfCusReportAccount.setCusNo(cusNo);
		List<MfCusReportAcount> cusFinMainList=mfCusReportAcountFeign.getList(mfCusReportAccount);
		cusFinMainList=setReportIsUsed(cusFinMainList);
		String sysProjectName = PropertiesUtil.getSysParamsProperty("sys.project.name");
		model.addAttribute("sysProjectName", sysProjectName);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");

		model.addAttribute("reportConfirmFlag", reportConfirmFlag);
		return "/component/pfs/CusFinMain_ListForPerson";
	}





	@RequestMapping(value = "/getAll")
	public String getAll(Model model, String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Object obj = cusInterfaceFeign.getCusByCusNo(cusNo);
		JSONObject jb = JSONObject.fromObject(obj);
		String cusName = jb.getString("cusName");
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
		JSONArray array = JSONArray.fromObject(cusFinMainList);
		JSONObject json = new JSONObject();
		json.put("cusFinMainList", array);
		model.addAttribute("json", json);
		model.addAttribute("cusName", cusName);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinMain_All";
	}

//	/**
//	 * 打开财务报表查看页面
//	 *
//	 * @author LJW date 2017-4-13
//	 * @param cusNo
//	 * @param finRptDate
//	 * @param finRptType
//	 * @param relationCorpName
//	 * @param relationCorpNo
//	 * @param accRule
//	 * @param finRptSts
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = "/inputReportView")
//	public String inputReportView(Model model, String ajaxData, String cusNo, String finRptDate, String finRptType,
//			String relationCorpName, String relationCorpNo, String accRule, String finRptSts) throws Exception {
//		ActionContext.initialize(request, response);
//		String teplateType = request.getParameter("teplateType");
//		String uploadFlag = request.getParameter("uploadFlag");
//		String finRatioEditFlag = request.getParameter("finRatioEditFlag");
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		CusFinMain cusFinMain = new CusFinMain();
//		cusFinMain.setCusNo(cusNo);
//		cusFinMain.setFinRptDate(finRptDate);
//		cusFinMain.setFinRptType(finRptType);
//		if("null".equals(relationCorpName)){
//			relationCorpName = null;
//		}
//		// 名下企业
//		if (StringUtil.isNotEmpty(relationCorpName)) {
//			cusFinMain.setRelationCorpName(relationCorpName);
//		}
//		if (StringUtil.isNotEmpty(relationCorpNo)) {
//			cusFinMain.setRelationCorpNo(relationCorpNo);
//		}
//		cusFinMain = cusFinMainFeign.getById(cusFinMain);
//		try{
//		cusFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_1));
//		cusFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_2));
//		cusFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_3));
//		cusFinMain.setFinSubjectFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_5));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		String query = "";
//		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
//		if(!"2".equals(reportConfirmFlag)){
//			if ("1".equals(cusFinMain.getFinRptSts())) {
//				query = "query";
//				request.setAttribute("query", query);
//			}
//		}
//		CusFinParm cusFinParm = new CusFinParm();
//		cusFinParm.setUseFlag("1");
//		List<CusFinParm> cusFinParmList = cusFinParmFeign.getList(cusFinParm);
//		List<CusFinParm> capList = new ArrayList<CusFinParm>();// 资产类
//		List<CusFinParm> capList1 = new ArrayList<CusFinParm>();// 负债类
//		List<CusFinParm> cashList = new ArrayList<CusFinParm>();
//		List<CusFinParm> proDataList = new ArrayList<CusFinParm>();
//		Map<String, String> dataTypeMap = new HashMap<String, String>();
//		dataTypeMap.put("finRptDate", finRptDate);
//		dataTypeMap.put("finRptType", finRptType);
//		// 资产负债表
//		String finRptYear = finRptDate.substring(0, 4);
//		CusFinCapData cusFinCapData = new CusFinCapData();
//		cusFinCapData.setCusNo(cusNo);
//		// 名下企业
//		if (StringUtil.isNotEmpty(relationCorpName)) {
//			cusFinCapData.setRelationCorpName(relationCorpName);
//		}
//		if (StringUtil.isNotEmpty(relationCorpNo)) {
//			cusFinCapData.setRelationCorpNo(relationCorpNo);
//		}
//		cusFinCapData.setFinRptYear(finRptYear);
//		cusFinCapData.setAccRule(accRule);
//		List<CusFinCapData> cusFinCapDataList = cusFinCapDataFeign.getByCusNoDateTypeForList(cusFinCapData);
//		// 现金流量表
//		CusFinCashData cusFinCashData = new CusFinCashData();
//		cusFinCashData.setCusNo(cusNo);
//		// 名下企业
//		if (StringUtil.isNotEmpty(relationCorpName)) {
//			cusFinCashData.setRelationCorpName(relationCorpName);
//		}
//		if (StringUtil.isNotEmpty(relationCorpNo)) {
//			cusFinCashData.setRelationCorpNo(relationCorpNo);
//		}
//		cusFinCashData.setFinRptYear(finRptYear);
//		cusFinCashData.setAccRule(accRule);
//		List<CusFinCashData> cusFinCashDataList = cusFinCashDataFeign.getByCusNoDateTypeForList(cusFinCashData);
//		// 利润表
//		CusFinProData cusFinProData = new CusFinProData();
//		cusFinProData.setCusNo(cusNo);
//		// 名下企业
//		if (StringUtil.isNotEmpty(relationCorpName)) {
//			cusFinProData.setRelationCorpName(relationCorpName);
//		}
//		if (StringUtil.isNotEmpty(relationCorpNo)) {
//			cusFinProData.setRelationCorpNo(relationCorpNo);
//		}
//		cusFinProData.setFinRptYear(finRptYear);
//		cusFinProData.setAccRule(accRule);
//		List<CusFinProData> cusFinProDataList = cusFinProDataFeign.getByCusNoDateTypeForList(cusFinProData);
//		// 报表是否上传标志
//		for (CusFinParm cusFinParm1 : cusFinParmList) {
//			String reportType = cusFinParm1.getReportType();
//			String codeColumn = cusFinParm1.getCodeColumn();
//			CusFinFormula cusFinFormula = new CusFinFormula();
//			cusFinFormula.setCodeColumn(codeColumn);
//			List<CusFinFormula> list = cusFinFormulaFeign.getByCodeColumn(cusFinFormula);
//			dataMap.put(codeColumn, list);
//			if (BizPubParm.FIN_REPORT_TYPE_1.equals(reportType)) {// 资产负债表
//				cusFinCapData = null;
//				CusFinCapData temp = new CusFinCapData();
//				Iterator<CusFinCapData> iter = cusFinCapDataList.iterator();
//				while (iter.hasNext()) {
//					temp = iter.next();
//					String tempCusNo = temp.getCusNo();
//					String tempFinRptYear = temp.getFinRptYear();
//					String tempAccRule = temp.getAccRule();
//					String tempCodeColumn = temp.getCodeColumn();
//					if (cusNo.endsWith(tempCusNo) && finRptYear.equals(tempFinRptYear) && accRule.equals(tempAccRule)
//							&& codeColumn.equals(tempCodeColumn)) {
//						cusFinCapData = temp;
//						break;
//					}
//				}
//				setCusFinModelValue(cusFinParm1, cusFinCapData, dataTypeMap);
//				if (cusFinParm1.getZcfzType().equals("1")) {// 资产类
//					capList.add(cusFinParm1);
//				} else {// 负债类
//					capList1.add(cusFinParm1);
//				}
//			} else if (BizPubParm.FIN_REPORT_TYPE_2.equals(reportType)) {// 利润分配表
//				cusFinProData = null;
//				CusFinProData temp = new CusFinProData();
//				Iterator<CusFinProData> iter = cusFinProDataList.iterator();
//				while (iter.hasNext()) {
//					temp = iter.next();
//					String tempCusNo = temp.getCusNo();
//					String tempFinRptYear = temp.getFinRptYear();
//					String tempAccRule = temp.getAccRule();
//					String tempCodeColumn = temp.getCodeColumn();
//					if (cusNo.endsWith(tempCusNo) && finRptYear.equals(tempFinRptYear) && accRule.equals(tempAccRule)
//							&& codeColumn.equals(tempCodeColumn)) {
//						cusFinProData = temp;
//						break;
//					}
//				}
//				setCusFinModelValue(cusFinParm1, cusFinProData, dataTypeMap);
//				proDataList.add(cusFinParm1);
//			} else if (BizPubParm.FIN_REPORT_TYPE_3.equals(reportType)) {// 现金流量表
//				cusFinCashData = null;
//				CusFinCashData temp = new CusFinCashData();
//				Iterator<CusFinCashData> iter = cusFinCashDataList.iterator();
//				while (iter.hasNext()) {
//					temp = iter.next();
//					String tempCusNo = temp.getCusNo();
//					String tempFinRptYear = temp.getFinRptYear();
//					String tempAccRule = temp.getAccRule();
//					String tempCodeColumn = temp.getCodeColumn();
//					if (cusNo.endsWith(tempCusNo) && finRptYear.equals(tempFinRptYear) && accRule.equals(tempAccRule)
//							&& codeColumn.equals(tempCodeColumn)) {
//						cusFinCashData = temp;
//						break;
//					}
//				}
//				setCusFinModelValue(cusFinParm1, cusFinCashData, dataTypeMap);
//				cashList.add(cusFinParm1);
//			}
//		}
//		dataMap.put("cusFinMain", cusFinMain);
//		dataMap.put("capList", capList);
//		dataMap.put("capList1", capList1);
//		dataMap.put("cashList", cashList);
//		dataMap.put("proDataList", proDataList);
//		//财务指标
//		List<CusFinRatioData> cusFinRatioDataList = null;
//		if ("1".equals(cusFinMain.getFinRptSts())) {//数据确认后才显示财务指标
////			cusFinRatioDataList = cusFinRatioDataFeign.getForFinIndicators(cusFinMain);
//			Map<String,Object> cusFinRatioMap = cusFinRatioDataFeign.getCusFinRatioListByClassNo(cusFinMain);
//			List<ParmDic> classNoList =(List<ParmDic>)cusFinRatioMap.get("classNoList");
//			Map<String,Object> cusFinRatioDataListMap = (Map<String,Object>)cusFinRatioMap.get("cusFinRatioDataMap");
//			dataMap.put("classNoList", classNoList);
//			dataMap.put("cusFinRatioDataListMap", cusFinRatioDataListMap);
//		}
//
//		//科目余额表
//		CusFinSubjectData cusFinSubjectData = new CusFinSubjectData();
//		cusFinSubjectData.setCusNo(cusNo);
//		cusFinSubjectData.setFinRptType(finRptType);
//		cusFinSubjectData.setFinRptYear(finRptYear);
//		cusFinSubjectData.setFinRptDate(finRptDate);
//		Map<String,Object> resMap = cusFinSubjectDataFeign.getByCusNoDateTypeForList(cusFinSubjectData);
//		Map<String,Object> cusFinSubjectDataList = (Map<String,Object>)resMap.get("cusFinSubjectDataListMap");
//		List<CusFinSubjectData>  firstList = (List<CusFinSubjectData>)resMap.get("firstList");
//		dataMap.put("cusFinSubjectDataListMap", cusFinSubjectDataList);
//		dataMap.put("firstList", firstList);
//
//		dataMap = JSONObject.fromObject(dataMap);
//		model.addAttribute("dataMap", dataMap);
//		model.addAttribute("cusNo", cusNo);
//		model.addAttribute("finRptDate", finRptDate);
//		model.addAttribute("finRptType", finRptType);
//		model.addAttribute("relationCorpName", relationCorpName);
//		model.addAttribute("relationCorpNo", relationCorpNo);
//		model.addAttribute("teplateType", teplateType);
//		model.addAttribute("finRptSts", cusFinMain.getFinRptSts());
//		model.addAttribute("accRule", accRule);
//		model.addAttribute("uploadFlag", uploadFlag);
//		model.addAttribute("finRatioEditFlag", finRatioEditFlag);
//
//		model.addAttribute("query", query);
//		return "/component/pfs/CusFinReport_InsertOrUpdate_View";
//	}

	/**
	 * 打开财务报表查看页面 peng-财务改
	 *
	 * @author LJW date 2017-4-13
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 * @param relationCorpName
	 * @param relationCorpNo
	 * @param accRule
	 * @param finRptSts
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inputReportView")
	public String inputReportView(Model model, String ajaxData, String cusNo, String finRptDate, String finRptType,
								  String relationCorpName, String relationCorpNo, String accRule, String finRptSts,String caliber) throws Exception {
		ActionContext.initialize(request, response);
		MfCusReportAcount queryCusReportAcount = null;
		MfCusReportData queryCusReportData = null;
		List<MfCusReportData> itemBList = null;
		Map<String,Object> itemBMap = null;
		String teplateType = request.getParameter("teplateType");
		String uploadFlag = request.getParameter("uploadFlag");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusReportAcount mfCusReportAcount=new MfCusReportAcount();
		mfCusReportAcount.setCusNo(cusNo);
		mfCusReportAcount.setWeeks(finRptDate);
		mfCusReportAcount.setReportType(finRptType);
		mfCusReportAcount.setCorpNo(relationCorpNo);
		mfCusReportAcount.setCaliber(caliber);
		List<MfCusReportAcount> accountList=mfCusReportAcountFeign.getList(mfCusReportAcount);
		if(accountList!=null&&accountList.size()>0){
			mfCusReportAcount=accountList.get(0);
		}

		if("null".equals(relationCorpName)){
			relationCorpName = null;
		}
		// 名下企业
		if (StringUtil.isNotEmpty(relationCorpName)) {
			mfCusReportAcount.setCorpName(relationCorpName);
		}
		if (StringUtil.isNotEmpty(relationCorpNo)) {
			mfCusReportAcount.setCorpNo(relationCorpNo);
		}

		String query = "";
		String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
		if(!"2".equals(reportConfirmFlag)){
			if ("2".equals(mfCusReportAcount.getReportSts())) {
				query = "query";
				request.setAttribute("query", query);
			}
		}

		Map<String, String> dataTypeMap = new HashMap<String, String>();
		dataTypeMap.put("finRptDate", finRptDate);
		dataTypeMap.put("finRptType", finRptType);
//		// 资产负债表
		String finRptYear = finRptDate.substring(0, 4);
		MfCusReportData mfCusReportData=new MfCusReportData();
		List<MfCusReportData> capList=new ArrayList<>();
		if (StringUtil.isNotBlank(mfCusReportAcount.getAssetsDataId())) {
			mfCusReportData.setReportTypeId("001");
			mfCusReportData.setDataId(mfCusReportAcount.getAssetsDataId());
			capList = mfCusReportDataFeign.getList(mfCusReportData);

			//报表为已经确认过的报表（暂时不考虑年报和月报混合,即年报对年报，月报对月报）
			//如果当前是年报，获取去年的年报即为年初数
			//如果当前是月报，获取去年12月份即为年初数
			queryCusReportAcount = new  MfCusReportAcount();
			queryCusReportAcount.setCusNo(mfCusReportAcount.getCusNo());
			queryCusReportAcount.setReportType(mfCusReportAcount.getReportType());
			queryCusReportAcount.setIsBase(BizPubParm.YES_NO_Y);
			if("1".equals(finRptType)){//月报
				queryCusReportAcount.setWeeks(DateUtil.addYear(finRptYear+"1201", -1).substring(0,6));
			}else if("3".equals(finRptType)){//年报
				queryCusReportAcount.setWeeks(DateUtil.addYear(finRptYear+"1201", -1).substring(0,4));
			}else{

			}
			//有且只能有一条  否则报错！！！
			queryCusReportAcount = mfCusReportAcountFeign.getById(queryCusReportAcount);
			if(queryCusReportAcount != null && StringUtil.isNotEmpty(queryCusReportAcount.getAssetsDataId())){
				queryCusReportData = new MfCusReportData();
				queryCusReportData.setDataId(queryCusReportAcount.getAssetsDataId());
				itemBList = mfCusReportDataFeign.getList(queryCusReportData);
				if(itemBList != null && itemBList.size()>0){
					itemBMap = new HashMap<>();
					for(MfCusReportData tmp : itemBList){
						itemBMap.put(tmp.getReportItemId(), tmp.getItemAmtBaseA());
					}
					itemBList = null;
				}
			}

			for(MfCusReportData data: capList){
				if(itemBMap!= null && itemBMap.get(data.getReportItemId())!=null){
					data.setItemAmtBaseB((Double) itemBMap.get(data.getReportItemId()));
				}
				MfCusReportCalc mfCusReportCalc=new MfCusReportCalc();
				mfCusReportCalc.setReportItemId(data.getReportItemId());
				List<MfCusReportCalc> list=mfCusReportCalcFeign.getList(mfCusReportCalc);
				if(list!=null&&list.size()>0){
					dataMap.put(data.getReportItemId(), list);
				}
			}
		}

//		// 利润分配
		mfCusReportData=new MfCusReportData();
		List<MfCusReportData> proDataList=new ArrayList<>();
		if(StringUtil.isNotBlank(mfCusReportAcount.getIncomeDataId())){
			mfCusReportData.setReportTypeId("003");
			mfCusReportData.setDataId(mfCusReportAcount.getIncomeDataId());
			proDataList=mfCusReportDataFeign.getList(mfCusReportData);
			//（暂时不考虑年报和月报混合,即年报对年报，月报对月报）
			//如果当前是年报，获取去年的年报即为年初数
			//如果当前是月报，获取去年12月份即为年初数
			//报表为已经确认过的报表（暂时不考虑年报和月报混合,即年报对年报，月报对月报）
			//如果当前是年报，获取去年的年报即为年初数
			//如果当前是月报，获取去年12月份即为年初数


			if("1".equals(finRptType)){//月报  如果是年报就取本期  如果是月报 循环出本年该期报表前每个月份的相加
				itemBMap = new HashMap<>();
				int [] month = DateUtil.getMonthsAndDays(finRptYear+"0101",DateUtil.addMonth(finRptDate+"01", 1));
				for(int i=0;i<month[0];i++){
					queryCusReportAcount = new  MfCusReportAcount();
					queryCusReportAcount.setCusNo(mfCusReportAcount.getCusNo());
					queryCusReportAcount.setReportType(mfCusReportAcount.getReportType());
					queryCusReportAcount.setIsBase(BizPubParm.YES_NO_Y);
					queryCusReportAcount.setWeeks(DateUtil.addMonth(finRptYear+"0101", i).substring(0,6));
					//有且只能有一条  否则报错！！！
					queryCusReportAcount = mfCusReportAcountFeign.getById(queryCusReportAcount);
					if(queryCusReportAcount != null && StringUtil.isNotEmpty(queryCusReportAcount.getIncomeDataId())){
						queryCusReportData = new MfCusReportData();
						queryCusReportData.setDataId(queryCusReportAcount.getIncomeDataId());
						itemBList = mfCusReportDataFeign.getList(queryCusReportData);
						if(itemBList != null && itemBList.size()>0){
							for(MfCusReportData tmp : itemBList){
								itemBMap.put(tmp.getReportItemId(),
										MathExtend.add(itemBMap.get(tmp.getReportItemId())==null?
												0d:(Double)itemBMap.get(tmp.getReportItemId()), tmp.getItemAmtBaseA()));
							}
							itemBList = null;
						}
					}
				}

			}

			for(MfCusReportData data: proDataList){
				if("3".equals(finRptType)){
					data.setItemAmtBaseB(data.getItemAmtBaseA());
				}else if("1".equals(finRptType)){
					if(itemBMap!= null && itemBMap.get(data.getReportItemId())!=null){
						data.setItemAmtBaseB((Double) itemBMap.get(data.getReportItemId()));
					}
				}
				MfCusReportCalc mfCusReportCalc=new MfCusReportCalc();
				mfCusReportCalc.setReportItemId(data.getReportItemId());
				List<MfCusReportCalc> list=mfCusReportCalcFeign.getList(mfCusReportCalc);
				if(list!=null&&list.size()>0){
					dataMap.put(data.getReportItemId(), list);
				}
			}
		}

//		// 现金流量
		mfCusReportData=new MfCusReportData();
		List<MfCusReportData> cashList=new ArrayList<>();
		if(StringUtil.isNotBlank(mfCusReportAcount.getCashDataId())){
			mfCusReportData.setReportTypeId("002");
			mfCusReportData.setDataId(mfCusReportAcount.getCashDataId());
			cashList=mfCusReportDataFeign.getList(mfCusReportData);
			//（暂时不考虑年报和月报混合,即年报对年报，月报对月报）
			//如果当前是年报，获取去年的年报即为年初数
			//如果当前是月报，获取去年12月份即为年初数
			queryCusReportAcount = new  MfCusReportAcount();
			queryCusReportAcount.setCusNo(mfCusReportAcount.getCusNo());
			queryCusReportAcount.setReportType(mfCusReportAcount.getReportType());
			queryCusReportAcount.setIsBase(BizPubParm.YES_NO_Y);
			if("1".equals(finRptType)){//月报
				queryCusReportAcount.setWeeks(DateUtil.addYear(finRptYear+"1201", -1).substring(0,6));
			}else if("3".equals(finRptType)){//年报
				queryCusReportAcount.setWeeks(DateUtil.addYear(finRptYear+"1201", -1).substring(0,4));
			}else{

			}
			//有且只能有一条  否则报错！！！
			queryCusReportAcount = mfCusReportAcountFeign.getById(queryCusReportAcount);
			if(queryCusReportAcount != null && StringUtil.isNotEmpty(queryCusReportAcount.getCashDataId())){
				queryCusReportData = new MfCusReportData();
				queryCusReportData.setDataId(queryCusReportAcount.getCashDataId());
				itemBList = mfCusReportDataFeign.getList(queryCusReportData);
				if(itemBList != null && itemBList.size()>0){
					itemBMap = new HashMap<>();
					for(MfCusReportData tmp : itemBList){
						itemBMap.put(tmp.getReportItemId(), tmp.getItemAmtBaseA());
					}
					itemBList = null;
				}
			}

			for(MfCusReportData data: cashList){
				if(itemBMap!= null && itemBMap.get(data.getReportItemId())!=null){
					data.setItemAmtBaseB((Double) itemBMap.get(data.getReportItemId()));
				}
				MfCusReportCalc mfCusReportCalc=new MfCusReportCalc();
				mfCusReportCalc.setReportItemId(data.getReportItemId());
				List<MfCusReportCalc> list=mfCusReportCalcFeign.getList(mfCusReportCalc);
				if(list!=null&&list.size()>0){
					dataMap.put(data.getReportItemId(), list);
				}
			}
		}
		//	科目余额
//		mfCusReportData=new MfCusReportData();
//		List<MfCusReportData> subBalanceList=new ArrayList<>();
//		if(StringUtil.isNotBlank(mfCusReportAcount.getBalanceDataId())){
//			mfCusReportData.setReportTypeId("004");
//			mfCusReportData.setDataId(mfCusReportAcount.getBalanceDataId());
//		    subBalanceList=mfCusReportDataFeign.getList(mfCusReportData);
//		}

		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		cusFinMain.setFinRptDate(finRptDate);
		cusFinMain.setFinRptType(finRptType);
		if("null".equals(relationCorpName)){
			relationCorpName = null;
		}
		// 名下企业
		if (StringUtil.isNotEmpty(relationCorpName)) {
			cusFinMain.setRelationCorpName(relationCorpName);
		}
		if (StringUtil.isNotEmpty(relationCorpNo)) {
			cusFinMain.setRelationCorpNo(relationCorpNo);
		}
		if(cusFinMainFeign.getById(cusFinMain)!=null){
			cusFinMain = cusFinMainFeign.getById(cusFinMain);
			try{
//				cusFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_1));
//				cusFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_2));
//				cusFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_3));
				cusFinMain.setFinSubjectFlag(cusFinMainFeign.doCheckFinData(cusFinMain, BizPubParm.FIN_REPORT_TYPE_5));
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			cusFinMain.setFinSubjectFlag(false);
		}

		//科目余额表-使用原来的程序
		CusFinSubjectData cusFinSubjectData = new CusFinSubjectData();
		cusFinSubjectData.setCusNo(cusNo);
		cusFinSubjectData.setFinRptType(finRptType);
		cusFinSubjectData.setFinRptYear(finRptYear);
		cusFinSubjectData.setFinRptDate(finRptDate);
		Map<String,Object> resMap = cusFinSubjectDataFeign.getByCusNoDateTypeForList(cusFinSubjectData);
		Map<String,Object> cusFinSubjectDataList = (Map<String,Object>)resMap.get("cusFinSubjectDataListMap");
		List<CusFinSubjectData>  firstList = (List<CusFinSubjectData>)resMap.get("firstList");
		dataMap.put("cusFinSubjectDataListMap", cusFinSubjectDataList);
		dataMap.put("firstList", firstList);
		dataMap.put("cusFinMain", cusFinMain);

		dataMap.put("mfCusReportAcount", mfCusReportAcount);
		dataMap.put("capList", capList);
		dataMap.put("cashList", cashList);
		dataMap.put("proDataList", proDataList);
//		dataMap.put("cusFinSubjectDataListMap", subBalanceList);
		//财务指标
		List<CusFinRatioData> cusFinRatioDataList = null;
		if ("2".equals(mfCusReportAcount.getReportSts())) {//数据确认后才显示财务指标
//			cusFinRatioDataList = cusFinRatioDataFeign.getForFinIndicators(cusFinMain);
			Map<String,Object> cusFinRatioMap = cusFinRatioDataFeign.getCusFinRatioListByClassNoNew(mfCusReportAcount);
			List<ParmDic> classNoList =(List<ParmDic>)cusFinRatioMap.get("classNoList");
			Map<String,Object> cusFinRatioDataListMap = (Map<String,Object>)cusFinRatioMap.get("cusFinRatioDataMap");
			dataMap.put("classNoList", classNoList);
			dataMap.put("cusFinRatioDataListMap", cusFinRatioDataListMap);
		}
		dataMap = JSONObject.fromObject(dataMap);
		model.addAttribute("cusFinRatioDataList", cusFinRatioDataList);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("finRptDate", finRptDate);
		model.addAttribute("finRptType", finRptType);
		model.addAttribute("relationCorpName", relationCorpName);
		model.addAttribute("relationCorpNo", relationCorpNo);
		model.addAttribute("teplateType", teplateType);
		model.addAttribute("finRptSts", mfCusReportAcount.getReportSts());
		model.addAttribute("accRule", accRule);
		model.addAttribute("uploadFlag", uploadFlag);

		model.addAttribute("query", query);
		return "/component/pfs/CusFinReport_InsertOrUpdate_View";
	}


	@RequestMapping("/inputReportContrast")
	public String inputReportContrast(Model model, String accountIdArray) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		Map<String,Map<String,Object>> capMap = new LinkedHashMap<>();
		Map<String,Map<String,Object>> cashMap = new LinkedHashMap<>();
		Map<String,Map<String,Object>> incomeMap = new LinkedHashMap<>();
		Map<String,Object> parmMap = null;
		//用于存放各类报表的其中一期，用于循环遍历行数
		Map<String,List<MfCusReportData>> tmpMap = new HashMap<>();
		List<MfCusReportData> dataList = null;
		MfCusReportAcount queryCusReportAcount = null;
		MfCusReportData queryCusReportData = null;

		List<List<Object>> trList = null;
		List<Object> tdList = null;

		List<String> capTitleList = new ArrayList<>();
		List<String> cashTitleList = new ArrayList<>();;
		List<String> incomeTitleList = new ArrayList<>();;

		Map<String,String> sortMap = new TreeMap<>();

		boolean finCapFlag = false;
		boolean finProFlag = false;
		boolean finCashFlag = false;
		if(StringUtil.isNotEmpty(accountIdArray)){
			String [] array = accountIdArray.split(",");
			Map<String,Object> subMap = mfCusReportAcountFeign.getSubJectsMap(accountIdArray);
			Map<String,Object> indexMap = mfCusReportAcountFeign.getFincialIndexMap(accountIdArray);
			dataMap.putAll(subMap);
			dataMap.putAll(indexMap);
			//进行排序
			for(String accountId : array) {
				queryCusReportAcount = new MfCusReportAcount();
				queryCusReportAcount.setAccountId(accountId);
				queryCusReportAcount = mfCusReportAcountFeign.getById(queryCusReportAcount);
				if (queryCusReportAcount != null) {
					sortMap.put(queryCusReportAcount.getWeeks() + queryCusReportAcount.getCaliber(), queryCusReportAcount.getAccountId());
				}
			}

			if(sortMap != null && sortMap.size()>0){
				for(Map.Entry<String,String> entry : sortMap.entrySet()){
					queryCusReportAcount = new MfCusReportAcount();
					queryCusReportAcount.setAccountId(entry.getValue());
					queryCusReportAcount = mfCusReportAcountFeign.getById(queryCusReportAcount);
					if(StringUtil.isNotEmpty(queryCusReportAcount.getAssetsDataId())){
						finCapFlag = true;
						queryCusReportData = new MfCusReportData();
						queryCusReportData.setDataId(queryCusReportAcount.getAssetsDataId());
						dataList = mfCusReportDataFeign.getList(queryCusReportData);
						if(dataList != null && dataList.size()>0){
							parmMap = new HashMap<>();
							for(MfCusReportData data : dataList){
								parmMap.put(data.getReportItemId(), data.getItemAmtBaseA()==null?0d:data.getItemAmtBaseA());
							}
							capMap.put(queryCusReportAcount.getAccountId(), parmMap);
							tmpMap.put("capBase", dataList);
						}
						capTitleList.add(queryCusReportAcount.getWeeks()+"期"+("2".equals(queryCusReportAcount.getCaliber())?"管理口径":"对税口径"));
					}
					if(StringUtil.isNotEmpty(queryCusReportAcount.getCashDataId())){
						finProFlag = true;
						queryCusReportData = new MfCusReportData();
						queryCusReportData.setDataId(queryCusReportAcount.getCashDataId());
						dataList = mfCusReportDataFeign.getList(queryCusReportData);
						if(dataList != null && dataList.size()>0){
							parmMap = new HashMap<>();
							for(MfCusReportData data : dataList){
								parmMap.put(data.getReportItemId(), data.getItemAmtBaseA()==null?0d:data.getItemAmtBaseA());
							}
							cashMap.put(queryCusReportAcount.getAccountId(), parmMap);
							tmpMap.put("cashBase", dataList);
						}
						cashTitleList.add(queryCusReportAcount.getWeeks()+"期"+("2".equals(queryCusReportAcount.getCaliber())?"管理口径":"对税口径"));
					}
					if(StringUtil.isNotEmpty(queryCusReportAcount.getIncomeDataId())){
						finCashFlag = true;
						queryCusReportData = new MfCusReportData();
						queryCusReportData.setDataId(queryCusReportAcount.getIncomeDataId());
						dataList = mfCusReportDataFeign.getList(queryCusReportData);
						if(dataList != null && dataList.size()>0){
							parmMap = new HashMap<>();
							for(MfCusReportData data : dataList){
								parmMap.put(data.getReportItemId(), data.getItemAmtBaseA()==null?0d:data.getItemAmtBaseA());
							}
							incomeMap.put(queryCusReportAcount.getAccountId(), parmMap);
							tmpMap.put("incomeBase", dataList);
						}
						incomeTitleList.add(queryCusReportAcount.getWeeks()+"期"+("2".equals(queryCusReportAcount.getCaliber())?"管理口径":"对税口径"));
					}

				}
			}

			if(capMap != null){
				trList = new ArrayList<>();
				//需要循环的行数
				dataList = tmpMap.get("capBase");
				for(MfCusReportData bean : dataList){
					tdList = new ArrayList<>();
					//展示名称
					tdList.add(bean.getShowName()+"@"+bean.getShowStyle());
					for(Map.Entry<String,Map<String,Object>> entry: capMap.entrySet()){
						//添加各期的同一指标值
						tdList.add(entry.getValue().get(bean.getReportItemId()));
					}
					trList.add(tdList);
				}
				dataMap.put("capList", trList);
			}
			if(cashMap != null){
				trList = new ArrayList<>();
				//需要循环的行数
				dataList = tmpMap.get("cashBase");
				for(MfCusReportData bean : dataList){
					tdList = new ArrayList<>();
					//展示名称
					tdList.add(bean.getShowName()+"@"+bean.getShowStyle());
					for(Map.Entry<String,Map<String,Object>> entry: cashMap.entrySet()){
						//添加各期的同一指标值
						tdList.add(entry.getValue().get(bean.getReportItemId()));
					}
					trList.add(tdList);
				}
				dataMap.put("cashList", trList);
			}
			if(incomeMap != null){
				trList = new ArrayList<>();
				//需要循环的行数
				dataList = tmpMap.get("incomeBase");
				for(MfCusReportData bean : dataList){
					tdList = new ArrayList<>();
					//展示名称
					tdList.add(bean.getShowName()+"@"+bean.getShowStyle());
					for(Map.Entry<String,Map<String,Object>> entry: incomeMap.entrySet()){
						//添加各期的同一指标值
						tdList.add(entry.getValue().get(bean.getReportItemId()));
					}
					trList.add(tdList);
				}
				dataMap.put("proDataList", trList);
			}
		}

		dataMap = JSONObject.fromObject(dataMap);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		model.addAttribute("finCashFlag", finCashFlag);
		model.addAttribute("finProFlag", finProFlag);
		model.addAttribute("finCapFlag", finCapFlag);

		model.addAttribute("incomeTitleList", incomeTitleList);
		model.addAttribute("capTitleList", capTitleList);
		model.addAttribute("cashTitleList", cashTitleList);
		request.setAttribute("query", "");
		return "/component/pfs/CusFinReport_inputReportContrastView";
	}


	// 获取查看的页面值
	@RequestMapping(value = "/setCusFinModelValue")
	public void setCusFinModelValue(CusFinParm cusFinParm, Object object, Map<String, String> dataTypeMap) {
		if (object != null) {
			JSONObject jb = JSONObject.fromObject(object);
			String finRptType = dataTypeMap.get("finRptType");
			String finRptDate = dataTypeMap.get("finRptDate");
			// int cnt = jb.get("cnt");
			Double beginVal = 0.00;
			Double originalVal =0.00;
			Double endVal = 0.00;
			if ("1".endsWith(finRptType)) {// 月报
				String month = finRptDate.substring(4);
				if ("01".equals(month)) {// 一月
					beginVal = jb.getDouble("initAmtM1");
					endVal = jb.getDouble("endAmtM1");
					originalVal = jb.getDouble("originalAmtM1");
				} else if ("02".equals(month)) {// 二月
					beginVal = jb.getDouble("initAmtM2");
					endVal = jb.getDouble("endAmtM2");
					originalVal = jb.getDouble("originalAmtM2");
				} else if ("03".equals(month)) {// 三月
					beginVal = jb.getDouble("initAmtM3");
					endVal = jb.getDouble("endAmtM3");
					originalVal = jb.getDouble("originalAmtM3");
				} else if ("04".equals(month)) {// 四月
					beginVal = jb.getDouble("initAmtM4");
					endVal = jb.getDouble("endAmtM4");
					originalVal = jb.getDouble("originalAmtM4");
				} else if ("05".equals(month)) {// 五月
					beginVal = jb.getDouble("initAmtM5");
					endVal = jb.getDouble("endAmtM5");
					originalVal = jb.getDouble("originalAmtM5");
				} else if ("06".equals(month)) {// 六月
					beginVal = jb.getDouble("initAmtM6");
					endVal = jb.getDouble("endAmtM6");
					originalVal = jb.getDouble("originalAmtM6");
				} else if ("07".equals(month)) {// 七月
					beginVal = jb.getDouble("initAmtM7");
					endVal = jb.getDouble("endAmtM7");
					originalVal = jb.getDouble("originalAmtM7");
				} else if ("08".equals(month)) {// 八月
					beginVal = jb.getDouble("initAmtM8");
					endVal = jb.getDouble("endAmtM8");
					originalVal = jb.getDouble("originalAmtM8");
				} else if ("09".equals(month)) {// 九月
					beginVal = jb.getDouble("initAmtM9");
					endVal = jb.getDouble("endAmtM9");
					originalVal = jb.getDouble("originalAmtM9");
				} else if ("10".equals(month)) {// 十月
					beginVal = jb.getDouble("initAmtM10");
					endVal = jb.getDouble("endAmtM10");
					originalVal = jb.getDouble("originalAmtM10");
				} else if ("11".equals(month)) {// 十一月
					beginVal = jb.getDouble("initAmtM11");
					endVal = jb.getDouble("endAmtM11");
					originalVal = jb.getDouble("originalAmtM11");
				} else if ("12".equals(month)) {// 十二月
					beginVal = jb.getDouble("initAmtM12");
					endVal = jb.getDouble("endAmtM12");
					originalVal = jb.getDouble("originalAmtM12");
				}else {
				}
			} else if ("2".endsWith(finRptType)) {// 季报
				String quarterly = finRptDate.substring(4);
				if ("01".equals(quarterly)) {// 第一季度
					beginVal = jb.getDouble("initAmtQ1");
					endVal = jb.getDouble("endAmtQ1");
					originalVal = jb.getDouble("originalAmtQ1");
				} else if ("02".equals(quarterly)) {// 第二季度
					beginVal = jb.getDouble("initAmtQ2");
					endVal = jb.getDouble("endAmtQ2");
					originalVal = jb.getDouble("originalAmtQ2");
				} else if ("03".equals(quarterly)) {// 第三季度
					beginVal = jb.getDouble("initAmtQ3");
					endVal = jb.getDouble("endAmtQ3");
					originalVal = jb.getDouble("originalAmtQ3");
				} else if ("04".equals(quarterly)) {// 第四季度
					beginVal = jb.getDouble("initAmtQ4");
					endVal = jb.getDouble("endAmtQ4");
					originalVal = jb.getDouble("originalAmtQ4");
				}else {
				}
			} else if ("3".endsWith(finRptType)) {// 年报
				beginVal = jb.getDouble("initAmtY");
				endVal = jb.getDouble("endAmtY");
				originalVal = jb.getDouble("originalAmtY");
			} else if ("4".endsWith(finRptType)) {// 半年报
				String halfYear = finRptDate.substring(4);
				if ("01".equals(halfYear)) {// 上半年
					beginVal = jb.getDouble("initAmtY1");
					endVal = jb.getDouble("endAmtY1");
					originalVal = jb.getDouble("originalAmtY1");
				} else if ("02".equals(halfYear)) {// 下半年
					beginVal = jb.getDouble("initAmtY2");
					endVal = jb.getDouble("endAmtY2");
					originalVal = jb.getDouble("originalAmtY2");
				}else {
				}
			}else {
			}
			DecimalFormat df = new DecimalFormat("0.00");
			cusFinParm.setBeginVal(df.format(beginVal));
			// cusFinModel.setCnt(cnt);
			cusFinParm.setEndVal(df.format(endVal));
			cusFinParm.setOriginalVal(df.format(originalVal));
		}
	}

//	/**
//	 * @author czk
//	 * @param cusNo
//	 * @param finRptDate
//	 * @param finRptType
//	 * @Description: 检查报表数据是否录入 date 2016-12-13
//	 */
//	@RequestMapping(value = "/checkFinDataAjax")
//	@ResponseBody
//	public Map<String, Object> checkFinDataAjax(String ajaxData, String cusNo, String finRptDate, String finRptType)
//			throws Exception {
//		ActionContext.initialize(request, response);
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		CusFinMain cusFinMain = new CusFinMain();
//		try {
//			cusFinMain.setCusNo(cusNo);
//			String accRule = "1";
//			cusFinMain.setAccRule(accRule);
//			cusFinMain.setFinRptDate(finRptDate);
//			cusFinMain.setFinRptType(finRptType);
//			if (cusFinMainFeign.doCheckFinData(cusFinMain, "4")) {
//				dataMap.put("flag", "success");
//				dataMap.put("checkFlag", "success");
//			} else {
//				dataMap.put("flag", "success");
//				dataMap.put("checkFlag", "error");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			dataMap.put("flag", "error");
//			dataMap.put("msg", e.getMessage());
//			throw e;
//		}
//		return dataMap;
//	}

	/***************
	 * 查询个人名下企业信息
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPersonCorpList")
	@ResponseBody
	public Map<String, Object> getPersonCorpList( String cusNo ) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusPersonCorp mfCusPersonCorp=new  MfCusPersonCorp();
		mfCusPersonCorp.setCusNo(cusNo);
		mfCusPersonCorp.setDelFlag("0");
		List<MfCusPersonCorp> list=mfCusPersonCorpFeign.getPersonCorpList(mfCusPersonCorp);
		dataMap.put("corpLen",list==null?0:list.size());
		return  dataMap;
	}

	/**
	 * @author czk
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 * @Description: 检查报表数据是否录入 date 2016-12-13
	 */
	@RequestMapping(value = "/checkFinDataAjax")
	@ResponseBody
	public Map<String, Object> checkFinDataAjax(String ajaxData, String cusNo, String finRptDate, String finRptType,String subjectDataFlag,String relationCorpNo,String accountId )
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusReportAcount mfCusReportAcount=new MfCusReportAcount();
		try {
			mfCusReportAcount.setCusNo(cusNo);
			String accRule = "1";
			mfCusReportAcount.setAccRule(accRule);
			mfCusReportAcount.setWeeks(finRptDate);
			mfCusReportAcount.setReportType(finRptType);
			mfCusReportAcount.setAccountId(accountId);
			if(StringUtil.isNotBlank(relationCorpNo)){
				mfCusReportAcount.setCorpNo(relationCorpNo);
			}
			List<MfCusReportAcount> list=mfCusReportAcountFeign.getList(mfCusReportAcount);
			if(list==null||list.size()==0){
				dataMap.put("flag", "success");
				dataMap.put("checkFlag", "error");
				dataMap.put("msg", "数据存在问题,请联系管理员!");
			}else{
				mfCusReportAcount=list.get(0);
				String msg="";
				if(mfCusReportAcount.getAssetsDataId()==null){
					msg+=" 资产负债表 ";
				}
				if(mfCusReportAcount.getCashDataId()==null){
					msg+=" 现金流量表 ";
				}
				if(mfCusReportAcount.getIncomeDataId()==null){
					msg+=" 利润分配表 ";
				}
				if(mfCusReportAcount.getBalanceDataId()==null&&"true".equals(subjectDataFlag)){
					msg+=" 科目余额表 ";
				}
				if("".equals(msg)){
					dataMap.put("flag", "success");
					dataMap.put("checkFlag", "success");
				}else{
					dataMap.put("flag", "success");
					dataMap.put("checkFlag", "error");
					dataMap.put("msg", msg+"数据未录入,是否继续确认?");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

//	/**
//	 * 数据确认
//	 *
//	 * @param cusNo
//	 * @param cusName
//	 * @param finRptDate
//	 * @param finRptType
//	 * @param relationCorpName
//	 * @param relationCorpNo
//	 *
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/updateReportConfirmAjax")
//	@ResponseBody
//	public Map<String, Object> updateReportConfirmAjax(String ajaxData, String cusNo, String cusName, String finRptDate,
//			String finRptType, String relationCorpName, String relationCorpNo) throws Exception {
//		ActionContext.initialize(request, response);
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		CusFinMain cusFinMain = new CusFinMain();
//		try {
//			cusFinMain.setCusNo(cusNo);
//			cusFinMain.setCusName(cusName);
//			String accRule = "1";
//			cusFinMain.setAccRule(accRule);
//			cusFinMain.setFinRptDate(finRptDate);
//			cusFinMain.setFinRptType(finRptType);
//			// 名下企业
//			if (StringUtil.isNotEmpty(relationCorpName)) {
//				cusFinMain.setRelationCorpName(relationCorpName);
//			}
//			if (StringUtil.isNotEmpty(relationCorpNo)) {
//				cusFinMain.setRelationCorpNo(relationCorpNo);
//		}
//			cusFinMain = cusFinMainFeign.getById(cusFinMain);
//			dataMap  = cusFinMainFeign.updateReportConfirm(cusFinMain);
//		} catch (Exception e) {
//			e.printStackTrace();
//			dataMap.put("flag", "error");
//			dataMap.put("msg", e.getMessage());
//
//		}
//		return dataMap;
//	}

	/**
	 * 数据确认
	 *
	 * @param cusNo
	 * @param cusName
	 * @param finRptDate
	 * @param finRptType
	 * @param relationCorpName
	 * @param relationCorpNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateReportConfirmAjax")
	@ResponseBody
	public Map<String, Object> updateReportConfirmAjax(String ajaxData, String cusNo, String cusName, String finRptDate,
													   String finRptType, String relationCorpName, String relationCorpNo,String accountId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusReportAcount cusFinMain=new MfCusReportAcount();
		try {
			cusFinMain.setCusNo(cusNo);
			cusFinMain.setCusName(cusName);
			String accRule = "1";
			cusFinMain.setAccRule(accRule);
			cusFinMain.setWeeks(finRptDate);
			cusFinMain.setReportType(finRptType);
			cusFinMain.setAccountId(accountId);
			// 名下企业
			if (StringUtil.isNotEmpty(relationCorpName)) {
				cusFinMain.setCorpName(relationCorpName);
			}
			if (StringUtil.isNotEmpty(relationCorpNo)) {
				cusFinMain.setCorpNo(relationCorpNo);
			}
			List<MfCusReportAcount> list=mfCusReportAcountFeign.getList(cusFinMain);
			if(list!=null&&list.size()>0){
				cusFinMain=list.get(0);
				cusFinMain.setReportSts("2");  //将报表更新为确认  暂时只修改一个表  20190517-peng ???
				Map<String,Object> map=mfCusReportAcountFeign.updateReportConfirm(cusFinMain);
				if(map.get("flag")!=null&&"error".equals(String.valueOf(map.get("flag")))){
					dataMap.put("flag", "error");
					dataMap.put("msg", map.get("msg")==null?MessageEnum.FAILED_OPERATION.getMessage("数据确认"):String.valueOf(map.get("msg")));
				}else{
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("数据确认"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinMain cusFinMain = new CusFinMain();
		try {
			cusFinMain.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinMain.setCriteriaList(cusFinMain, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinMain,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = cusFinMainFeign.findByPage(ipage, cusFinMain);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	 *
	 * @param ifcover
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String ifcover) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0001 = formService.getFormData("pfs0001");
			getFormValue(formpfs0001, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0001)) {
				CusFinMain cusFinMain = new CusFinMain();
				setObjValue(formpfs0001, cusFinMain);
				if ("1".equals(ifcover)) {// 覆盖时，先删除，再插入
					cusFinMainFeign.delete(cusFinMain);
					cusFinMain.setFinRptSts("0");
					cusFinMain.setAccRule("1");
					cusFinMainFeign.insert(cusFinMain);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("覆盖"));
				} else {
					// 先判断该报表是否已经存在
					CusFinMain cusFinMain1 = new CusFinMain();
					cusFinMain1.setCusNo(cusFinMain.getCusNo());
					cusFinMain1.setFinRptDate(cusFinMain.getFinRptDate());
					cusFinMain1.setFinRptType(cusFinMain.getFinRptType());
					cusFinMain1 = cusFinMainFeign.getById(cusFinMain1);
					if (cusFinMain1 == null) {// 说明不存在
						cusFinMain.setFinRptSts("0"); // 报表状态1:覆盖 0:新增
						cusFinMain.setAccRule("1");
						cusFinMainFeign.insert(cusFinMain);
						dataMap.put("flag", "success");
						dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
					} else {
						dataMap.put("flag", "exist");
						dataMap.put("msg", "该报表已存在，是否选择覆盖？");

					}
				}
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

	/**
	 * AJAX更新保存
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0001 = formService.getFormData("pfs0001");
			getFormValue(formpfs0001, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0001)) {
				CusFinMain cusFinMain = new CusFinMain();
				setObjValue(formpfs0001, cusFinMain);
				cusFinMain.setAccRule("1");
				cusFinMainFeign.update(cusFinMain);
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
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String cusNo, String finRptDate, String finRptType)
			throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0001 = formService.getFormData("pfs0001");
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		cusFinMain.setFinRptDate(finRptDate);
		cusFinMain.setFinRptType(finRptType);
		cusFinMain = cusFinMainFeign.getById(cusFinMain);
		getObjValue(formpfs0001, cusFinMain, formData);
		if (cusFinMain != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}


	/**
	 * Ajax异步删除  peng
	 *
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 * @param relationCorpName
	 * @param relationCorpNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String cusNo, String finRptDate, String finRptType,
										  String relationCorpName, String relationCorpNo,String accountId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusReportAcount mfCusReportAcount = new MfCusReportAcount();
		mfCusReportAcount.setCusNo(cusNo);
		mfCusReportAcount.setWeeks(finRptDate);
		mfCusReportAcount.setReportType(finRptType);
		mfCusReportAcount.setAccountId(accountId);
		// 名下企业
		if (StringUtil.isNotEmpty(relationCorpName)) {
			mfCusReportAcount.setCorpName(relationCorpName);
		}
		if (StringUtil.isNotEmpty(relationCorpNo)) {
			mfCusReportAcount.setCorpNo(relationCorpNo);
		}

		try {
			List<MfCusReportAcount> list = mfCusReportAcountFeign.getList(mfCusReportAcount);
			if (list != null && list.size() == 1) {
				mfCusReportAcount = list.get(0);  //需要删除关联表信息-peng
				//判断是否被引用
				AppEval appEval=new AppEval();
				appEval.setCusNo(mfCusReportAcount.getCusNo());
				appEval.setRptDate(mfCusReportAcount.getWeeks());
				appEval.setRptType(mfCusReportAcount.getReportType());
				List appEvalList=appEvalFeign.getListByCusNoAndRptDateAndRptType(appEval);
				if(appEvalList!=null&&appEvalList.size()>0){
					dataMap.put("flag", "error");
					dataMap.put("msg", "财报已被使用不能删除!");
				}else{
					mfCusReportAcountFeign.delete(mfCusReportAcount);
					//处理科目余额数据
					CusFinMain cusFinMain = new CusFinMain();
					cusFinMain.setCusNo(cusNo);
					cusFinMain.setFinRptDate(finRptDate);
					cusFinMain.setFinRptType(finRptType);
					if (StringUtil.isNotEmpty(relationCorpName)) {
						cusFinMain.setRelationCorpName(relationCorpName);
					}
					if (StringUtil.isNotEmpty(relationCorpNo)) {
						cusFinMain.setRelationCorpNo(relationCorpNo);
					}
					cusFinMainFeign.delete(cusFinMain);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "数据问题,请联系管理员!");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	private boolean maxMinFinRptDate(CusFinMain cusFinMain) throws Exception {
		boolean boo = false;
		Map<String, String> maxMinFinRptDate = cusFinMainFeign.maxMinFinRptDate(cusFinMain);
		int maxFinRptDate = Integer.valueOf(maxMinFinRptDate.get("maxFinRptDate"));
		int minFinRptDate = Integer.valueOf(maxMinFinRptDate.get("minFinRptDate"));
		int finRptDate = Integer.valueOf(cusFinMain.getFinRptDate());
		if (finRptDate == maxFinRptDate || minFinRptDate == finRptDate) {
			boo = true;
		}
		return boo;
	}

	/**
	 * 保存上传的数据
	 *
	 * @author LJW date 2017-4-13
	 * @param allPath
	 */
	@RequestMapping(value = "/insertFormMoreExcelAjax")
	@ResponseBody
	public Map<String, Object> insertFormMoreExcelAjax(String ajaxData, Object allPath) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
			allPath = uploadFinPath + File.separator + allPath;
			// cusFinMainFeign.insertMoreExcel(cusNo, cusName,
			// allPath,finRptType,finRptDate);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (ServiceException e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "操作失败！文件格式错误!");
			e.printStackTrace();
		}
		return dataMap;
	}

	/**
	 * 获取财务报表信息  财报数据已经加载是否需要再次加载-peng
	 *
	 * @author LJW date 2017-4-19
	 * @param cusNo
	 */
//	@RequestMapping(value = "/queryCusFinDataAjax")
//	@ResponseBody
//	public Map<String, Object> queryCusFinDataAjax(String ajaxData, String cusNo) throws Exception {
//		ActionContext.initialize(request, response);
//		Map<String, Object> dataMap = new HashMap<String, Object>();
//		try {
//			CusFinMain cusFinMain = new CusFinMain();
//			cusFinMain.setCusNo(cusNo);
//			List<CusFinMain> cusFinMainList = cusFinMainFeign.getAll(cusFinMain);
//			if (cusFinMainList != null && !cusFinMainList.isEmpty()) {
//				for (CusFinMain cFinMain : cusFinMainList) { // 检查财务报表具体报表是否填写
//					cFinMain.setFinCapFlag(cusFinMainFeign.doCheckFinData(cFinMain, "1"));
//					cFinMain.setFinProFlag(cusFinMainFeign.doCheckFinData(cFinMain, "2"));
//					cFinMain.setFinCashFlag(cusFinMainFeign.doCheckFinData(cFinMain, "3"));
//					cFinMain.setFinSubjectFlag(cusFinMainFeign.doCheckFinData(cFinMain, "5"));
//				}
//			}
//			dataMap.put("flag", "success");
//			dataMap.put("cusFinMainList", cusFinMainList);
//			String reportConfirmFlag =  new CodeUtils().getSingleValByKey("REPORT_CONFIRM_FLAG");
//			dataMap.put("reportConfirmFlag", reportConfirmFlag);
//		} catch (Exception e) {
//			dataMap.put("flag", "error");
//			dataMap.put("msg", e.getMessage());
//		}
//		return dataMap;
//	}

	@RequestMapping(value = "/queryCusFinDataAjax")
	@ResponseBody
	public Map<String, Object> queryCusFinDataAjax(String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusReportAcount cusFinMain = new MfCusReportAcount();
			cusFinMain.setCusNo(cusNo);
			List<MfCusReportAcount>  list=setReportIsUsed(mfCusReportAcountFeign.getList(cusFinMain));

			MfCusCustomer cus=new MfCusCustomer();
			cus.setCusNo(cusNo);
			cus=mfCusCustomerFeign.getById(cus);
			dataMap.put("cusType",cus.getCusBaseType());
			dataMap.put("flag", "success");
			dataMap.put("cusFinMainList", list);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	// 下载模板
	@RequestMapping(value = "/exportExcelAjax")
	@ResponseBody
	public Map<String, Object> exportExcelAjax(String downType, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 1:资产负债表模板 2:利润分配表模板 3:现金流量表模板 4:资产负债表、利润分配表和现金流量表的总模板 subjectBal-科目余额表
			Map<String, Object> resultMap = cusFinMainFeign.doExportExcelModel(downType, cusNo);
			String path = resultMap.get("path").toString();
			CusFinMain cusFinMain = (CusFinMain)JsonStrHandling.handlingStrToBean(resultMap.get("cusFinMain"), CusFinMain.class);
			PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
			poiExportExcelUtil.initFileModel(path, cusFinMain);
			dataMap.put("flag", "success");
			dataMap.put("exportFlag", "success");
			dataMap.put("path", path);
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("导出"));
			if (path == null) {
				dataMap.put("exportFlag", "error");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("模板导出路径"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 打包下载报表自动化处理结果文件（众微）
	 * @param period
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadZipAjax")
	public ResponseEntity<byte[]> downloadZipAjax(String period, String cusNo) throws Exception {
		ZipTool zt = new ZipTool();
		MfCusReportErrorInfo mfCusReportErrorInfo = new MfCusReportErrorInfo();
		mfCusReportErrorInfo.setCusNo(cusNo);
		mfCusReportErrorInfo.setPeriod(period);
		mfCusReportErrorInfo = mfCusReportErrorInfoFeign.getByErrorInfo(mfCusReportErrorInfo);
		if(mfCusReportErrorInfo.getFileAddr() != null && !"".equals(mfCusReportErrorInfo.getFileAddr())){
			String[] fileAddrs = mfCusReportErrorInfo.getFileAddr().split(",");
			for(int i =0;i<fileAddrs.length;i++){
				zt.addFile(fileAddrs[i]);
			}
		}
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String fileName = mfCusCustomer.getCusName()+"_"+ WaterIdUtil.getWaterId()+".zip";
		fileName = new String(fileName.getBytes(),"ISO8859-1");
		InputStream inputStream = new ByteArrayInputStream(zt.getOutputStream());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attchement;filename="+fileName);
		HttpStatus statusCode = HttpStatus.OK;
		ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
	}

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		byte[] buffer=new byte[1024*4];
		int n=0;
		while ( (n=in.read(buffer)) !=-1) {
			out.write(buffer,0,n);
		}
		return out.toByteArray();
	}

	// 下载个人名下企业财务模板
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportPersonExcelAjax")
	@ResponseBody
	public Map<String, Object> exportPersonExcelAjax(String downType, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 1:资产负债表模板 2:利润分配表模板 3:现金流量表模板 4:资产负债表、利润分配表和现金流量表的总模板
			Map<String, Object> resultMap = cusFinMainFeign.doPersonExportExcelModel(downType, cusNo);
			String path = resultMap.get("path").toString();
			CusFinMain cusFinMain = (CusFinMain)JsonStrHandling.handlingStrToBean(resultMap.get("cusFinMain"), CusFinMain.class);
			List<Map<String, String>>  corpList = (List<Map<String, String>>)resultMap.get("corpList");
			PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
			poiExportExcelUtil.initPersonFileModel(path, cusFinMain,corpList);
			dataMap.put("flag", "success");
			dataMap.put("exportFlag", "success");
			dataMap.put("path", path);
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("导出"));
			if (path == null) {
				dataMap.put("exportFlag", "error");
				dataMap.put("msg", MessageEnum.NO_FILE.getMessage("模板导出路径"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}










	/**
	 * 新增页面
	 * @param cusNo
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		Object obj = cusInterfaceFeign.getCusByCusNo(cusNo);
		JSONObject jb = JSONObject.fromObject(obj);
		String cusName = jb.getString("cusName");
		cusFinMain.setCusName(cusName);
		String yearThis = DateUtil.getDate().substring(0, 4); // 当前年份
		getObjValue(formpfs0001, cusFinMain);
		model.addAttribute("formpfs0001", formpfs0001);
		model.addAttribute("yearThis", yearThis);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinMain_Insert";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		getFormValue(formpfs0001);
		CusFinMain cusFinMain = new CusFinMain();
		setObjValue(formpfs0001, cusFinMain);
		cusFinMainFeign.insert(cusFinMain);
		getObjValue(formpfs0001, cusFinMain);
		this.addActionMessage(model, "保存成功");
		List<CusFinMain> cusFinMainList = (List<CusFinMain>) cusFinMainFeign.findByPage(this.getIpage(), cusFinMain).getResult();
		model.addAttribute("formpfs0001", formpfs0001);
		model.addAttribute("cusFinMainList", cusFinMainList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinMain_Detail";
	}

	/**
	 * 查询
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 * @param query
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String cusNo, String finRptDate, String finRptType, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		getFormValue(formpfs0001);
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		cusFinMain.setFinRptDate(finRptDate);
		cusFinMain.setFinRptType(finRptType);
		cusFinMain = cusFinMainFeign.getById(cusFinMain);
		if ("1".equals(cusFinMain.getFinRptSts())) {
			query = "query";
		}
		getObjValue(formpfs0001, cusFinMain);
		this.changeFormProperty(formpfs0001, "finRptType", "disabled", "1");
		this.changeFormProperty(formpfs0001, "accRule", "disabled", "1");
		this.changeFormProperty(formpfs0001, "finRptDate", "readonly", "1");
		model.addAttribute("formpfs0001", formpfs0001);
		model.addAttribute("query", query);
		return "/component/pfs/CusFinMain_Detail";
	}

	/**
	 * 删除
	 * @param cusNo
	 * @param finRptDate
	 * @param finRptType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String cusNo, String finRptDate, String finRptType) throws Exception {
		ActionContext.initialize(request, response);
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		cusFinMain.setFinRptDate(finRptDate);
		cusFinMain.setFinRptType(finRptType);
		cusFinMainFeign.delete(cusFinMain);
		return getListPage(model, cusNo,null,null);
	}

	/**
	 * 新增校验
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		getFormValue(formpfs0001);
		boolean validateFlag = this.validateFormData(formpfs0001);
	}

	/**
	 * 修改校验
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0001 = formService.getFormData("pfs0001");
		getFormValue(formpfs0001);
		boolean validateFlag = this.validateFormData(formpfs0001);
	}

	/**

	 *@描述 检查是否存在已数据确认的报表数据

	 *@参数

	 *@返回值

	 *@创建人  shenhaobing

	 *@创建时间  2019/1/16

	 *@修改人和其它信息

	 */
	@RequestMapping(value = "/checkFinDataForConfirmAjax")
	@ResponseBody
	public Map<String, Object> checkFinDataForConfirmAjax(String ajaxData, String cusNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinMain cusFinMain = new CusFinMain();
		try {
			cusFinMain.setCusNo(cusNo);
			String accRule = "1";
			cusFinMain.setAccRule(accRule);
			cusFinMain.setFinRptSts(BizPubParm.YES_NO_Y);
			List<CusFinMain> cusFinMainList = cusFinMainFeign.getCusFinMainList(cusFinMain);
			if (cusFinMainList!=null&&cusFinMainList.size()>0) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 多期对比功能
	 *
	 * @param model
	 * @param ajaxData
	 * @param cusNo
	 * @param accRule
	 * @param finRptSts
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/multiPeriodComparisonView")
	public String multiPeriodComparisonView(Model model, String ajaxData, String cusNo, String accRule,
											String finRptSts) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinMain cusFinMain = new CusFinMain();
		cusFinMain.setCusNo(cusNo);
		dataMap = cusFinMainFeign.multiPeriodComparisonView(cusFinMain);
		String query = "query";
		dataMap = JSONObject.fromObject(dataMap);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("query", query);
		return "/component/pfs/multiPeriodComparison_View";
	}

}
