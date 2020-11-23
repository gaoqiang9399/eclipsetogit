package  app.component.msgconf.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.msgconf.entity.MfMsgVar;
import app.util.toolkit.Ipage;

/**
* Title: MfMsgVarBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 05 15:23:19 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfMsgVarFeign {
	
	@RequestMapping(value = "/mfMsgVar/insert")
	public void insert(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/delete")
	public void delete(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/update")
	public void update(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/updateFlag")
	public int updateFlag(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/getById")
	public MfMsgVar getById(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfMsgVar/getAll")
	public List<MfMsgVar> getAll(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/getListByVarUsages")
	public List<MfMsgVar> getListByVarUsages(@RequestBody MfMsgVar mfMsgVar) throws Exception;
	
	@RequestMapping(value = "/mfMsgVar/getListByVarUsage")
	public List<MfMsgVar> getListByVarUsage(@RequestBody MfMsgVar mfMsgVar) throws Exception;
}
