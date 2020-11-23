package app.component.assureamt.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.assureamt.entity.MfBusAssureAmt;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusAssureAmtBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 11 16:08:36 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusAssureAmtFeign {
	
	@RequestMapping("/mfBusAssureAmt/insert")
	public MfBusAssureAmt insert(@RequestBody MfBusAssureAmt mfBusAssureAmt) throws Exception;

	@RequestMapping("/mfBusAssureAmt/delete")
	public void delete(@RequestBody MfBusAssureAmt mfBusAssureAmt) throws Exception;

	@RequestMapping("/mfBusAssureAmt/update")
	public void update(@RequestBody MfBusAssureAmt mfBusAssureAmt) throws Exception;

	@RequestMapping("/mfBusAssureAmt/getById")
	public MfBusAssureAmt getById(@RequestBody MfBusAssureAmt mfBusAssureAmt) throws Exception;

	@RequestMapping("/mfBusAssureAmt/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
}
