package app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.tech.wkf.entity.WfExecution;
import net.sf.json.JSONArray;

@FeignClient("mftcc-platform-factor")
public interface WfExecutionBoFeign {

	@RequestMapping(value = "/wfExecution/getById")
	public List<WfExecution> getById(@RequestBody WfExecution wfExecution) throws Exception;

	@RequestMapping(value = "/wfExecution/getByIdForHis")
	public List<WfExecution> getByIdForHis(@RequestBody WfExecution wfExecution) throws Exception;

	@RequestMapping(value = "/wfExecution/getByIdHis")
	public JSONArray getByIdHis(@RequestParam("appId") String appId) throws Exception;
	
}
