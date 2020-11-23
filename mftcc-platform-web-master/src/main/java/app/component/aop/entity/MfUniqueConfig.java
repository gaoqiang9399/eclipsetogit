package app.component.aop.entity;
import app.base.BaseDomain;
/**
* Title: MfUniqueConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 13 17:54:49 CST 2017
* @version：1.0
**/
public class MfUniqueConfig extends BaseDomain {
	private String configId;//唯一流水号
	private String executeClass;//执行类
	private String unType;//唯一类型
	private String parmObjClass;//参数实体类
	private String uniquevalAttrName;//唯一值属性名
	private String belongCusnoAttrName;//所属客户的客户编号属性名
	private String belongCusnameAttrName;//所属客户的客户名称属性名
	private String classNameZn;//实体中文名
	private String uniquevalAttrNameZn;//唯一值属性中文名
	private String realtionIdAttrName;//关联编号属性名
	private String belongPersonAttrName;//所属人名称的属性名
	private String executeClassUpdate;//'修改执行类';
	private String executeClassDelete;//'删除执行类';
	private String opType;//操作标示
	private String unTypeAttrName;//唯一类型属性名 用于获取同一个属性中存放不同类型的数据。


	/**
	 * @return 唯一流水号
	 */
	public String getConfigId() {
	 	return configId;
	}
	/**
	 * @设置 唯一流水号
	 * @param configId
	 */
	public void setConfigId(String configId) {
	 	this.configId = configId;
	}
	/**
	 * @return 执行类
	 */
	public String getExecuteClass() {
	 	return executeClass;
	}
	/**
	 * @设置 执行类
	 * @param executeClass
	 */
	public void setExecuteClass(String executeClass) {
	 	this.executeClass = executeClass;
	}
	/**
	 * @return 唯一类型
	 */
	public String getUnType() {
	 	return unType;
	}
	/**
	 * @设置 唯一类型
	 * @param unType
	 */
	public void setUnType(String unType) {
	 	this.unType = unType;
	}
	/**
	 * @return 参数实体类
	 */
	public String getParmObjClass() {
	 	return parmObjClass;
	}
	/**
	 * @设置 参数实体类
	 * @param parmObjClass
	 */
	public void setParmObjClass(String parmObjClass) {
	 	this.parmObjClass = parmObjClass;
	}
	/**
	 * @return 唯一值属性名
	 */
	public String getUniquevalAttrName() {
	 	return uniquevalAttrName;
	}
	/**
	 * @设置 唯一值属性名
	 * @param uniquevalAttrName
	 */
	public void setUniquevalAttrName(String uniquevalAttrName) {
	 	this.uniquevalAttrName = uniquevalAttrName;
	}
	/**
	 * @return 所属客户的客户编号属性名
	 */
	public String getBelongCusnoAttrName() {
	 	return belongCusnoAttrName;
	}
	/**
	 * @设置 所属客户的客户编号属性名
	 * @param belongCusnoAttrName
	 */
	public void setBelongCusnoAttrName(String belongCusnoAttrName) {
	 	this.belongCusnoAttrName = belongCusnoAttrName;
	}
	/**
	 * @return 所属客户的客户名称属性名
	 */
	public String getBelongCusnameAttrName() {
	 	return belongCusnameAttrName;
	}
	/**
	 * @设置 所属客户的客户名称属性名
	 * @param belongCusnameAttrName
	 */
	public void setBelongCusnameAttrName(String belongCusnameAttrName) {
	 	this.belongCusnameAttrName = belongCusnameAttrName;
	}
	/**
	 * @return 实体中文名
	 */
	public String getClassNameZn() {
	 	return classNameZn;
	}
	/**
	 * @设置 实体中文名
	 * @param classNameZn
	 */
	public void setClassNameZn(String classNameZn) {
	 	this.classNameZn = classNameZn;
	}
	/**
	 * @return 唯一值属性中文名
	 */
	public String getUniquevalAttrNameZn() {
	 	return uniquevalAttrNameZn;
	}
	/**
	 * @设置 唯一值属性中文名
	 * @param uniquevalAttrNameZn
	 */
	public void setUniquevalAttrNameZn(String uniquevalAttrNameZn) {
	 	this.uniquevalAttrNameZn = uniquevalAttrNameZn;
	}
	/**
	 * @return 关联编号属性名
	 */
	public String getRealtionIdAttrName() {
	 	return realtionIdAttrName;
	}
	/**
	 * @设置 关联编号属性名
	 * @param realtionIdAttrName
	 */
	public void setRealtionIdAttrName(String realtionIdAttrName) {
	 	this.realtionIdAttrName = realtionIdAttrName;
	}
	/**
	 * @return 所属人名称的属性名
	 */
	public String getBelongPersonAttrName() {
	 	return belongPersonAttrName;
	}
	/**
	 * @设置 所属人名称的属性名
	 * @param belongPersonAttrName
	 */
	public void setBelongPersonAttrName(String belongPersonAttrName) {
	 	this.belongPersonAttrName = belongPersonAttrName;
	}
	public String getExecuteClassUpdate() {
		return executeClassUpdate;
	}
	public void setExecuteClassUpdate(String executeClassUpdate) {
		this.executeClassUpdate = executeClassUpdate;
	}
	public String getExecuteClassDelete() {
		return executeClassDelete;
	}
	public void setExecuteClassDelete(String executeClassDelete) {
		this.executeClassDelete = executeClassDelete;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	public String getUnTypeAttrName() {
		return unTypeAttrName;
	}
	public void setUnTypeAttrName(String unTypeAttrName) {
		this.unTypeAttrName = unTypeAttrName;
	}
}