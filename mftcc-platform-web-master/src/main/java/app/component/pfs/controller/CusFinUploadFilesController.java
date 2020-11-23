package app.component.pfs.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.cus.report.entity.MfCusReportAcount;
import app.component.cus.report.entity.MfCusReportCheck;
import app.component.cus.report.entity.MfCusReportData;
import app.component.cus.report.feign.MfCusReportAcountFeign;
import app.component.cus.report.feign.MfCusReportCheckFeign;
import app.util.JsonStrHandling;
import cn.mftcc.util.ScriptEngineUtil;
import cn.mftcc.util.WaterIdUtil;
import org.apache.commons.io.FileUtils;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReadStatus;
import org.jxls.reader.XLSReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;

import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.pfs.entity.AssetsReport;
import app.component.pfs.entity.CashflowsReport;
import app.component.pfs.entity.CifFinFile;
import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.ProfitsReport;
import app.component.pfs.feign.CusFinMainFeign;
import app.component.pfs.feign.CusFinParmFeign;
import app.component.pfs.util.PoiExportExcelUtil;
import app.component.pfs.util.PoiReadExcelUtil;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;


/**
 * <p>Title:CusFinUploadFilesAction.java</p>
 * <p>Description:财务报表上传控制</p>
 * @author LJW
 * @date 2017-4-11 下午4:12:39
 */
@Controller
@RequestMapping("/cusFinUploadFiles")
public class CusFinUploadFilesController extends BaseFormBean{

	private List<CifFinFile> cifFinFileList = new ArrayList<CifFinFile>();

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusFinParmFeign cusFinParmFeign;   //财务指标业务控制
	@Autowired
	private CusFinMainFeign cusFinMainFeign;   //财务报表主表
	@Autowired
	private MfCusReportAcountFeign mfCusReportAcountFeign;   //新版财务报表主表
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;   //客户对外接口
	@Autowired
	private MfCusReportCheckFeign mfCusReportCheckFeign;

	@RequestMapping(value = "/upload")
	public String upload(Model model, String ajaxData, String info, List<String> fileFileName, List<File> file ) throws Exception{
		String[] pathInfo = info.split("@");
		String serverPath = "upload/ciffinreportdata";
		String allPath = null;
		int int_temp = 0;
		int list_index = 0;
		CifFinFile cifFinFile = new CifFinFile();
		for(String str : pathInfo){
			boolean flag = true;
			int_temp++;
			if("Y".equals(str)){
				String temp = request.getServletContext().getRealPath(serverPath);
				allPath = temp + File.separator + UUID.randomUUID().toString() + fileFileName.get(list_index).substring(fileFileName.get(list_index).lastIndexOf("."));
				File newFile = new File(allPath);
				try {
					FileUtils.copyFile(file.get(list_index), newFile);
				} catch (Exception e) {
					flag = false;
				} finally {
					if (flag) {
						list_index++;
						switch (int_temp) {
							case 1:
								cifFinFile.setFatherFile(allPath);
								cifFinFile.setPath(allPath);
								break;
							case 2:
								cifFinFile.setCapFile(allPath);
								cifFinFile.setPath(allPath);
								break;
							case 3:
								cifFinFile.setCashFile(allPath);
								cifFinFile.setPath(allPath);
								break;
							case 4:
								cifFinFile.setPayFile(allPath);
								cifFinFile.setPath(allPath);
								cifFinFile.setAllPath(cifFinFile.getPath());
								cifFinFileList.add(cifFinFile);
								cifFinFile = new CifFinFile();
								int_temp = 0;
								break;
						}
					} else {
						list_index++;
						switch (int_temp) {
							case 1:
								cifFinFile.setFatherFile(null);
								cifFinFile.setPath("n");
								break;
							case 2:
								cifFinFile.setCapFile(null);
								cifFinFile.setPath("n");
								break;
							case 3:
								cifFinFile.setCashFile(null);
								cifFinFile.setPath("n");
								break;
							case 4:
								cifFinFile.setPayFile(null);
								cifFinFile.setPath("n");
								cifFinFile.setAllPath(cifFinFile.getPath());
								cifFinFileList.add(cifFinFile);
								cifFinFile = new CifFinFile();
								int_temp = 0;
								break;
						}
					}
					flag = true;
				}
			} else {
				switch (int_temp) {
					case 1:
						cifFinFile.setPath("n");
						break;
					case 2:
						cifFinFile.setPath("n");
						break;
					case 3:
						cifFinFile.setPath("n");
						break;
					case 4:
						cifFinFile.setPath("n");
						cifFinFile.setAllPath(cifFinFile.getPath());
						cifFinFileList.add(cifFinFile);
						cifFinFile = new CifFinFile();
						int_temp = 0;
						break;
				}
			}
		}
		model.addAttribute("cifFinFile", cifFinFile);
		return "/component/pfs/CusFinUploadFiles_result";
	}

	//财务报表上传控制
	@RequestMapping(value = "/uploadAjax")
	@ResponseBody
	public Map<String, Object> uploadAjax(String ajaxData, String cusFinUploadFileName,@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		String cusNo = request.getParameter("cusNo");
		boolean flag = true;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		cusFinUploadFileName=cusFinUpload.getOriginalFilename();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if(cusFinUploadFileName.endsWith(".exe")||cusFinUploadFileName.endsWith(".jsp")||cusFinUploadFileName.endsWith(".bat")
				||cusFinUploadFileName.endsWith(".dll")||cusFinUploadFileName.endsWith(".com")||cusFinUploadFileName.endsWith(".sh")||cusFinUploadFileName.endsWith(".py")){
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
			return dataMap;
		}
		String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
		String newFileName = cusNo + UUID.randomUUID().toString() + cusFinUploadFileName.substring(cusFinUploadFileName.lastIndexOf("."));
		String allPath = uploadFinPath + File.separator + newFileName;
		File f = new File(allPath);
		cusFinUpload.transferTo(f);
		//根据文件名称判断是哪个类型的报表模板
		StringBuilder resMsg = new StringBuilder();
		CusFinMain cusFinMain = new CusFinMain();
		//获取每一个模板第一行第一列的值，标识是哪类模板
		PoiReadExcelUtil poiExcelUtil = new PoiReadExcelUtil(f.getPath(), 0);
		String teplateType = poiExcelUtil.getValue(0, 0);
		try {
			PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
			String infIntegrity;
			if(!"5".equals(teplateType)) {//如果报表类型是资产负债表模板、利润分配表模板、现金流量表模板 或者这三者的全部模板
				String xmlFile = poiExportExcelUtil.getFileModel(teplateType, "xml");
				String[] msg = new String[4];
				AssetsReport assetsReport = new AssetsReport();
				ProfitsReport profitsReport = new ProfitsReport();
				CashflowsReport cashflowsReport = new CashflowsReport();

				Map<String, Object> assetsReportMap = new HashMap<String, Object>();
				Map<String, Object> profitsReportMap = new HashMap<String, Object>();
				Map<String, Object> cashflowsReportMap = new HashMap<String, Object>();
				Map<String, Object> subjectBalReportMap = new HashMap<String, Object>();


				File xmlFileName = new File(xmlFile);
				InputStream inputXML = new FileInputStream(xmlFileName);
				XLSReader reader = ReaderBuilder.buildFromXML(inputXML);
				File excelFile = new File(f.getPath());
				InputStream inputXLS = new FileInputStream(excelFile);
				Map<String, Object> beans = new HashMap<String, Object>();
				if ("1".equals(teplateType)) {  //资产负债表模板
					profitsReport = null;
					cashflowsReport = null;
					beans.put("cusFinMain", cusFinMain);
					beans.put("assetsReportMap", assetsReportMap);
				}
				if ("2".equals(teplateType)) {  //利润分配表模板
					assetsReport = null;
					cashflowsReport = null;
					beans.put("cusFinMain", cusFinMain);
					beans.put("profitsReportMap", profitsReportMap);
				}
				if ("3".equals(teplateType)) {  //现金流量表模板
					assetsReport = null;
					profitsReport = null;
					beans.put("cusFinMain", cusFinMain);
					beans.put("cashflowsReportMap", cashflowsReportMap);
				}
				if ("4".equals(teplateType)) {  //资产负债表、利润分配表和现金流量表的总模板
					beans.put("cusFinMain", cusFinMain);
					beans.put("assetsReportMap", assetsReportMap);
					beans.put("profitsReportMap", profitsReportMap);
					beans.put("cashflowsReportMap", cashflowsReportMap);
				}
				reader.read(inputXLS, beans);    //将数据导入实体中

				/**20190505 产品要求去掉公式的校验**/
				msg[0] = "";//checkAssetsReportBN_New(assetsReportMap, "1");    //资产负债表
				msg[1] = "";//checkAssetsReportBN_New(profitsReportMap, "2");    //利润分配表
				msg[2] = "";//checkAssetsReportBN_New(cashflowsReportMap, "3");    //现金流量表
				msg[3] = checkCusFinMain(cusFinMain);
				if (!"".equals(msg[0])) {
					resMsg.append(msg[0]).append("<br>");
				}
				if (!"".equals(msg[1])) {
					resMsg.append(msg[1]).append("<br>");
				}
				if (!"".equals(msg[2])) {
					resMsg.append(msg[2]).append("<br>");
				}
				if (!"".equals(msg[3])) {
					resMsg.append(msg[3]).append("<br>");
				}
				if (resMsg != null && resMsg.length() > 0) {
					flag = false;
					dataMap.put("flag", "error");
					dataMap.put("resMsg", resMsg.toString());
					return dataMap;
				}
				//保存数据到数据库中时，先保存主表，再保存详细表
				cusFinMain = checkCusFinMainData(cusFinMain);
				cusFinMain = convertCusFinMainData(cusFinMain, cusNo);

				cusFinMainFeign.delete(cusFinMain);
				cusFinMainFeign.insert(cusFinMain);
				cusFinMainFeign.insertMoreExcelNew(beans, teplateType);
			}else if("5".equals(teplateType)){//科目余额表 由于模板结构与上面三种模板不统一，需要单独解析
				Map<String, Object> beans = new HashMap<String, Object>();
				beans = poiExportExcelUtil.resolveSubjectBalModel(allPath,cusNo,"");
				if("success".equals(beans.get("flag"))){
					cusFinMain = JsonStrHandling.handlingStrToBean(beans.get("cusFinMain"), CusFinMain.class);
					cusFinMain = convertCusFinMainData(cusFinMain, cusNo);
					cusFinMainFeign.delete(cusFinMain);
					cusFinMainFeign.insert(cusFinMain);
					cusFinMainFeign.insertMoreExcelNew(beans, teplateType);
				}else{
					flag = false;
					dataMap.put("flag", "error");
					dataMap.put("resMsg",beans.get("msg"));
					return dataMap;
				}
			}else {
			}
			infIntegrity = cusInterfaceFeign.updateInfIntegrity(cusNo);//更新客户信息完整度
			dataMap.put("infIntegrity",infIntegrity);

		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			dataMap.put("flag", "error");
			Map<String,String> parmMap = new HashMap<String,String>();
			parmMap.put("operation","上传文件");
			parmMap.put("reason",e.getMessage());
			dataMap.put("resMsg", MessageEnum.FAILED_OPERATION_CONTENT.getMessage(parmMap));
		} finally {
			if(flag){
				dataMap.put("flag", "success");
				dataMap.put("fileName", newFileName);
				dataMap.put("realName", cusFinUploadFileName);
				dataMap.put("finRptType", cusFinMain.getFinRptType());
				dataMap.put("finRptDate", cusFinMain.getFinRptDate());
				dataMap.put("cusName", cusFinMain.getCusName());
				dataMap.put("teplateType", teplateType);  //上传的模板类型
			}
		}
		return dataMap;
	}

