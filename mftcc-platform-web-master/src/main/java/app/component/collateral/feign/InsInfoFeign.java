package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.InsInfo;
import app.util.toolkit.Ipage;

/**
* Title: InsInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 15 13:19:29 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface InsInfoFeign {
	
	@RequestMapping(value = "/insInfo/insert")
	public void insert(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/delete")
	public void delete(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/update")
	public void update(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/getById")
	public InsInfo getById(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/insInfo/getAll")
	public List<InsInfo> getAll(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/getByCollateralId")
	public InsInfo getByCollateralId(@RequestBody InsInfo insInfo) throws ServiceException;
	
	@RequestMapping(value = "/insInfo/validateDupInsNo")
	public String validateDupInsNo(@RequestBody String insNo) throws ServiceException;

	@RequestMapping(value = "/insInfo/getInsAmt")
	public Double getInsAmt(@RequestBody String appId)throws Exception;

	@RequestMapping(value = "/insInfo/getListByCollateralId")
	public List<InsInfo> getListByCollateralId(@RequestBody InsInfo insInfo)throws Exception;
}
