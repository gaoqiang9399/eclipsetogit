package  app.component.lawsuit.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.util.toolkit.Ipage;

/**
* Title: MfLawsuitFollowBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Feb 22 21:21:30 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfLawsuitFollowFeign {
	
	@RequestMapping(value = "/mfLawsuitFollow/insert")
	public void insert(@RequestBody MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuitFollow/delete")
	public String delete(@RequestBody MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuitFollow/update")
	public void update(@RequestBody MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuitFollow/getById")
	public MfLawsuitFollow getById(@RequestBody MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuitFollow/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfLawsuitFollow") MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
	@RequestMapping(value = "/mfLawsuitFollow/getFollowListByCaseId")
	public List<MfLawsuitFollow> getFollowListByCaseId(@RequestBody MfLawsuitFollow mfLawsuitFollow) throws ServiceException;
	
}
