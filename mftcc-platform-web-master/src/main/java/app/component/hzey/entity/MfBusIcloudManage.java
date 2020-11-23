package app.component.hzey.entity;
import app.base.BaseDomain;
/**
* Title: MfBusIcloudManage.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 19 15:07:00 CST 2017
* @version：1.0
**/
public class MfBusIcloudManage extends BaseDomain {
	private String icloudId;//icloud账号
	private String icloudPwd;//icloud密码
	private String cusNo;//绑定客户号
	private String cusName;//绑定客户姓名
	private String iphoneType;//绑定iphone型号
	private String regTime;//创建时间
	private String lstModTime;//修改时间
	private String regDate;//创建日期
	private String lstModDate;//修改日期
	private String opNo;//最后操作人编号
	private String opName;//最后操作人姓名
	private String brNo;//所属部门编号
	private String brName;//所属部门名称
	private String status;//当前绑定状态：0、未绑定；1、已绑定；2、已下发；3、已绑手机 4、已修改密码 5、已锁定手机
	private String available;//是否可用（是否展示/删除）0、不可用 1、可用 

	/**
	 * @return icloud账号
	 */
	public String getIcloudId() {
	 	return icloudId;
	}
	/**
	 * @设置 icloud账号
	 * @param icloudId
	 */
	public void setIcloudId(String icloudId) {
	 	this.icloudId = icloudId;
	}
	/**
	 * @return icloud密码
	 */
	public String getIcloudPwd() {
	 	return icloudPwd;
	}
	/**
	 * @设置 icloud密码
	 * @param icloudPwd
	 */
	public void setIcloudPwd(String icloudPwd) {
	 	this.icloudPwd = icloudPwd;
	}
	/**
	 * @return 绑定客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 绑定客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 绑定客户姓名
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 绑定客户姓名
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 绑定iphone型号
	 */
	public String getIphoneType() {
	 	return iphoneType;
	}
	/**
	 * @设置 绑定iphone型号
	 * @param iphoneType
	 */
	public void setIphoneType(String iphoneType) {
	 	this.iphoneType = iphoneType;
	}
	/**
	 * @return 创建时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 创建时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 创建日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 创建日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	/**
	 * @return 修改日期
	 */
	public String getLstModDate() {
	 	return lstModDate;
	}
	/**
	 * @设置 修改日期
	 * @param lstModDate
	 */
	public void setLstModDate(String lstModDate) {
	 	this.lstModDate = lstModDate;
	}
	/**
	 * @return 最后操作人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 最后操作人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 最后操作人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 最后操作人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 所属部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 所属部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 所属部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 所属部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 当前绑定状态：0、未绑定；1、已绑定；2、已下发；3、已绑手机 4、已修改密码 5、已锁定手机
	 */
	public String getStatus() {
	 	return status;
	}
	/**
	 * @设置 当前绑定状态：0、未绑定；1、已绑定；2、已下发；3、已绑手机 4、已修改密码 5、已锁定手机
	 * @param status
	 */
	public void setStatus(String status) {
	 	this.status = status;
	}
	/**
	 * @return 是否可用（是否展示/删除）0、不可用 1、可用 
	 */
	public String getAvailable() {
	 	return available;
	}
	/**
	 * @设置 是否可用（是否展示/删除）0、不可用 1、可用 
	 * @param available
	 */
	public void setAvailable(String available) {
	 	this.available = available;
	}
}