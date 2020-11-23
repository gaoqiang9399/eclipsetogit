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

import app.component.pss.fund.entity.PssOtherRecDetailBill;
import app.component.pss.fund.feign.PssOtherRecDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherRecDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:14:07 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherRecDetailBill")
public class PssOtherRecDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherRecDetailBillFeign pssOtherRecDetailBillFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssOtherRecDetailBillFeign.getAll(pssOtherRecDetailBill),
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
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		// formpssotherrecdetailbill0002 =
		// formService.getFormData("pssotherrecdetailbill0002");
		// pssOtherRecDetailBill = new PssOtherRecDetailBill();
		// Ipage ipage = this.getIpage();
		// pssOtherRecDetailBillList =
		// (List<PssOtherRecDetailBill>)pssOtherRecDetailBillFeign.findByPage(ipage,
		// pssOtherRecDetailBill).getResult();
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherRecDetailBill_List";
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formpssotherrecdetailbill0002 = formService.getFormData("pssotherrecdetailbill0002");
		PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
		List<PssOtherRecDetailBill> pssOtherRecDetailBillList = pssOtherRecDetailBillFeign.getAll(pssOtherRecDetailBill);
		model.addAttribute("formpssotherrecdetailbill0002", formpssotherrecdetailbill0002);
		model.addAttribute("pssOtherRecDetailBillList", pssOtherRecDetailBillList);
		model.addAttribute("query", "");
		return "/component/pss/fund/PssOtherRecDetailBill_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formpssotherrecdetailbill0002 = formService.getFormData("pssotherrecdetailbill0002");
			getFormValue(formpssotherrecdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecdetailbill0002)) {
				PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
				setObjValue(formpssotherrecdetailbill0002, pssOtherRecDetailBill);
				pssOtherRecDetailBillFeign.insert(pssOtherRecDetailBill);
				getTableData(tableId, pssOtherRecDetailBill);// 获取列表
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
		PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
		try {
			FormData formpssotherrecdetailbill0002 = formService.getFormData("pssotherrecdetailbill0002");
			getFormValue(formpssotherrecdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherrecdetailbill0002)) {
				pssOtherRecDetailBill = new PssOtherRecDetailBill();
				setObjValue(formpssotherrecdetailbill0002, pssOtherRecDetailBill);
				pssOtherRecDetailBillFeign.update(pssOtherRecDetailBill);
				getTableData(tableId, pssOtherRecDetailBill);// 获取列表
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
	public Map<String, Object> getByIdAjax(String otherRecDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherrecdetailbill0002 = formService.getFormData("pssotherrecdetailbill0002");
		PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
		pssOtherRecDetailBill.setOtherRecDetailId(otherRecDetailId);
		pssOtherRecDetailBill = pssOtherRecDetailBillFeign.getById(pssOtherRecDetailBill);
		getObjValue(formpssotherrecdetailbill0002, pssOtherRecDetailBill, formData);
		if (pssOtherRecDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String otherRecDetailId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
		pssOtherRecDetailBill.setOtherRecDetailId(otherRecDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssOtherRecDetailBill = (PssOtherRecDetailBill) JSONObject.toBean(jb, PssOtherRecDetailBill.class);
			pssOtherRecDetailBillFeign.delete(pssOtherRecDetailBill);
			getTableData(tableId, pssOtherRecDetailBill);// 获取列表
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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String otherRecNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherRecDetailBill pssOtherRecDetailBill = new PssOtherRecDetailBill();
		List<PssOtherRecDetailBill> pssOtherRecDetailBillList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (otherRecNo == null || "".equals(otherRecNo)) {
				 pssOtherRecDetailBillList = new ArrayList<PssOtherRecDetailBill>();
			} else {
				pssOtherRecDetailBill.setOtherRecNo(otherRecNo);
				// 自定义查询Bo方法
				ipage = pssOtherRecDetailBillFeign.findByPage(ipage, pssOtherRecDetailBill);
				pssOtherRecDetailBillList = (List<PssOtherRecDetailBill>) ipage.getResult();
			}

			if (pssOtherRecDetailBillList != null && pssOtherRecDetailBillList.size() > 0) {
				if (pssOtherRecDetailBillList.size() < 5) {
					for (int i = pssOtherRecDetailBillList.size() + 1; i <= 5; i++) {
						pssOtherRecDetailBill = new PssOtherRecDetailBill();
						pssOtherRecDetailBill.setSequence(i);
						pssOtherRecDetailBillList.add(pssOtherRecDetailBill);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					PssOtherRecDetailBill pssSaleOrderDetail = new PssOtherRecDetailBill();
					pssSaleOrderDetail.setSequence(i);
					pssOtherRecDetailBillList.add(pssSaleOrderDetail);
				}
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssOtherRecDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);

		} catch (Exception ex) {
			ex.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", ex.getMessage());
			throw ex;
		}
		return dataMap;
	}

}
