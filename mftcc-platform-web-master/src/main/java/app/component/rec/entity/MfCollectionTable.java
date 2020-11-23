package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: MfCollectionTable.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Mar 19 15:03:41 CST 2017
* @version：1.0
**/
public class MfCollectionTable extends BaseDomain {
	private String collectionNo;//催收方案编号
	private String collectionType;//催收类型
	private String action;//
	private String isBase;//是否基准
	private String isMust;
	private String tableName;//表名
	private String tableDes;//表描述
	private String showType;//信息的展现形式
	private String dataFullFlag;//表中是否有数据
	private String htmlStr;
	private String delFlag;//删除标志
	private Integer sort;//显示顺序
	private String addModelDef;//新增模板描述
	private String showModelDef;//查看模板描述
	private String ext1;//
	private String ext2;//
	private String ext3;//

	/**
	 * @return 催收方案编号
	 */
	public String getCollectionNo() {
	 	return collectionNo;
	}
	/**
	 * @设置 催收方案编号
	 * @param collectionNo
	 */
	public void setCollectionNo(String collectionNo) {
	 	this.collectionNo = collectionNo;
	}
	/**
	 * @return 催收类型
	 */
	public String getCollectionType() {
	 	return collectionType;
	}
	/**
	 * @设置 催收类型
	 * @param collectionType
	 */
	public void setCollectionType(String collectionType) {
	 	this.collectionType = collectionType;
	}
	/**
	 * @return 是否基准
	 */
	public String getIsBase() {
	 	return isBase;
	}
	/**
	 * @设置 是否基准
	 * @param isBase
	 */
	public void setIsBase(String isBase) {
	 	this.isBase = isBase;
	}
	/**
	 * @return 表名
	 */
	public String getTableName() {
	 	return tableName;
	}
	/**
	 * @设置 表名
	 * @param tableName
	 */
	public void setTableName(String tableName) {
	 	this.tableName = tableName;
	}
	/**
	 * @return 表描述
	 */
	public String getTableDes() {
	 	return tableDes;
	}
	/**
	 * @设置 表描述
	 * @param tableDes
	 */
	public void setTableDes(String tableDes) {
	 	this.tableDes = tableDes;
	}
	/**
	 * @return 信息的展现形式
	 */
	public String getShowType() {
	 	return showType;
	}
	/**
	 * @设置 信息的展现形式
	 * @param showType
	 */
	public void setShowType(String showType) {
	 	this.showType = showType;
	}
	/**
	 * @return 表中是否有数据
	 */
	public String getDataFullFlag() {
	 	return dataFullFlag;
	}
	/**
	 * @设置 表中是否有数据
	 * @param dataFullFlag
	 */
	public void setDataFullFlag(String dataFullFlag) {
	 	this.dataFullFlag = dataFullFlag;
	}
	/**
	 * @return 删除标志
	 */
	public String getDelFlag() {
	 	return delFlag;
	}
	/**
	 * @设置 删除标志
	 * @param delFlag
	 */
	public void setDelFlag(String delFlag) {
	 	this.delFlag = delFlag;
	}
	/**
	 * @return 显示顺序
	 */
	public Integer getSort() {
	 	return sort;
	}
	/**
	 * @设置 显示顺序
	 * @param sort
	 */
	public void setSort(Integer sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 新增模板描述
	 */
	public String getAddModelDef() {
	 	return addModelDef;
	}
	/**
	 * @设置 新增模板描述
	 * @param addModelDef
	 */
	public void setAddModelDef(String addModelDef) {
	 	this.addModelDef = addModelDef;
	}
	/**
	 * @return 查看模板描述
	 */
	public String getShowModelDef() {
	 	return showModelDef;
	}
	/**
	 * @设置 查看模板描述
	 * @param showModelDef
	 */
	public void setShowModelDef(String showModelDef) {
	 	this.showModelDef = showModelDef;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public String getHtmlStr() {
		return htmlStr;
	}
	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}
}