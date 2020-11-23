/**
 * Copyright (C) DXHM 版权所有
 * 文件名： RiskBigBean.java
 * 包名： app.component.rules.entity
 * 说明：
 * @author 沈浩兵
 * @date 2017-1-17 下午5:07:22
 * @version V1.0
 */ 
package app.component.rules.entity;

import java.util.Map;

/**
 * 类名： RiskBigBean
 * 描述：风险拦截大实体
 * @author 沈浩兵
 * @date 2017-1-17 下午5:07:22
 *
 *
 */
public class RiskBigBean {
	//客户号
	private String cusNo;
	//业务申请号
	private String appId;
	//押品编号
	private String pledgeNo;
	//合同编号
	private String pactId;
	//放款编号
	private String fincId;
	//客户年收入
	private Double bussIncome = 0.00;
	//客户评级等级
	private String cusLevelId;
	//客户资料个数
	private String mustinputcnt;
	//客户分类
	private String classifyType;
	//营业结束日期
	private String bussEndDate;
	//客户存在未完结项目标识 1存在 0不存在
	private String noEndPactFlag;
	//客户存在未完结逾期项目标识 1存在 0不存在
	private String overduePactFlag;
	//该客户的法定代表人存在未完结逾期项目标识 1存在 0不存在
	private String legalManNoEndPact;
	//客户授信金额
	private Double authAmt = 0.00;
	//客户融资金额
	private Double appAmt = 0.00;
	//业务担保金额 即抵质押物的价值
	private String envalue;
	//财务报表检查
	private String checkCusFinMin;
	//应收账款检查
	private String checkPledgeExpire;
	//买卖双方是否为关联企业
	private String checkRelstion;
	//当前日期
	private String nowDate;
	//规则名（编号）
	private String rulesNo;
	//获得结果
	private Map<String,Object> resultMap;
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
	public String getPledgeNo() {
		return pledgeNo;
	}
	public void setPledgeNo(String pledgeNo) {
		this.pledgeNo = pledgeNo;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	
	public Double getBussIncome() {
		return bussIncome;
	}
	public void setBussIncome(Double bussIncome) {
		this.bussIncome = bussIncome;
	}
	public String getCusLevelId() {
		return cusLevelId;
	}
	public void setCusLevelId(String cusLevelId) {
		this.cusLevelId = cusLevelId;
	}
	public String getMustinputcnt() {
		return mustinputcnt;
	}
	public void setMustinputcnt(String mustinputcnt) {
		this.mustinputcnt = mustinputcnt;
	}
	public String getClassifyType() {
		return classifyType;
	}
	public void setClassifyType(String classifyType) {
		this.classifyType = classifyType;
	}
	public String getBussEndDate() {
		return bussEndDate;
	}
	public void setBussEndDate(String bussEndDate) {
		this.bussEndDate = bussEndDate;
	}
	public String getNoEndPactFlag() {
		return noEndPactFlag;
	}
	public void setNoEndPactFlag(String noEndPactFlag) {
		this.noEndPactFlag = noEndPactFlag;
	}
	public String getOverduePactFlag() {
		return overduePactFlag;
	}
	public void setOverduePactFlag(String overduePactFlag) {
		this.overduePactFlag = overduePactFlag;
	}
	public String getLegalManNoEndPact() {
		return legalManNoEndPact;
	}
	public void setLegalManNoEndPact(String legalManNoEndPact) {
		this.legalManNoEndPact = legalManNoEndPact;
	}
	public Double getAuthAmt() {
		return authAmt;
	}
	public void setAuthAmt(Double authAmt) {
		this.authAmt = authAmt;
	}
	
	public Double getAppAmt() {
		return appAmt;
	}
	public void setAppAmt(Double appAmt) {
		this.appAmt = appAmt;
	}
	public String getEnvalue() {
		return envalue;
	}
	public void setEnvalue(String envalue) {
		this.envalue = envalue;
	}
	public String getCheckCusFinMin() {
		return checkCusFinMin;
	}
	public void setCheckCusFinMin(String checkCusFinMin) {
		this.checkCusFinMin = checkCusFinMin;
	}
	public String getCheckPledgeExpire() {
		return checkPledgeExpire;
	}
	public void setCheckPledgeExpire(String checkPledgeExpire) {
		this.checkPledgeExpire = checkPledgeExpire;
	}
	public String getCheckRelstion() {
		return checkRelstion;
	}
	public void setCheckRelstion(String checkRelstion) {
		this.checkRelstion = checkRelstion;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public String getRulesNo() {
		return rulesNo;
	}
	public void setRulesNo(String rulesNo) {
		this.rulesNo = rulesNo;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
}
