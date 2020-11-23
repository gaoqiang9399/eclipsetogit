package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfAppCusIntegrity.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Nov 13 10:51:05 CST 2017
* @version：1.0
**/
public class MfAppCusIntegrity extends BaseDomain {
	private String cusNo;//客户号
	private String cusType;//客户类型
	private String customerInfoFlag;//进件信息完善标识，1已完善 0未完善
	private String baseInfoFlag;//基本信息完善标识，1已完善 0未完善
	private String bankInfoFlag;//主账户银行卡信息完善标识，1已完善 0未完善
	private String putoutBankInfoFlag;//放款银行卡信息完善标识，1已完善 0未完善
	private String repayBankInfoFlag;//还款银行卡信息完善标识，1已完善 0未完善
	private String jobInfoFlag;//工作信息完善标识，1已完善 0未完善
	private String familyInfoFlag;//联系人信息完善标识，1已完善 0未完善
	private String assetInfoFlag;//资产信息完善标识，1已完善 0未完善
	private String carInfoFlag;//车辆信息完善标识，1已完善 0未完善
	private String houseInfoFlag;//房屋信息完善标识，1已完善 0未完善
	private String educationaInfoFlag;//学历信息完善标识，1已完善 0未完善
	private String personDebtInfoFlag;//负债信息完善标识，1已完善 0未完善
	private String personIncExpeInfoFlag;//收支信息完善标识，1已完善 0未完善
	private String guaranteeOuterInfoFlag;//对外担保信息完善标识，1已完善 0未完善
	private String farmerIncExpeInfoFlag;//农户收支信息完善标识，1已完善 0未完善
	private String farmerEconoInfoFlag;//农户经济状况信息完善标识，1已完善 0未完善
	private String personCorpInfoFlag;//个人名下企业信息完善标识，1已完善 0未完善
	private String flowAssetInfoFlag;//流动资产信息完善标识，1已完善 0未完善
	private String applyInfoFlag;//借款申请信息完善标识，1已完善 0未完善
	private String assureInfo1Flag;//担保信息完善标识，1已完善 0未完善
	private String assureInfo2Flag;//担保人完善标识，1已完善 0未完善
	private String idcardInfoFlag;//身份证资料上传标识，1已上传 0未上传
	private String addressBookInfoFlag;//通讯录信息完善标识，1已完善 0未完善
	private String operatorInfoFlag;//运营商认证标识，1已认证 0未认证
	private String sesameInfoFlag;//芝麻信用认证标识，1已认证 0未认证
	private String fundInfoFlag;//公积金认证标识，1已认证 0未认证
	private String phoneInfoFlag;//手机认证标识，1已认证 0未认证
	private String infoIntegrity;//信息完整度
	private String integrityItem;//信息完整度统计项 1 基本信息 2银行卡 3放款银行卡 4还款银行卡 5职业 6联系人 7资产 8车辆 9房屋 10学历 11身份证 12通讯录 13运营商 14芝麻信用 15 公积金认证 16 手机认证
	private String channelType;//客户渠道来源
	private String redTime;//注册时间
	private String ext1;//预留字段
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//

	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户类型
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 客户类型
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	
	public String getCustomerInfoFlag() {
		return customerInfoFlag;
	}
	
