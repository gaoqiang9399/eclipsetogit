package app.component.thirdservice.lianlianpay.entity;


import java.io.Serializable;

/**
* 支付信息bean
* @author guoyx
* @date:Jun 24, 2013 3:25:29 PM
* @version :1.0
*
*/
public class LLAuthPaymentInfo implements Serializable{
    private static final long serialVersionUID = 1L;
    // 商户提交参数
    private String            version;              // 接口版本号
    private String            oid_partner;          // 商户编号
    private String platform;//平台来源标示
    private String            app_request;          // 请求应用标识 1：Android 2：ios 3：WAP
    private String            sign_type;            // 签名方式
    private String            sign;                 // 签名
    private String            bg_color;              // 商户色值
    private String font_color;//支付页面字体颜色
    private String syschnotify_flag;//是否主动同步通知标识  0-点击通知 1-主动通知    默认为 0
    private String            notify_url;           // 服务器异步通知地址
    private String            url_return;           // 支付结束回显url
    
    private String            user_id;
    private String            busi_partner;         // 商户业务类型虚拟商品销售：101001     实物商品销售：109001
    private String            no_order;             // 商户唯一订单号
    private String            dt_order;             // 商户订单时间
    private String            name_goods;           // 商品名称
    private String            info_order;           // 订单描述
    private String            money_order;          // 交易金额 单位为RMB-元
    private String            no_agree;             // 签约协议号
    private String            valid_order;          // 订单有效时间 分钟为单位，默认为10080分钟（7天）
    private String id_type;//证件类型  默认为 0    0:身份证
    private String            id_no;                // 身份证号码
    private String            acct_name;            // 姓名
    // 分账商户名称
    private String            shareing_data;
    private String            risk_item;
    private String            card_no;              // 银行卡号
    private String back_url;//返回修改信息地址 左上角返回按钮，指定返回地址    不传默认 history.go(-1)
    /**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getVersion() {
		return version;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getOid_partner() {
		return oid_partner;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	/**
     * 否
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getPlatform() {
		return platform;
	}
	/**
     * 否
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	/**
	 * 是 默认填 3
	 * 1-Android 2-ios 3-WAP
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:53:49
	 */
	public String getApp_request() {
		return app_request;
	}
	/**
	 * 是 默认填 3
	 * 1-Android 2-ios 3-WAP
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:53:49
	 */
	public void setApp_request(String app_request) {
		this.app_request = app_request;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getSign_type() {
		return sign_type;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	/**
     * 否
     * 计算生成
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getSign() {
		return sign;
	}
	/**
     * 否
     *	计算生成
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setSign(String sign) {
		this.sign = sign;
	}
	/**
	 * 否
	 * 支付页面背景颜色，000000~ffffff默认值为 ff5001
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:55:39
	 */
	public String getBg_color() {
		return bg_color;
	}
	/**
	 * 否
	 * 支付页面背景颜色，000000~ffffff默认值为 ff5001
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:55:39
	 */
	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}
	/**
	 * 否
	 * 支付页面字体颜色
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:56:09
	 */
	public String getFont_color() {
		return font_color;
	}
	/**
	 * 否
	 * 支付页面字体颜色
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:56:09
	 */
	public void setFont_color(String font_color) {
		this.font_color = font_color;
	}
	/**
	 * 否 默认填 0
	 * 0-点击通知 1-主动通知 默认为 0
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:56:41
	 */
	public String getSyschnotify_flag() {
		return syschnotify_flag;
	}
	/**
	 * 否 默认填 0
	 * 0-点击通知 1-主动通知 默认为 0
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午3:56:41
	 */
	public void setSyschnotify_flag(String syschnotify_flag) {
		this.syschnotify_flag = syschnotify_flag;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getNotify_url() {
		return notify_url;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public String getUrl_return() {
		return url_return;
	}
	/**
     * 否
     * pubconfig已配置
     * @return
     * @author MaHao
     * @date 2017-10-9 下午3:52:35
     */
	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}
	/**
	 * 是 填客户号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:00:15
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * 是 填客户号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:00:15
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * 否
	 * pubconfig配置
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:00:59
	 */
	public String getBusi_partner() {
		return busi_partner;
	}
	/**
	 * 否
	 * pubconfig配置
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:00:59
	 */
	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}
	/**
	 * 是
	 * 商户唯一订单号 填还款计划号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:01:40
	 */
	public String getNo_order() {
		return no_order;
	}
	/**
	 * 是
	 * 商户唯一订单号 填还款计划号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:01:40
	 */
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	/**
	 * 是
	 * 商户订单时间 mfbusfincapp.appTime使用借据申请时间
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:02:39
	 */
	public String getDt_order() {
		return dt_order;
	}
	/**
	 * 是
	 * 商户订单时间 mfbusfincapp.appTime使用借据申请时间
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:02:39
	 */
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	/**
	 * 是 
	 * 商品名称
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:13:23
	 */
	public String getName_goods() {
		return name_goods;
	}
	/**
	 * 是 
	 * 商品名称
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:13:23
	 */
	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}
	/**
	 * 否
	 * 订单描述
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:13:59
	 */
	public String getInfo_order() {
		return info_order;
	}
	/**
	 * 否
	 * 订单描述
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:13:59
	 */
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	/**
	 * 是
	 * 交易金额 单位：元。
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:14:55
	 */
	public String getMoney_order() {
		return money_order;
	}
	/**
	 * 是
	 * 交易金额 单位：元。
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:14:55
	 */
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	/**
	 * 必填（文档非必填，但第三方接口存储需要）
	 * 签约协议号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:16:04
	 */
	public String getNo_agree() {
		return no_agree;
	}
	/**
	 * 必填（文档非必填，但第三方接口存储需要）
	 * 签约协议号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:16:04
	 */
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
	/**
	 * 否
	 * 订单有效时间 分钟为单位，默认为 10080 分钟（7 天），
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:18:02
	 */
	public String getValid_order() {
		return valid_order;
	}
	/**
	 * 否
	 * 订单有效时间 分钟为单位，默认为 10080 分钟（7 天），
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:18:02
	 */
	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}
	/**
	 * 是 填0
	 * 默认为 0:身份证
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:19:54
	 */
	public String getId_type() {
		return id_type;
	}
	/**
	 * 是 填0
	 * 默认为 0:身份证
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:19:54
	 */
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	/**
	 * 是
	 * 证件号码 
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:20:29
	 */
	public String getId_no() {
		return id_no;
	}
	/**
	 * 是
	 * 证件号码 
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:20:29
	 */
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	/**
	 * 是
	 * 银行账号姓名
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:20:55
	 */
	public String getAcct_name() {
		return acct_name;
	}
	/**
	 * 是
	 * 银行账号姓名
	 * @param acct_name
	 * @author MaHao
	 * @date 2017-10-9 下午4:20:59
	 */
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	/**
	 * 否
	 * 分帐信息数据
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:21:21
	 */
	public String getShareing_data() {
		return shareing_data;
	}
	/**
	 * 否
	 * 分帐信息数据
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:21:21
	 */
	public void setShareing_data(String shareing_data) {
		this.shareing_data = shareing_data;
	}
	/**
	 * 是<br>
	 * 风险控制参数
	 * （商户上线时，
风控部门会审核
该参数；误传或
漏传可能导致交
易误拦截，影响
正常交易）
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:21:49
	 */
	public String getRisk_item() {
		return risk_item;
	}
	/**
	 * 是<br>
	 * 风险控制参数
	 * （商户上线时，
风控部门会审核
该参数；误传或
漏传可能导致交
易误拦截，影响
正常交易）
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:21:49
	 */
	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}
	/**
	 * 否
	 * 银行卡号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:22:47
	 */
	public String getCard_no() {
		return card_no;
	}
	/**
	 * 否
	 * 银行卡号
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:22:47
	 */
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	/**
	 * 否
	 * 返回修改信息地址
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:23:58
	 */
	public String getBack_url() {
		return back_url;
	}
	/**
	 * 否
	 * 返回修改信息地址
	 * @return
	 * @author MaHao
	 * @date 2017-10-9 下午4:23:58
	 */
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}


}
