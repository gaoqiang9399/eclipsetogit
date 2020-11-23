package app.component.report.util;

/**
 * *
 * 类描述:转换json用的实体
 * @author 李伟
 * @date 2017-8-24 下午5:42:04
 */
public class MfReportQueryConfigJson {
	private String condition;
	private String compareType;//运算符标志: 1-大于 2-大于等于 3-等于 4-小于 5-小于等于 6-不等于 7-like 8-in 9-not in
	private String value;
	private String varcharFlag;//字符串标志: 1-字符串 2-整形 
	private String conditionFlag;//条件标志: 1-部门 2-操作员 3-其他
	private String andOr;//0-and 1-or 
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getCompareType() {
		return compareType;
	}
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getVarcharFlag() {
		return varcharFlag;
	}
	public void setVarcharFlag(String varcharFlag) {
		this.varcharFlag = varcharFlag;
	}
	public String getConditionFlag() {
		return conditionFlag;
	}
	public void setConditionFlag(String conditionFlag) {
		this.conditionFlag = conditionFlag;
	}
	public String getAndOr() {
		return andOr;
	}
	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}
	
}
