package app.component.pact.receaccount.feign;

import app.component.pact.entity.MfBusFincApp;
import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
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
public interface MfBusFincAppMainFeign {
    /**
     *
     * @param ipage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFincAppMain/findFincByPage")
    public Ipage findFincByPage(@RequestBody Ipage ipage) throws Exception;
    /**
     *
     * 方法描述  更新融资申请主表以及子表信息(根据应收账款生成借据)
     * @param [parmMap]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/8/18 15:30
     */
    @RequestMapping(value = "/mfBusFincAppMain/insert")
    public Map<String,Object> insert(@RequestBody Map<String,Object> parmMap) throws Exception;

    /**
     * 保存批量放款申请信息
     * @param parmMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFincAppMain/insertForBatch")
    public Map<String,Object> insertForBatch(@RequestBody Map<String,Object> parmMap) throws Exception;

    /**
     * 根据计划放款笔数生成出账明细
     * @param parmMap
     * @return
     */
    @RequestMapping(value = "/mfBusFincAppMain/generateFincAppList")
    public Map<String,Object> generateFincAppList(@RequestBody Map<String,Object> parmMap);
    /**
    *
    * 方法描述 账款转让审批提交
    * @param [mfBusFincAppMain]
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @author zhs
    * @date 2018/8/18 14:49
    */
    @RequestMapping(value = "/mfBusFincAppMain/submitProcess")
    public Map<String,Object> submitProcess(@RequestBody MfBusFincAppMain mfBusFincAppMain) throws Exception;

    /**
     *
     * 方法描述 融资申请审批提交
     * @param [taskId, appId, certiId, opinionType, approvalOpinion, transition, opNo, nextUser, dataMap]
     * @return app.component.wkf.entity.Result
     * @author zhs
     * @date 2018/8/20 10:08
     */
    @RequestMapping(value = "/mfBusFincAppMain/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("appId") String appId, @RequestParam("fincMainId") String fincMainId, @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;
    /**
     *
     * 方法描述 获取转让主信息
     * @param [mfBusFincAppMain]
     * @return app.component.pact.entity.MfBusPact
     * @author zhs
     * @date 2018/8/17 10:21
     */
    @RequestMapping(value = "/mfBusFincAppMain/getById")
    public MfBusFincAppMain getById(@RequestBody MfBusFincAppMain mfBusFincAppMain) throws Exception;

    @RequestMapping(value = "/mfBusFincAppMain/update")
    public void update(@RequestBody MfBusFincAppMain mfBusFincAppMain) throws Exception;

    /**
     * 方法描述：获取融资申请下的账款列表（融资审批页面列表显示）
     * @param mfBusFincAppMainParm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFincAppMain/getReceFincList")
    public List<MfBusReceTransfer> getReceFincList(MfBusFincAppMain mfBusFincAppMainParm) throws Exception;

    @RequestMapping(value = "/mfBusFincAppMain/doCommitForBatch")
    public Result doCommitForBatch(@RequestParam("taskId") String taskId, @RequestParam("appId") String appId, @RequestParam("fincMainId") String fincMainId, @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> dataMap) throws Exception;


}
