package app.component.tour.entity;
import app.base.BaseDomain;
/**
* Title: MfBusTour.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jun 25 16:52:20 CST 2018
* @version：1.0
**/
public class MfBusTour extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tourId;//唯一标号
	private String fincChildId;//借据号
	private String opNo;//巡视人编号
	private String opName;//巡视人名称
	private String tourTime;//巡视人日期
	private String tourContext;//巡视人备注内容
	private String ext1;//预留字段
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String cusBaseType;//客户类型
	private String cusName;//客户姓名
	private String contactsName;//联系人姓名
	private Double loanBal;//贷款余额
	private String kindName;//产品种类
	private String pactBeginDate;//贷款开始日期
	private String pactEndDate;//贷款结束日期
	private Integer termMonth;//借款期限
	private String loanEconomicDispute;//是否有经济纠纷或涉及诉讼（网上查询）
	private String loanMaritalStatus;//婚姻状况是否稳定
	private String loanPhysicalCondition;//主要劳动力身体是否健康
	private String businessOperation;//企业经营现场是否正常
	private String businessInventories;//企业库存是否有较大变化
	private String actualPerson;//实际控制人是否发生改变
	private String actualMaritalStatus;//实际控制人婚姻状况是否稳定
	private String actualManpower;//劳动力数量是否变化
	private String actualPhysicalCondition;//实际控制人主要劳动力身体是否健康
	private String actualDependants;//抚养人口数量是否变化
	private String coinsuranceGroup;//联保小组成员是否具有继续联保并承担连带责任的意愿
	private String groupMember;//小组成员是否发生重大变故
	private String majorProduct;//主要产品的市场销售状况是否出现较大不利变化
	private String naturalHazard;//是否遭遇自然灾害
	private String limitsType;//抵质押物的形态是否完整
	private String businessCondition;//生产经营情况是否正常
	private String badInformation;//是否有不良的软信息
	private String unitFamily;//工作单位和家庭是否有变化
	private String negativeInformation;//查询是否有不良和负面信息
	private String selfDisciplined;//对客户经理的廉洁自律是否满意
	private String customerServe;//对客户经理的服务是否满意
	private String otherNeed;//其他需要说明的
	private String riskPoint;//检查发现的风险点
	private String rectificationMeasures;//拟采取的整改措施
	private String riskManagerOpinion;//风险经理意见
	private String departmentHeadOpinion;//部门负责人意见
	private String testingDepartment;//检查部门
	private String testMode;//检查方式
	private String financialStatus;//担保情况
	private String borrowerOperation;//借款人经营情况检查结果(重点描述发生变化的情况）
	private String guarantorOperation;//保证人经营情况检查结果（重点描述发生变化的情况）
	private String inspectionResults;//抵质押物检查结果（重点描述发生变化的情况）
	private String sinceFeedback;//借款人对客户经理履职情况反馈（从廉洁自律、工作态度、能力、效率、责任心等方面）
	private String conclusionsExamination;//检查结论（对借款人进行评价，是否发现风险预警信号，如果存在风险单独形成防控预案和建议）
	private String agentOpinion;//经办人意见
	private String departmentHead1Opinion;//风险合规部部门负责人意见
	private Double pactAmt;//金额
	
	

	public String getNegativeInformation() {
		return negativeInformation;
	}
	public void setNegativeInformation(String negativeInformation) {
		this.negativeInformation = negativeInformation;
	}
	/**
	 * @return 唯一标号
	 */
	public String getTourId() {
	 	return tourId;
	}
	/**
	 * @设置 唯一标号
	 * @param tourId
	 */
	public void setTourId(String tourId) {
	 	this.tourId = tourId;
	}
	/**
	 * @return 借据号
	 */
	public String getFincChildId() {
	 	return fincChildId;
	}
	/**
	 * @设置 借据号
	 * @param fincChildId
	 */
	public void setFincChildId(String fincChildId) {
	 	this.fincChildId = fincChildId;
	}
	/**
	 * @return 巡视人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 巡视人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 巡视人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 巡视人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 巡视人日期
	 */
	public String getTourTime() {
	 	return tourTime;
	}
	/**
	 * @设置 巡视人日期
	 * @param tourTime
	 */
	public void setTourTime(String tourTime) {
	 	this.tourTime = tourTime;
	}
	/**
	 * @return 巡视人备注内容
	 */
	public String getTourContext() {
	 	return tourContext;
	}
	/**
	 * @设置 巡视人备注内容
	 * @param tourContext
	 */
	public void setTourContext(String tourContext) {
	 	this.tourContext = tourContext;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 预留字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 客户类型
	 */
	public String getCusBaseType() {
	 	return cusBaseType;
	}
	/**
	 * @设置 客户类型
	 * @param cusBaseType
	 */
	public void setCusBaseType(String cusBaseType) {
	 	this.cusBaseType = cusBaseType;
	}
	/**
	 * @return 客户姓名
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户姓名
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 联系人姓名
	 */
	public String getContactsName() {
	 	return contactsName;
	}
	/**
	 * @设置 联系人姓名
	 * @param contactsName
	 */
	public void setContactsName(String contactsName) {
	 	this.contactsName = contactsName;
	}
	/**
	 * @return 贷款余额
	 */
	public Double getLoanBal() {
		return loanBal;
	}
	public void setLoanBal(Double loanBal) {
		this.loanBal = loanBal;
	}
	public Double getPactAmt() {
		return pactAmt;
	}
	/**
	 * @return 产品种类
	 */
	public String getKindName() {
	 	return kindName;
	}
	public void setPactAmt(Double pactAmt) {
		this.pactAmt = pactAmt;
	}
	/**
	 * @设置 产品种类
	 * @param kindName
	 */
	public void setKindName(String kindName) {
	 	this.kindName = kindName;
	}
	/**
	 * @return 贷款开始日期
	 */
	public String getPactBeginDate() {
	 	return pactBeginDate;
	}
	/**
	 * @设置 贷款开始日期
	 * @param pactBeginDate
	 */
	public void setPactBeginDate(String pactBeginDate) {
	 	this.pactBeginDate = pactBeginDate;
	}
	/**
	 * @return 贷款结束日期
	 */
	public String getPactEndDate() {
	 	return pactEndDate;
	}
	/**
	 * @设置 贷款结束日期
	 * @param pactEndDate
	 */
	public void setPactEndDate(String pactEndDate) {
	 	this.pactEndDate = pactEndDate;
	}
	/**
	 * @return 借款期限
	 */
	public Integer getTermMonth() {
	 	return termMonth;
	}
	/**
	 * @设置 借款期限
	 * @param termMonth
	 */
	public void setTermMonth(Integer termMonth) {
	 	this.termMonth = termMonth;
	}
	/**
	 * @return 是否有经济纠纷或涉及诉讼（网上查询）
	 */
	public String getLoanEconomicDispute() {
	 	return loanEconomicDispute;
	}
	/**
	 * @设置 是否有经济纠纷或涉及诉讼（网上查询）
	 * @param loanEconomicDispute
	 */
	public void setLoanEconomicDispute(String loanEconomicDispute) {
	 	this.loanEconomicDispute = loanEconomicDispute;
	}
	/**
	 * @return 婚姻状况是否稳定
	 */
	public String getLoanMaritalStatus() {
	 	return loanMaritalStatus;
	}
	/**
	 * @设置 婚姻状况是否稳定
	 * @param loanMaritalStatus
	 */
	public void setLoanMaritalStatus(String loanMaritalStatus) {
	 	this.loanMaritalStatus = loanMaritalStatus;
	}
	/**
	 * @return 主要劳动力身体是否健康
	 */
	public String getLoanPhysicalCondition() {
	 	return loanPhysicalCondition;
	}
	/**
	 * @设置 主要劳动力身体是否健康
	 * @param loanPhysicalCondition
	 */
	public void setLoanPhysicalCondition(String loanPhysicalCondition) {
	 	this.loanPhysicalCondition = loanPhysicalCondition;
	}
	/**
	 * @return 企业经营现场是否正常
	 */
	public String getBusinessOperation() {
	 	return businessOperation;
	}
	/**
	 * @设置 企业经营现场是否正常
	 * @param businessOperation
	 */
	public void setBusinessOperation(String businessOperation) {
	 	this.businessOperation = businessOperation;
	}
	/**
	 * @return 企业库存是否有较大变化
	 */
	public String getBusinessInventories() {
	 	return businessInventories;
	}
	/**
	 * @设置 企业库存是否有较大变化
	 * @param businessInventories
	 */
	public void setBusinessInventories(String businessInventories) {
	 	this.businessInventories = businessInventories;
	}
	/**
	 * @return 实际控制人是否发生改变
	 */
	public String getActualPerson() {
	 	return actualPerson;
	}
	/**
	 * @设置 实际控制人是否发生改变
	 * @param actualPerson
	 */
	public void setActualPerson(String actualPerson) {
	 	this.actualPerson = actualPerson;
	}
	/**
	 * @return 实际控制人婚姻状况是否稳定
	 */
	public String getActualMaritalStatus() {
	 	return actualMaritalStatus;
	}
	/**
	 * @设置 实际控制人婚姻状况是否稳定
	 * @param actualMaritalStatus
	 */
	public void setActualMaritalStatus(String actualMaritalStatus) {
	 	this.actualMaritalStatus = actualMaritalStatus;
	}
	/**
	 * @return 劳动力数量是否变化
	 */
	public String getActualManpower() {
	 	return actualManpower;
	}
	/**
	 * @设置 劳动力数量是否变化
	 * @param actualManpower
	 */
	public void setActualManpower(String actualManpower) {
	 	this.actualManpower = actualManpower;
	}
	/**
	 * @return 实际控制人主要劳动力身体是否健康
	 */
	public String getActualPhysicalCondition() {
	 	return actualPhysicalCondition;
	}
	/**
	 * @设置 实际控制人主要劳动力身体是否健康
	 * @param actualPhysicalCondition
	 */
	public void setActualPhysicalCondition(String actualPhysicalCondition) {
	 	this.actualPhysicalCondition = actualPhysicalCondition;
	}
	/**
	 * @return 抚养人口数量是否变化
	 */
	public String getActualDependants() {
	 	return actualDependants;
	}
	/**
	 * @设置 抚养人口数量是否变化
	 * @param actualDependants
	 */
	public void setActualDependants(String actualDependants) {
	 	this.actualDependants = actualDependants;
	}
	/**
	 * @return 联保小组成员是否具有继续联保并承担连带责任的意愿
	 */
	public String getCoinsuranceGroup() {
	 	return coinsuranceGroup;
	}
	/**
	 * @设置 联保小组成员是否具有继续联保并承担连带责任的意愿
	 * @param coinsuranceGroup
	 */
	public void setCoinsuranceGroup(String coinsuranceGroup) {
	 	this.coinsuranceGroup = coinsuranceGroup;
	}
	/**
	 * @return 小组成员是否发生重大变故
	 */
	public String getGroupMember() {
	 	return groupMember;
	}
	/**
	 * @设置 小组成员是否发生重大变故
	 * @param groupMember
	 */
	public void setGroupMember(String groupMember) {
	 	this.groupMember = groupMember;
	}
	/**
	 * @return 主要产品的市场销售状况是否出现较大不利变化
	 */
	public String getMajorProduct() {
	 	return majorProduct;
	}
	/**
	 * @设置 主要产品的市场销售状况是否出现较大不利变化
	 * @param majorProduct
	 */
	public void setMajorProduct(String majorProduct) {
	 	this.majorProduct = majorProduct;
	}
	/**
	 * @return 是否遭遇自然灾害
	 */
	public String getNaturalHazard() {
	 	return naturalHazard;
	}
	/**
	 * @设置 是否遭遇自然灾害
	 * @param naturalHazard
	 */
	public void setNaturalHazard(String naturalHazard) {
	 	this.naturalHazard = naturalHazard;
	}
	/**
	 * @return 抵质押物的形态是否完整
	 */
	public String getLimitsType() {
	 	return limitsType;
	}
	/**
	 * @设置 抵质押物的形态是否完整
	 * @param limitsType
	 */
	public void setLimitsType(String limitsType) {
	 	this.limitsType = limitsType;
	}
	/**
	 * @return 生产经营情况是否正常
	 */
	public String getBusinessCondition() {
	 	return businessCondition;
	}
	/**
	 * @设置 生产经营情况是否正常
	 * @param businessCondition
	 */
	public void setBusinessCondition(String businessCondition) {
	 	this.businessCondition = businessCondition;
	}
	/**
	 * @return 是否有不良的软信息
	 */
	public String getBadInformation() {
	 	return badInformation;
	}
	/**
	 * @设置 是否有不良的软信息
	 * @param badInformation
	 */
	public void setBadInformation(String badInformation) {
	 	this.badInformation = badInformation;
	}
	/**
	 * @return 工作单位和家庭是否有变化
	 */
	public String getUnitFamily() {
	 	return unitFamily;
	}
	/**
	 * @设置 工作单位和家庭是否有变化
	 * @param unitFamily
	 */
	public void setUnitFamily(String unitFamily) {
	 	this.unitFamily = unitFamily;
	}
	
	/**
	 * @return 对客户经理的廉洁自律是否满意
	 */
	public String getSelfDisciplined() {
	 	return selfDisciplined;
	}
	/**
	 * @设置 对客户经理的廉洁自律是否满意
	 * @param selfDisciplined
	 */
	public void setSelfDisciplined(String selfDisciplined) {
	 	this.selfDisciplined = selfDisciplined;
	}
	/**
	 * @return 对客户经理的服务是否满意
	 */
	public String getCustomerServe() {
	 	return customerServe;
	}
	/**
	 * @设置 对客户经理的服务是否满意
	 * @param customerServe
	 */
	public void setCustomerServe(String customerServe) {
	 	this.customerServe = customerServe;
	}
	/**
	 * @return 其他需要说明的
	 */
	public String getOtherNeed() {
	 	return otherNeed;
	}
	/**
	 * @设置 其他需要说明的
	 * @param otherNeed
	 */
	public void setOtherNeed(String otherNeed) {
	 	this.otherNeed = otherNeed;
	}
	/**
	 * @return 检查发现的风险点
	 */
	public String getRiskPoint() {
	 	return riskPoint;
	}
	/**
	 * @设置 检查发现的风险点
	 * @param riskPoint
	 */
	public void setRiskPoint(String riskPoint) {
	 	this.riskPoint = riskPoint;
	}
	/**
	 * @return 拟采取的整改措施
	 */
	public String getRectificationMeasures() {
	 	return rectificationMeasures;
	}
	/**
	 * @设置 拟采取的整改措施
	 * @param rectificationMeasures
	 */
	public void setRectificationMeasures(String rectificationMeasures) {
	 	this.rectificationMeasures = rectificationMeasures;
	}
	/**
	 * @return 风险经理意见
	 */
	public String getRiskManagerOpinion() {
	 	return riskManagerOpinion;
	}
	/**
	 * @设置 风险经理意见
	 * @param riskManagerOpinion
	 */
	public void setRiskManagerOpinion(String riskManagerOpinion) {
	 	this.riskManagerOpinion = riskManagerOpinion;
	}
	/**
	 * @return 部门负责人意见
	 */
	public String getDepartmentHeadOpinion() {
	 	return departmentHeadOpinion;
	}
	/**
	 * @设置 部门负责人意见
	 * @param departmentHeadOpinion
	 */
	public void setDepartmentHeadOpinion(String departmentHeadOpinion) {
	 	this.departmentHeadOpinion = departmentHeadOpinion;
	}
	/**
	 * @return 检查部门
	 */
	public String getTestingDepartment() {
	 	return testingDepartment;
	}
	/**
	 * @设置 检查部门
	 * @param testingDepartment
	 */
	public void setTestingDepartment(String testingDepartment) {
	 	this.testingDepartment = testingDepartment;
	}
	/**
	 * @return 检查方式
	 */
	public String getTestMode() {
	 	return testMode;
	}
	/**
	 * @设置 检查方式
	 * @param testMode
	 */
	public void setTestMode(String testMode) {
	 	this.testMode = testMode;
	}
	/**
	 * @return 担保情况
	 */
	public String getFinancialStatus() {
	 	return financialStatus;
	}
	/**
	 * @设置 担保情况
	 * @param financialStatus
	 */
	public void setFinancialStatus(String financialStatus) {
	 	this.financialStatus = financialStatus;
	}
	/**
	 * @return 借款人经营情况检查结果(重点描述发生变化的情况）
	 */
	public String getBorrowerOperation() {
	 	return borrowerOperation;
	}
	/**
	 * @设置 借款人经营情况检查结果(重点描述发生变化的情况）
	 * @param borrowerOperation
	 */
	public void setBorrowerOperation(String borrowerOperation) {
	 	this.borrowerOperation = borrowerOperation;
	}
	/**
	 * @return 保证人经营情况检查结果（重点描述发生变化的情况）
	 */
	public String getGuarantorOperation() {
	 	return guarantorOperation;
	}
	/**
	 * @设置 保证人经营情况检查结果（重点描述发生变化的情况）
	 * @param guarantorOperation
	 */
	public void setGuarantorOperation(String guarantorOperation) {
	 	this.guarantorOperation = guarantorOperation;
	}
	/**
	 * @return 抵质押物检查结果（重点描述发生变化的情况）
	 */
	public String getInspectionResults() {
	 	return inspectionResults;
	}
	/**
	 * @设置 抵质押物检查结果（重点描述发生变化的情况）
	 * @param inspectionResults
	 */
	public void setInspectionResults(String inspectionResults) {
	 	this.inspectionResults = inspectionResults;
	}
	/**
	 * @return 借款人对客户经理履职情况反馈（从廉洁自律、工作态度、能力、效率、责任心等方面）
	 */
	public String getSinceFeedback() {
	 	return sinceFeedback;
	}
	/**
	 * @设置 借款人对客户经理履职情况反馈（从廉洁自律、工作态度、能力、效率、责任心等方面）
	 * @param sinceFeedback
	 */
	public void setSinceFeedback(String sinceFeedback) {
	 	this.sinceFeedback = sinceFeedback;
	}
	/**
	 * @return 检查结论（对借款人进行评价，是否发现风险预警信号，如果存在风险单独形成防控预案和建议）
	 */
	public String getConclusionsExamination() {
	 	return conclusionsExamination;
	}
	/**
	 * @设置 检查结论（对借款人进行评价，是否发现风险预警信号，如果存在风险单独形成防控预案和建议）
	 * @param conclusionsExamination
	 */
	public void setConclusionsExamination(String conclusionsExamination) {
	 	this.conclusionsExamination = conclusionsExamination;
	}
	/**
	 * @return 经办人意见
	 */
	public String getAgentOpinion() {
	 	return agentOpinion;
	}
	/**
	 * @设置 经办人意见
	 * @param agentOpinion
	 */
	public void setAgentOpinion(String agentOpinion) {
	 	this.agentOpinion = agentOpinion;
	}
	/**
	 * @return 风险合规部部门负责人意见
	 */
	public String getDepartmentHead1Opinion() {
	 	return departmentHead1Opinion;
	}
	/**
	 * @设置 风险合规部部门负责人意见
	 * @param departmentHead1Opinion
	 */
	public void setDepartmentHead1Opinion(String departmentHead1Opinion) {
	 	this.departmentHead1Opinion = departmentHead1Opinion;
	}
	
	
}