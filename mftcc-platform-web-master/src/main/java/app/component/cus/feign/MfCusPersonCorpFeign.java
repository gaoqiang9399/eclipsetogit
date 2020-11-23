package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonCorp;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonCorpBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Jul 30 10:28:20 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonCorpFeign {
	
	@RequestMapping("/mfCusPersonCorp/insert")
	public void insert(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/insertForApp")
	public MfCusPersonCorp insertForApp(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/delete")
	public void delete(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/update")
	public void update(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/getById")
	public MfCusPersonCorp getById(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/getCusPersonCorpInfo")
	public MfCusPersonCorp getCusPersonCorpInfo(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/getPersonCorpList")
	public List<MfCusPersonCorp> getPersonCorpList(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
	
	@RequestMapping("/mfCusPersonCorp/getAllPersonCorpList")
	public List<MfCusPersonCorp> getAllPersonCorpList(@RequestBody MfCusPersonCorp mfCusPersonCorp) throws Exception;
}
