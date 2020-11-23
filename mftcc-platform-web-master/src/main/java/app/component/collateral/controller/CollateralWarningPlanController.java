
package app.component.collateral.controller;

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

import app.component.collateral.entity.CollateralWarningPlan;
import app.component.collateral.feign.CollateralWarningPlanFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: CollateralWarningPlanController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 31 16:47:32 CST 2017
 **/
@Controller
@RequestMapping("/collateralWarningPlan")
public class CollateralWarningPlanController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CollateralWarningPlanFeign collateralWarningPlanFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */

	private void getTableData(Map<String, Object> dataMap, String tableId, CollateralWarningPlan collateralWarningPlan)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", collateralWarningPlanFeign.getAll(collateralWarningPlan),
				null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
		CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
		Ipage ipage = this.getIpage();
		@SuppressWarnings("unchecked")
		List<CollateralWarningPlan> collateralWarningPlanList = (List<CollateralWarningPlan>) collateralWarningPlanFeign
				.findByPage(ipage, collateralWarningPlan).getResult();
		model.addAttribute("formdlcollateralwarningplan0002", formdlcollateralwarningplan0002);
		model.addAttribute("collateralWarningPlanList", collateralWarningPlanList);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralWarningPlan_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListAll")
	public String getListAll(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
		CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
		List<CollateralWarningPlan> collateralWarningPlanList = collateralWarningPlanFeign
				.getAll(collateralWarningPlan);
		model.addAttribute("formdlcollateralwarningplan0002", formdlcollateralwarningplan0002);
		model.addAttribute("collateralWarningPlanList", collateralWarningPlanList);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralWarningPlan_List";
	}

	/**
	 * AJAX新增
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
			FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
			getFormValue(formdlcollateralwarningplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarningplan0002)) {
				CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
				setObjValue(formdlcollateralwarningplan0002, collateralWarningPlan);
				// 生成主键
				String warnNo = WaterIdUtil.getWaterId();
				collateralWarningPlan.setWarnNo(warnNo);
				collateralWarningPlanFeign.insert(collateralWarningPlan);

				// getTableData();//获取列表

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
			throw e;
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
			getFormValue(formdlcollateralwarningplan0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdlcollateralwarningplan0002)) {
				CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
				setObjValue(formdlcollateralwarningplan0002, collateralWarningPlan);
				collateralWarningPlanFeign.update(collateralWarningPlan);
				getTableData(dataMap, tableId, collateralWarningPlan);// 获取列表
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
	@ResponseBody
	public Map<String, Object> getByIdAjax(String warnNo) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
		CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
		collateralWarningPlan.setWarnNo(warnNo);
		collateralWarningPlan = collateralWarningPlanFeign.getById(collateralWarningPlan);
		getObjValue(formdlcollateralwarningplan0002, collateralWarningPlan, formData);
		if (collateralWarningPlan != null) {
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
	public Map<String, Object> deleteAjax(String warnNo, String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
		collateralWarningPlan.setWarnNo(warnNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			collateralWarningPlan = (CollateralWarningPlan) JSONObject.toBean(jb, CollateralWarningPlan.class);
			collateralWarningPlanFeign.delete(collateralWarningPlan);
			getTableData(dataMap, tableId, collateralWarningPlan);// 获取列表
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

	/***
	 * 标准例子 列表数据查询的异步方法
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CollateralWarningPlan collateralWarningPlan = new CollateralWarningPlan();
		try {
			collateralWarningPlan.setCustomQuery(ajaxData);// 自定义查询参数赋值
			collateralWarningPlan.setCriteriaList(collateralWarningPlan, ajaxData);// 我的筛选
			// this.getRoleConditions(appProject,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = collateralWarningPlanFeign.findByPage(ipage, collateralWarningPlan);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formdlcollateralwarningplan0002 = formService.getFormData("dlcollateralwarningplan0002");
		// 获取产品种类
		// MfSysKind mfSysKind= new MfSysKind();
		// List<MfSysKind> list = prdctInterfaceFeign.getSysKindList(mfSysKind);
		// JSONArray array = JSONArray.fromObject(list);
		// json = new JSONObject();
		// json.put("kindList", array);
		model.addAttribute("formdlcollateralwarningplan0002", formdlcollateralwarningplan0002);
		model.addAttribute("query", "");
		return "/component/collateral/CollateralWarningPlanController_Insert";
	}

}
