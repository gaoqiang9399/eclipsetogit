package  app.component.risk.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.risk.entity.RiskPreventSceConfig;
import app.util.toolkit.Ipage;

/**
* Title: RiskPreventSceConfigBo.java
* Description:
* @author:@dhcc.com.cn
* @Thu Mar 03 07:11:49 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskPreventSceConfigFeign {
	
	@RequestMapping(value = "/riskPreventSceConfig/insert")
	public void insert(@RequestBody RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceConfig/delete")
	public void delete(@RequestBody RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceConfig/update")
	public void update(@RequestBody RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceConfig/getById")
	public RiskPreventSceConfig getById(@RequestBody RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;
	
	@RequestMapping(value = "/riskPreventSceConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("riskPreventSceConfig") RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;

	@RequestMapping(value = "/riskPreventSceConfig/getAll")
	public List<RiskPreventSceConfig> getAll(@RequestBody RiskPreventSceConfig riskPreventSceConfig) throws ServiceException;
}
