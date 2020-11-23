package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamIndex.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Feb 16 14:59:05 CST 2017
* @version：1.0
**/
public class MfExamIndex extends BaseDomain {
	private String indexId;//指标编号
	private String indexNameEn;//指标英文名
	private String indexName;//指标名称
	private String indexType;//指标类型1文本字段2数据字典
	private String keyNameEn;//数据字典名
	private String keyNameZn;//数据字典名称
	private String remark;//指标描述
	private String useFlag;//启用标识
	private String regTime;//登记时间

	/**
	 * @return 指标编号
	 */
	public String getIndexId() {
	 	return indexId;
	}
	/**
	 * @设置 指标编号
	 * @param indexId
	 */
	public void setIndexId(String indexId) {
	 	this.indexId = indexId;
	}
	/**
	 * @return 指标英文名
	 */
	public String getIndexNameEn() {
	 	return indexNameEn;
	}
	/**
	 * @设置 指标英文名
	 * @param indexNameEn
	 */
	public void setIndexNameEn(String indexNameEn) {
	 	this.indexNameEn = indexNameEn;
	}
	/**
	 * @return 指标名称
	 */
	public String getIndexName() {
	 	return indexName;
	}
	/**
	 * @设置 指标名称
	 * @param indexName
	 */
	public void setIndexName(String indexName) {
	 	this.indexName = indexName;
	}
	/**
	 * @return 指标类型1文本字段2数据字典
	 */
	public String getIndexType() {
	 	return indexType;
	}
	/**
	 * @设置 指标类型1文本字段2数据字典
	 * @param indexType
	 */
	public void setIndexType(String indexType) {
	 	this.indexType = indexType;
	}
	/**
	 * @return 数据字典名
	 */
	public String getKeyNameEn() {
	 	return keyNameEn;
	}
	/**
	 * @设置 数据字典名
	 * @param keyNameEn
	 */
	public void setKeyNameEn(String keyNameEn) {
	 	this.keyNameEn = keyNameEn;
	}
	/**
	 * @return 数据字典名称
	 */
	public String getKeyNameZn() {
	 	return keyNameZn;
	}
	/**
	 * @设置 数据字典名称
	 * @param keyNameZn
	 */
	public void setKeyNameZn(String keyNameZn) {
	 	this.keyNameZn = keyNameZn;
	}
	/**
	 * @return 指标描述
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 指标描述
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
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
}