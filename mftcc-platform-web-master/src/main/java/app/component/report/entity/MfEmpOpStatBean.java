package app.component.report.entity;

public class MfEmpOpStatBean {
	private String appAmt;
	private String appSts;
	private String pactAmt;
	private String putoutAmt;
	private String brNo;
	private String brName;
	private String cusMngNo;//客户经理编号
	private String cusMngName;//客户经理
	private String manageOpNo;//业务主办编号
	private String manageOpName;//业务主办编号
	private String manageBrNo;//业务主办所属部门编号
	private String manageBrName;//业务主办所属部门名称
	
	  public String getAppAmt(){
	    return appAmt;
	  }
	  public void setAppAmt(String appAmt){
	    this.appAmt=appAmt;
	  }
	  public String getAppSts(){
	    return appSts;
	  }
	  public void setAppSts(String appSts){
	    this.appSts=appSts;
	  }
	  public String getPactAmt(){
	    return pactAmt;
	  }
	  public void setPactAmt(String pactAmt){
	    this.pactAmt=pactAmt;
	  }
	  public String getPutoutAmt(){
	    return putoutAmt;
	  }
	  public void setPutoutAmt(String putoutAmt){
	    this.putoutAmt=putoutAmt;
	  }
	  public String getBrNo(){
	    return brNo;
	  }
	  public void setBrNo(String brNo){
	    this.brNo=brNo;
	  }
	  public String getBrName(){
	    return brName;
	  }
	  public void setBrName(String brName){
	    this.brName=brName;
	  }
	  public String getCusMngNo(){
	    return cusMngNo;
	  }
	  public void setCusMngNo(String cusMngNo){
	    this.cusMngNo=cusMngNo;
	  }
	  public String getCusMngName(){
	    return cusMngName;
	  }
	  public void setCusMngName(String cusMngName){
	    this.cusMngName=cusMngName;
	  }
	public String getManageOpNo() {
		return manageOpNo;
	}
	public void setManageOpNo(String manageOpNo) {
		this.manageOpNo = manageOpNo;
	}
	public String getManageOpName() {
		return manageOpName;
	}
	public void setManageOpName(String manageOpName) {
		this.manageOpName = manageOpName;
	}
	public String getManageBrNo() {
		return manageBrNo;
	}
	public void setManageBrNo(String manageBrNo) {
		this.manageBrNo = manageBrNo;
	}
	public String getManageBrName() {
		return manageBrName;
	}
	public void setManageBrName(String manageBrName) {
		this.manageBrName = manageBrName;
	}

}
