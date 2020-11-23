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

import app.component.pss.fund.entity.PssReceiptDetailBill;
import app.component.pss.fund.feign.PssReceiptDetailBillFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: PssReceiptDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 17:57:27 CST 2017
 **/
@Controller
@RequestMapping("/pssReceiptDetailBill")
public class PssReceiptDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssReceiptDetailBillFeign pssReceiptDetailBillFeign;
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
		return "/component/pss/fund/PssReceiptDetailBill_List";
	}

	/**
	 * 方法描述：收款新增初始化列表
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
		PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			List<PssReceiptDetailBill> pssReceiptDetailBillList = (List<PssReceiptDetailBill>) ipage.getResult();
			if (pssReceiptDetailBillList == null || pssReceiptDetailBillList.size() == 0) {
				pssReceiptDetailBillList = new ArrayList<PssReceiptDetailBill>();
				for (int i = 0; i < 3; i++) {
					pssReceiptDetailBill = new PssReceiptDetailBill();
					pssReceiptDetailBill.setSequence(i + 1);
					pssReceiptDetailBillList.add(pssReceiptDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssReceiptDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(pssReceiptDetailBillList.size());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("新增收款列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：初始化列表收款详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-25 下午06:26:06
	 */
	@RequestMapping(value = "/getReceiptDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getReceiptDetailBillListAjax(String receiptNo,String tableId,String tableType,Integer pageNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
		try {
			pssReceiptDetailBill.setReceiptNo(receiptNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssReceiptDetailBillFeign.findByPage(ipage, pssReceiptDetailBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			// ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("查询收款列表数据出错", e);
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
			FormData formpssreceiptdetailbill0002 = formService.getFormData("pssreceiptdetailbill0002");
			getFormValue(formpssreceiptdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptdetailbill0002)) {
				PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
				setObjValue(formpssreceiptdetailbill0002, pssReceiptDetailBill);
				pssReceiptDetailBillFeign.insert(pssReceiptDetailBill);
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
		PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
		try {
			FormData formpssreceiptdetailbill0002 = formService.getFormData("pssreceiptdetailbill0002");
			getFormValue(formpssreceiptdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssreceiptdetailbill0002)) {
				pssReceiptDetailBill = new PssReceiptDetailBill();
				setObjValue(formpssreceiptdetailbill0002, pssReceiptDetailBill);
				pssReceiptDetailBillFeign.update(pssReceiptDetailBill);
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
	public Map<String, Object> getByIdAjax(String receiptDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssreceiptdetailbill0002 = formService.getFormData("pssreceiptdetailbill0002");
		PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
		pssReceiptDetailBill.setReceiptDetailId(receiptDetailId);
		pssReceiptDetailBill = pssReceiptDetailBillFeign.getById(pssReceiptDetailBill);
		getObjValue(formpssreceiptdetailbill0002, pssReceiptDetailBill, formData);
		if (pssReceiptDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String receiptDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssReceiptDetailBill pssReceiptDetailBill = new PssReceiptDetailBill();
		pssReceiptDetailBill.setReceiptDetailId(receiptDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssReceiptDetailBill = (PssReceiptDetailBill) JSONObject.toBean(jb, PssReceiptDetailBill.class);
			pssReceiptDetailBillFeign.delete(pssReceiptDetailBill);
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
