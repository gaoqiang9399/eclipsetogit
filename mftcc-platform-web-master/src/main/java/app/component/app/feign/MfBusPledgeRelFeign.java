package  app.component.app.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusPledgeRel;
import app.util.toolkit.Ipage;

/**
* Title: MfBusPledgeRelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 29 19:02:57 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusPledgeRelFeign {
	
	@RequestMapping(value = "/mfBusPledgeRel/insert")
	public void insert(@RequestBody MfBusPledgeRel mfBusPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusPledgeRel/delete")
	public void delete(@RequestBody MfBusPledgeRel mfBusPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusPledgeRel/update")
	public void update(@RequestBody MfBusPledgeRel mfBusPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusPledgeRel/getById")
	public MfBusPledgeRel getById(@RequestBody MfBusPledgeRel mfBusPledgeRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusPledgeRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusPledgeRel") MfBusPledgeRel mfBusPledgeRel) throws ServiceException;

	@RequestMapping(value = "/mfBusPledgeRel/getAll")
	public List<MfBusPledgeRel> getAll(@RequestBody MfBusPledgeRel mfBusPledgeRel) throws ServiceException;
}
