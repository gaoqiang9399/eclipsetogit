package app.component.cus.report.feign;

import app.component.cus.report.entity.MfCusReportCheck;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfCusReportCheckFeign {

    @RequestMapping(value = "/mfCusReportCheck/getList")
    public List<MfCusReportCheck> getList(@RequestBody MfCusReportCheck mfCusReportCheck) throws Exception;

}
