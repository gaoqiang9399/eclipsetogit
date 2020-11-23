package  app.component.modifyinfo.feign;

import app.base.ServiceException;
import app.component.modifyinfo.entity.MfBusModifyFincUseDes;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfBusModifyFincUseDesFeign {

	@RequestMapping(value = "/mfBusModifyFincUseDes/insert")
	public void insert(@RequestBody MfBusModifyFincUseDes mfBusModifyFincUseDes) throws ServiceException;

	@RequestMapping(value = "/mfBusModifyFincUseDes/delete")
	public void delete(@RequestBody MfBusModifyFincUseDes mfBusModifyFincUseDes) throws ServiceException;

	@RequestMapping(value = "/mfBusModifyFincUseDes/update")
	public void update(@RequestBody MfBusModifyFincUseDes mfBusModifyFincUseDes) throws ServiceException;

	@RequestMapping(value = "/mfBusModifyFincUseDes/getById")
	public MfBusModifyFincUseDes getById(@RequestBody MfBusModifyFincUseDes mfBusModifyFincUseDes) throws ServiceException;

	@RequestMapping(value = "/mfBusModifyFincUseDes/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

}
