package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: RecallConfigNote.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Mar 20 09:55:30 CST 2017
* @version：1.0
**/
public class RecallConfigNote extends BaseDomain {
	private String noteNo;//短信编号
	private String collectionPlanNo;//催收方案编号
	private String startRecallWay;//发起方式
	private String exeWay;//执行方式
	private String sendTime;//短信发送时间
	private String returnBeforeDays;//还款日前（天）
	private String returnBeforeInteval;//还款日前每（天）
	private String returnAfterDays;//还款日后（天）
	private String returnAfterInteval;//还款日后每（天）
	private String recallDesc;//短信模板
	private String modelId;
	private String modelName;
	
	private String  noteCondition;
	private String noteDay;
	private String useFlag;
	private String recKindNo;
	
	/**
	 * @return 短信编号
	 */
	public String getNoteNo() {
	 	return noteNo;
	}
	/**
	 * @设置 短信编号
	 * @param noteNo
	 */
	public void setNoteNo(String noteNo) {
	 	this.noteNo = noteNo;
	}
	/**
	 * @return 催收方案编号
	 */
	public String getCollectionPlanNo() {
	 	return collectionPlanNo;
	}
	/**
	 * @设置 催收方案编号
	 * @param collectionPlanNo
	 */
	public void setCollectionPlanNo(String collectionPlanNo) {
	 	this.collectionPlanNo = collectionPlanNo;
	}
	/**
	 * @return 发起方式
	 */
	public String getStartRecallWay() {
	 	return startRecallWay;
	}
	/**
	 * @设置 发起方式
	 * @param startRecallWay
	 */
	public void setStartRecallWay(String startRecallWay) {
	 	this.startRecallWay = startRecallWay;
	}
	/**
	 * @return 执行方式
	 */
	public String getExeWay() {
	 	return exeWay;
	}
	/**
	 * @设置 执行方式
	 * @param exeWay
	 */
	public void setExeWay(String exeWay) {
	 	this.exeWay = exeWay;
	}
	/**
	 * @return 短信发送时间
	 */
	public String getSendTime() {
	 	return sendTime;
	}
	/**
	 * @设置 短信发送时间
	 * @param sendTime
	 */
	public void setSendTime(String sendTime) {
	 	this.sendTime = sendTime;
	}
	public String getReturnBeforeDays() {
		return returnBeforeDays;
	}
	public void setReturnBeforeDays(String returnBeforeDays) {
		this.returnBeforeDays = returnBeforeDays;
	}
	public String getReturnBeforeInteval() {
		return returnBeforeInteval;
	}
	public void setReturnBeforeInteval(String returnBeforeInteval) {
		this.returnBeforeInteval = returnBeforeInteval;
	}
	public String getReturnAfterDays() {
		return returnAfterDays;
	}
	public void setReturnAfterDays(String returnAfterDays) {
		this.returnAfterDays = returnAfterDays;
	}
	public String getReturnAfterInteval() {
		return returnAfterInteval;
	}
	public void setReturnAfterInteval(String returnAfterInteval) {
		this.returnAfterInteval = returnAfterInteval;
	}
	/**
	 * @return 短信模板
	 */
	public String getRecallDesc() {
	 	return recallDesc;
	}
	/**
	 * @设置 短信模板
	 * @param recallDesc
	 */
	public void setRecallDesc(String recallDesc) {
	 	this.recallDesc = recallDesc;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getNoteCondition() {
		return noteCondition;
	}
	public void setNoteCondition(String noteCondition) {
		this.noteCondition = noteCondition;
	}
	public String getNoteDay() {
		return noteDay;
	}
	public void setNoteDay(String noteDay) {
		this.noteDay = noteDay;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getRecKindNo() {
		return recKindNo;
	}
	public void setRecKindNo(String recKindNo) {
		this.recKindNo = recKindNo;
	}
	
}