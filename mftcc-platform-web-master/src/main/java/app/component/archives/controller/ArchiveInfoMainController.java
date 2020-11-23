package app.component.archives.controller;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.base.cacheinterface.BusiCacheInterface;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.archives.entity.*;
import app.component.archives.feign.*;
import app.component.auth.entity.MfCusAgenciesCredit;
import app.component.auth.entity.MfCusCreditApply;
import app.component.auth.feign.MfCusCreditApplyFeign;
import app.component.collateral.entity.*;
import app.component.collateral.feign.CertiInfoFeign;
import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.collateral.feign.MfBusCollateralRelFeign;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfBusAgencies;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfBusAgenciesFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.doc.feign.DocFeign;
import app.component.doc.feign.DocManageFeign;
import app.component.doc.feign.DocTypeConfigFeign;
import app.component.model.entity.MfSysTemplate;
import app.component.model.feign.MfSysTemplateFeign;
import app.component.model.feign.MfTemplateBizConfigFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPactExtend;
import app.component.pact.feign.MfBusPactExtendFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.repay.entity.MfDeductRefundApply;
import app.component.prdct.entity.MfKindFlow;
import cn.mftcc.util.*;
import com.core.struts.taglib.JsonFormUtil;
import config.YmlConfig;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.taskdefs.BZip2;
import org.eclipse.jdt.internal.compiler.tool.Archive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.ServiceException;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pactinterface.PactInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: ArchiveInfoMainAction.java
 * Description:归档主信息
 * @author:yudongwei@mftcc.cn
 * @Fri Apr 07 13:45:47 CST 2017
 **/
@Controller
@RequestMapping("/archiveInfoMain")
public class ArchiveInfoMainController extends BaseFormBean{

