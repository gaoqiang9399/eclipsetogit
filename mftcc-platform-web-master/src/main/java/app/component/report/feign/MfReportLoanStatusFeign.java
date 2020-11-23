package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.report.entity.MfReportStatus;

@FeignClient("mftcc-platform-factor")
public interface MfReportLoanStatusFeign {
	
	/**
	 * 
	 * 方法描述： 贷款情况汇总表数据源
	 * @return
	 * @throws ServiceException
	 * List<MfReportStatus>
	 * @author lwq
	 * @date 2017-7-11 下午2:21:53
	 */
	@RequestMapping(value = "/mfReportLoanStatus/getMfReportStatusList")
	public List<MfReportStatus> getMfReportStatusList(@RequestBody String opNo,@RequestParam("reportId") String reportId,@RequestParam("sql") String sql) throws ServiceException;
}
