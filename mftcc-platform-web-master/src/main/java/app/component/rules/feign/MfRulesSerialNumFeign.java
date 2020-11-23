package  app.component.rules.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.rules.entity.MfRulesSerialNum;
import app.util.toolkit.Ipage;

/**
* Title: RulesSerialNumBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Sep 22 15:10:46 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRulesSerialNumFeign {
	
	@RequestMapping(value = "/mfRulesSerialNum/insert")
	public void insert(@RequestBody MfRulesSerialNum mfRulesSerialNum) throws Exception;
	
	@RequestMapping(value = "/mfRulesSerialNum/delete")
	public void delete(@RequestBody MfRulesSerialNum mfRulesSerialNum) throws Exception;
	
	@RequestMapping(value = "/mfRulesSerialNum/update")
	public void update(@RequestBody MfRulesSerialNum mfRulesSerialNum) throws Exception;
	
	@RequestMapping(value = "/mfRulesSerialNum/getById")
	public MfRulesSerialNum getById(@RequestBody MfRulesSerialNum mfRulesSerialNum) throws Exception;
	
	@RequestMapping(value = "/mfRulesSerialNum/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRulesSerialNum") MfRulesSerialNum mfRulesSerialNum) throws Exception;
	
}
