package  app.component.archives.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveConfig;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveConfigBo.java
* Description:归档管理配置
* @author:yudongwei@mftcc.cn
* @Wed Apr 05 16:48:41 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveConfigFeign {
	@RequestMapping(value = "/archiveConfig/findByPageAjax")
	public Ipage findByPageAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveConfig/insert")
	public void insert(@RequestBody ArchiveConfig archiveConfig) throws ServiceException;
	
	@RequestMapping(value = "/archiveConfig/delete")
	public void delete(@RequestBody ArchiveConfig archiveConfig) throws ServiceException;
	
	@RequestMapping(value = "/archiveConfig/update")
	public void update(@RequestBody ArchiveConfig archiveConfig) throws ServiceException;
	
	@RequestMapping(value = "/archiveConfig/getById")
	public ArchiveConfig getById(@RequestBody ArchiveConfig archiveConfig) throws ServiceException;
	
	@RequestMapping(value = "/archiveConfig/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("archiveConfig") ArchiveConfig archiveConfig) throws ServiceException;

	@RequestMapping(value = "/archiveConfig/getAll")
	public List<ArchiveConfig> getAll(@RequestBody ArchiveConfig archiveConfig) throws ServiceException;
}