	public void setCustomerInfoFlag(String customerInfoFlag) {
		this.customerInfoFlag = customerInfoFlag;
	}
	/**
	 * @return 基本信息完善标识，1已完善 0未完善
	 */
	public String getBaseInfoFlag() {
	 	return baseInfoFlag;
	}
	/**
	 * @设置 基本信息完善标识，1已完善 0未完善
	 * @param baseInfoFlag
	 */
	public void setBaseInfoFlag(String baseInfoFlag) {
	 	this.baseInfoFlag = baseInfoFlag;
	}
	/**
	 * @return 主账户银行卡信息完善标识，1已完善 0未完善
	 */
	public String getBankInfoFlag() {
	 	return bankInfoFlag;
	}
	/**
	 * @设置 主账户银行卡信息完善标识，1已完善 0未完善
	 * @param bankInfoFlag
	 */
	public void setBankInfoFlag(String bankInfoFlag) {
	 	this.bankInfoFlag = bankInfoFlag;
	}
	/**
	 * @return 放款银行卡信息完善标识，1已完善 0未完善
	 */
	public String getPutoutBankInfoFlag() {
	 	return putoutBankInfoFlag;
	}
	/**
	 * @设置 放款银行卡信息完善标识，1已完善 0未完善
	 * @param putoutBankInfoFlag
	 */
	public void setPutoutBankInfoFlag(String putoutBankInfoFlag) {
	 	this.putoutBankInfoFlag = putoutBankInfoFlag;
	}
	/**
	 * @return 还款银行卡信息完善标识，1已完善 0未完善
	 */
	public String getRepayBankInfoFlag() {
	 	return repayBankInfoFlag;
	}
	/**
	 * @设置 还款银行卡信息完善标识，1已完善 0未完善
	 * @param repayBankInfoFlag
	 */
	public void setRepayBankInfoFlag(String repayBankInfoFlag) {
	 	this.repayBankInfoFlag = repayBankInfoFlag;
	}
	/**
	 * @return 工作信息完善标识，1已完善 0未完善
	 */
	public String getJobInfoFlag() {
	 	return jobInfoFlag;
	}
	/**
	 * @设置 工作信息完善标识，1已完善 0未完善
	 * @param jobInfoFlag
	 */
	public void setJobInfoFlag(String jobInfoFlag) {
	 	this.jobInfoFlag = jobInfoFlag;
	}
	/**
	 * @return 联系人信息完善标识，1已完善 0未完善
	 */
	public String getFamilyInfoFlag() {
	 	return familyInfoFlag;
	}
	/**
	 * @设置 联系人信息完善标识，1已完善 0未完善
	 * @param familyInfoFlag
	 */
	public void setFamilyInfoFlag(String familyInfoFlag) {
	 	this.familyInfoFlag = familyInfoFlag;
	}
	/**
	 * @return 资产信息完善标识，1已完善 0未完善
	 */
	public String getAssetInfoFlag() {
	 	return assetInfoFlag;
	}
	/**
	 * @设置 资产信息完善标识，1已完善 0未完善
	 * @param assetInfoFlag
	 */
	public void setAssetInfoFlag(String assetInfoFlag) {
	 	this.assetInfoFlag = assetInfoFlag;
	}
	/**
	 * @return 车辆信息完善标识，1已完善 0未完善
	 */
	public String getCarInfoFlag() {
		return carInfoFlag;
	}
	/**
	 * @设置 车辆信息完善标识，1已完善 0未完善
	 * @param assetInfoFlag
	 */
	public void setCarInfoFlag(String carInfoFlag) {
		this.carInfoFlag = carInfoFlag;
	}
	/**
	 * @return 房屋信息完善标识，1已完善 0未完善
	 */
	public String getHouseInfoFlag() {
		return houseInfoFlag;
	}
	/**
	 * @设置 房屋信息完善标识，1已完善 0未完善
	 * @param assetInfoFlag
	 */
	public void setHouseInfoFlag(String houseInfoFlag) {
		this.houseInfoFlag = houseInfoFlag;
	}
	/**
	 * @return 学历信息完善标识，1已完善 0未完善
	 */
	public String getEducationaInfoFlag() {
	 	return educationaInfoFlag;
	}
	/**
	 * @设置 学历信息完善标识，1已完善 0未完善
	 * @param educationaInfoFlag
	 */
	public void setEducationaInfoFlag(String educationaInfoFlag) {
	 	this.educationaInfoFlag = educationaInfoFlag;
	}
	
	public String getPersonDebtInfoFlag() {
		return personDebtInfoFlag;
	}
	
	public void setPersonDebtInfoFlag(String personDebtInfoFlag) {
		this.personDebtInfoFlag = personDebtInfoFlag;
	}
	
	public String getPersonIncExpeInfoFlag() {
		return personIncExpeInfoFlag;
	}
	
	public void setPersonIncExpeInfoFlag(String personIncExpeInfoFlag) {
		this.personIncExpeInfoFlag = personIncExpeInfoFlag;
	}
	
	public String getGuaranteeOuterInfoFlag() {
		return guaranteeOuterInfoFlag;
	}
	
	public void setGuaranteeOuterInfoFlag(String guaranteeOuterInfoFlag) {
		this.guaranteeOuterInfoFlag = guaranteeOuterInfoFlag;
	}
	
	public String getFarmerIncExpeInfoFlag() {
		return farmerIncExpeInfoFlag;
	}
	
	public void setFarmerIncExpeInfoFlag(String farmerIncExpeInfoFlag) {
		this.farmerIncExpeInfoFlag = farmerIncExpeInfoFlag;
	}
	
	public String getFarmerEconoInfoFlag() {
		return farmerEconoInfoFlag;
	}
	
	public void setFarmerEconoInfoFlag(String farmerEconoInfoFlag) {
		this.farmerEconoInfoFlag = farmerEconoInfoFlag;
	}
	
	public String getPersonCorpInfoFlag() {
		return personCorpInfoFlag;
	}
	
	public void setPersonCorpInfoFlag(String personCorpInfoFlag) {
		this.personCorpInfoFlag = personCorpInfoFlag;
	}
	
	public String getFlowAssetInfoFlag() {
		return flowAssetInfoFlag;
	}
	
	public void setFlowAssetInfoFlag(String flowAssetInfoFlag) {
		this.flowAssetInfoFlag = flowAssetInfoFlag;
	}
	
