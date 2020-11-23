package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfCusMng.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Sep 05 10:01:02 CST 2017
* @version：1.0
**/
public class MfAppCusMng extends BaseDomain {
	private String cusMngNo;//客户经理编号
	private String cusMngName;//客户经理名称
	private String cusMngMobile;//客户经理手机
	private String cusMngBrNo;//机构号
	private String cusMngBrName;//部门名称
	private String userNo;//员工编号
	private String roleNo;//角色号
	private String opSts;//状态 1 启用 0 注销
	private int cut;//数目
	private String channelType;//渠道类型

	/**
	 * @return 客户经理编号
	 */
	public String getCusMngNo() {
	 	return cusMngNo;
	}
	/**
	 * @设置 客户经理编号
	 * @param cusMngNo
	 */
	public void setCusMngNo(String cusMngNo) {
	 	this.cusMngNo = cusMngNo;
	}
	/**
	 * @return 客户经理名称
	 */
	public String getCusMngName() {
	 	return cusMngName;
	}
	/**
	 * @设置 客户经理名称
	 * @param cusMngName
	 */
	public void setCusMngName(String cusMngName) {
	 	this.cusMngName = cusMngName;
	}
	/**
	 * @return 客户经理手机
	 */
	public String getCusMngMobile() {
	 	return cusMngMobile;
	}
	/**
	 * @设置 客户经理手机
	 * @param cusMngMobile
	 */
	public void setCusMngMobile(String cusMngMobile) {
	 	this.cusMngMobile = cusMngMobile;
	}
	/**
	 * @return 机构号
	 */
	public String getCusMngBrNo() {
	 	return cusMngBrNo;
	}
	/**
	 * @设置 机构号
	 * @param cusMngBrNo
	 */
	public void setCusMngBrNo(String cusMngBrNo) {
	 	this.cusMngBrNo = cusMngBrNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getCusMngBrName() {
	 	return cusMngBrName;
	}
	/**
	 * @设置 部门名称
	 * @param cusMngBrName
	 */
	public void setCusMngBrName(String cusMngBrName) {
	 	this.cusMngBrName = cusMngBrName;
	}
	/**
	 * @return 员工编号
	 */
	public String getUserNo() {
	 	return userNo;
	}
	/**
	 * @设置 员工编号
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
	 	this.userNo = userNo;
	}
	/**
	 * @return 角色号
	 */
	public String getRoleNo() {
	 	return roleNo;
	}
	/**
	 * @设置 角色号
	 * @param roleNo
	 */
	public void setRoleNo(String roleNo) {
	 	this.roleNo = roleNo;
	}
	/**
	 * @return 状态 1 启用 0 注销
	 */
	public String getOpSts() {
	 	return opSts;
	}
	/**
	 * @设置 状态 1 启用 0 注销
	 * @param opSts
	 */
	public void setOpSts(String opSts) {
	 	this.opSts = opSts;
	}
	
	public int getCut() {
		return cut;
	}
	
	public void setCut(int cut) {
		this.cut = cut;
	}
	
	public String getChannelType() {
		return channelType;
	}
	
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	
}