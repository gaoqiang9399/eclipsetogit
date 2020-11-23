package app.component.collateral.entity;

public class PledgeListBean {
	
	private  String pleClsNo;//押品类别编号
	private  String pleClsName;//押品类别名称
	private  String tabColName[];//对应的数据库中的表列
	private  String tabColNameDesc[];//对应的数据库中的表列说明
	private  String exlColName[];//对应的清单exl的列号
	public String getPleClsNo() {
		return pleClsNo;
	}
	public void setPleClsNo(String pleClsNo) {
		this.pleClsNo = pleClsNo;
	}
	public String getPleClsName() {
		return pleClsName;
	}
	public void setPleClsName(String pleClsName) {
		this.pleClsName = pleClsName;
	}
	public String[] getTabColName() {
		return tabColName;
	}
	public void setTabColName(String[] tabColName) {
		this.tabColName = tabColName;
	}
	public String[] getTabColNameDesc() {
		return tabColNameDesc;
	}
	public void setTabColNameDesc(String[] tabColNameDesc) {
		this.tabColNameDesc = tabColNameDesc;
	}
	public String[] getExlColName() {
		return exlColName;
	}
	public void setExlColName(String[] exlColName) {
		this.exlColName = exlColName;
	}
	

}
