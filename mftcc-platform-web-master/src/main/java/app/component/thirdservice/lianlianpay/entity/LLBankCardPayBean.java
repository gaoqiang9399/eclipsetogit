package app.component.thirdservice.lianlianpay.entity;


/**
 * 银行卡支付类
 * @author mahao
 *
 */
public class LLBankCardPayBean{

    private String            sign_type;
    private String            sign;
    private String            oid_partner;          // 商户编号
    private String            busi_partner;         // 商户业务类型
    private String            no_order;             // 商户唯一订单号
    private String            dt_order;             // 商户订单时间
    private String            name_goods;           // 商品名称
    private String            info_order;           // 订单描述
    private String            money_order;          // 交易金额 单位元
    private String            notify_url;           // 服务器异步通知地址
    private String            pay_type;             // 支付方式
    private String            valid_order;          // 订单有效期
    private String            shareing_data;         // 分账信息

    private String            platform;             // 平台来源标示
    private String            user_id;              // 商户唯一ID
    private String            api_version;          // 版本标识
    private String            risk_item;            // 风控参数
    private String			  schedule_repayment_date;  // 计划还款日期
    private String            repayment_no;         // 还款计划编号
    private String            no_agree;             // 签约协议号
    
    /**
     * 必填
     * 计划还款日期2010-07-06
     * @return
     * @author MaHao
     * @date 2017-9-26 下午5:04:47
     */
	public String getSchedule_repayment_date() {
		return schedule_repayment_date;
	}
	/**
     * 必填
     * 计划还款日期2010-07-06
     * @return
     * @author MaHao
     * @date 2017-9-26 下午5:04:47
     */
	public void setSchedule_repayment_date(String schedule_repayment_date) {
		this.schedule_repayment_date = schedule_repayment_date;
	}
	/**
	 * 不填,pubconfig统一配置
	 * 交易结算商户编号
	 * 
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午4:46:38
	 */
	public String getOid_partner() {
		return oid_partner;
	}
	/**
	 * 不填,pubconfig统一配置
	 * 商户编号是商户在连连支付支付平台上开设结算到银行账户的商户号码，为 18 位数字，如：201304121000001004
	 * 交易结算商户编号
	 * @param oid_partner
	 * @author MaHao
	 * @date 2017-9-26 下午4:46:42
	 */
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public String getBusi_partner() {
		return busi_partner;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}
	/**
	 * 必填
	 * 商户唯一订单号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:05:48
	 */
	public String getNo_order() {
		return no_order;
	}
	/**
	 * 必填
	 * 商户唯一订单号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:05:48
	 */
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	/**
	 * 必填
	 * 商户订单时间格式：YYYYMMDDH24MISS 14 位数字，精确到秒
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:06:40
	 */
	public String getDt_order() {
		return dt_order;
	}
	/**
	 * 必填
	 * 商户订单时间格式：YYYYMMDDH24MISS 14 位数字，精确到秒
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:06:40
	 */
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	/**
	 * 必填
	 * 商品名称 
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:08:51
	 */
	public String getName_goods() {
		return name_goods;
	}
	/**
	 * 必填
	 * 商品名称 
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:08:51
	 */
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	/**
	 * 非必填
	 * 订单描述
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:09:17
	 */
	public String getInfo_order() {
		return info_order;
	}
	/**
	 * 非必填
	 * 订单描述
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:09:17
	 */
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	/**
	 * 必填
	 * 该笔订单的资金总额，单位为 RMB-元。大于 0 的数字，精确到小数点后两位。如：49.65
	 * 还款金额
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:10:11
	 */
	public String getMoney_order() {
		return money_order;
	}
	/**
	 * 必填
	 * 该笔订单的资金总额，单位为 RMB-元。大于 0 的数字，精确到小数点后两位。如：49.65
	 * 还款金额
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:10:11
	 */
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public String getNotify_url() {
		return notify_url;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	/**
	 * 必填  填D
	 * 支付方式
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:11:37
	 */
	public String getPay_type() {
		return pay_type;
	}
	/**
	 * 必填  填D
	 * 支付方式
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:11:37
	 */
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	/**
	 * 非必填
	 * 订单有效时间
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:48:09
	 */
	public String getValid_order() {
		return valid_order;
	}
	/**
	 * 非必填
	 * 订单有效时间
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:48:09
	 */
	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}
	/**
	 * 必填
	 * 客户号cusNo
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午4:42:41
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * 必填
	 * 客户号cusNo
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午4:42:41
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * 必填
	 * 签约时返回的协议号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:49:10
	 */
	public String getNo_agree() {
		return no_agree;
	}
	/**
	 * 必填
	 * 签约时返回的协议号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:49:10
	 */
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
	/**
	 * 必填<br>
	 * 需要传 基本参数中的，<br>
frms_ware_category 商品类目传2010<br>
user_info_mercht_userno 你们平台的用户id<br>
user_info_dt_register 用户在你们平台的注册时间YYYYMMDDH24MISS<br>
user_info_bind_phone 用户在你们平台绑定的手机号<br>

实名类参数中的<br>
user_info_identify_state  是否实名认证（）<br>
user_info_identify_type 实名认证方式<br>
user_info_full_name 用户姓名<br>
user_info_id_no  用户身份证号<br>
	 * <br>
	 * 此字段填写风控参数，采用 json 串的模式传
入，字段名和字段内容彼此对应好，例如：
{"frms_ware_category":"2010","user_info_
mercht_userno":"123456","user_info_bind
_phone":"13812345678","user_info_dt_reg
ister":"20141015165530","user_info_full_n
ame":" 张 三
","user_info_id_no":"33068219900121212
21","user_info_identify_type":"1","user_inf
o_identify_state":"1"}
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:49:44
	 */
	public String getRisk_item() {
		return risk_item;
	}
	/**
	 * 必填<br>
	 * 需要传 基本参数中的，<br>
frms_ware_category 商品类目传2010<br>
user_info_mercht_userno 你们平台的用户id<br>
user_info_dt_register 用户在你们平台的注册时间YYYYMMDDH24MISS<br>
user_info_bind_phone 用户在你们平台绑定的手机号<br>

实名类参数中的<br>
user_info_identify_state  是否实名认证（1：:是 0：无认证
商户自身是否对用户信息进行实名
认证。默认：0）<br>
user_info_identify_type 实名认证方式是实名认证时，必填
1：银行卡认证
2：现场认证
3：身份证远程认证
4：其它认证<br>
user_info_full_name 用户姓名<br>
user_info_id_no  用户身份证号<br>
	 * <br>
	 * 此字段填写风控参数，采用 json 串的模式传
入，字段名和字段内容彼此对应好，例如：
{"frms_ware_category":"2010","user_info_
mercht_userno":"123456","user_info_bind
_phone":"13812345678","user_info_dt_reg
ister":"20141015165530","user_info_full_n
ame":" 张 三
","user_info_id_no":"33068219900121212
21","user_info_identify_type":"1","user_inf
o_identify_state":"1"}
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:49:44
	 */
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
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
	 * 非必填
	 * 平台来源
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:55:14
	 */
	public String getPlatform() {
		return platform;
	}
	/**
	 * 非必填
	 * 平台来源
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:55:14
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * 必填
	 * 还款计划编号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:56:22
	 */
	public String getRepayment_no() {
		return repayment_no;
	}
	/**
	 * 必填
	 * 还款计划编号
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午5:56:22
	 */
	public void setRepayment_no(String repayment_no) {
		this.repayment_no = repayment_no;
	}

	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */

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
	/**
	 * 否 公共字段 pubconfig中已配置
	 * @author MaHao
	 */
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



   
}
