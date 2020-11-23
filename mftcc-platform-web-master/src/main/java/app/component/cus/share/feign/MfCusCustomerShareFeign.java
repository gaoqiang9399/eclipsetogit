package app.component.cus.share.feign;

import app.component.cus.share.entity.MfCusCustomerShare;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfCusCustomerShareFeign {

    /**
     *  获取客户共享列表
     * @param mfCusCustomerShare
     * @return
     */
    @RequestMapping(value = "/mfCusCustomerShare/getCusCustomerShareList")
    List<MfCusCustomerShare> getCusCustomerShareList(@RequestBody MfCusCustomerShare mfCusCustomerShare);

    /**
     *  删除客户共享信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/mfCusCustomerShare/deleteById")
    boolean deleteById(@RequestParam(name="id") String id);

    /**
     * 新增客户分享信息
     * @param mfCusCustomerShare
     */
    @RequestMapping(value = "/mfCusCustomerShare/insert")
    void insert(@RequestBody MfCusCustomerShare mfCusCustomerShare);
}
