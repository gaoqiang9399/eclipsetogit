package app.component.param.controller;

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

import app.component.param.entity.BusCtlParmMang;
import app.component.param.feign.BusCtlParmMangFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: BusCtlParmMangAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Fri Mar 11 01:44:29 GMT 2016
 **/
@Controller
@RequestMapping("/busCtlParmMang")
public class BusCtlParmMangController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private BusCtlParmMangFeign busCtlParmMangFeign;

	// 全局变量
	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
		List<BusCtlParmMang> busCtlParmMangList = busCtlParmMangFeign.getAll(busCtlParmMang);
		model.addAttribute("busCtlParmMangList", busCtlParmMangList);
		model.addAttribute("query", "");
		return "/component/param/BusCtlParmMang_List";
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageBus")
	public String getListPageBus(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
		Ipage ipage = this.getIpage();
		List<BusCtlParmMang> busCtlParmMangList = busCtlParmMangFeign.getAll(busCtlParmMang);
		model.addAttribute("busCtlParmMangList", busCtlParmMangList);
		model.addAttribute("query", "");
		return "/component/param/BusCtlParmMang_ListBus";
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
		FormData formparam0003 = formService.getFormData("param0003");
		BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
		List<BusCtlParmMang> busCtlParmMangList = busCtlParmMangFeign.getAll(busCtlParmMang);
		model.addAttribute("busCtlParmMangList", busCtlParmMangList);
		model.addAttribute("formparam0003", formparam0003);
		model.addAttribute("query", "");
		return "/component/param/BusCtlParmMang_List";
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
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
			busCtlParmMang.setKeyName(jb.getString("keyName"));
			busCtlParmMang.setFormula(jb.getString("formula"));
			busCtlParmMang.setRemark(jb.getString("remark"));
			busCtlParmMangFeign.insert(busCtlParmMang);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
			busCtlParmMang.setKeyName(jb.getString("keyName"));
			busCtlParmMang.setFormula(jb.getString("formula"));
			busCtlParmMang.setRemark(jb.getString("remark"));
			busCtlParmMangFeign.update(busCtlParmMang);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	@RequestMapping(value = "/updateBusAjax")
	@ResponseBody
	public Map<String, Object> updateBusAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
			busCtlParmMang.setKeyName(jb.getString("keyName"));
			busCtlParmMang = busCtlParmMangFeign.getById(busCtlParmMang);
			busCtlParmMang.setFormula(jb.getString("formula"));
			busCtlParmMangFeign.update(busCtlParmMang);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String keyName) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formparam0003 = formService.getFormData("param0003");
		BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
		busCtlParmMang.setKeyName(keyName);
		busCtlParmMang = busCtlParmMangFeign.getById(busCtlParmMang);
		getObjValue(formparam0003, busCtlParmMang, formData);
		if (busCtlParmMang != null) {
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
	public Map<String, Object> deleteAjax(String keyName) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BusCtlParmMang busCtlParmMang = new BusCtlParmMang();
		busCtlParmMang.setKeyName(keyName);
		try {
			busCtlParmMangFeign.delete(busCtlParmMang);
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
}
