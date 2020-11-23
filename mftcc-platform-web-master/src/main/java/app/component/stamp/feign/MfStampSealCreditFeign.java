package app.component.stamp.feign;

import app.base.ServiceException;
import app.component.stamp.entity.MfStampCredit;
import app.component.stamp.entity.MfStampSealCredit;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfStampSealCreditFeign {

    @RequestMapping(value = "/mfStampSealCredit/insert")
    public MfStampSealCredit insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/mfStampSealCredit/getById")
    public MfStampSealCredit getById(@RequestBody MfStampSealCredit mfStampSealCredit) throws Exception;

    @RequestMapping(value = "/mfStampSealCredit/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id, @RequestParam("opinionType") String opinionType, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;

}
