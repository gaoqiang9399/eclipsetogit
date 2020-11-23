package  app.component.archives.controller;
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

import app.component.archives.entity.ArchiveBackupInfo;
import app.component.archives.feign.ArchiveBackupInfoFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: ArchiveBackupInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Apr 08 15:42:40 CST 2017
 **/
@Controller
@RequestMapping("/archiveBackupInfo")
public class ArchiveBackupInfoController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveBackupInfoFeign archiveBackupInfoFeign;
	//全局变量
	//表单变量
	
	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, ArchiveBackupInfo archiveBackupInfo) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveBackupInfoFeign.getAll(archiveBackupInfo), null,true);
		Map<String,String> dataMap = new HashMap<String,String>();
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
		FormData formdl_archive_backup02 = formService.getFormData("dl_archive_backup02");
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
		Ipage ipage = this.getIpage();
		List<ArchiveBackupInfo> archiveBackupInfoList = (List<ArchiveBackupInfo>)archiveBackupInfoFeign.findByPage(ipage, archiveBackupInfo).getResult();
		model.addAttribute("formdl_archive_backup02", formdl_archive_backup02);
		model.addAttribute("archiveBackupInfoList", archiveBackupInfoList);
		model.addAttribute("query", "");
		return "/component/app/ArchiveBackupInfo_List";
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
		FormData formdl_archive_backup02 = formService.getFormData("dl_archive_backup02");
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
		List<ArchiveBackupInfo> archiveBackupInfoList = archiveBackupInfoFeign.getAll(archiveBackupInfo);
		model.addAttribute("archiveBackupInfoList", archiveBackupInfoList);
		model.addAttribute("formdl_archive_backup02", formdl_archive_backup02);
		model.addAttribute("query", "");
		return "/component/app/ArchiveBackupInfo_List";
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
		FormData 	formdl_archive_backup02 = formService.getFormData("dl_archive_backup02");
			getFormValue(formdl_archive_backup02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_backup02)){
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
				setObjValue(formdl_archive_backup02, archiveBackupInfo);
				archiveBackupInfoFeign.insert(archiveBackupInfo);
				getTableData(tableId,archiveBackupInfo);//获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
		try{
		FormData 	formdl_archive_backup02 = formService.getFormData("dl_archive_backup02");
			getFormValue(formdl_archive_backup02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_backup02)){
				setObjValue(formdl_archive_backup02, archiveBackupInfo);
				archiveBackupInfoFeign.update(archiveBackupInfo);
				getTableData(tableId,archiveBackupInfo);//获取列表
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
		FormData formdl_archive_backup02 = formService.getFormData("dl_archive_backup02");
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
		archiveBackupInfo.setId(id);
		archiveBackupInfo = archiveBackupInfoFeign.getById(archiveBackupInfo);
		getObjValue(formdl_archive_backup02, archiveBackupInfo,formData);
		if(archiveBackupInfo!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveBackupInfo archiveBackupInfo = new ArchiveBackupInfo();
		archiveBackupInfo.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveBackupInfo = (ArchiveBackupInfo)JSONObject.toBean(jb, ArchiveBackupInfo.class);
			archiveBackupInfoFeign.delete(archiveBackupInfo);
			getTableData(tableId,archiveBackupInfo);//获取列表
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

}
