package app.component.pss.information.feign;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.information.entity.PssQueryItem;
import app.component.pss.information.entity.PssReportQueryConditionUser;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface PssReportFeign {

	@RequestMapping("/pssReport/getReportItemAll")
	public Map<String, Object> getReportItemAll(@RequestBody PssQueryItem pssQueryItem) throws Exception ;

	@RequestMapping("/pssReport/getQueryItemList") 
	public Map<String, Object> getQueryItemList( ) throws Exception ;

	@RequestMapping("/pssReport/showFormConditionVal")
	public String showFormConditionVal(@RequestBody Map<String, String> formMap) throws Exception ;

	@RequestMapping("/pssReport/saveReoprtSqlCondition")
	public String saveReoprtSqlCondition(@RequestBody PssReportQueryConditionUser pssReportQueryConditionUser)
			throws Exception ;

	@RequestMapping("/pssReport/reportQuery")
	public String reportQuery(@RequestBody PssReportQueryConditionUser pssReportQueryConditionUser) throws Exception ;

	@RequestMapping("/pssReport/getReportSqlListBean")
	public Ipage getReportSqlListBean(@RequestBody Ipage ipage) throws Exception ;

	@RequestMapping("/pssReport/getPssSaleTotalAmount") 
	public List<Map<String, Object>> getPssSaleTotalAmount() throws Exception ;

	@RequestMapping("/pssReport/getPssBuyBillAmountRank") 
	public List<Map<String, Object>> getPssBuyBillAmountRank( ) throws Exception ;

	@RequestMapping("/pssReport/getPssStockCountDistribute") 
	public List<Map<String, Object>> getPssStockCountDistribute() throws Exception ;

	@RequestMapping("/pssReport/getCurrentMonthGoodsPurchaseCount")
	public Integer getCurrentMonthGoodsPurchaseCount(@RequestParam("beginDate") String beginDate,@RequestParam("endDate") String endDate) throws Exception ;

	@RequestMapping("/pssReport/getCurrentMonthPerchaseAmount")
	public BigDecimal getCurrentMonthPerchaseAmount(@RequestParam("beginDate") String beginDate,@RequestParam("endDate") String endDate) throws Exception ;

	@RequestMapping("/pssReport/getCurrentMonthPerchaseAmount")
	public BigDecimal getCurrentMonthSaleAmount(@RequestParam("beginDate") String beginDate, @RequestParam("endDate")String endDate) throws Exception ;

	@RequestMapping("/pssReport/getCurrentMonthSaleProfitAmount")
	public BigDecimal getCurrentMonthSaleProfitAmount(@RequestParam("beginDate") String beginDate, @RequestParam("endDate")String endDate) throws Exception ;

	@RequestMapping("/pssReport/getCusAndSupplierDebt") 
	public List<Map<String,Object>> getCusAndSupplierDebt( ) throws Exception ;

	@RequestMapping("/pssReport/getCashOrBankDepositByAccountTpye")
	public BigDecimal getCashOrBankDepositByAccountTpye(@RequestParam("beginDate") String beginDate, @RequestParam("endDate")String endDate,
			@RequestParam("accountType")String accountType) throws Exception ;
}
