package app.component.nmd.censor.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.censor.entity.MfBusCensorBase;
import app.util.toolkit.Ipage;



/**
* Title: MfBusCensorBaseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 15 14:44:57 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCensorBaseFeign {
	
	@RequestMapping(value = "/mfBusCensorBase/insert")
	public void insert(@RequestBody MfBusCensorBase mfBusCensorBase) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBase/delete")
	public void delete(@RequestBody MfBusCensorBase mfBusCensorBase) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBase/update")
	public void update(@RequestBody MfBusCensorBase mfBusCensorBase) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBase/getById")
	public MfBusCensorBase getById(@RequestBody MfBusCensorBase mfBusCensorBase) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCensorBase") MfBusCensorBase mfBusCensorBase) throws ServiceException;
	
}
