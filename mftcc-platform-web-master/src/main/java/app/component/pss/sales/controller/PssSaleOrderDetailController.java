package app.component.pss.sales.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import app.component.pss.conf.entity.PssConfig;
import app.component.pss.conf.feign.PssConfigFeign;
import app.component.pss.sales.entity.PssSaleOrderDetail;
import app.component.pss.sales.feign.PssSaleOrderDetailFeign;
import app.component.pss.utils.PssEnumBean;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: PssSaleOrderDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 31 16:55:14 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleOrderDetail")
public class PssSaleOrderDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleOrderDetailFeign pssSaleOrderDetailFeign;
	@Autowired
	private PssConfigFeign pssConfigFeign;
	// 全局变量
	private String saleOrderDetailDatas; // 以销定购中销货订单明细数据集合(格式:"主键,本次采购数量,本次采购价格;...")
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssSaleOrderDetail pssSaleOrderDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleOrderDetailFeign.getAll(pssSaleOrderDetail), null,
				true);
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
		FormData formdl_psssalesorder_detail02 = formService.getFormData("dl_psssalesorder_detail02");
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		Ipage ipage = this.getIpage();
		List<PssSaleOrderDetail> pssSaleOrderDetailList = (List<PssSaleOrderDetail>) pssSaleOrderDetailFeign
				.findByPage(ipage, pssSaleOrderDetail).getResult();
		model.addAttribute("formdl_psssalesorder_detail02", formdl_psssalesorder_detail02);
		model.addAttribute("pssSaleOrderDetailList", pssSaleOrderDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrderDetail_List";
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
		FormData formdl_psssalesorder_detail02 = formService.getFormData("dl_psssalesorder_detail02");
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		List<PssSaleOrderDetail> pssSaleOrderDetailList = pssSaleOrderDetailFeign.getAll(pssSaleOrderDetail);
		model.addAttribute("formdl_psssalesorder_detail02", formdl_psssalesorder_detail02);
		model.addAttribute("pssSaleOrderDetailList", pssSaleOrderDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrderDetail_List";
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
			FormData formdl_psssalesorder_detail02 = formService.getFormData("dl_psssalesorder_detail02");
			getFormValue(formdl_psssalesorder_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalesorder_detail02)) {
				PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
				setObjValue(formdl_psssalesorder_detail02, pssSaleOrderDetail);
				pssSaleOrderDetailFeign.insert(pssSaleOrderDetail);
				getTableData(tableId, pssSaleOrderDetail);// 获取列表
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
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		try {
			FormData formdl_psssalesorder_detail02 = formService.getFormData("dl_psssalesorder_detail02");
			getFormValue(formdl_psssalesorder_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalesorder_detail02)) {
				pssSaleOrderDetail = new PssSaleOrderDetail();
				setObjValue(formdl_psssalesorder_detail02, pssSaleOrderDetail);
				pssSaleOrderDetailFeign.update(pssSaleOrderDetail);
				getTableData(tableId, pssSaleOrderDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleOrderDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalesorder_detail02 = formService.getFormData("dl_psssalesorder_detail02");
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		pssSaleOrderDetail.setSaleOrderDetailId(saleOrderDetailId);
		pssSaleOrderDetail = pssSaleOrderDetailFeign.getById(pssSaleOrderDetail);
		getObjValue(formdl_psssalesorder_detail02, pssSaleOrderDetail, formData);
		if (pssSaleOrderDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String saleOrderDetailId, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		pssSaleOrderDetail.setSaleOrderDetailId(saleOrderDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleOrderDetail = (PssSaleOrderDetail) JSONObject.toBean(jb, PssSaleOrderDetail.class);
			pssSaleOrderDetailFeign.delete(pssSaleOrderDetail);
			getTableData(tableId, pssSaleOrderDetail);// 获取列表
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

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String saleOrderNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		List<PssSaleOrderDetail> pssSaleOrderDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (saleOrderNo == null || "".equals(saleOrderNo)) {
				pssSaleOrderDetailList = new ArrayList<PssSaleOrderDetail>();
			} else {
				pssSaleOrderDetail.setSaleOrderNo(saleOrderNo);
				// 自定义查询Bo方法
				ipage = pssSaleOrderDetailFeign.findByPage(ipage, pssSaleOrderDetail);
				pssSaleOrderDetailList = (List<PssSaleOrderDetail>) ipage.getResult();
			}

			if (pssSaleOrderDetailList != null && pssSaleOrderDetailList.size() > 0) {
				if (pssSaleOrderDetailList.size() < 5) {
					for (int i = pssSaleOrderDetailList.size() + 1; i <= 5; i++) {
						pssSaleOrderDetail = new PssSaleOrderDetail();
						pssSaleOrderDetail.setSequence(i);
						pssSaleOrderDetailList.add(pssSaleOrderDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssSaleOrderDetail = new PssSaleOrderDetail();
					pssSaleOrderDetail.setSequence(i);
					pssSaleOrderDetailList.add(pssSaleOrderDetail);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssSaleOrderDetailList, ipage, true);
			ipage.setResult(tableHtml);
			ipage.setPageCounts(5);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 跳转到以销定购列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getToBuyOrderListPage")
	public String getToBuyOrderListPage(Model model) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 前台自定义筛选组件的条件项，从数据字典缓存获取。
		CodeUtils codeUtils = new CodeUtils();
		JSONArray saleOrderScopeJsonArray = codeUtils.getJSONArrayByKeyName("PSS_SALE_ORDER_SCOPE");
		this.getHttpRequest().setAttribute("saleOrderScopeJsonArray", saleOrderScopeJsonArray);

		JSONObject json = new JSONObject();
		// 系统配置信息
		PssConfig pssConfig = new PssConfig();
		List<PssConfig> pssConfigList = pssConfigFeign.getAll(pssConfig);
		if (pssConfigList != null && pssConfigList.size() > 0) {
			pssConfig = pssConfigList.get(0);
			if (pssConfig.getNumDecimalDigit() == null || "".equals(pssConfig.getNumDecimalDigit())) {
				pssConfig.setNumDecimalDigit("0");
			}
			if (pssConfig.getAmtDecimalDigit() == null || "".equals(pssConfig.getAmtDecimalDigit())) {
				pssConfig.setAmtDecimalDigit("2");
			}
			if (pssConfig.getDutyRate() == null) {
				pssConfig.setDutyRate(0.0);
			}
		} else {
			pssConfig.setNumDecimalDigit("0");
			pssConfig.setAmtDecimalDigit("2");
			pssConfig.setDutyRate(0.0);
		}
		json.put("pssConfig", pssConfig);

		String ajaxData = json.toString();

		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleOrderDetail_ToBuyOrderList";
	}

	/**
	 * 以销定购列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findToBuyOrderByPageAjax")
	@ResponseBody
	public Map<String, Object> findToBuyOrderByPageAjax(String ajaxData, Integer pageNo, String tableId,
			String tableType) throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
		try {
			JSONArray ajaxDataArray = JSONArray.fromObject(ajaxData);
			if (ajaxDataArray.size() >= 3) {
				JSONArray criteriaArray = ajaxDataArray.getJSONArray(0);
				if (criteriaArray.size() > 0) {
					Iterator<JSONObject> iterator = criteriaArray.iterator();
					while (iterator.hasNext()) {
						JSONObject object = iterator.next();
						if ("saleOrderScope".equals(object.getString("condition"))) {
							pssSaleOrderDetail.setSaleOrderScope(object.getString("value"));
							iterator.remove();
							break;
						}
					}
					ajaxDataArray.set(0, criteriaArray);
					ajaxData = ajaxDataArray.toString();
				}
			}

			pssSaleOrderDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			pssSaleOrderDetail.setCustomSorts(ajaxData);// 自定义排序参数赋值
			pssSaleOrderDetail.setCriteriaList(pssSaleOrderDetail, ajaxData);// 我的筛选
			pssSaleOrderDetail.setAuditStsed(PssEnumBean.YES_OR_NO.YES.getNum());
			pssSaleOrderDetail.setBusinessType(PssEnumBean.SALE_BUSINESS_TYPE.SALE.getValue());
			pssSaleOrderDetail.setEnabledStatus(PssEnumBean.ENABLED_STATUS.ENABLED.getValue());

			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = pssSaleOrderDetailFeign.findToBuyOrderByPage(ipage, pssSaleOrderDetail);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("success", false);
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
