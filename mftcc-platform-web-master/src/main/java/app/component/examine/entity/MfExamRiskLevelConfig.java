package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamRiskLevelConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 26 09:56:36 CST 2017
* @version：1.0
**/
public class MfExamRiskLevelConfig extends BaseDomain {
	private String configId;//配置编号
	private String opSymbol1;//操作符1
	private String opSymbol2;//操作符2
	private Double opScore1;//区间分数1
	private Double opScore2;//区间分数2
	private String examRiskLevel;//级别
	private String examRiskLevelName;//级别
	private String remark;//贷后风险级别说明
	

	/**
	 * @return 配置编号
	 */
	public String getConfigId() {
	 	return configId;
	}
	/**
	 * @设置 配置编号
	 * @param configId
	 */
	public void setConfigId(String configId) {
	 	this.configId = configId;
	}
	/**
	 * @return 操作符1
	 */
	public String getOpSymbol1() {
	 	return opSymbol1;
	}
	/**
	 * @设置 操作符1
	 * @param opSymbol1
	 */
	public void setOpSymbol1(String opSymbol1) {
	 	this.opSymbol1 = opSymbol1;
	}
	/**
	 * @return 操作符2
	 */
	public String getOpSymbol2() {
	 	return opSymbol2;
	}
	/**
	 * @设置 操作符2
	 * @param opSymbol2
	 */
	public void setOpSymbol2(String opSymbol2) {
	 	this.opSymbol2 = opSymbol2;
	}
	/**
	 * @return 区间分数1
	 */
	public Double getOpScore1() {
	 	return opScore1;
	}
	/**
	 * @设置 区间分数1
	 * @param opScore1
	 */
	public void setOpScore1(Double opScore1) {
	 	this.opScore1 = opScore1;
	}
	/**
	 * @return 区间分数2
	 */
	public Double getOpScore2() {
	 	return opScore2;
	}
	/**
	 * @设置 区间分数2
	 * @param opScore2
	 */
	public void setOpScore2(Double opScore2) {
	 	this.opScore2 = opScore2;
	}
	/**
	 * @return 级别
	 */
	public String getExamRiskLevel() {
	 	return examRiskLevel;
	}
	/**
	 * @设置 级别
	 * @param examRiskLevel
	 */
	public void setExamRiskLevel(String examRiskLevel) {
	 	this.examRiskLevel = examRiskLevel;
	}
	/**
	 * @return 贷后风险级别说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 贷后风险级别说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	public String getExamRiskLevelName() {
		return examRiskLevelName;
	}
	public void setExamRiskLevelName(String examRiskLevelName) {
		this.examRiskLevelName = examRiskLevelName;
	}
}