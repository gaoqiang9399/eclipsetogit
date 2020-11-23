package app.component.pss.utils.excel;

public class PssExcelImportMsg {
	/** 错误行 */
	private int row;
	/** 错误列 */
	private int column;
	/** 错误信息展示列 */
	private int columnShow;
	/** 错误信息 */
	private StringBuilder sb;
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getColumnShow() {
		return columnShow;
	}
	public void setColumnShow(int columnShow) {
		this.columnShow = columnShow;
	}
	public StringBuilder getSb() {
		return sb;
	}
	public void setSb(StringBuilder sb) {
		this.sb = sb;
	}
	
}
