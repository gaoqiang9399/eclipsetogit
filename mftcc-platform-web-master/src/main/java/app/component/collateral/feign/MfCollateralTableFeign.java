package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.MfCollateralTable;
import app.util.toolkit.Ipage;

/**
* Title: MfCollateralTableBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Mar 09 16:08:58 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCollateralTableFeign {
	
	@RequestMapping(value = "/mfCollateralTable/insert")
	public void insert(@RequestBody MfCollateralTable mfCollateralTable) throws Exception;
	
	@RequestMapping(value = "/mfCollateralTable/delete")
	public void delete(@RequestBody MfCollateralTable mfCollateralTable) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralTable/update")
	public void update(@RequestBody MfCollateralTable mfCollateralTable) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralTable/getById")
	public MfCollateralTable getById(@RequestBody MfCollateralTable mfCollateralTable) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralTable/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCollateralTable") MfCollateralTable mfCollateralTable) throws ServiceException;

	@RequestMapping(value = "/mfCollateralTable/getAll")
	public List<MfCollateralTable> getAll(@RequestBody MfCollateralTable mfCollateralTable) throws ServiceException;
	
	@RequestMapping(value = "/mfCollateralTable/getList")
	public List<MfCollateralTable> getList(@RequestBody MfCollateralTable mfCollateralTable) throws ServiceException;
	
}
