package app.component.archives.feign;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoBorrow;
import app.component.archives.entity.ArchiveInfoVoucherReturn;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoVoucherReturnFeign {
    @RequestMapping(value = "/archiveInfoVoucherReturn/getById")
    public ArchiveInfoVoucherReturn getById(@RequestBody ArchiveInfoVoucherReturn archiveInfoVoucherReturn) throws ServiceException;

    @RequestMapping(value = "/archiveInfoVoucherReturn/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/archiveInfoVoucherReturn/insert")
    public void insert(@RequestBody ArchiveInfoVoucherReturn archiveInfoVoucherReturn) throws ServiceException;

    @RequestMapping(value = "/archiveInfoVoucherReturn/update")
    public void update(@RequestBody ArchiveInfoVoucherReturn archiveInfoVoucherReturn) throws ServiceException;

    @RequestMapping(value = "/archiveInfoVoucherReturn/confimTransfer")
    public void confimTransfer(@RequestBody ArchiveInfoVoucherReturn archiveInfoVoucherReturn) throws ServiceException;

    @RequestMapping(value = "/archiveInfoVoucherReturn/doCommit")
    public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("id") String id,
                           @RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition,
                           @RequestParam("opNo") String opNo, @RequestParam("nextUser") String nextUser, @RequestBody ArchiveInfoVoucherReturn archiveInfoVoucherReturn) throws Exception;
}
