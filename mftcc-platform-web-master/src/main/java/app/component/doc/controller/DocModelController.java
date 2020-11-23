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

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocModel;
import app.component.doc.feign.DocBizSceConfigFeign;
import app.component.doc.feign.DocModelFeign;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Title: DocModelController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Dec 21 15:43:44 CST 2016
 **/
@Controller
@RequestMapping("/docModel")
public class DocModelController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private DocModelFeign docModelFeign;
	@Autowired
	private ScenceFeign scenceFeign;
	@Autowired
	private DocBizSceConfigFeign docBizSceConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/doc/DocModel_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		DocModel docModel = new DocModel();
		try {
			docModel.setCustomQuery(ajaxData);// 自定义查询参数赋值
			docModel.setCriteriaList(docModel, ajaxData);// 我的筛选
			// getRoleConditions(docModel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = docModelFeign.findByPage(ipage, docModel);
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

	@RequestMapping("/getScenceForMutiSel")
	public String getScenceForMutiSel(Model model, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = scenceFeign.findByType(scence);
		String scenceListAjaxData = "";
		if (StringUtil.isNotEmpty(scNo)) {
			String[] scNos = scNo.split("@");
			List<Scence> scenceList1 = new ArrayList<Scence>();
			for (int i = 0; i < scenceList.size(); i++) {
				boolean flag = false;
				for (int j = 0; j < scNos.length; j++) {
					if (scNos[j].equals(scenceList.get(i).getScNo())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					scenceList1.add(scenceList.get(i));
				}
			}
			scenceListAjaxData = JSONArray.fromObject(scenceList1).toString();
		} else {
			scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
		}
		model.addAttribute("scenceListAjaxData", scenceListAjaxData);
		model.addAttribute("query", "");
		return "/component/doc/getScenceForMutiSel";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData, String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdocmodel0001 = formService.getFormData("docmodel0001");
			getFormValue(formdocmodel0001, getMapByJson(ajaxData));
			if (this.validateFormData(formdocmodel0001)) {
				DocModel docModel = new DocModel();
				setObjValue(formdocmodel0001, docModel);

				JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
				List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) JSONArray.toList(jsonArray,
						new DocBizSceConfig(), new JsonConfig());

				docModelFeign.insert(docModel, docBizSceConfigList);
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdocmodel0002 = formService.getFormData("docmodel0002");
		getFormValue(formdocmodel0002, getMapByJson(ajaxData));
		DocModel docModelJsp = new DocModel();
		setObjValue(formdocmodel0002, docModelJsp);
		DocModel docModel = docModelFeign.getById(docModelJsp);
		if (docModel != null) {
			try {
				docModel = (DocModel) EntityUtil.reflectionSetVal(docModel, docModelJsp, getMapByJson(ajaxData));
				docModelFeign.update(docModel);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData, String ajaxDataList) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdocmodel0001 = formService.getFormData("docmodel0001");
			getFormValue(formdocmodel0001, getMapByJson(ajaxData));
			if (this.validateFormData(formdocmodel0001)) {
				DocModel docModel = new DocModel();
				setObjValue(formdocmodel0001, docModel);

				JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
				List<DocBizSceConfig> docBizSceConfigList = (List<DocBizSceConfig>) JSONArray.toList(jsonArray,
						new DocBizSceConfig(), new JsonConfig());

				docModelFeign.update(docModel, docBizSceConfigList);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
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
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData, String docModelNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdocmodel0002 = formService.getFormData("docmodel0002");
		DocModel docModel = new DocModel();
		docModel.setDocModelNo(docModelNo);
		docModel = docModelFeign.getById(docModel);
		getObjValue(formdocmodel0002, docModel, formData);
		if (docModel != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String docModelNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			docModelFeign.deleteAndBizSce(docModelNo);
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
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = scenceFeign.findByType(scence);
		String scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
		FormService formService = new FormService();
		FormData formdocmodel0001 = formService.getFormData("docmodel0001");
		model.addAttribute("formdocmodel0001", formdocmodel0001);
		model.addAttribute("scenceListAjaxData", scenceListAjaxData);
		return "/component/doc/DocModel_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdocmodel0002 = formService.getFormData("docmodel0002");
		getFormValue(formdocmodel0002);
		DocModel docModel = new DocModel();
		setObjValue(formdocmodel0002, docModel);
		docModelFeign.insert(docModel);
		getObjValue(formdocmodel0002, docModel);
		this.addActionMessage(model, "保存成功");
		List<DocModel> docModelList = (List<DocModel>) docModelFeign.findByPage(this.getIpage(), docModel).getResult();
		model.addAttribute("formdocmodel0002", formdocmodel0002);
		model.addAttribute("docModelList", docModelList);
		model.addAttribute("query", "");
		return "/component/doc/DocModel_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String docModelNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdocmodel0001 = formService.getFormData("docmodel0001");
		getFormValue(formdocmodel0001);
		DocModel docModel = new DocModel();
		docModel.setDocModelNo(docModelNo);
		docModel = docModelFeign.getById(docModel);
		getObjValue(formdocmodel0001, docModel);

		Scence scence = new Scence();
		scence.setScenceType(BizPubParm.SCENCE_TYPE_DOC);
		scence.setUseFlag(BizPubParm.YES_NO_Y);
		List<Scence> scenceList = scenceFeign.findByType(scence);
		DocBizSceConfig docBizSceConfig = new DocBizSceConfig();
		docBizSceConfig.setDime1(docModelNo);
		List<DocBizSceConfig> docBizSceConfigList = docBizSceConfigFeign.getByDime(docBizSceConfig);
		String ajaxData = JSONArray.fromObject(docBizSceConfigList).toString();
		String scenceListAjaxData = JSONArray.fromObject(scenceList).toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("scenceListAjaxData", scenceListAjaxData);
		model.addAttribute("query", "");
		return "/component/doc/DocModel_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String docModelNo) throws Exception {
		ActionContext.initialize(request, response);
		DocModel docModel = new DocModel();
		docModel.setDocModelNo(docModelNo);
		docModelFeign.delete(docModel);
		return getListPage();
	}

}
