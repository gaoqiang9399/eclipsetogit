package app.component.finance.voucher.entity;
import app.base.BaseDomain;
/**
* Title: CwVchPlateMst.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Mar 07 11:04:27 CST 2017
* @version：1.0
**/
public class CwVchPlateMst extends BaseDomain {
	private String finBooks;//帐套标识
	private String plateNo;//模版编号
	private String plateName;//模版名称
	private String pzProofNo;//凭证字
	private String saveAmt;//保存金额Y：保存；N：不存
	private String saveItem;//保存辅助核算Y：保存；N：不存
	private String tlrno;//操作员
	private String createTime;//创建时间

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
	 * @return 模版名称
	 */
	public String getPlateName() {
	 	return plateName;
	}
	/**
	 * @设置 模版名称
	 * @param plateName
	 */
	public void setPlateName(String plateName) {
	 	this.plateName = plateName;
	}
	/**
	 * @return 凭证字
	 */
	public String getPzProofNo() {
	 	return pzProofNo;
	}
	/**
	 * @设置 凭证字
	 * @param pzProofNo
	 */
	public void setPzProofNo(String pzProofNo) {
	 	this.pzProofNo = pzProofNo;
	}
	/**
	 * @return 保存金额Y：保存；N：不存
	 */
	public String getSaveAmt() {
	 	return saveAmt;
	}
	/**
	 * @设置 保存金额Y：保存；N：不存
	 * @param saveAmt
	 */
	public void setSaveAmt(String saveAmt) {
	 	this.saveAmt = saveAmt;
	}
	/**
	 * @return 保存辅助核算Y：保存；N：不存
	 */
	public String getSaveItem() {
	 	return saveItem;
	}
	/**
	 * @设置 保存辅助核算Y：保存；N：不存
	 * @param saveItem
	 */
	public void setSaveItem(String saveItem) {
	 	this.saveItem = saveItem;
	}
	/**
	 * @return 操作员
	 */
	public String getTlrno() {
	 	return tlrno;
	}
	/**
	 * @设置 操作员
	 * @param tlrno
	 */
	public void setTlrno(String tlrno) {
	 	this.tlrno = tlrno;
	}
	/**
	 * @return 创建时间
	 */
	public String getCreateTime() {
	 	return createTime;
	}
	/**
	 * @设置 创建时间
	 * @param createTime
	 */
	public void setCreateTime(String createTime) {
	 	this.createTime = createTime;
	}
	public String getFinBooks() {
		return finBooks;
	}
	public void setFinBooks(String finBooks) {
		this.finBooks = finBooks;
	}
	
}