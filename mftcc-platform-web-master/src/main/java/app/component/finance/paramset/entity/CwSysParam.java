package app.component.finance.paramset.entity;
import app.base.BaseDomain;
/**
* Title: CwSysParam.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Dec 30 09:47:39 CST 2016
* @version：1.0
**/
public class CwSysParam extends BaseDomain {
	private String finBooks;//帐套标识
	private String pcode;//参数编号使用5位(参数化序列号)
	private String pname;//参数名称
	private String pvalue;//参数化值
	private String remarks;//参数描述说明
	private String isFlag;//是否启用Y：是；N：否；
	private String isShow;//是否对外展示Y：是；N：否；
	private String ptype;//文本类型select：下拉框
	private String params;//类型参数参数
	private String occTime;//时间戳
	
	
	private String jiTiType;//计提方式
	private String allJiTi;//全部提取比例
	private String zcJiTi;//正常提取比例
	private String gzJiTi;//关注提取比例
	private String cjJiTi;//次级提取比例
	private String kyJiTi;//可疑提取比例
	private String ssJiTi;//损失提取比例
	

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
	 * @return 参数编号使用5位
	 */
	public String getPcode() {
	 	return pcode;
	}
	/**
	 * @设置 参数编号使用5位
	 * @param pcode
	 */
	public void setPcode(String pcode) {
	 	this.pcode = pcode;
	}
	/**
	 * @return 参数名称
	 */
	public String getPname() {
	 	return pname;
	}
	/**
	 * @设置 参数名称
	 * @param pname
	 */
	public void setPname(String pname) {
	 	this.pname = pname;
	}
	/**
	 * @return 参数化值
	 */
	public String getPvalue() {
	 	return pvalue;
	}
	/**
	 * @设置 参数化值
	 * @param pvalue
	 */
	public void setPvalue(String pvalue) {
	 	this.pvalue = pvalue;
	}
	/**
	 * @return 参数描述说明
	 */
	public String getRemarks() {
	 	return remarks;
	}
	/**
	 * @设置 参数描述说明
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
	 	this.remarks = remarks;
	}
	/**
	 * @return 是否启用Y：是；N：否；
	 */
	public String getIsFlag() {
	 	return isFlag;
	}
	/**
	 * @设置 是否启用Y：是；N：否；
	 * @param isFlag
	 */
	public void setIsFlag(String isFlag) {
	 	this.isFlag = isFlag;
	}
	/**
	 * @return 是否对外展示Y：是；N：否；
	 */
	public String getIsShow() {
	 	return isShow;
	}
	/**
	 * @设置 是否对外展示Y：是；N：否；
	 * @param isShow
	 */
	public void setIsShow(String isShow) {
	 	this.isShow = isShow;
	}
	/**
	 * @return 文本类型select：下拉框
	 */
	public String getPtype() {
	 	return ptype;
	}
	/**
	 * @设置 文本类型select：下拉框
	 * @param ptype
	 */
	public void setPtype(String ptype) {
	 	this.ptype = ptype;
	}
	/**
	 * @return 类型参数参数
	 */
	public String getParams() {
	 	return params;
	}
	/**
	 * @设置 类型参数参数
	 * @param params
	 */
	public void setParams(String params) {
	 	this.params = params;
	}
	/**
	 * @return 时间戳
	 */
	public String getOccTime() {
	 	return occTime;
	}
	/**
	 * @设置 时间戳
	 * @param occTime
	 */
	public void setOccTime(String occTime) {
	 	this.occTime = occTime;
	}
	public String getJiTiType() {
		return jiTiType;
	}
	public void setJiTiType(String jiTiType) {
		this.jiTiType = jiTiType;
	}
	public String getAllJiTi() {
		return allJiTi;
	}
	public void setAllJiTi(String allJiTi) {
		this.allJiTi = allJiTi;
	}
	public String getZcJiTi() {
		return zcJiTi;
	}
	public void setZcJiTi(String zcJiTi) {
		this.zcJiTi = zcJiTi;
	}
	public String getGzJiTi() {
		return gzJiTi;
	}
	public void setGzJiTi(String gzJiTi) {
		this.gzJiTi = gzJiTi;
	}
	public String getCjJiTi() {
		return cjJiTi;
	}
	public void setCjJiTi(String cjJiTi) {
		this.cjJiTi = cjJiTi;
	}
	public String getKyJiTi() {
		return kyJiTi;
	}
	public void setKyJiTi(String kyJiTi) {
		this.kyJiTi = kyJiTi;
	}
	public String getSsJiTi() {
		return ssJiTi;
	}
	public void setSsJiTi(String ssJiTi) {
		this.ssJiTi = ssJiTi;
	}

	
}