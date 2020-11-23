package app.component.pss.purchases.controller;

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
import app.component.pss.purchases.entity.PssBuyBillDetail;
import app.component.pss.purchases.entity.PssBuyOrderDetail;
import app.component.pss.purchases.entity.PssBuyReturnBillDetail;
import app.component.pss.purchases.feign.PssBuyBillDetailFeign;
import app.component.pss.purchases.feign.PssBuyOrderDetailFeign;
import app.component.pss.purchases.feign.PssBuyReturnBillDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.MathExtend.RoundingType;
import net.sf.json.JSONObject;

/**
 * Title: PssBuyReturnBillDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 14 14:22:26 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyReturnBillDetail")
public class PssBuyReturnBillDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyReturnBillDetailFeign pssBuyReturnBillDetailFeign;
	@Autowired
	private PssBuyOrderDetailFeign pssBuyOrderDetailFeign;
	@Autowired
	private PssBuyBillDetailFeign pssBuyBillDetailFeign;
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
	private void getTableData(String tableId, PssBuyReturnBillDetail pssBuyReturnBillDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag",
				pssBuyReturnBillDetailFeign.getAll(pssBuyReturnBillDetail), null, true);
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
		FormData formdl_pssbuyreturnbill_detail02 = formService.getFormData("dl_pssbuyreturnbill_detail02");
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		Ipage ipage = this.getIpage();
		List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList = (List<PssBuyReturnBillDetail>) pssBuyReturnBillDetailFeign
				.findByPage(ipage, pssBuyReturnBillDetail).getResult();
		model.addAttribute("formdl_pssbuyreturnbill_detail02", formdl_pssbuyreturnbill_detail02);
		model.addAttribute("pssBuyReturnBillDetailList", pssBuyReturnBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyReturnBillDetail_List";
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
		FormData formdl_pssbuyreturnbill_detail02 = formService.getFormData("dl_pssbuyreturnbill_detail02");
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList = pssBuyReturnBillDetailFeign.getAll(pssBuyReturnBillDetail);
		model.addAttribute("formdl_pssbuyreturnbill_detail01", formdl_pssbuyreturnbill_detail02);
		model.addAttribute("pssBuyReturnBillDetailList", pssBuyReturnBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyReturnBillDetail_List";
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
			FormData formdl_pssbuyreturnbill_detail02 = formService.getFormData("dl_pssbuyreturnbill_detail02");
			getFormValue(formdl_pssbuyreturnbill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill_detail02)) {
				PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
				setObjValue(formdl_pssbuyreturnbill_detail02, pssBuyReturnBillDetail);
				pssBuyReturnBillDetailFeign.insert(pssBuyReturnBillDetail);
				getTableData(tableId,pssBuyReturnBillDetail);// 获取列表
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		try {
			FormData formdl_pssbuyreturnbill_detail02 = formService.getFormData("dl_pssbuyreturnbill_detail02");
			getFormValue(formdl_pssbuyreturnbill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuyreturnbill_detail02)) {
				pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
				setObjValue(formdl_pssbuyreturnbill_detail02, pssBuyReturnBillDetail);
				pssBuyReturnBillDetailFeign.update(pssBuyReturnBillDetail);
				getTableData(tableId,pssBuyReturnBillDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyReturnBillDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuyreturnbill_detail02 = formService.getFormData("dl_pssbuyreturnbill_detail02");
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		pssBuyReturnBillDetail.setBuyReturnBillDetailId(buyReturnBillDetailId);
		pssBuyReturnBillDetail = pssBuyReturnBillDetailFeign.getById(pssBuyReturnBillDetail);
		getObjValue(formdl_pssbuyreturnbill_detail02, pssBuyReturnBillDetail, formData);
		if (pssBuyReturnBillDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData,String buyReturnBillDetailId,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		pssBuyReturnBillDetail.setBuyReturnBillDetailId(buyReturnBillDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyReturnBillDetail = (PssBuyReturnBillDetail) JSONObject.toBean(jb, PssBuyReturnBillDetail.class);
			pssBuyReturnBillDetailFeign.delete(pssBuyReturnBillDetail);
			getTableData(tableId,pssBuyReturnBillDetail);// 获取列表
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
			String buyReturnBillNo,String buyOrderNo,String buyBillNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyReturnBillDetail pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
		List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (buyReturnBillNo == null || "".equals(buyReturnBillNo)) {
				pssBuyReturnBillDetailList = new ArrayList<PssBuyReturnBillDetail>();
				if (buyOrderNo != null && !"".equals(buyOrderNo)) {
					// 由购货订单生成
					PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
					pssBuyOrderDetail.setBuyOrderNo(buyOrderNo);
					// 自定义查询Bo方法
					ipage = pssBuyOrderDetailFeign.findByPage(ipage, pssBuyOrderDetail);
					List<PssBuyOrderDetail> pssBuyOrderDetailList = (List<PssBuyOrderDetail>) ipage.getResult();
					if (pssBuyOrderDetailList != null && pssBuyOrderDetailList.size() > 0) {
						for (int i = 0; i < pssBuyOrderDetailList.size(); i++) {
							pssBuyOrderDetail = pssBuyOrderDetailList.get(i);
							double quantity = MathExtend.subtract(pssBuyOrderDetail.getBaseQuantity(),
									pssBuyOrderDetail.getBillDetailQuantity());
							if (quantity > 0) {
								pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
								pssBuyReturnBillDetail.setSequence(pssBuyOrderDetail.getSequence());
								pssBuyReturnBillDetail.setGoodsId(pssBuyOrderDetail.getGoodsId());
								pssBuyReturnBillDetail.setGoodsModel(pssBuyOrderDetail.getGoodsModel());
								pssBuyReturnBillDetail.setUnitId(pssBuyOrderDetail.getUnitId());
								pssBuyReturnBillDetail.setStorehouseId(pssBuyOrderDetail.getStorehouseId());

								PssUnit pssUnit = new PssUnit();
								pssUnit.setUnitId(pssBuyOrderDetail.getUnitId());
								pssUnit = pssUnitFeign.getById(pssUnit);
								if (pssUnit != null) {
									quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
											RoundingType.UP);
								}

								pssBuyReturnBillDetail.setFreightSpaceId(pssBuyOrderDetail.getFreightSpaceId());
								pssBuyReturnBillDetail.setQuantity(quantity);
								pssBuyReturnBillDetail.setUnitPrice(pssBuyOrderDetail.getUnitPrice());
								pssBuyReturnBillDetail.setTaxUnitPrice(pssBuyOrderDetail.getTaxUnitPrice());
								pssBuyReturnBillDetail.setDiscountRate(pssBuyOrderDetail.getDiscountRate());
								pssBuyReturnBillDetail.setDiscountAmount(pssBuyOrderDetail.getDiscountAmount());
								pssBuyReturnBillDetail.setAmount(pssBuyOrderDetail.getAmount());
								pssBuyReturnBillDetail.setTaxRate(pssBuyOrderDetail.getTaxRate());
								pssBuyReturnBillDetail.setTaxAmount(pssBuyOrderDetail.getTaxAmount());
								pssBuyReturnBillDetail.setTotalPriceWithTax(pssBuyOrderDetail.getTotalPriceWithTax());
								pssBuyReturnBillDetail.setMemo(pssBuyOrderDetail.getMemo());
								pssBuyReturnBillDetail.setBuyOrderNo(pssBuyOrderDetail.getBuyOrderNo());
								pssBuyReturnBillDetail.setBuyOrderDetailId(pssBuyOrderDetail.getBuyOrderDetailId());
								pssBuyReturnBillDetailList.add(pssBuyReturnBillDetail);
							}
						}
					}
				} else if (buyBillNo != null && !"".equals(buyBillNo)) {
					// 由购货单生成
					PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
					pssBuyBillDetail.setBuyBillNo(buyBillNo);
					// 自定义查询Bo方法
					ipage = pssBuyBillDetailFeign.findByPage(ipage, pssBuyBillDetail);
					List<PssBuyBillDetail> pssBuyBillDetailList = (List<PssBuyBillDetail>) ipage.getResult();
					if (pssBuyBillDetailList != null && pssBuyBillDetailList.size() > 0) {
						for (int i = 0, sequence = 1; i < pssBuyBillDetailList.size(); i++) {
							pssBuyBillDetail = pssBuyBillDetailList.get(i);
							pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
							pssBuyReturnBillDetail.setSequence(sequence++);
							pssBuyReturnBillDetail.setGoodsId(pssBuyBillDetail.getGoodsId());
							pssBuyReturnBillDetail.setGoodsModel(pssBuyBillDetail.getGoodsModel());
							pssBuyReturnBillDetail.setUnitId(pssBuyBillDetail.getUnitId());
							pssBuyReturnBillDetail.setStorehouseId(pssBuyBillDetail.getStorehouseId());

							double quantity = MathExtend.subtract(pssBuyBillDetail.getBaseQuantity(),
									pssBuyBillDetail.getReturnBillDetailQuantity());
							double returnableQuantity = MathExtend.subtract(pssBuyBillDetail.getBaseQuantity(),
									pssBuyBillDetail.getReturnBillDetailQuantity());
							PssUnit pssUnit = new PssUnit();
							pssUnit.setUnitId(pssBuyBillDetail.getUnitId());
							pssUnit = pssUnitFeign.getById(pssUnit);
							if (pssUnit != null) {
								quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
										RoundingType.UP);
								returnableQuantity = MathExtend.divide(returnableQuantity,
										Double.valueOf(pssUnit.getRelNum()), 0, RoundingType.UP);
							}

							pssBuyReturnBillDetail.setFreightSpaceId(pssBuyBillDetail.getFreightSpaceId());
							pssBuyReturnBillDetail.setQuantity(quantity);
							pssBuyReturnBillDetail.setUnitPrice(pssBuyBillDetail.getUnitPrice());
							pssBuyReturnBillDetail.setTaxUnitPrice(pssBuyBillDetail.getTaxUnitPrice());
							pssBuyReturnBillDetail.setDiscountRate(pssBuyBillDetail.getDiscountRate());
							pssBuyReturnBillDetail.setDiscountAmount(pssBuyBillDetail.getDiscountAmount());
							pssBuyReturnBillDetail.setAmount(pssBuyBillDetail.getAmount());
							pssBuyReturnBillDetail.setTaxRate(pssBuyBillDetail.getTaxRate());
							pssBuyReturnBillDetail.setTaxAmount(pssBuyBillDetail.getTaxAmount());
							pssBuyReturnBillDetail.setTotalPriceWithTax(pssBuyBillDetail.getTotalPriceWithTax());
							pssBuyReturnBillDetail.setMemo(pssBuyBillDetail.getMemo());
							pssBuyReturnBillDetail.setBuyBillNo(pssBuyBillDetail.getBuyBillNo());
							pssBuyReturnBillDetail.setBuyBillDetailId(pssBuyBillDetail.getBuyBillDetailId());
							pssBuyReturnBillDetail.setBuyOrderDetailId(pssBuyBillDetail.getBuyOrderDetailId());
							pssBuyReturnBillDetail.setReturnableQuantity(returnableQuantity);
							pssBuyReturnBillDetailList.add(pssBuyReturnBillDetail);
						}
					}
				}else {
				}
			} else {
				pssBuyReturnBillDetail.setBuyReturnBillNo(buyReturnBillNo);
				// 自定义查询Bo方法
				ipage = pssBuyReturnBillDetailFeign.findByPage(ipage, pssBuyReturnBillDetail);
				pssBuyReturnBillDetailList = (List<PssBuyReturnBillDetail>) ipage.getResult();
				/*
				 * for (PssBuyReturnBillDetail pssBuyReturnBillDetail :
				 * pssBuyReturnBillDetailList) { double returnableQuantity =
				 * pssBuyReturnBillDetail.getReturnableQuantity(); PssUnit
				 * pssUnit = new PssUnit();
				 * pssUnit.setUnitId(pssBuyReturnBillDetail.getUnitId());
				 * pssUnit = pssUnitFeign.getById(pssUnit); if (pssUnit != null)
				 * { returnableQuantity = MathExtend.divide(returnableQuantity,
				 * Double.valueOf(pssUnit.getRelNum()), 0, RoundingType.UP);
				 * pssBuyReturnBillDetail.setReturnableQuantity(
				 * returnableQuantity); } }
				 */
			}
			if (pssBuyReturnBillDetailList != null && pssBuyReturnBillDetailList.size() > 0) {
				if (pssBuyReturnBillDetailList.size() < 5) {
					for (int i = pssBuyReturnBillDetailList.size() + 1; i <= 5; i++) {
						pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
						pssBuyReturnBillDetail.setSequence(i);
						pssBuyReturnBillDetailList.add(pssBuyReturnBillDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssBuyReturnBillDetail = new PssBuyReturnBillDetail();
					pssBuyReturnBillDetail.setSequence(i);
					pssBuyReturnBillDetailList.add(pssBuyReturnBillDetail);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssBuyReturnBillDetailList, ipage, true);
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
