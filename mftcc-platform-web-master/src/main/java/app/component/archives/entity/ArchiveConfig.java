package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveConfig.java
* Description:归档配置信息
* @author：yudongwei@mftcc.cn
* @Wed Apr 05 16:48:41 CST 2017
* @version：1.0
**/
public class ArchiveConfig extends BaseDomain {
	private String id;//主键
	private Integer cacheCleanDays;//缓存文件清理周期（天）
	private Integer docLendMaxDays;//文档借阅最大天数
	private String pactSignFilesOfSceneNos;//合同签订归档文件对应场景编号（以|分隔）
	private String pactSignFilesOfSceneNames;//合同签订归档文件对应场景名称（以|分隔）
	private String pactOverFilesOfSceneNos;//合同完结归档文件对应场景编号（以|分隔）
	private String pactOverFilesOfSceneNames;//合同完结归档文件对应场景名称（以分隔）
	private String updateOpNo;//更新人编号
	private String updateOpName;//更新人名称
	private String updateBrNo;//更新人部门编号
	private String updateBrName;//更新人部门名称
	private String updateDate;//更新日期

	private String relationNo;
	private String archiveName;
	private String archiveMode;
	private String archiveType;
	private String archiveDesc;

	public String getRelationNo() {
		return relationNo;
	}

	public void setRelationNo(String relationNo) {
		this.relationNo = relationNo;
	}

	public String getArchiveDesc() {
		return archiveDesc;
	}

	public void setArchiveDesc(String archiveDesc) {
		this.archiveDesc = archiveDesc;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getArchiveMode() {
		return archiveMode;
	}

	public void setArchiveMode(String archiveMode) {
		this.archiveMode = archiveMode;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

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
	 * @return 缓存文件清理周期（天）
	 */
	public Integer getCacheCleanDays() {
	 	return cacheCleanDays;
	}
	/**
	 * @设置 缓存文件清理周期（天）
	 * @param cacheCleanDays
	 */
	public void setCacheCleanDays(Integer cacheCleanDays) {
	 	this.cacheCleanDays = cacheCleanDays;
	}
	/**
	 * @return 文档借阅最大天数
	 */
	public Integer getDocLendMaxDays() {
	 	return docLendMaxDays;
	}
	/**
	 * @设置 文档借阅最大天数
	 * @param docLendMaxDays
	 */
	public void setDocLendMaxDays(Integer docLendMaxDays) {
	 	this.docLendMaxDays = docLendMaxDays;
	}
	/**
	 * @return 合同签订归档文件对应场景编号（以|分隔）
	 */
	public String getPactSignFilesOfSceneNos() {
	 	return pactSignFilesOfSceneNos;
	}
	/**
	 * 以|分隔设置 合同签订归档文件对应场景编号（以|分隔）
	 * @param pactSignFilesOfSceneNos
	 */
	public void setPactSignFilesOfSceneNos(String pactSignFilesOfSceneNos) {
	 	this.pactSignFilesOfSceneNos = pactSignFilesOfSceneNos;
	}
	/**
	 * @return 合同签订归档文件对应场景名称（以|分隔）
	 */
	public String getPactSignFilesOfSceneNames() {
	 	return pactSignFilesOfSceneNames;
	}
	/**
	 * @设置 合同签订归档文件对应场景名称（以|分隔）
	 * @param pactSignFilesOfSceneNames
	 */
	public void setPactSignFilesOfSceneNames(String pactSignFilesOfSceneNames) {
	 	this.pactSignFilesOfSceneNames = pactSignFilesOfSceneNames;
	}
	/**
	 * @return 合同完结归档文件对应场景编号（以|分隔）
	 */
	public String getPactOverFilesOfSceneNos() {
	 	return pactOverFilesOfSceneNos;
	}
	/**
	 * @设置 合同完结归档文件对应场景编号（以|分隔）
	 * @param pactOverFilesOfSceneNos
	 */
	public void setPactOverFilesOfSceneNos(String pactOverFilesOfSceneNos) {
	 	this.pactOverFilesOfSceneNos = pactOverFilesOfSceneNos;
	}
	/**
	 * @return 合同完结归档文件对应场景名称（以|分隔）
	 */
	public String getPactOverFilesOfSceneNames() {
	 	return pactOverFilesOfSceneNames;
	}
	/**
	 * @设置 合同完结归档文件对应场景名称（以|分隔）
	 * @param pactOverFilesOfSceneNames
	 */
	public void setPactOverFilesOfSceneNames(String pactOverFilesOfSceneNames) {
	 	this.pactOverFilesOfSceneNames = pactOverFilesOfSceneNames;
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
}