package app.component.report.entity;


public class MfLoanBean{

	
	//所属部门
	private String depName;
	//产品名称
	private String kindName;
	//客户类型
	private String cifTypeName;
	//客户编号
	private String cusno;
	//客户名称
	private String cusName;
	//客户证件号码
	private String idNumber;
	//学历
	private String eduName;
	//客户所属行业
	private String wayName;
	//合同号
	private String pactno;
	//借据号
	private String fincid;
	//申请金额
	private String appamt;
	//贷款金额
	private String putoutAmt;
	//贷款余额
	private String loanBal;
	//贷款期限
	private String dkqx;
	//利率类型
	private String rateTypeName;
	//贷款利率
	private String appRate;
	//担保方式
	private String vouName;
	//还款方式
	private String returnMethod;
	//贷款发放日or贷款起息日
	private String inistBegDte;
	//贷款到期日
	private String inistEndDte;
	//贷款用途
	private String loanUse;
	//担保人
	private String danbaoren;
	//保证金金额
	private String assureAmt;
	//抵押物类型
	private String vouTypeName;
	//抵质押物评估价值
	private String pledgeValue;
	//抵质押物到期日
	private String pledgeEndDate;
	//五级形态
	private String fiveCls;
	//已还期数
	private String repayNumber;
	//是否欠息
	private String maxOverDays;
	// 员工编号
	private String userNo;
	//业务主办
	private String userName;
	private String count;
	//客户经理
	private String cusMngName;
	//客户经理编号
	private String cusMngNo;
	//放款日期
	private String putoutDate;
	//显示借据号
	private  String fincShowId;
	//本金罚息利率(逾期利率)
	private  String overRate;
	//利息罚息利率(复利利率)
	private  String cmpdRate;
	private  String addr_detail;//联系地址
	private  String phone_num;//联系电话
	private  String pay_method;//支付方式
	private  String intst_begin_date;//贷款开始日
	private  String finc_use;//贷款投向
	private  String ext6;//贷款形式
	private  String koutouyueding;//口头约定还款计划
	private  String spr;//审批人
	private  String approve_part_name;//出账审批人
	private  String khjlbr;//管户机构
	private  String zbrbr;//入账机构
	private  String remark;//备注
	
	
	
	
	
	
	
	
	
