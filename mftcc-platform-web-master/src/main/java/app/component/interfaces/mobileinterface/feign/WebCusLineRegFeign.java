package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfAccessInfo;
import app.component.interfaces.mobileinterface.entity.VmCifCustomerDTO;
import app.component.interfaces.mobileinterface.entity.WebCusLineReg;
import app.util.toolkit.Ipage;

/**
* Title: WebCusLineRegBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 22 15:30:35 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface WebCusLineRegFeign{
	@RequestMapping(value = "/webCusLineReg/sendMessage")
	public Map<String, Object> sendMessage(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doLogin")
	public Map<String, Object> doLogin(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doLoginNew")
	public Map<String, Object> doLoginNew(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum,@RequestParam("verifyNum")
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doLogout")
	public Map<String, Object> doLogout(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doLoginForWx")
	public Map<String, Object> doLoginForWx(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum,
			@RequestParam("httpServletRequest")	HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/getByOpenid")
	public WebCusLineReg getByOpenid(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doCompleteCustomerInfo2")
	public Map<String, Object> doCompleteCustomerInfo2(@RequestBody  VmCifCustomerDTO cifcus,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest,@RequestParam("verifyNum")
			String verifyNum) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doCompleteCustomerInfoNew")
	public Map<String, Object> doCompleteCustomerInfoNew(@RequestBody  VmCifCustomerDTO cifcus,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest,@RequestParam("verifyNum")
			String verifyNum) throws Exception;

	@RequestMapping(value = "/webCusLineReg/registerCustomer")
	public Map<String, Object> registerCustomer(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("verifyNum") String verifyNum,@RequestParam("type") String type,@RequestParam("cusType")
			String cusType,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest,@RequestParam("flag") String flag) throws Exception;

	@RequestMapping(value = "/webCusLineReg/validateVerifyCode")
	public Map<String, Object> validateVerifyCode(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("verifyNum") String verifyNum);

	@RequestMapping(value = "/webCusLineReg/PClogin")
	public Map<String, Object> PClogin(@RequestBody  Map<String, String> paramMap) throws Exception;

	@RequestMapping(value = "/webCusLineReg/insert")
	public void insert(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/delete")
	public void delete(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/update")
	public void update(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/getById")
	public WebCusLineReg getById(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("webCusLineReg") WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/updateTelPhone")
	public Map<String, Object> updateTelPhone(@RequestBody  String tel,@RequestParam("newTel") String newTel,@RequestParam("verifyNum") String verifyNum) throws Exception;

	@RequestMapping(value = "/webCusLineReg/updateTelPhoneNew")
	public Map<String, Object> updateTelPhoneNew(@RequestBody  String channelSourceNo,@RequestParam("tel") String tel,@RequestParam("newTel") String newTel,@RequestParam("verifyNum") String verifyNum)
			throws Exception;

	@RequestMapping(value = "/webCusLineReg/updateTelPhoneForWx")
	public Map<String, Object> updateTelPhoneForWx(@RequestBody  String tel,@RequestParam("newTel") String newTel,@RequestParam("verifyNum") String verifyNum) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doShareLogin")
	public Map<String, Object> doShareLogin(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum,@RequestParam("cusName") String cusName,@RequestParam("recomTel")
			String recomTel,@RequestParam("random") String random,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doShareLoginNew")
	public Map<String, Object> doShareLoginNew(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("channelSourceNo") String channelSourceNo,@RequestParam("type") String type,@RequestParam("verifyNum")
			String verifyNum,@RequestParam("cusName") String cusName,@RequestParam("recomTel") String recomTel,@RequestParam("random") String random,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/webCusLineReg/doWxShareLogin")
	public Map<String, Object> doWxShareLogin(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("type") String type,@RequestParam("verifyNum") String verifyNum,@RequestParam("cusName")
			String cusName,@RequestParam("agenciesUid") String agenciesUid,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/registerShareCustomer")
	public Map<String, Object> registerShareCustomer(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("verifyNum") String verifyNum,@RequestParam("type") String type,@RequestParam("cusName")
			String cusName,@RequestParam("recomTel") String recomTel,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/registerShareCustomerNew")
	public Map<String, Object> registerShareCustomerNew(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("channelSourceNo") String channelSourceNo,@RequestParam("verifyNum")
			String verifyNum,@RequestParam("type") String type,@RequestParam("cusName") String cusName,@RequestParam("recomTel") String recomTel,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/webCusLineReg/registerWxShareCustomer")
	public Map<String, Object> registerWxShareCustomer(@RequestBody  WebCusLineReg webCusLineReg,@RequestParam("verifyNum") String verifyNum,@RequestParam("type") String type,@RequestParam("cusName")
			String cusName,@RequestParam("agenciesUid") String agenciesUid,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/getByCusNo")
	public WebCusLineReg getByCusNo(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/sendWxMessage")
	public Map<String, Object> sendWxMessage(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/sendWxUpdateMessage")
	public Map<String, Object> sendWxUpdateMessage(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doBindTelPhone")
	public Map<String, Object> doBindTelPhone(@RequestBody  String tel,@RequestParam("verifyNum") String verifyNum,@RequestParam("openid") String openid,@RequestParam("type") String type,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest,@RequestParam("osType") String osType) throws Exception;

	@RequestMapping(value = "/webCusLineReg/doBindTelPhoneNew")
	public Map<String, Object> doBindTelPhoneNew(@RequestBody  String tel,@RequestParam("verifyNum") String verifyNum,@RequestParam("openid") String openid,@RequestParam("type") String type,@RequestParam("osType")
			String osType,@RequestParam("channelSourceNo") String channelSourceNo,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/webCusLineReg/insertDeviceInfo")
	public Map<String, Object> insertDeviceInfo(@RequestBody  MfAccessInfo mfAccessInfo) throws Exception;

	@RequestMapping(value = "/webCusLineReg/addCustomerForNetLoan")
	public Map<String, Object> addCustomerForNetLoan(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	@RequestMapping(value = "/webCusLineReg/updatePhone")
	public void updatePhone(@RequestBody  WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 获得客户注册登录信息列表数据
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception List<WebCusLineReg>
	 * @author 沈浩兵
	 * @date 2018-3-7 下午2:30:26
	 */
	@RequestMapping(value = "/webCusLineReg/getCusLineRegList")
	public List<WebCusLineReg> getCusLineRegList(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

}
