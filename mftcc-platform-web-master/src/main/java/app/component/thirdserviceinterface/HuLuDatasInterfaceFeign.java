package app.component.thirdserviceinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient("mftcc-platform-factor")
public interface HuLuDatasInterfaceFeign {
	/**
	 * 
	* @Title: getToken  
	* @Description: 调用葫芦数据获取申请认证token,提供对外接口  
	* @param @param jsonParam
	* @param @param cusNo
	* @param @return
	* @param @throws Exception    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/huLuDatasInterface/getToken")
	Map<String, Object> getToken(@RequestBody String jsonParam,@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 
	* @Title: insertServicePwd  
	* @Description: 调用葫芦数据提交服务密码,提供对外接口  
	* @param @param jsonParam
	* @param @param cusNo
	* @param @return
	* @param @throws Exception    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/huLuDatasInterface/insertService")
	Map<String, Object> insertService(@RequestBody String jsonParam) throws Exception;
	/**
	 * 
	* @Title: updateService  
	* @Description: 调用葫芦数据重获验证码,提供对外接口  
	* @param @param jsonParam
	* @param @return
	* @param @throws Exception    参数  
	* @return Map<String,Object>    返回类型  
	* @throws
	 */
	@RequestMapping(value = "/huLuDatasInterface/updateService")
	Map<String, Object> updateService(@RequestBody String jsonParam) throws Exception;
}
