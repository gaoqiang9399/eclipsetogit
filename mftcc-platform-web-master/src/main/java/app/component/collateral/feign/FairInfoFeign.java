package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.FairInfo;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: FairInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 15 13:16:46 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface FairInfoFeign {
	
	@RequestMapping(value = "/fairInfo/insert")
	public void insert(@RequestBody FairInfo fairInfo) throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/delete")
	public void delete(@RequestBody FairInfo fairInfo) throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/update")
	public void update(@RequestBody FairInfo fairInfo) throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/getById")
	public FairInfo getById(@RequestBody FairInfo fairInfo) throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("fairInfo") FairInfo fairInfo) throws ServiceException;

	@RequestMapping(value = "/fairInfo/getAll")
	public List<FairInfo> getAll(@RequestBody FairInfo fairInfo) throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/getByCollateralId")
	public FairInfo getByCollateralId(@RequestBody FairInfo fairInfo) throws ServiceException;

	@RequestMapping(value = "/fairInfo/insertDocommit")
	public Result insertDocommit(@RequestBody FairInfo fairInfo)throws ServiceException;
	
	@RequestMapping(value = "/fairInfo/validateFairNo")
	public String validateFairNo(@RequestBody String fairNo) throws ServiceException;
}
