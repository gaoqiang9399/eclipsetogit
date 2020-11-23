package app.component.nmd.entity;

import app.base.BaseDomain;

public class SysLegalHoliday extends BaseDomain{
	private String holidayName;//假日名称
	private String holidayYear;//年份
	private String beginDate;//开始日期
	private String endDate;//结束日期
	private int totalNumber;//总共的天数
	private String edituser;//操作员登陆号
	private	String firstworkDay;//节后第一个工作日

	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getHolidayYear() {
		return holidayYear;
	}
	public void setHolidayYear(String holidayYear) {
		this.holidayYear = holidayYear;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getEdituser() {
		return edituser;
	}
	public void setEdituser(String edituser) {
		this.edituser = edituser;
	}
	public String getFirstworkDay() {
		return firstworkDay;
	}
	public void setFirstworkDay(String firstworkDay) {
		this.firstworkDay = firstworkDay;
	}
}
