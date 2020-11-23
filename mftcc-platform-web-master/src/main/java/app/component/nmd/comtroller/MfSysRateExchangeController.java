package app.component.nmd.comtroller;

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
import app.component.nmd.entity.MfSysRateExchange;
import app.component.nmd.feign.MfSysRateExchangeFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysRateExchangeAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 20:50:06 CST 2017
 **/
@Controller
@RequestMapping("/mfSysRateExchange")
public class MfSysRateExchangeController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysRateExchangeBo
	@Autowired
	private MfSysRateExchangeFeign mfSysRateExchangeFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateExchange_List";
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
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		try {
			mfSysRateExchange.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysRateExchange.setCriteriaList(mfSysRateExchange, ajaxData);// 我的筛选
			// this.getRoleConditions(mfSysRateExchange,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysRateExchange",mfSysRateExchange));
			ipage = mfSysRateExchangeFeign.findByPage(ipage);
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
			FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
			getFormValue(formexchangerate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formexchangerate0002)) {
				MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
				// 设置操作人员编号
				String userName = User.getRegNo(request);
				mfSysRateExchange.setEdituser(userName);
				setObjValue(formexchangerate0002, mfSysRateExchange);

				// 去重的操作--->判断当前输入的是否已经存在,如果已经存在则返回
				if (mfSysRateExchangeFeign.isInstance(mfSysRateExchange)) {
					dataMap.put("flag", "error");
					dataMap.put("msg", "该汇率已经存在");
					return dataMap;
				}
				mfSysRateExchangeFeign.insert(mfSysRateExchange);
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
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		getFormValue(formexchangerate0002, getMapByJson(ajaxData));
		MfSysRateExchange mfSysRateExchangeJsp = new MfSysRateExchange();
		setObjValue(formexchangerate0002, mfSysRateExchangeJsp);
		MfSysRateExchange mfSysRateExchange = mfSysRateExchangeFeign.getById(mfSysRateExchangeJsp);
		if (mfSysRateExchange != null) {
			try {
				mfSysRateExchange = (MfSysRateExchange) EntityUtil.reflectionSetVal(mfSysRateExchange,
						mfSysRateExchangeJsp, getMapByJson(ajaxData));
				mfSysRateExchangeFeign.update(mfSysRateExchange);
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
			MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
			FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
			getFormValue(formexchangerate0002, getMapByJson(ajaxData));
			if (this.validateFormData(formexchangerate0002)) {
				setObjValue(formexchangerate0002, mfSysRateExchange);
				// 设置操作人员编号
				String userName = (String) request.getSession().getAttribute("regNo");
				mfSysRateExchange.setEdituser(userName);
				mfSysRateExchangeFeign.update(mfSysRateExchange);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String rateno) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		mfSysRateExchange.setRateno(rateno);
		mfSysRateExchange = mfSysRateExchangeFeign.getById(mfSysRateExchange);
		getObjValue(formexchangerate0002, mfSysRateExchange, formData);
		if (mfSysRateExchange != null) {
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
	public Map<String, Object> deleteAjax(String rateno) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		mfSysRateExchange.setRateno(rateno);
		try {
			mfSysRateExchangeFeign.delete(mfSysRateExchange);
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
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		model.addAttribute("formexchangerate0002", formexchangerate0002);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateExchange_Insert";
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
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		getFormValue(formexchangerate0002);
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		setObjValue(formexchangerate0002, mfSysRateExchange);
		mfSysRateExchangeFeign.insert(mfSysRateExchange);
		getObjValue(formexchangerate0002, mfSysRateExchange);
		this.addActionMessage(model, "保存成功");
		this.getIpage().setParams(this.setIpageParams("mfSysRateExchange",mfSysRateExchange));
		List<MfSysRateExchange> mfSysRateExchangeList = (List<MfSysRateExchange>) mfSysRateExchangeFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("formexchangerate0002", formexchangerate0002);
		model.addAttribute("mfSysRateExchange", mfSysRateExchange);
		model.addAttribute("mfSysRateExchangeList", mfSysRateExchangeList);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateExchange_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById( String rateno,Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formexchangerate0001 = formService.getFormData("exchangerate0001");
		getFormValue(formexchangerate0001);
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		mfSysRateExchange.setRateno(rateno);
		mfSysRateExchange = mfSysRateExchangeFeign.getById(mfSysRateExchange);
		getObjValue(formexchangerate0001, mfSysRateExchange);
		model.addAttribute("formexchangerate0001", formexchangerate0001);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateExchange_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String rateno,Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfSysRateExchange mfSysRateExchange = new MfSysRateExchange();
		mfSysRateExchange.setRateno(rateno);
		mfSysRateExchangeFeign.delete(mfSysRateExchange);
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
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		getFormValue(formexchangerate0002);
		boolean validateFlag = this.validateFormData(formexchangerate0002);
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
		FormData formexchangerate0002 = formService.getFormData("exchangerate0002");
		getFormValue(formexchangerate0002);
		boolean validateFlag = this.validateFormData(formexchangerate0002);
	}

}
