package app.component.oa.archive.entity;
import app.base.BaseDomain;
/**
* Title: MfOaArchivesEducation.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 22 17:27:33 CST 2017
* @version：1.0
**/
public class MfOaArchivesEducation extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String educationId;//教育情况ID
	private String baseId;//基础信息ID
	private String gradSchool;//毕业院校
	private String regDate;//入学时间
	private String gradDate;//毕业时间
	private String profassional;//专业
	private String education;//学历
	private String degree;//学位
	private String proDescribe;//学位
	private String secondProfassional;//第二专业
	private String cultivateWay;//培养方式：1-统招；2-自考；3-成教；4-其他；

	/**
	 * @return 教育情况ID
	 */
	public String getEducationId() {
	 	return educationId;
	}
	/**
	 * @设置 教育情况ID
	 * @param educationId
	 */
	public void setEducationId(String educationId) {
	 	this.educationId = educationId;
	}
	/**
	 * @return 毕业院校
	 */
	public String getGradSchool() {
	 	return gradSchool;
	}
	/**
	 * @设置 毕业院校
	 * @param gradSchool
	 */
	public void setGradSchool(String gradSchool) {
	 	this.gradSchool = gradSchool;
	}
	/**
	 * @return 入学时间
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 入学时间
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 毕业时间
	 */
	public String getGradDate() {
	 	return gradDate;
	}
	/**
	 * @设置 毕业时间
	 * @param gradDate
	 */
	public void setGradDate(String gradDate) {
	 	this.gradDate = gradDate;
	}
	/**
	 * @return 专业
	 */
	public String getProfassional() {
	 	return profassional;
	}
	/**
	 * @设置 专业
	 * @param profassional
	 */
	public void setProfassional(String profassional) {
	 	this.profassional = profassional;
	}
	/**
	 * @return 学历
	 */
	public String getEducation() {
	 	return education;
	}
	/**
	 * @设置 学历
	 * @param education
	 */
	public void setEducation(String education) {
	 	this.education = education;
	}
	/**
	 * @return 学位
	 */
	public String getDegree() {
	 	return degree;
	}
	/**
	 * @设置 学位
	 * @param degree
	 */
	public void setDegree(String degree) {
	 	this.degree = degree;
	}
	public String getProDescribe() {
		return proDescribe;
	}
	public void setProDescribe(String proDescribe) {
		this.proDescribe = proDescribe;
	}
	/**
	 * @return the secondProfassional
	 */
	public String getSecondProfassional() {
		return secondProfassional;
	}
	/**
	 * @param secondProfassional the secondProfassional to set
	 */
	public void setSecondProfassional(String secondProfassional) {
		this.secondProfassional = secondProfassional;
	}
	/**
	 * @return the cultivateWay
	 */
	public String getCultivateWay() {
		return cultivateWay;
	}
	/**
	 * @param cultivateWay the cultivateWay to set
	 */
	public void setCultivateWay(String cultivateWay) {
		this.cultivateWay = cultivateWay;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
}