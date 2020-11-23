package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pms.entity.PmsDataRangRole;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface PmsDataRangRoleFeign {
	
	@RequestMapping(value = "/pmsDataRangRole/getById")
	public PmsDataRangRole getById(@RequestBody PmsDataRangRole pmsDataRangRole) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/getByRoleNo")
	public List<PmsDataRangRole> getByRoleNo(@RequestBody String[] roleNos) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/delete")
	public void delete(@RequestBody PmsDataRangRole pmsDataRangRole) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg, @RequestParam("pmsDataRangRole") PmsDataRangRole pmsDataRangRole) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/update")
	public void update(@RequestBody PmsDataRangRole pmsDataRangRole) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/insert")
	public void insert(@RequestBody PmsDataRangRole pmsDataRangRole) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/delByRoleNo")
	public void delByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/findByRoleNo")
	public List<PmsDataRangRole> findByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsDataRangRole/insertByRoleNo")
	public void insertByRoleNo(@RequestBody String roleNo, @RequestParam("funNo") String funNo) throws ServiceException;
}
