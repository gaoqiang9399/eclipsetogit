package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: RecallConfigOut.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Mar 23 17:09:28 CST 2017
* @version：1.0
**/
public class RecallConfigOut extends BaseDomain {
	private String outNo;//委外催收配置编号
	private String collectionPlanNo;//催收方案编号
	private String startRecallWay;//发起方式
	private String exeWay;//执行方式
	private Integer returnAfterStartDays;//还款日后(XX天)开始
	private Integer returnAfterEndDays;//还款日后(XX天)结束
	private String returnAfterInteval;//任务重复频率
	private String returnAfterIntevalUnit;//任务重复频率单位
	private Double minRecallAmt;//最小催收金额
	private String recType;//接收人类型
	private String roleNo;//角色编号
	private String roleName;//角色名称
	private String recNo;//接收人编号
	private String recName;//接收人名称
	private String recallDesc;//催收内容描述
	private String recKindNo;
	
	/**
	 * @return 委外催收配置编号
	 */
	public String getOutNo() {
	 	return outNo;
	}
	/**
	 * @设置 委外催收配置编号
	 * @param outNo
	 */
	public void setOutNo(String outNo) {
	 	this.outNo = outNo;
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
	 * @return 还款日后(XX天)开始
	 */
	public Integer getReturnAfterStartDays() {
	 	return returnAfterStartDays;
	}
	/**
	 * @设置 还款日后(XX天)开始
	 * @param returnAfterStartDays
	 */
	public void setReturnAfterStartDays(Integer returnAfterStartDays) {
	 	this.returnAfterStartDays = returnAfterStartDays;
	}
	/**
	 * @return 还款日后(XX天)结束
	 */
	public Integer getReturnAfterEndDays() {
	 	return returnAfterEndDays;
	}
	/**
	 * @设置 还款日后(XX天)结束
	 * @param returnAfterEndDays
	 */
	public void setReturnAfterEndDays(Integer returnAfterEndDays) {
	 	this.returnAfterEndDays = returnAfterEndDays;
	}
	/**
	 * @return 任务重复频率
	 */
	public String getReturnAfterInteval() {
	 	return returnAfterInteval;
	}
	/**
	 * @设置 任务重复频率
	 * @param returnAfterInteval
	 */
	public void setReturnAfterInteval(String returnAfterInteval) {
	 	this.returnAfterInteval = returnAfterInteval;
	}
	/**
	 * @return 任务重复频率单位
	 */
	public String getReturnAfterIntevalUnit() {
	 	return returnAfterIntevalUnit;
	}
	/**
	 * @设置 任务重复频率单位
	 * @param returnAfterIntevalUnit
	 */
	public void setReturnAfterIntevalUnit(String returnAfterIntevalUnit) {
	 	this.returnAfterIntevalUnit = returnAfterIntevalUnit;
	}
	/**
	 * @return 最小催收金额
	 */
	public Double getMinRecallAmt() {
	 	return minRecallAmt;
	}
	/**
	 * @设置 最小催收金额
	 * @param minRecallAmt
	 */
	public void setMinRecallAmt(Double minRecallAmt) {
	 	this.minRecallAmt = minRecallAmt;
	}
	/**
	 * @return 接收人类型
	 */
	public String getRecType() {
	 	return recType;
	}
	/**
	 * @设置 接收人类型
	 * @param recType
	 */
	public void setRecType(String recType) {
	 	this.recType = recType;
	}
	/**
	 * @return 角色编号
	 */
	public String getRoleNo() {
	 	return roleNo;
	}
	/**
	 * @设置 角色编号
	 * @param roleNo
	 */
	public void setRoleNo(String roleNo) {
	 	this.roleNo = roleNo;
	}
	/**
	 * @return 角色名称
	 */
	public String getRoleName() {
	 	return roleName;
	}
	/**
	 * @设置 角色名称
	 * @param roleName
	 */
	public void setRoleName(String roleName) {
	 	this.roleName = roleName;
	}
	/**
	 * @return 接收人编号
	 */
	public String getRecNo() {
	 	return recNo;
	}
	/**
	 * @设置 接收人编号
	 * @param recNo
	 */
	public void setRecNo(String recNo) {
	 	this.recNo = recNo;
	}
	/**
	 * @return 接收人名称
	 */
	public String getRecName() {
	 	return recName;
	}
	/**
	 * @设置 接收人名称
	 * @param recName
	 */
	public void setRecName(String recName) {
	 	this.recName = recName;
	}
	/**
	 * @return 催收内容描述
	 */
	public String getRecallDesc() {
	 	return recallDesc;
	}
	/**
	 * @设置 催收内容描述
	 * @param recallDesc
	 */
	public void setRecallDesc(String recallDesc) {
	 	this.recallDesc = recallDesc;
	}
	public String getRecKindNo() {
		return recKindNo;
	}
	public void setRecKindNo(String recKindNo) {
		this.recKindNo = recKindNo;
	}
	
}