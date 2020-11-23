package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.report.entity.MfReportRecall;

@FeignClient("mftcc-platform-factor")
public interface MfReportRecallFeign {
	@RequestMapping(value = "/mfReportRecall/getRecall")
	public List<MfReportRecall> getRecall(@RequestBody MfReportRecall mfReportRecall,@RequestParam("opNo") String opNo,@RequestParam("reportId") String reportId,@RequestParam("sqlMap") String sqlMap) throws Exception; 
}
