package app.component.cus.feign;

import app.component.cus.entity.MfKindAttrExt;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Administrator
 * @ClassName MfKindAttrExtFeign
 * @describe TODO
 * @date 2018/10/10 15:24
 **/
@FeignClient("mftcc-platform-factor")
public interface MfKindAttrExtFeign {

    @RequestMapping(value = "/mfKindAttrExt/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping("/mfKindAttrExt/insert")
    public void insert(@RequestBody MfKindAttrExt mfKindAttrExt) throws Exception;

    @RequestMapping("/mfKindAttrExt/getById")
    public  MfKindAttrExt getById(@RequestBody MfKindAttrExt mfKindAttrExt) throws Exception;

    @RequestMapping("/mfKindAttrExt/update")
    public void update(@RequestBody MfKindAttrExt mfKindAttrExt) throws Exception;

    @RequestMapping("/mfKindAttrExt/delete")
    public void delete(@RequestBody MfKindAttrExt mfKindAttrExt) throws Exception;

    @RequestMapping("/mfKindAttrExt/getMfKindAttrExtList")
    public List<MfKindAttrExt> getMfKindAttrExtList(@RequestBody MfKindAttrExt mfKindAttrExt) throws Exception;

}
