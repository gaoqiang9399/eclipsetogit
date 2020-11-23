package app.component.archives.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: ArchiveMergeInfo.java
* Description:归档文件合并信息（包括归档明细信息和文件操作日志）
* @author:yudongwei@mftcc.cn
* @Thu Apr 20 17:23:25 CST 2017
* @version：1.0
**/
public class ArchiveMergeInfoIncludeDetailAndLog extends BaseDomain {
	private String id;//主键
	private String archiveDetailNos;//档案明细编号（以@分隔）
	private String archiveMainNo;//档案编号
	private String mergeFileName;//合并文件名称
	private String mergeFilePath;//合并文件路径
	
	private String opNo;//操作人编号
	private String opName;//操作人名称
	private String brNo;//操作人部门编号
	private String brName;//操作人部门名称
	private String opDate;//操作日期
	private String opTime;//操作时间
	
	private List<ArchiveInfoDetail> ArchiveInfoDetailList;//归档明细信息集合

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
	 * @return 档案明细编号（以@分隔）
	 */
	public String getArchiveDetailNos() {
	 	return archiveDetailNos;
	}
	/**
	 * @设置 档案明细编号（以@分隔）
	 * @param archiveDetailNos
	 */
	public void setArchiveDetailNos(String archiveDetailNos) {
	 	this.archiveDetailNos = archiveDetailNos;
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
	 * @return 合并文件名称
	 */
	public String getMergeFileName() {
	 	return mergeFileName;
	}
	/**
	 * @设置 合并文件名称
	 * @param mergeFileName
	 */
	public void setMergeFileName(String mergeFileName) {
	 	this.mergeFileName = mergeFileName;
	}
	/**
	 * @return 合并文件路径
	 */
	public String getMergeFilePath() {
	 	return mergeFilePath;
	}
	/**
	 * @设置 合并文件路径
	 * @param mergeFilePath
	 */
	public void setMergeFilePath(String mergeFilePath) {
	 	this.mergeFilePath = mergeFilePath;
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
	/**
	 * @return 归档明细信息集合
	 */
	public List<ArchiveInfoDetail> getArchiveInfoDetailList() {
		return ArchiveInfoDetailList;
	}
	/**
	 * @设置 归档明细信息集合
	 * @param ArchiveInfoDetailList
	 */
	public void setArchiveInfoDetailList(
			List<ArchiveInfoDetail> archiveInfoDetailList) {
		ArchiveInfoDetailList = archiveInfoDetailList;
	}
	
}