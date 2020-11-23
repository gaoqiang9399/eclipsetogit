package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfCollateralFormConfig;
import app.util.toolkit.Ipage;

/**
* Title: MfCollateralFormConfigBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Mar 09 16:11:42 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCollateralFormConfigFeign {
	
	@RequestMapping(value = "/mfCollateralFormConfig/insert")
	public void insert(@RequestBody MfCollateralFormConfig mfCollateralFormConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralFormConfig/delete")
	public void delete(@RequestBody MfCollateralFormConfig mfCollateralFormConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralFormConfig/update")
	public void update(@RequestBody MfCollateralFormConfig mfCollateralFormConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralFormConfig/getById")
	public MfCollateralFormConfig getById(@RequestBody MfCollateralFormConfig mfCollateralFormConfig) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralFormConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfCollateralFormConfig/getAll")
	public List<MfCollateralFormConfig> getAll(@RequestBody MfCollateralFormConfig mfCollateralFormConfig) throws ServiceException;


	@RequestMapping(value = "/mfCollateralFormConfig/getByPledgeImPawnType")
	public MfCollateralFormConfig getByPledgeImPawnType(@RequestBody String pledgeImpawnType,@RequestParam("action") String action) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralFormConfig/getByPledgeImPawnType")
	public MfCollateralFormConfig getByPledgeImPawnType(@RequestParam("classId") String classId,@RequestParam("action") String action,@RequestParam("formType") String formType) throws ServiceException;

	@RequestMapping(value = "/mfCollateralFormConfig/getByFormType")
	public List<MfCollateralFormConfig> getByFormType(@RequestBody MfCollateralFormConfig mfCollateralFormConfig);
	
//	@RequestMapping(value = "/mfCollateralFormConfig/getByPledgeImPawnType")
	//public MfCollateralFormConfig getByPledgeImPawnType(@RequestBody String pledgeImpawnType,@RequestParam("vouType") String vouType,@RequestParam("action") String action) throws ServiceException;
}
