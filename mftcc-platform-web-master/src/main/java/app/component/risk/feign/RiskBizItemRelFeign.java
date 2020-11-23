package  app.component.risk.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.risk.entity.RiskBizItemRel;
import app.util.toolkit.Ipage;

/**
* Title: RiskBizItemRelBo.java
* Description:
* @author:@dhcc.com.cn
* @Fri Mar 18 06:37:03 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface RiskBizItemRelFeign {
	
	@RequestMapping(value = "/riskBizItemRel/insert")
	public void insert(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskBizItemRel/delete")
	public void delete(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskBizItemRel/update")
	public void update(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskBizItemRel/getById")
	public RiskBizItemRel getById(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;
	
	@RequestMapping(value = "/riskBizItemRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("riskBizItemRel") RiskBizItemRel riskBizItemRel) throws ServiceException;

	@RequestMapping(value = "/riskBizItemRel/getAll")
	public List<RiskBizItemRel> getAll(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;

}
