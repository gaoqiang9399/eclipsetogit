package app.component.frontview.feign;

import app.component.frontview.entity.VwProductManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface VwProductManageFeign {
    @RequestMapping("/VwProductManage/insert")
    public void insert(@RequestBody VwProductManage vwProductManage) throws Exception;
    @RequestMapping("/VwProductManage/delete")
    public void delete(@RequestBody VwProductManage vwProductManage) throws Exception;
    @RequestMapping("/VwProductManage/update")
    public void update(VwProductManage vwProductManage) throws Exception;
    @RequestMapping("/VwProductManage/getById")
    public VwProductManage getById(@RequestBody VwProductManage vwProductManage) throws Exception;
    @RequestMapping("/VwProductManage/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
    @RequestMapping("/VwProductManage/getsyskindinfo")
    public Ipage getsyskindinfo(@RequestBody Ipage ipage) throws Exception;

}
