/**
 * Copyright (C)  版权所有
 * 文件名： MfPledgeBigInfo.java
 * 包名： app.component.pledge.entity
 * 说明：
 * @author YuShuai
 * @date 2017-5-10 下午7:01:45
 * @version V1.0
 */ 
package app.component.pledge.entity;
/**
 * 类名： MfPledgeBigInfo
 * 描述：押品基本信息和评估信息一个大实体
 * @author YuShuai
 * @date 2017-5-10 下午7:01:45
 *
 *
 */
public class MfPledgeBigInfo {
	private String pledgeNo;//押品编号
	private String pledgeShowNo;
	private String pledgeName;//押品名称
	private String cusName;
	private String cusNo;
	private String classNo;
	private String className;
	private String certifiacteName;//押品所属人  同cusName
	private String pledgeMethod;//担保类别
	private String pledgeMethodName;
	private String regOrgName;
	private String regOrgNo;
	private String regCusNo;
	private String regCusName;
	private String lstOrgNo;
	private String lstOrgName;
	private String lstRegNo;
	private String lstRegName;
	private String regDate;
	private String lstDate;
	
	
	
	private Double evalAmout;//评估价值
	private String evalDate;//评估基准日
	private Double mortRate;//抵押率
	
	private String approve;
	private String approveIdea;
	
	
	
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public String getApproveIdea() {
		return approveIdea;
	}
	public void setApproveIdea(String approveIdea) {
		this.approveIdea = approveIdea;
	}
	public String getPledgeMethodName() {
		return pledgeMethodName;
	}
	public void setPledgeMethodName(String pledgeMethodName) {
		this.pledgeMethodName = pledgeMethodName;
	}
	public String getPledgeNo() {
		return pledgeNo;
	}
	public void setPledgeNo(String pledgeNo) {
		this.pledgeNo = pledgeNo;
	}
	public String getPledgeShowNo() {
		return pledgeShowNo;
	}
	public void setPledgeShowNo(String pledgeShowNo) {
		this.pledgeShowNo = pledgeShowNo;
	}
	public String getPledgeName() {
		return pledgeName;
	}
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCertifiacteName() {
		return certifiacteName;
	}
	public void setCertifiacteName(String certifiacteName) {
		this.certifiacteName = certifiacteName;
	}
	public String getPledgeMethod() {
		return pledgeMethod;
	}
	public void setPledgeMethod(String pledgeMethod) {
		this.pledgeMethod = pledgeMethod;
	}
	public String getRegOrgName() {
		return regOrgName;
	}
	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
	}
	public String getRegOrgNo() {
		return regOrgNo;
	}
	public void setRegOrgNo(String regOrgNo) {
		this.regOrgNo = regOrgNo;
	}
	public String getRegCusNo() {
		return regCusNo;
	}
	public void setRegCusNo(String regCusNo) {
		this.regCusNo = regCusNo;
	}
	public String getRegCusName() {
		return regCusName;
	}
	public void setRegCusName(String regCusName) {
		this.regCusName = regCusName;
	}
	public String getLstOrgNo() {
		return lstOrgNo;
	}
	public void setLstOrgNo(String lstOrgNo) {
		this.lstOrgNo = lstOrgNo;
	}
	public String getLstOrgName() {
		return lstOrgName;
	}
	public void setLstOrgName(String lstOrgName) {
		this.lstOrgName = lstOrgName;
	}
	public String getLstRegNo() {
		return lstRegNo;
	}
	public void setLstRegNo(String lstRegNo) {
		this.lstRegNo = lstRegNo;
	}
	public String getLstRegName() {
		return lstRegName;
	}
	public void setLstRegName(String lstRegName) {
		this.lstRegName = lstRegName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLstDate() {
		return lstDate;
	}
	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public Double getEvalAmout() {
		return evalAmout;
	}
	public void setEvalAmout(Double evalAmout) {
		this.evalAmout = evalAmout;
	}
	public String getEvalDate() {
		return evalDate;
	}
	public void setEvalDate(String evalDate) {
		this.evalDate = evalDate;
	}
	public Double getMortRate() {
		return mortRate;
	}
	public void setMortRate(Double mortRate) {
		this.mortRate = mortRate;
	}
	
	
}
