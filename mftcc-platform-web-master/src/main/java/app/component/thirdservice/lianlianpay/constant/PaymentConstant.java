package app.component.thirdservice.lianlianpay.constant;

import app.component.thirdservice.util.ThirdServiceUtil;



public class PaymentConstant {

	

	/**确认付款url**/
	public static  String CONFIRM_PAYMENT_URL ;

	/**  商户支付结果查询**/
	public static  String ORDER_QUERY_URL ;

	/** 连连银通公钥（不需替换，这是连连公钥，用于报文加密和连连返回响应报文时验签，不是商户生成的公钥. */
	public static  String PUBLIC_KEY_ONLINE ;//= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";

	/** 商户私钥 商户通过openssl工具生成公私钥，公钥通过商户站上传，私钥用于加签，替换下面的值 . */
	public static  String BUSINESS_PRIVATE_KEY ;//= "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAK5oCtldgp6A0cdjiRjYTJ3fFxBZ55bmFqNxCSICEMp4loS2a0Qm42bov5V3zmmgbhnOJh13dEZU8a1SEeoiYiiKE0F5Pan+RPt329F5YXLfT0h87N3YPmzfx2xiSJa3snmLy0gQHyo/+TfrO59HnY3DuB7aX0U5MkSMQauBiORLAgMBAAECgYEArQiCgwe4eQN7nePN+D1ZPmRA4LMiBt9+5GdYVUpRWF/tjfviTnp6sPYIZgW4X6mQsr+Jp0CFtuW95WSAa5fzY3ulxchf34Hxt1j4h/9IcMAc0qPdB0HTF7M06eEpVLZ5GnbpepStB18HBaGO66kqnwUZ38Hub2EZ8vwN8O8+q1ECQQDaATZ7W9TmPovPhUlerbYEzM2v2IpkhIaziDpwV152bta215fql1iwKINibCta/PYU7spZSLcNpJumHz93OUYJAkEAzM2W2hgujCnZm8trR+c7aY6tlo/8GSRRvFVxnfUOGKYdALXGGYoWE5donH5/2Yfkzzivqar/e/kc+qbFTLCMswJBAL2WRG0vRY0eY7QLM+1UoHC4M0Bzzpbv8bz8AeZk9M+GQNAt2f23tPctpGTZsTKlvtQhfnP7GsaQmpPzpNvoQRECQQCeGmRHT323pKMiG2Jhase50HR/k/345snWi1ufpktQigQ/xRP+KVSroSoYDavjIX5o3oj1gVWjvgc6FL6hWnXzAkEAlyzQsNoFu65uN8Cdc3bHm01H6UHsCdjR5p3KGK3RbAydAbbXInE3003duU/JxWh8P7fVpJohXQc4eh4bIBEhuw==";
	/** 商户号（商户需要替换自己的商户号）. */
	public static  String OID_PARTNER ;//= "201609080000214034";
	//得亨快贷产品商户
	/**得亨快贷商户号**/
	public static  String OID_PARTNER_DEHENGDAI ;
	/** 实时付款api版本. */
	public static  String REAL_TIME_PAY_API_VERSION ;//= "1.0";

	/** 实时付款签名方式. */
	public static  String REAL_TIME_PAY_SIGN_TYPE ;//= "RSA";
	
	/**实时付款回调地址**/
	public static  String REAL_TIME_PAY_NOTIFY_URL;
	/**实时付款申请接口地址**/
	public static String REAL_TIME_PAY_URL;
	/**实时付款结果查询地址 **/
	public static String REAL_TIME_PAY_RESULT_QUERY_URL;
	
	/**银行卡签约授权签名方式**/
	public static  String BANK_CARD_SIGN_APPLY_SIGN_TYPE ;
	/**银行卡签约授权api版本**/
	public static  String BANK_CARD_SIGN_APPLY_API_VERSION ;//= "1.0";
	/**1-Android 2-ios 3-WAP**/
	public static String APP_REQUEST;
	/**银行卡签约授权url**/
	public static String BANK_CARD_SIGN_APPLY_URL;
	/**连连支付签约平台在用户签约成功后或者失败通知商户服务端的地址，注意不可带参数**/
	public static String BANK_CARD_SIGN_APPLY_URL_RETURN;
	/**分期付款授权申请**/
	public static String AGREENO_AUTH_APPLY_API_VERSION;
	/**发送短信模板：商户名称**/
	public static String CONTRACT_TYPE;
	/**发送短信模板：商户联系方式**/
	public static String CONTACT_WAY;
	/** 分期付款授权申请url**/
	public static String AGREENO_AUTH_APPLY_URL;
	/**分期付款银行卡还款扣款api版本**/
	public static String BANK_CARD_REPAYMENT_API_VERSION;
	/**银行卡还款扣款商户业务类型**/
	public static String BANK_CARD_REPAYMENT_BUSI_PARTNER;
	/**银行卡还款扣款url**/
	public static  String BANK_CARD_REPAYMENT_URL ;
	/**银行卡还款扣款异步回调**/
	public static String BANK_CARD_REPAYMENT_NOTIFY_URL;
	/**签名方式**/
	public static  String SIGN_TYPE;
	/**认证付款版本**/
	public static String AUTH_PAY_VERSION;
	/**认证付款同步返回页面url**/
	public static String AUTH_PAY_URL_RETURN;
	/**商户业务类型 **/
	public static String BUSI_PARTNER;
	/**认证付款异步回调**/
	public static String AUTH_PAY_NOTIFY_URL;
	/**认证付款url**/
	public static String AUTH_PAY_URL;
	/**还款计划变更url**/
	public static String REPAYMENTPLAN_CHANGE_URL;
	/**发送短信模板：得亨快贷商户名**/
	public static String CONTRACT_TYPE_DEHENGDAI;
	/**发送短信模板：得亨快贷联系方式**/
	public static String CONTACT_WAY_DEHENGDAI;
	
