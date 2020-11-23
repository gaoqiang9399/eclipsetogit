package app.component.pss.stock.entity;
import app.base.BaseDomain;
/**
* Title: PssCostAdjustBill.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Aug 17 16:47:36 CST 2017
* @version：1.0
**/
public class PssCostAdjustBill extends BaseDomain {
	private String costAdjustId;//成本调整单据ID
	private String costAdjustNo;//成本调整单据编号
	private String billDate;//单据日期
	private String adjustType;//业务类别
	private Double adjustAmt;//金额
	private String memo;//备注
	private Integer printTimes;//打印次数
	private String regOpNo;//登记人编号
	private String regOpName;//登记人名称
	private String regBrNo;//登记机构编号
	private String regBrName;//登记机构名称
	private String regTime;//登记时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间

	/**
	 * @return 成本调整单据ID
	 */
	public String getCostAdjustId() {
	 	return costAdjustId;
	}
	/**
	 * @设置 成本调整单据ID
	 * @param costAdjustId
	 */
	public void setCostAdjustId(String costAdjustId) {
	 	this.costAdjustId = costAdjustId;
	}
	/**
	 * @return 成本调整单据编号
	 */
	public String getCostAdjustNo() {
	 	return costAdjustNo;
	}
	/**
	 * @设置 成本调整单据编号
	 * @param costAdjustNo
	 */
	public void setCostAdjustNo(String costAdjustNo) {
	 	this.costAdjustNo = costAdjustNo;
	}
	/**
	 * @return 单据日期
	 */
	public String getBillDate() {
	 	return billDate;
	}
	/**
	 * @设置 单据日期
	 * @param billDate
	 */
	public void setBillDate(String billDate) {
	 	this.billDate = billDate;
	}
	/**
	 * @return 业务类别
	 */
	public String getAdjustType() {
	 	return adjustType;
	}
	/**
	 * @设置 业务类别
	 * @param adjustType
	 */
	public void setAdjustType(String adjustType) {
	 	this.adjustType = adjustType;
	}
	/**
	 * @return 金额
	 */
	public Double getAdjustAmt() {
	 	return adjustAmt;
	}
	/**
	 * @设置 金额
	 * @param adjustAmt
	 */
	public void setAdjustAmt(Double adjustAmt) {
	 	this.adjustAmt = adjustAmt;
	}
	/**
	 * @return 备注
	 */
	public String getMemo() {
	 	return memo;
	}
	/**
	 * @设置 备注
	 * @param memo
	 */
	public void setMemo(String memo) {
	 	this.memo = memo;
	}
	/**
	 * @return 打印次数
	 */
	public Integer getPrintTimes() {
	 	return printTimes;
	}
	/**
	 * @设置 打印次数
	 * @param printTimes
	 */
	public void setPrintTimes(Integer printTimes) {
	 	this.printTimes = printTimes;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 登记人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 登记人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 登记机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 登记机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 登记机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}