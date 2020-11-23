package app.component.accnt.entity;
import app.base.BaseDomain;
/**
* Title: MfAccntTransferHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 27 09:20:34 CST 2016
* @version：1.0
**/
public class MfAccntTransferHis extends BaseDomain {
	private String id;//唯一id
	private String transferId;//
	private String pactNo;//合同展示号
	private String pactId;//合同号
	private Double pactAccntAmt;//合同应收账款金额
	private String cusNoBuy;//
	private String cusNameBuy;//
	private String cusNoSell;//
	private String cusNameSell;//
	private String endDateAccnt;//单据到期日
	private Integer transferCount;//转让笔数
	private String transferNum;//批次
	private String paperNo;//票据号码
	private Double transferAmt;//转让金额
	private Double repayedTransferAmt;//还款金额
	private Double transferBal;//未还金额
	private String passTime;//审核通过时间
	private String lastReturnDate;//上次还款日期
	private String finishDate;//还完日期
	private String transferSts;//状态1未提交2审核中3审核通过4欠款5部分还款6已还款未复核7完结
	private String disputeSts;//商纠状态0无商纠1有商纠
	private Double disputeAmt;//商纠金额
	private String flawSts;//是否瑕疵
	private String flawReasion;//瑕疵原因
	private String flawDeal;//瑕疵处理
	private Double adjustAmt;//账款调整金额
	private String remark;//备注
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regTime;//
	private String appTime;//提交申请时间
	private String approveProcessId;//审批流程id
	private String approveProcess;//审批流程
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String lstModeTime;//最近修改时间
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//

