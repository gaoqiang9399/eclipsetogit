package app.component.interfacesinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MobileServicePaymentPwdInterfaceFeign {
	/**
	 * 新增支付密码
	 * @param cusNo
	 * @param payPwd
	 * @param cusName
	 * @param cusTel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServicePaymentPwdInterface/insertPayPwd")
	public Map<String, Object> insertPayPwd(@RequestBody String cusNo,@RequestParam("payPwd") String payPwd,@RequestParam("cusName") String cusName,@RequestParam("cusTel") String cusTel) throws Exception;
	
	/**
	 * 根据客户号来更新密码
	 * @param cusNo
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServicePaymentPwdInterface/updatePayPwd")
	public Map<String, Object> updatePayPwd(@RequestBody String cusNo,@RequestParam("payPwd") String payPwd) throws Exception;
	
	/**
	 * 根据客户号来更新密码
	 * @param cusNo
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServicePaymentPwdInterface/getPayPwdByCusNo")
	public Map<String, Object> getPayPwdByCusNo(@RequestBody String cusNo) throws Exception;

	/**
	 * 根据手机号来更新密码
	 * @param cusTel
	 * @param payPwd
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServicePaymentPwdInterface/getPayPwdByCusTel")
	public Map<String, Object> getPayPwdByCusTel(@RequestBody String cusTel) throws Exception;
}
