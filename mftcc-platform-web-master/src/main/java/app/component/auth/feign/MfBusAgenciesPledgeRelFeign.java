package app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfBusAgenciesPledgeRel;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient("mftcc-platform-factor")
public interface MfBusAgenciesPledgeRelFeign {
	
	@RequestMapping(value = "/mfBusAgenciesPledgeRel/insert")
	public void insert(@RequestBody MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAgenciesPledgeRel/delete")
	public void delete(@RequestBody MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAgenciesPledgeRel/update")
	public void update(@RequestBody MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAgenciesPledgeRel/getById")
	public MfBusAgenciesPledgeRel getById(@RequestBody MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusAgenciesPledgeRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusAgenciesPledgeRel/findAgenciesListByPage")
	public Ipage findAgenciesListByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusAgenciesPledgeRel/getPledgeList")
	public Ipage getPledgeList(@RequestBody Ipage ipage,@RequestParam("parmMap") Map<String, String> parmMap) throws ServiceException;

	@RequestMapping(value = "/mfBusAgenciesPledgeRel/checkAgenciesPledgeRel")
	public Map<String,String> checkAgenciesPledgeRel(@RequestBody MfBusAgenciesPledgeRel mfBusAgenciesPledgeRel) throws ServiceException;

	@RequestMapping(value = "/mfBusAgenciesPledgeRel/copyAgenciesPledgeRel")
	public Map<String,String> copyAgenciesPledgeRel(@RequestParam("creditAppId") String creditAppId,@RequestParam("appId") String appId) throws ServiceException;
}
