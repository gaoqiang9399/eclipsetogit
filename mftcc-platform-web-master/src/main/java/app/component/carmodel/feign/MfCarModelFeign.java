package app.component.carmodel.feign;

import app.base.ServiceException;
import app.component.carmodel.entity.MfCarModel;
import app.component.carmodel.entity.MfCarModelDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @创始人 shenhaobing
 * @创建时间 2018/7/20
 * @描述
 */
@FeignClient("mftcc-platform-factor")
public interface MfCarModelFeign {
    @RequestMapping(value = "/mfCarModel/getCarModelListByPage")
    public Ipage getCarModelListByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfCarModel/getCarModelDetailById")
    public MfCarModelDetail getCarModelDetailById(@RequestBody MfCarModelDetail mfCarModelDetail) throws Exception;
}
