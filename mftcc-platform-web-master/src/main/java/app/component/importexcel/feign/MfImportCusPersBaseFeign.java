package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportCusPersBase;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* Title: MfImportCusPersBaseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportCusPersBaseFeign {
	
	@RequestMapping(value = "/mfImportCusPersBase/insert")
	public MfImportCusPersBase insert(@RequestBody MfImportCusPersBase mfImportCusPersBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusPersBase/delete")
	public void delete(@RequestBody MfImportCusPersBase mfImportCusPersBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusPersBase/update")
	public void update(@RequestBody MfImportCusPersBase mfImportCusPersBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusPersBase/getById")
	public MfImportCusPersBase getById(@RequestBody MfImportCusPersBase mfImportCusPersBase) throws Exception;
	
	@RequestMapping(value = "/mfImportCusPersBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportCusPersBase") MfImportCusPersBase mfImportCusPersBase) throws Exception;

	@RequestMapping(value = "/mfImportCusPersBase/getMfImportCusPersBaseList")
	public List<MfImportCusPersBase> getMfImportCusPersBaseList(@RequestBody MfImportCusPersBase mfImportCusPersBase) throws Exception;
}
