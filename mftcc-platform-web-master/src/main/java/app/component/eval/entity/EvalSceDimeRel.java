package app.component.eval.entity;
import app.base.BaseDomain;
/**
* Title: EvalSceDimeRel.java
* Description:
* @author：@dhcc.com.cn
* @Thu Mar 31 12:04:33 GMT 2016
* @version：1.0
**/
public class EvalSceDimeRel extends BaseDomain {
	private String scenceNo;//场景编号
	private String optCode;//对应数据字典编号

	/**
	 * @return 场景编号
	 */
	public String getScenceNo() {
	 	return scenceNo;
	}
	/**
	 * @设置 场景编号
	 * @param scenceNo
	 */
	public void setScenceNo(String scenceNo) {
	 	this.scenceNo = scenceNo;
	}
	/**
	 * @return 对应数据字典编号
	 */
	public String getOptCode() {
	 	return optCode;
	}
	/**
	 * @设置 对应数据字典编号
	 * @param optCode
	 */
	public void setOptCode(String optCode) {
	 	this.optCode = optCode;
	}
}