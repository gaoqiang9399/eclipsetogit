package cn.mftcc.function.bean;


public class MfMsgUtil {
	
	private String varType ;//变量类型
	private String variableEnName;//元素英文名称
	private String variableCnName;//元素中文名称,带{}
	private String variableValue;//元素对应从数据库中查询的值
	
	public String getVariableEnName() {
		return variableEnName;
	}
	public void setVariableEnName(String variableEnName) {
		this.variableEnName = variableEnName;
	}
	public String getVariableCnName() {
		return variableCnName;
	}
	public void setVariableCnName(String variableCnName) {
		this.variableCnName = variableCnName;
	}
	public String getVariableValue() {
		return variableValue;
	}
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
	public String getVarType() {
		return varType;
	}
	public void setVarType(String varType) {
		this.varType = varType;
	}
	
}
