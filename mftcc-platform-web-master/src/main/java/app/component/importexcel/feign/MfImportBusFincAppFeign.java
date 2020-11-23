package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportBusFincApp;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportBusFincAppBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportBusFincAppFeign {
	
	@RequestMapping(value = "/mfImportBusFincApp/insert")
	public MfImportBusFincApp insert(@RequestBody MfImportBusFincApp mfImportBusFincApp) throws Exception;
	
	@RequestMapping(value = "/mfImportBusFincApp/delete")
	public void delete(@RequestBody MfImportBusFincApp mfImportBusFincApp) throws Exception;
	
	@RequestMapping(value = "/mfImportBusFincApp/update")
	public void update(@RequestBody MfImportBusFincApp mfImportBusFincApp) throws Exception;
	
	@RequestMapping(value = "/mfImportBusFincApp/getById")
	public MfImportBusFincApp getById(@RequestBody MfImportBusFincApp mfImportBusFincApp) throws Exception;
	
	@RequestMapping(value = "/mfImportBusFincApp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportBusFincApp") MfImportBusFincApp mfImportBusFincApp) throws Exception;
}
