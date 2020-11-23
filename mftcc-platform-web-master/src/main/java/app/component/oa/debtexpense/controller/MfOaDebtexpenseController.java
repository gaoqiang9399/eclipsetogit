package app.component.oa.debtexpense.controller;

import java.math.BigDecimal;
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
import app.component.oa.debt.feign.MfOaDebtFeign;
import app.component.oa.debtexpense.entity.MfOaDebtexpense;
import app.component.oa.debtexpense.feign.MfOaDebtexpenseFeign;
import app.component.oa.expense.feign.MfOaExpenseFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfOaDebtexpenseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jan 22 10:12:02 CST 2017
 **/
@Controller
@RequestMapping("/mfOaDebtexpense")
public class MfOaDebtexpenseController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaDebtexpenseFeign mfOaDebtexpenseFeign;
	@Autowired
	private MfOaExpenseFeign mfOaExpenseFeign;
	@Autowired
	private MfOaDebtFeign mfOaDebtFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String opNo = User.getRegNo(request);
		BigDecimal sumAmt[] = mfOaDebtFeign.sumAmt(opNo);
		BigDecimal sumExpenseAmt = mfOaExpenseFeign.getExpenseSumAmt(opNo).get("expenseAmt");
		BigDecimal sumApplyAmt = sumAmt[0];
		BigDecimal sumReturnAmt = sumExpenseAmt.subtract(sumApplyAmt);
		model.addAttribute("sumApplyAmt", sumApplyAmt);
		model.addAttribute("sumExpenseAmt", sumExpenseAmt);
		model.addAttribute("sumReturnAmt", sumReturnAmt);
		model.addAttribute("opNo", opNo);
		return "/component/oa/debtexpense/debtexpense";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
			mfOaDebtexpense.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaDebtexpense.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfOaDebtexpense.setCriteriaList(mfOaDebtexpense, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaDebtexpense,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Bo方法
			mfOaDebtexpense.setOpNo(User.getRegNo(request));
			ipage.setParams(this.setIpageParams("mfOaDebtexpense", mfOaDebtexpense));
			ipage = mfOaDebtexpenseFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
			getFormValue(formdebtexpense0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdebtexpense0002)) {
				MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
				setObjValue(formdebtexpense0002, mfOaDebtexpense);
				mfOaDebtexpenseFeign.insert(mfOaDebtexpense);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		getFormValue(formdebtexpense0002, getMapByJson(ajaxData));
		MfOaDebtexpense mfOaDebtexpenseJsp = new MfOaDebtexpense();
		setObjValue(formdebtexpense0002, mfOaDebtexpenseJsp);
		MfOaDebtexpense mfOaDebtexpense = mfOaDebtexpenseFeign.getById(mfOaDebtexpenseJsp);
		if (mfOaDebtexpense != null) {
			try {
				mfOaDebtexpense = (MfOaDebtexpense) EntityUtil.reflectionSetVal(mfOaDebtexpense, mfOaDebtexpenseJsp,
						getMapByJson(ajaxData));
				mfOaDebtexpenseFeign.update(mfOaDebtexpense);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
			FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
			getFormValue(formdebtexpense0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdebtexpense0002)) {
				mfOaDebtexpense = new MfOaDebtexpense();
				setObjValue(formdebtexpense0002, mfOaDebtexpense);
				mfOaDebtexpenseFeign.update(mfOaDebtexpense);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String debtexpenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
		mfOaDebtexpense.setDebtexpenseId(debtexpenseId);
		mfOaDebtexpense = mfOaDebtexpenseFeign.getById(mfOaDebtexpense);
		getObjValue(formdebtexpense0002, mfOaDebtexpense, formData);
		if (mfOaDebtexpense != null) {
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
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String debtexpenseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
			mfOaDebtexpense.setDebtexpenseId(debtexpenseId);
			mfOaDebtexpenseFeign.delete(mfOaDebtexpense);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		model.addAttribute("formdebtexpense0002", formdebtexpense0002);
		model.addAttribute("query", "");
		return "/component/oa/debtexpense/MfOaDebtexpense_Insert";
	}

	/***
	 * 新增
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		getFormValue(formdebtexpense0002);
		MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
		setObjValue(formdebtexpense0002, mfOaDebtexpense);
		mfOaDebtexpenseFeign.insert(mfOaDebtexpense);
		getObjValue(formdebtexpense0002, mfOaDebtexpense);
		this.addActionMessage(model, "保存成功");
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfOaDebtexpense", mfOaDebtexpense));
		List<MfOaDebtexpense> mfOaDebtexpenseList = (List<MfOaDebtexpense>) mfOaDebtexpenseFeign.findByPage(ipage)
				.getResult();
		model.addAttribute("formdebtexpense0002", formdebtexpense0002);
		model.addAttribute("MfOaDebtexpense", mfOaDebtexpense);
		model.addAttribute("mfOaDebtexpenseList", mfOaDebtexpenseList);
		model.addAttribute("query", "");
		return "/component/oa/debtexpense/MfOaDebtexpense_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String debtexpenseId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebtexpense0001 = new FormService().getFormData("debtexpense0001");
		getFormValue(formdebtexpense0001);
		MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
		mfOaDebtexpense.setDebtexpenseId(debtexpenseId);
		mfOaDebtexpense = mfOaDebtexpenseFeign.getById(mfOaDebtexpense);
		getObjValue(formdebtexpense0001, mfOaDebtexpense);
		model.addAttribute("formdebtexpense0001", formdebtexpense0001);
		model.addAttribute("mfOaDebtexpense", mfOaDebtexpense);
		model.addAttribute("query", "");
		return "/component/oa/debtexpense/MfOaDebtexpense_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String debtexpenseId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaDebtexpense mfOaDebtexpense = new MfOaDebtexpense();
		mfOaDebtexpense.setDebtexpenseId(debtexpenseId);
		mfOaDebtexpenseFeign.delete(mfOaDebtexpense);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		getFormValue(formdebtexpense0002);
		boolean validateFlag = this.validateFormData(formdebtexpense0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formdebtexpense0002 = new FormService().getFormData("debtexpense0002");
		getFormValue(formdebtexpense0002);
		boolean validateFlag = this.validateFormData(formdebtexpense0002);
	}

}
