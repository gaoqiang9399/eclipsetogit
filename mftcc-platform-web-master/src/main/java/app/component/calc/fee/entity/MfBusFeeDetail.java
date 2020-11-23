package app.component.calc.fee.entity;
import app.base.BaseDomain;
/**
* Title: MfBusFeeDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Aug 08 14:39:47 CST 2016
* @version：1.0
**/
public class MfBusFeeDetail extends BaseDomain {
	private String id;//
	private String appId;//申请id
	private String pactId;//合同id
	private String itemNo;//费用项编号
	private String itemName;//费用项名称
	private String takeType;//1：百分比； 2：固额；
	private Double rateScale;//固额值或百分比值
	private String takeNode;//收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	private String standard;//基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	private String cusType;//预留字段客户类型  1：买方  2：卖方
	private String feeType;//收费方式： 0：一次性 1：分期 2：按次
	private Double shouldFee;//应收金额
	private Double realFee;//实收金额
	private String regDate;//登记日期
	private String reviewDate;//复核人员
	private String opNoReg;//登记人
	private String opNameReg;//
	private String opNoReivew;//复核人
	private String opNameReview;//
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//

	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 申请id
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请id
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 合同id
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同id
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 费用项编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 费用项编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 费用项名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 费用项名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 1：百分比； 2：固额；
	 */
	public String getTakeType() {
	 	return takeType;
	}
	/**
	 * @设置 1：百分比； 2：固额；
	 * @param takeType
	 */
	public void setTakeType(String takeType) {
	 	this.takeType = takeType;
	}
	/**
	 * @return 固额值或百分比值
	 */
	public Double getRateScale() {
	 	return rateScale;
	}
	/**
	 * @设置 固额值或百分比值
	 * @param rateScale
	 */
	public void setRateScale(Double rateScale) {
	 	this.rateScale = rateScale;
	}
	/**
	 * @return 收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	 */
	public String getTakeNode() {
	 	return takeNode;
	}
	/**
	 * @设置 收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	 * @param takeNode
	 */
	public void setTakeNode(String takeNode) {
	 	this.takeNode = takeNode;
	}
	/**
	 * @return 基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	 */
	public String getStandard() {
	 	return standard;
	}
	/**
	 * @设置 基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	 * @param standard
	 */
	public void setStandard(String standard) {
	 	this.standard = standard;
	}
	/**
	 * @return 预留字段客户类型  1：买方  2：卖方
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 预留字段客户类型  1：买方  2：卖方
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 收费方式： 0：一次性 1：分期 2：按次
	 */
	public String getFeeType() {
	 	return feeType;
	}
	/**
	 * @设置 收费方式： 0：一次性 1：分期 2：按次
	 * @param feeType
	 */
	public void setFeeType(String feeType) {
	 	this.feeType = feeType;
	}
	/**
	 * @return 应收金额
	 */
	public Double getShouldFee() {
	 	return shouldFee;
	}
	/**
	 * @设置 应收金额
	 * @param shouldFee
	 */
	public void setShouldFee(Double shouldFee) {
	 	this.shouldFee = shouldFee;
	}
	/**
	 * @return 实收金额
	 */
	public Double getRealFee() {
	 	return realFee;
	}
	/**
	 * @设置 实收金额
	 * @param realFee
	 */
	public void setRealFee(Double realFee) {
	 	this.realFee = realFee;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 复核人员
	 */
	public String getReviewDate() {
	 	return reviewDate;
	}
	/**
	 * @设置 复核人员
	 * @param reviewDate
	 */
	public void setReviewDate(String reviewDate) {
	 	this.reviewDate = reviewDate;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNoReg() {
	 	return opNoReg;
	}
	/**
	 * @设置 登记人
	 * @param opNoReg
	 */
	public void setOpNoReg(String opNoReg) {
	 	this.opNoReg = opNoReg;
	}
	/**
	 * @return 
	 */
	public String getOpNameReg() {
	 	return opNameReg;
	}
	/**
	 * @设置 
	 * @param opNameReg
	 */
	public void setOpNameReg(String opNameReg) {
	 	this.opNameReg = opNameReg;
	}
	/**
	 * @return 复核人
	 */
	public String getOpNoReivew() {
	 	return opNoReivew;
	}
	/**
	 * @设置 复核人
	 * @param opNoReivew
	 */
	public void setOpNoReivew(String opNoReivew) {
	 	this.opNoReivew = opNoReivew;
	}
	/**
	 * @return 
	 */
	public String getOpNameReview() {
	 	return opNameReview;
	}
	/**
	 * @设置 
	 * @param opNameReview
	 */
	public void setOpNameReview(String opNameReview) {
	 	this.opNameReview = opNameReview;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
}