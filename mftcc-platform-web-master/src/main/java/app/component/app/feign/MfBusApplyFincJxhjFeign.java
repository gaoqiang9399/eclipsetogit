package app.component.app.feign;

import app.component.app.entity.MfBusApplyFincJxhj;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Title: MfBusApplyFincJxhjFeign
 * @Description:
 * @Auther: Yuan Xiaolong
 * @Version: 1.0
 * @create:2019/4/316:09
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusApplyFincJxhjFeign {
    @RequestMapping(value = "/mfBusApplyFincJxhj/insert")
    public void insert(@RequestBody  MfBusApplyFincJxhj mfBusApplyFincJxhj) throws Exception;

    @RequestMapping(value = "/mfBusApplyFincJxhj/delete")
    public void delete(@RequestBody  MfBusApplyFincJxhj mfBusApplyFincJxhj) throws Exception;

    @RequestMapping(value = "/mfBusApplyFincJxhj/update")
    public void update(@RequestBody  MfBusApplyFincJxhj mfBusApplyFincJxhj) throws Exception;

    @RequestMapping(value = "/mfBusApplyFincJxhj/getById")
    public MfBusApplyFincJxhj getById(@RequestBody  MfBusApplyFincJxhj mfBusApplyFincJxhj) throws Exception;

    @RequestMapping(value = "/mfBusApplyFincJxhj/getList")
    public List<MfBusApplyFincJxhj> getList(@RequestBody  MfBusApplyFincJxhj mfBusApplyFincJxhj) throws Exception;


}
