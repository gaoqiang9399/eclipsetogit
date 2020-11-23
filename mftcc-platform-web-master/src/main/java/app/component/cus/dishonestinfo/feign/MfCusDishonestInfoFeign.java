package app.component.cus.dishonestinfo.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.entity.MfCusEquityInfo;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfCusDishonestInfoFeign {
	@RequestMapping(value = "/mfCusDishonestInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusDishonestInfo/getById")
	public MfCusDishonestInfo getById(@RequestBody MfCusDishonestInfo mfCusDishonestInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusDishonestInfo/insert")
	public Map<String, Object> insert(@RequestBody MfCusDishonestInfo mfCusDishonestInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusDishonestInfo/update")
	public void update(@RequestBody MfCusDishonestInfo mfCusDishonestInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusDishonestInfo/delete")
	public void delete(@RequestBody MfCusDishonestInfo mfCusDishonestInfo) throws Exception;
	
	@RequestMapping(value = "/mfCusDishonestInfo/getAllList")
	public List<MfCusDishonestInfo> getAllList(@RequestBody MfCusDishonestInfo mfCusDishonestInfo) throws ServiceException;

}
