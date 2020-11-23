package  app.component.calc.core.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayHistoryChannel;
import app.util.toolkit.Ipage;

/**
* Title: MfRepayHistoryChannelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Oct 30 15:39:10 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayHistoryChannelFeign {
	
	@RequestMapping(value = "/mfRepayHistoryChannel/insert")
	public void insert(@RequestBody MfRepayHistoryChannel mfRepayHistoryChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryChannel/delete")
	public void delete(@RequestBody MfRepayHistoryChannel mfRepayHistoryChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryChannel/update")
	public void update(@RequestBody MfRepayHistoryChannel mfRepayHistoryChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryChannel/getById")
	public MfRepayHistoryChannel getById(@RequestBody MfRepayHistoryChannel mfRepayHistoryChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryChannel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayHistoryChannel") MfRepayHistoryChannel mfRepayHistoryChannel) throws Exception;
	
}
