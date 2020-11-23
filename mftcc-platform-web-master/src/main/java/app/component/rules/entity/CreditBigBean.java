package app.component.rules.entity;

import java.util.Map;

/**
 * <p>Title:CreditBigBean.java</p>
 * <p>Description:授信业务申请前置条件检查</p>
 * @author LJW
 * @date 2017-3-13 上午9:21:12
 */
public class CreditBigBean {

	private String baseInfoStatus;    //检查基本信息情况
	private String cwReportStatus;    //检查财务报表填写情况
	private String rateStatus;		  //检查评级情况
	
	//获得结果
	private Map<String,Object> resultMap;
	
	public String getBaseInfoStatus() {
		return baseInfoStatus;
	}
	public void setBaseInfoStatus(String baseInfoStatus) {
		this.baseInfoStatus = baseInfoStatus;
	}
	public String getCwReportStatus() {
		return cwReportStatus;
	}
	public void setCwReportStatus(String cwReportStatus) {
		this.cwReportStatus = cwReportStatus;
	}
	public String getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
}
