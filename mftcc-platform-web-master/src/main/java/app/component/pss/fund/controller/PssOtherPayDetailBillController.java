package app.component.pss.fund.controller;

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
import app.component.pss.fund.entity.PssOtherPayDetailBill;
import app.component.pss.fund.feign.PssOtherPayDetailBillFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONObject;

/**
 * Title: PssOtherPayDetailBillAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:02:32 CST 2017
 **/
@Controller
@RequestMapping("/pssOtherPayDetailBill")
public class PssOtherPayDetailBillController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssOtherPayDetailBillFeign pssOtherPayDetailBillFeign;
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
		return "/component/pss/fund/PssOtherPayDetailBill_List";
	}

	/**
	 * 方法描述： 其他支出查看初始化列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-15 下午05:56:06
	 */
	/*
	 * public Map<String, Object> getOtherPayDetailBillListAjax(String ajaxData)
	 * throws Exception { FormService formService = new FormService();
	 * ActionContext.initialize(request, response); Map<String, Object> dataMap
	 * = new HashMap<String, Object>(); try { Map<String, String> formMap =
	 * CwPublicUtil.getMapByJson(ajaxData); Ipage ipage = this.getIpage();
	 * ipage.setPageNo(pageNo);//异步传页面翻页参数 ipage =
	 * pssOtherPayDetailBillFeign.getOtherPayDetailBillList(ipage, formMap);
	 * JsonTableUtil jtu = new JsonTableUtil();
	 * 
	 * @SuppressWarnings("rawtypes") String tableHtml = jtu.getJsonStr(tableId,
	 * tableType, (List)ipage.getResult(), ipage, true);
	 * ipage.setResult(tableHtml); dataMap.put("ipage", ipage); } catch
	 * (Exception e) { e.printStackTrace(); Log.error("查看其他支出列表数据出错", e);
	 * dataMap.put("flag", "error"); dataMap.put("msg", e.getMessage()); throw
	 * e; } return dataMap; }
	 */
	/**
	 * 方法描述： 其他支出单详情列表
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author Hgx
	 * @date 2017-11-15 下午05:56:06
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,String buySaleExpIds) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, String> formMap = CwPublicUtil.getMapByJson(ajaxData);
			formMap.put("buySaleExpIds", buySaleExpIds);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage = pssOtherPayDetailBillFeign.findByPage(ipage, formMap);
			JsonTableUtil jtu = new JsonTableUtil();
			@SuppressWarnings("rawtypes")
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			//Log.error("新增其他支出列表数据出错", e);
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
			FormData formpssotherpaydetailbill0002 = formService.getFormData("pssotherpaydetailbill0002");
			getFormValue(formpssotherpaydetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaydetailbill0002)) {
				PssOtherPayDetailBill pssOtherPayDetailBill = new PssOtherPayDetailBill();
				setObjValue(formpssotherpaydetailbill0002, pssOtherPayDetailBill);
				pssOtherPayDetailBillFeign.insert(pssOtherPayDetailBill);
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
		PssOtherPayDetailBill pssOtherPayDetailBill = new PssOtherPayDetailBill();
		try {
			FormData formpssotherpaydetailbill0002 = formService.getFormData("pssotherpaydetailbill0002");
			getFormValue(formpssotherpaydetailbill0002, getMapByJson(ajaxData));
			if (this.validateFormData(formpssotherpaydetailbill0002)) {
				pssOtherPayDetailBill = new PssOtherPayDetailBill();
				setObjValue(formpssotherpaydetailbill0002, pssOtherPayDetailBill);
				pssOtherPayDetailBillFeign.update(pssOtherPayDetailBill);
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
	public Map<String, Object> getByIdAjax(String otherPayDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formpssotherpaydetailbill0002 = formService.getFormData("pssotherpaydetailbill0002");
		PssOtherPayDetailBill pssOtherPayDetailBill = new PssOtherPayDetailBill();
		pssOtherPayDetailBill.setOtherPayDetailId(otherPayDetailId);
		pssOtherPayDetailBill = pssOtherPayDetailBillFeign.getById(pssOtherPayDetailBill);
		getObjValue(formpssotherpaydetailbill0002, pssOtherPayDetailBill, formData);
		if (pssOtherPayDetailBill != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String otherPayDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssOtherPayDetailBill pssOtherPayDetailBill = new PssOtherPayDetailBill();
		pssOtherPayDetailBill.setOtherPayDetailId(otherPayDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssOtherPayDetailBill = (PssOtherPayDetailBill) JSONObject.toBean(jb, PssOtherPayDetailBill.class);
			pssOtherPayDetailBillFeign.delete(pssOtherPayDetailBill);
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
