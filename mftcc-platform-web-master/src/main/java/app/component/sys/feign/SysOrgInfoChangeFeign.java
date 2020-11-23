package app.component.sys.feign;

import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysOrgInfoChange;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface SysOrgInfoChangeFeign {

    @RequestMapping("/sysOrgInfoChange/refreshBusData")
    public Map<String,Object> refreshBusData(@RequestBody SysOrgInfoChange sysOrgInfoChange) throws Exception;
}
