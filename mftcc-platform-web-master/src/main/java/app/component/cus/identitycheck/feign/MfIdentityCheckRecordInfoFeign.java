package app.component.cus.identitycheck.feign;

import app.component.cus.identitycheck.entity.MfIdentityCheckRecordInfo;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Title: MfIdentityCheckRecordInfoBo.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 24 15:19:26 CST 2018
 **/

@FeignClient("mftcc-platform-dhcc")
public interface MfIdentityCheckRecordInfoFeign {
    @RequestMapping(value = "/mfIdentityCheckRecordInfo/insert")
    public void insert(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo) throws Exception;

    @RequestMapping(value = "/mfIdentityCheckRecordInfo/delete")
    public void delete(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo) throws Exception;

    @RequestMapping(value = "/mfIdentityCheckRecordInfo/update")
    public void update(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo) throws Exception;

    @RequestMapping(value = "/mfIdentityCheckRecordInfo/getById")
    public MfIdentityCheckRecordInfo getById(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo) throws Exception;

    @RequestMapping(value = "/mfIdentityCheckRecordInfo/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    /**
     * 方法描述： 身份核查
     * @param mfIdentityCheckRecordInfo
     * @return
     * @throws Exception Map<String,Object>
     * @author 沈浩兵
     * @date 2018-1-24 下午3:21:22
     */
    @RequestMapping(value = "/mfIdentityCheckRecordInfo/doIdentityCheck")
    public Map<String, Object> doIdentityCheck(@RequestBody MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo) throws Exception;
}
