package  app.component.archives.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.base.User;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocTypeConfigFeign;
import app.component.finance.manage.entity.CwBillManage;
import app.component.model.entity.MfSysTemplate;
import app.component.model.feign.MfSysTemplateFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
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

import app.component.archives.entity.ArchiveConfig;
import app.component.archives.feign.ArchiveConfigFeign;
import app.component.common.BizPubParm;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: ArchiveConfigAction.java
 * Description:归档管理配置
 * @author:yudongwei@mftcc.cn
 * @Wed Apr 05 16:48:41 CST 2017
 **/
@Controller
@RequestMapping("/archiveConfig")
public class ArchiveConfigController extends BaseFormBean{
	//注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ArchiveConfigFeign archiveConfigFeign;
	/** 场景Feign */
	@Autowired
	private ScenceFeign scenceFeign;
	@Autowired
	private DocTypeConfigFeign docTypeConfigFeign;
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;

	/**
	 * 列表展示
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/archives/ArchiveConfig_List";
	}

	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId,
												  String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveConfig archiveConfig = new ArchiveConfig();
		try {
			archiveConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			archiveConfig.setCustomSorts(ajaxData);// 自定义排序参数赋值
			archiveConfig.setCriteriaList(archiveConfig, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("archiveConfig", archiveConfig));
			ipage = archiveConfigFeign.findByPageAjax(ipage);
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
	 * 新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		CwBillManage cwBillManage = new CwBillManage();
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ArchiveConfig archiveConfig = new ArchiveConfig();
		FormData formarchiveconfiginsert = formService.getFormData("archiveconfiginsert");
//		archiveConfig.setId(WaterIdUtil.getWaterId());
		getObjValue(formarchiveconfiginsert, archiveConfig);
		model.addAttribute("formarchiveconfiginsert", formarchiveconfiginsert);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveConfig_Insert";
	}

	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertConfig")
	@ResponseBody
	public Map<String, Object> insertConfig(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveConfig archiveConfig = new ArchiveConfig();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formarchiveconfiginsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchiveconfiginsert, getMapByJson(ajaxData));
			if (this.validateFormData(formarchiveconfiginsert)) {
				setObjValue(formarchiveconfiginsert, archiveConfig);
				archiveConfig.setUpdateOpNo(User.getRegNo(request));
				archiveConfig.setUpdateOpName(User.getRegName(request));
				archiveConfig.setUpdateBrNo(User.getOrgNo(request));
				archiveConfig.setUpdateBrName(User.getOrgName(request));
				archiveConfigFeign.insert(archiveConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 新增页面
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		CwBillManage cwBillManage = new CwBillManage();
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ArchiveConfig archiveConfig = new ArchiveConfig();
		archiveConfig.setId(id);
		archiveConfig = archiveConfigFeign.getById(archiveConfig);
		FormData formarchiveconfigedit = formService.getFormData("archiveconfigedit");
		getObjValue(formarchiveconfigedit, archiveConfig);
		model.addAttribute("formarchiveconfigedit", formarchiveconfigedit);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveConfig_Edit";
	}

	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ArchiveConfig archiveConfig = new ArchiveConfig();
		try{
			dataMap = getMapByJson(ajaxData);
			FormData formarchiveconfiginsert = formService.getFormData(String.valueOf(dataMap.get("formId")));
			getFormValue(formarchiveconfiginsert, getMapByJson(ajaxData));
			if (this.validateFormData(formarchiveconfiginsert)) {
				setObjValue(formarchiveconfiginsert, archiveConfig);
				archiveConfig.setUpdateOpNo(User.getRegNo(request));
				archiveConfig.setUpdateOpName(User.getRegName(request));
				archiveConfig.setUpdateBrNo(User.getOrgNo(request));
				archiveConfig.setUpdateBrName(User.getOrgName(request));
				archiveConfigFeign.update(archiveConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功！");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败！");
			throw e;
		}
		return dataMap;
	}


	@RequestMapping(value = "/getDocInfo")
	public String getDocInfo(Model model) throws Exception{
		List docList = null;
		//获取文档类型列表
		DocTypeConfig docTypeConfig = new DocTypeConfig();
		List<DocTypeConfig> docTypeConfigList = docTypeConfigFeign.getList(docTypeConfig);
		JSONArray jsonArray = new JSONArray();
		JSONObject rootObj = new JSONObject();
		rootObj.put("id", "1");
		rootObj.put("name", "文档");
		rootObj.put("pId", "0");
		rootObj.put("desc","根节点");
		jsonArray.add(rootObj);
		for (DocTypeConfig typeConfig : docTypeConfigList){
			JSONObject obj = new JSONObject();
			obj.put("id", typeConfig.getDocSplitNo());
			obj.put("name", typeConfig.getDocSplitName());
			obj.put("pId", "1");
			obj.put("desc", typeConfig.getDocDesc());
			jsonArray.add(obj);
		}

		//获取合同模板列表
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setUseFlag("1");
		List<MfSysTemplate> mfSysTemplateList = mfSysTemplateFeign.getAll(mfSysTemplate);
		JSONObject templateRootObj = new JSONObject();
		templateRootObj.put("id", "2");
		templateRootObj.put("name", "模板");
		templateRootObj.put("pId", "0");
		jsonArray.add(templateRootObj);
		templateRootObj.put("desc","根节点");
		for (MfSysTemplate sysTemplate : mfSysTemplateList){
			JSONObject obj = new JSONObject();
			obj.put("id", sysTemplate.getTemplateNo());
			obj.put("name", sysTemplate.getTemplateNameZh());
			obj.put("pId", "2");
			obj.put("desc", sysTemplate.getTemplateTypeName());
			jsonArray.add(obj);
		}
		model.addAttribute("ajaxData", jsonArray);
		return "/component/archives/ArchiveConfig_DocSelect";
	}

	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId,ArchiveConfig archiveConfig) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", archiveConfigFeign.getAll(archiveConfig), null,true);
		Map<String,String>dataMap = new HashMap<String,String>();
		dataMap.put("tableData",tableHtml);
	}
	
	/**
	 * 跳转详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetailPage")
	public String getDetailPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		List<Scence> scenceList = new ArrayList<Scence>();
		ActionContext.initialize(request,
				response);
		FormData formdl_archive_config02 = formService.getFormData("dl_archive_config02");
		getFormValue(formdl_archive_config02);
		ArchiveConfig archiveConfig = new ArchiveConfig();
		setObjValue(formdl_archive_config02, archiveConfig);
		archiveConfig = archiveConfigFeign.getById(archiveConfig);
		/*String pactSignFilesOfSceneNos = archiveConfig.getPactSignFilesOfSceneNos();
		if (pactSignFilesOfSceneNos != null && pactSignFilesOfSceneNos.length() > 0) {
			pactSignFilesOfSceneNos = pactSignFilesOfSceneNos.replace("@", "|");
			archiveConfig.setPactSignFilesOfSceneNos(pactSignFilesOfSceneNos);
		}
		String pactOverFilesOfSceneNos = archiveConfig.getPactOverFilesOfSceneNos();
		if (pactOverFilesOfSceneNos != null && pactOverFilesOfSceneNos.length() > 0) {
			pactOverFilesOfSceneNos = pactOverFilesOfSceneNos.replace("@", "|");
			archiveConfig.setPactOverFilesOfSceneNos(pactOverFilesOfSceneNos);
		}*/
		getObjValue(formdl_archive_config02, archiveConfig);
		
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		scenceList = this.scenceFeign.findByType(scence);
		JSONArray scenceArray = JSONArray.fromObject(scenceList);
		for (int i = 0; i < scenceArray.size(); i++) {
			scenceArray.getJSONObject(i).put("id",
					scenceArray.getJSONObject(i).getString("scNo"));
			scenceArray.getJSONObject(i).put("name",
					scenceArray.getJSONObject(i).getString("scName"));
		}
		JSONObject json = new JSONObject();
		json.put("scence", scenceArray);
		ajaxData = json.toString();
		
