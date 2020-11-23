package app.component.cus.feign;

import app.component.cus.entity.MfCusSaleProduct;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Title: MfCusAccountDebtorBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jul 17 12:15:38 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusSaleProductFeign {

	@RequestMapping(value = "/mfCusSaleProduct/insert")
	public void insert(@RequestBody MfCusSaleProduct mfCusSaleProduct) throws Exception;

	@RequestMapping(value = "/mfCusSaleProduct/delete")
	public void delete(@RequestBody MfCusSaleProduct mfCusSaleProduct) throws Exception;

	@RequestMapping(value = "/mfCusSaleProduct/update")
	public void update(@RequestBody MfCusSaleProduct mfCusSaleProduct) throws Exception;

	@RequestMapping(value = "/mfCusSaleProduct/getById")
	public MfCusSaleProduct getById(@RequestBody MfCusSaleProduct mfCusSaleProduct) throws Exception;
	
	@RequestMapping(value = "/mfCusSaleProduct/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
