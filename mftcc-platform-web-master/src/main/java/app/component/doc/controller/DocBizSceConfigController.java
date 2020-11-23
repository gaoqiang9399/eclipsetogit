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
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocDimm;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.entity.SceDocTypeRel;
import app.component.doc.feign.DocFeign;
import app.component.param.entity.Scence;
import app.component.param.feign.ParamFeign;
import app.component.prdct.entity.MfSysKind;
import app.component.prdct.feign.PrdtFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import app.tech.upload.FeignSpringFormEncoder;;

@Controller
@RequestMapping("/docBizSceConfig")
public class DocBizSceConfigController extends BaseFormBean {

	@Autowired
	private DocFeign docFeign;
	@Autowired
	private ParamFeign paramFeign;

	@Autowired
	private PrdtFeign prdtFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private FormData formdoc0001;
	private FormData formdoc0002;
	private FormService formService = new FormService();

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();

		try {
			JSONObject json = JSONObject.fromObject(ajaxData);
			docBizSceConfig.setDime1(json.getString("dime1"));
			docBizSceConfig.setDocSplitNo(json.getString("docSplitNo"));
			docBizSceConfig.setDocType(json.getString("docType"));
			docBizSceConfig.setScNo(json.getString("scNo"));
			docBizSceConfig.setIfMustInput(json.getString("ifMustInput"));
			this.docFeign.updateDocBizSceConfig(docBizSceConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/doc/DocBizSceConfig_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Ipage ipage, String tableId, String tableType)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		try {
			docBizSceConfig.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docBizSceConfig.setCriteriaList(docBizSceConfig, ajaxData);// 我的筛选
			// this.getRoleConditions(docBizSceConfig,"1000000001");//记录级权限控制方法
			Gson gson = new Gson();
			ipage.putParams("docBizSceConfig", docBizSceConfig);
			// 自定义查询Bo方法
			ipage = this.docFeign.findByPageDocBizSceConfig(ipage);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertDocsAjax")
	@ResponseBody
	public Map<String, Object> insertDocsAjax(String ajaxData,String dime1,String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxData);
			List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) JSONArray.toList(jsonArray,
					new DocBizSceConfig(), new JsonConfig());
			for(int i =0;i<docBizSceConfigList.size();i++){
				DocBizSceConfig docBizSceConfig = docBizSceConfigList.get(i);
				new FeignSpringFormEncoder().addParamsToBaseDomain(docBizSceConfig);
			}
			dataMap = this.docFeign.insertDocs(docBizSceConfigList,dime1,scNo);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/deleteSceDocTypeRelAjax")
	@ResponseBody
	public Map<String, Object> deleteSceDocTypeRelAjax(String scNo, String docType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SceDocTypeRel sceDocTypeRel = new SceDocTypeRel();
		sceDocTypeRel.setScNo(scNo);
		sceDocTypeRel.setDocType(docType);
		try {
			this.docFeign.deleteSceDocTypeRel(sceDocTypeRel);
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

	@RequestMapping("/deleteDocBizSceConfigAjax")
	@ResponseBody
	public Map<String, Object> deleteDocBizSceConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject jb = JSONObject.fromObject(ajaxData);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setScNo(jb.getString("scNo"));
		docBizSceConfig.setDocType(jb.getString("docType"));
		docBizSceConfig.setDime1(jb.getString("dimmNo"));
		docBizSceConfig.setDocSplitNo(jb.getString("docSplitNo"));
		docBizSceConfig.setIfMustInput(jb.getString("ifMustInput"));
		docBizSceConfig.setIfMustRead(jb.getString("ifMustRead"));
		docBizSceConfig.setDocSizeLimit(jb.getDouble("docSizeLimit"));
		try {
			// docBizSceConfigBo.delete(docBizSceConfig);
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

	@RequestMapping("/insertSceDocTypeRelAjax")
	@ResponseBody
	public Map<String, Object> insertSceDocTypeRelAjax(String scNo, String docType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SceDocTypeRel sceDocTypeRel = new SceDocTypeRel();
		sceDocTypeRel.setScNo(scNo);
		sceDocTypeRel.setDocType(docType);
		try {
			this.docFeign.insertSceDocTypeRel(sceDocTypeRel);
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

	@RequestMapping("/insertDocBizSceConfigAjax")
	@ResponseBody
	public Map<String, Object> insertDocBizSceConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONObject jb = JSONObject.fromObject(ajaxData);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setScNo(jb.getString("scNo"));
		docBizSceConfig.setDocType(jb.getString("docType"));
		docBizSceConfig.setDime1(jb.getString("dimmNo"));
		docBizSceConfig.setDocSplitNo(jb.getString("docSplitNo"));
		docBizSceConfig.setIfMustInput(jb.getString("ifMustInput"));
		docBizSceConfig.setIfMustRead(jb.getString("ifMustRead"));
		docBizSceConfig.setDocSizeLimit(jb.getDouble("docSizeLimit"));
		try {
			this.docFeign.insertDocBizSceConfig(docBizSceConfig);
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

	@RequestMapping("/updateDocBizSceConfigAjax")
	@ResponseBody
	public Map<String, Object> updateDocBizSceConfigAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		JSONObject jb = JSONObject.fromObject(ajaxData);
		docBizSceConfig.setDime1(jb.getString("dime1"));
		docBizSceConfig.setDocSizeLimit(0.0);
		docBizSceConfig.setDocSplitName(jb.getString("docSplitName"));
		docBizSceConfig.setDocSplitNo(jb.getString("docSplitNo"));
		docBizSceConfig.setDocType(jb.getString("docType"));
		docBizSceConfig.setIfMustInput(jb.getString("ifMustInput"));
		docBizSceConfig.setIfMustRead(jb.getString("ifMustRead"));
		docBizSceConfig.setScNo(jb.getString("scNo"));
		docBizSceConfig.setFormId(jb.getString("formId"));
		try {
			this.docFeign.updateDocBizSceConfig(docBizSceConfig);
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

	@RequestMapping("/updateDocBizSceConfigSeqAjax")
	@ResponseBody
	public Map<String, Object> updateDocBizSceConfigSeqAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		JSONObject json = JSONObject.fromObject(ajaxData);
		docBizSceConfig.setDime1(json.getString("dime1"));
		docBizSceConfig.setDocSplitNo(json.getString("seqArr"));
		docBizSceConfig.setDocType(json.getString("docType"));
		docBizSceConfig.setScNo(json.getString("scNo"));
		try {
			// docBizSceConfigBo.updateSeq(docBizSceConfig);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formdoc0002 = formService.getFormData("doc0002");
		getFormValue(formdoc0002, getMapByJson(ajaxData));
		DocBizSceConfig docBizSceConfigJsp = new DocBizSceConfig();
		setObjValue(formdoc0002, docBizSceConfigJsp);
		DocBizSceConfig docBizSceConfig = this.docFeign.getByIdDocBizSceConfig(docBizSceConfigJsp);
		if (docBizSceConfig != null) {
			try {
				docBizSceConfig = (DocBizSceConfig) EntityUtil.reflectionSetVal(docBizSceConfig, docBizSceConfigJsp,
						getMapByJson(ajaxData));
				this.docFeign.updateDocBizSceConfig(docBizSceConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String docType, String scNo, String dime1, String docSplitNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formdoc0002 = formService.getFormData("doc0002");
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setDocType(docType);
		docBizSceConfig.setScNo(scNo);
		docBizSceConfig.setDime1(dime1);
		docBizSceConfig.setDocSplitNo(docSplitNo);
		docBizSceConfig = this.docFeign.getByIdDocBizSceConfig(docBizSceConfig);
		getObjValue(formdoc0002, docBizSceConfig, formData);
		if (docBizSceConfig != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 删除摸一个场景下的单个要件配置
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-28 上午10:15:01
	 */
	@RequestMapping("/deleteDocAjax")
	@ResponseBody
	public Map<String, Object> deleteDocAjax(String dime1, String docSplitNo, String docType, String scNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		try {
			docBizSceConfig.setDime1(dime1);
			docBizSceConfig.setDocSplitNo(docSplitNo);
			docBizSceConfig.setDocType(docType);
			docBizSceConfig.setScNo(scNo);
			this.docFeign.deleteDocBizSceConfig(docBizSceConfig);
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
	 * 
	 * 方法描述： 删除产品的某一场景下的所有要件配置
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-28 上午10:15:38
	 */
	@RequestMapping("/deleteScenceAjax")
	@ResponseBody
	public Map<String, Object> deleteScenceAjax(String dime1, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		try {
			docBizSceConfig.setDime1(dime1);
			docBizSceConfig.setScNo(scNo);
			this.docFeign.deleteDocBizSceConfig(docBizSceConfig);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String query) throws Exception {
		ActionContext.initialize(request, response);
		formdoc0002 = formService.getFormData("doc0002");
		model.addAttribute("formdoc0002", formdoc0002);
		model.addAttribute("query", query == null ? "" : query);
		return "component/doc/DocBizSceConfig_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model, Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		formdoc0002 = formService.getFormData("doc0002");
		getFormValue(formdoc0002);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		setObjValue(formdoc0002, docBizSceConfig);
		this.docFeign.insertDocBizSceConfig(docBizSceConfig);
		getObjValue(formdoc0002, docBizSceConfig);
		this.addActionMessage(model, "保存成功");
		Gson gson = new Gson();
		ipage.putParams("docBizSceConfig", gson.toJson(docBizSceConfig));
		List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) this.docFeign
				.findByPageDocBizSceConfig(ipage).getResult();
		model.addAttribute("docBizSceConfigList", docBizSceConfigList);
		model.addAttribute("formdoc0002", formdoc0002);
		model.addAttribute("query", "");
		return "component/doc/DocBizSceConfig_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String docType, String scNo, String dime1, String docSplitNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		formdoc0002 = formService.getFormData("doc0002");
		getFormValue(formdoc0002);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setDocType(docType);
		docBizSceConfig.setScNo(scNo);
		docBizSceConfig.setDime1(dime1);
		docBizSceConfig.setDocSplitNo(docSplitNo);
		docBizSceConfig = this.docFeign.getByIdDocBizSceConfig(docBizSceConfig);
		getObjValue(formdoc0002, docBizSceConfig);
		model.addAttribute("formdoc0002", formdoc0002);
		model.addAttribute("query", "");
		model.addAttribute("docBizSceConfig", docBizSceConfig);
		return "component/doc/DocBizSceConfig_Detail";
	}

	@RequestMapping("/config")
	public String config() throws Exception {
		ActionContext.initialize(request, response);

		return "component/doc/DocBizSceConfigPage";
	}

	@RequestMapping("/findScenByType")
	public String findScenByType(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = this.paramFeign.findByType(scence);
		model.addAttribute("scenceList", scenceList);
		return "component/param/ScencesForDoc";

	}

	// 初始化文档业务场景
	@RequestMapping("/findScenByTypeAjax")
	@ResponseBody
	public Map<String, Object> findScenByTypeAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = this.paramFeign.findByType(scence);
		JSONArray dicArray = JSONArray.fromObject(scenceList);
		for (int i = 0; i < dicArray.size(); i++) {
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("scNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("scName"));
			dicArray.getJSONObject(i).put("pId", "0");
			dicArray.getJSONObject(i).put("open", true);
		}
		dataMap.put("zNodes", dicArray);
		return dataMap;
	}

	@RequestMapping("/findSceDocTypeRels")
	public String findSceDocTypeRels(SceDocTypeRel sceDocTypeRel, Model model) throws Exception {
		ActionContext.initialize(request, response);
		List<SceDocTypeRel> sceDocTypeRels = this.docFeign.getSceDocTypeRels(sceDocTypeRel);
		model.addAttribute("sceDocTypeRels", sceDocTypeRels);
		return "component/doc/getSceDocTypeRels";
	}

	// 加载文档类别
	@RequestMapping("/findSceDocTypeRelsAjax")
	@ResponseBody
	public Map<String, Object> findSceDocTypeRelsAjax(String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SceDocTypeRel sceDocTypeRel = new SceDocTypeRel();
		sceDocTypeRel.setScNo(scNo);
		List<SceDocTypeRel> sceDocTypeRels = this.docFeign.getSceDocTypeRels(sceDocTypeRel);
		JSONArray zNodes = new JSONArray();
		JSONArray dicArray = JSONArray.fromObject(sceDocTypeRels);
		for (int i = 0; i < dicArray.size(); i++) {
			String sn = dicArray.getJSONObject(i).getString("scNo");
			if (sn != null && !"".equals(sn)) {
				dicArray.getJSONObject(i).put("checked", true);
			}
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("docType"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("docTypeName"));
			dicArray.getJSONObject(i).put("scNo", scNo);
			dicArray.getJSONObject(i).put("pId", scNo);
			// dicArray.getJSONObject(i).put("open",true);
			List<DocTypeConfig> dtcList = new ArrayList<DocTypeConfig>();
			dtcList = this.docFeign.getListByDocTypeDocTypeConfig(dicArray.getJSONObject(i).getString("docType"));
			JSONArray dtcArray = JSONArray.fromObject(dtcList);
			for (int j = 0; j < dtcArray.size(); j++) {
				dtcArray.getJSONObject(j).put("pId", dicArray.getJSONObject(i).getString("docType"));
				dtcArray.getJSONObject(j).put("id", dtcArray.getJSONObject(j).getString("docSplitNo"));
				dtcArray.getJSONObject(j).put("name", dtcArray.getJSONObject(j).getString("docSplitName"));
			}
			zNodes.addAll(dtcArray);
		}
		zNodes.addAll(dicArray);
		dataMap.put("zNodes", zNodes);
		return dataMap;
	}

	@RequestMapping("/findDocDimms")
	public String findDocDimms() throws Exception {
		ActionContext.initialize(request, response);
		DocDimm docDimm = new DocDimm();
		List<DocDimm> docDimms = this.docFeign.getDocDimms(docDimm);
		return "component/doc/getDocDimms";
	}

	// 维度划分
	@RequestMapping("/findDocDimmsAjax")
	@ResponseBody
	public Map<String, Object> findDocDimmsAjax(String scNo, String docType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocDimm docDimm = new DocDimm();
		// this.docDimms = this.docBizSceConfigBo.getDocDimms(docDimm);
		List<DocDimm> docDimms = new ArrayList<DocDimm>();
		// 增加一个默认选项
		DocDimm docDimmInte = new DocDimm();
		docDimmInte.setDimmName("默认");
		docDimmInte.setDimmNo("99");
		docDimms.add(docDimmInte);
		MfSysKind sysKind = new MfSysKind();
		sysKind.setUseFlag("1");
		List<MfSysKind> sysKindList = this.prdtFeign.getSysKindList(sysKind);
		for (MfSysKind sysKindTmp : sysKindList) {
			docDimmInte = new DocDimm();
			docDimmInte.setDimmName(sysKindTmp.getKindName());
			docDimmInte.setDimmNo(sysKindTmp.getKindNo());
			docDimms.add(docDimmInte);
		}
		docDimms.add(docDimm);
		JSONArray dicArray = JSONArray.fromObject(docDimms);
		for (int i = 0; i < dicArray.size(); i++) {
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("dimmNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("dimmName"));
			dicArray.getJSONObject(i).put("pId", null);
			dicArray.getJSONObject(i).put("open", true);
			dicArray.getJSONObject(i).put("scNo", scNo);
			dicArray.getJSONObject(i).put("docType", docType);
		}
		dataMap.put("zNodes", dicArray);
		return dataMap;
	}

	@RequestMapping("/findSplitDocs")
	public String findSplitDocs(DocBizSceConfig docBizSceConfig) throws Exception {
		ActionContext.initialize(request, response);
		List<DocBizSceConfig> docBizSceConfigs = this.docFeign.getSplitDocs(docBizSceConfig);
		return "component/doc/getSplitDocs";
	}

	// 文档细分类型
	@RequestMapping("/findSplitDocsAjax")
	@ResponseBody
	public Map<String, Object> findSplitDocsAjax(String scNo, String docType, String dimmNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setScNo(scNo);
		docBizSceConfig.setDocType(docType);
		docBizSceConfig.setDime1(dimmNo);
		List<DocBizSceConfig> docBizSceConfigs = this.docFeign.getSplitDocs(docBizSceConfig);
		for (DocBizSceConfig doc : docBizSceConfigs) {
			doc.setDime1(dimmNo);
		}
		JSONArray dicArray = JSONArray.fromObject(docBizSceConfigs);
		for (int i = 0; i < dicArray.size(); i++) {
			String sn = dicArray.getJSONObject(i).getString("scNo");
			if (sn != null && !"".equals(sn)) {
				dicArray.getJSONObject(i).put("checked", true);
			}
			dicArray.getJSONObject(i).put("id", dicArray.getJSONObject(i).getString("docSplitNo"));
			dicArray.getJSONObject(i).put("name", dicArray.getJSONObject(i).getString("docSplitName"));
			dicArray.getJSONObject(i).put("pId", null);
			dicArray.getJSONObject(i).put("open", true);
			dicArray.getJSONObject(i).put("dimmNo", dimmNo);
			dicArray.getJSONObject(i).put("scNo", scNo);
			dicArray.getJSONObject(i).put("docType", docType);
		}
		dataMap.put("zNodes", dicArray);
		return dataMap;
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String docType, String scNo, String dime1, String docSplitNo) throws Exception {
		ActionContext.initialize(request, response);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setDocType(docType);
		docBizSceConfig.setScNo(scNo);
		docBizSceConfig.setDime1(dime1);
		docBizSceConfig.setDocSplitNo(docSplitNo);
		this.docFeign.deleteDocBizSceConfig(docBizSceConfig);
		return "redirect:findListPage";
	}

	/**
	 * 
	 * 方法描述： 根据dime1获取相应的配置信息
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-6-26 上午11:31:51
	 */
	@RequestMapping("/getDocBizSecConfigListAjax")
	@ResponseBody
	public Map<String, Object> getDocBizSecConfigListAjax(String dime1) throws Exception {
		ActionContext.initialize(request, response);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			docBizSceConfig.setDime1(dime1);
			List<Map<String, Object>> resList = this.docFeign.getDocBizSecConfigInfo(docBizSceConfig);
			dataMap.put("resList", resList);
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

}
