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
import app.component.pss.stock.entity.PssOtherStockInDetailBill;
import app.component.pss.stock.feign.PssOtherStockInDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherStockInDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 10:41:18 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherStockInDetailBill")
public class PssOtherStockInDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherStockInDetailBillFeign pssOtherStockInDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssOtherStockInDetailBillFeign.getAll(pssOtherStockInDetailBill), null, true);
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
		FormData formpssotherstockindetailbill0002 = formService.getFormData("pssotherstockindetailbill0002");
		PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
		Ipage ipage = this.getIpage();
		List<PssOtherStockInDetailBill> pssOtherStockInDetailBillList = (List<PssOtherStockInDetailBill>) pssOtherStockInDetailBillFeign
				.findByPage(ipage, pssOtherStockInDetailBill).getResult();
		model.addAttribute("formpssotherstockindetailbill0002", formpssotherstockindetailbill0002);
		model.addAttribute("pssOtherStockInDetailBillList", pssOtherStockInDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssOtherStockInDetailBill_List";
	}

	/**
	 * 方法描述： 其他入库查看初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-18 下午01:56:06
	 */
	@RequestMapping(value = "/getOtherStockInDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getOtherStockInDetailBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherStockInDetailBillFeign.getOtherStockInDetailBillList(ipage, formMap);
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
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			// ipage = pssOtherStockInDetailBillFeign.findByPage(ipage,
			// pssOtherStockInDetailBill);
			@SuppressWarnings("unchecked")
			List<PssOtherStockInDetailBill> pssOtherStockInDetailBillList = (List<PssOtherStockInDetailBill>) ipage
					.getResult();
			if (pssOtherStockInDetailBillList == null || pssOtherStockInDetailBillList.size() == 0) {
				pssOtherStockInDetailBillList = new ArrayList<PssOtherStockInDetailBill>();
				for (int i = 0; i < 5; i++) {
					pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
					pssOtherStockInDetailBill.setSequence(i + 1);
					pssOtherStockInDetailBillList.add(pssOtherStockInDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssOtherStockInDetailBillList, ipage, true);
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
			FormData formpssotherstockindetailbill0002 = formService.getFormData("pssotherstockindetailbill0002");
			getFormValue(formpssotherstockindetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockindetailbill0002)) {
				PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
				setObjValue(formpssotherstockindetailbill0002, pssOtherStockInDetailBill);
				pssOtherStockInDetailBillFeign.insert(pssOtherStockInDetailBill);
				getTableData(tableId, pssOtherStockInDetailBill);// 获取列表
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
		PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
		try {
			FormData formpssotherstockindetailbill0002 = formService.getFormData("pssotherstockindetailbill0002");
			getFormValue(formpssotherstockindetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherstockindetailbill0002)) {
				pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
				setObjValue(formpssotherstockindetailbill0002, pssOtherStockInDetailBill);
				pssOtherStockInDetailBillFeign.update(pssOtherStockInDetailBill);
				getTableData(tableId, pssOtherStockInDetailBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String otherStockInDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherstockindetailbill0002 = formService.getFormData("pssotherstockindetailbill0002");
		PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
		pssOtherStockInDetailBill.setOtherStockInDetailId(otherStockInDetailId);
		pssOtherStockInDetailBill = pssOtherStockInDetailBillFeign.getById(pssOtherStockInDetailBill);
		getObjValue(formpssotherstockindetailbill0002, pssOtherStockInDetailBill, formData);
		if (pssOtherStockInDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String otherStockInDetailId, String tableId)
			throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherStockInDetailBill pssOtherStockInDetailBill = new PssOtherStockInDetailBill();
		pssOtherStockInDetailBill.setOtherStockInDetailId(otherStockInDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssOtherStockInDetailBill = (PssOtherStockInDetailBill) JSONObject.toBean(jb,
					PssOtherStockInDetailBill.class);
			pssOtherStockInDetailBillFeign.delete(pssOtherStockInDetailBill);
			getTableData(tableId, pssOtherStockInDetailBill);// 获取列表
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
