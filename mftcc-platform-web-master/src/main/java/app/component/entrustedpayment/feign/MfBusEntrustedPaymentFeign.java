package app.component.entrustedpayment.feign;

import app.base.ServiceException;
import app.component.entrustedpayment.entity.MfBusEntrustedPayment;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfBusEntrustedPaymentFeign {
    @RequestMapping(value = "/mfBusEntrustedPayment/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/getById")
    public MfBusEntrustedPayment getById(@RequestBody MfBusEntrustedPayment mfBusEntrustedPayment) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/getListForCusNo")
    public List<MfBusEntrustedPayment> getListForCusNo(@RequestParam("cusNo") String cusNo) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/insert")
    public MfBusEntrustedPayment insert(@RequestBody MfBusEntrustedPayment mfBusEntrustedPayment) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/update")
    public void update(@RequestBody MfBusEntrustedPayment mfBusEntrustedPayment) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/delete")
    public void delete(@RequestBody MfBusEntrustedPayment mfBusEntrustedPayment) throws ServiceException;

    @RequestMapping(value = "/mfBusEntrustedPayment/getByFincId")
    public MfBusEntrustedPayment getByFincId(@RequestBody MfBusEntrustedPayment mfBusEntrustedPayment) throws ServiceException;

    /**
     * 方法描述： 审批提交
     *
     * @param taskId
     * @param appNo
     * @param transition
     * @param nextUser
     * @param dataMap
     * @return
     * @throws Exception
     *             Result
     * @author Javelin
     */
    @RequestMapping(value = "/mfBusEntrustedPayment/commitProcess")
    public Result commitProcess(@RequestParam("epId") String epId, @RequestParam("taskId") String taskId, @RequestParam("appNo") String appNo, @RequestParam("appId") String appId,
                                @RequestParam("transition") String transition, @RequestParam("nextUser") String nextUser,
                                @RequestBody Map<String, Object> dataMap) throws Exception;

    /**
     * 方法描述： 审批启动
     * @return
     * @throws Exception Result
     * @author Javelin
     */
    @RequestMapping(value = "/mfBusEntrustedPayment/insertAndStartProcess", produces = {"text/html;charset=utf-8"})
    public String insertAndStartProcess(@RequestBody Map<String, Object> parmMap) throws Exception;
}
