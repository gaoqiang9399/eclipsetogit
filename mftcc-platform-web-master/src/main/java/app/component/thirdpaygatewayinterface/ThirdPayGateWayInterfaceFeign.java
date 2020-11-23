/**
 * Copyright (C) DXHM 版权所有
 * 文件名： ThirdPayInterface.java
 * 包名： app.component.thirdpayinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-11-9 下午2:57:14
 * @version V1.0
 */ 
package app.component.thirdpaygatewayinterface;

import app.component.cus.entity.MfCusBankAccManage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 类名： ThirdPayGateWayInterfaceFeign
 * 描述：
 * @author 郝洋
 * @date 2018-10-31 下午2:57:14
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface ThirdPayGateWayInterfaceFeign {

	/**
	 *
	 * 方法描述：三方放款使用（向网关发送放款请求） 对应单笔
	 * @param  merchantId 商户ID
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author 郝洋
	 * @date 2018年10月31日
	 */
	@RequestMapping(value="/thirdPayGateWayInterfaceConT/getChannelConfigList")
	public Map<String, Object> getChannelConfigList() throws Exception;

	/**
	 *
	 * 方法描述：三方放款使用（向网关发送放款请求） 对应单笔
	 * @param parmMap
	 *  repayplanState 还款计划的获取   0 通过规则获取  1 从相关记录表获取（third_pay_repay_plan） 如果值为 1 需要传入相应的还款计划列表
	 *  fincId 借据号
	 *  putoutAmt 放款金额
	 *  beginDate 开始日期
	 *  endDate 结束日期
	 * 	platId 支付通道
	 *  planListData 还款计划列表    repayplanState 状态为 0 时 不用传 为 1 时需要传入还款计划列表
	 *  planListSize 还款计划条数  String            repayplanState 状态为 0 时 不用传 为 1 时需要传入还款计划条数
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author 郝洋
	 * @date 2018年10月31日
	 */
	@RequestMapping(value="/thirdPayGateWayInterfaceConT/doRepayPlan")
	public Map<String,String> doRepayPlan(@RequestBody Map<String,Object> parmMap) throws Exception;
}
