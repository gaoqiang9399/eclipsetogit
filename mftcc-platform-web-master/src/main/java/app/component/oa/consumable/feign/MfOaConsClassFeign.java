package  app.component.oa.consumable.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaConsClass;
import app.util.toolkit.Ipage;

/**
* Title: MfOaConsClassBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 24 11:58:00 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaConsClassFeign {
	@RequestMapping(value = "/mfOaConsClass/insert")
	public MfOaConsClass insert(@RequestBody MfOaConsClass mfOaConsClass,@RequestParam("isChild") String isChild) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/delete")
	public void delete(@RequestBody MfOaConsClass mfOaConsClass) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/update")
	public void update(@RequestBody MfOaConsClass mfOaConsClass) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/getById")
	public MfOaConsClass getById(@RequestBody MfOaConsClass mfOaConsClass) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/getConsClassList")
	public List<MfOaConsClass> getConsClassList(@RequestBody MfOaConsClass mfOaConsClass) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/getChildClassList")
	public List<MfOaConsClass> getChildClassList(@RequestBody MfOaConsClass mfOaConsClass) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsClass/getSuperClass")
	public List<MfOaConsClass> getSuperClass() throws ServiceException;

	@RequestMapping(value = "/mfOaConsClass/getAll")
	public List<MfOaConsClass> getAll(@RequestBody MfOaConsClass mfOaConsClass)  throws ServiceException;
}
