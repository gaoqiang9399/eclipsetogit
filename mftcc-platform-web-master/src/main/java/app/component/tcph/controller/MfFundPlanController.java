package app.component.tcph.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.tcph.entity.MfFundPlan;
import app.component.tcph.feign.MfFundPlanFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfFundPlanAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 29 18:13:15 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfFundPlan")
public class MfFundPlanController extends BaseFormBean {
	// 注入MfFundPlanBo
	@Autowired
	private MfFundPlanFeign mfFundPlanFeign;
	
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
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		// 从数据字典中拿到缓存
		CodeUtils codeUtils = new CodeUtils();
		JSONArray jsonArrayByKeyName = codeUtils.getJSONArrayByKeyName("FUND_PLAN_UNIT");
		this.getHttpRequest().setAttribute("jsonArrayByKeyName", jsonArrayByKeyName);
		String regNo = User.getRegNo(request);
		model.addAttribute("codeUtils", codeUtils);
		model.addAttribute("regNo", regNo);
		return "/component/tcph/MfFundPlan_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,Integer pageNo,Integer pageSize,String tableId,String tableType ) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFundPlan mfFundPlan = new MfFundPlan();
		try {
			mfFundPlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfFundPlan.setCriteriaList(mfFundPlan, ajaxData);// 我的筛选
			mfFundPlan.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfFundPlan",mfFundPlan));
			// 自定义查询Bo方法
			ipage = mfFundPlanFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfundplan0002 = formService.getFormData("fundplan0002");
			getFormValue(formfundplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfundplan0002)) {
				MfFundPlan mfFundPlan = new MfFundPlan();
				setObjValue(formfundplan0002, mfFundPlan);
				mfFundPlanFeign.insert(mfFundPlan);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfundplan0002 = formService.getFormData("fundplan0002");
		getFormValue(formfundplan0002, getMapByJson(ajaxData));
		MfFundPlan mfFundPlanJsp = new MfFundPlan();
		setObjValue(formfundplan0002, mfFundPlanJsp);
		MfFundPlan mfFundPlan = mfFundPlanFeign.getById(mfFundPlanJsp);
		if (mfFundPlan != null) {
			try {
				mfFundPlan = (MfFundPlan) EntityUtil.reflectionSetVal(mfFundPlan, mfFundPlanJsp, getMapByJson(ajaxData));
				mfFundPlanFeign.update(mfFundPlan);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
	    Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFundPlan mfFundPlan = new MfFundPlan();
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formfundplan0002 = formService.getFormData(formId);
			getFormValue(formfundplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfundplan0002)) {
				mfFundPlan = new MfFundPlan();
				setObjValue(formfundplan0002, mfFundPlan);
				if (!mfFundPlan.getOpNo().equals(User.getRegNo(request))) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "更新失败");
					return dataMap;
				}
				mfFundPlanFeign.update(mfFundPlan);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formfundplan0002 = formService.getFormData("fundplan0002");
		MfFundPlan mfFundPlan = new MfFundPlan();
		mfFundPlan.setId(id);
		mfFundPlan = mfFundPlanFeign.getById(mfFundPlan);
		getObjValue(formfundplan0002, mfFundPlan, formData);
		if (mfFundPlan != null) {
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
	@ResponseBody
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfFundPlan mfFundPlan = new MfFundPlan();
		mfFundPlan.setId(id);
		try {
			mfFundPlanFeign.delete(mfFundPlan);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formfundplan0002 = formService.getFormData("fundplan0002");
		JSONArray resultMap = new JSONArray();
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("FUND_PLAN_UNIT");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", map.getJSONObject(i).getString("optCode"));
			obj.put("name", map.getJSONObject(i).getString("optName"));
			resultMap.add(obj);
		}
		String regNo = User.getRegNo(request);
		model.addAttribute("formfundplan0002", formfundplan0002);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("map", map);
		model.addAttribute("regNo", regNo);
		model.addAttribute("query", "");
		return "/component/tcph/MfFundPlan_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formfundplan0002 = formService.getFormData("fundplan0002");
		getFormValue(formfundplan0002);
		MfFundPlan mfFundPlan = new MfFundPlan();
		mfFundPlan.setId(id);
		mfFundPlan = mfFundPlanFeign.getById(mfFundPlan);
		model.addAttribute("opNo", mfFundPlan.getOpNo());
		JSONArray resultMap = new JSONArray();
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("FUND_PLAN_UNIT");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", map.getJSONObject(i).getString("optCode"));
			obj.put("name", map.getJSONObject(i).getString("optName"));
			resultMap.add(obj);
		}
		getObjValue(formfundplan0002, mfFundPlan);
		model.addAttribute("formfundplan0002", formfundplan0002);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("regNo", User.getRegNo(request));
	
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("map", map);
		model.addAttribute("query", "");
		return "/component/tcph/MfFundPlan_Detail";
	}
	/**
	 * 创造资金计划表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createFundPlanTable")
	public String createFundPlanTable(Model model,String beginDate,String endDate) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(beginDate != null && endDate != null){
		}else{
			beginDate = DateUtil.getShowDateTime(DateUtil.addByDay(-6));
			endDate = DateUtil.getShowDateTime(DateUtil.getDate());
		}
		dataMap.put("beginDate", DateUtil.getYYYYMMDD(beginDate));
		dataMap.put("endDate", DateUtil.getYYYYMMDD(endDate));
		Map<String, Object> mfFundPlanTableMap = mfFundPlanFeign.getMfFundPlanTable(dataMap);
		CodeUtils cu = new CodeUtils();
		Object mapType = cu.getMapByKeyName("FUND_PLAN_TYPE");
		Object mapUnit = cu.getMapByKeyName("FUND_PLAN_UNIT");
		model.addAttribute("mapType", mapType);
		model.addAttribute("mapUnit", mapType);
		model.addAttribute("query", "");
		return "/component/tcph/MfFundPlan_fundPlanTable";
	}
	/**
	 * 获取天数
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getDaysAjax")
	public Map<String, Object> getDaysAjax( String beginDate,String endDate) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("beginDate", DateUtil.getYYYYMMDD(beginDate));
		dataMap.put("endDate", DateUtil.getYYYYMMDD(endDate));
		int count = mfFundPlanFeign.getDays(dataMap);
		if(count > 7){
			dataMap.put("flag", "error");
			dataMap.put("msg", "报表统计天数不能超过7天，请缩小日期范围");
		}else{
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		}
		return dataMap;
	}
	/**
	 * 导出excel
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/exportExcelAjax")
	public Map<String, Object> exportExcelAjax(String beginDate,String endDate ) throws Exception {
		ActionContext.initialize(request,response);
	    Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("beginDate", DateUtil.getYYYYMMDD(beginDate));
		dataMap.put("endDate", DateUtil.getYYYYMMDD(endDate));
		Map<String, Object> mfFundPlanTableMap = mfFundPlanFeign .getMfFundPlanTable(dataMap);
		String filePath = mfFundPlanFeign.exportExcel(mfFundPlanTableMap);
		dataMap.put("flag", "success");
		dataMap.put("filePath", filePath);
		dataMap.put("msg", "成功");
		return dataMap;
	}
	/**
	 * 方法描述： 下载文件
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018-1-22 下午3:56:15
	 */
	@ResponseBody
	@RequestMapping(value = "/fileDownload")
	public Map<String, Object> fileDownload(String filePath) throws Exception{
		ActionContext.initialize(request, response);
		BufferedInputStream inputStream = null;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			File file  = new File(filePath);
			String filename = file.getName();
			inputStream = new BufferedInputStream(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataMap;
	}

	/**
	 * 数据详情页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDetail")
	public String getDetail(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formfundplan0002 = formService.getFormData("fundplan0002");
		getFormValue(formfundplan0002);
		MfFundPlan mfFundPlan = new MfFundPlan();
		mfFundPlan.setId(id);
		mfFundPlan = mfFundPlanFeign.getById(mfFundPlan);
		getObjValue(formfundplan0002, mfFundPlan);
		JSONArray resultMap = new JSONArray();
		JSONArray map = new CodeUtils().getJSONArrayByKeyName("FUND_PLAN_UNIT");
		for (int i = 0; i < map.size(); i++) {
			JSONObject obj = new JSONObject();
			obj.put("id", map.getJSONObject(i).getString("optCode"));
			obj.put("name", map.getJSONObject(i).getString("optName"));
			resultMap.add(obj);
		}
		model.addAttribute("id", id);
		model.addAttribute("resultMap", resultMap);
		model.addAttribute("formfundplan0002", formfundplan0002);
		model.addAttribute("query", "");
		return "/component/tcph/MfFundPlan_Update";
	}

}
