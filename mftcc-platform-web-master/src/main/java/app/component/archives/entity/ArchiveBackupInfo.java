package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveBackupInfo.java
* Description:归档文件备份
* @author：yudongwei@mftcc.cn
* @Sat Apr 08 15:42:40 CST 2017
* @version：1.0
**/
public class ArchiveBackupInfo extends BaseDomain {
	private String id;//主键
	private String archiveDetailNo;//档案明细编号
	private String archiveMainNo;//档案编号
	private String docNo;//文件编号
	private String docName;//文件名称
	private Double docSize;//文件大小
	private String archiveDocAttribute;//归档文件属性（01-文档 02-图片 03-音频 04-视频  05-压缩 09-其它）
	private String docAddr;//文件地址
	private String docEncryptAddr;//文件加密地址
	private String archiveBackupAddr;//归档备份地址
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
	private String paperNo;//纸制文件编号
	private String paperKeeperNo;//纸制文件保管人编号
	private String paperKeeperName;//纸制文件保管人姓名
	private String archiveDocSource;//归档文件来源（01-业务归档 02-归档补充）
	private String updateOpNo;//更新人编号
	private String updateOpName;//更新人名称
	private String updateBrNo;//更新人部门编号
	private String updateBrName;//更新人部门名称
	private String updateDate;//更新日期
	
	private String isTemplate;//是否是模板文档（0-否 1-是）

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
	 * @return 归档文件属性（01-文档 02-图片 03-音频 04-视频  05-压缩 09-其它）
	 */
	public String getArchiveDocAttribute() {
	 	return archiveDocAttribute;
	}
	/**
	 * @设置 归档文件属性（01-文档 02-图片 03-音频 04-视频  05-压缩 09-其它）
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
	 * @return 归档备份地址
	 */
	public String getArchiveBackupAddr() {
	 	return archiveBackupAddr;
	}
	/**
	 * @设置 归档备份地址
	 * @param archiveBackupAddr
	 */
	public void setArchiveBackupAddr(String archiveBackupAddr) {
	 	this.archiveBackupAddr = archiveBackupAddr;
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
	 * @param archiveDocStatus
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
	 * @return 纸制文件编号
	 */
	public String getPaperNo() {
	 	return paperNo;
	}
	/**
	 * @设置 纸制文件编号
	 * @param paperNo
	 */
	public void setPaperNo(String paperNo) {
	 	this.paperNo = paperNo;
	}
	/**
	 * @return 纸制文件保管人编号
	 */
	public String getPaperKeeperNo() {
	 	return paperKeeperNo;
	}
	/**
	 * @设置 纸制文件保管人编号
	 * @param paperKeeperNo
	 */
	public void setPaperKeeperNo(String paperKeeperNo) {
	 	this.paperKeeperNo = paperKeeperNo;
	}
	/**
	 * @return 纸制文件保管人姓名
	 */
	public String getPaperKeeperName() {
	 	return paperKeeperName;
	}
	/**
	 * @设置 纸制文件保管人姓名
	 * @param paperKeeperName
	 */
	public void setPaperKeeperName(String paperKeeperName) {
	 	this.paperKeeperName = paperKeeperName;
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
	 * @return 更新人编号
	 */
	public String getUpdateOpNo() {
	 	return updateOpNo;
	}
	/**
	 * @设置 更新人编号
	 * @param updateOpNo
	 */
	public void setUpdateOpNo(String updateOpNo) {
	 	this.updateOpNo = updateOpNo;
	}
	/**
	 * @return 更新人名称
	 */
	public String getUpdateOpName() {
	 	return updateOpName;
	}
	/**
	 * @设置 更新人名称
	 * @param updateOpName
	 */
	public void setUpdateOpName(String updateOpName) {
	 	this.updateOpName = updateOpName;
	}
	/**
	 * @return 更新人部门编号
	 */
	public String getUpdateBrNo() {
	 	return updateBrNo;
	}
	/**
	 * @设置 更新人部门编号
	 * @param updateBrNo
	 */
	public void setUpdateBrNo(String updateBrNo) {
	 	this.updateBrNo = updateBrNo;
	}
	/**
	 * @return 更新人部门名称
	 */
	public String getUpdateBrName() {
	 	return updateBrName;
	}
	/**
	 * @设置 更新人部门名称
	 * @param updateBrName
	 */
	public void setUpdateBrName(String updateBrName) {
	 	this.updateBrName = updateBrName;
	}
	/**
	 * @return 更新日期
	 */
	public String getUpdateDate() {
	 	return updateDate;
	}
	/**
	 * @设置 更新日期
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) {
	 	this.updateDate = updateDate;
	}
	/**
	 * @return 是否是模板文档（0-否 1-是）
	 */
	public String getIsTemplate() {
		return isTemplate;
	}
	/**
	 * @设置 是否是模板文档（0-否 1-是）
	 * @param isTemplate
	 */
	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}
	
}