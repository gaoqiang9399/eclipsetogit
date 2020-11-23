package app.component.msgconf.entity;
import app.base.BaseDomain;
/**
* Title: CuslendWarning.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jul 13 10:44:08 CST 2017
* @version：1.0
**/
public class CuslendWarning extends BaseDomain {
	private String cuslendWarnNo;//预警编号
	private String cuslendWarnName;//预警名称
	private String cuslendWarnType;//预警类型（放款日后、还款日前、逾期后、按固定日期）
	private Integer cuslendDays;//指定天数
	private String cuslendWarnContent;//预警信息
	private String cuslendContentArgs;//预警信息参数
	private String cuslendRelModels;//关联贷后检查模型
	private String flag;//是否启用
	private String recCusType;//接收人
	private String sendType;//发送类型
	private String createDate;//创建时间
	private Integer cuslendFreq;//频率
	private String cuslendFreqUnit;//频率单位
	private Integer cuslendWarnLimit;//预警最大次数
	private String varUsageRel;//关联变量类型
	private String msgType;//预警或消息所属类型
	private String msgExplain;//预警或消息说明
	private String kindNo;//产品编号

	/*20190426 yxl 新增催收预警功能相关 -- START*/
	private String disappearDays;//失联天数
	private String outFlag;//	还款状态
	private String repayType;//	还款方式
	private String recallDate;//	计划开始日期
	private String recallEndDate;//	计划结束日期
	private String recallWay;//	催收方式
	private String recallDesc;//	催收描述
	private String repaymentQH;//	还款期号
	private String roleNo;// 接收角色
	private String riskLevel;
	/*20190426 yxl 新增催收预警功能相关 --END*/

	public String getDisappearDays() {
		return disappearDays;
	}

	public void setDisappearDays(String disappearDays) {
		this.disappearDays = disappearDays;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getRecallDate() {
		return recallDate;
	}

	public void setRecallDate(String recallDate) {
		this.recallDate = recallDate;
	}

	public String getRecallEndDate() {
		return recallEndDate;
	}

	public void setRecallEndDate(String recallEndDate) {
		this.recallEndDate = recallEndDate;
	}

	public String getRecallWay() {
		return recallWay;
	}

	public void setRecallWay(String recallWay) {
		this.recallWay = recallWay;
	}

	public String getRecallDesc() {
		return recallDesc;
	}

	public void setRecallDesc(String recallDesc) {
		this.recallDesc = recallDesc;
	}

	public String getRepaymentQH() {
		return repaymentQH;
	}

	public void setRepaymentQH(String repaymentQH) {
		this.repaymentQH = repaymentQH;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**
	 * @return 预警编号
	 */
	public String getCuslendWarnNo() {
	 	return cuslendWarnNo;
	}
	/**
	 * @设置 预警编号
	 * @param cuslendWarnNo
	 */
	public void setCuslendWarnNo(String cuslendWarnNo) {
	 	this.cuslendWarnNo = cuslendWarnNo;
	}
	/**
	 * @return 预警名称
	 */
	public String getCuslendWarnName() {
	 	return cuslendWarnName;
	}
	/**
	 * @设置 预警名称
	 * @param cuslendWarnName
	 */
	public void setCuslendWarnName(String cuslendWarnName) {
	 	this.cuslendWarnName = cuslendWarnName;
	}
	/**
	 * @return 预警类型（放款日后、还款日前、逾期后、按固定日期）
	 */
	public String getCuslendWarnType() {
	 	return cuslendWarnType;
	}
	/**
	 * @设置 预警类型（放款日后、还款日前、逾期后、按固定日期）
	 * @param cuslendWarnType
	 */
	public void setCuslendWarnType(String cuslendWarnType) {
	 	this.cuslendWarnType = cuslendWarnType;
	}
	/**
	 * @return 指定天数
	 */
	public Integer getCuslendDays() {
	 	return cuslendDays;
	}
	/**
	 * @设置 指定天数
	 * @param cuslendDays
	 */
	public void setCuslendDays(Integer cuslendDays) {
	 	this.cuslendDays = cuslendDays;
	}
	/**
	 * @return 预警信息
	 */
	public String getCuslendWarnContent() {
	 	return cuslendWarnContent;
	}
	/**
	 * @设置 预警信息
	 * @param cuslendWarnContent
	 */
	public void setCuslendWarnContent(String cuslendWarnContent) {
	 	this.cuslendWarnContent = cuslendWarnContent;
	}
	/**
	 * @return 预警信息参数
	 */
	public String getCuslendContentArgs() {
	 	return cuslendContentArgs;
	}
	/**
	 * @设置 预警信息参数
	 * @param cuslendContentArgs
	 */
	public void setCuslendContentArgs(String cuslendContentArgs) {
	 	this.cuslendContentArgs = cuslendContentArgs;
	}
	/**
	 * @return 关联贷后检查模型
	 */
	public String getCuslendRelModels() {
	 	return cuslendRelModels;
	}
	/**
	 * @设置 关联贷后检查模型
	 * @param cuslendRelModels
	 */
	public void setCuslendRelModels(String cuslendRelModels) {
	 	this.cuslendRelModels = cuslendRelModels;
	}
	/**
	 * @return 是否启用
	 */
	public String getFlag() {
	 	return flag;
	}
	/**
	 * @设置 是否启用
	 * @param flag
	 */
	public void setFlag(String flag) {
	 	this.flag = flag;
	}
	/**
	 * @return 接收人
	 */
	public String getRecCusType() {
	 	return recCusType;
	}
	/**
	 * @设置 接收人
	 * @param recCusType
	 */
	public void setRecCusType(String recCusType) {
	 	this.recCusType = recCusType;
	}
	/**
	 * @return 发送类型
	 */
	public String getSendType() {
	 	return sendType;
	}
	/**
	 * @设置 发送类型
	 * @param sendType
	 */
	public void setSendType(String sendType) {
	 	this.sendType = sendType;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 创建时间
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 频率
	 */
	public Integer getCuslendFreq() {
	 	return cuslendFreq;
	}
	/**
	 * @设置 频率
	 * @param cuslendFreq
	 */
	public void setCuslendFreq(Integer cuslendFreq) {
	 	this.cuslendFreq = cuslendFreq;
	}
	/**
	 * @return 频率单位
	 */
	public String getCuslendFreqUnit() {
	 	return cuslendFreqUnit;
	}
	/**
	 * @设置 频率单位
	 * @param cuslendFreqUnit
	 */
	public void setCuslendFreqUnit(String cuslendFreqUnit) {
	 	this.cuslendFreqUnit = cuslendFreqUnit;
	}
	/**
	 * @return 预警最大次数
	 */
	public Integer getCuslendWarnLimit() {
	 	return cuslendWarnLimit;
	}
	/**
	 * @设置 预警最大次数
	 * @param cuslendWarnLimit
	 */
	public void setCuslendWarnLimit(Integer cuslendWarnLimit) {
	 	this.cuslendWarnLimit = cuslendWarnLimit;
	}
	public String getVarUsageRel() {
		return varUsageRel;
	}
	public void setVarUsageRel(String varUsageRel) {
		this.varUsageRel = varUsageRel;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgExplain() {
		return msgExplain;
	}
	public void setMsgExplain(String msgExplain) {
		this.msgExplain = msgExplain;
	}
	/**
	 * @return 产品编号
	 */
	public String getKindNo() {
		return kindNo;
	}
	/**
	 * @设置 产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}
}