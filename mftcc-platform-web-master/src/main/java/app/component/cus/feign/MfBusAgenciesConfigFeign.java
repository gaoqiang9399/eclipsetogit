package app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfBusAgenciesConfig;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusAgenciesConfigFeign {
	@RequestMapping(value = "/mfBusAgenciesConfig/insert")	
	public MfBusAgenciesConfig insert(@RequestBody MfBusAgenciesConfig mfBusAgenciesConfig) throws Exception;
	
	@RequestMapping(value = "/mfBusAgenciesConfig/update")
	public void update(@RequestBody MfBusAgenciesConfig mfBusAgenciesConfig) throws Exception;
	
	@RequestMapping(value = "/mfBusAgenciesConfig/delete")
	public void delete(@RequestBody MfBusAgenciesConfig mfBusAgenciesConfig) throws Exception;
	
	@RequestMapping(value = "/mfBusAgenciesConfig/getById")
	public MfBusAgenciesConfig getById(@RequestBody MfBusAgenciesConfig mfBusAgenciesConfig) throws Exception;
	
	@RequestMapping(value = "/mfBusAgenciesConfig/getMfBusAgenciesConfigList")
	public List<MfBusAgenciesConfig> getMfBusAgenciesConfigList(@RequestBody MfBusAgenciesConfig mfBusAgenciesConfig) throws Exception;
	
	@RequestMapping(value = "/mfBusAgenciesConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
