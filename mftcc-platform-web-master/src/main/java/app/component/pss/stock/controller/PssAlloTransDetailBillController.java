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
import app.component.pss.stock.entity.PssAlloTransDetailBill;
import app.component.pss.stock.feign.PssAlloTransDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssAlloTransDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 13:20:07 CST 2017
 **/
@Controller
@RequestMapping("/pssAlloTransDetailBill")
public class PssAlloTransDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssAlloTransDetailBillFeign pssAlloTransDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssAlloTransDetailBillFeign.getAll(pssAlloTransDetailBill), null, true);
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
		FormData formpssallotransdetailbill0002 = formService.getFormData("pssallotransdetailbill0002");
		PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
		Ipage ipage = this.getIpage();
		List<PssAlloTransDetailBill> pssAlloTransDetailBillList = (List<PssAlloTransDetailBill>) pssAlloTransDetailBillFeign
				.findByPage(ipage, pssAlloTransDetailBill).getResult();
		model.addAttribute("formpssallotransdetailbill0002", formpssallotransdetailbill0002);
		model.addAttribute("pssAlloTransDetailBillList", pssAlloTransDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/stock/PssAlloTransDetailBill_List";
	}

	/**
	 * 方法描述： 调拨新增初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-18 下午01:56:06
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			// ipage = pssAlloTransDetailBillFeign.findByPage(ipage,
			// pssAlloTransDetailBill);
			List<PssAlloTransDetailBill> pssAlloTransDetailBillList = (List<PssAlloTransDetailBill>) ipage.getResult();
			if (pssAlloTransDetailBillList == null || pssAlloTransDetailBillList.size() == 0) {
				pssAlloTransDetailBillList = new ArrayList<PssAlloTransDetailBill>();
				for (int i = 0; i < 5; i++) {
					pssAlloTransDetailBill = new PssAlloTransDetailBill();
					pssAlloTransDetailBill.setSequence(i + 1);
					pssAlloTransDetailBillList.add(pssAlloTransDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssAlloTransDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("新增调拨列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 调拨查看初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-8-21 下午5:56:06
	 */
	@RequestMapping(value = "/getAlloTransDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getAlloTransDetailBillListAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssAlloTransDetailBillFeign.getAlloTransDetailBillList(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			// ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			// Log.error("查看调拨列表数据出错", e);
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
			FormData formpssallotransdetailbill0002 = formService.getFormData("pssallotransdetailbill0002");
			getFormValue(formpssallotransdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransdetailbill0002)) {
				PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
				setObjValue(formpssallotransdetailbill0002, pssAlloTransDetailBill);
				pssAlloTransDetailBillFeign.insert(pssAlloTransDetailBill);
				getTableData(tableId, pssAlloTransDetailBill);// 获取列表
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
		PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
		try {
			FormData formpssallotransdetailbill0002 = formService.getFormData("pssallotransdetailbill0002");
			getFormValue(formpssallotransdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssallotransdetailbill0002)) {
				pssAlloTransDetailBill = new PssAlloTransDetailBill();
				setObjValue(formpssallotransdetailbill0002, pssAlloTransDetailBill);
				pssAlloTransDetailBillFeign.update(pssAlloTransDetailBill);
				getTableData(tableId, pssAlloTransDetailBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String alloTransDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssallotransdetailbill0002 = formService.getFormData("pssallotransdetailbill0002");
		PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
		pssAlloTransDetailBill.setAlloTransDetailId(alloTransDetailId);
		pssAlloTransDetailBill = pssAlloTransDetailBillFeign.getById(pssAlloTransDetailBill);
		getObjValue(formpssallotransdetailbill0002, pssAlloTransDetailBill, formData);
		if (pssAlloTransDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String alloTransDetailId, String tableId) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssAlloTransDetailBill pssAlloTransDetailBill = new PssAlloTransDetailBill();
		pssAlloTransDetailBill.setAlloTransDetailId(alloTransDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssAlloTransDetailBill = (PssAlloTransDetailBill) JSONObject.toBean(jb, PssAlloTransDetailBill.class);
			pssAlloTransDetailBillFeign.delete(pssAlloTransDetailBill);
			getTableData(tableId, pssAlloTransDetailBill);// 获取列表
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
