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
import app.component.pss.stock.entity.PssCostAdjustDetailBill;
import app.component.pss.stock.feign.PssCostAdjustDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssCostAdjustDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 31 14:46:40 CST 2017
 **/
@Controller
@RequestMapping("/pssCostAdjustDetailBill")
public class PssCostAdjustDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssCostAdjustDetailBillFeign pssCostAdjustDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssCostAdjustDetailBillFeign.getAll(pssCostAdjustDetailBill), null, true);
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
		FormData formpsscostadjustdetailbill0002 = formService.getFormData("psscostadjustdetailbill0002");
		PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
		Ipage ipage = this.getIpage();
		List<PssCostAdjustDetailBill> pssCostAdjustDetailBillList = (List<PssCostAdjustDetailBill>) pssCostAdjustDetailBillFeign
				.findByPage(ipage, pssCostAdjustDetailBill).getResult();
		model.addAttribute("formpsscostadjustdetailbill0002", formpsscostadjustdetailbill0002);
		model.addAttribute("pssCostAdjustDetailBillList", pssCostAdjustDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssCostAdjustDetailBill_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			@SuppressWarnings("unchecked")
			List<PssCostAdjustDetailBill> pssCostAdjustDetailBillList = (List<PssCostAdjustDetailBill>) ipage
					.getResult();
			if (pssCostAdjustDetailBillList == null || pssCostAdjustDetailBillList.size() == 0) {
				pssCostAdjustDetailBillList = new ArrayList<PssCostAdjustDetailBill>();
				for (int i = 0; i < 5; i++) {
					pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
					pssCostAdjustDetailBill.setSequence(i + 1);
					pssCostAdjustDetailBillList.add(pssCostAdjustDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssCostAdjustDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("新增成本调整列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 成本调整查看初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 下午5:56:06
	 */
	@RequestMapping(value = "/getCostAdjustDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getCostAdjustDetailBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		//FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssCostAdjustDetailBillFeign.getCostAdjustDetailBillList(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查看成本调整列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpsscostadjustdetailbill0002 = formService.getFormData("psscostadjustdetailbill0002");
			getFormValue(formpsscostadjustdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustdetailbill0002)) {
				PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
				setObjValue(formpsscostadjustdetailbill0002, pssCostAdjustDetailBill);
				pssCostAdjustDetailBillFeign.insert(pssCostAdjustDetailBill);
				getTableData(tableId, pssCostAdjustDetailBill);// 获取列表
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
		PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
		try {
			FormData formpsscostadjustdetailbill0002 = formService.getFormData("psscostadjustdetailbill0002");
			getFormValue(formpsscostadjustdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsscostadjustdetailbill0002)) {
				pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
				setObjValue(formpsscostadjustdetailbill0002, pssCostAdjustDetailBill);
				pssCostAdjustDetailBillFeign.update(pssCostAdjustDetailBill);
				getTableData(tableId, pssCostAdjustDetailBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String costAdjustDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsscostadjustdetailbill0002 = formService.getFormData("psscostadjustdetailbill0002");
		PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
		pssCostAdjustDetailBill.setCostAdjustDetailId(costAdjustDetailId);
		pssCostAdjustDetailBill = pssCostAdjustDetailBillFeign.getById(pssCostAdjustDetailBill);
		getObjValue(formpsscostadjustdetailbill0002, pssCostAdjustDetailBill, formData);
		if (pssCostAdjustDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, String costAdjustDetailId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssCostAdjustDetailBill pssCostAdjustDetailBill = new PssCostAdjustDetailBill();
		pssCostAdjustDetailBill.setCostAdjustDetailId(costAdjustDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssCostAdjustDetailBill = (PssCostAdjustDetailBill) JSONObject.toBean(jb, PssCostAdjustDetailBill.class);
			pssCostAdjustDetailBillFeign.delete(pssCostAdjustDetailBill);
			getTableData(tableId, pssCostAdjustDetailBill);// 获取列表
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
