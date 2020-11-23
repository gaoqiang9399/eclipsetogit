package app.component.cus.feign;

import app.component.cus.entity.MfCusProfitLoss;
import app.util.toolkit.Ipage;

import java.util.List;

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
public interface MfCusProfitLossFeign {

	@RequestMapping(value = "/mfCusProfitLoss/insert")
	public void insert(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;

	@RequestMapping(value = "/mfCusProfitLoss/delete")
	public void delete(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;

	@RequestMapping(value = "/mfCusProfitLoss/update")
	public void update(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;

	@RequestMapping(value = "/mfCusProfitLoss/getById")
	public MfCusProfitLoss getById(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;
	@RequestMapping(value = "/mfCusProfitLoss/autoCalc")
	public MfCusProfitLoss autoCalc(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;

	@RequestMapping(value = "/mfCusProfitLoss/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusProfitLoss/getByAppId")
	public List<MfCusProfitLoss> getByAppId(@RequestBody MfCusProfitLoss mfCusProfitLoss) throws Exception;

}
