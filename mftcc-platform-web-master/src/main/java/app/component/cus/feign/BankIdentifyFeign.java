package app.component.cus.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.BankIdentify;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface BankIdentifyFeign {
	@RequestMapping(value = "/bankIdentify/insert")
	public void insert(@RequestBody BankIdentify bankIdentify) throws Exception;;
	
	@RequestMapping(value = "/bankIdentify/delete")
	public void delete(@RequestBody BankIdentify bankIdentify) throws Exception;
	
	@RequestMapping(value = "/bankIdentify/update")
	public void update(@RequestBody BankIdentify bankIdentify) throws Exception;
	
	@RequestMapping(value = "/bankIdentify/getById")
	public BankIdentify getById(@RequestBody BankIdentify bankIdentify) throws Exception;
	
	@RequestMapping(value = "/bankIdentify/getDataMapById")
	public Map<String,Object> getDataMapById(@RequestBody BankIdentify bankIdentify) throws Exception;
	
	@RequestMapping(value = "/bankIdentify/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws Exception;
}
