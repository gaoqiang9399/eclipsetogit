package app.component.pact.repay.entity;

public class MfRepaymentBuyBean {
    private String cusNo;//客户号
    private String cusName;//客户名称
    private String appId;//申请号
    private String appName;//项目名称doRepaymentJsp
    private String pactId;//合同编号
    private String pactNo;//合同编号
    private String fincId;//借据编号

    private Double pactAmt;//融资额度（合同金额）
    private Double fincAmt;//本次融资金额（借据金额）
    private Double fincLoanBal;//融资余额（借据余额）
    private Double rateAmt;//应还利息
    private String buyName;//买方名称
    private String buyNo;//买方名称
    private String repaySubject;//付款类型
    private String repayDate;//还款日期
    private Double repayAmt;//还款金额
    private String repayType;//还款方式

    private String fincMainId;//融资申请主表id
    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public Double getPactAmt() {
        return pactAmt;
    }

    public void setPactAmt(Double pactAmt) {
        this.pactAmt = pactAmt;
    }

    public Double getFincAmt() {
        return fincAmt;
    }

    public void setFincAmt(Double fincAmt) {
        this.fincAmt = fincAmt;
    }

    public Double getFincLoanBal() {
        return fincLoanBal;
    }

    public void setFincLoanBal(Double fincLoanBal) {
        this.fincLoanBal = fincLoanBal;
    }

    public Double getRateAmt() {
        return rateAmt;
    }

    public void setRateAmt(Double rateAmt) {
        this.rateAmt = rateAmt;
    }

    public String getBuyName() {
        return buyName;
    }

    public void setBuyName(String buyName) {
        this.buyName = buyName;
    }

    public String getRepaySubject() {
        return repaySubject;
    }

    public void setRepaySubject(String repaySubject) {
        this.repaySubject = repaySubject;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public Double getRepayAmt() {
        return repayAmt;
    }

    public void setRepayAmt(Double repayAmt) {
        this.repayAmt = repayAmt;
    }

    public String getBuyNo() {
        return buyNo;
    }

    public void setBuyNo(String buyNo) {
        this.buyNo = buyNo;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getFincMainId() {
        return fincMainId;
    }

    public void setFincMainId(String fincMainId) {
        this.fincMainId = fincMainId;
    }
}
