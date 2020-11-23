package app.component.oa.lawyer.entity;
import app.base.BaseDomain;

/**
* Title: MfOaLawyer.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jun 27 15:11:35 CST 2018
* @version：1.0
**/
public class MfOaLawyer extends BaseDomain {
	private String id;//id
	private String lawyerName;//律师名称
	private String idNum;//律师身份证号
	private String cusTel;//律师手机号
	private String premises;//律师事务所名称
	private String hireStartTime;//聘用开始日期
	private String hireEndTime;//聘用结束日期
	private String remark;//备注
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lastModTime;//最后一次修改时间
	private String regDate;//登记日期
	private String lstModDate;//最后一次修改日期
	private String ext1;//保留字段
	private String ext2;//
	private String ext3;//
	private Integer extInt;//
	private Double extNum;//

	/**
	 * @return id
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 id
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 律师名称
	 */
	public String getLawyerName() {
	 	return lawyerName;
	}
	/**
	 * @设置 律师名称
	 * @param lawyerName
	 */
	public void setLawyerName(String lawyerName) {
	 	this.lawyerName = lawyerName;
	}
	/**
	 * @return 律师身份证号
	 */
	public String getIdNum() {
	 	return idNum;
	}
	/**
	 * @设置 律师身份证号
	 * @param idNum
	 */
	public void setIdNum(String idNum) {
	 	this.idNum = idNum;
	}
	/**
	 * @return 律师手机号
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 律师手机号
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return 律师事务所名称
	 */
	public String getPremises() {
	 	return premises;
	}
	/**
	 * @设置 律师事务所名称
	 * @param premises
	 */
	public void setPremises(String premises) {
	 	this.premises = premises;
	}
	/**
	 * @return 聘用开始日期
	 */
	public String getHireStartTime() {
	 	return hireStartTime;
	}
	/**
	 * @设置 聘用开始日期
	 * @param hireStartTime
	 */
	public void setHireStartTime(String hireStartTime) {
	 	this.hireStartTime = hireStartTime;
	}
	/**
	 * @return 聘用结束日期
	 */
	public String getHireEndTime() {
	 	return hireEndTime;
	}
	/**
	 * @设置 聘用结束日期
	 * @param hireEndTime
	 */
	public void setHireEndTime(String hireEndTime) {
	 	this.hireEndTime = hireEndTime;
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
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
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
	 * @return 最后一次修改时间
	 */
	public String getLastModTime() {
	 	return lastModTime;
	}
	/**
	 * @设置 最后一次修改时间
	 * @param lastModTime
	 */
	public void setLastModTime(String lastModTime) {
	 	this.lastModTime = lastModTime;
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
	 * @return 最后一次修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 最后一次修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	/**
	 * @return 保留字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 保留字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public Integer getExtInt() {
	 	return extInt;
	}
	/**
	 * @设置 
	 * @param extInt
	 */
	public void setExtInt(Integer extInt) {
	 	this.extInt = extInt;
	}
	/**
	 * @return 
	 */
	public Double getExtNum() {
	 	return extNum;
	}
	/**
	 * @设置 
	 * @param extNum
	 */
	public void setExtNum(Double extNum) {
	 	this.extNum = extNum;
	}

	@Override
	public String toString() {
		return "MfOaLawyer{" +
				"id='" + id + '\'' +
				", lawyerName='" + lawyerName + '\'' +
				", idNum='" + idNum + '\'' +
				", cusTel='" + cusTel + '\'' +
				", premises='" + premises + '\'' +
				", hireStartTime='" + hireStartTime + '\'' +
				", hireEndTime='" + hireEndTime + '\'' +
				", remark='" + remark + '\'' +
				", opNo='" + opNo + '\'' +
				", opName='" + opName + '\'' +
				", brNo='" + brNo + '\'' +
				", brName='" + brName + '\'' +
				", regTime='" + regTime + '\'' +
				", lastModTime='" + lastModTime + '\'' +
				", regDate='" + regDate + '\'' +
				", lstModDate='" + lstModDate + '\'' +
				", ext1='" + ext1 + '\'' +
				", ext2='" + ext2 + '\'' +
				", ext3='" + ext3 + '\'' +
				", extInt=" + extInt +
				", extNum=" + extNum +
				'}';
	}
}