package app.component.interfaces.mobileinterface.controller;

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
import app.component.interfaces.mobileinterface.entity.MfAccessInfo;
import app.component.interfaces.mobileinterface.feign.MfAccessInfoFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfAccessInfoAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Oct 14 15:33:56 CST 2017
 **/
@Controller
@RequestMapping("/mfAccessInfo")
public class MfAccessInfoController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfAccessInfoBo
	@Autowired
	private MfAccessInfoFeign mfAccessInfoFeign;

	// 全局变量
	// 异步参数
	// 表单变量
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "MfAccessInfo_List";
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
		MfAccessInfo mfAccessInfo = new MfAccessInfo();
		try {
			mfAccessInfo.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAccessInfo.setCriteriaList(mfAccessInfo, ajaxData);// 我的筛选
			// mfAccessInfo.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfAccessInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfAccessInfoFeign.findByPage(ipage, mfAccessInfo);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
			getFormValue(formmobileinterface0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmobileinterface0002)) {
				MfAccessInfo mfAccessInfo = new MfAccessInfo();
				setObjValue(formmobileinterface0002, mfAccessInfo);
				mfAccessInfoFeign.insert(mfAccessInfo);
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
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
		getFormValue(formmobileinterface0002, getMapByJson(ajaxData));
		MfAccessInfo mfAccessInfoJsp = new MfAccessInfo();
		setObjValue(formmobileinterface0002, mfAccessInfoJsp);
		MfAccessInfo mfAccessInfo = mfAccessInfoFeign.getById(mfAccessInfoJsp);
		if (mfAccessInfo != null) {
			try {
				mfAccessInfo = (MfAccessInfo) EntityUtil.reflectionSetVal(mfAccessInfo, mfAccessInfoJsp,
						getMapByJson(ajaxData));
				mfAccessInfoFeign.update(mfAccessInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
			FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
			getFormValue(formmobileinterface0002, getMapByJson(ajaxData));
			if (this.validateFormData(formmobileinterface0002)) {
				MfAccessInfo mfAccessInfo = new MfAccessInfo();
				setObjValue(formmobileinterface0002, mfAccessInfo);
				mfAccessInfoFeign.update(mfAccessInfo);
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
	public Map<String, Object> getByIdAjax(String accessId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
		MfAccessInfo mfAccessInfo = new MfAccessInfo();
		mfAccessInfo.setAccessId(accessId);
		mfAccessInfo = mfAccessInfoFeign.getById(mfAccessInfo);
		getObjValue(formmobileinterface0002, mfAccessInfo, formData);
		if (mfAccessInfo != null) {
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
	public Map<String, Object> deleteAjax(String accessId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAccessInfo mfAccessInfo = new MfAccessInfo();
		mfAccessInfo.setAccessId(accessId);
		try {
			mfAccessInfoFeign.delete(mfAccessInfo);
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
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
		model.addAttribute("formmobileinterface0002", formmobileinterface0002);
		model.addAttribute("query", "");
		return "MfAccessInfo_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String accessId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0001");
		getFormValue(formmobileinterface0002);
		MfAccessInfo mfAccessInfo = new MfAccessInfo();
		mfAccessInfo.setAccessId(accessId);
		mfAccessInfo = mfAccessInfoFeign.getById(mfAccessInfo);
		getObjValue(formmobileinterface0002, mfAccessInfo);
		model.addAttribute("mfAccessInfo", mfAccessInfo);
		model.addAttribute("formmobileinterface0002", formmobileinterface0002);
		model.addAttribute("query", "");
		return "MfAccessInfo_Detail";
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
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
		getFormValue(formmobileinterface0002);
		boolean validateFlag = this.validateFormData(formmobileinterface0002);
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
		FormData formmobileinterface0002 = formService.getFormData("mobileinterface0002");
		getFormValue(formmobileinterface0002);
		boolean validateFlag = this.validateFormData(formmobileinterface0002);
	}

}
