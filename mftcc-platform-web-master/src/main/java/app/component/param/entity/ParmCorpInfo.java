package app.component.param.entity;
import app.base.BaseDomain;
/**
* Title: ParmCorpInfo.java
* Description:
* @author：@dhcc.com.cn
* @Thu Aug 11 05:40:43 GMT 2016
* @version：1.0
**/
public class ParmCorpInfo extends BaseDomain {
	private String corpNo;//编号
	private String corpName;//公司名称
	private String legalReps;//法定代表人
	private String corpAddr;//公司地址
	private String corpPerName;//公司联系人名称
	private String corpMobileTel;//公司联系人电话
	private String fax;//传真
	private String belongOrgNo;//所属机构号
	private String belongOrgName;//所属机构名称
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
	 * @return 编号
	 */
	public String getCorpNo() {
	 	return corpNo;
	}
	/**
	 * @设置 编号
	 * @param corpNo
	 */
	public void setCorpNo(String corpNo) {
	 	this.corpNo = corpNo;
	}
	/**
	 * @return 公司名称
	 */
	public String getCorpName() {
	 	return corpName;
	}
	/**
	 * @设置 公司名称
	 * @param corpName
	 */
	public void setCorpName(String corpName) {
	 	this.corpName = corpName;
	}
	/**
	 * @return 法定代表人
	 */
	public String getLegalReps() {
	 	return legalReps;
	}
	/**
	 * @设置 法定代表人
	 * @param legalReps
	 */
	public void setLegalReps(String legalReps) {
	 	this.legalReps = legalReps;
	}
	/**
	 * @return 公司地址
	 */
	public String getCorpAddr() {
	 	return corpAddr;
	}
	/**
	 * @设置 公司地址
	 * @param corpAddr
	 */
	public void setCorpAddr(String corpAddr) {
	 	this.corpAddr = corpAddr;
	}
	/**
	 * @return 公司联系人名称
	 */
	public String getCorpPerName() {
	 	return corpPerName;
	}
	/**
	 * @设置 公司联系人名称
	 * @param corpPerName
	 */
	public void setCorpPerName(String corpPerName) {
	 	this.corpPerName = corpPerName;
	}
	/**
	 * @return 公司联系人电话
	 */
	public String getCorpMobileTel() {
	 	return corpMobileTel;
	}
	/**
	 * @设置 公司联系人电话
	 * @param corpMobileTel
	 */
	public void setCorpMobileTel(String corpMobileTel) {
	 	this.corpMobileTel = corpMobileTel;
	}
	/**
	 * @return 传真
	 */
	public String getFax() {
	 	return fax;
	}
	/**
	 * @设置 传真
	 * @param fax
	 */
	public void setFax(String fax) {
	 	this.fax = fax;
	}
	/**
	 * @return 所属机构号
	 */
	public String getBelongOrgNo() {
	 	return belongOrgNo;
	}
	/**
	 * @设置 所属机构号
	 * @param belongOrgNo
	 */
	public void setBelongOrgNo(String belongOrgNo) {
	 	this.belongOrgNo = belongOrgNo;
	}
	/**
	 * @return 所属机构名称
	 */
	public String getBelongOrgName() {
	 	return belongOrgName;
	}
	/**
	 * @设置 所属机构名称
	 * @param belongOrgName
	 */
	public void setBelongOrgName(String belongOrgName) {
	 	this.belongOrgName = belongOrgName;
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