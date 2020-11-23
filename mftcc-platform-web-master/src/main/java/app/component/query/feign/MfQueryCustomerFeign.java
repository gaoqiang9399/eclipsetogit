package app.component.query.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.query.entity.MfQueryCustomer;

@FeignClient("mftcc-platform-factor")
public interface MfQueryCustomerFeign {
	@RequestMapping(value = "/mfQueryCustomer/getCustomer")
	public List<MfQueryCustomer> getCustomer() throws Exception;
}
