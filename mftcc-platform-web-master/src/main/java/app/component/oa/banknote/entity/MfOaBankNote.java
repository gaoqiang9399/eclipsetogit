package app.component.oa.banknote.entity;
import app.base.BaseDomain;
/**
* Title: MfOaBankNote.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 10:05:32 CST 2017
* @version：1.0
**/
public class MfOaBankNote extends BaseDomain {
	private String billId;//票据编号
	private String billBank;//出票行
	private Double billSum;//票据总额
	private String billType;//票据类型1：纸票；2：电票
	private String cusName;//户名
	private String accountNo;//帐号
	private String bank;//开户行
	private String bankNo;//大额行号
	private Double txAmt;//打款金额
	private Double txRate;//打款利率
	private String appSts;//审批状态0： 申请中；1：审批中；2：审批通过；3：已否决；4:已放款
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String remark;//备注
	private String ext1;//ext1
	private String ext2;//ext2
	private String ext3;//ext3
	private String ext4;//ext4
	private String ext5;//ext5
	private String ext6;//ext6
	private String ext7;//ext7
	private String ext8;//ext8
	private String ext9;//ext9
	private String ext10;//ext10

	/**
	 * @return 票据编号
	 */
	public String getBillId() {
	 	return billId;
	}
	/**
	 * @设置 票据编号
	 * @param billId
	 */
	public void setBillId(String billId) {
	 	this.billId = billId;
	}
	/**
	 * @return 出票行
	 */
	public String getBillBank() {
	 	return billBank;
	}
	/**
	 * @设置 出票行
	 * @param billBank
	 */
	public void setBillBank(String billBank) {
	 	this.billBank = billBank;
	}
	/**
	 * @return 票据总额
	 */
	public Double getBillSum() {
	 	return billSum;
	}
	/**
	 * @设置 票据总额
	 * @param billSum
	 */
	public void setBillSum(Double billSum) {
	 	this.billSum = billSum;
	}
	/**
	 * @return 票据类型1：纸票；2：电票
	 */
	public String getBillType() {
	 	return billType;
	}
	/**
	 * @设置 票据类型1：纸票；2：电票
	 * @param billType
	 */
	public void setBillType(String billType) {
	 	this.billType = billType;
	}
	/**
	 * @return 户名
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 户名
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 帐号
	 */
	public String getAccountNo() {
	 	return accountNo;
	}
	/**
	 * @设置 帐号
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
	 	this.accountNo = accountNo;
	}
	/**
	 * @return 开户行
	 */
	public String getBank() {
	 	return bank;
	}
	/**
	 * @设置 开户行
	 * @param bank
	 */
	public void setBank(String bank) {
	 	this.bank = bank;
	}
	/**
	 * @return 大额行号
	 */
	public String getBankNo() {
	 	return bankNo;
	}
	/**
	 * @设置 大额行号
	 * @param bankNo
	 */
	public void setBankNo(String bankNo) {
	 	this.bankNo = bankNo;
	}
	/**
	 * @return 打款金额
	 */
	public Double getTxAmt() {
	 	return txAmt;
	}
	/**
	 * @设置 打款金额
	 * @param txAmt
	 */
	public void setTxAmt(Double txAmt) {
	 	this.txAmt = txAmt;
	}
	/**
	 * @return 打款利率
	 */
	public Double getTxRate() {
	 	return txRate;
	}
	/**
	 * @设置 打款利率
	 * @param txRate
	 */
	public void setTxRate(Double txRate) {
	 	this.txRate = txRate;
	}
	/**
	 * @return 审批状态0： 申请中；1：审批中；2：审批通过；3：已否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 审批状态0： 申请中；1：审批中；2：审批通过；3：已否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return ext1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 ext1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return ext2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 ext2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return ext3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 ext3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return ext4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 ext4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return ext5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 ext5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return ext6
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 ext6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return ext7
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 ext7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return ext8
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 ext8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return ext9
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 ext9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return ext10
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 ext10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
}