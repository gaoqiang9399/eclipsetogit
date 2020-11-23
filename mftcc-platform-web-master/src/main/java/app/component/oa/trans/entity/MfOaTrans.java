package app.component.oa.trans.entity;
import app.base.BaseDomain;
/**
* Title: MfOaTrans.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Apr 25 15:20:00 CST 2017
* @version：1.0
**/
public class MfOaTrans extends BaseDomain {
	private String id;//移交号
	private String transId;//来自同一次移交标识
	private String transType;//移交类型(1客户移交2申请移交3合同移交)
	private String relationType;//关联类型(1客户2申请3合同)
	private String relationId;//关联id
	private String transReason;//移交原因
	private String transSts;//移交状态(0移交成功1审批中2否决 )
	private String recOpNo;//接收人编号
	private String recOpName;//接收人名称
	private String recBrNo;//接收人部门编号
	private String recBrName;//接收人部门名称
	private String transOpNo;//移交人编号
	private String transOpName;//移交人名称
	private String transBrNo;//移交人部门编号
	private String transBrName;//移交人部门名称
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//操作员部门名称
	private String regDate;//登记日期
	private String transDate;//移交日期
	private String approvePartName;//初审的操作员(不存入数据库)
	private String cusCount;//同一次移交的客户数目(不存入数据库)
	private String busCount;//同一次移交的项目数目(不存入数据库)
	private String transContent;//移交内容(不存入数据库)
	private String transContentNo;//移交内容编号(不存入数据库)
	private String relationName;//关联名称
	private String approveNodeName;//初审的操作员(不存入数据库)
	private String areaType;//区域类型1区域内2跨区域
	private String firstApprovalUserName;//初审操作员姓名
	private String firstApprovalUser;//初审操作员编号
	/**
	 * @return 移交号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 移交号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 来自同一次移交标识
	 */
	public String getTransId() {
	 	return transId;
	}
	/**
	 * @设置 来自同一次移交标识
	 * @param transId
	 */
	public void setTransId(String transId) {
	 	this.transId = transId;
	}
	/**
	 * @return 移交类型(1客户移交2申请移交3合同移交)
	 */
	public String getTransType() {
	 	return transType;
	}
	/**
	 * @设置 移交类型(1客户移交2申请移交3合同移交)
	 * @param transType
	 */
	public void setTransType(String transType) {
	 	this.transType = transType;
	}
	/**
	 * @return 关联id
	 */
	public String getRelationId() {
	 	return relationId;
	}
	/**
	 * @设置 关联id
	 * @param relationId
	 */
	public void setRelationId(String relationId) {
	 	this.relationId = relationId;
	}
	/**
	 * @return 移交原因
	 */
	public String getTransReason() {
	 	return transReason;
	}
	/**
	 * @设置 移交原因
	 * @param transReason
	 */
	public void setTransReason(String transReason) {
	 	this.transReason = transReason;
	}
	/**
	 * @return 移交状态(0移交成功1审批中)
	 */
	public String getTransSts() {
	 	return transSts;
	}
	/**
	 * @设置 移交状态(0移交成功1审批中)
	 * @param transSts
	 */
	public void setTransSts(String transSts) {
	 	this.transSts = transSts;
	}
	/**
	 * @return 接收人编号
	 */
	public String getRecOpNo() {
	 	return recOpNo;
	}
	/**
	 * @设置 接收人编号
	 * @param recOpNo
	 */
	public void setRecOpNo(String recOpNo) {
	 	this.recOpNo = recOpNo;
	}
	/**
	 * @return 接收人名称
	 */
	public String getRecOpName() {
	 	return recOpName;
	}
	/**
	 * @设置 接收人名称
	 * @param recOpName
	 */
	public void setRecOpName(String recOpName) {
	 	this.recOpName = recOpName;
	}
	/**
	 * @return 接收人部门编号
	 */
	public String getRecBrNo() {
	 	return recBrNo;
	}
	/**
	 * @设置 接收人部门编号
	 * @param recBrNo
	 */
	public void setRecBrNo(String recBrNo) {
	 	this.recBrNo = recBrNo;
	}
	/**
	 * @return 接收人部门名称
	 */
	public String getRecBrName() {
	 	return recBrName;
	}
	/**
	 * @设置 接收人部门名称
	 * @param recBrName
	 */
	public void setRecBrName(String recBrName) {
	 	this.recBrName = recBrName;
	}
	/**
	 * @return 移交人编号
	 */
	public String getTransOpNo() {
	 	return transOpNo;
	}
	/**
	 * @设置 移交人编号
	 * @param transOpNo
	 */
	public void setTransOpNo(String transOpNo) {
	 	this.transOpNo = transOpNo;
	}
	/**
	 * @return 移交人名称
	 */
	public String getTransOpName() {
	 	return transOpName;
	}
	/**
	 * @设置 移交人名称
	 * @param transOpName
	 */
	public void setTransOpName(String transOpName) {
	 	this.transOpName = transOpName;
	}
	/**
	 * @return 移交人部门编号
	 */
	public String getTransBrNo() {
	 	return transBrNo;
	}
	/**
	 * @设置 移交人部门编号
	 * @param transBrNo
	 */
	public void setTransBrNo(String transBrNo) {
	 	this.transBrNo = transBrNo;
	}
	/**
	 * @return 移交人部门名称
	 */
	public String getTransBrName() {
	 	return transBrName;
	}
	/**
	 * @设置 移交人部门名称
	 * @param transBrName
	 */
	public void setTransBrName(String transBrName) {
	 	this.transBrName = transBrName;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 操作员部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 操作员部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 移交日期
	 */
	public String getTransDate() {
	 	return transDate;
	}
	/**
	 * @设置 移交日期
	 * @param transDate
	 */
	public void setTransDate(String transDate) {
	 	this.transDate = transDate;
	}
	public String getApprovePartName() {
		return approvePartName;
	}
	public void setApprovePartName(String approvePartName) {
		this.approvePartName = approvePartName;
	}
	public String getCusCount() {
		return cusCount;
	}
	public void setCusCount(String cusCount) {
		this.cusCount = cusCount;
	}
	public String getBusCount() {
		return busCount;
	}
	public void setBusCount(String busCount) {
		this.busCount = busCount;
	}
	public String getTransContent() {
		return transContent;
	}
	public void setTransContent(String transContent) {
		this.transContent = transContent;
	}

	public String getTransContentNo() {
		return transContentNo;
	}

	public void setTransContentNo(String transContentNo) {
		this.transContentNo = transContentNo;
	}

	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getApproveNodeName() {
		return approveNodeName;
	}
	public void setApproveNodeName(String approveNodeName) {
		this.approveNodeName = approveNodeName;
	}

	public String getAreaType() {
		return areaType;
	}

	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}

	public String getFirstApprovalUserName() {
		return firstApprovalUserName;
	}

	public void setFirstApprovalUserName(String firstApprovalUserName) {
		this.firstApprovalUserName = firstApprovalUserName;
	}

	public String getFirstApprovalUser() {
		return firstApprovalUser;
	}

	public void setFirstApprovalUser(String firstApprovalUser) {
		this.firstApprovalUser = firstApprovalUser;
	}
}