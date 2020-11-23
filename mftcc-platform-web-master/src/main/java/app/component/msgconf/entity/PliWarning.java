package app.component.msgconf.entity;
import app.base.BaseDomain;
/**
* Title: PliWarning.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jul 11 10:38:28 CST 2017
* @version：1.0
**/
public class PliWarning extends BaseDomain {
	private String pliWarnNo;//预警编号
	private String pliWarnName;//预警名称
	private String pliWarnType;//预警类型（放款日后、还款日前、逾期后、按固定日期）
	private Integer pliDays;//指定天数
	private String pliWarnContent;//预警信息
	private String pliContentArgs;//预警信息参数
	private String pliRelModels;//关联贷后检查模型
	private String flag;//是否启用
	private String recCusType;//接收人
	private String sendType;//发送类型
	private String createDate;//创建时间
	private Integer pliFreq;//频率
	private String pliFreqUnit;//频率单位
	private Integer pliWarnLimit;//预警最大次数
	private String varUsageRel;

	/**
	 * @return 预警编号
	 */
	public String getPliWarnNo() {
	 	return pliWarnNo;
	}
	/**
	 * @设置 预警编号
	 * @param pliWarnNo
	 */
	public void setPliWarnNo(String pliWarnNo) {
	 	this.pliWarnNo = pliWarnNo;
	}
	/**
	 * @return 预警名称
	 */
	public String getPliWarnName() {
	 	return pliWarnName;
	}
	/**
	 * @设置 预警名称
	 * @param pliWarnName
	 */
	public void setPliWarnName(String pliWarnName) {
	 	this.pliWarnName = pliWarnName;
	}
	/**
	 * @return 预警类型（放款日后、还款日前、逾期后、按固定日期）
	 */
	public String getPliWarnType() {
	 	return pliWarnType;
	}
	/**
	 * @设置 预警类型（放款日后、还款日前、逾期后、按固定日期）
	 * @param pliWarnType
	 */
	public void setPliWarnType(String pliWarnType) {
	 	this.pliWarnType = pliWarnType;
	}
	/**
	 * @return 指定天数
	 */
	public Integer getPliDays() {
	 	return pliDays;
	}
	/**
	 * @设置 指定天数
	 * @param pliDays
	 */
	public void setPliDays(Integer pliDays) {
	 	this.pliDays = pliDays;
	}
	/**
	 * @return 预警信息
	 */
	public String getPliWarnContent() {
	 	return pliWarnContent;
	}
	/**
	 * @设置 预警信息
	 * @param pliWarnContent
	 */
	public void setPliWarnContent(String pliWarnContent) {
	 	this.pliWarnContent = pliWarnContent;
	}
	/**
	 * @return 预警信息参数
	 */
	public String getPliContentArgs() {
	 	return pliContentArgs;
	}
	/**
	 * @设置 预警信息参数
	 * @param pliContentArgs
	 */
	public void setPliContentArgs(String pliContentArgs) {
	 	this.pliContentArgs = pliContentArgs;
	}
	/**
	 * @return 关联贷后检查模型
	 */
	public String getPliRelModels() {
	 	return pliRelModels;
	}
	/**
	 * @设置 关联贷后检查模型
	 * @param pliRelModels
	 */
	public void setPliRelModels(String pliRelModels) {
	 	this.pliRelModels = pliRelModels;
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
	public Integer getPliFreq() {
	 	return pliFreq;
	}
	/**
	 * @设置 频率
	 * @param pliFreq
	 */
	public void setPliFreq(Integer pliFreq) {
	 	this.pliFreq = pliFreq;
	}
	/**
	 * @return 频率单位
	 */
	public String getPliFreqUnit() {
	 	return pliFreqUnit;
	}
	/**
	 * @设置 频率单位
	 * @param pliFreqUnit
	 */
	public void setPliFreqUnit(String pliFreqUnit) {
	 	this.pliFreqUnit = pliFreqUnit;
	}
	/**
	 * @return 预警最大次数
	 */
	public Integer getPliWarnLimit() {
	 	return pliWarnLimit;
	}
	/**
	 * @设置 预警最大次数
	 * @param pliWarnLimit
	 */
	public void setPliWarnLimit(Integer pliWarnLimit) {
	 	this.pliWarnLimit = pliWarnLimit;
	}
	public String getVarUsageRel() {
		return varUsageRel;
	}
	public void setVarUsageRel(String varUsageRel) {
		this.varUsageRel = varUsageRel;
	}
	
}