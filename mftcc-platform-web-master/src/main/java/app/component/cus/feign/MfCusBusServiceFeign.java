package app.component.cus.feign;

import app.component.cus.entity.MfCusBusService;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Title: MfCusAccountDebtorBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jul 17 12:15:38 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusBusServiceFeign {

	@RequestMapping(value = "/mfCusBusService/insert")
	public void insert(@RequestBody MfCusBusService mfCusBusService) throws Exception;

	@RequestMapping(value = "/mfCusBusService/delete")
	public void delete(@RequestBody MfCusBusService mfCusBusService) throws Exception;

	@RequestMapping(value = "/mfCusBusService/update")
	public void update(@RequestBody MfCusBusService mfCusBusService) throws Exception;

	@RequestMapping(value = "/mfCusBusService/getById")
	public MfCusBusService getById(@RequestBody MfCusBusService mfCusBusService) throws Exception;
	
	@RequestMapping(value = "/mfCusBusService/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
