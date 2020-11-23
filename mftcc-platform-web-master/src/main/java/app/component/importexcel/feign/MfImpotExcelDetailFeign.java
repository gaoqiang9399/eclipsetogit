package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImpotExcelDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImpotExcelDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImpotExcelDetailFeign {
	
	@RequestMapping(value = "/mfImpotExcelDetail/insert")
	public MfImpotExcelDetail insert(@RequestBody MfImpotExcelDetail mfImpotExcelDetail) throws Exception;
	
	@RequestMapping(value = "/mfImpotExcelDetail/delete")
	public void delete(@RequestBody MfImpotExcelDetail mfImpotExcelDetail) throws Exception;
	
	@RequestMapping(value = "/mfImpotExcelDetail/update")
	public void update(@RequestBody MfImpotExcelDetail mfImpotExcelDetail) throws Exception;
	
	@RequestMapping(value = "/mfImpotExcelDetail/getById")
	public MfImpotExcelDetail getById(@RequestBody MfImpotExcelDetail mfImpotExcelDetail) throws Exception;
	
	@RequestMapping(value = "/mfImpotExcelDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
