package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.manage.entity.CwBillManage;
import app.component.finance.manage.entity.CwRecourseConfim;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface CwRecourseConfimFeign {
    @RequestMapping(value = "/cwRecourseConfim/findListByAjax")
    public Ipage findListByAjax(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/cwRecourseConfim/insert")
    public CwRecourseConfim insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/cwRecourseConfim/getById")
    public CwRecourseConfim getById(@RequestBody CwRecourseConfim cwRecourseConfim) throws Exception;
}
