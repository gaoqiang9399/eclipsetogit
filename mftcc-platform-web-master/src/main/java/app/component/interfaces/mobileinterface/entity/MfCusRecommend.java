package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfCusRecommend.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Sep 15 15:33:18 CST 2017
* @version：1.0
**/
public class MfCusRecommend extends BaseDomain {
	private String id;//流水号
	private String rdCusTel;//推荐人手机号
	private String rdCusNo;//推荐人客户号
	private String rdCusName;//推荐人姓名
	private String agenciesUid;//资金机构uid
	private String agenciesName;//资金机构名称
	private String source;//来源（1：微信  2：app）
	private String cusTel;//新增客户手机号
	private String cusNo;//新增客户号
	private String cusName;//新增客户号
	private String rdType;//推荐种类
	private String redDate;//登记日期
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String ext1;//预留字段1   作为打款状态 0 未打款  1 已打款
	private String ext2;//预留字段2   作为打款金额
	private String ext3;//预留字段3
	private String ext4;//预留字段3
	private String ext5;//预留字段5
	private int commend;//推荐客户数量   仅供展示

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
	 * @return 推荐人手机号
	 */
	public String getRdCusTel() {
	 	return rdCusTel;
	}
	/**
	 * @设置 推荐人手机号
	 * @param rdCusTel
	 */
	public void setRdCusTel(String rdCusTel) {
	 	this.rdCusTel = rdCusTel;
	}
	/**
	 * @return 推荐人客户号
	 */
	public String getRdCusNo() {
	 	return rdCusNo;
	}
	/**
	 * @设置 推荐人客户号
	 * @param rdCusNo
	 */
	public void setRdCusNo(String rdCusNo) {
	 	this.rdCusNo = rdCusNo;
	}
	/**
	 * @return 推荐人姓名
	 */
	public String getRdCusName() {
	 	return rdCusName;
	}
	/**
	 * @设置 推荐人姓名
	 * @param rdCusName
	 */
	public void setRdCusName(String rdCusName) {
	 	this.rdCusName = rdCusName;
	}
	/**
	 * @return 来源（1：微信  2：app）
	 */
	public String getSource() {
	 	return source;
	}
	/**
	 * @设置 来源（1：微信  2：app）
	 * @param source
	 */
	public void setSource(String source) {
	 	this.source = source;
	}
	/**
	 * @return 新增客户手机号
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 新增客户手机号
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return 新增客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 新增客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 推荐种类
	 */
	public String getRdType() {
	 	return rdType;
	}
	/**
	 * @设置 推荐种类
	 * @param rdType
	 */
	public void setRdType(String rdType) {
	 	this.rdType = rdType;
	}
	/**
	 * @return 登记日期
	 */
	public String getRedDate() {
	 	return redDate;
	}
	/**
	 * @设置 登记日期
	 * @param redDate
	 */
	public void setRedDate(String redDate) {
	 	this.redDate = redDate;
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
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 预留字段1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 预留字段1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 预留字段2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 预留字段2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 预留字段3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 预留字段3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 预留字段3
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 预留字段3
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 预留字段5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 预留字段5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	public int getCommend() {
		return commend;
	}
	public void setCommend(int commend) {
		this.commend = commend;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getAgenciesUid() {
		return agenciesUid;
	}
	public void setAgenciesUid(String agenciesUid) {
		this.agenciesUid = agenciesUid;
	}
	public String getAgenciesName() {
		return agenciesName;
	}
	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}
	
	
}