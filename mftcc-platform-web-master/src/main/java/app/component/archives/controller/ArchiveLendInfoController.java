package  app.component.archives.controller;

import app.base.User;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveLendInfo;
import app.component.archives.entity.ArchiveLendInfoIncludeDetailAndLog;
import app.component.archives.entity.Constant;
import app.component.archives.feign.ArchiveLendInfoFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: ArchiveLendInfoAction.java
 * Description:归档文件借阅信息
 * @author:yudongwei@mftcc.cn
 * @Tue Apr 11 18:04:18 CST 2017
 **/
@Controller
@RequestMapping("/archiveLendInfo")
public class ArchiveLendInfoController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveLendInfoFeign archiveLendInfoFeign;
	//全局变量
	/** 归档编号 */
	//表单变量
	
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveLendInfo archiveLendInfo) throws Exception {
		Map<String,String> dataMap = new HashMap<String, String>();
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveLendInfoFeign.getAll(archiveLendInfo), null,true);
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
		FormData formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		Ipage ipage = this.getIpage();
		List<ArchiveLendInfo> archiveLendInfoList = (List<ArchiveLendInfo>)archiveLendInfoFeign.findByPage(ipage, archiveLendInfo).getResult();
		model.addAttribute("formdl_archive_lend02", formdl_archive_lend02);
		model.addAttribute("archiveLendInfoList", archiveLendInfoList);
		model.addAttribute("query", "");
		return "ArchiveLendInfo_List";
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
		FormData formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		archiveLendInfo.setArchiveMainNo(archiveMainNo);
		archiveLendInfo.setIsReturn(Constant.NO);
		List<ArchiveLendInfoIncludeDetailAndLog> lendIncludeDetailAndLogList = archiveLendInfoFeign.getAllIncludeDetailAndLog(archiveLendInfo);
		model.addAttribute("formdl_archive_lend02", formdl_archive_lend02);
		model.addAttribute("lendIncludeDetailAndLogList", lendIncludeDetailAndLogList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveLendInfo_ListAll";
	}
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String table) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
			getFormValue(formdl_archive_lend02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_lend02)){
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
				setObjValue(formdl_archive_lend02, archiveLendInfo);
				archiveLendInfoFeign.insert(archiveLendInfo);
				getTableData(table,archiveLendInfo);//获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String table) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		try{
		FormData 	formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
			getFormValue(formdl_archive_lend02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_lend02)){
				setObjValue(formdl_archive_lend02, archiveLendInfo);
				archiveLendInfoFeign.update(archiveLendInfo);
				getTableData(table,archiveLendInfo);//获取列表
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
		FormData formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		archiveLendInfo.setId(id);
		archiveLendInfo = archiveLendInfoFeign.getById(archiveLendInfo);
		getObjValue(formdl_archive_lend02, archiveLendInfo,formData);
		if(archiveLendInfo!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id,String table) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		archiveLendInfo.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveLendInfo = (ArchiveLendInfo)JSONObject.toBean(jb, ArchiveLendInfo.class);
			archiveLendInfoFeign.delete(archiveLendInfo);
			getTableData(table,archiveLendInfo);//获取列表
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
	public Map<String, Object> getAllIncludeDetailAndLogTableHtmlAjax(String archiveMainNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (archiveMainNo != null && !"".equals(archiveMainNo)) {
			ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
			archiveLendInfo.setArchiveMainNo(archiveMainNo);
			archiveLendInfo.setIsReturn(Constant.NO);
			try {
				List<ArchiveLendInfoIncludeDetailAndLog> archiveLendInfoList = archiveLendInfoFeign.getAllIncludeDetailAndLog(archiveLendInfo);
				JsonTableUtil jsonTableUtil = new JsonTableUtil();
				String tableHtml = jsonTableUtil.getJsonStr("tabledl_archive_lend01", "tableTag", archiveLendInfoList, null, true);
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
	
	@RequestMapping(value = "/getReturnFilePage")
	public String getReturnFilePage(Model model, String id) throws Exception {
		FormService formService = new FormService();
		FormData 	formdl_archive_lend02;
		ActionContext.initialize(request,
				response);
		if (id == null || "".equals(id)) {
			throw new Exception("归档文件借阅信息主键不能为空！");
		}
		ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
		archiveLendInfo.setId(id);
		List<ArchiveLendInfoIncludeDetailAndLog> archiveLendInfoList = archiveLendInfoFeign.getAllIncludeDetailAndLog(archiveLendInfo);
		if (archiveLendInfoList != null && archiveLendInfoList.size() == 1) {
			ArchiveLendInfoIncludeDetailAndLog archiveLendInfo1  = archiveLendInfoList.get(0);
			if (Constant.YES.equals(archiveLendInfo.getIsReturn())) {
				throw new Exception("该文件已归还！");
			}
		 	formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
			getObjValue(formdl_archive_lend02, archiveLendInfo1);
		} else {
			throw new Exception("没有查询到归档明细信息！");
		}
		model.addAttribute("formdl_archive_lend02", formdl_archive_lend02);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveLendInfo_ReturnFile";
	}
	
	@RequestMapping(value = "/returnFileAjax")
	@ResponseBody
	public Map<String, Object> returnFileAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try {
		FormData 	formdl_archive_lend02 = formService.getFormData("dl_archive_lend02");
			getFormValue(formdl_archive_lend02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_archive_lend02)) {
				ArchiveInfoDetail archiveInfoDetail = new ArchiveInfoDetail();
				setObjValue(formdl_archive_lend02, archiveInfoDetail);
				archiveInfoDetail.setCurrentSessionRegNo(User.getRegNo(request));
				archiveInfoDetail.setCurrentSessionRegName(User.getRegName(request));
				archiveInfoDetail.setCurrentSessionOrgNo(User.getOrgNo(request));
				archiveInfoDetail.setCurrentSessionOrgName(User.getOrgName(request));
				ArchiveLendInfo archiveLendInfo = new ArchiveLendInfo();
				setObjValue(formdl_archive_lend02, archiveLendInfo);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("archiveInfoDetail",archiveInfoDetail);
				map.put("archiveLendInfo",archiveLendInfo);
				boolean success = archiveLendInfoFeign.returnFile(map);
				if (success) {
					dataMap.put("success", true);
					dataMap.put("msg", "归档文件归还成功！");
				} else {
					dataMap.put("success", false);
					dataMap.put("msg", "归档文件归还失败!");
				}
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", "归档文件归还失败：" + e.getMessage());
		}
		
		return dataMap;
	}

}
