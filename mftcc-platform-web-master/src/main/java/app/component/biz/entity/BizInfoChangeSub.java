package app.component.biz.entity;
import app.base.BaseDomain;
/**
* Title: BizInfoChangeSub.java
* Description:
* @author：@dhcc.com.cn
* @Wed Apr 20 06:09:29 GMT 2016
* @version：1.0
**/
public class BizInfoChangeSub extends BaseDomain {
	private String id;//编号
	private String changeNo;//变更号
	private String cont;//内容

	/**
	 * @return 编号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 编号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 变更号
	 */
	public String getChangeNo() {
	 	return changeNo;
	}
	/**
	 * @设置 变更号
	 * @param changeNo
	 */
	public void setChangeNo(String changeNo) {
	 	this.changeNo = changeNo;
	}
	/**
	 * @return 内容
	 */
	public String getCont() {
	 	return cont;
	}
	/**
	 * @设置 内容
	 * @param cont
	 */
	public void setCont(String cont) {
	 	this.cont = cont;
	}
}