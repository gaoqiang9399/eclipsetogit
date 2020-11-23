package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusSurveySocialCredit;
import app.util.toolkit.Ipage;

/**
* Title: MfCusSurveySocialCreditBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Sep 16 12:09:44 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusSurveySocialCreditFeign {
	
	@RequestMapping("/mfCusSurveySocialCredit/insert")
	public void insert(@RequestBody MfCusSurveySocialCredit mfCusSurveySocialCredit) throws Exception;
	
	@RequestMapping("/mfCusSurveySocialCredit/delete")
	public void delete(@RequestBody MfCusSurveySocialCredit mfCusSurveySocialCredit) throws Exception;
	
	@RequestMapping("/mfCusSurveySocialCredit/update")
	public void update(@RequestBody MfCusSurveySocialCredit mfCusSurveySocialCredit) throws Exception;
	
	@RequestMapping("/mfCusSurveySocialCredit/getById")
	public MfCusSurveySocialCredit getById(@RequestBody MfCusSurveySocialCredit mfCusSurveySocialCredit) throws Exception;
	
	@RequestMapping("/mfCusSurveySocialCredit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
