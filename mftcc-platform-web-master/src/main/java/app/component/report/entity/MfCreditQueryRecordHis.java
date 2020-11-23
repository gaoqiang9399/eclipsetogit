package app.component.report.entity;
import app.base.BaseDomain;
/**
* Title: MfCreditQueryRecordInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Nov 30 09:15:13 CST 2017
* @version：1.0
**/
public class MfCreditQueryRecordHis extends BaseDomain {
	private String appId;//业务申请号
	private String cusName;//客户名称
	private String idNum;//客户身份证号
	private String queryReason;//查询原因
	private String authorizeDate;//授权日期（开始日期）
	private String queryDate;//查询日期
	private String opName;//登记人姓名
	private String creditOpNo;//征信查询员编号
	private String brName;//登记部门名称
	private String busStage;//业务办理情况
	private String regHomeAddre;//身份证地址
	private String trackDate;//跟踪日期
	private String trackOpName;//负责人
	private String remark;//备注

	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 客户身份证号
	 */
	public String getIdNum() {
	 	return idNum;
	}
	/**
	 * @设置 客户身份证号
	 * @param idNum
	 */
	public void setIdNum(String idNum) {
	 	this.idNum = idNum;
	}
	
	/**
	 * @return 查询日期
	 */
	public String getQueryDate() {
	 	return queryDate;
	}
	/**
	 * @设置 查询日期
	 * @param queryDate
	 */
	public void setQueryDate(String queryDate) {
	 	this.queryDate = queryDate;
	}
	
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getQueryReason() {
		return queryReason;
	}
	public void setQueryReason(String queryReason) {
		this.queryReason = queryReason;
	}
	public String getAuthorizeDate() {
		return authorizeDate;
	}
	public void setAuthorizeDate(String authorizeDate) {
		this.authorizeDate = authorizeDate;
	}
	public String getCreditOpNo() {
		return creditOpNo;
	}
	public void setCreditOpNo(String creditOpNo) {
		this.creditOpNo = creditOpNo;
	}
	public String getBusStage() {
		return busStage;
	}
	public void setBusStage(String busStage) {
		this.busStage = busStage;
	}
	public String getRegHomeAddre() {
		return regHomeAddre;
	}
	public void setRegHomeAddre(String regHomeAddre) {
		this.regHomeAddre = regHomeAddre;
	}
	public String getTrackDate() {
		return trackDate;
	}
	public void setTrackDate(String trackDate) {
		this.trackDate = trackDate;
	}
	public String getTrackOpName() {
		return trackOpName;
	}
	public void setTrackOpName(String trackOpName) {
		this.trackOpName = trackOpName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}