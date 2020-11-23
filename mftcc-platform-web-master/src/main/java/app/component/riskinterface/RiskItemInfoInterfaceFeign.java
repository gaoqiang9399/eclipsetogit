package app.component.riskinterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface RiskItemInfoInterfaceFeign {
	/**
	 * 授信金额检查
	 * 融资额不能大于授信余额
	 * 高风险
	 * @param appNo 业务申请号
	 * @param actualAmt 融资额
	 * 
	 */
	@RequestMapping(value = "/riskItemInfoInterface/checkAuth")
	public String checkAuth(@RequestBody String actualAmt, @RequestParam String appNo) throws Exception;
}
