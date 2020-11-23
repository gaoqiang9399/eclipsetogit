package  app.component.collateral.carcheck.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.collateral.carcheck.entity.MfCarCheckForm;
import app.util.toolkit.Ipage;

/**
* Title: MfPledgeEvalInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Aug 08 14:35:28 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCarCheckFormFeign {
	
	@RequestMapping(value = "/mfCarCheckForm/insert")
	public Map<String, Object> insert(@RequestBody MfCarCheckForm mfCarCheckForm) throws ServiceException;
	
	@RequestMapping(value = "/mfCarCheckForm/delete")
	public void delete(@RequestBody MfCarCheckForm mfCarCheckForm) throws ServiceException;
	
	@RequestMapping(value = "/mfCarCheckForm/update")
	public void update(@RequestBody MfCarCheckForm mfCarCheckForm) throws ServiceException;
	
	@RequestMapping(value = "/mfCarCheckForm/getById")
	public MfCarCheckForm getById(@RequestBody MfCarCheckForm mfCarCheckForm) throws ServiceException;

	@RequestMapping(value = "/mfCarCheckForm/getListById")
	public List<MfCarCheckForm> getListById(@RequestBody MfCarCheckForm mfCarCheckForm) throws ServiceException;

	@RequestMapping(value = "/mfCarCheckForm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCarCheckForm/saveGetCarInfo")
	public void saveGetCarInfo(@RequestBody MfCarCheckForm mfCarCheckForm);
	
}