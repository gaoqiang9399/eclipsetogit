package app.component.finance.voucher.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.util.InitExcleUtil;
import app.component.finance.util.R;
import app.component.finance.voucher.entity.CwVchPlateMst;
import app.component.finance.voucher.feign.CwVchPlateMstFeign;
import app.component.finance.voucher.util.VoucherXslUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: CwVchPlateMstAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 07 11:04:27 CST 2017
 **/
@Controller
@RequestMapping("/cwVchPlateMst")
public class CwVchPlateMstController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwVchPlateMstFeign cwVchPlateMstFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormService formService = new FormService();

	public CwVchPlateMstController() {
		query = "";
	}

	/**
	 * 方法描述： AJAX 凭证模版新增
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2016-12-30 上午10:57:40
	 */
	@RequestMapping(value = "/addVchPlateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addVchPlateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwVchPlateMstFeign.addVchPlate(formMap,finBooks);
			if(r.isOk()) {
				if ("0000".equals(r.getResult())) {
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				} else {
					dataMap.put("flag", "error");
					dataMap.put("msg", this.getFormulavaliErrorMsg());
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增凭证失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
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
		return "/component/finance/voucher/CwVchPlateMst_List";
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
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		try {
			cwVchPlateMst.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwVchPlateMst.setCriteriaList(cwVchPlateMst, ajaxData);// 我的筛选
			// this.getRoleConditions(cwVchPlateMst,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwVchPlateMst", cwVchPlateMst);
			ipage.setParams(params);
			ipage = cwVchPlateMstFeign.findByPage(ipage,finBooks);
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
	 * 方法描述： 获取凭证模版数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-16 下午3:45:55
	 */
	@RequestMapping(value = "/getVchPlateByNoAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVchPlateByNoAjax(String plateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		formData = cwVchPlateMstFeign.getVchPlateByNo(plateNo,finBooks);
		if (formData != null) {
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
	public Map<String, Object> deleteAjax(String plateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		cwVchPlateMst.setPlateNo(plateNo);
		try {
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwVchPlateMstFeign.delete(cwVchPlateMst,finBooks);
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
	 * 方法描述： 下载凭证导入模版 void
	 * 
	 * @author Javelin
	 * @date 2017-3-16 下午3:46:23
	 */
	@RequestMapping(value = "/printVchTemplate")
	public ResponseEntity<byte[]> printVchTemplate() {
		ActionContext.initialize(request, response);
		try {// 获取文件
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=voucherTemplate.xls");
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			
			byte[] bytes = cwVchPlateMstFeign.getVchTemplateWBData(finBooks);
			return new ResponseEntity<byte[]>(bytes, HttpStatus.CREATED);
			/*HSSFWorkbook wb = VoucherXslUtil.getVchTemplateWB(cwToolsFeign, finBooks);
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			//response.flushBuffer();
			out.flush();
			out.close();*/
		} catch (Exception e) {
//			logger.error("printVchTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法描述： 凭证模版上传
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-17 上午9:56:31
	 */
	@RequestMapping(value = "/importVchPlateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importVchPlateAjax(MultipartFile vch) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			
			R r = VoucherXslUtil.importVchPlate(cwToolsFeign, vch.getInputStream(), finBooks, User.getRegNo(request), User.getRegName(request));
			//R r = cwVchPlateMstFeign.importVchPlate(vch,finBooks,User.getRegNo(request),User.getRegName(request));
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "凭证导入失败");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("凭证导入"));
			e.printStackTrace();
			throw e;
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
	public void exportVchTemplate(String ajaxData) {
		ActionContext.initialize(request, response);
		try {// 获取文件
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(WaterIdUtil.getWaterId() + "凭证导入模版.xls", "utf-8"));
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			HSSFWorkbook wb = cwVchPlateMstFeign.getExportVchTemplateWB(ajaxData,finBooks);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			wb.write(out);
			response.flushBuffer();
			out.flush();
			out.close();
		} catch (Exception e) {
//			logger.error("printVchTemplate方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
		}
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
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		try {
			FormData formvoucher0002 = formService.getFormData("voucher0002");
			getFormValue(formvoucher0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvoucher0002)) {
				CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
				setObjValue(formvoucher0002, cwVchPlateMst);
				cwVchPlateMstFeign.insert(cwVchPlateMst,finBooks);
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
	@RequestMapping(value = "/updateAjaxByOne", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		getFormValue(formvoucher0002, getMapByJson(ajaxData));
		CwVchPlateMst cwVchPlateMstJsp = new CwVchPlateMst();
		setObjValue(formvoucher0002, cwVchPlateMstJsp);
		CwVchPlateMst cwVchPlateMst = cwVchPlateMstFeign.getById(cwVchPlateMstJsp,finBooks);
		if (cwVchPlateMst != null) {
			try {
				cwVchPlateMst = (CwVchPlateMst) EntityUtil.reflectionSetVal(cwVchPlateMst, cwVchPlateMstJsp,
						getMapByJson(ajaxData));
				cwVchPlateMstFeign.update(cwVchPlateMst,finBooks);
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
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		try {
			FormData formvoucher0002 = formService.getFormData("voucher0002");
			getFormValue(formvoucher0002, getMapByJson(ajaxData));
			if (this.validateFormData(formvoucher0002)) {
				CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
				setObjValue(formvoucher0002, cwVchPlateMst);
				cwVchPlateMstFeign.update(cwVchPlateMst,finBooks);
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
	@RequestMapping(value = "/getByIdAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getByIdAjax(String plateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		cwVchPlateMst.setPlateNo(plateNo);
		cwVchPlateMst = cwVchPlateMstFeign.getById(cwVchPlateMst,finBooks);
		getObjValue(formvoucher0002, cwVchPlateMst, formData);
		if (cwVchPlateMst != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
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
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		model.addAttribute("formvoucher0002", formvoucher0002);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVchPlateMst_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		getFormValue(formvoucher0002);
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		setObjValue(formvoucher0002, cwVchPlateMst);
		cwVchPlateMstFeign.insert(cwVchPlateMst,finBooks);
		getObjValue(formvoucher0002, cwVchPlateMst);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		// 自定义查询Bo方法
		Map<String, Object> params = new HashMap<>();
		params.put("cwVchPlateMst", cwVchPlateMst);
		ipage.setParams(params);
		List<CwVchPlateMst> cwVchPlateMstList = (List<CwVchPlateMst>) cwVchPlateMstFeign.findByPage(ipage,finBooks)
				.getResult();
		model.addAttribute("cwVchPlateMst", cwVchPlateMst);
		model.addAttribute("cwVchPlateMstList", cwVchPlateMstList);
		model.addAttribute("formvoucher0002", formvoucher0002);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVchPlateMst_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String plateNo) throws Exception {
		ActionContext.initialize(request, response);
		FormData formvoucher0001 = formService.getFormData("voucher0001");
		getFormValue(formvoucher0001);
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwVchPlateMst.setPlateNo(plateNo);
		cwVchPlateMst = cwVchPlateMstFeign.getById(cwVchPlateMst,finBooks);
		getObjValue(formvoucher0001, cwVchPlateMst);
		model.addAttribute("cwVchPlateMst", cwVchPlateMst);
		model.addAttribute("formvoucher0001", formvoucher0001);
		model.addAttribute("query", query);
		return "/component/finance/voucher/CwVchPlateMst_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String plateNo) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		CwVchPlateMst cwVchPlateMst = new CwVchPlateMst();
		cwVchPlateMst.setPlateNo(plateNo);
		cwVchPlateMstFeign.delete(cwVchPlateMst,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		getFormValue(formvoucher0002);
		boolean validateFlag = this.validateFormData(formvoucher0002);
		model.addAttribute("formvoucher0002", formvoucher0002);
		model.addAttribute("query", query);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formvoucher0002 = formService.getFormData("voucher0002");
		getFormValue(formvoucher0002);
		boolean validateFlag = this.validateFormData(formvoucher0002);
		model.addAttribute("formvoucher0002", formvoucher0002);
		model.addAttribute("query", query);
	}

}
