package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.manage.entity.CwPayConfim;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface CwPayConfimFeign {
    @RequestMapping(value = "/cwPayConfim/findListByAjax")
    public Ipage findListByAjax(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/cwPayConfim/insert")
    public CwPayConfim insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/cwPayConfim/getById")
    public CwPayConfim getById(@RequestBody CwPayConfim cwPayConfim) throws Exception;

    @RequestMapping("/cwPayConfim/update")
    public void update(@RequestBody CwPayConfim cwPayConfim) throws Exception;
}
