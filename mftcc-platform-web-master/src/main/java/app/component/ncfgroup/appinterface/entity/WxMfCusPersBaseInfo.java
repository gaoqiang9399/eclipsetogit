package app.component.ncfgroup.appinterface.entity;
import app.base.BaseDomain;
/**
* Title: MfCusPersBaseInfo.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon May 30 08:58:15 CST 2016
* @version：1.0
**/
public class WxMfCusPersBaseInfo extends BaseDomain {
	private String cusNo;//客户ID
	private String cusName;//客户名称
	private String idType;//证件类型（身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 ）
	private String idNum;//证件号码
	private String sex;//性别（男/女）
	private String brithday;//出生日期
	private String gradeModel;//评分模型（城户,农户,工商户）
	private String nationality;//民族
	private String ifParty;//政治面貌（党员，群众）
	private String resideSta;//居住状况（自置,按揭,砖房,土坯房,无自有住房,亲属楼宇,集体宿舍,租房,共有住宅,其他,未知）
	private String regHome;//户口性质（常驻户口，临时户口）
	private String marrige;//婚姻状况（已婚有子女,已婚无子女,未婚,其他）
	private String education;//最高学历（研究生,大学本科,大学专科和专科学校,中等专业学校或中等技术学校,技术学校,高中,初中,小学,文盲或半文盲,未知）
	private String healthStat;//个人健康状况（良好,一般,较差）
	private String cusTel;//手机号码
	private String emergencyContactName ;//紧急联系人
	private String emergencyContactTel;//紧急联系人电话
	private String regHomeAddre ;//户籍地址
	private String addreTel;//住址电话
	private String postalCode;//邮政编号
	private String email;//邮箱地址
	private String companyTel;//单位电话
	private String careaProvice;//客户所属地区
	private String careaCity;//客户所属市
	private String careaCounty;//客户所属县
	private String commAddress;//通讯地址
	private String remark;//备注
	private String cusMngNo;//客户经理编号
	private String cusMngName;//客户经理姓名
	private String recommenderName;//推荐人姓名
	private String recommenderIdNum;//推荐人证件号码
	private String regDate;// 登记日期
	private String lstModDate;// 最后更新日期
	private String regTime;// 登记时间
	private String lstModTime;// 最后更新时间
	private String infoOffer;//信息来源
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String ext1;//预留字段
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String ext6;//
	private String ext7;//
	private String ext8;//
	private String ext9;//
	private String ext10;//
	private String qq;//qq号
	private String weChat;//微信
	private String microBlog;//微博
	//单位信息
	private String companyAddress;//单位地址
	private String companyName;//单位电话
	private String workYears;//工作年限
	private String companyScale;//公司规模
	private String companyDuty;//职务
	private String incomeBeforeTax;//税前收入

