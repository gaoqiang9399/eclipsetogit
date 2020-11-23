package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfCreditQueryRecordHis;
import app.component.report.entity.MfReportCustomer;

@FeignClient("mftcc-platform-factor")
public interface MfReportCustomerFeign {
	@RequestMapping(value = "/mfReportCustomer/getCusList")
	public List<MfReportCustomer> getCusList(@RequestBody MfReportCustomer mfReportCustomer) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得客户征信查询台账
	 * @param mfCreditQueryRecordHis
	 * @return
	 * @throws Exception
	 * List<MfCreditQueryRecordHis>
	 * @author 沈浩兵
	 * @date 2018-1-17 下午4:30:54
	 */
	@RequestMapping(value = "/mfReportCustomer/getCreditQueryList")
	public List<MfCreditQueryRecordHis> getCreditQueryList(@RequestBody MfCreditQueryRecordHis mfCreditQueryRecordHis) throws Exception;
}
