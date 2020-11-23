package app.component.cus.gualoan.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.entity.MfCusGuaLoanOuterSum;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfCusGuaLoanOuterSumFeign {
	@RequestMapping(value = "/mfCusGuaLoanOuterSum/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGuaLoanOuterSum/insert")
	public Map<String, Object> insert(@RequestBody MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGuaLoanOuterSum/getById")
	public MfCusGuaLoanOuterSum getById(@RequestBody MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum) throws ServiceException;
	
	@RequestMapping("/mfCusGuaLoanOuterSum/update")
	public void update(@RequestBody MfCusGuaLoanOuterSum mfCusGuaLoanOuterSum) throws Exception;
}
