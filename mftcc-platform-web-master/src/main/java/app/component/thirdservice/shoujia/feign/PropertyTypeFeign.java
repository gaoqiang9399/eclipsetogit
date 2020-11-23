package app.component.thirdservice.shoujia.feign;

import app.component.thirdservice.entity.PropertyType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface PropertyTypeFeign {

    @RequestMapping("/propertyType/getAllList")
    List<PropertyType> getAllList(@RequestBody PropertyType propertyType)throws Exception;
}
