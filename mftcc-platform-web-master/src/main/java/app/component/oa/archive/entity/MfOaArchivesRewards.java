package app.component.oa.archive.entity;
import app.base.BaseDomain;
/**
* Title: MfOaArchivesRewards.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 22 17:46:04 CST 2017
* @version：1.0
**/
public class MfOaArchivesRewards extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String rewardId;//奖惩记录id
	private String baseId;//基础信息ID
	private String adwardDate;//日期
	private String adwards;//奖项
	private String grade;//成绩
	private String adwardRemark;//备注
	private String rewardOrPunishment;//惩罚或者奖励：1-奖励；2-惩罚；
	private String reason;//惩罚原因

	/**
	 * @return 奖惩记录id
	 */
	public String getRewardId() {
	 	return rewardId;
	}
	/**
	 * @设置 奖惩记录id
	 * @param rewardId
	 */
	public void setRewardId(String rewardId) {
	 	this.rewardId = rewardId;
	}
	/**
	 * @return 日期
	 */
	public String getAdwardDate() {
	 	return adwardDate;
	}
	/**
	 * @设置 日期
	 * @param adwardDate
	 */
	public void setAdwardDate(String adwardDate) {
	 	this.adwardDate = adwardDate;
	}
	/**
	 * @return 奖项
	 */
	public String getAdwards() {
	 	return adwards;
	}
	/**
	 * @设置 奖项
	 * @param adwards
	 */
	public void setAdwards(String adwards) {
	 	this.adwards = adwards;
	}
	/**
	 * @return 成绩
	 */
	public String getGrade() {
	 	return grade;
	}
	/**
	 * @设置 成绩
	 * @param grade
	 */
	public void setGrade(String grade) {
	 	this.grade = grade;
	}
	/**
	 * @return 备注
	 */
	public String getAdwardRemark() {
	 	return adwardRemark;
	}
	/**
	 * @设置 备注
	 * @param adwardRemark
	 */
	public void setAdwardRemark(String adwardRemark) {
	 	this.adwardRemark = adwardRemark;
	}
	/**
	 * @return the rewardOrPunishment
	 */
	public String getRewardOrPunishment() {
		return rewardOrPunishment;
	}
	/**
	 * @param rewardOrPunishment the rewardOrPunishment to set
	 */
	public void setRewardOrPunishment(String rewardOrPunishment) {
		this.rewardOrPunishment = rewardOrPunishment;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
}