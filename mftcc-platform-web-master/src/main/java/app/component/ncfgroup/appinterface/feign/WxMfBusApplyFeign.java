package app.component.ncfgroup.appinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.entity.MfBusFincApp;
import app.util.toolkit.Ipage;

/**
 * 
 * 处理融资申请的Bo类
 * 
 * @author zhang_dlei
 *
 */
@FeignClient("mftcc-platform-factor")
public interface WxMfBusApplyFeign {

	/**
	 * 移动端插入或更新银行卡账户信息
	 * 
	 * @author MaHao、
	 * @param map
	 *            中的键 phone 手机号 accountName 账户名 存客户名 cusName accountNo 银行卡号 bank 开户行
	 *            bankNumbei 开户行号
	 * @return
	 */
	@RequestMapping("/wxMfBusApply/insertOrUpdateBankInfo")
	public Map<String, Object> insertOrUpdateBankInfo(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 移动端插入或更新银行卡账户信息（无三方验证）
	 * 
	 * @author MaHao、
	 * @param map
	 *            中的键 phone 手机号 accountName 账户名 存客户名 cusName accountNo 银行卡号 bank 开户行
	 *            bankNumbei 开户行号
	 * @return
	 */
	@RequestMapping("/wxMfBusApply/insertOrUpdateBankInfoForNoValidation")
	public Map<String, Object> insertOrUpdateBankInfoForNoValidation(@RequestBody Map<String, Object> map)
			throws Exception;

	/**
	 * 根据手机号获取客户银行卡信息
	 * 
	 * @author MaHao
	 * 
	 * @param ipage
	 * @param cusTel
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getBankCardInfo")
	public Map<String, Object> getBankCardInfo(@RequestBody Ipage ipage, @RequestParam("cusTel") String cusTel,
			@RequestParam("cusNo") String cusNo, @RequestParam("type") String type) throws Exception;

	/**
	 * 更新信用卡信息
	 * 
	 * @param cusTel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/updateCreditCardInfo")
	public Map<String, Object> updateCreditCardInfo(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 更新信用卡信息
	 * 
	 * @param cusTel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getCreditCardInfo")
	public Map<String, Object> getCreditCardInfo(@RequestBody Ipage ipage, @RequestParam("cusTel") String cusTel,
			@RequestParam("cusNo") String cusNo, @RequestParam("type") String type) throws Exception;

	/**
	 * 判断此产品是否在业务申请中和合同履行中
	 * 
	 * @param cusNo
	 * @param kindNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/isInBusApply")
	public Map<String, Object> isInBusApply(@RequestBody String cusNo, @RequestParam("kindNo") String kindNo)
			throws Exception;

	@RequestMapping("/wxMfBusApply/isInBusApplyNew")
	public Map<String, Object> isInBusApplyNew(@RequestBody String cusNo) throws Exception;

	/**
	 * 获取授信总额，剩余额度及在帐金额<br/>
	 * 授信总额及剩余金额从产品号及客户号 取出<br/>
	 * 在账金额取借据表中所有剩余未还本金
	 * 
	 * @param cusTel
	 * @param cusNo
	 * @param pactNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getBusCreditInfo")
	public Map<String, Object> getBusCreditInfo(@RequestBody String cusTel, @RequestParam("cusNo") String cusNo,
			@RequestParam("pactNo") String pactNo) throws Exception;

	/***
	 * 申请提现
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/insertApplyWithdraw")
	public Map<String, Object> insertApplyWithdraw(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest) throws Exception;

	/**
	 * 
	 * @Title: findByPageForFlag @Description: 网信app获取我的借款列表 （应为状态标识变了
	 *         flag：1,，受理中（申请，申请提交，还没审批通过）2，可提现（合同，审批通过，应为没有签约了，所以直接有合同，但没有借据）
	 *         3、放款中（借据，放款申请提交，但是还没有复核） 4、还款中（借据，放款审批否决） 5、还款中（借据，放款复核通过，完结前）
	 *         6、已完结（借据，借据完结）） 7、申请被否决 @param @param ipage @param @param
	 *         cusNo @param @return @param @throws Exception 参数 @return
	 *         Map<String,Object> 返回类型 @throws
	 */
	@RequestMapping("/wxMfBusApply/findByPageForFlag")
	public Map<String, Object> findByPageForFlag(@RequestBody Ipage ipage, @RequestParam("cusNo") String cusNo)
			throws Exception;

	/**
	 * 
	 * @Title: findByPageForFlag @Description: 网信app获取我的借款列表 @param @param
	 *         ipage @param @param cusNo @param @return @param @throws Exception
	 *         参数 @return Map<String,Object> 返回类型 @throws
	 */
	@RequestMapping("/wxMfBusApply/findByPageForFinc")
	public Map<String, Object> findByPageForFinc(@RequestBody Ipage ipage, @RequestParam("cusNo") String cusNo)
			throws Exception;

	/**
	 * 根据借据号获取还款计划列表
	 * 
	 * @param fincId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getMfRepayPlanByFincId")
	public Map<String, Object> getMfRepayPlanByFincId(@RequestBody Ipage ipage, @RequestParam("fincId") String fincId)
			throws Exception;

	/**
	 * 根据版本号判断app是否需要更新
	 * 
	 * @param appVer
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/checkAppVersion")
	public Map<String, Object> checkAppVersion(@RequestBody String appVer,
			@RequestParam("channelSourceNo") String channelSourceNo) throws Exception;

	/**
	 * 根据客户号获取合同列表状态
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getPactListSts")
	public Map<String, Object> getPactListSts(@RequestBody String cusNo) throws Exception;

	/**
	 * 放款是否正在审批
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/doLoanIsApprove")
	public Map<String, Object> doLoanIsApprove(@RequestBody MfBusFincApp mfBusFincApp) throws Exception;

	/**
	 * 根据借据号获取当前期应还金额
	 * 
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfBusApply/getCurTermRepayAmtDetail")
	public Map<String, Object> getCurTermRepayAmtDetail(@RequestBody String fincId) throws Exception;
}
