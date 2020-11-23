package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveInfoMain.java
* Description:归档主信息
* @author：yudongwei@mftcc.cn
* @Fri Apr 07 13:45:47 CST 2017
* @version：1.0
**/
public class ArchiveInfoMain extends BaseDomain {
	private String archiveMainNo;//档案编号
	private String archiveStatus;//档案状态（01-归档待确认 02-已归档 03-已封档 04-移交待确认 05 已移交确认）
	private String archivePactStatus;//归档阶段（01-授信阶段 02-单笔阶段）
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String pactNo;//合同号
	private String pactId;//合同号
	private String appId;//申请号
	private String creditAppId;//授信申请id
	private String creditAppNo;//授信展示号
	private String agenciesId;//合作银行编号
	private String agenciesName;//合作银行名称
	private String appName;//项目名称
	private String kindNo;//产品号
	private String kindName;//产品名称
	private String optType;//1-他项凭证  2-资料合同 3-原始凭证
	private String archiveDesc;//归档说明
	private String applyNum;
	private String remark;
	//档案封存
	private String blockNo;//编号
	private String archiveNo;//档案号
	private String caseNum;//箱号
	private String boxNo;
	private String boxName;
	private String boxDate;
	private String boxRemark;
	private String archiveOpNo;//归档人编号
	private String archiveOpName;//归档人名称
	private String archiveBrNo;//归档人部门编号
	private String archiveBrName;//归档人部门名称
	private String archiveDate;//归档日期
	private String cusOpNo;//项目经理编号
	private String cusOpName;//项目经理名称
	private String cusBrNo;//项目经理部门编号
	private String cusBrName;//项目经理部门名称
	private String confimOpNo;//档案确认人编号
	private String confimOpName;//档案确认人名称
	private String confimBrNo;//档案确认人部门编号
	private String confimBrName;//档案确认人部门名称
	private String confimTime;//归档确认时间
	private String updateOpNo;//更新人编号
	private String updateOpName;//更新人名称
	private String updateBrNo;//更新人部门编号
	private String updateBrName;//更新人部门名称
	private String updateDate;//更新日期

	//文档、合同、凭证明细的拼接
	private String docNos;
	private String paperNos;
	private String templateNos;
	private String templateNumStr;
	private String templateTypeStr;
	private String extendPactS;
	private String vouchers;
	private String voucherOriginalNoStr;
	private String voucherOriginalStr;
	private String voucherOtherNoStr;
	private String voucherOtherStr;
	private String voucherHolders;
	private String dealMode;//根据是否为代偿业务来查询不同的项目列表
	private Integer archiveNum;
	private String busType;//选择业务时，是否为纯单笔 1-纯单笔  2-授信项下业务
	private String busiNo;//归档凭证时，授信合同号和委托合同号的反显

	private String collateralIds;
	private String cusArchiveNo;//归档确认时候的档案号

	private String type;//1-资料合同 2-凭证

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getCreditAppNo() {
		return creditAppNo;
	}

	public void setCreditAppNo(String creditAppNo) {
		this.creditAppNo = creditAppNo;
	}

	public String getAgenciesId() {
		return agenciesId;
	}

	public void setAgenciesId(String agenciesId) {
		this.agenciesId = agenciesId;
	}

