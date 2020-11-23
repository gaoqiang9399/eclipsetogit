package app.component.frontview.feign;

import app.component.frontview.entity.VwVisitManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface VwVisitManageFeign {

    @RequestMapping(value = "/vwVisitManage/insert")
    public VwVisitManage insert(@RequestBody VwVisitManage vwVisitManage) throws Exception ;

    @RequestMapping(value = "/vwVisitManage/delete")
    public void delete(@RequestBody VwVisitManage vwVisitManage) throws Exception ;

    @RequestMapping(value = "/vwVisitManage/update")
    public VwVisitManage update(@RequestBody VwVisitManage vwVisitManage) throws Exception;

    @RequestMapping(value = "/vwVisitManage/getById")
    public VwVisitManage getById(@RequestBody VwVisitManage vwVisitManage) throws Exception ;

    @RequestMapping(value = "/vwVisitManage/getRecentByPhone")
    public VwVisitManage getRecentByPhone(@RequestBody VwVisitManage vwVisitManage) throws Exception ;

    @RequestMapping(value = "/vwVisitManage/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

}
