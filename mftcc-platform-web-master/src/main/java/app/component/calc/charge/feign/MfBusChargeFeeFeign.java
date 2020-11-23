package app.component.calc.charge.feign;


import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.calc.fee.entity.MfBusChargeFee;
import app.component.calc.fee.entity.MfBusChargeFeeHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient("mftcc-platform-factor")
public interface MfBusChargeFeeFeign{

    /**
     *
     * 方法描述：缴款通知登记提交，业务流程提交
     * @param map
     * @return
     * @throws Exception
     * Result
     * @author jialei
     */
    @RequestMapping(value = "/mfBusChargeFee/doChargeSubmit", method = RequestMethod.POST)
    public Map<String,Object> doChargeSubmit(@RequestBody Map<String, Object> map) throws Exception;
    /**
     *
     * 方法描述：缴款通知登记提交，业务流程提交
     * @param map
     * @return
     * @throws Exception
     * Result
     * @author jialei
     */
    @RequestMapping(value = "/mfBusChargeFee/insert", method = RequestMethod.POST)
    public MfBusChargeFee insert(@RequestBody MfBusChargeFee mfBusChargeFee) throws Exception;


    @RequestMapping(value = "/mfBusChargeFee/submitProcessWithUser")
    public MfBusChargeFee submitProcessWithUser(@RequestBody String appId, @RequestParam("opNo") String opNo, @RequestParam("opName") String opName, @RequestParam("brNo") String brNo, @RequestParam("firstApprovalUser")  String firstApprovalUser) throws Exception;

    @RequestMapping(value = "/mfBusChargeFee/getById")
    public MfBusChargeFee getById(@RequestBody MfBusChargeFee mfBusChargeFee) throws ServiceException;

    @RequestMapping(value = "/mfBusChargeFee/findAllHis")
    public List<MfBusChargeFeeHis> findAllHis(@RequestBody MfBusChargeFeeHis mfBusChargeFeeHis) throws ServiceException;

    @RequestMapping(value = "/mfBusChargeFee/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
                           @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
                           @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
                           @RequestParam("nextUser") String nextUser,
                           @RequestBody Map<String, Object> dataMap) throws Exception;

    @RequestMapping(value = "/mfBusChargeFee/findByPage")
    Ipage findByPage(@RequestBody Ipage ipage)throws Exception;
    @RequestMapping(value = "/mfBusChargeFee/getFeeCollectList")
    Ipage getFeeCollectList(@RequestBody Ipage ipage)throws Exception;

    @RequestMapping(value = "/mfBusChargeFee/calcSecondFeeAmt")
    Map<String,Object> calcSecondFeeAmt(@RequestParam("pactId") String pactId)throws Exception;
    @RequestMapping(value = "/mfBusChargeFee/getGuaranteeFee")
    Map<String, Object> getGuaranteeFee(@RequestParam("appId") String appId)throws Exception;
    @RequestMapping(value = "/mfBusChargeFee/doChargeReplaceToPdf")
    Map<String, Object> doChargeReplaceToPdf(@RequestParam("chargeId") String chargeId)throws Exception;
    @RequestMapping(value = "/mfBusChargeFee/getTaxFlag")
    String getTaxFlag(@RequestParam("cusNo")String cusNo, @RequestParam("appId")String appId)throws Exception;
    @RequestMapping(value = "/mfBusChargeFee/getProjectSizeAjax")
    String getProjectSizeAjax(@RequestParam("empCnt")String empCnt,@RequestParam("saleAmt")String saleAmt,@RequestParam("wayClass")String wayClass,@RequestParam("assetsAmt")String assetsAmt)throws Exception;
}
