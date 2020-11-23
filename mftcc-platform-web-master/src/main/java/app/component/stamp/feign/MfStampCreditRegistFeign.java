package app.component.stamp.feign;

import app.base.ServiceException;
import app.component.stamp.entity.MfStampCreditRegist;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfStampCreditRegistFeign {

    @RequestMapping(value = "/mfStampCreditRegist/insert")
    public MfStampCreditRegist insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/mfStampCreditRegist/getById")
    public MfStampCreditRegist getById(@RequestBody MfStampCreditRegist mfStampCreditRegist) throws Exception;

}
