package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfCallRecords.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jul 20 10:28:02 CST 2017
* @version：1.0
**/
public class MfCallRecords extends BaseDomain {
	private String cusNo;//客户号
	private String phoneNo;//手机号码
	private String occDate;//通话日期
	private String useTime;//通话时长

	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 手机号码
	 */
	public String getPhoneNo() {
	 	return phoneNo;
	}
	/**
	 * @设置 手机号码
	 * @param phoneNo
	 */
	public void setPhoneNo(String phoneNo) {
	 	this.phoneNo = phoneNo;
	}
	/**
	 * @return 通话日期
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 通话日期
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
	/**
	 * @return 通话时长
	 */
	public String getUseTime() {
	 	return useTime;
	}
	/**
	 * @设置 通话时长
	 * @param useTime
	 */
	public void setUseTime(String useTime) {
	 	this.useTime = useTime;
	}
}