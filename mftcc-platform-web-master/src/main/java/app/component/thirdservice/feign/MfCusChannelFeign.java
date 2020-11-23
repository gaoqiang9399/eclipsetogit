package app.component.thirdservice.feign;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-cus-channel")
public interface MfCusChannelFeign {
    @RequestMapping(value = "/mfCusChannel/doSubmitCusInfo",produces="application/json;charset=UTF-8")
    public String doSubmitCusInfo(@RequestBody Map<String, String> map) throws Exception;
}
