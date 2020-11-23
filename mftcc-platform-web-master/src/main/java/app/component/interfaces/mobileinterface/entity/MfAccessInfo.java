package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfAccessInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Oct 14 15:33:56 CST 2017
* @version：1.0
**/
public class MfAccessInfo extends BaseDomain {
	private String accessId;//访问信息编号
	private String phoneNumber;//手机号码
	private String phoneModel;//手机型号
	private String imei;//移动设备国际识别码imei
	private String meid;//移动设备识别码meid
	private String networkType;//网络类型3、3G 4、4G 5、5G 6WiFi
	private String ipAddress;//IP地址
	private String systemType;//系统类型android、IOS等
	private String systemVersion;//系统版本号
	private String accessDate;//访问日期
	private String accessTime;//访问时间
	private String accessAddress;//访问地点
	private String longitude;//经度
	private String latitude;//纬度

	/**
	 * @return 访问信息编号
	 */
	public String getAccessId() {
	 	return accessId;
	}
	/**
	 * @设置 访问信息编号
	 * @param accessId
	 */
	public void setAccessId(String accessId) {
	 	this.accessId = accessId;
	}
	/**
	 * @return 手机号码
	 */
	public String getPhoneNumber() {
	 	return phoneNumber;
	}
	/**
	 * @设置 手机号码
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber) {
	 	this.phoneNumber = phoneNumber;
	}
	/**
	 * @return 手机型号
	 */
	public String getPhoneModel() {
	 	return phoneModel;
	}
	/**
	 * @设置 手机型号
	 * @param phoneModel
	 */
	public void setPhoneModel(String phoneModel) {
	 	this.phoneModel = phoneModel;
	}
	/**
	 * @return 移动设备国际识别码imei
	 */
	public String getImei() {
	 	return imei;
	}
	/**
	 * @设置 移动设备国际识别码imei
	 * @param imei
	 */
	public void setImei(String imei) {
	 	this.imei = imei;
	}
	/**
	 * @return 移动设备识别码meid
	 */
	public String getMeid() {
	 	return meid;
	}
	/**
	 * @设置 移动设备识别码meid
	 * @param meid
	 */
	public void setMeid(String meid) {
	 	this.meid = meid;
	}
	/**
	 * @return 网络类型3、3G 4、4G 5、5G 6WiFi
	 */
	public String getNetworkType() {
	 	return networkType;
	}
	/**
	 * @设置 网络类型3、3G 4、4G 5、5G 6WiFi
	 * @param networkType
	 */
	public void setNetworkType(String networkType) {
	 	this.networkType = networkType;
	}
	/**
	 * @return IP地址
	 */
	public String getIpAddress() {
	 	return ipAddress;
	}
	/**
	 * @设置 IP地址
	 * @param ipAddress
	 */
	public void setIpAddress(String ipAddress) {
	 	this.ipAddress = ipAddress;
	}
	/**
	 * @return 系统类型android、IOS等
	 */
	public String getSystemType() {
	 	return systemType;
	}
	/**
	 * @设置 系统类型android、IOS等
	 * @param systemType
	 */
	public void setSystemType(String systemType) {
	 	this.systemType = systemType;
	}
	/**
	 * @return 系统版本号
	 */
	public String getSystemVersion() {
	 	return systemVersion;
	}
	/**
	 * @设置 系统版本号
	 * @param systemVersion
	 */
	public void setSystemVersion(String systemVersion) {
	 	this.systemVersion = systemVersion;
	}
	/**
	 * @return 访问日期
	 */
	public String getAccessDate() {
	 	return accessDate;
	}
	/**
	 * @设置 访问日期
	 * @param accessDate
	 */
	public void setAccessDate(String accessDate) {
	 	this.accessDate = accessDate;
	}
	/**
	 * @return 访问时间
	 */
	public String getAccessTime() {
	 	return accessTime;
	}
	/**
	 * @设置 访问时间
	 * @param accessTime
	 */
	public void setAccessTime(String accessTime) {
	 	this.accessTime = accessTime;
	}
	/**
	 * @return 访问地点
	 */
	public String getAccessAddress() {
	 	return accessAddress;
	}
	/**
	 * @设置 访问地点
	 * @param accessAddress
	 */
	public void setAccessAddress(String accessAddress) {
	 	this.accessAddress = accessAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}