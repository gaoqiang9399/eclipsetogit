package  app.tech.wkf.bo;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.tech.wkf.entity.WfExecution;

@FeignClient("mftcc-platform-factor")
public interface WfExecutionFeign {
	@RequestMapping(value = "/wfExecution/getById")
	public List<WfExecution> getById(@RequestBody WfExecution wfExecution) throws ServiceException;
	@RequestMapping(value = "/wfExecution/getByIdForHis")
	public List<WfExecution> getByIdForHis(@RequestBody WfExecution wfExecution) throws ServiceException;
	@RequestMapping(value = "/wfExecution/getWkfId")
	public WfExecution getWkfId(@RequestBody WfExecution wfExecution) throws ServiceException;
	
}
