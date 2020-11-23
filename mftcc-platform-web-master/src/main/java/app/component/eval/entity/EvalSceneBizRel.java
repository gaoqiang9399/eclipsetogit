package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalSceneBizRel.java
* Description:
* @author：@dhcc.com.cn
* @Fri Sep 02 05:37:40 GMT 2016
* @version：1.0
**/
public class EvalSceneBizRel extends BaseDomain {
	private String relNo;//关联编号
	private String evalType;//评级类型
	private String evalScenceNo;//评级配置场景编号
	private String formal;//客户标识
	private String prodNo;//业务品种编号
	private String orgNo;//登记机构号
	private String orgName;//登记机构名称
	private String regNo;//登记人
	private String regName;//登记人名称
	private String lstOrgNo;//最新登记机构号
	private String lstOrgName;//最新登记机构名称
	private String lstRegNo;//最新登记人
	private String lstRegName;//最新登记人名称
	private String regDate;//登记日期
	private String lstDate;//更新日期

	/**
	 * @return 关联编号
	 */
	public String getRelNo() {
	 	return relNo;
	}
	/**
	 * @设置 关联编号
	 * @param relNo
	 */
	public void setRelNo(String relNo) {
	 	this.relNo = relNo;
	}
	/**
	 * @return 评级类型
	 */
	public String getEvalType() {
	 	return evalType;
	}
	/**
	 * @设置 评级类型
	 * @param evalType
	 */
	public void setEvalType(String evalType) {
	 	this.evalType = evalType;
	}
	/**
	 * @return 评级配置场景编号
	 */
	public String getEvalScenceNo() {
	 	return evalScenceNo;
	}
	/**
	 * @设置 评级配置场景编号
	 * @param evalScenceNo
	 */
	public void setEvalScenceNo(String evalScenceNo) {
	 	this.evalScenceNo = evalScenceNo;
	}
	/**
	 * @return 客户标识
	 */
	public String getFormal() {
	 	return formal;
	}
	/**
	 * @设置 客户标识
	 * @param formal
	 */
	public void setFormal(String formal) {
	 	this.formal = formal;
	}
	/**
	 * @return 业务品种编号
	 */
	public String getProdNo() {
	 	return prodNo;
	}
	/**
	 * @设置 业务品种编号
	 * @param prodNo
	 */
	public void setProdNo(String prodNo) {
	 	this.prodNo = prodNo;
	}
	/**
	 * @return 登记机构号
	 */
	public String getOrgNo() {
	 	return orgNo;
	}
	/**
	 * @设置 登记机构号
	 * @param orgNo
	 */
	public void setOrgNo(String orgNo) {
	 	this.orgNo = orgNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getOrgName() {
	 	return orgName;
	}
	/**
	 * @设置 登记机构名称
	 * @param orgName
	 */
	public void setOrgName(String orgName) {
	 	this.orgName = orgName;
	}
	/**
	 * @return 登记人
	 */
	public String getRegNo() {
	 	return regNo;
	}
	/**
	 * @设置 登记人
	 * @param regNo
	 */
	public void setRegNo(String regNo) {
	 	this.regNo = regNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegName() {
	 	return regName;
	}
	/**
	 * @设置 登记人名称
	 * @param regName
	 */
	public void setRegName(String regName) {
	 	this.regName = regName;
	}
	/**
	 * @return 最新登记机构号
	 */
	public String getLstOrgNo() {
	 	return lstOrgNo;
	}
	/**
	 * @设置 最新登记机构号
	 * @param lstOrgNo
	 */
	public void setLstOrgNo(String lstOrgNo) {
	 	this.lstOrgNo = lstOrgNo;
	}
	/**
	 * @return 最新登记机构名称
	 */
	public String getLstOrgName() {
	 	return lstOrgName;
	}
	/**
	 * @设置 最新登记机构名称
	 * @param lstOrgName
	 */
	public void setLstOrgName(String lstOrgName) {
	 	this.lstOrgName = lstOrgName;
	}
	/**
	 * @return 最新登记人
	 */
	public String getLstRegNo() {
	 	return lstRegNo;
	}
	/**
	 * @设置 最新登记人
	 * @param lstRegNo
	 */
	public void setLstRegNo(String lstRegNo) {
	 	this.lstRegNo = lstRegNo;
	}
	/**
	 * @return 最新登记人名称
	 */
	public String getLstRegName() {
	 	return lstRegName;
	}
	/**
	 * @设置 最新登记人名称
	 * @param lstRegName
	 */
	public void setLstRegName(String lstRegName) {
	 	this.lstRegName = lstRegName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 更新日期
	 */
	public String getLstDate() {
	 	return lstDate;
	}
	/**
	 * @设置 更新日期
	 * @param lstDate
	 */
	public void setLstDate(String lstDate) {
	 	this.lstDate = lstDate;
	}
}