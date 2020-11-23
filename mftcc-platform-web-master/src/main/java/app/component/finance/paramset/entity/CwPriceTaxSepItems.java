package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwPriceTaxSepItems.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Sep 08 11:04:35 CST 2017
* @version：1.0
**/
public class CwPriceTaxSepItems extends BaseDomain {
	private String traceNo;//唯一编号
	private String finBooks;//账套标识
	private String sepId;//对应cw_price_tax_sep的uuid
	private String accHrt;//科目控制字
	private String dcInd;//借贷方向，1借2贷
	private String ptFlag;//税价状态1税2价
	private String priceTaxAmt;//科目金额，即价税合计
	private String priceAmt;//金额，不含税
	private String taxAmt;//税额
	private String taxRate;//税率
	private String createTime;//创建时间
	private String occDate;//时间戳
	private String opNo;//当前操作员号
	private String opName;//操作员名称
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
	
	private String accNo;
	private String accName;
	

	/**
	 * @return 唯一编号
	 */
	public String getTraceNo() {
	 	return traceNo;
	}
	/**
	 * @设置 唯一编号
	 * @param traceNo
	 */
	public void setTraceNo(String traceNo) {
	 	this.traceNo = traceNo;
	}
	/**
	 * @return 账套标识
	 */
	public String getFinBooks() {
	 	return finBooks;
	}
	/**
	 * @设置 账套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
	 	this.finBooks = finBooks;
	}
	/**
	 * @return 对应cw_price_tax_sep的uuid
	 */
	public String getSepId() {
	 	return sepId;
	}
	/**
	 * @设置 对应cw_price_tax_sep的uuid
	 * @param sepId
	 */
	public void setSepId(String sepId) {
	 	this.sepId = sepId;
	}
	/**
	 * @return 科目控制字
	 */
	public String getAccHrt() {
	 	return accHrt;
	}
	/**
	 * @设置 科目控制字
	 * @param accHrt
	 */
	public void setAccHrt(String accHrt) {
	 	this.accHrt = accHrt;
	}
	/**
	 * @return 借贷方向，1借2贷
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 借贷方向，1借2贷
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 税价状态1税2价
	 */
	public String getPtFlag() {
	 	return ptFlag;
	}
	/**
	 * @设置 税价状态1税2价
	 * @param ptFlag
	 */
	public void setPtFlag(String ptFlag) {
	 	this.ptFlag = ptFlag;
	}
	/**
	 * @return 科目金额，即价税合计
	 */
	public String getPriceTaxAmt() {
	 	return priceTaxAmt;
	}
	/**
	 * @设置 科目金额，即价税合计
	 * @param priceTaxAmt
	 */
	public void setPriceTaxAmt(String priceTaxAmt) {
	 	this.priceTaxAmt = priceTaxAmt;
	}
	/**
	 * @return 金额，不含税
	 */
	public String getPriceAmt() {
	 	return priceAmt;
	}
	/**
	 * @设置 金额，不含税
	 * @param priceAmt
	 */
	public void setPriceAmt(String priceAmt) {
	 	this.priceAmt = priceAmt;
	}
	/**
	 * @return 税额
	 */
	public String getTaxAmt() {
	 	return taxAmt;
	}
	/**
	 * @设置 税额
	 * @param taxAmt
	 */
	public void setTaxAmt(String taxAmt) {
	 	this.taxAmt = taxAmt;
	}
	/**
	 * @return 税率
	 */
	public String getTaxRate() {
	 	return taxRate;
	}
	/**
	 * @设置 税率
	 * @param taxRate
	 */
	public void setTaxRate(String taxRate) {
	 	this.taxRate = taxRate;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 时间戳
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 时间戳
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
	/**
	 * @return 当前操作员号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 当前操作员号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
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
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	
	
}