package app.component.pms.feign;

import java.util.List;

import net.sf.json.JSONArray;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.pms.entity.PmsViewpoint;
import net.sf.json.JSONObject;

@FeignClient("mftcc-platform-factor")
public interface PmsViewpointFeign {
	
	@RequestMapping(value = "/pmsViewpoint/insert", method = RequestMethod.POST)
	public String insert(@RequestBody JSONObject json) throws Exception;

	@RequestMapping(value = "/pmsViewpoint/delete", method = RequestMethod.POST)
	public void delete(@RequestBody JSONObject json) throws Exception;

	@RequestMapping(value = "/pmsViewpoint/update", method = RequestMethod.POST)
	public void update(@RequestBody JSONObject json) throws Exception;

	@RequestMapping(value = "/pmsViewpoint/updateSeq", method = RequestMethod.POST)
	public void updateSeq(@RequestBody JSONObject json) throws Exception;

	@RequestMapping("/pmsViewpoint/findById/{viewpointMenuNo}")
	public int findById(@PathVariable("viewpointMenuNo") String viewpointMenuNo) throws Exception;

	@RequestMapping("/pmsViewpoint/getAllList")
	public List<PmsViewpoint> getAllList() throws Exception;

	@RequestMapping("/pmsViewpoint/getPmsViewpointByRole/{roleNo}")
	public List<PmsViewpoint> getPmsViewpointByRole(@PathVariable("roleNo") String roleNo) throws Exception;
	
	@RequestMapping("/pmsViewpoint/getAllPmsViewpoint")
	public List<PmsViewpoint> getAllPmsViewpoint() throws Exception;
	
	@RequestMapping("/pmsViewpoint/getAll")
	public JSONArray getAll()throws Exception;

	@RequestMapping("/pmsViewpoint/getChildrens")
	public String getChildrens(@RequestBody JSONObject jsonObj) throws Exception;

}
