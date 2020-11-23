package app.component.msgconf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.msgconf.entity.MfMsgPledge;
import app.util.toolkit.Ipage;

/**
 * Title: MfMsgPledgeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 10 13:45:12 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfMsgPledgeFeign {

	@RequestMapping(value = "/mfMsgPledge/insert")
	public void insert(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	@RequestMapping(value = "/mfMsgPledge/delete")
	public void delete(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	@RequestMapping(value = "/mfMsgPledge/update")
	public void update(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	@RequestMapping(value = "/mfMsgPledge/getById")
	public MfMsgPledge getById(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	@RequestMapping(value = "/mfMsgPledge/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfMsgPledge/getAll")
	public List<MfMsgPledge> getAll(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	// 预警信息map
	@RequestMapping(value = "/mfMsgPledge/getMfMsgPledgeMap")
	public Map<String, List<MfMsgPledge>> getMfMsgPledgeMap(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;

	@RequestMapping(value = "/mfMsgPledge/updateUseFlagById")
	public void updateUseFlagById(@RequestBody MfMsgPledge mfMsgPledge) throws Exception;
}