	/**
	 * @return 唯一id
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 唯一id
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 
	 */
	public String getTransferId() {
	 	return transferId;
	}
	/**
	 * @设置 
	 * @param transferId
	 */
	public void setTransferId(String transferId) {
	 	this.transferId = transferId;
	}
	/**
	 * @return 合同展示号
	 */
	public String getPactNo() {
	 	return pactNo;
	}
	/**
	 * @设置 合同展示号
	 * @param pactNo
	 */
	public void setPactNo(String pactNo) {
	 	this.pactNo = pactNo;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 * 
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 合同应收账款金额
	 */
	public Double getPactAccntAmt() {
	 	return pactAccntAmt;
	}
	/**
	 * @设置 合同应收账款金额
	 * @param pactAccntAmt
	 */
	public void setPactAccntAmt(Double pactAccntAmt) {
	 	this.pactAccntAmt = pactAccntAmt;
	}
	/**
	 * @return 
	 */
	public String getCusNoBuy() {
	 	return cusNoBuy;
	}
	/**
	 * @设置 
	 * @param cusNoBuy
	 */
	public void setCusNoBuy(String cusNoBuy) {
	 	this.cusNoBuy = cusNoBuy;
	}
	/**
	 * @return 
	 */
	public String getCusNameBuy() {
	 	return cusNameBuy;
	}
	/**
	 * @设置 
	 * @param cusNameBuy
	 */
	public void setCusNameBuy(String cusNameBuy) {
	 	this.cusNameBuy = cusNameBuy;
	}
	/**
	 * @return 
	 */
	public String getCusNoSell() {
	 	return cusNoSell;
	}
	/**
	 * @设置 
	 * @param cusNoSell
	 */
	public void setCusNoSell(String cusNoSell) {
	 	this.cusNoSell = cusNoSell;
	}
	/**
	 * @return 
	 */
	public String getCusNameSell() {
	 	return cusNameSell;
	}
	/**
	 * @设置 
	 * @param cusNameSell
	 */
	public void setCusNameSell(String cusNameSell) {
	 	this.cusNameSell = cusNameSell;
	}
	/**
	 * @return 单据到期日
	 */
	public String getEndDateAccnt() {
	 	return endDateAccnt;
	}
	/**
	 * @设置 单据到期日
	 * @param endDateAccnt
	 */
	public void setEndDateAccnt(String endDateAccnt) {
	 	this.endDateAccnt = endDateAccnt;
	}
	/**
	 * @return 转让笔数
	 */
	public Integer getTransferCount() {
	 	return transferCount;
	}
	/**
	 * @设置 转让笔数
	 * @param transferCount
	 */
	public void setTransferCount(Integer transferCount) {
	 	this.transferCount = transferCount;
	}
	/**
	 * @return 批次
	 */
	public String getTransferNum() {
	 	return transferNum;
	}
	/**
	 * @设置 批次
	 * @param transferNum
	 */
	public void setTransferNum(String transferNum) {
	 	this.transferNum = transferNum;
	}
	/**
	 * @return 票据号码
	 */
	public String getPaperNo() {
	 	return paperNo;
	}
	/**
	 * @设置 票据号码
	 * @param paperNo
	 */
	public void setPaperNo(String paperNo) {
	 	this.paperNo = paperNo;
	}
	/**
	 * @return 转让金额
	 */
	public Double getTransferAmt() {
	 	return transferAmt;
	}
	/**
	 * @设置 转让金额
	 * @param transferAmt
	 */
	public void setTransferAmt(Double transferAmt) {
	 	this.transferAmt = transferAmt;
	}
	/**
	 * @return 还款金额
	 */
	public Double getRepayedTransferAmt() {
	 	return repayedTransferAmt;
	}
	/**
	 * @设置 还款金额
	 * @param repayedTransferAmt
	 */
	public void setRepayedTransferAmt(Double repayedTransferAmt) {
	 	this.repayedTransferAmt = repayedTransferAmt;
	}
	/**
	 * @return 未还金额
	 */
	public Double getTransferBal() {
	 	return transferBal;
	}
	/**
	 * @设置 未还金额
	 * @param transferBal
	 */
	public void setTransferBal(Double transferBal) {
	 	this.transferBal = transferBal;
	}
	/**
	 * @return 审核通过时间
	 */
	public String getPassTime() {
	 	return passTime;
	}
	/**
	 * @设置 审核通过时间
	 * @param passTime
	 */
	public void setPassTime(String passTime) {
	 	this.passTime = passTime;
	}
	/**
	 * @return 上次还款日期
	 */
	public String getLastReturnDate() {
	 	return lastReturnDate;
	}
	/**
	 * @设置 上次还款日期
	 * @param lastReturnDate
	 */
	public void setLastReturnDate(String lastReturnDate) {
	 	this.lastReturnDate = lastReturnDate;
	}
	/**
	 * @return 还完日期
	 */
	public String getFinishDate() {
	 	return finishDate;
	}
	/**
	 * @设置 还完日期
	 * @param finishDate
	 */
	public void setFinishDate(String finishDate) {
	 	this.finishDate = finishDate;
	}
	/**
	 * @return 状态1未提交2审核中3审核通过4欠款5部分还款6已还款未复核7完结
	 */
	public String getTransferSts() {
	 	return transferSts;
	}
	/**
	 * @设置 状态1未提交2审核中3审核通过4欠款5部分还款6已还款未复核7完结
	 * @param transferSts
	 */
	public void setTransferSts(String transferSts) {
	 	this.transferSts = transferSts;
	}
	/**
	 * @return 商纠状态0无商纠1有商纠
	 */
	public String getDisputeSts() {
	 	return disputeSts;
	}
	/**
	 * @设置 商纠状态0无商纠1有商纠
	 * @param disputeSts
	 */
	public void setDisputeSts(String disputeSts) {
	 	this.disputeSts = disputeSts;
	}
	/**
	 * @return 商纠金额
	 */
	public Double getDisputeAmt() {
	 	return disputeAmt;
	}
	/**
	 * @设置 商纠金额
	 * @param disputeAmt
	 */
	public void setDisputeAmt(Double disputeAmt) {
	 	this.disputeAmt = disputeAmt;
	}
	/**
	 * @return 是否瑕疵
	 */
	public String getFlawSts() {
	 	return flawSts;
	}
	/**
	 * @设置 是否瑕疵
	 * @param flawSts
	 */
	public void setFlawSts(String flawSts) {
	 	this.flawSts = flawSts;
	}
	/**
	 * @return 瑕疵原因
	 */
	public String getFlawReasion() {
	 	return flawReasion;
	}
	/**
	 * @设置 瑕疵原因
	 * @param flawReasion
	 */
	public void setFlawReasion(String flawReasion) {
	 	this.flawReasion = flawReasion;
	}
	/**
	 * @return 瑕疵处理
	 */
	public String getFlawDeal() {
	 	return flawDeal;
	}
	/**
	 * @设置 瑕疵处理
	 * @param flawDeal
	 */
	public void setFlawDeal(String flawDeal) {
	 	this.flawDeal = flawDeal;
	}
	/**
	 * @return 账款调整金额
	 */
	public Double getAdjustAmt() {
	 	return adjustAmt;
	}
	/**
	 * @设置 账款调整金额
	 * @param adjustAmt
	 */
	public void setAdjustAmt(Double adjustAmt) {
	 	this.adjustAmt = adjustAmt;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 登记人号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记机构号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记机构号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记机构名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
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
	 * @return 提交申请时间
	 */
	public String getAppTime() {
	 	return appTime;
	}
	/**
	 * @设置 提交申请时间
	 * @param appTime
	 */
	public void setAppTime(String appTime) {
	 	this.appTime = appTime;
	}
	/**
	 * @return 审批流程id
	 */
	public String getApproveProcessId() {
	 	return approveProcessId;
	}
	/**
	 * @设置 审批流程id
	 * @param approveProcessId
	 */
	public void setApproveProcessId(String approveProcessId) {
	 	this.approveProcessId = approveProcessId;
	}
	/**
	 * @return 审批流程
	 */
	public String getApproveProcess() {
	 	return approveProcess;
	}
	/**
	 * @设置 审批流程
	 * @param approveProcess
	 */
	public void setApproveProcess(String approveProcess) {
	 	this.approveProcess = approveProcess;
	}
	/**
	 * @return 当前审批节点编号
	 */
	public String getApproveNodeNo() {
	 	return approveNodeNo;
	}
	/**
	 * @设置 当前审批节点编号
	 * @param approveNodeNo
	 */
	public void setApproveNodeNo(String approveNodeNo) {
	 	this.approveNodeNo = approveNodeNo;
	}
	/**
	 * @return 当前审批节点名称
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 当前审批节点名称
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 审批角色号/用户号
	 */
	public String getApprovePartNo() {
	 	return approvePartNo;
	}
	/**
	 * @设置 审批角色号/用户号
	 * @param approvePartNo
	 */
	public void setApprovePartNo(String approvePartNo) {
	 	this.approvePartNo = approvePartNo;
	}
	/**
	 * @return 当前审批角色/用户名称
	 */
	public String getApprovePartName() {
	 	return approvePartName;
	}
	/**
	 * @设置 当前审批角色/用户名称
	 * @param approvePartName
	 */
	public void setApprovePartName(String approvePartName) {
	 	this.approvePartName = approvePartName;
	}
	/**
	 * @return 审批说明
	 */
	public String getApproveRemark() {
	 	return approveRemark;
	}
	/**
	 * @设置 审批说明
	 * @param approveRemark
	 */
	public void setApproveRemark(String approveRemark) {
	 	this.approveRemark = approveRemark;
	}
	/**
	 * @return 审批意见1同意2否决3退回上一环节4退回初审5不同意
	 */
	public String getApproveResult() {
	 	return approveResult;
	}
	/**
	 * @设置 审批意见1同意2否决3退回上一环节4退回初审5不同意
	 * @param approveResult
	 */
	public void setApproveResult(String approveResult) {
	 	this.approveResult = approveResult;
	}
	/**
	 * @return 最近修改时间
	 */
	public String getLstModeTime() {
	 	return lstModeTime;
	}
	/**
	 * @设置 最近修改时间
	 * @param lstModeTime
	 */
	public void setLstModeTime(String lstModeTime) {
	 	this.lstModeTime = lstModeTime;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	
	public void setDefault(MfAccntTransfer mfAccntTransfer){
		this.transferId = mfAccntTransfer.getTransferId();
		this.pactNo = mfAccntTransfer.getPactNo();
		this.pactId = mfAccntTransfer.getPactNo();
		this.pactAccntAmt = mfAccntTransfer.getPactAccntAmt();
		this.cusNoBuy = mfAccntTransfer.getCusNoBuy();
		this.cusNameBuy = mfAccntTransfer.getCusNameBuy();
		this.cusNoSell = mfAccntTransfer.getCusNoBuy();
		this.cusNameSell = mfAccntTransfer.getCusNameSell();
		this.endDateAccnt = mfAccntTransfer.getEndDateAccnt();
		this.transferCount = mfAccntTransfer.getTransferCount();
		this.transferNum = mfAccntTransfer.getTransferNum();
		this.paperNo = mfAccntTransfer.getPaperNo();
		this.transferAmt = mfAccntTransfer.getTransferAmt();
		this.repayedTransferAmt = mfAccntTransfer.getRepayedTransferAmt();
		this.transferBal = mfAccntTransfer.getTransferBal();
		this.passTime = mfAccntTransfer.getPassTime();
		this.lastReturnDate = mfAccntTransfer.getLastReturnDate();
		this.finishDate = mfAccntTransfer.getFinishDate();
		this.transferSts = mfAccntTransfer.getTransferSts();
		this.disputeSts = mfAccntTransfer.getDisputeSts();
		this.disputeAmt = mfAccntTransfer.getDisputeAmt();
		this.flawSts = mfAccntTransfer.getFlawSts();
		this.flawReasion = mfAccntTransfer.getFlawReasion();
		this.flawDeal = mfAccntTransfer.getFlawDeal();
		this.adjustAmt = mfAccntTransfer.getAdjustAmt();
		this.remark = mfAccntTransfer.getRemark();
		this.opNo = mfAccntTransfer.getOpNo();
		this.opName = mfAccntTransfer.getOpName();
		this.brNo = mfAccntTransfer.getBrNo();
		this.brName = mfAccntTransfer.getBrName();
		this.regTime = mfAccntTransfer.getRegTime();
		this.appTime = mfAccntTransfer.getAppTime();
		this.lstModeTime = mfAccntTransfer.getLstModeTime();
		this.ext1 = mfAccntTransfer.getExt1();
		this.ext2 = mfAccntTransfer.getExt2();
		this.ext3 = mfAccntTransfer.getExt3();
		this.ext4 = mfAccntTransfer.getExt4();
		this.ext5 = mfAccntTransfer.getExt5();

	}
}