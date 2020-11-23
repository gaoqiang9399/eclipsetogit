package app.component.report.entity;


public class MfReportStatus {

	private String typename;

	//对应列项目名
	private String keyoption;
	//年初户数
	private String startyearh;
	//年初余额
	private String startyearcnt;
	//年初笔数
	private String startyearamt;
	//月初户数
	private String startmonthh;
	//月初余额笔数
    private String startmonthcnt;
    //月初余额
	private String startmonthamt;
	//本月发生额--发放户数
	private String currmonthhff;
	//本月发生额--发放笔数
    private String currmonthcntff;
    //本月发生额--发放金额
	private String currmonthamtff;
	//本月发生额--回收户数
	private String currmonthhhs;
	//本月发生额--回收笔数
    private String currmonthcnths;
    //本月发生额--回收金额
	private String currmonthamths;
	//本年累计发生额--发放户数
	private String curryearhff;
	//本年累计发生额--发放笔数
    private String curryearcntff;
    //本年累计发生额--发放金额
	private String curryearamtff;
	//本年累计发生额--回收户数
	private String curryearhhs;
	//本年累计发生额--回收笔数
    private String curryearcnths;
    //本年累计发生额--回收笔数
	private String curryearamths;
	//期末余额 --户数
	private String lasth;
	//期末余额 --笔数
	private String lastcnt;
	//期末余额 --金额
    private String lastbal;
	
	
    private String loanInfo;
    
    
    public String getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(String loanInfo) {
		this.loanInfo = loanInfo;
	}

	public String getStartyearh() {
		return startyearh;
	}

	public void setStartyearh(String startyearh) {
		this.startyearh = startyearh;
	}

	public String getStartmonthh() {
		return startmonthh;
	}

	public void setStartmonthh(String startmonthh) {
		this.startmonthh = startmonthh;
	}

	public String getCurrmonthhff() {
		return currmonthhff;
	}

	public void setCurrmonthhff(String currmonthhff) {
		this.currmonthhff = currmonthhff;
	}

	public String getCurrmonthhhs() {
		return currmonthhhs;
	}

	public void setCurrmonthhhs(String currmonthhhs) {
		this.currmonthhhs = currmonthhhs;
	}

	public String getCurryearhff() {
		return curryearhff;
	}

	public void setCurryearhff(String curryearhff) {
		this.curryearhff = curryearhff;
	}

	public String getCurryearhhs() {
		return curryearhhs;
	}

	public void setCurryearhhs(String curryearhhs) {
		this.curryearhhs = curryearhhs;
	}

	public String getLasth() {
		return lasth;
	}

	public void setLasth(String lasth) {
		this.lasth = lasth;
	}

	public String getKeyoption() {
		return keyoption;
	}

	public void setKeyoption(String keyoption) {
		this.keyoption = keyoption;
	}

	public String getStartyearcnt() {
		return startyearcnt;
	}

	public void setStartyearcnt(String startyearcnt) {
		this.startyearcnt = startyearcnt;
	}

	public String getStartyearamt() {
		return startyearamt;
	}

	public void setStartyearamt(String startyearamt) {
		this.startyearamt = startyearamt;
	}

	public String getStartmonthcnt() {
		return startmonthcnt;
	}

	public void setStartmonthcnt(String startmonthcnt) {
		this.startmonthcnt = startmonthcnt;
	}

	public String getStartmonthamt() {
		return startmonthamt;
	}

	public void setStartmonthamt(String startmonthamt) {
		this.startmonthamt = startmonthamt;
	}

	public String getCurrmonthcntff() {
		return currmonthcntff;
	}

	public void setCurrmonthcntff(String currmonthcntff) {
		this.currmonthcntff = currmonthcntff;
	}

	public String getCurrmonthamtff() {
		return currmonthamtff;
	}

	public void setCurrmonthamtff(String currmonthamtff) {
		this.currmonthamtff = currmonthamtff;
	}

	public String getCurrmonthcnths() {
		return currmonthcnths;
	}

	public void setCurrmonthcnths(String currmonthcnths) {
		this.currmonthcnths = currmonthcnths;
	}

	public String getCurrmonthamths() {
		return currmonthamths;
	}

	public void setCurrmonthamths(String currmonthamths) {
		this.currmonthamths = currmonthamths;
	}

	public String getCurryearcntff() {
		return curryearcntff;
	}

	public void setCurryearcntff(String curryearcntff) {
		this.curryearcntff = curryearcntff;
	}

	public String getCurryearamtff() {
		return curryearamtff;
	}

	public void setCurryearamtff(String curryearamtff) {
		this.curryearamtff = curryearamtff;
	}

	public String getCurryearcnths() {
		return curryearcnths;
	}

	public void setCurryearcnths(String curryearcnths) {
		this.curryearcnths = curryearcnths;
	}

	public String getCurryearamths() {
		return curryearamths;
	}

	public void setCurryearamths(String curryearamths) {
		this.curryearamths = curryearamths;
	}

	public String getLastcnt() {
		return lastcnt;
	}

	public void setLastcnt(String lastcnt) {
		this.lastcnt = lastcnt;
	}

	public String getLastbal() {
		return lastbal;
	}

	public void setLastbal(String lastbal) {
		this.lastbal = lastbal;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

}
