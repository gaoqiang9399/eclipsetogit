package app.component.frontview.controller;

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

import app.component.frontview.entity.VwFeatureManage;
import app.component.frontview.feign.VwFeatureManageFeign;
import app.util.toolkit.Ipage;

/**
 * Title: VwFeatureManageAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 10 19:16:00 CST 2017
 **/
@Controller
@RequestMapping("/vwFeatureManage")
public class VwFeatureManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwFeatureManageBo
	@Autowired
	private VwFeatureManageFeign vwFeatureManageFeign;
	// 全局变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/frontview/VwFeatureManage_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwFeatureManage vwFeatureManage = new VwFeatureManage();
		try {
			vwFeatureManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwFeatureManage.setCriteriaList(vwFeatureManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwFeatureManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = vwFeatureManageFeign.findByPage(ipage, vwFeatureManage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
//			logger.error("异常，", e);
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
			FormData formvwfeature0002 = formService.getFormData("vwfeature0002");
			getFormValue(formvwfeature0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvwfeature0002)) {
				VwFeatureManage vwFeatureManage = new VwFeatureManage();
				setObjValue(formvwfeature0002, vwFeatureManage);
				vwFeatureManageFeign.insert(vwFeatureManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("异常，", e);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formvwfeature0002 = formService.getFormData("vwfeature0002");
			getFormValue(formvwfeature0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvwfeature0002)) {
				VwFeatureManage vwFeatureManage = new VwFeatureManage();
				setObjValue(formvwfeature0002, vwFeatureManage);
				vwFeatureManageFeign.update(vwFeatureManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("异常，", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		VwFeatureManage vwFeatureManage = new VwFeatureManage();
		vwFeatureManage.setId(id);
		try {
			vwFeatureManageFeign.delete(vwFeatureManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
//			logger.error("异常，", e);
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
	@RequestMapping(value = "/input")
	public String input(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		VwFeatureManage vwFeatureManage = new VwFeatureManage();
		vwFeatureManage.setId(id);
		VwFeatureManage vf = vwFeatureManageFeign.getById(vwFeatureManage);
		if (vf == null) {// 如果没有就创建一个
			vwFeatureManageFeign.insert(vwFeatureManage);
		} else {// 有就查出来
			vwFeatureManage = vf;
		}
		FormData formvwfeature0002 = formService.getFormData("vwfeature0002");
		getObjValue(formvwfeature0002, vwFeatureManage);
		model.addAttribute("vwFeatureManage", vwFeatureManage);
		model.addAttribute("formvwfeature0002", formvwfeature0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwFeatureManage_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwfeature0002 = formService.getFormData("vwfeature0001");
		getFormValue(formvwfeature0002);
		VwFeatureManage vwFeatureManage = new VwFeatureManage();
		vwFeatureManage.setId(id);
		vwFeatureManage = vwFeatureManageFeign.getById(vwFeatureManage);
		getObjValue(formvwfeature0002, vwFeatureManage);
		model.addAttribute("vwFeatureManage", vwFeatureManage);
		model.addAttribute("formvwfeature0002", formvwfeature0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwFeatureManage_Detail";
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
		FormData formvwfeature0002 = formService.getFormData("vwfeature0002");
		getFormValue(formvwfeature0002);
		boolean validateFlag = this.validateFormData(formvwfeature0002);
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
		FormData formvwfeature0002 = formService.getFormData("vwfeature0002");
		getFormValue(formvwfeature0002);
		boolean validateFlag = this.validateFormData(formvwfeature0002);
	}

}