	//财务报表上传控制 peng-财务改
	@RequestMapping(value = "/uploadReportAjax")
	@ResponseBody
	public Map<String, Object> uploadReportAjax(String cusNo,String cusType, String caliber,String ifMonmer,String cusFinUploadFileName,@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String valmsg="";//校验提示信息
		boolean flag = true;
		cusFinUploadFileName=cusFinUpload.getOriginalFilename();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if(cusFinUploadFileName.endsWith(".exe")||cusFinUploadFileName.endsWith(".jsp")||cusFinUploadFileName.endsWith(".bat")
				||cusFinUploadFileName.endsWith(".dll")||cusFinUploadFileName.endsWith(".com")||cusFinUploadFileName.endsWith(".sh")||cusFinUploadFileName.endsWith(".py")){
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
			return dataMap;
		}
		String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
		File uploadFinPathFile = new File(uploadFinPath);
		if(!uploadFinPathFile.exists()){
			uploadFinPathFile.mkdirs();
		}
		String newFileName = cusNo + UUID.randomUUID().toString() + cusFinUploadFileName.substring(cusFinUploadFileName.lastIndexOf("."));
		String allPath = uploadFinPath + File.separator + newFileName;
		File xlsF = new File(allPath);
		cusFinUpload.transferTo(xlsF);
		//根据文件名称判断是哪个类型的报表模板
		StringBuilder resMsg = new StringBuilder();

		//获取每一个模板第一行第一列的值，标识是哪类模板
		PoiReadExcelUtil poiExcelUtil = new PoiReadExcelUtil(xlsF.getPath(), 0);
		String teplateType = poiExcelUtil.getValue(0, 0);
		PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
		String xmlStr = "1".equals(cusType)?poiExportExcelUtil.getFileModel(teplateType, "xml"):poiExportExcelUtil.getPersonFileModel(teplateType, "xml");
		String[] msg = new String[4];

		MfCusReportAcount mfCusReportAcount = new MfCusReportAcount();
		String infIntegrity;
		InputStream inputXLS = null;
		try {  //恢复原来程序
			if("5".equals(teplateType)){//科目余额表 由于模板结构与上面三种模板不统一，需要单独解析
				CusFinMain cusFinMain = new CusFinMain();
				Map<String, Object> beans = new HashMap<String, Object>();
				beans = poiExportExcelUtil.resolveSubjectBalModel(allPath,cusNo,cusType);
				if("success".equals(beans.get("flag"))){
					cusFinMain = JsonStrHandling.handlingStrToBean(beans.get("cusFinMain"), CusFinMain.class);
					cusFinMain = convertCusFinMainData(cusFinMain, cusNo);
					cusFinMainFeign.delete(cusFinMain);
					cusFinMainFeign.insert(cusFinMain);
					cusFinMainFeign.insertMoreExcelNew(beans, teplateType);
					dataMap.put("finRptType", cusFinMain.getFinRptType());
					dataMap.put("finRptDate", cusFinMain.getFinRptDate());
					dataMap.put("cusName", cusFinMain.getCusName());
					dataMap.put("corpNo", cusFinMain.getRelationCorpNo());

					//更新新表标志
					mfCusReportAcount.setCusNo(cusNo);
					mfCusReportAcount.setWeeks(cusFinMain.getFinRptDate());
					mfCusReportAcount.setReportType(cusFinMain.getFinRptType());
					mfCusReportAcount.setCaliber(caliber);
					mfCusReportAcount.setIfMonmer(ifMonmer);
					if("2".equals(cusType)){
						mfCusReportAcount.setCorpNo(cusFinMain.getRelationCorpNo());
						mfCusReportAcount.setCorpName(cusFinMain.getRelationCorpName());
					}
					List list=mfCusReportAcountFeign.getList(mfCusReportAcount);
					if(list!=null&&list.size()>0){
						mfCusReportAcount=(MfCusReportAcount)list.get(0);
						mfCusReportAcount.setBalanceDataId(cusFinMain.getCusNo());
						mfCusReportAcount.setReportSts("1");
						mfCusReportAcountFeign.update(mfCusReportAcount);
					}else{
						mfCusReportAcount.setOpNo(User.getRegNo(request));
						mfCusReportAcount.setOpName(User.getRegName(request));
						mfCusReportAcount.setCusName(cusFinMain.getCusName());
						mfCusReportAcount.setAccountId(WaterIdUtil.getWaterId());
						mfCusReportAcount.setBalanceDataId(cusFinMain.getCusNo());
						mfCusReportAcount.setAccRule("1");
						mfCusReportAcount.setReportSts("0");
						mfCusReportAcountFeign.insert(mfCusReportAcount);
					}
				}else{
					flag = false;
					dataMap.put("flag", "error");
					dataMap.put("resMsg",beans.get("msg"));
					return dataMap;
				}

				//暂时使用原来的程序
//				beans = poiExportExcelUtil.resolveSubjectBalModelNew(allPath,cusNo);
//				if(beans.get("flag").equals("success")){
//					mfCusReportAcount = JsonStrHandling.handlingStrToBean(beans.get("mfCusReportAcount"), MfCusReportAcount.class);
//				}else{
//					flag = false;
//					dataMap.put("flag", "error");
//					dataMap.put("resMsg",beans.get("msg"));
//					return dataMap;
//				}
			}else{
				File xmlF = new File(xmlStr);
				InputStream inputXML = new FileInputStream(xmlF);
				XLSReader reader = ReaderBuilder.buildFromXML(inputXML);
				File excelFile = new File(xlsF.getPath());
				inputXLS = new FileInputStream(excelFile);
				//将Excel中的值 根据XML配置 导入Map中
				Map<String, Object> beans = new HashMap<String, Object>();
				mfCusReportAcount.setCusNo(cusNo);
				mfCusReportAcount.setCaliber(caliber);
				mfCusReportAcount.setIfMonmer(ifMonmer);
				Map<String, Object> reportDataMap = new HashMap<String, Object>();
				beans.put("mfCusReportAcount", mfCusReportAcount);
				beans.put("reportDataMap", reportDataMap);
				reader.read(inputXLS, beans);    //将数据导入实体中
				/**
				 * TODO 校验导入数据
				 */
				//保存报表导入数据 并更新客户信息
				valmsg=valReportData(reportDataMap,teplateType);
				if(StringUtil.isNotBlank(valmsg)){
					throw new Exception(valmsg);
				}
				mfCusReportAcount = mfCusReportAcountFeign.insertMoreExcelNew(beans,teplateType,cusType);
				dataMap.put("finRptType", mfCusReportAcount.getReportType());
				dataMap.put("finRptDate", mfCusReportAcount.getWeeks());
				dataMap.put("cusName", mfCusReportAcount.getCusName());
				dataMap.put("corpNo", mfCusReportAcount.getCorpNo());
			}
			infIntegrity = cusInterfaceFeign.updateInfIntegrity(cusNo);//更新客户信息完整度
			dataMap.put("infIntegrity",infIntegrity);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			dataMap.put("flag", "error");
			if(StringUtil.isBlank(valmsg)) {
				Map<String, String> msgMap = new HashMap<>();
				msgMap.put("field", "信息填写");
				msgMap.put("field1", "完善报表信息后再次尝试上传");
				dataMap.put("resMsg", MessageEnum.DATA_INCOMPLETE_INFO.getMessage(msgMap));
			}else{
				dataMap.put("resMsg", valmsg);
			}
		} finally {
			if(flag){
				dataMap.put("flag", "success");
				dataMap.put("fileName", newFileName);
				dataMap.put("realName", cusFinUploadFileName);

				dataMap.put("teplateType", teplateType);  //上传的模板类型
			}
			if (null !=inputXLS){
				inputXLS.close();
			}
		}
		return dataMap;
	}

