package app.component.interfaces.appinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.sys.entity.SysTaskInfo;
import app.component.sys.entity.SysUser;
@FeignClient("mftcc-platform-factor")
public interface DingInterfaceFeign{
	
	@RequestMapping(value = "/dingInterface/insert")
	public Map<String, String> getDingJSConfig(@RequestBody  String url) throws Exception;

	@RequestMapping(value = "/dingInterface/getUserInfo")
	public SysUser getUserInfo(@RequestBody  String code) throws Exception;

	@RequestMapping(value = "/dingInterface/sendMessage")
	public void sendMessage(@RequestParam("opNo")  String opNo,@RequestParam("pasMinNo") String pasMinNo,@RequestParam("content") String content,@RequestBody SysTaskInfo sysTaskInfo) throws Exception;
	
}
