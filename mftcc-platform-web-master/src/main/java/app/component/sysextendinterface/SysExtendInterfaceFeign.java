package  app.component.sysextendinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysUser;


/**
* Title: sysextendinterface.java
* Description:
* @author:LiuYF@dhcc.com.cn
* @Mon Dec 26 16:51:15 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysExtendInterfaceFeign {
	@RequestMapping(value = "/sysExtendInterface/getAllUsers")
	public List<SysUser> getAllUsers() throws Exception;
	@RequestMapping(value = "/sysExtendInterface/getAllOrgs")
	public List<SysOrg> getAllOrgs() throws Exception;
	@RequestMapping(value = "/sysExtendInterface/getUserByRole")
	public String getUserByRole(@RequestBody String opNo) throws Exception; 
}


