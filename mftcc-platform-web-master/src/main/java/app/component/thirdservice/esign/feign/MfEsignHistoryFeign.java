package app.component.thirdservice.esign.feign;

import app.component.thirdservice.esign.entity.MfEsignHistory;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("mftcc-platform-factor")
public interface MfEsignHistoryFeign {

    @RequestMapping("/mfEsignHistory/insert")
    public void insert(@RequestBody MfEsignHistory mfEsignHistory) throws Exception;

    @RequestMapping("/mfEsignHistory/delete")
    public void delete(@RequestBody MfEsignHistory mfEsignHistory) throws Exception;

    @RequestMapping("/mfEsignHistory/update")
    public void update(@RequestBody MfEsignHistory mfEsignHistory) throws Exception;

    @RequestMapping("/mfEsignHistory/getById")
    public MfEsignHistory getById(@RequestBody MfEsignHistory mfEsignHistory) throws Exception;

    @RequestMapping("/mfEsignHistory/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
