package app.component.calc.fee.entity;
import app.base.BaseDomain;
/**
* Title: MfBusAppFeeHis.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 30 15:47:24 CST 2017
* @version：1.0
**/
public class MfBusAppFeeHis extends BaseDomain {
	private String serialNo;//唯一编号
	private String id;//id
	private String appId;//申请id
	private String pactId;//合同id
	private String feeStdNo;//收费标准编号
	private String feeStdName;//收费标准名称
	private String itemNo;//费用项编号
	private String itemName;//费用项名称
	private String takeType;//类型（1：百分比； 2：固额；）
	private Double rateScale;//固额值或百分比值
	private String takeNode;//收取节点（1：合同签订；2：应收账款转让；3：应收账款还款；4：融资款放款：5：融资款还款；）
	private String standard;//基准类型（1：应收账款总额；2：转让应收账款金额；3：应收账款还款金额；4：融资款放款金额；5：融资款还款金额）
	private String cusType;//cus_type
	private String feeType;//收费方式（ 0：一次性 1：分期 2：按次；）
	private String useFlag;//启用标记（0：禁用；1：启用；）
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
	private String feeMainNo;//费用主体编号
	private String feeMainName;//费用主体
	private String feeAccountId;//收费账号关联编号
	private String feeVoucherNo;//费用凭证号
	private String relationPactNo;//关联合同展示号
	private String advanceCollectTime;//预收时间
	/**
	 * @return 唯一编号
	 */
	public String getSerialNo() {
	 	return serialNo;
	}
	/**
	 * @设置 唯一编号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
	 	this.serialNo = serialNo;
	}
	/**
	 * @return id
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 id
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 申请id
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请id
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 合同id
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同id
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
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
	 * @return 类型（1：百分比； 2：固额；）
	 */
	public String getTakeType() {
	 	return takeType;
	}
	/**
	 * @设置 类型（1：百分比； 2：固额；）
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
	 * @return 收取节点（1：合同签订；2：应收账款转让；3：应收账款还款；4：融资款放款：5：融资款还款；）
	 */
	public String getTakeNode() {
	 	return takeNode;
	}
	/**
	 * @设置 收取节点（1：合同签订；2：应收账款转让；3：应收账款还款；4：融资款放款：5：融资款还款；）
	 * @param takeNode
	 */
	public void setTakeNode(String takeNode) {
	 	this.takeNode = takeNode;
	}
	/**
	 * @return 基准类型（1：应收账款总额；2：转让应收账款金额；3：应收账款还款金额；4：融资款放款金额；5：融资款还款金额）
	 */
	public String getStandard() {
	 	return standard;
	}
	/**
	 * @设置 基准类型（1：应收账款总额；2：转让应收账款金额；3：应收账款还款金额；4：融资款放款金额；5：融资款还款金额）
	 * @param standard
	 */
	public void setStandard(String standard) {
	 	this.standard = standard;
	}
	/**
	 * @return cus_type
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 cus_type
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 收费方式（ 0：一次性 1：分期 2：按次；）
	 */
	public String getFeeType() {
	 	return feeType;
	}
	/**
	 * @设置 收费方式（ 0：一次性 1：分期 2：按次；）
	 * @param feeType
	 */
	public void setFeeType(String feeType) {
	 	this.feeType = feeType;
	}
	/**
	 * @return 启用标记（0：禁用；1：启用；）
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标记（0：禁用；1：启用；）
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
	public String getFeeVoucherNo() {
		return feeVoucherNo;
	}
	public void setFeeVoucherNo(String feeVoucherNo) {
		this.feeVoucherNo = feeVoucherNo;
	}
	public String getRelationPactNo() {
		return relationPactNo;
	}
	public void setRelationPactNo(String relationPactNo) {
		this.relationPactNo = relationPactNo;
	}
	public String getAdvanceCollectTime() {
		return advanceCollectTime;
	}
	public void setAdvanceCollectTime(String advanceCollectTime) {
		this.advanceCollectTime = advanceCollectTime;
	}
	
}