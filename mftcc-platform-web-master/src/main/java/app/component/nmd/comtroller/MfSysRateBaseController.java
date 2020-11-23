package app.component.nmd.comtroller;

import java.util.ArrayList;
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

import app.base.CustomSort;
import app.component.common.EntityUtil;
import app.component.nmd.entity.MfSysRateBase;
import app.component.nmd.feign.MfSysRateBaseFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysRateBaseAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 09 10:15:21 CST 2017
 **/
@Controller
@RequestMapping("/mfSysRateBase")
public class MfSysRateBaseController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfSysRateBaseBo
	@Autowired
	private MfSysRateBaseFeign mfSysRateBaseFeign;

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
		return "component/nmd/MfSysRateBase_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param str
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String ratetype, String str) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfSysRateBase mfSysRateBase = new MfSysRateBase();
			// 获取需要查询的利息类型
			// 设置排序字段
			CustomSort customSort = new CustomSort();
			customSort.setSortField("begindate");// 设置排序字段
			customSort.setSortType("desc");// 设置排序的类型
			List<CustomSort> sortList = new ArrayList<CustomSort>();
			sortList.add(customSort);
			mfSysRateBase.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfSysRateBase.setCriteriaList(mfSysRateBase, ajaxData);// 我的筛选
			mfSysRateBase.addCustomQueryByString(str);
			// 设置排序
			mfSysRateBase.setCustomSorts(sortList);
			// this.getRoleConditions(mfSysRateBase,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfSysRateBase",mfSysRateBase));
			ipage = mfSysRateBaseFeign.findByPage(ipage/*, mfSysRateBase*/);
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
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbaserate = formService.getFormData("baserate");
			getFormValue(formbaserate, getMapByJson(ajaxData));
			if (this.validateFormData(formbaserate)) {
				MfSysRateBase mfSysRateBase = new MfSysRateBase();
				setObjValue(formbaserate, mfSysRateBase);
				mfSysRateBaseFeign.insert(mfSysRateBase);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbaserate = formService.getFormData("baserate");
		getFormValue(formbaserate, getMapByJson(ajaxData));
		MfSysRateBase mfSysRateBaseJsp = new MfSysRateBase();
		setObjValue(formbaserate, mfSysRateBaseJsp);
		MfSysRateBase mfSysRateBase = mfSysRateBaseFeign.getById(mfSysRateBaseJsp);
		if (mfSysRateBase != null) {
			try {
				mfSysRateBase = (MfSysRateBase) EntityUtil.reflectionSetVal(mfSysRateBase, mfSysRateBaseJsp,
						getMapByJson(ajaxData));
				mfSysRateBaseFeign.update(mfSysRateBase);
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
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbaserate = formService.getFormData("baserate");
			getFormValue(formbaserate, getMapByJson(ajaxData));
			if (this.validateFormData(formbaserate)) {
				MfSysRateBase mfSysRateBase = new MfSysRateBase();
				setObjValue(formbaserate, mfSysRateBase);
				mfSysRateBaseFeign.update(mfSysRateBase);
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
	 * @param rateno
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String rateno) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbaserate = formService.getFormData("baserate");
		MfSysRateBase mfSysRateBase = new MfSysRateBase();
		mfSysRateBase.setRateno(rateno);
		mfSysRateBase = mfSysRateBaseFeign.getById(mfSysRateBase);
		getObjValue(formbaserate, mfSysRateBase, formData);
		if (mfSysRateBase != null) {
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
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String rateno) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfSysRateBase mfSysRateBase = new MfSysRateBase();
		mfSysRateBase.setRateno(rateno);
		try {
			mfSysRateBaseFeign.delete(mfSysRateBase);
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
		FormData formbaserate = formService.getFormData("baserate");
		model.addAttribute("formbaserate", formbaserate);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateBase_Insert";
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
		FormData formbaserate = formService.getFormData("baserate");
		getFormValue(formbaserate);
		MfSysRateBase mfSysRateBase = new MfSysRateBase();
		setObjValue(formbaserate, mfSysRateBase);
		mfSysRateBaseFeign.insert(mfSysRateBase);
		getObjValue(formbaserate, mfSysRateBase);
		this.addActionMessage(model, "保存成功");
		this.getIpage().setParams(this.setIpageParams("mfSysRateBase",mfSysRateBase));
		List<MfSysRateBase> mfSysRateBaseList = (List<MfSysRateBase>) mfSysRateBaseFeign
				.findByPage(this.getIpage()/*, mfSysRateBase*/).getResult();
		model.addAttribute("formbaserate", formbaserate);
		model.addAttribute("mfSysRateBaseList", mfSysRateBaseList);
		model.addAttribute("mfSysRateBase", mfSysRateBase);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateBase_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String rateno, Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbaserate001 = formService.getFormData("baserate001");
		getFormValue(formbaserate001);
		MfSysRateBase mfSysRateBase = new MfSysRateBase();
		mfSysRateBase.setRateno(rateno);
		mfSysRateBase = mfSysRateBaseFeign.getById(mfSysRateBase);
		getObjValue(formbaserate001, mfSysRateBase);
		model.addAttribute("formbaserate001", formbaserate001);
		model.addAttribute("mfSysRateBase", mfSysRateBase);
		model.addAttribute("query", "");
		return "component/nmd/MfSysRateBase_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String rateno, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfSysRateBase mfSysRateBase = new MfSysRateBase();
		mfSysRateBase.setRateno(rateno);
		mfSysRateBaseFeign.delete(mfSysRateBase);
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
		FormData formbaserate = formService.getFormData("baserate");
		getFormValue(formbaserate);
		boolean validateFlag = this.validateFormData(formbaserate);
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
		FormData formbaserate = formService.getFormData("baserate");
		getFormValue(formbaserate);
		boolean validateFlag = this.validateFormData(formbaserate);
	}

}
