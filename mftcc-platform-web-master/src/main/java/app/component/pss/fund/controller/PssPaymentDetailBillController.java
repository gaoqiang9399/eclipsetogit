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

import app.component.pss.fund.entity.PssPaymentDetailBill;
import app.component.pss.fund.feign.PssPaymentDetailBillFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: PssPaymentDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 26 14:17:26 CST 2017
 **/
@Controller
@RequestMapping("/pssPaymentDetailBill")
public class PssPaymentDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssPaymentDetailBillFeign pssPaymentDetailBillFeign;
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
		return "/component/pss/fund/PssPaymentDetailBill_List";
	}

	/**
	 * 方法描述：付款新增初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-26 下午03:56:06
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			List<PssPaymentDetailBill> pssPaymentDetailBillList = (List<PssPaymentDetailBill>) ipage.getResult();
			if (pssPaymentDetailBillList == null || pssPaymentDetailBillList.size() == 0) {
				pssPaymentDetailBillList = new ArrayList<PssPaymentDetailBill>();
				for (int i = 0; i < 3; i++) {
					pssPaymentDetailBill = new PssPaymentDetailBill();
					pssPaymentDetailBill.setSequence(i + 1);
					pssPaymentDetailBillList.add(pssPaymentDetailBill);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssPaymentDetailBillList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(pssPaymentDetailBillList.size());
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("新增付款列表数据出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述：初始化列表付款详情
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-09-27 上午11:09:06
	 */
	@RequestMapping(value = "/getPaymentDetailBillListAjax")
	@ResponseBody
	public Map<String, Object> getPaymentDetailBillListAjax(String paymentNo,Integer pageNo,String tableId,String tableType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
		try {
			pssPaymentDetailBill.setPaymentNo(paymentNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssPaymentDetailBillFeign.findByPage(ipage, pssPaymentDetailBill);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			// ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("查询付款列表数据出错", e);
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
			FormData formpsspaymentdetailbill0002 = formService.getFormData("psspaymentdetailbill0002");
			getFormValue(formpsspaymentdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsspaymentdetailbill0002)) {
				PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
				setObjValue(formpsspaymentdetailbill0002, pssPaymentDetailBill);
				pssPaymentDetailBillFeign.insert(pssPaymentDetailBill);
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
		PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
		try {
			FormData formpsspaymentdetailbill0002 = formService.getFormData("psspaymentdetailbill0002");
			getFormValue(formpsspaymentdetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpsspaymentdetailbill0002)) {
				pssPaymentDetailBill = new PssPaymentDetailBill();
				setObjValue(formpsspaymentdetailbill0002, pssPaymentDetailBill);
				pssPaymentDetailBillFeign.update(pssPaymentDetailBill);
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
	public Map<String, Object> getByIdAjax(String paymentDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpsspaymentdetailbill0002 = formService.getFormData("psspaymentdetailbill0002");
		PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
		pssPaymentDetailBill.setPaymentDetailId(paymentDetailId);
		pssPaymentDetailBill = pssPaymentDetailBillFeign.getById(pssPaymentDetailBill);
		getObjValue(formpsspaymentdetailbill0002, pssPaymentDetailBill, formData);
		if (pssPaymentDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String paymentDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssPaymentDetailBill pssPaymentDetailBill = new PssPaymentDetailBill();
		pssPaymentDetailBill.setPaymentDetailId(paymentDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssPaymentDetailBill = (PssPaymentDetailBill) JSONObject.toBean(jb, PssPaymentDetailBill.class);
			pssPaymentDetailBillFeign.delete(pssPaymentDetailBill);
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
