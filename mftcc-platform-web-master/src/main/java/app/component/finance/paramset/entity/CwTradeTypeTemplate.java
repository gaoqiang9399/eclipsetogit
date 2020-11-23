package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwTradeTypeTemplate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Aug 26 11:58:09 CST 2017
* @version：1.0
**/
public class CwTradeTypeTemplate extends BaseDomain {
	private String finBooks;//帐套标识
	private String traceNo;//流水号
	private String txCode;//交易代码
	private String jieCount;//借的个数
	private String daiCount;//贷的个数
	private String jieValue;//借的取值，以@符号分割
	private String daiValue;//贷的取值，以@符号分割
	private String jieTradeDesc;//借方交易描述，以@符号隔开
	private String daiTradeDesc;//贷方交易描述，以@符号隔开
	private String ext1;//ext1扩展字段
	private String ext2;//ext2扩展字段
	private String ext3;//ext3扩展字段
	private String ext4;//ext4扩展字段
	private String ext5;//ext5扩展字段
	private String ext6;//ext6扩展字段
	private String ext7;//ext7扩展字段
	private String ext8;//ext8扩展字段
	private String ext9;//ext9扩展字段
	private String ext10;//ext10扩展字段
	private String valName;//取值名称
	private String valId;//取值编号

	/**
	 * @return 帐套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 帐套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 流水号
	 */
	public String getTraceNo() {
	 	return traceNo;
	}
	/**
	 * @设置 流水号
	 * @param traceNo
	 */
	public void setTraceNo(String traceNo) {
	 	this.traceNo = traceNo;
	}
	/**
	 * @return 交易代码
	 */
	public String getTxCode() {
	 	return txCode;
	}
	/**
	 * @设置 交易代码
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
	 	this.txCode = txCode;
	}
	/**
	 * @return 借的个数
	 */
	public String getJieCount() {
	 	return jieCount;
	}
	/**
	 * @设置 借的个数
	 * @param jieCount
	 */
	public void setJieCount(String jieCount) {
	 	this.jieCount = jieCount;
	}
	/**
	 * @return 贷的个数
	 */
	public String getDaiCount() {
	 	return daiCount;
	}
	/**
	 * @设置 贷的个数
	 * @param daiCount
	 */
	public void setDaiCount(String daiCount) {
	 	this.daiCount = daiCount;
	}
	/**
	 * @return 借的取值，以@符号分割
	 */
	public String getJieValue() {
	 	return jieValue;
	}
	/**
	 * @设置 借的取值，以@符号分割
	 * @param jieValue
	 */
	public void setJieValue(String jieValue) {
	 	this.jieValue = jieValue;
	}
	/**
	 * @return 贷的取值，以@符号分割
	 */
	public String getDaiValue() {
	 	return daiValue;
	}
	/**
	 * @设置 贷的取值，以@符号分割
	 * @param daiValue
	 */
	public void setDaiValue(String daiValue) {
	 	this.daiValue = daiValue;
	}
	/**
	 * @return ext1扩展字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 ext1扩展字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return ext2扩展字段
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 ext2扩展字段
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return ext3扩展字段
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 ext3扩展字段
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return ext4扩展字段
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 ext4扩展字段
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return ext5扩展字段
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 ext5扩展字段
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return ext6扩展字段
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 ext6扩展字段
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return ext7扩展字段
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 ext7扩展字段
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return ext8扩展字段
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 ext8扩展字段
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return ext9扩展字段
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 ext9扩展字段
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return ext10扩展字段
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 ext10扩展字段
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getValName() {
		return valName;
	}
	public void setValName(String valName) {
		this.valName = valName;
	}
	public String getValId() {
		return valId;
	}
	public void setValId(String valId) {
		this.valId = valId;
	}
	public String getJieTradeDesc() {
		return jieTradeDesc;
	}
	public void setJieTradeDesc(String jieTradeDesc) {
		this.jieTradeDesc = jieTradeDesc;
	}
	public String getDaiTradeDesc() {
		return daiTradeDesc;
	}
	public void setDaiTradeDesc(String daiTradeDesc) {
		this.daiTradeDesc = daiTradeDesc;
	}
	
	
}