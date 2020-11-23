package app.component.pss.stock.controller;

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

import app.component.finance.util.CwPublicUtil;
import app.component.pss.stock.entity.PssCheckStockDetailBill;
import app.component.pss.stock.feign.PssCheckStockDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssCheckStockDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 17:16:56 CST 2017
 **/
@Controller
@RequestMapping("/pssCheckStockDetailBill")
public class PssCheckStockDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCheckStockDetailBillFeign pssCheckStockDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssCheckStockDetailBillFeign.getAll(pssCheckStockDetailBill), null, true);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpsscheckstockdetailbill0002 = formService.getFormData("psscheckstockdetailbill0002");
		PssCheckStockDetailBill pssCheckStockDetailBill = new PssCheckStockDetailBill();
		Ipage ipage = this.getIpage();
		List<PssCheckStockDetailBill> pssCheckStockDetailBillList = (List<PssCheckStockDetailBill>) pssCheckStockDetailBillFeign
				.findByPage(ipage, pssCheckStockDetailBill).getResult();
		model.addAttribute("formpsscheckstockdetailbill0002", formpsscheckstockdetailbill0002);
		model.addAttribute("pssCheckStockDetailBillList", pssCheckStockDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCheckStockDetailBill_List";
	}

	/**
	 * 方法描述： 盘点详情初始化列表（新增）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-29 下午04:56:06
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssCheckStockDetailBillFeign.findByPage(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查询盘点明细列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 盘点详情初始化列表（查看）
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-31 上午09:56:06
	 */
	@RequestMapping(value = "/getCheckStockDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getCheckStockDetailBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssCheckStockDetailBillFeign.getCheckStockDetailBillList(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查询盘点明细列表数据出错", e);
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
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscheckstockdetailbill0002 = formService.getFormData("psscheckstockdetailbill0002");
			getFormValue(formpsscheckstockdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockdetailbill0002)) {
				PssCheckStockDetailBill pssCheckStockDetailBill = new PssCheckStockDetailBill();
				setObjValue(formpsscheckstockdetailbill0002, pssCheckStockDetailBill);
				pssCheckStockDetailBillFeign.insert(pssCheckStockDetailBill);
				getTableData(tableId, pssCheckStockDetailBill);// 获取列表
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCheckStockDetailBill pssCheckStockDetailBill = new PssCheckStockDetailBill();
		try {
			FormData formpsscheckstockdetailbill0002 = formService.getFormData("psscheckstockdetailbill0002");
			getFormValue(formpsscheckstockdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscheckstockdetailbill0002)) {
				pssCheckStockDetailBill = new PssCheckStockDetailBill();
				setObjValue(formpsscheckstockdetailbill0002, pssCheckStockDetailBill);
				pssCheckStockDetailBillFeign.update(pssCheckStockDetailBill);
				getTableData(tableId, pssCheckStockDetailBill);// 获取列表
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String checkStockDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscheckstockdetailbill0002 = formService.getFormData("psscheckstockdetailbill0002");
		PssCheckStockDetailBill pssCheckStockDetailBill = new PssCheckStockDetailBill();
		pssCheckStockDetailBill.setCheckStockDetailId(checkStockDetailId);
		pssCheckStockDetailBill = pssCheckStockDetailBillFeign.getById(pssCheckStockDetailBill);
		getObjValue(formpsscheckstockdetailbill0002, pssCheckStockDetailBill, formData);
		if (pssCheckStockDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, String checkStockDetailId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCheckStockDetailBill pssCheckStockDetailBill = new PssCheckStockDetailBill();
		pssCheckStockDetailBill.setCheckStockDetailId(checkStockDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssCheckStockDetailBill = (PssCheckStockDetailBill) JSONObject.toBean(jb, PssCheckStockDetailBill.class);
			pssCheckStockDetailBillFeign.delete(pssCheckStockDetailBill);
			getTableData(tableId, pssCheckStockDetailBill);// 获取列表
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
