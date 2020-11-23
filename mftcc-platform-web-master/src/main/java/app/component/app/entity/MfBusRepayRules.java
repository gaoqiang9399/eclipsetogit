package app.component.app.entity;
import app.base.BaseDomain;
/**
* Title: MfBusRepayRules.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Aug 28 16:38:07 CST 2017
* @version：1.0
**/
public class MfBusRepayRules extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668095680663111290L;
	private String id;//唯一ID
	private String kindNo;//产品种类编号
	private String repayType;//还款方式 1-等额本息,2-等额本金,3-利随本清,4-到期还本按月结息,5-到期还本按季结息,6-按计划,7-等本等息,8-按期还息,9-到期还本按自然季结息,A-自定义还款,B-按周期天数还款 取自parm_dic.key_name=REPAY_TYPE
	private String normCalcType;//利息计算方式 2-按月结息,3-按日结息 取自parm_dic.key_name=NORM_CALC_TYPE  
	private String secondNormCalcType;//按月结息时不足一月是否按月计算 2-按月结息,3-按日结息
	private String repayDateSet;//还款日设置：1-贷款发放日 2-月末 3-固定还款日  parm_dic表key_name=REPAY_DATE_SET_TYPE
	private String repayDateDef;//默认还款日 当repay_date_set为固定还款日时,该字段才有值
	private String interestCollectType;//利息收息方式：1-上收息,2-下收息  parm_dic表key_name=INTEREST_COLLECT_TYPE
	private String yearDays;//一年等于多少天：360,365
	private String rulesNo;//规则引擎唯一编号
	/**
	 * 预先支付利息收取方式：3-放款时收取，1-合并第一期，2-独立一期  parm_dic表key_name=PRE_INTEREST_COLLECT_TYPE
	 */
	private String preInstCollectType;

	/**
	 * @return 唯一ID
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一ID
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 产品种类编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品种类编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 还款方式 1-等额本息,2-等额本金,3-利随本清,4-到期还本按月结息,5-到期还本按季结息,6-按计划,7-等本等息,8-按期还息,9-到期还本按自然季结息,A-自定义还款,B-按周期天数还款 取自parm_dic.key_name=REPAY_TYPE
	 */
	public String getRepayType() {
	 	return repayType;
	}
	/**
	 * @设置 还款方式 1-等额本息,2-等额本金,3-利随本清,4-到期还本按月结息,5-到期还本按季结息,6-按计划,7-等本等息,8-按期还息,9-到期还本按自然季结息,A-自定义还款,B-按周期天数还款 取自parm_dic.key_name=REPAY_TYPE
	 * @param repayType
	 */
	public void setRepayType(String repayType) {
	 	this.repayType = repayType;
	}
	/**
	 * @return 利息计算方式 2-按月结息,3-按日结息 取自parm_dic.key_name=NORM_CALC_TYPE  
	 */
	public String getNormCalcType() {
	 	return normCalcType;
	}
	/**
	 * @设置 利息计算方式 2-按月结息,3-按日结息 取自parm_dic.key_name=NORM_CALC_TYPE  
	 * @param normCalcType
	 */
	public void setNormCalcType(String normCalcType) {
	 	this.normCalcType = normCalcType;
	}
	/**
	 * @return 按月结息时不足一月是否按月计算 2-按月结息,3-按日结息
	 */
	public String getSecondNormCalcType() {
	 	return secondNormCalcType;
	}
	/**
	 * @设置 按月结息时不足一月是否按月计算 2-按月结息,3-按日结息
	 * @param secondNormCalcType
	 */
	public void setSecondNormCalcType(String secondNormCalcType) {
	 	this.secondNormCalcType = secondNormCalcType;
	}
	/**
	 * @return 还款日设置：1-贷款发放日 2-月末 3-固定还款日  parm_dic表key_name=REPAY_DATE_SET_TYPE
	 */
	public String getRepayDateSet() {
	 	return repayDateSet;
	}
	/**
	 * @设置 还款日设置：1-贷款发放日 2-月末 3-固定还款日  parm_dic表key_name=REPAY_DATE_SET_TYPE
	 * @param repayDateSet
	 */
	public void setRepayDateSet(String repayDateSet) {
	 	this.repayDateSet = repayDateSet;
	}
	/**
	 * @return 默认还款日 当repay_date_set为固定还款日时,该字段才有值
	 */
	public String getRepayDateDef() {
	 	return repayDateDef;
	}
	/**
	 * @设置 默认还款日 当repay_date_set为固定还款日时,该字段才有值
	 * @param repayDateDef
	 */
	public void setRepayDateDef(String repayDateDef) {
	 	this.repayDateDef = repayDateDef;
	}
	/**
	 * @return 利息收息方式：1-上收息,2-下收息  parm_dic表key_name=INTEREST_COLLECT_TYPE
	 */
	public String getInterestCollectType() {
	 	return interestCollectType;
	}
	/**
	 * @设置 利息收息方式：1-上收息,2-下收息  parm_dic表key_name=INTEREST_COLLECT_TYPE
	 * @param interestCollectType
	 */
	public void setInterestCollectType(String interestCollectType) {
	 	this.interestCollectType = interestCollectType;
	}
	/**
	 * @return 一年等于多少天：360,365
	 */
	public String getYearDays() {
	 	return yearDays;
	}
	/**
	 * @设置 一年等于多少天：360,365
	 * @param yearDays
	 */
	public void setYearDays(String yearDays) {
	 	this.yearDays = yearDays;
	}
	/**
	 * @return 规则引擎唯一编号
	 */
	public String getRulesNo() {
	 	return rulesNo;
	}
	/**
	 * @设置 规则引擎唯一编号
	 * @param rulesNo
	 */
	public void setRulesNo(String rulesNo) {
	 	this.rulesNo = rulesNo;
	}
	public String getPreInstCollectType() {
		return preInstCollectType;
	}
	public void setPreInstCollectType(String preInstCollectType) {
		this.preInstCollectType = preInstCollectType;
	}
	
}