package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCreditInvestigateInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditInvestigateInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Oct 16 11:47:03 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCreditInvestigateInfoFeign {
	
	@RequestMapping("/mfCusCreditInvestigateInfo/insert")
	public void insert(@RequestBody MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo) throws Exception;
	
	@RequestMapping("/mfCusCreditInvestigateInfo/delete")
	public void delete(@RequestBody MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo) throws Exception;
	
	@RequestMapping("/mfCusCreditInvestigateInfo/update")
	public void update(@RequestBody MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo) throws Exception;
	
	@RequestMapping("/mfCusCreditInvestigateInfo/getById")
	public MfCusCreditInvestigateInfo getById(@RequestBody MfCusCreditInvestigateInfo mfCusCreditInvestigateInfo) throws Exception;
	
	@RequestMapping("/mfCusCreditInvestigateInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
