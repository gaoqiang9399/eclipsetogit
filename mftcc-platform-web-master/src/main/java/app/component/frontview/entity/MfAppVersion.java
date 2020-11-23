package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfAppVersion.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 01 10:51:33 CST 2017
* @version：1.0
**/
public class MfAppVersion extends BaseDomain {
	private String id;//流水号
	private String appName;//app名称
	private String appVersion;//app版本号
	private String ifUpdate;//是否更新 0 否 1是
	private String publishTime;//登记时间格式：yyyyMMdd HH:mm:ss

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return app名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 app名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return app版本号
	 */
	public String getAppVersion() {
	 	return appVersion;
	}
	/**
	 * @设置 app版本号
	 * @param appVersion
	 */
	public void setAppVersion(String appVersion) {
	 	this.appVersion = appVersion;
	}
	/**
	 * @return 是否更新 0 否 1是
	 */
	public String getIfUpdate() {
	 	return ifUpdate;
	}
	/**
	 * @设置 是否更新 0 否 1是
	 * @param ifUpdate
	 */
	public void setIfUpdate(String ifUpdate) {
	 	this.ifUpdate = ifUpdate;
	}
	/**
	 * @return 登记时间格式：yyyyMMdd HH:mm:ss
	 */
	public String getPublishTime() {
	 	return publishTime;
	}
	/**
	 * @设置 登记时间格式：yyyyMMdd HH:mm:ss
	 * @param publishTime
	 */
	public void setPublishTime(String publishTime) {
	 	this.publishTime = publishTime;
	}
}