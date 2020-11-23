package  app.component.archives.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveLendInfo;
import app.component.archives.entity.ArchiveLendInfoIncludeDetailAndLog;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveLendInfoBo.java
* Description:归档文件借阅信息Bo
* @author:yudongwei@mftcc.cn
* @Tue Apr 11 18:04:18 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveLendInfoFeign {
	
	@RequestMapping(value = "/archiveLendInfo/insert")
	public void insert(@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveLendInfo/delete")
	public void delete(@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveLendInfo/update")
	public void update(@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveLendInfo/getById")
	public ArchiveLendInfo getById(@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveLendInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("archiveLendInfo") ArchiveLendInfo archiveLendInfo) throws ServiceException;

	@RequestMapping(value = "/archiveLendInfo/getAll")
	public List<ArchiveLendInfo> getAll(@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;

	/**
	 * 获取归档文件借阅信息（包括归档明细信息和归档文件操作日志）集合
	 * @param archiveLendInfo 归档文件借阅信息
	 * @return 归档文件借阅信息（包括归档明细信息和归档文件操作日志）集合
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveLendInfo/getAllIncludeDetailAndLog")
	public List<ArchiveLendInfoIncludeDetailAndLog> getAllIncludeDetailAndLog(
			@RequestBody ArchiveLendInfo archiveLendInfo) throws ServiceException;

	/**
	 * 归档文件归还
	 * @param archiveInfoDetail 归档明细信息
	 * @param archiveLendInfo 归档文件借阅信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveLendInfo/returnFile")
	public boolean returnFile(@RequestBody Map<String, Object> map) throws ServiceException;
}
