package app.component.auth.entity;
import app.base.BaseDomain;
/**
* Title: MfDynamicForm.java
* Description:授信申请动态表单
* @author：LJW
* @Wed Feb 22 18:08:09 CST 2017
* @version：1.0
**/
public class MfDynamicForm extends BaseDomain {
	private Integer id;//
	private String formNo;//动态表单编号
	private String formName;//动态表单名称
	private String motherNewFormNo;//母版新增表单编号
	private String motherDetailFormNo;//母版详情表单编号
	private String newFormNo;//新增表单编号
	private String detailFormNo;//详情表单编号
	private String fromType;//动态表单类型
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String useFlag;//是否启用:0 否,1 是
	private String remak;//备注

	/**
	 * @return 
	 */
	public Integer getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(Integer id) {
	 	this.id = id;
	}
	/**
	 * @return 动态表单编号
	 */
	public String getFormNo() {
	 	return formNo;
	}
	/**
	 * @设置 动态表单编号
	 * @param formNo
	 */
	public void setFormNo(String formNo) {
	 	this.formNo = formNo;
	}
	/**
	 * @return 动态表单名称
	 */
	public String getFormName() {
	 	return formName;
	}
	/**
	 * @设置 动态表单名称
	 * @param formName
	 */
	public void setFormName(String formName) {
	 	this.formName = formName;
	}
	/**
	 * @return 母版新增表单编号
	 */
	public String getMotherNewFormNo() {
	 	return motherNewFormNo;
	}
	/**
	 * @设置 母版新增表单编号
	 * @param motherNewFormNo
	 */
	public void setMotherNewFormNo(String motherNewFormNo) {
	 	this.motherNewFormNo = motherNewFormNo;
	}
	/**
	 * @return 母版详情表单编号
	 */
	public String getMotherDetailFormNo() {
	 	return motherDetailFormNo;
	}
	/**
	 * @设置 母版详情表单编号
	 * @param motherDetailFormNo
	 */
	public void setMotherDetailFormNo(String motherDetailFormNo) {
	 	this.motherDetailFormNo = motherDetailFormNo;
	}
	/**
	 * @return 新增表单编号
	 */
	public String getNewFormNo() {
	 	return newFormNo;
	}
	/**
	 * @设置 新增表单编号
	 * @param newFormNo
	 */
	public void setNewFormNo(String newFormNo) {
	 	this.newFormNo = newFormNo;
	}
	/**
	 * @return 详情表单编号
	 */
	public String getDetailFormNo() {
	 	return detailFormNo;
	}
	/**
	 * @设置 详情表单编号
	 * @param detailFormNo
	 */
	public void setDetailFormNo(String detailFormNo) {
	 	this.detailFormNo = detailFormNo;
	}
	/**
	 * @return 动态表单类型
	 */
	public String getFromType() {
	 	return fromType;
	}
	/**
	 * @设置 动态表单类型
	 * @param fromType
	 */
	public void setFromType(String fromType) {
	 	this.fromType = fromType;
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
	/**
	 * @return 是否启用:0 否,1 是
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用:0 否,1 是
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 备注
	 */
	public String getRemak() {
	 	return remak;
	}
	/**
	 * @设置 备注
	 * @param remak
	 */
	public void setRemak(String remak) {
	 	this.remak = remak;
	}
}