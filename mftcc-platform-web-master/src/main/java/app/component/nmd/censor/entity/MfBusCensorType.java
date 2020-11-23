package app.component.nmd.censor.entity;

import app.base.BaseDomain;

public class MfBusCensorType extends BaseDomain{
	private String censorNo;
	private String censorName;
	public String getCensorNo() {
		return censorNo;
	}
	public void setCensorNo(String censorNo) {
		this.censorNo = censorNo;
	}
	public String getCensorName() {
		return censorName;
	}
	public void setCensorName(String censorName) {
		this.censorName = censorName;
	}
}