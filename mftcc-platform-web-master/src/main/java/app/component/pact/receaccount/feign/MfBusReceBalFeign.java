package app.component.pact.receaccount.feign;

import app.component.pact.receaccount.entity.MfBusReceBal;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Title: MfAssetHandleInfoBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusReceBalFeign {
    /**
     *
     * 方法描述 保存
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @author zhs
     * @date 2018/8/16 11:43
     */
    @RequestMapping(value = "/mfBusReceBal/insert")
    public  Map<String,Object> insert(MfBusReceBal mfBusReceBal) throws Exception;
    /**
     *
     * 方法描述
     * @param [mfBusReceTransfer]
     * @author zhs
     * @date 2018/8/17 10:23
     */
    @RequestMapping(value = "/mfBusReceBal/getById")
    public MfBusReceBal getById(MfBusReceBal mfBusReceBal) throws Exception;
}
