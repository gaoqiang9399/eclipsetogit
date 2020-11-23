package  app.component.cus.payment.feign;

import app.component.cus.payment.entity.MfCusAssurePayment;
import app.util.toolkit.Ipage;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


/**
* Title: mfCusAssurePaymentBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed May 02 14:12:41 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusAssurePaymentFeign {
	@RequestMapping(value = "/mfCusAssurePayment/insert")
	public void insert(@RequestBody MfCusAssurePayment mfCusAssurePayment) throws Exception;
	
	@RequestMapping(value = "/mfCusAssurePayment/delete")
	public void delete(@RequestBody MfCusAssurePayment mfCusAssurePayment) throws Exception;
	
	@RequestMapping(value = "/mfCusAssurePayment/update")
	public void update(@RequestBody MfCusAssurePayment mfCusAssurePayment) throws Exception;
	
	@RequestMapping(value = "/mfCusAssurePayment/getById")
	public MfCusAssurePayment getById(@RequestBody MfCusAssurePayment mfCusAssurePayment) throws Exception;
	
	@RequestMapping(value = "/mfCusAssurePayment/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusAssurePayment/findPageByAssureNo")
	public Ipage findPageByAssureNo(@RequestBody Ipage ipage) throws Exception;
	
	
	
}
