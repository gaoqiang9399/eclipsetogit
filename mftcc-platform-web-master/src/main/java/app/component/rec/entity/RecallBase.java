package app.component.rec.entity;
import app.base.BaseDomain;
/**
* Title: RecallBase.java
* Description:
* @author：@dhcc.com.cn
* @Tue Mar 15 09:24:17 GMT 2016
* @version：1.0
**/
public class RecallBase extends BaseDomain {
	private String taskNo;
	private String pactId;
	private String conNo;
	private String fincShowId;
	private String fincId;
	private String appId;
	private String appName;
	private String cusNo;
	private String cusName;
	private String cusTel;
	private String recallType;
	private String recallDate;
	private String recallEndDate;
	private String recallExeDate;
	private String startRecallWay;
	private String recallWay;
	private String recallDesc;
	private String mgrNo;
	private String mgrName;
	private String returnDesc;
	private String isLaw;
	private String lawDesc;
	private String riskLevel;
	private String prodType;
	
	private Integer mustCompleteDays;
	private Integer curOverDays;
	private Integer termLimit;
	private Integer repayTerm;
	
	private Double recallUnpayAmt1;
	private Double recallUnpayAmt2;
	private Double recallAmt;
	private Double brcContAmt;
	private String recallSts;
	
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	
	private Integer onlineTask;
	private Integer overTask;
	
	private String[] taskNoArray;
	private String taskNoStr;
	
	private String cusContactName;
	private String mustCompleteDate;
	
	private String regNo;
	private String regName;
	private String orgNo;
	private String orgName;
	private String regDate;
	private String lstDate;
	private String lstRegNo;
	private String lstRegName;
	private String scopeType;
	private Integer recallTimes;
	/*20190306yxl添加快递单号，收件地址，快递公司*/
	private String deliveryAddress;//送达地址
	private String courierCompany;//快递公司
	private String courierNumber;//快递单号
	
