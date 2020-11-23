/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DrainageInterface.java
 * 包名： app.component.drainageinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-12-8 上午10:58:05
 * @version V1.0
 */ 
package app.component.drainageinterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： DrainageInterface
 * 描述：
 * @author 沈浩兵
 * @date 2017-12-8 上午10:58:05
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface DrainageInterfaceFeign {
	/**
	 * 
	 * 方法描述：放款确认后，新增反馈信息 
	 * @param fincId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-12-8 上午11:05:08
	 */
	@RequestMapping(value = "/drainageInterface/doReturnRecordByLoan")
	public void doReturnRecordByLoan(@RequestBody String fincId) throws Exception;
}
