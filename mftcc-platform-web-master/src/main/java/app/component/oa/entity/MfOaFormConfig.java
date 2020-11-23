package app.component.oa.entity;
import app.base.BaseDomain;
/**
* Title: MfOaFormConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 23 13:52:13 CST 2017
* @version：1.0
**/
public class MfOaFormConfig extends BaseDomain {
	private String id;//流水号
	private String tableDes;//表单中文名（表中文名）
	private String tableName;//表单英文名（表名）
	private String formType;//表单类型：1-人事档案；2-入职；
	private String ifBase;//是否基础表单0-否1-是
	private String useFlag;//是否启用0-备用1-启用
	private String action;//对应action
	private String showType;//展示方式1表单2列表
	private String addModelBase;//新增基础表单
	private String addModel;//新增编辑后表单
	private String showModelBase;//详情基础表单
	private String showModel;//详情编辑后表单
	private String listModelBase;//列表基础表单
	private String listModel;//列表编辑后表单

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
	 * @return 表单类型：1-人事档案；2-入职；
	 */
	public String getFormType() {
	 	return formType;
	}
	/**
	 * @设置 表单类型：1-人事档案；2-入职；
	 * @param formType
	 */
	public void setFormType(String formType) {
	 	this.formType = formType;
	}
	/**
	 * @return 是否基础表单0-否1-是
	 */
	public String getIfBase() {
	 	return ifBase;
	}
	/**
	 * @设置 是否基础表单0-否1-是
	 * @param ifBase
	 */
	public void setIfBase(String ifBase) {
	 	this.ifBase = ifBase;
	}
	/**
	 * @return 是否启用0-备用1-启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用0-备用1-启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 对应action
	 */
	public String getAction() {
	 	return action;
	}
	/**
	 * @设置 对应action
	 * @param action
	 */
	public void setAction(String action) {
	 	this.action = action;
	}
	/**
	 * @return 展示方式1表单2列表
	 */
	public String getShowType() {
	 	return showType;
	}
	/**
	 * @设置 展示方式1表单2列表
	 * @param showType
	 */
	public void setShowType(String showType) {
	 	this.showType = showType;
	}
	/**
	 * @return 新增基础表单
	 */
	public String getAddModelBase() {
	 	return addModelBase;
	}
	/**
	 * @设置 新增基础表单
	 * @param addModelBase
	 */
	public void setAddModelBase(String addModelBase) {
	 	this.addModelBase = addModelBase;
	}
	/**
	 * @return 新增编辑后表单
	 */
	public String getAddModel() {
	 	return addModel;
	}
	/**
	 * @设置 新增编辑后表单
	 * @param addModel
	 */
	public void setAddModel(String addModel) {
	 	this.addModel = addModel;
	}
	/**
	 * @return 详情基础表单
	 */
	public String getShowModelBase() {
	 	return showModelBase;
	}
	/**
	 * @设置 详情基础表单
	 * @param showModelBase
	 */
	public void setShowModelBase(String showModelBase) {
	 	this.showModelBase = showModelBase;
	}
	/**
	 * @return 详情编辑后表单
	 */
	public String getShowModel() {
	 	return showModel;
	}
	/**
	 * @设置 详情编辑后表单
	 * @param showModel
	 */
	public void setShowModel(String showModel) {
	 	this.showModel = showModel;
	}
	/**
	 * @return 列表基础表单
	 */
	public String getListModelBase() {
	 	return listModelBase;
	}
	/**
	 * @设置 列表基础表单
	 * @param listModelBase
	 */
	public void setListModelBase(String listModelBase) {
	 	this.listModelBase = listModelBase;
	}
	/**
	 * @return 列表编辑后表单
	 */
	public String getListModel() {
	 	return listModel;
	}
	/**
	 * @设置 列表编辑后表单
	 * @param listModel
	 */
	public void setListModel(String listModel) {
	 	this.listModel = listModel;
	}
}