package app.component.cus.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusCustomer;

@FeignClient("mftcc-platform-factor")
public interface CusFeign {
	@RequestMapping("/cusInterface/getById")
	MfCusCustomer getByIdMfCusCustomer(@RequestBody MfCusCustomer mfCusCustomer);

}
