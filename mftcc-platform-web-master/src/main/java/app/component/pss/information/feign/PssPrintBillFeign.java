package app.component.pss.information.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("mftcc-platform-factor")
public interface PssPrintBillFeign {

	@RequestMapping("/pssPrintBill/getPssBuyBillTableList")
	public Map<String, Object> getPssBuyBillTableList(@RequestBody String buyBillId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssBuyOrderTableList")
	public Map<String, Object> getPssBuyOrderTableList(@RequestBody String buyOrderId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssBuyReturnBillTableList")
	public Map<String, Object> getPssBuyReturnBillTableList(@RequestBody String buyReturnBillId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssSaleBillTableList")
	public Map<String, Object> getPssSaleBillTableList(@RequestBody String saleBillId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssSaleOrderTableList")
	public Map<String, Object> getPssSaleOrderTableList(@RequestBody String saleOrderId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssSaleReturnBillTableList")
	public Map<String, Object> getPssSaleReturnBillTableList(@RequestBody String saleReturnBillId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssAlloTransBillTableList")
	public Map<String, Object> getPssAlloTransBillTableList(@RequestBody String alloTransId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssOtherStockInBillTableList")
	public Map<String, Object> getPssOtherStockInBillTableList(@RequestBody String otherStockInId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssOtherStockOutBillTableList")
	public Map<String, Object> getPssOtherStockOutBillTableList(@RequestBody String otherStockOutId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssReceiptBillTableList")
	public Map<String, Object> getPssReceiptBillTableList(@RequestBody String receiptId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssPaymentBillTableList")
	public Map<String, Object> getPssPaymentBillTableList(@RequestBody String paymentId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssOtherRecBillTableList")
	public Map<String, Object> getPssOtherRecBillTableList(@RequestBody String otherRecId) throws Exception;

	@RequestMapping("/pssPrintBill/getPssOtherPayBillTableList")
	public Map<String, Object> getPssOtherPayBillTableList(@RequestBody String otherPayId) throws Exception;

}
