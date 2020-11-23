package app.component.msgconf.entity;

import java.util.List;

public class MatchAfterLoan {
	private List<PliWarning> pliWarning; //封装贷后预警检查模型对象
	private String latelyAfterLoanDate ;//下次贷后检查的日期 YYYYMMDD
	public List<PliWarning> getPliWarning() {
		return pliWarning;
	}
	public void setPliWarning(List<PliWarning> pliWarning) {
		this.pliWarning = pliWarning;
	}
	public String getLatelyAfterLoanDate() {
		return latelyAfterLoanDate;
	}
	public void setLatelyAfterLoanDate(String latelyAfterLoanDate) {
		this.latelyAfterLoanDate = latelyAfterLoanDate;
	}
	
}
