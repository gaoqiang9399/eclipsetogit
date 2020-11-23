package app.component.pms.entity;
import app.base.BaseDomain;
/**
* Title: PmsFilterRel.java
* Description:
* @author：@dhcc.com.cn
* @Fri May 13 01:04:42 GMT 2016
* @version：1.0
**/
public class PmsFilterRel extends BaseDomain {
	private String relNo;//编号
	private String actionJspSour;//源请求
	private String actionJspComp;//关联请求

	/**
	 * @return 编号
	 */
	public String getRelNo() {
	 	return relNo;
	}
	/**
	 * @设置 编号
	 * @param relNo
	 */
	public void setRelNo(String relNo) {
	 	this.relNo = relNo;
	}
	/**
	 * @return 源请求
	 */
	public String getActionJspSour() {
	 	return actionJspSour;
	}
	/**
	 * @设置 源请求
	 * @param actionJspSour
	 */
	public void setActionJspSour(String actionJspSour) {
	 	this.actionJspSour = actionJspSour;
	}
	/**
	 * @return 关联请求
	 */
	public String getActionJspComp() {
	 	return actionJspComp;
	}
	/**
	 * @设置 关联请求
	 * @param actionJspComp
	 */
	public void setActionJspComp(String actionJspComp) {
	 	this.actionJspComp = actionJspComp;
	}
}