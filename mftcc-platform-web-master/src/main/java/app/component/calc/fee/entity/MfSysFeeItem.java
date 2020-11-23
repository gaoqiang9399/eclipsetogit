package app.component.calc.fee.entity;
import app.base.BaseDomain;
/**
* Title: MfSysFeeItem.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 20 18:04:05 CST 2016
* @version：1.0
**/
public class MfSysFeeItem extends BaseDomain {
	private String id;//
	private String feeStdNo;//收费标准编号
	private String feeStdName;//收费标准名称
	private String itemNo;//费用项编号
	private String itemName;//费用项名称
	private String takeType;//1：百分比； 2：固额； 3：固额（分摊）；
	private Double rateScale;//固额值或百分比值
	private String rateType;//利率类型
	private String takeNode;//收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	private String againstFlag;//是否支持冲抵 1-是 0-否
	private String deductFlag;//是否支持减免 1-是 0-否
	private String moneyBackFlag;//是否支持退款 1-是 0-否
	private String standard;//基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	private String cusType;//预留字段客户类型  1：买方  2：卖方
	private String useFlag;//0禁用1启用
	private String lstModTime;//最近修改时间
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String itemType;//费用项类型：1-收费项，2-退费项，3-冲抵项
	private String feeType;//收取方式：1-一次性，2-按照还款计划期数，3-按顺延年，4-按顺延季，5-按顺延月  6-按天收取  7-一次性按年收取
	private String feeCollectTime;//费用收取时间：1-业务阶段收取，2-还款时收取，3-单独收取
	private String opNo;//登记人员编号
	private String opName;//登记人员名称
	private String brNo;//登记人员部门编号
	private String brName;//登记人员部门名称
	//新增字段
	private String refundCusType;//退费对象类型
	private int sort;// 排序
	private String nodeNo;//业务环节编号
	private  String optPower;	//操作权限 1：改；2：查；3：收,跟节点走 or 跟费用项走？
	private Double receivableFeeAmt;//应收费用

	public Double getReceivableFeeAmt() {
		return receivableFeeAmt;
	}

	public void setReceivableFeeAmt(Double receivableFeeAmt) {
		this.receivableFeeAmt = receivableFeeAmt;
	}
	/**
	 * @return 
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 收费标准编号
	 */
	public String getFeeStdNo() {
	 	return feeStdNo;
	}
	/**
	 * @设置 收费标准编号
	 * @param feeStdNo
	 */
	public void setFeeStdNo(String feeStdNo) {
	 	this.feeStdNo = feeStdNo;
	}
	/**
	 * @return 收费标准名称
	 */
	public String getFeeStdName() {
	 	return feeStdName;
	}
	/**
	 * @设置 收费标准名称
	 * @param feeStdName
	 */
	public void setFeeStdName(String feeStdName) {
	 	this.feeStdName = feeStdName;
	}
	/**
	 * @return 费用项编号
	 */
	public String getItemNo() {
	 	return itemNo;
	}
	/**
	 * @设置 费用项编号
	 * @param itemNo
	 */
	public void setItemNo(String itemNo) {
	 	this.itemNo = itemNo;
	}
	/**
	 * @return 费用项名称
	 */
	public String getItemName() {
	 	return itemName;
	}
	/**
	 * @设置 费用项名称
	 * @param itemName
	 */
	public void setItemName(String itemName) {
	 	this.itemName = itemName;
	}
	/**
	 * @return 1：百分比； 2：固额；
	 */
	public String getTakeType() {
	 	return takeType;
	}
	/**
	 * @设置 1：百分比； 2：固额；
	 * @param takeType
	 */
	public void setTakeType(String takeType) {
	 	this.takeType = takeType;
	}
	/**
	 * @return 固额值或百分比值
	 */
	public Double getRateScale() {
	 	return rateScale;
	}
	/**
	 * @设置 固额值或百分比值
	 * @param rateScale
	 */
	public void setRateScale(Double rateScale) {
	 	this.rateScale = rateScale;
	}
	/**
	 *利率类型
	 */
	public String getRateType() {
		return rateType;
	}
	/**
	 *利率类型
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return 收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	 */
	public String getTakeNode() {
	 	return takeNode;
	}
	/**
	 * @设置 收取节点 1合同签订2应收账款转让3应收账款还款4融资款放款5融资款还款
	 * @param takeNode
	 */
	public void setTakeNode(String takeNode) {
	 	this.takeNode = takeNode;
	}
	
	public String getAgainstFlag() {
		return againstFlag;
	}
	public void setAgainstFlag(String againstFlag) {
		this.againstFlag = againstFlag;
	}
	public String getDeductFlag() {
		return deductFlag;
	}
	public void setDeductFlag(String deductFlag) {
		this.deductFlag = deductFlag;
	}
	public String getMoneyBackFlag() {
		return moneyBackFlag;
	}
	public void setMoneyBackFlag(String moneyBackFlag) {
		this.moneyBackFlag = moneyBackFlag;
	}
	/**
	 * @return 基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	 */
	public String getStandard() {
	 	return standard;
	}
	/**
	 * @设置 基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	 * @param standard
	 */
	public void setStandard(String standard) {
	 	this.standard = standard;
	}
	/**
	 * @return 预留字段客户类型  1：买方  2：卖方
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 预留字段客户类型  1：买方  2：卖方
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 收费方式：1-一次性，2-按照还款计划期数，3-按顺延年，4-按顺延季，5-按顺延月  6-按天收取    7一次性按年收取
	 */
	public String getFeeType() {
	 	return feeType;
	}
	/**
	 * @设置 收费方式：1-一次性，2-按照还款计划期数，3-按顺延年，4-按顺延季，5-按顺延月  6-按天收取    7一次性按年收取
	 * @param feeType
	 */
	public void setFeeType(String feeType) {
	 	this.feeType = feeType;
	}
	/**
	 * @return 0禁用1启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 0禁用1启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 最近修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最近修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
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
	/**
	 * @return 费用项类型 1-收费项，2-退费项，3-冲抵项
	 */
	public String getItemType() {
		return itemType;
	}
	/**
	 * @设置 费用项类型  1-收费项，2-退费项，3-冲抵项
	 * @param itemType
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * @return 费用收取时间
	 */
	public String getFeeCollectTime() {
		return feeCollectTime;
	}
	/**
	 * @设置 费用收取时间
	 * @param feeCollectTime
	 */
	public void setFeeCollectTime(String feeCollectTime) {
		this.feeCollectTime = feeCollectTime;
	}
	/**
	 * @return 登记人员编号
	 */
	public String getOpNo() {
		return opNo;
	}
	/**
	 * @设置 登记人员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	/**
	 * @return 登记人员名称
	 */
	public String getOpName() {
		return opName;
	}
	/**
	 * @设置 登记人员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	/**
	 * @return 登记人员部门编号
	 */
	public String getBrNo() {
		return brNo;
	}
	/**
	 * @设置 登记人员部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	/**
	 * @return 登记人员部门名称
	 */
	public String getBrName() {
		return brName;
	}
	/**
	 * @设置 登记人员部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getRefundCusType() {
		return refundCusType;
	}
	public void setRefundCusType(String refundCusType) {
		this.refundCusType = refundCusType;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getOptPower() {
		return optPower;
	}

	public void setOptPower(String optPower) {
		this.optPower = optPower;
	}
}