	/**
	 * @return 客户ID
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户ID
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
	 * @return 证件类型（身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 ）
	 */
	public String getIdType() {
	 	return idType;
	}
	/**
	 * @设置 证件类型（身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 ）
	 * @param idType
	 */
	public void setIdType(String idType) {
	 	this.idType = idType;
	}
	/**
	 * @return 证件号码
	 */
	public String getIdNum() {
	 	return idNum;
	}
	/**
	 * @设置 证件号码
	 * @param idNum
	 */
	public void setIdNum(String idNum) {
	 	this.idNum = idNum;
	}
	/**
	 * @return 性别（男/女）
	 */
	public String getSex() {
	 	return sex;
	}
	/**
	 * @设置 性别（男/女）
	 * @param sex
	 */
	public void setSex(String sex) {
	 	this.sex = sex;
	}
	/**
	 * @return 出生日期
	 */
	public String getBrithday() {
	 	return brithday;
	}
	/**
	 * @设置 出生日期
	 * @param brithday
	 */
	public void setBrithday(String brithday) {
	 	this.brithday = brithday;
	}
	/**
	 * @return 评分模型（城户,农户,工商户）
	 */
	public String getGradeModel() {
	 	return gradeModel;
	}
	/**
	 * @设置 评分模型（城户,农户,工商户）
	 * @param gradeModel
	 */
	public void setGradeModel(String gradeModel) {
	 	this.gradeModel = gradeModel;
	}
	/**
	 * @return 民族
	 */
	public String getNationality() {
	 	return nationality;
	}
	/**
	 * @设置 民族
	 * @param nationality
	 */
	public void setNationality(String nationality) {
	 	this.nationality = nationality;
	}
	/**
	 * @return 政治面貌（党员，群众）
	 */
	public String getIfParty() {
	 	return ifParty;
	}
	/**
	 * @设置 政治面貌（党员，群众）
	 * @param ifParty
	 */
	public void setIfParty(String ifParty) {
	 	this.ifParty = ifParty;
	}
	/**
	 * @return 居住状况（自置,按揭,砖房,土坯房,无自有住房,亲属楼宇,集体宿舍,租房,共有住宅,其他,未知）
	 */
	public String getResideSta() {
	 	return resideSta;
	}
	/**
	 * @设置 居住状况（自置,按揭,砖房,土坯房,无自有住房,亲属楼宇,集体宿舍,租房,共有住宅,其他,未知）
	 * @param resideSta
	 */
	public void setResideSta(String resideSta) {
	 	this.resideSta = resideSta;
	}
	/**
	 * @return 户口性质（常驻户口，临时户口）
	 */
	public String getRegHome() {
	 	return regHome;
	}
	/**
	 * @设置 户口性质（常驻户口，临时户口）
	 * @param regHome
	 */
	public void setRegHome(String regHome) {
	 	this.regHome = regHome;
	}
	/**
	 * @return 婚姻状况（已婚有子女,已婚无子女,未婚,其他）
	 */
	public String getMarrige() {
	 	return marrige;
	}
	/**
	 * @设置 婚姻状况（已婚有子女,已婚无子女,未婚,其他）
	 * @param marrige
	 */
	public void setMarrige(String marrige) {
	 	this.marrige = marrige;
	}
	/**
	 * @return 最高学历（研究生,大学本科,大学专科和专科学校,中等专业学校或中等技术学校,技术学校,高中,初中,小学,文盲或半文盲,未知）
	 */
	public String getEducation() {
	 	return education;
	}
	/**
	 * @设置 最高学历（研究生,大学本科,大学专科和专科学校,中等专业学校或中等技术学校,技术学校,高中,初中,小学,文盲或半文盲,未知）
	 * @param education
	 */
	public void setEducation(String education) {
	 	this.education = education;
	}
	/**
	 * @return 个人健康状况（良好,一般,较差）
	 */
	public String getHealthStat() {
	 	return healthStat;
	}
	/**
	 * @设置 个人健康状况（良好,一般,较差）
	 * @param healthStat
	 */
	public void setHealthStat(String healthStat) {
	 	this.healthStat = healthStat;
	}
	/**
	 * @return 手机号码
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 手机号码
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return 住址电话
	 */
	public String getAddreTel() {
	 	return addreTel;
	}
	/**
	 * @设置 住址电话
	 * @param addreTel
	 */
	public void setAddreTel(String addreTel) {
	 	this.addreTel = addreTel;
	}
	/**
	 * @return 邮政编号
	 */
	public String getPostalCode() {
	 	return postalCode;
	}
	/**
	 * @设置 邮政编号
	 * @param postalCode
	 */
	public void setPostalCode(String postalCode) {
	 	this.postalCode = postalCode;
	}
	/**
	 * @return 邮箱地址
	 */
	public String getEmail() {
	 	return email;
	}
	/**
	 * @设置 邮箱地址
	 * @param email
	 */
	public void setEmail(String email) {
	 	this.email = email;
	}
	/**
	 * @return 单位电话
	 */
	public String getCompanyTel() {
	 	return companyTel;
	}
	/**
	 * @设置 单位电话
	 * @param companyTel
	 */
	public void setCompanyTel(String companyTel) {
	 	this.companyTel = companyTel;
	}
	/**
	 * @return 客户所属地区
	 */
	public String getCareaProvice() {
	 	return careaProvice;
	}
	/**
	 * @设置 客户所属地区
	 * @param careaProvice
	 */
	public void setCareaProvice(String careaProvice) {
	 	this.careaProvice = careaProvice;
	}
	/**
	 * @return 客户所属市
	 */
	public String getCareaCity() {
	 	return careaCity;
	}
	/**
	 * @设置 客户所属市
	 * @param careaCity
	 */
	public void setCareaCity(String careaCity) {
	 	this.careaCity = careaCity;
	}
	/**
	 * @return 客户所属县
	 */
	public String getCareaCounty() {
	 	return careaCounty;
	}
	/**
	 * @设置 客户所属县
	 * @param careaCounty
	 */
	public void setCareaCounty(String careaCounty) {
	 	this.careaCounty = careaCounty;
	}
	/**
	 * @return 通讯地址
	 */
	public String getCommAddress() {
	 	return commAddress;
	}
	/**
	 * @设置 通讯地址
	 * @param mailingAddress
	 */
	public void setCommAddress(String commAddress) {
	 	this.commAddress = commAddress;
	}
	/**
	 * @return 备注
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
	 	this.remark = remark;
	}
	/**
	 * @return 客户经理编号
	 */
	public String getCusMngNo() {
	 	return cusMngNo;
	}
	/**
	 * @设置 客户经理编号
	 * @param cusMngNo
	 */
	public void setCusMngNo(String cusMngNo) {
	 	this.cusMngNo = cusMngNo;
	}
	/**
	 * @return 客户经理姓名
	 */
	public String getCusMngName() {
	 	return cusMngName;
	}
	/**
	 * @设置 客户经理姓名
	 * @param cusMngName
	 */
	public void setCusMngName(String cusMngName) {
	 	this.cusMngName = cusMngName;
	}
	/**
	 * @return 推荐人姓名
	 */
	public String getRecommenderName() {
	 	return recommenderName;
	}
	/**
	 * @设置 推荐人姓名
	 * @param recommenderName
	 */
	public void setRecommenderName(String recommenderName) {
	 	this.recommenderName = recommenderName;
	}
	/**
	 * @return 推荐人证件号码
	 */
	public String getRecommenderIdNum() {
	 	return recommenderIdNum;
	}
	/**
	 * @设置 推荐人证件号码
	 * @param recommenderIdNum
	 */
	public void setRecommenderIdNum(String recommenderIdNum) {
	 	this.recommenderIdNum = recommenderIdNum;
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
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 预留字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 预留字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getEmergencyContactName() {
		return emergencyContactName;
	}
	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}
	public String getEmergencyContactTel() {
		return emergencyContactTel;
	}
	public void setEmergencyContactTel(String emergencyContactTel) {
		this.emergencyContactTel = emergencyContactTel;
	}
	public String getRegHomeAddre() {
		return regHomeAddre;
	}
	public void setRegHomeAddre(String regHomeAddre) {
		this.regHomeAddre = regHomeAddre;
	}
	public String getInfoOffer() {
		return infoOffer;
	}
	public void setInfoOffer(String infoOffer) {
		this.infoOffer = infoOffer;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public String getMicroBlog() {
		return microBlog;
	}
	public void setMicroBlog(String microBlog) {
		this.microBlog = microBlog;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLstModDate() {
		return lstModDate;
	}
	public void setLstModDate(String lstModDate) {
		this.lstModDate = lstModDate;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getWorkYears() {
		return workYears;
	}
	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}
	public String getCompanyScale() {
		return companyScale;
	}
	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}
	public String getCompanyDuty() {
		return companyDuty;
	}
	public void setCompanyDuty(String companyDuty) {
		this.companyDuty = companyDuty;
	}
	public String getIncomeBeforeTax() {
		return incomeBeforeTax;
	}
	public void setIncomeBeforeTax(String incomeBeforeTax) {
		this.incomeBeforeTax = incomeBeforeTax;
	}
	
	
	
}