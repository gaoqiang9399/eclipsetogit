package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssStorehouse.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Aug 16 18:28:15 CST 2017
* @version：1.0
**/
public class PssStorehouse extends BaseDomain {
	private String storehouseId;//仓库ID
	private String storehouseName;//仓库名称
	private String storehouseAddr;//仓库地址
	private String storehousePhone;//仓库电话
	private String storehouseKeeper;//仓库联系人
	private String storehouseMobile;//仓库联系人手机
	private String flag;//启用标志
	private String regOpNo;//创建人编号
	private String regOpName;//创建人名称
	private String regBrNo;//创建人机构编号
	private String regBrName;//创建人机构名称
	private String regTime;//创建时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间
	private String displayFlag;
	private String brNo; //所属机构编号
	private String brName; //所属机构名称

	/**
	 * @return 仓库ID
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 仓库ID
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
	}
	/**
	 * @return 仓库名称
	 */
	public String getStorehouseName() {
	 	return storehouseName;
	}
	/**
	 * @设置 仓库名称
	 * @param storehouseName
	 */
	public void setStorehouseName(String storehouseName) {
	 	this.storehouseName = storehouseName;
	}
	/**
	 * @return 启用标志
	 */
	public String getFlag() {
	 	return flag;
	}
	/**
	 * @设置 启用标志
	 * @param flag
	 */
	public void setFlag(String flag) {
	 	this.flag = flag;
	}
	/**
	 * @return 创建人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 创建人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 创建人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 创建人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 创建人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 创建人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 创建人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 创建人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
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
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getStorehouseAddr() {
		return storehouseAddr;
	}
	public void setStorehouseAddr(String storehouseAddr) {
		this.storehouseAddr = storehouseAddr;
	}
	public String getStorehousePhone() {
		return storehousePhone;
	}
	public void setStorehousePhone(String storehousePhone) {
		this.storehousePhone = storehousePhone;
	}
	public String getStorehouseKeeper() {
		return storehouseKeeper;
	}
	public void setStorehouseKeeper(String storehouseKeeper) {
		this.storehouseKeeper = storehouseKeeper;
	}
	public String getStorehouseMobile() {
		return storehouseMobile;
	}
	public void setStorehouseMobile(String storehouseMobile) {
		this.storehouseMobile = storehouseMobile;
	}
	public String getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}
	/**
	 * 获取所属机构编号
	 * @return 所属机构编号
	 */
	public String getBrNo() {
		return brNo;
	}
	/**
	 * 设置所属机构编号
	 * @param brNo 所属机构编号
	 */
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	/**
	 * 获取所属机构名称
	 * @return 所属机构名称
	 */
	public String getBrName() {
		return brName;
	}
	/**
	 * 设置所属机构名称
	 * @param brName 所属机构名称
	 */
	public void setBrName(String brName) {
		this.brName = brName;
	}
	
}