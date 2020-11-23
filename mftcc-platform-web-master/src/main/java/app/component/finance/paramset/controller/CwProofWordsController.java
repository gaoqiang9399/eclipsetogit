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
import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.paramset.feign.CwProofWordsFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: CwProofWordsAction.java Description:凭证字管理action
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 26 16:14:31 CST 2016
 **/
@Controller
@RequestMapping("/cwProofWords")
public class CwProofWordsController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwProofWordsFeign cwProofWordsFeign;
	// 全局变量
	private String query;
	private FormService formService = new FormService();

	public CwProofWordsController() {
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
		try {
			ActionContext.initialize(request, response);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("getListPage方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}

		return "/component/finance/paramset/CwProofWords_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwProofWords cwProofWords = new CwProofWords();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwProofWords.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwProofWords.setCriteriaList(cwProofWords, ajaxData);// 我的筛选
			// this.getRoleConditions(cwProofWords,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwProofWords", cwProofWords);
			ipage.setParams(params);
			ipage = cwProofWordsFeign.findByPage(ipage,finBooks);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			System.out.println(tableHtml);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("findByPageAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formproofset0002 = formService.getFormData("proofset0002");
			getFormValue(formproofset0002, getMapByJson(ajaxData));
			if (this.validateFormData(formproofset0002)) {
				CwProofWords cwProofWords = new CwProofWords();
				setObjValue(formproofset0002, cwProofWords);
				cwProofWordsFeign.insert(cwProofWords,finBooks);// 新增凭证字
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("insertAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
		FormData formproofset0002 = formService.getFormData("proofset0002");
		getFormValue(formproofset0002, getMapByJson(ajaxData));
		CwProofWords cwProofWordsJsp = new CwProofWords();
		setObjValue(formproofset0002, cwProofWordsJsp);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwProofWords cwProofWords = cwProofWordsFeign.getById(cwProofWordsJsp,finBooks);
		if (cwProofWords != null) {
			try {
				cwProofWords = (CwProofWords) EntityUtil.reflectionSetVal(cwProofWords, cwProofWordsJsp,
						getMapByJson(ajaxData));
				cwProofWordsFeign.update(cwProofWords,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
//				logger.error("updateAjaxByOne方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formproofset0001 = formService.getFormData("proofset0001");
			getFormValue(formproofset0001, getMapByJson(ajaxData));
			if (this.validateFormData(formproofset0001)) {
				CwProofWords cwProofWords = new CwProofWords();
				setObjValue(formproofset0001, cwProofWords);
				cwProofWordsFeign.update(cwProofWords,finBooks);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("updateAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
	public Map<String, Object> getByIdAjax(String uid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formproofset0002 = formService.getFormData("proofset0002");
			CwProofWords cwProofWords = new CwProofWords();
			cwProofWords.setUid(uid);
			cwProofWords = cwProofWordsFeign.getById(cwProofWords,finBooks);
			getObjValue(formproofset0002, cwProofWords, formData);
			if (cwProofWords != null) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
			}
			dataMap.put("formData", formData);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("getByIdAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String uid) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwProofWords cwProofWords = new CwProofWords();
		cwProofWords.setUid(uid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwProofWordsFeign.delete(cwProofWords,finBooks);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("deleteAjax方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
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
		try {
			FormData formproofset0002 = formService.getFormData("proofset0002");
			model.addAttribute("formproofset0002", formproofset0002);
			model.addAttribute("query", query);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("input方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}

		return "/component/finance/paramset/CwProofWords_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formproofset0002 = formService.getFormData("proofset0002");
			getFormValue(formproofset0002);
			CwProofWords cwProofWords = new CwProofWords();
			setObjValue(formproofset0002, cwProofWords);
			cwProofWordsFeign.insert(cwProofWords,finBooks);
			getObjValue(formproofset0002, cwProofWords);
//			this.addActionMessage("保存成功");
			Ipage ipage = this.getIpage();
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwProofWords", cwProofWords);
			ipage.setParams(params);
			List<CwProofWords> cwProofWordsList = (List<CwProofWords>) cwProofWordsFeign.findByPage(ipage,finBooks)
					.getResult();
			model.addAttribute("cwProofWords", cwProofWords);
			model.addAttribute("cwProofWordsList", cwProofWordsList);
			model.addAttribute("formproofset0002", formproofset0002);
			model.addAttribute("query", query);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return "/component/finance/paramset/CwProofWords_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uid) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			FormData formproofset0001 = formService.getFormData("proofset0001");
			getFormValue(formproofset0001);
			CwProofWords cwProofWords = new CwProofWords();
			cwProofWords.setUid(uid);
			cwProofWords = cwProofWordsFeign.getById(cwProofWords,finBooks);
			getObjValue(formproofset0001, cwProofWords);
			model.addAttribute("cwProofWords", cwProofWords);
			model.addAttribute("formproofset0001", formproofset0001);
			model.addAttribute("query", query);
			return "/component/finance/paramset/CwProofWords_Detail";
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("getById方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String uid) throws Exception {
		ActionContext.initialize(request, response);
		CwProofWords cwProofWords = new CwProofWords();
		cwProofWords.setUid(uid);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwProofWordsFeign.delete(cwProofWords,finBooks);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("delete方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
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
		try {
			FormData formproofset0002 = formService.getFormData("proofset0002");
			getFormValue(formproofset0002);
			boolean validateFlag = this.validateFormData(formproofset0002);
			model.addAttribute("formproofset0002", formproofset0002);
			model.addAttribute("query", query);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("validateInsert方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
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
		try {
			FormData formproofset0002 = formService.getFormData("proofset0002");
			getFormValue(formproofset0002);
			boolean validateFlag = this.validateFormData(formproofset0002);
			model.addAttribute("formproofset0002", formproofset0002);
			model.addAttribute("query", query);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("validateUpdate方法出错，执行action层失败，抛出异常" + e.getMessage(), e);
			throw e;
		}
	}

}
