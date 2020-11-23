/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MobileServiceBussInterface.java
 * 包名： app.component.interfacesinterface
 * 说明：用钱宝业务相关接口
 * @author 沈浩兵
 * @date 2017-11-09 下午5:41:11
 * @version V1.0
 */ 
package app.component.interfacesinterface;

import java.io.InputStream;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 类名： MobileServiceBussInterface
 * 描述：业务相关接口
 * @author 沈浩兵
 * @date 2017-10-12 下午5:41:11
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServiceYqbBussInterfaceFeign {
	/**
	 * 
	 * 方法描述： 用钱宝app订单保存,业务申请
	 * 包括合同信息、身份信息、个人信息、银行账户信息和合作机构初审报告。
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午2:26:48
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/insertOrderApply")
	public Map<String,Object> insertOrderApply(@RequestBody Map<String,Object> paramMap) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号（业务申请号）或的放款情况
	 * 放款状态0放款申请完成1放款申请拒绝
	 * 审批意见说明
	 * 还款计划列表。放款完成返回还款计划
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/getLoanStateByOrderId")
	public Map<String,Object> getLoanStateByOrderId(@RequestBody String orderId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号（业务申请号）或的放款情况，只返回状态和审批意见说明,不返回还款计划
	 * 放款状态0放款申请完成1放款申请拒绝
	 * 审批意见说明
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-20 下午3:49:11
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/getOnlyLoanStateByOrderId")
	public Map<String,Object> getOnlyLoanStateByOrderId(@RequestBody String orderId) throws Exception;
	/**
	 * 方法描述： 用钱宝将客户的还款信息（本金、利息、费用）推送给业务系统，
	 * 业务系统根据该信息更新业借款信息，并将结果反馈给合作机构。
	 * @param paramData
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 上午11:34:08
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/doRepayByOrderId")
	public Map<String,Object> doRepayByOrderId(@RequestBody String paramData) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号获得签章合同模板文件服务相对路径和文件名
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * /factor/component/model/docword/
	 * 20170706183302598.docx
	 * @author 沈浩兵
	 * @date 2017-10-21 下午3:30:49
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/getSignaturePactTemplateInfo")
	public Map<String,Object> getSignaturePactTemplateInfo(@RequestBody String orderId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据订单号获得还款计划
	 * @param orderId
	 * @return
	 * @throws Exception
	 * List<MfRepayPlan>
	 * @author 沈浩兵
	 * @date 2017-10-31 上午11:05:14
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/getRepayPlanListByOrderId")
	public Map<String, Object> getRepayPlanListByOrderId(@RequestBody String orderId)throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据查询日期返回当日批量扣款报文文件流
	 * @param queryDate
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 下午2:13:31
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/getBatchDebitFileByDay")
	public Map<String,Object> getBatchDebitFileByDay(@RequestBody String queryDate) throws Exception;
	/**
	 * 
	 * 方法描述： 处理引流方推送到系统的业务初审报告文件流写到本地磁盘
	 * @param orderId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-10-31 下午3:16:20
	 */
	@RequestMapping(value = "/mobileServiceYqbBussInterface/doApprovePresentation")
	public Map<String,Object> doApprovePresentation(@RequestBody String orderId,@RequestParam("inputStream") InputStream inputStream) throws Exception;
}

