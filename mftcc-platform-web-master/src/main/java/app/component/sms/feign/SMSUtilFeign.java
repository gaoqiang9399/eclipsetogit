package app.component.sms.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface SMSUtilFeign {
    @RequestMapping(value = "/mfSMSUtil/sendSMS_Normal", method = RequestMethod.POST)
    public Map<String, String> sendSMS_Normal(@RequestBody Map<String, String> parMap, @RequestParam("msgId") String msgId, @RequestParam("cusTel") String cusTel);
}
