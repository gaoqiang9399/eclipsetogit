package app.component.collateral.movable.entity;
import app.base.BaseDomain;
/**
* Title: MfMoveableCompensationConfirm.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu Jun 15 18:43:41 CST 2017
* @version：1.0
**/
public class MfMoveableCompensationConfirm extends BaseDomain {
	private String confirmId;//补偿确认编号
	private String compensationId;//补偿编号
	private String pledgeNo;//押品编号
	private String pledgeName;//押品名称
	private String pledgeShowNo;//押品展示编号
	private Double pledgeWorth;//押品价值
	private Double regulatoryPrice;//最低监管货值
	private Double compensatePrice;//需补偿价值
	private String compensateType;//补偿类型
	private Double marginAmount;//保证金金额
	private Double goodsAmount;//货物价值
	private String pledgeBillNo;//货物明细编号
	private String pledgeBillHisNo;//货物明细审批过程保存编号
	private Double compensateTotalValue;//补偿总价值
	private String compensateDate;//补偿日期
	private String busPleId;//押品业务关联编号
	private String opNo;//登记人
	private String opName;//登记人姓名
	private String brNo;//登记部门
	private String brName;//登记部门名称
	private String regTime;//信息登记时间
	private String lstModTime;//最后修改时间
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String ext6;//
	private String ext7;//
	private String ext8;//
	private String ext9;//
	private String ext10;//

	/**
	 * @return 补偿确认编号
	 */
	public String getConfirmId() {
	 	return confirmId;
	}
	/**
	 * @设置 补偿确认编号
	 * @param confirmId
	 */
	public void setConfirmId(String confirmId) {
	 	this.confirmId = confirmId;
	}
	/**
	 * @return 补偿编号
	 */
	public String getCompensationId() {
	 	return compensationId;
	}
	/**
	 * @设置 补偿编号
	 * @param compensationId
	 */
	public void setCompensationId(String compensationId) {
	 	this.compensationId = compensationId;
	}
	/**
	 * @return 押品编号
	 */
	public String getPledgeNo() {
	 	return pledgeNo;
	}
	/**
	 * @设置 押品编号
	 * @param pledgeNo
	 */
	public void setPledgeNo(String pledgeNo) {
	 	this.pledgeNo = pledgeNo;
	}
	/**
	 * @return 押品名称
	 */
	public String getPledgeName() {
	 	return pledgeName;
	}
	/**
	 * @设置 押品名称
	 * @param pledgeName
	 */
	public void setPledgeName(String pledgeName) {
	 	this.pledgeName = pledgeName;
	}
	/**
	 * @return 押品展示编号
	 */
	public String getPledgeShowNo() {
	 	return pledgeShowNo;
	}
	/**
	 * @设置 押品展示编号
	 * @param pledgeShowNo
	 */
	public void setPledgeShowNo(String pledgeShowNo) {
	 	this.pledgeShowNo = pledgeShowNo;
	}
	/**
	 * @return 押品价值
	 */
	public Double getPledgeWorth() {
	 	return pledgeWorth;
	}
	/**
	 * @设置 押品价值
	 * @param pledgeWorth
	 */
	public void setPledgeWorth(Double pledgeWorth) {
	 	this.pledgeWorth = pledgeWorth;
	}
	/**
	 * @return 最低监管货值
	 */
	public Double getRegulatoryPrice() {
	 	return regulatoryPrice;
	}
	/**
	 * @设置 最低监管货值
	 * @param regulatoryPrice
	 */
	public void setRegulatoryPrice(Double regulatoryPrice) {
	 	this.regulatoryPrice = regulatoryPrice;
	}
	/**
	 * @return 需补偿价值
	 */
	public Double getCompensatePrice() {
	 	return compensatePrice;
	}
	/**
	 * @设置 需补偿价值
	 * @param compensatePrice
	 */
	public void setCompensatePrice(Double compensatePrice) {
	 	this.compensatePrice = compensatePrice;
	}
	/**
	 * @return 补偿类型
	 */
	public String getCompensateType() {
	 	return compensateType;
	}
	/**
	 * @设置 补偿类型
	 * @param compensateType
	 */
	public void setCompensateType(String compensateType) {
	 	this.compensateType = compensateType;
	}
	/**
	 * @return 保证金金额
	 */
	public Double getMarginAmount() {
	 	return marginAmount;
	}
	/**
	 * @设置 保证金金额
	 * @param margin amount
	 */
	public void setMarginAmount(Double marginAmount) {
	 	this.marginAmount = marginAmount;
	}
	/**
	 * @return 货物价值
	 */
	public Double getGoodsAmount() {
	 	return goodsAmount;
	}
	/**
	 * @设置 货物价值
	 * @param goods amount
	 */
	public void setGoodsAmount(Double goodsAmount) {
	 	this.goodsAmount = goodsAmount;
	}
	/**
	 * @return 货物明细编号
	 */
	public String getPledgeBillNo() {
	 	return pledgeBillNo;
	}
	/**
	 * @设置 货物明细编号
	 * @param pledgeBillNo
	 */
	public void setPledgeBillNo(String pledgeBillNo) {
	 	this.pledgeBillNo = pledgeBillNo;
	}
	/**
	 * @return 货物明细审批过程保存编号
	 */
	public String getPledgeBillHisNo() {
	 	return pledgeBillHisNo;
	}
	/**
	 * @设置 货物明细审批过程保存编号
	 * @param pledgeBillHisNo
	 */
	public void setPledgeBillHisNo(String pledgeBillHisNo) {
	 	this.pledgeBillHisNo = pledgeBillHisNo;
	}
	/**
	 * @return 补偿总价值
	 */
	public Double getCompensateTotalValue() {
	 	return compensateTotalValue;
	}
	/**
	 * @设置 补偿总价值
	 * @param compensateTotalValue
	 */
	public void setCompensateTotalValue(Double compensateTotalValue) {
	 	this.compensateTotalValue = compensateTotalValue;
	}
	/**
	 * @return 补偿日期
	 */
	public String getCompensateDate() {
	 	return compensateDate;
	}
	/**
	 * @设置 补偿日期
	 * @param compensateDate
	 */
	public void setCompensateDate(String compensateDate) {
	 	this.compensateDate = compensateDate;
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
	 * @return 
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
}