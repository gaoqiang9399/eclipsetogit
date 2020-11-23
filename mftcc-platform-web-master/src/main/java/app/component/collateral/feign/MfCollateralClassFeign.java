package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfCollateralClass;
import app.util.toolkit.Ipage;

/**
* Title: MfCollateralClassBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Mar 09 10:15:54 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCollateralClassFeign {
	
	@RequestMapping(value = "/mfCollateralClass/insert")
	public void insert(@RequestBody MfCollateralClass mfCollateralClass) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralClass/delete")
	public void delete(@RequestBody MfCollateralClass mfCollateralClass) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralClass/update")
	public void update(@RequestBody MfCollateralClass mfCollateralClass) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralClass/getById")
	public MfCollateralClass getById(@RequestBody MfCollateralClass mfCollateralClass) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralClass/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfCollateralClass/getAll")
	public List<MfCollateralClass> getAll(@RequestBody MfCollateralClass mfCollateralClass) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralClass/findNextClassSecondNo")
	public String findNextClassSecondNo(@RequestBody String classFirstNo) throws Exception;
	
	@RequestMapping(value = "/mfCollateralClass/getByClassSecondNo")
	public MfCollateralClass getByClassSecondNo(@RequestBody MfCollateralClass mfCollateralClass) throws Exception;
}
