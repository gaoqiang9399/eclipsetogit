package  app.component.sys.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.sys.entity.SysBtnDef;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: SysBtnDefBo.java
* Description:
* @author:@dhcc.com.cn
* @Mon Aug 22 07:31:44 GMT 2016
**/
@FeignClient("mftcc-platform-factor")
public interface SysBtnDefFeign {
	@RequestMapping("/sysBtnDef/insert")
	public void insert(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/delete")
	public void delete(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/update")
	public void update(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/getById")
	public SysBtnDef getById(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/getByBtnNo")
	public SysBtnDef getByBtnNo(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/getByFunNo")
	public SysBtnDef getByFunNo(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/getByComponentName")
	public SysBtnDef getByComponentName(SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	@RequestMapping("/sysBtnDef/findByLv")
	public JSONArray findByLv(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	@RequestMapping("/sysBtnDef/findByLvForRoleNo")
	public JSONArray findByLvForRoleNo(@RequestBody SysBtnDef sysBtnDef) throws ServiceException;
	
}
