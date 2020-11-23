package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pms.entity.PmsViewpointRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@FeignClient("mftcc-platform-factor")
public interface PmsViewpointRoleFeign {
	@RequestMapping(value = "/pmsViewpointRole/delete")
	public void delete(@RequestBody JSONObject json) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/getAll")
	public JSONArray getAll() throws Exception;

	@RequestMapping(value = "/pmsViewpointRole/update")
	public void update(@RequestBody JSONObject json) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/insert")
	public String insert(@RequestBody JSONObject json) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/findById")
	public int findById(@RequestBody String viewpointMenuNo) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/delByRoleNo")
	public void delByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/findByRoleNo")
	public List<PmsViewpointRole> findByRoleNo(@RequestBody String roleNo) throws ServiceException;

	@RequestMapping(value = "/pmsViewpointRole/insertByRoleNo")
	public void insertByRoleNo(@RequestBody String roleNo, @RequestParam("viewpointMenuNo") String viewpointMenuNo) throws ServiceException;
}
