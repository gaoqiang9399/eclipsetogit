package app.component.pact.extension.controller;

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
import app.component.pact.extension.entity.MfBusExtensionApproveHis;
import app.component.pact.extension.feign.MfBusExtensionApproveHisFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusExtensionApproveHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 11:07:23 CST 2017
 **/
@Controller
@RequestMapping("/mfBusExtensionApproveHis")
public class MfBusExtensionApproveHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusExtensionApproveHisBo
	@Autowired
	private MfBusExtensionApproveHisFeign mfBusExtensionApproveHisFeign;
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
		return "/component/pact/extension/MfBusExtensionApproveHis_List";
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
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		try {
			mfBusExtensionApproveHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusExtensionApproveHis.setCriteriaList(mfBusExtensionApproveHis, ajaxData);// 我的筛选
			// mfBusExtensionApproveHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfBusExtensionApproveHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfBusExtensionApproveHisFeign.findByPage(ipage, mfBusExtensionApproveHis);
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
			FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
			getFormValue(formextapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formextapprohis0002)) {
				MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
				setObjValue(formextapprohis0002, mfBusExtensionApproveHis);
				mfBusExtensionApproveHisFeign.insert(mfBusExtensionApproveHis);
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
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		getFormValue(formextapprohis0002, getMapByJson(ajaxData));
		MfBusExtensionApproveHis mfBusExtensionApproveHisJsp = new MfBusExtensionApproveHis();
		setObjValue(formextapprohis0002, mfBusExtensionApproveHisJsp);
		MfBusExtensionApproveHis mfBusExtensionApproveHis = mfBusExtensionApproveHisFeign.getById(mfBusExtensionApproveHisJsp);
		if (mfBusExtensionApproveHis != null) {
			try {
				mfBusExtensionApproveHis = (MfBusExtensionApproveHis) EntityUtil.reflectionSetVal(
						mfBusExtensionApproveHis, mfBusExtensionApproveHisJsp, getMapByJson(ajaxData));
				mfBusExtensionApproveHisFeign.update(mfBusExtensionApproveHis);
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
			FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
			getFormValue(formextapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formextapprohis0002)) {
				MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
				setObjValue(formextapprohis0002, mfBusExtensionApproveHis);
				mfBusExtensionApproveHisFeign.update(mfBusExtensionApproveHis);
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
	public Map<String, Object> getByIdAjax(String approveHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		mfBusExtensionApproveHis.setApproveHisId(approveHisId);
		mfBusExtensionApproveHis = mfBusExtensionApproveHisFeign.getById(mfBusExtensionApproveHis);
		getObjValue(formextapprohis0002, mfBusExtensionApproveHis, formData);
		if (mfBusExtensionApproveHis != null) {
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
	public Map<String, Object> deleteAjax(String approveHisId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		mfBusExtensionApproveHis.setApproveHisId(approveHisId);
		try {
			mfBusExtensionApproveHisFeign.delete(mfBusExtensionApproveHis);
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
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		model.addAttribute("formextapprohis0002", formextapprohis0002);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApproveHis_Insert";
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
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		getFormValue(formextapprohis0002);
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		setObjValue(formextapprohis0002, mfBusExtensionApproveHis);
		mfBusExtensionApproveHisFeign.insert(mfBusExtensionApproveHis);
		getObjValue(formextapprohis0002, mfBusExtensionApproveHis);
		this.addActionMessage(model, "保存成功");
		List<MfBusExtensionApproveHis> mfBusExtensionApproveHisList = (List<MfBusExtensionApproveHis>) mfBusExtensionApproveHisFeign
				.findByPage(this.getIpage(), mfBusExtensionApproveHis).getResult();
		model.addAttribute("mfBusExtensionApproveHisList", mfBusExtensionApproveHisList);
		model.addAttribute("formextapprohis0002", formextapprohis0002);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApproveHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String approveHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formextapprohis0002 = formService.getFormData("extapprohis0001");
		getFormValue(formextapprohis0002);
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		mfBusExtensionApproveHis.setApproveHisId(approveHisId);
		mfBusExtensionApproveHis = mfBusExtensionApproveHisFeign.getById(mfBusExtensionApproveHis);
		getObjValue(formextapprohis0002, mfBusExtensionApproveHis);
		model.addAttribute("mfBusExtensionApproveHis", mfBusExtensionApproveHis);
		model.addAttribute("formextapprohis0002", formextapprohis0002);
		model.addAttribute("query", "");
		return "/component/pact/extension/MfBusExtensionApproveHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String approveHisId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusExtensionApproveHis mfBusExtensionApproveHis = new MfBusExtensionApproveHis();
		mfBusExtensionApproveHis.setApproveHisId(approveHisId);
		mfBusExtensionApproveHisFeign.delete(mfBusExtensionApproveHis);
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
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		getFormValue(formextapprohis0002);
		boolean validateFlag = this.validateFormData(formextapprohis0002);
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
		FormData formextapprohis0002 = formService.getFormData("extapprohis0002");
		getFormValue(formextapprohis0002);
		boolean validateFlag = this.validateFormData(formextapprohis0002);
	}

}
