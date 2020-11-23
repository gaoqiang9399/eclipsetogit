package app.component.oa.notice.entity;
import app.base.BaseDomain;
/**
* Title: MfOaNotice.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 09 16:16:55 CST 2016
* @version：1.0
**/
public class MfOaNotice extends BaseDomain {
	private String noticeId;//通知公告id
	private String noticeTitle;//通知公告标题
	private String keyWord;//关键词
	private String contentAbstract;//内容简介/摘要
	private String noticeContent;//通知公告内容
	private String publishTime;//发布时间
	private String startDate;//通知公告生效日期
	private String endDate;//终止日期
	private String noticeScorp;//发布范围 存放公告接收者的操作员号 不同操作员用竖线分隔
	private String noticeSts;//公告状态 0未发布 1 生效 2 终止
	private String isRemind;//是否事务提醒 0 提醒 1 不提醒 默认0
	private String isTop;//是否置顶 0置顶 1不置顶
	private String isLoad;//是否可下载附件 0 可下载 1 不可下载
	private String opNo;//发布人编号
	private String brNo;//发布人部门编号
	private String opName;//发布人名称
	private String brName;//发布人部门名称
	private String lstModTime;//通知公告最后修改时间
	private String noticeScorpName;//发布范围中文名字 存放公告接收者的操作员号名称 不同操作员用竖线分隔

    private String noticeType;//通知类型 1 通知公告 2 发布产品

	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	/**
	 * @return 通知公告id
	 */
	public String getNoticeId() {
	 	return noticeId;
	}
	/**
	 * @设置 通知公告id
	 * @param noticeId
	 */
	public void setNoticeId(String noticeId) {
	 	this.noticeId = noticeId;
	}
	/**
	 * @return 通知公告标题
	 */
	public String getNoticeTitle() {
	 	return noticeTitle;
	}
	/**
	 * @设置 通知公告标题
	 * @param noticeTitle
	 */
	public void setNoticeTitle(String noticeTitle) {
	 	this.noticeTitle = noticeTitle;
	}
	/**
	 * @return 关键词
	 */
	public String getKeyWord() {
	 	return keyWord;
	}
	/**
	 * @设置 关键词
	 * @param keyWord
	 */
	public void setKeyWord(String keyWord) {
	 	this.keyWord = keyWord;
	}
	/**
	 * @return 内容简介/摘要
	 */
	public String getContentAbstract() {
	 	return contentAbstract;
	}
	/**
	 * @设置 内容简介/摘要
	 * @param contentAbstract
	 */
	public void setContentAbstract(String contentAbstract) {
	 	this.contentAbstract = contentAbstract;
	}
	/**
	 * @return 通知公告内容
	 */
	public String getNoticeContent() {
	 	return noticeContent;
	}
	/**
	 * @设置 通知公告内容
	 * @param noticeContent
	 */
	public void setNoticeContent(String noticeContent) {
	 	this.noticeContent = noticeContent;
	}
	/**
	 * @return 发布时间
	 */
	public String getPublishTime() {
	 	return publishTime;
	}
	/**
	 * @设置 发布时间
	 * @param publishTime
	 */
	public void setPublishTime(String publishTime) {
	 	this.publishTime = publishTime;
	}
	/**
	 * @return 通知公告生效日期
	 */
	public String getStartDate() {
	 	return startDate;
	}
	/**
	 * @设置 通知公告生效日期
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
	 	this.startDate = startDate;
	}
	/**
	 * @return 终止日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 终止日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 发布范围 存放公告接收者的操作员号 不同操作员用竖线分隔
	 */
	public String getNoticeScorp() {
	 	return noticeScorp;
	}
	/**
	 * @设置 发布范围 存放公告接收者的操作员号 不同操作员用竖线分隔
	 * @param noticeScorp
	 */
	public void setNoticeScorp(String noticeScorp) {
	 	this.noticeScorp = noticeScorp;
	}
	/**
	 * @return 公告状态 0未发布 1 生效 2 终止
	 */
	public String getNoticeSts() {
	 	return noticeSts;
	}
	/**
	 * @设置 公告状态 0未发布 1 生效 2 终止
	 * @param noticeSts
	 */
	public void setNoticeSts(String noticeSts) {
	 	this.noticeSts = noticeSts;
	}
	/**
	 * @return 是否事务提醒 0 提醒 1 不提醒 默认0
	 */
	public String getIsRemind() {
	 	return isRemind;
	}
	/**
	 * @设置 是否事务提醒 0 提醒 1 不提醒 默认0
	 * @param isRemind
	 */
	public void setIsRemind(String isRemind) {
	 	this.isRemind = isRemind;
	}
	/**
	 * @return 是否置顶 0置顶 1不置顶
	 */
	public String getIsTop() {
	 	return isTop;
	}
	/**
	 * @设置 是否置顶 0置顶 1不置顶
	 * @param isTop
	 */
	public void setIsTop(String isTop) {
	 	this.isTop = isTop;
	}
	/**
	 * @return 是否可下载附件 0 可下载 1 不可下载
	 */
	public String getIsLoad() {
	 	return isLoad;
	}
	/**
	 * @设置 是否可下载附件 0 可下载 1 不可下载
	 * @param isLoad
	 */
	public void setIsLoad(String isLoad) {
	 	this.isLoad = isLoad;
	}
	/**
	 * @return 发布人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 发布人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 发布人部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 发布人部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 发布人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 发布人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 发布人部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 发布人部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 通知公告最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 通知公告最后修改时间
	 * @param lastModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getNoticeScorpName() {
		return noticeScorpName;
	}
	public void setNoticeScorpName(String noticeScorpName) {
		this.noticeScorpName = noticeScorpName;
	}
}