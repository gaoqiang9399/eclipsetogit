package app.component.pss.utils.excel;

public class PssExcelParmBean {
	private String pssClass;//标识类型
	private String tabColName;//对应的数据库中的表列
	private String tabColNameDesc;//对应的数据库中的表列说明
	private String tabColVal;//对应的数据库中的表列值
	private String dataType;//对应的数据库中的表列数据类型
	private int excelColNo;//对应的清单exl的列号

	public String getPssClass() {
		return pssClass;
	}
	public void setPssClass(String pssClass) {
		this.pssClass = pssClass;
	}
	public String getTabColName() {
		return tabColName;
	}
	public void setTabColName(String tabColName) {
		this.tabColName = tabColName;
	}
	public String getTabColNameDesc() {
		return tabColNameDesc;
	}
	public void setTabColNameDesc(String tabColNameDesc) {
		this.tabColNameDesc = tabColNameDesc;
	}
	public String getTabColVal() {
		return tabColVal;
	}
	public void setTabColVal(String tabColVal) {
		this.tabColVal = tabColVal;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getExcelColNo() {
		return excelColNo;
	}
	public void setExcelColNo(int excelColNo) {
		this.excelColNo = excelColNo;
	}

}
