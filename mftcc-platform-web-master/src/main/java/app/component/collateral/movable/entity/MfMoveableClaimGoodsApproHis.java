package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableClaimGoodsApproHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Jun 10 18:35:10 CST 2017
* @version：1.0
**/
public class MfMoveableClaimGoodsApproHis extends BaseDomain {
	private String claimApproHisId;//提货申请审批编号
	private String claimId;//提货申请编号
	private String cusNo;//申请方
	private String cusName;//申请方名称
	private String principal;//负责人
	private String telephone;//联系方式 手机号或固定电话
	private String applyDate;//申请日期
	private String expectClaimDate;//期望提货时间
	private String claimPledge;//提取押品
	private String claimPledgeNo;//押品编号
	private String claimType;//提货方式0部门1全部
	private Integer inventoryNum;//库存货物数量
	private Integer goodsNum;//货物数量
	private String goodsDetail;//货物明细
	private Double claimGoodsAmt;//提取货物总价
	private Double claimGoodsBal;//剩余押品价值
	private String remark;//申请说明
	private Double affirmClaimGoodsBal;//实际剩余押品价值
	private String isBelowMinAmt;//是否低于最低库存价值
	private Double affirmClaimGoodsAmt;//实际提取货物总价
	private String isAccordance;//是否与提货申请书一致
	private String affirmGoodsDetail;//货物明细
	private String affirmGoodsNum;//实际货物数量
	private String claimRemark;//提货说明
	private String appSts;//申请状态0新增申请1审批中2审批通过3否决
	private String busPleId;//押品业务关联编号
	private String approveNodeNo;//当前审批节点编号
	private String approveNodeName;//当前审批节点名称
	private String approvePartNo;//审批角色号/用户号
	private String approvePartName;//当前审批角色/用户名称
	private String approveRemark;//审批说明
	private String approveResult;//审批意见1同意2否决3退回上一环节4退回初审5不同意
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间

