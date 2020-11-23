package app.component.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WkfType.java
* Description:
* @author：@dhcc.com.cn
* @Fri Jul 01 06:17:10 GMT 2016
* @version：1.0
**/
public class WkfType extends BaseDomain {
	private String wkfVpNo;//审批任务类型编号
	private String wkfVpName;//审批任务类型名称

	/**
	 * @return 审批任务类型编号
	 */
	public String getWkfVpNo() {
	 	return wkfVpNo;
	}
	/**
	 * @设置 审批任务类型编号
	 * @param wkfVpNo
	 */
	public void setWkfVpNo(String wkfVpNo) {
	 	this.wkfVpNo = wkfVpNo;
	}
	/**
	 * @return 审批任务类型名称
	 */
	public String getWkfVpName() {
	 	return wkfVpName;
	}
	/**
	 * @设置 审批任务类型名称
	 * @param wkfVpName
	 */
	public void setWkfVpName(String wkfVpName) {
	 	this.wkfVpName = wkfVpName;
	}
}