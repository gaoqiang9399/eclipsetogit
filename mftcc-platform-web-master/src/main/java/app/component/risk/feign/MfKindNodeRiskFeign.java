package  app.component.risk.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
* Title: MfKindNodeInterceptBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 01 14:28:19 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfKindNodeRiskFeign {
	/**
	 * 方法描述： 根据节点设置调用规则引擎获取风险拦截数据
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * Map<String, Object>
	 * 返回结果：{list=[客户评级等级高于B评级, ....], exsitRefused=false}
	 * exsitRefused ： 是否存在业务拒绝  Boolean
	 * list ：业务拒绝具体描述
	 * @author Javelin
	 * @param rulesNo 
	 * @date 2017-7-1 下午2:43:37
	 */
	@RequestMapping(value = "/mfKindNodeRisk/getRiskBeanForNode")
	public Map<String, Object> getRiskBeanForNode (@RequestBody Map<String, String> dataMap) throws Exception;

}
