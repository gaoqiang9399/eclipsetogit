package app.component.pact.feerefund.feign;

import app.component.pact.feerefund.entity.MfBusFeeRefundHis;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Title: MfBusPactBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusFeeRefundHisFeign {

    /**
     * 查询最近一条审批历史
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/mfBusFeeRefundHis/getListOrderByDESC")
    List<MfBusFeeRefundHis> getListOrderByDESC(@RequestBody MfBusFeeRefundHis mfBusFeeRefundHis)throws Exception;
}

