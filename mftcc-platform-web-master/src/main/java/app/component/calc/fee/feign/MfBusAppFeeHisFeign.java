package  app.component.calc.fee.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.calc.fee.entity.MfBusAppFeeHis;
import app.util.toolkit.Ipage;

/**
* Title: MfBusAppFeeHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 30 15:47:24 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusAppFeeHisFeign {
	
	@RequestMapping(value = "/mfBusAppFeeHis/insert")
	public void insert(@RequestBody MfBusAppFeeHis mfBusAppFeeHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFeeHis/delete")
	public void delete(@RequestBody MfBusAppFeeHis mfBusAppFeeHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFeeHis/update")
	public void update(@RequestBody MfBusAppFeeHis mfBusAppFeeHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFeeHis/getById")
	public MfBusAppFeeHis getById(@RequestBody MfBusAppFeeHis mfBusAppFeeHis) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAppFeeHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusAppFeeHis") MfBusAppFeeHis mfBusAppFeeHis) throws ServiceException;
	
}
