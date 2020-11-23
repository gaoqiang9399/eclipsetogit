package app.component.pss.information.entity;
import app.base.BaseDomain;
/**
* Title: PssBillnoConf.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Sep 12 14:31:41 CST 2017
* @version：1.0
**/
public class PssBillnoConf extends BaseDomain {
	private String noId;//编号
	private String billType;//单据类型
	private String noEx;//前缀
	private String addYear;//年
	private String addMonth;//月
	private String addDay;//日
	private String noEndDigit;//后缀位数
	private String noStart;//起始号码
	private String noNow;//当前号码

	/**
	 * @return 编号
	 */
	public String getNoId() {
	 	return noId;
	}
	/**
	 * @设置 编号
	 * @param noId
	 */
	public void setNoId(String noId) {
	 	this.noId = noId;
	}
	/**
	 * @return 单据类型
	 */
	public String getBillType() {
	 	return billType;
	}
	/**
	 * @设置 单据类型
	 * @param billType
	 */
	public void setBillType(String billType) {
	 	this.billType = billType;
	}
	/**
	 * @return 前缀
	 */
	public String getNoEx() {
	 	return noEx;
	}
	/**
	 * @设置 前缀
	 * @param noEx
	 */
	public void setNoEx(String noEx) {
	 	this.noEx = noEx;
	}
	/**
	 * @return 年
	 */
	public String getAddYear() {
	 	return addYear;
	}
	/**
	 * @设置 年
	 * @param addYear
	 */
	public void setAddYear(String addYear) {
	 	this.addYear = addYear;
	}
	/**
	 * @return 月
	 */
	public String getAddMonth() {
	 	return addMonth;
	}
	/**
	 * @设置 月
	 * @param addMonth
	 */
	public void setAddMonth(String addMonth) {
	 	this.addMonth = addMonth;
	}
	/**
	 * @return 日
	 */
	public String getAddDay() {
	 	return addDay;
	}
	/**
	 * @设置 日
	 * @param addDay
	 */
	public void setAddDay(String addDay) {
	 	this.addDay = addDay;
	}
	/**
	 * @return 后缀位数
	 */
	public String getNoEndDigit() {
	 	return noEndDigit;
	}
	/**
	 * @设置 后缀位数
	 * @param noEndDigit
	 */
	public void setNoEndDigit(String noEndDigit) {
	 	this.noEndDigit = noEndDigit;
	}
	/**
	 * @return 起始号码
	 */
	public String getNoStart() {
	 	return noStart;
	}
	/**
	 * @设置 起始号码
	 * @param noStart
	 */
	public void setNoStart(String noStart) {
	 	this.noStart = noStart;
	}
	/**
	 * @return 当前号码
	 */
	public String getNoNow() {
	 	return noNow;
	}
	/**
	 * @设置 当前号码
	 * @param noNow
	 */
	public void setNoNow(String noNow) {
	 	this.noNow = noNow;
	}
}