package app.component.finance.account.entity;
import app.base.BaseDomain;
/**
* Title: CwComItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Dec 26 15:52:06 CST 2016
* @version：1.0
**/
public class CwComItem extends BaseDomain {
	
	private String finBooks;//帐套标识
	private String accNo;//科目号
	private String accHrt;//科目控制字
	private String accHrtCod;//科目简码
	private String appCod;//应用代码
	private String upAccHrt;//上级科目控制字
	private String accName;//科目名名称
	private String showName;//科目显示的名称
	private String subAccYn;//子科目标志N：没有下级科目；Y：有下级科目
	private String accLvl;//科目级别1：一级科目；2：二级科目；3：三级科目
	private String dcInd;//借贷标志1：借；2：贷；
	private String seqCtl;//是否存在辅助核算项N：否 Y：是
	private String seqNum;//辅助核算编号对应 cw_fication.type 以@隔开
	private String rollInd;//轧差标志N：不轧；Y：轧差
	private String accKnd;//科目类别1：流动资产；2：非流动资产；3：流动负债；4：非流动负债；5：所有者权益；6：成本；7：营业收入；8：其他收益；9：期间费用；10：其他损失；11：营业成本及税金；12：以前年度损益调整；13：所得税；14：表外科目；
	private String foreInd;//本外币标志该字段暂时不用
	private String equaInd;//平衡标识该字段暂时不用
	private String amtDcInd;//发生额方向该字段暂时不用
	private String odInd;//透支标志Y：允许透支；N：不允许透支；
	private String inInd;//内部科目标志D：存款帐；L：贷款账；I：内部帐；A：考核账；默认为I；
	private String scope;//开设范围该字段暂时不用
	private Double drTxAmt;//借方科目使用额
	private Double crTxAmt;//贷方科目使用额
	private Double drTxCnt;//借方使用次数
	private Double crTxCnt;//贷方使用次数
	private Double drLimitAmt;//借方限额
	private Double crLimitAmt;//贷方限额
	private String brNo;//所属部门
	private String accInd;//科目标志该字段暂时不用
	private String usys;//使用系统
	private String brf;//备注
	private String accType;//科目类型1：资产类；2：负债类；3：共同类；4：所有者权益类；5：成本类；6：损益类；7：表外类
	private String xjFl;//现金分类1：现金类；2：银行类；3：现金及等价物；4：其他；
	private String corbind;//明细对应科目用于导入导出
	private String iscash;//是否是现金流量项目用于导入导出
	private String opNo;//操作员
	private String opName;//操作员名称
	private String occDate;//时间戳

	private String upAccno;//上级科目号，数据库没有此字段
	private String showAccLvl;//展示中文的科目，数据库没有此字段
	private String accLength;//科目编码类型,数据库没有此字段
	private String accNewno;//调整后科目编号,数据库没有此字段
	private String seqSelect;//自定义的辅助号，数据库没有此字段
	private String pvalueType;//科目编码格式类型，数据库没有此字段，0:4-2-2,1：4-3-3,2， 4-4-4,3：4-5-5
	private String accLvlName;//科目级别名称 ，数据库没有此字段
	private String copyFinBooks;//复制帐套
	
