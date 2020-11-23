package app.component.pact.feerefund.feign;

import app.component.pact.entity.MfBusPact;
import app.component.pact.feerefund.entity.MfBusFeeRefund;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Title: MfBusPactBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusFeeRefundFeign {
    /**
     * 查询列表
     * @param ipage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFeeRefund/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws  Exception;

    /**
     * 新增
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFeeRefund/insert")
    Map<String,Object> insert(@RequestBody MfBusFeeRefund mfBusFeeRefund)throws  Exception;
    /**
     * 新增
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFeeRefund/getById")
    MfBusFeeRefund getById(@RequestBody MfBusFeeRefund mfBusFeeRefund)throws  Exception;

    @RequestMapping(value = "/mfBusFeeRefund/doCommit")
    Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("feeId") String feeId,
                    @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
                    @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser,
                    @RequestBody Map<String, Object> dataMap) throws  Exception;

    @RequestMapping(value = "/mfBusFeeRefund/getPactList")
    Ipage getPactList(@RequestBody Ipage ipage)throws  Exception;

    @RequestMapping(value = "/mfBusFeeRefund/calcAppAmt")
    Double calcAppAmt(@RequestBody Map<String,Object> parmMap)throws  Exception;
}

