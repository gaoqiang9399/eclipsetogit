package app.component.sys.entity;
import app.base.BaseDomain;

public class SysBtnUrl extends BaseDomain {
	private String urlId; // 编号
	private String btnId; // 外键
	private String btnNo; // 按钮编号
	private String urlName; // 链接名称
	private String urlValue; // 链接地址
	public String getUrlId() {
		return urlId;
	}
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	public String getBtnId() {
		return btnId;
	}
	public void setBtnId(String btnId) {
		this.btnId = btnId;
	}
	public String getUrlValue() {
		return urlValue;
	}
	public void setUrlValue(String urlValue) {
		this.urlValue = urlValue;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public String getBtnNo() {
		return btnNo;
	}
	public void setBtnNo(String btnNo) {
		this.btnNo = btnNo;
	}
	
}
