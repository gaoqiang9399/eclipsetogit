package app.component.paraminterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.param.entity.BusCtlParmMang;
import app.component.param.entity.ParmCorpInfo;
import app.component.param.entity.Scence;
import app.component.sys.entity.SysOrg;

@FeignClient("mftcc-platform-factor")
public interface ParamInterfaceFeign {

	@RequestMapping(value = "/paramInterface/getBusCtlParmMang", method = RequestMethod.POST)
	public BusCtlParmMang getBusCtlParmMang(@RequestBody String keyName) throws Exception;

	@RequestMapping(value = "/paramInterface/getAllScence", method = RequestMethod.POST)
	public List<Scence> getAllScence() throws Exception;

	@RequestMapping(value = "/paramInterface/getParmCorpInfo", method = RequestMethod.POST)
	public ParmCorpInfo getParmCorpInfo(@RequestBody SysOrg sysOrg) throws Exception;

}
