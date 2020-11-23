package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: RecallConfig.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 15 09:20:56 GMT 2016
* @version：1.0
**/
public class RecallConfig extends BaseDomain {
	private String id;
	private String recallWay;
	private String noteCondition;
	private String modelName;
	private String recallDesc;
	private String recKindNo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRecallWay() {
		return recallWay;
	}
	public void setRecallWay(String recallWay) {
		this.recallWay = recallWay;
	}
	public String getNoteCondition() {
		return noteCondition;
	}
	public void setNoteCondition(String noteCondition) {
		this.noteCondition = noteCondition;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getRecallDesc() {
		return recallDesc;
	}
	public void setRecallDesc(String recallDesc) {
		this.recallDesc = recallDesc;
	}
	public String getRecKindNo() {
		return recKindNo;
	}
	public void setRecKindNo(String recKindNo) {
		this.recKindNo = recKindNo;
	}
	
	/**
	private String recallNo;
	private String prodType;
	private String cusType;
	private String recOrg;
	private String recType;
	private String roleNo;
	private String recNo;
	private String revName;
	private String startRecallWay;
	private String recallWay;
	private String recallType;
	private String recallTime;
	private String recallDesc;
	private String isAvg;
	private String defSts;
	private String manageNo;
	private String manageName;
	private String regNo;
	private String regName;
	private String orgNo;
	private String orgName;
	private String regDate;
	private String lstDate;
	private String lstRegNo;
	private String lstRegName;
	private String roleName;
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;	
	private Integer toLoanDueDate;
	private Integer mustCompleteDays;
	private Double minRecallAmt;
	**/
	
}