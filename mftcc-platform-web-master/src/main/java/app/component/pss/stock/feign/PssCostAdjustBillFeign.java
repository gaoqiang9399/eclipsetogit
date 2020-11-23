package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssCostAdjustBill;
import app.component.pss.stock.entity.PssCostAdjustDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssCostAdjustBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 17 16:47:36 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssCostAdjustBillFeign {
	
	@RequestMapping(value = "/pssCostAdjustBill/insert")
	public void insert(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/delete")
	public void delete(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/update")
	public void update(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/getById")
	public PssCostAdjustBill getById(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssCostAdjustBill") PssCostAdjustBill pssCostAdjustBill) throws Exception;

	@RequestMapping(value = "/pssCostAdjustBill/getAll")
	public List<PssCostAdjustBill> getAll(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/getCostAdjustBillList")
	public Ipage getCostAdjustBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/getByCABNo")
	public PssCostAdjustBill getByCABNo(@RequestBody PssCostAdjustBill pssCostAdjustBill) throws Exception;
	
//	@RequestMapping(value = "/pssCostAdjustBill/insertCABBet")
//	public void insertCABBet(@RequestBody PssCostAdjustBill pssCostAdjustBill, List<PssCostAdjustDetailBill> tableList) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/insertCABBet")
	public Map<String, String> insertCABBet(@RequestBody PssCostAdjustBill pssCostAdjustBill, @RequestParam("tableList")List<PssCostAdjustDetailBill> tableList) throws Exception;
	
	@RequestMapping(value = "/pssCostAdjustBill/updateCABBet")
	public Map<String, String> updateCABBet(@RequestBody PssCostAdjustBill pssCostAdjustBill, @RequestParam("tableList")List<PssCostAdjustDetailBill> tableList) throws Exception;
	
	//批量删除
	@RequestMapping(value = "/pssCostAdjustBill/deleteCABBatch")
	public Map<String, String> deleteCABBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
}
