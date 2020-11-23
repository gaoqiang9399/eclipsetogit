package app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.api.ProcessDefinition;
import com.dhcc.workflow.api.RepositoryService;

import app.component.wkf.entity.DTOProcessDefinitionQuery;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface ProcessDefinitionFeign {
	/// wFProcessDefinition
	@RequestMapping(value = "/wFProcessDefinition/getRepositoryService")
	public RepositoryService getRepositoryService() throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/getRepositoryServiceCreateProcessDefinitionQuery")
	public DTOProcessDefinitionQuery createProcessDefinitionQuery() throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/getRepositoryServiceCreateProcessDefinitionQueryActivated")
	public List<ProcessDefinition> activated(@RequestParam("currentPage") Integer currentPage, @RequestParam("processDefName") String processDefName,
			@RequestParam("processDefKey") String processDefKey, @RequestParam("processDefDesc") String processDefDesc, @RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/delete")
	public void delete(@RequestParam("id") String id) throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/activate")
	public void activate(@RequestParam("id") String id) throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/suspend")
	public void suspend(@RequestParam("id") String id) throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/processDefFindByPage")
	public Ipage processDefFindByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/selectProcess")
	public List<ProcessDefinition> selectProcess() throws Exception;

	@RequestMapping(value = "/wFProcessDefinition/findByPageAjax")
	public Ipage findByPageAjax(@RequestBody Ipage ipage)
			throws Exception;

}
