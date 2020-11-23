package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVoucherMst.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Dec 29 15:49:43 CST 2016
* @version：1.0
**/
public class CwVoucherMst extends BaseDomain implements Cloneable{
	private String uid;//唯一编号
	private String finBooks;//帐套标识
	private String besType;//业务类型
	private String voucherAmt;//凭证金额
	private String remark;//凭证第一条摘要
	private String txcode;//交易代码
	private String prdtNo;//子交易代码用于批量交易代码配置批量分析
	private String businessno;//业务编号
	private String dyCnt;//打印次数
	private String pzProofNo;//凭证字编号
	private String pzTitle;//凭证标题
	private String pzPrefix;//凭证前缀
	private String voucherNoteNo;//凭证字号
	private String voucherNo;//凭证编号
	private String voucherDate;//凭证日期
	private String weeks;//凭证周期
	private String shDate;//审核日期
	private String shTime;//审核时间
	private String shTel;//审核人编号
	private String shTelName;//审核人名称
	private String qzDate;//出纳签字日期
	private String qzTime;//出纳签字时间
	private String qzTel;//出纳签字人编号
	private String qzTelName;//出纳签字人名称
	private String hcDate;//核查日期
	private String hcTime;//核查时间
	private String hcTel;//核查人编号
	private String hcTelName;//核查人名称
	private String jzDate;//记账日期
	private String jzTime;//记账时间
	private String jzTel;//记账人编号
	private String jzTelName;//记账人名称
	private String createDate;//创建日期
	private String createTime;//创建时间
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String jzFlag;//记账标志N：未记账；Y：已记账
	private String pzFlag;//凭证状态0：已制单；1：已审核：2：已签字；3：已核查；4：已记账；5：已作废
	private String voucherSource;//凭证来源0：手工凭证；1：业务凭证；3：结账凭证；4：系统导入凭证；5：冲销凭证
	private String isAuto;//是否自动凭证Y：是；N：否；
	private String counts;//附件数量
	private String isCash;//是否是现金银行类凭证 0：不是；1：是；2：表外类凭证
	private String cashSts;//现金流量分析标志0：不需要分析；1：需要分析（未分析）；2：已分析
	private String isCx;//是否是冲消凭证 0：不是；1：是；2：被冲销凭证
	private String srcVoucherNo;//被冲消凭证编号
	private String isJzpz;//是否是结转凭证Y：是；N：否；
	private String isBc;//是否标错N：正常；Y：已标错
	private String isInvalid;//是否作废Y：是；N：否；
	private String occTime;//时间戳最后一次修改日期

	//列表使用
	private String accNo;// 科目列
	private String drAmt;// 借方金额
	private String crAmt;// 贷方金额
	private String checkAll;// 贷方金额
	
