
package app.component.oa.accredit.entity;

/**
*AccreditFuncEnum
 * @author
 */
public enum AccreditFuncEnum {
	/**
	 *请假审批
	 */
	LEAVE_APPROVE("01", "请假审批"),
//	("02", "借款报销审批"),
//	("03", "移交审批"),
//	("04", "评级审批"),
//	("05", "授信审批"),
//	("06", "业务审批"),
//	("07", "押品审批"),
//	("08", "合同审批"),
//	("09", "放款审批"),
//	("10", "提前还款审批"),
//	("11", "贷后检查审批"),
//	("12", "归档审批"),
//	("13", "五级分类审批"),
//	("14", "进件"),
//	("15", "合同"),
//	("16", "贷后检查"),
//	("17", "贷后还款"),
//	("18", "五级分类"),
	/**
	 *归档
	 */
	ARRCHIVE("19", "归档")
	;

	/* 枚举类的属性 */
	private String funcValue;
	private String funcName;

	private AccreditFuncEnum(String val, String name) {
		this.funcValue = val;
		this.funcName = name;
	}
	
	public String getFuncValue() {
		return this.funcValue;
	}
	public String getFuncName() {
		return this.funcName;
	}
	
}
