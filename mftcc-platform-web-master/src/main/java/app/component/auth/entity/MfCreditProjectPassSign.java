package app.component.auth.entity;
import app.base.BaseDomain;
/**
* Title: MfCreditProjectPassSign.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Apr 03 10:48:15 CST 2018
* @version：1.0
**/
public class MfCreditProjectPassSign extends BaseDomain {
	private String passSignId;//传签编号
	private String creditAppId;//立项授信编号
	private String passSignBrNo;//传签部门编号
	private String passSignBrName;//传签部门名称
	private String contactsOpNo;//联系人编号
	private String contactsOpName;//联系人姓名
	private String opinionType;//传签意见类型1同意2不同意
	private String passSignReason;//传签意见
	private String passSignDate;//传签日期
	private String passSignTime;//传签时间
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String regDate;//登记日期
	private String lstModDate;//最后修改日期

	/**
	 * @return 传签编号
	 */
	public String getPassSignId() {
	 	return passSignId;
	}
	/**
	 * @设置 传签编号
	 * @param passSignId
	 */
	public void setPassSignId(String passSignId) {
	 	this.passSignId = passSignId;
	}
	/**
	 * @return 立项授信编号
	 */
	public String getCreditAppId() {
	 	return creditAppId;
	}
	/**
	 * @设置 立项授信编号
	 * @param creditAppId
	 */
	public void setCreditAppId(String creditAppId) {
	 	this.creditAppId = creditAppId;
	}
	/**
	 * @return 传签意见类型1同意2不同意
	 */
	public String getOpinionType() {
	 	return opinionType;
	}
	/**
	 * @设置 传签意见类型1同意2不同意
	 * @param opinionType
	 */
	public void setOpinionType(String opinionType) {
	 	this.opinionType = opinionType;
	}
	/**
	 * @return 传签意见
	 */
	public String getPassSignReason() {
	 	return passSignReason;
	}
	/**
	 * @设置 传签意见
	 * @param passSignReason
	 */
	public void setPassSignReason(String passSignReason) {
	 	this.passSignReason = passSignReason;
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
	 * @return 最后修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 最后修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	public String getPassSignBrNo() {
		return passSignBrNo;
	}
	public void setPassSignBrNo(String passSignBrNo) {
		this.passSignBrNo = passSignBrNo;
	}
	public String getPassSignBrName() {
		return passSignBrName;
	}
	public void setPassSignBrName(String passSignBrName) {
		this.passSignBrName = passSignBrName;
	}
	public String getContactsOpNo() {
		return contactsOpNo;
	}
	public void setContactsOpNo(String contactsOpNo) {
		this.contactsOpNo = contactsOpNo;
	}
	public String getContactsOpName() {
		return contactsOpName;
	}
	public void setContactsOpName(String contactsOpName) {
		this.contactsOpName = contactsOpName;
	}
	public String getPassSignDate() {
		return passSignDate;
	}
	public void setPassSignDate(String passSignDate) {
		this.passSignDate = passSignDate;
	}
	public String getPassSignTime() {
		return passSignTime;
	}
	public void setPassSignTime(String passSignTime) {
		this.passSignTime = passSignTime;
	}
}