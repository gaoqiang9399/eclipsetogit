package app.component.finance.paramset.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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

import app.base.User;
import app.component.common.EntityUtil;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.entity.CwInitBal;
import app.component.finance.paramset.feign.CwInitBalFeign;
import app.component.finance.paramset.util.InitExcleUtil;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.voucher.entity.CwVoucherAssist;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: CwInitBalAction.java Description:
 * 
 * @author:Yanghaitao@dhcc.com.cn
 * @Tue Jan 03 10:25:01 CST 2017
 **/
@Controller
@RequestMapping("/cwInitBal")
public class CwInitBalController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwInitBalFeign cwInitBalFeign;
	@Autowired
	private CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;

	private FormService formService = new FormService();

	public CwInitBalController() {
		query = "";
	}

	/**
	 * 跳转至财务余额初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getInitPage")
	public String getInitPage(Model model) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			Map<String, String> flagMap = cwInitBalFeign.getIfBalInited(finBooks);
			if (MathExtend.comparison(flagMap.get("balCnt"), "0") == 0) {
				return "/component/finance/paramset/CwInitBal_InitPage";
			}
			dataMap.put("initFlag", flagMap.get("initFlag"));
		} catch (Exception e) {
//			logger.error("getInitPage方法出错，执行action层失败，抛出异常，", e);
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/paramset/CwInitBal_List";
	}

	/**
	 * 下载财务余额初始化模板excel文件
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadExcel")
	public void downloadExcel() throws Exception  {
		ActionContext.initialize(request, response);
		try {// 获取文件
			String filename = "财务科目余额导入模板_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
			HSSFWorkbook demoWorkBook = InitExcleUtil.getHSSFWorkbook(cwToolsFeign, finBooks);
			// 输出流
			ServletOutputStream out = response.getOutputStream();
			demoWorkBook.write(out);
			response.flushBuffer();
			out.flush();
			out.close();
		} catch (Exception e) {
//			logger.error("downloadExcel方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 财务余额初始化表的导入
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadExcel")
	public String uploadExcel(Model model, MultipartFile file1) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		dataMap.put("regNo", User.getRegNo(request));
		dataMap.put("regName", User.getRegName(request));
		try {
			String[] rflag = new String[2];// 存放处理结果
			//rflag = cwInitBalFeign.getreaderInitExcleResult(file1, file1FileName,finBooks);
			rflag = InitExcleUtil.getreaderInitExcleResult(file1.getInputStream(),cwInitBalFeign, finBooks,User.getRegNo(request),User.getRegName(request));
			if ("0000".equals(rflag[0])) {
				model.addAttribute("ifShow", "true");
				model.addAttribute("flag", "success");
				model.addAttribute("msg", MessageEnum.SUCCEED_UPLOAD.getMessage());
			} else {
				model.addAttribute("ifShow", "false");
				model.addAttribute("flag", "error");
				model.addAttribute("msg", rflag[1]);
			}
		} catch (Exception e) {
//			logger.error("uploadExcel方法出错，执行action层失败，抛出异常，" + e.getMessage(), e);
			model.addAttribute("ifShow", "false");
			model.addAttribute("flag", "error");
			model.addAttribute("msg", "系统异常！");
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPLOAD_FILE.getMessage("科目初始化余额"));
			e.printStackTrace();
			throw e;
		}
		return "/component/finance/paramset/CwInitBal_InitPage";
	}
	/////////////////////////////////////////////////

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/finance/paramset/CwInitBal_List";
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
		CwInitBal cwInitBal = new CwInitBal();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			cwInitBal.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwInitBal.setCriteriaList(cwInitBal, ajaxData);// 我的筛选
			// this.getRoleConditions(cwInitBal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			// 自定义查询Bo方法
			Map<String, Object> params = new HashMap<>();
			params.put("cwInitBal", cwInitBal);
			ipage.setParams(params);
			ipage = cwInitBalFeign.findByPage(ipage,finBooks);
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
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			FormData forminitbal0002 = formService.getFormData("initbal0002");
			getFormValue(forminitbal0002, getMapByJson(ajaxData));
			if (this.validateFormData(forminitbal0002)) {
				CwInitBal cwInitBal = new CwInitBal();
				setObjValue(forminitbal0002, cwInitBal);
				cwInitBalFeign.insert(cwInitBal,finBooks);
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
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		getFormValue(forminitbal0002, getMapByJson(ajaxData));
		CwInitBal cwInitBalJsp = new CwInitBal();
		setObjValue(forminitbal0002, cwInitBalJsp);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		CwInitBal cwInitBal = cwInitBalFeign.getById(cwInitBalJsp,finBooks);
		if (cwInitBal != null) {
			try {
				cwInitBal = (CwInitBal) EntityUtil.reflectionSetVal(cwInitBal, cwInitBalJsp, getMapByJson(ajaxData));
				cwInitBalFeign.update(cwInitBal,finBooks);
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
		try {
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			FormData forminitbal0002 = formService.getFormData("initbal0002");
			getFormValue(forminitbal0002, getMapByJson(ajaxData));
			if (this.validateFormData(forminitbal0002)) {
				CwInitBal cwInitBal = new CwInitBal();
				setObjValue(forminitbal0002, cwInitBal);
				cwInitBalFeign.update(cwInitBal,finBooks);
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
	public Map<String, Object> getByIdAjax(String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		CwInitBal cwInitBal = new CwInitBal();
		cwInitBal.setAccHrt(accHrt);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwInitBal = cwInitBalFeign.getById(cwInitBal,finBooks);
		getObjValue(forminitbal0002, cwInitBal, formData);
		if (cwInitBal != null) {
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
	public Map<String, Object> deleteAjax(String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwInitBal cwInitBal = new CwInitBal();
		cwInitBal.setAccHrt(accHrt);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		try {
			cwInitBalFeign.delete(cwInitBal,finBooks);
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
		ActionContext.initialize(request, response);
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		model.addAttribute("forminitbal0002", forminitbal0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwInitBal_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		getFormValue(forminitbal0002);
		CwInitBal cwInitBal = new CwInitBal();
		setObjValue(forminitbal0002, cwInitBal);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwInitBalFeign.insert(cwInitBal,finBooks);
		getObjValue(forminitbal0002, cwInitBal);
//		this.addActionMessage("保存成功");
		Ipage ipage = this.getIpage();
		Map<String, Object> params = new HashMap<>();
		params.put("cwInitBal", cwInitBal);
		ipage.setParams(params);
		List<CwInitBal> cwInitBalList = (List<CwInitBal>) cwInitBalFeign.findByPage(ipage,finBooks).getResult();
		model.addAttribute("cwInitBal", cwInitBal);
		model.addAttribute("cwInitBalList", cwInitBalList);
		model.addAttribute("forminitbal0002", forminitbal0002);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwInitBal_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		FormData forminitbal0001 = formService.getFormData("initbal0001");
		getFormValue(forminitbal0001);
		CwInitBal cwInitBal = new CwInitBal();
		cwInitBal.setAccHrt(accHrt);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwInitBal = cwInitBalFeign.getById(cwInitBal,finBooks);
		getObjValue(forminitbal0001, cwInitBal);
		model.addAttribute("cwInitBal", cwInitBal);
		model.addAttribute("forminitbal0001", forminitbal0001);
		model.addAttribute("query", query);
		return "/component/finance/paramset/CwInitBal_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		CwInitBal cwInitBal = new CwInitBal();
		cwInitBal.setAccHrt(accHrt);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		cwInitBalFeign.delete(cwInitBal,finBooks);
		return getListPage();
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	public void validateInsert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		getFormValue(forminitbal0002);
		boolean validateFlag = this.validateFormData(forminitbal0002);
		model.addAttribute("forminitbal0002", forminitbal0002);
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
		FormData forminitbal0002 = formService.getFormData("initbal0002");
		getFormValue(forminitbal0002);
		boolean validateFlag = this.validateFormData(forminitbal0002);
		model.addAttribute("forminitbal0002", forminitbal0002);
		model.addAttribute("query", query);
	}

	/**
	 * 
	 * 方法描述： 财务余额初始化添加辅助核算页面
	 * 
	 * @return String
	 * @author lzshuai
	 * @throws Exception 
	 * @date 2017-6-3 下午4:55:44
	 */
	@RequestMapping(value = "/initAssistInsert")
	public String initAssistInsert(Model model, String accHrt) throws Exception {
		ActionContext.initialize(request, response);
		FormData forminitassist0001 = formService.getFormData("initassist0001");
		CwVoucherAssist assbean = new CwVoucherAssist();
		assbean.setAccHrt(accHrt);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> mapobj = cwInitBalFeign.getMapObjByAcchrt(assbean,finBooks);
		Map<String, Object>  assNewbeanMap = (Map) mapobj.get("assbean");
		CwVoucherAssist assNewbean = new CwVoucherAssist();
		BeanUtils.populate(assNewbean, assNewbeanMap);
		@SuppressWarnings("unchecked")
		List<CwRelation> cwRelatiionList = (List<CwRelation>) mapobj.get("cwRelatiionList");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		getObjValue(forminitassist0001, assNewbean);
		String json = CwPublicUtil.gson.toJson(cwRelatiionList);
		dataMap.put("cwRelatiionList", json);
		model.addAttribute("assNewbean", assNewbean);
		model.addAttribute("forminitassist0001", forminitassist0001);
		model.addAttribute("query", query);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/paramset/CwInitBal_initAssistInsert";
	}

	/**
	 * 
	 * 方法描述： 财务出事余额，添加辅助核算项
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-3 下午6:57:04
	 */
	@RequestMapping(value = "/initAssistInsertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssistInsertAjax(String ajaxData) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData forminitassist0001 = formService.getFormData("initassist0001");
			getFormValue(forminitassist0001, getMapByJson(ajaxData));
			// if(this.validateFormData(forminitassist0001)){
			CwVoucherAssist assbean = new CwVoucherAssist();
			setObjValue(forminitassist0001, assbean);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			String result = cwInitBalFeign.doInitAssistInsert(assbean,finBooks);
			if ("0000".equals(result)) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else if ("1111".equals(result)) {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.EXIST_INFORMATION_EVAL.getMessage("数据"));
			} else {
				dataMap.put("flag", "error");
				// dataMap.put("msg",this.getFormulavaliErrorMsg());
				dataMap.put("msg", result);
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "新增科目失败");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：修改财务辅助核算余额
	 * 
	 * @return String
	 * @author lzshuai
	 * @throws Exception 
	 * @date 2017-6-21 上午11:31:04
	 */
	@RequestMapping(value = "/addInitAssistBalAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addInitAssistBalAjax(String accHrt, String voucherNo, String bal) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String tlrno = User.getRegNo(request);
		String tlrnoName = User.getRegName(request);
		Map<String, String> haMap = new HashMap<String, String>();
		haMap.put("accHrt", accHrt);
		haMap.put("voucherNo", voucherNo);
		haMap.put("bal", bal);
		haMap.put("tlrno", tlrno);
		haMap.put("tlrnoName", tlrnoName);
		String result = cwInitBalFeign.addInitAssistBal(haMap,finBooks);
		dataMap.put("flag", "success");
		dataMap.put("result", result);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：删除初始化辅助核算的功能
	 * 
	 * @return String
	 * @author lzshuai
	 * @throws Exception 
	 * @date 2017-6-22 上午9:01:37
	 */
	@RequestMapping(value = "/delInitAssistBalAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delInitAssistBalAjax(String accHrt, String voucherNo) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String result = cwInitBalFeign.delInitAssistBal(accHrt, voucherNo,finBooks);
		dataMap.put("flag", "success");
		dataMap.put("result", result);
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 试算平衡校验页面
	 * 
	 * @return String
	 * @author lzshuai
	 * @throws Exception 
	 * @date 2017-6-28 下午4:08:26
	 */
	@RequestMapping(value = "/checkBalBalance")
	public String checkBalBalance(Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, String> balmap = cwInitBalFeign.doCheckBalDatalance(finBooks);

		dataMap.put("flag", "success");
		dataMap.put("datamap", balmap);
		String json = CwPublicUtil.gson.toJson(dataMap);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("jsondata", json);
		return "/component/finance/paramset/CwInitBal_checkBalBalance";
	}

	/**
	 * 
	 * 方法描述： 试算平衡校验
	 * 
	 * @return String
	 * @author lzshuai
	 * @throws Exception 
	 * @date 2017-6-28 下午5:00:12
	 */
	@RequestMapping(value = "/checkBalDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkBalDataAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		Map<String, String> balmap = cwInitBalFeign.doCheckBalDatalance(finBooks);

		dataMap.put("flag", "success");
		dataMap.put("datamap", balmap);
		return dataMap;
	}

}
