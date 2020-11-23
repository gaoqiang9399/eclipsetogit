package app.component.finance.paramset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.paramset.entity.CwVchRuleMstPlate;
import app.component.finance.paramset.feign.CwVchRuleMstPlateFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwVchRuleMstPlateAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 07 14:23:55 CST 2017
 **/
@Controller
@RequestMapping("/cwVchRuleMstPlate")
public class CwVchRuleMstPlateController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchRuleMstPlateFeign cwVchRuleMstPlateFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwVchRuleMstPlateController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/paramset/CwVchRuleMstPlate_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		try {
			cwVchRuleMstPlate.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchRuleMstPlate.setCriteriaList(cwVchRuleMstPlate, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVchRuleMstPlate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchRuleMstPlate", cwVchRuleMstPlate);
			ipage.setParams(params);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwVchRuleMstPlateFeign.findByPage(ipage,finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
			getFormValue(formcwvchrulemstplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchrulemstplate0002)) {
				CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
				setObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlate);
				cwVchRuleMstPlateFeign.insert(cwVchRuleMstPlate);
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		getFormValue(formcwvchrulemstplate0002, getMapByJson(ajaxData));
		CwVchRuleMstPlate cwVchRuleMstPlateJsp = new CwVchRuleMstPlate();
		setObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlateJsp);
		CwVchRuleMstPlate cwVchRuleMstPlate = cwVchRuleMstPlateFeign.getById(cwVchRuleMstPlateJsp);
		if (cwVchRuleMstPlate != null) {
			try {
				cwVchRuleMstPlate = (CwVchRuleMstPlate) EntityUtil.reflectionSetVal(cwVchRuleMstPlate,
						cwVchRuleMstPlateJsp, getMapByJson(ajaxData));
				cwVchRuleMstPlateFeign.update(cwVchRuleMstPlate);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
			getFormValue(formcwvchrulemstplate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwvchrulemstplate0002)) {
				CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
				setObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlate);
				cwVchRuleMstPlateFeign.update(cwVchRuleMstPlate);
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
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		cwVchRuleMstPlate.setUuid(uuid);
		cwVchRuleMstPlate = cwVchRuleMstPlateFeign.getById(cwVchRuleMstPlate);
		getObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlate, formData);
		if (cwVchRuleMstPlate != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		cwVchRuleMstPlate.setUuid(uuid);
		try {
			cwVchRuleMstPlateFeign.delete(cwVchRuleMstPlate);
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
		ActionContext.initialize(request, response);
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		model.addAttribute("formcwvchrulemstplate0002", formcwvchrulemstplate0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMstPlate_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		getFormValue(formcwvchrulemstplate0002);
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		setObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlate);
		cwVchRuleMstPlateFeign.insert(cwVchRuleMstPlate);
		getObjValue(formcwvchrulemstplate0002, cwVchRuleMstPlate);
		// this.addActionMessage("保存成功");
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		Map<String, Object> params = new HashMap<>();
		params.put("cwVchRuleMstPlate", cwVchRuleMstPlate);
		ipage.setParams(params);
		List<CwVchRuleMstPlate> cwVchRuleMstPlateList = (List<CwVchRuleMstPlate>) cwVchRuleMstPlateFeign
				.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwVchRuleMstPlate", cwVchRuleMstPlate);
		model.addAttribute("cwVchRuleMstPlateList", cwVchRuleMstPlateList);
		model.addAttribute("formcwvchrulemstplate0002", formcwvchrulemstplate0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMstPlate_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uuid) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemstplate0001 = formService.getFormData("cwvchrulemstplate0001");
		getFormValue(formcwvchrulemstplate0001);
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		cwVchRuleMstPlate.setUuid(uuid);
		cwVchRuleMstPlate = cwVchRuleMstPlateFeign.getById(cwVchRuleMstPlate);
		getObjValue(formcwvchrulemstplate0001, cwVchRuleMstPlate);
		model.addAttribute("cwVchRuleMstPlate", cwVchRuleMstPlate);
		model.addAttribute("formcwvchrulemstplate0001", formcwvchrulemstplate0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwVchRuleMstPlate_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String uuid) throws Exception {
		ActionContext.initialize(request, response);
		CwVchRuleMstPlate cwVchRuleMstPlate = new CwVchRuleMstPlate();
		cwVchRuleMstPlate.setUuid(uuid);
		cwVchRuleMstPlateFeign.delete(cwVchRuleMstPlate);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		getFormValue(formcwvchrulemstplate0002);
		boolean validateFlag = this.validateFormData(formcwvchrulemstplate0002);
		model.addAttribute("formcwvchrulemstplate0002", formcwvchrulemstplate0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formcwvchrulemstplate0002 = formService.getFormData("cwvchrulemstplate0002");
		getFormValue(formcwvchrulemstplate0002);
		boolean validateFlag = this.validateFormData(formcwvchrulemstplate0002);
		model.addAttribute("formcwvchrulemstplate0002", formcwvchrulemstplate0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 跳转到业务记账详情页面中
	 * 
	 * @return String
	 * @author lzshuai
	 * @date 2017-3-7 下午7:31:51
	 */
	@RequestMapping(value = "/getVchDetailPage")
	public String getVchDetailPage() {
		return "/component/finance/paramset/CwVchRuleMstPlate_getVchDetailPage";
	}

}
