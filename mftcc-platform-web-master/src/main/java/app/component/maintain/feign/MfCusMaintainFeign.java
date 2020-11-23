package app.component.maintain.feign;


import app.component.cus.entity.MfCusCustomer;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusMaintainFeign {

    /**
     *
     * 方法描述： 更新客户登记信息
     * @param mfCusCustomer
     * @return
     * @throws Exception
     * MfCusCustomer
     * @author 陈迪
     * @date 2019-02-22 下午17:30:02
     */
    @RequestMapping("/mfCusMaintain/updateCusName")
    public MfCusCustomer updateCusName(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;


    @RequestMapping("/mfCusMaintain/updateIdNum")
    public MfCusCustomer updateIdNum(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

    @RequestMapping("/mfCusMaintain/updateCusTel")
    public MfCusCustomer updateCusTel(@RequestBody MfCusCustomer mfCusCustomer) throws Exception;

    @RequestMapping("/mfCusMaintain/findByPage")
    public Ipage findByPage(@RequestBody Ipage ipage) throws  Exception;
}