package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableUnusualTailInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 16 18:04:45 CST 2017
* @version：1.0
**/
public class MfMoveableUnusualTailInfo extends BaseDomain {
	private String unusualTailId;//异常情况跟踪编号
	private String tailDate;//跟踪时间
	private String tailOpNo;//跟踪人
	private String tailOpName;//跟踪人名称
	private String unusualDealType;//对账结果
	private String dealRemark;//处理情况说明
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String accountCheckId;//对账结果编号

	/**
	 * @return 异常情况跟踪编号
	 */
	public String getUnusualTailId() {
	 	return unusualTailId;
	}
	/**
	 * @设置 异常情况跟踪编号
	 * @param unusualTailId
	 */
	public void setUnusualTailId(String unusualTailId) {
	 	this.unusualTailId = unusualTailId;
	}
	/**
	 * @return 跟踪时间
	 */
	public String getTailDate() {
	 	return tailDate;
	}
	/**
	 * @设置 跟踪时间
	 * @param tailDate
	 */
	public void setTailDate(String tailDate) {
	 	this.tailDate = tailDate;
	}
	/**
	 * @return 跟踪人
	 */
	public String getTailOpNo() {
	 	return tailOpNo;
	}
	/**
	 * @设置 跟踪人
	 * @param tailOpNo
	 */
	public void setTailOpNo(String tailOpNo) {
	 	this.tailOpNo = tailOpNo;
	}
	/**
	 * @return 跟踪人名称
	 */
	public String getTailOpName() {
	 	return tailOpName;
	}
	/**
	 * @设置 跟踪人名称
	 * @param tailOpName
	 */
	public void setTailOpName(String tailOpName) {
	 	this.tailOpName = tailOpName;
	}
	/**
	 * @return 对账结果
	 */
	public String getUnusualDealType() {
	 	return unusualDealType;
	}
	/**
	 * @设置 对账结果
	 * @param unusualDealType
	 */
	public void setUnusualDealType(String unusualDealType) {
	 	this.unusualDealType = unusualDealType;
	}
	/**
	 * @return 处理情况说明
	 */
	public String getDealRemark() {
	 	return dealRemark;
	}
	/**
	 * @设置 处理情况说明
	 * @param dealRemark
	 */
	public void setDealRemark(String dealRemark) {
	 	this.dealRemark = dealRemark;
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
	public String getAccountCheckId() {
		return accountCheckId;
	}
	public void setAccountCheckId(String accountCheckId) {
		this.accountCheckId = accountCheckId;
	}
}