package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableBuybackConfirm.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jun 20 14:24:03 CST 2017
* @version：1.0
**/
public class MfMoveableBuybackConfirm extends BaseDomain {
	private String confirmId;//回购确认编号
	private String backId;//回购编号
	private String pledgeNo;//押品编号
	private String pledgeName;//押品名称
	private String pledgeBillNo;//货物明细
	private Double pledgeWorth;//押品价值
	private Double buyBackPrice;//回购意向价
	private Double actualPrice;//实际回购价
	private String buyBackDate;//回购时间
	private String buyBackReason;//回购原因
	private String appSts;//申请状态0新增申请1审批中2审批通过3否决
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 回购确认编号
	 */
	public String getConfirmId() {
	 	return confirmId;
	}
	/**
	 * @设置 回购确认编号
	 * @param confirmId
	 */
	public void setConfirmId(String confirmId) {
	 	this.confirmId = confirmId;
	}
	/**
	 * @return 回购编号
	 */
	public String getBackId() {
	 	return backId;
	}
	/**
	 * @设置 回购编号
	 * @param backId
	 */
	public void setBackId(String backId) {
	 	this.backId = backId;
	}
	/**
	 * @return 押品编号
	 */
	public String getPledgeNo() {
	 	return pledgeNo;
	}
	/**
	 * @设置 押品编号
	 * @param pledgeNo
	 */
	public void setPledgeNo(String pledgeNo) {
	 	this.pledgeNo = pledgeNo;
	}
	/**
	 * @return 押品名称
	 */
	public String getPledgeName() {
	 	return pledgeName;
	}
	/**
	 * @设置 押品名称
	 * @param pledgeName
	 */
	public void setPledgeName(String pledgeName) {
	 	this.pledgeName = pledgeName;
	}
	/**
	 * @return 货物明细
	 */
	public String getPledgeBillNo() {
	 	return pledgeBillNo;
	}
	/**
	 * @设置 货物明细
	 * @param pledgeBillNo
	 */
	public void setPledgeBillNo(String pledgeBillNo) {
	 	this.pledgeBillNo = pledgeBillNo;
	}
	/**
	 * @return 押品价值
	 */
	public Double getPledgeWorth() {
	 	return pledgeWorth;
	}
	/**
	 * @设置 押品价值
	 * @param pledgeWorth
	 */
	public void setPledgeWorth(Double pledgeWorth) {
	 	this.pledgeWorth = pledgeWorth;
	}
	/**
	 * @return 回购意向价
	 */
	public Double getBuyBackPrice() {
	 	return buyBackPrice;
	}
	/**
	 * @设置 回购意向价
	 * @param buyBackPrice
	 */
	public void setBuyBackPrice(Double buyBackPrice) {
	 	this.buyBackPrice = buyBackPrice;
	}
	/**
	 * @return 实际回购价
	 */
	public Double getActualPrice() {
	 	return actualPrice;
	}
	/**
	 * @设置 实际回购价
	 * @param actualPrice
	 */
	public void setActualPrice(Double actualPrice) {
	 	this.actualPrice = actualPrice;
	}
	/**
	 * @return 回购时间
	 */
	public String getBuyBackDate() {
	 	return buyBackDate;
	}
	/**
	 * @设置 回购时间
	 * @param buyBackDate
	 */
	public void setBuyBackDate(String buyBackDate) {
	 	this.buyBackDate = buyBackDate;
	}
	/**
	 * @return 回购原因
	 */
	public String getBuyBackReason() {
	 	return buyBackReason;
	}
	/**
	 * @设置 回购原因
	 * @param buyBackReason
	 */
	public void setBuyBackReason(String buyBackReason) {
	 	this.buyBackReason = buyBackReason;
	}
	/**
	 * @return 申请状态0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 申请状态0新增申请1审批中2审批通过3否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
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
}