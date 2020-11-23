package app.component.audit.feign;

import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.risk.audit.entity.MfBusRiskAudit;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfBusRiskAuditFeign {

    @RequestMapping(value = "/mfBusRiskAudit/insert")
    public void insert(@RequestBody MfBusRiskAudit mfBusRiskAudit) throws Exception;

    @RequestMapping(value = "/mfBusRiskAudit/pushMessage")
    public Map<String, Object> pushMessage(@RequestParam("appId") String appId,@RequestParam("auditType") String auditType,@RequestParam("opNo") String opNo) throws Exception;

    /**
     *
     * 方法描述：登记保存，开启审批流程
     * @param map
     * @return
     * @throws Exception
     * Result
     * @author jialei
     */
    @RequestMapping(value = "/mfBusRiskAudit/insertAjax", method = RequestMethod.POST)
    public MfBusRiskAudit insertAjax(@RequestBody Map<String, Object> map) throws Exception;

    @RequestMapping(value = "/mfBusRiskAudit/getById", method = RequestMethod.POST)
    public MfBusRiskAudit getById(@RequestBody MfBusRiskAudit mfBusRiskAudit) throws Exception;

    @RequestMapping(value = "/mfBusRiskAudit/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
                           @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
                           @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
                           @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> dataMap) throws Exception;

    @RequestMapping(value = "/mfBusRiskAudit/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
