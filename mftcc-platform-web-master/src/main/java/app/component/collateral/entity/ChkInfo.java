package app.component.collateral.entity;
import app.base.BaseDomain;
/**
* Title: ChkInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Mar 08 10:21:44 CST 2017
* @version：1.0
**/
public class ChkInfo extends BaseDomain {
	private String chkId;//检查编号
	private String chkDate;//检查日期
	private String chkType;//检查方式（包括现场、电核）（必填）
	private String pledgeSts;//押品状态（包括正常、异常）（必填
	private String valueChg;//价值变化情况（升值、不变、贬值
	private String isKeep;//是否妥善保管（是、否）
	private String isSale;//是否变卖出售（是、否）
	private String isMove;//是否被转移（是、否）
	private String isRenewal;//保险到期后是否续保（是、否）
	private String chkOrgId;
	private String chkOrgName;
	private String chkCusId;//检查人
	private String chkCusName;//检查人名称
	private String isLegality;//是否合法
	private String isSuff;//是否充足
	private String liquidate;//变现能力
	private String chkDesc;//检查描述
	private String collateralId;//押品编号
	private String regCusId;//登记人编号
	private String regCusName;//登记人姓名
	private String regOrgId;//登记人机构
	private String regOrgName;//登记人机构名称
	private String regDate;//登记日期
	private String updateCusId;//更新人编号
	private String updateCusName;//更新人姓名
	private String updateOrgId;//更新人机构
	private String updateOrgName;//更新人机构名称
	private String updateDate;//更新日期
//	private String collateralType;//押品类型
	private String classId;

