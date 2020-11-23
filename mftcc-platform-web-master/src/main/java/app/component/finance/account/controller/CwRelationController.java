package app.component.finance.account.controller;

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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.account.feign.CwRelationFeign;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

/**
 * Title: CwRelationAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jan 24 09:42:23 CST 2017
 **/
@Controller
@RequestMapping("/cwRelation")
public class CwRelationController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CwFicationDataFeign
	@Autowired
	private CwRelationFeign cwRelationFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formcwrelation0002;
	private FormService formService = new FormService();

	public CwRelationController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String txType) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("txType", txType);
		return "/component/finance/account/CwRelation_List";
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
			String ajaxData, String txType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwRelation cwRelation = new CwRelation();
		try {
			cwRelation.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwRelation.setCriteriaList(cwRelation, ajaxData);// 我的筛选
			// this.getRoleConditions(cwRelation,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwRelation", cwRelation);
			ipage.setParams(params);
			// 自定义查询Bo方法
			cwRelation.setTxType(txType);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			ipage = cwRelationFeign.findByPage(ipage,finBooks);
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
	 * 方法描述： 获取所有科目下挂有的辅助核算(凭证使用)
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-1 下午2:03:17
	 */
	@RequestMapping(value = "/getRelaForVchAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRelaForVchAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap=null;
			if(StringUtil.isEmpty(ajaxData)) {
				paramMap=new HashMap<String, String>();
				
			}else {
				paramMap = CwPublicUtil.getMapByJson(ajaxData);
				if(paramMap==null) {
					paramMap=new HashMap<String, String>();
				}
			}
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			dataMap.putAll(cwRelationFeign.getRelaForVchData(paramMap,finBooks));
			dataMap.put("flag", "success");
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
			formcwrelation0002 = formService.getFormData("cwrelation0002");
			getFormValue(formcwrelation0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwrelation0002)) {
				CwRelation cwRelation = new CwRelation();
				setObjValue(formcwrelation0002, cwRelation);
				cwRelation.setOpNo(User.getRegNo(request));
				cwRelation.setOpName(User.getRegName(request));
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwRelationFeign.insert(cwRelation,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
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
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		getFormValue(formcwrelation0002, getMapByJson(ajaxData));
		CwRelation cwRelationJsp = new CwRelation();
		setObjValue(formcwrelation0002, cwRelationJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwRelation cwRelation = cwRelationFeign.getById(cwRelationJsp,finBooks);
		if (cwRelation != null) {
			try {
				cwRelation = (CwRelation) EntityUtil.reflectionSetVal(cwRelation, cwRelationJsp,
						getMapByJson(ajaxData));
				R r = cwRelationFeign.update(cwRelation,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				e.printStackTrace();
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
			formcwrelation0002 = formService.getFormData("cwrelation0002");
			getFormValue(formcwrelation0002, getMapByJson(ajaxData));
			if (this.validateFormData(formcwrelation0002)) {
				CwRelation cwRelation = new CwRelation();
				setObjValue(formcwrelation0002, cwRelation);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				R r = cwRelationFeign.update(cwRelation,finBooks);
				if(r.isOk()) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
				}else {
					dataMap.put("flag", "error");
					dataMap.put("msg", r.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			e.printStackTrace();
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
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		CwRelation cwRelation = new CwRelation();
		cwRelation.setUid(uid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwRelation = cwRelationFeign.getById(cwRelation,finBooks);
		getObjValue(formcwrelation0002, cwRelation, formData);
		if (cwRelation != null) {
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
	public Map<String, Object> deleteAjax(String uid, String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwRelation cwRelation = new CwRelation();
		cwRelation.setUid(uid);
		cwRelation.setAccHrt(accHrt);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwRelationFeign.delete(cwRelation,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			e.printStackTrace();
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
	public String input(Model model, String txType) throws Exception {
		ActionContext.initialize(request, response);
		CwRelation cwRelationbean = new CwRelation();
		cwRelationbean.setTxType(txType);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwRelation rleationbeanNew = cwRelationFeign.getCwFicationBean(cwRelationbean,finBooks);
		cwRelationbean.setTxName(rleationbeanNew.getTxName());
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		getObjValue(formcwrelation0002, cwRelationbean);
		model.addAttribute("formcwrelation0002", formcwrelation0002);
		model.addAttribute("query", query);
		return "/component/finance/account/CwRelation_Insert";

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
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		getFormValue(formcwrelation0002);
		CwRelation cwRelation = new CwRelation();
		setObjValue(formcwrelation0002, cwRelation);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwRelationFeign.insert(cwRelation,finBooks);
		getObjValue(formcwrelation0002, cwRelation);
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwRelation", cwRelation);
		ipage.setParams(params);
		List<CwRelation> cwRelationList = (List<CwRelation>) cwRelationFeign.findByPage(ipage,finBooks)
				.getResult();
		model.addAttribute("cwRelation", cwRelation);
		model.addAttribute("formcwrelation0002", formcwrelation0002);
		model.addAttribute("cwRelationList", cwRelationList);
		model.addAttribute("query", query);
		return "/component/finance/account/CwRelation_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String uid, String txType) throws Exception {
		ActionContext.initialize(request, response);
		formcwrelation0002 = formService.getFormData("cwrelation0001");
		getFormValue(formcwrelation0002);
		CwRelation cwRelation = new CwRelation();
		cwRelation.setUid(uid);
		cwRelation.setTxType(txType);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwRelation = cwRelationFeign.getById(cwRelation,finBooks);
		getObjValue(formcwrelation0002, cwRelation);
		model.addAttribute("cwRelation", cwRelation);
		model.addAttribute("formcwrelation0002", formcwrelation0002);
		model.addAttribute("query", query);
		return "/component/finance/account/CwRelation_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String txType, String uid) throws Exception {
		ActionContext.initialize(request, response);
		CwRelation cwRelation = new CwRelation();
		cwRelation.setUid(uid);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwRelationFeign.delete(cwRelation,finBooks);
		return getListPage(model, txType);
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
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		getFormValue(formcwrelation0002);
		boolean validateFlag = this.validateFormData(formcwrelation0002);
		model.addAttribute("formcwrelation0002", formcwrelation0002);
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
		formcwrelation0002 = formService.getFormData("cwrelation0002");
		getFormValue(formcwrelation0002);
		boolean validateFlag = this.validateFormData(formcwrelation0002);
		model.addAttribute("formcwrelation0002", formcwrelation0002);
		model.addAttribute("query", query);
	}

}
