package app.component.message.controller;

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
import app.component.message.entity.MfInnerMessageTemplete;
import app.component.message.feign.MfInnerMessageTempleteFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfInnerMessageTempleteAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 30 11:05:19 CST 2018
 **/
@Controller
@RequestMapping("/mfInnerMessageTemplete")
public class MfInnerMessageTempleteController extends BaseFormBean {
	// 注入MfInnerMessageTempleteBo
	@Autowired
	private MfInnerMessageTempleteFeign mfInnerMessageTempleteFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	private FormService formService = new FormService();

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfInnerMessageTemplete_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId ,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
		try {
			mfInnerMessageTemplete.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfInnerMessageTemplete.setCriteriaList(mfInnerMessageTemplete, ajaxData);// 我的筛选
			// mfInnerMessageTemplete.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfInnerMessageTemplete,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfInnerMessageTempleteFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmessage0002 = formService.getFormData("message0002");
			getFormValue(formmessage0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmessage0002)) {
				MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
				setObjValue(formmessage0002, mfInnerMessageTemplete);
				mfInnerMessageTempleteFeign.insert(mfInnerMessageTemplete);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmessage0002 = formService.getFormData("message0002");
		getFormValue(formmessage0002, getMapByJson(ajaxData));
		MfInnerMessageTemplete mfInnerMessageTempleteJsp = new MfInnerMessageTemplete();
		setObjValue(formmessage0002, mfInnerMessageTempleteJsp);
		MfInnerMessageTemplete mfInnerMessageTemplete = mfInnerMessageTempleteFeign.getById(mfInnerMessageTempleteJsp);
		if (mfInnerMessageTemplete != null) {
			try {
				mfInnerMessageTemplete = (MfInnerMessageTemplete) EntityUtil.reflectionSetVal(mfInnerMessageTemplete,
						mfInnerMessageTempleteJsp, getMapByJson(ajaxData));
				mfInnerMessageTempleteFeign.update(mfInnerMessageTemplete);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
		try {
			FormData formmessage0002 = formService.getFormData("message0002");
			getFormValue(formmessage0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmessage0002)) {
				mfInnerMessageTemplete = new MfInnerMessageTemplete();
				setObjValue(formmessage0002, mfInnerMessageTemplete);
				mfInnerMessageTempleteFeign.update(mfInnerMessageTemplete);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmessage0002 = formService.getFormData("message0002");
		MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
		mfInnerMessageTemplete.setId(id);
		mfInnerMessageTemplete = mfInnerMessageTempleteFeign.getById(mfInnerMessageTemplete);
		getObjValue(formmessage0002, mfInnerMessageTemplete, formData);
		if (mfInnerMessageTemplete != null) {
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
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
		mfInnerMessageTemplete.setId(id);
		try {
			mfInnerMessageTempleteFeign.delete(mfInnerMessageTemplete);
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
		FormData formmessage0002 = formService.getFormData("message0002");
		model.addAttribute("formmessage0002", formmessage0002);
		return "MfInnerMessageTemplete_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormData formmessage0001 = formService.getFormData("message0001");
		getFormValue(formmessage0001);
		MfInnerMessageTemplete mfInnerMessageTemplete = new MfInnerMessageTemplete();
		mfInnerMessageTemplete.setId(id);
		mfInnerMessageTemplete = mfInnerMessageTempleteFeign.getById(mfInnerMessageTemplete);
		getObjValue(formmessage0001, mfInnerMessageTemplete);
		model.addAttribute("formmessage0001", formmessage0001);
		return "MfInnerMessageTemplete_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formmessage0002 = formService.getFormData("message0002");
		getFormValue(formmessage0002);
		boolean validateFlag = this.validateFormData(formmessage0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formmessage0002 = formService.getFormData("message0002");
		getFormValue(formmessage0002);
		boolean validateFlag = this.validateFormData(formmessage0002);
	}

}
