package app.component.pact.stopintst.feign;

import app.component.calc.core.entity.MfRepayAmt;
import app.component.pact.stopintst.entity.MfBusStopIntstApply;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.wkf.entity.Result;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfBusStopIntstApplyFeign {
    @RequestMapping(value = "/mfBusStopIntstApply/getById")
    public MfBusStopIntstApply getById(@RequestBody MfBusStopIntstApply mfBusStopIntstApply) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/insert")
    public void insert(@RequestBody MfBusStopIntstApply mfBusStopIntstApply) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/updashowflag")
    public void updashowflag(@RequestBody MfBusStopIntstApply mfBusStopIntstApply) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/getBusFincAppinfo")
    public Ipage getBusFincAppinfo(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/submitProcess")
    public MfBusStopIntstApply submitProcess(@RequestBody MfBusStopIntstApply mfBusStopIntstApply) throws Exception;

    @RequestMapping(value = "/mfBusStopIntstApply/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo,
                           @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
                           @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody MfBusStopIntstApply mfBusStopIntstApply) throws Exception;

    @RequestMapping(value = "/calcRepaymentInterface/getCurTermYingShouAmtList")
    public List<MfRepayAmt> getCurTermYingShouAmtList(Map<String,Object> parmMap)throws Exception;

    @RequestMapping(value = "/mfBusFincApp/doMfBusFincAppStopIntst")
    public Map<String,String> doMfBusFincAppStopIntst(Map<String,Object> parmMap)throws Exception;
}
