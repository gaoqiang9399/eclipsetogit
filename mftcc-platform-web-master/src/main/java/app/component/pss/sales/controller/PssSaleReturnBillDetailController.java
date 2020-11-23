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
import app.component.pss.sales.entity.PssSaleReturnBillDetail;
import app.component.pss.sales.feign.PssSaleBillDetailFeign;
import app.component.pss.sales.feign.PssSaleOrderDetailFeign;
import app.component.pss.sales.feign.PssSaleReturnBillDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.MathExtend.RoundingType;
import net.sf.json.JSONObject;

/**
 * Title: PssSaleReturnBillDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 14 15:32:40 CST 2017
 **/
@Controller
@RequestMapping("/pssSaleReturnBillDetail")
public class PssSaleReturnBillDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssSaleReturnBillDetailFeign pssSaleReturnBillDetailFeign;
	@Autowired
	private PssSaleOrderDetailFeign pssSaleOrderDetailFeign;
	@Autowired
	private PssSaleBillDetailFeign pssSaleBillDetailFeign;
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
	private void getTableData(String tableId, PssSaleReturnBillDetail pssSaleReturnBillDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssSaleReturnBillDetailFeign.getAll(pssSaleReturnBillDetail), null, true);
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
		FormData formdl_psssalereturnbill_detail02 = formService.getFormData("dl_psssalereturnbill_detail02");
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		Ipage ipage = this.getIpage();
		List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList = (List<PssSaleReturnBillDetail>) pssSaleReturnBillDetailFeign
				.findByPage(ipage, pssSaleReturnBillDetail).getResult();
		model.addAttribute("formdl_psssalereturnbill_detail02", formdl_psssalereturnbill_detail02);
		model.addAttribute("pssSaleReturnBillDetailList", pssSaleReturnBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBillDetail_List";
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
		FormData formdl_psssalereturnbill_detail02 = formService.getFormData("dl_psssalereturnbill_detail02");
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList = pssSaleReturnBillDetailFeign
				.getAll(pssSaleReturnBillDetail);
		model.addAttribute("formdl_psssalereturnbill_detail02", formdl_psssalereturnbill_detail02);
		model.addAttribute("pssSaleReturnBillDetailList", pssSaleReturnBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/sales/PssSaleReturnBillDetail_List";
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
			FormData formdl_psssalereturnbill_detail02 = formService.getFormData("dl_psssalereturnbill_detail02");
			getFormValue(formdl_psssalereturnbill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill_detail02)) {
				PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
				setObjValue(formdl_psssalereturnbill_detail02, pssSaleReturnBillDetail);
				pssSaleReturnBillDetailFeign.insert(pssSaleReturnBillDetail);
				getTableData(tableId, pssSaleReturnBillDetail);// 获取列表
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
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		try {
			FormData formdl_psssalereturnbill_detail02 = formService.getFormData("dl_psssalereturnbill_detail02");
			getFormValue(formdl_psssalereturnbill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_psssalereturnbill_detail02)) {
				pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
				setObjValue(formdl_psssalereturnbill_detail02, pssSaleReturnBillDetail);
				pssSaleReturnBillDetailFeign.update(pssSaleReturnBillDetail);
				getTableData(tableId, pssSaleReturnBillDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String saleReturnBillDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_psssalereturnbill_detail02 = formService.getFormData("dl_psssalereturnbill_detail02");
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		pssSaleReturnBillDetail.setSaleReturnBillDetailId(saleReturnBillDetailId);
		pssSaleReturnBillDetail = pssSaleReturnBillDetailFeign.getById(pssSaleReturnBillDetail);
		getObjValue(formdl_psssalereturnbill_detail02, pssSaleReturnBillDetail, formData);
		if (pssSaleReturnBillDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String saleReturnBillDetailId, String tableId)
			throws Exception {
		// FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		pssSaleReturnBillDetail.setSaleReturnBillDetailId(saleReturnBillDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssSaleReturnBillDetail = (PssSaleReturnBillDetail) JSONObject.toBean(jb, PssSaleReturnBillDetail.class);
			pssSaleReturnBillDetailFeign.delete(pssSaleReturnBillDetail);
			getTableData(tableId, pssSaleReturnBillDetail);// 获取列表
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
			String saleReturnBillNo, String saleOrderNo, String saleBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssSaleReturnBillDetail pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
		List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (saleReturnBillNo == null || "".equals(saleReturnBillNo)) {
				pssSaleReturnBillDetailList = new ArrayList<PssSaleReturnBillDetail>();
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
								pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
								pssSaleReturnBillDetail.setSequence(sequence++);
								pssSaleReturnBillDetail.setGoodsId(pssSaleOrderDetail.getGoodsId());
								pssSaleReturnBillDetail.setGoodsModel(pssSaleOrderDetail.getGoodsModel());
								pssSaleReturnBillDetail.setUnitId(pssSaleOrderDetail.getUnitId());
								pssSaleReturnBillDetail.setStorehouseId(pssSaleOrderDetail.getStorehouseId());

								PssUnit pssUnit = new PssUnit();
								pssUnit.setUnitId(pssSaleOrderDetail.getUnitId());
								pssUnit = pssUnitFeign.getById(pssUnit);
								if (pssUnit != null) {
									quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
											RoundingType.UP);
								}

								pssSaleReturnBillDetail.setFreightSpaceId(pssSaleOrderDetail.getFreightSpaceId());
								pssSaleReturnBillDetail.setQuantity(quantity);
								pssSaleReturnBillDetail.setUnitPrice(pssSaleOrderDetail.getUnitPrice());
								pssSaleReturnBillDetail.setTaxUnitPrice(pssSaleOrderDetail.getTaxUnitPrice());
								pssSaleReturnBillDetail.setDiscountRate(pssSaleOrderDetail.getDiscountRate());
								pssSaleReturnBillDetail.setDiscountAmount(pssSaleOrderDetail.getDiscountAmount());
								pssSaleReturnBillDetail.setAmount(pssSaleOrderDetail.getAmount());
								pssSaleReturnBillDetail.setTaxRate(pssSaleOrderDetail.getTaxRate());
								pssSaleReturnBillDetail.setTaxAmount(pssSaleOrderDetail.getTaxAmount());
								pssSaleReturnBillDetail.setTotalPriceWithTax(pssSaleOrderDetail.getTotalPriceWithTax());
								pssSaleReturnBillDetail.setMemo(pssSaleOrderDetail.getMemo());
								pssSaleReturnBillDetail.setSaleOrderNo(pssSaleOrderDetail.getSaleOrderNo());
								pssSaleReturnBillDetail.setSaleOrderDetailId(pssSaleOrderDetail.getSaleOrderDetailId());
								pssSaleReturnBillDetailList.add(pssSaleReturnBillDetail);
							}
						}
					}
				} else if (saleBillNo != null && !"".equals(saleBillNo)) {
					// 由销货单生成
					PssSaleBillDetail pssSaleBillDetail = new PssSaleBillDetail();
					pssSaleBillDetail.setSaleBillNo(saleBillNo);
					// 自定义查询Bo方法
					ipage = pssSaleBillDetailFeign.findByPage(ipage, pssSaleBillDetail);
					List<PssSaleBillDetail> pssSaleBillDetailList = (List<PssSaleBillDetail>) ipage.getResult();
					if (pssSaleBillDetailList != null && pssSaleBillDetailList.size() > 0) {
						for (int i = 0; i < pssSaleBillDetailList.size(); i++) {
							pssSaleBillDetail = pssSaleBillDetailList.get(i);
							pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
							pssSaleReturnBillDetail.setSequence(pssSaleBillDetail.getSequence());
							pssSaleReturnBillDetail.setGoodsId(pssSaleBillDetail.getGoodsId());
							pssSaleReturnBillDetail.setGoodsModel(pssSaleBillDetail.getGoodsModel());
							pssSaleReturnBillDetail.setUnitId(pssSaleBillDetail.getUnitId());
							pssSaleReturnBillDetail.setStorehouseId(pssSaleBillDetail.getStorehouseId());

							double quantity = MathExtend.subtract(pssSaleBillDetail.getBaseQuantity(),
									pssSaleBillDetail.getReturnBillDetailQuantity());
							double returnableQuantity = MathExtend.subtract(pssSaleBillDetail.getBaseQuantity(),
									pssSaleBillDetail.getReturnBillDetailQuantity());
							PssUnit pssUnit = new PssUnit();
							pssUnit.setUnitId(pssSaleBillDetail.getUnitId());
							pssUnit = pssUnitFeign.getById(pssUnit);
							if (pssUnit != null) {
								quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
										RoundingType.UP);
								returnableQuantity = MathExtend.divide(returnableQuantity,
										Double.valueOf(pssUnit.getRelNum()), 0, RoundingType.UP);
							}

							pssSaleReturnBillDetail.setFreightSpaceId(pssSaleBillDetail.getFreightSpaceId());
							pssSaleReturnBillDetail.setQuantity(quantity);
							pssSaleReturnBillDetail.setUnitPrice(pssSaleBillDetail.getUnitPrice());
							pssSaleReturnBillDetail.setTaxUnitPrice(pssSaleBillDetail.getTaxUnitPrice());
							pssSaleReturnBillDetail.setDiscountRate(pssSaleBillDetail.getDiscountRate());
							pssSaleReturnBillDetail.setDiscountAmount(pssSaleBillDetail.getDiscountAmount());
							pssSaleReturnBillDetail.setAmount(pssSaleBillDetail.getAmount());
							pssSaleReturnBillDetail.setTaxRate(pssSaleBillDetail.getTaxRate());
							pssSaleReturnBillDetail.setTaxAmount(pssSaleBillDetail.getTaxAmount());
							pssSaleReturnBillDetail.setTotalPriceWithTax(pssSaleBillDetail.getTotalPriceWithTax());
							pssSaleReturnBillDetail.setMemo(pssSaleBillDetail.getMemo());
							pssSaleReturnBillDetail.setSaleBillNo(pssSaleBillDetail.getSaleBillNo());
							pssSaleReturnBillDetail.setSaleBillDetailId(pssSaleBillDetail.getSaleBillDetailId());
							pssSaleReturnBillDetail.setSaleOrderDetailId(pssSaleBillDetail.getSaleOrderDetailId());
							pssSaleReturnBillDetail.setReturnableQuantity(returnableQuantity);
							pssSaleReturnBillDetailList.add(pssSaleReturnBillDetail);
						}
					}
				}else {
				}
			} else {
				pssSaleReturnBillDetail.setSaleReturnBillNo(saleReturnBillNo);
				// 自定义查询Bo方法
				ipage = pssSaleReturnBillDetailFeign.findByPage(ipage, pssSaleReturnBillDetail);
				pssSaleReturnBillDetailList = (List<PssSaleReturnBillDetail>) ipage.getResult();
				/*
				 * for (PssSaleReturnBillDetail pssSaleReturnBillDetail :
				 * pssSaleReturnBillDetailList) { double returnableQuantity =
				 * pssSaleReturnBillDetail.getReturnableQuantity(); PssUnit
				 * pssUnit = new PssUnit();
				 * pssUnit.setUnitId(pssSaleReturnBillDetail.getUnitId());
				 * pssUnit = pssUnitFeign.getById(pssUnit); if (pssUnit != null)
				 * { returnableQuantity = MathExtend.divide(returnableQuantity,
				 * Double.valueOf(pssUnit.getRelNum()), 0, RoundingType.UP);
				 * pssSaleReturnBillDetail.setReturnableQuantity(
				 * returnableQuantity); } }
				 */
			}
			if (pssSaleReturnBillDetailList != null && pssSaleReturnBillDetailList.size() > 0) {
				if (pssSaleReturnBillDetailList.size() < 5) {
					for (int i = pssSaleReturnBillDetailList.size() + 1; i <= 5; i++) {
						pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
						pssSaleReturnBillDetail.setSequence(i);
						pssSaleReturnBillDetailList.add(pssSaleReturnBillDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssSaleReturnBillDetail = new PssSaleReturnBillDetail();
					pssSaleReturnBillDetail.setSequence(i);
					pssSaleReturnBillDetailList.add(pssSaleReturnBillDetail);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssSaleReturnBillDetailList, ipage, true);
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
