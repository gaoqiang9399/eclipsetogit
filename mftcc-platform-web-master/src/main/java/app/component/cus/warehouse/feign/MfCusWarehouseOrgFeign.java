package app.component.cus.warehouse.feign;

import app.base.ServiceException;
import app.component.cus.entity.MfBusTrench;
import app.component.cus.warehouse.entity.MfCusWarehouseOrg;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusWarehouseOrgFeign {
    @RequestMapping(value = "/mfCusWarehouseOrg/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfCusWarehouseOrg/insert")
    public Map <String, Object> insert(@RequestBody MfCusWarehouseOrg mfCusWarehouseOrg) throws ServiceException;

    @RequestMapping(value = "/mfCusWarehouseOrg/getById")
    public MfCusWarehouseOrg getById(@RequestBody MfCusWarehouseOrg mfCusWarehouseOrg) throws ServiceException;

    @RequestMapping(value = "/mfCusWarehouseOrg/update")
    public void update(@RequestBody MfCusWarehouseOrg mfCusWarehouseOrg) throws Exception;

    @RequestMapping(value = "/mfCusWarehouseOrg/delete")
    public void delete(@RequestBody MfCusWarehouseOrg mfCusWarehouseOrg) throws Exception;

    @RequestMapping(value = "/mfCusWarehouseOrg/getWarehouseOrgBusHisAjax")
    public Map<String,String> getWarehouseOrgBusHisAjax(@RequestParam("cusNo") String cusNo) throws Exception;

    @RequestMapping(value = "/mfCusWarehouseOrg/getByOpNo")
    public MfCusWarehouseOrg getByOpNo(@RequestParam("opNo") String opNo)throws Exception;
}
