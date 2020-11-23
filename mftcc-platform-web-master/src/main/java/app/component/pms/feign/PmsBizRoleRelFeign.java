package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pms.entity.PmsBizRoleRel;
@FeignClient("mftcc-platform-factor")
public interface PmsBizRoleRelFeign {
	
	@RequestMapping(value = "/pmsBizRoleRel/getAllByRoleNo")
	public List<PmsBizRoleRel> getAllByRoleNo(@RequestBody String roleNo) throws Exception;

	@RequestMapping(value = "/pmsBizRoleRel/getAll4Cache")
	public List<PmsBizRoleRel> getAll4Cache() throws Exception;

	@RequestMapping(value = "/pmsBizRoleRel/insert")
	public void insert(@RequestBody PmsBizRoleRel pmsBizRoleRel) throws Exception;

	@RequestMapping(value = "/pmsBizRoleRel/deleteByRoleNo")
	public void deleteByRoleNo(@RequestBody PmsBizRoleRel pmsBizRoleRel) throws Exception;

	@RequestMapping(value = "/pmsBizRoleRel/insertBatch")
	public void insertBatch(@RequestBody List<PmsBizRoleRel> list) throws Exception;
}
