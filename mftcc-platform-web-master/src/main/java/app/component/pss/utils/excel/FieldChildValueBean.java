package app.component.pss.utils.excel;

public class FieldChildValueBean {
	/** 属性名称,必须 */
	private String name;
	/** 标题,必须 */
	private String title;
	/** value*/
	private String value;
	/** 是否可以为null */
	private boolean isNull = true;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	
}
