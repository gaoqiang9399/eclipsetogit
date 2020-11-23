package  app.component.archives.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveMergeInfo;
import app.component.archives.entity.ArchiveMergeInfoIncludeDetailAndLog;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveMergeInfoBo.java
 * Description:归档文件合并信息Bo
 * @author:yudongwei@mftcc.cn
* @Thu Apr 20 17:23:25 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveMergeInfoFeign {
	
	@RequestMapping(value = "/archiveMergeInfo/insert")
	public void insert(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveMergeInfo/delete")
	public void delete(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveMergeInfo/update")
	public void update(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveMergeInfo/getById")
	public ArchiveMergeInfo getById(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
	
	@RequestMapping(value = "/archiveMergeInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("archiveMergeInfo") ArchiveMergeInfo archiveMergeInfo) throws ServiceException;

	@RequestMapping(value = "/archiveMergeInfo/getAll")
	public List<ArchiveMergeInfo> getAll(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
	
	/**
	 * 获取归档文件合并信息（包括归档明细信息集合和归档文件操作日志）集合
	 * @param archiveMergeInfo 归档文件合并信息
	 * @return 归档文件合并信息（包括归档明细信息集合和归档文件操作日志）集合
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveMergeInfo/getAllIncludeDetailAndLog")
	public List<ArchiveMergeInfoIncludeDetailAndLog> getAllIncludeDetailAndLog(
			ArchiveMergeInfo archiveMergeInfo) throws ServiceException;

	/**
	 * 合并文件删除
	 * @param archiveMergeInfo 归档文件合并信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveMergeInfo/deleteFile")
	public boolean deleteFile(@RequestBody ArchiveMergeInfo archiveMergeInfo) throws ServiceException;
}
