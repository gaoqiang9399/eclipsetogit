package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssCheckStockBill;
import app.component.pss.stock.entity.PssCheckStockDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssCheckStockBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 11 17:11:31 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssCheckStockBillFeign {
	
	@RequestMapping(value = "/pssCheckStockBill/insert")
	public void insert(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/delete")
	public void delete(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/update")
	public void update(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/getById")
	public PssCheckStockBill getById(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssCheckStockBill") PssCheckStockBill pssCheckStockBill) throws Exception;

	@RequestMapping(value = "/pssCheckStockBill/getAll")
	public List<PssCheckStockBill> getAll(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/getCheckStockBillList")
	public Ipage getCheckStockBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/insertCSBBet")
	public Map<String, String> insertCSBBet(@RequestBody PssCheckStockBill pssCheckStockBill, @RequestParam("tableList")List<PssCheckStockDetailBill> tableList) throws Exception;
	
	//生成盘点单据
	@RequestMapping(value = "/pssCheckStockBill/insertChkBill")
	public Map<String, String> insertChkBill(@RequestBody PssCheckStockBill pssCheckStockBill, @RequestParam("tableList")List<PssCheckStockDetailBill> tableList) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockBill/getByCSBNo")
	public PssCheckStockBill getByCSBNo(@RequestBody PssCheckStockBill pssCheckStockBill) throws Exception;
	
}
