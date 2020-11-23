package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.pms.entity.PmsEntrance;
import net.sf.json.JSONObject;

@FeignClient("mftcc-platform-factor")
public interface PmsEntranceFeign {
	
	// ToDo: 可能需要改造为返回对象。
	@RequestMapping(value = "/pmsEntrance/insert", method = RequestMethod.POST)
	public String insert(@RequestBody JSONObject json) throws Exception;

	@RequestMapping(value = "/pmsEntrance/delete", method = RequestMethod.POST)
	public void delete(@RequestBody JSONObject json) throws Exception;

	@RequestMapping(value = "/pmsEntrance/update", method = RequestMethod.POST)
	public void update(@RequestBody JSONObject json) throws Exception;

	@RequestMapping("/pmsEntrance/getById")
	public PmsEntrance getById(@RequestBody PmsEntrance pmsEntrance) throws Exception;

	@RequestMapping("/pmsEntrance/getAllList")
	public List<PmsEntrance> getAllList() throws Exception;

	@RequestMapping("/pmsEntrance/getAllPmsEntrance")
	public List<PmsEntrance> getAllPmsEntrance() throws Exception;

	@RequestMapping("/pmsEntrance/getEntranceList")
	public List<PmsEntrance> getEntranceList() throws Exception;

	@RequestMapping("/pmsEntrance/getByRoleNo/{roleNo}")
	public List<PmsEntrance> getByRoleNo(@PathVariable("roleNo") String roleNo) throws Exception;

	@RequestMapping(value = "/pmsEntrance/getAll",produces = {"text/html;charset=utf-8"})
	public String getAll() throws Exception;

}
