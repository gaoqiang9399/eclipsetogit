package app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfCreditFrozenThaw;
import app.component.auth.entity.MfCreditIntentionApply;
import app.component.auth.entity.MfCreditOaApproveDetails;
import app.component.wkf.entity.Result;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCreditFrozenThawFeign {
    @RequestMapping(value = "/mfCreditFrozenThaw/insert")
    public void insert(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;

    @RequestMapping(value = "/mfCreditFrozenThaw/delete")
    public void delete(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;

    @RequestMapping(value = "/mfCreditFrozenThaw/update")
    public void update(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception ;

    @RequestMapping(value = "/mfCreditFrozenThaw/getById")
    public MfCreditFrozenThaw getById(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;

    @RequestMapping(value = "/mfCreditFrozenThaw/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfCreditFrozenThaw/getFrozenThawList")
    public List<MfCreditFrozenThaw> getFrozenThawList(@RequestBody MfCreditFrozenThaw mfCreditFrozenThaw) throws Exception;
}