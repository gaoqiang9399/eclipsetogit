package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssOtherStockOutDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssOtherStockOutDetailBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 28 09:44:54 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssOtherStockOutDetailBillFeign {
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/insert")
	public void insert(@RequestBody PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/delete")
	public void delete(@RequestBody PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/update")
	public void update(@RequestBody PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/getById")
	public PssOtherStockOutDetailBill getById(@RequestBody PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssOtherStockOutDetailBill") PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherStockOutDetailBill/getAll")
	public List<PssOtherStockOutDetailBill> getAll(@RequestBody PssOtherStockOutDetailBill pssOtherStockOutDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutDetailBill/getOtherStockOutDetailBillList")
	public Ipage getOtherStockOutDetailBillList(@RequestBody Ipage ipage,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
}
