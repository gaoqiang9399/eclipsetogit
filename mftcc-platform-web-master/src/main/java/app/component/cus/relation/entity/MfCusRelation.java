package app.component.cus.relation.entity;
import app.base.BaseDomain;
/**
* Title: MfCusRelation.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Oct 11 15:47:03 CST 2016
* @version：1.0
**/
public class MfCusRelation extends BaseDomain {
	private String relationId;//关联关系ID
	private String cusNo;//客户ID
	private String cusName;//客户姓名
	private String tabName;//客户所在表名
	private String tabNameZn;//客户所在表中文名
	private String columnName;//客户证件类型
	private String columnNameZn;//客户证件类型中文名
	private String cusNo1;//关联客户ID
	private String cusName1;//客户2姓名
	private String tabName1;//关联客户所在表名
	private String tabNameZn1;//关联客户所在表中文名
	private String columnName1;//关联客户证件类型
	private String columnNameZn1;//关联客户证件类行中文名
	private String relationType;//关联关系类型
	private String relationTypeName;//关联关系类型描述
	private String idType;//关联证件类型
	private String idNum;//关联证件号码
	private String remark;//备注
	private String isHandle;//是否手动录入
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String cusBaseType;//客户基本类型 null企业客户  2个人客户

	/**
	 * @return 关联关系ID
	 */
	public String getRelationId() {
	 	return relationId;
	}
	/**
	 * @设置 关联关系ID
	 * @param relationId
	 */
	public void setRelationId(String relationId) {
	 	this.relationId = relationId;
	}
	/**
	 * @return 客户1ID
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户1ID
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户1姓名
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户1姓名
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 
	 */
	public String getTabName() {
	 	return tabName;
	}
	/**
	 * @设置 
	 * @param tabName
	 */
	public void setTabName(String tabName) {
	 	this.tabName = tabName;
	}
	/**
	 * @return 
	 */
	public String getTabNameZn() {
	 	return tabNameZn;
	}
	/**
	 * @设置 
	 * @param tabNameZn
	 */
	public void setTabNameZn(String tabNameZn) {
	 	this.tabNameZn = tabNameZn;
	}
	/**
	 * @return 
	 */
	public String getColumnName() {
	 	return columnName;
	}
	/**
	 * @设置 
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
	 	this.columnName = columnName;
	}
	/**
	 * @return 
	 */
	public String getColumnNameZn() {
	 	return columnNameZn;
	}
	/**
	 * @设置 
	 * @param columnNameZn
	 */
	public void setColumnNameZn(String columnNameZn) {
	 	this.columnNameZn = columnNameZn;
	}
	/**
	 * @return 客户2ID
	 */
	public String getCusNo1() {
	 	return cusNo1;
	}
	/**
	 * @设置 客户2ID
	 * @param cusNo1
	 */
	public void setCusNo1(String cusNo1) {
	 	this.cusNo1 = cusNo1;
	}
	/**
	 * @return 客户2姓名
	 */
	public String getCusName1() {
	 	return cusName1;
	}
	/**
	 * @设置 客户2姓名
	 * @param cusName1
	 */
	public void setCusName1(String cusName1) {
	 	this.cusName1 = cusName1;
	}
	/**
	 * @return 
	 */
	public String getTabName1() {
	 	return tabName1;
	}
	/**
	 * @设置 
	 * @param tabName1
	 */
	public void setTabName1(String tabName1) {
	 	this.tabName1 = tabName1;
	}
	/**
	 * @return 
	 */
	public String getTabNameZn1() {
	 	return tabNameZn1;
	}
	/**
	 * @设置 
	 * @param tabNameZn1
	 */
	public void setTabNameZn1(String tabNameZn1) {
	 	this.tabNameZn1 = tabNameZn1;
	}
	/**
	 * @return 
	 */
	public String getColumnName1() {
	 	return columnName1;
	}
	/**
	 * @设置 
	 * @param columnName1
	 */
	public void setColumnName1(String columnName1) {
	 	this.columnName1 = columnName1;
	}
	/**
	 * @return 
	 */
	public String getColumnNameZn1() {
	 	return columnNameZn1;
	}
	/**
	 * @设置 
	 * @param columnNameZn1
	 */
	public void setColumnNameZn1(String columnNameZn1) {
	 	this.columnNameZn1 = columnNameZn1;
	}
	/**
	 * @return 关联关系类型（0-自定义关联关系1-相同股东关系2-股东关系3-相同法人关系4-相同关键人5-集团客户6-担保关系）
	 */
	public String getRelationType() {
	 	return relationType;
	}
	/**
	 * @设置 关联关系类型（0-自定义关联关系1-相同股东关系2-股东关系3-相同法人关系4-相同关键人5-集团客户6-担保关系）
	 * @param relationType
	 */
	public void setRelationType(String relationType) {
	 	this.relationType = relationType;
	}
	/**
	 * @return 关联关系类型名称
	 */
	public String getRelationTypeName() {
	 	return relationTypeName;
	}
	/**
	 * @设置 关联关系类型名称
	 * @param relationTypeName
	 */
	public void setRelationTypeName(String relationTypeName) {
	 	this.relationTypeName = relationTypeName;
	}
	/**
	 * @return 关联证件类型
	 */
	public String getIdType() {
	 	return idType;
	}
	/**
	 * @设置 关联证件类型
	 * @param idType
	 */
	public void setIdType(String idType) {
	 	this.idType = idType;
	}
	/**
	 * @return 关联证件号码
	 */
	public String getIdNum() {
	 	return idNum;
	}
	/**
	 * @设置 关联证件号码
	 * @param idNum
	 */
	public void setIdNum(String idNum) {
	 	this.idNum = idNum;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 是否手动录入 0-自动 1-手动
	 */
	public String getIsHandle() {
	 	return isHandle;
	}
	/**
	 * @设置 是否手动录入 0-自动 1-手动
	 * @param isHandle
	 */
	public void setIsHandle(String isHandle) {
	 	this.isHandle = isHandle;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
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
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getCusBaseType() {
		return cusBaseType;
	}
	public void setCusBaseType(String cusBaseType) {
		this.cusBaseType = cusBaseType;
	}
	
	
}