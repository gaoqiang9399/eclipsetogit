package app.component.nmd.entity;

public class SysLegalHolidayEvent {
	private String title;//标题
	private String start;//开始日期
	private String end;//结束日期
	private String allDay = "true";//是否是全天事件,true
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getAllDay() {
		return allDay;
	}
	public void setAllDay(String allDay) {
		this.allDay = allDay;
	}
}