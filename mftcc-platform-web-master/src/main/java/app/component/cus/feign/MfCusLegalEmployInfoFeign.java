package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusLegalEmployInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusLegalEmployInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 03 17:52:29 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusLegalEmployInfoFeign {
	
	@RequestMapping("/mfCusLegalEmployInfo/insert")
	public void insert(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/insertForApp")
	public void insertForApp(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/delete")
	public void delete(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/update")
	public void update(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/getById")
	public MfCusLegalEmployInfo getById(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusLegalEmployInfo/findByEntity")
	public List<MfCusLegalEmployInfo> findByEntity(@RequestBody MfCusLegalEmployInfo mfCusLegalEmployInfo) throws Exception;
	
}
