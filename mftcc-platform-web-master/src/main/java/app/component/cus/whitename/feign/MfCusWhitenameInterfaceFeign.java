package app.component.cus.whitename.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.component.cus.whitename.entity.MfCusWhitename;
import app.component.pact.entity.MfBusFincApp;

/**
 * Created by dark on 2017/9/5.
 */
@FeignClient("mftcc-platform-factor")
public interface MfCusWhitenameInterfaceFeign {
    /**
     *根据身份证号查详情信息
     * @param 
     * 		cusno 客户号
     *      idCardNo 客户身份证
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfCusWhitenameInterface/getByIdNumInterface")
	public MfCusWhitename getByIdNumInterface(@RequestBody MfCusWhitename mfCusWhitename) throws Exception;
	/**
     *校验是否是白名单客户
     * @param 
     * 		cusno 客户号
     *      idNum 客户身份证
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/mfCusWhitenameInterface/checkWhitenameInterface")
	public MfCusWhitename checkWhitenameInterface(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 * 校验白名单客户状态
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusWhitenameInterface/checkWhitenameStateInterface")
	public Map<String,Object> checkWhitenameStateInterface(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;
}
