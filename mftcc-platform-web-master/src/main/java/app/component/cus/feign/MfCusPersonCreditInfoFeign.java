package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonCreditInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonCreditInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 11 09:23:17 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonCreditInfoFeign {
	
	@RequestMapping("/mfCusPersonCreditInfo/insert")
	public void insert(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/insertForApp")
	public MfCusPersonCreditInfo insertForApp(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/delete")
	public void delete(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/update")
	public void update(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/getById")
	public MfCusPersonCreditInfo getById(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonCreditInfo/getList")
	public List<MfCusPersonCreditInfo> getList(@RequestBody MfCusPersonCreditInfo mfCusPersonCreditInfo) throws Exception;
	
}
