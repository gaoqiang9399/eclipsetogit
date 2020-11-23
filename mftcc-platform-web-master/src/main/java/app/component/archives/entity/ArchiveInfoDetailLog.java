package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveInfoDetailLog.java
* Description:归档文件操作日志
* @author：yudongwei@mftcc.cn
* @Sat Apr 08 15:07:35 CST 2017
* @version：1.0
**/
public class ArchiveInfoDetailLog extends BaseDomain {
	private String id;//主键
	private String archiveDetailNo;//档案明细编号
	private String archiveMainNo;//档案编号
	private String archiveOperationType;//操作类型（01-归档 02-新增 03-删除 04-恢复 05-借阅 06-归还 07-合并 08-备份 09-封档）
	private String lendId;//借阅信息主键
	private String mergeId;//合并信息主键
	private String backupId;//备份信息主键
	private String opNo;//操作人编号
	private String opName;//操作人名称
	private String brNo;//操作人部门编号
	private String brName;//操作人部门名称
	private String opDate;//操作日期
	private String opTime;//操作时间

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
	 * @return 操作类型（01-归档 02-新增 03-删除 04-恢复 05-借阅 06-归还 07-合并 08-备份 09-封档）
	 */
	public String getArchiveOperationType() {
	 	return archiveOperationType;
	}
	/**
	 * @设置 操作类型（01-归档 02-新增 03-删除 04-恢复 05-借阅 06-归还 07-合并 08-备份 09-封档）
	 * @param archiveOperationType
	 */
	public void setArchiveOperationType(String archiveOperationType) {
	 	this.archiveOperationType = archiveOperationType;
	}
	/**
	 * @return 借阅信息主键
	 */
	public String getLendId() {
	 	return lendId;
	}
	/**
	 * @设置 借阅信息主键
	 * @param lendId
	 */
	public void setLendId(String lendId) {
	 	this.lendId = lendId;
	}
	/**
	 * @return 合并信息主键
	 */
	public String getMergeId() {
	 	return mergeId;
	}
	/**
	 * @设置 合并信息主键
	 * @param mergeId
	 */
	public void setMergeId(String mergeId) {
	 	this.mergeId = mergeId;
	}
	/**
	 * @return 备份信息主键
	 */
	public String getBackupId() {
	 	return backupId;
	}
	/**
	 * @设置 备份信息主键
	 * @param backupId
	 */
	public void setBackupId(String backupId) {
	 	this.backupId = backupId;
	}
	/**
	 * @return 操作人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 操作人部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 操作人部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 操作人部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 操作人部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 操作日期
	 */
	public String getOpDate() {
	 	return opDate;
	}
	/**
	 * @设置 操作日期
	 * @param opDate
	 */
	public void setOpDate(String opDate) {
	 	this.opDate = opDate;
	}
	/**
	 * @return 操作时间
	 */
	public String getOpTime() {
	 	return opTime;
	}
	/**
	 * @设置 操作时间
	 * @param opTime
	 */
	public void setOpTime(String opTime) {
	 	this.opTime = opTime;
	}
}