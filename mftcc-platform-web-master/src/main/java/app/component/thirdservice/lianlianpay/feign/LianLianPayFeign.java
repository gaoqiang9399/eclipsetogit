package app.component.thirdservice.lianlianpay.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.thirdservice.lianlianpay.entity.BusinessNoticeBean;
import app.component.thirdservice.lianlianpay.entity.ConfirmPaymentRequestBean;
import app.component.thirdservice.lianlianpay.entity.LLAgreeNoAuthApplyBean;
import app.component.thirdservice.lianlianpay.entity.LLAuthPaymentInfo;
import app.component.thirdservice.lianlianpay.entity.LLBankCardAgreeBean;
import app.component.thirdservice.lianlianpay.entity.LLBankCardPayBean;
import app.component.thirdservice.lianlianpay.entity.LLRealTimePayBean;
import app.component.thirdservice.lianlianpay.entity.OrderQuery;

@FeignClient("mftcc-platform-factor")
public interface LianLianPayFeign {
	/**
	 * 实时支付接口(交易成功不是指放款成功，是调用成功)
	 * 用于放款时调用
	 * @return
	 *  resultMap.put("no_order", noOrder);//商户系统唯一标识该付款的流水号
@RequestParam("		resultMap.put("ret_code"") 		resultMap.put("ret_code",@RequestParam("RetCodeEnum.NO_RESULT_DATA.code")  RetCodeEnum.NO_RESULT_DATA.code);//只有0000时请求成功，付款状态等待回调确认
@RequestParam("		resultMap.put("ret_msg"") 		resultMap.put("ret_msg",@RequestParam("RetCodeEnum.NO_RESULT_DATA.msg")  RetCodeEnum.NO_RESULT_DATA.msg);
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-25 下午3:44:06
	 */
	@RequestMapping(value = "/lianLianPay/realTimePay")
	public Map<String, Object> realTimePay(@RequestBody LLRealTimePayBean llRealTimePayBean) throws Exception;
	/**
	 * 处理实时付款回调业务
	 * <br>
	    if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_SUCCESS.getValue())) {
			// TODO 商户更新订单为成功，处理自己的业务逻辑 放款成功
			//记录成功，处理成功逻辑
		} else if (businessNoticeBean.getResult_pay().equals(PaymentStatusEnum.PAYMENT_FAILURE.getValue())) {
			// TODO 商户更新订单为失败，处理自己的业务逻辑 放款失败
			//记录失败，处理成功逻辑
		} else {
			// TODO 返回订单为退款状态 ，商户可以更新订单为失败或者退款
			// 退款这种情况是极小概率情况下才会发生的，个别银行处理机制是先扣款后再打款给用户时，
			// 才检验卡号姓名信息的有效性，当卡号姓名信息有误发生退款，实际上钱没打款到商户。
			// 这种情况商户代码上也可不做考虑，如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对
			// 退款情况，异步通知会通知两次，先通知成功，后通知退款（极小概率情况下才会发生的）
@RequestParam("			logger.error("连连支付实时付款：返回订单为退款状态") 			logger.error("连连支付实时付款：返回订单为退款状态,@RequestParam("退款这种情况是极小概率情况下才会发生的") 退款这种情况是极小概率情况下才会发生的,@RequestParam("如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对") 如发生用户投诉未收到钱，可直接联系连连客服，连连会跟银行核对,@RequestParam("回调信息："+JSONObject.toJSONString(businessNoticeBean") 回调信息："+JSONObject.toJSONString(businessNoticeBean));
			//记录退款状态，处理失败逻辑
		}
	 * <br>
	 * @param businessNoticeBean
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 上午9:22:27
	 */
	@RequestMapping(value = "/lianLianPay/receiveNotify")
	public BusinessNoticeBean receiveNotify(
			BusinessNoticeBean businessNoticeBean) throws Exception;
	/**
	 * 实时付款确认付款
	 * @param confirmPaymentRequestBean
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-11-14 上午9:34:22
	 */
	@RequestMapping(value = "/lianLianPay/confirmPayment")
	public Map<String,Object> confirmPayment(@RequestBody ConfirmPaymentRequestBean confirmPaymentRequestBean,@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 银行卡前置签约
	 * @param bankCardAgreeBean
	 * @return
	 * 		返回结果 key:<br>
	 *  parmMap.put("req_data", reqJson);
@RequestParam("parmMap.put("url"")         parmMap.put("url",@RequestParam("PaymentConstant.BANK_CARD_SIGN_APPLY_URL")  PaymentConstant.BANK_CARD_SIGN_APPLY_URL);
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 上午10:33:43
	 */
	@RequestMapping(value = "/lianLianPay/signApply")
	public Map<String, Object> signApply(@RequestBody LLBankCardAgreeBean llBankCardAgreeBean) throws Exception;
	/**
	 * 银行卡前置签约 回调处理 
	 * @param status
	 * @param result
	 * @return
	 * 		result结果的map格式
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 下午2:10:19
	 */
	@RequestMapping(value = "/lianLianPay/receiveSignApplyNotify")
	public Map<String, Object> receiveSignApplyNotify(@RequestBody String status,@RequestParam("result")  String result)throws Exception;
	/**
	 *   授权申请 需上传还款计划
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 下午2:02:22
	 */
	@RequestMapping(value = "/lianLianPay/agreeNoAuthApply")
	public Map<String, Object> agreeNoAuthApply(@RequestBody LLAgreeNoAuthApplyBean llAgreeNoAuthApplyBean) throws Exception;
	/**
	 * 银行卡还款扣款接口
	 * @param llBankCardPayBean
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 下午4:32:51
	 */
	@RequestMapping(value = "/lianLianPay/bankCardRepayment")
	public Map<String, Object> bankCardRepayment(@RequestBody LLBankCardPayBean llBankCardPayBean) throws Exception;
	/**
	 * 银行卡还款扣款 回调
	 * @param businessNoticeBean
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-9-26 下午8:34:27
	 */
	@RequestMapping(value = "/lianLianPay/receiveBankCardRepaymentNotify")
	public BusinessNoticeBean receiveBankCardRepaymentNotify(
			BusinessNoticeBean businessNoticeBean)throws Exception;
	/**
	 * 认证付款参数封装
	 * @param llAuthPaymentInfo
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-9 下午4:34:23
	 */
	@RequestMapping(value = "/lianLianPay/authpay")
	public Map<String, Object> authpay(@RequestBody LLAuthPaymentInfo llAuthPaymentInfo)throws Exception;
	/**
	 * 认证付款保存同步回调到第三方日志表中
	 * @param res_data
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-11 下午3:40:34
	 */
	@RequestMapping(value = "/lianLianPay/receiveAuthpayNotify")
	public void receiveAuthpayNotify(@RequestBody String res_data)throws Exception;
	/**
	 * 连连认证订单查询
	 * @param orderQuery
	 * @param cusNo 客户号用于第三方日志表
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-24 上午9:18:45
	 */
	@RequestMapping(value = "/lianLianPay/checkAuthPay")
	public OrderQuery checkAuthPay(@RequestBody OrderQuery orderQuery,@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 连连订单流水序列 目前改为时间+3位随机数
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-10-24 上午10:50:10
	 */
	@RequestMapping(value = "/lianLianPay/getLianLianPlanNoOrderSeq")
	public Long getLianLianPlanNoOrderSeq() throws Exception;
	/**
	 * 更新连连还款计划接口
	 * @param llAgreeNoAuthApplyBean
	 * @return
	 * @throws Exception
	 * @author MaHao
	 * @date 2017-11-7 下午6:30:32
	 */
	@RequestMapping(value = "/lianLianPay/repaymentPlanChange")
	public Map<String, Object> repaymentPlanChange(@RequestBody LLAgreeNoAuthApplyBean llAgreeNoAuthApplyBean) throws Exception;
}
