package app.component.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WkfModeRole.java
* Description:
* @author：@dhcc.com.cn
* @Wed Jan 27 03:09:54 GMT 2016
* @version：1.0
**/
public class WkfModeRole extends BaseDomain {
	private String wkfModeNo;//模式编号
	private String wkfVpNo;//视角号
	private String wkfVpMenuNo;//菜单号（或按钮编号）
	private String urlType;//类型 1-菜单；2-按钮
	private String wkfVpName;//视角名称
	private String wkfModeName; //模式名称
	private String oldWkfModeNo;//修改前模式编号
	private String oldWkfVpNo;//修改前视图号
	private String oldWkfMenuNo;//修改前菜单编号
	/**
	 * @return 模式编号
	 */
	public String getWkfModeNo() {
	 	return wkfModeNo;
	}
	/**
	 * @设置 模式编号
	 * @param wkfModeNo
	 */
	public void setWkfModeNo(String wkfModeNo) {
	 	this.wkfModeNo = wkfModeNo;
	}
	/**
	 * @return 视角号
	 */
	public String getWkfVpNo() {
	 	return wkfVpNo;
	}
	/**
	 * @设置 视角号
	 * @param wkfVpNo
	 */
	public void setWkfVpNo(String wkfVpNo) {
	 	this.wkfVpNo = wkfVpNo;
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
	public String getWkfModeName() {
		return wkfModeName;
	}
	public void setWkfModeName(String wkfModeName) {
		this.wkfModeName = wkfModeName;
	}
	public String getOldWkfModeNo() {
		return oldWkfModeNo;
	}
	public void setOldWkfModeNo(String oldWkfModeNo) {
		this.oldWkfModeNo = oldWkfModeNo;
	}
	public String getOldWkfVpNo() {
		return oldWkfVpNo;
	}
	public void setOldWkfVpNo(String oldWkfVpNo) {
		this.oldWkfVpNo = oldWkfVpNo;
	}
	public String getOldWkfMenuNo() {
		return oldWkfMenuNo;
	}
	public void setOldWkfMenuNo(String oldWkfMenuNo) {
		this.oldWkfMenuNo = oldWkfMenuNo;
	}
	
	
}