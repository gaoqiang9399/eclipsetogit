package app.component.wkf.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dhcc.workflow.pvm.internal.task.WFProxy;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface WFProxyFeign {

	@RequestMapping(value = "/wFProxy/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wFProxy/saveProxy")
	public String saveProxy(@RequestBody WFProxy proxy) throws Exception;

	@RequestMapping(value = "/wFProxy/deleteProxy")
	public void deleteProxy(@RequestParam("proxyId") String proxyId) throws Exception;

	@RequestMapping(value = "/wFProxy/findProxy")
	public WFProxy findProxy(@RequestBody String proxyId) throws Exception;

	@RequestMapping(value = "/wFProxy/findUsers")
	public List findUsers(@RequestParam("userId") String userId, @RequestParam("userName") String userName) throws Exception;

	@RequestMapping(value = "/wFProxy/createProcessDefinitionQuery")
	public List createProcessDefinitionQuery(@RequestParam("no") Integer no) throws Exception;

}
