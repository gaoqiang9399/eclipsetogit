package  app.component.calc.core.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfRepayHistoryDetailChannel;
import app.util.toolkit.Ipage;

/**
* Title: MfRepayHistoryDetailChannelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Oct 30 15:40:53 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfRepayHistoryDetailChannelFeign {
	
	@RequestMapping(value = "/mfRepayHistoryDetailChannel/insert")
	public MfRepayHistoryDetailChannel insert(@RequestBody MfRepayHistoryDetailChannel mfRepayHistoryDetailChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetailChannel/delete")
	public void delete(@RequestBody MfRepayHistoryDetailChannel mfRepayHistoryDetailChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetailChannel/update")
	public void update(@RequestBody MfRepayHistoryDetailChannel mfRepayHistoryDetailChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetailChannel/getById")
	public MfRepayHistoryDetailChannel getById(@RequestBody MfRepayHistoryDetailChannel mfRepayHistoryDetailChannel) throws Exception;
	
	@RequestMapping(value = "/mfRepayHistoryDetailChannel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfRepayHistoryDetailChannel") MfRepayHistoryDetailChannel mfRepayHistoryDetailChannel) throws Exception;
	
}
