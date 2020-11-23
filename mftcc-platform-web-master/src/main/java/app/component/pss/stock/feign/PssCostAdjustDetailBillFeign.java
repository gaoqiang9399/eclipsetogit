package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssCostAdjustDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssCostAdjustDetailBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 31 14:46:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssCostAdjustDetailBillFeign {
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/insert")
	public void insert(@RequestBody PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/delete")
	public void delete(@RequestBody PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/update")
	public void update(@RequestBody PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/getById")
	public PssCostAdjustDetailBill getById(@RequestBody PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssCostAdjustDetailBill") PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;

	@RequestMapping(value = "/pssCostAdjustDetailBill/getAll")
	public List<PssCostAdjustDetailBill> getAll(@RequestBody PssCostAdjustDetailBill pssCostAdjustDetailBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustDetailBill/getCostAdjustDetailBillList")
	public Ipage getCostAdjustDetailBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
}
