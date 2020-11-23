package app.component.stamp.feign;

import app.base.ServiceException;
import app.component.stamp.entity.MfStampSealPact;
import app.component.wkf.entity.Result;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfStampSealPactFeign {
    @RequestMapping(value = "/mfStampSealPact/insert")
    public MfStampSealPact insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/mfStampSealPact/getById")
    public MfStampSealPact getById(@RequestBody MfStampSealPact mfStampSealPact) throws Exception;

    @RequestMapping(value = "/mfStampSealPact/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id, @RequestParam("opinionType") String opinionType, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;

}
