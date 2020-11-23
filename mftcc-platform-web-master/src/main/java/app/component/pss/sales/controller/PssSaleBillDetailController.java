package app.component.pss.sales.controller;

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

import app.component.pss.information.entity.PssUnit;
import app.component.pss.information.feign.PssUnitFeign;
import app.component.pss.sales.entity.PssSaleBillDetail;
import app.component.pss.sales.entity.PssSaleOrderDetail;
import app.component.pss.sales.feign.PssSaleBillDetailFeign;
import app.component.pss.sales.feign.PssSaleOrderDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.MathExtend.RoundingType;
import net.sf.json.JSONObject;

/**
 * Title: PssSaleBillDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 05 15:03:17 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleBillDetail")
public class PssSaleBillDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleBillDetailFeign pssSaleBillDetailFeign;
	@Autowired
	private PssSaleOrderDetailFeign pssSaleOrderDetailFeign;
	@Autowired
	private PssUnitFeign pssUnitFeign;
	// 全局变量
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssSaleBillDetail pssSaleBillDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssSaleBillDetailFeign.getAll(pssSaleBillDetail), null,
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
		FormData formdl_psssalebill_detail02 = formService.getFormData("dl_psssalebill_detail02");
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		Ipage ipage = this.getIpage();
		List<PssSaleBillDetail> pssSaleBillDetailList = (List<PssSaleBillDetail>) pssSaleBillDetailFeign
				.findByPage(ipage, pssSaleBillDetail).getResult();
		model.addAttribute("formdl_psssalebill_detail02", formdl_psssalebill_detail02);
		model.addAttribute("pssSaleBillDetailList", pssSaleBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleBillDetail_List";
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
		FormData formdl_psssalebill_detail02 = formService.getFormData("dl_psssalebill_detail02");
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		List<PssSaleBillDetail> pssSaleBillDetailList = pssSaleBillDetailFeign.getAll(pssSaleBillDetail);
		model.addAttribute("formdl_psssalebill_detail02", formdl_psssalebill_detail02);
		model.addAttribute("pssSaleBillDetailList", pssSaleBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleBillDetail_List";
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
			FormData formdl_psssalebill_detail02 = formService.getFormData("dl_psssalebill_detail02");
			getFormValue(formdl_psssalebill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill_detail02)) {
				PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
				setObjValue(formdl_psssalebill_detail02, pssSaleBillDetail);
				pssSaleBillDetailFeign.insert(pssSaleBillDetail);
				getTableData(tableId, pssSaleBillDetail);// 获取列表
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
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		try {
			FormData formdl_psssalebill_detail02 = formService.getFormData("dl_psssalebill_detail02");
			getFormValue(formdl_psssalebill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalebill_detail02)) {
				pssSaleBillDetail = new PssSaleBillDetail();
				setObjValue(formdl_psssalebill_detail02, pssSaleBillDetail);
				pssSaleBillDetailFeign.update(pssSaleBillDetail);
				getTableData(tableId, pssSaleBillDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleBillDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalebill_detail02 = formService.getFormData("dl_psssalebill_detail02");
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		pssSaleBillDetail.setSaleBillDetailId(saleBillDetailId);
		pssSaleBillDetail = pssSaleBillDetailFeign.getById(pssSaleBillDetail);
		getObjValue(formdl_psssalebill_detail02, pssSaleBillDetail, formData);
		if (pssSaleBillDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String tableId, String saleBillDetailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		pssSaleBillDetail.setSaleBillDetailId(saleBillDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleBillDetail = (PssSaleBillDetail) JSONObject.toBean(jb, PssSaleBillDetail.class);
			pssSaleBillDetailFeign.delete(pssSaleBillDetail);
			getTableData(tableId, pssSaleBillDetail);// 获取列表
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
			String saleBillNo, String saleOrderNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
		List<PssSaleBillDetail> pssSaleBillDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (saleBillNo == null || "".equals(saleBillNo)) {
				pssSaleBillDetailList = new ArrayList<PssSaleBillDetail>();
				if (saleOrderNo != null && !"".equals(saleOrderNo)) {
					// 由销货订单生成
					PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
					pssSaleOrderDetail.setSaleOrderNo(saleOrderNo);
					// 自定义查询Bo方法
					ipage = pssSaleOrderDetailFeign.findByPage(ipage, pssSaleOrderDetail);
					List<PssSaleOrderDetail> pssSaleOrderDetailList = (List<PssSaleOrderDetail>) ipage.getResult();
					if (pssSaleOrderDetailList != null && pssSaleOrderDetailList.size() > 0) {
						for (int i = 0, sequence = 1; i < pssSaleOrderDetailList.size(); i++) {
							pssSaleOrderDetail = pssSaleOrderDetailList.get(i);
							double quantity = MathExtend.subtract(pssSaleOrderDetail.getBaseQuantity(),
									pssSaleOrderDetail.getBillDetailQuantity());
							if (quantity > 0) {
								pssSaleBillDetail = new PssSaleBillDetail();
								pssSaleBillDetail.setSequence(sequence++);
								pssSaleBillDetail.setGoodsId(pssSaleOrderDetail.getGoodsId());
								pssSaleBillDetail.setGoodsModel(pssSaleOrderDetail.getGoodsModel());
								pssSaleBillDetail.setUnitId(pssSaleOrderDetail.getUnitId());
								pssSaleBillDetail.setStorehouseId(pssSaleOrderDetail.getStorehouseId());

								PssUnit pssUnit = new PssUnit();
								pssUnit.setUnitId(pssSaleOrderDetail.getUnitId());
								pssUnit = pssUnitFeign.getById(pssUnit);
								if (pssUnit != null) {
									quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
											RoundingType.UP);
								}

								pssSaleBillDetail.setFreightSpaceId(pssSaleOrderDetail.getFreightSpaceId());
								pssSaleBillDetail.setQuantity(quantity);
								pssSaleBillDetail.setUnitPrice(pssSaleOrderDetail.getUnitPrice());
								pssSaleBillDetail.setTaxUnitPrice(pssSaleOrderDetail.getTaxUnitPrice());
								pssSaleBillDetail.setDiscountRate(pssSaleOrderDetail.getDiscountRate());
								pssSaleBillDetail.setDiscountAmount(pssSaleOrderDetail.getDiscountAmount());
								pssSaleBillDetail.setAmount(pssSaleOrderDetail.getAmount());
								pssSaleBillDetail.setTaxRate(pssSaleOrderDetail.getTaxRate());
								pssSaleBillDetail.setTaxAmount(pssSaleOrderDetail.getTaxAmount());
								pssSaleBillDetail.setTotalPriceWithTax(pssSaleOrderDetail.getTotalPriceWithTax());
								pssSaleBillDetail.setMemo(pssSaleOrderDetail.getMemo());
								pssSaleBillDetail.setSaleOrderNo(pssSaleOrderDetail.getSaleOrderNo());
								pssSaleBillDetail.setSaleOrderDetailId(pssSaleOrderDetail.getSaleOrderDetailId());
								pssSaleBillDetailList.add(pssSaleBillDetail);
							}
						}
					}
				}
			} else {
				pssSaleBillDetail.setSaleBillNo(saleBillNo);
				// 自定义查询Bo方法
				ipage = pssSaleBillDetailFeign.findByPage(ipage, pssSaleBillDetail);
				pssSaleBillDetailList = (List<PssSaleBillDetail>) ipage.getResult();
			}
			if (pssSaleBillDetailList != null && pssSaleBillDetailList.size() > 0) {
				if (pssSaleBillDetailList.size() < 5) {
					for (int i = pssSaleBillDetailList.size() + 1; i <= 5; i++) {
						pssSaleBillDetail = new PssSaleBillDetail();
						pssSaleBillDetail.setSequence(i);
						pssSaleBillDetailList.add(pssSaleBillDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssSaleBillDetail = new PssSaleBillDetail();
					pssSaleBillDetail.setSequence(i);
					pssSaleBillDetailList.add(pssSaleBillDetail);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssSaleBillDetailList, ipage, true);
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

}
