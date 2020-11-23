package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysLog.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 01 06:53:50 GMT 2016
* @version：1.0
**/
public class SysLog extends BaseDomain {
	private String logId;//日志流水号
	private String opId;//操作主键
	private String opSeqn;//操作子健（与主键唯一确定一条记录）
	private String opDesc;//操作描述
	private String opTab;//操作表
	private String opDate;//操作日期
	private String opTime;//操作时间
	private String opNo;//操作员号
	private String brNo;//操作员机构

	/**
	 * @return 日志流水号
	 */
	public String getLogId() {
	 	return logId;
	}
	/**
	 * @设置 日志流水号
	 * @param logId
	 */
	public void setLogId(String logId) {
	 	this.logId = logId;
	}
	/**
	 * @return 操作主键
	 */
	public String getOpId() {
	 	return opId;
	}
	/**
	 * @设置 操作主键
	 * @param opId
	 */
	public void setOpId(String opId) {
	 	this.opId = opId;
	}
	/**
	 * @return 操作子健（与主键唯一确定一条记录）
	 */
	public String getOpSeqn() {
	 	return opSeqn;
	}
	/**
	 * @设置 操作子健（与主键唯一确定一条记录）
	 * @param opSeqn
	 */
	public void setOpSeqn(String opSeqn) {
	 	this.opSeqn = opSeqn;
	}
	/**
	 * @return 操作描述
	 */
	public String getOpDesc() {
	 	return opDesc;
	}
	/**
	 * @设置 操作描述
	 * @param opDesc
	 */
	public void setOpDesc(String opDesc) {
	 	this.opDesc = opDesc;
	}
	/**
	 * @return 操作表
	 */
	public String getOpTab() {
	 	return opTab;
	}
	/**
	 * @设置 操作表
	 * @param opTab
	 */
	public void setOpTab(String opTab) {
	 	this.opTab = opTab;
	}
	/**
	 * @return 操作日期
	 */
	public String getOpDate() {
	 	return opDate;
	}
	/**
	 * @设置 操作日期
	 * @param opDate
	 */
	public void setOpDate(String opDate) {
	 	this.opDate = opDate;
	}
	/**
	 * @return 操作时间
	 */
	public String getOpTime() {
	 	return opTime;
	}
	/**
	 * @设置 操作时间
	 * @param opTime
	 */
	public void setOpTime(String opTime) {
	 	this.opTime = opTime;
	}
	/**
	 * @return 操作员号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员机构
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 操作员机构
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
}