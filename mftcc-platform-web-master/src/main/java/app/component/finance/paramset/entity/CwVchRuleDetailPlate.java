package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwVchRuleDetailPlate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Mar 09 20:13:26 CST 2017
* @version：1.0
**/
public class CwVchRuleDetailPlate extends BaseDomain {
	private String uuid;//uuiduuid
	private String plateNo;//模版编号对应cw_vch_rule_mst_plate.plate_no
	private Double traceCnt;//流水笔次
	private String accHrt;//科目控制字
	private String dcInd;//借贷标识1：借；2：贷；
	private String amtNo;//分录编码
	private String txDesc;//分录描述
	private String txRemark;//摘要
	private String kmSts;//现金银行分类01：其他；02：现金银行
	private String isCashAuto;//是否可以现金流量配置0：不需要；1需要
	private String cashType;//报表项编号
	private String cashTypeName;//报表项名称
	private String finBooks;//帐套标识

	private String txCode;//交易代码，数据库中没有此字段
	/**
	 * @return uuiduuid
	 */
	public String getUuid() {
	 	return uuid;
	}
	/**
	 * @设置 uuiduuid
	 * @param uuid
	 */
	public void setUuid(String uuid) {
	 	this.uuid = uuid;
	}
	/**
	 * @return 模版编号对应cw_vch_rule_mst_plate.plate_no
	 */
	public String getPlateNo() {
	 	return plateNo;
	}
	/**
	 * @设置 模版编号对应cw_vch_rule_mst_plate.plate_no
	 * @param plateNo
	 */
	public void setPlateNo(String plateNo) {
	 	this.plateNo = plateNo;
	}
	/**
	 * @return 流水笔次
	 */
	public Double getTraceCnt() {
	 	return traceCnt;
	}
	/**
	 * @设置 流水笔次
	 * @param traceCnt
	 */
	public void setTraceCnt(Double traceCnt) {
	 	this.traceCnt = traceCnt;
	}
	/**
	 * @return 科目控制字
	 */
	public String getAccHrt() {
	 	return accHrt;
	}
	/**
	 * @设置 科目控制字
	 * @param accHrt
	 */
	public void setAccHrt(String accHrt) {
	 	this.accHrt = accHrt;
	}
	/**
	 * @return 借贷标识1：借；2：贷；
	 */
	public String getDcInd() {
	 	return dcInd;
	}
	/**
	 * @设置 借贷标识1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
	 	this.dcInd = dcInd;
	}
	/**
	 * @return 分录编码
	 */
	public String getAmtNo() {
	 	return amtNo;
	}
	/**
	 * @设置 分录编码
	 * @param amtNo
	 */
	public void setAmtNo(String amtNo) {
	 	this.amtNo = amtNo;
	}
	/**
	 * @return 分录描述
	 */
	public String getTxDesc() {
	 	return txDesc;
	}
	/**
	 * @设置 分录描述
	 * @param txDesc
	 */
	public void setTxDesc(String txDesc) {
	 	this.txDesc = txDesc;
	}
	/**
	 * @return 摘要
	 */
	public String getTxRemark() {
	 	return txRemark;
	}
	/**
	 * @设置 摘要
	 * @param txRemark
	 */
	public void setTxRemark(String txRemark) {
	 	this.txRemark = txRemark;
	}
	/**
	 * @return 现金银行分类01：其他；02：现金银行
	 */
	public String getKmSts() {
	 	return kmSts;
	}
	/**
	 * @设置 现金银行分类01：其他；02：现金银行
	 * @param kmSts
	 */
	public void setKmSts(String kmSts) {
	 	this.kmSts = kmSts;
	}
	/**
	 * @return 是否可以现金流量配置0：不需要；1需要
	 */
	public String getIsCashAuto() {
	 	return isCashAuto;
	}
	/**
	 * @设置 是否可以现金流量配置0：不需要；1需要
	 * @param isCashAuto
	 */
	public void setIsCashAuto(String isCashAuto) {
	 	this.isCashAuto = isCashAuto;
	}
	/**
	 * @return 报表项编号
	 */
	public String getCashType() {
	 	return cashType;
	}
	/**
	 * @设置 报表项编号
	 * @param cashType
	 */
	public void setCashType(String cashType) {
	 	this.cashType = cashType;
	}
	/**
	 * @return 报表项名称
	 */
	public String getCashTypeName() {
	 	return cashTypeName;
	}
	/**
	 * @设置 报表项名称
	 * @param cashTypeName
	 */
	public void setCashTypeName(String cashTypeName) {
	 	this.cashTypeName = cashTypeName;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
}