	//凭证导出对应关系使用
	private String corrType;//关系类型（0 辅助核算；1：凭证字；2 现金流量分析；9：临时科目）
	private String itemNo;//辅助项编号
	private String itemName;//辅助项名称
	private String corrNo;//关系编号
	private String corrName;//关系名称
	
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
	 * @return 科目号
	 */
	public String getAccNo() {
	 	return accNo;
	}
	/**
	 * @设置 科目号
	 * @param accNo
	 */
	public void setAccNo(String accNo) {
	 	this.accNo = accNo;
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
	 * @return 科目简码
	 */
	public String getAccHrtCod() {
	 	return accHrtCod;
	}
	/**
	 * @设置 科目简码
	 * @param accHrtCod
	 */
	public void setAccHrtCod(String accHrtCod) {
	 	this.accHrtCod = accHrtCod;
	}
	/**
	 * @return 应用代码
	 */
	public String getAppCod() {
	 	return appCod;
	}
	/**
	 * @设置 应用代码
	 * @param appCod
	 */
	public void setAppCod(String appCod) {
	 	this.appCod = appCod;
	}
	/**
	 * @return 上级科目控制字
	 */
	public String getUpAccHrt() {
	 	return upAccHrt;
	}
	/**
	 * @设置 上级科目控制字
	 * @param upAccHrt
	 */
	public void setUpAccHrt(String upAccHrt) {
	 	this.upAccHrt = upAccHrt;
	}
	/**
	 * @return 科目名名称
	 */
	public String getAccName() {
	 	return accName;
	}
	/**
	 * @设置 科目名名称
	 * @param accName
	 */
	public void setAccName(String accName) {
	 	this.accName = accName;
	}
	/**
	 * @return 科目显示的名称
	 */
	public String getShowName() {
	 	return showName;
	}
	/**
	 * @设置 科目显示的名称
	 * @param showName
	 */
	public void setShowName(String showName) {
	 	this.showName = showName;
	}
	/**
	 * @return 子科目标志N：没有下级科目；Y：有下级科目
	 */
	public String getSubAccYn() {
	 	return subAccYn;
	}
	/**
	 * @设置 子科目标志N：没有下级科目；Y：有下级科目
	 * @param subAccYn
	 */
	public void setSubAccYn(String subAccYn) {
	 	this.subAccYn = subAccYn;
	}
	/**
	 * @return 科目级别1：一级科目；2：二级科目；3：三级科目
	 */
	public String getAccLvl() {
	 	return accLvl;
	}
	/**
	 * @设置 科目级别1：一级科目；2：二级科目；3：三级科目
	 * @param accLvl
	 */
	public void setAccLvl(String accLvl) {
	 	this.accLvl = accLvl;
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
	 * @return 是否存在辅助核算项N：否 Y：是
	 */
	public String getSeqCtl() {
	 	return seqCtl;
	}
	/**
	 * @设置 是否存在辅助核算项N：否 Y：是
	 * @param seqCtl
	 */
	public void setSeqCtl(String seqCtl) {
	 	this.seqCtl = seqCtl;
	}
	/**
	 * @return 辅助核算编号对应 cw_fication.type 以@隔开
	 */
	public String getSeqNum() {
	 	return seqNum;
	}
	/**
	 * @设置 辅助核算编号对应 cw_fication.type 以@隔开
	 * @param seqNum
	 */
	public void setSeqNum(String seqNum) {
	 	this.seqNum = seqNum;
	}
	/**
	 * @return 轧差标志N：不轧；Y：轧差
	 */
	public String getRollInd() {
	 	return rollInd;
	}
	/**
	 * @设置 轧差标志N：不轧；Y：轧差
	 * @param rollInd
	 */
	public void setRollInd(String rollInd) {
	 	this.rollInd = rollInd;
	}
	/**
	 * @return 科目类别1：流动资产；2：非流动资产；3：流动负债；4：非流动负债；5：所有者权益；6：成本；7：营业收入；8：其他收益；9：期间费用；10：其他损失；11：营业成本及税金；12：以前年度损益调整；13：所得税；14：表外科目；
	 */
	public String getAccKnd() {
	 	return accKnd;
	}
	/**
	 * @设置 科目类别1：流动资产；2：非流动资产；3：流动负债；4：非流动负债；5：所有者权益；6：成本；7：营业收入；8：其他收益；9：期间费用；10：其他损失；11：营业成本及税金；12：以前年度损益调整；13：所得税；14：表外科目；
	 * @param accKnd
	 */
	public void setAccKnd(String accKnd) {
	 	this.accKnd = accKnd;
	}
	/**
	 * @return 本外币标志该字段暂时不用
	 */
	public String getForeInd() {
	 	return foreInd;
	}
	/**
	 * @设置 本外币标志该字段暂时不用
	 * @param foreInd
	 */
	public void setForeInd(String foreInd) {
	 	this.foreInd = foreInd;
	}
	/**
	 * @return 平衡标识该字段暂时不用
	 */
	public String getEquaInd() {
	 	return equaInd;
	}
	/**
	 * @设置 平衡标识该字段暂时不用
	 * @param equaInd
	 */
	public void setEquaInd(String equaInd) {
	 	this.equaInd = equaInd;
	}
	/**
	 * @return 发生额方向该字段暂时不用
	 */
	public String getAmtDcInd() {
	 	return amtDcInd;
	}
	/**
	 * @设置 发生额方向该字段暂时不用
	 * @param amtDcInd
	 */
	public void setAmtDcInd(String amtDcInd) {
	 	this.amtDcInd = amtDcInd;
	}
	/**
	 * @return 透支标志Y：允许透支；N：不允许透支；
	 */
	public String getOdInd() {
	 	return odInd;
	}
	/**
	 * @设置 透支标志Y：允许透支；N：不允许透支；
	 * @param odInd
	 */
	public void setOdInd(String odInd) {
	 	this.odInd = odInd;
	}
	/**
	 * @return 内部科目标志D：存款帐；L：贷款账；I：内部帐；A：考核账；默认为I；
	 */
	public String getInInd() {
	 	return inInd;
	}
	/**
	 * @设置 内部科目标志D：存款帐；L：贷款账；I：内部帐；A：考核账；默认为I；
	 * @param inInd
	 */
	public void setInInd(String inInd) {
	 	this.inInd = inInd;
	}
	/**
	 * @return 开设范围该字段暂时不用
	 */
	public String getScope() {
	 	return scope;
	}
	/**
	 * @设置 开设范围该字段暂时不用
	 * @param scope
	 */
	public void setScope(String scope) {
	 	this.scope = scope;
	}
	/**
	 * @return 借方科目使用额
	 */
	public Double getDrTxAmt() {
	 	return drTxAmt;
	}
	/**
	 * @设置 借方科目使用额
	 * @param drTxAmt
	 */
	public void setDrTxAmt(Double drTxAmt) {
	 	this.drTxAmt = drTxAmt;
	}
	/**
	 * @return 贷方科目使用额
	 */
	public Double getCrTxAmt() {
	 	return crTxAmt;
	}
	/**
	 * @设置 贷方科目使用额
	 * @param crTxAmt
	 */
	public void setCrTxAmt(Double crTxAmt) {
	 	this.crTxAmt = crTxAmt;
	}
	/**
	 * @return 借方使用次数
	 */
	public Double getDrTxCnt() {
	 	return drTxCnt;
	}
	/**
	 * @设置 借方使用次数
	 * @param drTxCnt
	 */
	public void setDrTxCnt(Double drTxCnt) {
	 	this.drTxCnt = drTxCnt;
	}
	/**
	 * @return 贷方使用次数
	 */
	public Double getCrTxCnt() {
	 	return crTxCnt;
	}
	/**
	 * @设置 贷方使用次数
	 * @param crTxCnt
	 */
	public void setCrTxCnt(Double crTxCnt) {
	 	this.crTxCnt = crTxCnt;
	}
	/**
	 * @return 借方限额
	 */
	public Double getDrLimitAmt() {
	 	return drLimitAmt;
	}
	/**
	 * @设置 借方限额
	 * @param drLimitAmt
	 */
	public void setDrLimitAmt(Double drLimitAmt) {
	 	this.drLimitAmt = drLimitAmt;
	}
	/**
	 * @return 贷方限额
	 */
	public Double getCrLimitAmt() {
	 	return crLimitAmt;
	}
	/**
	 * @设置 贷方限额
	 * @param crLimitAmt
	 */
	public void setCrLimitAmt(Double crLimitAmt) {
	 	this.crLimitAmt = crLimitAmt;
	}
	/**
	 * @return 所属部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 所属部门
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 科目标志该字段暂时不用
	 */
	public String getAccInd() {
	 	return accInd;
	}
	/**
	 * @设置 科目标志该字段暂时不用
	 * @param accInd
	 */
	public void setAccInd(String accInd) {
	 	this.accInd = accInd;
	}
	/**
	 * @return 使用系统
	 */
	public String getUsys() {
	 	return usys;
	}
	/**
	 * @设置 使用系统
	 * @param uSys
	 */
	public void setUsys(String usys) {
	 	this.usys = usys;
	}
	/**
	 * @return 备注
	 */
	public String getBrf() {
	 	return brf;
	}
	/**
	 * @设置 备注
	 * @param brf
	 */
	public void setBrf(String brf) {
	 	this.brf = brf;
	}
	/**
	 * @return 科目类型1：资产类；2：负债类；3：共同类；4：所有者权益类；5：成本类；6：损益类；7：表外类
	 */
	public String getAccType() {
	 	return accType;
	}
	/**
	 * @设置 科目类型1：资产类；2：负债类；3：共同类；4：所有者权益类；5：成本类；6：损益类；7：表外类
	 * @param accType
	 */
	public void setAccType(String accType) {
	 	this.accType = accType;
	}
	/**
	 * @return 现金分类1：现金类；2：银行类；3：现金及等价物；4：其他；
	 */
	public String getXjFl() {
	 	return xjFl;
	}
	/**
	 * @设置 现金分类1：现金类；2：银行类；3：现金及等价物；4：其他；
	 * @param xjFl
	 */
	public void setXjFl(String xjFl) {
	 	this.xjFl = xjFl;
	}
	/**
	 * @return 明细对应科目用于导入导出
	 */
	public String getCorbind() {
	 	return corbind;
	}
	/**
	 * @设置 明细对应科目用于导入导出
	 * @param corbind
	 */
	public void setCorbind(String corbind) {
	 	this.corbind = corbind;
	}
	/**
	 * @return 是否是现金流量项目用于导入导出
	 */
	public String getIscash() {
	 	return iscash;
	}
	/**
	 * @设置 是否是现金流量项目用于导入导出
	 * @param iscash
	 */
	public void setIscash(String iscash) {
	 	this.iscash = iscash;
	}
	/**
	 * @return 操作员
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员
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
	 * @return 时间戳
	 */
	public String getOccDate() {
	 	return occDate;
	}
	/**
	 * @设置 时间戳
	 * @param occDate
	 */
	public void setOccDate(String occDate) {
	 	this.occDate = occDate;
	}
	/**
	 * @设置  上级科目号
	 * @param upAccno
	 */
	public String getUpAccno() {
		return upAccno;
	}
	/**
	 * @设置 上级科目号
	 * @param upAccno
	 */
	public void setUpAccno(String upAccno) {
		this.upAccno = upAccno;
	}
	public String getShowAccLvl() {
		return showAccLvl;
	}
	public void setShowAccLvl(String showAccLvl) {
		this.showAccLvl = showAccLvl;
	}
	public String getAccLength() {
		return accLength;
	}
	public void setAccLength(String accLength) {
		this.accLength = accLength;
	}
	public String getAccNewno() {
		return accNewno;
	}
	public void setAccNewno(String accNewno) {
		this.accNewno = accNewno;
	}
	public String getSeqSelect() {
		return seqSelect;
	}
	public void setSeqSelect(String seqSelect) {
		this.seqSelect = seqSelect;
	}
	public String getPvalueType() {
		return pvalueType;
	}
	public void setPvalueType(String pvalueType) {
		this.pvalueType = pvalueType;
	}
	public String getAccLvlName() {
		return accLvlName;
	}
	public void setAccLvlName(String accLvlName) {
		this.accLvlName = accLvlName;
	}
	public String getCopyFinBooks() {
		return copyFinBooks;
	}
	public void setCopyFinBooks(String copyFinBooks) {
		this.copyFinBooks = copyFinBooks;
	}
	public String getCorrType() {
		return corrType;
	}
	public void setCorrType(String corrType) {
		this.corrType = corrType;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCorrNo() {
		return corrNo;
	}
	public void setCorrNo(String corrNo) {
		this.corrNo = corrNo;
	}
	public String getCorrName() {
		return corrName;
	}
	public void setCorrName(String corrName) {
		this.corrName = corrName;
	}
	
	
}