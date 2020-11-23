package app.component.finance.assets.entity;
import app.base.BaseDomain;
/**
* Title: CwAssetsClass.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Apr 14 17:21:08 CST 2017
* @version：1.0
**/
public class CwAssetsClass extends BaseDomain {
	private String uuid;//唯一编号
	private String finBooks;//帐套标识
	private String assetsType;//资产分类0：固定资产；1：无形资产
	private String classNo;//类别编号
	private String className;//类别名称
	private String assetsMethod;//折旧方式0：平均年限法；1：不提折旧
	private String assetsYear;//预计折旧年限
	private String assetsTerm;//预计折旧期限
	private String residualRate;//预计净残值率
	private String comAssets;//资产科目
	private String comDepre;//累计折旧科目
	private String itemAssets;//资产科目辅助项
	private String itemDepre;//累计折旧科目辅助项
	private String classSts;//类别状态0：禁用；1：启用
	private String ctDate;//登记日期
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String remark;//备注
	private String occTime;//时间戳最后一次修改日期
	
	public CwAssetsClass() {
	}
	
	public CwAssetsClass(String finBooks) {
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
	 * @return 预计净残值率
	 */
	public String getResidualRate() {
	 	return residualRate;
	}
	/**
	 * @设置 预计净残值率
	 * @param residualRate
	 */
	public void setResidualRate(String residualRate) {
	 	this.residualRate = residualRate;
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
	 * @return 类别状态0：禁用；1：启用
	 */
	public String getClassSts() {
	 	return classSts;
	}
	/**
	 * @设置 类别状态0：禁用；1：启用
	 * @param classSts
	 */
	public void setClassSts(String classSts) {
	 	this.classSts = classSts;
	}
	/**
	 * @return 登记日期
	 */
	public String getCtDate() {
	 	return ctDate;
	}
	/**
	 * @设置 登记日期
	 * @param ctDate
	 */
	public void setCtDate(String ctDate) {
	 	this.ctDate = ctDate;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}