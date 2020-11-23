package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: MfCollectionFormConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Mar 19 15:13:37 CST 2017
* @version：1.0
**/
public class MfCollectionFormConfig extends BaseDomain {
	private String id;//流水号
	private String tableDes;//表中文名
	private String tableName;//表名
	private String formType;//配置表单类型（短信/电话...）
	private String formBaseType;//基础表单类型
	private String useFlag;//是否启用
	private String isBase;//是否基础表单
	private String isMust;//是否必须
	private String action;//Action
	private String showType;//展示方式
	private Integer sort;//显示顺序
	private String addModel;//新增模板
	private String addModelDef;//新增模板描述
	private String showModel;//查看模板
	private String showModelDef;//查看模板描述
	private String ext1;//
	private String ext2;//
	private String ext3;//

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 表中文名
	 */
	public String getTableDes() {
	 	return tableDes;
	}
	/**
	 * @设置 表中文名
	 * @param tableDes
	 */
	public void setTableDes(String tableDes) {
	 	this.tableDes = tableDes;
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
	 * @return 配置表单类型（短信/电话...）
	 */
	public String getFormType() {
	 	return formType;
	}
	/**
	 * @设置 配置表单类型（短信/电话...）
	 * @param formType
	 */
	public void setFormType(String formType) {
	 	this.formType = formType;
	}
	/**
	 * @return 基础表单类型
	 */
	public String getFormBaseType() {
	 	return formBaseType;
	}
	/**
	 * @设置 基础表单类型
	 * @param formBaseType
	 */
	public void setFormBaseType(String formBaseType) {
	 	this.formBaseType = formBaseType;
	}
	/**
	 * @return 是否启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 是否基础表单
	 */
	public String getIsBase() {
	 	return isBase;
	}
	/**
	 * @设置 是否基础表单
	 * @param isBase
	 */
	public void setIsBase(String isBase) {
	 	this.isBase = isBase;
	}
	/**
	 * @return 是否必须
	 */
	public String getIsMust() {
	 	return isMust;
	}
	/**
	 * @设置 是否必须
	 * @param isMust
	 */
	public void setIsMust(String isMust) {
	 	this.isMust = isMust;
	}
	/**
	 * @return Action
	 */
	public String getAction() {
	 	return action;
	}
	/**
	 * @设置 Action
	 * @param action
	 */
	public void setAction(String action) {
	 	this.action = action;
	}
	/**
	 * @return 展示方式
	 */
	public String getShowType() {
	 	return showType;
	}
	/**
	 * @设置 展示方式
	 * @param showType
	 */
	public void setShowType(String showType) {
	 	this.showType = showType;
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
	 * @return 新增模板
	 */
	public String getAddModel() {
	 	return addModel;
	}
	/**
	 * @设置 新增模板
	 * @param addModel
	 */
	public void setAddModel(String addModel) {
	 	this.addModel = addModel;
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
	 * @return 查看模板
	 */
	public String getShowModel() {
	 	return showModel;
	}
	/**
	 * @设置 查看模板
	 * @param showModel
	 */
	public void setShowModel(String showModel) {
	 	this.showModel = showModel;
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
}