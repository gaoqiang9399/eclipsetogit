package app.component.pledge.entity;
import app.base.BaseDomain;
/**
* Title: MfPledgeDynamicForm.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Nov 17 15:37:26 CST 2016
* @version：1.0
**/
public class MfPledgeDynamicForm extends BaseDomain {
	private String pleFormNo;//押品动态表单编号
	private String pleFormName;//押品动态表单名称
	private String useFlag;//是否启用 0，否；1,是。
	private String remark;//备注
	private String motherAddFormId;//母版新增表单编号
	private String motherDetailFormId;//母版详情表单编号
	private String addFormId;//新增表单编号
	private String detailFormId;//详情表单编号
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String increId;//自增

	/**
	 * @return 押品动态表单编号
	 */
	public String getPleFormNo() {
	 	return pleFormNo;
	}
	/**
	 * @设置 押品动态表单编号
	 * @param pleFormNo
	 */
	public void setPleFormNo(String pleFormNo) {
	 	this.pleFormNo = pleFormNo;
	}
	/**
	 * @return 押品动态表单名称
	 */
	public String getPleFormName() {
	 	return pleFormName;
	}
	/**
	 * @设置 押品动态表单名称
	 * @param pleFormName
	 */
	public void setPleFormName(String pleFormName) {
	 	this.pleFormName = pleFormName;
	}
	/**
	 * @return 是否启用 0，否；1,是。
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用 0，否；1,是。
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
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
	 * @return 母版新增表单编号
	 */
	public String getMotherAddFormId() {
	 	return motherAddFormId;
	}
	/**
	 * @设置 母版新增表单编号
	 * @param motherAddFormId
	 */
	public void setMotherAddFormId(String motherAddFormId) {
	 	this.motherAddFormId = motherAddFormId;
	}
	/**
	 * @return 母版详情表单编号
	 */
	public String getMotherDetailFormId() {
	 	return motherDetailFormId;
	}
	/**
	 * @设置 母版详情表单编号
	 * @param motherDetailFormId
	 */
	public void setMotherDetailFormId(String motherDetailFormId) {
	 	this.motherDetailFormId = motherDetailFormId;
	}
	/**
	 * @return 新增表单编号
	 */
	public String getAddFormId() {
	 	return addFormId;
	}
	/**
	 * @设置 新增表单编号
	 * @param addFormId
	 */
	public void setAddFormId(String addFormId) {
	 	this.addFormId = addFormId;
	}
	/**
	 * @return 详情表单编号
	 */
	public String getDetailFormId() {
	 	return detailFormId;
	}
	/**
	 * @设置 详情表单编号
	 * @param detailFormId
	 */
	public void setDetailFormId(String detailFormId) {
	 	this.detailFormId = detailFormId;
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
	public String getIncreId() {
		return increId;
	}
	public void setIncreId(String increId) {
		this.increId = increId;
	}
	
	
}