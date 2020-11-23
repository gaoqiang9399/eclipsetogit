package cn.mftcc.util.date;

public enum Week {
	/**
	 *星期一
	 */
	MONDAY("星期一", "Monday", "Mon.", 1),
	/**
	 *星期二
	 */
	TUESDAY("星期二", "Tuesday", "Tues.", 2),
	/**
	 *星期三
	 */
	WEDNESDAY("星期三", "Wednesday", "Wed.", 3),
	/**
	 *星期四
	 */
	THURSDAY("星期四", "Thursday", "Thur.", 4),
	/**
	 *星期五
	 */
	FRIDAY("星期五", "Friday", "Fri.", 5),
	/**
	 *星期六
	 */
	SATURDAY("星期六", "Saturday", "Sat.", 6),
	/**
	 *星期日
	 */
	SUNDAY("星期日", "Sunday", "Sun.", 7);
	
	String name_cn;
	String name_en;
	String name_enShort;
	int number;
	
	Week(String name_cn, String name_en, String name_enShort, int number) {
		this.name_cn = name_cn;
		this.name_en = name_en;
		this.name_enShort = name_enShort;
		this.number = number;
	}
	
	public String getChineseName() {
		return name_cn;
	}

	public String getName() {
		return name_en;
	}

	public String getShortName() {
		return name_enShort;
	}

	public int getNumber() {
		return number;
	}
}