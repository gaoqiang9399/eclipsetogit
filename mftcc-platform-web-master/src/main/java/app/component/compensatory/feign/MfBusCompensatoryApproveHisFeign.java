package  app.component.compensatory.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Title: MfBusCompensatoryApproveHisFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:01:16 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusCompensatoryApproveHisFeign{
	
}
