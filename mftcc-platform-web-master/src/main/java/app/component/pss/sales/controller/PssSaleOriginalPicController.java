package app.component.pss.sales.controller;

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

import app.component.pss.sales.entity.PssSaleOriginalPic;
import app.component.pss.sales.feign.PssSaleOriginalPicFeign;
import net.sf.json.JSONObject;

/**
 * Title: PssSaleOriginalPicAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Sep 11 14:42:40 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleOriginalPic")
public class PssSaleOriginalPicController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleOriginalPicFeign pssSaleOriginalPicFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssSaleOriginalPic pssSaleOriginalPic) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleOriginalPicFeign.getAll(pssSaleOriginalPic), null,
				true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		this.getHttpRequest().setAttribute("pssNo", "pss20170912001");// 财务需要
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOriginalPic_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpsssaleoriginalpic0002 = formService.getFormData("psssaleoriginalpic0002");
		PssSaleOriginalPic pssSaleOriginalPic = new PssSaleOriginalPic();
		List<PssSaleOriginalPic> pssSaleOriginalPicList = pssSaleOriginalPicFeign.getAll(pssSaleOriginalPic);
		model.addAttribute("formpsssaleoriginalpic0002", formpsssaleoriginalpic0002);
		model.addAttribute("pssSaleOriginalPicList", pssSaleOriginalPicList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOriginalPic_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsssaleoriginalpic0002 = formService.getFormData("psssaleoriginalpic0002");
			getFormValue(formpsssaleoriginalpic0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssaleoriginalpic0002)) {
				PssSaleOriginalPic pssSaleOriginalPic = new PssSaleOriginalPic();
				setObjValue(formpsssaleoriginalpic0002, pssSaleOriginalPic);
				pssSaleOriginalPicFeign.insert(pssSaleOriginalPic);
				getTableData(tableId, pssSaleOriginalPic);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOriginalPic pssSaleOriginalPic = new PssSaleOriginalPic();
		try {
			FormData formpsssaleoriginalpic0002 = formService.getFormData("psssaleoriginalpic0002");
			getFormValue(formpsssaleoriginalpic0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssaleoriginalpic0002)) {
				pssSaleOriginalPic = new PssSaleOriginalPic();
				setObjValue(formpsssaleoriginalpic0002, pssSaleOriginalPic);
				pssSaleOriginalPicFeign.update(pssSaleOriginalPic);
				getTableData(tableId, pssSaleOriginalPic);// 获取列表
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsssaleoriginalpic0002 = formService.getFormData("psssaleoriginalpic0002");
		PssSaleOriginalPic pssSaleOriginalPic = new PssSaleOriginalPic();
		pssSaleOriginalPic.setId(id);
		pssSaleOriginalPic = pssSaleOriginalPicFeign.getById(pssSaleOriginalPic);
		getObjValue(formpsssaleoriginalpic0002, pssSaleOriginalPic, formData);
		if (pssSaleOriginalPic != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String id, String tableId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOriginalPic pssSaleOriginalPic = new PssSaleOriginalPic();
		pssSaleOriginalPic.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleOriginalPic = (PssSaleOriginalPic) JSONObject.toBean(jb, PssSaleOriginalPic.class);
			pssSaleOriginalPicFeign.delete(pssSaleOriginalPic);
			getTableData(tableId, pssSaleOriginalPic);// 获取列表
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

}
