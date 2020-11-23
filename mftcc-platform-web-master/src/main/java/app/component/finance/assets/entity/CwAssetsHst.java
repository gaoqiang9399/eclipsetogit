package app.component.finance.assets.entity;
import app.base.BaseDomain;
/**
* Title: CwAssetsHst.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Thu May 11 15:26:18 CST 2017
* @version：1.0
**/
public class CwAssetsHst extends BaseDomain {
	private String uuid;//唯一编号
	private String finBooks;//帐套标识
	private String assetsNo;//资产编码关联cw_assets.assets_no
	private String assetsName;//资产名称
	private String assetsType;//资产分类0：固定资产；1：无形资产
	private String weeks;//周期折旧使用
	private String deprePeriod;//折旧期间折旧使用
	private Double depreAmt;//累计折旧金额折旧使用
	private Double monthAmt;//折旧金额额折旧使用
	private Double reservesAmt;//减值准备金额资产变动使用
	private String assetsTerm;//预计折旧期限资产变动使用
	private Double changeReservesAmt;//减值准备变动后金额资产变动使用
	private String changeAssetsTerm;//变动后折旧期限资产变动使用
	private String cleanWeek;//清理期间清理使用
	private String remark;//备注
	private String assetsSts;//资产操作类型0：折旧；1：变动；2：清理；
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String txDate;//操作日期
	private String occTime;//时间戳最后一次修改日期

	public CwAssetsHst() {
	}
	
	public CwAssetsHst(String finBooks) {
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
	 * @return 资产编码关联cw_assets.assets_no
	 */
	public String getAssetsNo() {
	 	return assetsNo;
	}
	/**
	 * @设置 资产编码关联cw_assets.assets_no
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
	 * @return 周期折旧使用
	 */
	public String getWeeks() {
	 	return weeks;
	}
	/**
	 * @设置 周期折旧使用
	 * @param weeks
	 */
	public void setWeeks(String weeks) {
	 	this.weeks = weeks;
	}
	/**
	 * @return 折旧期间折旧使用
	 */
	public String getDeprePeriod() {
	 	return deprePeriod;
	}
	/**
	 * @设置 折旧期间折旧使用
	 * @param deprePeriod
	 */
	public void setDeprePeriod(String deprePeriod) {
	 	this.deprePeriod = deprePeriod;
	}
	/**
	 * @return 累计折旧金额折旧使用
	 */
	public Double getDepreAmt() {
	 	return depreAmt;
	}
	/**
	 * @设置 累计折旧金额折旧使用
	 * @param depreAmt
	 */
	public void setDepreAmt(Double depreAmt) {
	 	this.depreAmt = depreAmt;
	}
	/**
	 * @return 折旧金额额折旧使用
	 */
	public Double getMonthAmt() {
	 	return monthAmt;
	}
	/**
	 * @设置 折旧金额额折旧使用
	 * @param monthAmt
	 */
	public void setMonthAmt(Double monthAmt) {
	 	this.monthAmt = monthAmt;
	}
	/**
	 * @return 减值准备金额资产变动使用
	 */
	public Double getReservesAmt() {
	 	return reservesAmt;
	}
	/**
	 * @设置 减值准备金额资产变动使用
	 * @param reservesAmt
	 */
	public void setReservesAmt(Double reservesAmt) {
	 	this.reservesAmt = reservesAmt;
	}
	/**
	 * @return 预计折旧期限资产变动使用
	 */
	public String getAssetsTerm() {
	 	return assetsTerm;
	}
	/**
	 * @设置 预计折旧期限资产变动使用
	 * @param assetsTerm
	 */
	public void setAssetsTerm(String assetsTerm) {
	 	this.assetsTerm = assetsTerm;
	}
	/**
	 * @return 减值准备变动后金额资产变动使用
	 */
	public Double getChangeReservesAmt() {
	 	return changeReservesAmt;
	}
	/**
	 * @设置 减值准备变动后金额资产变动使用
	 * @param changeReservesAmt
	 */
	public void setChangeReservesAmt(Double changeReservesAmt) {
	 	this.changeReservesAmt = changeReservesAmt;
	}
	/**
	 * @return 变动后折旧期限资产变动使用
	 */
	public String getChangeAssetsTerm() {
	 	return changeAssetsTerm;
	}
	/**
	 * @设置 变动后折旧期限资产变动使用
	 * @param changeAssetsTerm
	 */
	public void setChangeAssetsTerm(String changeAssetsTerm) {
	 	this.changeAssetsTerm = changeAssetsTerm;
	}
	/**
	 * @return 清理期间清理使用
	 */
	public String getCleanWeek() {
	 	return cleanWeek;
	}
	/**
	 * @设置 清理期间清理使用
	 * @param cleanWeek
	 */
	public void setCleanWeek(String cleanWeek) {
	 	this.cleanWeek = cleanWeek;
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
	 * @return 资产操作类型0：折旧；1：变动；2：清理；
	 */
	public String getAssetsSts() {
	 	return assetsSts;
	}
	/**
	 * @设置 资产操作类型0：折旧；1：变动；2：清理；
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
	 * @return 操作日期
	 */
	public String getTxDate() {
	 	return txDate;
	}
	/**
	 * @设置 操作日期
	 * @param txDate
	 */
	public void setTxDate(String txDate) {
	 	this.txDate = txDate;
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
}