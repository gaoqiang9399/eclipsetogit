package app.component.model.controller;
import java.io.File;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assure.entity.MfAssureInfo;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.model.entity.PageContent;
import app.component.model.entity.MfTemplateTagBase;
import app.component.model.entity.MfTagColumnConfig;
import app.component.model.entity.MfTemplateModelRel;
import app.component.model.entity.MfSysTemplate;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.entity.PageOfficePage;
import app.component.model.feign.MfToPageOfficeFeign;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.model.feign.MfTemplateModelRelFeign;
import app.component.model.feign.MfNewToPageOfficeFeign;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfNewTemplateTagBaseFeign;
import app.component.model.feign.MfNewTagColumnConfigFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.Md5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import config.YmlConfig;

/**
 * Title: MfSysModelAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 05 18:48:35 CST 2016
 **/
@Controller
@RequestMapping("/mfToPageOffice")
public class MfToPageOfficeController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfToPageOfficeFeign mfToPageOfficeFeign;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	@Autowired
	private MfTemplateModelRelFeign mfTemplateModelRelFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfNewToPageOfficeFeign mfNewToPageOfficeFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	@Autowired
	private MfNewTemplateTagBaseFeign mfNewTemplateTagBaseFeign;
	@Autowired
	private MfNewTagColumnConfigFeign mfNewTagColumnConfigFeign;
	// 使用pageoffice的版本标识 0：老版本 1：新版本
	@Value("${mftcc.pageoffice.office_version:0}")
	private String pageOfficeSts;
	//异步参数
	
	/**
	 * 
	 * 方法描述： 调用新版标签取值公共方法
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-9-8 上午10:03:51
	 */
	@ResponseBody
	@RequestMapping(value = "/getDataMapComm")
	public Map<String, Object> getDataMapComm(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> dataMapNew = new HashMap<String, Object>();
		try {
			String templateNo = request.getParameter("templateNo");
			String modelId = request.getParameter("modelId");
			String pactId = request.getParameter("pactId");
			String appId = request.getParameter("appId");
			String pledgeNo = request.getParameter("pledgeNo");
			String fincId = request.getParameter("fincId");
			String repayDetailId = request.getParameter("repayDetailId");
			String cusNo = request.getParameter("cusNo");
			String filename = request.getParameter("filename");
			String oaAppId = request.getParameter("oaAppId");//办公业务申请号
			String oaBaseId = request.getParameter("oaBaseId");//人事基本信息编号

			Map<String, String> bookParmMap = new HashMap<String, String>();
			bookParmMap.put("modelId", modelId);
			bookParmMap.put("filename", filename);
			bookParmMap.put("templateNo", templateNo);
			bookParmMap.put("cusNo", cusNo);
			bookParmMap.put("pactId", pactId);
			bookParmMap.put("appId", appId);
			bookParmMap.put("pledgeNo", pledgeNo);
			bookParmMap.put("fincId", fincId);
			bookParmMap.put("repayDetailId", repayDetailId);
			bookParmMap.put("oaAppId", oaAppId);
			bookParmMap.put("oaBaseId", oaBaseId);
			bookParmMap.put("regNo", User.getRegNo(request));
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String webPath = ymlConfig.getWebservice().get("webPath");
			if(webPath.startsWith("/")){
				basePath+=webPath;
			}else{
				basePath= basePath+"/"+webPath;
			}
			if("0".equals(pageOfficeSts)){
				dataMap = mfToPageOfficeFeign.getDataMapComm(basePath, bookParmMap);
			}else if("1".equals(pageOfficeSts)){
				dataMap = mfNewToPageOfficeFeign.getDataMapComm(bookParmMap);
			}else {
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMapNew;
	}
	
	/**
	 * 
	 * 方法描述： pageoffice新增入口
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2017-8-21 下午5:39:25
	 */
	@RequestMapping(value = "/mfPageOffice")
	public String mfPageOffice(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		PageContent poCnt = null;
		PageOfficePage poPage = null;
		String bookParmStr = "";
		try {
			bookParmStr = request.getParameter("bookParmStr");
			bookParmStr = URLDecoder.decode(bookParmStr, "utf-8");
			Map<String, String> bookParmMap = gson.fromJson(bookParmStr, new TypeToken<Map<String, Object>>() {
			}.getType());
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String webPath = ymlConfig.getWebservice().get("webPath");
			if(webPath.startsWith("/")){
				basePath+=webPath;
			}else{
				basePath= basePath+"/"+webPath;
			}
			bookParmMap.put("regNo", User.getRegNo(request));
			String poCntStr = mfToPageOfficeFeign.getPageContent(basePath, bookParmMap);
			poCnt = JSON.parseObject(poCntStr, PageContent.class);
			poPage=PageOfficePage.getAddPO(poCnt, request);
			request.setAttribute("poPage",poPage);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("query", "");
		return "/component/model/mfPageOffice";
//		return "/component/model/mfPageOfficeBackGroud";
	}
	
	/**
	 * 
	 * 方法描述： pageofice编辑文档
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2017-8-29 下午2:42:17
	 */
	@RequestMapping(value = "/pageOfficeEdit")
	public String pageOfficeEdit(Model model, String ajaxData) throws Exception {
		Logger logger = LoggerFactory.getLogger(MfToPageOfficeController.class);
		try{
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		//接收页面设置及内容json字符串
		String poCntJson=URLDecoder.decode(request.getParameter("poCnt"),"UTF-8");
		//转换成实体
		PageContent poCnt=gson.fromJson(poCntJson,PageContent.class);
		String fileName = poCnt.getPathFileName();
		fileName = fileName.trim();
		fileName = fileName.substring(fileName.lastIndexOf("/")+1);
		String wholeFileName = fileName;
		fileName = fileName.split("\\.")[0];
		//系统模板设计保存路径
		if("templateFile".equals(fileName)){
			String savePath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
			if(StringUtil.isEmpty(savePath)){
				logger.error("pageoffice -> office_sys_template 模板路径没有配置");
			}
			poCnt.setSavePath(savePath);
		}else{
			//其他模板保存过后，再次打开模板需要根据后台数据字典项里TEMPLATE_TYPE 类型的remark判断 0 - 不允许修改 1-允许修改
			MfTemplateBizConfig parmConfig = new  MfTemplateBizConfig();
			parmConfig.setDocFileName(wholeFileName);
			List<MfTemplateBizConfig> configList = mfTemplateBizConfigFeign.getBizConfigList(parmConfig);
			if(configList != null && configList.size()>0){
				String templateType = configList.get(0).getTemplateType();
				if(templateType == null || "".equals(templateType)){
					MfTemplateModelRel parmModelRel = new MfTemplateModelRel();
					parmModelRel.setDocFileName(wholeFileName);
					MfTemplateModelRel mfTemplateModelRel = mfTemplateModelRelFeign.getById(parmModelRel);
					templateType = mfTemplateModelRel.getTemplateType();
				}
				Map<String, ParmDic> templateTypeMap = new CodeUtils().getMapObjByKeyName("TEMPLATE_TYPE");
				String remark=templateTypeMap.get(templateType).getRemark();
				if("0".equals(remark)){
					poCnt.setReadOnly("1");
				}else{
					poCnt.setReadOnly("0");
				}
			}
		}
		//文档保存后调用的页面
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String webPath = ymlConfig.getWebservice().get("webPath");
		if(webPath.startsWith("/")){
			basePath+=webPath;
		}else{
			basePath= basePath+"/"+webPath;
		}
		String waterId = WaterIdUtil.getWaterId();
		String saveUrl = poCnt.getSaveUrl();
		if(!"".equals(StringUtil.KillBlankAndTrim(saveUrl,""))){
			StringBuilder saveUrlStr = new StringBuilder();
			saveUrlStr.append(basePath+"/"+saveUrl);
			saveUrlStr.append("&waterId=").append(waterId);
			saveUrlStr.append("&pageOffice=").append(Md5Util.getMD5Str(waterId+"pass"));
			poCnt.setSaveUrl(saveUrlStr.toString());
		}
		PageOfficePage poPage=PageOfficePage.getEditPO(poCnt, request);
		request.setAttribute("basePath",basePath);
		request.setAttribute("poPage",poPage);
		model.addAttribute("query", "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/component/model/mfPageOffice";
	}
	
	/**
	 * 
	 * 方法描述： 在线打开pdf
	 * @return
	 * @throws Exception
	 * String
	 * @author lwq
	 * @date 2017-12-1 上午11:36:38
	 */
	@RequestMapping(value = "/mfOpenPdf")
	public String mfOpenPdf(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		//接收页面设置及内容json字符串
		String poCntJson=URLDecoder.decode(request.getParameter("poCnt"),"UTF-8");
		//转换成实体
		PageContent poCnt=gson.fromJson(poCntJson,PageContent.class);
		PageOfficePage poPdfPage = PageOfficePage.getPdfPO(poCnt, request);
		request.setAttribute("poPdfPage",poPdfPage);
		model.addAttribute("query", "");
		return "/component/model/mfOpenPdf";
	}


	@ResponseBody
	@RequestMapping(value = "/showHtmlTemplate")
	public void showHtmlTemplate(Model model, String templateBizConfigId,String paramStr) throws Exception {
		ActionContext.initialize(request, response);
		Gson gson = new Gson();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PrintWriter printWriter = null;
		try {
			paramStr = URLDecoder.decode(paramStr, "utf-8");
			Map<String, String> bookParmMap = gson.fromJson(paramStr, new TypeToken<Map<String, Object>>() {}.getType());
			bookParmMap.put("templateBizConfigId", templateBizConfigId);
			String result = mfToPageOfficeFeign.showHtmlTemplatePage(bookParmMap);
			printWriter = new PrintWriter(response.getOutputStream());
			printWriter.print(result);
			printWriter.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			if (printWriter != null) {
				try {
					printWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	@ResponseBody
	@RequestMapping(value = "/getHtmlTemplate" ,produces="text/html;charset=utf-8")
	public String getHtmlTemplate(String filePath) throws Exception {
		ActionContext.initialize(request, response);
		String result = "";
		Gson gson = new Gson();
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(filePath));
			bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				line = bufferedReader.readLine();
			}
			result = stringBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}



	private Map<String,String> getParmStrMap(String templateBizConfigId, String cusNo, String appId, String pactId,
											 String fincId, String repayDetailId, String collateralId) throws Exception{
		Gson gson = new Gson();
		Map<String, String> resultMap = new HashMap<>();
		Logger logger = LoggerFactory.getLogger(MfToPageOfficeController.class);
		if(StringUtil.isEmpty(templateBizConfigId) || StringUtil.isEmpty(appId)){
			resultMap.put("flag", "error");
			resultMap.put("msg", "templateBizConfigId/appId不能为空！");
			return resultMap;
		}
		// 查询当前配置
		MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
		templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
		templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);


		if(StringUtil.isNotEmpty(templateBizConfig.getTemplateNo())){
			//获取模版要生成文件类型标识
			MfSysTemplate mfSysTemplate = new MfSysTemplate();
			mfSysTemplate.setTemplateNo(templateBizConfig.getTemplateNo());
			mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
			resultMap.put("changeType", mfSysTemplate.getChangeType());
		}
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = appInterfaceFeign.getMfBusApply(mfBusApply);
		if(mfBusApply != null){
			resultMap.put("kindNo", mfBusApply.getKindNo());//获取产品编号
		}
		if (StringUtil.isNotEmpty(templateBizConfig.getDocFileName())) {// 已保存过
			resultMap.put("filePath", templateBizConfig.getDocFilePath());// 打开文件路径
			resultMap.put("fileName", templateBizConfig.getDocFileName());// 打开文件名
		} else {// 未保存过
			resultMap.put("fileName", templateBizConfig.getTemplateNameEn());// 打开文件名
		}
		if(cusNo == null || "".equals(cusNo)){
			// 主要参数赋值
			cusNo = setAttributeByAppId(appId);
		}
		// 查询当前模板对应的标签数据
		resultMap.put("templateNo", templateBizConfig.getTemplateNo());
		resultMap.put("templateSuffix", templateBizConfig.getTemplateSuffix());
		resultMap.put("templateBizConfigId", templateBizConfig.getTemplateBizConfigId());
		//获取 押品保证信息id
		if(StringUtil.isNotEmpty(appId)){
			//抵质押信息列表
			List<PledgeBaseInfo>  pledgeBaseInfoList = null;
			//保证信息列表
			List<MfAssureInfo> assureInfoList = null;
			List<MfBusCollateralDetailRel> collateralDetailRelList = collateralInterfaceFeign.getCollateralDetailRelList(appId);;
			if(collateralDetailRelList!=null&&collateralDetailRelList.size()>0){
				pledgeBaseInfoList = new ArrayList<PledgeBaseInfo>();
				assureInfoList = new ArrayList<MfAssureInfo>();
				StringBuffer pleId = new StringBuffer();
				for(MfBusCollateralDetailRel relbean  : collateralDetailRelList){
					String colateralId = relbean.getCollateralId();//押品编号
					//押品信息id
					pleId.append(",").append(colateralId);
					PledgeBaseInfo pledgebean = new PledgeBaseInfo();
					pledgebean.setPledgeNo(colateralId);
					PledgeBaseInfo plbean = collateralInterfaceFeign.getById(pledgebean);//获取押品实体类
					if(plbean!=null){
						//获取押品类型，这里只想要房屋的
						String classId = plbean.getClassId();
						MfCollateralClass mfCollateralClass = new MfCollateralClass();
						mfCollateralClass.setClassId(classId);
						mfCollateralClass = collateralInterfaceFeign.getCollateralClassById(mfCollateralClass);//获取类别
						if(mfCollateralClass!=null){
							String classFirstNo = mfCollateralClass.getClassFirstNo();
							if("B".equals(classFirstNo)){//是房屋类型的就存押品
								resultMap.put("pledgeNo", colateralId);
							}
						}
						pledgeBaseInfoList.add(plbean);
					}

					//保证信息查询
					MfAssureInfo assureInfo = new MfAssureInfo();
					assureInfo.setId(colateralId);
					assureInfo = assureInterfaceFeign.getById(assureInfo);
					if(assureInfo != null){
						assureInfoList.add(assureInfo);
					}

				}
				String pledgeAssureId = pleId.toString();
				if(pledgeAssureId.length()>1){
					pledgeAssureId = pledgeAssureId.substring(1);
					resultMap.put("pledgeAssureId", pledgeAssureId);
				}
			}
		}
		resultMap.put("cusNo", cusNo);
		resultMap.put("pactId", pactId);
		resultMap.put("appId", appId);
		resultMap.put("fincId", fincId);
		resultMap.put("collateralId", collateralId);
		resultMap.put("opName", User.getRegName(request));
		resultMap.put("orgNo", User.getOrgNo(request));
		resultMap.put("orgName", User.getOrgName(request));
		resultMap.put("repayDetailId", repayDetailId);
		resultMap.put("flag", "success");
		return resultMap;
	}


	private String setAttributeByAppId(String appid) throws Exception {
		String cusNo = null;
		if(appid != null && appid.contains("SX")){
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(appid);
			mfCusCreditApply = creditApplyInterfaceFeign.getCusCreditApply(mfCusCreditApply);
			cusNo = mfCusCreditApply.getCusNo();
		}else{
			MfBusApply mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appid);
			cusNo = mfBusApply.getCusNo();
		}
		return cusNo;
	}


	private Map<String, Object> creditHtmlFile(MfTemplateBizConfig templateBizConfig,Map<String,String> bookParmMap) throws  Exception{
		MfTemplateTagBase mfTemplateTagBase = null;
		MfTagColumnConfig mfTagColumnConfig = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		List<List<String>> rowsList = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			//文件路径
			String fileDestPath = templateBizConfig.getDocFilePath();
			//文件名称
			String fileDestName = templateBizConfig.getDocFileName();
			//1.读取对应模板的内容
			String fileSrcPath = PropertiesUtil.getPageOfficeConfigProperty("office_sys_template");
			if (StringUtil.isEmpty(fileSrcPath)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "pageOffice路径office_sys_template没有配置");
				return dataMap;
			}
			String fileSrc = fileSrcPath + templateBizConfig.getTemplateNameEn();
			inputStreamReader = new InputStreamReader(new FileInputStream(fileSrc));
			bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				line = bufferedReader.readLine();
			}
			//2.替换文件中的标签
			Map<String, Object> bookMap = mfNewToPageOfficeFeign.getDataMapComm(bookParmMap);
			Map<String, String> columnMap = (Map<String, String>) bookMap.get("column");
			Map<String, Object> tableMap = (Map<String, Object>) bookMap.get("table");
			String str = stringBuffer.toString();
			if(columnMap!=null){
				Iterator columnIt = columnMap.entrySet().iterator();
				while (columnIt.hasNext()) {
					Map.Entry entry = (Map.Entry) columnIt.next();
					str = str.replace((String)entry.getKey(), (String)entry.getValue());
				}
			}
			if(tableMap!=null){
				Iterator itMap = tableMap.entrySet().iterator();
				while (itMap.hasNext()) {
					Map.Entry entry = (Map.Entry) itMap.next();
					Map<String, Object> commonTable = (Map<String, Object>) entry.getValue();
					if(commonTable != null ){
						rowsList = (List<List<String>>) commonTable.get("rows");
						if(rowsList != null && rowsList.size()>0){
							mfTemplateTagBase = new MfTemplateTagBase();
							mfTemplateTagBase.setPoName(String.valueOf(entry.getKey()));
							mfTemplateTagBase = mfNewTemplateTagBaseFeign.getById(mfTemplateTagBase);
							if(mfTemplateTagBase != null){
								mfTagColumnConfig = new MfTagColumnConfig();
								mfTagColumnConfig.setKeyNo(mfTemplateTagBase.getKeyNo());
								List<MfTagColumnConfig> columList = mfNewTagColumnConfigFeign.getMfTagColumnConfigList(mfTagColumnConfig);
								str = createTableHtml(columList,rowsList,str,mfTemplateTagBase);
							}
						}
					}
				}

			}
			//3.生成新的文件
			String destPath = PropertiesUtil.getPageOfficeConfigProperty("office_user_template");
			if (StringUtil.isEmpty(destPath)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "pageOffice路径office_user_template没有配置");
				return dataMap;
			}
			if(StringUtil.isEmpty(fileDestPath)){
				fileDestPath = destPath + bookParmMap.get("cusNo")+"/";
				fileDestName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+".html";
			}
			destPath = fileDestPath + fileDestName;
			File file = new File(destPath);
			if(file.exists()){
				file.delete();
			}
			File fileParent = file.getParentFile();
			if(!fileParent.exists()){
				fileParent.mkdirs();
			}
			file.createNewFile();
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			bufferedWriter.write(str);
			bufferedWriter.flush();

			templateBizConfig.setDocFilePath(fileDestPath);
			templateBizConfig.setDocFileName(fileDestName);
			MfTemplateBizConfig tbc = new MfTemplateBizConfig();
			tbc.setTemBizNo(templateBizConfig.getTemBizNo());
			tbc.setTemplateNo(templateBizConfig.getTemplateNo());
			tbc.setDocFilePath(fileDestPath);
			tbc.setDocFileName(fileDestName);
			tbc.setLstModTime(DateUtil.getDateTime());// 最后修改时间 格式：yyyyMMdd HH:mm:ss
			if (StringUtil.isEmpty(templateBizConfig.getDocFileName())) {// 之前还未上传，记录登记时间
				tbc.setRegTime(DateUtil.getDateTime());// 登记时间 格式：yyyyMMdd HH:mm:ss
			}
			mfTemplateBizConfigFeign.update(tbc);
			dataMap.put("flag", "success");
			dataMap.put("filePath", destPath);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if (inputStreamReader != null) {
				try {
					inputStreamReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dataMap;
	}

	private String createTableHtml(List<MfTagColumnConfig> columList, List<List<String>> rowsList, String str,MfTemplateTagBase mfTemplateTagBase) throws  Exception{
		int divWidth = columList == null?rowsList.get(0).size()*10:columList.size()*10;
		StringBuffer htmlStr = new StringBuffer("<div style=\"margin: 0 auto;").append("width:").append(divWidth).append("%").append("\">");
		htmlStr.append("<table style=\"border-collapse:collapse;width:100%\">");
		if(columList != null && columList.size()>0){
			htmlStr.append("<tr>");
			for(int i=0; i<columList.size();i++){
				htmlStr.append("<th style=\"border:1px solid #8E8E8E;\">").append(columList.get(i).getColumnName()).append("</th>");
			}
			htmlStr.append("</tr>");
			for(List<String> list : rowsList){
				htmlStr.append("<tr>");
				for(String value : list){
					htmlStr.append("<td style=\"border:1px solid #8E8E8E;text-align: center;\">");
					htmlStr.append(value);
					htmlStr.append("</td>");
				}
				htmlStr.append("</tr>");
			}
		}
		htmlStr.append("</table></div>");
		return  str.replace(mfTemplateTagBase.getTagKeyName(), htmlStr.toString());
	}


	/**
	 *
	 * @param templateBizConfigId
	 * @param cusNo
	 * @param appId
	 * @param pactId
	 * @param fincId
	 * @param repayDetailId
	 * @param collateralId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHtmlTemplateUrl")
	@ResponseBody
	public Map<String, Object> getHtmlTemplateUrl(String templateBizConfigId, String cusNo,String appId, String pactId,
												  String fincId, String repayDetailId,String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> parmMap = null;
		try{
			parmMap = this.getParmStrMap(templateBizConfigId,cusNo,appId,pactId,fincId,repayDetailId,collateralId);
			if (parmMap != null && "success".equals(parmMap.get("flag"))) {
				MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
				templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
				templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);
				resultMap = creditHtmlFile(templateBizConfig,parmMap);
			}else{
				resultMap.put("flag", "error");
				resultMap.put("msg", parmMap.get("msg"));
			}
		}catch(Exception e){
			resultMap.put("flag", "error");
			resultMap.put("msg", MessageEnum.ERROR_SERVER.getMessage());
		}
		return resultMap;
	}

	/**
	 *
	 * @param templateBizConfigId
	 * @param cusNo
	 * @param appId
	 * @param pactId
	 * @param fincId
	 * @param repayDetailId
	 * @param collateralId
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHtmlTemplatePage")
	@ResponseBody
	public void getHtmlTemplatePage(String templateBizConfigId, String cusNo,String appId, String pactId,
												  String fincId, String repayDetailId,String collateralId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> parmMap = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try{
			parmMap = this.getParmStrMap(templateBizConfigId,cusNo,appId,pactId,fincId,repayDetailId,collateralId);
			if (parmMap != null && "success".equals(parmMap.get("flag"))) {
				MfTemplateBizConfig templateBizConfig = new MfTemplateBizConfig();
				templateBizConfig.setTemplateBizConfigId(templateBizConfigId);
				templateBizConfig = mfTemplateBizConfigFeign.getById(templateBizConfig);
				resultMap = creditHtmlFile(templateBizConfig,parmMap);
				response.reset();
				response.setHeader("content-type", "text/html;charset=UTF-8");
				inputStream = new BufferedInputStream(new FileInputStream(resultMap.get("filePath").toString()));
				byte[] buffer = new byte[inputStream.available()];
				inputStream.read(buffer);
				outputStream = new BufferedOutputStream(response.getOutputStream());
				outputStream.write(buffer);
				outputStream.flush();
			}
		}catch(Exception e){
			throw e;
		}finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
