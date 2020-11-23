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

import app.component.pss.stock.entity.PssStoreStock;
import app.component.pss.stock.feign.PssStoreStockFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssStoreStockAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 16:54:32 CST 2017
 **/
@Controller
@RequestMapping("/pssStoreStock")
public class PssStoreStockController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssStoreStockFeign pssStoreStockFeign;
	// 全局变量
	// 表单变量

	/**
	 * 列表有翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model, String goodsId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("goodsId", goodsId);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssStoreStock_List";
	}

	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData, String goodsId, Integer pageNo, String tableId,
			String tableType) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStoreStock pssStoreStock = new PssStoreStock();
		try {
			pssStoreStock.setCustomQuery(ajaxData);
			pssStoreStock.setCustomSorts(ajaxData);
			pssStoreStock.setCriteriaList(pssStoreStock, ajaxData);
			pssStoreStock.setGoodsId(goodsId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssStoreStockFeign.findByPage(ipage, pssStoreStock);
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
	 * 列表有翻页（预警）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForWarn")
	public String getListPageForWarn(Model model, String ajaxData) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssStoreStock_ListForWarn";
	}

	@RequestMapping(value = "/getListPageForWarnAjax")
	@ResponseBody
	public Map<String, Object> getListPageForWarnAjax(String ajaxData, Integer pageNo, String tableId, String tableType)
			throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStoreStock pssStoreStock = new PssStoreStock();
		try {
			pssStoreStock.setCustomQuery(ajaxData);
			pssStoreStock.setCustomSorts(ajaxData);
			pssStoreStock.setCriteriaList(pssStoreStock, ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage = pssStoreStockFeign.findByPageForWarn(ipage, pssStoreStock);
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
			FormData formpssstorestock0002 = formService.getFormData("pssstorestock0002");
			getFormValue(formpssstorestock0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorestock0002)) {
				PssStoreStock pssStoreStock = new PssStoreStock();
				setObjValue(formpssstorestock0002, pssStoreStock);
				pssStoreStockFeign.insert(pssStoreStock);
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
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStoreStock pssStoreStock = new PssStoreStock();
		try {
			FormData formpssstorestock0002 = formService.getFormData("pssstorestock0002");
			getFormValue(formpssstorestock0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorestock0002)) {
				pssStoreStock = new PssStoreStock();
				setObjValue(formpssstorestock0002, pssStoreStock);
				pssStoreStockFeign.update(pssStoreStock);
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
	public Map<String, Object> getByIdAjax(String stockId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssstorestock0002 = formService.getFormData("pssstorestock0002");
		PssStoreStock pssStoreStock = new PssStoreStock();
		pssStoreStock.setStockId(stockId);
		pssStoreStock = pssStoreStockFeign.getById(pssStoreStock);
		getObjValue(formpssstorestock0002, pssStoreStock, formData);
		if (pssStoreStock != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String stockId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStoreStock pssStoreStock = new PssStoreStock();
		pssStoreStock.setStockId(stockId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssStoreStock = (PssStoreStock) JSONObject.toBean(jb, PssStoreStock.class);
			pssStoreStockFeign.delete(pssStoreStock);
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