	public String getAddr_detail() {
		return addr_detail;
	}
	public void setAddr_detail(String addr_detail) {
		this.addr_detail = addr_detail;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getIntst_begin_date() {
		return intst_begin_date;
	}
	public void setIntst_begin_date(String intst_begin_date) {
		this.intst_begin_date = intst_begin_date;
	}
	public String getFinc_use() {
		return finc_use;
	}
	public void setFinc_use(String finc_use) {
		this.finc_use = finc_use;
	}
	public String getExt6() {
		return ext6;
	}
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	public String getKoutouyueding() {
		return koutouyueding;
	}
	public void setKoutouyueding(String koutouyueding) {
		this.koutouyueding = koutouyueding;
	}
	public String getSpr() {
		return spr;
	}
	public void setSpr(String spr) {
		this.spr = spr;
	}
	public String getApprove_part_name() {
		return approve_part_name;
	}
	public void setApprove_part_name(String approve_part_name) {
		this.approve_part_name = approve_part_name;
	}
	public String getKhjlbr() {
		return khjlbr;
	}
	public void setKhjlbr(String khjlbr) {
		this.khjlbr = khjlbr;
	}
	public String getZbrbr() {
		return zbrbr;
	}
	public void setZbrbr(String zbrbr) {
		this.zbrbr = zbrbr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getCifTypeName() {
		return cifTypeName;
	}
	public void setCifTypeName(String cifTypeName) {
		this.cifTypeName = cifTypeName;
	}
	public String getCusno() {
		return cusno;
	}
	public void setCusno(String cusno) {
		this.cusno = cusno;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getEduName() {
		return eduName;
	}
	public void setEduName(String eduName) {
		this.eduName = eduName;
	}
	public String getWayName() {
		return wayName;
	}
	public void setWayName(String wayName) {
		this.wayName = wayName;
	}
	public String getPactno() {
		return pactno;
	}
	public void setPactno(String pactno) {
		this.pactno = pactno;
	}
	public String getFincid() {
		return fincid;
	}
	public void setFincid(String fincid) {
		this.fincid = fincid;
	}
	public String getAppamt() {
		return appamt;
	}
	public void setAppamt(String appamt) {
		this.appamt = appamt;
	}
	public String getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(String putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	public String getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(String loanBal) {
		this.loanBal = loanBal;
	}
	public String getDkqx() {
		return dkqx;
	}
	public void setDkqx(String dkqx) {
		this.dkqx = dkqx;
	}
	public String getRateTypeName() {
		return rateTypeName;
	}
	public void setRateTypeName(String rateTypeName) {
		this.rateTypeName = rateTypeName;
	}
	public String getAppRate() {
		return appRate;
	}
	public void setAppRate(String appRate) {
		this.appRate = appRate;
	}
	public String getVouName() {
		return vouName;
	}
	public void setVouName(String vouName) {
		this.vouName = vouName;
	}
	public String getInistBegDte() {
		return inistBegDte;
	}
	public void setInistBegDte(String inistBegDte) {
		this.inistBegDte = inistBegDte;
	}
	public String getInistEndDte() {
		return inistEndDte;
	}
	public void setInistEndDte(String inistEndDte) {
		this.inistEndDte = inistEndDte;
	}
	public String getLoanUse() {
		return loanUse;
	}
	public void setLoanUse(String loanUse) {
		this.loanUse = loanUse;
	}
	public String getDanbaoren() {
		return danbaoren;
	}
	public void setDanbaoren(String danbaoren) {
		this.danbaoren = danbaoren;
	}
	public String getAssureAmt() {
		return assureAmt;
	}
	public void setAssureAmt(String assureAmt) {
		this.assureAmt = assureAmt;
	}
	public String getVouTypeName() {
		return vouTypeName;
	}
	public void setVouTypeName(String vouTypeName) {
		this.vouTypeName = vouTypeName;
	}
	public String getPledgeValue() {
		return pledgeValue;
	}
	public void setPledgeValue(String pledgeValue) {
		this.pledgeValue = pledgeValue;
	}
	public String getPledgeEndDate() {
		return pledgeEndDate;
	}
	public void setPledgeEndDate(String pledgeEndDate) {
		this.pledgeEndDate = pledgeEndDate;
	}
	public String getFiveCls() {
		return fiveCls;
	}
	public void setFiveCls(String fiveCls) {
		this.fiveCls = fiveCls;
	}
	public String getRepayNumber() {
		return repayNumber;
	}
	public void setRepayNumber(String repayNumber) {
		this.repayNumber = repayNumber;
	}
	public String getMaxOverDays() {
		return maxOverDays;
	}
	public void setMaxOverDays(String maxOverDays) {
		this.maxOverDays = maxOverDays;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReturnMethod() {
		return returnMethod;
	}
	public void setReturnMethod(String returnMethod) {
		this.returnMethod = returnMethod;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getCusMngName() {
		return cusMngName;
	}
	public void setCusMngName(String cusMngName) {
		this.cusMngName = cusMngName;
	}
	public String getCusMngNo() {
		return cusMngNo;
	}
	public void setCusMngNo(String cusMngNo) {
		this.cusMngNo = cusMngNo;
	}
	public String getPutoutDate() {
		return putoutDate;
	}
	public void setPutoutDate(String putoutDate) {
		this.putoutDate = putoutDate;
	}
	public String getFincShowId() {
		return fincShowId;
	}
	public void setFincShowId(String fincShowId) {
		this.fincShowId = fincShowId;
	}
	public String getOverRate() {
		return overRate;
	}
	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}
	public String getCmpdRate() {
		return cmpdRate;
	}
	public void setCmpdRate(String cmpdRate) {
		this.cmpdRate = cmpdRate;
	}
	
}
