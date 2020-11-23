package app.component.pact.receaccount.feign;

import app.component.pact.entity.MfBusPact;
import app.component.pact.receaccount.entity.MfBusReceTransferMain;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfAssetHandleInfoBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusReceTransferMainFeign {

    /**
     *
     * @param ipage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusReceTransferMain/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    /**
     *
     * 方法描述 保存账款转让主表信息
     * @param [mfBusReceTransferMain]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/8/16 15:30
     */
    @RequestMapping(value = "/mfBusReceTransferMain/insert")
    public MfBusReceTransferMain insert(@RequestBody MfBusReceTransferMain mfBusReceTransferMain) throws Exception;
   /**
    *
    * 方法描述 账款转让审批提交
    * @param [mfBusReceTransferMain]
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @author zhs
    * @date 2018/8/16 14:49
    */
    @RequestMapping(value = "/mfBusReceTransferMain/submitProcess")
    public Map<String,Object> submitProcess(@RequestBody MfBusReceTransferMain mfBusReceTransferMain) throws Exception;

    @RequestMapping(value = "/mfBusReceTransferMain/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("appId") String appId, @RequestParam("transMainId")  String transMainId, @RequestParam("opinionType")  String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition")  String transition, @RequestParam("opNo")  String opNo, @RequestParam("nextUser") String nextUser, @RequestBody  Map<String, Object> dataMap) throws Exception;
    /**
     *
     * 方法描述 获取转让主信息
     * @param [mfBusReceTransferMain]
     * @return app.component.pact.entity.MfBusPact
     * @author zhs
     * @date 2018/8/17 10:21
     */
    @RequestMapping(value = "/mfBusReceTransferMain/getById")
    public MfBusReceTransferMain getById(@RequestBody MfBusReceTransferMain mfBusReceTransferMain) throws Exception;

    @RequestMapping(value = "/mfBusReceTransferMain/update")
    public MfBusReceTransferMain update(@RequestBody MfBusReceTransferMain mfBusReceTransferMain) throws Exception;
}
