package app.component.cus.report.feign;

import app.component.cus.report.entity.MfCusReportErrorInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfCusReportErrorInfoFeign {
    @RequestMapping(value = "/mfCusReportErrorInfo/getList")
    public List<MfCusReportErrorInfo> getList(@RequestBody MfCusReportErrorInfo mfCusReportErrorInfo) throws Exception;

    @RequestMapping(value = "/mfCusReportErrorInfo/getByErrorInfo")
    public MfCusReportErrorInfo getByErrorInfo(@RequestBody MfCusReportErrorInfo mfCusReportErrorInfo) throws Exception;
}
