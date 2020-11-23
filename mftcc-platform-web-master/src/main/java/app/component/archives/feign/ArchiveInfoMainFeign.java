package  app.component.archives.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveBusinessInfo;
import app.component.archives.entity.ArchiveInfoDetail;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.ArchiveResult;
import app.component.model.entity.MfTemplateBizConfig;
import app.util.toolkit.Ipage;

/**
* Title: ArchiveInfoMainBo.java
* Description:归档主信息
* @author:yudongwei@mftcc.cn
* @Fri Apr 07 13:45:47 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoMainFeign {
	/**
	 * 方法描述： 获取前端展示用的归档文件 龙马法律诉讼结案归档
	 * @param archiveBusinessInfo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author zlhb
	 * @date 2018-7-13 下午5:41:28
	 */
	@RequestMapping(value = "/archiveInfoMain/getArchiveFiles1",produces="application/json;charset=UTF-8")
	public String getArchiveFiles1(@RequestBody ArchiveBusinessInfo archiveBusinessInfo) throws ServiceException;
	@RequestMapping(value = "/archiveInfoMain/insert")
	public void insert(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/insertArchiveInfo")
	public void insertArchiveInfo(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/insertArchiveInfoForCredit")
	public void insertArchiveInfoForCredit(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/insertVoucherOrgin")
	public void insertVoucherOrgin(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/insertVoucherOther")
	public void insertVoucherOther(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/confimArchiveInfo")
	public void confimArchiveInfo(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/confimVoucherOri")
	public void confimVoucherOri(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/confimVoucherOther")
	public void confimVoucherOther(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoMain/delete")
	public void delete(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoMain/update")
	public void update(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/updateArchiveInfo")
	public void updateArchiveInfo(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/updateArchiveInfoForCredit")
	public void updateArchiveInfoForCredit(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/updateBlock")
	public void updateBlock(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoMain/getById")
	public ArchiveInfoMain getById(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;
	
	@RequestMapping(value = "/archiveInfoMain/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 获取授信下的合作银行
	 *
	 * @param ipage
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoMain/findAgeniesAjax")
	public Ipage findAgeniesAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findApplyByPageAjax")
	public Ipage findApplyByPageAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findPaperAjax")
	public Ipage findPaperAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findCreditAndApplyList")
	public List<ArchiveInfoMain> findCreditAndApplyList(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findQueryByPage")
	public Ipage findQueryByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findCusByPageAjax")
	public Ipage findCusByPageAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findAppByPageAjax")
	public Ipage findAppByPageAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findBlockAppByPageAjax")
	public Ipage findBlockAppByPageAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findAppByPageForBorrowAjax")
	public Ipage findAppByPageForBorrowAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/findCreditForBorrowAjax")
	public Ipage findCreditForBorrowAjax(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/archiveInfoMain/getAll")
	public List<ArchiveInfoMain> getAll(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;
	
	/**
	 * 根据业务信息查询要归档的文件
	 * @param archiveBusinessInfo 归档业务信息
	 * @return 查询要归档的文件结果信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/archiveInfoMain/getArchiveFiles",produces="text/html;charset=utf-8")
	public String getArchiveFiles(@RequestBody ArchiveBusinessInfo archiveBusinessInfo) throws Exception;

	/**
	 * 
	 * 方法描述： 获取前端展示用的归档文件
	 * @param archiveBusinessInfo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-4-13 下午5:41:28
	 */
	@RequestMapping(value = "/archiveInfoMain/getArchiveNodes")
	public Map<String, Object> getArchiveNodes(@RequestBody ArchiveBusinessInfo archiveBusinessInfo) throws ServiceException;
	/**
	 * 归档操作
	 * @param isDeleteBusinessDoc 是否删除业务上传的文件
	 * @return 归档操作结果信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/archiveInfoMain/doArchive",produces="text/html;charset=utf-8")
	public String doArchive(@RequestBody Map<String, Object> paramMap,
			@RequestParam("isDeleteBusinessDoc") Boolean isDeleteBusinessDoc) throws Exception;

	/**
	 * 封档操作
	 * @param archiveMainNo 归档编号
	 * @return 操作结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/archiveInfoMain/seal")
	public boolean seal(@RequestBody String archiveMainNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获取前端展示用的封档的文件信息 
	 * @param archiveBusinessInfo
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-4-14 下午3:32:14
	 */
	@RequestMapping(value = "/archiveInfoMain/getSealNodes")
	public Map<String, Object> getSealNodes(@RequestBody ArchiveBusinessInfo archiveBusinessInfo) throws ServiceException;

	/**
	 * 
	 * 方法描述：封档操作 
	 * @param archiveInfoMain
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @return 
	 * @date 2017-4-14 下午3:54:54
	 */
	@RequestMapping(value = "/archiveInfoMain/doSeal")
	public Map<String, Object> doSeal(@RequestBody ArchiveInfoMain archiveInfoMain) throws ServiceException;

}
