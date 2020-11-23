package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfFrontAppSetting.java
* Description:移动端基本配置
* @author：kaifa@dhcc.com.cn
* @Thu Jul 20 11:25:24 CST 2017
* @version：1.0
**/
public class MfFrontAppSetting extends BaseDomain {
	private String id;//唯一编号
	private String tel;//400电话
	private String serviceQq;//qq在线客服
	private String shareIconPath;//分享图标地址
	private String shareUrl;//分享链接
	private String shareContent;//分享内容
	private String regTime;//登记时间

	/**
	 * @return 唯一编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 400电话
	 */
	public String getTel() {
	 	return tel;
	}
	/**
	 * @设置 400电话
	 * @param tel
	 */
	public void setTel(String tel) {
	 	this.tel = tel;
	}
	/**
	 * @return qq在线客服
	 */
	public String getServiceQq() {
	 	return serviceQq;
	}
	/**
	 * @设置 qq在线客服
	 * @param serviceQq
	 */
	public void setServiceQq(String serviceQq) {
	 	this.serviceQq = serviceQq;
	}
	/**
	 * @return 分享图标地址
	 */
	public String getShareIconPath() {
	 	return shareIconPath;
	}
	/**
	 * @设置 分享图标地址
	 * @param shareIconPath
	 */
	public void setShareIconPath(String shareIconPath) {
	 	this.shareIconPath = shareIconPath;
	}
	/**
	 * @return 分享链接
	 */
	public String getShareUrl() {
	 	return shareUrl;
	}
	/**
	 * @设置 分享链接
	 * @param shareUrl
	 */
	public void setShareUrl(String shareUrl) {
	 	this.shareUrl = shareUrl;
	}
	/**
	 * @return 分享内容
	 */
	public String getShareContent() {
	 	return shareContent;
	}
	/**
	 * @设置 分享内容
	 * @param shareContent
	 */
	public void setShareContent(String shareContent) {
	 	this.shareContent = shareContent;
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
}