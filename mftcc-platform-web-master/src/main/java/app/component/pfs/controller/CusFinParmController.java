package app.component.pfs.controller;

import java.io.File;
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

import app.base.ServiceException;
import app.component.nmd.entity.ParmDic;
import app.component.nmdinterface.NmdInterfaceFeign;
import app.component.pfs.entity.CusFinFormula;
import app.component.pfs.entity.CusFinParm;
import app.component.pfs.feign.CusFinFormulaFeign;
import app.component.pfs.feign.CusFinParmFeign;
import app.tech.upload.UploadUtil;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 财务报表-财务指标项控制
 * 
 * @author LJW
 */
@Controller
@RequestMapping("/cusFinParm")
public class CusFinParmController extends BaseFormBean {

	@Autowired
	private NmdInterfaceFeign nmdInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private CusFinParmFeign cusFinParmFeign;
	@Autowired
	private CusFinFormulaFeign cusFinFormulaFeign;
	// 全局变量

	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinParm_List";
	}

	/**
	 * @author czk
	 * @Description: 获得报表项选择页面 date 2016-11-17
	 */
	@RequestMapping(value = "/getListPageForSelect")
	public String getListPageForSelect(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinParm_ListForSelect";
	}

	/**
	 * @author czk
	 * @param reportType
	 * @Description: 报表预览 date 2016-11-17
	 */
	@RequestMapping(value = "/getPageReportPreview")
	public String getPageReportPreview(Model model, String ajaxData, String reportType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinParm cusFinParm = new CusFinParm();
		cusFinParm.setReportType(reportType);

		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("REPORT_TYPE");
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		String reportName = "";
		for (int i = 0; i < parmDicList.size(); i++) {
			parmDic =(ParmDic) JsonStrHandling.handlingStrToBean( parmDicList.get(i), ParmDic.class);
			if (parmDic.getOptCode().equals(reportType)) {
				reportName = parmDic.getOptName();
				break;
			}
		}
		if (reportName == null) {
			if ("1".equals(reportType)) {
				reportName = "资产负债表";
			} else if ("2".equals(reportType)) {
				reportName = "利润分配表";
			} else if ("3".equals(reportType)) {
				reportName = "现金流量表";
			}else {
			}
		}
		model.addAttribute("reportType", reportType);
		model.addAttribute("reportName", reportName);
		cusFinParm.setUseFlag("1");
		List<CusFinParm> cusFinParmList;
		if ("1".equals(reportType)) {
			cusFinParm.setZcfzType("1");
			cusFinParmList = cusFinParmFeign.getList(cusFinParm);
			cusFinParm.setZcfzType("2");
			List<CusFinParm> cusFinParmList1 = cusFinParmFeign.getList(cusFinParm);
			for (int i = 0; i < cusFinParmList.size(); i++) {
				if (i < cusFinParmList1.size()) {
					cusFinParmList.get(i).setCnt1(cusFinParmList1.get(i).getCnt());
					cusFinParmList.get(i).setCodeName1(cusFinParmList1.get(i).getCodeName());
					cusFinParmList.get(i).setFontShowStyle1(cusFinParmList1.get(i).getFontShowStyle());
				} else {
					break;
				}
			}
			if (cusFinParmList1.size() > cusFinParmList.size()) {
				for (int i = cusFinParmList.size(); i < cusFinParmList1.size(); i++) {
					CusFinParm cusFiPa = new CusFinParm();
					cusFiPa.setCnt1(cusFinParmList1.get(i).getCnt());
					cusFiPa.setCodeName1(cusFinParmList1.get(i).getCodeName());
					cusFiPa.setFontShowStyle1(cusFinParmList1.get(i).getFontShowStyle());
					cusFiPa.setCnt("");
					cusFiPa.setCodeName("");
					cusFiPa.setFontShowStyle("");
					cusFinParmList.add(cusFiPa);
				}
			}
		} else {
			cusFinParmList = cusFinParmFeign.getList(cusFinParm);
		}
		model.addAttribute("cusFinParmList", cusFinParmList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinParm_preview";
	}

	/**
	 * 打开报表元素配置页面
	 * 
	 * @author LJW date 2017-4-12
	 * @param reportType
	 */
	@RequestMapping(value = "/getListPageNew")
	public String getListPageNew(Model model, String ajaxData, String reportType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		CusFinParm cusFinParm = new CusFinParm();
		cusFinParm.setReportType(reportType);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("REPORT_TYPE");
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);
		String reportName = null;
		for (int i = 0; i < parmDicList.size(); i++) {
			parmDic =(ParmDic) JsonStrHandling.handlingStrToBean( parmDicList.get(i), ParmDic.class);
			if (parmDic.getOptCode().equals(reportType)) {
				reportName = parmDic.getOptName();
				break;
			}
		}
		if (reportName == null) {
			if ("1".equals(reportType)) {
				reportName = "资产负债表";
			} else if ("2".equals(reportType)) {
				reportName = "利润分配表";
			} else if ("3".equals(reportType)) {
				reportName = "现金流量表";
			}else {
			}
		}
		FormData formpfs5008 = formService.getFormData("pfs5009");
		getObjValue(formpfs5008, cusFinParm);
		List<CusFinParm> cusFinParmList;
		model.addAttribute("reportType", reportType);
		model.addAttribute("reportName", reportName);
		model.addAttribute("formpfs5008", formpfs5008);
		if ("1".equals(reportType)) {
			cusFinParm.setZcfzType("1");
			cusFinParmList = cusFinParmFeign.getList(cusFinParm);
			cusFinParm.setZcfzType("2");
			List<CusFinParm> cusFinParmList1 = cusFinParmFeign.getList(cusFinParm);
			model.addAttribute("cusFinParmList", cusFinParmList);
			model.addAttribute("cusFinParmList1", cusFinParmList1);
			model.addAttribute("query", "");
			return "/component/pfs/CusFinCapParm_List";
		} else {
			cusFinParmList = cusFinParmFeign.getList(cusFinParm);
			model.addAttribute("cusFinParmList", cusFinParmList);
			model.addAttribute("query", "");
			return "/component/pfs/CusFinCashOrProParm_List";
		}
	}

	/**
	 * @author czk
	 * @Description: 获得显示报表类型的页面 date 2016-11-17
	 */
	@RequestMapping(value = "/getReportPage")
	public String getReportPage(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		ParmDic parmDic = new ParmDic();
		parmDic.setKeyName("REPORT_TYPE");
		List<ParmDic> parmDicList = nmdInterfaceFeign.findAllParmDicByKeyName(parmDic);

		model.addAttribute("parmDicList", parmDicList);
		model.addAttribute("query", "");
		return "/component/pfs/CusFinReport_List";
	}

	/**
	 * @author czk
	 * @param reportType
	 * @param zcfzType 
	 * @Description: 用来选择报表项的页面的异步加载 date 2016-11-17
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String reportType, String zcfzType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinParm cusFinParm = new CusFinParm();
		try {
			cusFinParm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			if (StringUtil.isEmpty(reportType)) {
				reportType = null;
			} else {
				if ("1".equals(reportType)) {// 如果是资产负债表，则要将资产类和负债类分开
					if (StringUtil.isEmpty(zcfzType)) {
						zcfzType = null;
					}
					cusFinParm.setZcfzType(zcfzType);
				}
			}
			cusFinParm.setReportType(reportType);
			// 只查询启用的
			cusFinParm.setUseFlag("1");
			cusFinParm.setCriteriaList(cusFinParm, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("cusFinParm", cusFinParm));
			ipage = cusFinParmFeign.findByPage(ipage);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
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

	@RequestMapping(value = "/insertParmByExcelAjax")
	@ResponseBody
	public Map<String, Object> insertParmByExcelAjax(String ajaxData, String allPath) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String uploadFinPath = PropertiesUtil.getUploadProperty("uploadFinFilePath");
			allPath = uploadFinPath + File.separator + allPath;
			cusFinParmFeign.insertParmByExcel(allPath);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (ServiceException e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "操作失败！文件格式错误!");
			e.printStackTrace();
		}
		return dataMap;
	}

	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax(String ajaxData, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinParm cusFinParm = new CusFinParm();
		FormData formpfs5008 = formService.getFormData("pfs5008");
		getObjValue(formpfs5008, cusFinParm);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs5008, "bigFormTag", query);
		dataMap.put("formHtml", formHtml);
		return dataMap;
	}

	/**
	 * AJAX新增-财务指标元素
	 * @param ajaxDataList 
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, Object ajaxDataList) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			List<CusFinFormula> cusFinFormulaList = (List<CusFinFormula>) JSONArray.toList(jsonArray, new CusFinFormula(),
					new JsonConfig());
			FormData formpfs5008 = formService.getFormData("pfs5009");
			getFormValue(formpfs5008, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs5008)) {
				CusFinParm cusFinParm = new CusFinParm();
				setObjValue(formpfs5008, cusFinParm);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("cusFinParm", cusFinParm);
				paramMap.put("cusFinFormulaList", cusFinFormulaList);
				cusFinParm = cusFinParmFeign.insert(paramMap);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				dataMap.put("cusFinParm", cusFinParm);
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

	// 更新计算项
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String ajaxDataList) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JSONArray jsonArray = JSONArray.fromObject(ajaxDataList);
			List<CusFinFormula> cusFinFormulaList = (List<CusFinFormula>) JSONArray.toList(jsonArray, new CusFinFormula(),
					new JsonConfig());
			FormData formpfs5008 = formService.getFormData("pfs5009");
			getFormValue(formpfs5008, getMapByJson(ajaxData));
			if (this.validateFormData(formpfs5008)) {
				CusFinParm cusFinParm = new CusFinParm();
				setObjValue(formpfs5008, cusFinParm);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("cusFinParm", cusFinParm);
				paramMap.put("cusFinFormulaList", cusFinFormulaList);
				cusFinParm = cusFinParmFeign.update1(paramMap);
				// cusFinParm=cusFinParmFeign.getById(cusFinParm);
				// List<CusFinParm> list = new ArrayList<CusFinParm>();
				// list.add(cusFinParm);
				// getTableData(list);//获取列表
				dataMap.put("cusFinParm", cusFinParm);
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
	 * 根据财务报表指标项和报表类型查询该项是否有计算项
	 * 
	 * @author LJW date 2017-4-12
	 * @param codeColumn 
	 * @param reportType 
	 * @param query 
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String codeColumn, String reportType, String query) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpfs5008 = formService.getFormData("pfs5009");
		CusFinParm cusFinParm = new CusFinParm();
		cusFinParm.setCodeColumn(codeColumn);
		System.out.println(codeColumn);
		cusFinParm.setReportType(reportType);
		cusFinParm = cusFinParmFeign.getById(cusFinParm);
		getObjValue(formpfs5008, cusFinParm);
		JsonFormUtil jfu = new JsonFormUtil();
		String formHtml = jfu.getJsonStr(formpfs5008, "bootstarpTag", "");
		List<CusFinFormula> cusFinFormulaList = null;
		if ("2".equals(cusFinParm.getInputType())) {// 运算项
			CusFinFormula cusFinFormula = new CusFinFormula();
			cusFinFormula.setCodeColumn(codeColumn);
			cusFinFormulaList = cusFinFormulaFeign.getByCodeColumn(cusFinFormula);
		}
		dataMap.put("cusFinParm", cusFinParm);
		dataMap.put("flag", "success");
		dataMap.put("formHtml", formHtml);
		dataMap.put("cusFinFormulaList", cusFinFormulaList);
		dataMap.put("query", "");
		return dataMap;
	}

	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String codeColumn, String reportType, String cnt) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CusFinParm cusFinParm = new CusFinParm();
		cusFinParm.setCodeColumn(codeColumn);
		cusFinParm.setReportType(reportType);
		cusFinParm.setCnt(cnt);
		try {
			cusFinParmFeign.del(cusFinParm);
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

	private void getTableData(Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = "";// jtu.getJsonStr(tableId,"tableTag",
								// cusFinParmFeign.getAllList(cusFinParm),
								// null,true);
		dataMap.put("tableData", tableHtml);
	}

	private void getTableData(List<CusFinParm> list, String tableId, Map<String, Object> dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", list, null, true);
		dataMap.put("tableData", tableHtml);
	}

//	@RequestMapping(value = "/input")
//	public String input(Model model) throws Exception {
//		ActionContext.initialize(request, response);
//		model.addAttribute("query", "");
//		return "input";
//	}
//
//	@RequestMapping(value = "/insert")
//	public String insert(Model model) throws Exception {
//		FormService formService = new FormService();
//		ActionContext.initialize(request, response);
//		FormData formpfs5009 = formService.getFormData("formpfs5009");
//		getFormValue(formpfs5009);
//		CusFinParm cusFinParm = new CusFinParm();
//		setObjValue(formpfs5009, cusFinParm);
//		cusFinParmFeign.insert(cusFinParm, null);
//		// 以下操作是为了跳转到详情查看页面
//		FormData formpfs5010 = formService.getFormData("formpfs5010");
//		getObjValue(formpfs5010, cusFinParm);
//		String codeColumn = cusFinParm.getCodeColumn();
//		String reportType = cusFinParm.getReportType();
//		String query = "query";
//		
//		model.addAttribute("formpfs5009", formpfs5009);
//		model.addAttribute("formpfs5010", formpfs5010);
//		model.addAttribute("codeColumn", codeColumn);
//		model.addAttribute("reportType", reportType);
//		model.addAttribute("query", query);
//		return "detail";
//	}

	/**
	 * 新增验证
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs5009 = formService.getFormData("formpfs5009");
		getFormValue(formpfs5009);
		validateFormData(formpfs5009);
		// 将值赋给对象用来进行进一步的验证
		CusFinParm cusFinParm = new CusFinParm();
		setObjValue(formpfs5009, cusFinParm);
	}

//	@RequestMapping(value = "/update")
//	public String update(Model model, String ajaxData) throws Exception {
//		FormService formService = new FormService();
//		ActionContext.initialize(request, response);
//		FormData formpfs5010 = formService.getFormData("formpfs5010");
//		getFormValue(formpfs5010);
//		CusFinParm cusFinParm = new CusFinParm();
//		setObjValue(formpfs5010, cusFinParm);
//		cusFinParmFeign.update(cusFinParm);
//		getObjValue(formpfs5010, cusFinParm);
//		String codeColumn = cusFinParm.getCodeColumn();
//		String reportType = cusFinParm.getReportType();
//		// cnt=CusFinParm.getCnt();
//		String query = "query";
//		model.addAttribute("formpfs5010", formpfs5010);
//		model.addAttribute("codeColumn", codeColumn);
//		model.addAttribute("reportType", reportType);
//		model.addAttribute("query", query);
//		return "detail";
//	}

	/**
	 * 修改验证
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpfs5010 = formService.getFormData("formpfs5010");
		getFormValue(formpfs5010);
		validateFormData(formpfs5010);
		CusFinParm cusFinParm = new CusFinParm();
		setObjValue(formpfs5010, cusFinParm);
	}

//	@RequestMapping(value = "/del")
//	public String del(Model model, String ajaxData, String codeColumn, String cnt, String reportType) throws Exception {
//		FormService formService = new FormService();
//		ActionContext.initialize(request, response);
//		CusFinParm cusFinParm = new CusFinParm();
//		cusFinParm.setCodeColumn(codeColumn);
//		cusFinParm.setReportType(reportType);
//		cusFinParm.setCnt(cnt);
//		cusFinParmFeign.del(cusFinParm);
//		this.setMessage("操作成功");
//		model.addAttribute("query", "");
//		return "return_list";
//	}
//
//	@RequestMapping(value = "/getById")
//	public String getById(Model model, String ajaxData, String codeColumn, String reportType, String view) throws Exception {
//		FormService formService = new FormService();
//		ActionContext.initialize(request, response);
//		CusFinParm cusFinParm = new CusFinParm();
//		cusFinParm.setCodeColumn(codeColumn);
//		cusFinParm.setReportType(reportType);
//		cusFinParm = cusFinParmFeign.getById(cusFinParm);
//		FormData formpfs5010 = formService.getFormData("formpfs5010");
//		getObjValue(formpfs5010, cusFinParm);
//		Object query = null;
//		if (view.equals("view")) {
//			query = "query";
//		}
//		model.addAttribute("formpfs5010", formpfs5010);
//		model.addAttribute("query", query);
//		return "detail";
//	}

}
