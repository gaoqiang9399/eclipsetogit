package app.component.thirdservice.lianlianpay.constant;

public enum PaymentStatusEnum {
	/**
	 *付款申请
	 */
	PAYMENT_APPLY("APPLY", "付款申请"),
	/**
	 *复核申请
	 */
	PAYMENT_CHECK("CHECK", "复核申请"),
	/**
	 *付款处理中
	 */
	PAYMENT_DEALING("PROCESSING", "付款处理中"),
	/**
	 *付款成功
	 */
	PAYMENT_SUCCESS("SUCCESS", "付款成功"),
	/**
	 *付款失败
	 */
	PAYMENT_FAILURE("FAILURE", "付款失败"),
	/**
	 *退款
	 */
	PAYMENT_RETURN("CANCEL", "退款"),
	/**
	 *订单关闭
	 */
	PAYMENT_CLOSED("CLOSED", "订单关闭");

	private String value;

	private String desc;


	private PaymentStatusEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	public static PaymentStatusEnum getPaymentStatusEnumByValue(String value) {
		for (PaymentStatusEnum statusEnum : PaymentStatusEnum.values()) {
			if (statusEnum.getValue().equals(value)) {
				return statusEnum;
			}
		}
		return null;
	}

}