	/**
	 * @return 检查编号
	 */
	public String getChkId() {
	 	return chkId;
	}
	/**
	 * @设置 检查编号
	 * @param chkId
	 */
	public void setChkId(String chkId) {
	 	this.chkId = chkId;
	}
	/**
	 * @return 检查日期
	 */
	public String getChkDate() {
	 	return chkDate;
	}
	/**
	 * @设置 检查日期
	 * @param chkDate
	 */
	public void setChkDate(String chkDate) {
	 	this.chkDate = chkDate;
	}
	/**
	 * @return the chkType
	 */
	public String getChkType() {
		return chkType;
	}
	/**
	 * @param chkType the chkType to set
	 */
	public void setChkType(String chkType) {
		this.chkType = chkType;
	}
	/**
	 * @return the pledgeSts
	 */
	public String getPledgeSts() {
		return pledgeSts;
	}
	/**
	 * @param pledgeSts the pledgeSts to set
	 */
	public void setPledgeSts(String pledgeSts) {
		this.pledgeSts = pledgeSts;
	}
	/**
	 * @return the valueChg
	 */
	public String getValueChg() {
		return valueChg;
	}
	/**
	 * @param valueChg the valueChg to set
	 */
	public void setValueChg(String valueChg) {
		this.valueChg = valueChg;
	}
	/**
	 * @return the isKeep
	 */
	public String getIsKeep() {
		return isKeep;
	}
	/**
	 * @param isKeep the isKeep to set
	 */
	public void setIsKeep(String isKeep) {
		this.isKeep = isKeep;
	}
	/**
	 * @return the isSale
	 */
	public String getIsSale() {
		return isSale;
	}
	/**
	 * @param isSale the isSale to set
	 */
	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}
	/**
	 * @return the isMove
	 */
	public String getIsMove() {
		return isMove;
	}
	/**
	 * @param isMove the isMove to set
	 */
	public void setIsMove(String isMove) {
		this.isMove = isMove;
	}
	/**
	 * @return the isRenewal
	 */
	public String getIsRenewal() {
		return isRenewal;
	}
	/**
	 * @param isRenewal the isRenewal to set
	 */
	public void setIsRenewal(String isRenewal) {
		this.isRenewal = isRenewal;
	}
	/**
	 * @return 检查人
	 */
	public String getChkCusId() {
	 	return chkCusId;
	}
	/**
	 * @设置 检查人
	 * @param chkCusId
	 */
	public void setChkCusId(String chkCusId) {
	 	this.chkCusId = chkCusId;
	}
	/**
	 * @return 检查人名称
	 */
	public String getChkCusName() {
	 	return chkCusName;
	}
	/**
	 * @设置 检查人名称
	 * @param chkCusName
	 */
	public void setChkCusName(String chkCusName) {
	 	this.chkCusName = chkCusName;
	}
	/**
	 * @return 是否合法
	 */
	public String getIsLegality() {
	 	return isLegality;
	}
	/**
	 * @设置 是否合法
	 * @param isLegality
	 */
	public void setIsLegality(String isLegality) {
	 	this.isLegality = isLegality;
	}
	/**
	 * @return 是否充足
	 */
	public String getIsSuff() {
	 	return isSuff;
	}
	/**
	 * @设置 是否充足
	 * @param isSuff
	 */
	public void setIsSuff(String isSuff) {
	 	this.isSuff = isSuff;
	}
	/**
	 * @return 变现能力
	 */
	public String getLiquidate() {
	 	return liquidate;
	}
	/**
	 * @设置 变现能力
	 * @param liquidate
	 */
	public void setLiquidate(String liquidate) {
	 	this.liquidate = liquidate;
	}
	/**
	 * @return 检查描述
	 */
	public String getChkDesc() {
	 	return chkDesc;
	}
	/**
	 * @设置 检查描述
	 * @param chkDesc
	 */
	public void setChkDesc(String chkDesc) {
	 	this.chkDesc = chkDesc;
	}
	public String getCollateralId() {
		return collateralId;
	}
	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}
	public String getChkOrgId() {
		return chkOrgId;
	}
	public void setChkOrgId(String chkOrgId) {
		this.chkOrgId = chkOrgId;
	}
	public String getChkOrgName() {
		return chkOrgName;
	}
	public void setChkOrgName(String chkOrgName) {
		this.chkOrgName = chkOrgName;
	}
	/**
	 * @return the regCusId
	 */
	public String getRegCusId() {
		return regCusId;
	}
	/**
	 * @param regCusId the regCusId to set
	 */
	public void setRegCusId(String regCusId) {
		this.regCusId = regCusId;
	}
	/**
	 * @return the regCusName
	 */
	public String getRegCusName() {
		return regCusName;
	}
	/**
	 * @param regCusName the regCusName to set
	 */
	public void setRegCusName(String regCusName) {
		this.regCusName = regCusName;
	}
	/**
	 * @return the regOrgId
	 */
	public String getRegOrgId() {
		return regOrgId;
	}
	/**
	 * @param regOrgId the regOrgId to set
	 */
	public void setRegOrgId(String regOrgId) {
		this.regOrgId = regOrgId;
	}
	/**
	 * @return the regOrgName
	 */
	public String getRegOrgName() {
		return regOrgName;
	}
	/**
	 * @param regOrgName the regOrgName to set
	 */
	public void setRegOrgName(String regOrgName) {
		this.regOrgName = regOrgName;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the updateCusId
	 */
	public String getUpdateCusId() {
		return updateCusId;
	}
	/**
	 * @param updateCusId the updateCusId to set
	 */
	public void setUpdateCusId(String updateCusId) {
		this.updateCusId = updateCusId;
	}
	/**
	 * @return the updateCusName
	 */
	public String getUpdateCusName() {
		return updateCusName;
	}
	/**
	 * @param updateCusName the updateCusName to set
	 */
	public void setUpdateCusName(String updateCusName) {
		this.updateCusName = updateCusName;
	}
	/**
	 * @return the updateOrgId
	 */
	public String getUpdateOrgId() {
		return updateOrgId;
	}
	/**
	 * @param updateOrgId the updateOrgId to set
	 */
	public void setUpdateOrgId(String updateOrgId) {
		this.updateOrgId = updateOrgId;
	}
	/**
	 * @return the updateOrgName
	 */
	public String getUpdateOrgName() {
		return updateOrgName;
	}
	/**
	 * @param updateOrgName the updateOrgName to set
	 */
	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}
	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
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

}