	public String getAgenciesName() {
		return agenciesName;
	}

	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}

	public String getCusOpNo() {
		return cusOpNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCusOpNo(String cusOpNo) {
		this.cusOpNo = cusOpNo;
	}

	public String getCusOpName() {
		return cusOpName;
	}

	public void setCusOpName(String cusOpName) {
		this.cusOpName = cusOpName;
	}

	public String getCusBrNo() {
		return cusBrNo;
	}

	public void setCusBrNo(String cusBrNo) {
		this.cusBrNo = cusBrNo;
	}

	public String getCusBrName() {
		return cusBrName;
	}

	public void setCusBrName(String cusBrName) {
		this.cusBrName = cusBrName;
	}

	public String getConfimOpNo() {
		return confimOpNo;
	}

	public void setConfimOpNo(String confimOpNo) {
		this.confimOpNo = confimOpNo;
	}

	public String getConfimOpName() {
		return confimOpName;
	}

	public void setConfimOpName(String confimOpName) {
		this.confimOpName = confimOpName;
	}

	public String getConfimBrNo() {
		return confimBrNo;
	}

	public void setConfimBrNo(String confimBrNo) {
		this.confimBrNo = confimBrNo;
	}

	public String getConfimBrName() {
		return confimBrName;
	}

	public void setConfimBrName(String confimBrName) {
		this.confimBrName = confimBrName;
	}

	public String getConfimTime() {
		return confimTime;
	}

	public void setConfimTime(String confimTime) {
		this.confimTime = confimTime;
	}

	public String getCreditAppId() {
		return creditAppId;
	}

	public void setCreditAppId(String creditAppId) {
		this.creditAppId = creditAppId;
	}

	public String getExtendPactS() {
		return extendPactS;
	}

	public void setExtendPactS(String extendPactS) {
		this.extendPactS = extendPactS;
	}

	public String getCusArchiveNo() {
		return cusArchiveNo;
	}

	public void setCusArchiveNo(String cusArchiveNo) {
		this.cusArchiveNo = cusArchiveNo;
	}

	public String getCollateralIds() {
		return collateralIds;
	}

	public void setCollateralIds(String collateralIds) {
		this.collateralIds = collateralIds;
	}

	public String getPaperNos() {
		return paperNos;
	}

	public void setPaperNos(String paperNos) {
		this.paperNos = paperNos;
	}

	public String getVoucherHolders() {
		return voucherHolders;
	}

	public void setVoucherHolders(String voucherHolders) {
		this.voucherHolders = voucherHolders;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getTemplateNumStr() {
		return templateNumStr;
	}

	public void setTemplateNumStr(String templateNumStr) {
		this.templateNumStr = templateNumStr;
	}

	public String getTemplateTypeStr() {
		return templateTypeStr;
	}

	public void setTemplateTypeStr(String templateTypeStr) {
		this.templateTypeStr = templateTypeStr;
	}

	public String getVoucherOriginalNoStr() {
		return voucherOriginalNoStr;
	}

	public void setVoucherOriginalNoStr(String voucherOriginalNoStr) {
		this.voucherOriginalNoStr = voucherOriginalNoStr;
	}

	public String getVoucherOriginalStr() {
		return voucherOriginalStr;
	}

	public void setVoucherOriginalStr(String voucherOriginalStr) {
		this.voucherOriginalStr = voucherOriginalStr;
	}

	public String getVoucherOtherNoStr() {
		return voucherOtherNoStr;
	}

	public void setVoucherOtherNoStr(String voucherOtherNoStr) {
		this.voucherOtherNoStr = voucherOtherNoStr;
	}

	public String getVoucherOtherStr() {
		return voucherOtherStr;
	}

	public void setVoucherOtherStr(String voucherOtherStr) {
		this.voucherOtherStr = voucherOtherStr;
	}

	public Integer getArchiveNum() {
		return archiveNum;
	}

	public void setArchiveNum(Integer archiveNum) {
		this.archiveNum = archiveNum;
	}

	public String getDealMode() {
		return dealMode;
	}

	public void setDealMode(String dealMode) {
		this.dealMode = dealMode;
	}

	public String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}

	public String getDocNos() {
		return docNos;
	}

	public void setDocNos(String docNos) {
		this.docNos = docNos;
	}

	public String getTemplateNos() {
		return templateNos;
	}

	public void setTemplateNos(String templateNos) {
		this.templateNos = templateNos;
	}

	public String getVouchers() {
		return vouchers;
	}

	public void setVouchers(String vouchers) {
		this.vouchers = vouchers;
	}

	private String archiveStatusString;//档案状态文字

	public String getArchiveDesc() {
		return archiveDesc;
	}

	public void setArchiveDesc(String archiveDesc) {
		this.archiveDesc = archiveDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public String getBoxNo() {
		return boxNo;
	}

	public void setBoxNo(String boxNo) {
		this.boxNo = boxNo;
	}

	public String getBoxName() {
		return boxName;
	}

	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}

	public String getBoxDate() {
		return boxDate;
	}

	public void setBoxDate(String boxDate) {
		this.boxDate = boxDate;
	}

	public String getBoxRemark() {
		return boxRemark;
	}

	public void setBoxRemark(String boxRemark) {
		this.boxRemark = boxRemark;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public String getArchiveNo() {
		return archiveNo;
	}

	public void setArchiveNo(String archiveNo) {
		this.archiveNo = archiveNo;
	}

	/**
	 * @return 档案编号
	 */
	public String getArchiveMainNo() {
	 	return archiveMainNo;
	}
	/**
	 * @设置 档案编号
	 * @param archiveMainNo
	 */
	public void setArchiveMainNo(String archiveMainNo) {
	 	this.archiveMainNo = archiveMainNo;
	}
	/**
	 * @return 档案状态（01-归档 02-封档）
	 */
	public String getArchiveStatus() {
	 	return archiveStatus;
	}
	/**
	 * @设置 档案状态（01-归档 02-封档）
	 * @param archiveStatus
	 */
	public void setArchiveStatus(String archiveStatus) {
	 	this.archiveStatus = archiveStatus;
	}
	/**
	 * @return 归档阶段（01-合同签订 02-合同完结）
	 */
	public String getArchivePactStatus() {
		return archivePactStatus;
	}
	/**
	 * @设置 归档阶段（01-合同签订 02-合同完结）
	 * @param archivePactStatus
	 */
	public void setArchivePactStatus(String archivePactStatus) {
		this.archivePactStatus = archivePactStatus;
	}
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
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 产品号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 产品名称
	 */
	public String getKindName() {
	 	return kindName;
	}
	/**
	 * @设置 产品名称
	 * @param kindName
	 */
	public void setKindName(String kindName) {
	 	this.kindName = kindName;
	}
	/**
	 * @return 归档人编号
	 */
	public String getArchiveOpNo() {
	 	return archiveOpNo;
	}
	/**
	 * @设置 归档人编号
	 * @param archiveOpNo
	 */
	public void setArchiveOpNo(String archiveOpNo) {
	 	this.archiveOpNo = archiveOpNo;
	}
	/**
	 * @return 归档人名称
	 */
	public String getArchiveOpName() {
	 	return archiveOpName;
	}
	/**
	 * @设置 归档人名称
	 * @param archiveOpName
	 */
	public void setArchiveOpName(String archiveOpName) {
	 	this.archiveOpName = archiveOpName;
	}
	/**
	 * @return 归档人部门编号
	 */
	public String getArchiveBrNo() {
	 	return archiveBrNo;
	}
	/**
	 * @设置 归档人部门编号
	 * @param archiveBrNo
	 */
	public void setArchiveBrNo(String archiveBrNo) {
	 	this.archiveBrNo = archiveBrNo;
	}
	/**
	 * @return 归档人部门名称
	 */
	public String getArchiveBrName() {
	 	return archiveBrName;
	}
	/**
	 * @设置 归档人部门名称
	 * @param archiveBrName
	 */
	public void setArchiveBrName(String archiveBrName) {
	 	this.archiveBrName = archiveBrName;
	}
	/**
	 * @return 归档日期
	 */
	public String getArchiveDate() {
	 	return archiveDate;
	}
	/**
	 * @设置 归档日期
	 * @param archiveDate
	 */
	public void setArchiveDate(String archiveDate) {
	 	this.archiveDate = archiveDate;
	}
	/**
	 * @return 更新人编号
	 */
	public String getUpdateOpNo() {
	 	return updateOpNo;
	}
	/**
	 * @设置 更新人编号
	 * @param updateOpNo
	 */
	public void setUpdateOpNo(String updateOpNo) {
	 	this.updateOpNo = updateOpNo;
	}
	/**
	 * @return 更新人名称
	 */
	public String getUpdateOpName() {
	 	return updateOpName;
	}
	/**
	 * @设置 更新人名称
	 * @param updateOpName
	 */
	public void setUpdateOpName(String updateOpName) {
	 	this.updateOpName = updateOpName;
	}
	/**
	 * @return 更新人部门编号
	 */
	public String getUpdateBrNo() {
	 	return updateBrNo;
	}
	/**
	 * @设置 更新人部门编号
	 * @param updateBrNo
	 */
	public void setUpdateBrNo(String updateBrNo) {
	 	this.updateBrNo = updateBrNo;
	}
	/**
	 * @return 更新人部门名称
	 */
	public String getUpdateBrName() {
	 	return updateBrName;
	}
	/**
	 * @设置 更新人部门名称
	 * @param updateBrName
	 */
	public void setUpdateBrName(String updateBrName) {
	 	this.updateBrName = updateBrName;
	}
	/**
	 * @return 更新日期
	 */
	public String getUpdateDate() {
	 	return updateDate;
	}
	/**
	 * @设置 更新日期
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) {
	 	this.updateDate = updateDate;
	}
	/**
	 * @return 档案状态文字
	 */
	public String getArchiveStatusString() {
		return archiveStatusString;
	}
	/**
	 * @设置 档案状态文字
	 * @param archiveStatusString
	 */
	public void setArchiveStatusString(String archiveStatusString) {
		this.archiveStatusString = archiveStatusString;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	
}