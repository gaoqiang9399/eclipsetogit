package app.component.rules.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.rules.entity.CreditBigBean;

/**
 * <p>Title:CreditDataBo.java</p>
 * <p>Description:授信业务申请-规则引擎返回结果处理</p>
 * <p>Company:</p>
 * @author LJW
 * @date 2017-3-13 上午9:39:02
 */
@FeignClient("mftcc-platform-factor")
public interface CreditDataFeign {

	/**
	 * 获取规则引擎返回的结果数据
	 * @author LJW
	 * date 2017-3-13
	 */
	@RequestMapping(value = "/creditData/getCreditDataByRules")
	public CreditBigBean getCreditDataByRules(@RequestBody Map<String, Object> dataMap) throws Exception;
}
