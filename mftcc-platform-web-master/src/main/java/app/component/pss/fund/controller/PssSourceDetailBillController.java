package app.component.pss.fund.controller;

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

import app.component.pss.fund.entity.PssSourceDetailBill;
import app.component.pss.fund.feign.PssSourceDetailBillFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: PssSourceDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 18:03:13 CST 2017
 **/
@Controller
@RequestMapping("/pssSourceDetailBill")
public class PssSourceDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSourceDetailBillFeign pssSourceDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssSourceDetailBill_List";
	}

	/**
	 * 方法描述：收款、付款新增初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-20 下午05:56:06
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			List<PssSourceDetailBill> pssSourceDetailBillList = (List<PssSourceDetailBill>) ipage.getResult();
			if (pssSourceDetailBillList == null || pssSourceDetailBillList.size() == 0) {
				pssSourceDetailBillList = new ArrayList<PssSourceDetailBill>();
				for (int i = 0; i < 3; i++) {
					pssSourceDetailBill = new PssSourceDetailBill();
					pssSourceDetailBill.setSequence(i + 1);
					pssSourceDetailBillList.add(pssSourceDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssSourceDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(pssSourceDetailBillList.size());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("新增收款、付款列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：收款、付款、核销详情初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-25 下午05:56:06
	 */
	@RequestMapping(value = "/getSourceDetailBillListForRecPayCancelAjax")
	@ResponseBody
	public Map<String, Object> getSourceDetailBillListForRecPayCancelAjax(String billNo,String cancelDetailType,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			pssSourceDetailBill.setBillNo(billNo);
			if (null != cancelDetailType && !"".equals(cancelDetailType)) {
				pssSourceDetailBill.setCancelDetailType(cancelDetailType);
			}
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssSourceDetailBillFeign.findByPage(ipage, pssSourceDetailBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			// ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("查看收款、付款、核销列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：收款单选择源单查询
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-22 下午02:56:06
	 */
	@RequestMapping(value = "/getListPageForRec")
	public String getListPageForRec(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssSourceDetailBill_ListForRec";
	}

	/**
	 * 方法描述：收款单选择源单查询（选择）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-22 下午02:56:06
	 */
	@RequestMapping(value = "/getListPageForRecAjax")
	@ResponseBody
	public Map<String, Object> getListPageForRecAjax(String ajaxData,String cusNo,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			pssSourceDetailBill.setCustomQuery(ajaxData);
			pssSourceDetailBill.setCustomSorts(ajaxData);
			pssSourceDetailBill.setCriteriaList(pssSourceDetailBill, ajaxData);
			pssSourceDetailBill.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForRec(ipage, pssSourceDetailBill);
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
	 * 方法描述：收款单选择源单查询（返回）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-25 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForRecBackAjax")
	@ResponseBody
	public Map<String, Object> getListPageForRecBackAjax(String sourceBillNos,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			// pssSourceDetailBill.setCustomQuery(ajaxData);
			// pssSourceDetailBill.setCustomSorts(ajaxData);
			// pssSourceDetailBill.setCriteriaList(pssSourceDetailBill,
			// ajaxData);
			pssSourceDetailBill.setSourceBillNo(sourceBillNos);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForRecBack(ipage, pssSourceDetailBill);
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
	 * 方法描述：付款单选择源单查询
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-26 下午04:56:06
	 */
	@RequestMapping(value = "/getListPageForPay")
	public String getListPageForPay(Model model, String supplierId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("supplierId", supplierId);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssSourceDetailBill_ListForPay";
	}

	/**
	 * 方法描述：付款单选择源单查询（选择）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-26 下午04:56:06
	 */
	@RequestMapping(value = "/getListPageForPayAjax")
	@ResponseBody
	public Map<String, Object> getListPageForPayAjax(String ajaxData,String supplierId,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			pssSourceDetailBill.setCustomQuery(ajaxData);
			pssSourceDetailBill.setCustomSorts(ajaxData);
			pssSourceDetailBill.setCriteriaList(pssSourceDetailBill, ajaxData);
			pssSourceDetailBill.setSupplierId(supplierId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForPay(ipage, pssSourceDetailBill);
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
	 * 方法描述：付款单选择源单查询（返回）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-27 上午08:56:06
	 */
	@RequestMapping(value = "/getListPageForPayBackAjax")
	@ResponseBody
	public Map<String, Object> getListPageForPayBackAjax(String sourceBillNos,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			// pssSourceDetailBill.setCustomQuery(ajaxData);
			// pssSourceDetailBill.setCustomSorts(ajaxData);
			// pssSourceDetailBill.setCriteriaList(pssSourceDetailBill,
			// ajaxData);
			pssSourceDetailBill.setSourceBillNo(sourceBillNos);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForPayBack(ipage, pssSourceDetailBill);
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
	 * 方法描述：核销单选择预收单据源单查询
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-28 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefRec")
	public String getListPageForBefRec(Model model, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("cusNo", cusNo);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssSourceDetailBill_ListForBefRec";
	}

	/**
	 * 方法描述：核销单选择预收单据源单查询（选择）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-28 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefRecAjax")
	@ResponseBody
	public Map<String, Object> getListPageForBefRecAjax(String ajaxData,String cusNo,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			pssSourceDetailBill.setCustomQuery(ajaxData);
			pssSourceDetailBill.setCustomSorts(ajaxData);
			pssSourceDetailBill.setCriteriaList(pssSourceDetailBill, ajaxData);
			pssSourceDetailBill.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForBefRec(ipage, pssSourceDetailBill);
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
	 * 方法描述：核销单选择预收单据源单查询（返回）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-28 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefRecBackAjax")
	@ResponseBody
	public Map<String, Object> getListPageForBefRecBackAjax(String sourceBillNos,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			// pssSourceDetailBill.setCustomQuery(ajaxData);
			// pssSourceDetailBill.setCustomSorts(ajaxData);
			// pssSourceDetailBill.setCriteriaList(pssSourceDetailBill,
			// ajaxData);
			pssSourceDetailBill.setSourceBillNo(sourceBillNos);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForBefRecBack(ipage, pssSourceDetailBill);
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
	 * 方法描述：核销单选择预付单据源单查询
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-29 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefPay")
	public String getListPageForBefPay(Model model, String supplierId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("supplierId", supplierId);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssSourceDetailBill_ListForBefPay";
	}

	/**
	 * 方法描述：核销单选择预付单据源单查询（选择）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-29 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefPayAjax")
	@ResponseBody
	public Map<String, Object> getListPageForBefPayAjax(String ajaxData,String supplierId,Integer pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			pssSourceDetailBill.setCustomQuery(ajaxData);
			pssSourceDetailBill.setCustomSorts(ajaxData);
			pssSourceDetailBill.setCriteriaList(pssSourceDetailBill, ajaxData);
			pssSourceDetailBill.setSupplierId(supplierId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForBefPay(ipage, pssSourceDetailBill);
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
	 * 方法描述：核销单选择预付单据源单查询（返回）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-29 上午10:56:06
	 */
	@RequestMapping(value = "/getListPageForBefPayBackAjax")
	@ResponseBody
	public Map<String, Object> getListPageForBefPayBackAjax(String sourceBillNos,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			// pssSourceDetailBill.setCustomQuery(ajaxData);
			// pssSourceDetailBill.setCustomSorts(ajaxData);
			// pssSourceDetailBill.setCriteriaList(pssSourceDetailBill,
			// ajaxData);
			pssSourceDetailBill.setSourceBillNo(sourceBillNos);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssSourceDetailBillFeign.findByPageForBefPayBack(ipage, pssSourceDetailBill);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsssourcedetailbill0002 = formService.getFormData("psssourcedetailbill0002");
			getFormValue(formpsssourcedetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssourcedetailbill0002)) {
				PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
				setObjValue(formpsssourcedetailbill0002, pssSourceDetailBill);
				pssSourceDetailBillFeign.insert(pssSourceDetailBill);
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
			throw new Exception(e.getMessage());
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
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		try {
			FormData formpsssourcedetailbill0002 = formService.getFormData("psssourcedetailbill0002");
			getFormValue(formpsssourcedetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsssourcedetailbill0002)) {
				pssSourceDetailBill = new PssSourceDetailBill();
				setObjValue(formpsssourcedetailbill0002, pssSourceDetailBill);
				pssSourceDetailBillFeign.update(pssSourceDetailBill);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE.getMessage());
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String sourceDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsssourcedetailbill0002 = formService.getFormData("psssourcedetailbill0002");
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		pssSourceDetailBill.setSourceDetailId(sourceDetailId);
		pssSourceDetailBill = pssSourceDetailBillFeign.getById(pssSourceDetailBill);
		getObjValue(formpsssourcedetailbill0002, pssSourceDetailBill, formData);
		if (pssSourceDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String sourceDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSourceDetailBill pssSourceDetailBill = new PssSourceDetailBill();
		pssSourceDetailBill.setSourceDetailId(sourceDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSourceDetailBill = (PssSourceDetailBill) JSONObject.toBean(jb, PssSourceDetailBill.class);
			pssSourceDetailBillFeign.delete(pssSourceDetailBill);
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

}
