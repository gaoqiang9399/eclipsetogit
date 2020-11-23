/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SendToCoreService.java
 * 包名： app.component.coreservice.bo
 * 说明：
 * @author Javelin
 * @date 2017-8-12 上午11:27:21
 * @version V1.0
 */ 
package app.component.coreservice.feign;

import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.coreservice.entity.CoreSys1230;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 类名： SendToCoreService
 * 描述：
 * @author Javelin
 * @date 2017-8-12 上午11:27:21
 *
 *
 */
@FeignClient("mftcc-platform-dhcc")
public interface SendToCoreFeign {

	/**
	 * 方法描述： 信贷存款账号还款账号检查（验证阳光银行卡号）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 上午11:59:17
	 */
	@RequestMapping(value = "/sendToCore/checkSunBankCardNum")
	public Map<String, String> checkSunBankCardNum(@RequestBody Map<String, String> parmMap) throws Exception;
	
	/**
	 * 方法描述：账户信息查询（阳光银行卡号信息）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:18:35
	 */
	@RequestMapping(value = "/sendToCore/querySunBankCardNumInfo")
	public Map<String, String> querySunBankCardNumInfo(@RequestBody Map<String, String> parmMap) throws Exception;
	
	
	/**
	 * 方法描述： 抵质押出入库记账
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:25:44
	 */
	@RequestMapping(value = "/sendToCore/doChargeForThePledge")
	public Map<String, String> doChargeForThePledge(@RequestBody Map<String, String> parmMap) throws Exception; 
	
	/**
	 * 方法描述： 贷款本金核销
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:28:37
	 */
	@RequestMapping(value = "/sendToCore/doCancelLoanPrincipal")
	public Map<String, String> doCancelLoanPrincipal(@RequestBody Map<String, String> parmMap) throws Exception; 
	
	/**
	 * 方法描述： 贷款利息核销
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:28:41
	 */
	@RequestMapping(value = "/sendToCore/doCancelLoanInterest")
	public Map<String, String> doCancelLoanInterest(@RequestBody Map<String, String> parmMap) throws Exception; 
	
	/**
	 * 方法描述： 实时放款记账
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:31:51
	 */
	@RequestMapping(value = "/sendToCore/doChargeRealTimeLoan")
	public Map<String, String> doChargeRealTimeLoan(@RequestBody Map<String, String> parmMap,@RequestParam("sys1230")  CoreSys1230 sys1230) throws Exception; 
	
	/**
	 * 方法描述： 实时还款记账
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:37:42
	 */
	@RequestMapping(value = "/sendToCore/doChargeRealTimeRepay")
	public Map<String, String> doChargeRealTimeRepay (@RequestBody Map<String, String> parmMap,@RequestParam("sys1230")  CoreSys1230 sys1230) throws Exception; 
	
	/**
	 * 方法描述： 实时贷款核销记账
	 * @param parmMap
	 * @param sys1230
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-6 下午3:16:35
	 */
	@RequestMapping(value = "/sendToCore/doChargeRealTimeCancel")
	public Map<String, String> doChargeRealTimeCancel (@RequestBody Map<String, String> parmMap,@RequestParam("sys1230")  CoreSys1230 sys1230) throws Exception;
	
	/**
	 * 方法描述： 实时贷款核销收回记账
	 * @param parmMap
	 * @param sys1230
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-8 下午5:18:25
	 */
	@RequestMapping(value = "/sendToCore/doChargeRealTimeCancelBack")
	public Map<String, String> doChargeRealTimeCancelBack (@RequestBody Map<String, String> parmMap,@RequestParam("sys1230")  CoreSys1230 sys1230) throws Exception;
	
	/**
	 * 方法描述： 受托支付转账记账（调用HxServer）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-22 下午3:09:34
	 */
	@RequestMapping(value = "/sendToCore/doChargeHxTransferAcc")
	public Map<String, String> doChargeHxTransferAcc(@RequestBody Map<String, String> parmMap) throws Exception;
	
	/**
	 * 方法描述： 受托支付转账记账（行外）（调用CNAPS2_WS）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-12-7 上午9:10:40
	 */
	@RequestMapping(value = "/sendToCore/doChargeRemittedTransferBig")
	public Map<String, String> doChargeRemittedTransferBig(@RequestBody Map<String, String> parmMap) throws Exception;

	/**
	 * 调用核心6232接口，查询存单信息
	 *
	 * @param sys30  存单号
	 * @param sys96a 交易账户
	 * @return
	 */
	@RequestMapping(value = "/sendToCore/call6232")
	Map<String, Object> call6232(@RequestParam("sys30") String sys30, @RequestParam("sys96a") String sys96a);

	/**
	 * 发送短信
	 *
	 * @param cellphone 手机号
	 * @param dxmsg     短信内容
	 * @return
	 */
	@RequestMapping("/sendToCore/call6001")
	public Map<String, Object> call6001(@RequestParam("cellphone") String cellphone, @RequestParam("dxmsg") String dxmsg);
}
