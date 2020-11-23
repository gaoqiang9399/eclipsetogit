package app.component.finance.vchout.entity;
import app.base.BaseDomain;
/**
 * Title: CwVoucherDetial.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Fri Dec 30 10:02:05 CST 2016
 * @version：1.0
 **/
public class ExportVchBean extends BaseDomain {
	private String uid;//唯一编号
	private String vchNum;// 凭证的序号
	private String finBooks;// 帐套标识
	private String voucherNo;// 凭证号关联字段cw_voucher_mst.voucher_no
	private String voucherDate;// 凭证日期
	private String pzPrefix;// 凭证前缀
	private String voucherNoteNo;// 凭证字号
	private String weeks;// 凭证周期
	private String years;// 会计年度
	private String term;// 会计期间
	private String counts;// 附件数量
	private String shTel;// 审核人编号
	private String shTelName;// 审核人名称
	private String qzTel;// 出纳签字人编号
	private String qzTelName;// 出纳签字人名称
	private String hcTel;// 核查人编号
	private String hcTelName;// 核查人名称
	private String jzTel;// 记账人编号
	private String jzTelName;// 记账人名称
	private String opNo;// 操作员编号
	private String opName;// 操作员名称

	private String exchRate = "1";// 汇率 默认值为1
	private String zero = "0";// 默认值为0
	private String empty = "";// 默认值为空

	private String dcInd;// 借贷标志1：借；2：贷；
	private String tranceCnt;// 流水笔次
	private String txAmt;// 发生额
	private String txDesc;// 摘要
	private String txDate;// 交易日期
	private String accHrt;// 科目控制字
	private String accNo;// 科目号
	private String accName;// 科目名称
	
	private String drAmt;// 借方发生额
	private String crAmt;// 贷方发生额
	private String curNo;// 币种
	private String curName;// 币种名称
	private String employCode;// 员工核算项目
	private String employCodeName;// 员工核算项目名称
	private String deptCode;// 部门核算项目
	private String deptCodeName;// 部门核算项目名称
	private String customerCode;// 客户核算项目
	private String customerCodeName;// 客户核算项目名称
	private String zdyType;// 自定义核算类别
	private String zdyCode;// 自定义核算项目
	private String zdyCodeName;// 自定义核算项目名称
	
	private String oneCode;//带一个辅助核算使用
	private String oneCodeName;//带一个辅助核算使用


	private String ctInd;// 现转标志该字段不使用
	private String itemsNo;// 辅助核算类编号
	private String itemsName;// 辅助核算类名称
	private String itemsValueNo;// 辅助核算项值编号
	private String itemsValueName;// 辅助核算项值名称
	private String cashType;// 报表项编号
	private String cashTypeName;// 报表项名称
	private String cashType2;// 报表项编号2
	private String cashType2Name;// 报表项名称2
	private String brfsAmt;// 现金流量分析金额
	private String amtNo;// 交易代码编号用于批量交易代码配置批量分析
	private String txRemark;// 备注
	private String occTime;// 时间戳

