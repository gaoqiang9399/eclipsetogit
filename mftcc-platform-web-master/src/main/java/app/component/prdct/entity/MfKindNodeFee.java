package app.component.prdct.entity;
import app.base.BaseDomain;
/**
* Title: MfKindNodeFee.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 29 15:43:10 CST 2017
* @version：1.0
**/
public class MfKindNodeFee extends BaseDomain {
	private String kindNodeFeeId;//唯一编号
	private String kindNo;//产品编号
	private String busModel;//业务模式
	private String modelType;//是否业务模式基础数据normal：正常；base：基础；
	private String nodeNo;//节点编号
	private String itemNo;//费用项编号
	private String itemName;//费用项名称
	private String remark;//描述说明
	private String optPower;//操作权限1：改；2：查；3：收
	private Integer sort;//排序
	private String useFlag;//是否启用
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String regTime;//登记时间格式：yyyyMMdd HH:mm:ss
	private String lstModTime;//最后修改时间格式：yyyyMMdd HH:mm:ss
	private String ext1;//ext1
	private String ext2;//ext2
	private String ext3;//ext3
	private String ext4;//ext4
	private String ext5;//ext5
	private String ext6;//ext6
	private String ext7;//ext7
	private String ext8;//ext8
	private String ext9;//ext9
	private String ext10;//ext10
	
	//过程变量
	private String appId;//申请ID
	private String feeId;//费用的唯一编号
	private String standard;//基准类型 1应收账款总额2转让应收账款金额3应收账款还款金额4融资款放款金额5融资款还款金额
	private String takeType;//1：百分比； 2：固额；
	private Double rateScale;//固额值或百分比值
	private Double feeAmt;//应收费用
	private String feeType;//收取方式
	//辅助属性
	private String moneyBackFlag;
	private String itemType;//费用类型（收 退 冲）
	private String feeCollectTime;//费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	private String feeMainNo;//费用主体编号
	private String feeMainName;//费用主体
	private String feeAccountId;//收费账号关联编号
	private String feeAccountNo;//收费账号
	private String feeVoucherNo;//费用凭证号
	private Double receivableFeeAmt;//应收费用
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Double getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}
	/**
	 * @return 唯一编号
	 */
	public String getKindNodeFeeId() {
	 	return kindNodeFeeId;
	}
	/**
	 * @设置 唯一编号
	 * @param kindNodeFeeId
	 */
	public void setKindNodeFeeId(String kindNodeFeeId) {
	 	this.kindNodeFeeId = kindNodeFeeId;
	}
	/**
	 * @return 产品编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 业务模式
	 */
	public String getBusModel() {
	 	return busModel;
	}
	/**
	 * @设置 业务模式
	 * @param modelId
	 */
	public void setBusModel(String busModel) {
	 	this.busModel = busModel;
	}
	/**
	 * @return 是否业务模式基础数据normal：正常；base：基础；
	 */
	public String getModelType() {
	 	return modelType;
	}
	/**
	 * @设置 是否业务模式基础数据normal：正常；base：基础；
	 * @param modelType
	 */
	public void setModelType(String modelType) {
	 	this.modelType = modelType;
	}
	/**
	 * @return 节点编号
	 */
	public String getNodeNo() {
	 	return nodeNo;
	}
	/**
	 * @设置 节点编号
	 * @param nodeNo
	 */
	public void setNodeNo(String nodeNo) {
	 	this.nodeNo = nodeNo;
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
	 * @return 描述说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 描述说明
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 操作权限1：改；2：查；3：收
	 */
	public String getOptPower() {
	 	return optPower;
	}
	/**
	 * @设置 操作权限1：改；2：查；3：收
	 * @param power
	 */
	public void setOptPower(String optPower) {
	 	this.optPower = optPower;
	}
	/**
	 * @return 排序
	 */
	public Integer getSort() {
	 	return sort;
	}
	/**
	 * @设置 排序
	 * @param sort
	 */
	public void setSort(Integer sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 是否启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
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
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间格式：yyyyMMdd HH:mm:ss
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间格式：yyyyMMdd HH:mm:ss
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间格式：yyyyMMdd HH:mm:ss
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间格式：yyyyMMdd HH:mm:ss
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return ext1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 ext1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return ext2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 ext2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return ext3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 ext3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return ext4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 ext4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return ext5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 ext5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return ext6
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 ext6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return ext7
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 ext7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return ext8
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 ext8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return ext9
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 ext9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return ext10
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 ext10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getStandard() {
		return standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}
	public String getTakeType() {
		return takeType;
	}
	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}
	public Double getRateScale() {
		return rateScale;
	}
	public void setRateScale(Double rateScale) {
		this.rateScale = rateScale;
	}
	
	public String getFeeId() {
		return feeId;
	}
	public void setFeeId(String feeId) {
		this.feeId = feeId;
	}
	public String getMoneyBackFlag() {
		return moneyBackFlag;
	}
	public void setMoneyBackFlag(String moneyBackFlag) {
		this.moneyBackFlag = moneyBackFlag;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	/**
	 * 费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	 */
	public String getFeeCollectTime() {
		return feeCollectTime;
	}
	/**
	 * 费用收取时间：1-放款时收取，2-还款时收取，3-单独收取
	 */
	public void setFeeCollectTime(String feeCollectTime) {
		this.feeCollectTime = feeCollectTime;
	}
	public String getFeeMainNo() {
		return feeMainNo;
	}
	public void setFeeMainNo(String feeMainNo) {
		this.feeMainNo = feeMainNo;
	}
	public String getFeeMainName() {
		return feeMainName;
	}
	public void setFeeMainName(String feeMainName) {
		this.feeMainName = feeMainName;
	}
	public String getFeeAccountId() {
		return feeAccountId;
	}
	public void setFeeAccountId(String feeAccountId) {
		this.feeAccountId = feeAccountId;
	}
	public String getFeeAccountNo() {
		return feeAccountNo;
	}
	public void setFeeAccountNo(String feeAccountNo) {
		this.feeAccountNo = feeAccountNo;
	}
	public String getFeeVoucherNo() {
		return feeVoucherNo;
	}
	public void setFeeVoucherNo(String feeVoucherNo) {
		this.feeVoucherNo = feeVoucherNo;
	}
	public Double getReceivableFeeAmt() {
		return receivableFeeAmt;
	}
	public void setReceivableFeeAmt(Double receivableFeeAmt) {
		this.receivableFeeAmt = receivableFeeAmt;
	}
	
}