package app.component.model.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfSysTemplate;
import app.component.model.entity.MfSysTemplateEsignerType;
import app.component.model.feign.MfSysTemplateEsignerTypeFeign;
import app.component.nmd.entity.ParmDic;
import app.tech.upload.UploadUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.interfacesinterface.MobileServiceCusInterfaceFeign;
import app.component.interfacesinterface.MobileServicePledgeInterfaceFeign;
import app.component.model.entity.LoanTemplateTagBase;
import app.component.model.feign.LoanTemplateTagBaseFeign;
import app.component.model.feign.MfSysTemplateFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title: MfSysTemplateAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Nov 22 11:14:05 CST 2016
 **/
@Controller
@RequestMapping("/mfSysTemplate")
public class MfSysTemplateController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysTemplateBo
	@Autowired
	private MfSysTemplateFeign mfSysTemplateFeign;
	@Autowired
	private LoanTemplateTagBaseFeign loanTemplateTagBaseFeign;
	// 全局变量
	// 异步参数
	// 表单变量
	@Autowired
	private MobileServiceCusInterfaceFeign mobileServiceCusInterfaceFeign;
	@Autowired
	private MobileServicePledgeInterfaceFeign mobileServicePledgeInterfaceFeign;
	@Autowired
	private MfSysTemplateEsignerTypeFeign mfSysTemplateEsignerTypeFeign;

	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_List";
	}

	/**
	 *
	 * 方法描述：获取系统中的模板（不过滤已经选择配置在产品下的）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2018-3-5 下午6:41:04
	 */
	@RequestMapping(value = "/getSysTemplateListNoFilterAjax")
	@ResponseBody
	public Map<String, Object> getSysTemplateListNoFilterAjax() throws Exception {
		ActionContext.initialize(request,response);
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setUseFlag("1");
		List<MfSysTemplate> mfSysTemplateList = mfSysTemplateFeign.getAll(mfSysTemplate);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mfSysTemplateList", mfSysTemplateList);

		return dataMap;
	}


	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		try {
			mfSysTemplate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysTemplate.setCriteriaList(mfSysTemplate, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysTemplate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysTemplate", mfSysTemplate));
			ipage = mfSysTemplateFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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

	/**
	 * AJAX新增
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formsystemplate0002 = formService.getFormData("systemplate0002");
			getFormValue(formsystemplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsystemplate0002)) {
				MfSysTemplate mfSysTemplate = new MfSysTemplate();
				setObjValue(formsystemplate0002, mfSysTemplate);
				mfSysTemplate = mfSysTemplateFeign.insert(mfSysTemplate);
				dataMap.put("mfSysTemplate", mfSysTemplate);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		getFormValue(formsystemplate0002, getMapByJson(ajaxData));
		MfSysTemplate mfSysTemplateJsp = new MfSysTemplate();
		setObjValue(formsystemplate0002, mfSysTemplateJsp);
		MfSysTemplate mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplateJsp);
		if (mfSysTemplate != null) {
			try {
				mfSysTemplate = (MfSysTemplate) EntityUtil.reflectionSetVal(mfSysTemplate, mfSysTemplateJsp,
						getMapByJson(ajaxData));
				mfSysTemplateFeign.update(mfSysTemplate);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formsystemplate0002 = formService.getFormData("systemplateedit");
			getFormValue(formsystemplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formsystemplate0002)) {
				MfSysTemplate mfSysTemplate = new MfSysTemplate();
				setObjValue(formsystemplate0002, mfSysTemplate);
				mfSysTemplateFeign.update(mfSysTemplate);
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
	 *
	 * 方法描述： 修改模板文件名
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-11-30 下午2:53:06
	 */
	@RequestMapping(value = "/updateTemplateNameAjax")
	@ResponseBody
	public Map<String, Object> updateTemplateNameAjax(String orgNo,String orgName ,String userId,String opName,String templateNo,String templateNameEn,String modelId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		try {
			mfSysTemplate.setTemplateNo(templateNo);
			mfSysTemplate.setTemplateNameEn(templateNameEn);
			mfSysTemplateFeign.update(mfSysTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述：修改模板启用状态
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2016-9-7 下午3:57:12
	 */
	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formsystemplate0002 = formService.getFormData("systemplate0002");
			getFormValue(formsystemplate0002, jobj);
			MfSysTemplate mfSysTemplate = new MfSysTemplate();
			setObjValue(formsystemplate0002, mfSysTemplate);
			mfSysTemplateFeign.update(mfSysTemplate);
			mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
			ArrayList<MfSysTemplate> list = new ArrayList<>();
			list.add(mfSysTemplate);
			getTableDataList(list, tableId, dataMap);
			dataMap.put("flag", "success");
			dataMap.put("id", mfSysTemplate.getTemplateNo());
			dataMap.put("useFlag", mfSysTemplate.getUseFlag());
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	private void getTableData(String templateNo, String tableId, Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		Ipage ipage = this.getIpage();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", mfSysTemplateFeign.getAll(mfSysTemplate), null, true);
		dataMap.put("tableData", tableHtml);
	}

	private void getTableDataList(List<MfSysTemplate> list,String tableId,Map<String,Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		Ipage ipage = this.getIpage();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * AJAX获取查看
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
		getObjValue(formsystemplate0002, mfSysTemplate, formData);
		if (mfSysTemplate != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		dataMap.put("mfSysTemplate", mfSysTemplate);
		//产品、授信等中设计模板时获得模板地址
		dataMap.put("filePath", PropertiesUtil.getPageOfficeConfigProperty("office_sys_template"));
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		try {
			mfSysTemplateFeign.delete(mfSysTemplate);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		String fileName = WaterIdUtil.getWaterId();
		String templateNo = WaterIdUtil.getWaterId();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setUseFlag(BizPubParm.YES_NO_Y);
		JSONArray jsonArray = mfSysTemplateFeign.getJSONArray(mfSysTemplate);
		JSONObject json = new JSONObject();
		json.put("tempJsonArray", jsonArray);
		String ajaxData = json.toString();
		model.addAttribute("formsystemplate0002", formsystemplate0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("templateNo", templateNo);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_Insert";
	}

	/**
	 * 跳转到设计模板
	 */
	@RequestMapping(value = "/labelSetBase")
	public String labelSetBase(Model model, String ajaxData,String fileName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		LoanTemplateTagBase loanTemplateTagBase = new LoanTemplateTagBase();
		List<Map<String, Object>> tagMaps = new ArrayList<Map<String, Object>>();
		String groupFlag;
		try {
			Map<String, String> map = new CodeUtils().getMapByKeyName("BOOKMARK_CLASS");
			tagMaps = mfSysTemplateFeign.getLoantempTagBase(fileName, map);
			JSONArray jsonArray = JSONArray.fromObject(tagMaps);
			ajaxData = jsonArray.toString();
			/*** 字典项升序排列 */
			Map<String, String> sortMmap = new TreeMap<String, String>(new Comparator<String>() {
				@Override
				public int compare(String obj1, String obj2) {
					// 升序排序
					return obj1.compareTo(obj2);
				}
			});
			sortMmap.putAll(map);
			List<Map<String, String>> parmMapList = new ArrayList<Map<String, String>>();
			Iterator it = sortMmap.entrySet().iterator();
			while (it.hasNext()) {
				Map<String, String> temMap = new HashMap<String, String>();
				Map.Entry entry = (Entry) it.next();
				temMap.put("optCode", "type" + (String) entry.getKey());
				temMap.put("optName", (String) entry.getValue());
				parmMapList.add(temMap);
			}
			/*** 字典项升序排列结束 */
			groupFlag = new Gson().toJson(parmMapList);
		} catch (Exception e) {
			throw e;
		}

		model.addAttribute("formsystemplate0002", formsystemplate0002);
		model.addAttribute("groupFlag", groupFlag);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_labelSetBase";
	}

	/***
	 * 新增
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		getFormValue(formsystemplate0002);
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		setObjValue(formsystemplate0002, mfSysTemplate);
		mfSysTemplateFeign.insert(mfSysTemplate);
		getObjValue(formsystemplate0002, mfSysTemplate);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfSysTemplate", mfSysTemplate));
		List<MfSysTemplate> mfSysTemplateList = (List<MfSysTemplate>) mfSysTemplateFeign.findByPage(ipage) .getResult();
		model.addAttribute("formsystemplate0002", formsystemplate0002);
		model.addAttribute("mfSysTemplateList", mfSysTemplateList);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_Insert";
	}

	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplateedit");
		getFormValue(formsystemplate0002);
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
		getObjValue(formsystemplate0002, mfSysTemplate);
		JSONArray jsonArray = mfSysTemplateFeign.getJSONArray(mfSysTemplate);
		MfSysTemplateEsignerType mfSysTemplateEsignerType = new MfSysTemplateEsignerType();
		mfSysTemplateEsignerType.setTemplateNo(templateNo);
		JsonTableUtil jtu = new JsonTableUtil();
		String esignerTypeTableHtml = jtu.getJsonStr(
				"tablemfSysTemplateEsignerTypeList",
				"tableTag", mfSysTemplateEsignerTypeFeign.getByTemplateNo(mfSysTemplateEsignerType), null, true);
		model.addAttribute("esignerTypeTableHtml", esignerTypeTableHtml);
		JSONObject json = new JSONObject();
		json.put("tempJsonArray", jsonArray);
		String ajaxData = json.toString();
		model.addAttribute("formsystemplate0002", formsystemplate0002);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("mfSysTemplate", mfSysTemplate);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_Detail";
	}

	/**
	 * 删除
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String templateNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setTemplateNo(templateNo);
		mfSysTemplateFeign.delete(mfSysTemplate);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		getFormValue(formsystemplate0002);
		boolean validateFlag = this.validateFormData(formsystemplate0002);
	}

	/**
	 * 修改校验
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		getFormValue(formsystemplate0002);
		boolean validateFlag = this.validateFormData(formsystemplate0002);
	}

	/**
	 *
	 * 方法描述： 跳转到选择模板页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param mfSysTemplate
	 * @param templateNoStr
	 * @date 2017-8-5 下午8:17:30
	 */
	@RequestMapping(value = "/getAllTemplateList")
	public String getAllTemplateList(Model model, MfSysTemplate mfSysTemplate, String templateNoStr ) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray jsonArray = mfSysTemplateFeign.getAllTemplateList(mfSysTemplate, templateNoStr);
		String ajaxData = jsonArray.toString();
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_select";
	}

	/**
	 *
	 * 方法描述： 跳转选择现有模板
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @param mfSysTemplate
	 * @param templateNoStr
	 * @date 2017-12-19 下午7:45:39
	 */
	@RequestMapping(value = "/getAllTemplateByRadioList")
	public String getAllTemplateByRadioList(Model model, String ajaxData, MfSysTemplate mfSysTemplate, String templateNoStr) throws Exception {
		ActionContext.initialize(request, response);
		JSONArray jsonArray = mfSysTemplateFeign.getAllTemplateList(mfSysTemplate, templateNoStr);
		ajaxData = jsonArray.toString();
		model.addAttribute("query", "");
		return "/component/model/MfSysTemplate_selectByRadio";
	}

	/**
	 *
	 * 方法描述： 开发者后台新增模板方法
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author lwq
	 * @date 2017-8-30 下午4:37:12
	 */
	@RequestMapping(value = "/addTemplate")
	public String addTemplate(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsystemplate0002 = formService.getFormData("systemplate0002");
		String templateNo = WaterIdUtil.getWaterId();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		mfSysTemplate.setUseFlag(BizPubParm.YES_NO_Y);
		mfSysTemplate.setIfElectricSign(BizPubParm.YES_NO_N);
		mfSysTemplate.setTemplateNo(templateNo);
		JSONArray jsonArray = mfSysTemplateFeign.getJSONArray(mfSysTemplate);
		JSONObject json = new JSONObject();
		json.put("tempJsonArray", jsonArray);
		ajaxData = json.toString();
		getObjValue(formsystemplate0002,mfSysTemplate);
		model.addAttribute("formsystemplate0002", formsystemplate0002);
		model.addAttribute("query", "");
		model.addAttribute("ifElectricSign", BizPubParm.YES_NO_N);
		model.addAttribute("templateNo", templateNo);
		model.addAttribute("ajaxData", ajaxData);
		return "/component/model/MfSysTemplateDeview_Insert";
	}

	/**
	 *
	 * 方法描述： 跳转模板配置页面
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-19 上午9:53:38
	 */
	@RequestMapping(value = "/templateConfig")
	public String templateConfig(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/model/TemplateConfig";
	}

	/**
	 *
	 * 方法描述： 获得模板
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-19 上午10:33:44
	 */
	@RequestMapping(value = "/getSysTemplateConfigListAjax")
	@ResponseBody
	public Map<String, Object> getSysTemplateConfigListAjax(String templateType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfSysTemplate mfSysTemplate = new MfSysTemplate();
			// mfSysTemplate.setUseFlag("1");
			mfSysTemplate.setTemplateType(templateType);
			List<MfSysTemplate> mfSysTemplateList = mfSysTemplateFeign.getTemplateListByDesc(mfSysTemplate);
			CodeUtils cu = new CodeUtils();
			Map<String, String> groupMap = cu.getMapByKeyName("TEMPLATE_TYPE");
			List<ParmDic> dicList = (List<ParmDic>)new CodeUtils().getCacheByKeyName("TEMPLATE_TYPE");
			dataMap.put("tempMap",groupMap);
			dataMap.put("templateTypeDicList", dicList);
			dataMap.put("mfSysTemplateList", mfSysTemplateList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询模板基础数据出错");
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 获得模板设置表单html
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-12-19 下午8:30:02
	 */
	@RequestMapping(value = "/getSysTemplateHtmlAjax")
	@ResponseBody
	public Map<String, Object> getSysTemplateHtmlAjax(String templateNo,String query) throws Exception {
		query = "";
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfSysTemplate mfSysTemplate = new MfSysTemplate();
			if("00000".equals(templateNo)){
				mfSysTemplate.setTemplateSource("00000");
				mfSysTemplate.setTemplateSourceName("空白模板");
			}else{
				mfSysTemplate.setTemplateNo(templateNo);
				mfSysTemplate = mfSysTemplateFeign.getById(mfSysTemplate);
				mfSysTemplate.setTemplateSource(templateNo);
				mfSysTemplate.setTemplateSourceName(mfSysTemplate.getTemplateNameZh());
			}
			mfSysTemplate.setIfElectricSign(BizPubParm.YES_NO_N);
			FormData formsystemplate0002 = formService.getFormData("systemplate0002");
			getFormValue(formsystemplate0002);
			getObjValue(formsystemplate0002, mfSysTemplate);
			JsonFormUtil jsonFormUtil = new JsonFormUtil();
			String htmlStr = jsonFormUtil.getJsonStr(formsystemplate0002, "bootstarpTag", query);
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("templateSuffix", mfSysTemplate.getTemplateSuffix());
			dataMap.put("templateSource", mfSysTemplate.getTemplateSource());
			dataMap.put("mfSysTemplate", mfSysTemplate);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询模板基础数据出错");
		}
		return dataMap;
	}

	/**
	 *@desc 跳转授信合同模板列表
	 *@author lwq
	 *@date 2018/8/29 15:14
	 *@parm [model, cusNo, relNo]
	 *@return java.lang.String
	 **/
	@RequestMapping(value = "/toCreditTemplateList")
	public String toCreditTemplateList(Model model,String cusNo,String relNo,String templateType,String tableId){
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("creditAppId", relNo);
		model.addAttribute("templateType", templateType);
		model.addAttribute("tableId", tableId);
		if("13".equals(templateType)){
			model.addAttribute("saveBtn", "0");
		}
		return "/component/model/MfCreditTemplateList";
	}

	/**
	 *@desc 授信合同打印模板
	 *@author lwq
	 *@date 2018/8/29 15:09
	 *@parm [model, cusNo, relNo]
	 *@return java.lang.String
	 **/
	@RequestMapping(value = "/getCreditTemplateListAjax")
	@ResponseBody
	public Map<String, Object> getCreditTemplateListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
														 String ajaxData,String templateType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysTemplate mfSysTemplate = new MfSysTemplate();
		try {
			mfSysTemplate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysTemplate.setCriteriaList(mfSysTemplate, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysTemplate,"1000000001");//记录级权限控制方法
			mfSysTemplate.setTemplateType(templateType);
			mfSysTemplate.setUseFlag(BizPubParm.YES_NO_Y);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysTemplate", mfSysTemplate));
			ipage = mfSysTemplateFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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

}
