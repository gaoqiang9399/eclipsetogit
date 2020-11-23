package app.component.pact.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.pact.entity.MfBusPactHis;
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
import app.component.pact.feign.MfBusPactHisFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusPactHisAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 22 10:21:41 CST 2016
 **/
@Controller
@RequestMapping("/mfBusPactHis")
public class MfBusPactHisController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfBusPactHisBo
	@Autowired
	private MfBusPactHisFeign mfBusPactHisFeign;

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
		return "/component/pact/MfBusPactHis_List";
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
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		try {
			mfBusPactHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusPactHis.setCriteriaList(mfBusPactHis, ajaxData);// 我的筛选
			// this.getRoleConditions(mfBusPactHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfBusPactHisFeign.findByPage(ipage, mfBusPactHis);
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
			FormData formpacthis0002 = formService.getFormData("pacthis0002");
			getFormValue(formpacthis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpacthis0002)) {
				MfBusPactHis mfBusPactHis = new MfBusPactHis();
				setObjValue(formpacthis0002, mfBusPactHis);
				mfBusPactHisFeign.insert(mfBusPactHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		getFormValue(formpacthis0002, getMapByJson(ajaxData));
		MfBusPactHis mfBusPactHisJsp = new MfBusPactHis();
		setObjValue(formpacthis0002, mfBusPactHisJsp);
		MfBusPactHis mfBusPactHis = mfBusPactHisFeign.getById(mfBusPactHisJsp);
		if (mfBusPactHis != null) {
			try {
				mfBusPactHis = (MfBusPactHis) EntityUtil.reflectionSetVal(mfBusPactHis, mfBusPactHisJsp,
						getMapByJson(ajaxData));
				mfBusPactHisFeign.update(mfBusPactHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpacthis0002 = formService.getFormData("pacthis0002");
			getFormValue(formpacthis0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpacthis0002)) {
				MfBusPactHis mfBusPactHis = new MfBusPactHis();
				setObjValue(formpacthis0002, mfBusPactHis);
				mfBusPactHisFeign.update(mfBusPactHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setId(id);
		mfBusPactHis = mfBusPactHisFeign.getById(mfBusPactHis);
		getObjValue(formpacthis0002, mfBusPactHis, formData);
		if (mfBusPactHis != null) {
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setId(id);
		try {
			mfBusPactHisFeign.delete(mfBusPactHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		model.addAttribute("formpacthis0002", formpacthis0002);
		model.addAttribute("query", "");
		return "/component/pact/MfBusPactHis_Insert";
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
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		getFormValue(formpacthis0002);
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		setObjValue(formpacthis0002, mfBusPactHis);
		mfBusPactHisFeign.insert(mfBusPactHis);
		getObjValue(formpacthis0002, mfBusPactHis);
		this.addActionMessage(model, "保存成功");
		List<MfBusPactHis> mfBusPactHisList = (List<MfBusPactHis>) mfBusPactHisFeign.findByPage(this.getIpage(), mfBusPactHis).getResult();
		model.addAttribute("mfBusPactHisList", mfBusPactHisList);
		model.addAttribute("formpacthis0002", formpacthis0002);
		model.addAttribute("query", "");
		return "/component/pact/MfBusPactHis_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpacthis0002 = formService.getFormData("pacthis0001");
		getFormValue(formpacthis0002);
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setId(id);
		mfBusPactHis = mfBusPactHisFeign.getById(mfBusPactHis);
		getObjValue(formpacthis0002, mfBusPactHis);
		model.addAttribute("mfBusPactHis", mfBusPactHis);
		model.addAttribute("formpacthis0002", formpacthis0002);
		model.addAttribute("query", "");
		return "/component/pact/MfBusPactHis_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusPactHis mfBusPactHis = new MfBusPactHis();
		mfBusPactHis.setId(id);
		mfBusPactHisFeign.delete(mfBusPactHis);
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
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		getFormValue(formpacthis0002);
		boolean validateFlag = this.validateFormData(formpacthis0002);
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
		FormData formpacthis0002 = formService.getFormData("pacthis0002");
		getFormValue(formpacthis0002);
		boolean validateFlag = this.validateFormData(formpacthis0002);
	}

}