	public String valReportData(Map<String, Object> reportDataMap,String teplateType) throws  Exception{
		MfCusReportCheck mfCusReportCheck=new MfCusReportCheck();
		String typeId="";
		if("1".equals(teplateType)){
			typeId="001";
		}else if("2".equals(teplateType)){
			typeId="003";
		}else if("3".equals(teplateType)){
			typeId="002";
		}else {
		}
		mfCusReportCheck.setReportTypeId(typeId);
		mfCusReportCheck.setIsUse("1");
		List<MfCusReportCheck> list=mfCusReportCheckFeign.getList(mfCusReportCheck);
		for(MfCusReportCheck check:list){
				double[] result=new double[2];
				String[] checkFormula=checkFormulaSplit(check.getCheckFormula());
				for(int i=0;i<checkFormula.length;i++){
					String formula=checkFormula[i];
					result[i]=Double.valueOf(ScriptEngineUtil.getCalData(formula,getItemIdData(formula,reportDataMap,"")).toString());
				}
				if(result[0]!=result[1]){
					return  "【"+check.getRemarks()+"】校验公式不平";
				}
			}
		return "";
	}


	public Map<String,Object>  getItemIdData(String formula,Map<String, Object> reportDataMap,String type){
		Map<String,Object> map=new HashMap<>();
		String opr="+ - * % ( )";
		String tmpStr="";
		char [] strArr = formula.toCharArray();
		for(int i=0;i<strArr.length;i++){
			if(opr.contains(String.valueOf(strArr[i]))){
				if(StringUtil.isNotBlank(tmpStr)){
					map.put(tmpStr,reportDataMap.get(tmpStr));
				}
				tmpStr="";
			}else{
				tmpStr+=String.valueOf(strArr[i]);
				if(i==strArr.length-1){
					map.put(tmpStr,reportDataMap.get(tmpStr));
				}
			}
		}
		return map;
	}

	/**********
	 *  将校验公式拆成两部分
	 * @param checkFormula
	 * @return
	 */

	public String[] checkFormulaSplit(String checkFormula){
		String[] formula=null;
		String[] compareOpr={"=",">=",">","<=","<"};
		for(String opr:compareOpr){
			if(checkFormula.contains(opr)){
				formula=checkFormula.split(opr);
				return formula;
			}
		}
		return formula;
	}


	/**
	 * 把从excel中读取的数据进行转换,如报表类型获取的是中文描述将其转换为代码
	 * @author LJW
	 * date 2017-4-17
	 */
	private CusFinMain checkCusFinMainData(CusFinMain cusFinMain){
		String finRptType = cusFinMain.getFinRptType();
		String ifAud = cusFinMain.getIfAud();
		if ("月报".equals(finRptType)) {
			cusFinMain.setFinRptType("1");
		}
		if ("季报".equals(finRptType)) {
			cusFinMain.setFinRptType("2");
		}
		if ("年报".equals(finRptType)) {
			cusFinMain.setFinRptType("3");
		}
		if ("已审计".equals(ifAud)) {
			cusFinMain.setIfAud("1");
		}
		if ("未审计".equals(ifAud)) {
			cusFinMain.setIfAud("0");
		}
		return cusFinMain;
	}

