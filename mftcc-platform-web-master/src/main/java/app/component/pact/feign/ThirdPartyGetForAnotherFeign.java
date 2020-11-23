package app.component.pact.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.ThirdPartyGetForAnother;
import app.util.toolkit.Ipage;

/**
 * Title: ThirdPartyGetForAnotherBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 16:34:21 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface ThirdPartyGetForAnotherFeign {

	@RequestMapping(value = "/thirdPartyGetForAnother/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("thirdPartyGetForAnother") ThirdPartyGetForAnother thirdPartyGetForAnother)
			throws Exception;

	@RequestMapping(value = "/thirdPartyGetForAnother/exportForExcel")
	public int exportForExcel(@RequestBody ThirdPartyGetForAnother thirdPartyGetForAnother,
			@RequestParam("templateFileName") String templateFileName, @RequestParam("destFileName") String destFileName) throws Exception;

}
