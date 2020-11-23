package  app.component.cus.cusgroup.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.cusgroup.entity.MfCusGroup;
import app.util.toolkit.Ipage;

/**
* Title: MfCusGroupBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Dec 05 15:36:35 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusGroupFeign {
	
	@RequestMapping(value = "/mfCusGroup/insert")
	public void insert(@RequestBody MfCusGroup mfCusGroup) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGroup/delete")
	public void delete(@RequestBody MfCusGroup mfCusGroup) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGroup/update")
	public void update(@RequestBody MfCusGroup mfCusGroup) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGroup/getById1")
	public MfCusGroup getById1(@RequestBody String string) throws ServiceException;
	@RequestMapping(value = "/mfCusGroup/getById")
	public MfCusGroup getById(@RequestBody MfCusGroup mfCusGroup) throws ServiceException;
	
	@RequestMapping(value = "/mfCusGroup/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/mfCusGroup/getCusGroupList")
	public Ipage getCusGroupList(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping("/mfCusGroup/getCusGroupListReal")
	public List<MfCusGroup> getCusGroupListReal(@RequestBody MfCusGroup mfCusGroup) throws Exception;
	
	@RequestMapping(value = "/mfCusGroup/getByIdNum")
	public MfCusGroup getByIdNum(@RequestBody MfCusGroup mfCusGroup) throws ServiceException;
}
