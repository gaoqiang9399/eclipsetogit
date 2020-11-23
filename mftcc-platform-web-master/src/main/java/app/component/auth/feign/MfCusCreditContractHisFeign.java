package  app.component.auth.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditContractHis;

/**
* Title: MfCusCreditContractBo.java
* Description:授信协议业务控制
* @author:LJW
* @Tue Mar 07 15:39:22 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditContractHisFeign {
	
	
	
	@RequestMapping(value = "/mfCusCreditContractHis/getCreditContractHisList")
	public List<MfCusCreditContractHis> getCreditContractHisList(@RequestBody MfCusCreditContractHis mfCusCreditContractHis) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContractHis/getById")
	public MfCusCreditContractHis getById(@RequestBody MfCusCreditContractHis mfCusCreditContractHis) throws ServiceException;
	
}
