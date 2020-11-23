package  app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusBranchOrganization;
import app.util.toolkit.Ipage;

/**
* Title: MfCusBranchOrganizationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Apr 23 16:01:54 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusBranchOrganizationFeign {
	@RequestMapping(value = "/mfCusBranchOrganization/insert")
	public void insert(@RequestBody MfCusBranchOrganization mfCusBranchOrganization) throws Exception;
	@RequestMapping(value = "/mfCusBranchOrganization/delete")
	public void delete(@RequestBody MfCusBranchOrganization mfCusBranchOrganization) throws Exception;
	@RequestMapping(value = "/mfCusBranchOrganization/update")
	public void update(@RequestBody MfCusBranchOrganization mfCusBranchOrganization) throws Exception;
	@RequestMapping(value = "/mfCusBranchOrganization/getById")
	public MfCusBranchOrganization getById(@RequestBody MfCusBranchOrganization mfCusBranchOrganization) throws Exception;
	@RequestMapping(value = "/mfCusBranchOrganization/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
