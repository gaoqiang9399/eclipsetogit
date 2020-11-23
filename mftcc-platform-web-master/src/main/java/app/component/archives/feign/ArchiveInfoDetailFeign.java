package  app.component.archives.feign;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveLendInfo;
import app.component.archives.entity.ArchiveMergeInfo;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveInfoDetailBo.java
* Description:归档明细信息Bo
* @author:yudongwei@mftcc.cn
* @Fri Apr 07 15:04:12 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoDetailFeign {
	
	@RequestMapping(value = "/archiveInfoDetail/insert")
	public void insert(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/insertPaperAjax")
	public void insertPaperAjax(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/insertPactAjax")
	public void insertPactAjax(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/insertOriginVoucherAjax")
	public void insertOriginVoucherAjax(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetail/delete")
	public void delete(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetail/update")
	public void update(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetail/getById")
	public ArchiveInfoDetail getById(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/findQueryByPage")
	public Ipage findQueryByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/findVoucherQueryByPage")
	public Ipage findVoucherQueryByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/getAll")
	public List<ArchiveInfoDetail> getAll(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/getList")
	public List<ArchiveInfoDetail> getList(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;

	@RequestMapping(value = "/archiveInfoDetail/getListForQuery")
	public List<ArchiveInfoDetail> getListForQuery(@RequestBody ArchiveInfoDetail archiveInfoDetail) throws ServiceException;
	/**
	 * 压缩要下载的文件
	 * @param archiveMainNo 归档编号
	 * @param archiveInfoDetailList 归档明细信息集合
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/compressDownloadFile")
	public boolean compressDownloadFile(@RequestParam("archiveMainNo") String archiveMainNo,
			@RequestBody List<ArchiveInfoDetail> archiveInfoDetailList)
			throws ServiceException;
	
	/**
	 * 获取下载文件输入流
	 * @param archiveMainNo 归档编号
	 * @return 下载文件输入流
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/getDownloadFileInputStream")
	public InputStream getDownloadFileInputStream(@RequestBody String archiveMainNo)
			throws ServiceException;
	
	/**
	 * 合并文件
	 * @param archiveMergeInfo 归档文件合并信息
	 * @param archiveInfoDetailList 归档明细信息集合
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/compressMergeFile")
	public boolean compressMergeFile(@RequestBody ArchiveMergeInfo archiveMergeInfo,
			@RequestParam("archiveInfoDetailList")List<ArchiveInfoDetail> archiveInfoDetailList)
			throws ServiceException;

	/**
	 * 归档文件借阅
	 * @param archiveInfoDetail 归档明细信息
	 * @param archiveLendInfo 归档文件借阅信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/lendFile")
	public boolean lendFile(@RequestBody Map<String, Object> map) throws ServiceException;

	/**
	 * 归档文件删除
	 * @param archiveInfoDetail 归档明细信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/deleteFile")
	public boolean deleteFile(@RequestBody ArchiveInfoDetail archiveInfoDetail)
			throws ServiceException;
	
	/**
	 * 归档文件恢复
	 * @param archiveInfoDetail 归档明细信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/recoverFile")
	public boolean recoverFile(@RequestBody ArchiveInfoDetail archiveInfoDetail)
			throws ServiceException;

	/**
	 * 获取图片字节数组输出流
	 * @param archiveDetailNo 归档明细编号
	 * @return 图片字节数组输出流
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/getImageByteArrayOutputStream")
	public ByteArrayOutputStream getImageByteArrayOutputStream(
			String archiveDetailNo) throws ServiceException;

	/**
	 * 归档文件补充
	 * @param archiveInfoDetail 归档明细信息
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/addFile")
	public boolean addFile(@RequestBody ArchiveInfoDetail archiveInfoDetail)
			throws Exception;

	/**
	 * 根据业务场景编号删除没有保存上传的归档文件
	 * @param docBizNo 业务场景编号
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoDetail/deleteFileByDocBizNo")
	public void deleteFileByDocBizNo(@RequestBody String docBizNo) throws ServiceException;
}
