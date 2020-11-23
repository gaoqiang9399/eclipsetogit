package app.component.interfaces.appinterface.feign;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.util.toolkit.Ipage;

/**
 * 
 * 处理融资申请的Bo类
 * @author zhang_dlei
 *
 */
@FeignClient("mftcc-platform-factor")
public interface AppBusApplyFeign{
	
	@RequestMapping(value = "/appBusApply/insert")
	public String insert(@RequestBody  MfBusApply mfBusApply,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/appBusApply/getById")
	public Map<String, Object> getById(@RequestBody  String appId) throws Exception;

	@RequestMapping(value = "/appBusApply/findByPage")
	public List<Map<String, Object>> findByPage(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo) throws Exception;
	
	
}
