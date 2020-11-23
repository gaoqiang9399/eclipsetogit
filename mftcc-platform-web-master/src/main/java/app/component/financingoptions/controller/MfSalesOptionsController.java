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

import app.component.carmodel.entity.MfCarBrand;
import app.component.carmodel.entity.MfCarModel;
import app.component.carmodel.entity.MfCarSeries;
import app.component.carmodelinterface.CarModelInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.financingoptions.entity.MfSalesOptions;
import app.component.financingoptions.feign.MfSalesOptionsFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfSalesOptionsAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 10 09:40:36 CST 2018
 **/
@Controller
@RequestMapping("/mfSalesOptions")
public class MfSalesOptionsController extends BaseFormBean {
	@Autowired
	private MfSalesOptionsFeign mfSalesOptionsFeign;
	@Autowired
	private CarModelInterfaceFeign carModelInterfaceFeign;
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
		return "/component/financingoptions/MfSalesOptions_List";
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
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		try {
			mfSalesOptions.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSalesOptions.setCriteriaList(mfSalesOptions, ajaxData);// 我的筛选
			mfSalesOptions.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfSalesOptions,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSalesOptions", mfSalesOptions));
			ipage = mfSalesOptionsFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
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
			FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
			getFormValue(formsalesoptionsbase, getMapByJson(ajaxData));
			if (this.validateFormData(formsalesoptionsbase)) {
				MfSalesOptions mfSalesOptions = new MfSalesOptions();
				setObjValue(formsalesoptionsbase, mfSalesOptions);
				mfSalesOptionsFeign.insert(mfSalesOptions);
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
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		getFormValue(formsalesoptionsbase, getMapByJson(ajaxData));
		MfSalesOptions mfSalesOptionsJsp = new MfSalesOptions();
		setObjValue(formsalesoptionsbase, mfSalesOptionsJsp);
		MfSalesOptions mfSalesOptions = mfSalesOptionsFeign.getById(mfSalesOptionsJsp);
		if (mfSalesOptions != null) {
			try {
				mfSalesOptions = (MfSalesOptions) EntityUtil.reflectionSetVal(mfSalesOptions,
						mfSalesOptionsJsp, getMapByJson(ajaxData));
				mfSalesOptionsFeign.update(mfSalesOptions);
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
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		try {
			FormData formsalesoptionsbase = formService.getFormData("salesoptionsdetail");
			getFormValue(formsalesoptionsbase, getMapByJson(ajaxData));
			if (this.validateFormData(formsalesoptionsbase)) {
				mfSalesOptions = new MfSalesOptions();
				setObjValue(formsalesoptionsbase, mfSalesOptions);
				mfSalesOptionsFeign.update(mfSalesOptions);
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
	 * @param salesOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String salesOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		mfSalesOptions.setSalesOptionsId(salesOptionsId);
		mfSalesOptions = mfSalesOptionsFeign.getById(mfSalesOptions);
		getObjValue(formsalesoptionsbase, mfSalesOptions, formData);
		if (mfSalesOptions != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("mfSalesOptions", mfSalesOptions);
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param salesOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String salesOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		mfSalesOptions.setSalesOptionsId(salesOptionsId);
		try {
			mfSalesOptionsFeign.delete(mfSalesOptions);
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
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		// 获取品牌
		List<MfCarBrand> mfCarBrandList = carModelInterfaceFeign.getMfCarBrandAllList();
		JSONArray mfCarBrandMap = new JSONArray();
		for (MfCarBrand mfCarBrand : mfCarBrandList) {
			JSONObject obj = new JSONObject();
			obj.put("id", mfCarBrand.getBrandId());
			obj.put("name", mfCarBrand.getBrandName());
			mfCarBrandMap.add(obj);
		}
		model.addAttribute("mfCarBrandMap", mfCarBrandMap.toString());
		Map<String,Object> mfCarSeriesMap = getSeriesByBrandIdAjax(mfCarBrandList.get(0).getBrandId());
		model.addAttribute("mfCarSeriesMap", mfCarSeriesMap.get("mfCarSeriesMap").toString());
		Map<String,Object> mfCarModelMap = getModelBySeriesIdAjax(mfCarSeriesMap.get("seriesId").toString());
		model.addAttribute("mfCarModelMap", mfCarModelMap.get("mfCarModelMap").toString());
			

		CodeUtils cu = new CodeUtils();
		JSONArray resultMap = new JSONArray();// 获取产品
		JSONArray map = cu.getJSONArrayByKeyName("KIND_NO");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("kindNo", resultMap.toString());
		
		model.addAttribute("formsalesoptionsbase", formsalesoptionsbase);
		model.addAttribute("query", "");
		return "/component/financingoptions/MfSalesOptions_Insert";
	}

	@RequestMapping("/getSeriesByBrandIdAjax")
	@ResponseBody
	public Map<String, Object> getSeriesByBrandIdAjax(String brandId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCarSeries mfCarSeries = new MfCarSeries();
		mfCarSeries.setBrandId(brandId);
		try {
			List<MfCarSeries> mfCarSeriesList = carModelInterfaceFeign.getMfCarSeriesByBrandId(mfCarSeries);
			JSONArray mfCarSeriesMap = new JSONArray();
			for (MfCarSeries mfCarSeries1 : mfCarSeriesList) {
				JSONObject obj = new JSONObject();
				obj.put("id", mfCarSeries1.getSeriesId());
				obj.put("name", mfCarSeries1.getSeriesName());
				mfCarSeriesMap.add(obj);
			}
			dataMap.put("mfCarSeriesMap", mfCarSeriesMap);
			
			dataMap.put("mfCarModelMap", getModelBySeriesIdAjax(mfCarSeriesList.get(0).getSeriesId()).get("mfCarModelMap"));
			dataMap.put("seriesId", mfCarSeriesList.get(0).getSeriesId());
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getModelBySeriesIdAjax")
	@ResponseBody
	public Map<String, Object> getModelBySeriesIdAjax(String seriesId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCarModel mfCarModel = new MfCarModel();
		mfCarModel.setSeriesId(seriesId);
		try {
			List<MfCarModel> mfCarModelList = carModelInterfaceFeign.getMfCarModelBySeriesId(mfCarModel);
			JSONArray mfCarModelMap = new JSONArray();
			for (MfCarModel mfCarModel1 : mfCarModelList) {
				JSONObject obj = new JSONObject();
				obj.put("id", mfCarModel1.getModelId());
				obj.put("name", mfCarModel1.getModelName());
				mfCarModelMap.add(obj);
			}
			dataMap.put("mfCarModelMap", mfCarModelMap);
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
	 * 方法描述： 
	 * @param seriesId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年7月20日 下午8:35:54
	 */
	@RequestMapping("/ifRepeat")
	@ResponseBody
	public Map<String, Object> ifRepeat(String modelId,String salesArea,String salesOptionsId,String selectColors) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfSalesOptions mfSalesOptions = new MfSalesOptions();
			mfSalesOptions.setModelId(modelId);
			mfSalesOptions.setSalesOptionsId(salesOptionsId);
			mfSalesOptions.setSalesArea(salesArea);
			mfSalesOptions.setSelectColors(selectColors);
			dataMap = mfSalesOptionsFeign.ifRepeat(mfSalesOptions);
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
	 * 查询
	 * 
	 * @param salesOptionsId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String salesOptionsId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formsalesoptionsdetail = formService.getFormData("salesoptionsdetail");
		getFormValue(formsalesoptionsdetail);
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		mfSalesOptions.setSalesOptionsId(salesOptionsId);
		mfSalesOptions = mfSalesOptionsFeign.getById(mfSalesOptions);
		getObjValue(formsalesoptionsdetail, mfSalesOptions);
		// 获取品牌
		List<MfCarBrand> mfCarBrandList = carModelInterfaceFeign.getMfCarBrandAllList();
		JSONArray mfCarBrandMap = new JSONArray();
		for (MfCarBrand mfCarBrand : mfCarBrandList) {
			JSONObject obj = new JSONObject();
			obj.put("id", mfCarBrand.getBrandId());
			obj.put("name", mfCarBrand.getBrandName());
			mfCarBrandMap.add(obj);
		}
		model.addAttribute("mfCarBrandMap", mfCarBrandMap.toString());
		// 获取车系
		
		model.addAttribute("mfCarSeriesMap", getSeriesByBrandIdAjax(mfSalesOptions.getBrandId()).get("mfCarSeriesMap").toString());
		// 获取车型
		model.addAttribute("mfCarModelMap", getModelBySeriesIdAjax(mfSalesOptions.getSeriesId()).get("mfCarModelMap").toString());
		CodeUtils cu = new CodeUtils();
		JSONArray resultMap = new JSONArray();// 获取产品
		JSONArray map = cu.getJSONArrayByKeyName("KIND_NO");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = map.getJSONObject(i);
			obj.put("id", obj.getString("optCode"));
			obj.put("name", obj.getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("kindNo", resultMap.toString());
		
		model.addAttribute("formsalesoptionsdetail", formsalesoptionsdetail);
		model.addAttribute("query", "");
		return "/component/financingoptions/MfSalesOptions_Detail";
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
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		getFormValue(formsalesoptionsbase);
		boolean validateFlag = this.validateFormData(formsalesoptionsbase);
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
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		getFormValue(formsalesoptionsbase);
		boolean validateFlag = this.validateFormData(formsalesoptionsbase);
	}
	/**

	 *@描述 根据车型、上牌城市获得销售方案信息

	 *@参数

	 *@返回值

	 *@创建人  shenhaobing

	 *@创建时间  2018/7/22

	 *@修改人和其它信息

	 */
	@RequestMapping("/getByModelIdAjax")
	@ResponseBody
	public Map<String, Object> getByModelIdAjax(String modelId,String salesArea) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formsalesoptionsbase = formService.getFormData("salesoptionsbase");
		MfSalesOptions mfSalesOptions = new MfSalesOptions();
		mfSalesOptions.setModelId(modelId);
		mfSalesOptions.setSalesArea(salesArea);
		mfSalesOptions = mfSalesOptionsFeign.getById(mfSalesOptions);
		if (mfSalesOptions != null) {
			dataMap.put("flag", "success");
			dataMap.put("mfSalesOptions", mfSalesOptions);
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}
}
