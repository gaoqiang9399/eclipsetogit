package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableAccountCheckInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 16 18:03:18 CST 2017
* @version：1.0
**/
public class MfMoveableAccountCheckInfo extends BaseDomain {
	private String accountCheckId;//对账结果编号
	private String checkDate;//对账时间
	private String checkOpNo;//对账人
	private String checkOpName;//对账人名称
	private String checkResultType;//对账结果
	private String unusualRemark;//异常情况
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String unusualDealType;//对账结果

	/**
	 * @return 对账结果编号
	 */
	public String getAccountCheckId() {
	 	return accountCheckId;
	}
	/**
	 * @设置 对账结果编号
	 * @param accountCheckId
	 */
	public void setAccountCheckId(String accountCheckId) {
	 	this.accountCheckId = accountCheckId;
	}
	/**
	 * @return 对账时间
	 */
	public String getCheckDate() {
	 	return checkDate;
	}
	/**
	 * @设置 对账时间
	 * @param checkDate
	 */
	public void setCheckDate(String checkDate) {
	 	this.checkDate = checkDate;
	}
	/**
	 * @return 对账人
	 */
	public String getCheckOpNo() {
	 	return checkOpNo;
	}
	/**
	 * @设置 对账人
	 * @param checkOpNo
	 */
	public void setCheckOpNo(String checkOpNo) {
	 	this.checkOpNo = checkOpNo;
	}
	/**
	 * @return 对账人名称
	 */
	public String getCheckOpName() {
	 	return checkOpName;
	}
	/**
	 * @设置 对账人名称
	 * @param checkOpName
	 */
	public void setCheckOpName(String checkOpName) {
	 	this.checkOpName = checkOpName;
	}
	/**
	 * @return 对账结果
	 */
	public String getCheckResultType() {
	 	return checkResultType;
	}
	/**
	 * @设置 对账结果
	 * @param checkResultType
	 */
	public void setCheckResultType(String checkResultType) {
	 	this.checkResultType = checkResultType;
	}
	/**
	 * @return 异常情况
	 */
	public String getUnusualRemark() {
	 	return unusualRemark;
	}
	/**
	 * @设置 异常情况
	 * @param unusualRemark
	 */
	public void setUnusualRemark(String unusualRemark) {
	 	this.unusualRemark = unusualRemark;
	}
	/**
	 * @return 押品业务关联编号
	 */
	public String getBusPleId() {
	 	return busPleId;
	}
	/**
	 * @设置 押品业务关联编号
	 * @param busPleId
	 */
	public void setBusPleId(String busPleId) {
	 	this.busPleId = busPleId;
	}
	/**
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
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
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
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
	 * @return 信息登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 信息登记时间
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
	public String getUnusualDealType() {
		return unusualDealType;
	}
	public void setUnusualDealType(String unusualDealType) {
		this.unusualDealType = unusualDealType;
	}
}