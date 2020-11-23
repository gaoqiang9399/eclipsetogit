package  app.component.calc.fee.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.calc.fee.entity.MfBusFeeDetail;
import app.util.toolkit.Ipage;

/**
* Title: MfBusFeeDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 08 14:39:47 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusFeeDetailFeign {
	
	@RequestMapping(value = "/mfBusFeeDetail/insert")
	public void insert(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws ServiceException;
	
	@RequestMapping(value = "/mfBusFeeDetail/delete")
	public void delete(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws ServiceException;
	
	@RequestMapping(value = "/mfBusFeeDetail/update")
	public void update(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws ServiceException;
	
	@RequestMapping(value = "/mfBusFeeDetail/getById")
	public MfBusFeeDetail getById(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws ServiceException;
	
	@RequestMapping(value = "/mfBusFeeDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusFeeDetail") MfBusFeeDetail mfBusFeeDetail) throws ServiceException;
	
}
