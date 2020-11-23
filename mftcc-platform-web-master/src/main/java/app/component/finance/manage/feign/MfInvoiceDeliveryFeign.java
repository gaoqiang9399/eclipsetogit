package app.component.finance.manage.feign;

import app.base.ServiceException;
import app.component.finance.manage.entity.MfInvoiceDelivery;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfInvoiceDeliveryFeign {
    @RequestMapping(value = "/mfInvoiceDelivery/findListByAjax")
    public Ipage findListByAjax(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfInvoiceDelivery/insert")
    public MfInvoiceDelivery insert(@RequestBody Map<String, Object> dataMap) throws ServiceException;

    @RequestMapping("/mfInvoiceDelivery/getById")
    public MfInvoiceDelivery getById(@RequestBody MfInvoiceDelivery mfInvoiceDelivery) throws Exception;

    @RequestMapping("/mfInvoiceDelivery/deliveryBillsAjax")
    public void deliveryBillsAjax(@RequestBody String ids) throws ServiceException;

    @RequestMapping("/mfInvoiceDelivery/receiveConfimAjax")
    public void receiveConfimAjax(@RequestBody String id) throws ServiceException;
}
