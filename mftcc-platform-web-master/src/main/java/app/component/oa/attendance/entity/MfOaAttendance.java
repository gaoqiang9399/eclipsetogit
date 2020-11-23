package app.component.oa.attendance.entity;
import app.base.BaseDomain;
/**
* Title: MfOaAttendance.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 15 15:31:49 CST 2018
* @version：1.0
**/
public class MfOaAttendance extends BaseDomain {
	private String id;//签到表主键
	private String userName;//用户名
	private String userPhone;//手机号
	private String attendanceType;//打卡类型
	private String attendanceYear;//打卡年
	private String attendanceMonth;//打卡月
	private String attendanceDay;//打卡日
	private String attendanceTime;//打卡时间
	private String attendanceAdress;//打卡地址
	private String attendanceSts;//打卡状态

	public String getAttendanceSts() {
		return attendanceSts;
	}
	public void setAttendanceSts(String attendanceSts) {
		this.attendanceSts = attendanceSts;
	}
	/**
	 * @return 签到表主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 签到表主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 用户名
	 */
	public String getUserName() {
	 	return userName;
	}
	/**
	 * @设置 用户名
	 * @param userName
	 */
	public void setUserName(String userName) {
	 	this.userName = userName;
	}
	/**
	 * @return 手机号
	 */
	public String getUserPhone() {
	 	return userPhone;
	}
	/**
	 * @设置 手机号
	 * @param userPhone
	 */
	public void setUserPhone(String userPhone) {
	 	this.userPhone = userPhone;
	}
	/**
	 * @return 打卡类型
	 */
	public String getAttendanceType() {
	 	return attendanceType;
	}
	/**
	 * @设置 打卡类型
	 * @param attendanceType
	 */
	public void setAttendanceType(String attendanceType) {
	 	this.attendanceType = attendanceType;
	}
	/**
	 * @return 打卡年
	 */
	public String getAttendanceYear() {
	 	return attendanceYear;
	}
	/**
	 * @设置 打卡年
	 * @param attendanceYear
	 */
	public void setAttendanceYear(String attendanceYear) {
	 	this.attendanceYear = attendanceYear;
	}
	/**
	 * @return 打卡月
	 */
	public String getAttendanceMonth() {
	 	return attendanceMonth;
	}
	/**
	 * @设置 打卡月
	 * @param attendanceMonth
	 */
	public void setAttendanceMonth(String attendanceMonth) {
	 	this.attendanceMonth = attendanceMonth;
	}
	/**
	 * @return 打卡日
	 */
	public String getAttendanceDay() {
	 	return attendanceDay;
	}
	/**
	 * @设置 打卡日
	 * @param attendanceDay
	 */
	public void setAttendanceDay(String attendanceDay) {
	 	this.attendanceDay = attendanceDay;
	}
	/**
	 * @return 打卡时间
	 */
	public String getAttendanceTime() {
	 	return attendanceTime;
	}
	/**
	 * @设置 打卡时间
	 * @param attendanceTime
	 */
	public void setAttendanceTime(String attendanceTime) {
	 	this.attendanceTime = attendanceTime;
	}
	/**
	 * @return 打卡地址
	 */
	public String getAttendanceAdress() {
	 	return attendanceAdress;
	}
	/**
	 * @设置 打卡地址
	 * @param attendanceAdress
	 */
	public void setAttendanceAdress(String attendanceAdress) {
	 	this.attendanceAdress = attendanceAdress;
	}
}