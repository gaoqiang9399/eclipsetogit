package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.CollateralWarningDetail;
import app.util.toolkit.Ipage;

/**
* Title: CollateralWarningDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Mar 31 16:49:24 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface CollateralWarningDetailFeign {
	
	@RequestMapping(value = "/collateralWarningDetail/insert")
	public void insert(@RequestBody CollateralWarningDetail collateralWarningDetail) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningDetail/delete")
	public void delete(@RequestBody CollateralWarningDetail collateralWarningDetail) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningDetail/update")
	public void update(@RequestBody CollateralWarningDetail collateralWarningDetail) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningDetail/getById")
	public CollateralWarningDetail getById(@RequestBody CollateralWarningDetail collateralWarningDetail) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("collateralWarningDetail") CollateralWarningDetail collateralWarningDetail) throws ServiceException;

	@RequestMapping(value = "/collateralWarningDetail/getAll")
	public List<CollateralWarningDetail> getAll(@RequestBody CollateralWarningDetail collateralWarningDetail) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningDetail/updateList")
	public void updateList(@RequestBody List<CollateralWarningDetail> collateralWarningDetailList) throws ServiceException;
}
