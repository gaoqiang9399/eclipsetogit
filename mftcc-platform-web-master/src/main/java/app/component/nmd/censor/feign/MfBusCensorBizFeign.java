package app.component.nmd.censor.feign;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.censor.entity.MfBusCensorBiz;
import app.util.toolkit.Ipage;



/**
* Title: MfBusCensorBizBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 15 14:49:56 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCensorBizFeign {
	
	@RequestMapping(value = "/mfBusCensorBiz/insert")
	public void insert(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBiz/delete")
	public void delete(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBiz/update")
	public void update(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBiz/getById")
	public MfBusCensorBiz getById(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCensorBiz/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCensorBiz") MfBusCensorBiz mfBusCensorBiz) throws ServiceException;
	
}