		model.addAttribute("formdl_archive_config02", formdl_archive_config02);
		model.addAttribute("query", "");
		model.addAttribute("ajaxData", ajaxData);
		return "/component/archives/ArchiveConfig_Detail";
	}
	
	@RequestMapping(value = "/getScenceForMutiSel")
	public String getScenceForMutiSel(Model model, String ajaxData) throws Exception{
		List<Scence> scenceList = new ArrayList<Scence>();
		ActionContext.initialize(request,response);
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		scenceList = this.scenceFeign.findByType(scence);
		String scenceListJSONArray = JSONArray.fromObject(scenceList).toString();
		model.addAttribute("query", "");
		model.addAttribute("scenceListJSONArray", scenceListJSONArray);
		return "/component/archives/ScencePage_MutiSel";
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
		FormData formdl_archive_config02 = formService.getFormData("dl_archive_config02");
		ArchiveConfig archiveConfig = new ArchiveConfig();
		List<ArchiveConfig> archiveConfigList = archiveConfigFeign.getAll(archiveConfig);
		model.addAttribute("formdl_archive_config02", formdl_archive_config02);
		model.addAttribute("archiveConfigList", archiveConfigList);
		model.addAttribute("query", "");
		return "/component/archives/ArchiveConfig_List";
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
		FormData 	formdl_archive_config02 = formService.getFormData("dl_archive_config02");
			getFormValue(formdl_archive_config02, getMapByJson(ajaxData));
			if(this.validateFormData(formdl_archive_config02)){
		ArchiveConfig archiveConfig = new ArchiveConfig();
				setObjValue(formdl_archive_config02, archiveConfig);
				archiveConfigFeign.insert(archiveConfig);
				getTableData(tableId,archiveConfig);//获取列表
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
	@RequestMapping(value = "/saveAjax")
	@ResponseBody
	public Map<String, Object> saveAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formdl_archive_config02 = formService.getFormData("dl_archive_config02");
			getFormValue(formdl_archive_config02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_archive_config02)) {
		ArchiveConfig archiveConfig = new ArchiveConfig();
				setObjValue(formdl_archive_config02, archiveConfig);
				/*String pactSignFilesOfSceneNos = archiveConfig.getPactSignFilesOfSceneNos();
				if (pactSignFilesOfSceneNos != null && pactSignFilesOfSceneNos.length() > 0) {
					if ("|".equals(pactSignFilesOfSceneNos.substring(0, 1))) {
						pactSignFilesOfSceneNos = pactSignFilesOfSceneNos.substring(1);
						pactSignFilesOfSceneNos = pactSignFilesOfSceneNos.replace("|", "@");
					}
					archiveConfig.setPactSignFilesOfSceneNos(pactSignFilesOfSceneNos);
				}
				String pactOverFilesOfSceneNos = archiveConfig.getPactOverFilesOfSceneNos();
				if (pactOverFilesOfSceneNos != null && pactOverFilesOfSceneNos.length() > 0) {
					if ("|".equals(pactOverFilesOfSceneNos.substring(0, 1))) {
						pactOverFilesOfSceneNos = pactOverFilesOfSceneNos.substring(1);
						pactOverFilesOfSceneNos = pactOverFilesOfSceneNos.replace("|", "@");
					}
					archiveConfig.setPactOverFilesOfSceneNos(pactOverFilesOfSceneNos);
				}*/
				archiveConfigFeign.update(archiveConfig);
				archiveConfig = archiveConfigFeign.getById(archiveConfig);
				archiveConfig.setUpdateDate(DateUtil.getShowDateTime(archiveConfig.getUpdateDate()));
				JSONObject archiveConfigJson = JSONObject.fromObject(archiveConfig);
				dataMap.put("archiveConfigJson", archiveConfigJson);
				dataMap.put("success", true);
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("success", false);
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formdl_archive_config02 = formService.getFormData("dl_archive_config02");
		ArchiveConfig archiveConfig = new ArchiveConfig();
		archiveConfig.setId(id);
		archiveConfig = archiveConfigFeign.getById(archiveConfig);
		getObjValue(formdl_archive_config02, archiveConfig,formData);
		if(archiveConfig!=null){
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
	public Map<String, Object> deleteAjax(String id,String ajaxData,String tableId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		ArchiveConfig archiveConfig = new ArchiveConfig();
		archiveConfig.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			archiveConfig = (ArchiveConfig)JSONObject.toBean(jb, ArchiveConfig.class);
			archiveConfigFeign.delete(archiveConfig);
			getTableData(tableId,archiveConfig);//获取列表
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
