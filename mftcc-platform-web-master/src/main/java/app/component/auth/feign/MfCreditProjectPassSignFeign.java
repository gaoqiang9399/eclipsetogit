package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCreditProjectPassSign;
import app.util.toolkit.Ipage;

/**
* Title: MfCreditProjectPassSignBo.java
* Description:客户授信申请业务操作控制
* @author:LJW
* @Mon Feb 27 10:43:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCreditProjectPassSignFeign {
	
	@RequestMapping(value = "/mfCreditProjectPassSign/insert")
	public MfCreditProjectPassSign insert(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	
	@RequestMapping(value = "/mfCreditProjectPassSign/delete")
	public void delete(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	

	@RequestMapping(value = "/mfCreditProjectPassSign/update")
	public void update(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	
	
	@RequestMapping(value = "/mfCreditProjectPassSign/getById")
	public MfCreditProjectPassSign getById(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	
	@RequestMapping(value = "/mfCreditProjectPassSign/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCreditProjectPassSign") MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	
	@RequestMapping(value = "/mfCreditProjectPassSign/getMfCreditProjectPassSignList")
	public List<MfCreditProjectPassSign> getMfCreditProjectPassSignList(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws ServiceException;
	
	@RequestMapping(value = "/mfCreditProjectPassSign/updateProjectPassSign")
	public Map<String, Object> updateProjectPassSign(@RequestBody MfCreditProjectPassSign mfCreditProjectPassSign) throws Exception;
}
