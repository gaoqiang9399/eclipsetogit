package app.component.stamp.feign;

import app.base.ServiceException;
import app.component.stamp.entity.MfStampPactRegist;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfStampPactRegistFeign {

    @RequestMapping(value = "/mfStampPactRegist/insert")
    public MfStampPactRegist insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/mfStampPactRegist/getById")
    public MfStampPactRegist getById(@RequestBody MfStampPactRegist mfStampPactRegist) throws Exception;

}
