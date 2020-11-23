package app.component.thirdservice.lianlianpay.entity;
/**
 * 连连实时支付实体bean
 * 这里的属性都是必填的，非必填和公共参数不在此类
 * @author MaHao
 * @date 2017-9-25 下午4:08:17
 */
public class LLRealTimePayBean {
//	paymentRequestBean.setMoney_order("0.02");
//	paymentRequestBean.setCard_no("6216261000000000018");
//	paymentRequestBean.setAcct_name("全渠道");
	// paymentRequestBean.setBank_name("中国平安银行");
//	paymentRequestBean.setInfo_order("test测试10.00?");
//	paymentRequestBean.setFlag_card("0");
//	paymentRequestBean.setMemo("代付");
	// 填写商户自己的接收付款结果回调异步通知
//	paymentRequestBean.setNotify_url("http://10.20.41.35:8080/tradepayapi/receiveNotify.htm");
	/** 商户付款流水号 . */
	private String dtOrder;
	private String noOrder;//
	private String cusNo;//客户号，保存第三方请求信息时要用到，必须
	private String moneyOrder;//付款金额，单位为元，精确到小数点后两位。	如：49.65 元
	private String cardNo;//收款银行账号
	private String acctName;//收款人姓名
	private String infoOrder;//订单描述
	private String flagCard;//对公对私标志0-对私 1-对公
//	private String notifyUrl;//连连支付平台在付款后通知商户服务端的地址
	private String memo;//传递至银行，特殊字符会过滤掉不传递至银行，	单 个 字 符 过 滤 规 则 正 则 表 达 式 ：	[^a-zA-Z0-9\\u4e00-\\u9fa5-_]
	/****对公字段****/
	private String prcptcd;//对公字段，若传，则省市支行可不传，且大额	行号以此为准。
	private String bankCode;//对公字段，见银行编码	对公 bank_code 必传
	private String cityCode ;//对公字段，标准地市编码	26 家银行可不传	详情请看银行列表
	private String brabankName;//对公字段	26 家银行可不传
	
	public String getMoneyOrder() {
		return moneyOrder;
	}
	/**
	 * 付款金额，单位为元，精确到小数点后两位。	如：49.65 元
	 * @param moneyOrder
	 * @author MaHao
	 * @date 2017-9-25 下午4:19:44
	 */
	public void setMoneyOrder(String moneyOrder) {
		this.moneyOrder = moneyOrder;
	}
	public String getCardNo() {
		return cardNo;
	}
	/**
	 * 收款银行账号
	 * @param cardNo
	 * @author MaHao
	 * @date 2017-9-25 下午4:19:57
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAcctName() {
		return acctName;
	}
	/**
	 * 收款人姓名
	 * @param acctName
	 * @author MaHao
	 * @date 2017-9-25 下午4:20:09
	 */
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getInfoOrder() {
		return infoOrder;
	}
	/**
	 * 订单描述
	 * @param infoOrder
	 * @author MaHao
	 * @date 2017-9-25 下午4:20:22
	 */
	public void setInfoOrder(String infoOrder) {
		this.infoOrder = infoOrder;
	}
	public String getFlagCard() {
		return flagCard;
	}
	/**
	 * 对公对私标志0-对私 1-对公 填"0"
	 * @param flagCard
	 * @author MaHao
	 * @date 2017-9-25 下午4:20:39
	 */
	public void setFlagCard(String flagCard) {
		this.flagCard = flagCard;
	}
	public String getMemo() {
		return memo;
	}
	/**
	 * 传递至银行，特殊字符会过滤掉不传递至银行，	单 个 字 符 过 滤 规 则 正 则 表 达 式 ：	[^a-zA-Z0-9\\u4e00-\\u9fa5-_]
	 * @param memo
	 * @author MaHao
	 * @date 2017-9-25 下午4:21:44
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getPrcptcd() {
		return prcptcd;
	}
	/**
	 * 对公字段 
	 * @author MaHao
	 * @date 2017-9-25 下午4:21:53
	 */
	public void setPrcptcd(String prcptcd) {
		this.prcptcd = prcptcd;
	}
	public String getBankCode() {
		return bankCode;
	}
	/**
	 * 对公字段 
	 * @author MaHao
	 * @date 2017-9-25 下午4:21:53
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 对公字段 
	 * @author MaHao
	 * @date 2017-9-25 下午4:21:53
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getBrabankName() {
		return brabankName;
	}
	/**
	 * 对公字段 
	 * @author MaHao
	 * @date 2017-9-25 下午4:21:53
	 */
	public void setBrabankName(String brabankName) {
		this.brabankName = brabankName;
	}
	public String getCusNo() {
		return cusNo;
	}
	/**
	 * 客户号，保存第三方请求信息时要用到，必须传
	 * @param cusNo
	 * @author MaHao
	 * @date 2017-9-25 下午6:03:21
	 */
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	/**
	 * 商户订单编号，唯一能标识该笔订单
	 */
	public String getNoOrder() {
		return noOrder;
	}
	/**
	 * 必填
	 * 商户订单编号，唯一能标识该笔订单，
	 */
	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}
	/**
	 * 使用借据创建时间regTime
	 * 格式：YYYYMMDDH24MISS 14 位数字，	精确到秒
	 * @return
	 * @author MaHao
	 * @date 2017-9-30 上午9:23:49
	 */
	public String getDtOrder() {
		return dtOrder;
	}
	/**
	 * 使用借据创建时间regTime
	 * 格式：YYYYMMDDH24MISS 14 位数字，	精确到秒
	 * @param dtOrder
	 * @author MaHao
	 * @date 2017-9-30 上午9:23:54
	 */
	public void setDtOrder(String dtOrder) {
		this.dtOrder = dtOrder;
	}
	
}
