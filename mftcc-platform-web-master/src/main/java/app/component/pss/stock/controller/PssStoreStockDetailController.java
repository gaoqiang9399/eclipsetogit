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

import app.component.pss.stock.entity.PssStoreStockDetail;
import app.component.pss.stock.feign.PssStoreStockDetailFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssStoreStockDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Aug 16 15:45:36 CST 2017
 **/
@Controller
@RequestMapping("/pssStoreStockDetail")
public class PssStoreStockDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssStoreStockDetailFeign pssStoreStockDetailFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssStoreStockDetail pssStoreStockDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssStoreStockDetailFeign.getAll(pssStoreStockDetail),
				null, true);
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
		FormData formpssstorestockdetail0002 = formService.getFormData("pssstorestockdetail0002");
		PssStoreStockDetail pssStoreStockDetail = new PssStoreStockDetail();
		Ipage ipage = this.getIpage();
		List<PssStoreStockDetail> pssStoreStockDetailList = (List<PssStoreStockDetail>) pssStoreStockDetailFeign
				.findByPage(ipage, pssStoreStockDetail).getResult();
		model.addAttribute("formpssstorestockdetail0002", formpssstorestockdetail0002);
		model.addAttribute("pssStoreStockDetailList", pssStoreStockDetailList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssStoreStockDetail_List";
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
			FormData formpssstorestockdetail0002 = formService.getFormData("pssstorestockdetail0002");
			getFormValue(formpssstorestockdetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorestockdetail0002)) {
				PssStoreStockDetail pssStoreStockDetail = new PssStoreStockDetail();
				setObjValue(formpssstorestockdetail0002, pssStoreStockDetail);
				pssStoreStockDetailFeign.insert(pssStoreStockDetail);
				getTableData(tableId, pssStoreStockDetail);// 获取列表
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
		PssStoreStockDetail pssStoreStockDetail = new PssStoreStockDetail();
		try {
			FormData formpssstorestockdetail0002 = formService.getFormData("pssstorestockdetail0002");
			getFormValue(formpssstorestockdetail0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssstorestockdetail0002)) {
				pssStoreStockDetail = new PssStoreStockDetail();
				setObjValue(formpssstorestockdetail0002, pssStoreStockDetail);
				pssStoreStockDetailFeign.update(pssStoreStockDetail);
				getTableData(tableId, pssStoreStockDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String inOutId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssstorestockdetail0002 = formService.getFormData("pssstorestockdetail0002");
		PssStoreStockDetail pssStoreStockDetail = new PssStoreStockDetail();
		pssStoreStockDetail.setInOutId(inOutId);
		pssStoreStockDetail = pssStoreStockDetailFeign.getById(pssStoreStockDetail);
		getObjValue(formpssstorestockdetail0002, pssStoreStockDetail, formData);
		if (pssStoreStockDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String inOutId, String tableId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssStoreStockDetail pssStoreStockDetail = new PssStoreStockDetail();
		pssStoreStockDetail.setInOutId(inOutId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssStoreStockDetail = (PssStoreStockDetail) JSONObject.toBean(jb, PssStoreStockDetail.class);
			pssStoreStockDetailFeign.delete(pssStoreStockDetail);
			getTableData(tableId, pssStoreStockDetail);// 获取列表
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
