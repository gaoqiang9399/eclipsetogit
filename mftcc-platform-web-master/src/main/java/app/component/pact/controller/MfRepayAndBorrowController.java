package app.component.pact.controller;

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

import app.component.common.EntityUtil;
import app.component.pact.entity.MfRepayAndBorrow;
import app.component.pact.feign.MfRepayAndBorrowFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfRepayAndBorrowAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jul 13 09:59:18 CST 2018
 **/
@Controller
@RequestMapping("/mfRepayAndBorrow")
public class MfRepayAndBorrowController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入mfRepayAndBorrowFeign
	@Autowired
	private MfRepayAndBorrowFeign mfRepayAndBorrowFeign;

	private FormService formService = new FormService();

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "MfRepayAndBorrow_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,
			Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
		try {
			mfRepayAndBorrow.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfRepayAndBorrow.setCriteriaList(mfRepayAndBorrow, ajaxData);// 我的筛选
			// mfRepayAndBorrow.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfRepayAndBorrow,"1000000001");//记录级权限控制方法
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfRepayAndBorrow", mfRepayAndBorrow));
			ipage = mfRepayAndBorrowFeign.findByPage(ipage);
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
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpact0002 = formService.getFormData("pact0002");
			getFormValue(formpact0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpact0002)) {
				MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
				setObjValue(formpact0002, mfRepayAndBorrow);
				mfRepayAndBorrowFeign.insert(mfRepayAndBorrow);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpact0002 = formService.getFormData("pact0002");
		getFormValue(formpact0002, getMapByJson(ajaxData));
		MfRepayAndBorrow mfRepayAndBorrowJsp = new MfRepayAndBorrow();
		setObjValue(formpact0002, mfRepayAndBorrowJsp);
		MfRepayAndBorrow mfRepayAndBorrow = mfRepayAndBorrowFeign.getById(mfRepayAndBorrowJsp);
		if (mfRepayAndBorrow != null) {
			try {
				mfRepayAndBorrow = (MfRepayAndBorrow) EntityUtil.reflectionSetVal(mfRepayAndBorrow, mfRepayAndBorrowJsp,
						getMapByJson(ajaxData));
				mfRepayAndBorrowFeign.update(mfRepayAndBorrow);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
		try {
			FormData formpact0002 = formService.getFormData("pact0002");
			getFormValue(formpact0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpact0002)) {
				mfRepayAndBorrow = new MfRepayAndBorrow();
				setObjValue(formpact0002, mfRepayAndBorrow);
				mfRepayAndBorrowFeign.update(mfRepayAndBorrow);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpact0002 = formService.getFormData("pact0002");
		MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
		mfRepayAndBorrow.setId(id);
		mfRepayAndBorrow = mfRepayAndBorrowFeign.getById(mfRepayAndBorrow);
		getObjValue(formpact0002, mfRepayAndBorrow, formData);
		if (mfRepayAndBorrow != null) {
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
		mfRepayAndBorrow.setId(id);
		try {
			mfRepayAndBorrowFeign.delete(mfRepayAndBorrow);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
		ActionContext.initialize(request, response);
		FormData formpact0002 = formService.getFormData("pact0002");
		model.addAttribute("formpact0002", formpact0002);
		return "MfRepayAndBorrow_Insert";
	}

	/**
	 * 查询
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		ActionContext.initialize(request, response);
		FormData formpact0001 = formService.getFormData("pact0001");
		getFormValue(formpact0001);
		MfRepayAndBorrow mfRepayAndBorrow = new MfRepayAndBorrow();
		mfRepayAndBorrow.setId(id);
		mfRepayAndBorrow = mfRepayAndBorrowFeign.getById(mfRepayAndBorrow);
		getObjValue(formpact0001, mfRepayAndBorrow);
		model.addAttribute("formpact0001", formpact0001);
		return "MfRepayAndBorrow_Detail";
	}

}
