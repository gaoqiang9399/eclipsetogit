package app.component.financingoptions.controller;

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

import app.component.common.EntityUtil;
import app.component.financingoptions.entity.MfFinancingOptions;
import app.component.financingoptions.feign.MfFinancingOptionsFeign;
import app.component.pss.utils.DealTableUtil;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfFinancingOptionsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 10 09:40:36 CST 2018
 **/
@Controller
@RequestMapping("/mfFinancingOptions")
public class MfFinancingOptionsController extends BaseFormBean {
	@Autowired
	private MfFinancingOptionsFeign mfFinancingOptionsFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/financingoptions/MfFinancingOptions_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param tableId
	 * @param tableType
	 * @param pageNo
	 * @param ajaxData
	 * @param pageSize
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String tableId, String tableType, Integer pageNo, String ajaxData,
			Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		try {
			mfFinancingOptions.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFinancingOptions.setCriteriaList(mfFinancingOptions, ajaxData);// 我的筛选
			mfFinancingOptions.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfFinancingOptions,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfFinancingOptions", mfFinancingOptions));
			ipage = mfFinancingOptionsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			// 处理需要隐藏的列
			Map<String, String> map = new HashMap<String, String>();
			tableHtml = DealTableUtil.dealTabStr(tableHtml, map);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
			getFormValue(formfinancingoptionsbase, getMapByJson(ajaxData));
			if (this.validateFormData(formfinancingoptionsbase)) {
				MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
				setObjValue(formfinancingoptionsbase, mfFinancingOptions);
				mfFinancingOptionsFeign.insert(mfFinancingOptions);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		getFormValue(formfinancingoptionsbase, getMapByJson(ajaxData));
		MfFinancingOptions mfFinancingOptionsJsp = new MfFinancingOptions();
		setObjValue(formfinancingoptionsbase, mfFinancingOptionsJsp);
		MfFinancingOptions mfFinancingOptions = mfFinancingOptionsFeign.getById(mfFinancingOptionsJsp);
		if (mfFinancingOptions != null) {
			try {
				mfFinancingOptions = (MfFinancingOptions) EntityUtil.reflectionSetVal(mfFinancingOptions,
						mfFinancingOptionsJsp, getMapByJson(ajaxData));
				mfFinancingOptionsFeign.update(mfFinancingOptions);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		try {
			FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
			getFormValue(formfinancingoptionsbase, getMapByJson(ajaxData));
			if (this.validateFormData(formfinancingoptionsbase)) {
				mfFinancingOptions = new MfFinancingOptions();
				setObjValue(formfinancingoptionsbase, mfFinancingOptions);
				mfFinancingOptionsFeign.update(mfFinancingOptions);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param financingOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String financingOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		mfFinancingOptions.setFinancingOptionsId(financingOptionsId);
		mfFinancingOptions = mfFinancingOptionsFeign.getById(mfFinancingOptions);
		getObjValue(formfinancingoptionsbase, mfFinancingOptions, formData);
		if (mfFinancingOptions != null) {
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
	 * @param financingOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String financingOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		mfFinancingOptions.setFinancingOptionsId(financingOptionsId);
		try {
			mfFinancingOptionsFeign.delete(mfFinancingOptions);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
		FormService formService = new FormService();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		CodeUtils cu = new CodeUtils();
		JSONArray downPaymentRatioJsArray = new JSONArray();// 获取首付比例
		JSONArray downPaymentRatioMap = cu.getJSONArrayByKeyName("DOWN_PAYMENT_RATIO");
		for (int i = 0; i < downPaymentRatioMap.size(); i++) {
			JSONObject obj = downPaymentRatioMap.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			downPaymentRatioJsArray.add(obj);
		}
		model.addAttribute("downPaymentRatio", downPaymentRatioJsArray.toString());

		JSONArray financingTermJsArray = new JSONArray();// 获取融资期限
		JSONArray financingTermMap = cu.getJSONArrayByKeyName("FINANCING_TERM");
		for (int i = 0; i < financingTermMap.size(); i++) {
			JSONObject obj = financingTermMap.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			financingTermJsArray.add(obj);
		}
		model.addAttribute("financingTerm", financingTermJsArray.toString());

		List<MfFinancingOptions> mfFinancingOptionsList = mfFinancingOptionsFeign.getAll();
		JSONArray resultMap = new JSONArray();// 获取产品
		JSONArray map = cu.getJSONArrayByKeyName("KIND_NO");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			boolean flag = true;
			String id = obj.getString("optCode");
			for (MfFinancingOptions mfFinancingOptions : mfFinancingOptionsList) {
				if (id.equals(mfFinancingOptions.getApplicableProducts())) {
					flag = false;
				}
			}
			if (flag) {
				obj.put("id", id);
				obj.put("name", obj.getString("optName"));
				resultMap.add(obj);
			}
		}
		model.addAttribute("kindNo", resultMap.toString());
		// 标签
		JSONArray labelArray = cu.getJSONArrayByKeyName("FINAN_LABEL");
		JSONArray labelMap = new JSONArray();
		for (int i = 0; i < labelArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", labelArray.getJSONObject(i).getString("optCode"));
			obj.put("name", labelArray.getJSONObject(i).getString("optName"));
			labelMap.add(obj);
		}
		model.addAttribute("labelMap", labelMap);
		// 产品优势
		JSONArray kindAdvantageArray = cu.getJSONArrayByKeyName("KIND_ADVANTAGE");
		JSONArray kindAdvantageMap = new JSONArray();
		for (int i = 0; i < kindAdvantageArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", kindAdvantageArray.getJSONObject(i).getString("optCode"));
			obj.put("name", kindAdvantageArray.getJSONObject(i).getString("optName"));
			kindAdvantageMap.add(obj);
		}
		model.addAttribute("kindAdvantageMap", kindAdvantageMap);
		// 准入条件
		JSONArray entryCriteriaArray = cu.getJSONArrayByKeyName("ENTRY_CRITERIA");
		JSONArray entryCriteriaMap = new JSONArray();
		for (int i = 0; i < entryCriteriaArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", entryCriteriaArray.getJSONObject(i).getString("optCode"));
			obj.put("name", entryCriteriaArray.getJSONObject(i).getString("optName"));
			entryCriteriaMap.add(obj);
		}
		model.addAttribute("entryCriteriaMap", entryCriteriaMap);
		// 申请流程
		JSONArray applicationProcessArray = cu.getJSONArrayByKeyName("APPLICATION_PROCESS");
		JSONArray applicationProcessMap = new JSONArray();
		for (int i = 0; i < applicationProcessArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", applicationProcessArray.getJSONObject(i).getString("optCode"));
			obj.put("name", applicationProcessArray.getJSONObject(i).getString("optName"));
			applicationProcessMap.add(obj);
		}
		model.addAttribute("applicationProcessMap", applicationProcessMap);

		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		String financingOptionsId = WaterIdUtil.getWaterId();
		mfFinancingOptions.setFinancingOptionsId(financingOptionsId);
		getObjValue(formfinancingoptionsbase, mfFinancingOptions);
		model.addAttribute("formfinancingoptionsbase", formfinancingoptionsbase);
		model.addAttribute("financingOptionsId", financingOptionsId);
		model.addAttribute("query", "");
		return "/component/financingoptions/MfFinancingOptions_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param financingOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String financingOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formfinancingoptionsdetail = formService.getFormData("financingoptionsdetail");
		getFormValue(formfinancingoptionsdetail);
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		mfFinancingOptions.setFinancingOptionsId(financingOptionsId);
		mfFinancingOptions = mfFinancingOptionsFeign.getById(mfFinancingOptions);
		getObjValue(formfinancingoptionsdetail, mfFinancingOptions);
		CodeUtils cu = new CodeUtils();
		JSONArray downPaymentRatioJsArray = new JSONArray();// 获取首付比例
		JSONArray downPaymentRatioMap = cu.getJSONArrayByKeyName("DOWN_PAYMENT_RATIO");
		for (int i = 0; i < downPaymentRatioMap.size(); i++) {
			JSONObject obj = downPaymentRatioMap.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			downPaymentRatioJsArray.add(obj);
		}
		model.addAttribute("downPaymentRatio", downPaymentRatioJsArray.toString());

		JSONArray financingTermJsArray = new JSONArray();// 获取融资期限
		JSONArray financingTermMap = cu.getJSONArrayByKeyName("FINANCING_TERM");
		for (int i = 0; i < financingTermMap.size(); i++) {
			JSONObject obj = financingTermMap.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			financingTermJsArray.add(obj);
		}
		model.addAttribute("financingTerm", financingTermJsArray.toString());

		List<MfFinancingOptions> mfFinancingOptionsList = mfFinancingOptionsFeign.getAll();
		JSONArray resultMap = new JSONArray();// 获取产品
		JSONArray map = cu.getJSONArrayByKeyName("KIND_NO");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			boolean flag = true;
			String id = obj.getString("optCode");
			for (MfFinancingOptions mfFinancingOptions1 : mfFinancingOptionsList) {
				if (id.equals(mfFinancingOptions1.getApplicableProducts())) {
					flag = false;
				}
				if (id.equals(mfFinancingOptions.getApplicableProducts())) {
					flag = true;
				}
			}
			if (flag) {
				obj.put("id", id);
				obj.put("name", obj.getString("optName"));
				resultMap.add(obj);
			}
		}
		model.addAttribute("kindNo", resultMap.toString());
		JSONArray labelArray = cu.getJSONArrayByKeyName("FINAN_LABEL");
		JSONArray labelMap = new JSONArray();
		for (int i = 0; i < labelArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", labelArray.getJSONObject(i).getString("optCode"));
			obj.put("name", labelArray.getJSONObject(i).getString("optName"));
			labelMap.add(obj);
		}
		model.addAttribute("labelMap", labelMap);
		// 产品优势
		JSONArray kindAdvantageArray = cu.getJSONArrayByKeyName("KIND_ADVANTAGE");
		JSONArray kindAdvantageMap = new JSONArray();
		for (int i = 0; i < kindAdvantageArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", kindAdvantageArray.getJSONObject(i).getString("optCode"));
			obj.put("name", kindAdvantageArray.getJSONObject(i).getString("optName"));
			kindAdvantageMap.add(obj);
		}
		model.addAttribute("kindAdvantageMap", kindAdvantageMap);
		// 准入条件
		JSONArray entryCriteriaArray = cu.getJSONArrayByKeyName("ENTRY_CRITERIA");
		JSONArray entryCriteriaMap = new JSONArray();
		for (int i = 0; i < entryCriteriaArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", entryCriteriaArray.getJSONObject(i).getString("optCode"));
			obj.put("name", entryCriteriaArray.getJSONObject(i).getString("optName"));
			entryCriteriaMap.add(obj);
		}
		model.addAttribute("entryCriteriaMap", entryCriteriaMap);
		// 申请流程
		JSONArray applicationProcessArray = cu.getJSONArrayByKeyName("APPLICATION_PROCESS");
		JSONArray applicationProcessMap = new JSONArray();
		for (int i = 0; i < applicationProcessArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", applicationProcessArray.getJSONObject(i).getString("optCode"));
			obj.put("name", applicationProcessArray.getJSONObject(i).getString("optName"));
			applicationProcessMap.add(obj);
		}
		model.addAttribute("applicationProcessMap", applicationProcessMap);
		model.addAttribute("financingOptionsId", financingOptionsId);
		model.addAttribute("formfinancingoptionsdetail", formfinancingoptionsdetail);
		model.addAttribute("query", "");
		return "/component/financingoptions/MfFinancingOptions_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		getFormValue(formfinancingoptionsbase);
		boolean validateFlag = this.validateFormData(formfinancingoptionsbase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		getFormValue(formfinancingoptionsbase);
		boolean validateFlag = this.validateFormData(formfinancingoptionsbase);
	}

	/**
	 * 
	 * @描述 根据产品获得金融方案
	 * 
	 * @参数
	 * 
	 * @返回值
	 * 
	 * @创建人 shenhaobing
	 * 
	 * @创建时间 2018/7/22
	 * 
	 * @修改人和其它信息
	 * 
	 */
	@RequestMapping("/getByKindNoAjax")
	@ResponseBody
	public Map<String, Object> getByKindNoAjax(String kindNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfinancingoptionsbase = formService.getFormData("financingoptionsbase");
		MfFinancingOptions mfFinancingOptions = new MfFinancingOptions();
		mfFinancingOptions.setApplicableProducts(kindNo);
		mfFinancingOptions = mfFinancingOptionsFeign.getById(mfFinancingOptions);
		if (mfFinancingOptions != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfFinancingOptions", mfFinancingOptions);
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
}
