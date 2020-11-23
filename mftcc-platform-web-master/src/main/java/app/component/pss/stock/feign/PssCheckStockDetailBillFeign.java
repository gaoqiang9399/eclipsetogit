package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssCheckStockDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssCheckStockDetailBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 11 17:16:56 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssCheckStockDetailBillFeign {
	
	@RequestMapping(value = "/pssCheckStockDetailBill/insert")
	public void insert(@RequestBody PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/delete")
	public void delete(@RequestBody PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/update")
	public void update(@RequestBody PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/getById")
	public PssCheckStockDetailBill getById(@RequestBody PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssCheckStockDetailBill") PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;

	@RequestMapping(value = "/pssCheckStockDetailBill/getAll")
	public List<PssCheckStockDetailBill> getAll(@RequestBody PssCheckStockDetailBill pssCheckStockDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssCheckStockDetailBill/getCheckStockDetailBillList")
	public Ipage getCheckStockDetailBillList(@RequestBody Ipage ipage,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
}
