package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pms.entity.PmsBiz;

@FeignClient("mftcc-platform-factor")
public interface PmsBizFeign {
	
	@RequestMapping(value = "/pmsBiz/getAll")
	public List<PmsBiz> getAll() throws Exception;

	@RequestMapping(value = "/pmsBiz/getAllBySts")
	public List<PmsBiz> getAllBySts(@RequestBody String bizState) throws Exception;

	@RequestMapping(value = "/pmsBiz/insert")
	public void insert(@RequestBody PmsBiz pmsBiz) throws Exception;

	@RequestMapping(value = "/pmsBiz/update")
	public void update(@RequestBody PmsBiz pmsBiz) throws Exception;

	@RequestMapping(value = "/pmsBiz/updateStsById")
	public void updateStsById(@RequestBody PmsBiz pmsBiz) throws Exception;

	@RequestMapping(value = "/pmsBiz/getById")
	public PmsBiz getById(@RequestBody String pmsSerno) throws Exception;

	@RequestMapping(value = "/pmsBiz/deleteByIdArr")
	public void deleteByIdArr(@RequestBody PmsBiz pmsBiz) throws Exception;

	@RequestMapping(value = "/pmsBiz/getAllByEntranceRole")
	public List<PmsBiz> getAllByEntranceRole(@RequestBody String roleNo) throws Exception;

	@RequestMapping(value = "/pmsBiz/getAllByEntrance")
	public List<PmsBiz> getAllByEntrance(@RequestBody String bizState, @RequestParam("entrNoStr") String entrNoStr, @RequestParam("roleNo") String roleNo) throws Exception;

	@RequestMapping(value = "/pmsBiz/getAllPmsBizByRole")
	public List<PmsBiz> getAllPmsBizByRole(@RequestBody String bizState, @RequestParam("roleNo") String roleNo) throws Exception;

	@RequestMapping(value = "/pmsBiz/getAllOaByRoleNo")
	public List<PmsBiz> getAllOaByRoleNo(@RequestBody String roleNo) throws Exception;
}
