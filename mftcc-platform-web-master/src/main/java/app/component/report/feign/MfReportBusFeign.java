package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportBus;

@FeignClient("mftcc-platform-factor")
public interface MfReportBusFeign {
	@RequestMapping(value = "/mfReportBus/getBusList")
	public List<MfReportBus> getBusList(@RequestBody MfReportBus mfReportBus) throws ServiceException; 
}
