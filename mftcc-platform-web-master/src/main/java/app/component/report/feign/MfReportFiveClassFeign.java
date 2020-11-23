package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportFiveClass;

@FeignClient("mftcc-platform-factor")
public interface MfReportFiveClassFeign {
	@RequestMapping(value = "/mfReportFiveClass/getFiveClass")
	public List<MfReportFiveClass> getFiveClass(@RequestBody MfReportFiveClass mfReportFiveClass) throws ServiceException; 
}