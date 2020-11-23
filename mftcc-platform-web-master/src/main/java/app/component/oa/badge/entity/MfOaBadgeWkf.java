package app.component.oa.badge.entity;


public class MfOaBadgeWkf {
	private String appNo;//业务流水号
	private String taskId;
	private String opinionType;
	private String approvalOpinion;
	private String transition;// 下一节点的线
	private String nextUser;
	private String badgeNodeType;//1 : 一般情况  2: 章保管员确认节点   3:章保管员确认归还节点
	private String isChairman;
	public String getAppNo() {
		return appNo;
	}
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
	public String getBadgeNodeType() {
		return badgeNodeType;
	}
	public void setBadgeNodeType(String badgeNodeType) {
		this.badgeNodeType = badgeNodeType;
	}
	public String getIsChairman() {
		return isChairman;
	}
	public void setIsChairman(String isChairman) {
		this.isChairman = isChairman;
	}
	
}
