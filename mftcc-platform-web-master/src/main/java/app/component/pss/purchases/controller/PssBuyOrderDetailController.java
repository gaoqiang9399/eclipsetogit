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

import app.component.pss.purchases.entity.PssBuyOrderDetail;
import app.component.pss.purchases.feign.PssBuyOrderDetailFeign;
import app.component.pss.sales.entity.PssSaleOrderDetail;
import app.component.pss.sales.feign.PssSaleOrderDetailFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import net.sf.json.JSONObject;

/**
 * Title: PssBuyOrderDetailAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Aug 10 17:41:38 CST 2017
 **/
@Controller
@RequestMapping("/pssBuyOrderDetail")
public class PssBuyOrderDetailController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private PssBuyOrderDetailFeign pssBuyOrderDetailFeign;
	@Autowired
	private PssSaleOrderDetailFeign pssSaleOrderDetailFeign;
	// 全局变量
	private String saleOrderDetailDatas; // 以销定购中销货订单明细数据集合(格式:"销货订单主键,商品ID,本次采购数量,本次采购价格;...")
	// 表单变量

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(String tableId, PssBuyOrderDetail pssBuyOrderDetail) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", pssBuyOrderDetailFeign.getAll(pssBuyOrderDetail), null,
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
		FormData formdl_buyorder_detail02 = formService.getFormData("dl_buyorder_detail02");
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("pssBuyOrderDetail", pssBuyOrderDetail));
		List<PssBuyOrderDetail> pssBuyOrderDetailList = (List<PssBuyOrderDetail>) pssBuyOrderDetailFeign
				.findByPage(ipage, pssBuyOrderDetail).getResult();
		model.addAttribute("formdl_buyorder_detail02", formdl_buyorder_detail02);
		model.addAttribute("pssBuyOrderDetailList", pssBuyOrderDetailList);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyOrderDetail_List";
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
		FormData formdl_buyorder_detail02 = formService.getFormData("dl_buyorder_detail02");
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		List<PssBuyOrderDetail> pssBuyOrderDetailList = pssBuyOrderDetailFeign.getAll(pssBuyOrderDetail);
		model.addAttribute("formdl_buyorder_detail02", formdl_buyorder_detail02);
		model.addAttribute("query", "");
		return "/component/pss/purchases/PssBuyOrderDetail_List";
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
			FormData formdl_buyorder_detail02 = formService.getFormData("dl_buyorder_detail02");
			getFormValue(formdl_buyorder_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_buyorder_detail02)) {
				PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
				setObjValue(formdl_buyorder_detail02, pssBuyOrderDetail);
				pssBuyOrderDetailFeign.insert(pssBuyOrderDetail);
				getTableData(tableId, pssBuyOrderDetail);// 获取列表
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
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		try {
			FormData formdl_buyorder_detail02 = formService.getFormData("dl_buyorder_detail02");
			getFormValue(formdl_buyorder_detail02, getMapByJson(ajaxData));
			if (this.validateFormData(formdl_buyorder_detail02)) {
				pssBuyOrderDetail = new PssBuyOrderDetail();
				setObjValue(formdl_buyorder_detail02, pssBuyOrderDetail);
				pssBuyOrderDetailFeign.update(pssBuyOrderDetail);
				getTableData(tableId, pssBuyOrderDetail);// 获取列表
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
	public Map<String, Object> getByIdAjax(String buyOrderDetailId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdl_buyorder_detail02 = formService.getFormData("dl_buyorder_detail02");
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		pssBuyOrderDetail.setBuyOrderDetailId(buyOrderDetailId);
		pssBuyOrderDetail = pssBuyOrderDetailFeign.getById(pssBuyOrderDetail);
		getObjValue(formdl_buyorder_detail02, pssBuyOrderDetail, formData);
		if (pssBuyOrderDetail != null) {
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
	public Map<String, Object> deleteAjax(String ajaxData, String buyOrderDetailId, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		pssBuyOrderDetail.setBuyOrderDetailId(buyOrderDetailId);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			pssBuyOrderDetail = (PssBuyOrderDetail) JSONObject.toBean(jb, PssBuyOrderDetail.class);
			pssBuyOrderDetailFeign.delete(pssBuyOrderDetail);
			getTableData(tableId, pssBuyOrderDetail);// 获取列表
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
			String buyOrderNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		PssBuyOrderDetail pssBuyOrderDetail = new PssBuyOrderDetail();
		List<PssBuyOrderDetail> pssBuyOrderDetailList = null;
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			if (buyOrderNo == null || "".equals(buyOrderNo)) {
				pssBuyOrderDetailList = new ArrayList<PssBuyOrderDetail>();
				if (saleOrderDetailDatas != null && !"".equals(saleOrderDetailDatas)) {
					// 以销定购生成购货订单
					String[] saleOrderDetailDataArray = saleOrderDetailDatas.split(";");
					if (saleOrderDetailDataArray != null && saleOrderDetailDataArray.length > 0) {
						int sequence = 1;
						for (String saleOrderDetailData : saleOrderDetailDataArray) {
							String saleOrderId = saleOrderDetailData.split(",")[0];
							String goodsId = saleOrderDetailData.split(",")[1];
							String thisBuyQuantity = saleOrderDetailData.split(",")[2];
							String thisBuyUnitPrice = saleOrderDetailData.split(",")[3];
							PssSaleOrderDetail pssSaleOrderDetail = new PssSaleOrderDetail();
							pssSaleOrderDetail.setSaleOrderId(saleOrderId);
							pssSaleOrderDetail.setGoodsId(goodsId);
							List<PssSaleOrderDetail> pssSaleOrderDetailList = pssSaleOrderDetailFeign
									.getAll(pssSaleOrderDetail);
							if (pssSaleOrderDetailList != null && pssSaleOrderDetailList.size() > 0) {
								pssBuyOrderDetail = new PssBuyOrderDetail();
								pssBuyOrderDetail.setSequence(sequence);
								pssBuyOrderDetail.setGoodsId(goodsId);
								pssBuyOrderDetail.setGoodsModel(pssSaleOrderDetailList.get(0).getGoodsModel());
								// pssBuyOrderDetail.setUnitId(pssSaleOrderDetailList.get(0).getUnitId());
								pssBuyOrderDetail.setStorehouseId(pssSaleOrderDetailList.get(0).getStorehouseId());
								pssBuyOrderDetail.setQuantity(Double.valueOf(thisBuyQuantity));
								pssBuyOrderDetail.setUnitPrice(Double.valueOf(thisBuyUnitPrice));
								pssBuyOrderDetail.setTaxUnitPrice(Double.valueOf(thisBuyUnitPrice));
								pssBuyOrderDetail.setDiscountRate(0.0);
								pssBuyOrderDetail.setDiscountAmount(0.0);
								pssBuyOrderDetail.setAmount(MathExtend.multiply(Double.valueOf(thisBuyQuantity),
										Double.valueOf(thisBuyUnitPrice)));
								pssBuyOrderDetail.setTaxRate(0.0);
								pssBuyOrderDetail.setTaxAmount(0.0);
								pssBuyOrderDetail.setTotalPriceWithTax(pssBuyOrderDetail.getAmount());
								pssBuyOrderDetail.setSaleOrderNo(pssSaleOrderDetailList.get(0).getSaleOrderNo());
								// pssBuyOrderDetail.setSaleOrderDetailId(pssSaleOrderDetail.getSaleOrderDetailId());
								pssBuyOrderDetailList.add(pssBuyOrderDetail);
								sequence++;
							}
						}
						dataMap.put("isSaleToBuy", true);
					}
				}
			} else {
				pssBuyOrderDetail.setBuyOrderNo(buyOrderNo);
				// 自定义查询Bo方法
				ipage = pssBuyOrderDetailFeign.findByPage(ipage, pssBuyOrderDetail);
				pssBuyOrderDetailList = (List<PssBuyOrderDetail>) ipage.getResult();
			}

			if (pssBuyOrderDetailList != null && pssBuyOrderDetailList.size() > 0) {
				if (pssBuyOrderDetailList.size() < 5) {
					for (int i = pssBuyOrderDetailList.size() + 1; i <= 5; i++) {
						pssBuyOrderDetail = new PssBuyOrderDetail();
						pssBuyOrderDetail.setSequence(i);
						pssBuyOrderDetailList.add(pssBuyOrderDetail);
					}
				}
			} else {
				for (int i = 1; i <= 5; i++) {
					pssBuyOrderDetail = new PssBuyOrderDetail();
					pssBuyOrderDetail.setSequence(i);
					pssBuyOrderDetailList.add(pssBuyOrderDetail);
				}
			}

			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, pssBuyOrderDetailList, ipage, true);
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
