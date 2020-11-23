package app.component.pact.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.ThirdPartyPayForAnother;
import app.util.toolkit.Ipage;

/**
 * Title: ThirdPartyPayForAnotherBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Aug 14 15:16:07 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface ThirdPartyPayForAnotherFeign {

	@RequestMapping(value = "/thirdPartyPayForAnother/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("thirdPartyPayForAnother") ThirdPartyPayForAnother thirdPartyPayForAnother)
			throws Exception;

	@RequestMapping(value = "/thirdPartyPayForAnother/exportForExcel")
	public int exportForExcel(@RequestBody ThirdPartyPayForAnother thirdPartyPayForAnother,
			@RequestParam("templateFileName") String templateFileName, @RequestParam("destFileName") String destFileName) throws Exception;

}
