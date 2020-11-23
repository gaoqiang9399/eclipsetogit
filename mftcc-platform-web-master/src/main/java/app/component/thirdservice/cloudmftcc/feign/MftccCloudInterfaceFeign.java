package app.component.thirdservice.cloudmftcc.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MftccCloudInterfaceFeign {
	@RequestMapping(value = "/mftccCloudInterface/highcourtP",produces="text/html;charset=utf-8")
	public String  highcourtP(@RequestBody Map<String,String> paramMap) throws Exception;
	//企业法执情况走的是三方查询，不在从法执情况按钮进入
	@RequestMapping(value = "/mftccCloudInterface/corpLawEnforcement")
	public Map<String,String> corpLawEnforcement(@RequestBody Map<String,String> paramMap) throws Exception;
	
	//个人法执情况走的是三方查询，不在从法执情况按钮进入
	@RequestMapping(value = "/mftccCloudInterface/personLawEnforcement")
	public Map<String,String> personLawEnforcement(@RequestBody Map<String,String> paramMap) throws Exception;
}
