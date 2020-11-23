package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusWarehouse;
import app.util.toolkit.Ipage;

/**
* Title: MfCusWarehouseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Oct 13 15:24:44 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusWarehouseFeign {
	
	@RequestMapping(value = "/mfCusWarehouse/insert")
	public void insert(@RequestBody MfCusWarehouse mfCusWarehouse) throws Exception;
	
	@RequestMapping(value = "/mfCusWarehouse/delete")
	public void delete(@RequestBody MfCusWarehouse mfCusWarehouse) throws Exception;
	
	@RequestMapping(value = "/mfCusWarehouse/update")
	public void update(@RequestBody MfCusWarehouse mfCusWarehouse) throws Exception;
	
	@RequestMapping(value = "/mfCusWarehouse/getById")
	public MfCusWarehouse getById(@RequestBody MfCusWarehouse mfCusWarehouse) throws Exception;
	
	@RequestMapping(value = "/mfCusWarehouse/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
