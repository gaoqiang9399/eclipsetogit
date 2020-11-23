package app.component.finance.assets.entity;
import app.base.BaseDomain;
/**
* Title: CwAssets.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Apr 10 16:28:21 CST 2017
* @version：1.0
**/
public class CwAssets extends BaseDomain {
	private String uuid;//唯一编号
	private String finBooks;//帐套标识
	private String assetsNo;//资产编码                                                                                         
	private String assetsName;//资产名称                                                                                  
	private String assetsType;//资产分类0：固定资产；1：无形资产                                    
	private String classNo;//类别编号关联cw_assets_class.class_no
	private String className;//类别名称
	private String weeks;//录入期间
	private String brNo;//部门
	private String useDate;//使用日期                                                                                    
	private String number;//数量
	private String cleanWeek;//清理期间                                                                      
	private String assetsMethod;//折旧方式0：平均年限法；1：不提折旧                              
	private String assetsYear;//预计折旧年限
	private String assetsTerm;//预计折旧期限                                                                            
	private String comAssets;//资产科目
	private String comDepre;//累计折旧科目
	private String comBuy;//资产购入科目
	private String comCost;//折旧费用科目
	private String comTax;//税金科目
	private String comClean;//资产清理科目
	private String itemAssets;//资产科目辅助项
	private String itemDepre;//累计折旧科目辅助项
	private String itemBuy;//资产购入科目辅助项
	private String itemCost;//折旧费用科目辅助项
	private String itemTax;//税金科目辅助项
	private String itemClean;//资产清理科目辅助项
	private Double originalAmt;//原值     ？                                                                                 
	private Double taxAmt;//税额   
	private String residualRate;//净残值率                                                                    
	private Double residualAmt;//预计残值金额                                                                 
	private Double reservesAmt;//减值准备金额   ?                                                          12
	private String deprePeriod;//已折旧期间
	private Double depreAmt;//期初累计折旧金额      ？                                                                
	private Double networthAmt;//期初净值  
	private Double monthAmt;//月折旧额                    ？                                                                 
	private String remark;//备注
	private String buyVchPrefix;//购入凭证字                                                        
	private String buyVchNo;//购入凭证编号
	private String cleanVchPrefix;//清理凭证字                                                 
	private String cleanVchNo;//清理凭证编号
	private String assetsSts;//资产状态0：正常；1：清理；                                 
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String occTime;//时间戳最后一次修改日期
	
	//显示使用
	private String brName;//
	private String wkShow;//录入显示周期
	private String ckShow;//清理显示周期

	public CwAssets() {
	}
	
