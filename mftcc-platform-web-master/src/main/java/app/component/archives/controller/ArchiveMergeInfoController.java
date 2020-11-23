package  app.component.archives.controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

import app.component.archives.entity.ArchiveMergeInfo;
import app.component.archives.entity.ArchiveMergeInfoIncludeDetailAndLog;
import app.component.archives.feign.ArchiveMergeInfoFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: ArchiveMergeInfoAction.java
 * Description:归档文件合并信息
 * @author:yudongwei@mftcc.cn
 * @Thu Apr 20 17:23:25 CST 2017
 **/
@Controller
@RequestMapping("/archiveMergeInfo")
public class ArchiveMergeInfoController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveMergeInfoFeign archiveMergeInfoFeign;
	//全局变量
	/** 下载文件名称 */
	/** 归档编号 */
	//表单变量
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveMergeInfo archiveMergeInfo) throws Exception {
		Map<String,String> dataMap = new HashMap<String, String>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveMergeInfoFeign.getAll(archiveMergeInfo), null,true);
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
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_merge02 = formService.getFormData("dl_archive_merge02");
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		Ipage ipage = this.getIpage();
		List<ArchiveMergeInfo> archiveMergeInfoList = (List<ArchiveMergeInfo>)archiveMergeInfoFeign.findByPage(ipage, archiveMergeInfo).getResult();
		model.addAttribute("formdl_archive_merge02", formdl_archive_merge02);
		model.addAttribute("archiveMergeInfoList", archiveMergeInfoList);
		model.addAttribute("query", "");
		return "ArchiveMergeInfo_List";
	}
	/**
	 * 列表全部无翻页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAllPage")
	public String getListAllPage(Model model, String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_merge02 = formService.getFormData("dl_archive_merge02");
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setArchiveMainNo(archiveMainNo);
		List<ArchiveMergeInfoIncludeDetailAndLog> mergeIncludeDetailAndLogList = archiveMergeInfoFeign.getAllIncludeDetailAndLog(archiveMergeInfo);
		model.addAttribute("formdl_archive_merge02", formdl_archive_merge02);
		model.addAttribute("mergeIncludeDetailAndLogList", mergeIncludeDetailAndLogList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveMergeInfo_ListAll";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tabel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_merge02 = formService.getFormData("dl_archive_merge02");
			getFormValue(formdl_archive_merge02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_merge02)){
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
				setObjValue(formdl_archive_merge02, archiveMergeInfo);
				archiveMergeInfoFeign.insert(archiveMergeInfo);
				getTableData(tabel,archiveMergeInfo);//获取列表
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData,String tabel) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		try{
		FormData 	formdl_archive_merge02 = formService.getFormData("dl_archive_merge02");
			getFormValue(formdl_archive_merge02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_merge02)){
				setObjValue(formdl_archive_merge02, archiveMergeInfo);
				archiveMergeInfoFeign.update(archiveMergeInfo);
				getTableData(tabel,archiveMergeInfo);//获取列表
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formdl_archive_merge02 = formService.getFormData("dl_archive_merge02");
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		archiveMergeInfo = archiveMergeInfoFeign.getById(archiveMergeInfo);
		getObjValue(formdl_archive_merge02, archiveMergeInfo,formData);
		if(archiveMergeInfo!=null){
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
	public Map<String, Object> deleteAjax(String id,String ajaxData,String tabel) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveMergeInfo = (ArchiveMergeInfo)JSONObject.toBean(jb, ArchiveMergeInfo.class);
			archiveMergeInfoFeign.delete(archiveMergeInfo);
			getTableData(tabel,archiveMergeInfo);//获取列表
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
	
	@RequestMapping(value = "/getAllIncludeDetailAndLogTableHtmlAjax")
	@ResponseBody
	public Map<String, Object> getAllIncludeDetailAndLogTableHtmlAjax(String archiveMainNo ) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo != null && !"".equals(archiveMainNo)) {
		ArchiveMergeInfo 	archiveMergeInfo = new ArchiveMergeInfo();
			archiveMergeInfo.setArchiveMainNo(archiveMainNo);
			try {
				List<ArchiveMergeInfoIncludeDetailAndLog> archiveMergeInfoList = archiveMergeInfoFeign.getAllIncludeDetailAndLog(archiveMergeInfo);
				JsonTableUtil jsonTableUtil = new JsonTableUtil();
				String tableHtml = jsonTableUtil.getJsonStr("tabledl_archive_merge01", "tableTag", archiveMergeInfoList, null, true);
				dataMap.put("tableHtml", tableHtml);
				dataMap.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("success", false);
			}
		} else {
			dataMap.put("success", false);
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/previewFileAjax")
	@ResponseBody
	public Map<String, Object> previewFileAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (id == null || "".equals(id)) {
			throw new Exception("主键不能为空！");
		}
		
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		try {
			archiveMergeInfo = archiveMergeInfoFeign.getById(archiveMergeInfo);
			if (archiveMergeInfo != null) {
				String mergeFilePath = archiveMergeInfo.getMergeFilePath();
				if (mergeFilePath != null && !"".equals(mergeFilePath)) {
					File file = new File(mergeFilePath);
					if (file.exists() && file.isFile()) {
						/**
						 * 弃用openoffice相关东西
						 */
						//Office2PDF op = new Office2PDF();
						String viewPath = null;
						if (viewPath == null) {
							throw new Exception("文件不存在，或者不支持的文档类型！");
						} else {
							String fileName = viewPath.substring(viewPath.lastIndexOf(File.separator) + 1);
							dataMap.put("viewPath", fileName);
							
							JSONObject archiveMergeInfoJson = JSONObject.fromObject(archiveMergeInfo);
							dataMap.put("archiveMergeInfo", archiveMergeInfoJson);
							dataMap.put("success", true);
						}
					} else {
						throw new Exception("合并文件不存在或格式不正确！");
					}
				} else {
					throw new Exception("没有查询到合并文件路径！");
				}
			} else {
				throw new Exception("没有查询到归档文件合并信息！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "无法预览该文件：" + e.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/isDownLoadFileExistAjax")
	@ResponseBody
	public Map<String, Object> isDownLoadFileExistAjax(String id) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (id == null || "".equals(id)) {
			throw new Exception("主键不能为空！");
		}
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		try {
			archiveMergeInfo = archiveMergeInfoFeign.getById(archiveMergeInfo);
			if (archiveMergeInfo == null) {
				throw new Exception("没有查询到归档明细信息！");
			}
			String mergeFilePath = archiveMergeInfo.getMergeFilePath();
			if (mergeFilePath == null || "".equals(mergeFilePath)) {
				throw new Exception("合并文件路径不能为空！");
			}
			
			File file = new File(mergeFilePath);
			if (file.exists()) {
				dataMap.put("success", true);
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", "没有找到可下载的文件！");
			}
		} catch (Exception e) {
			dataMap.put("success", false);
			dataMap.put("msg", "下载文件出错：" + e.getMessage());
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(Model model, String ajaxData) throws Exception {
	}
	
	public InputStream getDownloadFileInputStream(String id) throws Exception {
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		archiveMergeInfo = archiveMergeInfoFeign.getById(archiveMergeInfo);
		if (archiveMergeInfo != null) {
			String mergeFilePath = archiveMergeInfo.getMergeFilePath();
			File file = new File(mergeFilePath);
			if (file.exists()) {
		String downloadFileName = new String(archiveMergeInfo.getMergeFileName().getBytes(), "ISO8859-1");
				return new FileInputStream(file.getAbsolutePath());
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/deleteFileAjax")
	@ResponseBody
	public Map<String, Object> deleteFileAjax(String id) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (id == null || "".equals(id)) {
			throw new Exception("主键不能为空！");
		}
		
		ArchiveMergeInfo archiveMergeInfo = new ArchiveMergeInfo();
		archiveMergeInfo.setId(id);
		try {
			archiveMergeInfo = archiveMergeInfoFeign.getById(archiveMergeInfo);
			if (archiveMergeInfo != null) {
				boolean success = archiveMergeInfoFeign.deleteFile(archiveMergeInfo);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", "文件删除成功！");
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", "文件删除失败!");
				}
			} else {
				throw new Exception("没有查询到归档文件合并信息！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "文件删除失败：" + e.getMessage());
		}
		
		return dataMap;
	}

	
}
