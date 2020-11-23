package app.component.finance.vchout.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.finance.util.CustomException;
import app.component.finance.vchout.entity.CwVchOutSoftver;
import app.component.finance.vchout.feign.CwVchOutSoftverFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: CwVchOutSoftverAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 18 17:37:08 CST 2017
 **/
@Controller
@RequestMapping("/cwVchOutSoftver")
public class CwVchOutSoftverController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchOutSoftverFeign cwVchOutSoftverFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formvchout0001;
	private FormData formvchout0002;
	private FormService formService = new FormService();

	public CwVchOutSoftverController() {
		query = "";
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/vchout/CwVchOutSoftver_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
		try {
			cwVchOutSoftver.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchOutSoftver.setCriteriaList(cwVchOutSoftver, ajaxData);// 我的筛选
			// cwVchOutSoftver.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(cwVchOutSoftver,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			cwVchOutSoftver.setSoftType("1");
			ipage = cwVchOutSoftverFeign.findByPage(ipage, cwVchOutSoftver);
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

	@RequestMapping(value = "/getSelectExportVchAjax")
	@ResponseBody
	public Map<String, Object> getSelectExportVchAjax(Integer pageNo, Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			cwVchOutSoftver.setSoftType("0");
			cwVchOutSoftver.setUseAuto("1");
			// 自定义查询Bo方法
			ipage = cwVchOutSoftverFeign.getSelectExportVch(ipage, cwVchOutSoftver);
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
	 * 方法描述： 导出凭证模版数据 void
	 * 
	 * @author Javelin
	 * @date 2017-3-18 下午2:24:01
	 */
	@RequestMapping(value = "/exportVchTemplate")
	@ResponseBody
	public ResponseEntity<byte[]> exportVchTemplate(String ajaxData, String softId) throws Exception {
		ActionContext.initialize(request, response);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "";
		// 输出文件；
		try {// 获取文件
			CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
			cwVchOutSoftver.setSoftId(softId);
			cwVchOutSoftver = cwVchOutSoftverFeign.getById(cwVchOutSoftver);
			if ("0".equals(cwVchOutSoftver.getFileType())) {
				fileName = WaterIdUtil.getWaterId() + "凭证导入模版.xls";
				response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				byte[] bytes = cwVchOutSoftverFeign.getExportVoucherTemplateWB(cwVchOutSoftver, ajaxData,finBooks);
				return new ResponseEntity<byte[]>(bytes,HttpStatus.CREATED);
			} else {// 导出xml格式文件
				// fileName = WaterIdUtil.getWaterId()+"凭证导入模版.xml";
				// response.addHeader("Content-Disposition", "attachment;filename="+
				// URLEncoder.encode(fileName, "utf-8"));
				// out = response.getOutputStream();
				// Document Doc = cwVchOutSoftverFeign.outVoucherT3Data(ajaxData);
				// XMLOutputter XMLOut = new XMLOutputter();
				// XMLOut.setFormat(Format.getCompactFormat().setEncoding("utf-8").setIndent("
				// "));
				// //输出流
				// XMLOut.output(Doc, out);
				
			}
			
		} catch (Exception e) {
//			logger.error("exportVchTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formvchout0002 = formService.getFormData("vchout0002");
			getFormValue(formvchout0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvchout0002)) {
				CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
				setObjValue(formvchout0002, cwVchOutSoftver);
				cwVchOutSoftverFeign.insert(cwVchOutSoftver);
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002, getMapByJson(ajaxData));
		CwVchOutSoftver cwVchOutSoftverJsp = new CwVchOutSoftver();
		setObjValue(formvchout0002, cwVchOutSoftverJsp);
		CwVchOutSoftver cwVchOutSoftver = cwVchOutSoftverFeign.getById(cwVchOutSoftverJsp);
		if (cwVchOutSoftver != null) {
			try {
				cwVchOutSoftver = (CwVchOutSoftver) EntityUtil.reflectionSetVal(cwVchOutSoftver, cwVchOutSoftverJsp,
						getMapByJson(ajaxData));
				cwVchOutSoftverFeign.update(cwVchOutSoftver);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formvchout0002 = formService.getFormData("vchout0002");
			getFormValue(formvchout0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvchout0002)) {
				CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
				setObjValue(formvchout0002, cwVchOutSoftver);
				cwVchOutSoftverFeign.update(cwVchOutSoftver);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String softId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formvchout0002 = formService.getFormData("vchout0002");
		CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
		cwVchOutSoftver.setSoftId(softId);
		cwVchOutSoftver = cwVchOutSoftverFeign.getById(cwVchOutSoftver);
		getObjValue(formvchout0002, cwVchOutSoftver, formData);
		if (cwVchOutSoftver != null) {
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
	@RequestMapping(value = "/deleteAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAjax(String softId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
		cwVchOutSoftver.setSoftId(softId);
		try {
			cwVchOutSoftverFeign.delete(cwVchOutSoftver);
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
		ActionContext.initialize(request, response);
		formvchout0002 = formService.getFormData("vchout0002");
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
		return "/component/finance/vchout/CwVchOutSoftver_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String softId) throws Exception {
		ActionContext.initialize(request, response);
		formvchout0001 = formService.getFormData("vchout0001");
		getFormValue(formvchout0001);
		CwVchOutSoftver cwVchOutSoftver = new CwVchOutSoftver();
		cwVchOutSoftver.setSoftId(softId);
		cwVchOutSoftver = cwVchOutSoftverFeign.getById(cwVchOutSoftver);
		getObjValue(formvchout0001, cwVchOutSoftver);
		model.addAttribute("cwVchOutSoftver", cwVchOutSoftver);
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
		return "/component/finance/vchout/CwVchOutSoftver_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002);
		boolean validateFlag = this.validateFormData(formvchout0002);
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		formvchout0002 = formService.getFormData("vchout0002");
		getFormValue(formvchout0002);
		boolean validateFlag = this.validateFormData(formvchout0002);
		model.addAttribute("formvchout0002", formvchout0002);
		model.addAttribute("query", query);
	}

}
