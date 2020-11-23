package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportFinc;

@FeignClient("mftcc-platform-factor")
public interface MfReportFincFeign {
	@RequestMapping(value = "/mfReportFinc/getFincList")
	public List<MfReportFinc> getFincList(@RequestBody MfReportFinc mfReportFinc) throws ServiceException;
}
