/**
 * Copyright (C)  版权所有
 * 文件名： MfDataCleanInterface.java
 * 包名： app.component.cleaninterface
 * 说明：
 * @author YuShuai
 * @date 2017-12-12 下午7:45:10
 * @version V1.0
 */ 
package app.component.cleaninterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： MfDataCleanInterface
 * 描述：
 * @author YuShuai
 * @date 2017-12-12 下午7:45:10
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfDataCleanInterfaceFeign {
	
	/**
	 * 方法描述： 删除客户信息
	 * @param paramMap
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-12-12 下午7:49:51
	 */
	@RequestMapping(value = "/mfDataCleanInterface/deleteCus")
	public void deleteCus(@RequestBody Map<String, String> paramMap) throws Exception;
}
