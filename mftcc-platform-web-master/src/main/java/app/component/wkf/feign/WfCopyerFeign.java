package  app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.wkf.entity.WfCopyer;

/**
* Title: WfCopyerBo.java
* Description:
* @author:@dhcc.com.cn
* @Sat Dec 17 01:56:07 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface WfCopyerFeign {
	@RequestMapping(value = "/wfCopyer/getById")
	public WfCopyer getById(@RequestBody WfCopyer wfCopyer) throws ServiceException;
	@RequestMapping(value = "/wfCopyer/getByTaskId")
	public WfCopyer getByTaskId(@RequestBody WfCopyer wfCopyer) throws ServiceException;
	
}
