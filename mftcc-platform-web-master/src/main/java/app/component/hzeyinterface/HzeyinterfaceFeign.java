package  app.component.hzeyinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.hzey.entity.MfBusIcloudManage;



/**
* Title: hzeyinterface.java
* Description:
* @author:刘沛@dhcc.com.cn
* @Mon Jul 24 15:52:30 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface HzeyinterfaceFeign{
	
	@RequestMapping(value = "/hzeyinterface/insert")
	public MfBusIcloudManage getUnbindIcloud() throws Exception;

	@RequestMapping(value = "/hzeyinterface/getIcloudByCifno")
	public MfBusIcloudManage getIcloudByCifno(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/hzeyinterface/updateById")
	public void updateById(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/hzeyinterface/updateByCusNo")
	public void updateByCusNo(@RequestBody  MfBusIcloudManage mfBusIcloudManage) throws Exception;

	@RequestMapping(value = "/hzeyinterface/doIncreaseRestAmt")
	public void doIncreaseRestAmt(@RequestBody  String cusNo,@RequestParam("giveAmt") Double giveAmt) throws Exception;

	@RequestMapping(value = "/hzeyinterface/doDecreaseRestAmt")
	public void doDecreaseRestAmt(@RequestBody  String cusNo,@RequestParam("useAmt") Double useAmt) throws Exception;


	@RequestMapping(value = "/hzeyinterface/doAuthpayForList")
	public Map<String, Object> doAuthpayForList(@RequestParam("cusNo")  String cusNo,@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/hzeyinterface/doSignLianLianBank")
	public Map<String, Object> doSignLianLianBank(@RequestBody  String cusNo,@RequestParam("bankCardNo") String bankCardNo) throws Exception;


}


