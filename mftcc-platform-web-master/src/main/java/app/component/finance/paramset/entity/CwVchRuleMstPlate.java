package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwVchRuleMstPlate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Mar 07 14:23:55 CST 2017
* @version：1.0
**/
/**
 * 类名： CwVchRuleMstPlate
 * 描述：
 * @author lzshuai
 * @date 2017-3-8 上午11:58:46
 * 
 *
 */
public class CwVchRuleMstPlate extends BaseDomain {
	private String uuid;//uuid
	private String txCode;//交易代码
	private String txName;//交易代码名称
	private Double sort;//顺序
	private String txType;//交易类型01：财务；02：信贷
	private String isAuto;//是否可用Y：启用；N：禁用
	private String plateNo;//模版编号
	private String parmType;//分类编号
	private String finBooks;//帐套标识

	//以下数据库中没有此字段
	private String adaptName;//适应交易
	private String pzName;//凭证字
	private String txTypeName;//交易类型名称：01：财务；02：信贷
	
	/**
	 * @return uuid
	 */
	public String getUuid() {
	 	return uuid;
	}
	/**
	 * @设置 uuid
	 * @param uuid
	 */
	public void setUuid(String uuid) {
	 	this.uuid = uuid;
	}
	/**
	 * @return 交易代码
	 */
	public String getTxCode() {
	 	return txCode;
	}
	/**
	 * @设置 交易代码
	 * @param txCode
	 */
	public void setTxCode(String txCode) {
	 	this.txCode = txCode;
	}
	/**
	 * @return 交易代码名称
	 */
	public String getTxName() {
	 	return txName;
	}
	/**
	 * @设置 交易代码名称
	 * @param txName
	 */
	public void setTxName(String txName) {
	 	this.txName = txName;
	}
	/**
	 * @return 顺序
	 */
	public Double getSort() {
	 	return sort;
	}
	/**
	 * @设置 顺序
	 * @param sort
	 */
	public void setSort(Double sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 交易类型01：财务；02：信贷
	 */
	public String getTxType() {
	 	return txType;
	}
	/**
	 * @设置 交易类型01：财务；02：信贷
	 * @param txType
	 */
	public void setTxType(String txType) {
	 	this.txType = txType;
	}
	/**
	 * @return 是否可用Y：启用；N：禁用
	 */
	public String getIsAuto() {
	 	return isAuto;
	}
	/**
	 * @设置 是否可用Y：启用；N：禁用
	 * @param isAuto
	 */
	public void setIsAuto(String isAuto) {
	 	this.isAuto = isAuto;
	}
	/**
	 * @return 模版编号
	 */
	public String getPlateNo() {
	 	return plateNo;
	}
	/**
	 * @设置 模版编号
	 * @param plateNo
	 */
	public void setPlateNo(String plateNo) {
	 	this.plateNo = plateNo;
	}
	/**
	 * @return 分类编号
	 */
	public String getParmType() {
	 	return parmType;
	}
	/**
	 * @设置 分类编号
	 * @param parmType
	 */
	public void setParmType(String parmType) {
	 	this.parmType = parmType;
	}
	public String getAdaptName() {
		return adaptName;
	}
	public void setAdaptName(String adaptName) {
		this.adaptName = adaptName;
	}
	public String getPzName() {
		return pzName;
	}
	public void setPzName(String pzName) {
		this.pzName = pzName;
	}
	public String getTxTypeName() {
		return txTypeName;
	}
	public void setTxTypeName(String txTypeName) {
		this.txTypeName = txTypeName;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
}