package app.component.interfaces.appinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusApply;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersBaseInfo;



/**
 *  客户信息管理的Bo类
 * @author zhang_dlei
 */
@FeignClient("mftcc-platform-factor")
public interface AppCusCustomerFeign{

	@RequestMapping(value = "/appCusCustomer/insert")
	public Map<String, Object> insert(@RequestBody  MfCusCustomer mfCusCustomer,@RequestParam("mfCusPersBaseInfo") MfCusPersBaseInfo mfCusPersBaseInfo,@RequestParam("mfCusCorpBaseInfo")
			MfCusCorpBaseInfo mfCusCorpBaseInfo,@RequestParam("busApply") MfBusApply busApply,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appCusCustomer/updateForPersBase")
	public Map<String, Object> updateForPersBase(@RequestBody  MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	@RequestMapping(value = "/appCusCustomer/updateForCorpBase")
	public Map<String, Object> updateForCorpBase(@RequestBody  MfCusCorpBaseInfo mfCusCorpBaseInfo) throws Exception;

	@RequestMapping(value = "/appCusCustomer/insertCusOtherInfo")
	public Map<String, Object> insertCusOtherInfo(@RequestParam("tableName")  String tableName,@RequestParam("jsonObj") String jsonObj,@RequestBody
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/appCusCustomer/getDocInfoList")
	public Map<String, Object> getDocInfoList(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/appCusCustomer/insertDocInfo")
	public Map<String, Object> insertDocInfo(@RequestBody  String ajaxData) throws Exception;

	@RequestMapping(value = "/appCusCustomer/getMfCusTableList")
	public Map<String, Object> getMfCusTableList(@RequestBody  String cusNo) throws Exception;
	
	
	
}
