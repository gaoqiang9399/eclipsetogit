package app.component.finance.finreport.controller;

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
import com.google.gson.Gson;

import app.base.ServiceException;
import app.base.User;
import app.component.finance.account.entity.CwComItem;
import app.component.finance.cwtools.feign.CwMonthKnotFeign;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.finreport.entity.CwItemCalcList;
import app.component.finance.finreport.entity.CwReportAcount;
import app.component.finance.finreport.entity.CwReportItem;
import app.component.finance.finreport.entity.CwReportRelation;
import app.component.finance.finreport.entity.CwSearchReportList;
import app.component.finance.finreport.feign.CwReportItemFeign;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CwPublicUtil;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONObject;

/**
 * Title: CwReportItemAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 19 14:01:41 CST 2017
 **/
@Controller
@RequestMapping("/cwReportItem")
public class CwReportItemController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入cwComItemFeign
	@Autowired
	private CwReportItemFeign cwReportItemFeign;
	@Autowired
	private CwMonthKnotFeign cwMonthKnotFeign;
	@Autowired
	public CwToolsFeign cwToolsFeign;
	// 全局变量
	private String query;
	// 表单变量
	private FormData formfinreport0002;
	private FormService formService = new FormService();

	public CwReportItemController() {
		query = "";
	}

	/**
	 * 方法描述： 进入财务报表查询页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-19 下午2:24:58
	 */
	@RequestMapping(value = "/toSearchReportPage")
	public String toSearchReportPage(Model model, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		// Map<String, String> formMap = new HashMap<String, String>();
		// formMap.put("reportTypeId", reportTypeId);
		// cwSearchReportList = cwReportItemFeign.getSearchReportList(formMap);
		String weeks = cwMonthKnotFeign.getMaxCloseWeek(finBooks);
		String hcUseFlag = cwReportItemFeign.getHangCiFlag(reportTypeId);// 查看是否启用行次
		String comName = cwReportItemFeign.getComNameForReport();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("hcUseFlag", hcUseFlag);
		dataMap.put("reportTypeId", reportTypeId);
		dataMap.put("weeks", weeks);
		dataMap.put("comName", comName);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/finreport/CwReportItem_List";
	}

	/**
	 * 方法描述： 进入财务报表设置列表页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-9 下午2:11:11
	 */
	@RequestMapping(value = "/toReportItemSet")
	public String toReportItemSet(Model model, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		dataMap.put("reportTypeId", reportTypeId);
		dataMap.put("basePValue",cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.getTypeNum(reportTypeId),finBooks).getPvalue());
		dataMap.put("pCode", CWEnumBean.SYSPARAM_CODE.getTypeNum(reportTypeId));
		String hcUseFlag = cwReportItemFeign.getHangCiFlag(reportTypeId);// 查看是否启用行次
		dataMap.put("hcUseFlag", hcUseFlag);
		model.addAttribute("dataMap", dataMap);
		return "component/finance/finreport/CwReportItem_Set";
	}

	/**
	 * 方法描述： 进入财务报表预览页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-13 下午2:53:23
	 */
	@RequestMapping(value = "/toReportItemView")
	public String toReportItemView(Model model, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		dataMap.put("reportTypeId", reportTypeId);
		dataMap.put("basePValue",
				cwToolsFeign.getCwSysParamBeanByCode(CWEnumBean.SYSPARAM_CODE.getTypeNum(reportTypeId),finBooks).getPvalue());
		dataMap.put("pCode", CWEnumBean.SYSPARAM_CODE.getTypeNum(reportTypeId));
		String hcUseFlag = cwReportItemFeign.getHangCiFlag(reportTypeId);// 查看是否启用行次
		dataMap.put("hcUseFlag", hcUseFlag);
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/finreport/CwReportItem_View";
	}

	/**
	 * 方法描述： 进入报表项新增页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-9 下午2:14:06
	 */
	@RequestMapping(value = "/toReportItemAdd")
	public String toReportItemAdd(Model model, String reportItemId, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		formfinreport0002 = formService.getFormData("reportitem0001");
		CwReportItem cwReportItem = new CwReportItem();
		if (StringUtil.isNotEmpty(reportItemId)) {
			CwReportItem query = new CwReportItem();
			query.setReportItemId(reportItemId);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			query = cwReportItemFeign.getById(query,finBooks);
			cwReportItem.setReoprtItemLev(query.getReportItemId());
			cwReportItem.setReoprtLevName(query.getReportName());
			cwReportItem.setReportItemType(query.getReportItemType());
			dataMap.put("reoprtItemLev", reportItemId);
		} else {
			dataMap.put("reoprtItemLev", "");
		}
		dataMap.put("reportTypeId", reportTypeId);
		cwReportItem.setReportTypeId(reportTypeId);
		getObjValue(formfinreport0002, cwReportItem, formData);
		model.addAttribute("query", "");
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("formfinreport0002", formfinreport0002);
		model.addAttribute("cwReportItem", cwReportItem);
		return "/component/finance/finreport/CwReportItem_add";
	}

	/**
	 * 方法描述： 默认查询最后结账期报表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-19 下午2:34:42
	 */
	@RequestMapping(value = "/getSearchReportListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSearchReportListAjax(Integer pageNo, Integer pageSize, String tableId,
			String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
            formMap.put("regNo", User.getRegNo(request));
            formMap.put("regName", User.getRegName(request));
			Ipage ipage = this.getIpage();
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwReportItemFeign.getSearchReportList(formMap,finBooks);
			if(r.isOk()) {
				List<CwSearchReportList> list =(List<CwSearchReportList>) r.getResult();
				ipage.setResult(list);
				ipage.setPageCounts(list.size());
				ipage.setPageSize(pageSize);
				// ipage = cwReportItemFeign.getSearchReportList(ipage, formMap);
				// 返回相应的HTML方法
				JsonTableUtil jtu = new JsonTableUtil();
				String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
				ipage.setResult(tableHtml);
				dataMap.put("ipage", ipage);
				dataMap.put("tableHtml", tableHtml);
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
			
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			e.printStackTrace();
			// logger.error("查询凭证列表数据出错", e);
			throw e;
			
		}
		return dataMap;
	}

	/**
	 * 方法描述： 报表数据重置
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2018-1-9 下午5:04:46
	 */
	@RequestMapping(value = "/resetReportDataAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetReportDataAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);

			String finBooks = (String)request.getSession().getAttribute("finBooks");

			cwReportItemFeign.resetReportData(formMap,finBooks);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			// logger.error("查询凭证列表数据出错", e);
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/findByPageAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			CwReportItem cwReportItem = new CwReportItem();
			cwReportItem.setCustomQuery(ajaxData);// 自定义查询参数赋值
			cwReportItem.setReportTypeId(reportTypeId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			Map<String, Object> params = new HashMap<>();
			params.put("cwReportItem", cwReportItem);
			ipage.setParams(params);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			ipage = cwReportItemFeign.findByPage(ipage,finBooks);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取财务报表设置列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-8 下午5:30:06
	 */
	@RequestMapping(value = "/getSetReportListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSetReportListAjax(String tableId, String tableType, String reportTypeId,
			String itemflag) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			Map<String, String> formMap = new HashMap<String, String>();
			formMap.put("reportTypeId", reportTypeId);
			formMap.put("itemflag", itemflag);
			Ipage ipage = this.getIpage();
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			List<CwSearchReportList> list = cwReportItemFeign.getSetReportList(formMap,finBooks);
			ipage.setResult(list);
			ipage.setPageCounts(list.size());
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取财务预览报表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-13 下午3:25:57
	 */
	@RequestMapping(value = "/getReportViewListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getReportViewListAjax(String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {

			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			List<CwSearchReportList> list = cwReportItemFeign.getReportViewList(formMap,finBooks);
			ipage.setResult(list);
			ipage.setPageCounts(list.size());
			// ipage = cwReportItemFeign.getSearchReportList(ipage, formMap);
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 进入报表项编辑页面
	 * 
	 * @return String
	 * @author Javelin
	 * @throws Exception
	 * @date 2017-1-23 上午9:11:12
	 */
	@RequestMapping(value = "/toDeployPage")
	public String toDeployPage(Model model, String reportItemId) throws Exception {
		Map<String, Object> formData = new HashMap<String, Object>();
		formfinreport0002 = formService.getFormData("finreport0002");
		CwReportItem cwReportItem = new CwReportItem();
		// cwReportItem.setReportTypeId(reportTypeId);
		cwReportItem.setReportItemId(reportItemId);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwReportItem = cwReportItemFeign.getReportItemDetail(cwReportItem,finBooks);
		getObjValue(formfinreport0002, cwReportItem, formData);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("reportItemId", reportItemId);
		dataMap.put("isInput", cwReportItem.getIsInput());
		model.addAttribute("cwReportItem", cwReportItem);
		model.addAttribute("formfinreport0002", formfinreport0002);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", query);
		return "/component/finance/finreport/CwReportDeploy";
	}

	/**
	 * 方法描述： 进入报表查询金额组成公式列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-2-8 下午3:01:15
	 */
	@RequestMapping(value = "/toItemCalcPage")
	public String toItemCalcPage(Model model, String ajaxData) throws Exception {
		Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
		String finBooks = (String)request.getSession().getAttribute("finBooks");
		List<CwItemCalcList> cwItemCalcList = cwReportItemFeign.getItemCalcViewList(formMap,finBooks);
		model.addAttribute("cwItemCalcList", cwItemCalcList);
		return "/component/finance/finreport/CwReportItemCalc";
	}

	/**
	 * 方法描述： 获取报表项公式列表数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-1-23 下午3:37:45
	 */
	@RequestMapping(value = "/getItemCalcSetListAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getItemCalcSetListAjax(String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			List<CwReportRelation> list = cwReportItemFeign.getItemCalcSetList(formMap,finBooks);
			ipage.setResult(list);
			ipage.setPageCounts(list.size());
			// 返回相应的HTML方法
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.error("查询凭证列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 新增报表项
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-9 下午4:30:11
	 */
	@RequestMapping(value = "/addItemAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItemAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formfinreport0002 = formService.getFormData("reportitem0001");
			getFormValue(formfinreport0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfinreport0002)) {
				CwReportItem cwReportItem = new CwReportItem();
				setObjValue(formfinreport0002, cwReportItem);
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				cwReportItemFeign.insert(cwReportItem,finBooks);
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
	 * 方法描述： 修改报表排序
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-10 上午9:12:55
	 */
	@RequestMapping(value = "/updateOrderAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrderAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			R r = cwReportItemFeign.updateOrder(formMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				// dataMap.put("msg", "修改排序成功");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}

		} catch (Exception e) {
			dataMap.put("flag", "error");
			// dataMap.put("msg", "修改排序失败");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
			throw new ServiceException(e);
		}
		return dataMap;
	}

	/**
	 * 方法描述： 修改报表项公式与详情数据
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-10 下午2:18:17
	 */
	@RequestMapping(value = "/updateDeployAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeployAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = getMapByJson(ajaxData);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			R r = cwReportItemFeign.updateDeployData(paramMap,finBooks);
			if(r.isOk()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw new ServiceException(e);
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获取报表检查未配置列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-13 下午2:21:48
	 */
	@RequestMapping(value = "/checkPickComAjax")
	public String checkPickComAjax(String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("reportTypeId", reportTypeId);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			
			R r = cwReportItemFeign.getCheckPickComList(paramMap,finBooks);
			if(r.isOk()) {
				List<CwComItem> list = (List<CwComItem>) r.getResult();
				dataMap.put("data", list);
				dataMap.put("size", list.size());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				if (list.size() > 0) {
					return "/component/finance/finreport/CwReportNoItem";
				}
				
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}


		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw new ServiceException(e);
		}
		//FIXME 返回值
		return "/component/finance/finreport/CwReportNoItem";
	}


	/**
	 * 获取列表数据
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, CwReportItem cwReportItem, String tableId) throws Exception {
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", cwReportItemFeign.getAll(cwReportItem,finBooks), null,true);
		dataMap.put("tableData",tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		formfinreport0002 = formService.getFormData("finreport0002");
		CwReportItem cwReportItem = new CwReportItem();
		Ipage ipage = this.getIpage();
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		Map<String, Object> params = new HashMap<>();
		params.put("cwReportItem", cwReportItem);
		ipage.setParams(params);
		List<CwReportItem> cwReportItemList = (List<CwReportItem>) cwReportItemFeign.findByPage(ipage,finBooks).getResult();
		model.addAttribute("formfinreport0002", formfinreport0002);
		model.addAttribute("cwReportItemList", cwReportItemList);
		model.addAttribute("reportTypeId", reportTypeId);
		return "component/finance/finreport/CwReportItem_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		formfinreport0002 = formService.getFormData("finreport0002");
		CwReportItem cwReportItem = new CwReportItem();
		cwReportItem.setReportTypeId(reportTypeId);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		List<CwReportItem> cwReportItemList = cwReportItemFeign.getAll(cwReportItem,finBooks);
		model.addAttribute("formfinreport0002", formfinreport0002);
		model.addAttribute("cwReportItemList", cwReportItemList);
		return "/component/finance/finreport/CwReportItem_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formfinreport0002 = formService.getFormData("reportitem0001");
			getFormValue(formfinreport0002, getMapByJson(ajaxData));
			Map<String, Object> mapByJson = getMapByJson(ajaxData);// 2017年10月13日 修改流入流出的bug

			if (this.validateFormData(formfinreport0002)) {
				CwReportItem cwReportItem = new CwReportItem();
				setObjValue(formfinreport0002, cwReportItem);
				String reportTypeId2 = cwReportItem.getReportTypeId();
				if ("002".equals(reportTypeId2)) {
					String isInput = (String) mapByJson.get("isInput");
					cwReportItem.setIsInput(isInput);
				}
				String finBooks = (String)request.getSession().getAttribute("finBooks");
				cwReportItemFeign.insert(cwReportItem,finBooks);
				getTableData(dataMap, cwReportItem, tableId);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			formfinreport0002 = formService.getFormData("finreport0002");
			getFormValue(formfinreport0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfinreport0002)) {
				CwReportItem cwReportItem = new CwReportItem();
				setObjValue(formfinreport0002, cwReportItem);
				String finBooks = (String) request.getSession().getAttribute("finBooks");
				cwReportItemFeign.update(cwReportItem,finBooks);
				getTableData(dataMap, cwReportItem, tableId);// 获取列表
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
	public Map<String, Object> getByIdAjax(String reportTypeId, String reportItemId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formfinreport0002 = formService.getFormData("finreport0002");
		CwReportItem cwReportItem = new CwReportItem();
		cwReportItem.setReportTypeId(reportTypeId);
		cwReportItem.setReportItemId(reportItemId);
		String finBooks = (String) request.getSession().getAttribute("finBooks");
		cwReportItem = cwReportItemFeign.getById(cwReportItem,finBooks);
		getObjValue(formfinreport0002, cwReportItem, formData);
		if (cwReportItem != null) {
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
	public Map<String, Object> deleteAjax(String tableId, String ajaxData, String reportTypeId, String reportItemId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CwReportItem cwReportItem = new CwReportItem();
		cwReportItem.setReportTypeId(reportTypeId);
		cwReportItem.setReportItemId(reportItemId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			cwReportItem = (CwReportItem) JSONObject.toBean(jb, CwReportItem.class);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwReportItemFeign.delete(cwReportItem,finBooks);
			getTableData(dataMap, cwReportItem, tableId);// 获取列表
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
	 * 获取报表项树形
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDataItemForTree")
	@ResponseBody
	public Map<String, Object> getDataItemForTree(String reportTypeId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CwReportItem ci = new CwReportItem();
			ci.setReportTypeId(reportTypeId);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			List<Map<String, String>> rlst = cwReportItemFeign.getDataItemForTree(ci,finBooks);
			dataMap.put("items", rlst);
		} catch (Exception e) {
//			logger.error("getDataItemForTree方法出错，执行action层失败，抛出异常，");
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 获取ReportAccount实体
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getReportAccountAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getReportAccountAjax(String reportTypeId, String searchWeek) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CwReportAcount cwReportAcount = new CwReportAcount();
			cwReportAcount.setBbType(reportTypeId);
			cwReportAcount.setBbWeek(searchWeek);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			cwReportAcount = cwReportItemFeign.getReportAccount(cwReportAcount,finBooks);
			dataMap.put("cwReportAcount", cwReportAcount);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
//			logger.error("getDataItemForTree方法出错，执行action层失败，抛出异常，");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：修改行次的功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-13 下午6:26:26
	 */
	@RequestMapping(value = "/updateHangCiAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHangCiAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			String result = cwReportItemFeign.updateReportDataHangCi(formMap,finBooks);
			dataMap.put("flag", "success");

			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 
	 * 方法描述： 修改列表行次
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-13 下午6:26:40
	 */
	@RequestMapping(value = "/updateHangCiForItemAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHangCiForItemAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> formMap = new Gson().fromJson(ajaxData, Map.class);
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			String result = cwReportItemFeign.updateReportItemHangCiForItem(formMap,finBooks);
			dataMap.put("flag", "success");

			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;

	}

	@RequestMapping(value = "/CwReportNoItem")
	public String CwReportNoItem(Model model, String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("reportTypeId", reportTypeId);
			String finBooks = (String)request.getSession().getAttribute("finBooks");
			 R r = cwReportItemFeign.getCheckPickComList(paramMap,finBooks);
			if(r.isOk()) {
				List<CwComItem> list=(List<CwComItem>) r.getResult();
				dataMap.put("data", list);
				dataMap.put("dataJson", new Gson().toJson(list));
				dataMap.put("size", list.size());
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", r.getMsg());
			}


		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw new ServiceException(e);
		}
		model.addAttribute("dataMap", dataMap);
		return "/component/finance/finreport/CwReportNoItem";
	}

	/**
	 * 
	 * 方法描述： 启用行次
	 * 
	 * @return String
	 * @author lzshuai
	 * @date 2017-10-13 下午6:27:08
	 */
	@RequestMapping(value = "/updateTrOpenAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTrOpenAjax(String reportTypeId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("reportTypeId", reportTypeId);
			paramMap.put("type", "1");
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			String result = cwReportItemFeign.updateTrOpenCloseAjax(paramMap,finBooks);
			dataMap.put("flag", "success");

			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 
	 * 方法描述： 禁用行次
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-10-13 下午6:29:32
	 */
	@RequestMapping(value = "/updateTrCloseAjax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateTrCloseAjax(String reportTypeId) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("reportTypeId", reportTypeId);
			paramMap.put("type", "0");
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			String result = cwReportItemFeign.updateTrOpenCloseAjax(paramMap,finBooks);
			dataMap.put("flag", "success");

			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;

	}

	/**
	 * 
	 * 方法描述： 报表导出的功能
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-12-8 下午2:51:01
	 */
	@RequestMapping(value = "/downReportToExcel")
	@ResponseBody
	public ResponseEntity<byte[]> downReportToExcel(String reportTypeId, String weeks, String bbLb) throws Exception {

		ActionContext.initialize(request, response);
		try {// 获取文件
			String finBooks = (String) request.getSession().getAttribute("finBooks");
			//Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			String reportName = "";
			if ("001".equals(reportTypeId)) {
				reportName = "资产负债表" + weeks;
			} else if ("002".equals(reportTypeId)) {
				reportName = "现金流量表" + weeks;
			} else {
				reportName = "利润表" + weeks;
			}
			String filename = reportName + "_" + (WaterIdUtil.getWaterId()) + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("reportTypeId", reportTypeId);
			paramMap.put("bbLb", bbLb);
			paramMap.put("weeks", weeks);
			//FIXME excel 下载  HSSFWorkBook的jar包
			//HSSFWorkBook demoWorkFeignok = cwReportItemFeign.downReportToExcel(paramMap);
			byte[] bytes = cwReportItemFeign.downReportToExcelBinary(paramMap, finBooks);
			return new ResponseEntity<byte[]>(bytes,HttpStatus.CREATED);
		} catch (Exception e) {
			// logger.error("downloadExcel方法出错，执行action层失败，抛出异常，"+e.getMessage(),e);
			e.printStackTrace();
			throw e;
		}
	}

}
