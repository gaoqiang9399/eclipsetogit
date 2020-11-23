package app.component.prdct.entity;
import app.base.BaseDomain;
/**
* Title: MfKindTableConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Nov 25 17:04:10 CST 2017
* @version：1.0
**/
public class MfKindTableConfig extends BaseDomain {
	private String id;//唯一编号
	private String kindNo;//产品编号
	private String tableDes;//表单中文名（表中文名）
	private String tableName;//表单英文名（表名）
	private String isMust;//是否必填0-否1-是
	private String tableType;//所属模块 1 客户  
	private Integer sort;//排序
	/**
	 * @return 唯一编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 产品编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 表单中文名（表中文名）
	 */
	public String getTableDes() {
	 	return tableDes;
	}
	/**
	 * @设置 表单中文名（表中文名）
	 * @param tableDes
	 */
	public void setTableDes(String tableDes) {
	 	this.tableDes = tableDes;
	}
	/**
	 * @return 表单英文名（表名）
	 */
	public String getTableName() {
	 	return tableName;
	}
	/**
	 * @设置 表单英文名（表名）
	 * @param tableName
	 */
	public void setTableName(String tableName) {
	 	this.tableName = tableName;
	}
	/**
	 * @return 是否必填0-否1-是
	 */
	public String getIsMust() {
	 	return isMust;
	}
	/**
	 * @设置 是否必填0-否1-是
	 * @param isMust
	 */
	public void setIsMust(String isMust) {
	 	this.isMust = isMust;
	}
	/**
	 * @return 所属模块 1 客户  
	 */
	public String getTableType() {
	 	return tableType;
	}
	/**
	 * @设置 所属模块 1 客户  
	 * @param tableType
	 */
	public void setTableType(String tableType) {
	 	this.tableType = tableType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}