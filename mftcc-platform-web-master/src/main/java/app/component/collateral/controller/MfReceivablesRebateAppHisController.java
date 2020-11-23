package app.component.collateral.controller;

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

import app.component.collateral.entity.MfReceivablesRebateAppHis;
import app.component.collateral.feign.MfReceivablesRebateAppHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfReceivablesRebateAppHisController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon May 15 11:31:02 CST 2017
 **/
@Controller
@RequestMapping("/mfReceivablesRebateAppHis")
public class MfReceivablesRebateAppHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReceivablesRebateAppHisFeign mfReceivablesRebateAppHisFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/MfReceivablesRebateAppHis_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		try {
			mfReceivablesRebateAppHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfReceivablesRebateAppHis.setCriteriaList(mfReceivablesRebateAppHis, ajaxData);// 我的筛选
			// mfReceivablesRebateAppHis.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfReceivablesRebateAppHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfReceivablesRebateAppHisFeign.findByPage(ipage, mfReceivablesRebateAppHis);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
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
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
			getFormValue(formrebateapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrebateapprohis0002)) {
				MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
				setObjValue(formrebateapprohis0002, mfReceivablesRebateAppHis);
				mfReceivablesRebateAppHisFeign.insert(mfReceivablesRebateAppHis);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
		getFormValue(formrebateapprohis0002, getMapByJson(ajaxData));
		MfReceivablesRebateAppHis mfReceivablesRebateAppHisJsp = new MfReceivablesRebateAppHis();
		setObjValue(formrebateapprohis0002, mfReceivablesRebateAppHisJsp);
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = mfReceivablesRebateAppHisFeign
				.getById(mfReceivablesRebateAppHisJsp);
		if (mfReceivablesRebateAppHis != null) {
			try {
				mfReceivablesRebateAppHis = (MfReceivablesRebateAppHis) EntityUtil.reflectionSetVal(
						mfReceivablesRebateAppHis, mfReceivablesRebateAppHisJsp, getMapByJson(ajaxData));
				mfReceivablesRebateAppHisFeign.update(mfReceivablesRebateAppHis);
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
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
			getFormValue(formrebateapprohis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrebateapprohis0002)) {
				MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
				setObjValue(formrebateapprohis0002, mfReceivablesRebateAppHis);
				mfReceivablesRebateAppHisFeign.update(mfReceivablesRebateAppHis);
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
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String ajaxData, String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		mfReceivablesRebateAppHis.setRebateAppId(rebateAppId);
		mfReceivablesRebateAppHis = mfReceivablesRebateAppHisFeign.getById(mfReceivablesRebateAppHis);
		getObjValue(formrebateapprohis0002, mfReceivablesRebateAppHis, formData);
		if (mfReceivablesRebateAppHis != null) {
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		mfReceivablesRebateAppHis.setRebateAppId(rebateAppId);
		try {
			mfReceivablesRebateAppHisFeign.delete(mfReceivablesRebateAppHis);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
		model.addAttribute("formrebateapprohis0002", formrebateapprohis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateAppHis_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrebateapprohis0002 = formService.getFormData("rebateapprohis0002");
		getFormValue(formrebateapprohis0002);
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		setObjValue(formrebateapprohis0002, mfReceivablesRebateAppHis);
		mfReceivablesRebateAppHisFeign.insert(mfReceivablesRebateAppHis);
		getObjValue(formrebateapprohis0002, mfReceivablesRebateAppHis);
		this.addActionMessage(model, "保存成功");
		List<MfReceivablesRebateAppHis> mfReceivablesRebateAppHisList = (List<MfReceivablesRebateAppHis>) mfReceivablesRebateAppHisFeign
				.findByPage(this.getIpage(), mfReceivablesRebateAppHis).getResult();
		model.addAttribute("mfReceivablesRebateAppHisList", mfReceivablesRebateAppHisList);
		model.addAttribute("formrebateapprohis0002", formrebateapprohis0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateAppHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrebateapprohis0001 = formService.getFormData("rebateapprohis0001");
		getFormValue(formrebateapprohis0001);
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		mfReceivablesRebateAppHis.setRebateAppId(rebateAppId);
		mfReceivablesRebateAppHis = mfReceivablesRebateAppHisFeign.getById(mfReceivablesRebateAppHis);
		getObjValue(formrebateapprohis0001, mfReceivablesRebateAppHis);
		model.addAttribute("formrebateapprohis0001", formrebateapprohis0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceivablesRebateAppHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String rebateAppId) throws Exception {
		ActionContext.initialize(request, response);
		MfReceivablesRebateAppHis mfReceivablesRebateAppHis = new MfReceivablesRebateAppHis();
		mfReceivablesRebateAppHis.setRebateAppId(rebateAppId);
		mfReceivablesRebateAppHisFeign.delete(mfReceivablesRebateAppHis);
		return getListPage();
	}

}
