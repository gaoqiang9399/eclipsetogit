package app.component.collateral.feign;

import app.base.ServiceException;
import app.component.collateral.entity.MfCollateralInsuranceClaims;
import app.component.collateral.entity.MfRequestPayoutBill;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfRequestPayoutBillFeign {

    @RequestMapping(value = "/mfRequestPayoutBill/insert")
    public MfRequestPayoutBill insert(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/getBillList")
    public Ipage getBillList(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfRequestPayoutBill/update")
    public void update(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/getById")
    public MfRequestPayoutBill getById(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/submitProcess")
    public MfRequestPayoutBill submitProcess(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping("/mfRequestPayoutBill/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId,
                           @RequestParam("transition") String transition,
                           @RequestParam("transition") String nextUser,
                           @RequestBody Map<String, Object> formDataMap) throws Exception;

    @RequestMapping(value = "/mfRequestPayoutBill/paymentReg")
    public void paymentReg(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/finishPayment")
    public Map<String, Object> finishPayment(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/getRelationRequestIdList")
    public List<String> getRelationRequestIdList() throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/getTotalCostAmount")
    public Double getTotalCostAmount(@RequestBody String requestId) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/updatePayoutAmount")
    public void updatePayoutAmount(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutBill/updateRequestCount")
    public void updateRequestCount(@RequestBody MfRequestPayoutBill mfRequestPayoutBill) throws ServiceException;

}
