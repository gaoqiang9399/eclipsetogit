package app.component.tools.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
@FeignClient("mftcc-platform-factor")
public interface FreeMarkerDownLoadFeign {

    @RequestMapping(value = "/freeMarkerDownLoad/freeMarkerDownLoad")
    public Map<String, Object> freeMarkerDownLoad(@RequestBody Map<String, String> parMap);
}
