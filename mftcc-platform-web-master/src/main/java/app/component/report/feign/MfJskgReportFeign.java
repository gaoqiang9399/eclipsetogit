package app.component.report.feign;

import app.base.ServiceException;
import app.component.report.entity.MfBalAnalysis;
import app.component.report.entity.MfInfoSource;
import app.component.report.entity.MfReportPerfaStaBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfJskgReportFeign {

	/**
	 * 获取业绩统计表
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/mfJskgReport/getPerformanceStaList")
	public List<MfReportPerfaStaBean> getPerformanceStaList(@RequestBody Map<String, String> map) throws Exception ;

}
