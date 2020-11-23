package app.component.thirdservice.lmxd.entity;
import app.base.BaseDomain;
/**
* Title: MfThirdServiceRecord.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Dec 19 15:46:55 CST 2017
* @version：1.0
**/
public class MfThirdServiceRecord extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String id;//流水号
	private String cusNo;//客户号
	private String cusName;//客户名称
	private String appId;//申请号
	private String appName;//项目名称
	private String thirdFlag;//第三方厂商标识
	private String thirdName;//第三方厂商名称
	private String method;//调用的方法名称
	private String methodDes;//调用的方法描述
	private String paramSet;//参数
	private String resultFlag;//成功标识 0-成功 1-失败
	private String statusShow;//状态展示 获取token,运营商数据采集完成,电商数据采集完成,流程完结,报告已生成,查询成功,查询失败
	private String status;//蜜蜂状态0:获取token,1:运营商数据采集完成,2:电商数据采集完成,3流程完结,4:报告已生成
	private String ifHoneybee;//是否是蜜蜂查询 1-是，0-否
	private String resultPath;//结果存放路径（相对路径）
	private String resultPathtype;//路径位置类型（路径的前缀，从配置文件读取）
	private String opNo;//登记人号
	private String opName;//登记人名称
	private String brNo;//登记机构号
	private String brName;//登记机构名称
	private String regTime;//登记时间
	private String regDate;//登记日期

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 客户名称
	 */
	public String getCusName() {
	 	return cusName;
	}
	/**
	 * @设置 客户名称
	 * @param cusName
	 */
	public void setCusName(String cusName) {
	 	this.cusName = cusName;
	}
	/**
	 * @return 申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 项目名称
	 */
	public String getAppName() {
	 	return appName;
	}
	/**
	 * @设置 项目名称
	 * @param appName
	 */
	public void setAppName(String appName) {
	 	this.appName = appName;
	}
	/**
	 * @return 第三方厂商标识
	 */
	public String getThirdFlag() {
	 	return thirdFlag;
	}
	/**
	 * @设置 第三方厂商标识
	 * @param thirdFlag
	 */
	public void setThirdFlag(String thirdFlag) {
	 	this.thirdFlag = thirdFlag;
	}
	/**
	 * @return 第三方厂商名称
	 */
	public String getThirdName() {
	 	return thirdName;
	}
	/**
	 * @设置 第三方厂商名称
	 * @param thirdName
	 */
	public void setThirdName(String thirdName) {
	 	this.thirdName = thirdName;
	}
	/**
	 * @return 调用的方法名称
	 */
	public String getMethod() {
	 	return method;
	}
	/**
	 * @设置 调用的方法名称
	 * @param method
	 */
	public void setMethod(String method) {
	 	this.method = method;
	}
	/**
	 * @return 调用的方法描述
	 */
	public String getMethodDes() {
	 	return methodDes;
	}
	/**
	 * @设置 调用的方法描述
	 * @param methodDes
	 */
	public void setMethodDes(String methodDes) {
	 	this.methodDes = methodDes;
	}
	/**
	 * @return 参数
	 */
	public String getParamSet() {
	 	return paramSet;
	}
	/**
	 * @设置 参数
	 * @param paramSet
	 */
	public void setParamSet(String paramSet) {
	 	this.paramSet = paramSet;
	}
	/**
	 * @return 成功标识 0-成功 1-失败
	 */
	public String getResultFlag() {
	 	return resultFlag;
	}
	/**
	 * @设置 成功标识 0-成功 1-失败
	 * @param resultFlag
	 */
	public void setResultFlag(String resultFlag) {
	 	this.resultFlag = resultFlag;
	}
	/**
	 * @return 结果存放路径（相对路径）
	 */
	public String getResultPath() {
	 	return resultPath;
	}
	/**
	 * @设置 结果存放路径（相对路径）
	 * @param resultPath
	 */
	public void setResultPath(String resultPath) {
	 	this.resultPath = resultPath;
	}
	/**
	 * @return 路径位置类型（路径的前缀，从配置文件读取）
	 */
	public String getResultPathtype() {
	 	return resultPathtype;
	}
	/**
	 * @设置 路径位置类型（路径的前缀，从配置文件读取）
	 * @param resultPathtype
	 */
	public void setResultPathtype(String resultPathtype) {
	 	this.resultPathtype = resultPathtype;
	}
	/**
	 * @return 登记人号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记机构号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记机构号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记机构名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记机构名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 登记日期
	 */
	public String getRegDate() {
	 	return regDate;
	}
	/**
	 * @设置 登记日期
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
	 	this.regDate = regDate;
	}
	public String getStatusShow() {
		return statusShow;
	}
	public void setStatusShow(String statusShow) {
		this.statusShow = statusShow;
	}
	public String getIfHoneybee() {
		return ifHoneybee;
	}
	public void setIfHoneybee(String ifHoneybee) {
		this.ifHoneybee = ifHoneybee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}