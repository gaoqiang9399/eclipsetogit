package app.component.finance.finreport.entity;
import app.base.BaseDomain;

/**
 * 类名： CwItemCalcList 描述：
 * 
 * @author Javelin
 * @date 2017-2-8 下午3:47:00
 */
public class CwItemCalcList extends BaseDomain {
	private String reportItemId;// 上级报表项编号
	private String reportName;// 报表项名称
	private String calcSign;// 运算符号1：加 ；2：减
	private String gsType;// 格式类型1：科目 ；2：报表项
	private String calcItem;// 公式数据：科目或报表项
	private String calcRule;// 公式深度
	private String calcAmt;// 公式金额

	/**
	 * @return 上级报表项编号
	 */
	public String getReportItemId() {
		return reportItemId;
	}
	/**
	 * @设置 上级报表项编号
	 * @param reportItemId
	 */
	public void setReportItemId(String reportItemId) {
		this.reportItemId = reportItemId;
	}
	/**
	 * @return 运算符号1：加 ；2：减
	 */
	public String getCalcSign() {
		return calcSign;
	}
	/**
	 * @设置 运算符号1：加 ；2：减
	 * @param calcSign
	 */
	public void setCalcSign(String calcSign) {
		this.calcSign = calcSign;
	}
	/**
	 * @return 格式类型1：科目 ；2：报表项
	 */
	public String getGsType() {
		return gsType;
	}
	/**
	 * @设置 格式类型1：科目 ；2：报表项
	 * @param gsType
	 */
	public void setGsType(String gsType) {
		this.gsType = gsType;
	}
	/**
	 * @return 公式数据
	 */
	public String getCalcItem() {
		return calcItem;
	}
	/**
	 * @设置 公式数据
	 * @param calcItem
	 */
	public void setCalcItem(String calcItem) {
		this.calcItem = calcItem;
	}
	/**
	 * @return 公式深度
	 */
	public String getCalcRule() {
		return calcRule;
	}
	/**
	 * @设置 公式深度
	 * @param calcRule
	 */
	public void setCalcRule(String calcRule) {
		this.calcRule = calcRule;
	}
	/**
	 * @return 公式金额
	 */
	public String getCalcAmt() {
		return calcAmt;
	}
	/**
	 * @设置 公式金额
	 * @param calcRule
	 */
	public void setCalcAmt(String calcAmt) {
		this.calcAmt = calcAmt;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}