	public String getApplyInfoFlag() {
		return applyInfoFlag;
	}
	
	public void setApplyInfoFlag(String applyInfoFlag) {
		this.applyInfoFlag = applyInfoFlag;
	}
	
	public String getAssureInfo1Flag() {
		return assureInfo1Flag;
	}
	
	public void setAssureInfo1Flag(String assureInfo1Flag) {
		this.assureInfo1Flag = assureInfo1Flag;
	}
	
	public String getAssureInfo2Flag() {
		return assureInfo2Flag;
	}
	
	public void setAssureInfo2Flag(String assureInfo2Flag) {
		this.assureInfo2Flag = assureInfo2Flag;
	}
	/**
	 * @return 身份证资料上传标识，1已上传 0未上传
	 */
	public String getIdcardInfoFlag() {
	 	return idcardInfoFlag;
	}
	/**
	 * @设置 身份证资料上传标识，1已上传 0未上传
	 * @param idcardInfoFlag
	 */
	public void setIdcardInfoFlag(String idcardInfoFlag) {
	 	this.idcardInfoFlag = idcardInfoFlag;
	}
	/**
	 * @return 通讯录信息完善标识，1已完善 0未完善
	 */
	public String getAddressBookInfoFlag() {
	 	return addressBookInfoFlag;
	}
	/**
	 * @设置 通讯录信息完善标识，1已完善 0未完善
	 * @param addressBookInfoFlag
	 */
	public void setAddressBookInfoFlag(String addressBookInfoFlag) {
	 	this.addressBookInfoFlag = addressBookInfoFlag;
	}
	/**
	 * @return 运营商认证标识，1已认证 0未认证
	 */
	public String getOperatorInfoFlag() {
	 	return operatorInfoFlag;
	}
	/**
	 * @设置 运营商认证标识，1已认证 0未认证
	 * @param operatorInfoFlag
	 */
	public void setOperatorInfoFlag(String operatorInfoFlag) {
	 	this.operatorInfoFlag = operatorInfoFlag;
	}
	/**
	 * @return 芝麻信用认证标识，1已认证 0未认证
	 */
	public String getSesameInfoFlag() {
	 	return sesameInfoFlag;
	}
	/**
	 * @设置 芝麻信用认证标识，1已认证 0未认证
	 * @param sesameInfoFlag
	 */
	public void setSesameInfoFlag(String sesameInfoFlag) {
	 	this.sesameInfoFlag = sesameInfoFlag;
	}
	/**
	 * @return 公积金认证标识，1已认证 0未认证
	 */
	public String getFundInfoFlag() {
	 	return fundInfoFlag;
	}
	/**
	 * @设置 公积金认证标识，1已认证 0未认证
	 * @param fundInfoFlag
	 */
	public void setFundInfoFlag(String fundInfoFlag) {
	 	this.fundInfoFlag = fundInfoFlag;
	}
	/**
	 * @return 手机认证标识，1已认证 0未认证
	 */
	public String getPhoneInfoFlag() {
	 	return phoneInfoFlag;
	}
	/**
	 * @设置 手机认证标识，1已认证 0未认证
	 * @param phoneInfoFlag
	 */
	public void setPhoneInfoFlag(String phoneInfoFlag) {
	 	this.phoneInfoFlag = phoneInfoFlag;
	}
	/**
	 * @return 信息完整度
	 */
	public String getInfoIntegrity() {
	 	return infoIntegrity;
	}
	/**
	 * @设置 信息完整度
	 * @param infoIntegrity
	 */
	public void setInfoIntegrity(String infoIntegrity) {
	 	this.infoIntegrity = infoIntegrity;
	}
	/**
	 * @return 信息完整度统计项 1 基本信息 2银行卡 3放款银行卡 4还款银行卡 5职业 6联系人 7资产 8学历 9身份证 10通讯录 11运营商 12芝麻信用 13 公积金认证 14 手机认证
	 */
	public String getIntegrityItem() {
	 	return integrityItem;
	}
	/**
	 * @设置 信息完整度统计项 1 基本信息 2银行卡 3放款银行卡 4还款银行卡 5职业 6联系人 7资产 8学历 9身份证 10通讯录 11运营商 12芝麻信用 13 公积金认证 14 手机认证
	 * @param integrityItem
	 */
	public void setIntegrityItem(String integrityItem) {
	 	this.integrityItem = integrityItem;
	}
	/**
	 * @return 客户渠道来源
	 */
	public String getChannelType() {
	 	return channelType;
	}
	/**
	 * @设置 客户渠道来源
	 * @param channelType
	 */
	public void setChannelType(String channelType) {
	 	this.channelType = channelType;
	}
	public String getRedTime() {
		return redTime;
	}
	public void setRedTime(String redTime) {
		this.redTime = redTime;
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
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	
	
}