	/**
	 * 对财务报表中的某些数据进行简单转换-目前只对客户名称进行控制
	 * @author LJW
	 * date 2017-4-26
	 * @throws Exception
	 */
	private CusFinMain convertCusFinMainData(CusFinMain cusFinMain,String cusNo) throws Exception{
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		try {
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			if (mfCusCustomer != null) {
				cusFinMain.setCusName(mfCusCustomer.getCusName());
			}
			cusFinMain.setFinRptSts("0");  //报表状态1:覆盖  0:新增
			cusFinMain.setAccRule("1");
			cusFinMain.setCusNo(cusNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return cusFinMain;
	}

	private boolean annualReportContinuity(CusFinMain cusFinMain) throws Exception {
		boolean boo = false;
		CusFinMain cusFinMainTmp = new CusFinMain();
		cusFinMainTmp.setCusNo(cusFinMain.getCusNo());
		cusFinMainTmp.setFinRptType(cusFinMain.getFinRptType());
		List<CusFinMain> cusFinMainList = cusFinMainFeign.getCusFinMainList(cusFinMainTmp);
		if (null != cusFinMainList && cusFinMainList.size() > 0) {
			cusFinMainTmp = cusFinMainFeign.getById(cusFinMain);
			if (null == cusFinMainTmp) {
				Map<String, String> maxMinFinRptDate = cusFinMainFeign.maxMinFinRptDate(cusFinMain);
				int maxFinRptDate = Integer.valueOf(maxMinFinRptDate.get("maxFinRptDate"));
				int minFinRptDate = Integer.valueOf(maxMinFinRptDate.get("minFinRptDate"));
				int finRptDate = Integer.valueOf(cusFinMain.getFinRptDate());
				if (finRptDate - maxFinRptDate == 1 || minFinRptDate - finRptDate == 1) {
					boo = true;
				}
			} else {
				boo = true;
			}
		} else {
			boo = true;
		}

		return boo;
	}

	//解析财务报表上传文件中的检查结果
	private String[] checkResult(File finUpload,String cusNo, File cusFinUpload)throws Exception{
		//根据文件名称判断是哪个类型的报表模板
		PoiReadExcelUtil poiExcelUtil = new PoiReadExcelUtil(cusFinUpload.getPath(), 0);
		String teplateType = poiExcelUtil.getValue(0, 0);
		PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
		String xmlFile = poiExportExcelUtil.getFileModel(teplateType,"xml");
		String[] msg = new String[3];
		InputStream inputXLS = null;
		try {
			File xmlFileName = new File(xmlFile);
			InputStream inputXML = new FileInputStream(xmlFileName);
			XLSReader reader = ReaderBuilder.buildFromXML(inputXML);
			File excelFile = new File(cusFinUpload.getPath());
			inputXLS = new FileInputStream(excelFile);
			CusFinMain cusFinMain = new CusFinMain();
			AssetsReport assetsReport = new AssetsReport();
			ProfitsReport profitsReport = new ProfitsReport();
			CashflowsReport cashflowsReport = new CashflowsReport();
			Map<String, Object> beans = new HashMap<String, Object>();
			if ("1".equals(teplateType)) {  //资产负债表模板
				profitsReport = null;
				cashflowsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("assetsReport", assetsReport);
			}
			if ("2".equals(teplateType)) {  //利润分配表模板
				assetsReport = null;
				cashflowsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("profitsReport", profitsReport);
			}
			if ("3".equals(teplateType)) {  //现金流量表模板
				assetsReport = null;
				profitsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("cashflowsReport", cashflowsReport);
			}
			if ("4".equals(teplateType)) {  //资产负债表、利润分配表和现金流量表的总模板
				beans.put("cusFinMain", cusFinMain);
				beans.put("assetsReport", assetsReport);
				beans.put("profitsReport", profitsReport);
				beans.put("cashflowsReport", cashflowsReport);
			}
			reader.read(inputXLS, beans);    //将数据导入实体中
			msg[0] = checkAssetsReportBN(assetsReport);    //资产负债表
			msg[1] = checkProfitsReportBN(profitsReport);  //利润分配表
			msg[2] = checkCashflowsReportBN(cashflowsReport); //现金流量表
		} catch (Exception e) {
			throw e;
		}finally {
			if (null !=inputXLS){
				inputXLS.close();
			}
		}
		return msg;
	}
	//名下企业财务报表上传控制
	@SuppressWarnings("unused")
	@RequestMapping(value = "/uploadPersonAjax")
	@ResponseBody
	public Map<String, Object> uploadPersonAjax(@RequestParam(value="cusFinUpload")  MultipartFile cusFinUpload) throws Exception{
		ActionContext.initialize(request,response);
		String cusNo = request.getParameter("cusNo");
		boolean flag = true;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String cusFinUploadFileName =cusFinUpload.getOriginalFilename();
		//添加文件上传格式验证，过滤可能威胁系统安全非法后缀
		if(cusFinUploadFileName.endsWith(".exe")||cusFinUploadFileName.endsWith(".jsp")||cusFinUploadFileName.endsWith(".bat")
				||cusFinUploadFileName.endsWith(".dll")||cusFinUploadFileName.endsWith(".com")||cusFinUploadFileName.endsWith(".sh")||cusFinUploadFileName.endsWith(".py")){
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
			return dataMap;
		}
		String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
		String newFileName = cusNo + UUID.randomUUID().toString() + cusFinUploadFileName.substring(cusFinUploadFileName.lastIndexOf("."));
		String allPath = uploadFinPath + File.separator + newFileName;
		File newFile = new File(allPath);
		//根据文件名称判断是哪个类型的报表模板
		StringBuilder resMsg = new StringBuilder();
		//获取每一个模板第一行第一列的值，标识是哪类模板
//		MultipartFile cf= (MultipartFile)cusFinUpload;
//        DiskFileItem fi = (DiskFileItem)cf.getFileItem();
//        File f = fi.getStoreLocation();
		File f = null;
		f=File.createTempFile("tmp", null);
		cusFinUpload.transferTo(f);
		f.deleteOnExit();

		PoiReadExcelUtil poiExcelUtil = new PoiReadExcelUtil(f.getPath(), 0);
		String teplateType = poiExcelUtil.getValue(0, 0);
		PoiExportExcelUtil poiExportExcelUtil = new PoiExportExcelUtil();
		String xmlFile = poiExportExcelUtil.getPersonFileModel(teplateType, "xml");
		String[] msg = new String[4];
		CusFinMain cusFinMain = new CusFinMain();
		AssetsReport assetsReport = new AssetsReport();
		ProfitsReport profitsReport = new ProfitsReport();
		CashflowsReport cashflowsReport = new CashflowsReport();
		String infIntegrity;
		try {
			File xmlFileName = new File(xmlFile);
			InputStream inputXML = new FileInputStream(xmlFileName);
			XLSReader reader = ReaderBuilder.buildFromXML(inputXML);
			File excelFile = new File(f.getPath());
			InputStream inputXLS = new FileInputStream(excelFile);
			Map<String, Object> beans = new HashMap<String, Object>();
			if ("1".equals(teplateType)) {  //资产负债表模板
				profitsReport = null;
				cashflowsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("assetsReport", assetsReport);
			}
			if ("2".equals(teplateType)) {  //利润分配表模板
				assetsReport = null;
				cashflowsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("profitsReport", profitsReport);
			}
			if ("3".equals(teplateType)) {  //现金流量表模板
				assetsReport = null;
				profitsReport = null;
				beans.put("cusFinMain", cusFinMain);
				beans.put("cashflowsReport", cashflowsReport);
			}
			if ("4".equals(teplateType)) {  //资产负债表、利润分配表和现金流量表的总模板
				beans.put("cusFinMain", cusFinMain);
				beans.put("assetsReport", assetsReport);
				beans.put("profitsReport", profitsReport);
				beans.put("cashflowsReport", cashflowsReport);
			}
			XLSReadStatus readStatus = reader.read(inputXLS, beans);    //将数据导入实体中
			msg[0] = checkAssetsReportBN(assetsReport);    //资产负债表
			msg[1] = checkProfitsReportBN(profitsReport);  //利润分配表
			msg[2] = checkCashflowsReportBN(cashflowsReport); //现金流量表
			msg[3] = checkCusPersonFinMain(cusFinMain);
			//msg = checkResult(cusFinUpload,cusNo);
			if (!"".equals(msg[0])) {
				resMsg.append(msg[0]).append("<br>");
			}
			if (!"".equals(msg[1])) {
				resMsg.append(msg[1]).append("<br>");
			}
			if (!"".equals(msg[2])) {
				resMsg.append(msg[2]).append("<br>");
			}
			if (!"".equals(msg[3])) {
				resMsg.append(msg[3]).append("<br>");
			}
			if (resMsg != null && resMsg.length() > 0) {
				flag = false;
				dataMap.put("flag", "error");
				dataMap.put("resMsg", resMsg.toString());
				return dataMap;
			}
			FileUtils.copyFile(f, newFile);
			//保存数据到数据库中时，先保存主表，再保存详细表
			cusFinMain = checkCusFinMainData(cusFinMain);
			cusFinMain = convertCusFinMainData(cusFinMain,cusNo);
			cusFinMainFeign.delete(cusFinMain);
			cusFinMainFeign.insert(cusFinMain);
			cusFinMainFeign.insertMoreExcel(cusFinMain,allPath,teplateType);
			infIntegrity = cusInterfaceFeign.updateInfIntegrity(cusNo);//更新客户信息完整度
			dataMap.put("infIntegrity",infIntegrity);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
			dataMap.put("flag", "error");
			dataMap.put("resMsg", MessageEnum.ERROR_DATA_CREDIT.getMessage("上传文件"));
		} finally {
			if(flag){
				dataMap.put("flag", "success");
				dataMap.put("fileName", newFileName);
				dataMap.put("realName", cusFinUploadFileName);
				dataMap.put("finRptType", cusFinMain.getFinRptType());
				dataMap.put("finRptDate", cusFinMain.getFinRptDate());
				dataMap.put("cusName", cusFinMain.getCusName());
				dataMap.put("relationCorpName", cusFinMain.getRelationCorpName());
				dataMap.put("relationCorpNo", cusFinMain.getRelationCorpNo());
				dataMap.put("teplateType", teplateType);  //上传的模板类型
			}
		}
		return dataMap;
	}

	//检测上传的主表信息是否填写
	private String checkCusFinMain(CusFinMain cusFinMain){
		StringBuilder msg = new StringBuilder();
		if (cusFinMain == null) {
			return msg.toString();
		}
		String finRptDate = cusFinMain.getFinRptDate();
		String finRptType = cusFinMain.getFinRptType();
		String ifAud = cusFinMain.getIfAud();
		/*String cusName = cusFinMain.getCusName();
		 if (cusName == null || "".equals(cusName)) {
			msg.append("客户名称为空<br>");
		}*/
		if (finRptDate == null || "".equals(finRptDate)) {
			msg.append("报表日期为空<br>");
		}else {
			if(("月报".equals(finRptType) && finRptDate.length() != 6) || ("年报".equals(finRptType) && finRptDate.length() != 4)){
				msg.append("报表日期与报表类型不符<br>");
			}
		}
		if ("已审计".equals(ifAud)) {
			if(StringUtil.isBlank(cusFinMain.getAudOrg())){
				msg.append("若是否审计选已审记,审计单位必须填写<br>");
			}
			if(StringUtil.isBlank(cusFinMain.getAudDate())){
				msg.append("若是否审计选已审记,审计日期必须填写<br>");
			}
			if(StringUtil.isBlank(cusFinMain.getAudIdea())){
				msg.append("若是否审计选已审记,审计意见必须填写<br>");
			}
		}
		return msg.toString();
	}
	//个人名下财务报表检测上传的主表信息是否填写
	private String checkCusPersonFinMain(CusFinMain cusFinMain){
		StringBuilder msg = new StringBuilder();
		if (cusFinMain == null) {
			return msg.toString();
		}
		String finRptDate = cusFinMain.getFinRptDate();
		String finRptType = cusFinMain.getFinRptType();
		String relationCorpNo = cusFinMain.getRelationCorpNo();
		if (relationCorpNo == null || "".equals(relationCorpNo)) {
			msg.append("企业名称为空<br>");
		}
		if (finRptDate == null || "".equals(finRptDate)) {
			msg.append("报表日期为空<br>");
		}else {
			if(("月报".equals(finRptType) && finRptDate.length() != 6) || ("年报".equals(finRptType) && finRptDate.length() != 4)){
				msg.append("报表日期与报表类型不符<br>");
			}
		}
		return msg.toString();
	}

	//资产负债表 表内关系校验
	private String checkAssetsReportBN(AssetsReport assetsReport){
		StringBuilder msg = new StringBuilder();
		if (assetsReport == null) {
			return msg.toString();
		}
		//负债类
		msg = checkAssetsReportZcBN(msg,assetsReport);
		msg = checkAssetsReportFzBN(msg,assetsReport);
		if (msg != null && !"".equals(msg.toString())) {
			msg.insert(0, "资产负债表表内关系应符合以下关系：<br>");
		}
		return msg.toString();
	}
	//资产负债表 表内关系校验
	private String checkAssetsReportBN_New(Map<String, Object> assetsReportMap,String reportType){
		StringBuilder msg = new StringBuilder();
		for (Map.Entry<String, Object> entry : assetsReportMap.entrySet()) {
			String msg1 = cusFinMainFeign.doCheckFinParam(entry.getKey(), assetsReportMap);
			if(StringUtil.isNotEmpty(msg1)){
				msg.append(msg1);
				break;
			}
		}
		//负债类

		if (msg != null && !"".equals(msg.toString())) {
			if("1".equals(reportType)){
				msg.insert(0, "资产负债表表内关系应符合以下关系：<br>");
			}else if("2".equals(reportType)){
				msg.insert(0, "利润分配表表内关系应符合以下关系：<br>");
			}else if("3".equals(reportType)){
				msg.insert(0, "现金流量表内关系应符合以下关系：<br>");
			}else if("4".equals(reportType)){
				msg.insert(0, "科目余额表内关系应符合以下关系：<br>");
			}else {
			}

		}
		return msg.toString();
	}

	//资产负债表 负债列 表内关系校验
	private StringBuilder checkAssetsReportFzBN(StringBuilder msg,AssetsReport assetsReport){
		//负债列
		//流动负债合计 = 短期借款 + 应付票据 + 应付帐款 + 预收帐款 + 应付工资 + 应付福利费 + 应交税金 +
		//				应付利润 + 其他应交款 + 其他应付款 + 预提费用 + 预计负债 + 一年内到期的长期负债 + 其他流动负债
		Double fz_ldfzhj = assetsReport.getFz_ldfzhj();
		Double fzLdfzhJSum = assetsReport.getFz_dqjk() + assetsReport.getFz_yfpj() + assetsReport.getFz_yfzk() + assetsReport.getFz_yszk()
				+ assetsReport.getFz_yfgz() + assetsReport.getFz_yfflf() + assetsReport.getFz_yjsj() + assetsReport.getFz_yflr()
				+ assetsReport.getFz_qtyjk() + assetsReport.getFz_qtyfk() + assetsReport.getFz_ytfy() + assetsReport.getFz_yjfz()
				+ assetsReport.getFz_ynndqdcqfz() + assetsReport.getFz_qtldfz();
		if (fz_ldfzhj.doubleValue() != fzLdfzhJSum.doubleValue()) {
			msg.append("负债类：流动负债合计 = 短期借款 + 应付票据 + 应付帐款 + 预收帐款 + 应付工资 + 应付福利费 + 应交税金 + 应付利润 + 其他应交款 + 其他应付款 + 预提费用 + 预计负债 + 一年内到期的长期负债 + 其他流动负债").append("<br>");
		}
		//长期负债合计 = 长期借款 + 应付债券 + 长期应付款 + 其他长期负债 + 其中：特准储备资金 + 专项应付款
		Double fz_cqfzhj = assetsReport.getFz_cqfzhj();
		Double fzCqfzhjSum = assetsReport.getFz_cqjk() + assetsReport.getFz_yfzq() + assetsReport.getFz_cqyfk() + assetsReport.getFz_qtcqfz()
				+ assetsReport.getFz_qztzcbzj() + assetsReport.getFz_zxyfk();
		if (fz_cqfzhj.doubleValue() != fzCqfzhjSum.doubleValue()) {
			msg.append("负债类：长期负债合计 = 长期借款 + 应付债券 + 长期应付款 + 其他长期负债 + 其中：特准储备资金 + 专项应付款").append("<br>");
		}
		//负债合计 = 流动负债合计 + 长期负债合计 + 递延税款贷项
		Double fz_fzhj = assetsReport.getFz_fzhj();
		Double fzFzhjSum = fz_ldfzhj + fz_cqfzhj + assetsReport.getFz_dyskdx();
		if (fz_fzhj.doubleValue() != fzFzhjSum.doubleValue()) {
			msg.append("负债类：负债合计 = 流动负债合计 + 长期负债合计 + 递延税款贷项").append("<br>");
		}
		//实收资本（股本）= 国家资本 + 集体资本 + 法人资本 + 个人资本 + 外商资本
		Double fz_sszb = assetsReport.getFz_sszbgb();
		Double fzSszbSum = assetsReport.getFz_gjzb() + assetsReport.getFz_jtzb() + assetsReport.getFz_frzb() + assetsReport.getFz_grzb() + assetsReport.getFz_wszb();
		if (fz_sszb.doubleValue() != fzSszbSum.doubleValue()) {
			msg.append("负债类：实收资本（股本）= 国家资本 + 集体资本 + 法人资本 + 个人资本 + 外商资本").append("<br>");
		}
		//盈余公积 = 其中：法定盈余公积 + 公益金
		Double fz_yygj = assetsReport.getFz_yygj();
		Double fzYygjSum = assetsReport.getFz_qzfdyygj() + assetsReport.getFz_gyj();
		if (fz_yygj.doubleValue() != fzYygjSum.doubleValue()) {
			msg.append("负债类：盈余公积 = 其中：法定盈余公积 + 公益金").append("<br>");
		}
		//所有者权益合计 = 实收资本（股本） + 少数股东权益 + 资本公积 + 盈余公积 + 补充流动资产 + 未确认投资损失 + 未分配利润 + 外币报表折算差额
		Double fz_syzqyhj = assetsReport.getFz_syzqyhj();
		Double fzSyzqyhjSum = fz_sszb + assetsReport.getFz_ssgdqy() + assetsReport.getFz_zbgj() + fz_yygj + assetsReport.getFz_bcldzc()
				+ assetsReport.getFz_wqrtzss() + assetsReport.getFz_wfplr() + assetsReport.getFz_wbbbzsce();
		if (fz_syzqyhj.doubleValue() != fzSyzqyhjSum.doubleValue()) {
			msg.append("负债类：所有者权益合计 = 实收资本（股本） + 少数股东权益 + 资本公积 + 盈余公积 + 补充流动资产 + 未确认投资损失 + 未分配利润 + 外币报表折算差额").append("<br>");
		}
		//负债及所有者权益总计 = 负债合计 + 所有者权益合计
		Double fz_fzjsszqyzj = assetsReport.getFz_fzjsyzqyzj();
		Double fz_fzjsszqyzjSum = fz_fzhj + fz_syzqyhj;
		if (fz_fzjsszqyzj.doubleValue() != fz_fzjsszqyzjSum.doubleValue()) {
			msg.append("负债类：负债及所有者权益总计 = 负债合计 + 所有者权益合计").append("<br>");
		}
		if (assetsReport.getZc_zczj().doubleValue() != fz_fzjsszqyzj.doubleValue()) {
			msg.append("资产总计=负债及所有者权益总计").append("<br>");
		}
		return msg;
	}

	//资产负债表 资产列 表内关系校验
	private StringBuilder checkAssetsReportZcBN(StringBuilder msg,AssetsReport assetsReport){
		//资产类
		//存货 = 其中：原材料 + 产成品
		Double zc_ch = assetsReport.getZc_ch();
		Double b18AndB19 = assetsReport.getZc_qzycl() + assetsReport.getZc_ccp();
		if (zc_ch.doubleValue() != b18AndB19.doubleValue()) {
			msg.append("资产类：存货 =其中：原材料 +产成品").append("<br>");
		}
		//流动资产合计 = (货币资金+短期投资+应收票据+应收股利+应收利息+应收帐款+其他应收款+预付帐款+期货保证金+应收补贴款+应收出口退税+存货)
		//				+(待摊费用+待处理流动资产损失+一年内到期的长期债权投资+其他流动资产)
		Double zc_ldzchj = assetsReport.getZc_ldzchj();
		Double zcLdzcSum = (assetsReport.getZc_hbzj() + assetsReport.getZc_dqtz() + assetsReport.getZc_yspj() + assetsReport.getZc_ysgl() + assetsReport.getZc_yslx()
				+ assetsReport.getZc_yszk() + assetsReport.getZc_qtysk() + assetsReport.getZc_yfzk() + assetsReport.getZc_qhbzj() + assetsReport.getZc_ysbtk()
				+ assetsReport.getZc_ysckts() + zc_ch) + (assetsReport.getZc_dtfy() + assetsReport.getZc_dclldzcss() + assetsReport.getZc_ynndqdcqzctz() + assetsReport.getZc_qtldzc());
		if (zc_ldzchj.doubleValue() != zcLdzcSum.doubleValue()) {
			msg.append("资产类：流动资产合计 = (货币资金+短期投资+应收票据+应收股利+应收利息+应收帐款+其他应收款+预付帐款+期货保证金+应收补贴款+应收出口退税+存货)+(待摊费用+待处理流动资产损失+一年内到期的长期债权投资+其他流动资产)").append("<br>");
		}
		//长期投资 = 其中:长期股权投资 + 长期债权投资
		Double zc_cqtz = assetsReport.getZc_cqtz();
		Double zcCqtzSum = assetsReport.getZc_qzcqgqtz() + assetsReport.getZc_cqzqtz();
		if (zc_cqtz.doubleValue() != zcCqtzSum.doubleValue()) {
			msg.append("资产类：长期投资 = 其中:长期股权投资 + 长期债权投资").append("<br>");
		}
		//长期投资合计 = 长期投资 - 其中：合并差价
		Double zc_cqtzhj = assetsReport.getZc_cqtzhj();
		Double zcCqtzhjSum = zc_cqtz - assetsReport.getZc_qzhbcj();
		if (zc_cqtzhj.doubleValue() != zcCqtzhjSum.doubleValue()) {
			msg.append("资产类：长期投资合计 = 长期投资 - 其中：合并差价").append("<br>");
		}
		//固定资产净值 = 固定资产原值 - 减：累计折旧
		Double zc_gdzcjz = assetsReport.getZc_gdzcjz();
		Double zcGdzcjzSum = assetsReport.getZc_gdzcyz() - assetsReport.getZc_jljzj();
		if (zc_gdzcjz.doubleValue() != zcGdzcjzSum.doubleValue()) {
			msg.append("资产类：固定资产净值 = 固定资产原值 - 减：累计折旧").append("<br>");
		}
		//固定资产净额 = 固定资产净值 - 减：固定资产减值准备
		Double zc_gdzcje = assetsReport.getZc_gdzcje();
		Double zcGdzcjeSum = zc_gdzcjz - assetsReport.getZc_jgdzcjzzb();
		if (zc_gdzcje.doubleValue() != zcGdzcjeSum.doubleValue()) {
			msg.append("资产类：固定资产净额 = 固定资产净值 - 减：固定资产减值准备").append("<br>");
		}
		//固定资产合计 = 固定资产净额 + 固定资产清理 + 工程物资 + 在建工程 + 待处理固定资产净损失
		Double zc_gdzchj = assetsReport.getZc_gdzchj();
		Double zcGdzchjSum = zc_gdzcje + assetsReport.getZc_gdzcql() + assetsReport.getZc_gcwz() + assetsReport.getZc_zjgc() + assetsReport.getZc_dclgdzcjss();
		if (zc_gdzchj.doubleValue() != zcGdzchjSum.doubleValue()) {
			msg.append("资产类：固定资产合计 = 固定资产清理 + 工程物资 + 在建工程 + 待处理固定资产净损失").append("<br>");
		}
		//无形资产=其中：土地使用权
		if (assetsReport.getZc_wxzc().doubleValue() != assetsReport.getZc_qztdssq().doubleValue()) {
			msg.append("资产类：无形资产=其中：土地使用权").append("<br>");
		}
		//递延资产 = 递延资产固定资产修理 + 固定资产改良支出
		Double zc_dyzc = assetsReport.getZc_dyzc();
		Double zcDyzcSum = assetsReport.getZc_dyzcgdzcxl() + assetsReport.getZc_gdzcglzc();
		if (zc_dyzc.doubleValue() != zcDyzcSum.doubleValue()) {
			msg.append("资产类：递延资产 = 递延资产固定资产修理 + 固定资产改良支出").append("<br>");
		}
		//无形及递延资产合计 = 无形资产 + 递延资产
		Double zc_wxjdyzchj = assetsReport.getZc_wxjdyzchj();
		Double zcWxjdyzchjSum = assetsReport.getZc_wxzc() + assetsReport.getZc_dyzc();
		if (zc_wxjdyzchj.doubleValue() != zcWxjdyzchjSum.doubleValue()) {
			msg.append("资产类：无形及递延资产合计 = 无形资产 + 递延资产").append("<br>");
		}
		//资产总计 = 流动资产合计 + 长期投资合计 + 固定资产合计 + 无形及递延资产合计 + 其他长期资产 + 其中：特种储备物资 + 递延税款借项
		Double zc_zczj = assetsReport.getZc_zczj();
		Double zcZczjSum = zc_ldzchj + zc_cqtzhj + zc_gdzchj + zc_wxjdyzchj + assetsReport.getZc_qtcqzc() + assetsReport.getZc_qztzcbwz() + assetsReport.getZc_dyskjx();
		if (zc_zczj.doubleValue() != zcZczjSum.doubleValue()) {
			msg.append("资产类：资产总计 = 流动资产合计 + 长期投资合计 + 固定资产合计 + 无形及递延资产合计 + 其他长期资产 + 其中：特种储备物资 + 递延税款借项").append("<br>");
		}
		return msg;
	}

	//利润分配表 表内关系校验
	private String checkProfitsReportBN(ProfitsReport profitsReport){
		StringBuilder msg = new StringBuilder();
		if (profitsReport == null) {
			return msg.toString();
		}
		//一.主营业务收入 = 其中:出口销售收入 + 进口销售收入
		Double zyywsr = profitsReport.getZyywsr();
		Double zyywsrSum = profitsReport.getZyywsr_qzckxssr() + profitsReport.getZyywsr_jkxssr();
		if (zyywsr.doubleValue() != zyywsrSum.doubleValue()) {
			msg.append("一.主营业务收入 = 其中:出口销售收入 + 进口销售收入").append("<br>");
		}
		//二.主营业务收入净额 = 一.主营业务收入 - 减:折扣与折让
		Double zyywsrje = profitsReport.getZyywsrje();
		Double zyywsrjeSum = zyywsr - profitsReport.getZyywsr_jzkyzr();
		if (zyywsrje.doubleValue() != zyywsrjeSum.doubleValue()) {
			msg.append("二.主营业务收入净额 = 一.主营业务收入 - 减:折扣与折让").append("<br>");
		}
		//二.主营业务收入净额   减:(一)主营业务成本 = 其中:出口产品（商品)销售成本
		if (profitsReport.getZyywsrje_jzyywcb().doubleValue() != profitsReport.getZyywsrje_qzckcp().doubleValue()) {
			msg.append("减:(一)主营业务成本 = 其中:出口产品（商品)销售成本").append("<br>");
		}
		//三.主营业务利润 = 二.主营业务收入净额-减:(一)主营业务成本 -(二)主营业务税金及附加 -(三)经营费用-(四).其他 + 加:(一)递延收益+(二)代购代销收入+(三)其他
		Double zyywlr = profitsReport.getZyywlr();
		Double zyywlrSum = zyywsrje - profitsReport.getZyywsrje_jzyywcb() - profitsReport.getZyywsrje_zyywsj() - profitsReport.getZyywsrje_jyfy()
				-profitsReport.getZyywsrje_qt() + profitsReport.getZyywsrje_jdysy() + profitsReport.getZyywsrje_dgdxsr() + profitsReport.getZyywsrje_addqt();
		if (zyywlr.doubleValue() != zyywlrSum.doubleValue()) {
			msg.append("三.主营业务利润 = 二.主营业务收入净额-减:(一)主营业务成本 -(二)主营业务税金及附加 -(三)经营费用-(四).其他 + 加:(一)递延收益+(二)代购代销收入+(三)其他").append("<br>");
		}
		//四.营业利润 =三.主营业务利润 +加:其他业务利润-减:(一)营业费用-(二)管理费用-(三)财务费用-(四)其他
		Double yylr = profitsReport.getYylr();
		Double yylrSum = profitsReport.getZyywlr() + profitsReport.getZyywlr_jqtywlr() - profitsReport.getZyywlr_jyyfy() - profitsReport.getZyywlr_glfy()
				- profitsReport.getZyywlr_cwfy() - profitsReport.getZyywlr_qt();
		if (yylr.doubleValue() != yylrSum.doubleValue()) {
			msg.append("四.营业利润 =三.主营业务利润 +加:其他业务利润-减:(一)营业费用-(二)管理费用-(三)财务费用-(四)其他").append("<br>");
		}
		//四.营业利润    (三)补贴收入 = 其中:补贴前亏损企业补贴收入
		if (profitsReport.getYylr_btsr().doubleValue() != profitsReport.getYylr_qzbtqksqybtsr().doubleValue()) {
			msg.append("(三)补贴收入 = 其中:补贴前亏损企业补贴收入").append("<br>");
		}
		//(四)营业外收入 = 其中:处置固定资产净收益 + 非货币性交易收益 + 出售无形资产收益 + 罚款净收入
		Double yywsr = profitsReport.getYylr_yywsr();
		Double yywsrSum = profitsReport.getYylr_qzczgdzcjsy() + profitsReport.getYylr_fhbxjysy() + profitsReport.getYylr_ccwxzcsy() + profitsReport.getYylr_fkjsr();
		if (yywsr.doubleValue() != yywsrSum.doubleValue()) {
			msg.append("(四)营业外收入=其中:处置固定资产净收益+非货币性交易收益+出售无形资产收益+罚款净收入").append("<br>");
		}
		//四.营业利润  (五)其他=其中:用以前年度含量工资结余弥补
		if (profitsReport.getYylr_qt().doubleValue() != profitsReport.getYylr_qzyyqndhlgzjymb().doubleValue()) {
			msg.append("四.营业利润  (五)其他=其中:用以前年度含量工资结余弥补").append("<br>");
		}
		//减:(一)营业外支出 = 其中:处置固定资产净损失 + 债务重组损失 + 罚款支出 + 捐赠支出
		Double jyywzc = profitsReport.getYylr_jyywzc();
		Double jyywzcSum = profitsReport.getYylr_qzczgdzcjss() + profitsReport.getYylr_zwczss() + profitsReport.getYylr_fkzc() + profitsReport.getYylr_jzzc();
		if (jyywzc.doubleValue() != jyywzcSum.doubleValue()) {
			msg.append("减:(一)营业外支出=其中:处置固定资产净损失+债务重组损失+罚款支出 +捐赠支出").append("<br>");
		}
		//四.营业利润   (二)其他支出=其中:结转的含量工资包干结余
		if (profitsReport.getYylr_qtzc().doubleValue() != profitsReport.getYylr_qzjzdhlgzbgjy().doubleValue()) {
			msg.append("四.营业利润   (二)其他支出=其中:结转的含量工资包干结余").append("<br>");
		}
		//五.利润总额 = 四.营业利润+加:(一)投资收益+(二)期货收益+(三)补贴收入+(四)营业外收入+(五)其他-减:(一)营业外支出-(二)其他支出
		Double lrze = profitsReport.getLrze();
		Double lrzeSum = yylr + profitsReport.getYylr_jtzsy() + profitsReport.getYylr_qhsy() + profitsReport.getYylr_btsr() + yywsr + profitsReport.getYylr_qt()
				- jyywzc - profitsReport.getYylr_qtzc();
		if (lrze.doubleValue() != lrzeSum.doubleValue()) {
			msg.append("五.利润总额=四.营业利润+加:(一)投资收益+(二)期货收益+(三)补贴收入+(四)营业外收入+(五)其他-减:(一)营业外支出-(二)其他支出").append("<br>");
		}
		//六.净利润=五.利润总额-减:所得税-少数股东损益+加:未确认的投资损失
		Double jlr = profitsReport.getJlr();
		Double jlrSum = lrze - profitsReport.getLrze_jsds() - profitsReport.getLrze_ssgdsy() + profitsReport.getLrze_jwqrdtzss();
		if (jlr.doubleValue() != jlrSum.doubleValue()) {
			msg.append("六.净利润=五.利润总额-减:所得税-少数股东损益+加:未确认的投资损失").append("<br>");
		}
		//七.可供分配利润=六.净利润+加:(一)年初未分配利润+(二)盈余公积补亏+(三)其他调整因素
		Double kgfplr = profitsReport.getKgfplr();
		Double kgfplrSum = jlr + profitsReport.getJlr_jncwfplr() + profitsReport.getJlr_yygjbk() + profitsReport.getJlr_qttzys();
		if (kgfplr.doubleValue() != kgfplrSum.doubleValue()) {
			msg.append("七.可供分配利润=六.净利润+加:(一)年初未分配利润+(二)盈余公积补亏+(三)其他调整因素").append("<br>");
		}
		//七.可供分配利润  (三)提取盈余公积=其中:法定盈余公积金
		if (profitsReport.getKgfplr_tqyygj().doubleValue() != profitsReport.getKgfplr_qzfdyygjj().doubleValue()) {
			msg.append("七.可供分配利润  (三)提取盈余公积=其中:法定盈余公积金").append("<br>");
		}
		//八.可供投资者分配的利润=七.可供分配利润-减:(一)单项留用的利润-(二)补充流动资本-(三)提取盈余公积-(四)法定盈余公益金-(五)提取职工奖励及福利基金
		//		                -(六)提取储备基金-(七)提取企业发展基金-(八)利润归还投资-(九)其他
		Double kgtzzfpdlr = profitsReport.getKgtzzfpdlr();
		Double kgtzzfpdlrSum = kgfplr - profitsReport.getKgfplr_jdxlydlr() - profitsReport.getKgfplr_bcldzb() - profitsReport.getKgfplr_tqyygj()
				- profitsReport.getKgfplr_fdyygyj() - profitsReport.getKgfplr_tqzgjljfljj() - profitsReport.getKgfplr_tqcbjj() - profitsReport.getKgfplr_tqqyfzjj()
				- profitsReport.getKgfplr_lrghtz() - profitsReport.getKgfplr_qt();
		if (kgtzzfpdlr.doubleValue() != kgtzzfpdlrSum.doubleValue()) {
			msg.append("八.可供投资者分配的利润=七.可供分配利润-减:(一)单项留用的利润-(二)补充流动资本-(三)提取盈余公积-其中:法定盈余公积金-(四)法定盈余公益金-(五)提取职工奖励及福利基金-(六)提取储备基金-(七)提取企业发展基金-(八)利润归还投资-(九)其他").append("<br>");
		}
		//九:未分配利润=八.可供投资者分配的利润-减:(一)应付优先股股利-(二)提取任意盈余公积-(三)应付普通股股利-(四)转作股本的普通股股利-(五)其他
		Double wfplr = profitsReport.getWfplr();
		Double wfplrSum = kgtzzfpdlr - profitsReport.getKgtzzfpdlr_jyfyxggl() - profitsReport.getKgtzzfpdlr_tqryyygj() - profitsReport.getKgtzzfpdlr_yfptggl()
				- profitsReport.getKgtzzfpdlr_zzgbdptggl() - profitsReport.getKgtzzfpdlr_qt();
		if (wfplr.doubleValue() != wfplrSum.doubleValue()) {
			msg.append("九:未分配利润=八.可供投资者分配的利润-减:(一)应付优先股股利-(二)提取任意盈余公积-(三)应付普通股股利-(四)转作股本的普通股股利-(五)其他").append("<br>");
		}
		if (msg != null && !"".equals(msg.toString())) {
			msg.insert(0, "利润分配表表内关系应符合以下关系：<br>");
		}
		return msg.toString();
	}

	//现金流量表 表内关系校验
	private String checkCashflowsReportBN(CashflowsReport cashflowsReport){
		StringBuilder msg = new StringBuilder();
		if (cashflowsReport == null) {
			return msg.toString();
		}
		//一、经营活动产生的现金流量   现金流入小计=销售商品、提供劳务收到的现金+收到的税费返还+收到的其他与经营活动有关的现金
		Double jyhd_xjlrxj = cashflowsReport.getJyhd_xjlrxj();
		Double jyhdXjlrxjSum = cashflowsReport.getJyhd_xssp() + cashflowsReport.getJyhd_sddsffh() + cashflowsReport.getJyhd_sddqtjyygdxj();
		if (jyhd_xjlrxj.doubleValue() != jyhdXjlrxjSum.doubleValue()) {
			msg.append("一、经营活动产生的现金流量   现金流入小计=销售商品、提供劳务收到的现金+收到的税费返还+收到的其他与经营活动有关的现金").append("<br>");
		}
		//一、经营活动产生的现金流量   现金流出小计=购买商品、接受劳务支付的现金+经营租赁所支付的现金+支付给职工以及为职工支付的现金+支付的所得税款+支付的其他与经营活动有关的现金
		Double jyhd_xjlcxj = cashflowsReport.getJyhd_xjlcxj();
		Double jyhdXjlcxjSum = cashflowsReport.getJyhd_gmsp() + cashflowsReport.getJyhd_jyzlszfdxj() + cashflowsReport.getJyhd_zfyjzgzfxj()
				+ cashflowsReport.getJyhd_zfdsdsk() + cashflowsReport.getJyhd_zfdqtjyygdxj();
		if (jyhd_xjlcxj.doubleValue() != jyhdXjlcxjSum.doubleValue()) {
			msg.append("一、经营活动产生的现金流量   现金流出小计=购买商品、接受劳务支付的现金+经营租赁所支付的现金+支付给职工以及为职工支付的现金+支付的所得税款+支付的其他与经营活动有关的现金").append("<br>");
		}
		//一、经营活动产生的现金流量   经营活动产生的现金流量净额= 现金流入小计-现金流出小计
		Double jyhd_xjllje = cashflowsReport.getJyhd_jyhdccdxjllje();
		Double jyhdXjlljeSum = jyhd_xjlrxj - jyhd_xjlcxj;
		if (jyhd_xjllje.doubleValue() != jyhdXjlljeSum.doubleValue()) {
			msg.append("一、经营活动产生的现金流量   经营活动产生的现金流量净额= 现金流入小计-现金流出小计").append("<br>");
		}
		//二、投资活动产生的现金流量  现金流入小计=收回投资所收到的现金+分得股利或利润所收到的现金+处置固定资产无形资产和其他长期资产所收回的现金净额+收到的其他与投资活动有关的现金
		Double tzhd_xjlrxj = cashflowsReport.getTzhd_xjlrxj();
		Double tzhdXjlrxjSum = cashflowsReport.getTzhd_shtzssddxj()+cashflowsReport.getTzhd_fdglhlrssddxj()+cashflowsReport.getTzhd_czgdzchqtcqdxjje()+cashflowsReport.getTzhd_sddqttzygdxj();
		if (tzhd_xjlrxj.doubleValue() != tzhdXjlrxjSum.doubleValue()) {
			msg.append("二、投资活动产生的现金流量  现金流入小计=收回投资所收到的现金+分得股利或利润所收到的现金+处置固定资产无形资产和其他长期资产所收回的现金净额+收到的其他与投资活动有关的现金").append("<br>");
		}
		//二、投资活动产生的现金流量  现金流出小计=购建固定资产、无形资产和其他长期资产所支付的现金+债券性投资所支付的现金+支付的其他与投资活动有关的现金
		Double tzhd_xjlcxj = cashflowsReport.getTzhd_xjlcxj();
		Double tzhdXjlcxjSum = cashflowsReport.getTzhd_gjgdzcwxzccqzcxj() + cashflowsReport.getTzhd_zqxtzszfdxj() + cashflowsReport.getTzhd_zfdqttzdxj();
		if (tzhd_xjlcxj.doubleValue() != tzhdXjlcxjSum.doubleValue()) {
			msg.append("二、投资活动产生的现金流量  现金流出小计=购建固定资产、无形资产和其他长期资产所支付的现金+债券性投资所支付的现金+支付的其他与投资活动有关的现金").append("<br>");
		}
		//二、投资活动产生的现金流量  投资活动产生的现金流量净额=现金流入小计-现金流出小计
		Double tzhdXjllje = cashflowsReport.getTzhd_tzhdcsdxjllje();
		Double tzhdXjlljeSum = tzhd_xjlrxj - tzhd_xjlcxj;
		if (tzhdXjllje.doubleValue() != tzhdXjlljeSum.doubleValue()) {
			msg.append("二、投资活动产生的现金流量  投资活动产生的现金流量净额=现金流入小计-现金流出小计").append("<br>");
		}
		//三、筹资活动产生的现金流量  现金流入小计=吸收收益性投资所收到的现金+取得借款所收到的现金+收到的其他与筹资活动有关的现金
		Double czhd_xjlrxj = cashflowsReport.getCzhd_xjlrxj();
		Double czhdXjlrxjSum = cashflowsReport.getCzhd_xssyxtzssddxj() + cashflowsReport.getCzhd_qdjkssddxj() + cashflowsReport.getCzhd_sddqtczdxj();
		if (czhd_xjlrxj.doubleValue() != czhdXjlrxjSum.doubleValue()) {
			msg.append("三、筹资活动产生的现金流量  现金流入小计=吸收收益性投资所收到的现金+取得借款所收到的现金+收到的其他与筹资活动有关的现金").append("<br>");
		}
		//三、筹资活动产生的现金流量  现金流出小计=偿还债务所支付的现金+分配股利或利润所支付的现金+支付的其他与筹资活动有关的现金
		Double czhd_xjlcxj = cashflowsReport.getCzhd_xjlcxj();
		Double czhdXjlcxjSum = cashflowsReport.getCzhd_chzwszfdxj() + cashflowsReport.getCzhd_fpglhlrzfzfdxj() + cashflowsReport.getCzhd_zfdqtczyjdxj();
		if (czhd_xjlcxj.doubleValue() != czhdXjlcxjSum.doubleValue()) {
			msg.append("三、筹资活动产生的现金流量  现金流出小计=偿还债务所支付的现金+分配股利或利润所支付的现金+支付的其他与筹资活动有关的现金").append("<br>");
		}
		//三、筹资活动产生的现金流量  筹资活动产生的现金流量净额=现金流入小计-现金流出小计
		Double czhdXjllje = cashflowsReport.getCzhd_czhdcsdxjllje();
		Double czhdXjlljeSum = czhd_xjlrxj - czhd_xjlcxj;
		if (czhdXjllje.doubleValue() != czhdXjlljeSum.doubleValue()) {
			msg.append("三、筹资活动产生的现金流量  筹资活动产生的现金流量净额=现金流入小计-现金流出小计").append("<br>");
		}
		//五、现金及现金等价物净增加额=一、经营活动产生的现金流量 经营活动产生的现金流量净额+二、投资活动产生的现金流量 投资活动产生的现金流量净额+三、筹资活动产生的现金流量  筹资活动产生的现金流量净额+四、汇率变动对现金的影响
		Double xjjxjdjwjzje = cashflowsReport.getXjdjw();
		Double xjjxjdjwjzjeSum = cashflowsReport.getJyhd_jyhdccdxjllje() + cashflowsReport.getTzhd_tzhdcsdxjllje() + cashflowsReport.getCzhd_czhdcsdxjllje() + cashflowsReport.getHlbddxjdyx();
		if (xjjxjdjwjzje.doubleValue() != xjjxjdjwjzjeSum.doubleValue()) {
			msg.append("五、现金及现金等价物净增加额=一、经营活动产生的现金流量 经营活动产生的现金流量净额+二、投资活动产生的现金流量 投资活动产生的现金流量净额+三、筹资活动产生的现金流量  筹资活动产生的现金流量净额+四、汇率变动对现金的影响").append("<br>");
		}
		//五、现金及现金等价物净增加额  经营活动产生的现金流量净额=净利润+加:计提的坏帐准备或转销的坏帐+固定资产折旧+无形资产摊销+开办费、长期待摊费用摊销+待摊费用的减少（减增加）+预提费用的增加（减减少）+处置固定资产、无形资产和其他长期资产的损失（减收益）
		//							+固定资产报废损失+财务费用+投资损失（减收益）+递延税款贷项（减借项）+存货减少（减增加）+经营性应收项目的减少（减增加）+经营性应付项目的增加（减减少）+其他
		Double xjdjwXjllje = cashflowsReport.getXjdjw_jyhdccdxjllje();
		Double xjdjwXjlljeSum = cashflowsReport.getXjdjw_jlr() + cashflowsReport.getXjdjw_jjtdhzzx() + cashflowsReport.getXjdjw_gdzczj() + cashflowsReport.getXjdjw_wxzctx() + cashflowsReport.getXjdjw_kbfcqdtfy()
				+ cashflowsReport.getXjdjw_dtfydjs() + cashflowsReport.getXjdjw_ytfydzj() + cashflowsReport.getXjdjw_czgdzcwxzccqzcss() + cashflowsReport.getXjdjw_gdzcbfss()
				+ cashflowsReport.getXjdjw_cwfy() + cashflowsReport.getXjdjw_tzss() + cashflowsReport.getXjdjw_dyskdx() + cashflowsReport.getXjdjw_chjs() + cashflowsReport.getXjdjw_jyxssxmdjs()
				+ cashflowsReport.getXjdjw_jyxsfxmdzj() + cashflowsReport.getXjdjw_qt();
		if (xjdjwXjllje.doubleValue() != xjdjwXjlljeSum.doubleValue()) {
			msg.append("五、现金及现金等价物净增加额  经营活动产生的现金流量净额=净利润+加:计提的坏帐准备或转销的坏帐+固定资产折旧+无形资产摊销+开办费、长期待摊费用摊销+待摊费用的减少（减增加）+预提费用的增加（减减少）+处置固定资产、无形资产和其他长期资产的损失（减收益）+固定资产报废损失+财务费用+投资损失（减收益）+递延税款贷项（减借项）+存货减少（减增加）+经营性应收项目的减少（减增加）+经营性应付项目的增加（减减少）+其他").append("<br>");
		}
		//五、现金及现金等价物净增加额  现金及现金等价物净增加额=现金的期末余额-减：现金的期初余额+现金等价物的期末余额-减：现金等价物的期初余额
		Double xjdjw_xjdjwjzje = cashflowsReport.getXjdjw_xjdjwjzje();
		Double xjdjwXjdjwjzjesSum = cashflowsReport.getXjdjw_xjdqwye() - cashflowsReport.getXjdjw_jxjdqcye() + cashflowsReport.getXjdjw_xjdjwdqwye() -cashflowsReport.getXjdjw_jxjdjwdqcye();
		if (xjdjw_xjdjwjzje.doubleValue() != xjdjwXjdjwjzjesSum.doubleValue()) {
			msg.append("五、现金及现金等价物净增加额  现金及现金等价物净增加额=现金的期末余额-减：现金的期初余额+现金等价物的期末余额-减：现金等价物的期初余额").append("<br>");
		}
		if (msg != null && !"".equals(msg.toString())) {
			msg.insert(0, "现金流量表表内关系应符合以下关系：<br>");
		}
		return msg.toString();
	}

	/**
	 * 删除文件
	 * @author LJW
	 * date 2017-4-19
	 */
	@RequestMapping(value = "/deletUploadFileAjax")
	@ResponseBody
	public Map<String, Object> deletUploadFileAjax(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
		String newFileName = ajaxData;
		String allPath = uploadFinPath + File.separator + newFileName;
		try {
			File newFile = new File(allPath);
			if (newFile.isFile() && newFile.exists()) {
				newFile.delete();
			}
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_DELETE.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/toUpload")
	public String toUpload(Model model, String ajaxData) throws Exception{
		return "/component/pfs/CusFinUploadFiles";
	}

}
