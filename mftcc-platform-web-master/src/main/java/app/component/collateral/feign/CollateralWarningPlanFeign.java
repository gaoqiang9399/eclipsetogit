package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.CollateralWarningPlan;
import app.util.toolkit.Ipage;

/**
* Title: CollateralWarningPlanBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Mar 31 16:47:32 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface CollateralWarningPlanFeign {
	
	@RequestMapping(value = "/collateralWarningPlan/insert")
	public void insert(@RequestBody CollateralWarningPlan collateralWarningPlan) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningPlan/delete")
	public void delete(@RequestBody CollateralWarningPlan collateralWarningPlan) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningPlan/update")
	public void update(@RequestBody CollateralWarningPlan collateralWarningPlan) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningPlan/getById")
	public CollateralWarningPlan getById(@RequestBody CollateralWarningPlan collateralWarningPlan) throws ServiceException;
	
	@RequestMapping(value = "/collateralWarningPlan/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("collateralWarningPlan") CollateralWarningPlan collateralWarningPlan) throws ServiceException;

	@RequestMapping(value = "/collateralWarningPlan/getAll")
	public List<CollateralWarningPlan> getAll(@RequestBody CollateralWarningPlan collateralWarningPlan) throws ServiceException;
}