	/**
	 * @return 提货申请审批编号
	 */
	public String getClaimApproHisId() {
	 	return claimApproHisId;
	}
	/**
	 * @设置 提货申请审批编号
	 * @param claimApproHisId
	 */
	public void setClaimApproHisId(String claimApproHisId) {
	 	this.claimApproHisId = claimApproHisId;
	}
	/**
	 * @return 提货申请编号
	 */
	public String getClaimId() {
	 	return claimId;
	}
	/**
	 * @设置 提货申请编号
	 * @param claimId
	 */
	public void setClaimId(String claimId) {
	 	this.claimId = claimId;
	}
	/**
	 * @return 申请方
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 申请方
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 申请方名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 申请方名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 负责人
	 */
	public String getPrincipal() {
	 	return principal;
	}
	/**
	 * @设置 负责人
	 * @param principal
	 */
	public void setPrincipal(String principal) {
	 	this.principal = principal;
	}
	/**
	 * @return 联系方式 手机号或固定电话
	 */
	public String getTelephone() {
	 	return telephone;
	}
	/**
	 * @设置 联系方式 手机号或固定电话
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
	 	this.telephone = telephone;
	}
	/**
	 * @return 申请日期
	 */
	public String getApplyDate() {
	 	return applyDate;
	}
	/**
	 * @设置 申请日期
	 * @param applyDate
	 */
	public void setApplyDate(String applyDate) {
	 	this.applyDate = applyDate;
	}
	/**
	 * @return 期望提货时间
	 */
	public String getExpectClaimDate() {
	 	return expectClaimDate;
	}
	/**
	 * @设置 期望提货时间
	 * @param expectClaimDate
	 */
	public void setExpectClaimDate(String expectClaimDate) {
	 	this.expectClaimDate = expectClaimDate;
	}
	/**
	 * @return 提取押品
	 */
	public String getClaimPledge() {
	 	return claimPledge;
	}
	/**
	 * @设置 提取押品
	 * @param claimPledge
	 */
	public void setClaimPledge(String claimPledge) {
	 	this.claimPledge = claimPledge;
	}
	/**
	 * @return 押品编号
	 */
	public String getClaimPledgeNo() {
	 	return claimPledgeNo;
	}
	/**
	 * @设置 押品编号
	 * @param claimPledgeNo
	 */
	public void setClaimPledgeNo(String claimPledgeNo) {
	 	this.claimPledgeNo = claimPledgeNo;
	}
	/**
	 * @return 提货方式0部门1全部
	 */
	public String getClaimType() {
	 	return claimType;
	}
	/**
	 * @设置 提货方式0部门1全部
	 * @param claimType
	 */
	public void setClaimType(String claimType) {
	 	this.claimType = claimType;
	}
	/**
	 * @return 货物数量
	 */
	public Integer getGoodsNum() {
	 	return goodsNum;
	}
	/**
	 * @设置 货物数量
	 * @param goodsNum
	 */
	public void setGoodsNum(Integer goodsNum) {
	 	this.goodsNum = goodsNum;
	}
	/**
	 * @return 货物明细
	 */
	public String getGoodsDetail() {
	 	return goodsDetail;
	}
	/**
	 * @设置 货物明细
	 * @param goodsDetail
	 */
	public void setGoodsDetail(String goodsDetail) {
	 	this.goodsDetail = goodsDetail;
	}
	/**
	 * @return 提取货物总价
	 */
	public Double getClaimGoodsAmt() {
	 	return claimGoodsAmt;
	}
	/**
	 * @设置 提取货物总价
	 * @param claimGoodsAmt
	 */
	public void setClaimGoodsAmt(Double claimGoodsAmt) {
	 	this.claimGoodsAmt = claimGoodsAmt;
	}
	/**
	 * @return 剩余押品价值
	 */
	public Double getClaimGoodsBal() {
	 	return claimGoodsBal;
	}
	/**
	 * @设置 剩余押品价值
	 * @param claimGoodsBal
	 */
	public void setClaimGoodsBal(Double claimGoodsBal) {
	 	this.claimGoodsBal = claimGoodsBal;
	}
	/**
	 * @return 申请说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 申请说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 实际剩余押品价值
	 */
	public Double getAffirmClaimGoodsBal() {
	 	return affirmClaimGoodsBal;
	}
	/**
	 * @设置 实际剩余押品价值
	 * @param affirmClaimGoodsBal
	 */
	public void setAffirmClaimGoodsBal(Double affirmClaimGoodsBal) {
	 	this.affirmClaimGoodsBal = affirmClaimGoodsBal;
	}
	/**
	 * @return 是否低于最低库存价值
	 */
	public String getIsBelowMinAmt() {
	 	return isBelowMinAmt;
	}
	/**
	 * @设置 是否低于最低库存价值
	 * @param isBelowMinAmt
	 */
	public void setIsBelowMinAmt(String isBelowMinAmt) {
	 	this.isBelowMinAmt = isBelowMinAmt;
	}
	/**
	 * @return 实际提取货物总价
	 */
	public Double getAffirmClaimGoodsAmt() {
	 	return affirmClaimGoodsAmt;
	}
	/**
	 * @设置 实际提取货物总价
	 * @param affirmClaimGoodsAmt
	 */
	public void setAffirmClaimGoodsAmt(Double affirmClaimGoodsAmt) {
	 	this.affirmClaimGoodsAmt = affirmClaimGoodsAmt;
	}
	/**
	 * @return 是否与提货申请书一致
	 */
	public String getIsAccordance() {
	 	return isAccordance;
	}
	/**
	 * @设置 是否与提货申请书一致
	 * @param isAccordance
	 */
	public void setIsAccordance(String isAccordance) {
	 	this.isAccordance = isAccordance;
	}
	/**
	 * @return 货物明细
	 */
	public String getAffirmGoodsDetail() {
	 	return affirmGoodsDetail;
	}
	/**
	 * @设置 货物明细
	 * @param affirmGoodsDetail
	 */
	public void setAffirmGoodsDetail(String affirmGoodsDetail) {
	 	this.affirmGoodsDetail = affirmGoodsDetail;
	}
	/**
	 * @return 实际货物数量
	 */
	public String getAffirmGoodsNum() {
	 	return affirmGoodsNum;
	}
	/**
	 * @设置 实际货物数量
	 * @param affirmGoodsNum
	 */
	public void setAffirmGoodsNum(String affirmGoodsNum) {
	 	this.affirmGoodsNum = affirmGoodsNum;
	}
	/**
	 * @return 提货说明
	 */
	public String getClaimRemark() {
	 	return claimRemark;
	}
	/**
	 * @设置 提货说明
	 * @param claimRemark
	 */
	public void setClaimRemark(String claimRemark) {
	 	this.claimRemark = claimRemark;
	}
	/**
	 * @return 申请状态0新增申请1审批中2审批通过3否决
	 */
	public String getAppSts() {
	 	return appSts;
	}
	/**
	 * @设置 申请状态0新增申请1审批中2审批通过3否决
	 * @param appSts
	 */
	public void setAppSts(String appSts) {
	 	this.appSts = appSts;
	}
	/**
	 * @return 押品业务关联编号
	 */
	public String getBusPleId() {
	 	return busPleId;
	}
	/**
	 * @设置 押品业务关联编号
	 * @param busPleId
	 */
	public void setBusPleId(String busPleId) {
	 	this.busPleId = busPleId;
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
	 * @return 登记人
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 信息登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 信息登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
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
	public Integer getInventoryNum() {
		return inventoryNum;
	}
	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
}