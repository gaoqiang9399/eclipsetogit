package app.component.archivesinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.archives.entity.ArchiveBusinessInfo;
import app.component.archives.entity.ArchiveConfig;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.ArchiveResult;
import app.component.model.entity.MfTemplateBizConfig;

/**
 * 归档文件管理接口
 * @author yudongwei@mftcc.cn
 * @Date 2017-04-06 10:06
 */
@FeignClient("mftcc-platform-factor")
public interface ArchivesInterfaceFeign {
	
	/**
	 * 获取归档管理配置信息
	 * @return 归档管理配置信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/getArchiveConfig")
	public ArchiveConfig getArchiveConfig() throws Exception;
	
	/**
	 * 根据业务信息查询要归档的文件
	 * @param archiveBusinessInfo 归档业务信息
	 * @return 查询要归档的文件结果信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/getArchiveFiles")
	public ArchiveResult getArchiveFiles(@RequestBody ArchiveBusinessInfo archiveBusinessInfo) throws Exception;
	
	/**
	 * 归档操作
	 * @param archiveInfoMain 归档主信息
	 * @param archiveInfoDetailList 归档明细信息集合
	 * @param isDeleteBusinessDoc 是否删除业务上传的文件
	 * @param mfTemplateBizConfigList 电子模板文件集合
	 * @return 归档操作结果信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/archive")
	public ArchiveResult archive(@RequestBody ArchiveInfoMain archiveInfoMain,
			@RequestParam("archiveInfoDetailList") List<ArchiveInfoDetail> archiveInfoDetailList,
			@RequestParam("mfTemplateBizConfigList") List<MfTemplateBizConfig> mfTemplateBizConfigList,
			@RequestParam("isDeleteBusinessDoc") Boolean isDeleteBusinessDoc) throws Exception;
	
	/**
	 * 获取归档主信息
	 * @param archiveInfoMain 归档主信息
	 * @return 归档主信息集合
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/getArchiveInfoMainList")
	public List<ArchiveInfoMain> getArchiveInfoMainList (@RequestBody ArchiveInfoMain archiveInfoMain) throws Exception;
	
	/**
	 * 获取归档明细信息
	 * @param archiveInfoDetail 归档明细信息
	 * @return 归档明细信息集合
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/getArchiveInfoDetailList")
	public List<ArchiveInfoDetail> getArchiveInfoDetailList (@RequestBody ArchiveInfoDetail archiveInfoDetail) throws Exception;
	
	/**
	 * 封档操作
	 * @param archiveMainNo 归档编号
	 * @return 操作结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/archivesInterface/seal")
	public boolean seal(@RequestBody String archiveMainNo) throws Exception;

}
