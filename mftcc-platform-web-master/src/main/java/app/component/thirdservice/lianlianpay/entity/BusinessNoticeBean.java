package app.component.thirdservice.lianlianpay.entity;

import java.io.Serializable;

/**
 * 异步
 * 
 * @author lihp
 * @date 2017-3-17 上午10:07:06
 */
public class BusinessNoticeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -821204118733047614L;

	/** 商户编号 . */
	private String oid_partner;

	/** 签名方式 . */
	private String sign_type;

	/** 签名串 . */
	private String sign;

	/** 商户付款流水号即订单号. */
	private String no_order;

	/** 商户付款请求时间. */
	private String dt_order;

	/** 金额. */
	private String money_order;

	/** 连连支付单. */
	private String oid_paybill;

	/** 商户付款请求时间. */
	private String info_order;

	/** 订单状态，付款结果以订单状态为判断依据. */
	private String result_pay;

	/** 清算时间. */
	private String settle_date;
	
	private String pay_type;
	private String bank_code;
	private String no_agree;
	private String id_type;
	private String id_no;
	private String acct_name;
	private String card_no;

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getOid_paybill() {
		return oid_paybill;
	}

	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getResult_pay() {
		return result_pay;
	}

	public void setResult_pay(String result_pay) {
		this.result_pay = result_pay;
	}

	public String getSettle_date() {
		return settle_date;
	}

	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BusinessNoticeBean [oid_partner=");
		builder.append(oid_partner);
		builder.append(", sign_type=");
		builder.append(sign_type);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", no_order=");
		builder.append(no_order);
		builder.append(", dt_order=");
		builder.append(dt_order);
		builder.append(", money_order=");
		builder.append(money_order);
		builder.append(", oid_paybill=");
		builder.append(oid_paybill);
		builder.append(", info_order=");
		builder.append(info_order);
		builder.append(", result_pay=");
		builder.append(result_pay);
		builder.append(", settle_date=");
		builder.append(settle_date);
		builder.append("]");
		return builder.toString();
	}

}
