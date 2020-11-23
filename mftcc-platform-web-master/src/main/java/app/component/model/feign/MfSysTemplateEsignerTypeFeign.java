package app.component.model.feign;

import app.component.model.entity.MfSysTemplateEsignerType;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfSysTemplateEsignerTypeFeign {

    @RequestMapping(value = "/mfSysTemplateEsignerType/insert")
    public void insert(@RequestBody MfSysTemplateEsignerType mfSysTemplateEsignerType) throws Exception;

    @RequestMapping(value = "/mfSysTemplateEsignerType/delete")
    public void delete(@RequestBody MfSysTemplateEsignerType mfSysTemplateEsignerType) throws Exception;

    @RequestMapping(value = "/mfSysTemplateEsignerType/update")
    public void update(@RequestBody MfSysTemplateEsignerType mfSysTemplateEsignerType) throws Exception;

    @RequestMapping(value = "/mfSysTemplateEsignerType/getById")
    public MfSysTemplateEsignerType getById(@RequestBody MfSysTemplateEsignerType mfSysTemplateEsignerType) throws Exception;

    @RequestMapping(value = "/mfSysTemplateEsignerType/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfSysTemplateEsignerType/getByTemplateNo")
    public List<MfSysTemplateEsignerType> getByTemplateNo(@RequestBody MfSysTemplateEsignerType mfSysTemplateEsignerType)throws Exception;
}
