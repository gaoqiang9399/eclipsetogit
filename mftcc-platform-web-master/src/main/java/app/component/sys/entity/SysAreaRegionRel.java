package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysAreaRegionRel.java
* Description:
* @author：@dhcc.com.cn
* @Tue May 10 03:42:07 GMT 2016
* @version：1.0
**/
public class SysAreaRegionRel extends BaseDomain {
	private String regionNo;//大区编号
	private String regionName;//大区名称
	private String provNo;//省编号
	private String provName;//省名称
	private String id;//序号

	/**
	 * @return 大区编号
	 */
	public String getRegionNo() {
	 	return regionNo;
	}
	/**
	 * @设置 大区编号
	 * @param regionNo
	 */
	public void setRegionNo(String regionNo) {
	 	this.regionNo = regionNo;
	}
	/**
	 * @return 大区名称
	 */
	public String getRegionName() {
	 	return regionName;
	}
	/**
	 * @设置 大区名称
	 * @param regionName
	 */
	public void setRegionName(String regionName) {
	 	this.regionName = regionName;
	}
	/**
	 * @return 省编号
	 */
	public String getProvNo() {
	 	return provNo;
	}
	/**
	 * @设置 省编号
	 * @param provNo
	 */
	public void setProvNo(String provNo) {
	 	this.provNo = provNo;
	}
	/**
	 * @return 省名称
	 */
	public String getProvName() {
	 	return provName;
	}
	/**
	 * @设置 省名称
	 * @param provName
	 */
	public void setProvName(String provName) {
	 	this.provName = provName;
	}
	/**
	 * @return 序号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 序号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
}