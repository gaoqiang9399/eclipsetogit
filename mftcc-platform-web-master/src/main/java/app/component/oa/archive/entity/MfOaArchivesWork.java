package app.component.oa.archive.entity;
import app.base.BaseDomain;
/**
* Title: MfOaArchivesWork.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 22 17:01:09 CST 2017
* @version：1.0
**/
public class MfOaArchivesWork extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String workId;//工作经历ID
	private String baseId;//基础信息ID
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private String workContent;//工作内容
	private String jobTitle;//职位
	private Double salaryMonth;//月薪
	private String confirmPerson;//证明人
	private String comfirmTel;//证明人电话
	private String companyName;//公司名称
	private String dimissionReason;//离职原因

	/**
	 * @return 工作经历ID
	 */
	public String getWorkId() {
	 	return workId;
	}
	/**
	 * @设置 工作经历ID
	 * @param workId
	 */
	public void setWorkId(String workId) {
	 	this.workId = workId;
	}
	/**
	 * @return 开始日期
	 */
	public String getBeginDate() {
	 	return beginDate;
	}
	/**
	 * @设置 开始日期
	 * @param beginDate
	 */
	public void setBeginDate(String beginDate) {
	 	this.beginDate = beginDate;
	}
	/**
	 * @return 结束日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 结束日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 工作内容
	 */
	public String getWorkContent() {
	 	return workContent;
	}
	/**
	 * @设置 工作内容
	 * @param workContent
	 */
	public void setWorkContent(String workContent) {
	 	this.workContent = workContent;
	}
	/**
	 * @return 职位
	 */
	public String getJobTitle() {
	 	return jobTitle;
	}
	/**
	 * @设置 职位
	 * @param jobTitle
	 */
	public void setJobTitle(String jobTitle) {
	 	this.jobTitle = jobTitle;
	}
	/**
	 * @return 月薪
	 */
	public Double getSalaryMonth() {
	 	return salaryMonth;
	}
	/**
	 * @设置 月薪
	 * @param salaryMonth
	 */
	public void setSalaryMonth(Double salaryMonth) {
	 	this.salaryMonth = salaryMonth;
	}
	/**
	 * @return 证明人
	 */
	public String getConfirmPerson() {
	 	return confirmPerson;
	}
	/**
	 * @设置 证明人
	 * @param confirmPerson
	 */
	public void setConfirmPerson(String confirmPerson) {
	 	this.confirmPerson = confirmPerson;
	}
	/**
	 * @return 证明人电话
	 */
	public String getComfirmTel() {
	 	return comfirmTel;
	}
	/**
	 * @设置 证明人电话
	 * @param comfirmTel
	 */
	public void setComfirmTel(String comfirmTel) {
	 	this.comfirmTel = comfirmTel;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDimissionReason() {
		return dimissionReason;
	}
	public void setDimissionReason(String dimissionReason) {
		this.dimissionReason = dimissionReason;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
}