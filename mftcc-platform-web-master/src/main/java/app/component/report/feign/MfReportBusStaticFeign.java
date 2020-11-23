package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.report.entity.MfReportStatic;

/**
 * 业务综合汇总表的业务
 * @author LiWQ
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfReportBusStaticFeign {

	/**
	 * 
	 * 方法描述： 业务综合汇总表的数据源
	 * @return
	 * @throws ServiceException
	 * List<MfReportStatic>
	 * @author lwq
	 * @date 2017-7-14 上午11:55:16
	 */
	@RequestMapping(value = "/mfReportBusStatic/getMfReportStaticList")
	public List<MfReportStatic> getMfReportStaticList(@RequestBody String opNo,@RequestParam("reportId") String reportId,@RequestParam("sqlMap") String sqlMap)throws ServiceException;
}
