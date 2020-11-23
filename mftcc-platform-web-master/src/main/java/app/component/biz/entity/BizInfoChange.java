package app.component.biz.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: BizInfoChange.java
* Description:
* @author：@dhcc.com.cn
* @Wed Apr 20 06:08:57 GMT 2016
* @version：1.0
**/
public class BizInfoChange extends BaseDomain {
	private String changeNo;//变更号
	private String date;//变更日期
	private String time;//变更时间
	private String changeType;//变更类型
	private String bizType;//业务类型（1客户、2业务）
	private String cont;//内容
	private String relNo;//关联号
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private List<BizInfoChange> subList;//子集

	/**
	 * @return 变更号
	 */
	public String getChangeNo() {
	 	return changeNo;
	}
	/**
	 * @设置 变更号
	 * @param changeNo
	 */
	public void setChangeNo(String changeNo) {
	 	this.changeNo = changeNo;
	}
	/**
	 * @return 变更日期
	 */
	public String getDate() {
	 	return date;
	}
	/**
	 * @设置 变更日期
	 * @param date
	 */
	public void setDate(String date) {
	 	this.date = date;
	}
	
	/**
	 * @return 变更类型
	 */
	public String getChangeType() {
	 	return changeType;
	}
	/**
	 * @设置 变更类型
	 * @param changeType
	 */
	public void setChangeType(String changeType) {
	 	this.changeType = changeType;
	}
	/**
	 * @return 内容
	 */
	public String getCont() {
	 	return cont;
	}
	/**
	 * @设置 内容
	 * @param cont
	 */
	public void setCont(String cont) {
	 	this.cont = cont;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public List<BizInfoChange> getSubList() {
		return subList;
	}
	public void setSubList(List<BizInfoChange> subList) {
		this.subList = subList;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	
	
}