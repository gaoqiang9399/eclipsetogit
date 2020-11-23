/**
 * Copyright (C) DXHM 版权所有
 * 文件名： DisposableDealDataBo.java
 * 包名： app.component.tools.disposable.bo
 * 说明：
 * @author 沈浩兵
 * @date 2018-1-6 下午5:05:53
 * @version V1.0
 */ 
package app.component.tools.disposable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类名： DisposableDealDataBo
 * 描述：
 * @author 沈浩兵
 * @date 2018-1-6 下午5:05:53
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface DisposableDealDataFeign {
	/**
	 * 
	 * 方法描述：  处理合同审批历史、放款审批历史表中审批人员信息老数据
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2018-1-6 下午2:50:15
	 */
	@RequestMapping(value = "/disposableDealData/doApproveHisForPactFinc")
	public void doApproveHisForPactFinc() throws Exception;

	/**
	 * 
	 * 方法描述： 刷流程历史表中的批复利率
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2018-1-8 上午10:00:34
	 */
	@RequestMapping(value = "/disposableDealData/doWkfHisTaskRepayFincRate")
	public void doWkfHisTaskRepayFincRate() throws Exception;
}
