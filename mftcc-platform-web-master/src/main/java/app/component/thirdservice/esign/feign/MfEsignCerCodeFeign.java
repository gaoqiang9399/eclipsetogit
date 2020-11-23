package app.component.thirdservice.esign.feign;

import app.component.thirdservice.esign.entity.MfEsignCerCode;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("mftcc-platform-factor")
public interface MfEsignCerCodeFeign {

    @RequestMapping("/mfEsignCerCode/insert")
    public void insert(@RequestBody MfEsignCerCode mfEsignCerCode) throws Exception;

    @RequestMapping("/mfEsignCerCode/delete")
    public void delete(@RequestBody MfEsignCerCode mfEsignCerCode) throws Exception;

    @RequestMapping("/mfEsignCerCode/update")
    public void update(@RequestBody MfEsignCerCode mfEsignCerCode) throws Exception;

    @RequestMapping("/mfEsignCerCode/getById")
    public MfEsignCerCode getById(@RequestBody MfEsignCerCode mfEsignCerCode) throws Exception;

    @RequestMapping("/mfEsignCerCode/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
