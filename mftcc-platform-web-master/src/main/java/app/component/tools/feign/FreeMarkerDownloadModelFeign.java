package app.component.tools.feign;

import app.base.ServiceException;
import app.component.freeMarker.entity.FreeMarkerDownloadModel;
import app.component.msgconf.entity.PliWarning;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface FreeMarkerDownloadModelFeign {
    @RequestMapping(value = "/freeMarkerDownloadModel/getById")
    public FreeMarkerDownloadModel getById(@RequestBody FreeMarkerDownloadModel freeMarkerDownloadModel)throws ServiceException;
    @RequestMapping(value = "/freeMarkerDownloadModel/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
    @RequestMapping(value = "/freeMarkerDownloadModel/getAll")
    public List<FreeMarkerDownloadModel> getAll(@RequestBody FreeMarkerDownloadModel freeMarkerDownloadModel) throws ServiceException;
    @RequestMapping(value = "/freeMarkerDownloadModel/update")
    public void update(@RequestBody FreeMarkerDownloadModel freeMarkerDownloadModel) throws ServiceException;
    @RequestMapping(value = "/freeMarkerDownloadModel/insert")
    public void insert(@RequestBody FreeMarkerDownloadModel freeMarkerDownloadModel) throws ServiceException;
    @RequestMapping(value = "/freeMarkerDownloadModel/updateFlag")
    public int updateFlag(@RequestBody FreeMarkerDownloadModel freeMarkerDownloadModel) throws ServiceException;

}
