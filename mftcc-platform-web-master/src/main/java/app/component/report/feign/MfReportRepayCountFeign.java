package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.report.entity.MfBusMonth;
import app.component.report.entity.MfRepayDetail;

@FeignClient("mftcc-platform-factor")
public interface MfReportRepayCountFeign {

	/**
	 * 
	 * 方法描述： 还款统计明细表列表
	 * @return
	 * @throws ServiceException
	 * List<MfRepayDetail>
	 * @author lwq
	 * @date 2017-7-22 上午11:42:43
	 */
	@RequestMapping(value = "/mfReportRepayCount/getRepayDetailList")
	public List<MfRepayDetail> getRepayDetailList()throws ServiceException;
	
	/**
	 * 
	 * 方法描述：  获取业务经营统计月报表数据
	 * @return
	 * @throws ServiceException
	 * List<MfBusMonth>
	 * @author lwq
	 * @date 2017-7-23 上午11:56:33
	 */
	@RequestMapping(value = "/mfReportRepayCount/getBussinessMonthList")
	public List<MfBusMonth> getBussinessMonthList(@RequestBody String opNo,@RequestParam("reportId") String reportId,@RequestParam("sqlMap")  String sqlMap)throws Exception;
	
}
