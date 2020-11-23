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
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.param.entity.Scence;
import app.component.param.feign.ScenceFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: ScenceAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Jan 20 03:11:13 GMT 2016
 **/
@Controller
@RequestMapping("/scence")
public class ScenceController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入ScenceBo
	@Autowired
	private ScenceFeign scenceFeign;
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
		return "/component/param/Scence_List";
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
		Scence scence = new Scence();
		try {
			scence.setCustomQuery(ajaxData);// 自定义查询参数赋值
			scence.setCriteriaList(scence, ajaxData);// 我的筛选
			// this.getRoleConditions(scence,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = scenceFeign.findByPage(ipage, scence);
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
			FormData formparam0002 = formService.getFormData("param0002");
			getFormValue(formparam0002, getMapByJson(ajaxData));
			if (this.validateFormData(formparam0002)) {
				Scence scence = new Scence();
				setObjValue(formparam0002, scence);
				scenceFeign.insert(scence);
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
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002, getMapByJson(ajaxData));
		Scence scenceJsp = new Scence();
		setObjValue(formparam0002, scenceJsp);
		Scence scence = scenceFeign.getById(scenceJsp);
		if (scence != null) {
			try {
				scence = (Scence) EntityUtil.reflectionSetVal(scence, scenceJsp, getMapByJson(ajaxData));
				scenceFeign.update(scence);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
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
			FormData formparam0002 = formService.getFormData("param0002");
			getFormValue(formparam0002, getMapByJson(ajaxData));
			if (this.validateFormData(formparam0002)) {
				Scence scence = new Scence();
				setObjValue(formparam0002, scence);
				scenceFeign.update(scence);
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

	@RequestMapping(value = "/updateUseFlagAjax")
	@ResponseBody
	public Map<String, Object> updateUseFlagAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Scence scence = new Scence();
			JSONObject jb = JSONObject.fromObject(ajaxData);
			scence = (Scence) JSONObject.toBean(jb, Scence.class);
			if (BizPubParm.USE_FLAG_1.equals(scence.getUseFlag())) {
				FormData formparam0002 = formService.getFormData("param0002");
				getFormValue(formparam0002);
				Scence scences = new Scence();
				scences.setScNo(scence.getScNo());// 获取产品号
				scences = scenceFeign.getById(scences); // 获取产品
				getObjValue(formparam0002, scences);
				boolean pass = this.validateFormData(formparam0002);
				if (pass) {
					scenceFeign.updateUseFlag(scence);
					dataMap.put("flag", "success");
					dataMap.put("msg", "更新成功");
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			} else {
				scenceFeign.updateUseFlag(scence);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String scNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formparam0002 = formService.getFormData("param0002");
		Scence scence = new Scence();
		scence.setScNo(scNo);
		scence = scenceFeign.getById(scence);
		getObjValue(formparam0002, scence, formData);
		if (scence != null) {
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
	public Map<String, Object> deleteAjax(String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Scence scence = new Scence();
		scence.setScNo(scNo);
		try {
			scence = scenceFeign.getById(scence);
			if (BizPubParm.USE_FLAG_1.equals(scence.getUseFlag())) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "该业务场景已被使用,不能删除!");
			} else {
				scenceFeign.delete(scence);
				dataMap.put("flag", "success");
				dataMap.put("msg", "成功");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0002 = formService.getFormData("param0002");
		model.addAttribute("formparam0002", formparam0002);
		model.addAttribute("query", "");
		return "/component/param/Scence_Insert";
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
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		Scence scence = new Scence();
		setObjValue(formparam0002, scence);
		scenceFeign.insert(scence);
		getObjValue(formparam0002, scence);
		this.addActionMessage(model, "保存成功");
		List<Scence> scenceList = (List<Scence>) scenceFeign.findByPage(this.getIpage(), scence).getResult();
		model.addAttribute("scenceList", scenceList);
		model.addAttribute("formparam0002", formparam0002);
		model.addAttribute("query", "");
		return "/component/param/Scence_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String scNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		Scence scence = new Scence();
		scence.setScNo(scNo);
		scence = scenceFeign.getById(scence);
		getObjValue(formparam0002, scence);
		model.addAttribute("scence", scence);
		model.addAttribute("formparam0002", formparam0002);
		model.addAttribute("query", "");
		return "/component/param/Scence_Detail";
	}

	/**
	 * 获取修改页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUpdatePage")
	public String getUpdatePage(Model model, String scNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		Scence scence = new Scence();
		scence.setScNo(scNo);
		scence = scenceFeign.getById(scence);
		getObjValue(formparam0002, scence);
		model.addAttribute("scence", scence);
		model.addAttribute("formparam0002", formparam0002);
		model.addAttribute("query", "");
		return "/component/param/Scence_Update";
	}

	@RequestMapping(value = "/update")
	public String update(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		Scence scence = new Scence();
		setObjValue(formparam0002, scence);
		scenceFeign.update(scence);
		getObjValue(formparam0002, scence);
		model.addAttribute("scence", scence);
		model.addAttribute("formparam0002", formparam0002);
		model.addAttribute("query", "");
		return "/component/param/Scence_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String scNo) throws Exception {
		ActionContext.initialize(request, response);
		Scence scence = new Scence();
		scence.setScNo(scNo);
		scenceFeign.delete(scence);
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
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		boolean validateFlag = this.validateFormData(formparam0002);
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
		FormData formparam0002 = formService.getFormData("param0002");
		getFormValue(formparam0002);
		boolean validateFlag = this.validateFormData(formparam0002);
	}

}
