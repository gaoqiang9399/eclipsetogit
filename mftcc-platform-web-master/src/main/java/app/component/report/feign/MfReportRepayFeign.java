package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportRepay;

@FeignClient("mftcc-platform-factor")
public interface MfReportRepayFeign {
	@RequestMapping(value = "/mfReportRepay/getRepay")
	public List<MfReportRepay> getRepay(@RequestBody MfReportRepay mfReportRepay) throws ServiceException; 
}
