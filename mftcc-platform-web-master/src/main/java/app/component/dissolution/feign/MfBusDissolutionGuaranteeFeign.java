package app.component.dissolution.feign;

import app.component.dissolution.entity.MfBusDissolutionGuarantee;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MfBusDissolutionGuaranteeFeign {

    @RequestMapping(value = "/mfBusDissolutionGuarantee/findByPage")
    Ipage findByPage(Ipage ipage);

    @RequestMapping(value = "/mfBusDissolutionGuarantee/findByDissolutionPage")
    Ipage findByDissolutionPage(Ipage ipage);

    @RequestMapping(value = "/mfBusDissolutionGuarantee/insert")
    void insert(MfBusDissolutionGuarantee mfBusDissolutionGuarantee);


    @RequestMapping(value = "/mfBusDissolutionGuarantee/getById")
    MfBusDissolutionGuarantee getById(@RequestParam(name="id") String id) throws Exception;

    @RequestMapping(value = "/mfBusDissolutionGuarantee/getDisGuarantee")
    MfBusDissolutionGuarantee getDisGuarantee(@RequestBody  MfBusDissolutionGuarantee mfBusDissolutionGuarantee)throws Exception;
}
