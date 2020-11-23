package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysNotice.java
* Description:
* @author：@dhcc.com.cn
* @Tue Jul 26 04:21:37 GMT 2016
* @version：1.0
**/
public class SysNotice extends BaseDomain {
	private String no;//系统通知NO
	private String title;//通知标题
	private String content;//通知内容
	private String pushDate;//通知日期
	private String brNo;//被通知机构
	private String roleNo;//被通知角色
	private String opNo;//被通知操作员
	private String sts;//是否发送
	private String optNo;//操作员编号
	private String txDate;//登记日期
	private String orgNo;//机构号
	private String pasStick;//是否置顶

	/**
	 * @return 系统通知NO
	 */
	public String getNo() {
	 	return no;
	}
	/**
	 * @设置 系统通知NO
	 * @param no
	 */
	public void setNo(String no) {
	 	this.no = no;
	}
	/**
	 * @return 通知标题
	 */
	public String getTitle() {
	 	return title;
	}
	/**
	 * @设置 通知标题
	 * @param title
	 */
	public void setTitle(String title) {
	 	this.title = title;
	}
	/**
	 * @return 通知内容
	 */
	public String getContent() {
	 	return content;
	}
	/**
	 * @设置 通知内容
	 * @param content
	 */
	public void setContent(String content) {
	 	this.content = content;
	}
	/**
	 * @return 通知日期
	 */
	public String getPushDate() {
	 	return pushDate;
	}
	/**
	 * @设置 通知日期
	 * @param pushDate
	 */
	public void setPushDate(String pushDate) {
	 	this.pushDate = pushDate;
	}
	/**
	 * @return 被通知机构
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 被通知机构
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 被通知角色
	 */
	public String getRoleNo() {
	 	return roleNo;
	}
	/**
	 * @设置 被通知角色
	 * @param roleNo
	 */
	public void setRoleNo(String roleNo) {
	 	this.roleNo = roleNo;
	}
	/**
	 * @return 被通知操作员
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 被通知操作员
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 是否发送
	 */
	public String getSts() {
	 	return sts;
	}
	/**
	 * @设置 是否发送
	 * @param sts
	 */
	public void setSts(String sts) {
	 	this.sts = sts;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOptNo() {
	 	return optNo;
	}
	/**
	 * @设置 操作员编号
	 * @param optNo
	 */
	public void setOptNo(String optNo) {
	 	this.optNo = optNo;
	}
	/**
	 * @return 登记日期
	 */
	public String getTxDate() {
	 	return txDate;
	}
	/**
	 * @设置 登记日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
	 	this.txDate = txDate;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getPasStick() {
		return pasStick;
	}
	public void setPasStick(String pasStick) {
		this.pasStick = pasStick;
	}
}