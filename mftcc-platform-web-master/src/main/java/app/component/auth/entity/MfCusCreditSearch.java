package app.component.auth.entity;

import app.base.BaseDomain;

public class MfCusCreditSearch extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String idNum;//证件号码
	private String id;//
	private String pactNo;//合同展示号
	private String creditAppId;//授信申请编号
	private Double creditSum;//授信总额(合同金额)
	private Double authBal;//可用余额(合同余额)
	private Integer creditTerm;//授信期限
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private String regTime;//合同签订时间
	private String remark;//意见描述
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String lstModTime;//最近修改时间
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appId;//业务编号
	private String appName;//项目名称
	private String creditUseType;//用信类型
	private String pactSignDate;//合同签约日期
	private String cusType;//客户类型
	private String isCeilingLoop;//额度是否可循环
	private String creditType;//授信类型
	private String modelId;//模型编号
	private String creditSts;//授信状态
	private String wkfAppId;  //业务流程id
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getCreditAppId() {
		return creditAppId;
	}
	public void setCreditAppId(String creditAppId) {
		this.creditAppId = creditAppId;
	}
	public Double getCreditSum() {
		return creditSum;
	}
	public void setCreditSum(Double creditSum) {
		this.creditSum = creditSum;
	}
	public Double getAuthBal() {
		return authBal;
	}
	public void setAuthBal(Double authBal) {
		this.authBal = authBal;
	}
	public Integer getCreditTerm() {
		return creditTerm;
	}
	public void setCreditTerm(Integer creditTerm) {
		this.creditTerm = creditTerm;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
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
	public String getCreditUseType() {
		return creditUseType;
	}
	public void setCreditUseType(String creditUseType) {
		this.creditUseType = creditUseType;
	}
	public String getPactSignDate() {
		return pactSignDate;
	}
	public void setPactSignDate(String pactSignDate) {
		this.pactSignDate = pactSignDate;
	}
	public String getCusType() {
		return cusType;
	}
	public void setCusType(String cusType) {
		this.cusType = cusType;
	}
	public String getIsCeilingLoop() {
		return isCeilingLoop;
	}
	public void setIsCeilingLoop(String isCeilingLoop) {
		this.isCeilingLoop = isCeilingLoop;
	}
	public String getCreditType() {
		return creditType;
	}
	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getCreditSts() {
		return creditSts;
	}
	public void setCreditSts(String creditSts) {
		this.creditSts = creditSts;
	}
	public String getWkfAppId() {
		return wkfAppId;
	}
	public void setWkfAppId(String wkfAppId) {
		this.wkfAppId = wkfAppId;
	}
}
