package app.component.archives.feign;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoBorrow;
import app.component.pact.repay.entity.MfPreRepayApply;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoBorrowFeign {

    @RequestMapping(value = "/archiveInfoBorrow/insert")
    public ArchiveInfoBorrow insert(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/update")
    public void update (@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/returnArchive")
    public void returnArchive (@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/getById")
    public ArchiveInfoBorrow getById(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/delete")
    public void delete(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/getAll")
    public List<ArchiveInfoBorrow> getAll(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/getBorrowListByAppId")
    public List<ArchiveInfoBorrow> getBorrowListByAppId(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/getBorrowVoucherDetail")
    public List<ArchiveInfoBorrow> getBorrowVoucherDetail(@RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrow/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("borrowId") String borrowId,
                           @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
                           @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody ArchiveInfoBorrow archiveInfoBorrow) throws Exception;
}
