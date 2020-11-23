package app.component.vouafter.feign;

import app.base.ServiceException;
import app.component.vouafter.entity.MfVouAfterPayManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfVouAfterPayManageFeign {

    @RequestMapping(value = "/mfVouAfterPayManage/findListByAjax")
    public Ipage findListByAjax(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfVouAfterPayManage/getPactListForVouPay")
    Ipage getPactListForVouPay(@RequestBody Ipage ipage)throws  Exception;

    @RequestMapping(value = "/mfVouAfterPayManage/insert")
    public Map<String, Object> insert(@RequestBody MfVouAfterPayManage mfVouAfterPayManage) throws ServiceException;

    @RequestMapping("/mfVouAfterPayManage/getById")
    public MfVouAfterPayManage getById(@RequestBody MfVouAfterPayManage mfVouAfterPayManage) throws Exception;

    @RequestMapping(value = "/mfVouAfterPayManage/update")
    public Map<String, Object> update(@RequestBody MfVouAfterPayManage mfVouAfterPayManage) throws ServiceException;
}
