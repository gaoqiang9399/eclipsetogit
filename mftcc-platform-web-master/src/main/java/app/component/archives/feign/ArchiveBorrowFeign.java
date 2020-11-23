package app.component.archives.feign;
import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoMain;
import app.component.archives.entity.MfArchiveBorrow;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: ArchiveBackupInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Apr 08 15:42:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface ArchiveBorrowFeign {
	@RequestMapping(value = "/mfArchiveBorrow/insert")
	public void insert(@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/update")
	public void update (@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/updatear")
	public void updatear (@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/getById")
	public MfArchiveBorrow getById(@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/delete")
	public void delete(@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
    @RequestMapping(value = "/mfArchiveBorrow/getAll")
    public List<MfArchiveBorrow> getAll(@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/submitProcess")
	public MfArchiveBorrow submitProcess(@RequestBody MfArchiveBorrow mfArchiveBorrow) throws ServiceException;
	@RequestMapping(value = "/mfArchiveBorrow/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("preRepayAppId") String preRepayAppId,
						   @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
						   @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody MfPreRepayApply mfPreRepayApply) throws Exception;
	@RequestMapping(value = "/mfArchiveBorrow/getarchive")
	public Ipage getarchive(@RequestBody Ipage ipage) throws ServiceException;
}
