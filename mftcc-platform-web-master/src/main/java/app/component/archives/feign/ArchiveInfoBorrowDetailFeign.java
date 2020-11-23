package app.component.archives.feign;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoBorrow;
import app.component.archives.entity.ArchiveInfoBorrowDetail;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoBorrowDetailFeign {
    @RequestMapping(value = "/archiveInfoBorrowDetail/getListByArchiveInfoBorrowDetail")
    public List<ArchiveInfoBorrowDetail> getListByArchiveInfoBorrowDetail(@RequestBody ArchiveInfoBorrowDetail archiveInfoBorrowDetail) throws ServiceException;

    @RequestMapping(value = "/archiveInfoBorrowDetail/getById")
    public ArchiveInfoBorrowDetail getById(@RequestBody ArchiveInfoBorrowDetail archiveInfoBorrowDetail) throws ServiceException;
}
