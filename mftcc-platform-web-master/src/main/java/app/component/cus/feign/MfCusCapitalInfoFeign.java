package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCapitalInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCapitalInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Jun 03 16:21:44 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCapitalInfoFeign {
	
	@RequestMapping("/mfCusCapitalInfo/insert")
	public void insert(@RequestBody MfCusCapitalInfo mfCusCapitalInfo) throws Exception;
	
	@RequestMapping("/mfCusCapitalInfo/delete")
	public void delete(@RequestBody MfCusCapitalInfo mfCusCapitalInfo) throws Exception;
	
	@RequestMapping("/mfCusCapitalInfo/update")
	public void update(@RequestBody MfCusCapitalInfo mfCusCapitalInfo) throws Exception;
	
	@RequestMapping("/mfCusCapitalInfo/getById")
	public MfCusCapitalInfo getById(@RequestBody MfCusCapitalInfo mfCusCapitalInfo) throws Exception;
	
	@RequestMapping("/mfCusCapitalInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
