package app.component.thirdservice.cloudmftcc.entity;
import app.base.BaseDomain;
/**
* Title: MfThirdMftccHighcourt.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Jan 08 14:16:19 CST 2018
* @version：1.0
**/
public class MfThirdMftccHighcourt extends BaseDomain {
	private String busId;//业务系统中的主键：客户号、担保人号、社会关系主键等
	private String busType;//业务主键的类型：1：客户、2：担保人、3：社会关系、
	private String busName;//被查询人名称
	private String busNumber;//被查询人号码：身份证号、社会信用代码等
	private String relId;//关联id：关联的客户号或申请号
	private String relType;//关联类型：0:客户本人、1：保证人、2：社会关系
	private String requestParam;//请求报文
	private String result;//请求结果

	/**
	 * @return 业务系统中的主键：客户号、担保人号、社会关系主键等
	 */
	public String getBusId() {
	 	return busId;
	}
	/**
	 * @设置 业务系统中的主键：客户号、担保人号、社会关系主键等
	 * @param busId
	 */
	public void setBusId(String busId) {
	 	this.busId = busId;
	}
	/**
	 * @return 业务主键的类型：1：客户、2：担保人、3：社会关系、
	 */
	public String getBusType() {
	 	return busType;
	}
	/**
	 * @设置 业务主键的类型：1：客户、2：担保人、3：社会关系、
	 * @param busType
	 */
	public void setBusType(String busType) {
	 	this.busType = busType;
	}
	/**
	 * @return 被查询人名称
	 */
	public String getBusName() {
	 	return busName;
	}
	/**
	 * @设置 被查询人名称
	 * @param busName
	 */
	public void setBusName(String busName) {
	 	this.busName = busName;
	}
	/**
	 * @return 被查询人号码：身份证号、社会信用代码等
	 */
	public String getBusNumber() {
	 	return busNumber;
	}
	/**
	 * @设置 被查询人号码：身份证号、社会信用代码等
	 * @param busNumber
	 */
	public void setBusNumber(String busNumber) {
	 	this.busNumber = busNumber;
	}
	/**
	 * @return 关联id：关联的客户号或申请号
	 */
	public String getRelId() {
	 	return relId;
	}
	/**
	 * @设置 关联id：关联的客户号或申请号
	 * @param relId
	 */
	public void setRelId(String relId) {
	 	this.relId = relId;
	}
	/**
	 * @return 关联类型：0:客户本人、1：保证人、2：社会关系
	 */
	public String getRelType() {
	 	return relType;
	}
	/**
	 * @设置 关联类型：0:客户本人、1：保证人、2：社会关系
	 * @param relType
	 */
	public void setRelType(String relType) {
	 	this.relType = relType;
	}
	/**
	 * @return 请求报文
	 */
	public String getRequestParam() {
	 	return requestParam;
	}
	/**
	 * @设置 请求报文
	 * @param requestParam
	 */
	public void setRequestParam(String requestParam) {
	 	this.requestParam = requestParam;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}