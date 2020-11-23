package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.ChkInfo;
import app.util.toolkit.Ipage;

/**
* Title: ChkInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 08 10:21:44 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ChkInfoFeign {
	
	@RequestMapping(value = "/chkInfo/insert")
	public void insert(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/delete")
	public void delete(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/update")
	public void update(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/getById")
	public ChkInfo getById(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/chkInfo/getAll")
	public List<ChkInfo> getAll(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/getByCollateralId")
	public ChkInfo getByCollateralId(@RequestBody ChkInfo chkInfo) throws ServiceException;
	
	@RequestMapping(value = "/chkInfo/getLastest")
	public ChkInfo getLastest(@RequestBody ChkInfo chkInfo) throws ServiceException;
}
