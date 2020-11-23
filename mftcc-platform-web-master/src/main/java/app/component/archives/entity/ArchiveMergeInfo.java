package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveMergeInfo.java
* Description:归档文件合并信息
* @author:yudongwei@mftcc.cn
* @Thu Apr 20 17:23:25 CST 2017
* @version：1.0
**/
public class ArchiveMergeInfo extends BaseDomain {
	private String id;//主键
	private String archiveDetailNos;//档案明细编号（以@分隔）
	private String archiveMainNo;//档案编号
	private String mergeFileName;//合并文件名称
	private String mergeFilePath;//合并文件路径

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
}