	private static Logger log = LoggerFactory.getLogger(ArchiveInfoMainController.class);
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveInfoMainFeign archiveInfoMainFeign;
	/** 归档明细信息Feign */
	@Autowired
	private ArchiveInfoDetailFeign archiveInfoDetailFeign;
	@Autowired
	private ArchiveConfigFeign archiveConfigFeign;
	/** 合同接口 */
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private ArchiveInfoDetailLogFeign archiveInfoDetailLogFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
    private DocFeign docFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfTemplateBizConfigFeign mfTemplateBizConfigFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private MfBusCollateralRelFeign mfBusCollateralRelFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;
	@Autowired
	private DocManageFeign docManageFeign;
	@Autowired
	private DocTypeConfigFeign docTypeConfigFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private CertiInfoFeign certiInfoFeign;
	@Autowired
	private BusiCacheInterface busiCacheInterface;
	@Autowired
	private MfBusPactExtendFeign mfBusPactExtendFeign;
	@Autowired
	private ArchiveInfoBorrowDetailFeign archiveInfoBorrowDetailFeign;
	@Autowired
	private ArchiveInfoBorrowFeign archiveInfoBorrowFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusCreditApplyFeign mfCusCreditApplyFeign;
	@Autowired
	private MfBusAgenciesFeign mfBusAgenciesFeign;
	//全局变量
	/** 下载文件名称 */
	/** 归档文件借阅信息（包括归档明细信息和归档文件操作日志）集合 */
	/** 归档文件合并信息集合（包括归档明细信息和文件操作日志） */
	/** 申请号 */
	/** 合同信息 */
	//表单变量
	
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveInfoDetailLog archiveInfoDetailLog) throws Exception {
		Map<String,String> dataMap = new HashMap<String,String>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveInfoDetailLogFeign.getAll(archiveInfoDetailLog), null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		JSONArray archiveStatusJsonArray = new CodeUtils().getJSONArrayByKeyName("ARCHIVE_STATUS");
		this.getHttpRequest().setAttribute("archiveStatusJsonArray", archiveStatusJsonArray);
		model.addAttribute("query", "");
		return "component/archives/ArchiveInfoMain_List";
	}

	/**
	 * 选择归档项目弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveCusList")
	public String getArchiveCusList(Model model,String archiveStatus) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		model.addAttribute("archiveStatus",archiveStatus);
		return "component/archives/ArchiveInfoMain_SelectCus";
	}

	/***
	 * 在借阅/借出申请以及档案封存时候选择项目（需要对项目进行去重）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCusByPageAjax")
	@ResponseBody
	public Map<String, Object> findCusByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findCusByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 选择归档项目弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveAppNameList")
	public String getArchiveAppNameList(Model model,String cusNo,String archiveStatus, String dealMode,String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("archiveStatus",archiveStatus);
		model.addAttribute("archivePactStatus",archivePactStatus);
		model.addAttribute("dealMode",dealMode);
		return "component/archives/ArchiveInfoMain_SelectAppName";
	}

	/***
	 * 在借阅/借出申请以及档案封存时候选择项目（需要对项目进行去重）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAppByPageAjax")
	@ResponseBody
	public Map<String, Object> findAppByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus,String cusNo,String dealMode,String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			if(cusNo != null && !"".equals(cusNo)){
				archiveInfoMain.setCusNo(cusNo);
			}
			if(dealMode != null && !"".equals(dealMode)){
				archiveInfoMain.setDealMode(dealMode);
			}
			if(StringUtil.isNotEmpty(archivePactStatus)){
				archiveInfoMain.setArchivePactStatus(archivePactStatus);
			}

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findAppByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 选择归档项目弹窗--档案封存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveBlockAppList")
	public String getArchiveBlockAppList(Model model,String cusNo,String archiveStatus, String dealMode) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("archiveStatus",archiveStatus);
		model.addAttribute("dealMode",dealMode);
		return "component/archives/ArchiveInfoMain_SelectBlockApp";
	}

	/***
	 * 在档案封存时候选择项目（需要对项目进行去重）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findBlockAppByPageAjax")
	@ResponseBody
	public Map<String, Object> findBlockAppByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus,String cusNo,String dealMode) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			if(cusNo != null && !"".equals(cusNo)){
				archiveInfoMain.setCusNo(cusNo);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findBlockAppByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 选择授信弹窗--借阅/借出
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveCreditListForBorrow")
	public String getArchiveCreditListForBorrow(Model model,String cusNo,String archiveStatus, String dealMode, String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("archiveStatus",archiveStatus);
		model.addAttribute("archivePactStatus",archivePactStatus);
		model.addAttribute("dealMode",dealMode);
		return "component/archives/ArchiveInfoMain_SelectCreditForBorrow";
	}

	/***
	 * 在借阅/借出申请时候选择授信
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCreditForBorrowAjax")
	@ResponseBody
	public Map<String, Object> findCreditForBorrowAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus,String cusNo,String dealMode, String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值gge
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			if(cusNo != null && !"".equals(cusNo)){
				archiveInfoMain.setCusNo(cusNo);
			}
			if(dealMode != null && !"".equals(dealMode)){
				archiveInfoMain.setDealMode(dealMode);
			}
			if(archivePactStatus != null && !"".equals(archivePactStatus)){
				archiveInfoMain.setArchivePactStatus(archivePactStatus);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findCreditForBorrowAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();

			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getArchiveAppNameListForBorrow")
	public String getArchiveAppNameListForBorrow(Model model,String cusNo,String archiveStatus, String dealMode, String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		model.addAttribute("cusNo",cusNo);
		model.addAttribute("archiveStatus",archiveStatus);
		model.addAttribute("archivePactStatus",archivePactStatus);
		model.addAttribute("dealMode",dealMode);
		return "component/archives/ArchiveInfoMain_SelectAppNameForBorrow";
	}

	/***
	 * 在借阅/借出申请时候选择项目（需要对项目进行去重）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAppByPageForBorrowAjax")
	@ResponseBody
	public Map<String, Object> findAppByPageForBorrowAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus,String cusNo,String dealMode, String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值gge
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			if(cusNo != null && !"".equals(cusNo)){
				archiveInfoMain.setCusNo(cusNo);
			}
			if(dealMode != null && !"".equals(dealMode)){
				archiveInfoMain.setDealMode(dealMode);
			}
			if(archivePactStatus != null && !"".equals(archivePactStatus)){
				archiveInfoMain.setArchivePactStatus(archivePactStatus);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findAppByPageForBorrowAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();

			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 资料合同归档申请列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForApply")
	public String getListPageForApply(Model model) throws Exception {
		ActionContext.initialize(request,response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
		model.addAttribute("statusJsonArray", statusJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_MainList";
	}

	/***
	 * 归档申请列表查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findApplyByPageAjax")
	@ResponseBody
	public Map<String, Object> findApplyByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String type) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setType(type);
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findApplyByPageAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 凭证归档申请列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForVoucherApply")
	public String getListPageForVoucherApply(Model model) throws Exception {
		ActionContext.initialize(request,response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
		model.addAttribute("statusJsonArray", statusJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_MainVoucherList";
	}

	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveInfoMainByIdAjax")
	@ResponseBody
	public Map<String, Object> getArchiveInfoMainByIdAjax(String archiveMainNo,String appId,String creditAppId, String archivePactStatus) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
			ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
			if("01".equals(archivePactStatus)){//授信
				if(creditAppId != null && !"".equals(creditAppId)){
					archiveInfoMain.setCreditAppId(creditAppId);
					archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMain);
					int applyNum = archiveInfoMainList.size();
					if(applyNum > 0){
						archiveInfoMainList.get(0).setApplyNum(String.valueOf(applyNum));
						dataMap.put("archiveInfoMain",archiveInfoMainList.get(0));
						dataMap.put("flag","success");
					}
				}else if(archiveMainNo != null && !"".equals(archiveMainNo)){
					archiveInfoMain.setArchiveMainNo(archiveMainNo);
					archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
					dataMap.put("archiveInfoMain",archiveInfoMain);
					dataMap.put("flag","success");
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", "归档信息查询失败");
				}
			}else{
				if(appId != null && !"".equals(appId)){
					archiveInfoMain.setAppId(appId);
					archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMain);
					int applyNum = archiveInfoMainList.size();
					if(applyNum > 0){
						archiveInfoMainList.get(0).setApplyNum(String.valueOf(applyNum));
						dataMap.put("archiveInfoMain",archiveInfoMainList.get(0));
						dataMap.put("flag","success");
					}
				}else if(archiveMainNo != null && !"".equals(archiveMainNo)){
					archiveInfoMain.setArchiveMainNo(archiveMainNo);
					archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
					dataMap.put("archiveInfoMain",archiveInfoMain);
					dataMap.put("flag","success");
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", "归档项目信息查询失败");
				}
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 归档确认列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForConfim")
	public String getListPageForConfim(Model model) throws Exception {
		ActionContext.initialize(request,response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
		model.addAttribute("statusJsonArray", statusJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_ConfimList";
	}

	/**
	 * 档案封存列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForBlock")
	public String getListPageForBlock(Model model) throws Exception {
		ActionContext.initialize(request,response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray statusJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_STATUS");
		model.addAttribute("statusJsonArray", statusJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_BlockList";
	}
	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String archiveStatus,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			if(archiveStatus != null && !"".equals(archiveStatus)){
				archiveInfoMain.setArchiveStatus(archiveStatus);
			}
			if(cusNo != null && !"".equals(cusNo)){
				archiveInfoMain.setCusNo(cusNo);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 20200911 凭证归档可选择银行授信和业务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCreditAndApplyList")
	public String getCreditAndApplyList(Model model, String type, String cusName) throws Exception {
		ActionContext.initialize(request,response);
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		if(StringUtil.isNotEmpty(cusName)){
			archiveInfoMain.setCusName(cusName);
		}
		Ipage ipage = this.getIpage();
		ipage.setPageSize(1);
		ipage.setPageNo(1);//异步传页面翻页参数
		ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
		//自定义查询Bo方法
		List<ArchiveInfoMain> archiveInfoMainList = archiveInfoMainFeign.findCreditAndApplyList(ipage);
		model.addAttribute("query", "");
		model.addAttribute("type", type);
		model.addAttribute("archiveInfoMainList", archiveInfoMainList);
		return "component/archives/ArchiveInfoMain_SelectCreditAndApply";
	}

	/***
	 * 选择纸质归档类别
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findCreditAndApplyList")
	@ResponseBody
	public Map<String, Object> findCreditAndApplyList(Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			List<ArchiveInfoMain> archiveInfoMainList = archiveInfoMainFeign.findCreditAndApplyList(ipage);
			/*ipage = archiveInfoMainFeign.findCreditAndApplyList(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);*/
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 档案查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForQuery")
	public String getListPageForQuery(Model model,String appId,String docType) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray docTypeJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_TYPE");
		model.addAttribute("docTypeJsonArray", docTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_ListForQuery";
	}

	/***
	 * 档案查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findQueryByPage")
	@ResponseBody
	public Map<String, Object> findQueryByPage(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findQueryByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 凭证查询
	 * @param model
	 * @param appId
	 * @param docType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForVoucherQuery")
	public String getListPageForVoucherQuery(Model model,String appId,String docType) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray docTypeJsonArray = codeUtils.getJSONArrayByKeyName("ARCHIVE_TYPE");
		model.addAttribute("docTypeJsonArray", docTypeJsonArray);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_ListForVoucherQuery";
	}

	/***
	 * 凭证查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findVoucherQueryByPage")
	@ResponseBody
	public Map<String, Object> findVoucherQueryByPage(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try {
			archiveInfoMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoMain.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoMain.setCriteriaList(archiveInfoMain, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findQueryByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


	/**
	 * 资料合同归档申请--第一视角
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivemaininsert = formService.getFormData("archivemaininsert");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getRegName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		getObjValue(formarchivemaininsert, archiveInfoMain);
		model.addAttribute("formarchivemaininsert", formarchivemaininsert);
		model.addAttribute("isShow", true);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_Insert";
	}

	/**
	 * 资料合同-归档申请界面--归档阶段选择业务
	 * 资料/合同需要在拿到原始上传信息后，需要处理，在库的资料不必要展示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBusApply")
	public String inputBusApply(Model model, String appId,String archivePactStatus) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivemaininsert = formService.getFormData("archivemaininsertread");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		String archiveMainNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_MAIN_NO);
		CodeUtils cu = new CodeUtils();
		Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
		String attach = constantMap.get("attach");
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		appId = mfBusApply.getAppId();
		//首先判断是否为授信项下业务
		List<ArchiveInfoDetail> docList = new ArrayList<ArchiveInfoDetail>();
		List<MfTemplateBizConfig> successTemplateList = new ArrayList<MfTemplateBizConfig>();
		DocManage docManageQuery = new DocManage();
		String isRelCredit = mfBusApply.getIsRelCredit();
		//isRelCredit为null是授信流程中发起单笔业务
		if(isRelCredit == null || BizPubParm.YES_NO_Y.equals(isRelCredit)){//是授信项下业务
			archiveInfoMain.setCreditAppId(mfBusApply.getCreditAppId());
			archiveInfoMain.setAgenciesId(mfBusApply.getAgenciesId());
			archiveInfoMain.setAgenciesName(mfBusApply.getAgenciesName());
			archiveInfoMain.setBusType("2");//授信项下
			//项目-合同模板
			MfTemplateBizConfig appTemplateBizConfig = new MfTemplateBizConfig();
			appTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
			List<MfTemplateBizConfig> appTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(appTemplateBizConfig);
			//对列表进行处理，过滤掉已经在库的
			for(MfTemplateBizConfig appTemplate : appTemplateBizConfigList){
				//去掉授信带过来的重复的
				if("report".equals(appTemplate.getNodeNo()) || "resp_investigation".equals(appTemplate.getNodeNo()) || "protocolPrint".equals(appTemplate.getNodeNo())
						|| "batch_printing".equals(appTemplate.getNodeNo()) || "credit_batch_printing".equals(appTemplate.getNodeNo()) || "letter_intent".equals(appTemplate.getNodeNo())
						|| "credit_letter_intent".equals(appTemplate.getNodeNo()) || ("contract_sign".equals(appTemplate.getNodeNo())&&"1".equals(appTemplate.getTemplateType()))){
					continue;
				}else {
					if(appTemplate.getDocFilePath() != null && !"".equals(appTemplate.getDocFilePath())){//首先判断该合同是否还没有转换
						//如果合同未归档，或者归档被打回才能再次归档
						ArchiveInfoDetail templateArchiveDetail = new ArchiveInfoDetail();
						templateArchiveDetail.setDocNo(appTemplate.getTemplateBizConfigId());
						List<ArchiveInfoDetail> templateArchiveDetailList = archiveInfoDetailFeign.getList(templateArchiveDetail);
						boolean templateFlag = true;//是否能归档
						for(int k = 0;k<templateArchiveDetailList.size();k++){
							if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(templateArchiveDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
								templateFlag = false;
								break;
							}
						}
						if(templateFlag){//没有被归档过
							//有些合同模板是资料
							ArchiveConfig archiveConfig = new ArchiveConfig();
							archiveConfig.setRelationNo(appTemplate.getTemplateNo());
							archiveConfig = archiveConfigFeign.getById(archiveConfig);
							if(archiveConfig != null){
								String archiveType = archiveConfig.getArchiveType();
								if("1".equals(archiveType)){//归档设置是资料
									ArchiveInfoDetail temDocDetail = new ArchiveInfoDetail();
									temDocDetail.setDocNo(appTemplate.getTemplateBizConfigId());
									temDocDetail.setDocName(appTemplate.getTemplateNameZh());
									temDocDetail.setDocSplitName(appTemplate.getTemplateTypeName());
									docList.add(temDocDetail);
								}else if("2".equals(archiveType)){
									appTemplate.setExt9("0");//是授信合同
									successTemplateList.add(appTemplate);
								}
							}else{
								appTemplate.setExt9("0");//是授信合同
								successTemplateList.add(appTemplate);
							}
						}
					}
				}
			}
		}else{//纯单笔，只归项目资料和合同
			archiveInfoMain.setBusType("1");
			//查询该项目资料
			docManageQuery.setDocBizNo(mfBusApply.getAppId());
			List<DocManage> appDocManageList = docManageFeign.getList(docManageQuery);
			for(DocManage appManage : appDocManageList){
				ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
				docDetailQuery.setDocNo(appManage.getDocNo());
				List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
				boolean appDocFlag = true;//是否能归档
				for(int l = 0;l<docArchiveDetailList.size();l++){
					if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
						appDocFlag = false;
						break;
					}
				}
				if(appDocFlag){
					DocTypeConfig appConfig = new DocTypeConfig();
					appConfig.setDocSplitNo(appManage.getDocSplitNo());
					appConfig = docTypeConfigFeign.getById(appConfig);
					ArchiveInfoDetail appDetail = new ArchiveInfoDetail();
					appDetail.setDocNo(appManage.getDocNo());
					appDetail.setDocName(appManage.getDocName());
					appDetail.setDocSplitName(appConfig.getDocSplitName());
					docList.add(appDetail);
				}
			}

			//项目-合同模板
			MfTemplateBizConfig appTemplateBizConfig = new MfTemplateBizConfig();
			appTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
			List<MfTemplateBizConfig> appTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(appTemplateBizConfig);
			//对列表进行处理，过滤掉已经在库的
			for(MfTemplateBizConfig appTemplate : appTemplateBizConfigList){
				if(appTemplate.getDocFilePath() != null && !"".equals(appTemplate.getDocFilePath())){//首先判断该合同是否还没有转换
					//如果合同未归档，或者归档被打回才能再次归档
					ArchiveInfoDetail templateArchiveDetail = new ArchiveInfoDetail();
					templateArchiveDetail.setDocNo(appTemplate.getTemplateBizConfigId());
					List<ArchiveInfoDetail> templateArchiveDetailList = archiveInfoDetailFeign.getList(templateArchiveDetail);
					boolean templateFlag = true;//是否能归档
					for(int k = 0;k<templateArchiveDetailList.size();k++){
						if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(templateArchiveDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
							templateFlag = false;
							break;
						}
					}
					if(templateFlag){//没有被归档过
						//有些合同模板是资料
						ArchiveConfig archiveConfig = new ArchiveConfig();
						archiveConfig.setRelationNo(appTemplate.getTemplateNo());
						archiveConfig = archiveConfigFeign.getById(archiveConfig);
						if(archiveConfig != null){
							String archiveType = archiveConfig.getArchiveType();
							if("1".equals(archiveType)){//归档设置是资料
								ArchiveInfoDetail temDocDetail = new ArchiveInfoDetail();
								temDocDetail.setDocNo(appTemplate.getTemplateBizConfigId());
								temDocDetail.setDocName(appTemplate.getTemplateNameZh());
								temDocDetail.setDocSplitName(appTemplate.getTemplateTypeName());
								docList.add(temDocDetail);
							}else if("2".equals(archiveType)){
								appTemplate.setExt9("0");//是授信合同
								successTemplateList.add(appTemplate);
							}
						}else{
							appTemplate.setExt9("0");//是授信合同
							successTemplateList.add(appTemplate);
						}
					}
				}
			}
		}
		model.addAttribute("successTemplateList", successTemplateList);
		model.addAttribute("docList", docList);
		//其他资料列表
		List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
		model.addAttribute("paperInfoDetailList", paperInfoDetailList);
		//非系统生成合同
		List<ArchiveInfoDetail> pactDetailList = new ArrayList<ArchiveInfoDetail>();
		MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
		List<MfBusPactExtend> mfBusPactExtendList = new ArrayList<>();
		if(StringUtil.isNotEmpty(appId)){
			mfBusPactExtend.setAppId(appId);
			mfBusPactExtendList = mfBusPactExtendFeign.getAllListForApply(mfBusPactExtend);
			for(MfBusPactExtend extendPact : mfBusPactExtendList){
				//如果合同未归档，或者归档被打回才能再次归档
				ArchiveInfoDetail extendDetailQuery = new ArchiveInfoDetail();
				extendDetailQuery.setDocBizNo(extendPact.getId());
				List<ArchiveInfoDetail> extendDetailQueryList = archiveInfoDetailFeign.getList(extendDetailQuery);
				boolean extendFlag = true;//是否能归档
				for(int b = 0;b<extendDetailQueryList.size();b++){
					if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(extendDetailQueryList.get(b).getArchiveDocStatus()) && !"0".equals(extendDetailQueryList.get(b).getArchiveDocStatus())){
						extendFlag = false;
						break;
					}
				}
				if(extendFlag){//没有被归档过
					ArchiveInfoDetail extendPactArchiveInfoDetail = new ArchiveInfoDetail();
					String archiveDetailNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_DETAIL_NO);
					extendPactArchiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
					extendPactArchiveInfoDetail.setArchiveMainNo(archiveMainNo);
					extendPactArchiveInfoDetail.setCusNo(archiveInfoMain.getCusNo());
					extendPactArchiveInfoDetail.setCusName(archiveInfoMain.getCusName());
					extendPactArchiveInfoDetail.setAppId(archiveInfoMain.getAppId());
					extendPactArchiveInfoDetail.setAppName(archiveInfoMain.getAppName());
					extendPactArchiveInfoDetail.setPactId(archiveInfoMain.getPactId());
					extendPactArchiveInfoDetail.setPactNo(archiveInfoMain.getPactNo());
					extendPactArchiveInfoDetail.setKindNo(archiveInfoMain.getKindNo());
					extendPactArchiveInfoDetail.setKindName(archiveInfoMain.getKindName());
					extendPactArchiveInfoDetail.setCreditAppId(archiveInfoMain.getCreditAppId());
					extendPactArchiveInfoDetail.setDocBizNo(extendPact.getId());
					extendPactArchiveInfoDetail.setScNo("000002");
					extendPactArchiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);//非系统生成合同
					//合同名称和合同号
					extendPactArchiveInfoDetail.setCorpName(extendPact.getPactName());
					extendPactArchiveInfoDetail.setCorpNo(extendPact.getTemplatePactNo());
					//如果已经上传非系统生成合同
					/*DocManage paperManage = new DocManage();
					paperManage.setDocBizNo(extendPact.getId());
					paperManage = docManageFeign.getById(paperManage);
					if(paperManage != null){
						extendPactArchiveInfoDetail.setDocNo(paperManage.getDocNo());
						extendPactArchiveInfoDetail.setDocName(paperManage.getDocName());
					}else{
						extendPactArchiveInfoDetail.setDocNo("");
						extendPactArchiveInfoDetail.setDocName(extendPact.getTemplateName());
					}*/
					extendPactArchiveInfoDetail.setDocNo("");
					extendPactArchiveInfoDetail.setDocName(extendPact.getTemplateName());
					extendPactArchiveInfoDetail.setDocSplitNo(attach);
					extendPactArchiveInfoDetail.setDocSplitName(extendPact.getPactName());
					extendPactArchiveInfoDetail.setArchiveDocStatus("0");//归档待确认
					extendPactArchiveInfoDetail.setIsPaper("0");
					extendPactArchiveInfoDetail.setIsLend(Constant.NO);
					extendPactArchiveInfoDetail.setArchiveDocSource(Constant.ARCHIVE_DOC_SOURCE_BUSINESS);
					extendPactArchiveInfoDetail.setUpdateOpNo(User.getRegNo(request));
					extendPactArchiveInfoDetail.setUpdateOpName(User.getRegName(request));
					extendPactArchiveInfoDetail.setUpdateBrNo(User.getOrgNo(request));
					extendPactArchiveInfoDetail.setUpdateBrName(User.getOrgName(request));
					extendPactArchiveInfoDetail.setUpdateDate(DateUtil.getDate());
					extendPactArchiveInfoDetail.setIsTemplate(Constant.NO);
					extendPactArchiveInfoDetail.setDataSource("5");//非系统生成合同表
					extendPactArchiveInfoDetail.setArchiveNum(1);
					extendPactArchiveInfoDetail.setBorrowInNum(0);
					extendPactArchiveInfoDetail.setBorrowOutNum(0);
					extendPactArchiveInfoDetail.setReturnNum(0);
					pactDetailList.add(extendPactArchiveInfoDetail);
					archiveInfoDetailFeign.insert(extendPactArchiveInfoDetail);
				}
			}
		}
		model.addAttribute("pactDetailList", pactDetailList);
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getRegName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		archiveInfoMain.setArchivePactStatus(archivePactStatus);
		String brNo = User.getOrgNo(request);
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(brNo);
		archiveInfoMain.setUpdateBrName(User.getRegName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());
		archiveInfoMain.setCusNo(mfBusApply.getCusNo());
		archiveInfoMain.setCusName(mfBusApply.getCusName());
		archiveInfoMain.setKindNo(mfBusApply.getKindNo());
		archiveInfoMain.setKindName(mfBusApply.getKindName());
		archiveInfoMain.setPactId(mfBusApply.getPactId());
		archiveInfoMain.setPactNo(mfBusApply.getLetterPactNo());
		archiveInfoMain.setAppId(mfBusApply.getAppId());
		archiveInfoMain.setAppName(mfBusApply.getAppName());
		archiveInfoMain.setOptType("2");
		getObjValue(formarchivemaininsert, archiveInfoMain);
		model.addAttribute("formarchivemaininsert", formarchivemaininsert);
		model.addAttribute("archiveMainNo", archiveMainNo);
		model.addAttribute("archivePactStatus", archivePactStatus);
		model.addAttribute("isShow", true);//非系统生成合同的在单笔显示
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_Insert";
	}

	/**
	 * 资料合同-归档申请界面in
	 * 归档阶段选择授信
	 * 资料/合同需要在拿到原始上传信息后，需要处理，在库的资料不必要展示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputCredit")
	public String inputCredit(Model model, String creditAppId, String archivePactStatus, String agenciesId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivemaininsert = formService.getFormData("archivemaininsertread");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		List<ArchiveInfoDetail> docList = new ArrayList<ArchiveInfoDetail>();
		List<MfTemplateBizConfig> successTemplateList = new ArrayList<MfTemplateBizConfig>();
		DocManage docManageQuery = new DocManage();
		//查询该企业资料
		docManageQuery.setDocBizNo(mfCusCreditApply.getCusNo());
		List<DocManage> cusManageList = docManageFeign.getList(docManageQuery);
		for(DocManage cusManage : cusManageList){
			ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
			docDetailQuery.setDocNo(cusManage.getDocNo());
			List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
			boolean docFlag = true;//是否能归档
			for(int l = 0;l<docArchiveDetailList.size();l++){
				if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
					docFlag = false;
					break;
				}
			}
			if(docFlag){
				DocTypeConfig cusConfig = new DocTypeConfig();
				cusConfig.setDocSplitNo(cusManage.getDocSplitNo());
				cusConfig = docTypeConfigFeign.getById(cusConfig);
				ArchiveInfoDetail cusDetail = new ArchiveInfoDetail();
				cusDetail.setDocNo(cusManage.getDocNo());
				cusDetail.setDocName(cusManage.getDocName());
				cusDetail.setDocSplitName(cusConfig.getDocSplitName());
				docList.add(cusDetail);
			}
		}
		//授信资料
		if(mfCusCreditApply.getCreditAppId() != null && !"".equals(mfCusCreditApply.getCreditAppId())){
			docManageQuery.setDocBizNo(mfCusCreditApply.getCreditAppId());
			List<DocManage> creditDocManageList = docManageFeign.getList(docManageQuery);
			for(DocManage creditManage : creditDocManageList){
				ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
				docDetailQuery.setDocNo(creditManage.getDocNo());
				List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
				boolean creditDocFlag = true;//是否能归档
				for(int l = 0;l<docArchiveDetailList.size();l++){
					if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
						creditDocFlag = false;
						break;
					}
				}
				if(creditDocFlag){
					DocTypeConfig creditConfig = new DocTypeConfig();
					creditConfig.setDocSplitNo(creditManage.getDocSplitNo());
					creditConfig = docTypeConfigFeign.getById(creditConfig);
					ArchiveInfoDetail creditDetail = new ArchiveInfoDetail();
					creditDetail.setDocNo(creditManage.getDocNo());
					creditDetail.setDocName(creditManage.getDocName());
					creditDetail.setDocSplitName(creditConfig.getDocSplitName());
					docList.add(creditDetail);
				}
			}
		}
		//授信-合同模板
		if(mfCusCreditApply.getCreditAppId() != null && !"".equals(mfCusCreditApply.getCreditAppId())){
			MfTemplateBizConfig creditTemplateBizConfig = new MfTemplateBizConfig();
			creditTemplateBizConfig.setTemBizNo(mfCusCreditApply.getCreditAppId());
			List<MfTemplateBizConfig> creditTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(creditTemplateBizConfig);
			//对列表进行处理，过滤掉已经在库的
			for(MfTemplateBizConfig creditTemplate : creditTemplateBizConfigList){
				if(creditTemplate.getDocFilePath() != null && !"".equals(creditTemplate.getDocFilePath())){//首先判断该合同是否还没有转换
					//先判断是否该项目所使用的授信银行
					boolean agenciesFlag = true;//是否为同一个银行
					if(!agenciesId.equals(creditTemplate.getExt1())){//合同的银行id和选择归档的银行id一致
						agenciesFlag = false;
						continue;
					}
					if(agenciesFlag){
						//如果合同未归档，或者归档被打回才能再次归档
						ArchiveInfoDetail creditTemplateArchiveDetail = new ArchiveInfoDetail();
						creditTemplateArchiveDetail.setDocNo(creditTemplate.getTemplateBizConfigId());
						List<ArchiveInfoDetail> creditTemplateArchiveDetailList = archiveInfoDetailFeign.getList(creditTemplateArchiveDetail);
						boolean templateFlag = true;//是否能归档
						for(int k = 0;k<creditTemplateArchiveDetailList.size();k++){
							if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(creditTemplateArchiveDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
								templateFlag = false;
								break;
							}
						}
						if(templateFlag){//没有被归档过
							//有些合同模板是资料
							ArchiveConfig archiveConfig = new ArchiveConfig();
							archiveConfig.setRelationNo(creditTemplate.getTemplateNo());
							archiveConfig = archiveConfigFeign.getById(archiveConfig);
							if(archiveConfig != null){
								String archiveType = archiveConfig.getArchiveType();
								if("1".equals(archiveType)){//归档设置是资料
									ArchiveInfoDetail temDocDetail = new ArchiveInfoDetail();
									temDocDetail.setDocNo(creditTemplate.getTemplateBizConfigId());
									temDocDetail.setDocName(creditTemplate.getTemplateNameZh());
									temDocDetail.setDocSplitName(creditTemplate.getTemplateTypeName());
									docList.add(temDocDetail);
								}else if("2".equals(archiveType)){
									creditTemplate.setExt9("1");//是授信合同
									successTemplateList.add(creditTemplate);
								}
							}else{
								creditTemplate.setExt9("1");//是授信合同
								successTemplateList.add(creditTemplate);
							}
						}
					}
				}
			}
		}
		model.addAttribute("successTemplateList", successTemplateList);
		model.addAttribute("docList", docList);
		//其他资料列表
		List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
		model.addAttribute("paperInfoDetailList", paperInfoDetailList);
		String archiveMainNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_MAIN_NO);
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getRegName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoMain.setUpdateBrName(User.getRegName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());
		archiveInfoMain.setArchivePactStatus(archivePactStatus);
		archiveInfoMain.setOptType("2");
		archiveInfoMain.setCusNo(mfCusCreditApply.getCusNo());
		archiveInfoMain.setCusName(mfCusCreditApply.getCusName());
		archiveInfoMain.setKindNo(mfCusCreditApply.getTemplateCreditId());
		archiveInfoMain.setKindName(mfCusCreditApply.getTemplateCreditName());
		archiveInfoMain.setCreditAppId(mfCusCreditApply.getCreditAppId());
		archiveInfoMain.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
		MfBusAgencies mfBusAgencies = new MfBusAgencies();
		mfBusAgencies.setAgenciesId(agenciesId);
		mfBusAgencies = mfBusAgenciesFeign.getById(mfBusAgencies);
		if(mfBusAgencies != null ){
			archiveInfoMain.setAgenciesId(agenciesId);
			archiveInfoMain.setAgenciesName(mfBusAgencies.getAgenciesName());
		}
		getObjValue(formarchivemaininsert, archiveInfoMain);
		model.addAttribute("formarchivemaininsert", formarchivemaininsert);
		model.addAttribute("archiveMainNo", archiveMainNo);
		model.addAttribute("isShow", false);//授信不展示非系统生成合同
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("archivePactStatus", archivePactStatus);
		model.addAttribute("agenciesId", agenciesId);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_Insert";
	}

	/**
	 * 资料合同归档修改重新上传
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editForArchiveMain")
	public String editForArchiveMain(Model model, String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		boolean isShow = true;
		FormData formarchivemainupdate = formService.getFormData("archivemainupdate");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);

		List<ArchiveInfoDetail> docList = new ArrayList<ArchiveInfoDetail>();
		List<MfTemplateBizConfig> successTemplateList = new ArrayList<MfTemplateBizConfig>();
		DocManage docManageQuery = new DocManage();
		//首先看是授信还是业务
		if("01".equals(archiveInfoMain.getArchivePactStatus())){//授信
			isShow = false;
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(archiveInfoMain.getCreditAppId());
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			//查询该企业资料
			docManageQuery.setDocBizNo(mfCusCreditApply.getCusNo());
			List<DocManage> cusManageList = docManageFeign.getList(docManageQuery);
			for(DocManage cusManage : cusManageList){
				ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
				docDetailQuery.setDocNo(cusManage.getDocNo());
				List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
				boolean docFlag = true;//是否能归档
				for(int l = 0;l<docArchiveDetailList.size();l++){
					if(!BizPubParm.ARCHIVE_DOC_STATE_01.equals(docArchiveDetailList.get(l).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
						docFlag = false;
						break;
					}
				}
				if(docFlag){
					DocTypeConfig cusConfig = new DocTypeConfig();
					cusConfig.setDocSplitNo(cusManage.getDocSplitNo());
					cusConfig = docTypeConfigFeign.getById(cusConfig);
					ArchiveInfoDetail cusDetail = new ArchiveInfoDetail();
					cusDetail.setDocNo(cusManage.getDocNo());
					cusDetail.setDocName(cusManage.getDocName());
					cusDetail.setDocSplitName(cusConfig.getDocSplitName());
					docList.add(cusDetail);
				}
			}
			//授信资料
			if(mfCusCreditApply.getCreditAppId() != null && !"".equals(mfCusCreditApply.getCreditAppId())){
				docManageQuery.setDocBizNo(mfCusCreditApply.getCreditAppId());
				List<DocManage> creditDocManageList = docManageFeign.getList(docManageQuery);
				for(DocManage creditManage : creditDocManageList){
					ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
					docDetailQuery.setDocNo(creditManage.getDocNo());
					List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
					boolean creditDocFlag = true;//是否能归档
					for(int l = 0;l<docArchiveDetailList.size();l++){
						if(!BizPubParm.ARCHIVE_DOC_STATE_01.equals(docArchiveDetailList.get(l).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
							creditDocFlag = false;
							break;
						}
					}
					if(creditDocFlag){
						DocTypeConfig creditConfig = new DocTypeConfig();
						creditConfig.setDocSplitNo(creditManage.getDocSplitNo());
						creditConfig = docTypeConfigFeign.getById(creditConfig);
						ArchiveInfoDetail creditDetail = new ArchiveInfoDetail();
						creditDetail.setDocNo(creditManage.getDocNo());
						creditDetail.setDocName(creditManage.getDocName());
						creditDetail.setDocSplitName(creditConfig.getDocSplitName());
						docList.add(creditDetail);
					}
				}
			}
			//授信-合同模板
			if(mfCusCreditApply.getCreditAppId() != null && !"".equals(mfCusCreditApply.getCreditAppId())){
				MfTemplateBizConfig creditTemplateBizConfig = new MfTemplateBizConfig();
				creditTemplateBizConfig.setTemBizNo(mfCusCreditApply.getCreditAppId());
				List<MfTemplateBizConfig> creditTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(creditTemplateBizConfig);
				//对列表进行处理，过滤掉已经在库的
				for(MfTemplateBizConfig creditTemplate : creditTemplateBizConfigList){
					if(creditTemplate.getDocFilePath() != null && !"".equals(creditTemplate.getDocFilePath())){//首先判断该合同是否还没有转换
						//先判断是否该项目所使用的授信银行
						boolean agenciesFlag = true;//是否为同一个银行
						if(!archiveInfoMain.getAgenciesId().equals(creditTemplate.getExt1())){//合同的银行id和选择归档的银行id一致
							agenciesFlag = false;
							continue;
						}
						if(agenciesFlag){
							//如果合同未归档，或者归档被打回才能再次归档
							ArchiveInfoDetail creditTemplateArchiveDetail = new ArchiveInfoDetail();
							creditTemplateArchiveDetail.setDocNo(creditTemplate.getTemplateBizConfigId());
							List<ArchiveInfoDetail> creditTemplateArchiveDetailList = archiveInfoDetailFeign.getList(creditTemplateArchiveDetail);
							boolean templateFlag = true;//是否能归档
							for(int k = 0;k<creditTemplateArchiveDetailList.size();k++){
								if(!BizPubParm.ARCHIVE_DOC_STATE_01.equals(creditTemplateArchiveDetailList.get(k).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(creditTemplateArchiveDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
									templateFlag = false;
									break;
								}
							}
							if(templateFlag){//没有被归档过
								//有些合同模板是资料
								ArchiveConfig archiveConfig = new ArchiveConfig();
								archiveConfig.setRelationNo(creditTemplate.getTemplateNo());
								archiveConfig = archiveConfigFeign.getById(archiveConfig);
								if(archiveConfig != null){
									String archiveType = archiveConfig.getArchiveType();
									if("1".equals(archiveType)){//归档设置是资料
//										if(isFirstArchive){
											ArchiveInfoDetail temDocDetail = new ArchiveInfoDetail();
											temDocDetail.setDocNo(creditTemplate.getTemplateBizConfigId());
											temDocDetail.setDocName(creditTemplate.getTemplateNameZh());
											temDocDetail.setDocSplitName(creditTemplate.getTemplateTypeName());
											docList.add(temDocDetail);
//										}
									}else if("2".equals(archiveType)){
										creditTemplate.setExt9("1");//是授信合同
										successTemplateList.add(creditTemplate);
									}
								}else{
									creditTemplate.setExt9("1");//是授信合同
									successTemplateList.add(creditTemplate);
								}
							}
						}
					}
				}
			}
		}else if("02".equals(archiveInfoMain.getArchivePactStatus())){
			isShow = true;
			String appId = archiveInfoMain.getAppId();
			if(StringUtil.isNotEmpty(appId)) {
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(appId);
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				//首先判断是否为授信项下业务
				String isRelCredit = mfBusApply.getIsRelCredit();
				//isRelCredit为null是授信流程中发起单笔业务
				if (isRelCredit == null || BizPubParm.YES_NO_Y.equals(isRelCredit)) {//是授信项下业务
					//项目-合同模板
					MfTemplateBizConfig appTemplateBizConfig = new MfTemplateBizConfig();
					appTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
					List<MfTemplateBizConfig> appTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(appTemplateBizConfig);
					//对列表进行处理，过滤掉已经在库的
					for (MfTemplateBizConfig appTemplate : appTemplateBizConfigList) {
						//去掉授信带过来的重复的
						if ("report".equals(appTemplate.getNodeNo()) || "resp_investigation".equals(appTemplate.getNodeNo()) || "protocolPrint".equals(appTemplate.getNodeNo())
								|| "batch_printing".equals(appTemplate.getNodeNo()) || "credit_batch_printing".equals(appTemplate.getNodeNo()) || "letter_intent".equals(appTemplate.getNodeNo())
								|| "credit_letter_intent".equals(appTemplate.getNodeNo()) || ("contract_sign".equals(appTemplate.getNodeNo()) && "1".equals(appTemplate.getTemplateType()))) {
							continue;
						} else {
							if (appTemplate.getDocFilePath() != null && !"".equals(appTemplate.getDocFilePath())) {//首先判断该合同是否还没有转换
								//如果合同未归档，或者归档被打回才能再次归档
								ArchiveInfoDetail templateArchiveDetail = new ArchiveInfoDetail();
								templateArchiveDetail.setDocNo(appTemplate.getTemplateBizConfigId());
								List<ArchiveInfoDetail> templateArchiveDetailList = archiveInfoDetailFeign.getList(templateArchiveDetail);
								boolean templateFlag = true;//是否能归档
								for (int k = 0; k < templateArchiveDetailList.size(); k++) {
									if (!BizPubParm.ARCHIVE_DOC_STATE_01.equals(templateArchiveDetailList.get(k).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(templateArchiveDetailList.get(k).getArchiveDocStatus())) {//只有被打回，才能归档
										templateFlag = false;
										break;
									}
								}
								if (templateFlag) {//没有被归档过
									appTemplate.setExt9("0");//是授信合同
									successTemplateList.add(appTemplate);
								}
							}
						}
					}
				} else {//纯单笔，只归项目资料和合同
					//查询该项目资料
					docManageQuery.setDocBizNo(mfBusApply.getAppId());
					List<DocManage> appDocManageList = docManageFeign.getList(docManageQuery);
					for (DocManage appManage : appDocManageList) {
						ArchiveInfoDetail docDetailQuery = new ArchiveInfoDetail();
						docDetailQuery.setDocNo(appManage.getDocNo());
						List<ArchiveInfoDetail> docArchiveDetailList = archiveInfoDetailFeign.getList(docDetailQuery);
						boolean appDocFlag = true;//是否能归档
						for(int l = 0;l<docArchiveDetailList.size();l++){
							if(!BizPubParm.ARCHIVE_DOC_STATE_01.equals(docArchiveDetailList.get(l).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(docArchiveDetailList.get(l).getArchiveDocStatus())){//只有被打回，才能归档
								appDocFlag = false;
								break;
							}
						}
						if(appDocFlag){
							DocTypeConfig appConfig = new DocTypeConfig();
							appConfig.setDocSplitNo(appManage.getDocSplitNo());
							appConfig = docTypeConfigFeign.getById(appConfig);
							ArchiveInfoDetail appDetail = new ArchiveInfoDetail();
							appDetail.setDocNo(appManage.getDocNo());
							appDetail.setDocName(appManage.getDocName());
							appDetail.setDocSplitName(appConfig.getDocSplitName());
							docList.add(appDetail);
						}
					}
					//项目-合同模板
					MfTemplateBizConfig appTemplateBizConfig = new MfTemplateBizConfig();
					appTemplateBizConfig.setTemBizNo(mfBusApply.getAppId());
					List<MfTemplateBizConfig> appTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(appTemplateBizConfig);
					//对列表进行处理，过滤掉已经在库的
					for (MfTemplateBizConfig appTemplate : appTemplateBizConfigList) {
						if (appTemplate.getDocFilePath() != null && !"".equals(appTemplate.getDocFilePath())) {//首先判断该合同是否还没有转换
							//如果合同未归档，或者归档被打回才能再次归档
							ArchiveInfoDetail templateArchiveDetail = new ArchiveInfoDetail();
							templateArchiveDetail.setDocNo(appTemplate.getTemplateBizConfigId());
							List<ArchiveInfoDetail> templateArchiveDetailList = archiveInfoDetailFeign.getList(templateArchiveDetail);
							boolean templateFlag = true;//是否能归档
							for (int k = 0; k < templateArchiveDetailList.size(); k++) {
								if (!BizPubParm.ARCHIVE_DOC_STATE_01.equals(templateArchiveDetailList.get(k).getArchiveDocStatus()) && !BizPubParm.ARCHIVE_DOC_STATE_03.equals(templateArchiveDetailList.get(k).getArchiveDocStatus())) {//只有被打回，才能归档
									templateFlag = false;
									break;
								}
							}
							if (templateFlag) {//没有被归档过
								//有些合同模板是资料
								ArchiveConfig archiveConfig = new ArchiveConfig();
								archiveConfig.setRelationNo(appTemplate.getTemplateNo());
								archiveConfig = archiveConfigFeign.getById(archiveConfig);
								if (archiveConfig != null) {
									String archiveType = archiveConfig.getArchiveType();
									if ("1".equals(archiveType)) {//归档设置是资料
										ArchiveInfoDetail temDocDetail = new ArchiveInfoDetail();
										temDocDetail.setDocNo(appTemplate.getTemplateBizConfigId());
										temDocDetail.setDocName(appTemplate.getTemplateNameZh());
										temDocDetail.setDocSplitName(appTemplate.getTemplateTypeName());
										docList.add(temDocDetail);
									} else if ("2".equals(archiveType)) {
										appTemplate.setExt9("0");//是授信合同
										successTemplateList.add(appTemplate);
									}
								} else {
									appTemplate.setExt9("0");//是授信合同
									successTemplateList.add(appTemplate);
								}
							}
						}
					}
				}

			}
		}
		model.addAttribute("successTemplateList", successTemplateList);
		model.addAttribute("docList", docList);
		//其他资料列表
		List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveMainNo(archiveMainNo);
		archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_04);
		paperInfoDetailList = archiveInfoDetailFeign.getList(archiveInfoDetail);
		model.addAttribute("paperInfoDetailList", paperInfoDetailList);
		//非系统生成合同
		List<ArchiveInfoDetail> pactDetailList = new ArrayList<ArchiveInfoDetail>();
		ArchiveInfoDetail pactInfoDetail = new ArchiveInfoDetail();
		pactInfoDetail.setArchiveMainNo(archiveMainNo);
		pactInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);
		pactDetailList = archiveInfoDetailFeign.getList(pactInfoDetail);
		model.addAttribute("pactDetailList", pactDetailList);

		getObjValue(formarchivemainupdate, archiveInfoMain);
		model.addAttribute("formarchivemainupdate", formarchivemainupdate);
		model.addAttribute("isShow", isShow);
		model.addAttribute("creditAppId", archiveInfoMain.getCreditAppId());
		model.addAttribute("archivePactStatus", archiveInfoMain.getArchivePactStatus());
		model.addAttribute("agenciesId", archiveInfoMain.getAgenciesId());
		model.addAttribute("archiveMainNo", archiveMainNo);
		model.addAttribute("appId", archiveInfoMain.getAppId());
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_Update";
	}

	/**
	 * 根初始化授信的合作银行
	 * @param creditAppId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/breedBankInit")
	@ResponseBody
	public Map<String,Object> breedBankInit(int pageNo,String ajaxData,String creditAppId)throws Exception{
		ActionContext.initialize(request, response);
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusAgenciesCredit mfCusAgenciesCredit = new MfCusAgenciesCredit();
			mfCusAgenciesCredit.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCusAgenciesCredit.setCustomSorts(ajaxData);
			mfCusAgenciesCredit.setCriteriaList(mfCusAgenciesCredit, ajaxData);// 我的筛选
			mfCusAgenciesCredit.setCreditAppId(creditAppId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCusAgenciesCredit",mfCusAgenciesCredit));
			ipage = archiveInfoMainFeign.findAgeniesAjax(ipage);
			ipage.setParamsStr(ajaxData);
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
	 * 其他资料-点击新增 进入界面
	 * @param model
	 * @param archiveMainNo
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editForPaper")
	public String editForPaper(Model model,String archivePactStatus, String archiveMainNo, String relationId) throws Exception {
		ActionContext.initialize(request, response);
		String archiveDetailNo = "";
		FormService formService = new FormService();
		FormData formarchivepaperinsert = null;
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		if("01".equals(archivePactStatus)){//授信
			formarchivepaperinsert = formService.getFormData("archivepapercredit");
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(relationId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			archiveDetailNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_DETAIL_NO);
			archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setCusNo(mfCusCreditApply.getCusNo());
			archiveInfoDetail.setCusName(mfCusCreditApply.getCusName());
			archiveInfoDetail.setKindNo(mfCusCreditApply.getKindNo());
			archiveInfoDetail.setKindName(mfCusCreditApply.getKindName());
			archiveInfoDetail.setCreditAppId(mfCusCreditApply.getCreditAppId());
			archiveInfoDetail.setCreditAppNo(mfCusCreditApply.getCreditAppNo());

		}else if("02".equals(archivePactStatus)){//项目
			formarchivepaperinsert = formService.getFormData("archivepaperinsert");
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(relationId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			archiveDetailNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_DETAIL_NO);
			archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setCusNo(mfBusApply.getCusNo());
			archiveInfoDetail.setCusName(mfBusApply.getCusName());
			archiveInfoDetail.setKindNo(mfBusApply.getKindNo());
			archiveInfoDetail.setKindName(mfBusApply.getKindName());
			archiveInfoDetail.setPactId(mfBusApply.getPactId());
			archiveInfoDetail.setPactNo(mfBusApply.getLetterPactNo());
			archiveInfoDetail.setAppId(mfBusApply.getAppId());
			archiveInfoDetail.setAppName(mfBusApply.getAppName());
		}
		archiveInfoDetail.setDataSource("3");//手动上传
		archiveInfoDetail.setUpdateOpNo(User.getRegNo(request));
		archiveInfoDetail.setUpdateOpName(User.getRegName(request));
		archiveInfoDetail.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoDetail.setUpdateBrName(User.getOrgName(request));
		getObjValue(formarchivepaperinsert, archiveInfoDetail);
		model.addAttribute("formarchivepaperinsert", formarchivepaperinsert);
		model.addAttribute("query", "");
		model.addAttribute("archivePactStatus", archivePactStatus);
		model.addAttribute("archiveDetailNo", archiveDetailNo);
		return "/component/archives/ArchiveInfoMain_EditForPaper";
	}

	/**
	 * 其他资料-点击新增 进入界面
	 * @param model
	 * @param archiveDetailNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPaperDetail")
	public String getPaperDetail(Model model, String archiveDetailNo, String archivePactStatus) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivepaperinsert = null;
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if("01".equals(archivePactStatus)){//授信
			formarchivepaperinsert = formService.getFormData("archivepapercredit");
		}else if("02".equals(archivePactStatus)){//项目
			formarchivepaperinsert = formService.getFormData("archivepaperinsert");
		}
		archiveInfoDetail.setDataSource("3");//手动上传
		archiveInfoDetail.setUpdateOpNo(User.getRegNo(request));
		archiveInfoDetail.setUpdateOpName(User.getRegName(request));
		archiveInfoDetail.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoDetail.setUpdateBrName(User.getOrgName(request));
		getObjValue(formarchivepaperinsert, archiveInfoDetail);
		model.addAttribute("formarchivepaperinsert", formarchivepaperinsert);
		model.addAttribute("query", "");
		model.addAttribute("archivePactStatus", archivePactStatus);
		model.addAttribute("archiveDetailNo", archiveDetailNo);
		return "/component/archives/ArchiveInfoMain_PaperDetail";
	}

	/**
	 * 选择纸质归档类别弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchivePaperList")
	public String getArchivePaperList(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "component/archives/ArchiveInfoMain_SelectPaper";
	}

	/***
	 * 选择纸质归档类别
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findPaperAjax")
	@ResponseBody
	public Map<String, Object> findPaperAjax(Integer pageNo, Integer pageSize, String tableId, String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoMain", archiveInfoMain));
			//自定义查询Bo方法
			ipage = archiveInfoMainFeign.findPaperAjax(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId, tableType, (List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDocConfigAjax")
	@ResponseBody
	public Map<String, Object> getDocConfigAjax(String docSplitNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			DocTypeConfig docTypeConfig = new DocTypeConfig();
			docTypeConfig.setDocSplitNo(docSplitNo);
			docTypeConfig = docTypeConfigFeign.getById(docTypeConfig);
			if(docTypeConfig != null){
				dataMap.put("docTypeConfig",docTypeConfig);
				dataMap.put("flag","success");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "查询失败");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 保存其他资料
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertPaperAjax")
	@ResponseBody
	public  Map <String, Object> insertPaperAjax(String ajaxData,String archivePactStatus)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map <String, Object> dataMap = new HashMap <String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formarchivepaperinsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchivepaperinsert, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formarchivepaperinsert)) {
				setObjValue(formarchivepaperinsert, archiveInfoDetail);
				/*if("01".equals(archivePactStatus)){//授信

				}else if("02".equals(archivePactStatus)){
					ArchiveInfoDetail paperDetail = new ArchiveInfoDetail();
					paperDetail.setDocSplitNo(archiveInfoDetail.getDocSplitNo());
					paperDetail.setAppId(archiveInfoDetail.getAppId());
					List<ArchiveInfoDetail> paperArchiveDetailList = archiveInfoDetailFeign.getList(paperDetail);
					boolean templateFlag = true;//是否能归档
					for(int k = 0;k<paperArchiveDetailList.size();k++){
						if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(paperArchiveDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
							templateFlag = false;
							break;
						}
					}
					if(!templateFlag){//没有被归档过
						dataMap.put("flag", "error");
						dataMap.put("msg", "该资料类别已存在，不能重复归档。");
						return dataMap;
					}
				}*/
				archiveInfoDetailFeign.insertPaperAjax(archiveInfoDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 新增纸质文件保存后返回
	 * @param archiveMainNo
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getListHtmlAjax(String archiveMainNo,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_04);
			List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getList(archiveInfoDetail);
			if(archiveInfoDetailList.size()>0){
				dataMap.put("ifHasList", "1");
			}else{
				dataMap.put("ifHasList", "0");
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", archiveInfoDetailList, null, true);
			dataMap.put("tableData", tableHtml);
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

	/**
	 * 非系统生成合同-点击新增 进入界面
	 * @param model
	 * @param archiveMainNo
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPactExtend")
	public String addPactExtend(Model model,String archiveMainNo, String relationId, String archivePactStatus) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchiveextendpactedit = formService.getFormData("archiveextendpactedit");
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		String archiveDetailNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_DETAIL_NO);
		archiveInfoDetail.setArchivePactStatus(archivePactStatus);
		if("01".equals(archivePactStatus)){
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(relationId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			archiveInfoDetail.setCusNo(mfCusCreditApply.getCusNo());
			archiveInfoDetail.setCusName(mfCusCreditApply.getCusName());
			archiveInfoDetail.setKindNo(mfCusCreditApply.getKindNo());
			archiveInfoDetail.setKindName(mfCusCreditApply.getKindName());
			archiveInfoDetail.setCreditAppId(mfCusCreditApply.getCreditAppId());
			archiveInfoDetail.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
		}else if("02".equals(archivePactStatus)){
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(relationId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			archiveInfoDetail.setCusNo(mfBusApply.getCusNo());
			archiveInfoDetail.setCusName(mfBusApply.getCusName());
			archiveInfoDetail.setKindNo(mfBusApply.getKindNo());
			archiveInfoDetail.setKindName(mfBusApply.getKindName());
			archiveInfoDetail.setPactId(mfBusApply.getPactId());
			archiveInfoDetail.setPactNo(mfBusApply.getLetterPactNo());
			archiveInfoDetail.setAppId(mfBusApply.getAppId());
			archiveInfoDetail.setAppName(mfBusApply.getAppName());
		}
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail.setArchiveMainNo(archiveMainNo);
		getObjValue(formarchiveextendpactedit, archiveInfoDetail);
		model.addAttribute("formarchiveextendpactedit", formarchiveextendpactedit);
		model.addAttribute("query", "");
		model.addAttribute("archiveDetailNo", archiveDetailNo);
		return "/component/archives/ArchiveInfoMain_EditForPact";
	}

	/**
	 * 保存非系统生成合同
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertPactAjax")
	@ResponseBody
	public  Map <String, Object> insertPactAjax(String ajaxData)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map <String, Object> dataMap = new HashMap <String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formarchiveextendpactedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchiveextendpactedit, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formarchiveextendpactedit)) {
				setObjValue(formarchiveextendpactedit, archiveInfoDetail);
				archiveInfoDetailFeign.insertPactAjax(archiveInfoDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 非系统生成合同-点击详情界面
	 * @param model
	 * @param archiveDetailNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/pactExtendDetail")
	public String pactExtendDetail(Model model,String archiveDetailNo, String archivePactStatus) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchiveextendpactedit = null;
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		archiveInfoDetail.setArchivePactStatus(archivePactStatus);
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		if("5".equals(archiveInfoDetail.getDataSource())){//业务流程中的非系统生成合同
			formarchiveextendpactedit = formService.getFormData("pactExtendCreditDetail");
			MfBusPactExtend mfBusPactExtend = new MfBusPactExtend();
			mfBusPactExtend.setId(archiveInfoDetail.getDocBizNo());
			mfBusPactExtend = mfBusPactExtendFeign.getById(mfBusPactExtend);
			getObjValue(formarchiveextendpactedit, mfBusPactExtend);
			model.addAttribute("archiveDetailNo", archiveInfoDetail.getDocBizNo());
		}else{
			formarchiveextendpactedit = formService.getFormData("archiveextendpactedit");
			model.addAttribute("archiveDetailNo", archiveDetailNo);
			getObjValue(formarchiveextendpactedit, archiveInfoDetail);
		}
		model.addAttribute("formarchiveextendpactedit", formarchiveextendpactedit);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_PactDetail";
	}

	/**
	 * 保存非系统生成合同后-跳转回非系统合同列表
	 * @param archiveMainNo
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getExendListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getExendListHtmlAjax(String archiveMainNo,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);
			List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getList(archiveInfoDetail);
			if(archiveInfoDetailList.size()>0){
				dataMap.put("ifHasList", "1");
			}else{
				dataMap.put("ifHasList", "0");
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", archiveInfoDetailList, null, true);
			dataMap.put("tableData", tableHtml);
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

	/**
	 * 凭证归档-新增界面
	 * 20200911改为原始凭证和他项凭证，都是直接选择 银行授信和业务的混合
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputVoucher")
	public String inputVoucher(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getRegName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		//更新信息
		String brNo = User.getOrgNo(request);
		CodeUtils cu = new CodeUtils();
		Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
		String dizhiya = constantMap.get("dizhiya");
		if(brNo.contains(dizhiya)){//抵质押小组
			FormData formarchivevoucherother = formService.getFormData("archivevoucherother");
			List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
			archiveInfoMain.setOptType("1");//抵质押落实小组归档
			getObjValue(formarchivevoucherother, archiveInfoMain);
			model.addAttribute("optType", "1");//他项凭证
			model.addAttribute("formarchivevoucherother", formarchivevoucherother);
			model.addAttribute("certiInfoList", certiInfoList);
			model.addAttribute("query", "");
			return "/component/archives/ArchiveInfoMain_InsertForVoucher";
		}else{//非抵质押小组，归档原始凭证
			FormData formarchivevoucherorgininsert = formService.getFormData("archivevoucherorgininsert");
			archiveInfoMain.setOptType("3");//原始凭证
			String archiveMainNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_MAIN_NO);
			archiveInfoMain.setArchiveMainNo(archiveMainNo);
			getObjValue(formarchivevoucherorgininsert, archiveInfoMain);
			model.addAttribute("formarchivevoucherorgininsert", formarchivevoucherorgininsert);
			model.addAttribute("query", "");
			model.addAttribute("archiveMainNo", archiveMainNo);
			return "/component/archives/ArchiveInfoMain_InsertForVoucherOrgin";
		}
	}

	/**
	 * 选择授信时加载的 他项凭证界面
	 * @param model
	 * @param creditAppId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputVoucherForCredit")
	public String inputVoucherForCredit(Model model, String creditAppId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivevoucherother = null;
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		String archiveMainNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_MAIN_NO);
		MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
		mfCusCreditApply.setCreditAppId(creditAppId);
		mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
		formarchivevoucherother = formService.getFormData("archivevoucherother");
		List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setAppId(creditAppId);
		List<CertiInfo> list = certiInfoFeign.getListByAppId(certiInfo);
		for(CertiInfo certi : list){
			//他项凭证，为已打回，或者明细里没有，才能被归档
			ArchiveInfoDetail voucherOtherArchiveDetail = new ArchiveInfoDetail();
			voucherOtherArchiveDetail.setDocBizNo(certi.getCollateralId());
			voucherOtherArchiveDetail.setCreditAppId(creditAppId);
			List<ArchiveInfoDetail> voucherOtherDetailList = archiveInfoDetailFeign.getList(voucherOtherArchiveDetail);
			boolean voucherOtherFlag = true;//是否能归档
			for(int k = 0;k<voucherOtherDetailList.size();k++){
				if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(voucherOtherDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
					//判断是否为已出库
					if(BizPubParm.ARCHIVE_DOC_STATE_10.equals(voucherOtherDetailList.get(k).getArchiveDocStatus())){
						//已出库的时候，要判断借出状态是否为归还新凭证
						ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
						archiveInfoBorrowDetail.setArchiveDetailNo(voucherOtherDetailList.get(k).getArchiveDetailNo());
						List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
						if(archiveInfoBorrowDetail != null){
							if("02".equals(archiveInfoBorrowDetailList.get(0).getBorrowSts())){//归还新凭证，需要可以归档

							}else{
								voucherOtherFlag = false;
								break;
							}
						}
					}else{
						voucherOtherFlag = false;
						break;
					}
				}//在为已出库的时候判断是否为归还新凭证
			}
			if(voucherOtherFlag){//没有被归档过
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(certi.getCollateralId());
				pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
				certi.setCertificateName(pledgeBaseInfo.getCertificateName());//权属人
				certi.setExtLstr03(pledgeBaseInfo.getPleCertificateOwner());//原始凭证号
				certi.setExtLstr04(pledgeBaseInfo.getExtLstr04());//原始凭证名称
				certiInfoList.add(certi);
			}
		}
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getOrgName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoMain.setUpdateBrName(User.getOrgName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());
		archiveInfoMain.setArchivePactStatus("01");
		archiveInfoMain.setCusNo(mfCusCreditApply.getCusNo());
		archiveInfoMain.setCusName(mfCusCreditApply.getCusName());
		archiveInfoMain.setKindNo(mfCusCreditApply.getTemplateCreditId());
		archiveInfoMain.setKindName(mfCusCreditApply.getTemplateCreditName());
		archiveInfoMain.setCreditAppId(mfCusCreditApply.getCreditAppId());
		archiveInfoMain.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
		archiveInfoMain.setBusiNo(mfCusCreditApply.getCreditAppNo());
		archiveInfoMain.setOptType("1");//抵质押落实小组归档
		getObjValue(formarchivevoucherother, archiveInfoMain);
		model.addAttribute("optType", "1");
		model.addAttribute("formarchivevoucherother", formarchivevoucherother);
		model.addAttribute("certiInfoList", certiInfoList);
		model.addAttribute("creditAppId", creditAppId);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_InsertForVoucher";
	}

	/**
	 * 选择单笔时加载的 他项凭证界面
	 * @param model
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputVoucherForBus")
	public String inputVoucherForBus(Model model, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		String archiveMainNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_MAIN_NO);
		MfBusApply mfBusApply = new MfBusApply();
		mfBusApply.setAppId(appId);
		mfBusApply = mfBusApplyFeign.getById(mfBusApply);
		FormData formarchivevoucherother = formService.getFormData("archivevoucherother");
		List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
		CertiInfo certiInfo = new CertiInfo();
		certiInfo.setAppId(appId);
		List<CertiInfo> list = certiInfoFeign.getListByAppId(certiInfo);
		for(CertiInfo certi : list){
			//他项凭证，为已打回，或者明细里没有，才能被归档
			ArchiveInfoDetail voucherOtherArchiveDetail = new ArchiveInfoDetail();
			voucherOtherArchiveDetail.setDocBizNo(certi.getCollateralId());
			voucherOtherArchiveDetail.setCreditAppId(mfBusApply.getCreditAppId());
			List<ArchiveInfoDetail> voucherOtherDetailList = archiveInfoDetailFeign.getList(voucherOtherArchiveDetail);
			boolean voucherOtherFlag = true;//是否能归档
			for(int k = 0;k<voucherOtherDetailList.size();k++){
				if(!BizPubParm.ARCHIVE_DOC_STATE_03.equals(voucherOtherDetailList.get(k).getArchiveDocStatus())){//只有被打回，才能归档
					//判断是否为已出库
					if(BizPubParm.ARCHIVE_DOC_STATE_10.equals(voucherOtherDetailList.get(k).getArchiveDocStatus())){
						//已出库的时候，要判断借出状态是否为归还新凭证
						ArchiveInfoBorrowDetail archiveInfoBorrowDetail = new ArchiveInfoBorrowDetail();
						archiveInfoBorrowDetail.setArchiveDetailNo(voucherOtherDetailList.get(k).getArchiveDetailNo());
						List<ArchiveInfoBorrowDetail> archiveInfoBorrowDetailList = archiveInfoBorrowDetailFeign.getListByArchiveInfoBorrowDetail(archiveInfoBorrowDetail);
						if(archiveInfoBorrowDetail != null){
							if("02".equals(archiveInfoBorrowDetailList.get(0).getBorrowSts())){//归还新凭证，需要可以归档

							}else{
								voucherOtherFlag = false;
								break;
							}
						}
					}else{
						voucherOtherFlag = false;
						break;
					}
				}//在为已出库的时候判断是否为归还新凭证
			}
			if(voucherOtherFlag){//没有被归档过
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(certi.getCollateralId());
				pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
				certi.setCertificateName(pledgeBaseInfo.getCertificateName());//权属人
				certi.setExtLstr03(pledgeBaseInfo.getPleCertificateOwner());//原始凭证号
				certi.setExtLstr04(pledgeBaseInfo.getExtLstr04());//原始凭证名称
				certiInfoList.add(certi);
			}
		}
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
		archiveInfoMain.setArchiveOpName(User.getRegName(request));
		archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
		archiveInfoMain.setArchiveBrName(User.getOrgName(request));
		archiveInfoMain.setArchiveDate(DateUtil.getDate());
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoMain.setUpdateBrName(User.getOrgName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());
		archiveInfoMain.setArchivePactStatus("02");
		archiveInfoMain.setCusNo(mfBusApply.getCusNo());
		archiveInfoMain.setCusName(mfBusApply.getCusName());
		archiveInfoMain.setKindNo(mfBusApply.getKindNo());
		archiveInfoMain.setKindName(mfBusApply.getKindName());
		archiveInfoMain.setPactId(mfBusApply.getPactId());
		archiveInfoMain.setPactNo(mfBusApply.getLetterPactNo());
		archiveInfoMain.setAppId(mfBusApply.getAppId());
		archiveInfoMain.setAppName(mfBusApply.getAppName());
		archiveInfoMain.setCreditAppId(mfBusApply.getCreditAppId());
		archiveInfoMain.setCreditAppNo(mfBusApply.getCreditPactNo());
		archiveInfoMain.setAgenciesId(mfBusApply.getAgenciesId());
		archiveInfoMain.setAgenciesName(mfBusApply.getAgenciesName());
		archiveInfoMain.setBusiNo(mfBusApply.getLetterPactNo());
		archiveInfoMain.setOptType("1");//抵质押落实小组归档
		getObjValue(formarchivevoucherother, archiveInfoMain);
		model.addAttribute("optType", "1");
		model.addAttribute("formarchivevoucherother", formarchivevoucherother);
		model.addAttribute("certiInfoList", certiInfoList);
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_InsertForVoucher";
	}

	/**
	 * 新增原始凭证
	 * @param model
	 * @param archiveMainNo
	 * @param relationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOriginVoucher")
	public String addOriginVoucher(Model model,String archiveMainNo,String archivePactStatus, String relationId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchiveoriginvoucher = formService.getFormData("archiveoriginvoucher");
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		String archiveDetailNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_DETAIL_NO);
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		if("01".equals(archivePactStatus)){
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCreditAppId(relationId);
			mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
			archiveInfoDetail.setCusNo(mfCusCreditApply.getCusNo());
			archiveInfoDetail.setCusName(mfCusCreditApply.getCusName());
			archiveInfoDetail.setKindNo(mfCusCreditApply.getTemplateCreditId());
			archiveInfoDetail.setKindName(mfCusCreditApply.getTemplateCreditName());
			archiveInfoDetail.setCreditAppId(mfCusCreditApply.getCreditAppId());
			archiveInfoDetail.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
		}else if("02".equals(archivePactStatus)){
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setAppId(relationId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			archiveInfoDetail.setCusNo(mfBusApply.getCusNo());
			archiveInfoDetail.setCusName(mfBusApply.getCusName());
			archiveInfoDetail.setKindNo(mfBusApply.getKindNo());
			archiveInfoDetail.setKindName(mfBusApply.getKindName());
			archiveInfoDetail.setPactId(mfBusApply.getPactId());
			archiveInfoDetail.setPactNo(mfBusApply.getLetterPactNo());
			archiveInfoDetail.setAppId(mfBusApply.getAppId());
			archiveInfoDetail.setAppName(mfBusApply.getAppName());
		}
		archiveInfoDetail.setArchiveMainNo(archiveMainNo);
		archiveInfoDetail.setUpdateOpNo(User.getRegNo(request));
		archiveInfoDetail.setUpdateOpName(User.getRegName(request));
		archiveInfoDetail.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoDetail.setUpdateBrName(User.getOrgName(request));
		getObjValue(formarchiveoriginvoucher, archiveInfoDetail);
		model.addAttribute("formarchiveoriginvoucher", formarchiveoriginvoucher);
		model.addAttribute("query", "");
		model.addAttribute("archiveDetailNo", archiveDetailNo);
		model.addAttribute("archivePactStatus", archivePactStatus);
		return "/component/archives/ArchiveInfoMain_EditForOriginVoucher";
	}

	/**
	 * 保存原始凭证
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertOriginVoucherAjax")
	@ResponseBody
	public  Map <String, Object> insertOriginVoucherAjax(String ajaxData)throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map <String, Object> dataMap = new HashMap <String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			dataMap = getMapByJson(ajaxData);
			FormData formarchiveoriginvoucher = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchiveoriginvoucher, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formarchiveoriginvoucher)) {
				setObjValue(formarchiveoriginvoucher, archiveInfoDetail);
				archiveInfoDetailFeign.insertOriginVoucherAjax(archiveInfoDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 保存原始凭证后-跳转列表
	 * @param archiveMainNo
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOriginListHtmlAjax")
	@ResponseBody
	public Map<String, Object> getOriginListHtmlAjax(String archiveMainNo,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_03);
			List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getList(archiveInfoDetail);
			if(archiveInfoDetailList.size()>0){
				dataMap.put("ifHasList", "1");
			}else{
				dataMap.put("ifHasList", "0");
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, "tableTag", archiveInfoDetailList, null, true);
			dataMap.put("tableData", tableHtml);
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

	/**
	 * AJAX新增-项目经理新增（原始凭证）
	 * 该项目存在归档待确认的记录，不能发起归档申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertVoucherOrgin")
	@ResponseBody
	public Map<String, Object> insertVoucherOrgin(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormData(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				if("01".equals(archiveInfoMain.getArchivePactStatus())){
					MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
					mfCusCreditApply.setCreditAppId(archiveInfoMain.getCreditAppId());
					mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
					archiveInfoMain.setCusNo(mfCusCreditApply.getCusNo());
					archiveInfoMain.setCusName(mfCusCreditApply.getCusName());
					archiveInfoMain.setKindNo(mfCusCreditApply.getTemplateCreditId());
					archiveInfoMain.setKindName(mfCusCreditApply.getTemplateCreditName());
					archiveInfoMain.setCreditAppId(mfCusCreditApply.getCreditAppId());
					archiveInfoMain.setCreditAppNo(mfCusCreditApply.getCreditAppNo());
				}else if("02".equals(archiveInfoMain.getArchivePactStatus())){
					MfBusApply mfBusApply = new MfBusApply();
					mfBusApply.setAppId(archiveInfoMain.getAppId());
					mfBusApply = mfBusApplyFeign.getById(mfBusApply);
					archiveInfoMain.setCusNo(mfBusApply.getCusNo());
					archiveInfoMain.setCusName(mfBusApply.getCusName());
					archiveInfoMain.setKindNo(mfBusApply.getKindNo());
					archiveInfoMain.setKindName(mfBusApply.getKindName());
					archiveInfoMain.setPactId(mfBusApply.getPactId());
					archiveInfoMain.setPactNo(mfBusApply.getLetterPactNo());
					archiveInfoMain.setAppId(mfBusApply.getAppId());
					archiveInfoMain.setAppName(mfBusApply.getAppName());
				}
				archiveInfoMain.setArchiveOpNo(User.getRegNo(request));
				archiveInfoMain.setArchiveOpName(User.getRegName(request));
				archiveInfoMain.setArchiveBrNo(User.getOrgNo(request));
				archiveInfoMain.setArchiveBrName(User.getOrgName(request));
				archiveInfoMain.setArchiveDate(DateUtil.getDate());
				archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
				archiveInfoMain.setUpdateOpName(User.getRegName(request));
				archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
				archiveInfoMain.setUpdateBrName(User.getOrgName(request));
				archiveInfoMain.setUpdateDate(DateUtil.getDate());
				archiveInfoMainFeign.insertVoucherOrgin(archiveInfoMain);
				dataMap.put("flag", "success");
				dataMap.put("msg", "原始凭证归档成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "凭证归档失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 他项凭证列表-编辑权证信息--归档申请
	 * @param model
	 * @param certiId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editForListNew")
	public String editForListNew(Model model,String certiId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivecertiedit = formService.getFormData("archivecertiedit");
		getFormValue(formarchivecertiedit);
		if(StringUtil.isNotEmpty(certiId)){
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCertiId(certiId);
			certiInfo = certiInfoFeign.getById(certiInfo);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			getObjValue(formarchivecertiedit, pledgeBaseInfo);
			getObjValue(formarchivecertiedit, certiInfo);
		}
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("formarchivecertiedit", formarchivecertiedit);
		return "/component/archives/ArchiveInfoMain_EditForList";
	}

	/**
	 * 他项凭证列表-编辑权证信息--归档确认
	 * @param model
	 * @param certiId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/editForConfim")
	public String editForConfim(Model model,String certiId,String appId,String archiveMainNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formarchivecertiedit = formService.getFormData("archivecertiedit");
		getFormValue(formarchivecertiedit);
		if(StringUtil.isNotEmpty(certiId)){
			CertiInfo certiInfo = new CertiInfo();
			certiInfo.setCertiId(certiId);
			certiInfo = certiInfoFeign.getById(certiInfo);
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			getObjValue(formarchivecertiedit, pledgeBaseInfo);
			getObjValue(formarchivecertiedit, certiInfo);
			model.addAttribute("collateralId", certiInfo.getCollateralId());

		}
		model.addAttribute("queryFile", "query");
//		model.addAttribute("cusNo", mfBusApply.getCusNo());
		//要件的展示方式：0块状1列表
		List<Object> parmDics = busiCacheInterface.getParmDicList("DOC_SHOW_TYPE");
		for (Object o :parmDics){
			ParmDic p = (ParmDic) o;
			String docShowType = p.getOptCode();
			model.addAttribute("docShowType", docShowType);
		}
		String scNo = BizPubParm.SCENCE_TYPE_DOC_PLE_REG;
		model.addAttribute("scNo", scNo);
		model.addAttribute("query", "");
		model.addAttribute("appId", appId);
		model.addAttribute("archiveMainNo", archiveMainNo);
		model.addAttribute("formarchivecertiedit", formarchivecertiedit);
		return "/component/archives/ArchiveInfoMain_EditForConfim";
	}

	/**
	 * 归档申请-保存他项凭证的权证信息
	 * @param ajaxData
	 * @param appId
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateForListAjax")
	@ResponseBody
	public Map<String, Object> updateForListAjax(String ajaxData,String appId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formarchivecertiedit = formService.getFormData("archivecertiedit");
			getFormValue(formarchivecertiedit, getMapByJson(ajaxData));
			if (this.validateFormData(formarchivecertiedit)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formarchivecertiedit, certiInfo);
				certiInfoFeign.update(certiInfo);
				certiInfo = new CertiInfo();
				certiInfo.setAppId(appId);
				List<CertiInfo> certiInfoList = certiInfoFeign.getListByAppId(certiInfo);
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
				dataMap.put("tableData", tableHtml);
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

	/**
	 * 归档确认-保存他项凭证的权证信息
	 * @param ajaxData
	 * @param appId
	 * @param tableId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateCertiForConfim")
	@ResponseBody
	public Map<String, Object> updateCertiForConfim(String ajaxData,String appId,String tableId,String archiveMainNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formarchivecertiedit = formService.getFormData("archivecertiedit");
			getFormValue(formarchivecertiedit, getMapByJson(ajaxData));
			if (this.validateFormData(formarchivecertiedit)) {
				CertiInfo certiInfo = new CertiInfo();
				setObjValue(formarchivecertiedit, certiInfo);
				CodeUtils cu = new CodeUtils();
				Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
				String otherVoucher = constantMap.get("otherVoucher");
				DocManage docManageQuery = new DocManage();
				docManageQuery.setDocBizNo(certiInfo.getCollateralId());
				docManageQuery.setDocSplitNo(otherVoucher);
				List<DocManage> docManageList = docManageFeign.getList(docManageQuery);
				if(docManageList == null || docManageList.size() <= 0){//未上传他项凭证
					dataMap.put("flag", "error");
					dataMap.put("msg", "必须上传他项凭证！");
					return dataMap;
				}
				certiInfoFeign.update(certiInfo);

				//top界面渲染
				List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
				List<ArchiveInfoDetail> voucherDetailList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail archiveInfoDetailQuery = new ArchiveInfoDetail();
				archiveInfoDetailQuery.setArchiveMainNo(archiveMainNo);
				voucherDetailList = archiveInfoDetailFeign.getList(archiveInfoDetailQuery);
				for(ArchiveInfoDetail archiveInfoDetail : voucherDetailList){
					CertiInfo certiInfoQuery = new CertiInfo();
					certiInfoQuery.setCollateralId(archiveInfoDetail.getDocBizNo());
					certiInfoQuery = certiInfoFeign.getByCollateralId(certiInfoQuery);
					PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
					pledgeBaseInfo.setPledgeNo(certiInfoQuery.getCollateralId());
					pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
					certiInfoQuery.setCertificateName(pledgeBaseInfo.getCertificateName());//权属人
					certiInfoQuery.setExtLstr03(pledgeBaseInfo.getPleCertificateOwner());//原始凭证号
					certiInfoQuery.setExtLstr04(pledgeBaseInfo.getExtLstr04());//原始凭证名称
					certiInfoList.add(certiInfoQuery);
				}
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", certiInfoList, null, true);
				dataMap.put("tableData", tableHtml);
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

	/**
	 * 归档确认
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputConfim")
	public String inputConfim(Model model, String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivemainconfim = null;
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
		//更新信息
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoMain.setUpdateBrName(User.getRegName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());

		archiveInfoMain.setConfimOpNo(User.getRegNo(request));
		archiveInfoMain.setConfimOpName(User.getRegName(request));
		archiveInfoMain.setConfimBrNo(User.getOrgNo(request));
		archiveInfoMain.setConfimBrName(User.getRegName(request));
		archiveInfoMain.setConfimTime(DateUtil.getDateTime());
		if("1".equals(archiveInfoMain.getOptType())){//抵质押落实小组归档他项凭证
			formarchivemainconfim = formService.getFormData("archivemainvoucherconfim");
			List<CertiInfo> certiInfoList = new ArrayList<CertiInfo>();
			List<ArchiveInfoDetail> voucherDetailList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail archiveInfoDetailQuery = new ArchiveInfoDetail();
			archiveInfoDetailQuery.setArchiveMainNo(archiveMainNo);
			voucherDetailList = archiveInfoDetailFeign.getList(archiveInfoDetailQuery);
			for(ArchiveInfoDetail archiveInfoDetail : voucherDetailList){
				CertiInfo certiInfo = new CertiInfo();
				certiInfo.setCollateralId(archiveInfoDetail.getDocBizNo());
				certiInfo = certiInfoFeign.getByCollateralId(certiInfo);
				PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
				pledgeBaseInfo.setPledgeNo(certiInfo.getCollateralId());
				pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
				certiInfo.setCertificateName(pledgeBaseInfo.getCertificateName());//权属人
				certiInfo.setExtLstr03(pledgeBaseInfo.getPleCertificateOwner());//原始凭证号
				certiInfo.setExtLstr04(pledgeBaseInfo.getExtLstr04());//原始凭证名称
				certiInfoList.add(certiInfo);
			}
			//获取档案号-以客户为单位，首先查询业务系统，
			String cusArchiveNo = getCusArchiveNo(archiveInfoMain.getCusNo());
			archiveInfoMain.setCusArchiveNo(cusArchiveNo);
			getObjValue(formarchivemainconfim, archiveInfoMain);
			model.addAttribute("optType", "1");
			model.addAttribute("formarchivemainconfim", formarchivemainconfim);
			model.addAttribute("certiInfoList", certiInfoList);
			model.addAttribute("appId", archiveInfoMain.getAppId());
			model.addAttribute("archiveMainNo", archiveInfoMain.getArchiveMainNo());
			model.addAttribute("query", "");
			return "/component/archives/ArchiveInfoMain_ConfimForVoucher";
		}else if("3".equals(archiveInfoMain.getOptType())){//原始凭证
			formarchivemainconfim = formService.getFormData("archivemainvoucherconfim");
			List<ArchiveInfoDetail> voucherList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail creditVoucherInfoDetail = new ArchiveInfoDetail();
			creditVoucherInfoDetail.setArchiveMainNo(archiveMainNo);
			creditVoucherInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_03);
			voucherList = archiveInfoDetailFeign.getList(creditVoucherInfoDetail);
			model.addAttribute("voucherList", voucherList);

			//获取档案号-以客户为单位，首先查询业务系统，
			String cusArchiveNo = getCusArchiveNo(archiveInfoMain.getCusNo());
			archiveInfoMain.setCusArchiveNo(cusArchiveNo);
			getObjValue(formarchivemainconfim, archiveInfoMain);
			model.addAttribute("formarchivemainconfim", formarchivemainconfim);
			model.addAttribute("query", "");
			model.addAttribute("isDoc", false);
			model.addAttribute("archiveMainNo", archiveMainNo);
			model.addAttribute("cusNo", archiveInfoMain.getCusNo());
			model.addAttribute("appId", archiveInfoMain.getAppId());
			model.addAttribute("pactId", archiveInfoMain.getPactId());
			return "/component/archives/ArchiveInfoMain_ConfimForVoucherOri";
		}else{
			formarchivemainconfim = formService.getFormData("archivemainconfim");
			if("01".equals(archiveInfoMain.getArchivePactStatus())){//授信
				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(archiveInfoMain.getCreditAppId());
				mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
				//客户资料
				List<ArchiveInfoDetail> docManageList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail docDetail = new ArchiveInfoDetail();
				docDetail.setArchiveMainNo(archiveMainNo);
				docDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_01);
				docManageList = archiveInfoDetailFeign.getList(docDetail);
				model.addAttribute("docManageList", docManageList);

				//其他资料
				List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail paperDetail = new ArchiveInfoDetail();
				paperDetail.setArchiveMainNo(archiveMainNo);
				paperDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_04);
				paperInfoDetailList = archiveInfoDetailFeign.getList(paperDetail);
				model.addAttribute("paperInfoDetailList", paperInfoDetailList);

				//授信-合同模板
				List<ArchiveInfoDetail> creditTemplateList = new ArrayList<ArchiveInfoDetail>();
				if(StringUtil.isNotEmpty(mfCusCreditApply.getCreditAppId())){
					ArchiveInfoDetail creditTmplateDetail = new ArchiveInfoDetail();
					creditTmplateDetail.setArchiveMainNo(archiveMainNo);
					creditTmplateDetail.setDocBizNo(mfCusCreditApply.getCreditAppId());
					creditTmplateDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_02);
					creditTemplateList = archiveInfoDetailFeign.getList(creditTmplateDetail);
				}
				model.addAttribute("creditTemplateList", creditTemplateList);

				//非系统生成合同
				List<ArchiveInfoDetail> pactDetailList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail extendDetail = new ArchiveInfoDetail();
				extendDetail.setArchiveMainNo(archiveMainNo);
				extendDetail.setArchiveDocStatus(BizPubParm.ARCHIVE_DOC_STATE_01);
				extendDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);
				pactDetailList = archiveInfoDetailFeign.getList(extendDetail);
				model.addAttribute("pactDetailList", pactDetailList);
			}else if("02".equals(archiveInfoMain.getArchivePactStatus())){//业务
				MfBusApply mfBusApply = new MfBusApply();
				mfBusApply.setAppId(archiveInfoMain.getAppId());
				mfBusApply = mfBusApplyFeign.getById(mfBusApply);
				//客户资料
				List<ArchiveInfoDetail> docManageList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail docDetail = new ArchiveInfoDetail();
				docDetail.setArchiveMainNo(archiveMainNo);
				docDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_01);
				docManageList = archiveInfoDetailFeign.getList(docDetail);
				model.addAttribute("docManageList", docManageList);

				//其他资料
				List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail paperDetail = new ArchiveInfoDetail();
				paperDetail.setArchiveMainNo(archiveMainNo);
				paperDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_04);
				paperInfoDetailList = archiveInfoDetailFeign.getList(paperDetail);
				model.addAttribute("paperInfoDetailList", paperInfoDetailList);

				//授信-合同模板
				List<ArchiveInfoDetail> creditTemplateList = new ArrayList<ArchiveInfoDetail>();
				if(StringUtil.isNotEmpty(mfBusApply.getCreditAppId())){
					ArchiveInfoDetail creditTmplateDetail = new ArchiveInfoDetail();
					creditTmplateDetail.setArchiveMainNo(archiveMainNo);
					creditTmplateDetail.setDocBizNo(mfBusApply.getCreditAppId());
					creditTmplateDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_02);
					creditTemplateList = archiveInfoDetailFeign.getList(creditTmplateDetail);
				}
				model.addAttribute("creditTemplateList", creditTemplateList);

				//单笔合同模板
				List<ArchiveInfoDetail> appTemplateList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail appTemplateDetail = new ArchiveInfoDetail();
				appTemplateDetail.setArchiveMainNo(archiveMainNo);
				appTemplateDetail.setDocBizNo(mfBusApply.getAppId());
				appTemplateDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_02);
				appTemplateList = archiveInfoDetailFeign.getList(appTemplateDetail);
				model.addAttribute("appTemplateList", appTemplateList);

				//非系统生成合同
				List<ArchiveInfoDetail> pactDetailList = new ArrayList<ArchiveInfoDetail>();
				ArchiveInfoDetail extendDetail = new ArchiveInfoDetail();
				extendDetail.setArchiveMainNo(archiveMainNo);
				extendDetail.setArchiveDocStatus(BizPubParm.ARCHIVE_DOC_STATE_01);
				extendDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);
				pactDetailList = archiveInfoDetailFeign.getList(extendDetail);
				model.addAttribute("pactDetailList", pactDetailList);
			}

			//获取档案号-以客户为单位，首先查询业务系统，
			String cusArchiveNo = getCusArchiveNo(archiveInfoMain.getCusNo());
			archiveInfoMain.setCusArchiveNo(cusArchiveNo);
			getObjValue(formarchivemainconfim, archiveInfoMain);
			model.addAttribute("formarchivemainconfim", formarchivemainconfim);
			model.addAttribute("query", "");
			model.addAttribute("isDoc", true);
			model.addAttribute("archiveMainNo", archiveMainNo);
			model.addAttribute("archivePactStatus", archiveInfoMain.getArchivePactStatus());
			model.addAttribute("cusNo", archiveInfoMain.getCusNo());
			model.addAttribute("appId", archiveInfoMain.getAppId());
			model.addAttribute("pactId", archiveInfoMain.getPactId());
			return "/component/archives/ArchiveInfoMain_Confim";
		}
	}

	/**
	 * 获取档案号
	 * @param cusNo
	 * @return
	 */
	private String getCusArchiveNo(String cusNo) throws Exception {
		//获取档案号-以客户为单位，首先查询业务系统，
		String cusArchiveNo = "";
		ArchiveInfoMain mainQuery = new ArchiveInfoMain();
		mainQuery.setCusNo(cusNo);
		List<ArchiveInfoMain> cusArchiveNoList = archiveInfoMainFeign.getAll(mainQuery);
		if(cusArchiveNoList != null && cusArchiveNoList.size() > 0) {
			for(ArchiveInfoMain main : cusArchiveNoList){
				if(StringUtil.isNotEmpty(main.getCusArchiveNo())){
					cusArchiveNo = main.getCusArchiveNo();
					break;
				}
			}
		}
		if(StringUtil.isEmpty(cusArchiveNo)){//调用GGIP查询
			Map<String, String> ggipApiMap = new CodeUtils().getMapByKeyName("GGIP_API");
			//客户校验接口
			String isAllow = ggipApiMap.get("3");
			log.debug("GGIP获取客户档案号接口开关:{}",isAllow);
			if (isAllow != null) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String ggipUrl = ymlConfig.getWebservice().get("ggipUrl");
				Map<String, String> ggipParamMap = new HashMap<>();
				ggipParamMap.put("cusName", URLEncoder.encode(mfCusCustomer.getCusName(), "UTF-8"));
				ggipParamMap.put("socialCreditCode", mfCusCustomer.getIdNum());
				ggipParamMap.put("managerNo", mfCusCustomer.getCusMngNo());
				ggipUrl += "/CheckArchiveMsg/checkArchive!checkArchivesNo.action";
				log.debug("GGIP获取客户档案号接口url:{}", ggipUrl);
				log.debug("GGIP获取客户档案号接口入参,cusName:{},socialCreditCode:{}", mfCusCustomer.getCusName(),mfCusCustomer.getIdNum());
				String returnData = HttpClientUtil.sendPost(ggipParamMap, ggipUrl);
				log.debug("GGIP获取客户档案号接口返回信息{}", returnData);
				JSONObject result = JSONObject.fromObject(returnData);
				String exist = result.getString("exist");
				if(StringUtils.isNotEmpty(exist) && exist.equals("1")){
					JSONObject data =  JSONObject.fromObject(result.get("data"));
					cusArchiveNo = data.getString("archivesNo");
				}
			}
		}
		return  cusArchiveNo;
	}

	@RequestMapping(value = "/getVoucherReceiveAjax")
	@ResponseBody
	public Map<String, Object> getVoucherReceiveAjax(String archiveMainNo)throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//收据凭证
		CodeUtils cu = new CodeUtils();
		Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
		String voucherReceipt = constantMap.get("voucherReceipt");
		JsonTableUtil jtu = new JsonTableUtil();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(voucherReceipt);
		mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
		MfTemplateBizConfig mfTemplateBizConfigQuery = new MfTemplateBizConfig();
		mfTemplateBizConfigQuery.setTemBizNo(archiveMainNo);
		List<MfTemplateBizConfig> mfTemplateBizConfigList = mfTemplateBizConfigFeign.getBizConfigList(mfTemplateBizConfigQuery);
		if(mfTemplateBizConfigList==null||mfTemplateBizConfigList.size()<=0){
			MfTemplateBizConfig mfTemplateBizConfig = new MfTemplateBizConfig();
			PropertyUtils.copyProperties(mfTemplateBizConfig, mfSysTemplate);
			mfTemplateBizConfig.setTemplateBizConfigId(WaterIdUtil.getWaterId());
			mfTemplateBizConfig.setTemBizNo(archiveMainNo);
			mfTemplateBizConfig.setKindNo("ARCHIVE");
			mfTemplateBizConfig.setNodeNo("ARCHIVE");
			mfTemplateBizConfigFeign.insert(mfTemplateBizConfig);
			mfTemplateBizConfigList.add(mfTemplateBizConfig);
		}
		String  tableHtml = jtu.getJsonStr("tablevoucherreceive","tableTag", mfTemplateBizConfigList, null,true);
		dataMap.put("bizConfigs", tableHtml);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * 下载凭证收据界面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadReceipt")
	public String downloadReceipt(Model model, String archiveMainNo) throws Exception {
		ActionContext.initialize(request, response);
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
		model.addAttribute("query", "");
		model.addAttribute("archiveMainNo", archiveMainNo);
		model.addAttribute("cusNo", archiveInfoMain.getCusNo());
		model.addAttribute("appId", archiveInfoMain.getAppId());
		model.addAttribute("pactId", archiveInfoMain.getPactId());
		return "/component/archives/ArchiveInfoMain_Reciept";
	}

	/**
	 * AJAX新增-项目经理新增（资料合同）
	 * 该项目存在归档待确认的记录，不能发起归档申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertArchiveInfo")
	@ResponseBody
	public Map<String, Object> insertArchiveInfo(String ajaxData, String extendPactS, String templateNos,String templateNumStr, String templateTypeStr, String paperNos) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				archiveInfoMain.setTemplateNos(templateNos);
				archiveInfoMain.setTemplateNumStr(templateNumStr);
				archiveInfoMain.setTemplateTypeStr(templateTypeStr);
				List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
				ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
				if("01".equals(archiveInfoMain.getArchivePactStatus())){//授信
					archiveInfoMainQuery.setCreditAppId(archiveInfoMain.getCreditAppId());
					archiveInfoMainQuery.setOptType("2");//资料合同
					archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
					boolean flag = false;//是否存在 归档待确认的归档记录
					if(archiveInfoMainList != null && archiveInfoMainList.size()> 0){
						for (ArchiveInfoMain main : archiveInfoMainList){
							if(BizPubParm.ARCHIVE_STATUS_01.equals(main.getArchiveStatus())){//归档待确认
								flag = true;
								break;
							}
						}
					}
					if(flag){
						dataMap.put("flag", "error");
						dataMap.put("msg","该授信存在归档申请中的记录，不能归档。");
						return dataMap;
					}
					archiveInfoMainFeign.insertArchiveInfoForCredit(archiveInfoMain);
				}else if("02".equals(archiveInfoMain.getArchivePactStatus())){//业务
					MfBusApply mfBusApply = new MfBusApply();
					mfBusApply.setAppId(archiveInfoMain.getAppId());
					mfBusApply = mfBusApplyFeign.getById(mfBusApply);
					if(BizPubParm.YES_NO_N.equals(mfBusApply.getIsRelCredit())){//纯单笔 通过appId判断
						archiveInfoMainQuery.setAppId(archiveInfoMain.getAppId());
					}else{
						//判断其授信是否已归档
						ArchiveInfoMain archiveInfoMain1 = new ArchiveInfoMain();
						archiveInfoMain1.setCreditAppId(archiveInfoMain.getCreditAppId());
						archiveInfoMain1.setArchivePactStatus("01");
						List<ArchiveInfoMain> list = archiveInfoMainFeign.getAll(archiveInfoMain1);
						if(list.size() <= 0){
							dataMap.put("flag", "error");
							dataMap.put("msg","该业务的授信还未归档，授信项下业务需先归档授信。");
							return dataMap;
						}else{
							boolean flag = false;//该业务的合作银行是否进行授信归档 true-该业务的合作银行已经进行了授信归档 false-银行未进行归档
							for(ArchiveInfoMain main : list){
								if(StringUtil.isNotEmpty(main.getAgenciesId())){//
									if(main.getAgenciesId().equals(mfBusApply.getAgenciesId())){
										flag = true;
										break;
									}
								}
							}
							if(!flag){
								dataMap.put("flag", "error");
								dataMap.put("msg","该授信项下业务的合作银行还未归档，授信项下业务需先归档授信。");
								return dataMap;
							}
						}
						archiveInfoMainQuery.setCreditAppId(archiveInfoMain.getCreditAppId());
					}
					archiveInfoMainQuery.setOptType("2");//资料合同
					archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
					boolean flag = false;//是否存在 归档待确认的归档记录
					if(archiveInfoMainList != null && archiveInfoMainList.size()> 0){
						for (ArchiveInfoMain main : archiveInfoMainList){
							if(BizPubParm.ARCHIVE_STATUS_01.equals(main.getArchiveStatus())){//归档待确认
								flag = true;
								break;
							}
						}
					}
					if(flag){
						dataMap.put("flag", "error");
						dataMap.put("msg","该项目存在归档申请中的记录，不能归档。");
						return dataMap;
					}
					archiveInfoMainFeign.insertArchiveInfo(archiveInfoMain);
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "资料合同归档成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "资料合同归档失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX修改-项目经理新增（资料合同）
	 * 该项目存在归档待确认的记录，不能发起归档申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateArchiveInfo")
	@ResponseBody
	public Map<String, Object> updateArchiveInfo(String ajaxData, String extendPactS, String templateNos,String templateNumStr, String templateTypeStr, String paperNos) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				archiveInfoMain.setTemplateNos(templateNos);
				archiveInfoMain.setTemplateNumStr(templateNumStr);
				archiveInfoMain.setTemplateTypeStr(templateTypeStr);
				if("01".equals(archiveInfoMain.getArchivePactStatus())){//授信
					archiveInfoMainFeign.updateArchiveInfoForCredit(archiveInfoMain);
				}else if("02".equals(archiveInfoMain.getArchivePactStatus())){
					archiveInfoMainFeign.updateArchiveInfo(archiveInfoMain);
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", "资料合同归档成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "资料合同归档失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增-抵质押落实小组新增（他项凭证）
	 * 该项目存在归档待确认的记录，不能发起归档申请
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertVoucherOther")
	@ResponseBody
	public Map<String, Object> insertVoucherOther(String ajaxData,String collateralIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormDataAnchor(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				archiveInfoMain.setCreditAppNo(archiveInfoMain.getBusiNo());
				archiveInfoMain.setCollateralIds(collateralIds);
				archiveInfoMainFeign.insertVoucherOther(archiveInfoMain);
				dataMap.put("flag", "success");
				dataMap.put("msg", "归档申请成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "归档申请失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 归档确认--资料合同
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confimArchiveInfo")
	@ResponseBody
	public Map<String, Object> confimArchiveInfo(String ajaxData, String paperNos, String templateNos,String templateNumStr, String templateTypeStr,String extendPactS) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormData(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				//验证客户 档案号的唯一性
				List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
				ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
				archiveInfoMainQuery.setCusArchiveNo(archiveInfoMain.getCusArchiveNo());
				archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
				if(archiveInfoMainList != null && archiveInfoMainList.size()> 0){
					for (ArchiveInfoMain main : archiveInfoMainList){
						if(main.getCusNo().equals(archiveInfoMain.getCusNo())){//都是同一个项目的
							continue;
						}else{
							dataMap.put("flag", "error");
							dataMap.put("msg","该档案号已存在，请重新输入！");
							return dataMap;
						}
					}
				}
				archiveInfoMain.setPaperNos(paperNos);
				archiveInfoMain.setTemplateNos(templateNos);
				archiveInfoMain.setTemplateNumStr(templateNumStr);
				archiveInfoMain.setTemplateTypeStr(templateTypeStr);
				archiveInfoMain.setExtendPactS(extendPactS);
				archiveInfoMainFeign.confimArchiveInfo(archiveInfoMain);
				dataMap.put("flag", "success");
				dataMap.put("msg", "归档确认成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "归档确认失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 归档确认--原始凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confimVoucherOri")
	@ResponseBody
	public Map<String, Object> confimVoucherOri(String ajaxData, String vouchers,String voucherOriginalNoStr,String voucherOriginalStr, String voucherOtherNoStr, String voucherOtherStr) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formcwbillmanageedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formcwbillmanageedit, getMapByJson(ajaxData));
			if(this.validateFormData(formcwbillmanageedit)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formcwbillmanageedit, archiveInfoMain);
				archiveInfoMain.setVouchers(vouchers);
				archiveInfoMain.setVoucherOriginalNoStr(voucherOriginalNoStr);
				archiveInfoMain.setVoucherOriginalStr(voucherOriginalStr);
				archiveInfoMainFeign.confimVoucherOri(archiveInfoMain);
				dataMap.put("flag", "success");
				dataMap.put("msg", "归档确认成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "归档确认失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 抵质押落实小组-归档确认
	 * 验证档案号是否存在
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confimVoucherOther")
	@ResponseBody
	public Map<String, Object> confimVoucherOther(String ajaxData,String collateralIds) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formarchivemainconfim = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchivemainconfim, getMapByJson(ajaxData));
			if(this.validateFormData(formarchivemainconfim)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formarchivemainconfim, archiveInfoMain);
				//验证所选的他项凭证是否都上传他项凭证
				if(StringUtil.isNotEmpty(collateralIds)){
					String[] collateralIdArr = collateralIds.split(",");
					CodeUtils cu = new CodeUtils();
					Map<String, String> constantMap = cu.getMapByKeyName("ARCHIVE_CONSTANT");
					String otherVoucher = constantMap.get("otherVoucher");
					for(int k=0;k<collateralIdArr.length;k++){
						DocManage docManageQuery = new DocManage();
						docManageQuery.setDocBizNo(collateralIdArr[k]);
						docManageQuery.setDocSplitNo(otherVoucher);
						docManageQuery = docManageFeign.getById(docManageQuery);
						//对凭证列表进行处理，过滤掉已经在库的
						if (docManageQuery != null) {//抵质押单独归档他项凭证
							continue;
						}else{
							dataMap.put("flag", "error");
							dataMap.put("msg","所选择的他项凭证存在未上传他证的情况，请先上传他证后再归档！");
							return dataMap;
						}
					}
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg","请先选择需要归档的他项凭证！");
					return dataMap;
				}
				//验证客户 档案号的唯一性
				/*List<ArchiveInfoMain> archiveInfoMainList = new ArrayList<ArchiveInfoMain>();
				ArchiveInfoMain archiveInfoMainQuery = new ArchiveInfoMain();
				archiveInfoMainQuery.setCusArchiveNo(archiveInfoMain.getCusArchiveNo());
				archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMainQuery);
				String cusArchiveNo = "";
				if(archiveInfoMainList != null && archiveInfoMainList.size()> 0){
					for (ArchiveInfoMain main : archiveInfoMainList){
						if(main.getCusNo().equals(archiveInfoMain.getCusNo())){//都是同一个项目的
							continue;
						}else{
							dataMap.put("flag", "error");
							dataMap.put("msg","该档案号已存在，请重新输入！");
							return dataMap;
						}
					}
				}*/
				archiveInfoMain.setCollateralIds(collateralIds);
				archiveInfoMainFeign.confimVoucherOther(archiveInfoMain);
				dataMap.put("flag", "success");
				dataMap.put("msg", "归档确认成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "归档确认失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 档案封存
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputBlock")
	public String inputBlock(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivemainblock = formService.getFormData("archivemainblock");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		//更新信息
		archiveInfoMain.setUpdateOpNo(User.getRegNo(request));
		archiveInfoMain.setUpdateOpName(User.getRegName(request));
		archiveInfoMain.setUpdateBrNo(User.getOrgNo(request));
		archiveInfoMain.setUpdateBrName(User.getRegName(request));
		archiveInfoMain.setUpdateDate(DateUtil.getDate());
		archiveInfoMain.setBoxDate(DateUtil.getDate());
		archiveInfoMain.setBoxNo(User.getRegNo(request));
		archiveInfoMain.setBoxName(User.getRegName(request));
		getObjValue(formarchivemainblock, archiveInfoMain);
		model.addAttribute("formarchivemainblock", formarchivemainblock);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoMain_Block";
	}

	/**
	 * 档案封存（档案封存时，需要修改该项目的所有归档记录）
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBlockAjax")
	@ResponseBody
	public Map<String, Object> updateBlockAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formarchivemainblock = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchivemainblock, getMapByJson(ajaxData));
			if(this.validateFormData(formarchivemainblock)){
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formarchivemainblock, archiveInfoMain);
				//校验下该项目是否全部都是归档状态，如果不是，则不能封档
				ArchiveInfoMain infoMain = new ArchiveInfoMain();
				infoMain.setAppId(archiveInfoMain.getAppId());
				List<ArchiveInfoMain> archiveInfoMainList = archiveInfoMainFeign.getAll(infoMain);
				boolean flag = false;
				for(ArchiveInfoMain main : archiveInfoMainList ){
					if(!BizPubParm.ARCHIVE_STATUS_02.equals(main.getArchiveStatus())){
						flag = true;
						break;
					}
				}
				if(flag){
					dataMap.put("flag", "error");
					dataMap.put("msg", "该项目存在未归档的归档申请，不能封档！");
					return dataMap;
				}else{
					archiveInfoMainFeign.updateBlock(archiveInfoMain);
					dataMap.put("flag", "success");
					dataMap.put("msg", "档案封存成功");
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "档案封存失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 *  归档申请，归档确认详情
	 * @param model
	 * @param archiveMainNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档编号"));
		}
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
		if (archiveInfoMain == null) {
			throw new Exception("没有查询到归档主信息！");
		} else {
			//资料列表
			List<ArchiveInfoDetail> docManageList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_01);
			docManageList = archiveInfoDetailFeign.getList(archiveInfoDetail);
			model.addAttribute("docManageList", docManageList);

			List<ArchiveInfoDetail> paperInfoDetailList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail detail = new ArchiveInfoDetail();
			detail.setArchiveMainNo(archiveMainNo);
			detail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_04);
			paperInfoDetailList = archiveInfoDetailFeign.getList(detail);
			model.addAttribute("paperInfoDetailList", paperInfoDetailList);

			//合同模板
			List<ArchiveInfoDetail> successTemplateList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail templateInfoDetail = new ArchiveInfoDetail();
			templateInfoDetail.setArchiveMainNo(archiveMainNo);
			templateInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_02);
			successTemplateList = archiveInfoDetailFeign.getList(templateInfoDetail);
			model.addAttribute("successTemplateList", successTemplateList);

			//非系统生成合同
			List<ArchiveInfoDetail> pactDetailList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail pactDetail = new ArchiveInfoDetail();
			pactDetail.setArchiveMainNo(archiveMainNo);
			pactDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_05);
			pactDetailList = archiveInfoDetailFeign.getList(pactDetail);
			model.addAttribute("pactDetailList", pactDetailList);

			//凭证
			List<ArchiveInfoDetail> voucherList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail voucherInfoDetail = new ArchiveInfoDetail();
			voucherInfoDetail.setArchiveMainNo(archiveMainNo);
			voucherInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_03);
			voucherList = archiveInfoDetailFeign.getList(voucherInfoDetail);
			model.addAttribute("voucherList", voucherList);
			model.addAttribute("query", "");

			FormData formdl_archive_main01 = null;
			String archiveStatus = archiveInfoMain.getArchiveStatus();
			Map<String, String> archiveStatusMap = codeUtils.getMapByKeyName("ARCHIVE_STATUS");
			if (archiveStatusMap != null && archiveStatusMap.size() > 0) {
				archiveInfoMain.setArchiveStatusString(archiveStatusMap.get(archiveStatus));
			}
			boolean isDoc = false;
			if("2".equals(archiveInfoMain.getOptType())){//资料合同
				isDoc = true;
			}
			model.addAttribute("isDoc", isDoc);
			if("01".equals(archiveInfoMain.getArchivePactStatus())){//授信
				MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
				mfCusCreditApply.setCreditAppId(archiveInfoMain.getCreditAppId());
				mfCusCreditApply = mfCusCreditApplyFeign.getById(mfCusCreditApply);
				//金额格式化
				if (mfCusCreditApply != null && mfCusCreditApply.getCreditSum() != null) {
					mfCusCreditApply.setApplySum(MathExtend.moneyStr(mfCusCreditApply.getCreditSum()/10000));
					model.addAttribute("mfCusCreditApply", mfCusCreditApply);
				}
				formdl_archive_main01 = formService.getFormData("dlarchivemaincredit");
				getFormValue(formdl_archive_main01);
				getObjValue(formdl_archive_main01, archiveInfoMain);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				request.setAttribute("ifBizManger","2");
				String htmlStr = jsonFormUtil.getJsonStr(formdl_archive_main01, "propertySeeTag", "");
				model.addAttribute("htmlStr", htmlStr);
				model.addAttribute("archiveInfoMain", archiveInfoMain);
				model.addAttribute("archivePactStatus", archiveInfoMain.getArchivePactStatus());
				model.addAttribute("archiveMainNo", archiveMainNo);
				return "/component/archives/ArchiveInfoMain_DetailForCredit";
			}else{
				MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(archiveInfoMain.getAppId());
				// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
				Map<String, ParmDic> rateTypeMap = codeUtils.getMapObjByKeyName("RATE_TYPE");
				String rateType=mfBusPact.getRateType();
				if(StringUtil.isEmpty(rateType)){
					rateType = "4";
				}
				String rateUnit = rateTypeMap.get(rateType).getRemark();
				model.addAttribute("rateUnit", rateUnit);
				double fincRate =  MathExtend.showRateMethod(rateType,mfBusPact.getFincRate(),360,2);
				mfBusPact.setFincRate(fincRate);
				//金额格式化
				if (mfBusPact != null && mfBusPact.getPactAmt() != null) {
					mfBusPact.setFincAmt(MathExtend.moneyStr(mfBusPact.getPactAmt()/10000));
					model.addAttribute("mfBusPact", mfBusPact);
				}
				formdl_archive_main01 = formService.getFormData("dl_archive_main01");
				getFormValue(formdl_archive_main01);
				getObjValue(formdl_archive_main01, archiveInfoMain);
				JsonFormUtil jsonFormUtil = new JsonFormUtil();
				request.setAttribute("ifBizManger","2");
				String htmlStr = jsonFormUtil.getJsonStr(formdl_archive_main01, "propertySeeTag", "");
				model.addAttribute("htmlStr", htmlStr);
				model.addAttribute("archiveInfoMain", archiveInfoMain);
				model.addAttribute("archivePactStatus", archiveInfoMain.getArchivePactStatus());
				model.addAttribute("archiveMainNo", archiveMainNo);
				return "/component/archives/ArchiveInfoMain_Detail";
			}
		}
	}
	
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_main02 = formService.getFormData("dl_archive_main02");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		List<ArchiveInfoMain> archiveInfoMainList = archiveInfoMainFeign.getAll(archiveInfoMain);
		model.addAttribute("formdl_archive_main02", formdl_archive_main02);
		model.addAttribute("archiveInfoMainList", archiveInfoMainList);
		model.addAttribute("query", "");
		return "component/archives/ArchiveInfoMain_List";
	}
	/**
	 * AJAX新增
	 * @param archiveInfoDetailLog 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId, ArchiveInfoDetailLog archiveInfoDetailLog) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_main02 = formService.getFormData("dl_archive_main02");
			getFormValue(formdl_archive_main02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_main02)){
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				setObjValue(formdl_archive_main02, archiveInfoMain);
				archiveInfoMainFeign.insert(archiveInfoMain);
				getTableData(tableId,archiveInfoDetailLog);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	 * AJAX更新保存
	 * @param tableId 
	 * @param archiveInfoDetailLog 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId, ArchiveInfoDetailLog archiveInfoDetailLog) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		try{
		FormData 	formdl_archive_main02 = formService.getFormData("dl_archive_main02");
			getFormValue(formdl_archive_main02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_main02)){
				setObjValue(formdl_archive_main02, archiveInfoMain);
				archiveInfoMainFeign.update(archiveInfoMain);
				getTableData(tableId,archiveInfoDetailLog);//获取列表
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formdl_archive_main02 = formService.getFormData("dl_archive_main02");
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
		getObjValue(formdl_archive_main02, archiveInfoMain,formData);
		if(archiveInfoMain!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param archiveInfoDetailLog 
	 * @param tableId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String archiveMainNo, ArchiveInfoDetailLog archiveInfoDetailLog, String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveInfoMain = (ArchiveInfoMain)JSONObject.toBean(jb, ArchiveInfoMain.class);
			archiveInfoMainFeign.delete(archiveInfoMain);
			getTableData(tableId,archiveInfoDetailLog);//获取列表
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
	
	@RequestMapping(value = "/compressDownloadFileAjax")
	@ResponseBody
	public Map<String, Object> compressDownloadFileAjax(String archiveMainNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档编号"));
		}
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		try {
			archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
			if (archiveInfoMain == null) {
				throw new Exception("没有查询到归档主信息！");
			}
			
			ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getAll(archiveInfoDetail);
			if (archiveInfoDetailList == null || archiveInfoDetailList.size() == 0) {
				throw new Exception(MessageEnum.NO_FILE.getMessage("可下载的文件"));
			} else {
				boolean success = docFeign.compressDownloadFile(archiveMainNo, archiveInfoDetailList);
				if (success) {
					dataMap.put("success", true);
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.NO_FILE.getMessage("可下载的文件"));
				}
			}
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", "下载文件出错：" + e.getMessage());
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/downloadFile")
	public ResponseEntity<byte[]> downloadFile(Model model,String archiveMainNo) throws Exception {
		InputStream inputStream = null;
		String archivesPath = ymlConfig.getOfficeConfig().get("archives.root.folder");
		String downloadPath = archivesPath + File.separator + Constant.DOWNLOAD_DIR_NAME;
		downloadPath=downloadPath + File.separator + archiveMainNo + ".zip";
		ResponseEntity<byte[]> entity  = docFeign.getFileByFilePath(downloadPath);
//		if (zipFile.exists()) {
//			try {
//				inputStream=  new FileInputStream(zipFile.getAbsolutePath());
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//				throw new ServiceException(e);
//			}
//		}
//		String downloadFileName = archiveMainNo + ".zip";
//        HttpHeaders headers = new HttpHeaders();
//	    headers.add("Content-Disposition", "attchement;filename="+new String(downloadFileName.getBytes("utf-8"), "ISO8859-1"));
//	    HttpStatus statusCode = HttpStatus.OK;
//	    ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(toByteArray(inputStream), headers, statusCode);
		return entity;
	}
	@RequestMapping(value = "/downloadArchiveFile")
	public ResponseEntity<byte[]> downloadArchiveFile(Model model,String archiveMainNo) throws Exception {

		return docFeign.downloadArchiveFile(archiveMainNo);
	}
	
	public InputStream getDownloadFileInputStream(String archiveMainNo) throws Exception {
		return archiveInfoDetailFeign.getDownloadFileInputStream(archiveMainNo);
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

	@RequestMapping(value = "/getPactPage")
	public String getPactPage(Model model, String appId) throws Exception {
		FormService formService = new FormService();
		FormData 	formdl_archive_pact01;
		ActionContext.initialize(request,
				response);
		if (appId == null || "".equals(appId)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("申请号"));
		}
		MfBusPact mfBusPact = pactInterfaceFeign.getByAppId(appId);
		if (mfBusPact != null) {
		 	formdl_archive_pact01 = formService.getFormData("dl_archive_pact01");
			getObjValue(formdl_archive_pact01, mfBusPact);
		} else {
			throw new Exception("没有查询到项目信息！");
		}
		model.addAttribute("formdl_archive_pact01", formdl_archive_pact01);
		model.addAttribute("query", "");
		return "component/archives/ArchiveInfoMain_Pact";
	}
	
	@RequestMapping(value = "/sealByIdAjax")
	@ResponseBody
	public Map<String, Object> sealByIdAjax(String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		try {
			archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
			if (archiveInfoMain != null) {
				if (archiveInfoMain.getArchiveStatus().equals(Constant.ARCHIVE_STATUS_SEAL)) {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("该档案已封档，封档"));
				} else {
					ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
					archiveInfoDetail.setArchiveMainNo(archiveMainNo);
					archiveInfoDetail.setArchiveDocStatus(Constant.ARCHIVE_DOC_STATUS_NORMAL);
					archiveInfoDetail.setIsLend(Constant.YES);
					List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getAll(archiveInfoDetail);
					if (archiveInfoDetailList != null && archiveInfoDetailList.size() > 0) {
						dataMap.put("success", false);
						dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("存在借出未归还的归档文件，封档"));
					} else {
						boolean success = archiveInfoMainFeign.seal(archiveInfoMain.getArchiveMainNo());
						if (success) {
							dataMap.put("success", true);
							dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("封档"));
						} else {
							dataMap.put("success", false);
							dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("封档"));
						}
					}
				}
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage(e.getMessage()));
		}
		
		return dataMap;
	}
	
	/**
	 * 
	 * 方法描述： 获取前端展示用的归档文件
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-4-13 下午8:59:18
	 */
	@RequestMapping(value = "/getArchiveNodes")
	public String getArchiveNodes(Model model, String optType, ArchiveBusinessInfo archiveBusinessInfo,String flag) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		JSONObject json = new JSONObject();
		Gson gson = new Gson();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		if("01".equals(optType)){//归档
			dataMap =archiveInfoMainFeign.getArchiveNodes(archiveBusinessInfo);
		}else if("02".equals(optType)){//封挡
			dataMap =archiveInfoMainFeign.getSealNodes(archiveBusinessInfo);
		}else {
		}
		FormData formdl_archive_main03 =formService.getFormData("dl_archive_main03");
	   // request.setAttribute("zTreeNodes", dataMap.get("zTreeNodes"));
		json.put("bizConfigs", JSONArray.fromObject(dataMap.get("bizConfigs")));
		json.put("archiveBusinessInfo", JSONObject.fromObject(archiveBusinessInfo));
		String ajaxData = json.toString();
		dataMap.put("query", "query");
		model.addAttribute("formdl_archive_main03", formdl_archive_main03);
		model.addAttribute("query", "");
		model.addAttribute("optType", optType);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("flag", flag);

		String zTreeNodesString  = gson.toJson(dataMap.get("zTreeNodes"));
//		zTreeNodesString.replaceAll("\\\\", "\\");
		model.addAttribute("zTreeNodes",zTreeNodesString );
		return "component/archives/ArchiveInfoMain_fileList";
	}
	
	/**
	 * 
	 * 方法描述： 归档操作
	 * @param isDeleteBusinessDoc
	 * @return
	 * @throws Exception
	 * ArchiveResult
	 * @author zhs
	 * @date 2017-4-13 下午9:06:57
	 */
	@RequestMapping(value = "/archiveAjax")
	@ResponseBody
	public Map<String, Object> archiveAjax(String ajaxData,String isDeleteBusinessDoc,String flag) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Gson gson = new Gson();
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			String getArchiveFilesResultStr="";
			ArchiveBusinessInfo  archiveBusinessInfo = (ArchiveBusinessInfo)JSONObject.toBean(jsonObject,ArchiveBusinessInfo.class);
			//刘豪彬 结案归档与文件归档区分
			if("lawfile".equals(flag)){//结案归档
				 getArchiveFilesResultStr = archiveInfoMainFeign.getArchiveFiles1(archiveBusinessInfo);
			}else{
				 getArchiveFilesResultStr = archiveInfoMainFeign.getArchiveFiles(archiveBusinessInfo);
			}
			ArchiveResult getArchiveFilesResult = gson.fromJson(getArchiveFilesResultStr, ArchiveResult.class);
		    boolean delFlag = false;
			if("true".equals(isDeleteBusinessDoc)){
				delFlag = true;
			}
			ArchiveInfoMain archiveInfoMain = getArchiveFilesResult.getArchiveInfoMain();
			new FeignSpringFormEncoder().addParamsToBaseDomain(archiveInfoMain);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("archiveInfoMain", archiveInfoMain);
			paramMap.put("successList", getArchiveFilesResult.getSuccessList());
			paramMap.put("successTemplateList", getArchiveFilesResult.getSuccessTemplateList());
			String archiveResultStr = archiveInfoMainFeign.doArchive(paramMap,delFlag);
//			ArchiveResult doArchiveResult = new Gson().fromJson(archiveResultStr, ArchiveResult.class);
			ArchiveResult doArchiveResult = JSON.parseObject(archiveResultStr,ArchiveResult.class);
			Map<ArchiveInfoDetail, String> failedMap = doArchiveResult.getFailedMap();
			Iterator<Map.Entry<ArchiveInfoDetail, String>> entries = failedMap.entrySet().iterator();
			String failedMessage = "";
			while (entries.hasNext()) {
			    Map.Entry<ArchiveInfoDetail, String> entry = entries.next();
			    failedMessage = failedMessage + "资料[" + entry.getKey().getDocName() + "]：" + entry.getValue() + "<br>";
			}
			
			Map<MfTemplateBizConfig, String> failedTemplateMap = doArchiveResult.getFailedTemplateMap();
			Iterator<Map.Entry<MfTemplateBizConfig, String>> templateEntries = failedTemplateMap.entrySet().iterator();
			while (templateEntries.hasNext()) {
			    Map.Entry<MfTemplateBizConfig, String> templateEntry = templateEntries.next();
			    failedMessage = failedMessage + "模板文档[" + templateEntry.getKey().getDocFileName() + "]：" + templateEntry.getValue() + "<br>";
			}
			dataMap.put("flag", "success");
			int  failedCnt =doArchiveResult.getFailedMap().size() + doArchiveResult.getFailedTemplateMap().size();
			dataMap.put("msg", "成功归档" + doArchiveResult.getSuccessList().size()+ "条记录！未成功归档" + failedCnt + "条记录：<br>" + failedMessage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("error", false);
			dataMap.put("msg", "归档失败，错误信息：" + e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 封档操作
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-4-14 下午3:50:59
	 */
	@RequestMapping(value = "/sealAjax")
	@ResponseBody
	public Map<String, Object> sealAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = JSONObject.fromObject(ajaxData);
			ArchiveBusinessInfo  archiveBusinessInfo = (ArchiveBusinessInfo)JSONObject.toBean(jsonObject,ArchiveBusinessInfo.class);
			//获取主信息
			ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
			archiveInfoMain.setCusNo(archiveBusinessInfo.getCusNo());
			archiveInfoMain.setPactId(archiveBusinessInfo.getPactId());
			archiveInfoMain.setAppId(archiveBusinessInfo.getAppId());
			dataMap = archiveInfoMainFeign.doSeal(archiveInfoMain);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "归档失败，错误信息：" + e.getMessage());
		}
		return dataMap;
	}
	
	
}
