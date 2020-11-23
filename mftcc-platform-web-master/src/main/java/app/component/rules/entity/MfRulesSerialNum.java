package app.component.rules.entity;

import app.base.BaseDomain;

/**
 * Title: RulesSerialNum.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Fri Sep 22 15:10:46 CST 2017
 * @version：1.0
 **/
public class MfRulesSerialNum extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String serialNumId;//
	private Integer serialNum;//
	private String noType;//
	private String regTime;//
	private String lstModTime;//
	private String opNo;//

	/**
	 * @return
	 */
	public String getSerialNumId() {
		return serialNumId;
	}

	/**
	 * @设置
	 * @param serialNumId
	 */
	public void setSerialNumId(String serialNumId) {
		this.serialNumId = serialNumId;
	}

	/**
	 * @return
	 */
	public Integer getSerialNum() {
		return serialNum;
	}

	/**
	 * @设置
	 * @param serialNum
	 */
	public void setSerialNum(Integer serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * @return
	 */
	public String getRegTime() {
		return regTime;
	}

	/**
	 * @设置
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	/**
	 * @return
	 */
	public String getOpNo() {
		return opNo;
	}

	/**
	 * @设置
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

	public String getNoType() {
		return noType;
	}

	public void setNoType(String noType) {
		this.noType = noType;
	}

	public String getLstModTime() {
		return lstModTime;
	}

	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
}