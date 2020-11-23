package app.component.pact.receaccount.feign;

import app.component.pact.receaccount.entity.MfBusReceTransfer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Title: MfAssetHandleInfoBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusReceTransferFeign {
    /**
     *
     * 方法描述 保存转让子表数据
     * @param [mfBusReceTransfer]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/8/16 11:43
     */
    @RequestMapping(value = "/mfBusReceTransfer/insert")
    public  Map<String,Object> insert(@RequestBody MfBusReceTransfer mfBusReceTransfer) throws Exception;
    /**
     *
     * 方法描述 获取合同下的转让列表
     * @param [mfBusReceTransferMain]
     * @return java.util.List<app.component.pact.receaccount.entity.MfBusReceTransfer>
     * @author zhs
     * @date 2018/8/16 9:43
     */
    @RequestMapping(value = "/mfBusReceTransfer/getUnSubmitTransferList")
    public List<MfBusReceTransfer> getUnSubmitTransferList(@RequestBody MfBusReceTransfer mfBusReceTransfer) throws Exception;

    /**
     *
     * 方法描述 根据转让主id获取转让列表
     * @param [mfBusReceTransfer]
     * @return java.util.List<app.component.pact.receaccount.entity.MfBusReceTransfer>
     * @author zhs
     * @date 2018/8/17 10:23
     */
    @RequestMapping(value = "/mfBusReceTransfer/getReceTransferList")
    public List<MfBusReceTransfer> getReceTransferList(@RequestBody MfBusReceTransfer mfBusReceTransfer) throws Exception;
    /**
     *
     * 方法描述 根据转让详情id获取转让详情
     * @param [mfBusReceTransfer]
     * @return java.util.List<app.component.pact.receaccount.entity.MfBusReceTransfer>
     * @author zhs
     * @date 2018/8/17 10:23
     */
    @RequestMapping(value = "/mfBusReceTransfer/getById")
    public MfBusReceTransfer getById(@RequestBody MfBusReceTransfer mfBusReceTransfer) throws Exception;
    /**
     *
     * 方法描述 获取合同下所有已转账款的可融资金额
     * @param [mfBusReceTransfer]
     * @return app.component.pact.receaccount.entity.MfBusReceTransfer
     * @author zhs
     * @date 2018/8/28 16:15
     */
    @RequestMapping(value = "/mfBusReceTransfer/getReceUsableFincAmt")
    public MfBusReceTransfer getReceUsableFincAmt(MfBusReceTransfer mfBusReceTransfer) throws  Exception;
}
