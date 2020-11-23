package app.component.doc.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.feign.DocFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/docTypeConfig")
public class DocTypeConfigController extends BaseFormBean{
	@Autowired
	private DocFeign docFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	
	private static final long serialVersionUID = 9196454891709523438L;
	// 全局变量
	private DocTypeConfig docTypeConfig;
	private List<DocTypeConfig> docTypeConfigList;
	private String docSplitNo;
	private String docSplitName;
	private String docType;
	private String tableType;
	private String tableId;
	private int pageNo;
	private String query;
	// 异步参数
	private String ajaxData;
	private Map<String, Object> dataMap;
	// 表单变量
	private FormData formdoc0004;
	private FormData formdoc0005;
	private FormService formService = new FormService();

	public DocTypeConfigController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "component/doc/DocTypeConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,Ipage ipage,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		try {
			docTypeConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docTypeConfig.setCriteriaList(docTypeConfig, ajaxData);// 我的筛选
			// this.getRoleConditions(docTypeConfig,"1000000001");//记录级权限控制方法
//			Ipage ipage = this.getIpage();
//			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Gson gson = new Gson();
			ipage.putParams("docTypeConfig", gson.toJson(docTypeConfig));
			ipage = this.docFeign.findByPageDocTypeConfig(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType,
					(List) ipage.getResult(), ipage, true);
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
	@RequestMapping("/getPageForSel")
	public String getPageForSel(Model model,String docType,String docSplitNo) throws Exception {
		ActionContext.initialize(request,response);
		String[] docTypes = null;
		String[] docSplitNos = null;
		if (StringUtil.isNotEmpty(docType)) {
			docTypes = docType.split("@");
		}
		if (StringUtil.isNotEmpty(docSplitNo)) {
			docSplitNos=docSplitNo.split("@");
		}
		List<ParmDic> parmDiclist = (List<ParmDic>)new CodeUtils().getCacheByKeyName("DOC_TYPE");
		JSONArray dicArray2 = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < dicArray2.size(); i++) {
			dicArray2.getJSONObject(i).put("id",dicArray2.getJSONObject(i).getString("optCode"));
			dicArray2.getJSONObject(i).put("name",dicArray2.getJSONObject(i).getString("optName"));
			dicArray2.getJSONObject(i).put("pId", "0");
			if (docTypes != null) {
				for (int j = 0; j < docTypes.length; j++) {
					if (dicArray2.getJSONObject(i).getString("optCode").equals(docTypes[j])) {
						dicArray2.getJSONObject(i).put("open", true);
						break;
					}
				}
			}
		}

		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setUseFlag("1");
		docTypeConfigList = this.docFeign.getListDocTypeConfig(docTypeConfig);
		for(DocTypeConfig typeConfig:docTypeConfigList){
			typeConfig.setDocDesc("");
		}
		JSONArray dicArray1 = JSONArray.fromObject(docTypeConfigList);