	// 铁甲网-begin
	private String recallCusType;// 客户分类  1-A 2-B 3-C 4-D
	private String recallActionType;// 催收动作  1-拨打借款人 2-拨打借款人配偶 3-拨打担保人 4-拨打联系人;
	private String recallResultType;// 催收结果  1-转告本人 2-愿意代偿 3-拒绝配合 4-借款人本人接听 5-机主更换 6-联系不到借款人 7-否认认识借款人 8-屏蔽设置 9-无人接听 10-无法接通 11-通话中 12-拒接 13-关机 14-停机 15-空号 16-忙音 17-其他
	private String promiseDate;// 承诺时间
	private Double promiseAmt;//  承诺金额
	private String takeDeviceStatus;//拖机状态 1-是 0-否
	private String deviceAddress;// 设备位置
	private String lawsuitStatus;// 诉讼状态 1-发起诉讼 2-委托失败 3-诉讼中 4-诉讼终止
	private String recallId;//流水号,上传要件使用
	// 铁甲网-end
	
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLstDate() {
		return lstDate;
	}
	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public String getLstRegNo() {
		return lstRegNo;
	}
	public void setLstRegNo(String lstRegNo) {
		this.lstRegNo = lstRegNo;
	}
	public String getLstRegName() {
		return lstRegName;
	}
	public void setLstRegName(String lstRegName) {
		this.lstRegName = lstRegName;
	}
	public String getCusContactName() {
		return cusContactName;
	}
	public void setCusContactName(String cusContactName) {
		this.cusContactName = cusContactName;
	}
	public String getMustCompleteDate() {
		return mustCompleteDate;
	}
	public void setMustCompleteDate(String mustCompleteDate) {
		this.mustCompleteDate = mustCompleteDate;
	}
	public String[] getTaskNoArray() {
		return taskNoArray;
	}
	public void setTaskNoArray(String[] taskNoArray) {
		this.taskNoArray = taskNoArray;
	}
	public String getTaskNoStr() {
		return taskNoStr;
	}
	public void setTaskNoStr(String taskNoStr) {
		this.taskNoStr = taskNoStr;
	}
	public String getRecallSts() {
		return recallSts;
	}
	public void setRecallSts(String recallSts) {
		this.recallSts = recallSts;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getConNo() {
		return conNo;
	}
	public void setConNo(String conNo) {
		this.conNo = conNo;
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
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getCusTel() {
		return cusTel;
	}
	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}
	public String getRecallType() {
		return recallType;
	}
	public void setRecallType(String recallType) {
		this.recallType = recallType;
	}
	public String getRecallDate() {
		return recallDate;
	}
	public void setRecallDate(String recallDate) {
		this.recallDate = recallDate;
	}
	public String getRecallEndDate() {
		return recallEndDate;
	}
	public void setRecallEndDate(String recallEndDate) {
		this.recallEndDate = recallEndDate;
	}
	public String getRecallExeDate() {
		return recallExeDate;
	}
	public void setRecallExeDate(String recallExeDate) {
		this.recallExeDate = recallExeDate;
	}
	public String getStartRecallWay() {
		return startRecallWay;
	}
	public void setStartRecallWay(String startRecallWay) {
		this.startRecallWay = startRecallWay;
	}
	public String getRecallWay() {
		return recallWay;
	}
	public void setRecallWay(String recallWay) {
		this.recallWay = recallWay;
	}
	public String getRecallDesc() {
		return recallDesc;
	}
	public void setRecallDesc(String recallDesc) {
		this.recallDesc = recallDesc;
	}
	public String getMgrNo() {
		return mgrNo;
	}
	public void setMgrNo(String mgrNo) {
		this.mgrNo = mgrNo;
	}
	public String getMgrName() {
		return mgrName;
	}
	public void setMgrName(String mgrName) {
		this.mgrName = mgrName;
	}
	public String getReturnDesc() {
		return returnDesc;
	}
	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
	public String getIsLaw() {
		return isLaw;
	}
	public void setIsLaw(String isLaw) {
		this.isLaw = isLaw;
	}
	public String getLawDesc() {
		return lawDesc;
	}
	public void setLawDesc(String lawDesc) {
		this.lawDesc = lawDesc;
	}
	public String getRiskLevel() {
		return riskLevel;
	}
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public Integer getMustCompleteDays() {
		return mustCompleteDays;
	}
	public void setMustCompleteDays(Integer mustCompleteDays) {
		this.mustCompleteDays = mustCompleteDays;
	}
	public Integer getCurOverDays() {
		return curOverDays;
	}
	public void setCurOverDays(Integer curOverDays) {
		this.curOverDays = curOverDays;
	}
	public Integer getTermLimit() {
		return termLimit;
	}
	public void setTermLimit(Integer termLimit) {
		this.termLimit = termLimit;
	}
	public Integer getRepayTerm() {
		return repayTerm;
	}
	public void setRepayTerm(Integer repayTerm) {
		this.repayTerm = repayTerm;
	}
	public Double getRecallUnpayAmt1() {
		return recallUnpayAmt1;
	}
	public void setRecallUnpayAmt1(Double recallUnpayAmt1) {
		this.recallUnpayAmt1 = recallUnpayAmt1;
	}
	public Double getRecallUnpayAmt2() {
		return recallUnpayAmt2;
	}
	public void setRecallUnpayAmt2(Double recallUnpayAmt2) {
		this.recallUnpayAmt2 = recallUnpayAmt2;
	}
	public Double getRecallAmt() {
		return recallAmt;
	}
	public void setRecallAmt(Double recallAmt) {
		this.recallAmt = recallAmt;
	}
	public Double getBrcContAmt() {
		return brcContAmt;
	}
	public void setBrcContAmt(Double brcContAmt) {
		this.brcContAmt = brcContAmt;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getExt4() {
		return ext4;
	}
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	public String getExt5() {
		return ext5;
	}
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	public Integer getOnlineTask() {
		return onlineTask;
	}
	public void setOnlineTask(Integer onlineTask) {
		this.onlineTask = onlineTask;
	}
	public Integer getOverTask() {
		return overTask;
	}
	public void setOverTask(Integer overTask) {
		this.overTask = overTask;
	}
	public Integer getRecallTimes() {
		return recallTimes;
	}
	public void setRecallTimes(Integer recallTimes) {
		this.recallTimes = recallTimes;
	}
	public String getScopeType() {
		return scopeType;
	}
	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}
	public String getFincShowId() {
		return fincShowId;
	}
	public void setFincShowId(String fincShowId) {
		this.fincShowId = fincShowId;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getRecallCusType() {
		return recallCusType;
	}
	public void setRecallCusType(String recallCusType) {
		this.recallCusType = recallCusType;
	}
	public String getRecallActionType() {
		return recallActionType;
	}
	public void setRecallActionType(String recallActionType) {
		this.recallActionType = recallActionType;
	}
	public String getRecallResultType() {
		return recallResultType;
	}
	public void setRecallResultType(String recallResultType) {
		this.recallResultType = recallResultType;
	}
	public String getPromiseDate() {
		return promiseDate;
	}
	public void setPromiseDate(String promiseDate) {
		this.promiseDate = promiseDate;
	}
	public Double getPromiseAmt() {
		return promiseAmt;
	}
	public void setPromiseAmt(Double promiseAmt) {
		this.promiseAmt = promiseAmt;
	}
	public String getTakeDeviceStatus() {
		return takeDeviceStatus;
	}
	public void setTakeDeviceStatus(String takeDeviceStatus) {
		this.takeDeviceStatus = takeDeviceStatus;
	}
	public String getDeviceAddress() {
		return deviceAddress;
	}
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}
	public String getLawsuitStatus() {
		return lawsuitStatus;
	}
	public void setLawsuitStatus(String lawsuitStatus) {
		this.lawsuitStatus = lawsuitStatus;
	}
	public String getRecallId() {
		return recallId;
	}
	public void setRecallId(String recallId) {
		this.recallId = recallId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(String courierCompany) {
		this.courierCompany = courierCompany;
	}

	public String getCourierNumber() {
		return courierNumber;
	}

	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
}