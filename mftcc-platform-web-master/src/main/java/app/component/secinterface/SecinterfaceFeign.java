package  app.component.secinterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.sec.entity.SecAuditConfig;
import app.component.sys.entity.SysUser;


/**
* Title: secinterface.java
* Description:
* @author:jzh@dhcc.com.cn
* @Mon Feb 22 05:39:01 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SecinterfaceFeign {
	@RequestMapping(value="/secinterface/securityPwd",produces="text/html;charset=utf-8")
	public String SecurityPwd(@RequestParam("userId") String userId) throws Exception;

	@RequestMapping("/secinterface/pwdTimes")
	public String PwdTimes(@RequestParam("userId") String userId) throws Exception;
	@RequestMapping(value = "/secinterface/changePassWordDate")
	public void changePassWordDate(@RequestBody String userId,@RequestParam("passwordUpdateTime") String passwordUpdateTime)throws ServiceException;
	@RequestMapping(value = "/secinterface/insertOrUpdate/{username}/{message}", method = RequestMethod.POST)
	public void insertOrUpdate(@PathVariable("username") String userId, @PathVariable("message") String message) throws Exception;
	@RequestMapping(value = "/secinterface/update")
	public void update(@RequestBody String userId)throws ServiceException;
	@RequestMapping(value = "/secinterface/insertAudit")
	public void insertAudit(@RequestBody SysUser sysUser,@RequestParam("userId") String userId,@RequestParam("userName") String userName,@RequestParam("auditType") String auditType,@RequestParam("changeMeg") String changeMeg)throws ServiceException;
	@RequestMapping(value = "/secinterface/SecurityChangePwd")
	public String SecurityChangePwd(@RequestBody String userId,@RequestParam("pwd") String pwd)throws ServiceException;
	@RequestMapping(value = "/secinterface/getById")
	public SecAuditConfig getById(@RequestBody String secId) throws ServiceException;
	@RequestMapping(value = "/secinterface/insertUserApptime")
	public void insertUserApptime(@RequestParam("urlStr") String urlStr,@RequestParam("tlrno") String tlrno,@RequestBody double ce) throws ServiceException;
	
}


