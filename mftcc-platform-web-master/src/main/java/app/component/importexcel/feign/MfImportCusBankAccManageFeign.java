package app.component.importexcel.feign;

import app.component.importexcel.entity.MfImportCusBankAccManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Title: MfImportCusBankAccManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat May 27 17:08:34 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfImportCusBankAccManageFeign {
	
	@RequestMapping(value = "/mfImportCusBankAccManage/insert")
	public MfImportCusBankAccManage insert(@RequestBody MfImportCusBankAccManage mfImportCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/mfImportCusBankAccManage/delete")
	public void delete(@RequestBody MfImportCusBankAccManage mfImportCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/mfImportCusBankAccManage/update")
	public void update(@RequestBody MfImportCusBankAccManage mfImportCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/mfImportCusBankAccManage/getById")
	public MfImportCusBankAccManage getById(@RequestBody MfImportCusBankAccManage mfImportCusBankAccManage) throws Exception;
	
	@RequestMapping(value = "/mfImportCusBankAccManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfImportCusBankAccManage") MfImportCusBankAccManage mfImportCusBankAccManage) throws Exception;
}
