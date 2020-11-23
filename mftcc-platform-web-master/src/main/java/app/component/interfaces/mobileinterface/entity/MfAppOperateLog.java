package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfAppOperateLog.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Sep 12 17:20:20 CST 2017
* @version：1.0
**/
public class MfAppOperateLog extends BaseDomain {
	private String operateId;//ID
	private String cusNo;
	private String operateDataType;//操作数据类型（1身份证、2手机号、3银行卡）
	private String dataContent;//数据内容（如类型为手机号，这里就是手机号码）
	private String operateTrait;//操作特征（1登陆 2申请）
	private String deviceId;//设备识别码
	private String ip;//ip地址
	private String province;//位置信息,省
	private String city;//位置信息，市
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return ID
	 */
	public String getOperateId() {
	 	return operateId;
	}
	/**
	 * @设置 ID
	 * @param operateId
	 */
	public void setOperateId(String operateId) {
	 	this.operateId = operateId;
	}
	/**
	 * @return 操作数据类型（1身份证、2手机号、3银行卡）
	 */
	public String getOperateDataType() {
	 	return operateDataType;
	}
	/**
	 * @设置 操作数据类型（1身份证、2手机号、3银行卡）
	 * @param operateDataType
	 */
	public void setOperateDataType(String operateDataType) {
	 	this.operateDataType = operateDataType;
	}
	/**
	 * @return 数据内容（如类型为手机号，这里就是手机号码）
	 */
	public String getDataContent() {
	 	return dataContent;
	}
	/**
	 * @设置 数据内容（如类型为手机号，这里就是手机号码）
	 * @param dataContent
	 */
	public void setDataContent(String dataContent) {
	 	this.dataContent = dataContent;
	}
	/**
	 * @return 操作特征（1登陆2申请）
	 */
	public String getOperateTrait() {
	 	return operateTrait;
	}
	/**
	 * @设置 操作特征（1登陆 2申请）
	 * @param operateTrait
	 */
	public void setOperateTrait(String operateTrait) {
	 	this.operateTrait = operateTrait;
	}
	/**
	 * @return 设备识别码
	 */
	public String getDeviceId() {
	 	return deviceId;
	}
	/**
	 * @设置 设备识别码
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
	 	this.deviceId = deviceId;
	}
	/**
	 * @return 位置信息,省
	 */
	public String getProvince() {
	 	return province;
	}
	/**
	 * @设置 位置信息,省
	 * @param province
	 */
	public void setProvince(String province) {
	 	this.province = province;
	}
	/**
	 * @return 位置信息，市
	 */
	public String getCity() {
	 	return city;
	}
	/**
	 * @设置 位置信息，市
	 * @param city
	 */
	public void setCity(String city) {
	 	this.city = city;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}