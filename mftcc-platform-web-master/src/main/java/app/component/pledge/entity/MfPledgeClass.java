package app.component.pledge.entity;
import app.base.BaseDomain;
/**
* Title: MfPledgeClass.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Sep 20 11:18:22 CST 2016
* @version：1.0
**/
public class MfPledgeClass extends BaseDomain {
	private String classNo;// 类别编号eg:0102。
	private String className;//类别名称
	private String useFlag;//是否启用 0，否；1,是。
	private String remark;//备注
	private String cid;//流水号：唯一标示
	private String motherAddFormId;//母版新增表单编号
	private String motherDetailFormId;//母版详情表单编号
	private String addFormId;//新增表单编号
	private String detailFormId;//详情表单编号
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return  类别编号eg:0102。
	 */
	public String getClassNo() {
	 	return classNo;
	}
	/**
	 * @设置  类别编号eg:0102。
	 * @param classNo
	 */
	public void setClassNo(String classNo) {
	 	this.classNo = classNo;
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
	 * @return 流水号：唯一标示
	 */
	public String getCid() {
	 	return cid;
	}
	/**
	 * @设置 流水号：唯一标示
	 * @param cid
	 */
	public void setCid(String cid) {
	 	this.cid = cid;
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
}