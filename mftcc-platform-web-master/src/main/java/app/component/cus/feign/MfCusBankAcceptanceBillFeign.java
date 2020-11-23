package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusBankAcceptanceBill;
import app.util.toolkit.Ipage;

/**
* Title: MfCusBankAcceptanceBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Feb 07 10:34:26 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusBankAcceptanceBillFeign {
	
	@RequestMapping("/mfCusBankAcceptanceBill/insert")
	public void insert(@RequestBody MfCusBankAcceptanceBill mfCusBankAcceptanceBill) throws Exception;
	
	@RequestMapping("/mfCusBankAcceptanceBill/delete")
	public void delete(@RequestBody MfCusBankAcceptanceBill mfCusBankAcceptanceBill) throws Exception;
	
	@RequestMapping("/mfCusBankAcceptanceBill/update")
	public void update(@RequestBody MfCusBankAcceptanceBill mfCusBankAcceptanceBill) throws Exception;
	
	@RequestMapping("/mfCusBankAcceptanceBill/getById")
	public MfCusBankAcceptanceBill getById(@RequestBody MfCusBankAcceptanceBill mfCusBankAcceptanceBill) throws Exception;
	
	@RequestMapping("/mfCusBankAcceptanceBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
