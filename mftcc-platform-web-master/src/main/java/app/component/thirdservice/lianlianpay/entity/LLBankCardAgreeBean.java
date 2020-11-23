package app.component.thirdservice.lianlianpay.entity;

public class LLBankCardAgreeBean {
    private String            payType;             // 支付方式
    private String            cardNo;              // 银行卡号
    private String            acctName;            // 银行账号姓名
    private String            idType;              // 证件类型
    private String            idNo;                // 证件号码
    /**
     * cusNo
     */
    private String            userId;              // 用户唯一ID，用于签名后更新银行卡信息，传到user_id字段回调使用

    private String riskItem;//此字段填写风控参数，采用 json 串的模式传入，字段名和字段内容彼此对应好，例如：    {"frms_ware_category":"2010","user_info_mercht_userno":"123456","user_info_bind_phone":"13812345678","user_info_dt_register":"20141015165530","user_info_full_name":" 张 三    ","user_info_id_no":"3306821990012121221","user_info_identify_type":"1","user_info_identify_state":"1"}
    
   
    /**
     *  必填 需要传 基本参数中的，<br>
frms_ware_category 商品类目传2010<br>
user_info_mercht_userno 你们平台的用户id<br>
user_info_dt_register 用户在你们平台的注册时间<br>
user_info_bind_phone 用户在你们平台绑定的手机号<br>

实名类参数中的<br>
user_info_identify_state  是否实名认证（）<br>
user_info_identify_type 实名认证方式<br>
user_info_full_name 用户姓名<br>
user_info_id_no  用户身份证号<br>
	 * <br>
     * @return
     * @author MaHao
     * @date 2017-9-27 下午4:49:47
     */
	public String getRiskItem() {
		return riskItem;
	}
	/**
	 * * 需要传 基本参数中的，<br>
frms_ware_category 商品类目传2010<br>
user_info_mercht_userno 你们平台的用户id<br>
user_info_dt_register 用户在你们平台的注册时间
YYYYMMDDH24MISS<br>
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
	 * 此字段填写风控参数，采用 json 串的模式传入，字段名和字段内容彼此对应好，例如：    {"frms_ware_category":"2010","user_info_mercht_userno":"123456","user_info_bind_phone":"13812345678","user_info_dt_register":"20141015165530","user_info_full_name":" 张 三    ","user_info_id_no":"3306821990012121221","user_info_identify_type":"1","user_info_identify_state":"1"}
	 * @param riskItem
	 * @author MaHao
	 * @date 2017-9-26 下午2:59:17
	 */
	public void setRiskItem(String riskItem) {
		this.riskItem = riskItem;
	}
	/**
	 * 分期付签约传 I
	 * @return
	 * @author MaHao
	 * @date 2017-9-26 下午12:58:21
	 */
	public String getPayType() {
		return payType;
	}
	/**
	 * 分期付签约传 I
	 * @param payType
	 * @author MaHao
	 * @date 2017-9-26 下午12:58:26
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}


/**
 * 银行卡号
 * @return
 * @author MaHao
 * @date 2017-9-27 下午5:57:00
 */

	public String getCardNo() {
		return cardNo;
	}
/**
 * 银行卡号
 * @param cardNo
 * @author MaHao
 * @date 2017-9-27 下午5:56:56
 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}


/**
 * 默认为 0
0:身份证
 * @return
 * @author MaHao
 * @date 2017-9-26 上午11:32:37
 */
	public String getIdType() {
		return idType;
	}
/**
 * 默认为 0
0:身份证
 * @param idType
 * @author MaHao
 * @date 2017-9-26 上午11:32:33
 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	

	public String getIdNo() {
		return idNo;
	}
	
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	
}
