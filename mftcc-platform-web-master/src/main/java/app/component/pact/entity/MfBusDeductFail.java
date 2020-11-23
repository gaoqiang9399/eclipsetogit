package app.component.pact.entity;
import app.base.BaseDomain;
/**
* Title: MfBusDeductFail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Sep 09 14:18:17 CST 2017
* @version：1.0
**/
public class MfBusDeductFail extends BaseDomain {
	private String deductId;//唯一编号
	private String returnPlanId;//还款计划id
	private String cusName;
	private String appName;//项目名称
	private Double deductAmt;//划扣总额
	private String deductSts;//划扣状态 0：失败 1成功 2处理中
	private String deductErrorMsg;//划扣失败原因
	private String delFlag;//删除标识 （1：删除）
	private String regTime;//登记时间（格式：yyyyMMdd HH:mm:ss）
	private String lstModTime;//最后修改时间（格式：yyyyMMdd HH:mm:ss）
	/******冗余 供列表展示******/
	private String cusNo;
	private String appId;
	private String fincId;
	private String pactId;//合同ID
	private String pactNo;//客户查看的合同号
	private String fincShowId;//客户查看的借据号
	private String termNum;//期号
	private String planEndDate;//到期时间
	/**
	 * @return 唯一编号
	 */
	public String getDeductId() {
	 	return deductId;
	}
	/**
	 * @设置 唯一编号
	 * @param deductId
	 */
	public void setDeductId(String deductId) {
	 	this.deductId = deductId;
	}
	/**
	 * @return 还款计划id
	 */
	public String getReturnPlanId() {
	 	return returnPlanId;
	}
	/**
	 * @设置 还款计划id
	 * @param returnPlanId
	 */
	public void setReturnPlanId(String returnPlanId) {
	 	this.returnPlanId = returnPlanId;
	}
	/**
	 * @return 划扣总额
	 */
	public Double getDeductAmt() {
	 	return deductAmt;
	}
	/**
	 * @设置 划扣总额
	 * @param deductAmt
	 */
	public void setDeductAmt(Double deductAmt) {
	 	this.deductAmt = deductAmt;
	}
	/**
	 * @return 划扣失败原因
	 */
	public String getDeductErrorMsg() {
	 	return deductErrorMsg;
	}
	/**
	 * @设置 划扣失败原因
	 * @param deductErrorMsg
	 */
	public void setDeductErrorMsg(String deductErrorMsg) {
	 	this.deductErrorMsg = deductErrorMsg;
	}
	/**
	 * @return 删除标识 （1：删除）
	 */
	public String getDelFlag() {
	 	return delFlag;
	}
	/**
	 * @设置 删除标识 （1：删除）
	 * @param delFlag
	 */
	public void setDelFlag(String delFlag) {
	 	this.delFlag = delFlag;
	}
	/**
	 * @return 登记时间（格式：yyyyMMdd HH:mm:ss）
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间（格式：yyyyMMdd HH:mm:ss）
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间（格式：yyyyMMdd HH:mm:ss）
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间（格式：yyyyMMdd HH:mm:ss）
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getFincShowId() {
		return fincShowId;
	}
	public void setFincShowId(String fincShowId) {
		this.fincShowId = fincShowId;
	}
	public String getTermNum() {
		return termNum;
	}
	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}
	public String getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	/**
	 * 划扣状态 0：失败 1成功 2处理中
	 * @return
	 * @author MaHao
	 * @date 2017-9-12 上午11:37:20
	 */
	public String getDeductSts() {
		return deductSts;
	}
	/**
	 * 划扣状态 0：失败 1成功 2处理中
	 * @param deductSts
	 * @author MaHao
	 * @date 2017-9-12 上午11:37:15
	 */
	public void setDeductSts(String deductSts) {
		this.deductSts = deductSts;
	}
	
}