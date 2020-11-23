package app.component.oa.consumable.entity;
import app.base.BaseDomain;
/**
* Title: MfOaConsClass.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 24 11:58:00 CST 2016
* @version：1.0
**/
public class MfOaConsClass extends BaseDomain {
	private String classId;//类别编号
	private String className;//类别名称
	private String superClassId;//父类编号
	private String unit;//计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	private String appType;//申领类型 1领用 2借用
	private Integer limitNum;//最低警戒库存
	private String remark;//备注
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记单位编号
	private String brName;//登记单位名
	private String createTime;//登记时间
	private String editTime;//最近修改时间

	/**
	 * @return 类别编号
	 */
	public String getClassId() {
	 	return classId;
	}
	/**
	 * @设置 类别编号
	 * @param classId
	 */
	public void setClassId(String classId) {
	 	this.classId = classId;
	}
	/**
	 * @return 类别名称
	 */
	public String getClassName() {
	 	return className;
	}
	/**
	 * @设置 类别名称
	 * @param className
	 */
	public void setClassName(String className) {
	 	this.className = className;
	}
	/**
	 * @return 父类编号
	 */
	public String getSuperClassId() {
	 	return superClassId;
	}
	/**
	 * @设置 父类编号
	 * @param superClassId
	 */
	public void setSuperClassId(String superClassId) {
	 	this.superClassId = superClassId;
	}
	/**
	 * @return 计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	 */
	public String getUnit() {
	 	return unit;
	}
	/**
	 * @设置 计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	 * @param unit
	 */
	public void setUnit(String unit) {
	 	this.unit = unit;
	}
	/**
	 * @return 申领类型 1领用 2借用
	 */
	public String getAppType() {
	 	return appType;
	}
	/**
	 * @设置 申领类型 1领用 2借用
	 * @param appType
	 */
	public void setAppType(String appType) {
	 	this.appType = appType;
	}
	/**
	 * @return 最低警戒库存
	 */
	public Integer getLimitNum() {
	 	return limitNum;
	}
	/**
	 * @设置 最低警戒库存
	 * @param limitNum
	 */
	public void setLimitNum(Integer limitNum) {
	 	this.limitNum = limitNum;
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
	 * @return 登记单位编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记单位编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记单位名
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记单位名
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 登记时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 最近修改时间
	 */
	public String getEditTime() {
	 	return editTime;
	}
	/**
	 * @设置 最近修改时间
	 * @param editTime
	 */
	public void setEditTime(String editTime) {
	 	this.editTime = editTime;
	}
}