	public static String BAO_FOO_VERSION;
	public static String BAO_FOO_MEMBER_ID;
	public static String BAO_FOO_TERMINAL_ID;
	public static String BAO_FOO_TXN_TYPE;
	public static String BAO_FOO_DATA_TYPE;
	public static String BAO_FOO_PFX;
	public static String BAO_FOO_CER;
	public static String BAO_FOO_PFX_PWD;
	public static String BAO_FOO_POST_URL;
	static {
		PUBLIC_KEY_ONLINE = ThirdServiceUtil.getValue("LLPAY.public_key_online");
		BUSINESS_PRIVATE_KEY = ThirdServiceUtil.getValue("LLPAY.business_private_key");
		OID_PARTNER = ThirdServiceUtil.getValue("LLPAY.oid_partner");
		OID_PARTNER_DEHENGDAI = ThirdServiceUtil.getValue("LLPAY.oid_partner_dehengdai");
		REAL_TIME_PAY_API_VERSION = ThirdServiceUtil.getValue("LLPAY.real_time_pay_api_version");
		REAL_TIME_PAY_SIGN_TYPE = ThirdServiceUtil.getValue("LLPAY.real_time_pay_sign_type");
		REAL_TIME_PAY_NOTIFY_URL = ThirdServiceUtil.getValue("LLPAY.real_time_pay_notify_url");
		REAL_TIME_PAY_URL = ThirdServiceUtil.getValue("LLPAY.real_time_pay_url");
		REAL_TIME_PAY_RESULT_QUERY_URL = ThirdServiceUtil.getValue("LLPAY.real_time_pay_result_query_url");
		BANK_CARD_SIGN_APPLY_SIGN_TYPE = ThirdServiceUtil.getValue("LLPAY.bank_card_sign_apply_sign_type");
		BANK_CARD_SIGN_APPLY_API_VERSION = ThirdServiceUtil.getValue("LLPAY.bank_card_sign_apply_api_version");
		APP_REQUEST = ThirdServiceUtil.getValue("LLPAY.app_request");
		BANK_CARD_SIGN_APPLY_URL = ThirdServiceUtil.getValue("LLPAY.bank_card_sign_apply_url");
		BANK_CARD_SIGN_APPLY_URL_RETURN = ThirdServiceUtil.getValue("LLPAY.bank_card_sign_apply_url_return");
		AGREENO_AUTH_APPLY_API_VERSION = ThirdServiceUtil.getValue("LLPAY.agreeno_auth_apply_api_version");
		CONTRACT_TYPE = ThirdServiceUtil.getValue("LLPAY.contract_type");
		CONTACT_WAY = ThirdServiceUtil.getValue("LLPAY.contact_way");
		AGREENO_AUTH_APPLY_URL = ThirdServiceUtil.getValue("LLPAY.agreeno_auth_apply_url");
		BANK_CARD_REPAYMENT_API_VERSION = ThirdServiceUtil.getValue("LLPAY.bank_card_repayment_api_version");
		BANK_CARD_REPAYMENT_BUSI_PARTNER = ThirdServiceUtil.getValue("LLPAY.bank_card_repayment_busi_partner");
		BANK_CARD_REPAYMENT_URL = ThirdServiceUtil.getValue("LLPAY.bank_card_repayment_url");
		BANK_CARD_REPAYMENT_NOTIFY_URL = ThirdServiceUtil.getValue("LLPAY.bank_card_repayment_notify_url");
		SIGN_TYPE = ThirdServiceUtil.getValue("LLPAY.sign_type");
		AUTH_PAY_VERSION = ThirdServiceUtil.getValue("LLPAY.auth_pay_version");
		AUTH_PAY_URL_RETURN = ThirdServiceUtil.getValue("LLPAY.auth_pay_url_return");
		BUSI_PARTNER = ThirdServiceUtil.getValue("LLPAY.busi_partner");
		AUTH_PAY_NOTIFY_URL = ThirdServiceUtil.getValue("LLPAY.auth_pay_notify_url");
		AUTH_PAY_URL = ThirdServiceUtil.getValue("LLPAY.auth_pay_url");
		ORDER_QUERY_URL = ThirdServiceUtil.getValue("LLPAY.order_query_url");
		REPAYMENTPLAN_CHANGE_URL = ThirdServiceUtil.getValue("LLPAY.repaymentplan_change_url");
		CONTRACT_TYPE_DEHENGDAI	= ThirdServiceUtil.getValue("LLPAY.contract_type_dehengdai");
		CONTACT_WAY_DEHENGDAI = ThirdServiceUtil.getValue("LLPAY.contact_way_dehengdai");
		CONFIRM_PAYMENT_URL = ThirdServiceUtil.getValue("LLPAY.confirm_payment_url");
		
		BAO_FOO_VERSION = ThirdServiceUtil.getValue("VERSION");
		BAO_FOO_MEMBER_ID = ThirdServiceUtil.getValue("MEMBER_ID");
		BAO_FOO_TERMINAL_ID = ThirdServiceUtil.getValue("TERMINAL_ID");
		BAO_FOO_TXN_TYPE = ThirdServiceUtil.getValue("TXN_TYPE");
		BAO_FOO_DATA_TYPE = ThirdServiceUtil.getValue("DATA_TYPE");
		BAO_FOO_PFX = ThirdServiceUtil.getValue("PFX");
		BAO_FOO_CER = ThirdServiceUtil.getValue("CER");
		BAO_FOO_PFX_PWD = ThirdServiceUtil.getValue("PFX_PWD");
		BAO_FOO_POST_URL = ThirdServiceUtil.getValue("POST_URL");
	}
}
