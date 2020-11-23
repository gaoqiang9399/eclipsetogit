package app.component.report.entity;

import app.base.BaseDomain;

public class MfReportCustomer extends BaseDomain{
	private String cusNo; // 企业客户号
	private String cusName; // 客户名称
	private String usedName; //曾用名
	private String engName; //英文名
	private String cusClass; // 客户类别
	private String legalRepresName; // 法人代表姓名
	private String legalIdType; // 法人代表证件类型
	private String legalIdNum; // 法人代表证件号码
	private String setupDate; // 成立年份
	private String careaProvice;// 行政区划
	private String careaCity; //客户所属市
	private String careaCounty; // 客户所属县
	private String postalCode;//邮政编码
	private String corpNature; //企业性质 
	private String wayClass;// 行业分类
	private String wayMaxClass;//大类
	private String wayMidClass;//中类
	private String wayMinClass;// 小类
	private String empCnt; //从业人数
	private String assetSum;// 资产总额
	private String bussIncome;// 营业收入
	private String gradeModel; // 评分模型
	private String newIndustry;//是否战略新兴产业(0否1是)
	private String autoEnterprise;// 是否自治区百亿工程企业(0否1是)
	private String cusBussType;//客户业务类型
	private String belongWay; //行业分类(隐藏，同小类)
	private String infoOffer;// 信息来源
	private String ifGroup;//0非集团客户1集团客户
	private String groupNo;//所属集团客户编号
	private String groupName;//集团名称
	private String recommenderName; // 推荐人姓名
	private String recommenderIdNum;//推荐人证件号码
	private String cusMngNo;// 客户经理编号
	private String cusMngName;//客户经理姓名
	private String opNo; // 登记人编号
	private String opName; // 登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	/* parm_dic */
	private String keyName;
	private String optCode;
	private String optName;
	
	
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getOptCode() {
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
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
	public String getUsedName() {
		return usedName;
	}
	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getCusClass() {
		return cusClass;
	}
	public void setCusClass(String cusClass) {
		this.cusClass = cusClass;
	}
	public String getLegalRepresName() {
		return legalRepresName;
	}
	public void setLegalRepresName(String legalRepresName) {
		this.legalRepresName = legalRepresName;
	}
	public String getLegalIdType() {
		return legalIdType;
	}
	public void setLegalIdType(String legalIdType) {
		this.legalIdType = legalIdType;
	}
	public String getLegalIdNum() {
		return legalIdNum;
	}
	public void setLegalIdNum(String legalIdNum) {
		this.legalIdNum = legalIdNum;
	}
	public String getSetupDate() {
		return setupDate;
	}
	public void setSetupDate(String setupDate) {
		this.setupDate = setupDate;
	}
	public String getCareaProvice() {
		return careaProvice;
	}
	public void setCareaProvice(String careaProvice) {
		this.careaProvice = careaProvice;
	}
	public String getCareaCity() {
		return careaCity;
	}
	public void setCareaCity(String careaCity) {
		this.careaCity = careaCity;
	}
	public String getCareaCounty() {
		return careaCounty;
	}
	public void setCareaCounty(String careaCounty) {
		this.careaCounty = careaCounty;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCorpNature() {
		return corpNature;
	}
	public void setCorpNature(String corpNature) {
		this.corpNature = corpNature;
	}
	public String getWayClass() {
		return wayClass;
	}
	public void setWayClass(String wayClass) {
		this.wayClass = wayClass;
	}
	public String getWayMaxClass() {
		return wayMaxClass;
	}
	public void setWayMaxClass(String wayMaxClass) {
		this.wayMaxClass = wayMaxClass;
	}
	public String getWayMidClass() {
		return wayMidClass;
	}
	public void setWayMidClass(String wayMidClass) {
		this.wayMidClass = wayMidClass;
	}
	public String getWayMinClass() {
		return wayMinClass;
	}
	public void setWayMinClass(String wayMinClass) {
		this.wayMinClass = wayMinClass;
	}
	public String getEmpCnt() {
		return empCnt;
	}
	public void setEmpCnt(String empCnt) {
		this.empCnt = empCnt;
	}
	public String getAssetSum() {
		return assetSum;
	}
	public void setAssetSum(String assetSum) {
		this.assetSum = assetSum;
	}
	public String getBussIncome() {
		return bussIncome;
	}
	public void setBussIncome(String bussIncome) {
		this.bussIncome = bussIncome;
	}
	public String getGradeModel() {
		return gradeModel;
	}
	public void setGradeModel(String gradeModel) {
		this.gradeModel = gradeModel;
	}
	public String getNewIndustry() {
		return newIndustry;
	}
	public void setNewIndustry(String newIndustry) {
		this.newIndustry = newIndustry;
	}
	public String getAutoEnterprise() {
		return autoEnterprise;
	}
	public void setAutoEnterprise(String autoEnterprise) {
		this.autoEnterprise = autoEnterprise;
	}
	public String getCusBussType() {
		return cusBussType;
	}
	public void setCusBussType(String cusBussType) {
		this.cusBussType = cusBussType;
	}
	public String getBelongWay() {
		return belongWay;
	}
	public void setBelongWay(String belongWay) {
		this.belongWay = belongWay;
	}
	public String getInfoOffer() {
		return infoOffer;
	}
	public void setInfoOffer(String infoOffer) {
		this.infoOffer = infoOffer;
	}
	public String getGroupNo() {
		return groupNo;
	}
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRecommenderName() {
		return recommenderName;
	}
	public void setRecommenderName(String recommenderName) {
		this.recommenderName = recommenderName;
	}
	public String getRecommenderIdNum() {
		return recommenderIdNum;
	}
	public void setRecommenderIdNum(String recommenderIdNum) {
		this.recommenderIdNum = recommenderIdNum;
	}
	public String getCusMngNo() {
		return cusMngNo;
	}
	public void setCusMngNo(String cusMngNo) {
		this.cusMngNo = cusMngNo;
	}
	public String getCusMngName() {
		return cusMngName;
	}
	public void setCusMngName(String cusMngName) {
		this.cusMngName = cusMngName;
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
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	public String getIfGroup() {
		return ifGroup;
	}
	public void setIfGroup(String ifGroup) {
		this.ifGroup = ifGroup;
	}
	
}
