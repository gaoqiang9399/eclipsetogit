package app.component.pfs.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.pfs.entity.CusFinInv;
import app.component.pfs.entity.CusFinList;
import app.component.pfs.feign.CusFinInvFeign;
import app.component.pfs.feign.CusFinListFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinInvAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:26:58 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinInv")
public class CusFinInvController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinInvBo
	@Autowired
	private CusFinInvFeign cusFinInvFeign;
	@Autowired
	private CusFinListFeign cusFinListFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	private void getTableData(Ipage ipage, Integer pageSize, Integer pageNo, String cusNo, String rptDate,
			String tableId, Map<String, Object> dataMap) throws Exception {
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// 自定义查询Bo方法
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setCusNo(cusNo);
		cusFinInv.setRptDate(rptDate);
		ipage = cusFinInvFeign.findByPage(ipage, cusFinInv);
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", (List) ipage.getResult(), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @param cusNo
	 * @param rptDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0056 = formService.getFormData("pfs0056");
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setCusNo(cusNo);
		cusFinInv.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinInv> cusFinInvList = (List<CusFinInv>) cusFinInvFeign.findByPage(ipage, cusFinInv).getResult();
		model.addAttribute("formpfs0056", formpfs0056);
		model.addAttribute("cusFinInvList", cusFinInvList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinInv_List";
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
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinInv cusFinInv = new CusFinInv();
		try {
			cusFinInv.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinInv.setCriteriaList(cusFinInv, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinInv,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = cusFinInvFeign.findByPage(ipage, cusFinInv);
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
	public Map<String, Object> insertAjax(String ajaxData, Integer pageSize, String tableId, Integer pageNo,
			Ipage ipage, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0056 = formService.getFormData("pfs0056");
			getFormValue(formpfs0056, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0056)) {
				CusFinInv cusFinInv = new CusFinInv();
				setObjValue(formpfs0056, cusFinInv);
				cusFinInvFeign.insert(cusFinInv);
				String cusNo = cusFinInv.getCusNo();
				String rptDate = cusFinInv.getRptDate();
				getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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
		FormData formpfs0056 = formService.getFormData("pfs0056");
		getFormValue(formpfs0056, getMapByJson(ajaxData));
		CusFinInv cusFinInvJsp = new CusFinInv();
		setObjValue(formpfs0056, cusFinInvJsp);
		CusFinInv cusFinInv = cusFinInvFeign.getById(cusFinInvJsp);
		if (cusFinInv != null) {
			try {
				cusFinInv = (CusFinInv) EntityUtil.reflectionSetVal(cusFinInv, cusFinInvJsp, getMapByJson(ajaxData));
				cusFinInvFeign.update(cusFinInv);
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
	public Map<String, Object> updateAjax(String ajaxData, Integer pageSize, String tableId, Integer pageNo,
			Ipage ipage, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0056 = formService.getFormData("pfs0056");
			getFormValue(formpfs0056, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0056)) {
				CusFinInv cusFinInv = new CusFinInv();
				setObjValue(formpfs0056, cusFinInv);
				cusFinInvFeign.update(cusFinInv);
				String cusNo = cusFinInv.getCusNo();
				String rptDate = cusFinInv.getRptDate();
				getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0056 = formService.getFormData("pfs0056");
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setId(id);
		cusFinInv = cusFinInvFeign.getById(cusFinInv);
		getObjValue(formpfs0056, cusFinInv, formData);
		if (cusFinInv != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, Integer pageSize, String tableId, Integer pageNo,
			Ipage ipage, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinInv = (CusFinInv) JSONObject.toBean(jb, CusFinInv.class);
			cusFinInvFeign.delete(cusFinInv);
			String cusNo = cusFinInv.getCusNo();
			String rptDate = cusFinInv.getRptDate();
			getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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

	/****
	 * ajax新增获取数据
	 * 
	 * @param cusNo
	 * @param rptDate
	 * @param query 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String cusNo, String rptDate, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinList cusFinList = new CusFinList();
		cusFinList.setCusNo(cusNo);
		cusFinList.setRptDate(rptDate);
		cusFinList = cusFinListFeign.getById(cusFinList);

		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setCusNo(cusNo);
		cusFinInv.setCusName(cusFinList.getCusName());
		cusFinInv.setRptDate(rptDate);
		cusFinInv.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		cusFinInv.setRegNo(User.getRegNo(this.getHttpRequest()));
		cusFinInv.setRegDate(User.getSysDate(this.getHttpRequest()));
		cusFinInv.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs0056 = formService.getFormData("pfs0056");
		getObjValue(formpfs0056, cusFinInv);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs0056, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
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
		FormData formpfs0056 = formService.getFormData("pfs0056");
		model.addAttribute("formpfs0056", formpfs0056);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinInv_Insert";
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
		FormData formpfs0056 = formService.getFormData("pfs0056");
		getFormValue(formpfs0056);
		CusFinInv cusFinInv = new CusFinInv();
		setObjValue(formpfs0056, cusFinInv);
		cusFinInvFeign.insert(cusFinInv);
		getObjValue(formpfs0056, cusFinInv);
		this.addActionMessage(model, "保存成功");
		List<CusFinInv> cusFinInvList = (List<CusFinInv>) cusFinInvFeign.findByPage(this.getIpage(), cusFinInv)
				.getResult();
		model.addAttribute("formpfs0056", formpfs0056);
		model.addAttribute("cusFinInvList", cusFinInvList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinInv_Detail";
	}

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0055 = formService.getFormData("pfs0055");
		getFormValue(formpfs0055);
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setId(id);
		cusFinInv = cusFinInvFeign.getById(cusFinInv);
		getObjValue(formpfs0055, cusFinInv);
		model.addAttribute("formpfs0055", formpfs0055);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinInv_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id,String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinInv cusFinInv = new CusFinInv();
		cusFinInv.setId(id);
		cusFinInvFeign.delete(cusFinInv);
		return getListPage(model,cusNo,rptDate);
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
		FormData formpfs0056 = formService.getFormData("pfs0056");
		getFormValue(formpfs0056);
		boolean validateFlag = this.validateFormData(formpfs0056);
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
		FormData formpfs0056 = formService.getFormData("pfs0056");
		getFormValue(formpfs0056);
		boolean validateFlag = this.validateFormData(formpfs0056);
	}

}
