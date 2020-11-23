package app.component.receivables.feign;

import app.base.ServiceException;
import app.component.receivables.entity.MfBankReceivablesBuss;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfBankReceivablesBussFeign {

    @RequestMapping(value = "/mfBankReceivablesBuss/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfBankReceivablesBuss/findBussByPage")
    public Ipage findBussByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfBankReceivablesBuss/insert")
    public MfBankReceivablesBuss insert(@RequestBody MfBankReceivablesBuss mfBankReceivablesBuss) throws ServiceException;

    @RequestMapping(value = "/mfBankReceivablesBuss/delete")
    public void delete(@RequestBody MfBankReceivablesBuss mfBankReceivablesBuss) throws ServiceException;

    @RequestMapping(value = "/mfBankReceivablesBuss/update")
    public void update(@RequestBody MfBankReceivablesBuss mfBankReceivablesBuss) throws ServiceException;

    @RequestMapping(value = "/mfBankReceivablesBuss/getById")
    public MfBankReceivablesBuss getById(@RequestBody MfBankReceivablesBuss mfBankReceivablesBuss) throws ServiceException;

}
