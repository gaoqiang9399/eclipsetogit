package app.component.oa.consumable.entity;
import app.base.BaseDomain;
/**
* Title: MfOaConsOperate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 24 12:00:15 CST 2016
* @version：1.0
**/
public class MfOaConsOperate extends BaseDomain {
	private String operateId;//操作编号
	private String operateType;//操作类型 1入库 2领用 3借用 5报损 6盘点
	private String consNo;//操作对应易耗品编号
	private String consName;//品名
	private String operateRemark;//操作备注（原因）
	private Integer operateNum;//操作低值易耗品数量
	private String opNo;//登记人编号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String createTime;//提交时间
	private String scrapTime;//报损时间
	private String approveNodeName;//报损审批环节
	private String lstModTime;//最后一次编辑时间
	private String operateState;//操作状态 1审批中 2已通过 3未通过 4未办理 5已办理 6已借出 7已归还 8已入库 9已盘点
	private String addType;//入库增加方式 1购置 2其他
	private String amortizeType;//入库摊销方式 1全部摊销 2部分摊销
	private String billNo;//入库发票号
	private Integer receiveNum;//实际领用数量
	private Integer returnNum;//总归还数量
	private String regNo;//办理人编号
	private String regName;//办理人姓名
	private String regTime;//办理时间
	private Integer putNum;//单次入库数量
	private MfOaCons mfOaCons;
	private Integer returnItem;//临时记录单次归还数量
	
	/**
	 * @return 操作编号
	 */
	public String getOperateId() {
	 	return operateId;
	}
	/**
	 * @设置 操作编号
	 * @param operateId
	 */
	public void setOperateId(String operateId) {
	 	this.operateId = operateId;
	}
	/**
	 * @return 操作类型 1入库 2领用 3借用 5报损 6盘点
	 */
	public String getOperateType() {
	 	return operateType;
	}
	/**
	 * @设置 操作类型 1入库 2领用 3借用 5报损 6盘点
	 * @param operateType
	 */
	public void setOperateType(String operateType) {
	 	this.operateType = operateType;
	}
	/**
	 * @return 操作对应易耗品编号
	 */
	public String getConsNo() {
	 	return consNo;
	}
	/**
	 * @设置 操作对应易耗品编号
	 * @param consNo
	 */
	public void setConsNo(String consNo) {
	 	this.consNo = consNo;
	}
	/**
	 * @return 品名
	 */
	public String getConsName() {
	 	return consName;
	}
	/**
	 * @设置 品名
	 * @param consName
	 */
	public void setConsName(String consName) {
	 	this.consName = consName;
	}
	/**
	 * @return 操作备注（原因）
	 */
	public String getOperateRemark() {
	 	return operateRemark;
	}
	/**
	 * @设置 操作备注（原因）
	 * @param remark
	 */
	public void setOperateRemark(String operateRemark) {
	 	this.operateRemark = operateRemark;
	}
	/**
	 * @return 操作低值易耗品数量
	 */
	public Integer getOperateNum() {
	 	return operateNum;
	}
	/**
	 * @设置 操作低值易耗品数量
	 * @param operateNum
	 */
	public void setOperateNum(Integer operateNum) {
	 	this.operateNum = operateNum;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
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
	 * @return 提交时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 提交时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	/**
	 * @return 报损时间
	 */
	public String getScrapTime() {
	 	return scrapTime;
	}
	/**
	 * @设置 报损时间
	 * @param scrapTime
	 */
	public void setScrapTime(String scrapTime) {
	 	this.scrapTime = scrapTime;
	}
	/**
	 * @return 报损审批环节
	 */
	public String getApproveNodeName() {
	 	return approveNodeName;
	}
	/**
	 * @设置 报损审批环节
	 * @param approveNodeName
	 */
	public void setApproveNodeName(String approveNodeName) {
	 	this.approveNodeName = approveNodeName;
	}
	/**
	 * @return 最后一次编辑时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后一次编辑时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 操作状态 1审批中 2已通过 3未通过 4未办理 5已办理 6已借出 7已归还 8已入库 9已盘点
	 */
	public String getOperateState() {
	 	return operateState;
	}
	/**
	 * @设置 操作状态 1审批中 2已通过 3未通过 4未办理 5已办理 6已借出 7已归还 8已入库 9已盘点
	 * @param operateState
	 */
	public void setOperateState(String operateState) {
	 	this.operateState = operateState;
	}
	/**
	 * @return 入库增加方式 1购置 2其他
	 */
	public String getAddType() {
	 	return addType;
	}
	/**
	 * @设置 入库增加方式 1购置 2其他
	 * @param addType
	 */
	public void setAddType(String addType) {
	 	this.addType = addType;
	}
	/**
	 * @return 入库摊销方式 1全部摊销 2部分摊销
	 */
	public String getAmortizeType() {
	 	return amortizeType;
	}
	/**
	 * @设置 入库摊销方式 1全部摊销 2部分摊销
	 * @param amortizeType
	 */
	public void setAmortizeType(String amortizeType) {
	 	this.amortizeType = amortizeType;
	}
	/**
	 * @return 入库发票号
	 */
	public String getBillNo() {
	 	return billNo;
	}
	/**
	 * @设置 入库发票号
	 * @param billNo
	 */
	public void setBillNo(String billNo) {
	 	this.billNo = billNo;
	}
	/**
	 * @return 实际领用数量
	 */
	public Integer getReceiveNum() {
	 	return receiveNum;
	}
	/**
	 * @设置 实际领用数量
	 * @param receiveNum
	 */
	public void setReceiveNum(Integer receiveNum) {
	 	this.receiveNum = receiveNum;
	}
	/**
	 * @return 归还数量
	 */
	public Integer getReturnNum() {
	 	return returnNum;
	}
	/**
	 * @设置 归还数量
	 * @param returnNum
	 */
	public void setReturnNum(Integer returnNum) {
	 	this.returnNum = returnNum;
	}
	/**
	 * @return 办理人编号
	 */
	public String getRegNo() {
	 	return regNo;
	}
	/**
	 * @设置 办理人编号
	 * @param regNo
	 */
	public void setRegNo(String regNo) {
	 	this.regNo = regNo;
	}
	/**
	 * @return 办理人姓名
	 */
	public String getRegName() {
	 	return regName;
	}
	/**
	 * @设置 办理人姓名
	 * @param regName
	 */
	public void setRegName(String regName) {
	 	this.regName = regName;
	}
	/**
	 * @return 办理时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 办理时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	public Integer getPutNum() {
		return putNum;
	}
	public void setPutNum(Integer putNum) {
		this.putNum = putNum;
	}
	
	public MfOaCons getMfOaCons() {
		return mfOaCons;
	}
	public void setMfOaCons(MfOaCons mfOaCons) {
		this.mfOaCons = mfOaCons;
	}
	public Integer getReturnItem() {
		return returnItem;
	}
	public void setReturnItem(Integer returnItem) {
		this.returnItem = returnItem;
	}
}