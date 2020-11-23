package app.component.collateral.feign;

import app.base.ServiceException;
import app.component.collateral.entity.MfRequestPayoutBill;
import app.component.collateral.entity.MfRequestPayoutDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfRequestPayoutDetailFeign {

    @RequestMapping(value = "/mfRequestPayoutDetail/insert")
    public void insert(@RequestBody MfRequestPayoutDetail mfRequestPayoutDetail) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/update")
    public void update(@RequestBody MfRequestPayoutDetail mfRequestPayoutDetail) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/delete")
    public void delete(@RequestBody MfRequestPayoutDetail mfRequestPayoutDetail) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/getById")
    public MfRequestPayoutDetail getById(@RequestBody MfRequestPayoutDetail mfRequestPayoutDetail) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/mfRequestPayoutDetail/getByRequestId")
    public List<MfRequestPayoutDetail> getByRequestId(@RequestBody String requestId) throws ServiceException;

}
