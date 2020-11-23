package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: MfApplyInsertConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 22 14:33:04 CST 2017
* @version：1.0
**/
public class MfApplyInsertConfig extends BaseDomain {
	private String configId;//进件配置编号
	private String reqUrl;//请求地址
	private String configType;//设置类型brNo部门roleNo角色(对应字段)
	private String deptroleNo;//设置的部门或角色编号
	private String deptroleName;//部门或角色名称
	private String regTime;//信息登记时间

	/**
	 * @return 进件配置编号
	 */
	public String getConfigId() {
	 	return configId;
	}
	/**
	 * @设置 进件配置编号
	 * @param configId
	 */
	public void setConfigId(String configId) {
	 	this.configId = configId;
	}
	/**
	 * @return 请求地址
	 */
	public String getReqUrl() {
	 	return reqUrl;
	}
	/**
	 * @设置 请求地址
	 * @param reqUrl
	 */
	public void setReqUrl(String reqUrl) {
	 	this.reqUrl = reqUrl;
	}
	/**
	 * @return 设置类型brNo部门roleNo角色(对应字段)
	 */
	public String getConfigType() {
	 	return configType;
	}
	/**
	 * @设置 设置类型brNo部门roleNo角色(对应字段)
	 * @param configType
	 */
	public void setConfigType(String configType) {
	 	this.configType = configType;
	}
	/**
	 * @return 设置的部门或角色编号
	 */
	public String getDeptroleNo() {
	 	return deptroleNo;
	}
	/**
	 * @设置 设置的部门或角色编号
	 * @param deptroleNo
	 */
	public void setDeptroleNo(String deptroleNo) {
	 	this.deptroleNo = deptroleNo;
	}
	/**
	 * @return 部门或角色名称
	 */
	public String getDeptroleName() {
	 	return deptroleName;
	}
	/**
	 * @设置 部门或角色名称
	 * @param deptroleName
	 */
	public void setDeptroleName(String deptroleName) {
	 	this.deptroleName = deptroleName;
	}
	/**
	 * @return 信息登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 信息登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
}