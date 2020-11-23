package  app.component.collateral.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfPledgeStatus;
import app.util.toolkit.Ipage;

/**
* Title: MfPledgeStatusBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 18:20:47 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfPledgeStatusFeign {
	
	@RequestMapping(value = "/mfPledgeStatus/insert")
	public void insert(@RequestBody MfPledgeStatus mfPledgeStatus) throws ServiceException;
	
	@RequestMapping(value = "/mfPledgeStatus/delete")
	public void delete(@RequestBody MfPledgeStatus mfPledgeStatus) throws ServiceException;
	
	@RequestMapping(value = "/mfPledgeStatus/update")
	public void update(@RequestBody MfPledgeStatus mfPledgeStatus) throws ServiceException;
	
	@RequestMapping(value = "/mfPledgeStatus/getById")
	public MfPledgeStatus getById(@RequestBody MfPledgeStatus mfPledgeStatus) throws ServiceException;
	
	@RequestMapping(value = "/mfPledgeStatus/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfPledgeStatus") MfPledgeStatus mfPledgeStatus) throws ServiceException;

	@RequestMapping(value = "/mfPledgeStatus/getPleStatusList")
	public List<MfPledgeStatus> getPleStatusList(@RequestBody String appId) throws ServiceException;
	
}
