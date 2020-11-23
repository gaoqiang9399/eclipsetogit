package app.component.msgconf.entity;
import app.base.BaseDomain;
/**
* Title: MfMsgPledge.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jul 10 13:45:12 CST 2017
* @version：1.0
**/
public class MfMsgPledge extends BaseDomain {
	private static final long serialVersionUID = 1L;
	
	private String id;//ID主键
	private String funName;//功能点名称
	private String funType;//功能点类型
	private String classId;//押品类型ID
	private String listenerClass;//监听类名
	private String listenerFunc;//监听方法
	private String triggerType;//触发节点类型：业务操作、日终处理
	private String triggerName;//触发节点名称
	private String funDes;//功能触发节点描述
	private int intervalExpireDays;//距离到期天数
	private int sendFrequencyValue;//发送频率
	private String sendFrequencyUnit;//频率单位
	private int sendCounts;//发送次数
	private int cycleValue;//重估周期
	private String cycleUnit;//周期单位
	private String waveType;//波动类型
	private double waveThresholdValue;//波动警戒值
	private double waveThresholdRate;//波动警戒比
	private String reciverName;//发送对象名称
	private String reciverType;//发送对象类型
	private String sendName;//发送方式名称
	private String sendType;//发送方式类型
	private String modelContent;//消息模板内容
	private String args;//变量参数（存 变量id）
	private String varUsage;//变量来源类型
	private String useFlag;//是否启用

	/**
	 * @return ID主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 ID主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 功能点名称
	 */
	public String getFunName() {
	 	return funName;
	}
	/**
	 * @设置 功能点名称
	 * @param funName
	 */
	public void setFunName(String funName) {
	 	this.funName = funName;
	}
	/**
	 * @return 功能点类型
	 */
	public String getFunType() {
	 	return funType;
	}
	/**
	 * @设置 功能点类型
	 * @param funType
	 */
	public void setFunType(String funType) {
	 	this.funType = funType;
	}
	/**
	 * @return 押品类型ID
	 */
	public String getClassId() {
	 	return classId;
	}
	/**
	 * @设置 押品类型ID
	 * @param classId
	 */
	public void setClassId(String classId) {
	 	this.classId = classId;
	}
	/**
	 * @return 监听类名
	 */
	public String getListenerClass() {
	 	return listenerClass;
	}
	/**
	 * @设置 监听类名
	 * @param listenerClass
	 */
	public void setListenerClass(String listenerClass) {
	 	this.listenerClass = listenerClass;
	}
	/**
	 * @return 监听方法
	 */
	public String getListenerFunc() {
	 	return listenerFunc;
	}
	/**
	 * @设置 监听方法
	 * @param listenerFunc
	 */
	public void setListenerFunc(String listenerFunc) {
	 	this.listenerFunc = listenerFunc;
	}
	/**
	 * @return 触发节点类型：业务操作、日终处理
	 */
	public String getTriggerType() {
	 	return triggerType;
	}
	/**
	 * @设置 触发节点类型：业务操作、日终处理
	 * @param triggerType
	 */
	public void setTriggerType(String triggerType) {
	 	this.triggerType = triggerType;
	}
	/**
	 * @return 触发节点名称
	 */
	public String getTriggerName() {
	 	return triggerName;
	}
	/**
	 * @设置 触发节点名称
	 * @param triggerName
	 */
	public void setTriggerName(String triggerName) {
	 	this.triggerName = triggerName;
	}
	/**
	 * @return 功能触发节点描述
	 */
	public String getFunDes() {
	 	return funDes;
	}
	/**
	 * @设置 功能触发节点描述
	 * @param funDes
	 */
	public void setFunDes(String funDes) {
	 	this.funDes = funDes;
	}
	public int getIntervalExpireDays() {
		return intervalExpireDays;
	}
	public void setIntervalExpireDays(int intervalExpireDays) {
		this.intervalExpireDays = intervalExpireDays;
	}
	public int getSendFrequencyValue() {
		return sendFrequencyValue;
	}
	public void setSendFrequencyValue(int sendFrequencyValue) {
		this.sendFrequencyValue = sendFrequencyValue;
	}
	public String getSendFrequencyUnit() {
		return sendFrequencyUnit;
	}
	public void setSendFrequencyUnit(String sendFrequencyUnit) {
		this.sendFrequencyUnit = sendFrequencyUnit;
	}
	public int getSendCounts() {
		return sendCounts;
	}
	public void setSendCounts(int sendCounts) {
		this.sendCounts = sendCounts;
	}
	public int getCycleValue() {
		return cycleValue;
	}
	public void setCycleValue(int cycleValue) {
		this.cycleValue = cycleValue;
	}
	public String getCycleUnit() {
		return cycleUnit;
	}
	public void setCycleUnit(String cycleUnit) {
		this.cycleUnit = cycleUnit;
	}
	public String getWaveType() {
		return waveType;
	}
	public void setWaveType(String waveType) {
		this.waveType = waveType;
	}
	public double getWaveThresholdValue() {
		return waveThresholdValue;
	}
	public void setWaveThresholdValue(double waveThresholdValue) {
		this.waveThresholdValue = waveThresholdValue;
	}
	public double getWaveThresholdRate() {
		return waveThresholdRate;
	}
	public void setWaveThresholdRate(double waveThresholdRate) {
		this.waveThresholdRate = waveThresholdRate;
	}
	/**
	 * @return 发送对象名称
	 */
	public String getReciverName() {
	 	return reciverName;
	}
	/**
	 * @设置 发送对象名称
	 * @param reciverName
	 */
	public void setReciverName(String reciverName) {
	 	this.reciverName = reciverName;
	}
	/**
	 * @return 发送对象类型
	 */
	public String getReciverType() {
	 	return reciverType;
	}
	/**
	 * @设置 发送对象类型
	 * @param reciverType
	 */
	public void setReciverType(String reciverType) {
	 	this.reciverType = reciverType;
	}
	/**
	 * @return 发送方式名称
	 */
	public String getSendName() {
	 	return sendName;
	}
	/**
	 * @设置 发送方式名称
	 * @param sendName
	 */
	public void setSendName(String sendName) {
	 	this.sendName = sendName;
	}
	/**
	 * @return 发送方式类型
	 */
	public String getSendType() {
	 	return sendType;
	}
	/**
	 * @设置 发送方式类型
	 * @param sendType
	 */
	public void setSendType(String sendType) {
	 	this.sendType = sendType;
	}
	/**
	 * @return 消息模板内容
	 */
	public String getModelContent() {
	 	return modelContent;
	}
	/**
	 * @设置 消息模板内容
	 * @param modelContent
	 */
	public void setModelContent(String modelContent) {
	 	this.modelContent = modelContent;
	}
	/**
	 * @return 变量参数（存 变量id）
	 */
	public String getArgs() {
	 	return args;
	}
	/**
	 * @设置 变量参数（存 变量id）
	 * @param args
	 */
	public void setArgs(String args) {
	 	this.args = args;
	}
	/**
	 * @return 变量来源类型
	 */
	public String getVarUsage() {
	 	return varUsage;
	}
	/**
	 * @设置 变量来源类型
	 * @param varUsage
	 */
	public void setVarUsage(String varUsage) {
	 	this.varUsage = varUsage;
	}
	/**
	 * @return 是否启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
}