	 @Override
	 public CwVoucherMst clone(){
		 CwVoucherMst o = null;
	        try{
	            o = (CwVoucherMst)super.clone();
	        }catch(CloneNotSupportedException e){
	            e.printStackTrace();
	        }
	        return o;
	    }
	
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
	 * @return 业务类型
	 */
	public String getBesType() {
	 	return besType;
	}
	/**
	 * @设置 业务类型
	 * @param besType
	 */
	public void setBesType(String besType) {
	 	this.besType = besType;
	}
	/**
	 * @return 凭证金额
	 */
	public String getVoucherAmt() {
	 	return voucherAmt;
	}
	/**
	 * @设置 凭证金额
	 * @param voucherAmt
	 */
	public void setVoucherAmt(String voucherAmt) {
	 	this.voucherAmt = voucherAmt;
	}
	/**
	 * @return 凭证第一条摘要
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 凭证第一条摘要
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 交易代码
	 */
	public String getTxcode() {
	 	return txcode;
	}
	/**
	 * @设置 交易代码
	 * @param txcode
	 */
	public void setTxcode(String txcode) {
	 	this.txcode = txcode;
	}
	/**
	 * @return 子交易代码用于批量交易代码配置批量分析
	 */
	public String getPrdtNo() {
	 	return prdtNo;
	}
	/**
	 * @设置 子交易代码用于批量交易代码配置批量分析
	 * @param prdtNo
	 */
	public void setPrdtNo(String prdtNo) {
	 	this.prdtNo = prdtNo;
	}
	/**
	 * @return 业务编号
	 */
	public String getBusinessno() {
	 	return businessno;
	}
	/**
	 * @设置 业务编号
	 * @param businessno
	 */
	public void setBusinessno(String businessno) {
	 	this.businessno = businessno;
	}
	/**
	 * @return 打印次数
	 */
	public String getDyCnt() {
	 	return dyCnt;
	}
	/**
	 * @设置 打印次数
	 * @param dyCnt
	 */
	public void setDyCnt(String dyCnt) {
	 	this.dyCnt = dyCnt;
	}
	/**
	 * @return 凭证字编号
	 */
	public String getPzProofNo() {
	 	return pzProofNo;
	}
	/**
	 * @设置 凭证字编号
	 * @param pzProofNo
	 */
	public void setPzProofNo(String pzProofNo) {
	 	this.pzProofNo = pzProofNo;
	}
	/**
	 * @return 凭证标题
	 */
	public String getPzTitle() {
	 	return pzTitle;
	}
	/**
	 * @设置 凭证标题
	 * @param pzTitle
	 */
	public void setPzTitle(String pzTitle) {
	 	this.pzTitle = pzTitle;
	}
	/**
	 * @return 凭证前缀
	 */
	public String getPzPrefix() {
	 	return pzPrefix;
	}
	/**
	 * @设置 凭证前缀
	 * @param pzPrefix
	 */
	public void setPzPrefix(String pzPrefix) {
	 	this.pzPrefix = pzPrefix;
	}
	/**
	 * @return 凭证字号
	 */
	public String getVoucherNoteNo() {
	 	return voucherNoteNo;
	}
	/**
	 * @设置 凭证字号
	 * @param voucherNoteNo
	 */
	public void setVoucherNoteNo(String voucherNoteNo) {
	 	this.voucherNoteNo = voucherNoteNo;
	}
	/**
	 * @return 凭证编号
	 */
	public String getVoucherNo() {
	 	return voucherNo;
	}
	/**
	 * @设置 凭证编号
	 * @param voucherNo
	 */
	public void setVoucherNo(String voucherNo) {
	 	this.voucherNo = voucherNo;
	}
	/**
	 * @return 凭证日期
	 */
	public String getVoucherDate() {
	 	return voucherDate;
	}
	/**
	 * @设置 凭证日期
	 * @param voucherDate
	 */
	public void setVoucherDate(String voucherDate) {
	 	this.voucherDate = voucherDate;
	}
	/**
	 * @return 凭证周期
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 凭证周期
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
	}
	/**
	 * @return 审核日期
	 */
	public String getShDate() {
	 	return shDate;
	}
	/**
	 * @设置 审核日期
	 * @param shDate
	 */
	public void setShDate(String shDate) {
	 	this.shDate = shDate;
	}
	/**
	 * @return 审核时间
	 */
	public String getShTime() {
	 	return shTime;
	}
	/**
	 * @设置 审核时间
	 * @param shTime
	 */
	public void setShTime(String shTime) {
	 	this.shTime = shTime;
	}
	/**
	 * @return 审核人编号
	 */
	public String getShTel() {
	 	return shTel;
	}
	/**
	 * @设置 审核人编号
	 * @param shTel
	 */
	public void setShTel(String shTel) {
	 	this.shTel = shTel;
	}
	/**
	 * @return 审核人名称
	 */
	public String getShTelName() {
	 	return shTelName;
	}
	/**
	 * @设置 审核人名称
	 * @param shTelName
	 */
	public void setShTelName(String shTelName) {
	 	this.shTelName = shTelName;
	}
	/**
	 * @return 出纳签字日期
	 */
	public String getQzDate() {
	 	return qzDate;
	}
	/**
	 * @设置 出纳签字日期
	 * @param qzDate
	 */
	public void setQzDate(String qzDate) {
	 	this.qzDate = qzDate;
	}
	/**
	 * @return 出纳签字时间
	 */
	public String getQzTime() {
	 	return qzTime;
	}
	/**
	 * @设置 出纳签字时间
	 * @param qzTime
	 */
	public void setQzTime(String qzTime) {
	 	this.qzTime = qzTime;
	}
	/**
	 * @return 出纳签字人编号
	 */
	public String getQzTel() {
	 	return qzTel;
	}
	/**
	 * @设置 出纳签字人编号
	 * @param qzTel
	 */
	public void setQzTel(String qzTel) {
	 	this.qzTel = qzTel;
	}
	/**
	 * @return 出纳签字人名称
	 */
	public String getQzTelName() {
	 	return qzTelName;
	}
	/**
	 * @设置 出纳签字人名称
	 * @param qzTelName
	 */
	public void setQzTelName(String qzTelName) {
	 	this.qzTelName = qzTelName;
	}
	/**
	 * @return 核查日期
	 */
	public String getHcDate() {
	 	return hcDate;
	}
	/**
	 * @设置 核查日期
	 * @param hcDate
	 */
	public void setHcDate(String hcDate) {
	 	this.hcDate = hcDate;
	}
	/**
	 * @return 核查时间
	 */
	public String getHcTime() {
	 	return hcTime;
	}
	/**
	 * @设置 核查时间
	 * @param hcTime
	 */
	public void setHcTime(String hcTime) {
	 	this.hcTime = hcTime;
	}
	/**
	 * @return 核查人编号
	 */
	public String getHcTel() {
	 	return hcTel;
	}
	/**
	 * @设置 核查人编号
	 * @param hcTel
	 */
	public void setHcTel(String hcTel) {
	 	this.hcTel = hcTel;
	}
	/**
	 * @return 核查人名称
	 */
	public String getHcTelName() {
	 	return hcTelName;
	}
	/**
	 * @设置 核查人名称
	 * @param hcTelName
	 */
	public void setHcTelName(String hcTelName) {
	 	this.hcTelName = hcTelName;
	}
	/**
	 * @return 记账日期
	 */
	public String getJzDate() {
	 	return jzDate;
	}
	/**
	 * @设置 记账日期
	 * @param jzDate
	 */
	public void setJzDate(String jzDate) {
	 	this.jzDate = jzDate;
	}
	/**
	 * @return 记账时间
	 */
	public String getJzTime() {
	 	return jzTime;
	}
	/**
	 * @设置 记账时间
	 * @param jzTime
	 */
	public void setJzTime(String jzTime) {
	 	this.jzTime = jzTime;
	}
	/**
	 * @return 记账人编号
	 */
	public String getJzTel() {
	 	return jzTel;
	}
	/**
	 * @设置 记账人编号
	 * @param jzTel
	 */
	public void setJzTel(String jzTel) {
	 	this.jzTel = jzTel;
	}
	/**
	 * @return 记账人名称
	 */
	public String getJzTelName() {
	 	return jzTelName;
	}
	/**
	 * @设置 记账人名称
	 * @param jzTelName
	 */
	public void setJzTelName(String jzTelName) {
	 	this.jzTelName = jzTelName;
	}
	/**
	 * @return 创建日期
	 */
	public String getCreateDate() {
	 	return createDate;
	}
	/**
	 * @设置 创建日期
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
	 	this.createDate = createDate;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 记账标志N：未记账；Y：已记账
	 */
	public String getJzFlag() {
	 	return jzFlag;
	}
	/**
	 * @设置 记账标志N：未记账；Y：已记账
	 * @param jzFlag
	 */
	public void setJzFlag(String jzFlag) {
	 	this.jzFlag = jzFlag;
	}
	/**
	 * @return 凭证状态0：已制单；1：已审核：2：已签字；3：已核查；4：已记账；5：已作废
	 */
	public String getPzFlag() {
	 	return pzFlag;
	}
	/**
	 * @设置 凭证状态0：已制单；1：已审核：2：已签字；3：已核查；4：已记账；5：已作废
	 * @param pzFlag
	 */
	public void setPzFlag(String pzFlag) {
	 	this.pzFlag = pzFlag;
	}
	/**
	 * @return 凭证来源0：手工凭证；1：业务凭证；3：结账凭证；4：系统导入凭证；5：冲销凭证
	 */
	public String getVoucherSource() {
	 	return voucherSource;
	}
	/**
	 * @设置 凭证来源0：手工凭证；1：业务凭证；3：结账凭证；4：系统导入凭证；5：冲销凭证
	 * @param voucherSource
	 */
	public void setVoucherSource(String voucherSource) {
	 	this.voucherSource = voucherSource;
	}
	/**
	 * @return 是否自动凭证Y：是；N：否；
	 */
	public String getIsAuto() {
	 	return isAuto;
	}
	/**
	 * @设置 是否自动凭证Y：是；N：否；
	 * @param isAuto
	 */
	public void setIsAuto(String isAuto) {
	 	this.isAuto = isAuto;
	}
	/**
	 * @return 附件数量
	 */
	public String getCounts() {
	 	return counts;
	}
	/**
	 * @设置 附件数量
	 * @param counts
	 */
	public void setCounts(String counts) {
	 	this.counts = counts;
	}
	/**
	 * @return 是否是现金银行类凭证 0：不是；1：是；2：表外类凭证
	 */
	public String getIsCash() {
	 	return isCash;
	}
	/**
	 * @设置 是否是现金银行类凭证 0：不是；1：是；2：表外类凭证
	 * @param isCash
	 */
	public void setIsCash(String isCash) {
	 	this.isCash = isCash;
	}
	/**
	 * @return 现金流量分析标志0：不需要分析；1：需要分析（未分析）；2：已分析
	 */
	public String getCashSts() {
	 	return cashSts;
	}
	/**
	 * @设置 现金流量分析标志0：不需要分析；1：需要分析（未分析）；2：已分析
	 * @param cashSts
	 */
	public void setCashSts(String cashSts) {
	 	this.cashSts = cashSts;
	}
	/**
	 * @return 是否是冲消凭证 0：不是；1：是；2：被冲销凭证
	 */
	public String getIsCx() {
	 	return isCx;
	}
	/**
	 * @设置 是否是冲消凭证 0：不是；1：是；2：被冲销凭证
	 * @param isCx
	 */
	public void setIsCx(String isCx) {
	 	this.isCx = isCx;
	}
	/**
	 * @return 被冲消凭证编号
	 */
	public String getSrcVoucherNo() {
	 	return srcVoucherNo;
	}
	/**
	 * @设置 被冲消凭证编号
	 * @param srcVoucherNo
	 */
	public void setSrcVoucherNo(String srcVoucherNo) {
	 	this.srcVoucherNo = srcVoucherNo;
	}
	/**
	 * @return 是否是结转凭证Y：是；N：否；
	 */
	public String getIsJzpz() {
	 	return isJzpz;
	}
	/**
	 * @设置 是否是结转凭证Y：是；N：否；
	 * @param isJzpz
	 */
	public void setIsJzpz(String isJzpz) {
	 	this.isJzpz = isJzpz;
	}
	/**
	 * @return 是否标错N：正常；Y：已标错
	 */
	public String getIsBc() {
	 	return isBc;
	}
	/**
	 * @设置 是否标错N：正常；Y：已标错
	 * @param isBc
	 */
	public void setIsBc(String isBc) {
	 	this.isBc = isBc;
	}
	/**
	 * @return 是否作废Y：是；N：否；
	 */
	public String getIsInvalid() {
	 	return isInvalid;
	}
	/**
	 * @设置 是否作废Y：是；N：否；
	 * @param isInvalid
	 */
	public void setIsInvalid(String isInvalid) {
	 	this.isInvalid = isInvalid;
	}
	/**
	 * @return 时间戳最后一次修改日期
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳最后一次修改日期
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
		this.occTime = occTime;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
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

	public String getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}
	
}