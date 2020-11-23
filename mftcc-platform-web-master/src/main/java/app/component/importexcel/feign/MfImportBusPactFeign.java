package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportBusPact;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportBusPactBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportBusPactFeign {
	
	@RequestMapping(value = "/mfImportBusPact/insert")
	public MfImportBusPact insert(@RequestBody MfImportBusPact mfImportBusPact) throws Exception;
	
	@RequestMapping(value = "/mfImportBusPact/delete")
	public void delete(@RequestBody MfImportBusPact mfImportBusPact) throws Exception;
	
	@RequestMapping(value = "/mfImportBusPact/update")
	public void update(@RequestBody MfImportBusPact mfImportBusPact) throws Exception;
	
	@RequestMapping(value = "/mfImportBusPact/getById")
	public MfImportBusPact getById(@RequestBody MfImportBusPact mfImportBusPact) throws Exception;
	
	@RequestMapping(value = "/mfImportBusPact/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportBusPact") MfImportBusPact mfImportBusPact) throws Exception;
}
