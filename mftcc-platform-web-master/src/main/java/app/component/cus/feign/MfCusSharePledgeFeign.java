package app.component.cus.feign;

import app.component.cus.entity.MfCusSharePledge;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfCusSharePledgeFeign {
    @RequestMapping("/mfCusSharePledge/insert")
    public MfCusSharePledge insert(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;

    @RequestMapping("/mfCusSharePledge/insertForApp")
    public MfCusSharePledge insertForApp(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;

    @RequestMapping("/mfCusSharePledge/delete")
    public void delete(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;

    @RequestMapping("/mfCusSharePledge/update")
    public void update(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;

    @RequestMapping("/mfCusSharePledge/getById")
    public MfCusSharePledge getById(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;

    @RequestMapping("/mfCusSharePledge/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfCusSharePledge/findByPage2")
    public List<MfCusSharePledge> findByPage2(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;
    @RequestMapping("/mfCusSharePledge/getByCusNo")
    public List<MfCusSharePledge> getByCusNo(@RequestBody MfCusSharePledge mfCusSharePledge) throws Exception;


}
