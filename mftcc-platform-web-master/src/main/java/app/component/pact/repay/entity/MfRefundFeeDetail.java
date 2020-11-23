package app.component.pact.repay.entity;
import app.base.BaseDomain;
/**
* Title: MfDeductRefundApply.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Aug 22 11:24:01 CST 2017
* @version：1.0
**/
public class MfRefundFeeDetail extends BaseDomain {
	private String id;//减免/退费唯一ID
	private String appId;//融资业务申请编号
	private String pactId;//合同号
	private String pactNo;//合同展示号
	private String fincId;//放款申请号
    private Double feeAmt;//费用
    private String feeAmtName;//费用名称（费用项名称）
    private String itemNo;//费用项编号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	public String getPactNo() {
		return pactNo;
	}
	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public Double getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getFeeAmtName() {
		return feeAmtName;
	}
	public void setFeeAmtName(String feeAmtName) {
		this.feeAmtName = feeAmtName;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
}