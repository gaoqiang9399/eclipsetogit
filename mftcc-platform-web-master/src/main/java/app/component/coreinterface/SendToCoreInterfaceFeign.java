/**
 * Copyright (C) DXHM 版权所有
 * 文件名： SendToCoreInterface.java
 * 包名： app.component.coreinterface
 * 说明：
 * @author Javelin
 * @date 2017-8-14 下午4:27:09
 * @version V1.0
 */ 
package app.component.coreinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.checkoff.entity.MfBusCheckoffs;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.coreservice.entity.MfHxBusKind;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.pact.entity.MfBusFincApp;

/**
 * 类名： SendToCoreInterface
 * 描述：
 * @author Javelin
 * @date 2017-8-14 下午4:27:09
 *
 *
 */
@FeignClient("mftcc-platform-dhcc")
public interface SendToCoreInterfaceFeign {

	/**
	 * 方法描述：  获取业务对应的核心产品
	 * @param parmMap
	 *	kindNo;//产品编号
		cusType;//客户类型2：个人；1：企业；
		repayType;//还款方式1|2|：按揭；3|4|5|6|7|8|A|：非按揭；
		agrType;//涉农标记1：涉农；2：非农；
		companyScale;//企业规模1|2|：大型，中型；3|4|：小型，微型；
		pledgeType;//押品类型17061309580618115：居住用房地产；17062211213501712：商用房地产；
	 * @return
	 * @throws Exception
	 * MfHxBusKind
	 * @author Javelin
	 * @date 2017-8-31 下午3:54:20
	 */
	@RequestMapping(value = "/sendToCoreInterface/getMfHxBusKindBean")
	public MfHxBusKind getMfHxBusKindBean(@RequestBody Map<String, String> parmMap) throws Exception;
	
	/**
	 * 方法描述：  获取业务对应的核心产品列表
	 * @param parmMap
	 *	kindNo;//产品编号
		cusType;//客户类型2：个人；1：企业；
		repayType;//还款方式1|2|：按揭；3|4|5|6|7|8|A|：非按揭；
		agrType;//涉农标记1：涉农；2：非农；
		companyScale;//企业规模1|2|：大型，中型；3|4|：小型，微型；
		pledgeType;//押品类型17061309580618115：居住用房地产；17062211213501712：商用房地产；
	 * @return
	 * @throws Exception
	 * List<MfHxBusKind>
	 * @author Javelin
	 * @date 2017-8-31 下午7:39:02
	 */
	@RequestMapping(value = "/sendToCoreInterface/getMfHxBusKindList")
	public List<MfHxBusKind> getMfHxBusKindList(@RequestBody Map<String, String> parmMap) throws Exception;
	/**
	 * 方法描述： 信贷存款账号还款账号检查（验证阳光银行卡号）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 上午11:59:17
	 */
	@RequestMapping(value = "/sendToCoreInterface/checkSunBankCardNum")
	public Map<String, String> checkSunBankCardNum (@RequestBody Map<String, String> parmMap,@RequestParam("mfCusBankAccManage")  MfCusBankAccManage mfCusBankAccManage) throws Exception;
	
	/**
	 * 方法描述：账户信息查询（阳光银行卡号信息）
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:18:35
	 */
	@RequestMapping(value = "/sendToCoreInterface/querySunBankCardNumInfo")
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
	@RequestMapping(value = "/sendToCoreInterface/doChargeForThePledge")
	public Map<String, String> doChargeForThePledge (@RequestBody Map<String, String> parmMap,@RequestParam("pledgeBaseInfo")  PledgeBaseInfo pledgeBaseInfo) throws Exception; 
	
	/**
	 * 方法描述： 贷款本金核销
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:28:37
	 */

	@RequestMapping(value = "/sendToCoreInterface/doCancelLoanPrincipal")
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

	@RequestMapping(value = "/sendToCoreInterface/doCancelLoanInterest")
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
	@RequestMapping(value = "/sendToCoreInterface/doChargeRealTimeLoan")
	public Map<String, String> doChargeRealTimeLoan (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusFincApp")  MfBusFincApp mfBusFincApp) throws Exception; 
	
	/**
	 * 方法描述： 实时还款记账
	 * @param parmMap
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-8-12 下午3:37:42
	 */
	@RequestMapping(value = "/sendToCoreInterface/doChargeRealTimeRepay")
	public Map<String, String> doChargeRealTimeRepay (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusFincApp")  MfBusFincApp mfBusFincApp) throws Exception; 
	
	/**
	 * 方法描述： 实时提前还款记账
	 * @param parmMap
	 * @param sys1230
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-8 下午4:04:30
	 */
	@RequestMapping(value = "/sendToCoreInterface/doChargeRealTimeTQRepay")
	public Map<String, String> doChargeRealTimeTQRepay (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusFincApp")  MfBusFincApp mfBusFincApp) throws Exception; 
	
	/**
	 * 方法描述： 实时贷款核销记账
	 * @param parmMap
	 * @param mfBusCheckoffs
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-7 上午11:09:20
	 */
	@RequestMapping(value = "/sendToCoreInterface/doChargeRealTimeCancel")
	public Map<String, String> doChargeRealTimeCancel (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusCheckoffs")  MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	/**
	 * 方法描述： 实时贷款核销收回记账
	 * @param parmMap
	 * @param mfBusCheckoffs
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-8 下午5:01:05
	 */
	@RequestMapping(value = "/sendToCoreInterface/doChargeRealTimeCancelBack")
	public Map<String, String> doChargeRealTimeCancelBack (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusCheckoffs")  MfBusCheckoffs mfBusCheckoffs) throws Exception;
	
	/**
	 * 方法描述： 受托支付转账记账（行内/行外）（调用HxServer）
	 * @param parmMap
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * Map<String,String>
	 * @author Javelin
	 * @date 2017-9-24 上午10:23:08
	 */
	@RequestMapping(value = "/sendToCoreInterface/doChargeHxTransferAcc")
	public Map<String, String> doChargeHxTransferAcc (@RequestBody Map<String, String> parmMap,@RequestParam("mfBusFincApp")  MfBusFincApp mfBusFincApp) throws Exception;
}
