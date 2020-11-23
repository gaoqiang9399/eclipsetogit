package app.component.interfaces.appinterface.entity;
import app.base.BaseDomain;
/**
* Title: SysTaskInfo.java
* Description:任务审批
* @author：kaifa@dhcc.com.cn
* @Fri Aug 11 09:50:36 CST 2017
* @version：1.0
**/
public class DingBusApproval extends BaseDomain {
	private Integer pasNo;//
	private String bizPkNo;//业务主键
	private String pasMaxNo;//业务大类
	private String pasMinNo;//业务小类
	private String pasMinType;//业务小类类型
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
	private String pasResult;//完成返回值
	private String pasAware;//是否关注
	private String optType;//操作类型
	private String formId;//展开的表单编号
	private String isMustReply;//是否必须回复
	private String regName;//消息发起人（只记录名字不记录号）
	private String pasType;//任务类型
	private String pasStick;//置顶状态
	private String lookTime;//查阅时间
	
	//合同需要展示信息
	private String cusName;
	private String appName;
	private String busStage;//业务状态
	private Double pactAmt;//合同金额
	private Double pactAmtShow;//合同金额展示，不允许修改
	private String beginDate;//合同开始日期
	private String pactEndDate;//合同开始日期
	private String signDate;//合同签订日期
	private Double fincRate;//融资款利率
	private String rateType;//利率类型 1为年利率
	private String termType;//申请期限类型1-月 2-天
	private Integer term;//申请期限数值
	private String  termShow;//申请期限显示

	/**
	 * @return 
	 */
	public Integer getPasNo() {
	 	return pasNo;
	}
	/**
	 * @设置 
	 * @param pasNo
	 */
	public void setPasNo(Integer pasNo) {
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
	 * @return 业务小类类型
	 */
	public String getPasMinType() {
	 	return pasMinType;
	}
	/**
	 * @设置 业务小类类型
	 * @param pasMinType
	 */
	public void setPasMinType(String pasMinType) {
	 	this.pasMinType = pasMinType;
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
	 * @return 完成返回值
	 */
	public String getPasResult() {
	 	return pasResult;
	}
	/**
	 * @设置 完成返回值
	 * @param pasResult
	 */
	public void setPasResult(String pasResult) {
	 	this.pasResult = pasResult;
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
	/**
	 * @return 操作类型
	 */
	public String getOptType() {
	 	return optType;
	}
	/**
	 * @设置 操作类型
	 * @param optType
	 */
	public void setOptType(String optType) {
	 	this.optType = optType;
	}
	/**
	 * @return 展开的表单编号
	 */
	public String getFormId() {
	 	return formId;
	}
	/**
	 * @设置 展开的表单编号
	 * @param formId
	 */
	public void setFormId(String formId) {
	 	this.formId = formId;
	}
	/**
	 * @return 是否必须回复
	 */
	public String getIsMustReply() {
	 	return isMustReply;
	}
	/**
	 * @设置 是否必须回复
	 * @param isMustReply
	 */
	public void setIsMustReply(String isMustReply) {
	 	this.isMustReply = isMustReply;
	}
	/**
	 * @return 消息发起人（只记录名字不记录号）
	 */
	public String getRegName() {
	 	return regName;
	}
	/**
	 * @设置 消息发起人（只记录名字不记录号）
	 * @param regName
	 */
	public void setRegName(String regName) {
	 	this.regName = regName;
	}
	/**
	 * @return 任务类型
	 */
	public String getPasType() {
	 	return pasType;
	}
	/**
	 * @设置 任务类型
	 * @param pasType
	 */
	public void setPasType(String pasType) {
	 	this.pasType = pasType;
	}
	/**
	 * @return 置顶状态
	 */
	public String getPasStick() {
	 	return pasStick;
	}
	/**
	 * @设置 置顶状态
	 * @param pasStick
	 */
	public void setPasStick(String pasStick) {
	 	this.pasStick = pasStick;
	}
	/**
	 * @return 查阅时间
	 */
	public String getLookTime() {
	 	return lookTime;
	}
	/**
	 * @设置 查阅时间
	 * @param lookTime
	 */
	public void setLookTime(String lookTime) {
	 	this.lookTime = lookTime;
	}
	public Double getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(Double pactAmt) {
		this.pactAmt = pactAmt;
	}
	public Double getPactAmtShow() {
		return pactAmtShow;
	}
	public void setPactAmtShow(Double pactAmtShow) {
		this.pactAmtShow = pactAmtShow;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public Double getFincRate() {
		return fincRate;
	}
	public void setFincRate(Double fincRate) {
		this.fincRate = fincRate;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getTermShow() {
		return termShow;
	}
	public void setTermShow(String termShow) {
		this.termShow = termShow;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getPactEndDate() {
		return pactEndDate;
	}
	public void setPactEndDate(String pactEndDate) {
		this.pactEndDate = pactEndDate;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getBusStage() {
		return busStage;
	}
	public void setBusStage(String busStage) {
		this.busStage = busStage;
	}
	
	
}