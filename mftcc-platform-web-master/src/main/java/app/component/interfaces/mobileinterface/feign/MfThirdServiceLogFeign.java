package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfThirdServiceLog;
import app.util.toolkit.Ipage;

/**
* Title: MfThirdServiceLogBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Jul 26 14:42:40 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfThirdServiceLogFeign{
	
	@RequestMapping(value = "/mfThirdServiceLog/insert")
	public void insert(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/delete")
	public void delete(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/update")
	public void update(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getById")
	public MfThirdServiceLog getById(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfThirdServiceLog") MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getByCusNoAndMethod")
	public MfThirdServiceLog getByCusNoAndMethod(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getByCusNoAndThird")
	public MfThirdServiceLog getByCusNoAndThird(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getListByCusNoAndMethod")
	public List<MfThirdServiceLog> getListByCusNoAndMethod(@RequestBody  MfThirdServiceLog mfThirdServiceLog) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getAllThirdServiceInfo")
	public Map<String, Object> getAllThirdServiceInfo(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getThirdServiceUrl")
	public Map<String, Object> getThirdServiceUrl(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/checkThirdData")
	public Map<String, Object> checkThirdData(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getBrRuleSpecialList")
	public Map<String, Object> getBrRuleSpecialList(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getBrAccountChangeC")
	public Map<String, Object> getBrAccountChangeC(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getBrRuleApplyLoan")
	public Map<String, Object> getBrRuleApplyLoan(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getFireEyesBlack")
	public Map<String, Object> getFireEyesBlack(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getDhbGetSauronC")
	public Map<String, Object> getDhbGetSauronC(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getMobileVerifyThree")
	public Map<String, Object> getMobileVerifyThree(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getBankCardVerifyFour")
	public Map<String, Object> getBankCardVerifyFour(@RequestBody  String cusNo,@RequestParam("bankCard") String bankCard) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getWangxinReport")
	public Map<String, Object> getWangxinReport(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getOperatorReport")
	public Map<String, Object> getOperatorReport(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getHuLuDatasToken")
	public Map<String, Object> getHuLuDatasToken(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/insertHuLuDatasService")
	public Map<String, Object> insertHuLuDatasService(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/updateHuLuDatasService")
	public Map<String, Object> updateHuLuDatasService(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/saveZhimaCreditScore")
	public Map<String, Object> saveZhimaCreditScore(@RequestBody  String cusNo,@RequestParam("zhimascore") String zhimascore) throws Exception;

	@RequestMapping(value = "/mfThirdServiceLog/getSauronDHB")
	public Map<String, Object> getSauronDHB(@RequestBody  String cusNo) throws Exception;
	
	
	
}
