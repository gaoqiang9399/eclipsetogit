package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssFreightSpace.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jan 10 15:43:42 CST 2018
* @version：1.0
**/
public class PssFreightSpace extends BaseDomain {
	private String freightSpaceId;//主键
	private String storehouseId;//仓库ID
	private Integer sequence;//序号
	private String freightSpaceNo;//编号
	private String freightSpaceName;//名称
	private String enabledStatus;//启用状态(1-启用 0-关闭)
	private String memo;//备注

	/**
	 * @return 主键
	 */
	public String getFreightSpaceId() {
	 	return freightSpaceId;
	}
	/**
	 * @设置 主键
	 * @param freightSpaceId
	 */
	public void setFreightSpaceId(String freightSpaceId) {
	 	this.freightSpaceId = freightSpaceId;
	}
	/**
	 * @return 仓库ID
	 */
	public String getStorehouseId() {
	 	return storehouseId;
	}
	/**
	 * @设置 仓库ID
	 * @param storehouseId
	 */
	public void setStorehouseId(String storehouseId) {
	 	this.storehouseId = storehouseId;
	}
	/**
	 * @return 序号
	 */
	public Integer getSequence() {
		return sequence;
	}
	/**
	 * @设置 序号
	 * @param sequence
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return 编号
	 */
	public String getFreightSpaceNo() {
	 	return freightSpaceNo;
	}
	/**
	 * @设置 编号
	 * @param freightSpaceNo
	 */
	public void setFreightSpaceNo(String freightSpaceNo) {
	 	this.freightSpaceNo = freightSpaceNo;
	}
	/**
	 * @return 名称
	 */
	public String getFreightSpaceName() {
	 	return freightSpaceName;
	}
	/**
	 * @设置 名称
	 * @param freightSpaceName
	 */
	public void setFreightSpaceName(String freightSpaceName) {
	 	this.freightSpaceName = freightSpaceName;
	}
	/**
	 * @return 启用状态(1-启用 0-关闭)
	 */
	public String getEnabledStatus() {
	 	return enabledStatus;
	}
	/**
	 * @设置 启用状态(1-启用 0-关闭)
	 * @param enabledStatus
	 */
	public void setEnabledStatus(String enabledStatus) {
	 	this.enabledStatus = enabledStatus;
	}
	/**
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
}