package app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.pvm.internal.model.ProcessDefinitionImpl;

@FeignClient("mftcc-platform-factor")
public interface WkfDesignFeign {

	@RequestMapping(value = "/wkfDesign/getTaskURL")
	public String getTaskURL(@RequestBody Map<String, String> map) throws Exception;

	@RequestMapping(value = "/wkfDesign/processDefinitionKey")
	public List<ProcessDefinitionImpl> processDefinitionKey(@RequestParam("appWorkflowNo") String appWorkflowNo) throws Exception;

	@RequestMapping(value = "/wkfDesign/processDefinitionId")
	public List<ProcessDefinitionImpl> processDefinitionId(@RequestParam("procdefid") String procdefid) throws Exception;

	@RequestMapping(value = "/wkfDesign/orderDesc")
	public List<ProcessDefinitionImpl> orderDesc() throws Exception;

	@RequestMapping(value = "/wkfDesign/createProcessDefinitionQuery")
	public List<ProcessDefinitionImpl> createProcessDefinitionQuery() throws Exception;
	
	@RequestMapping(value = "/wkfDesign/saveOrUpdate")
	public String saveOrUpdate(@RequestParam("id") String id,@RequestBody  String text) throws Exception;
	@RequestMapping(value = "/wkfDesign/findById")
	public String findById(@RequestParam("processId") String processId) throws Exception;
	@RequestMapping(value = "/wkfDesign/saveAndStart")
	public String saveAndStart(@RequestParam("id") String id,@RequestBody  String text) throws Exception;
}
