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
import app.component.pss.purchases.feign.PssBuyBillDetailFeign;
import app.component.pss.purchases.feign.PssBuyOrderDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.MathExtend.RoundingType;
import net.sf.json.JSONObject;

/**
 * Title: PssBuyBillDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 24 22:07:54 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyBillDetail")
public class PssBuyBillDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyBillDetailFeign pssBuyBillDetailFeign;
	@Autowired
	private PssBuyOrderDetailFeign pssBuyOrderDetailFeign;
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
	private void getTableData(String tableId, PssBuyBillDetail pssBuyBillDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBuyBillDetailFeign.getAll(pssBuyBillDetail), null,
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
		FormData formdl_pssbuybill_detail02 = formService.getFormData("dl_pssbuybill_detail02");
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssBuyBillDetail", pssBuyBillDetail));
		List<PssBuyBillDetail> pssBuyBillDetailList = (List<PssBuyBillDetail>) pssBuyBillDetailFeign
				.findByPage(ipage, pssBuyBillDetail).getResult();
		model.addAttribute("formdl_pssbuybill_detail01", formdl_pssbuybill_detail02);
		model.addAttribute("pssBuyBillDetailList", pssBuyBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyBillDetail_List";
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
		FormData formdl_pssbuybill_detail02 = formService.getFormData("dl_pssbuybill_detail02");
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		List<PssBuyBillDetail> pssBuyBillDetailList = pssBuyBillDetailFeign.getAll(pssBuyBillDetail);
		model.addAttribute("formdl_pssbuybill_detail02", formdl_pssbuybill_detail02);
		model.addAttribute("pssBuyBillDetailList", pssBuyBillDetailList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyBillDetail_List";
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
			FormData formdl_pssbuybill_detail02 = formService.getFormData("dl_pssbuybill_detail02");
			getFormValue(formdl_pssbuybill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill_detail02)) {
				PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
				setObjValue(formdl_pssbuybill_detail02, pssBuyBillDetail);
				pssBuyBillDetailFeign.insert(pssBuyBillDetail);
				getTableData(tableId, pssBuyBillDetail);// 获取列表
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
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		try {
			FormData formdl_pssbuybill_detail02 = formService.getFormData("dl_pssbuybill_detail02");
			getFormValue(formdl_pssbuybill_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_pssbuybill_detail02)) {
				pssBuyBillDetail = new PssBuyBillDetail();
				setObjValue(formdl_pssbuybill_detail02, pssBuyBillDetail);
				pssBuyBillDetailFeign.update(pssBuyBillDetail);
				getTableData(tableId, pssBuyBillDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyBillDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_pssbuybill_detail02 = formService.getFormData("dl_pssbuybill_detail02");
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		pssBuyBillDetail.setBuyBillDetailId(buyBillDetailId);
		pssBuyBillDetail = pssBuyBillDetailFeign.getById(pssBuyBillDetail);
		getObjValue(formdl_pssbuybill_detail02, pssBuyBillDetail, formData);
		if (pssBuyBillDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String buyBillDetailId, String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		pssBuyBillDetail.setBuyBillDetailId(buyBillDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyBillDetail = (PssBuyBillDetail) JSONObject.toBean(jb, PssBuyBillDetail.class);
			pssBuyBillDetailFeign.delete(pssBuyBillDetail);
			getTableData(tableId, pssBuyBillDetail);// 获取列表
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
			String buyBillNo, String buyOrderNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyBillDetail pssBuyBillDetail = new PssBuyBillDetail();
		List<PssBuyBillDetail> pssBuyBillDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (buyBillNo == null || "".equals(buyBillNo)) {
				pssBuyBillDetailList = new ArrayList<PssBuyBillDetail>();
				if (buyOrderNo != null && !"".equals(buyOrderNo)) {
					// 由购货订单生成
					PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
					pssBuyOrderDetail.setBuyOrderNo(buyOrderNo);
					// 自定义查询Bo方法
					ipage = pssBuyOrderDetailFeign.findByPage(ipage, pssBuyOrderDetail);
					List<PssBuyOrderDetail> pssBuyOrderDetailList = (List<PssBuyOrderDetail>) ipage.getResult();
					if (pssBuyOrderDetailList != null && pssBuyOrderDetailList.size() > 0) {
						for (int i = 0, sequence = 1; i < pssBuyOrderDetailList.size(); i++) {
							pssBuyOrderDetail = pssBuyOrderDetailList.get(i);
							double quantity = MathExtend.subtract(pssBuyOrderDetail.getBaseQuantity(),
									pssBuyOrderDetail.getBillDetailQuantity());
							if (quantity > 0) {
								pssBuyBillDetail = new PssBuyBillDetail();
								pssBuyBillDetail.setSequence(sequence++);
								pssBuyBillDetail.setGoodsId(pssBuyOrderDetail.getGoodsId());
								pssBuyBillDetail.setGoodsModel(pssBuyOrderDetail.getGoodsModel());
								pssBuyBillDetail.setUnitId(pssBuyOrderDetail.getUnitId());
								pssBuyBillDetail.setStorehouseId(pssBuyOrderDetail.getStorehouseId());

								PssUnit pssUnit = new PssUnit();
								pssUnit.setUnitId(pssBuyOrderDetail.getUnitId());
								pssUnit = pssUnitFeign.getById(pssUnit);
								if (pssUnit != null) {
									quantity = MathExtend.divide(quantity, Double.valueOf(pssUnit.getRelNum()), 0,
											RoundingType.UP);
								}

								pssBuyBillDetail.setFreightSpaceId(pssBuyOrderDetail.getFreightSpaceId());
								pssBuyBillDetail.setQuantity(quantity);
								pssBuyBillDetail.setUnitPrice(pssBuyOrderDetail.getUnitPrice());
								pssBuyBillDetail.setTaxUnitPrice(pssBuyOrderDetail.getTaxUnitPrice());
								pssBuyBillDetail.setDiscountRate(pssBuyOrderDetail.getDiscountRate());
								pssBuyBillDetail.setDiscountAmount(pssBuyOrderDetail.getDiscountAmount());
								pssBuyBillDetail.setAmount(pssBuyOrderDetail.getAmount());
								pssBuyBillDetail.setTaxRate(pssBuyOrderDetail.getTaxRate());
								pssBuyBillDetail.setTaxAmount(pssBuyOrderDetail.getTaxAmount());
								pssBuyBillDetail.setTotalPriceWithTax(pssBuyOrderDetail.getTotalPriceWithTax());
								pssBuyBillDetail.setMemo(pssBuyOrderDetail.getMemo());
								pssBuyBillDetail.setBuyOrderNo(pssBuyOrderDetail.getBuyOrderNo());
								pssBuyBillDetail.setBuyOrderDetailId(pssBuyOrderDetail.getBuyOrderDetailId());
								pssBuyBillDetailList.add(pssBuyBillDetail);
							}
						}
					}
				}
			} else {
				pssBuyBillDetail.setBuyBillNo(buyBillNo);
				// 自定义查询Bo方法
				ipage = pssBuyBillDetailFeign.findByPage(ipage, pssBuyBillDetail);
				pssBuyBillDetailList = (List<PssBuyBillDetail>) ipage.getResult();
			}
			if (pssBuyBillDetailList != null && pssBuyBillDetailList.size() > 0) {
				if (pssBuyBillDetailList.size() < 5) {
					for (int i = pssBuyBillDetailList.size() + 1; i <= 5; i++) {
						pssBuyBillDetail = new PssBuyBillDetail();
						pssBuyBillDetail.setSequence(i);
						pssBuyBillDetailList.add(pssBuyBillDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssBuyBillDetail = new PssBuyBillDetail();
					pssBuyBillDetail.setSequence(i);
					pssBuyBillDetailList.add(pssBuyBillDetail);
				}
			}
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssBuyBillDetailList, ipage, true);
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
