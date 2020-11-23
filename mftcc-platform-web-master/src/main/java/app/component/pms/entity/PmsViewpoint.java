package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsViewpoint extends BaseDomain{
	private String roleNo;
	private String viewpointNo;//视角编号(字典)
	private String viewpointName;//视角名称(字典)
	private String viewpointMenuNo;//菜单编号（按钮编号),主键（手动输入）
	private String viewpointMenuName;//菜单名称（按钮名称)
	private String viewpointMenuUrl;//菜单URL
	private String jsMethod;//定制页面对应的请求
	private String upViewpointMenuNo;//上级的菜单号
	private String urlType;//类型 1-菜单；2-按钮
	private String urlSts;//状态
	private String expr;//表达式
	private String isrefresh;//刷新,isrefresh:"1"
	private int seq;//序列
	public String getViewpointNo() {
		return viewpointNo;
	}
	public void setViewpointNo(String viewpointNo) {
		this.viewpointNo = viewpointNo;
	}
	public String getViewpointName() {
		return viewpointName;
	}
	public void setViewpointName(String viewpointName) {
		this.viewpointName = viewpointName;
	}
	public String getViewpointMenuNo() {
		return viewpointMenuNo;
	}
	public void setViewpointMenuNo(String viewpointMenuNo) {
		this.viewpointMenuNo = viewpointMenuNo;
	}
	public String getViewpointMenuName() {
		return viewpointMenuName;
	}
	public void setViewpointMenuName(String viewpointMenuName) {
		this.viewpointMenuName = viewpointMenuName;
	}
	public String getViewpointMenuUrl() {
		return viewpointMenuUrl;
	}
	public void setViewpointMenuUrl(String viewpointMenuUrl) {
		this.viewpointMenuUrl = viewpointMenuUrl;
	}
	public String getJsMethod() {
		return jsMethod;
	}
	public void setJsMethod(String jsMethod) {
		this.jsMethod = jsMethod;
	}
	public String getUpViewpointMenuNo() {
		return upViewpointMenuNo;
	}
	public void setUpViewpointMenuNo(String upViewpointMenuNo) {
		this.upViewpointMenuNo = upViewpointMenuNo;
	}
	public String getUrlType() {
		return urlType;
	}
	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}
	public String getUrlSts() {
		return urlSts;
	}
	public void setUrlSts(String urlSts) {
		this.urlSts = urlSts;
	}
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
	public String getIsrefresh() {
		return isrefresh;
	}
	public void setIsrefresh(String isrefresh) {
		this.isrefresh = isrefresh;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	
}
