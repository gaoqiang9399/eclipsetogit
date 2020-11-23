package  app.component.pss.stock.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssStoreStockDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssStoreStockDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Aug 16 15:45:36 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssStoreStockDetailFeign {
	
	@RequestMapping(value = "/pssStoreStockDetail/insert")
	public void insert(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/delete")
	public void delete(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/deleteByBillDetailId")
	public void deleteByBillDetailId(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/deleteByBillId")
	public void deleteByBillId(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/update")
	public void update(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/getById")
	public PssStoreStockDetail getById(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/getByBillDetailId")
	public PssStoreStockDetail getByBillDetailId(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssStoreStockDetail") PssStoreStockDetail pssStoreStockDetail) throws Exception;

	@RequestMapping(value = "/pssStoreStockDetail/getAll")
	public List<PssStoreStockDetail> getAll(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
	@RequestMapping(value = "/pssStoreStockDetail/getPssStoreStockDetailList")
	public List<PssStoreStockDetail> getPssStoreStockDetailList(@RequestBody PssStoreStockDetail pssStoreStockDetail) throws Exception;
	
}
