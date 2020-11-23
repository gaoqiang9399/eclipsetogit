package  app.component.collateral.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.collateral.entity.WarnInfo;
import app.util.toolkit.Ipage;

/**
* Title: WarnInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Mar 08 11:22:01 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface WarnInfoFeign {
	
	@RequestMapping(value = "/warnInfo/insert")
	public void insert(@RequestBody WarnInfo warnInfo) throws ServiceException;
	
	@RequestMapping(value = "/warnInfo/delete")
	public void delete(@RequestBody WarnInfo warnInfo) throws ServiceException;
	
	@RequestMapping(value = "/warnInfo/update")
	public void update(@RequestBody WarnInfo warnInfo) throws ServiceException;
	
	@RequestMapping(value = "/warnInfo/getById")
	public WarnInfo getById(@RequestBody WarnInfo warnInfo) throws ServiceException;
	
	@RequestMapping(value = "/warnInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("warnInfo") WarnInfo warnInfo) throws ServiceException;

	@RequestMapping(value = "/warnInfo/getAll")
	public List<WarnInfo> getAll(@RequestBody WarnInfo warnInfo) throws ServiceException;
}
