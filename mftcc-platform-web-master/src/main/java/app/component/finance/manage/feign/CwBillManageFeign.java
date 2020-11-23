package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.manage.entity.CwBillManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface CwBillManageFeign {
    @RequestMapping(value = "/cwBillManage/findPactListByAjax")
    public Ipage findPactListByAjax(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/cwBillManage/insert")
    public Map<String, Object> insert(@RequestBody CwBillManage cwBillManage) throws ServiceException;

    @RequestMapping(value = "/cwBillManage/doBill")
    public Map<String, Object> doBill(@RequestBody CwBillManage cwBillManage) throws ServiceException;

    @RequestMapping("/cwBillManage/getById")
    public CwBillManage getById(@RequestBody CwBillManage cwBillManage) throws Exception;

    @RequestMapping("/cwBillManage/batchDelivery")
    public Map<String, Object> batchDelivery(@RequestBody CwBillManage cwBillManage) throws Exception;

    @RequestMapping("/cwBillManage/getBillManageAccountByPactId")
    Ipage getBillManageAccountByPactId(@RequestBody Ipage ipage)throws Exception;
}
