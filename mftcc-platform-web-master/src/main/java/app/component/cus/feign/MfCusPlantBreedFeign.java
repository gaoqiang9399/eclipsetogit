package app.component.cus.feign;

import app.component.cus.entity.MfCusPlantBreed;
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
public interface MfCusPlantBreedFeign {

	@RequestMapping(value = "/mfCusPlantBreed/insert")
	public void insert(@RequestBody MfCusPlantBreed mfCusPlantBreed) throws Exception;

	@RequestMapping(value = "/mfCusPlantBreed/delete")
	public void delete(@RequestBody MfCusPlantBreed mfCusPlantBreed) throws Exception;

	@RequestMapping(value = "/mfCusPlantBreed/update")
	public void update(@RequestBody MfCusPlantBreed mfCusPlantBreed) throws Exception;

	@RequestMapping(value = "/mfCusPlantBreed/getById")
	public MfCusPlantBreed getById(@RequestBody MfCusPlantBreed mfCusPlantBreed) throws Exception;
	
	@RequestMapping(value = "/mfCusPlantBreed/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
