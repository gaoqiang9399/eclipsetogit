package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: WarnInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Mar 08 11:22:01 CST 2017
* @version：1.0
**/
public class WarnInfo extends BaseDomain {
	private String warnId;//预警编号
	private String warnType;//预警类型
	private String warnCont;//预警内容
	private String warnfixMethod;//解决方法
	private String warnfixCusId;//解决人
	private String warnfixCusName;//解决人名称
	private String warnfixDate;//解决日期
	private String pledgeId;//押品编号

	/**
	 * @return 预警编号
	 */
	public String getWarnId() {
	 	return warnId;
	}
	/**
	 * @设置 预警编号
	 * @param warnId
	 */
	public void setWarnId(String warnId) {
	 	this.warnId = warnId;
	}
	/**
	 * @return 预警类型
	 */
	public String getWarnType() {
	 	return warnType;
	}
	/**
	 * @设置 预警类型
	 * @param warnType
	 */
	public void setWarnType(String warnType) {
	 	this.warnType = warnType;
	}
	/**
	 * @return 预警内容
	 */
	public String getWarnCont() {
	 	return warnCont;
	}
	/**
	 * @设置 预警内容
	 * @param warnCont
	 */
	public void setWarnCont(String warnCont) {
	 	this.warnCont = warnCont;
	}
	/**
	 * @return 解决方法
	 */
	public String getWarnfixMethod() {
	 	return warnfixMethod;
	}
	/**
	 * @设置 解决方法
	 * @param warnfixMethod
	 */
	public void setWarnfixMethod(String warnfixMethod) {
	 	this.warnfixMethod = warnfixMethod;
	}
	/**
	 * @return 解决人
	 */
	public String getWarnfixCusId() {
	 	return warnfixCusId;
	}
	/**
	 * @设置 解决人
	 * @param warnfixCusId
	 */
	public void setWarnfixCusId(String warnfixCusId) {
	 	this.warnfixCusId = warnfixCusId;
	}
	/**
	 * @return 解决人名称
	 */
	public String getWarnfixCusName() {
	 	return warnfixCusName;
	}
	/**
	 * @设置 解决人名称
	 * @param warnfixCusName
	 */
	public void setWarnfixCusName(String warnfixCusName) {
	 	this.warnfixCusName = warnfixCusName;
	}
	/**
	 * @return 解决日期
	 */
	public String getWarnfixDate() {
	 	return warnfixDate;
	}
	/**
	 * @设置 解决日期
	 * @param warnfixDate
	 */
	public void setWarnfixDate(String warnfixDate) {
	 	this.warnfixDate = warnfixDate;
	}
	/**
	 * @return 押品编号
	 */
	public String getPledgeId() {
	 	return pledgeId;
	}
	/**
	 * @设置 押品编号
	 * @param pledgeId
	 */
	public void setPledgeId(String pledgeId) {
	 	this.pledgeId = pledgeId;
	}
}