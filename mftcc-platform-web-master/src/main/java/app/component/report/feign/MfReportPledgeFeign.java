package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportPledge;

@FeignClient("mftcc-platform-factor")
public interface MfReportPledgeFeign {
	@RequestMapping(value = "/mfReportPledge/getPledgeList")
	public List<MfReportPledge> getPledgeList(@RequestBody MfReportPledge mfReportPledge) throws ServiceException;
}