	private String ext1;// 备用1
	private String ext2;// 备用2
	private String ext3;// 备用3
	private String ext4;// 备用4
	private String ext5;// 备用5
	private String ext6;// 备用6
	private String ext7;// 备用7
	private String ext8;// 备用8
	private String ext9;// 备用9
	private String ext10;// 备用10
	
	
	/**
	 * @return 唯一编号
	 */
	public String getUid() {
	 	return uid;
	}
	/**
	 * @设置 唯一编号
	 * @param uid
	 */
	public void setUid(String uid) {
	 	this.uid = uid;
	}
	/**
	 * @return 帐套标识
	 */
	public String getFinBooks() {
		return finBooks;
	}
	/**
	 * @设置 帐套标识
	 * @param finBooks
	 */
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	/**
	 * @return 凭证号关联字段cw_voucher_mst.voucher_no
	 */
	public String getVoucherNo() {
		return voucherNo;
	}
	/**
	 * @设置 凭证号关联字段cw_voucher_mst.voucher_no
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	/**
	 * @return 币种
	 */
	public String getCurNo() {
		return curNo;
	}
	/**
	 * @设置 币种
	 * @param curNo
	 */
	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}
	/**
	 * @return 交易日期
	 */
	public String getTxDate() {
		return txDate;
	}
	/**
	 * @设置 交易日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	/**
	 * @return 流水笔次
	 */
	public String getTranceCnt() {
		return tranceCnt;
	}
	/**
	 * @设置 流水笔次
	 * @param tranceCnt
	 */
	public void setTranceCnt(String tranceCnt) {
		this.tranceCnt = tranceCnt;
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
	 * @return 借贷标志1：借；2：贷；
	 */
	public String getDcInd() {
		return dcInd;
	}
	/**
	 * @设置 借贷标志1：借；2：贷；
	 * @param dcInd
	 */
	public void setDcInd(String dcInd) {
		this.dcInd = dcInd;
	}
	/**
	 * @return 现转标志该字段不使用
	 */
	public String getCtInd() {
		return ctInd;
	}
	/**
	 * @设置 现转标志该字段不使用
	 * @param ctInd
	 */
	public void setCtInd(String ctInd) {
		this.ctInd = ctInd;
	}
	/**
	 * @return 发生额
	 */
	public String getTxAmt() {
		return txAmt;
	}
	/**
	 * @设置 发生额
	 * @param txAmt
	 */
	public void setTxAmt(String txAmt) {
		this.txAmt = txAmt;
	}
	/**
	 * @return 摘要
	 */
	public String getTxDesc() {
		return txDesc;
	}
	/**
	 * @设置 摘要
	 * @param txDesc
	 */
	public void setTxDesc(String txDesc) {
		this.txDesc = txDesc;
	}
	/**
	 * @return 辅助核算类编号
	 */
	public String getItemsNo() {
		return itemsNo;
	}
	/**
	 * @设置 辅助核算类编号
	 * @param itemsNo
	 */
	public void setItemsNo(String itemsNo) {
		this.itemsNo = itemsNo;
	}
	/**
	 * @return 辅助核算类名称
	 */
	public String getItemsName() {
		return itemsName;
	}
	/**
	 * @设置 辅助核算类名称
	 * @param itemsName
	 */
	public void setItemsName(String itemsName) {
		this.itemsName = itemsName;
	}
	/**
	 * @return 辅助核算项值编号
	 */
	public String getItemsValueNo() {
		return itemsValueNo;
	}
	/**
	 * @设置 辅助核算项值编号
	 * @param itemsValueNo
	 */
	public void setItemsValueNo(String itemsValueNo) {
		this.itemsValueNo = itemsValueNo;
	}
	/**
	 * @return 辅助核算项值名称
	 */
	public String getItemsValueName() {
		return itemsValueName;
	}
	/**
	 * @设置 辅助核算项值名称
	 * @param itemsValueName
	 */
	public void setItemsValueName(String itemsValueName) {
		this.itemsValueName = itemsValueName;
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
	/**
	 * @return 报表项编号2
	 */
	public String getCashType2() {
		return cashType2;
	}
	/**
	 * @设置 报表项编号2
	 * @param cashType2
	 */
	public void setCashType2(String cashType2) {
		this.cashType2 = cashType2;
	}
	/**
	 * @return 报表项名称2
	 */
	public String getCashType2Name() {
		return cashType2Name;
	}
	/**
	 * @设置 报表项名称2
	 * @param cashType2Name
	 */
	public void setCashType2Name(String cashType2Name) {
		this.cashType2Name = cashType2Name;
	}
	/**
	 * @return 现金流量分析金额
	 */
	public String getBrfsAmt() {
		return brfsAmt;
	}
	/**
	 * @设置 现金流量分析金额
	 * @param brfsAmt
	 */
	public void setBrfsAmt(String brfsAmt) {
		this.brfsAmt = brfsAmt;
	}
	/**
	 * @return 交易代码编号用于批量交易代码配置批量分析
	 */
	public String getAmtNo() {
		return amtNo;
	}
	/**
	 * @设置 交易代码编号用于批量交易代码配置批量分析
	 * @param amtNo
	 */
	public void setAmtNo(String amtNo) {
		this.amtNo = amtNo;
	}
	/**
	 * @return 备注
	 */
	public String getTxRemark() {
		return txRemark;
	}
	/**
	 * @设置 备注
	 * @param txRemark
	 */
	public void setTxRemark(String txRemark) {
		this.txRemark = txRemark;
	}
	/**
	 * @return 时间戳
	 */
	public String getOccTime() {
		return occTime;
	}
	/**
	 * @设置 时间戳
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
		this.occTime = occTime;
	}

	/**
	 * @return 备用1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * @设置 备用1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * @return 备用2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * @设置 备用2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * @return 备用3
	 */
	public String getExt3() {
		return ext3;
	}
	/**
	 * @设置 备用3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	/**
	 * @return 备用4
	 */
	public String getExt4() {
		return ext4;
	}
	/**
	 * @设置 备用4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	/**
	 * @return 备用5
	 */
	public String getExt5() {
		return ext5;
	}
	/**
	 * @设置 备用5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	/**
	 * @return 备用6
	 */
	public String getExt6() {
		return ext6;
	}
	/**
	 * @设置 备用6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}
	/**
	 * @return 备用7
	 */
	public String getExt7() {
		return ext7;
	}
	/**
	 * @设置 备用7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}
	/**
	 * @return 备用8
	 */
	public String getExt8() {
		return ext8;
	}
	/**
	 * @设置 备用8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}
	/**
	 * @return 备用9
	 */
	public String getExt9() {
		return ext9;
	}
	/**
	 * @设置 备用9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
		this.ext9 = ext9;
	}
	/**
	 * @return 备用10
	 */
	public String getExt10() {
		return ext10;
	}
	/**
	 * @设置 备用10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
		this.ext10 = ext10;
	}
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	public String getPzPrefix() {
		return pzPrefix;
	}
	public void setPzPrefix(String pzPrefix) {
		this.pzPrefix = pzPrefix;
	}
	public String getVoucherNoteNo() {
		return voucherNoteNo;
	}
	public void setVoucherNoteNo(String voucherNoteNo) {
		this.voucherNoteNo = voucherNoteNo;
	}
	public String getWeeks() {
		return weeks;
	}
	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getShTel() {
		return shTel;
	}
	public void setShTel(String shTel) {
		this.shTel = shTel;
	}
	public String getShTelName() {
		return shTelName;
	}
	public void setShTelName(String shTelName) {
		this.shTelName = shTelName;
	}
	public String getQzTel() {
		return qzTel;
	}
	public void setQzTel(String qzTel) {
		this.qzTel = qzTel;
	}
	public String getQzTelName() {
		return qzTelName;
	}
	public void setQzTelName(String qzTelName) {
		this.qzTelName = qzTelName;
	}
	public String getHcTel() {
		return hcTel;
	}
	public void setHcTel(String hcTel) {
		this.hcTel = hcTel;
	}
	public String getHcTelName() {
		return hcTelName;
	}
	public void setHcTelName(String hcTelName) {
		this.hcTelName = hcTelName;
	}
	public String getJzTel() {
		return jzTel;
	}
	public void setJzTel(String jzTel) {
		this.jzTel = jzTel;
	}
	public String getJzTelName() {
		return jzTelName;
	}
	public void setJzTelName(String jzTelName) {
		this.jzTelName = jzTelName;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getExchRate() {
		return exchRate;
	}
	public void setExchRate(String exchRate) {
		this.exchRate = exchRate;
	}
	public String getZero() {
		return zero;
	}
	public void setZero(String zero) {
		this.zero = zero;
	}
	public String getEmpty() {
		return empty;
	}
	public void setEmpty(String empty) {
		this.empty = empty;
	}
	public String getDrAmt() {
		return drAmt;
	}
	public void setDrAmt(String drAmt) {
		this.drAmt = drAmt;
	}
	public String getCrAmt() {
		return crAmt;
	}
	public void setCrAmt(String crAmt) {
		this.crAmt = crAmt;
	}
	public String getCurName() {
		return curName;
	}
	public void setCurName(String curName) {
		this.curName = curName;
	}
	public String getEmployCode() {
		return employCode;
	}
	public void setEmployCode(String employCode) {
		this.employCode = employCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getZdyType() {
		return zdyType;
	}
	public void setZdyType(String zdyType) {
		this.zdyType = zdyType;
	}
	public String getZdyCode() {
		return zdyCode;
	}
	public void setZdyCode(String zdyCode) {
		this.zdyCode = zdyCode;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getVchNum() {
		return vchNum;
	}
	public void setVchNum(String vchNum) {
		this.vchNum = vchNum;
	}
	public String getEmployCodeName() {
		return employCodeName;
	}
	public void setEmployCodeName(String employCodeName) {
		this.employCodeName = employCodeName;
	}
	public String getDeptCodeName() {
		return deptCodeName;
	}
	public void setDeptCodeName(String deptCodeName) {
		this.deptCodeName = deptCodeName;
	}
	public String getCustomerCodeName() {
		return customerCodeName;
	}
	public void setCustomerCodeName(String customerCodeName) {
		this.customerCodeName = customerCodeName;
	}
	public String getZdyCodeName() {
		return zdyCodeName;
	}
	public void setZdyCodeName(String zdyCodeName) {
		this.zdyCodeName = zdyCodeName;
	}
	public String getOneCode() {
		return oneCode;
	}
	public void setOneCode(String oneCode) {
		this.oneCode = oneCode;
	}
	public String getOneCodeName() {
		return oneCodeName;
	}
	public void setOneCodeName(String oneCodeName) {
		this.oneCodeName = oneCodeName;
	}

}