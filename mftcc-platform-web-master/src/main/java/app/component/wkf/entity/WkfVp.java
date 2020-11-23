package app.component.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WkfVp.java
* Description:
* @author：@dhcc.com.cn
* @Wed Jan 27 03:07:15 GMT 2016
* @version：1.0
**/
public class WkfVp extends BaseDomain {
	private String wkfVpNo;//视角编号
	private String wkfVpName;//视角名称
	private String wkfVpMenuNo;//菜单号（或按钮编号）
	private String wkfVpMenuName;//菜单名称（或按钮名称）
	private String wkfVpMenuUrl;//菜单URL
	private String jsMethod;//触发JS方法
	private String upWkfVpMenuNo;//上级的菜单号
	private String urlType;//类型 1-菜单；2-按钮
	private String urlSts;//状态
	private String wkfVpRemark;//描述
	private int wkfSeq;//序号-排序用
	private String wkfModeNo;//
	private String expr;//不显示条件

	/**
	 * @return 视角编号
	 */
	public String getWkfVpNo() {
	 	return wkfVpNo;
	}
	/**
	 * @设置 视角编号
	 * @param wkfVpNo
	 */
	public void setWkfVpNo(String wkfVpNo) {
	 	this.wkfVpNo = wkfVpNo;
	}
	/**
	 * @return 视角名称
	 */
	public String getWkfVpName() {
	 	return wkfVpName;
	}
	/**
	 * @设置 视角名称
	 * @param wkfVpName
	 */
	public void setWkfVpName(String wkfVpName) {
	 	this.wkfVpName = wkfVpName;
	}
	/**
	 * @return 菜单号（或按钮编号）
	 */
	public String getWkfVpMenuNo() {
	 	return wkfVpMenuNo;
	}
	/**
	 * @设置 菜单号（或按钮编号）
	 * @param wkfVpMenuNo
	 */
	public void setWkfVpMenuNo(String wkfVpMenuNo) {
	 	this.wkfVpMenuNo = wkfVpMenuNo;
	}
	/**
	 * @return 菜单名称（或按钮名称）
	 */
	public String getWkfVpMenuName() {
	 	return wkfVpMenuName;
	}
	/**
	 * @设置 菜单名称（或按钮名称）
	 * @param wkfVpMenuName
	 */
	public void setWkfVpMenuName(String wkfVpMenuName) {
	 	this.wkfVpMenuName = wkfVpMenuName;
	}
	/**
	 * @return 菜单URL
	 */
	public String getWkfVpMenuUrl() {
	 	return wkfVpMenuUrl;
	}
	/**
	 * @设置 菜单URL
	 * @param wkfVpMenuUrl
	 */
	public void setWkfVpMenuUrl(String wkfVpMenuUrl) {
	 	this.wkfVpMenuUrl = wkfVpMenuUrl;
	}
	/**
	 * @return 触发JS方法
	 */
	public String getJsMethod() {
	 	return jsMethod;
	}
	/**
	 * @设置 触发JS方法
	 * @param jsMethod
	 */
	public void setJsMethod(String jsMethod) {
	 	this.jsMethod = jsMethod;
	}
	/**
	 * @return 上级的菜单号
	 */
	public String getUpWkfVpMenuNo() {
	 	return upWkfVpMenuNo;
	}
	/**
	 * @设置 上级的菜单号
	 * @param upWkfVpMenuNo
	 */
	public void setUpWkfVpMenuNo(String upWkfVpMenuNo) {
	 	this.upWkfVpMenuNo = upWkfVpMenuNo;
	}
	/**
	 * @return 类型 1-菜单；2-按钮
	 */
	public String getUrlType() {
	 	return urlType;
	}
	/**
	 * @设置 类型 1-菜单；2-按钮
	 * @param urlType
	 */
	public void setUrlType(String urlType) {
	 	this.urlType = urlType;
	}
	/**
	 * @return 状态
	 */
	public String getUrlSts() {
	 	return urlSts;
	}
	/**
	 * @设置 状态
	 * @param urlSts
	 */
	public void setUrlSts(String urlSts) {
	 	this.urlSts = urlSts;
	}
	/**
	 * @return 描述
	 */
	public String getWkfVpRemark() {
	 	return wkfVpRemark;
	}
	/**
	 * @设置 描述
	 * @param wkfVpRemark
	 */
	public void setWkfVpRemark(String wkfVpRemark) {
	 	this.wkfVpRemark = wkfVpRemark;
	}
	public int getWkfSeq() {
		return wkfSeq;
	}
	public void setWkfSeq(int wkfSeq) {
		this.wkfSeq = wkfSeq;
	}
	public String getWkfModeNo() {
		return wkfModeNo;
	}
	public void setWkfModeNo(String wkfModeNo) {
		this.wkfModeNo = wkfModeNo;
	}
	public String getExpr() {
		return expr;
	}
	public void setExpr(String expr) {
		this.expr = expr;
	}
}