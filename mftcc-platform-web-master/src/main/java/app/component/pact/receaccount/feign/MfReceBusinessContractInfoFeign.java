package app.component.pact.receaccount.feign;

import app.component.pact.receaccount.entity.MfBusFincAppMain;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfReceBusinessContractInfo;
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
public interface MfReceBusinessContractInfoFeign {

    /**
     * 获取商务合同信息
     * @param mfReceBusinessContractInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfReceBusinessContractInfo/getById")
    public MfReceBusinessContractInfo getById(MfReceBusinessContractInfo mfReceBusinessContractInfo) throws Exception;
   /**
    *
    * 方法描述 新增应收账款商务合同信息
    * @param [mfReceBusinessContractInfo]
    * @return java.util.Map<java.lang.String,java.lang.Object>
    * @author zhs
    * @date 2018/8/31 11:54
    */
    @RequestMapping(value = "/mfReceBusinessContractInfo/insert")
    public Map<String,Object> insert(MfReceBusinessContractInfo mfReceBusinessContractInfo) throws Exception;
    /**
     *
     * 方法描述 获取商务合同列表（根据客户号）
     * @param [mfReceBusinessContractInfo]
     * @return java.util.List<app.component.pact.receaccount.entity.MfReceBusinessContractInfo>
     * @author zhs
     * @date 2018/8/31 16:11
     */
    @RequestMapping(value = "/mfReceBusinessContractInfo/getList")
    public List<MfReceBusinessContractInfo> getList(MfReceBusinessContractInfo mfReceBusinessContractInfo) throws Exception;
}