		for (int i = 0; i < dicArray1.size(); i++) {
			dicArray1.getJSONObject(i).put("id",dicArray1.getJSONObject(i).getString("docSplitNo"));
			dicArray1.getJSONObject(i).put("name",dicArray1.getJSONObject(i).getString("docSplitName"));
			dicArray1.getJSONObject(i).put("pId",dicArray1.getJSONObject(i).getString("docType"));
			if (docSplitNos != null) {
				for (int j = 0; j < docSplitNos.length; j++) {
					if (dicArray1.getJSONObject(i).getString("docSplitNo").equals(docSplitNos[j])) {
						dicArray1.getJSONObject(i).put("checked", true);
						break;
					}
				}
			}
		}
		dicArray1.addAll(dicArray2);
		ajaxData = dicArray1.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("docType", docType);
		model.addAttribute("docTypeConfigList", docTypeConfigList);
		model.addAttribute("docTypeConfig", docTypeConfig);
		return "component/doc/DocType_ForSel";
	}
	
	@RequestMapping("/getDocTypeForSelAjax")
	@ResponseBody
	public Map<String,Object> getDocTypeForSelAjax(String docType,String docSplitNo) throws Exception {
		ActionContext.initialize(request,response);
		String[] docTypes = null;
		String[] docSplitNos = null;
		if (StringUtil.isNotEmpty(docType)) {
			docTypes = docType.split("@");
		}
		if (StringUtil.isNotEmpty(docSplitNo)) {
			docSplitNos=docSplitNo.split("@");
		}
//		ParmDic parmDic = new ParmDic();
//		parmDic.setKeyName("DOC_TYPE");
//		List<ParmDic> parmDiclist = this.docFeign.findAllParmDicByKeyNameNmdInterface(parmDic);
		List<ParmDic> parmDiclist = (List<ParmDic>)new CodeUtils().getCacheByKeyName("DOC_TYPE");
		JSONArray dicArray2 = JSONArray.fromObject(parmDiclist);
		for (int i = 0; i < dicArray2.size(); i++) {
			dicArray2.getJSONObject(i).put("id",
					dicArray2.getJSONObject(i).getString("optCode"));
			dicArray2.getJSONObject(i).put("name",
					dicArray2.getJSONObject(i).getString("optName"));
			dicArray2.getJSONObject(i).put("pId", "0");
			if (docTypes != null) {
				for (int j = 0; j < docTypes.length; j++) {
					if (dicArray2.getJSONObject(i).getString("optCode")
							.equals(docTypes[j])) {
						dicArray2.getJSONObject(i).put("open", true);
						break;
					}
				}
			}
		}
		
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setUseFlag("1");
		docTypeConfigList = this.docFeign.getListDocTypeConfig(docTypeConfig);
		JSONArray dicArray1 = JSONArray.fromObject(docTypeConfigList);
		
		for (int i = 0; i < dicArray1.size(); i++) {
			dicArray1.getJSONObject(i).put("id",
					dicArray1.getJSONObject(i).getString("docSplitNo"));
			dicArray1.getJSONObject(i).put("name",
					dicArray1.getJSONObject(i).getString("docSplitName"));
			dicArray1.getJSONObject(i).put("pId",
					dicArray1.getJSONObject(i).getString("docType"));
//			if (docSplitNos != null) {
//				for (int j = 0; j < docSplitNos.length; j++) {
//					if (dicArray1.getJSONObject(i).getString("docSplitNo")
//							.equals(docSplitNos[j])) {
//						dicArray1.getJSONObject(i).put("checked", true);
//						break;
//					}
//				}
//			}
		}
		dicArray1.addAll(dicArray2);
//		ajaxData = dicArray1.toString();
		dataMap= new HashMap<String,Object>();
		dataMap.put("items", dicArray1);
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDocTypeConfigAjax")
	@ResponseBody
	public Map<String,Object> insertDocTypeConfigAjax(String docSplitName,String docType,String docSplitNo) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitName(docSplitName);
		docTypeConfig.setDocType(docType);
		try {
			this.docFeign.insertDocTypeConfig(docTypeConfig);
			dataMap.put("docSplitName", docSplitName);
			dataMap.put("docType", docType);
			dataMap.put("docSplitNo", docSplitNo);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping("/updateDocTypeConfigAjax")
	@ResponseBody
	public Map<String,Object> updateDocTypeConfigAjax(String docSplitName,String docSplitNo,String docType) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitName(docSplitName);
		docTypeConfig.setDocSplitNo(docSplitNo);
		docTypeConfig.setDocType(docType);
		try {
			this.docFeign.updateDocTypeConfig(docTypeConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping("/deleteDocTypeConfigAjax")
	@ResponseBody
	public Map<String,Object> deleteDocTypeConfigAjax(String docSplitName,String docSplitNo,String docType) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitName(docSplitName);
		docTypeConfig.setDocSplitNo(docSplitNo);
		docTypeConfig.setDocType(docType);
		try {
			this.docFeign.deleteDocTypeConfig(docTypeConfig);
			DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
			docBizSceConfig.setDocSplitNo(docTypeConfig.getDocSplitNo());
			this.docFeign.deleteBysplitNoDocBizSceConfig(docBizSceConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		formdoc0005 = formService.getFormData("doc0005");
		getFormValue(formdoc0005, getMapByJson(ajaxData));
		DocTypeConfig docTypeConfigJsp = new DocTypeConfig();
		setObjValue(formdoc0005, docTypeConfigJsp);
		docTypeConfig = this.docFeign.getByIdDocTypeConfig(docTypeConfigJsp);
		if (docTypeConfig != null) {
			try {
				docTypeConfig = (DocTypeConfig) EntityUtil
						.reflectionSetVal(docTypeConfig, docTypeConfigJsp,
								getMapByJson(ajaxData));
				this.docFeign.updateDocTypeConfig(docTypeConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
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
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		try {
			formdoc0005 = formService.getFormData("doc0005");
			getFormValue(formdoc0005, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0005)) {
				docTypeConfig = new DocTypeConfig();
				setObjValue(formdoc0005, docTypeConfig);
				this.docFeign.updateDocTypeConfig(docTypeConfig);
				List<DocTypeConfig> list = new ArrayList<DocTypeConfig>();
				list.add(docTypeConfig);
				getTableData(list);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	private void getTableData(List<DocTypeConfig> list) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "thirdTableTag", list, null,
				true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String docSplitNo,String query) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> formData = new HashMap<String, Object>();
		dataMap = new HashMap<String, Object>();
		formdoc0005 = formService.getFormData("doc0005");
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitNo(docSplitNo);
		docTypeConfig = this.docFeign.getByIdDocTypeConfig(docTypeConfig);
		getObjValue(formdoc0005, docTypeConfig, formData);
		if (docTypeConfig != null) {
			JsonFormUtil jfu = new JsonFormUtil();
			String formHtml = jfu.getJsonStr(formdoc0005, "bigFormTag", query == null?"":query);
			dataMap.put("formHtml", formHtml);
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String docSplitNo) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitNo(docSplitNo);
		try {
			this.docFeign.deleteAndBizSce(docSplitNo);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request,response);
		formdoc0005 = formService.getFormData("doc0005");
		
		model.addAttribute("formdoc0005", formdoc0005);
		model.addAttribute("query", query);
		return "component/doc/DocTypeConfig_Insert";
	}

	/**
	 * 新增页面的ajax
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputAjax")
	@ResponseBody
	public Map<String,Object> inputAjax(String query) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		docTypeConfig = new DocTypeConfig();
		formdoc0005 = formService.getFormData("doc0005");
		getObjValue(formdoc0005, docTypeConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formdoc0005, "bigFormTag", query==null?"":query);
		dataMap.put("formHtml", formHtml);
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		try {
			formdoc0005 = formService.getFormData("doc0005");
			getFormValue(formdoc0005, getMapByJson(ajaxData));
			if (this.validateFormData(formdoc0005)) {
				docTypeConfig = new DocTypeConfig();
				setObjValue(formdoc0005, docTypeConfig);
				this.docFeign.insertDocTypeConfig(docTypeConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("/insert")
//	public String insert(Model model) throws Exception {
//		ActionContext.initialize(request,response);
//		formdoc0005 = formService.getFormData("doc0005");
//		getFormValue(formdoc0005);
//		docTypeConfig = new DocTypeConfig();
//		setObjValue(formdoc0005, docTypeConfig);
//		this.docFeign.insertDocTypeConfig(docTypeConfig);
//		getObjValue(formdoc0005, docTypeConfig);
//		this.addActionMessage(model,"保存成功");
//		docTypeConfigList = (List<DocTypeConfig>) docTypeConfigBo.findByPage(
//				this.getIpage(), docTypeConfig).getResult();
//		return "insert";
//	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String docSplitNo,Model model) throws Exception {
		ActionContext.initialize(request,response);
		formdoc0005 = formService.getFormData("doc0005");
		getFormValue(formdoc0005);
		docTypeConfig = new DocTypeConfig();
		docTypeConfig.setDocSplitNo(docSplitNo);
		docTypeConfig = this.docFeign.getByIdDocTypeConfig(docTypeConfig);
		getObjValue(formdoc0005, docTypeConfig);
		model.addAttribute("docTypeConfig", docTypeConfig);
		model.addAttribute("query", "");
		model.addAttribute("formdoc0005", formdoc0005);
		return "component/doc/DocTypeConfig_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
//	public String delete() throws Exception {
//		ActionContext.initialize(ServletActionContext.getRequest(),
//				ServletActionContext.getResponse());
//		docTypeConfig = new DocTypeConfig();
//		docTypeConfig.setDocSplitNo(docSplitNo);
//		docTypeConfigBo.delete(docTypeConfig);
//		return getListPage();
//	}
	
	
	
	/**
	 * 
	 * 方法描述： 配置要件预览表单
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-5-15 下午4:32:18
	 */
	@RequestMapping("/checkAndCreateFormAjax")
	@ResponseBody
	public Map<String,Object> checkAndCreateFormAjax(String docSplitNo,String docType) throws Exception {
		ActionContext.initialize(request,response);
		dataMap = new HashMap<String, Object>();
		try {
			docTypeConfig = new DocTypeConfig();
			docTypeConfig.setDocSplitNo(docSplitNo);
			docTypeConfig.setDocType(docType);
			dataMap = this.docFeign.checkAndCreateForm(docTypeConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("配置"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("配置"));
			throw e;
		}
		return dataMap;
	}
}
