package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineIndexSub.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jul 24 14:27:45 CST 2017
* @version：1.0
**/
public class MfExamineIndexSub extends BaseDomain {
	private String subId;//子项编号
	private String subNo;//子项关联检查指标名
	private String subName;//子项名称
	private String remark;//子项说明
	private String useFlag;//启用标识
	private String indexId;//贷后检查指标编号
	private String indexName;//贷后检查评级指标名

	/**
	 * @return 子项编号
	 */
	public String getSubId() {
	 	return subId;
	}
	/**
	 * @设置 子项编号
	 * @param subId
	 */
	public void setSubId(String subId) {
	 	this.subId = subId;
	}
	/**
	 * @return 子项关联检查指标名
	 */
	public String getSubNo() {
	 	return subNo;
	}
	/**
	 * @设置 子项关联检查指标名
	 * @param subNo
	 */
	public void setSubNo(String subNo) {
	 	this.subNo = subNo;
	}
	/**
	 * @return 子项名称
	 */
	public String getSubName() {
	 	return subName;
	}
	/**
	 * @设置 子项名称
	 * @param subName
	 */
	public void setSubName(String subName) {
	 	this.subName = subName;
	}
	/**
	 * @return 子项说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 子项说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 启用标识
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标识
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 贷后检查指标编号
	 */
	public String getIndexId() {
	 	return indexId;
	}
	/**
	 * @设置 贷后检查指标编号
	 * @param indexId
	 */
	public void setIndexId(String indexId) {
	 	this.indexId = indexId;
	}
	/**
	 * @return 贷后检查评级指标名
	 */
	public String getIndexName() {
	 	return indexName;
	}
	/**
	 * @设置 贷后检查评级指标名
	 * @param indexName
	 */
	public void setIndexName(String indexName) {
	 	this.indexName = indexName;
	}
}