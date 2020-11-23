package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportCusCorpBase;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportCusCorpBaseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportCusCorpBaseFeign {
	
	@RequestMapping(value = "/mfImportCusCorpBase/insert")
	public MfImportCusCorpBase insert(@RequestBody MfImportCusCorpBase mfImportCusCorpBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCorpBase/delete")
	public void delete(@RequestBody MfImportCusCorpBase mfImportCusCorpBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCorpBase/update")
	public void update(@RequestBody MfImportCusCorpBase mfImportCusCorpBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCorpBase/getById")
	public MfImportCusCorpBase getById(@RequestBody MfImportCusCorpBase mfImportCusCorpBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCorpBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportCusCorpBase") MfImportCusCorpBase mfImportCusCorpBase) throws Exception;
}
