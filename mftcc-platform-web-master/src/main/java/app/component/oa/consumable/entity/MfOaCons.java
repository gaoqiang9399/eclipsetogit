package app.component.oa.consumable.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: MfOaCons.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 24 11:56:30 CST 2016
* @version：1.0
**/
public class MfOaCons extends BaseDomain {
	private String consId;//编号
	private String classNo;//类别编号
	private String consName;//名称
	private Integer storeNum;//当前库存
	private Double price;//单价
	private Integer sumPutNum;//总入库量,默认0
	private Integer scrapNum;//报损数量
	private Integer appNum;//申领数量
	private String remark;//备注
	private String specification;//规格型号
	private String barCode;//条形码
	private String useState;//使用状况 1经营性使用 2非经营性使用 3未使用
	private String opNo;//(第一次入库)登记人编号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String className;//类别名称
	private String appType;//申领类型 1领用 2借用
	private String unit;//计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	private List<MfOaConsOperate> consOperatesList;
	
	public List<MfOaConsOperate> getConsOperatesList() {
		return consOperatesList;
	}
	public void setConsOperatesList(List<MfOaConsOperate> consOperatesList) {
		this.consOperatesList = consOperatesList;
	}
	public Integer getPutNum() {
		return putNum;
	}
	public void setPutNum(Integer putNum) {
		this.putNum = putNum;
	}
	private Integer putNum;//单次入库数量
	/**
	 * @return 编号
	 */
	public String getConsId() {
	 	return consId;
	}
	/**
	 * @设置 编号
	 * @param consId
	 */
	public void setConsId(String consId) {
	 	this.consId = consId;
	}
	/**
	 * @return 类别编号
	 */
	public String getClassNo() {
	 	return classNo;
	}
	/**
	 * @设置 类别编号
	 * @param classNo
	 */
	public void setClassNo(String classNo) {
	 	this.classNo = classNo;
	}
	/**
	 * @return 名称
	 */
	public String getConsName() {
	 	return consName;
	}
	/**
	 * @设置 名称
	 * @param consName
	 */
	public void setConsName(String consName) {
	 	this.consName = consName;
	}
	/**
	 * @return 当前库存
	 */
	public Integer getStoreNum() {
	 	return storeNum;
	}
	/**
	 * @设置 当前库存
	 * @param storeNum
	 */
	public void setStoreNum(Integer storeNum) {
	 	this.storeNum = storeNum;
	}
	/**
	 * @return 单价
	 */
	public Double getPrice() {
	 	return price;
	}
	/**
	 * @设置 单价
	 * @param price
	 */
	public void setPrice(Double price) {
	 	this.price = price;
	}
	/**
	 * @return 总入库量
	 */
	public Integer getSumPutNum() {
	 	return sumPutNum;
	}
	/**
	 * @设置 总入库量
	 * @param sumPutNum
	 */
	public void setSumPutNum(Integer sumPutNum) {
	 	this.sumPutNum = sumPutNum;
	}
	/**
	 * @return 报损数量
	 */
	public Integer getScrapNum() {
	 	return scrapNum;
	}
	/**
	 * @设置 报损数量
	 * @param scrapNum
	 */
	public void setScrapNum(Integer scrapNum) {
	 	this.scrapNum = scrapNum;
	}
	/**
	 * @return 申领数量
	 */
	public Integer getAppNum() {
	 	return appNum;
	}
	/**
	 * @设置 申领数量
	 * @param appNum
	 */
	public void setAppNum(Integer appNum) {
	 	this.appNum = appNum;
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
	 * @return 规格型号
	 */
	public String getSpecification() {
	 	return specification;
	}
	/**
	 * @设置 规格型号
	 * @param specification
	 */
	public void setSpecification(String specification) {
	 	this.specification = specification;
	}
	/**
	 * @return 条形码
	 */
	public String getBarCode() {
	 	return barCode;
	}
	/**
	 * @设置 条形码
	 * @param barCode
	 */
	public void setBarCode(String barCode) {
	 	this.barCode = barCode;
	}
	/**
	 * @return 使用状况 1经营性使用 2非经营性使用 3未使用
	 */
	public String getUseState() {
	 	return useState;
	}
	/**
	 * @设置 使用状况 1经营性使用 2非经营性使用 3未使用
	 * @param useState
	 */
	public void setUseState(String useState) {
	 	this.useState = useState;
	}
	/**
	 * @return (第一次入库)登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 (第一次入库)登记人编号
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
	 * @return 类别名称
	 */
	public String getClassName() {
	 	return className;
	}
	/**
	 * @设置 类别名称
	 * @param className
	 */
	public void setClassName(String className) {
	 	this.className = className;
	}
	/**
	 * @return 申领类型 1领用 2借用
	 */
	public String getAppType() {
	 	return appType;
	}
	/**
	 * @设置 申领类型 1领用 2借用
	 * @param appType
	 */
	public void setAppType(String appType) {
	 	this.appType = appType;
	}
	/**
	 * @return 计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	 */
	public String getUnit() {
	 	return unit;
	}
	/**
	 * @设置 计量单位 01个 02台 03辆 04张 05米 06千米 07立方米 08千克 09包 10袋
	 * @param unit
	 */
	public void setUnit(String unit) {
	 	this.unit = unit;
	}
}