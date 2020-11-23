package app.component.oa.budget.entity;
import app.base.BaseDomain;
/**
* Title: MfOaBudgetDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 15:25:40 CST 2017
* @version：1.0
**/
public class MfOaBudgetDetail extends BaseDomain {
	private String budgetId;//预算编号
	private String budgetType;//预算类别
	private String itemName;//项目名称
	private String compleTarget;//完成目标
	private String compleDate;//完成时间
	private String zrOpNo;//责任人编号
	private String zrOpName;//责任人名称
	private String khOpNo;//考核人编号
	private String khOpName;//考核人名称
	private String accNo;//科目编码
	private String accHrt;//科目控制字
	private String accName;//科目名称
	private String budgetClass;//预算分级
	private Double budgetAmt;//预计数
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
	 * @return 预算编号
	 */
	public String getBudgetId() {
	 	return budgetId;
	}
	/**
	 * @设置 预算编号
	 * @param budgetId
	 */
	public void setBudgetId(String budgetId) {
	 	this.budgetId = budgetId;
	}
	/**
	 * @return 预算类别
	 */
	public String getBudgetType() {
	 	return budgetType;
	}
	/**
	 * @设置 预算类别
	 * @param budgetType
	 */
	public void setBudgetType(String budgetType) {
	 	this.budgetType = budgetType;
	}
	/**
	 * @return 项目名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 项目名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 完成目标
	 */
	public String getCompleTarget() {
	 	return compleTarget;
	}
	/**
	 * @设置 完成目标
	 * @param compleTarget
	 */
	public void setCompleTarget(String compleTarget) {
	 	this.compleTarget = compleTarget;
	}
	/**
	 * @return 完成时间
	 */
	public String getCompleDate() {
	 	return compleDate;
	}
	/**
	 * @设置 完成时间
	 * @param compleDate
	 */
	public void setCompleDate(String compleDate) {
	 	this.compleDate = compleDate;
	}
	/**
	 * @return 责任人编号
	 */
	public String getZrOpNo() {
	 	return zrOpNo;
	}
	/**
	 * @设置 责任人编号
	 * @param zrOpNo
	 */
	public void setZrOpNo(String zrOpNo) {
	 	this.zrOpNo = zrOpNo;
	}
	/**
	 * @return 责任人名称
	 */
	public String getZrOpName() {
	 	return zrOpName;
	}
	/**
	 * @设置 责任人名称
	 * @param zrOpName
	 */
	public void setZrOpName(String zrOpName) {
	 	this.zrOpName = zrOpName;
	}
	/**
	 * @return 考核人编号
	 */
	public String getKhOpNo() {
	 	return khOpNo;
	}
	/**
	 * @设置 考核人编号
	 * @param khOpNo
	 */
	public void setKhOpNo(String khOpNo) {
	 	this.khOpNo = khOpNo;
	}
	/**
	 * @return 考核人名称
	 */
	public String getKhOpName() {
	 	return khOpName;
	}
	/**
	 * @设置 考核人名称
	 * @param khOpName
	 */
	public void setKhOpName(String khOpName) {
	 	this.khOpName = khOpName;
	}
	/**
	 * @return 科目编码
	 */
	public String getAccNo() {
	 	return accNo;
	}
	/**
	 * @设置 科目编码
	 * @param accNo
	 */
	public void setAccNo(String accNo) {
	 	this.accNo = accNo;
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
	 * @return 科目名称
	 */
	public String getAccName() {
	 	return accName;
	}
	/**
	 * @设置 科目名称
	 * @param accName
	 */
	public void setAccName(String accName) {
	 	this.accName = accName;
	}
	/**
	 * @return 预算分级
	 */
	public String getBudgetClass() {
	 	return budgetClass;
	}
	/**
	 * @设置 预算分级
	 * @param budgetClass
	 */
	public void setBudgetClass(String budgetClass) {
	 	this.budgetClass = budgetClass;
	}
	/**
	 * @return 预计数
	 */
	public Double getBudgetAmt() {
	 	return budgetAmt;
	}
	/**
	 * @设置 预计数
	 * @param budgetAmt
	 */
	public void setBudgetAmt(Double budgetAmt) {
	 	this.budgetAmt = budgetAmt;
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