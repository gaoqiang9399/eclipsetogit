package app.component.nmd.entity;
import app.base.BaseDomain;
/**
* Title: NmdBank.java
* Description:
* @author：renyongxian@dhcc.com.cn
* @Tue Mar 26 10:10:12 GMT 2013
* @version：1.0
**/
public class NmdBank extends BaseDomain {
	private String bankSerno;//金融机构编号
	private String bankName;//金融机构名称
	private String areaCode;//行政区划代码
	private String phone;//联系电话
	private String postcode;//邮政编码
	private String addr;//联系地址
	private String superBankSerno;//上级行号

	/**
	 * @return 金融机构编号
	 */
	 public String getBankSerno() {
	 	return bankSerno;
	 }
	 /**
	 * @设置 金融机构编号
	 * @param bankSerno
	 */
	 public void setBankSerno(String bankSerno) {
	 	this.bankSerno = bankSerno;
	 }
	/**
	 * @return 金融机构名称
	 */
	 public String getBankName() {
	 	return bankName;
	 }
	 /**
	 * @设置 金融机构名称
	 * @param bankName
	 */
	 public void setBankName(String bankName) {
	 	this.bankName = bankName;
	 }
	/**
	 * @return 行政区划代码
	 */
	 public String getAreaCode() {
	 	return areaCode;
	 }
	 /**
	 * @设置 行政区划代码
	 * @param areaCode
	 */
	 public void setAreaCode(String areaCode) {
	 	this.areaCode = areaCode;
	 }
	/**
	 * @return 联系电话
	 */
	 public String getPhone() {
	 	return phone;
	 }
	 /**
	 * @设置 联系电话
	 * @param phone
	 */
	 public void setPhone(String phone) {
	 	this.phone = phone;
	 }
	/**
	 * @return 邮政编码
	 */
	 public String getPostcode() {
	 	return postcode;
	 }
	 /**
	 * @设置 邮政编码
	 * @param postcode
	 */
	 public void setPostcode(String postcode) {
	 	this.postcode = postcode;
	 }
	/**
	 * @return 联系地址
	 */
	 public String getAddr() {
	 	return addr;
	 }
	 /**
	 * @设置 联系地址
	 * @param addr
	 */
	 public void setAddr(String addr) {
	 	this.addr = addr;
	 }
	/**
	 * @return 上级行号
	 */
	 public String getSuperBankSerno() {
	 	return superBankSerno;
	 }
	 /**
	 * @设置 上级行号
	 * @param superBankSerno
	 */
	 public void setSuperBankSerno(String superBankSerno) {
	 	this.superBankSerno = superBankSerno;
	 }
}