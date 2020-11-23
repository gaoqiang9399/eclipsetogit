package app.component.extension.refuse.entity;
import app.base.BaseDomain;

import java.util.List;

/**
* Title: MfArmourRefuseConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jan 03 15:38:12 CST 2018
* @version：1.0
**/
public class MfArmourRefuseConfig extends BaseDomain {
	private String id;//主键id
	private String refNum;//拒绝编号
	private String refName;//拒绝名称
	private String levFlag;//级别标志 1-一级 2-二级
	private String uplev;//上级编号
	private String type;//意见类型
	private String useFlag;//启用标志
	private String remark;//具体描述
	private String name;
	private String pId;
	private List<String> uplevList;

	/**
	 * @return 主键id
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 主键id
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 拒绝编号
	 */
	public String getRefNum() {
	 	return refNum;
	}
	/**
	 * @设置 拒绝编号
	 * @param refNum
	 */
	public void setRefNum(String refNum) {
	 	this.refNum = refNum;
	}
	/**
	 * @return 拒绝名称
	 */
	public String getRefName() {
	 	return refName;
	}
	/**
	 * @设置 拒绝名称
	 * @param refName
	 */
	public void setRefName(String refName) {
	 	this.refName = refName;
	}
	/**
	 * @return 级别标志 1-一级 2-二级
	 */
	public String getLevFlag() {
	 	return levFlag;
	}
	/**
	 * @设置 级别标志 1-一级 2-二级
	 * @param levFlag
	 */
	public void setLevFlag(String levFlag) {
	 	this.levFlag = levFlag;
	}
	/**
	 * @return 上级编号
	 */
	public String getUplev() {
	 	return uplev;
	}
	/**
	 * @设置 上级编号
	 * @param uplev
	 */
	public void setUplev(String uplev) {
	 	this.uplev = uplev;
	}
	/**
	 * @return 启用标志
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标志
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 具体描述
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 具体描述
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getUplevList() {
		return uplevList;
	}

	public void setUplevList(List<String> uplevList) {
		this.uplevList = uplevList;
	}
}