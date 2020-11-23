package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssOtherStockInDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssOtherStockInDetailBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Aug 22 10:41:18 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssOtherStockInDetailBillFeign {
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/insert")
	public void insert(@RequestBody PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/delete")
	public void delete(@RequestBody PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/update")
	public void update(@RequestBody PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/getById")
	public PssOtherStockInDetailBill getById(@RequestBody PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssOtherStockInDetailBill") PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherStockInDetailBill/getAll")
	public List<PssOtherStockInDetailBill> getAll(@RequestBody PssOtherStockInDetailBill pssOtherStockInDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInDetailBill/getOtherStockInDetailBillList")
	public Ipage getOtherStockInDetailBillList(@RequestBody Ipage ipage,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
}
