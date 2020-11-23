package app.component.interfaces.mobileinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.hzey.entity.MfBusIcloudManage;

@FeignClient("mftcc-platform-factor")
public interface AppMfCusPersBaseInfoFeign{
	
	@RequestMapping(value = "/appMfCusPersBaseInfo/insert")
	public String insert(@RequestBody  String paramJson) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/update")
	public Map<String, Object> update(@RequestBody  MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getByCusNoOrTel")
	public Map<String, Object> getByCusNoOrTel(@RequestBody  String cusNo,@RequestParam("cusTel") String cusTel) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getByCusNoOrTelForWx")
	public Map<String, Object> getByCusNoOrTelForWx(@RequestBody  String cusNo,@RequestParam("cusTel") String cusTel) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/checkByThirdAuthor")
	public Map<String, Object> checkByThirdAuthor(@RequestBody  String cusTel,@RequestParam("openid") String openid,@RequestParam("type") String type) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/insertCusFamilyInfo")
	public Map<String, Object> insertCusFamilyInfo(@RequestParam("tableName")  String tableName,@RequestBody MfCusFamilyInfo mfCusFamilyInfo,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/updateCusFamilyInfo")
	public Map<String, Object> updateCusFamilyInfo( @RequestParam("tableName") String tableName,@RequestBody MfCusFamilyInfo mfCusFamilyInfo) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/selectCusFamilyInfo")
	public Map<String, Object> selectCusFamilyInfo(@RequestBody  String tableName,@RequestParam("cusNo") String cusNo,@RequestParam("cusTel") String cusTel) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/validateCusFamilyInfo")
	public Map<String, Object> validateCusFamilyInfo(@RequestBody  String tableName,@RequestParam("cusNo") String cusNo,@RequestParam("cusTel") String cusTel) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getIcloudInfo")
	public Map<String, Object> getIcloudInfo(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/updateIcloudInfo")
	public Map<String, Object> updateIcloudInfo(@RequestBody  MfBusIcloudManage icloudManage) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getZhimaCredit")
	public Map<String, Object> getZhimaCredit(@RequestBody  String cusNo,@RequestParam("accountType") String accountType) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getCusFamilyInfoById")
	public Map<String, Object> getCusFamilyInfoById(@RequestBody  String id) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getIdCardPicture")
	public Map<String, Object> getIdCardPicture(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/appMfCusPersBaseInfo/getIdCardIsOk")
	public Map<String, Object> getIdCardIsOk(@RequestBody  String cusNo) throws Exception;
	
	

}
