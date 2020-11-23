package app.component.intention.feign;

import app.component.intention.entity.MfBusIntentionApply;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MfBusIntentionApplyFeign {

    @RequestMapping(value = "/mfBusIntentionApply/findByPage")
    Ipage findByPage(@RequestBody Ipage ipage) throws Exception;


    @RequestMapping(value = "/mfBusIntentionApply/getById")
    MfBusIntentionApply getById(@RequestParam(name="id") String id) throws Exception;

}
