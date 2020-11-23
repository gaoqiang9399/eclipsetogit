package app.component.model.entity;

//页面输出字段
public class PageField {
	//标识 $key$
	private String key;
	//值
	private String value;
	//字体
	private String font;
	//颜色
	private String color;
	
	public PageField() {
		super();
	}

	public PageField(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
}
