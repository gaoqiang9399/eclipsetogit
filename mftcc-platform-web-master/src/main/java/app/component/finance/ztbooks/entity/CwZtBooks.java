package app.component.finance.ztbooks.entity;
import app.base.BaseDomain;
/**
* Title: CwZtBooks.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 19 09:27:42 CST 2017
* @version：1.0
**/
public class CwZtBooks extends BaseDomain {
	private String bookCode;//帐套代码
	private String bookName;//帐套名称
	private String finBooks;//帐套标识
	private String bookType;//帐套类型（0：系统默认；1：新建）
	private String useFlag;//是否启用Y：是；N：否；   存的是0和1,0禁用，1启用
	private String delSts;//是否删除Y：是；N：否；
	private String useOpNo;//使用人员
	private String useBrNo;//使用部门
	private String opNo;//创建人编号
	private String opName;//创建人名称
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
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
	
	private String operator;//操作人员（条件使用）
	
	private String weeks;//启用期间，数据库没存此字段
	private String oldBookName;//复制账套的名称
	private String initFlag;//是否初始化标识0未初始化，1已初始化

	/**
	 * @return 帐套代码
	 */
	public String getBookCode() {
	 	return bookCode;
	}
	/**
	 * @设置 帐套代码
	 * @param bookCode
	 */
	public void setBookCode(String bookCode) {
	 	this.bookCode = bookCode;
	}
	/**
	 * @return 帐套名称
	 */
	public String getBookName() {
	 	return bookName;
	}
	/**
	 * @设置 帐套名称
	 * @param bookName
	 */
	public void setBookName(String bookName) {
	 	this.bookName = bookName;
	}
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
	 * @return 帐套类型（0：系统默认；1：新建）
	 */
	public String getBookType() {
	 	return bookType;
	}
	/**
	 * @设置 帐套类型（0：系统默认；1：新建）
	 * @param bookType
	 */
	public void setBookType(String bookType) {
	 	this.bookType = bookType;
	}
	/**
	 * @return 是否启用Y：是；N：否；
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用Y：是；N：否；
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 是否删除Y：是；N：否；
	 */
	public String getDelSts() {
	 	return delSts;
	}
	/**
	 * @设置 是否删除Y：是；N：否；
	 * @param delSts
	 */
	public void setDelSts(String delSts) {
	 	this.delSts = delSts;
	}
	/**
	 * @return 创建人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 创建人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 创建人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 创建人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
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

	/**
	 * @return 使用人员
	 */
	public String getUseOpNo() {
		return useOpNo;
	}
	/**
	 * @设置 使用人员
	 * @param useOpNo
	 */
	public void setUseOpNo(String useOpNo) {
		this.useOpNo = useOpNo;
	}

	public String getUseBrNo() {
		return useBrNo;
	}
	public void setUseBrNo(String useBrNo) {
		this.useBrNo = useBrNo;
	}

	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	public String getOldBookName() {
		return oldBookName;
	}
	public void setOldBookName(String oldBookName) {
		this.oldBookName = oldBookName;
	}
	public String getInitFlag() {
		return initFlag;
	}
	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}
	
}