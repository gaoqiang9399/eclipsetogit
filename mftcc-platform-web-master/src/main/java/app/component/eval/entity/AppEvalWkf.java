package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: AlpBase.java
* Description:
* @author：@dhcc.com.cn
* @Mon Feb 29 08:43:59 GMT 2016
* @version：1.0
**/
public class AppEvalWkf extends BaseDomain {
	private String appNo;//业务流水号
	private String taskId;
	private String opinionType;
	private String approvalOpinion;
	private String transition;// 下一节点的线
	private String nextUser;
	

	/**
	 * @return 业务流水号
	 */
	public String getAppNo() {
	 	return appNo;
	}
	/**
	 * @设置 业务流水号
	 * @param appNo
	 */
	public void setAppNo(String appNo) {
	 	this.appNo = appNo;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getOpinionType() {
		return opinionType;
	}
	public void setOpinionType(String opinionType) {
		this.opinionType = opinionType;
	}
	public String getApprovalOpinion() {
		return approvalOpinion;
	}
	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	public String getTransition() {
		return transition;
	}
	public void setTransition(String transition) {
		this.transition = transition;
	}
	public String getNextUser() {
		return nextUser;
	}
	public void setNextUser(String nextUser) {
		this.nextUser = nextUser;
	}
	
	
}