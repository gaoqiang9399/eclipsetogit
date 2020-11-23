package app.component.report.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.report.entity.MfReportFilter;

@FeignClient("mftcc-platform-factor")
public interface MfReportFilterFeign {
	@RequestMapping(value = "/mfReportFilter/getById")
	public MfReportFilter getById(@RequestBody MfReportFilter mfReportFilter) throws ServiceException;
	@RequestMapping(value = "/mfReportFilter/insert")
	public void insert(@RequestBody MfReportFilter mfReportFilter) throws ServiceException;
	@RequestMapping(value = "/mfReportFilter/delete")
	public void delete(@RequestBody MfReportFilter mfReportFilter) throws ServiceException;
	@RequestMapping(value = "/mfReportFilter/updateUseFlag")
	public void updateUseFlag(@RequestBody MfReportFilter mfReportFilter) throws ServiceException;
	@RequestMapping(value = "/mfReportFilter/getFilter")
	public List<MfReportFilter> getFilter(@RequestBody MfReportFilter mfReportFilter) throws ServiceException;
}
