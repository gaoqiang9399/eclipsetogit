package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveLendInfo.java
* Description:归档文件借阅信息
* @author：yudongwei@mftcc.cn
* @Tue Apr 11 18:04:18 CST 2017
* @version：1.0
**/
public class ArchiveLendInfo extends BaseDomain {
	private String id;//主键
	private String archiveDetailNo;//档案明细编号
	private String archiveMainNo;//档案编号
	private String borrower;//借阅人
	private String borrowerContact;//借阅人联系方式
	private String borrowingPurposes;//借阅用途
	private String borrowingDate;//借阅时间
	private String returnPlanDate;//计划归还日期
	private String returnActualDate;//实际归还日期
	private String isReturn;//是否归还（0-否 1-是）

	/**
	 * @return 主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 档案明细编号
	 */
	public String getArchiveDetailNo() {
	 	return archiveDetailNo;
	}
	/**
	 * @设置 档案明细编号
	 * @param archiveDetailNo
	 */
	public void setArchiveDetailNo(String archiveDetailNo) {
	 	this.archiveDetailNo = archiveDetailNo;
	}
	/**
	 * @return 档案编号
	 */
	public String getArchiveMainNo() {
	 	return archiveMainNo;
	}
	/**
	 * @设置 档案编号
	 * @param archiveMainNo
	 */
	public void setArchiveMainNo(String archiveMainNo) {
	 	this.archiveMainNo = archiveMainNo;
	}
	/**
	 * @return 借阅人
	 */
	public String getBorrower() {
	 	return borrower;
	}
	/**
	 * @设置 借阅人
	 * @param borrower
	 */
	public void setBorrower(String borrower) {
	 	this.borrower = borrower;
	}
	/**
	 * @return 借阅人联系方式
	 */
	public String getBorrowerContact() {
	 	return borrowerContact;
	}
	/**
	 * @设置 借阅人联系方式
	 * @param borrowerContact
	 */
	public void setBorrowerContact(String borrowerContact) {
	 	this.borrowerContact = borrowerContact;
	}
	/**
	 * @return 借阅用途
	 */
	public String getBorrowingPurposes() {
	 	return borrowingPurposes;
	}
	/**
	 * @设置 借阅用途
	 * @param borrowingPurposes
	 */
	public void setBorrowingPurposes(String borrowingPurposes) {
	 	this.borrowingPurposes = borrowingPurposes;
	}
	/**
	 * @return 借阅时间
	 */
	public String getBorrowingDate() {
	 	return borrowingDate;
	}
	/**
	 * @设置 借阅时间
	 * @param borrowingDate
	 */
	public void setBorrowingDate(String borrowingDate) {
	 	this.borrowingDate= borrowingDate;
	}
	/**
	 * @return 计划归还日期
	 */
	public String getReturnPlanDate() {
	 	return returnPlanDate;
	}
	/**
	 * @设置 计划归还日期
	 * @param returnPlanDate
	 */
	public void setReturnPlanDate(String returnPlanDate) {
	 	this.returnPlanDate = returnPlanDate;
	}
	/**
	 * @return 实际归还日期
	 */
	public String getReturnActualDate() {
	 	return returnActualDate;
	}
	/**
	 * @设置 实际归还日期
	 * @param returnActualDate
	 */
	public void setReturnActualDate(String returnActualDate) {
	 	this.returnActualDate = returnActualDate;
	}
	/**
	 * @return 是否归还（0-否 1-是）
	 */
	public String getIsReturn() {
		return isReturn;
	}
	/**
	 * @设置 是否归还（0-否 1-是）
	 * @param isReturn
	 */
	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	
}