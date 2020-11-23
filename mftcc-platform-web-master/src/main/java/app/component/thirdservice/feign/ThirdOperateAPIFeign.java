package app.component.thirdservice.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor") 
public interface ThirdOperateAPIFeign {
	/**
	 * 请求运营商认证
	 * @param paramMap
	 * 					cusNo:客户号
	 * 					name：姓名
	 * 					idCard：身份证号
	 * 					phone：手机号
	 * 					password：服务密码
	 * @return  
	 * 					错误码含义：00000 成功，交互结束
	 * 										00001 弹出错误信息
	 * 										00002 跳转到验证码页面且弹出提示
	 * 										00003 跳转到验证码页面且不弹出提示 
	 * 										99999 交互失败，弹出错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/thirdOperateAPI/requestOperate")
	Map<String,Object> requestOperate(@RequestBody Map<String,String> paramMap)throws Exception;
	/**
	 * 验证运营商认证（验证码）
	 * @param paramMap
	 * 					cusNo:客户号
	 * 					orderId：业务凭证（业务的唯一标识）
	 * 					code：验证码
	 * @return
	 * 					错误码含义：00000 成功，交互结束
	 * 										00001 弹出错误信息
	 * 										00002 跳转到验证码页面且弹出提示
	 * 										00003 跳转到验证码页面且不弹出提示 
	 * 										99999 交互失败，弹出错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/thirdOperateAPI/verifyOperate")
	Map<String,Object> verifyOperate(@RequestBody Map<String,String> paramMap)throws Exception;
}
