package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pms.entity.PmsEntranceRole;

@FeignClient("mftcc-platform-factor")
public interface PmsEntranceRoleFeign {
	
	@RequestMapping(value = "/pmsEntranceRole/getByRoleNo")
	public List<PmsEntranceRole> getByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsEntranceRole/findByRoleNo")
	public List<PmsEntranceRole> findByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsEntranceRole/delByRoleNo")
	public void delByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsEntranceRole/insertByRoleNo")
	public void insertByRoleNo(@RequestBody String roleNo, @RequestParam("pmsNo") String pmsNo) throws ServiceException;
}
