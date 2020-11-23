package app.component.auth.feign;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditConfig;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfCusCreditConfigBo.java
* Description:客户授信申请业务控制操作
* @author:LJW
* @Wed Feb 22 18:08:09 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditConfigFeign {
	
	@RequestMapping(value = "/mfCusCreditConfig/insert")
	public MfCusCreditConfig insert(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditConfig/delete")
	public void delete(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditConfig/update")
	public void update(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditConfig/getById")
	public MfCusCreditConfig getById(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfCusCreditConfig/getAllList")
	public List<MfCusCreditConfig> getAllList() throws Exception;

	@RequestMapping(value = "/mfCusCreditConfig/updateCreditConfig")
	public Map<String,Object> updateCreditConfig(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws Exception;

	@RequestMapping(value = "/mfCusCreditConfig/getCreditConfigList")
	public List<MfCusCreditConfig> getCreditConfigList(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws Exception;

    @RequestMapping(value = "/mfCusCreditConfig/getCreditAndProjectList")
    public List<MfCusCreditConfig> getCreditAndProjectList() throws Exception;

    @RequestMapping(value = "/mfCusCreditConfig/getListByCusNo")
    public Map<String,Object> getListByCusNo(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws Exception;

	@RequestMapping(value = "/mfCusCreditConfig/getCredtByAdaptationKindNo")
	public List<MfCusCreditConfig> getCredtByAdaptationKindNo(@RequestBody MfCusCreditConfig mfCusCreditConfig) throws Exception;
}
