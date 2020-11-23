package  app.component.archives.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoDetailLog;
import app.component.archives.entity.ArchiveInfoDetailLogIncludeDetailAndLend;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveInfoDetailLogBo.java
* Description:归档文件操作日志Bo
* @author：yudongwei@mftcc.cn
* @Sat Apr 08 15:07:35 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoDetailLogFeign {
	
	@RequestMapping(value = "/archiveInfoDetailLog/insert")
	public void insert(@RequestBody ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetailLog/delete")
	public void delete(@RequestBody ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetailLog/update")
	public void update(@RequestBody ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetailLog/getById")
	public ArchiveInfoDetailLog getById(@RequestBody ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetailLog/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetailLog/getAll")
	public List<ArchiveInfoDetailLog> getAll(@RequestBody ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
	
	/**
	 * 获取归档文件操作日志（包括归档明细信息和归档文件借阅信息）
	 * @param archiveInfoDetailLog 归档文件操作日志
	 * @return 归档文件操作日志（包括归档明细信息和归档文件借阅信息）集合
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetailLog/getAllIncludeDetailAndLend")
	public List<ArchiveInfoDetailLogIncludeDetailAndLend> getAllIncludeDetailAndLend(
			ArchiveInfoDetailLog archiveInfoDetailLog) throws ServiceException;
}
