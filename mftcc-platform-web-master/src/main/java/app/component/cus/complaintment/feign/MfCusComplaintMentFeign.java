package app.component.cus.complaintment.feign;

import app.base.ServiceException;
import app.component.cus.complaintment.entity.MfCusComplaintMent;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusComplaintMentFeign {
	@RequestMapping(value = "/mfCusComplaintMent/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusComplaintMent/getById")
	public MfCusComplaintMent getById(@RequestBody MfCusComplaintMent mfCusComplaintMent) throws ServiceException;
	
	@RequestMapping(value = "/mfCusComplaintMent/insert")
	public Map<String, Object> insert(@RequestBody MfCusComplaintMent mfCusComplaintMent) throws ServiceException;
	
	@RequestMapping(value = "/mfCusComplaintMent/update")
	public void update(@RequestBody MfCusComplaintMent mfCusComplaintMent) throws ServiceException;
	
	@RequestMapping(value = "/mfCusComplaintMent/delete")
	public void delete(@RequestBody MfCusComplaintMent mfCusComplaintMent) throws Exception;

	@RequestMapping(value = "/mfCusComplaintMent/getAllList")
	public List<MfCusComplaintMent> getAllList(@RequestBody MfCusComplaintMent mfCusComplaintMent) throws Exception;

}
