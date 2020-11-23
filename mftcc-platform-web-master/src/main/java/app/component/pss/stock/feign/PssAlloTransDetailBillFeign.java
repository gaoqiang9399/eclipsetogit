package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssAlloTransDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssAlloTransDetailBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 11 13:20:07 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssAlloTransDetailBillFeign {
	
	@RequestMapping(value = "/pssAlloTransDetailBill/insert")
	public void insert(@RequestBody PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransDetailBill/delete")
	public void delete(@RequestBody PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransDetailBill/update")
	public void update(@RequestBody PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransDetailBill/getById")
	public PssAlloTransDetailBill getById(@RequestBody PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssAlloTransDetailBill") PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;

	@RequestMapping(value = "/pssAlloTransDetailBill/getAll")
	public List<PssAlloTransDetailBill> getAll(@RequestBody PssAlloTransDetailBill pssAlloTransDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransDetailBill/getAlloTransDetailBillList")
	public Ipage getAlloTransDetailBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
}
