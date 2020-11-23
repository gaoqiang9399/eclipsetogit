package app.component.thirdservice.shoujia.feign;

import app.component.thirdservice.entity.PropertyType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfHouseEvalFeign {


    @RequestMapping("/mfHouseEval/getInterfaceResult")
    Map<String,Object> getInterfaceResult(@RequestBody Map<String,Object> parmMap);


    @RequestMapping("/mfHouseEval/reEvalHouseInfo")
    Map<String,Object> reEvalHouseInfo(@RequestParam("evalId") String evalId);
}
