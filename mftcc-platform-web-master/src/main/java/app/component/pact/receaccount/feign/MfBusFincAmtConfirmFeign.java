package app.component.pact.receaccount.feign;

import app.component.pact.receaccount.entity.MfBusFincAmtConfirm;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
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
public interface MfBusFincAmtConfirmFeign {
    /**
     *
     * 方法描述 获取融资额度确认列表
     * @param [ipage]
     * @return app.util.toolkit.Ipage
     * @author zhs
     * @date 2018/9/14 11:46
     */
    @RequestMapping(value = "/mfBusFincAmtConfirm/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    /**
     *
     * 方法描述 保存融资申请主表信息（主要是额度确认信息）
     * @param [map]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/9/15 10:44
     */
    @RequestMapping(value = "/mfBusFincAmtConfirm/insert")
    public MfBusFincAmtConfirm insert(@RequestBody Map<String, Object> parmMap) throws Exception;

    @RequestMapping(value = "/mfBusFincAmtConfirm/update")
    public MfBusFincAmtConfirm update(@RequestBody Map<String, Object> parmMap) throws Exception;

    /**
    *
    * 方法描述 融资额度申请审批启动
    * @param [MfBusFincAmtConfirm]
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @author zhs
    * @date 2018/8/18 14:49
    */
    @RequestMapping(value = "/mfBusFincAmtConfirm/submitProcess")
    public Map<String,Object> submitProcess(@RequestBody MfBusFincAmtConfirm mfBusFincAmtConfirm) throws Exception;

    /**
     *
     * 方法描述 融资额度申请审批提交
     * @param [taskId, appId, certiId, opinionType, approvalOpinion, transition, opNo, nextUser, dataMap]
     * @return app.component.wkf.entity.Result
     * @author zhs
     * @date 2018/8/20 10:08
     */
    @RequestMapping(value = "/mfBusFincAmtConfirm/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("appId") String appId, @RequestParam("confirmId") String confirmId, @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;
    /**
     *
     * 方法描述 获取转让主信息
     * @param [mfBusFincAppMain]
     * @return app.component.pact.entity.MfBusPact
     * @author zhs
     * @date 2018/8/17 10:21
     */
    @RequestMapping(value = "/mfBusFincAmtConfirm/getById")
    public MfBusFincAmtConfirm getById(@RequestBody MfBusFincAmtConfirm mfBusFincAmtConfirm) throws Exception;
    /**
     * 方法描述： 获取可以进行账款融资的合同
     * @param ipage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFincAmtConfirm/getPactListForReceFinc")
    public Ipage getPactListForReceFinc(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfBusFincAmtConfirm/processDataForConfirm")
    public MfBusFincAmtConfirm processDataForConfirm(MfBusFincAmtConfirm mfBusFincAmtConfirm) throws Exception;
}
