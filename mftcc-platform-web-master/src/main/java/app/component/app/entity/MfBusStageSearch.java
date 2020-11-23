/**
 * 
 */
package app.component.app.entity;

import app.base.BaseDomain;

/**
 * 业务阶段查询
 * @author QiuZhao
 *
 */
public class MfBusStageSearch extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String appId;//申请号
	private String appName;//项目名称
	private String pactId;//合同id
	private String pactNo;//合同编号
	private String pactSts;//合同状态
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private String cusType;//客户类型
	private Double appAmt;//申请金额
	private Double fincRate;//融资款利率
	private String termType;//期限类型 1-月 2-天
	private Integer term;//申请期限数值
	private String termShow;//申请期限显示
	private String contactsTel;//联系电话
	private String rateType;//利率类型 1为年利率
	private String manageOpName1;//主办人员名称
	private String busStage;//业务阶段-业务流程中的节点名称
	private String fincSts;//借据状态
	private String fincId;//借据ID
	private String busEntrance;//业务入口
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public Double getAppAmt() {
		return appAmt;
	}
	public void setAppAmt(Double appAmt) {
		this.appAmt = appAmt;
	}
	public Double getFincRate() {
		return fincRate;
	}
	public void setFincRate(Double fincRate) {
		this.fincRate = fincRate;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getTermShow() {
		return termShow;
	}
	public void setTermShow(String termShow) {
		this.termShow = termShow;
	}
	public String getContactsTel() {
		return contactsTel;
	}
	public void setContactsTel(String contactsTel) {
		this.contactsTel = contactsTel;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public String getBusStage() {
		return busStage;
	}
	public void setBusStage(String busStage) {
		this.busStage = busStage;
	}
	public String getFincSts() {
		return fincSts;
	}
	public void setFincSts(String fincSts) {
		this.fincSts = fincSts;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getPactSts() {
		return pactSts;
	}
	public void setPactSts(String pactSts) {
		this.pactSts = pactSts;
	}
	public String getBusEntrance() {
		return busEntrance;
	}
	public void setBusEntrance(String busEntrance) {
		this.busEntrance = busEntrance;
	}
	public String getManageOpName1() {
		return manageOpName1;
	}
	public void setManageOpName1(String manageOpName1) {
		this.manageOpName1 = manageOpName1;
	}
	
}
