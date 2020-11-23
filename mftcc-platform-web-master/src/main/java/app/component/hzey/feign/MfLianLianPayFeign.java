package app.component.hzey.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("mftcc-platform-factor")
public interface MfLianLianPayFeign{
	
	@RequestMapping(value = "/mfLianLianPay/insert")
	public List<Map<String, String>> getLianLianPayBanks() throws Exception;

	@RequestMapping(value = "/mfLianLianPay/doSignLianLianBank")
	public Map<String, Object> doSignLianLianBank(@RequestBody  String cusNo,@RequestParam("bankCardNo") String bankCardNo) throws Exception;

	@RequestMapping(value = "/mfLianLianPay/receiveSignApplyNotify")
	public void receiveSignApplyNotify(@RequestBody  String status,@RequestParam("result") String result) throws Exception;

	@RequestMapping(value = "/mfLianLianPay/doAuthpay")
	public Map<String, Object> doAuthpay(@RequestParam("cusNo") String cusNo,@RequestBody  Map<String, String> parmMap) throws Exception;

	@RequestMapping(value = "/mfLianLianPay/doAuthpayForList")
	public Map<String, Object> doAuthpayForList(@RequestParam("cusNo") String cusNo,@RequestBody   Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfLianLianPay/saveReceiveAuthpayNotify")
	public void saveReceiveAuthpayNotify(@RequestBody  String res_data) throws Exception;

	@RequestMapping(value = "/mfLianLianPay/checkAuthpayStatu")
	public Map<String, Object> checkAuthpayStatu(@RequestBody  String planId,@RequestParam("fincId") String fincId) throws Exception;

	
}
