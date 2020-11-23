package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportCusCreditContract;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportCusCreditContractBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportCusCreditContractFeign {
	
	@RequestMapping(value = "/mfImportCusCreditContract/insert")
	public MfImportCusCreditContract insert(@RequestBody MfImportCusCreditContract mfImportCusCreditContract) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCreditContract/delete")
	public void delete(@RequestBody MfImportCusCreditContract mfImportCusCreditContract) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCreditContract/update")
	public void update(@RequestBody MfImportCusCreditContract mfImportCusCreditContract) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCreditContract/getById")
	public MfImportCusCreditContract getById(@RequestBody MfImportCusCreditContract mfImportCusCreditContract) throws Exception;
	
	@RequestMapping(value = "/mfImportCusCreditContract/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportCusCreditContract") MfImportCusCreditContract mfImportCusCreditContract) throws Exception;
}
