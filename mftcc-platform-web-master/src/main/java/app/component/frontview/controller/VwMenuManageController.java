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

import app.component.frontview.entity.VwMenuManage;
import app.component.frontview.feign.VwMenuManageFeign;
import app.util.toolkit.Ipage;

/**
 * Title: VwMenuManageAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 28 11:18:58 CST 2017
 **/
@Controller
@RequestMapping("/vwMenuManage")
public class VwMenuManageController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入VwMenuManageBo
	@Autowired
	private VwMenuManageFeign vwMenuManageFeign;
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
		return "/component/frontview/VwMenuManage_List";
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
		VwMenuManage vwMenuManage = new VwMenuManage();
		try {
			vwMenuManage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			vwMenuManage.setCriteriaList(vwMenuManage, ajaxData);// 我的筛选
			// this.getRoleConditions(vwMenuManage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = vwMenuManageFeign.findByPage(ipage, vwMenuManage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
//			logger.error("异常", e);
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
			FormData formvwmenu0002 = formService.getFormData("vwmenu0002");
			getFormValue(formvwmenu0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvwmenu0002)) {
				VwMenuManage vwMenuManage = new VwMenuManage();
				setObjValue(formvwmenu0002, vwMenuManage);
				vwMenuManageFeign.insert(vwMenuManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("异常", e);
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
			FormData formvwmenu0002 = formService.getFormData("vwmenu0001");
			getFormValue(formvwmenu0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvwmenu0002)) {
				VwMenuManage vwMenuManage = new VwMenuManage();
				setObjValue(formvwmenu0002, vwMenuManage);
				vwMenuManageFeign.update(vwMenuManage);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
//			logger.error("异常", e);
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
		VwMenuManage vwMenuManage = new VwMenuManage();
		vwMenuManage.setId(id);
		try {
			vwMenuManageFeign.delete(vwMenuManage);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
//			logger.error("异常", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获取最大序号
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMaxSort")
	@ResponseBody
	public Map<String, Object> getMaxSort(Model model, String pId) {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap.put("maxSort", vwMenuManageFeign.getMaxSort(pId));
		} catch (Exception e) {
//			logger.error("异常", e);
			dataMap.put("maxSort", "0");
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
		FormData formvwmenu0002 = formService.getFormData("vwmenu0002");
		model.addAttribute("formvwmenu0002", formvwmenu0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwMenuManage_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formvwmenu0002 = formService.getFormData("vwmenu0002");
		getFormValue(formvwmenu0002);
		VwMenuManage vwMenuManage = new VwMenuManage();
		setObjValue(formvwmenu0002, vwMenuManage);
		vwMenuManageFeign.insert(vwMenuManage);
		getObjValue(formvwmenu0002, vwMenuManage);
		this.addActionMessage(model, "保存成功");
		List<VwMenuManage> vwMenuManageList = (List<VwMenuManage>) vwMenuManageFeign.findByPage(this.getIpage(), vwMenuManage).getResult();
		model.addAttribute("vwMenuManageList", vwMenuManageList);
		model.addAttribute("formvwmenu0002", formvwmenu0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwMenuManage_Insert";
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
		FormData formvwmenu0002 = formService.getFormData("vwmenu0001");
		getFormValue(formvwmenu0002);
		VwMenuManage vwMenuManage = new VwMenuManage();
		vwMenuManage.setId(id);
		vwMenuManage = vwMenuManageFeign.getById(vwMenuManage);
		getObjValue(formvwmenu0002, vwMenuManage);
		model.addAttribute("vwMenuManage", vwMenuManage);
		model.addAttribute("formvwmenu0002", formvwmenu0002);
		model.addAttribute("query", "");
		return "/component/frontview/VwMenuManage_Detail";
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
		FormData formvwmenu0002 = formService.getFormData("vwmenu0002");
		getFormValue(formvwmenu0002);
		boolean validateFlag = this.validateFormData(formvwmenu0002);
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
		FormData formvwmenu0002 = formService.getFormData("vwmenu0002");
		getFormValue(formvwmenu0002);
		boolean validateFlag = this.validateFormData(formvwmenu0002);
	}

}
