package cn.mftcc.function.bean;
import app.base.BaseDomain;
/**
* Title: SysTaskInfo.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 22 07:19:50 GMT 2016
* @version：1.0
**/
public class SysTaskInfo extends BaseDomain {
	private String pasNo;//流水号
	private String bizPkNo;//业务主键
	private String pasMaxNo;//业务大类
	private String pasMinNo;//业务小类
	private	String pasMinType;//
	private String pasTitle;//业务主题
	private String pasContent;//业务内容
	private String pasUrl;//审批URL
	private String wkfTaskNo;//流程审批节点
	private String userNo;//当前执行人
	private String createDate;//生成日期
	private String createTime;//生成时间
	private String importLev;//重要程度
	private String dueDate;//要求完成日期
	private String endDate;//完成日期
	private String pasIsMust;//是否必须完成
	private String pasSts;//任务状态
	private String pasAware;//是否关注
	private String optType;//操作类型
	private String isMustReply;//是否必须回复
	private String formId;//展开的表单编号
	private String pasResult;//完成返回值
	private String pasType;//任务类型
	private String regName;//消息发起人
	private String pasStick;//置顶状态
	private String taskOrder;//排序配置
	private String lookTime;//查阅时间
	
	/**
	 * @return 流水号
	 */
	public String getPasNo() {
	 	return pasNo;
	}
	/**
	 * @设置 流水号
	 * @param pasNo
	 */
	public void setPasNo(String pasNo) {
	 	this.pasNo = pasNo;
	}
	/**
	 * @return 业务主键
	 */
	public String getBizPkNo() {
	 	return bizPkNo;
	}
	/**
	 * @设置 业务主键
	 * @param bizPkNo
	 */
	public void setBizPkNo(String bizPkNo) {
	 	this.bizPkNo = bizPkNo;
	}
	/**
	 * @return 业务大类
	 */
	public String getPasMaxNo() {
	 	return pasMaxNo;
	}
	/**
	 * @设置 业务大类
	 * @param pasMaxNo
	 */
	public void setPasMaxNo(String pasMaxNo) {
	 	this.pasMaxNo = pasMaxNo;
	}
	/**
	 * @return 业务小类
	 */
	public String getPasMinNo() {
	 	return pasMinNo;
	}
	/**
	 * @设置 业务小类
	 * @param pasMinNo
	 */
	public void setPasMinNo(String pasMinNo) {
	 	this.pasMinNo = pasMinNo;
	}
	/**
	 * @return 业务主题
	 */
	public String getPasTitle() {
	 	return pasTitle;
	}
	/**
	 * @设置 业务主题
	 * @param pasTitle
	 */
	public void setPasTitle(String pasTitle) {
	 	this.pasTitle = pasTitle;
	}
	/**
	 * @return 业务内容
	 */
	public String getPasContent() {
	 	return pasContent;
	}
	/**
	 * @设置 业务内容
	 * @param pasContent
	 */
	public void setPasContent(String pasContent) {
	 	this.pasContent = pasContent;
	}
	/**
	 * @return 审批URL
	 */
	public String getPasUrl() {
	 	return pasUrl;
	}
	/**
	 * @设置 审批URL
	 * @param pasUrl
	 */
	public void setPasUrl(String pasUrl) {
	 	this.pasUrl = pasUrl;
	}
	/**
	 * @return 流程审批节点
	 */
	public String getWkfTaskNo() {
	 	return wkfTaskNo;
	}
	/**
	 * @设置 流程审批节点
	 * @param wkfTaskNo
	 */
	public void setWkfTaskNo(String wkfTaskNo) {
	 	this.wkfTaskNo = wkfTaskNo;
	}
	/**
	 * @return 当前执行人
	 */
	public String getUserNo() {
	 	return userNo;
	}
	/**
	 * @设置 当前执行人
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
	 	this.userNo = userNo;
	}
	/**
	 * @return 生成日期
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 生成日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 生成时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 生成时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 重要程度
	 */
	public String getImportLev() {
	 	return importLev;
	}
	/**
	 * @设置 重要程度
	 * @param importLev
	 */
	public void setImportLev(String importLev) {
	 	this.importLev = importLev;
	}
	/**
	 * @return 要求完成日期
	 */
	public String getDueDate() {
	 	return dueDate;
	}
	/**
	 * @设置 要求完成日期
	 * @param dueDate
	 */
	public void setDueDate(String dueDate) {
	 	this.dueDate = dueDate;
	}
	/**
	 * @return 完成日期
	 */
	public String getEndDate() {
	 	return endDate;
	}
	/**
	 * @设置 完成日期
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
	 	this.endDate = endDate;
	}
	/**
	 * @return 是否必须完成
	 */
	public String getPasIsMust() {
	 	return pasIsMust;
	}
	/**
	 * @设置 是否必须完成
	 * @param pasIsMust
	 */
	public void setPasIsMust(String pasIsMust) {
	 	this.pasIsMust = pasIsMust;
	}
	/**
	 * @return 任务状态
	 */
	public String getPasSts() {
	 	return pasSts;
	}
	/**
	 * @设置 任务状态
	 * @param pasSts
	 */
	public void setPasSts(String pasSts) {
	 	this.pasSts = pasSts;
	}
	/**
	 * @return 是否关注
	 */
	public String getPasAware() {
	 	return pasAware;
	}
	/**
	 * @设置 是否关注
	 * @param pasAware
	 */
	public void setPasAware(String pasAware) {
	 	this.pasAware = pasAware;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getIsMustReply() {
		return isMustReply;
	}
	public void setIsMustReply(String isMustReply) {
		this.isMustReply = isMustReply;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getPasResult() {
		return pasResult;
	}
	public void setPasResult(String pasResult) {
		this.pasResult = pasResult;
	}
	public String getPasType() {
		return pasType;
	}
	public void setPasType(String pasType) {
		this.pasType = pasType;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getPasStick() {
		return pasStick;
	}
	public void setPasStick(String pasStick) {
		this.pasStick = pasStick;
	}
	public String getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(String taskOrder) {
		this.taskOrder = taskOrder;
	}
	public String getLookTime() {
		return lookTime;
	}
	public void setLookTime(String lookTime) {
		this.lookTime = lookTime;
	}
	public String getPasMinType() {
		return pasMinType;
	}
	public void setPasMinType(String pasMinType) {
		this.pasMinType = pasMinType;
	}
	
}