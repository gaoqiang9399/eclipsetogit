package app.component.cus.feign;

import app.base.ServiceException;
import app.component.cus.entity.MfCusMainBusiness;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusMainBusinessFeign {
	@RequestMapping(value = "/mfCusMainBusiness/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMainBusiness/getById")
	public MfCusMainBusiness getById(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws ServiceException;

	@RequestMapping(value = "/mfCusMainBusiness/getByMainType")
	public MfCusMainBusiness getByMainType(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws ServiceException;

	@RequestMapping(value = "/mfCusMainBusiness/insert")
	public Map<String, Object> insert(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws ServiceException;

	@RequestMapping(value = "/mfCusMainBusiness/insertInfo")
	public Map<String, Object> insertInfo(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws ServiceException;

	@RequestMapping(value = "/mfCusMainBusiness/update")
	public void update(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMainBusiness/delete")
	public void delete(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws Exception;

	@RequestMapping(value = "/mfCusMainBusiness/deleteForSelect")
	public void deleteForSelect(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws Exception;

	@RequestMapping(value = "/mfCusMainBusiness/getAllList")
	public List<MfCusMainBusiness> getAllList(@RequestBody MfCusMainBusiness mfCusMainBusiness) throws Exception;

}
