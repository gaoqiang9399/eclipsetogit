package  app.component.pss.stock.feign;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssStoreStock;
import app.util.toolkit.Ipage;

/**
* Title: PssStoreStockBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Aug 11 16:54:32 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssStoreStockFeign {
	
	@RequestMapping(value = "/pssStoreStock/insert")
	public void insert(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/delete")
	public void delete(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/update")
	public void update(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/getById")
	public PssStoreStock getById(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/getByStorehouseIdAndGoodsId")
	public PssStoreStock getByStorehouseIdAndGoodsId(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssStoreStock") PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/findByPageForWarn")
	public Ipage findByPageForWarn(@RequestBody Ipage ipage,@RequestParam("pssStoreStock") PssStoreStock pssStoreStock) throws Exception;
	
	@RequestMapping(value = "/pssStoreStock/selectCountForWarn")
	public Integer selectCountForWarn() throws Exception;

	@RequestMapping(value = "/pssStoreStock/getAll")
	public List<PssStoreStock> getAll(@RequestBody PssStoreStock pssStoreStock) throws Exception;
	
	/** 库存总量 */
	@RequestMapping(value = "/pssStoreStock/selectStockTotalNum")
	public Double selectStockTotalNum() throws Exception;
	
	/** 库存成本 */
	@RequestMapping(value = "/pssStoreStock/selectStockTotalCost")
	public BigDecimal selectStockTotalCost() throws Exception;
}
