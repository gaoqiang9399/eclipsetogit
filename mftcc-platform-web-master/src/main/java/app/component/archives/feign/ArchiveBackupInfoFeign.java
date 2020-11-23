package  app.component.archives.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveBackupInfo;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveBackupInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Apr 08 15:42:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveBackupInfoFeign {
	
	@RequestMapping(value = "/archiveBackupInfo/insert")
	public void insert(@RequestBody ArchiveBackupInfo archiveBackupInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveBackupInfo/delete")
	public void delete(@RequestBody ArchiveBackupInfo archiveBackupInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveBackupInfo/update")
	public void update(@RequestBody ArchiveBackupInfo archiveBackupInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveBackupInfo/getById")
	public ArchiveBackupInfo getById(@RequestBody ArchiveBackupInfo archiveBackupInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveBackupInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("archiveBackupInfo") ArchiveBackupInfo archiveBackupInfo) throws ServiceException;

	@RequestMapping(value = "/archiveBackupInfo/getAll")
	public List<ArchiveBackupInfo> getAll(@RequestBody ArchiveBackupInfo archiveBackupInfo) throws ServiceException;
}
