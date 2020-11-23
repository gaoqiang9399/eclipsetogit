package app.base;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SessionAttr {
	private String regNo;
	private String regName;
	private String orgNo;
	private String orgName;
	private String sysDate;
	private String []roleNo;
	private String regJob;
	@JsonIgnore
	private Map<String,Map<String, String>> funNoType;
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getSysDate() {
		return sysDate;
	}
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}
	public String[] getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo[]) {
		this.roleNo = roleNo;
	}
	public String getRegJob() {
		return regJob;
	}
	public void setRegJob(String regJob) {
		this.regJob = regJob;
	}
	public Map<String,Map<String, String>> getFunNoType() {
		return funNoType;
	}
	@JsonIgnore
	public void setFunNoType(Map<String,Map<String, String>> funNoType) {
		if(funNoType==null || funNoType.isEmpty()) {
			funNoType = new HashMap<String,Map<String, String>>();
			Map<String, String> handlerNull  = new HashMap<String, String>();
			handlerNull.put("handlerNull", "handlerNull");
			funNoType.put("handlerNull", handlerNull);
		}
		this.funNoType = funNoType;
	}
}
