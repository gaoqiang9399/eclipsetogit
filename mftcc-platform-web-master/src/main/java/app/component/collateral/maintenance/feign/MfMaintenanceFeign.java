package app.component.collateral.maintenance.feign;

import app.util.toolkit.Ipage;

import app.component.collateral.maintenance.entity.MfMaintenance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Title: MfMaintenanceFeign.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Aug 15 15:31:13 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfMaintenanceFeign {

	@RequestMapping(value = "/mfMaintenance/insert")
	public void insert(@RequestBody MfMaintenance mfMaintenance) throws Exception;

	@RequestMapping(value = "/mfMaintenance/delete")
	public void delete(@RequestBody MfMaintenance mfMaintenance) throws Exception;

	@RequestMapping(value = "/mfMaintenance/update")
	public void update(@RequestBody MfMaintenance mfMaintenance) throws Exception;

	@RequestMapping(value = "/mfMaintenance/getById")
	public MfMaintenance getById(@RequestBody MfMaintenance mfMaintenance) throws Exception;

	@RequestMapping(value = "/mfMaintenance/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
