package  app.component.archives.controller;

import app.base.User;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.ArchiveLendInfo;
import app.component.archives.entity.ArchiveMergeInfo;
import app.component.archives.entity.ArchiveMergeInfoIncludeDetailAndLog;
import app.component.archives.entity.Constant;
import app.component.archives.feign.ArchiveInfoDetailFeign;
import app.component.archives.feign.ArchiveInfoMainFeign;
import app.component.archives.feign.ArchiveMergeInfoFeign;
import app.component.common.BizPubParm;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocFeign;
import app.component.docinterface.DocInterfaceFeign;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysUserFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.UploadUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MediaConvertUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Title: ArchiveInfoDetailAction.java
 * Description:归档明细信息
 * @author:yudongwei@mftcc.cn
 * @Fri Apr 07 15:04:12 CST 2017
 **/
@Controller
@RequestMapping("/archiveInfoDetail")
public class ArchiveInfoDetailController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveInfoDetailFeign archiveInfoDetailFeign;
	/** 归档主信息Feign */
	@Autowired
	private ArchiveInfoMainFeign archiveInfoMainFeign;
	/** 归档文件合并信息Feign */
	@Autowired
	private ArchiveMergeInfoFeign archiveMergeInfoFeign;
	/** 场景Feign */
	@Autowired
	private ScenceFeign scenceFeign;
	/** 要件接口 */
	@Autowired
	private DocInterfaceFeign docInterfaceFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private DocFeign docFeign;
	//全局变量
	/** 归档编号 */
	/** 下载文件名称 */
	/** 多个归档明细编号（以@分隔） */
	/** 场景集合JSONArray */
	/** 文档类型编号 */
	/** 业务场景编号 */
	//表单变量
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveInfoDetail archiveInfoDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveInfoDetailFeign.getAll(archiveInfoDetail), null,true);
		Map<String,String> dataMap= new HashMap<String,String>();
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 列表有翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formdl_archive_detail02 = formService.getFormData("dl_archive_detail02");
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
		List<ArchiveInfoDetail> archiveInfoDetailList = (List<ArchiveInfoDetail>)archiveInfoDetailFeign.findByPage(ipage).getResult();
		model.addAttribute("formdl_archive_detail02", formdl_archive_detail02);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_List";
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
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetail.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetail.setCriteriaList(archiveInfoDetail, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
			//自定义查询Bo方法
			ipage = archiveInfoDetailFeign.findByPage(ipage);
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
	 * 选择资料弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveDocList")
	public String getArchiveDocList(Model model,String appId,String docType,String relationId,String archivePactStatus) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("appId",appId);
		model.addAttribute("relationId",relationId);
		model.addAttribute("archivePactStatus",archivePactStatus);
		model.addAttribute("docType",docType);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_ListForSelect";
	}

	/**
	 * 凭证退还--选择凭证弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getArchiveVoucherList")
	public String getArchiveVoucherList(Model model,String archivePactStatus, String relationId,String docType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("archivePactStatus",archivePactStatus);
		model.addAttribute("relationId",relationId);
		model.addAttribute("docType",docType);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_ListForVoucherSelect";
	}

	/***
	 * 凭证退还申请---选择凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findVoucherByPage")
	@ResponseBody
	public Map<String, Object> findVoucherByPage(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String relationId,String docType,String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetail.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetail.setCriteriaList(archiveInfoDetail, ajaxData);//我的筛选
			if("01".equals(archivePactStatus)){//授信
				archiveInfoDetail.setCreditAppId(relationId);
			}else{
				archiveInfoDetail.setAppId(relationId);
			}
			archiveInfoDetail.setArchiveDocStatus(BizPubParm.ARCHIVE_DOC_STATE_02);//在库的
			archiveInfoDetail.setDocType(docType);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
			//自定义查询Bo方法
			ipage = archiveInfoDetailFeign.findByPage(ipage);
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
	 * 归档统计界面
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
		return "/component/archives/ArchiveInfoDetail_ListForQuery";
	}

	/**
	 * 根据appId查询归档资料详情弹窗
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPageForQuery")
	public String getDetailPageForQuery(Model model,String appId,String docType) throws Exception {
		ActionContext.initialize(request, response);
		//资料列表
		List<ArchiveInfoDetail> docManageList = new ArrayList<ArchiveInfoDetail>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setAppId(appId);
		archiveInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_01);
		docManageList = archiveInfoDetailFeign.getListForQuery(archiveInfoDetail);
		model.addAttribute("docManageList", docManageList);

		//合同模板
		List<ArchiveInfoDetail> successTemplateList = new ArrayList<ArchiveInfoDetail>();
		ArchiveInfoDetail templateInfoDetail = new ArchiveInfoDetail();
		templateInfoDetail.setAppId(appId);
		templateInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_02);
		successTemplateList = archiveInfoDetailFeign.getListForQuery(templateInfoDetail);
		model.addAttribute("successTemplateList", successTemplateList);

		//凭证
		List<ArchiveInfoDetail> voucherList = new ArrayList<ArchiveInfoDetail>();
		ArchiveInfoDetail voucherInfoDetail = new ArchiveInfoDetail();
		voucherInfoDetail.setAppId(appId);
		voucherInfoDetail.setDocType(BizPubParm.ARCHIVE_DOC_TYPE_03);
		voucherList = archiveInfoDetailFeign.getListForQuery(voucherInfoDetail);
		model.addAttribute("voucherList", voucherList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_DetailDoc";
	}

	/***
	 * 根据条件选择资料
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findQueryByPage")
	@ResponseBody
	public Map<String, Object> findQueryByPage(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetail.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetail.setCriteriaList(archiveInfoDetail, ajaxData);//我的筛选
			if(appId != null && !"".equals(appId)){
				archiveInfoDetail.setAppId(appId);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
			//自定义查询Bo方法
			ipage = archiveInfoDetailFeign.findQueryByPage(ipage);
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
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetail.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetail.setCriteriaList(archiveInfoDetail, ajaxData);//我的筛选

			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
			//自定义查询Bo方法
			ipage = archiveInfoDetailFeign.findVoucherQueryByPage(ipage);
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
	 * 删除其他资料
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePaperAjax")
	@ResponseBody
	public Map<String, Object> deletePaperAjax(String archiveDetailNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetailFeign.delete(archiveInfoDetail);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功！");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败！");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 删除原始凭证
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOriginVoucherAjax")
	@ResponseBody
	public Map<String, Object> deleteOriginVoucherAjax(String archiveDetailNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetailFeign.delete(archiveInfoDetail);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功！");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败！");
			throw e;
		}
		return dataMap;
	}

	/***
	 * 根据条件选择资料
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findDocByPage")
	@ResponseBody
	public Map<String, Object> findDocByPage(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String relationId,String docType,String archivePactStatus) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try {
			archiveInfoDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			archiveInfoDetail.setCustomSorts(ajaxData);//自定义排序参数赋值
			archiveInfoDetail.setCriteriaList(archiveInfoDetail, ajaxData);//我的筛选
			if(StringUtil.isNotEmpty(archivePactStatus)){
				if("01".equals(archivePactStatus)){//授信
					archiveInfoDetail.setCreditAppId(relationId);
				}else{
					archiveInfoDetail.setAppId(relationId);
				}
			}
			archiveInfoDetail.setArchiveDocStatus(BizPubParm.ARCHIVE_DOC_STATE_02);//在库的
			archiveInfoDetail.setDocType(docType);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("archiveInfoDetail", archiveInfoDetail));
			//自定义查询Bo方法
			ipage = archiveInfoDetailFeign.findByPage(ipage);
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
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getAll(archiveInfoDetail);
		model.addAttribute("query", "");
		model.addAttribute("archiveInfoDetailList", archiveInfoDetailList);
		return "/component/archives/ArchiveInfoDetail_List";
	}
	
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData 	formdl_archive_detail03;
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception("归档明细编号不能为空！");
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if (archiveInfoDetail != null) {
		 	formdl_archive_detail03 = formService.getFormData("dl_archive_detail03");
			getObjValue(formdl_archive_detail03, archiveInfoDetail);
		} else {
			throw new Exception("没有查询到归档明细信息！");
		}
		model.addAttribute("formdl_archive_detail03", formdl_archive_detail03);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_Detail";
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_detail02 = formService.getFormData("dl_archive_detail02");
			getFormValue(formdl_archive_detail02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_detail02)){
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				setObjValue(formdl_archive_detail02, archiveInfoDetail);
				archiveInfoDetailFeign.insert(archiveInfoDetail);
				getTableData(tableId,archiveInfoDetail);//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try{
		FormData 	formdl_archive_detail02 = formService.getFormData("dl_archive_detail02");
			getFormValue(formdl_archive_detail02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_detail02)){
				setObjValue(formdl_archive_detail02, archiveInfoDetail);
				archiveInfoDetailFeign.update(archiveInfoDetail);
				getTableData(tableId,archiveInfoDetail);;//获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 合同编辑
	 * @param model
	 * @param archiveDetailNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getTemplateForm")
	public String getTemplateForm(Model model, String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivedetailedit;
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception("归档明细编号不能为空！");
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if (archiveInfoDetail != null) {
			formarchivedetailedit = formService.getFormData("archivetemplateedit");
			getObjValue(formarchivedetailedit, archiveInfoDetail);
		} else {
			throw new Exception("没有查询到归档明细信息！");
		}
		model.addAttribute("formarchivedetailedit", formarchivedetailedit);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_TemplateEdit";
	}

	/**
	 * 凭证编辑
	 * @param model
	 * @param archiveDetailNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBigForm")
	public String getBigForm(Model model, String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formarchivedetailedit;
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception("归档明细编号不能为空！");
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if (archiveInfoDetail != null) {
			formarchivedetailedit = formService.getFormData("archivedetailedit");
			getObjValue(formarchivedetailedit, archiveInfoDetail);
		} else {
			throw new Exception("没有查询到归档明细信息！");
		}
		model.addAttribute("formarchivedetailedit", formarchivedetailedit);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_Edit";
	}

	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDetail")
	@ResponseBody
	public Map<String, Object> updateDetail(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formarchivedetailedit = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchivedetailedit, getMapByJson(ajaxData));
			if(this.validateFormData(formarchivedetailedit)){
				setObjValue(formarchivedetailedit, archiveInfoDetail);
				archiveInfoDetail.setUpdateOpNo(User.getRegNo(request));
				archiveInfoDetail.setUpdateOpName(User.getRegName(request));
				archiveInfoDetail.setUpdateBrNo(User.getOrgNo(request));
				archiveInfoDetail.setUpdateBrName(User.getOrgName(request));
				archiveInfoDetail.setUpdateDate(DateUtil.getDate());
				archiveInfoDetailFeign.update(archiveInfoDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData,String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formdl_archive_detail02 = formService.getFormData("dl_archive_detail02");
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		getObjValue(formdl_archive_detail02, archiveInfoDetail,formData);
		if(archiveInfoDetail!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String archiveDetailNo,String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveInfoDetail = (ArchiveInfoDetail)JSONObject.toBean(jb, ArchiveInfoDetail.class);
			archiveInfoDetailFeign.delete(archiveInfoDetail);
			getTableData(tableId,archiveInfoDetail);;//获取列表
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
	
	@RequestMapping(value = "/isDownLoadFileExistAjax")
	@ResponseBody
	public Map<String, Object> isDownLoadFileExistAjax(String archiveDetailNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			if (archiveInfoDetail == null) {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
			String archiveAddr = archiveInfoDetail.getArchiveAddr();
			if (archiveAddr == null || "".equals(archiveAddr)) {
				throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档地址"));
			}
			
			File file = new File(archiveAddr);
			if (file.exists()) {
				dataMap.put("success", true);
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", MessageEnum.NO_FILE_DOWNLOAD.getMessage());
			}
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_FILE_DOWNLOAD.getMessage(e.getMessage()));
			e.printStackTrace();
		}
		
		return dataMap;
	}

	@RequestMapping(value = "/downloadFile")
	public ResponseEntity<byte[]> downloadFile(String archiveDetailNo) throws Exception {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);

		ResponseEntity<byte[]> entity = docFeign.getFileByFilePath(archiveInfoDetail.getArchiveAddr());
		return entity;
	}

	public InputStream getDownloadFileInputStream(String archiveDetailNo) throws Exception {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if (archiveInfoDetail != null) {
			String archiveAddr = archiveInfoDetail.getArchiveAddr();
			File file = new File(archiveAddr);
			if (file.exists()) {
		String downloadFileName = new String(archiveInfoDetail.getDocName().getBytes(), "ISO8859-1");
				return new FileInputStream(file.getAbsolutePath());
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/compressDownloadFileAjax")
	@ResponseBody
	public Map<String, Object> compressDownloadFileAjax(String archiveMainNo,String archiveDetailNos) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		if (archiveDetailNos == null || "".equals(archiveDetailNos)) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		}
		
		ArrayList<ArchiveInfoDetail> archiveInfoDetailList = new ArrayList<ArchiveInfoDetail>();
		String[] archiveDetailNoArray = archiveDetailNos.split("@");
		if (archiveDetailNoArray != null && archiveDetailNoArray.length > 0) {
			for (String archiveDetailNo : archiveDetailNoArray) {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
				try {
					archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
					if (archiveInfoDetail != null) {
						archiveInfoDetailList.add(archiveInfoDetail);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			if (archiveInfoDetailList.size() == 0) {
				throw new Exception(MessageEnum.NO_FILE.getMessage("可下载的文件"));
			} else {
				boolean success = archiveInfoDetailFeign.compressDownloadFile(archiveMainNo, archiveInfoDetailList);
				if (success) {
					dataMap.put("success", true);
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.NO_FILE.getMessage("可下载的文件"));
				}
			}
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_FILE_DOWNLOAD.getMessage(e.getMessage()));
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/getMergeFilePage")
	public String getMergeFilePage(Model model, String archiveMainNo,String archiveDetailNos) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		if (archiveDetailNos == null || "".equals(archiveDetailNos)) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		}
		ArrayList<ArchiveInfoDetail> archiveInfoDetailList = new ArrayList<ArchiveInfoDetail>();
		String[] archiveDetailNoArray = archiveDetailNos.split("@");
		if (archiveDetailNoArray != null && archiveDetailNoArray.length > 0) {
			for (String archiveDetailNo : archiveDetailNoArray) {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
				try {
					archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
					if (archiveInfoDetail != null) {
						archiveInfoDetail.setArchiveAddr("");
						archiveInfoDetail.setDocAddr("");
						archiveInfoDetail.setDocEncryptAddr("");
						String archiveDocAttribute = archiveInfoDetail.getArchiveDocAttribute();
						if (archiveDocAttribute != null && (Constant.ARCHIVE_DOC_ATTRIBUTE_DOCUMENT.equals(archiveDocAttribute)
								|| Constant.ARCHIVE_DOC_ATTRIBUTE_PICTURE.equals(archiveDocAttribute))) {
							archiveInfoDetailList.add(archiveInfoDetail);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		FormData formdl_archive_merge01 = formService.getFormData("dl_archive_merge01");
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setArchiveMainNo(archiveMainNo);
		archiveMergeInfo.setArchiveDetailNos(archiveDetailNos);
		getObjValue(formdl_archive_merge01, archiveMergeInfo);
		String archiveInfoDetailListJSONArray = JSONArray.fromObject(archiveInfoDetailList).toString();
		model.addAttribute("formdl_archive_merge01", formdl_archive_merge01);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_MergeFile";
	}
	
	@RequestMapping(value = "/compressMergeFileAjax")
	@ResponseBody
	public Map<String, Object> compressMergeFileAjax(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formdl_archive_merge01 = formService.getFormData("dl_archive_merge01");
			getFormValue(formdl_archive_merge01, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_archive_merge01)) {
				ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
				setObjValue(formdl_archive_merge01, archiveMergeInfo);
				
		ArrayList<ArchiveInfoDetail> archiveInfoDetailList = new ArrayList<ArchiveInfoDetail>();
				String[] archiveDetailNoArray = archiveMergeInfo.getArchiveDetailNos().split("@");
				if (archiveDetailNoArray != null && archiveDetailNoArray.length > 0) {
					for (String archiveDetailNo : archiveDetailNoArray) {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
						archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
						try {
							archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
							if (archiveInfoDetail != null) {
								String archiveDocAttribute = archiveInfoDetail.getArchiveDocAttribute();
								if (archiveDocAttribute != null && (Constant.ARCHIVE_DOC_ATTRIBUTE_DOCUMENT.equals(archiveDocAttribute)
										|| Constant.ARCHIVE_DOC_ATTRIBUTE_PICTURE.equals(archiveDocAttribute))) {
									archiveInfoDetailList.add(archiveInfoDetail);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					if (archiveInfoDetailList.size() == 0) {
						throw new Exception(MessageEnum.NO_FILE.getMessage("可合并的文件"));
					} else {
						boolean success = archiveInfoDetailFeign.compressMergeFile(archiveMergeInfo, archiveInfoDetailList);
						if (success) {
							try {
								List<ArchiveMergeInfoIncludeDetailAndLog> archiveMergeInfoList = archiveMergeInfoFeign.getAllIncludeDetailAndLog(archiveMergeInfo);
								JsonTableUtil jsonTableUtil = new JsonTableUtil();
								String tableHtml = jsonTableUtil.getJsonStr("tabledl_archive_merge01", "tableTag", archiveMergeInfoList, null, true);
								dataMap.put("tableHtml", tableHtml);
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							dataMap.put("success", true);
						} else {
							dataMap.put("success", false);
							dataMap.put("msg",MessageEnum.NO_FILE.getMessage("可合并的文件"));
						}
					}
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("合并文件出错"));
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/getLendFilePage")
	public String getLendFilePage(Model model, String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		FormData 	formdl_archive_lend01;
		ActionContext.initialize(request,
				response);
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
		if (archiveInfoDetail != null) {
		 	formdl_archive_lend01 = formService.getFormData("dl_archive_lend01");
			getObjValue(formdl_archive_lend01, archiveInfoDetail);
		} else {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		}
		model.addAttribute("formdl_archive_lend01", formdl_archive_lend01);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_LendFile";
	}
	
	@RequestMapping(value = "/lendFileAjax")
	@ResponseBody
	public Map<String, Object> lendFileAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
		FormData 	formdl_archive_lend01 = formService.getFormData("dl_archive_lend01");
			getFormValue(formdl_archive_lend01, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_archive_lend01)) {
				ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				setObjValue(formdl_archive_lend01, archiveInfoDetail);
				archiveInfoDetail.setCurrentSessionRegNo(User.getRegNo(request));
				archiveInfoDetail.setCurrentSessionRegName(User.getRegName(request));
				archiveInfoDetail.setCurrentSessionOrgNo(User.getOrgNo(request));
				archiveInfoDetail.setCurrentSessionOrgName(User.getOrgName(request));
				ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
				setObjValue(formdl_archive_lend01, archiveLendInfo);
				//archiveLendInfo.setBorrower(User.getRegName(request));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("archiveInfoDetail",archiveInfoDetail);
				map.put("archiveLendInfo",archiveLendInfo);
				boolean success = archiveInfoDetailFeign.lendFile(map);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE_CONTENT.getMessage("借阅信息"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("借阅信息登记"));
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("借阅信息登记"));
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/deleteFileAjax")
	@ResponseBody
	public Map<String, Object> deleteFileAjax(String archiveDetailNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			if (archiveInfoDetail != null) {
				ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
				archiveInfoMain.setArchiveMainNo(archiveInfoDetail.getArchiveMainNo());
				archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
				if (archiveInfoMain.getArchiveStatus().equals(Constant.ARCHIVE_STATUS_SEAL)) {
					dataMap.put("success", false);
					dataMap.put("msg",MessageEnum.ERROR_RECORD_SEALED.getMessage("删除"));
				} else {
					boolean success = archiveInfoDetailFeign.deleteFile(archiveInfoDetail);
					if (success) {
						dataMap.put("success", true);
						dataMap.put("msg",MessageEnum.SUCCEED_OPERATION.getMessage("归档文件删除"));
					} else {
						dataMap.put("success", false);
						dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("归档文件删除"));
					}
				}
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("归档文件删除"));
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/recoverFileAjax")
	@ResponseBody
	public Map<String, Object> recoverFileAjax(String archiveDetailNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		}
		
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			if (archiveInfoDetail != null) {
				if (!Constant.ARCHIVE_DOC_STATUS_DELETED.equals(archiveInfoDetail.getArchiveDocStatus())) {
					dataMap.put("success", false);
					dataMap.put("msg",MessageEnum.FAILED_OPERATION.getMessage("该文件状态不正确，无法恢复"));
				} else {
					ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
					archiveInfoMain.setArchiveMainNo(archiveInfoDetail.getArchiveMainNo());
					archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
					if (archiveInfoMain.getArchiveStatus().equals(Constant.ARCHIVE_STATUS_SEAL)) {
						dataMap.put("success", false);
						dataMap.put("msg",MessageEnum.ERROR_RECORD_SEALED.getMessage("恢复"));
					} else {
						boolean success = archiveInfoDetailFeign.recoverFile(archiveInfoDetail);
						if (success) {
							dataMap.put("success", true);
							dataMap.put("msg", MessageEnum.SUCCEED_ARCHIVE_UNDO.getMessage());
						} else {
							dataMap.put("success", false);
							dataMap.put("msg",MessageEnum.FAILED_ARCHIVE_UNDO.getMessage());
						}
					}
				}
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg",MessageEnum.FAILED_ARCHIVE_UNDO.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/deleteFileByDocBizNoAjax")
	@ResponseBody
	public void deleteFileByDocBizNoAjax(String docBizNo) throws Exception {
		if (docBizNo == null || "".equals(docBizNo)) {
			throw new Exception("业务场景编号不能为空！");
		}
		
		try {
			archiveInfoDetailFeign.deleteFileByDocBizNo(docBizNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 资料预览
	 * @param archiveDetailNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/previewFileAjax")
	@ResponseBody
	public Map<String, Object> previewFileAjax(String archiveDetailNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveDetailNo == null || "".equals(archiveDetailNo)) {
			throw new Exception(MessageEnum.NO_DATA.getMessage());
		}
		
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
		try {
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			if (archiveInfoDetail != null) {
				String archiveDocAttribute = archiveInfoDetail.getArchiveDocAttribute();
				if (Constant.ARCHIVE_DOC_ATTRIBUTE_DOCUMENT.equals(archiveDocAttribute)) {
					Gson gson = new Gson();
					Map<String, String> poCnt = new HashMap<String, String>();
					poCnt.put("saveBtn", "0");// 保存按钮 0无1有
					poCnt.put("readOnly", "1");// 只读 1只读 0非只读
					String sPath = archiveInfoDetail.getArchiveAddr();
					if(StringUtil.isNotEmpty(sPath)){
						File tempFile = new File(sPath);
						String fileName = tempFile.getName();
						poCnt.put("filePath", tempFile.getParent()+File.separator);// 打开文件路径
						poCnt.put("fileName", fileName);// 打开文件名
						poCnt.put("fileType", "0");// 打开属性 0自动判断1 doc 2excel 3ppt
						poCnt.put("printBtn", "1");// 打印按钮 0无1有
						dataMap.put("poCnt", gson.toJson(poCnt));
						JSONObject archiveInfoDetailJson = JSONObject.fromObject(archiveInfoDetail);
						dataMap.put("archiveInfoDetail", archiveInfoDetailJson);
						dataMap.put("success", true);
					}else{
						dataMap.put("success", false);
						dataMap.put("msg", "文件不存在，或者不支持的文档类型！");
					}
					/**
					 * 弃用openoffice相关东西
					 */
					return dataMap;
				} else if (Constant.ARCHIVE_DOC_ATTRIBUTE_AUDIO.equals(archiveDocAttribute)|| Constant.ARCHIVE_DOC_ATTRIBUTE_VIDEO.equals(archiveDocAttribute)) {
					String rootUrl = request.getServletContext().getRealPath("/");
					String mediaCacheDirPath = rootUrl + Constant.MEDIA_CACHE_PATH + File.separator + archiveDetailNo;
					String mediaCacheFilePath = mediaCacheDirPath + File.separator + archiveDetailNo + ".mp4";
					File mediaCacheFile = new File(mediaCacheFilePath);
					if (!mediaCacheFile.exists()) {
						MediaConvertUtil mediaConvertUtil = new MediaConvertUtil(archiveInfoDetail.getArchiveAddr(),mediaCacheDirPath, archiveDetailNo);
						Thread thread = new Thread(mediaConvertUtil);
						thread.start();
						try {
							thread.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println("转码线程执行结束!");
					}
				}else {
				}
				JSONObject archiveInfoDetailJson = JSONObject.fromObject(archiveInfoDetail);
				dataMap.put("archiveInfoDetail", archiveInfoDetailJson);
				dataMap.put("success", true);
			} else {
				throw new Exception(MessageEnum.NO_DATA.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "无法预览该文件：" + e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 归档申请的时候，拼接上绝对地址
	 * @param docNo
	 * @param docBizNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/geDocManageByIdAjax")
	@ResponseBody
	public Map<String, Object> geDocManageByIdAjax(String docNo,String docBizNo) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocManage docManage = new DocManage();
		docManage.setDocNo(docNo);
		docManage.setDocBizNo(docBizNo);
		docManage = docInterfaceFeign.getDocManageById(docManage);
		if (docManage != null) {
			dataMap.put("flag", "success");
			String path = UploadUtil.getFileUploadPath();
			//根据路径类型获取上传的前缀
			docManage.setDocAddr(path + docManage.getDocAddr());
			dataMap.put("docManage", docManage);
		} else {
			dataMap.put("flag", "error");
			dataMap.put("flag","文件不存在");
		}
		return dataMap;
	}

	@RequestMapping(value = "/getImageByteArrayOutputStream")
	public ResponseEntity<byte[]> getImageByteArrayOutputStream(Model model, String archiveDetailNo) throws Exception {
		ResponseEntity<byte[]> entity = null;
		if (archiveDetailNo != null && !"".equals(archiveDetailNo)) {
			ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
			archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			String path = archiveInfoDetail.getArchiveAddr();
			entity = docFeign.getFileByFilePath(path);
		}

		return entity;
	}
	
	@RequestMapping(value = "/getAllTableHtmlAjax")
	@ResponseBody
	public Map<String, Object> getAllTableHtmlAjax(String archiveMainNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo != null && !"".equals(archiveMainNo)) {
			Map<String, List<ArchiveInfoDetail>> detailDocTypeMap = new HashMap<String, List<ArchiveInfoDetail>>();
			Map<String, String> docTypeMap = new TreeMap<String, String>();
			List<ArchiveInfoDetail> otherDetailList = new ArrayList<ArchiveInfoDetail>();
			ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
			archiveInfoDetail.setArchiveMainNo(archiveMainNo);
			archiveInfoDetail.setArchiveDocStatus(Constant.ARCHIVE_DOC_STATUS_NORMAL);
			List<ArchiveInfoDetail> archiveInfoDetailList = archiveInfoDetailFeign.getAll(archiveInfoDetail);
			if (archiveInfoDetailList != null && archiveInfoDetailList.size() > 0) {
				for (ArchiveInfoDetail detail : archiveInfoDetailList) {
					String docType = detail.getDocType();
					if (docType != null && !"".equals(docType) && !detailDocTypeMap.containsKey(docType)) {
						List<ArchiveInfoDetail> detailList = new ArrayList<ArchiveInfoDetail>();
						detailList.add(detail);
						detailDocTypeMap.put(docType, detailList);
						docTypeMap.put(docType, detail.getDocTypeName());
					} else if (docType != null && !"".equals(docType) && detailDocTypeMap.containsKey(docType)) {
						detailDocTypeMap.get(docType).add(detail);
					} else if (docType == null || "".equals(docType)) {
						otherDetailList.add(detail);
					}else {
					}
					
					String isLend = detail.getIsLend();
					if (isLend != null && !"".equals(isLend)) {
						if (isLend.equals(Constant.YES)) {
							detail.setIsLendString("借阅");
						} else {
							detail.setIsLendString("在档");
						}
					}
				}
				if (otherDetailList.size() > 0) {
					detailDocTypeMap.put("otherInfo", otherDetailList);
					docTypeMap.put("otherInfo", "其他资料");
				}
				
				if (detailDocTypeMap.size() > 0) {
					JsonTableUtil jsonTableUtil = new JsonTableUtil();
					Map<String, String> detailTableHtmlMap = new HashMap<String, String>();
					for (String docType : detailDocTypeMap.keySet()) {
						List<ArchiveInfoDetail> list = detailDocTypeMap.get(docType);
						String tableHtml = jsonTableUtil.getJsonStr("tabledl_archive_detail02", "tableTag", list, null, true);
						detailTableHtmlMap.put(docType, tableHtml);
					}
					
					JSONObject detailTableHtmlMapJson = JSONObject.fromObject(detailTableHtmlMap);
					dataMap.put("detailTableHtmlMapJson", detailTableHtmlMapJson);
					
					List<DocTypeConfig> docTypeConfigList = new ArrayList<DocTypeConfig>();
					if (docTypeMap != null && docTypeMap.keySet().size() > 0) {
						for (Map.Entry<String, String> entry : docTypeMap.entrySet()) {
							DocTypeConfig docTypeConfig = new DocTypeConfig();
							docTypeConfig.setDocType(entry.getKey());
							//前台页面借用docDesc字段显示文档类型名称
							docTypeConfig.setDocDesc(entry.getValue());
							docTypeConfigList.add(docTypeConfig);
						}
					}
					dataMap.put("docTypeConfigListJson", docTypeConfigList);
					dataMap.put("success", true);
				}
			}
		} else {
			dataMap.put("success", false);
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getAddFilePage")
	public String getAddFilePage(Model model, String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		if (archiveMainNo == null || "".equals(archiveMainNo)) {
			throw new Exception(MessageEnum.NOT_FORM_EMPTY.getMessage("归档明细编号"));
		}
		ArchiveInfoMain archiveInfoMain = new ArchiveInfoMain();
		archiveInfoMain.setArchiveMainNo(archiveMainNo);
		archiveInfoMain = archiveInfoMainFeign.getById(archiveInfoMain);
		if (archiveInfoMain.getArchiveStatus().equals(Constant.ARCHIVE_STATUS_SEAL)) {
			throw new Exception("该档案已封档，无法补充文件！");
		}
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
		String docBizNo = WaterIdUtil.getWaterId(Constant.PREFIX_ARCHIVE_ADD_NO);
		archiveInfoDetail.setDocBizNo(docBizNo);
		archiveInfoDetail.setArchiveMainNo(archiveMainNo);
		FormData formdl_archive_detail01 = formService.getFormData("dl_archive_detail01");
		getObjValue(formdl_archive_detail01, archiveInfoDetail);
		
		JSONObject json = new JSONObject();
		CodeUtils codeUtils = new CodeUtils();
		//场景选择组件
		Scence queryScence = new Scence();
		queryScence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		queryScence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = scenceFeign.findByType(queryScence);
		JSONArray scenceArray = new JSONArray();
		for (Scence scence : scenceList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", scence.getScNo());
			jsonObject.put("name", scence.getScName());
			scenceArray.add(jsonObject);
		}
		json.put("scence", scenceArray);
		/*scenceListJSONArray = JSONArray.fromObject(scenceList).toString();*/
		
		//文档类型选择组件
		JSONArray docTypeArray = codeUtils.getJSONArrayByKeyName("DOC_TYPE");
		for (int i = 0; i < docTypeArray.size(); i++) {
			docTypeArray.getJSONObject(i).put("id", docTypeArray.getJSONObject(i).getString("optCode"));
			docTypeArray.getJSONObject(i).put("name", docTypeArray.getJSONObject(i).getString("optName"));
		}
		json.put("docType", docTypeArray);
		SysUser sysUser = new SysUser();
		sysUser.setOpNoType(BizPubParm.OP_NO_TYPE1);
		List<SysUser> sysUserList = sysUserFeign.getAllUserList(sysUser);
		JSONArray sysUserArray = JSONArray.fromObject(sysUserList);
		for (int i = 0; i < sysUserArray.size(); i++) {
			sysUserArray.getJSONObject(i).put("id",
					sysUserArray.getJSONObject(i).getString("opNo"));
			sysUserArray.getJSONObject(i).put("name",
					sysUserArray.getJSONObject(i).getString("opName"));
		}
		json.put("sysUser", sysUserArray);
		String ajaxData = json.toString();
		
		model.addAttribute("formdl_archive_detail01", formdl_archive_detail01);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveInfoDetail_AddFile";
	}
	
	@RequestMapping(value = "/addFileAjax")
	@ResponseBody
	public Map<String, Object> addFileAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		FormData 	formdl_archive_detail01 = formService.getFormData("dl_archive_detail01");
			getFormValue(formdl_archive_detail01, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_archive_detail01)) {
		ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				setObjValue(formdl_archive_detail01, archiveInfoDetail);
				
				CodeUtils codeUtils = new CodeUtils();
				Map<String, String> docTypeMap = codeUtils.getMapByKeyName("DOC_TYPE");
				archiveInfoDetail.setDocTypeName(docTypeMap.get(archiveInfoDetail.getDocType()));
				List<DocTypeConfig> docTypeConfigList = docInterfaceFeign.getDocTypeConfigList(archiveInfoDetail.getDocType());
				for (DocTypeConfig docTypeConfig : docTypeConfigList) {
					if (docTypeConfig.getDocSplitNo().equals(archiveInfoDetail.getDocSplitNo())) {
						archiveInfoDetail.setDocSplitName(docTypeConfig.getDocSplitName());
						break;
					}
				}
				boolean success = archiveInfoDetailFeign.addFile(archiveInfoDetail);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("归档文件保存"));
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("归档文件保存"));
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("content","归档文件");
			paramMap.put("reason",e.getMessage());
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage(paramMap));
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/getListByDocTypeAjax")
	@ResponseBody
	public Map<String, Object> getListByDocTypeAjax(String ajaxData,String docType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (docType != null && !"".equals(docType)) {
			List<DocTypeConfig> docTypeConfigList = docInterfaceFeign.getDocTypeConfigList(docType);
			
			JSONArray docTypeConfigArray = new JSONArray();
			for (DocTypeConfig docTypeConfig : docTypeConfigList) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", docTypeConfig.getDocSplitNo());
				jsonObject.put("name", docTypeConfig.getDocSplitName());
				docTypeConfigArray.add(jsonObject);
			}
			dataMap.put("docTypeConfig", docTypeConfigArray);
			dataMap.put("success", true);
		} else {
			dataMap.put("success", false);
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getMainDetailPage")
	public String getMainDetailPage(Model model, String ajaxData,String archiveDetailNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_detail02 = null;
		String query = "query";
		if (archiveDetailNo != null && !"".equals(archiveDetailNo)) {
		ArchiveInfoDetail 	archiveInfoDetail = new ArchiveInfoDetail();
			archiveInfoDetail.setArchiveDetailNo(archiveDetailNo);
			archiveInfoDetail = archiveInfoDetailFeign.getById(archiveInfoDetail);
			if (archiveInfoDetail != null) {
				formdl_archive_detail02 = formService.getFormData("dl_archive_detail02");
				getObjValue(formdl_archive_detail02, archiveInfoDetail);
			}
		}
		model.addAttribute("formdl_archive_detail02",formdl_archive_detail02);
		model.addAttribute("query", query);
		return "/component/archives/ArchiveInfoDetail_MainDetail";
	}

	
}
