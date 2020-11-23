package app.component.pact.repay.entity;
import app.base.BaseDomain;
/**
* Title: MfDeductRefundApply.java
* Description:退款信息
* @author：kaifa@dhcc.com.cn
* @Tue Aug 22 11:24:01 CST 2017
* @version：1.0
**/
public class MfRefundFeeApply extends BaseDomain {
	private String id;//减免/退费唯一ID
	private String appType;//申请类型 1-减免 2-退费
	private String appId;//融资业务申请编号
	private String pactId;//合同号
	private String pactNo;//合同展示号
	private String fincId;//放款申请号
	private String appName;//项目名称
	private String cusNo;//客户编号
	private String cusName;//客户名称
	private String pactAmt;//合同金额
	private String fincRate;//合同利率
	private Double recvAmt;//本次实际收到贷款客户还款合计总额（不包括减免的）=prcp_sum+norm_intst_sum+over_intst_sum+cmpd_intst_sum+penalty_sum+fee_sum+bal_amt
	private Double prcpSum;//本次还款本金总额
	private Double normIntstSum;//本次还款正常利息（不包括减免的）
	private Double overIntstSum;//本次还款逾期利息合计（不包括减免的）
	private Double cmpdIntstSum;//本次还款复利利息合计（不包括减免的）
	private Double penaltySum;//本次划款违约金合计（不包括减免的）
	private Double balAmtSum;//冲抵金额
	private Double feeSum;//本次划款费用合计（不包括减免的）（费用收取与还款合并是存储，单独收费时有单独的费用历史表）
	private Double applySum;//本次减免/退费合计
	private Double applyNormIntstSum;//本次减免/退费正常利息合计
	private Double applyOverIntstSum;//本次减免/退费逾期利息合计
	private Double applyCmpdIntstSum;//本次减免/退费复利利息合计
	private Double applyPenaltySum;//本次减免/退费违约金合计
	private Double applyBalAmtSum;//冲抵金额
	private Double applyFeeSum;//本次减免/退费费用合计
	private Double feeAmt1;//费用1
	private Double feeAmt2;//费用2
	private Double feeAmt3;//费用3
	private Double feeAmt4;//费用4
	private Double feeAmt5;//费用5
	private Double feeAmt6;//费用6
	private Double feeAmt7;//费用7
	private Double feeAmt8;//费用8
	private Double feeAmt9;//费用9
	private Double feeAmt10;//费用10
	private Double feeAmt11;//费用11
	private Double feeAmt12;//费用12
	private Double feeAmt13;//费用13
	private Double feeAmt14;//费用14
	private Double feeAmt15;//费用15
	private String feeAmtName1;//费用名称1（对应费用项名称）
	private String feeAmtName2;//费用名称2（对应费用项名称）
	private String feeAmtName3;//费用名称3（对应费用项名称）
	private String feeAmtName4;//费用名称4（对应费用项名称）
	private String feeAmtName5;//费用名称5（对应费用项名称）
	private String feeAmtName6;//费用名称6（对应费用项名称）
	private String feeAmtName7;//费用名称7（对应费用项名称）
	private String feeAmtName8;//费用名称8（对应费用项名称）
	private String feeAmtName9;//费用名称9（对应费用项名称）
	private String feeAmtName10;//费用名称10（对应费用项名称）
	private String feeAmtName11;//费用名称11（对应费用项名称）
	private String feeAmtName12;//费用名称12（对应费用项名称）
	private String feeAmtName13;//费用名称13（对应费用项名称）
	private String feeAmtName14;//费用名称14（对应费用项名称）
	private String feeAmtName15;//费用名称15（对应费用项名称）
	private Double applyFeeAmt1;//本次退费费用金额1
	private Double applyFeeAmt2;//本次退费费用金额2
	private Double applyFeeAmt3;//本次退费费用金额3
	private Double applyFeeAmt4;//本次退费费用金额4
	private Double applyFeeAmt5;//本次退费费用金额5
	private Double applyFeeAmt6;//本次退费费用金额6
	private Double applyFeeAmt7;//本次退费费用金额7
	private Double applyFeeAmt8;//本次退费费用金额8
	private Double applyFeeAmt9;//本次退费费用金额9
	private Double applyFeeAmt10;//本次退费费用金额10
	private Double applyFeeAmt11;//本次退费费用金额11
	private Double applyFeeAmt12;//本次退费费用金额12
	private Double applyFeeAmt13;//本次退费费用金额13
	private Double applyFeeAmt14;//本次退费费用金额14
	private Double applyFeeAmt15;//本次退费费用金额15
	private String itemNoStr;//该借据允许退费的退费项编号
	
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String appTime;//登记时间
	private String lstModTime;//最后修改时间
	private String regDate;//登记日期
	private String lstModDate;//上次修改日期
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
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
	public String getPactAmt() {
		return pactAmt;
	}
	public void setPactAmt(String pactAmt) {
		this.pactAmt = pactAmt;
	}
	public String getFincRate() {
		return fincRate;
	}
	public void setFincRate(String fincRate) {
		this.fincRate = fincRate;
	}
	public Double getRecvAmt() {
		return recvAmt;
	}
	public void setRecvAmt(Double recvAmt) {
		this.recvAmt = recvAmt;
	}
	public Double getPrcpSum() {
		return prcpSum;
	}
	public void setPrcpSum(Double prcpSum) {
		this.prcpSum = prcpSum;
	}
	public Double getNormIntstSum() {
		return normIntstSum;
	}
	public void setNormIntstSum(Double normIntstSum) {
		this.normIntstSum = normIntstSum;
	}
	public Double getOverIntstSum() {
		return overIntstSum;
	}
	public void setOverIntstSum(Double overIntstSum) {
		this.overIntstSum = overIntstSum;
	}
	public Double getCmpdIntstSum() {
		return cmpdIntstSum;
	}
	public void setCmpdIntstSum(Double cmpdIntstSum) {
		this.cmpdIntstSum = cmpdIntstSum;
	}
	public Double getPenaltySum() {
		return penaltySum;
	}
	public void setPenaltySum(Double penaltySum) {
		this.penaltySum = penaltySum;
	}
	public Double getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(Double feeSum) {
		this.feeSum = feeSum;
	}
	public Double getApplySum() {
		return applySum;
	}
	public void setApplySum(Double applySum) {
		this.applySum = applySum;
	}
	public Double getApplyNormIntstSum() {
		return applyNormIntstSum;
	}
	public void setApplyNormIntstSum(Double applyNormIntstSum) {
		this.applyNormIntstSum = applyNormIntstSum;
	}
	public Double getApplyOverIntstSum() {
		return applyOverIntstSum;
	}
	public void setApplyOverIntstSum(Double applyOverIntstSum) {
		this.applyOverIntstSum = applyOverIntstSum;
	}
	public Double getApplyCmpdIntstSum() {
		return applyCmpdIntstSum;
	}
	public void setApplyCmpdIntstSum(Double applyCmpdIntstSum) {
		this.applyCmpdIntstSum = applyCmpdIntstSum;
	}
	public Double getApplyPenaltySum() {
		return applyPenaltySum;
	}
	public void setApplyPenaltySum(Double applyPenaltySum) {
		this.applyPenaltySum = applyPenaltySum;
	}
	public Double getApplyFeeSum() {
		return applyFeeSum;
	}
	public void setApplyFeeSum(Double applyFeeSum) {
		this.applyFeeSum = applyFeeSum;
	}
	public Double getFeeAmt1() {
		return feeAmt1;
	}
	public void setFeeAmt1(Double feeAmt1) {
		this.feeAmt1 = feeAmt1;
	}
	public Double getFeeAmt2() {
		return feeAmt2;
	}
	public void setFeeAmt2(Double feeAmt2) {
		this.feeAmt2 = feeAmt2;
	}
	public Double getFeeAmt3() {
		return feeAmt3;
	}
	public void setFeeAmt3(Double feeAmt3) {
		this.feeAmt3 = feeAmt3;
	}
	public Double getFeeAmt4() {
		return feeAmt4;
	}
	public void setFeeAmt4(Double feeAmt4) {
		this.feeAmt4 = feeAmt4;
	}
	public Double getFeeAmt5() {
		return feeAmt5;
	}
	public void setFeeAmt5(Double feeAmt5) {
		this.feeAmt5 = feeAmt5;
	}
	public Double getFeeAmt6() {
		return feeAmt6;
	}
	public void setFeeAmt6(Double feeAmt6) {
		this.feeAmt6 = feeAmt6;
	}
	public Double getFeeAmt7() {
		return feeAmt7;
	}
	public void setFeeAmt7(Double feeAmt7) {
		this.feeAmt7 = feeAmt7;
	}
	public Double getFeeAmt8() {
		return feeAmt8;
	}
	public void setFeeAmt8(Double feeAmt8) {
		this.feeAmt8 = feeAmt8;
	}
	public Double getFeeAmt9() {
		return feeAmt9;
	}
	public void setFeeAmt9(Double feeAmt9) {
		this.feeAmt9 = feeAmt9;
	}
	public Double getFeeAmt10() {
		return feeAmt10;
	}
	public void setFeeAmt10(Double feeAmt10) {
		this.feeAmt10 = feeAmt10;
	}
	public Double getFeeAmt11() {
		return feeAmt11;
	}
	public void setFeeAmt11(Double feeAmt11) {
		this.feeAmt11 = feeAmt11;
	}
	public Double getFeeAmt12() {
		return feeAmt12;
	}
	public void setFeeAmt12(Double feeAmt12) {
		this.feeAmt12 = feeAmt12;
	}
	public Double getFeeAmt13() {
		return feeAmt13;
	}
	public void setFeeAmt13(Double feeAmt13) {
		this.feeAmt13 = feeAmt13;
	}
	public Double getFeeAmt14() {
		return feeAmt14;
	}
	public void setFeeAmt14(Double feeAmt14) {
		this.feeAmt14 = feeAmt14;
	}
	public Double getFeeAmt15() {
		return feeAmt15;
	}
	public void setFeeAmt15(Double feeAmt15) {
		this.feeAmt15 = feeAmt15;
	}
	public String getFeeAmtName1() {
		return feeAmtName1;
	}
	public void setFeeAmtName1(String feeAmtName1) {
		this.feeAmtName1 = feeAmtName1;
	}
	public String getFeeAmtName2() {
		return feeAmtName2;
	}
	public void setFeeAmtName2(String feeAmtName2) {
		this.feeAmtName2 = feeAmtName2;
	}
	public String getFeeAmtName3() {
		return feeAmtName3;
	}
	public void setFeeAmtName3(String feeAmtName3) {
		this.feeAmtName3 = feeAmtName3;
	}
	public String getFeeAmtName4() {
		return feeAmtName4;
	}
	public void setFeeAmtName4(String feeAmtName4) {
		this.feeAmtName4 = feeAmtName4;
	}
	public String getFeeAmtName5() {
		return feeAmtName5;
	}
	public void setFeeAmtName5(String feeAmtName5) {
		this.feeAmtName5 = feeAmtName5;
	}
	public String getFeeAmtName6() {
		return feeAmtName6;
	}
	public void setFeeAmtName6(String feeAmtName6) {
		this.feeAmtName6 = feeAmtName6;
	}
	public String getFeeAmtName7() {
		return feeAmtName7;
	}
	public void setFeeAmtName7(String feeAmtName7) {
		this.feeAmtName7 = feeAmtName7;
	}
	public String getFeeAmtName8() {
		return feeAmtName8;
	}
	public void setFeeAmtName8(String feeAmtName8) {
		this.feeAmtName8 = feeAmtName8;
	}
	public String getFeeAmtName9() {
		return feeAmtName9;
	}
	public void setFeeAmtName9(String feeAmtName9) {
		this.feeAmtName9 = feeAmtName9;
	}
	public String getFeeAmtName10() {
		return feeAmtName10;
	}
	public void setFeeAmtName10(String feeAmtName10) {
		this.feeAmtName10 = feeAmtName10;
	}
	public String getFeeAmtName11() {
		return feeAmtName11;
	}
	public void setFeeAmtName11(String feeAmtName11) {
		this.feeAmtName11 = feeAmtName11;
	}
	public String getFeeAmtName12() {
		return feeAmtName12;
	}
	public void setFeeAmtName12(String feeAmtName12) {
		this.feeAmtName12 = feeAmtName12;
	}
	public String getFeeAmtName13() {
		return feeAmtName13;
	}
	public void setFeeAmtName13(String feeAmtName13) {
		this.feeAmtName13 = feeAmtName13;
	}
	public String getFeeAmtName14() {
		return feeAmtName14;
	}
	public void setFeeAmtName14(String feeAmtName14) {
		this.feeAmtName14 = feeAmtName14;
	}
	public String getFeeAmtName15() {
		return feeAmtName15;
	}
	public void setFeeAmtName15(String feeAmtName15) {
		this.feeAmtName15 = feeAmtName15;
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
	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLstModDate() {
		return lstModDate;
	}
	public void setLstModDate(String lstModDate) {
		this.lstModDate = lstModDate;
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
	public Double getApplyFeeAmt1() {
		return applyFeeAmt1;
	}
	public void setApplyFeeAmt1(Double applyFeeAmt1) {
		this.applyFeeAmt1 = applyFeeAmt1;
	}
	public Double getApplyFeeAmt2() {
		return applyFeeAmt2;
	}
	public void setApplyFeeAmt2(Double applyFeeAmt2) {
		this.applyFeeAmt2 = applyFeeAmt2;
	}
	public Double getApplyFeeAmt3() {
		return applyFeeAmt3;
	}
	public void setApplyFeeAmt3(Double applyFeeAmt3) {
		this.applyFeeAmt3 = applyFeeAmt3;
	}
	public Double getApplyFeeAmt4() {
		return applyFeeAmt4;
	}
	public void setApplyFeeAmt4(Double applyFeeAmt4) {
		this.applyFeeAmt4 = applyFeeAmt4;
	}
	public Double getApplyFeeAmt5() {
		return applyFeeAmt5;
	}
	public void setApplyFeeAmt5(Double applyFeeAmt5) {
		this.applyFeeAmt5 = applyFeeAmt5;
	}
	public Double getApplyFeeAmt6() {
		return applyFeeAmt6;
	}
	public void setApplyFeeAmt6(Double applyFeeAmt6) {
		this.applyFeeAmt6 = applyFeeAmt6;
	}
	public Double getApplyFeeAmt7() {
		return applyFeeAmt7;
	}
	public void setApplyFeeAmt7(Double applyFeeAmt7) {
		this.applyFeeAmt7 = applyFeeAmt7;
	}
	public Double getApplyFeeAmt8() {
		return applyFeeAmt8;
	}
	public void setApplyFeeAmt8(Double applyFeeAmt8) {
		this.applyFeeAmt8 = applyFeeAmt8;
	}
	public Double getApplyFeeAmt9() {
		return applyFeeAmt9;
	}
	public void setApplyFeeAmt9(Double applyFeeAmt9) {
		this.applyFeeAmt9 = applyFeeAmt9;
	}
	public Double getApplyFeeAmt10() {
		return applyFeeAmt10;
	}
	public void setApplyFeeAmt10(Double applyFeeAmt10) {
		this.applyFeeAmt10 = applyFeeAmt10;
	}
	public Double getApplyFeeAmt11() {
		return applyFeeAmt11;
	}
	public void setApplyFeeAmt11(Double applyFeeAmt11) {
		this.applyFeeAmt11 = applyFeeAmt11;
	}
	public Double getApplyFeeAmt12() {
		return applyFeeAmt12;
	}
	public void setApplyFeeAmt12(Double applyFeeAmt12) {
		this.applyFeeAmt12 = applyFeeAmt12;
	}
	public Double getApplyFeeAmt13() {
		return applyFeeAmt13;
	}
	public void setApplyFeeAmt13(Double applyFeeAmt13) {
		this.applyFeeAmt13 = applyFeeAmt13;
	}
	public Double getApplyFeeAmt14() {
		return applyFeeAmt14;
	}
	public void setApplyFeeAmt14(Double applyFeeAmt14) {
		this.applyFeeAmt14 = applyFeeAmt14;
	}
	public Double getApplyFeeAmt15() {
		return applyFeeAmt15;
	}
	public void setApplyFeeAmt15(Double applyFeeAmt15) {
		this.applyFeeAmt15 = applyFeeAmt15;
	}
	public String getItemNoStr() {
		return itemNoStr;
	}
	public void setItemNoStr(String itemNoStr) {
		this.itemNoStr = itemNoStr;
	}

	public Double getBalAmtSum() {
		return balAmtSum;
	}

	public void setBalAmtSum(Double balAmtSum) {
		this.balAmtSum = balAmtSum;
	}

	public Double getApplyBalAmtSum() {
		return applyBalAmtSum;
	}

	public void setApplyBalAmtSum(Double applyBalAmtSum) {
		this.applyBalAmtSum = applyBalAmtSum;
	}
}