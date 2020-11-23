package app.component.thirdservice.lianlianpay.entity;

/**
 * 授权申请api bean
 * 
 * @author MaHao
 * @date 2017-9-26 下午2:28:00
 */
public class LLAgreeNoAuthApplyBean {
	private String platform;//  否  String(18)  平台来源能有效区分该交易是从此平台发起，
	private String user_id;//  是  String(16)  商户用户唯一编号 保证唯一
	private String oid_partner;// 是  String(18)  商户编号是商户在连连支付支付平台上开设结
	private String  sign_type;//  是  String  RSA
	private String  sign;// 是  String  RSA 加密签名，见安全签名机制
	private String  api_version;//  是  String(6)  输入当前版本 1.0
	private String repayment_plan;//  是  String  {"repaymentPlan":[{"date":"2010-05-06","	amount":"100"},{"date":"2010-06-06","am	ount":"200"},{"date":"2010-07-06","amou	nt":"300"}]} json 格式字符串	
	private String  repayment_no;//  是  String	
	private String sms_param;//短信参数  
	private String pay_type;  //支付方式 
	private String no_agree;//签约协议号
	private String repayment_state;//终止还款 传 1
	public String getPlatform() {
		return platform;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * 商户用户唯一编号 保证唯一
	 * 使用客户号 必须统一
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:41:33
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * 商户用户唯一编号 保证唯一
	 * 使用客户号 必须统一
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:41:33
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getSign_type() {
		return sign_type;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getApi_version() {
		return api_version;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}
	/**
	 *  是  String  {"repaymentPlan":[{"date":"2010-05-06","	amount":"100"},{"date":"2010-06-06","am	ount":"200"},{"date":"2010-07-06","amou	nt":"300"}]} json 格式字符串
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:46:56
	 */
	public String getRepayment_plan() {
		return repayment_plan;
	}
	/**
	 *  是  jsonArray字符串格式  {"repaymentPlan":[{"date":"2010-05-06","	amount":"100"},{"date":"2010-06-06","am	ount":"200"},{"date":"2010-07-06","amou	nt":"300"}]} json 格式字符串
	 * @param repayment_plan
	 * @author MaHao
	 * @date 2017-9-26 下午2:46:42
	 */
	public void setRepayment_plan(String repayment_plan) {
		this.repayment_plan = repayment_plan;
	}
	/**
	 * 使用的是借据号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:48:01
	 */
	public String getRepayment_no() {
		return repayment_no;
	}
	/**
	 * 使用的是借据号
	 * @param repayment_no
	 * @author MaHao
	 * @date 2017-9-26 下午2:48:17
	 */
	public void setRepayment_no(String repayment_no) {
		this.repayment_no = repayment_no;
	}
	/**
	 * 短信参数，商户名称 与 商户联系方式
{"contract_type":" 融 资 租 赁
","contact_way":"0571-12345678"}
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:48:33
	 */
	public String getSms_param() {
		return sms_param;
	}
	/**
	 * 短信参数，商户名称 与 商户联系方式
{"contract_type":" 融 资 租 赁
","contact_way":"0571-12345678"}
<br>
	 *  否 公共字段 pubconfig中已配置
	 * @param sms_param
	 * @author MaHao
	 * @date 2017-9-26 下午2:48:48
	 */
	public void setSms_param(String sms_param) {
		this.sms_param = sms_param;
	}
	/**
	 * D：认证支付（借记卡）
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:49:23
	 */
	public String getPay_type() {
		return pay_type;
	}
	/**
	 * D：认证支付（借记卡）
	 * @param pay_type
	 * @author MaHao
	 * @date 2017-9-26 下午2:49:29
	 */
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	/**
	 * 签约协议号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午2:49:51
	 */
	public String getNo_agree() {
		return no_agree;
	}
	/**
	 * 银行卡签约时返回的数据
	 * 签约协议号
	 * @param no_agree
	 * @author MaHao
	 * @date 2017-9-26 下午2:49:56
	 */
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
	public String getRepayment_state() {
		return repayment_state;
	}
	/**
	 * 非必填
	 * 终止还款 传 1
	 * @param repayment_state
	 * @author MaHao
	 * @date 2017-11-8 上午9:19:25
	 */
	public void setRepayment_state(String repayment_state) {
		this.repayment_state = repayment_state;
	}
	
	
}