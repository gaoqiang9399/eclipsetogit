package app.component.pss.stock.controller;

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

import app.component.finance.util.CwPublicUtil;
import app.component.pss.stock.entity.PssOtherStockOutDetailBill;
import app.component.pss.stock.feign.PssOtherStockOutDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherStockOutDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 28 09:44:54 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherStockOutDetailBill")
public class PssOtherStockOutDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherStockOutDetailBillFeign pssOtherStockOutDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssOtherStockOutDetailBillFeign.getAll(pssOtherStockOutDetailBill), null, true);
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
		FormData formpssotherstockoutdetailbill0002 = formService.getFormData("pssotherstockoutdetailbill0002");
		PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
		Ipage ipage = this.getIpage();
		List<PssOtherStockOutDetailBill> pssOtherStockOutDetailBillList = (List<PssOtherStockOutDetailBill>) pssOtherStockOutDetailBillFeign
				.findByPage(ipage, pssOtherStockOutDetailBill).getResult();
		model.addAttribute("formpssotherstockoutdetailbill0002", formpssotherstockoutdetailbill0002);
		model.addAttribute("pssOtherStockOutDetailBillList", pssOtherStockOutDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockOutDetailBill_List";
	}

	/**
	 * 方法描述： 其他出库查看初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-29 上午10:56:06
	 */
	@RequestMapping(value = "/getOtherStockOutDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getOtherStockOutDetailBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockOutDetailBillFeign.getOtherStockOutDetailBillList(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查看其他入库列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 其他入库详情初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-25 下午04:56:06
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			// ipage = pssOtherStockOutDetailBillFeign.findByPage(ipage,
			// pssOtherStockOutDetailBill);
			@SuppressWarnings("unchecked")
			List<PssOtherStockOutDetailBill> pssOtherStockOutDetailBillList = (List<PssOtherStockOutDetailBill>) ipage
					.getResult();
			if (pssOtherStockOutDetailBillList == null || pssOtherStockOutDetailBillList.size() == 0) {
				pssOtherStockOutDetailBillList = new ArrayList<PssOtherStockOutDetailBill>();
				for (int i = 0; i < 5; i++) {
					pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
					pssOtherStockOutDetailBill.setSequence(i + 1);
					pssOtherStockOutDetailBillList.add(pssOtherStockOutDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssOtherStockOutDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("新增其他入库列表数据出错", e);
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
			FormData formpssotherstockoutdetailbill0002 = formService.getFormData("pssotherstockoutdetailbill0002");
			getFormValue(formpssotherstockoutdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutdetailbill0002)) {
				PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
				setObjValue(formpssotherstockoutdetailbill0002, pssOtherStockOutDetailBill);
				pssOtherStockOutDetailBillFeign.insert(pssOtherStockOutDetailBill);
				getTableData(tableId, pssOtherStockOutDetailBill);// 获取列表
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
		PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
		try {
			FormData formpssotherstockoutdetailbill0002 = formService.getFormData("pssotherstockoutdetailbill0002");
			getFormValue(formpssotherstockoutdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockoutdetailbill0002)) {
				pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
				setObjValue(formpssotherstockoutdetailbill0002, pssOtherStockOutDetailBill);
				pssOtherStockOutDetailBillFeign.update(pssOtherStockOutDetailBill);
				getTableData(tableId, pssOtherStockOutDetailBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String otherStockOutDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockoutdetailbill0002 = formService.getFormData("pssotherstockoutdetailbill0002");
		PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
		pssOtherStockOutDetailBill.setOtherStockOutDetailId(otherStockOutDetailId);
		pssOtherStockOutDetailBill = pssOtherStockOutDetailBillFeign.getById(pssOtherStockOutDetailBill);
		getObjValue(formpssotherstockoutdetailbill0002, pssOtherStockOutDetailBill, formData);
		if (pssOtherStockOutDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, String otherStockOutDetailId)
			throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockOutDetailBill pssOtherStockOutDetailBill = new PssOtherStockOutDetailBill();
		pssOtherStockOutDetailBill.setOtherStockOutDetailId(otherStockOutDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssOtherStockOutDetailBill = (PssOtherStockOutDetailBill) JSONObject.toBean(jb,
					PssOtherStockOutDetailBill.class);
			pssOtherStockOutDetailBillFeign.delete(pssOtherStockOutDetailBill);
			getTableData(tableId, pssOtherStockOutDetailBill);// 获取列表
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
