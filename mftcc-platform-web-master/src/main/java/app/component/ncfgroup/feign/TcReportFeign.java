package app.component.ncfgroup.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface TcReportFeign {
	/**
	 * 根据客户号获取网信认证报告
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getWangxinReport")
	public Map<String, Object> getWangxinReport(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据客户号获取小风策报告
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/getRiskAssessmentReport")
	public Map<String, Object> getRiskAssessmentReport(@RequestBody String cusNo) throws Exception;
}
