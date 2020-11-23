package app.component.collateral.entity;
import app.base.BaseDomain;
/**
 * Title: InsInfo.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Wed Mar 15 13:19:29 CST 2017
 * @version：1.0
 **/
public class InsInfo extends BaseDomain {
	private String insId;// 保险ID
	private String collateralId;// 押品编号
	private String insNo;// 保险证编号
	private Double insAmount;// 保险金额
	private String insCrop;// 保险公司名称
	private String insStart;// 投保日期
	private String insEnd;// 保险到日期
	private String regCusId;// 登记人编号
	private String regCusName;// 登记人姓名
	private String regOrgId;// 登记人机构
	private String regOrgName;// 登记人机构名称
	private String regDate;// 登记日期
	private String updateCusId;// 更新人编号
	private String updateCusName;// 更新人姓名
	private String updateOrgId;// 更新人机构
	private String updateOrgName;// 更新人机构名称
	private String updateDate;// 更新日期
	// private String collateralType;
	private String classId;
	// 保险类型
	private String insType;
	// 保险第一受益人编号
	private String insBeneficiaryNo;
	// 保险第一受益人
	private String insBeneficiaryName;
	// 备注
	private String remarks;

	//add by lance 20180815 增加保险信息内容

	private String insMode;	//保险方式
	private String insPolicyHolderName;//投保人
	private String insStartDate;//保险投保开始日
	private Double insFee;//保险费
	private String insFeeUndertaker;//保险费承担人


	/**
	 * @return 保险ID
	 */
	public String getInsId() {
		return insId;
	}
	/**
	 * @设置 保险ID
	 * @param insId
	 */
	public void setInsId(String insId) {
		this.insId = insId;
	}
	/**
	 * @return 押品编号
	 */
	public String getCollateralId() {
		return collateralId;
	}
	/**
	 * @设置 押品编号
	 * @param collateralId
	 */
	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}
	/**
	 * @return 保险证编号
	 */
	public String getInsNo() {
		return insNo;
	}
	/**
	 * @设置 保险证编号
	 * @param insNo
	 */
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	/**
	 * @return 保险金额
	 */
	public Double getInsAmount() {
		return insAmount;
	}
	/**
	 * @设置 保险金额
	 * @param insAmount
	 */
	public void setInsAmount(Double insAmount) {
		this.insAmount = insAmount;
	}
	/**
	 * @return 保险公司名称
	 */
	public String getInsCrop() {
		return insCrop;
	}
	/**
	 * @设置 保险公司名称
	 * @param insCrop
	 */
	public void setInsCrop(String insCrop) {
		this.insCrop = insCrop;
	}
	public String getInsStart() {
		return insStart;
	}
	public void setInsStart(String insStart) {
		this.insStart = insStart;
	}
	/**
	 * @return 保险到日期
	 */
	public String getInsEnd() {
		return insEnd;
	}
	/**
	 * @设置 保险到日期
	 * @param insEnd
	 */
	public void setInsEnd(String insEnd) {
		this.insEnd = insEnd;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegCusId() {
		return regCusId;
	}
	/**
	 * @设置 登记人编号
	 * @param regCusId
	 */
	public void setRegCusId(String regCusId) {
		this.regCusId = regCusId;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getRegCusName() {
		return regCusName;
	}
	/**
	 * @设置 登记人姓名
	 * @param regCusName
	 */
	public void setRegCusName(String regCusName) {
		this.regCusName = regCusName;
	}
	/**
	 * @return 登记人机构
	 */
	public String getRegOrgId() {
		return regOrgId;
	}
	/**
	 * @设置 登记人机构
	 * @param regOrgId
	 */
	public void setRegOrgId(String regOrgId) {
		this.regOrgId = regOrgId;
	}
	/**
	 * @return 登记人机构名称
	 */
	public String getRegOrgName() {
		return regOrgName;
	}
	/**
	 * @设置 登记人机构名称
	 * @param regOrgName
	 */
	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return 更新人编号
	 */
	public String getUpdateCusId() {
		return updateCusId;
	}
	/**
	 * @设置 更新人编号
	 * @param updateCusId
	 */
	public void setUpdateCusId(String updateCusId) {
		this.updateCusId = updateCusId;
	}
	/**
	 * @return 更新人姓名
	 */
	public String getUpdateCusName() {
		return updateCusName;
	}
	/**
	 * @设置 更新人姓名
	 * @param updateCusName
	 */
	public void setUpdateCusName(String updateCusName) {
		this.updateCusName = updateCusName;
	}
	/**
	 * @return 更新人机构
	 */
	public String getUpdateOrgId() {
		return updateOrgId;
	}
	/**
	 * @设置 更新人机构
	 * @param updateOrgId
	 */
	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	/**
	 * @return 更新人机构名称
	 */
	public String getUpdateOrgName() {
		return updateOrgName;
	}
	/**
	 * @设置 更新人机构名称
	 * @param updateOrgName
	 */
	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
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
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 保险类型
	 */
	public String getInsType() {
		return insType;
	}
	/**
	 * 保险类型
	 */
	public void setInsType(String insType) {
		this.insType = insType;
	}
	/**
	 * 保险第一受益人编号
	 */
	public String getInsBeneficiaryNo() {
		return insBeneficiaryNo;
	}
	/**
	 * 保险第一受益人编号
	 */
	public void setInsBeneficiaryNo(String insBeneficiaryNo) {
		this.insBeneficiaryNo = insBeneficiaryNo;
	}
	/**
	 * 保险第一受益人
	 */
	public String getInsBeneficiaryName() {
		return insBeneficiaryName;
	}
	/**
	 * 保险第一受益人
	 */
	public void setInsBeneficiaryName(String insBeneficiaryName) {
		this.insBeneficiaryName = insBeneficiaryName;
	}
	/**
	 * 备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getInsMode() {
		return insMode;
	}

	public void setInsMode(String insMode) {
		this.insMode = insMode;
	}

	public String getInsPolicyHolderName() {
		return insPolicyHolderName;
	}

	public void setInsPolicyHolderName(String insPolicyHolderName) {
		this.insPolicyHolderName = insPolicyHolderName;
	}

	public String getInsStartDate() {
		return insStartDate;
	}

	public void setInsStartDate(String insStartDate) {
		this.insStartDate = insStartDate;
	}

	public Double getInsFee() {
		return insFee;
	}

	public void setInsFee(Double insFee) {
		this.insFee = insFee;
	}

	public String getInsFeeUndertaker() {
		return insFeeUndertaker;
	}

	public void setInsFeeUndertaker(String insFeeUndertaker) {
		this.insFeeUndertaker = insFeeUndertaker;
	}
}