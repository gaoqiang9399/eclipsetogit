package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsEntrance extends BaseDomain{
	
	private String roleNo;//角色号
	private String pmsNo;//主键
	private String entranceNo;//入口编号(字典)
	private String entranceName;//入口名称
	private String entranceImgCss;//图标css
	private String entranceUrl;//定制页面对应的请求
	private String entranceUrlDesc;//定制页面描述
	private String lev;//级别
	private String urlSts;//状态
	private Integer seqn;//排序
	public String getPmsNo() {
		return pmsNo;
	}
	public void setPmsNo(String pmsNo) {
		this.pmsNo = pmsNo;
	}
	public String getEntranceNo() {
		return entranceNo;
	}
	public void setEntranceNo(String entranceNo) {
		this.entranceNo = entranceNo;
	}
	public String getEntranceName() {
		return entranceName;
	}
	public void setEntranceName(String entranceName) {
		this.entranceName = entranceName;
	}
	public String getEntranceImgCss() {
		return entranceImgCss;
	}
	public void setEntranceImgCss(String entranceImgCss) {
		this.entranceImgCss = entranceImgCss;
	}
	public String getEntranceUrl() {
		return entranceUrl;
	}
	public void setEntranceUrl(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}
	public String getEntranceUrlDesc() {
		return entranceUrlDesc;
	}
	public void setEntranceUrlDesc(String entranceUrlDesc) {
		this.entranceUrlDesc = entranceUrlDesc;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getUrlSts() {
		return urlSts;
	}
	public void setUrlSts(String urlSts) {
		this.urlSts = urlSts;
	}
	public Integer getSeqn() {
		return seqn;
	}
	public void setSeqn(Integer seqn) {
		this.seqn = seqn;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	
}
