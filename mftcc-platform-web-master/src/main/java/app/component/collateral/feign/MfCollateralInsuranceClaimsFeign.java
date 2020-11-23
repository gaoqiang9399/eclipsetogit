package app.component.collateral.feign;

import app.base.ServiceException;
import app.component.collateral.entity.MfCollateralInsuranceClaims;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@FeignClient("mftcc-platform-factor")
public interface MfCollateralInsuranceClaimsFeign {

    @RequestMapping(value = "/mfCollateralInsuranceClaims/insert")
    public void insert(@RequestBody MfCollateralInsuranceClaims mfCollateralInsuranceClaims) throws ServiceException;

    @RequestMapping(value = "/mfCollateralInsuranceClaims/delete")
    public void delete(@RequestBody MfCollateralInsuranceClaims mfCollateralInsuranceClaims) throws ServiceException;

    @RequestMapping(value = "/mfCollateralInsuranceClaims/update")
    public void update(@RequestBody MfCollateralInsuranceClaims mfCollateralInsuranceClaims) throws ServiceException;

    @RequestMapping(value = "/mfCollateralInsuranceClaims/getById")
    public MfCollateralInsuranceClaims getById(@RequestBody MfCollateralInsuranceClaims mfCollateralInsuranceClaims) throws ServiceException;

    @RequestMapping(value = "/mfCollateralInsuranceClaims/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

}
