package app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfCreditQueryApp;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* Title: MfCreditQueryAppBo.java
* Description:授信模型配置业务控制
* @author:LJW
* @Thu Feb 23 16:13:12 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCreditQueryAppFeign {
	
	@RequestMapping(value = "/mfCreditQueryApp/insert")
	public void insert(@RequestBody MfCreditQueryApp mfCreditQueryApp) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryApp/delete")
	public void delete(@RequestBody MfCreditQueryApp mfCreditQueryApp) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryApp/update")
	public void update(@RequestBody MfCreditQueryApp mfCreditQueryApp) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryApp/getById")
	public MfCreditQueryApp getById(@RequestBody MfCreditQueryApp mfCreditQueryApp) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryApp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfCreditQueryApp/getCountByCusNo")
    public int getCountByCusNo(@RequestParam("cusNo") String cusNo) throws Exception;
}
