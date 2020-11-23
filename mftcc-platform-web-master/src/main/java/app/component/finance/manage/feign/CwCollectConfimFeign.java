package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.manage.entity.CwCollectConfim;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface CwCollectConfimFeign {
    @RequestMapping(value = "/cwCollectConfim/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/cwCollectConfim/insert")
    public CwCollectConfim insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/cwCollectConfim/getById")
    public CwCollectConfim getById(@RequestBody CwCollectConfim cwCollectConfim) throws Exception;

    @RequestMapping(value = "/cwCollectConfim/update")
    public CwCollectConfim update(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping(value = "/cwCollectConfim/collectConfim")
    public Map<String,Object>  collectConfim(@RequestBody CwCollectConfim cwCollectConfim) throws ServiceException;

    @RequestMapping(value = "/cwCollectConfim/rallback")
    public Map<String,Object>  rallback(@RequestBody CwCollectConfim cwCollectConfim) throws ServiceException;

    @RequestMapping(value = "/cwCollectConfim/batchConfim")
    public Map<String, Object> batchConfim(@RequestBody CwCollectConfim cwCollectConfim) throws ServiceException;

    @RequestMapping("/cwCollectConfim/findAllCollectListByPage")
    Ipage findAllCollectListByPage(@RequestBody Ipage ipage);

    @RequestMapping("/cwCollectConfim/insertCwCollectConfim")
    void insertCwCollectConfim(@RequestBody CwCollectConfim cwCollectConfim) throws Exception;
}
