package app.component.model.feign;

import app.component.model.entity.MfTemplateEsignerList;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfTemplateEsignerListFeign {

    @RequestMapping(value = "/mfTemplateEsignerList/insert")
    public void insert(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/delete")
    public void delete(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/update")
    public void update(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/getById")
    public MfTemplateEsignerList getById(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/getEsigners")
    public List<MfTemplateEsignerList> getEsigners(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/findByPageByCoreCompany")
    public Ipage findByPageByCoreCompany(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/getNotSignedCount")
    public Integer getNotSignedCount(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

    @RequestMapping(value = "/mfTemplateEsignerList/getEsignersCor")
    public List<MfTemplateEsignerList> getEsignersCor(@RequestBody MfTemplateEsignerList mfTemplateEsignerList) throws Exception;

}