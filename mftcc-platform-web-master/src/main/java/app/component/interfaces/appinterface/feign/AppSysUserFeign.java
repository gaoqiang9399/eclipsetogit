package app.component.interfaces.appinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



/**
 * 用户登录信息管理的Bo类
 * @author zhang_dlei
 */
@FeignClient("mftcc-platform-factor")
public interface AppSysUserFeign{
	
	@RequestMapping(value = "/appSysUser/sendMessage")
	public Map<String, Object> sendMessage(@RequestBody  String mobile) throws Exception;

	@RequestMapping(value = "/appSysUser/validateVerifyCode")
	public Map<String, Object> validateVerifyCode(@RequestBody  String mobile,@RequestParam("verifyNum") String verifyNum);

	@RequestMapping(value = "/appSysUser/login")
	public Map<String, Object> login(@RequestBody  String mobile,@RequestParam("verifyNum") String verifyNum) throws Exception;

	@RequestMapping(value = "/appSysUser/getPersonalCenterInfo")
	public Map<String, Object> getPersonalCenterInfo(@RequestBody  String opNo) throws Exception;
	
	
}
