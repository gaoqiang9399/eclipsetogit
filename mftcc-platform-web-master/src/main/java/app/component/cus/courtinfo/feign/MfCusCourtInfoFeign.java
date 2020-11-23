package app.component.cus.courtinfo.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.courtinfo.entity.MfCusCourtInfo;
import app.component.cus.entity.MfCusEquityInfo;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfCusCourtInfoFeign {
	@RequestMapping(value = "/mfCusCourtInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCourtInfo/getById")
	public MfCusCourtInfo getById(@RequestBody MfCusCourtInfo mfCusCourtInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCourtInfo/insert")
	public Map<String, Object> insert(@RequestBody MfCusCourtInfo mfCusCourtInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCourtInfo/update")
	public void update(@RequestBody MfCusCourtInfo mfCusCourtInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCourtInfo/delete")
	public void delete(@RequestBody MfCusCourtInfo mfCusCourtInfo) throws Exception;

	@RequestMapping(value = "/mfCusCourtInfo/getAllList")
	public List<MfCusCourtInfo> getAllList(@RequestBody MfCusCourtInfo mfCusCourtInfo) throws Exception;

}
