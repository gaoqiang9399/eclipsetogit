package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveLendInfoIncludeDetailAndLog.java
* Description:归档文件借阅信息（包括归档明细信息和归档文件操作日志）
* @author：yudongwei@mftcc.cn
* @Fri Apr 07 15:04:12 CST 2017
* @version：1.0
**/
public class ArchiveLendInfoIncludeDetailAndLog extends BaseDomain {
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
	
	private String docNo;//文件编号
	private String docName;//文件名称
	private Double docSize;//文件大小
	private String archiveDocAttribute;//归档文件属性（01-文档 02-图片 03-音频 04-视频）
	private String docAddr;//文件地址
	private String docEncryptAddr;//文件加密地址
	private String archiveAddr;//归档地址
	private String docType;//文档类型编号
	private String docTypeName;//文档类型名称
	private String docSplitNo;//文档细分类型编号
	private String docSplitName;//文档细分类型名称
	private String scNo;//场景编号
	private String docBizNo;//业务场景编号
	private String archiveDocStatus;//归档文件状态（01-正常 02-删除）
	private String isLend;//是否借出（0-否 1-是）
	private String isPaper;//是否存在纸制文档（0-否 1-是）
	private String paperAddr;//纸制文件存放位置
	private String archiveDocSource;//归档文件来源（01-业务归档 02-归档补充）
	
	private String logId;//操作日志主键
	private String archiveOperationType;//操作类型（01-归档 02-新增 03-删除 04-恢复 05-借阅 06-归还 07-合并 08-备份 09-封档）
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
	/**
	 * @return 文件编号
	 */
	public String getDocNo() {
	 	return docNo;
	}
	/**
	 * @设置 文件编号
	 * @param docNo
	 */
	public void setDocNo(String docNo) {
	 	this.docNo = docNo;
	}
	/**
	 * @return 文件名称
	 */
	public String getDocName() {
	 	return docName;
	}
	/**
	 * @设置 文件名称
	 * @param docName
	 */
	public void setDocName(String docName) {
	 	this.docName = docName;
	}
	/**
	 * @return 文件大小
	 */
	public Double getDocSize() {
	 	return docSize;
	}
	/**
	 * @设置 文件大小
	 * @param docSize
	 */
	public void setDocSize(Double docSize) {
	 	this.docSize = docSize;
	}
	/**
	 * @return 归档文件属性（01-文档 02-图片 03-音频 04-视频）
	 */
	public String getArchiveDocAttribute() {
	 	return archiveDocAttribute;
	}
	/**
	 * @设置 归档文件属性（01-文档 02-图片 03-音频 04-视频）
	 * @param archiveDocAttribute
	 */
	public void setArchiveDocAttribute(String archiveDocAttribute) {
	 	this.archiveDocAttribute = archiveDocAttribute;
	}
	/**
	 * @return 文件地址
	 */
	public String getDocAddr() {
	 	return docAddr;
	}
	/**
	 * @设置 文件地址
	 * @param docAddr
	 */
	public void setDocAddr(String docAddr) {
	 	this.docAddr = docAddr;
	}
	/**
	 * @return 文件加密地址
	 */
	public String getDocEncryptAddr() {
	 	return docEncryptAddr;
	}
	/**
	 * @设置 文件加密地址
	 * @param docEncryptAddr
	 */
	public void setDocEncryptAddr(String docEncryptAddr) {
	 	this.docEncryptAddr = docEncryptAddr;
	}
	/**
	 * @return 归档地址
	 */
	public String getArchiveAddr() {
	 	return archiveAddr;
	}
	/**
	 * @设置 归档地址
	 * @param archiveAddr
	 */
	public void setArchiveAddr(String archiveAddr) {
	 	this.archiveAddr = archiveAddr;
	}
	/**
	 * @return 文档类型编号
	 */
	public String getDocType() {
	 	return docType;
	}
	/**
	 * @设置 文档类型编号
	 * @param docType
	 */
	public void setDocType(String docType) {
	 	this.docType = docType;
	}
	/**
	 * @return 文档类型名称
	 */
	public String getDocTypeName() {
	 	return docTypeName;
	}
	/**
	 * @设置 文档类型名称
	 * @param docTypeName
	 */
	public void setDocTypeName(String docTypeName) {
	 	this.docTypeName = docTypeName;
	}
	/**
	 * @return 文档细分类型编号
	 */
	public String getDocSplitNo() {
	 	return docSplitNo;
	}
	/**
	 * @设置 文档细分类型编号
	 * @param docSplitNo
	 */
	public void setDocSplitNo(String docSplitNo) {
	 	this.docSplitNo = docSplitNo;
	}
	/**
	 * @return 文档细分类型名称
	 */
	public String getDocSplitName() {
	 	return docSplitName;
	}
	/**
	 * @设置 文档细分类型名称
	 * @param docSplitName
	 */
	public void setDocSplitName(String docSplitName) {
	 	this.docSplitName = docSplitName;
	}
	/**
	 * @return 场景编号
	 */
	public String getScNo() {
	 	return scNo;
	}
	/**
	 * @设置 场景编号
	 * @param scNo
	 */
	public void setScNo(String scNo) {
	 	this.scNo = scNo;
	}
	/**
	 * @return 业务场景编号
	 */
	public String getDocBizNo() {
	 	return docBizNo;
	}
	/**
	 * @设置 业务场景编号
	 * @param docBizNo
	 */
	public void setDocBizNo(String docBizNo) {
	 	this.docBizNo = docBizNo;
	}
	/**
	 * @return 归档文件状态（01-正常 02-删除）
	 */
	public String getArchiveDocStatus() {
	 	return archiveDocStatus;
	}
	/**
	 * @设置 归档文件状态（01-正常 02-删除）
	 * @param docStatus
	 */
	public void setArchiveDocStatus(String archiveDocStatus) {
	 	this.archiveDocStatus = archiveDocStatus;
	}
	/**
	 * @return 是否借出（0-否 1-是）
	 */
	public String getIsLend() {
	 	return isLend;
	}
	/**
	 * @设置 是否借出（0-否 1-是）
	 * @param isLend
	 */
	public void setIsLend(String isLend) {
	 	this.isLend = isLend;
	}
	/**
	 * @return 是否存在纸制文档（0-否 1-是）
	 */
	public String getIsPaper() {
	 	return isPaper;
	}
	/**
	 * @设置 是否存在纸制文档（0-否 1-是）
	 * @param isPaper
	 */
	public void setIsPaper(String isPaper) {
	 	this.isPaper = isPaper;
	}
	/**
	 * @return 纸制文件存放位置
	 */
	public String getPaperAddr() {
	 	return paperAddr;
	}
	/**
	 * @设置 纸制文件存放位置
	 * @param paperAddr
	 */
	public void setPaperAddr(String paperAddr) {
	 	this.paperAddr = paperAddr;
	}
	/**
	 * @return 归档文件来源（01-业务归档 02-归档补充）
	 */
	public String getArchiveDocSource() {
	 	return archiveDocSource;
	}
	/**
	 * @设置 归档文件来源（01-业务归档 02-归档补充）
	 * @param source
	 */
	public void setArchiveDocSource(String archiveDocSource) {
	 	this.archiveDocSource = archiveDocSource;
	}
	/**
	 * @return 操作日志主键
	 */
	public String getLogId() {
		return logId;
	}
	/**
	 * @设置 操作日志主键
	 * @param logId
	 */
	public void setLogId(String logId) {
		this.logId = logId;
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