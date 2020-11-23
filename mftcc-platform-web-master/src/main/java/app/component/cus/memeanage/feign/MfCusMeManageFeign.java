package app.component.cus.memeanage.feign;

import app.base.ServiceException;
import app.component.cus.cusmemanage.entity.MfCusMeManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusMeManageFeign {
	@RequestMapping(value = "/mfCusMeManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMeManage/getById")
	public MfCusMeManage getById(@RequestBody MfCusMeManage mfCusMeManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMeManage/insert")
	public Map<String, Object> insert(@RequestBody MfCusMeManage mfCusMeManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMeManage/update")
	public void update(@RequestBody MfCusMeManage mfCusMeManage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusMeManage/delete")
	public void delete(@RequestBody MfCusMeManage mfCusMeManage) throws Exception;

	@RequestMapping(value = "/mfCusMeManage/getAllList")
	public List<MfCusMeManage> getAllList(@RequestBody MfCusMeManage mfCusMeManage) throws Exception;

}
