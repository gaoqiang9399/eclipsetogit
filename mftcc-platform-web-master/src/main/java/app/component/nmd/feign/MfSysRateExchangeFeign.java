package app.component.nmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.MfSysRateExchange;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysRateExchangeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 05 20:50:06 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfSysRateExchangeFeign {

	@RequestMapping("/mfSysRateExchange/insert")
	public void insert(@RequestBody MfSysRateExchange mfSysRateExchange) throws ServiceException;

	@RequestMapping("/mfSysRateExchange/delete")
	public void delete(@RequestBody MfSysRateExchange mfSysRateExchange) throws ServiceException;

	@RequestMapping("/mfSysRateExchange/update")
	public void update(@RequestBody MfSysRateExchange mfSysRateExchange) throws ServiceException;

	@RequestMapping("/mfSysRateExchange/getById")
	public MfSysRateExchange getById(@RequestBody MfSysRateExchange mfSysRateExchange) throws ServiceException;

	@RequestMapping("/mfSysRateExchange/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage/*,@RequestParam("mfSysRateExchange") MfSysRateExchange mfSysRateExchange*/) throws ServiceException;

	@RequestMapping("/mfSysRateExchange/isInstance")
	public boolean isInstance(@RequestBody MfSysRateExchange mfSysRateExchange);

}