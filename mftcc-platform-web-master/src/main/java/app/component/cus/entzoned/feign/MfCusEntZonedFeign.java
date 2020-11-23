package app.component.cus.entzoned.feign;

import app.base.ServiceException;
import app.component.cus.entzoned.entity.MfCusEntZoned;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusEntZonedFeign {
	@RequestMapping(value = "/mfCusEntZoned/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusEntZoned/getById")
	public MfCusEntZoned getById(@RequestBody MfCusEntZoned mfCusEntZoned) throws ServiceException;
	
	@RequestMapping(value = "/mfCusEntZoned/insert")
	public Map<String, Object> insert(@RequestBody MfCusEntZoned mfCusEntZoned) throws ServiceException;
	
	@RequestMapping(value = "/mfCusEntZoned/update")
	public void update(@RequestBody MfCusEntZoned mfCusEntZoned) throws ServiceException;
	
	@RequestMapping(value = "/mfCusEntZoned/delete")
	public void delete(@RequestBody MfCusEntZoned mfCusEntZoned) throws Exception;

	@RequestMapping(value = "/mfCusEntZoned/getAllList")
	public List<MfCusEntZoned> getAllList(@RequestBody MfCusEntZoned mfCusEntZoned) throws Exception;

}
