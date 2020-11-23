package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssSupplierContacts.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 01 16:32:54 SGT 2017
* @version：1.0
**/
public class PssSupplierContacts extends BaseDomain {
	private String supplierContactsId;//主键
	private String supplierId;//供应商主表主键
	private Integer sequence;//序号
	private String contactsName;//联系人名称
	private String mobilePhone;//手机号码
	private String telePhone;//固定电话号码
	private String otherContactMode;//QQ/微信/EMAIL
	private String isfirstContactMan;//是否首要联系人
	private String regOpNo;//登记人编号
	private String regOpName;//登记人名称
	private String regBrNo;//登记人机构编号
	private String regBrName;//登记人机构名称
	private String regTime;//登记时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	private String contactsAddress;
	
	public String getContactsAddress() {
		return contactsAddress;
	}
	public void setContactsAddress(String contactsAddress) {
		this.contactsAddress = contactsAddress;
	}
	/**
	 * @return 主键
	 */
	public String getSupplierContactsId() {
	 	return supplierContactsId;
	}
	/**
	 * @设置 主键
	 * @param supplierContactsId
	 */
	public void setSupplierContactsId(String supplierContactsId) {
	 	this.supplierContactsId = supplierContactsId;
	}
	/**
	 * @return 供应商主表主键
	 */
	public String getSupplierId() {
	 	return supplierId;
	}
	/**
	 * @设置 供应商主表主键
	 * @param supplierId
	 */
	public void setSupplierId(String supplierId) {
	 	this.supplierId = supplierId;
	}
	/**
	 * @return 序号
	 */
	public Integer getSequence() {
	 	return sequence;
	}
	/**
	 * @设置 序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
	 	this.sequence = sequence;
	}
	/**
	 * @return 联系人名称
	 */
	public String getContactsName() {
	 	return contactsName;
	}
	/**
	 * @设置 联系人名称
	 * @param contactsName
	 */
	public void setContactsName(String contactsName) {
	 	this.contactsName = contactsName;
	}
	/**
	 * @return 手机号码
	 */
	public String getMobilePhone() {
	 	return mobilePhone;
	}
	/**
	 * @设置 手机号码
	 * @param mobilePhone
	 */
	public void setMobilePhone(String mobilePhone) {
	 	this.mobilePhone = mobilePhone;
	}
	/**
	 * @return 固定电话号码
	 */
	public String getTelePhone() {
	 	return telePhone;
	}
	/**
	 * @设置 固定电话号码
	 * @param telePhone
	 */
	public void setTelePhone(String telePhone) {
	 	this.telePhone = telePhone;
	}
	/**
	 * @return QQ/微信/EMAIL
	 */
	public String getOtherContactMode() {
	 	return otherContactMode;
	}
	/**
	 * @设置 QQ/微信/EMAIL
	 * @param otherContactMode
	 */
	public void setOtherContactMode(String otherContactMode) {
	 	this.otherContactMode = otherContactMode;
	}
	/**
	 * @return 是否首要联系人
	 */
	public String getIsfirstContactMan() {
	 	return isfirstContactMan;
	}
	/**
	 * @设置 是否首要联系人
	 * @param isfirstContactMan
	 */
	public void setIsfirstContactMan(String isfirstContactMan) {
	 	this.isfirstContactMan = isfirstContactMan;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 登记人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 登记人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 登记人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 登记人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 登记人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 登记人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
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
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
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
}