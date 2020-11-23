package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwJitiItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Aug 25 11:52:29 CST 2017
* @version：1.0
**/
public class CwJitiItem extends BaseDomain {
	private String finBooks;//帐套标识
	private String jtItemId;//计提明细表唯一id
	private String jtId;//关联计提cw_jiti
	private String jtTypeSort;//序号0-全部，1-正常，2-关注，3-次级，4-可疑，5，损失
	private String jtItemBal;//贷款余额
	private String jtItemPre;//贷款提取比例
	private String jtItemJKm;//借方科目控制字
	private String jtItemDKm;//贷方科目控制字
	private String jtItemDKmBal;//贷方科目余额
	private String jtItemJtBal;//计提余额
	private String jtItemFlag;//计提状态
	private String jtItemJKmBal;//借方科目余额
	private String jtItemResutBal;//计提结算结果
	
	private String upjtAmt;//上次计提金额
	private String upchaAmt;//差值
	private String createDate;//创建时间
	private String occDate;//时间戳
	
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

	private String yunsuan1;//乘
	private String yunsuan2;//等
	
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
	 * @return 计提明细表唯一id
	 */
	public String getJtItemId() {
	 	return jtItemId;
	}
	/**
	 * @设置 计提明细表唯一id
	 * @param jtItemId
	 */
	public void setJtItemId(String jtItemId) {
	 	this.jtItemId = jtItemId;
	}
	/**
	 * @return 关联计提cw_jiti
	 */
	public String getJtId() {
	 	return jtId;
	}
	/**
	 * @设置 关联计提cw_jiti
	 * @param jtId
	 */
	public void setJtId(String jtId) {
	 	this.jtId = jtId;
	}
	/**
	 * @return 序号0-全部，1-正常，2-关注，3-次级，4-可疑，5，损失
	 */
	public String getJtTypeSort() {
	 	return jtTypeSort;
	}
	/**
	 * @设置 序号0-全部，1-正常，2-关注，3-次级，4-可疑，5，损失
	 * @param jtTypeSort
	 */
	public void setJtTypeSort(String jtTypeSort) {
	 	this.jtTypeSort = jtTypeSort;
	}
	/**
	 * @return 贷款余额
	 */
	public String getJtItemBal() {
	 	return jtItemBal;
	}
	/**
	 * @设置 贷款余额
	 * @param jtItemBal
	 */
	public void setJtItemBal(String jtItemBal) {
	 	this.jtItemBal = jtItemBal;
	}
	/**
	 * @return 贷款提取比例
	 */
	public String getJtItemPre() {
	 	return jtItemPre;
	}
	/**
	 * @设置 贷款提取比例
	 * @param jtItemPre
	 */
	public void setJtItemPre(String jtItemPre) {
	 	this.jtItemPre = jtItemPre;
	}
	/**
	 * @return 借方科目控制字
	 */
	public String getJtItemJKm() {
	 	return jtItemJKm;
	}
	/**
	 * @设置 借方科目控制字
	 * @param jtItemJKm
	 */
	public void setJtItemJKm(String jtItemJKm) {
	 	this.jtItemJKm = jtItemJKm;
	}
	/**
	 * @return 贷方科目控制字
	 */
	public String getJtItemDKm() {
	 	return jtItemDKm;
	}
	/**
	 * @设置 贷方科目控制字
	 * @param jtItemDKm
	 */
	public void setJtItemDKm(String jtItemDKm) {
	 	this.jtItemDKm = jtItemDKm;
	}
	/**
	 * @return 贷方科目余额
	 */
	public String getJtItemDKmBal() {
	 	return jtItemDKmBal;
	}
	/**
	 * @设置 贷方科目余额
	 * @param jtItemDKmBal
	 */
	public void setJtItemDKmBal(String jtItemDKmBal) {
	 	this.jtItemDKmBal = jtItemDKmBal;
	}
	/**
	 * @return 计提余额
	 */
	public String getJtItemJtBal() {
	 	return jtItemJtBal;
	}
	/**
	 * @设置 计提余额
	 * @param jtItemJtBal
	 */
	public void setJtItemJtBal(String jtItemJtBal) {
	 	this.jtItemJtBal = jtItemJtBal;
	}
	/**
	 * @return 计提状态
	 */
	public String getJtItemFlag() {
	 	return jtItemFlag;
	}
	/**
	 * @设置 计提状态
	 * @param jtItemFlag
	 */
	public void setJtItemFlag(String jtItemFlag) {
	 	this.jtItemFlag = jtItemFlag;
	}
	/**
	 * @return 借方科目余额
	 */
	public String getJtItemJKmBal() {
	 	return jtItemJKmBal;
	}
	/**
	 * @设置 借方科目余额
	 * @param jtItemJKmBal
	 */
	public void setJtItemJKmBal(String jtItemJKmBal) {
	 	this.jtItemJKmBal = jtItemJKmBal;
	}
	/**
	 * @return 计提结算结果
	 */
	public String getJtItemResutBal() {
	 	return jtItemResutBal;
	}
	/**
	 * @设置 计提结算结果
	 * @param jtItemResutBal
	 */
	public void setJtItemResutBal(String jtItemResutBal) {
	 	this.jtItemResutBal = jtItemResutBal;
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
	public String getYunsuan1() {
		return yunsuan1;
	}
	public void setYunsuan1(String yunsuan1) {
		this.yunsuan1 = yunsuan1;
	}
	public String getYunsuan2() {
		return yunsuan2;
	}
	public void setYunsuan2(String yunsuan2) {
		this.yunsuan2 = yunsuan2;
	}
	public String getUpjtAmt() {
		return upjtAmt;
	}
	public void setUpjtAmt(String upjtAmt) {
		this.upjtAmt = upjtAmt;
	}
	public String getUpchaAmt() {
		return upchaAmt;
	}
	public void setUpchaAmt(String upchaAmt) {
		this.upchaAmt = upchaAmt;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOccDate() {
		return occDate;
	}
	public void setOccDate(String occDate) {
		this.occDate = occDate;
	}
	
}