	public CwAssets(String finBooks) {
		this.finBooks = finBooks;
	}
	/**
	 * @return 唯一编号
	 */
	public String getUuid() {
	 	return uuid;
	}
	/**
	 * @设置 唯一编号
	 * @param uuid
	 */
	public void setUuid(String uuid) {
	 	this.uuid = uuid;
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
	 * @return 资产编码
	 */
	public String getAssetsNo() {
	 	return assetsNo;
	}
	/**
	 * @设置 资产编码
	 * @param assetsNo
	 */
	public void setAssetsNo(String assetsNo) {
	 	this.assetsNo = assetsNo;
	}
	/**
	 * @return 资产名称
	 */
	public String getAssetsName() {
	 	return assetsName;
	}
	/**
	 * @设置 资产名称
	 * @param assetsName
	 */
	public void setAssetsName(String assetsName) {
	 	this.assetsName = assetsName;
	}
	/**
	 * @return 资产分类0：固定资产；1：无形资产
	 */
	public String getAssetsType() {
	 	return assetsType;
	}
	/**
	 * @设置 资产分类0：固定资产；1：无形资产
	 * @param assetsType
	 */
	public void setAssetsType(String assetsType) {
	 	this.assetsType = assetsType;
	}
	/**
	 * @return 类别编号关联cw_assets_class.class_no
	 */
	public String getClassNo() {
	 	return classNo;
	}
	/**
	 * @设置 类别编号关联cw_assets_class.class_no
	 * @param classNo
	 */
	public void setClassNo(String classNo) {
	 	this.classNo = classNo;
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
	 * @return 录入期间
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 录入期间
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
	}
	/**
	 * @return 部门
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 使用日期
	 */
	public String getUseDate() {
	 	return useDate;
	}
	/**
	 * @设置 使用日期
	 * @param useDate
	 */
	public void setUseDate(String useDate) {
	 	this.useDate = useDate;
	}
	/**
	 * @return 数量
	 */
	public String getNumber() {
	 	return number;
	}
	/**
	 * @设置 数量
	 * @param number
	 */
	public void setNumber(String number) {
	 	this.number = number;
	}
	/**
	 * @return 清理期间
	 */
	public String getCleanWeek() {
	 	return cleanWeek;
	}
	/**
	 * @设置 清理期间
	 * @param cleanWeek
	 */
	public void setCleanWeek(String cleanWeek) {
	 	this.cleanWeek = cleanWeek;
	}
	/**
	 * @return 折旧方式0：平均年限法；1：不提折旧
	 */
	public String getAssetsMethod() {
	 	return assetsMethod;
	}
	/**
	 * @设置 折旧方式0：平均年限法；1：不提折旧
	 * @param assetsMethod
	 */
	public void setAssetsMethod(String assetsMethod) {
	 	this.assetsMethod = assetsMethod;
	}
	/**
	 * @return 预计折旧年限
	 */
	public String getAssetsYear() {
	 	return assetsYear;
	}
	/**
	 * @设置 预计折旧年限
	 * @param assetsYear
	 */
	public void setAssetsYear(String assetsYear) {
	 	this.assetsYear = assetsYear;
	}
	/**
	 * @return 预计折旧期限
	 */
	public String getAssetsTerm() {
	 	return assetsTerm;
	}
	/**
	 * @设置 预计折旧期限
	 * @param assetsTerm
	 */
	public void setAssetsTerm(String assetsTerm) {
	 	this.assetsTerm = assetsTerm;
	}
	/**
	 * @return 资产科目
	 */
	public String getComAssets() {
	 	return comAssets;
	}
	/**
	 * @设置 资产科目
	 * @param comAssets
	 */
	public void setComAssets(String comAssets) {
	 	this.comAssets = comAssets;
	}
	/**
	 * @return 累计折旧科目
	 */
	public String getComDepre() {
	 	return comDepre;
	}
	/**
	 * @设置 累计折旧科目
	 * @param comDepre
	 */
	public void setComDepre(String comDepre) {
	 	this.comDepre = comDepre;
	}
	/**
	 * @return 资产购入科目
	 */
	public String getComBuy() {
	 	return comBuy;
	}
	/**
	 * @设置 资产购入科目
	 * @param comBuy
	 */
	public void setComBuy(String comBuy) {
	 	this.comBuy = comBuy;
	}
	/**
	 * @return 折旧费用科目
	 */
	public String getComCost() {
	 	return comCost;
	}
	/**
	 * @设置 折旧费用科目
	 * @param comCost
	 */
	public void setComCost(String comCost) {
	 	this.comCost = comCost;
	}
	/**
	 * @return 税金科目
	 */
	public String getComTax() {
	 	return comTax;
	}
	/**
	 * @设置 税金科目
	 * @param comTax
	 */
	public void setComTax(String comTax) {
	 	this.comTax = comTax;
	}
	/**
	 * @return 资产清理科目
	 */
	public String getComClean() {
	 	return comClean;
	}
	/**
	 * @设置 资产清理科目
	 * @param comClean
	 */
	public void setComClean(String comClean) {
	 	this.comClean = comClean;
	}
	/**
	 * @return 资产科目辅助项
	 */
	public String getItemAssets() {
	 	return itemAssets;
	}
	/**
	 * @设置 资产科目辅助项
	 * @param itemAssets
	 */
	public void setItemAssets(String itemAssets) {
	 	this.itemAssets = itemAssets;
	}
	/**
	 * @return 累计折旧科目辅助项
	 */
	public String getItemDepre() {
	 	return itemDepre;
	}
	/**
	 * @设置 累计折旧科目辅助项
	 * @param itemDepre
	 */
	public void setItemDepre(String itemDepre) {
	 	this.itemDepre = itemDepre;
	}
	/**
	 * @return 资产购入科目辅助项
	 */
	public String getItemBuy() {
	 	return itemBuy;
	}
	/**
	 * @设置 资产购入科目辅助项
	 * @param itemBuy
	 */
	public void setItemBuy(String itemBuy) {
	 	this.itemBuy = itemBuy;
	}
	/**
	 * @return 折旧费用科目辅助项
	 */
	public String getItemCost() {
	 	return itemCost;
	}
	/**
	 * @设置 折旧费用科目辅助项
	 * @param itemCost
	 */
	public void setItemCost(String itemCost) {
	 	this.itemCost = itemCost;
	}
	/**
	 * @return 税金科目辅助项
	 */
	public String getItemTax() {
	 	return itemTax;
	}
	/**
	 * @设置 税金科目辅助项
	 * @param itemTax
	 */
	public void setItemTax(String itemTax) {
	 	this.itemTax = itemTax;
	}
	/**
	 * @return 资产清理科目辅助项
	 */
	public String getItemClean() {
	 	return itemClean;
	}
	/**
	 * @设置 资产清理科目辅助项
	 * @param itemClean
	 */
	public void setItemClean(String itemClean) {
	 	this.itemClean = itemClean;
	}
	
	/**
	 * @return 已折旧期间
	 */
	public String getDeprePeriod() {
	 	return deprePeriod;
	}
	/**
	 * @设置 已折旧期间
	 * @param deprePeriod
	 */
	public void setDeprePeriod(String deprePeriod) {
	 	this.deprePeriod = deprePeriod;
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
	 * @return 购入凭证字
	 */
	public String getBuyVchPrefix() {
	 	return buyVchPrefix;
	}
	/**
	 * @设置 购入凭证字
	 * @param buyVchPrefix
	 */
	public void setBuyVchPrefix(String buyVchPrefix) {
	 	this.buyVchPrefix = buyVchPrefix;
	}
	/**
	 * @return 购入凭证编号
	 */
	public String getBuyVchNo() {
	 	return buyVchNo;
	}
	/**
	 * @设置 购入凭证编号
	 * @param buyVchNo
	 */
	public void setBuyVchNo(String buyVchNo) {
	 	this.buyVchNo = buyVchNo;
	}
	/**
	 * @return 清理凭证字
	 */
	public String getCleanVchPrefix() {
	 	return cleanVchPrefix;
	}
	/**
	 * @设置 清理凭证字
	 * @param cleanVchPrefix
	 */
	public void setCleanVchPrefix(String cleanVchPrefix) {
	 	this.cleanVchPrefix = cleanVchPrefix;
	}
	/**
	 * @return 清理凭证编号
	 */
	public String getCleanVchNo() {
	 	return cleanVchNo;
	}
	/**
	 * @设置 清理凭证编号
	 * @param cleanVchNo
	 */
	public void setCleanVchNo(String cleanVchNo) {
	 	this.cleanVchNo = cleanVchNo;
	}
	/**
	 * @return 资产状态0：正常；1：清理；
	 */
	public String getAssetsSts() {
	 	return assetsSts;
	}
	/**
	 * @设置 资产状态0：正常；1：清理；
	 * @param assetsSts
	 */
	public void setAssetsSts(String assetsSts) {
	 	this.assetsSts = assetsSts;
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
	public Double getOriginalAmt() {
		return originalAmt;
	}
	public void setOriginalAmt(Double originalAmt) {
		this.originalAmt = originalAmt;
	}
	public Double getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(Double taxAmt) {
		this.taxAmt = taxAmt;
	}
	
	public String getResidualRate() {
		return residualRate;
	}
	public void setResidualRate(String residualRate) {
		this.residualRate = residualRate;
	}
	public Double getResidualAmt() {
		return residualAmt;
	}
	public void setResidualAmt(Double residualAmt) {
		this.residualAmt = residualAmt;
	}
	public Double getReservesAmt() {
		return reservesAmt;
	}
	public void setReservesAmt(Double reservesAmt) {
		this.reservesAmt = reservesAmt;
	}
	public Double getDepreAmt() {
		return depreAmt;
	}
	public void setDepreAmt(Double depreAmt) {
		this.depreAmt = depreAmt;
	}
	public Double getNetworthAmt() {
		return networthAmt;
	}
	public void setNetworthAmt(Double networthAmt) {
		this.networthAmt = networthAmt;
	}
	public Double getMonthAmt() {
		return monthAmt;
	}
	public void setMonthAmt(Double monthAmt) {
		this.monthAmt = monthAmt;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getWkShow() {
		return wkShow;
	}
	public void setWkShow(String wkShow) {
		this.wkShow = wkShow;
	}
	public String getCkShow() {
		return ckShow;
	}
	public void setCkShow(String ckShow) {
		this.ckShow = ckShow;
	}
		
}