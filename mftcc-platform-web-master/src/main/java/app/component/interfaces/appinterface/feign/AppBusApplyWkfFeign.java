package app.component.interfaces.appinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;





/**
 * 处理业务审批的Bo类
 * @author zhang_dlei
 */
@FeignClient("mftcc-platform-factor")
public interface AppBusApplyWkfFeign{
	
	@RequestMapping(value = "/appBusApplyWkf/insert")
	public Map<String, Object> submitProcess(@RequestParam("appId")  String appId,@RequestParam("opNo") String opNo,@RequestBody HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appBusApplyWkf/getApprovalForBus")
	public Map<String, Object> getApprovalForBus(@RequestBody  String appId,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/appBusApplyWkf/submitUpdateAjax")
	public Map<String, Object> submitUpdateAjax(@RequestBody  String ajaxData,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;
	
}
