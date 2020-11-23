package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCorpMajorChange;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCorpMajorChangeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jun 03 19:42:31 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusCorpMajorChangeFeign {

	@RequestMapping("/mfCusCorpMajorChange/insert")
	public void insert(@RequestBody MfCusCorpMajorChange mfCusCorpMajorChange) throws Exception;
	
	@RequestMapping("/mfCusCorpMajorChange/delete")
	public void delete(@RequestBody MfCusCorpMajorChange mfCusCorpMajorChange) throws Exception;
	
	@RequestMapping("/mfCusCorpMajorChange/update")
	public void update(@RequestBody MfCusCorpMajorChange mfCusCorpMajorChange) throws Exception;
	
	@RequestMapping("/mfCusCorpMajorChange/getById")
	public MfCusCorpMajorChange getById(@RequestBody MfCusCorpMajorChange mfCusCorpMajorChange) throws Exception;
	
	@RequestMapping("/mfCusCorpMajorChange/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
