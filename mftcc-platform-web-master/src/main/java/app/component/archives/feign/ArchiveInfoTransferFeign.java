package app.component.archives.feign;

import app.base.ServiceException;
import app.component.archives.entity.ArchiveInfoTransfer;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
@FeignClient("mftcc-platform-factor")
public interface ArchiveInfoTransferFeign {
    @RequestMapping(value = "/archiveInfoTransfer/getById")
    public ArchiveInfoTransfer getById(@RequestBody ArchiveInfoTransfer archiveInfoTransfer) throws ServiceException;

    @RequestMapping(value = "/archiveInfoTransfer/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

    @RequestMapping(value = "/archiveInfoTransfer/insert")
    public void insert(@RequestBody ArchiveInfoTransfer archiveInfoTransfer) throws ServiceException;

    @RequestMapping(value = "/archiveInfoTransfer/confimTransfer")
    public void confimTransfer(@RequestBody ArchiveInfoTransfer archiveInfoTransfer) throws ServiceException;
}
