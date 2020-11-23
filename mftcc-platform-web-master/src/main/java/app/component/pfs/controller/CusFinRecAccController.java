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
import app.component.pfs.entity.CusFinList;
import app.component.pfs.entity.CusFinRecAcc;
import app.component.pfs.feign.CusFinListFeign;
import app.component.pfs.feign.CusFinRecAccFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: CusFinRecAccAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Aug 30 05:34:11 GMT 2016
 **/
@Controller
@RequestMapping("/cusFinRecAcc")
public class CusFinRecAccController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入CusFinRecAccBo
	@Autowired
	private CusFinRecAccFeign cusFinRecAccFeign;
	@Autowired
	private CusFinListFeign cusFinListFeign;
	// 全局变量
	// 异步参数
	// 表单变量

	/***
	 * 获取数据
	 * 
	 * @throws Exception
	 */
	private void getTableData(Ipage ipage, Integer pageSize, Integer pageNo, String cusNo, String rptDate,
			String tableId, Map<String, Object> dataMap) throws Exception {
		ipage.setPageSize(pageSize);
		ipage.setPageNo(pageNo);// 异步传页面翻页参数
		// 自定义查询Bo方法
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setCusNo(cusNo);
		cusFinRecAcc.setRptDate(rptDate);
		ipage = cusFinRecAccFeign.findByPage(ipage, cusFinRecAcc);
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setCusNo(cusNo);
		cusFinRecAcc.setRptDate(rptDate);
		Ipage ipage = this.getIpage();
		List<CusFinRecAcc> cusFinRecAccList = (List<CusFinRecAcc>) cusFinRecAccFeign.findByPage(ipage, cusFinRecAcc).getResult();
		model.addAttribute("formpfs0064", formpfs0064);
		model.addAttribute("cusFinRecAccList", cusFinRecAccList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinRecAcc_List";
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
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		try {
			cusFinRecAcc.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cusFinRecAcc.setCriteriaList(cusFinRecAcc, ajaxData);// 我的筛选
			// this.getRoleConditions(cusFinRecAcc,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = cusFinRecAccFeign.findByPage(ipage, cusFinRecAcc);
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
	public Map<String, Object> insertAjax(String ajaxData, String tableId, Integer pageNo, Integer pageSize,
			Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0064 = formService.getFormData("pfs0064");
			getFormValue(formpfs0064, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0064)) {
				CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
				setObjValue(formpfs0064, cusFinRecAcc);
				cusFinRecAccFeign.insert(cusFinRecAcc);
				String cusNo = cusFinRecAcc.getCusNo();
				String rptDate = cusFinRecAcc.getRptDate();
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
	 * @param cusNo
	 * @param rptDate
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData, String tableId, Integer pageNo, Integer pageSize,
			Ipage ipage, String cusNo, String rptDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs0064 = formService.getFormData("pfs0064");
		getFormValue(formpfs0064, getMapByJson(ajaxData));
		CusFinRecAcc cusFinRecAccJsp = new CusFinRecAcc();
		setObjValue(formpfs0064, cusFinRecAccJsp);
		CusFinRecAcc cusFinRecAcc = cusFinRecAccFeign.getById(cusFinRecAccJsp);
		if (cusFinRecAcc != null) {
			try {
				cusFinRecAcc = (CusFinRecAcc) EntityUtil.reflectionSetVal(cusFinRecAcc, cusFinRecAccJsp,
						getMapByJson(ajaxData));
				cusFinRecAccFeign.update(cusFinRecAcc);
				getTableData(ipage, pageSize, pageNo, cusNo, rptDate, tableId, dataMap);
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

	/****
	 * ajax新增
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
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setCusNo(cusNo);
		cusFinRecAcc.setCusName(cusFinList.getCusName());
		cusFinRecAcc.setRptDate(rptDate);
		cusFinRecAcc.setRegOrgNo(User.getOrgNo(this.getHttpRequest()));
		cusFinRecAcc.setRegNo(User.getRegNo(this.getHttpRequest()));
		cusFinRecAcc.setRegDate(User.getSysDate(this.getHttpRequest()));
		cusFinRecAcc.setLstDate(User.getSysDate(this.getHttpRequest()));
		FormData formpfs0064 = formService.getFormData("pfs0064");
		getObjValue(formpfs0064, cusFinRecAcc);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs0064, "formTag", query);
		dataMap.put("formHtml", formHtml);
		dataMap.put("flag", "success");
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
	public Map<String, Object> updateAjax(String ajaxData, String tableId, Integer pageNo, Integer pageSize,
			Ipage ipage) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpfs0064 = formService.getFormData("pfs0064");
			getFormValue(formpfs0064, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs0064)) {
				CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
				setObjValue(formpfs0064, cusFinRecAcc);
				cusFinRecAccFeign.update(cusFinRecAcc);
				String cusNo = cusFinRecAcc.getCusNo();
				String rptDate = cusFinRecAcc.getRptDate();
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
	 * @param id 
	 * 
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setId(id);
		cusFinRecAcc = cusFinRecAccFeign.getById(cusFinRecAcc);
		getObjValue(formpfs0064, cusFinRecAcc, formData);
		if (cusFinRecAcc != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, Integer pageNo, Integer pageSize,
			Ipage ipage, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cusFinRecAcc = (CusFinRecAcc) JSONObject.toBean(jb, CusFinRecAcc.class);
			cusFinRecAccFeign.delete(cusFinRecAcc);
			String cusNo = cusFinRecAcc.getCusNo();
			String rptDate = cusFinRecAcc.getRptDate();
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		model.addAttribute("formpfs0064", formpfs0064);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinRecAcc_Insert";
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		getFormValue(formpfs0064);
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		setObjValue(formpfs0064, cusFinRecAcc);
		cusFinRecAccFeign.insert(cusFinRecAcc);
		getObjValue(formpfs0064, cusFinRecAcc);
		this.addActionMessage(model, "保存成功");
		List<CusFinRecAcc> cusFinRecAccList = (List<CusFinRecAcc>) cusFinRecAccFeign.findByPage(this.getIpage(), cusFinRecAcc).getResult();
		model.addAttribute("formpfs0064", formpfs0064);
		model.addAttribute("cusFinRecAccList", cusFinRecAccList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinRecAcc_Detail";
	}

	/**
	 * 查询
	 * @param id 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs0063 = formService.getFormData("pfs0063");
		getFormValue(formpfs0063);
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setId(id);
		cusFinRecAcc = cusFinRecAccFeign.getById(cusFinRecAcc);
		getObjValue(formpfs0063, cusFinRecAcc);
		model.addAttribute("formpfs0063", formpfs0063);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinRecAcc_Detail";
	}

	/**
	 * 删除
	 * @param id 
	 * @param cusNo 
	 * @param rpsDate 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String id, String cusNo, String rpsDate) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinRecAcc cusFinRecAcc = new CusFinRecAcc();
		cusFinRecAcc.setId(id);
		cusFinRecAccFeign.delete(cusFinRecAcc);
		return getListPage(model, cusNo, rpsDate);
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		getFormValue(formpfs0064);
		boolean validateFlag = this.validateFormData(formpfs0064);
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
		FormData formpfs0064 = formService.getFormData("pfs0064");
		getFormValue(formpfs0064);
		boolean validateFlag = this.validateFormData(formpfs0064);
	}

}
