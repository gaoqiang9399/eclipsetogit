package app.component.interfaces.mobileinterface.feign;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * 
 * 处理融资申请的Bo类
 * @author zhang_dlei
 *
 */
@FeignClient("mftcc-platform-factor")
public interface AppCalcRepayPlanFeign{
	
	@RequestMapping(value = "/appCalcRepayPlan/getRepayAmtByCusNo")
	public Map<String, Object> getRepayAmtByCusNo(@RequestBody  String cusNo) throws Exception;
	
	
}
