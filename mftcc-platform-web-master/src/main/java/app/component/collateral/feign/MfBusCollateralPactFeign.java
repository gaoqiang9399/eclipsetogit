package app.component.collateral.feign;

import app.component.collateral.entity.MfBusCollateralPact;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title:MfCusCertificationFeign
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 23 21:47:28 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusCollateralPactFeign {

	@RequestMapping(value = "/mfBusCollateralPact/insert")
	public void insert(@RequestBody MfBusCollateralPact mfBusCollateralPact) throws Exception;

	@RequestMapping(value = "/mfBusCollateralPact/delete")
	public void delete(@RequestBody MfBusCollateralPact mfBusCollateralPact) throws Exception;

	@RequestMapping(value = "/mfBusCollateralPact/update")
	public void update(@RequestBody MfBusCollateralPact mfBusCollateralPact) throws Exception;

	@RequestMapping(value = "/mfBusCollateralPact/getById")
	public MfBusCollateralPact getById(@RequestBody MfBusCollateralPact mfBusCollateralPact) throws Exception;

	@RequestMapping(value = "/mfBusCollateralPact/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;


}
