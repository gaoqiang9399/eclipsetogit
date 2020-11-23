package app.component.oa.archive.entity;
import app.base.BaseDomain;
/**
* Title: MfOaArchivesBase.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 22 12:13:41 CST 2017
* @version：1.0
**/
public class MfOaArchivesBase extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String opNo;//员工编号
	private String baseId;//基本信息ID
	private String opName;//姓名
	private String brNo;//部门编号
	private String brName;//部门名称
	private String opSts;//在职状态
	private String useFlag;//启用状态
	private String position;//职级（经理，主任等）
	private String positionShow;//职级（经理，主任等）展示
	private String registerName;//登记此员工信息的管理员
	private String registerNo;//添加此人员操作员编号
	private String birthday;//出生日期
	private String email;//出生日期
	private String healthSts;//身体状况（数据字典）
	private String marriageSts;//婚姻状态
	private String sex;//性别
	private String sexShow;//性别展示
	private String nation;//民族
	private String englishName;//英文名
	private String nativePlace;//籍贯
	private String jobTitle;//职称
	private String ifParty;//政治面貌(团员党员群众)
	private String workDate;//参加工作时间
	private String education;//学历：0-未知；1-研究生；2-大学本科；3-大学专科和专科学校；4-中等专业学校或中等技术学校；5-技术学校；6-高中；7-初中；8-小学；9-文盲或半文盲；
	private String registeredAddr;//户口地址
	private String idNum;//身份证号
	private String socialSecurity;//是否交过社保
	private String homeTel;//家庭电话
	private String tel;//手机
	private String insuranceAddr;//保险所在地
	private String archiveAddr;//档案所在地
	private String workCartificate;//是否有就业证
	private String liveCartificate;//是否有居住证
	private String emergencyContacts;//紧急联系人
	private String emergencyContactsPlane;//紧急联系人电话
	private String emergencyContactsTel;//紧急联系人手机
	private String hireDate;//入职时间
	private String inseranceFund;//是否缴纳五险一金
	private String liveCondition;//目前居住情况
	private String addr;//地址
	private String nowSalary;//目前薪水
	private String updalaryDate;//上次调薪时间
	private String localPurchase;//是否有意在本地买房
	private String houseAddr;//地址
	private String single;//是否单身
	private String loverName;//恋人姓名
	private Double loverIncome;//恋人收入
	private String loverAddr;//恋人地址
	private String loverRemark;//恋人备注
	private String fatherCondition;//父亲情况
	private String motherCondition;//母亲情况
	private String homeCondition;//家庭情况
	private String remark;//备注
	private String regTime;//登记时间
	private String lstModTime;//最后一次修改时间
	private String headImg;//头像名称
	private String ifUploadHead;//是否上传
	private String height;//身高
	private String homeAddr;//家庭详细地址
	private String applyChannel;//应聘渠道：0-公开招募；1-媒体招募；2-同事引荐；3-亲属介绍；4-其他渠道
	private String major;//专业
	private String graduateSchool;//毕业学校
	private String constellation;//星座：01-白羊座；02-金牛座；03-双子座；04-巨蟹座；05-狮子座；06-处女座；07-天秤座；08-天蝎座；09-射手座；10-摩羯座；11-水瓶座；12-双鱼座；
	private String bloodType;//血型：1-A型；2-B型；3-AB型；4-O型；
	private String referrer;//推荐人
	private Double wage;//薪资待遇
	private Double probationaryWage;//试用期待遇
	private String accountNo;//银行卡号
	private String bank;//所属银行，支行
	private String employeeType;//员工类型：1-全职；2-兼职
	private String employeeTypeUpdateDate;//员工类型变更日期
	private String isRelation;//是否有亲戚或朋友在我司任职：1-是；0-否；
	private String relationName;//在我司任职的亲戚或者朋友的姓名
	private String relationBrName;//在我司任职的亲戚或者朋友的部门
	private String relation;//在我司任职的亲戚或者朋友的关系
	private String relationPhone;//在我司任职的亲戚或者朋友的关系的联系方式
	private String relationBrNo;//在我司任职的亲戚或者朋友的部门编号
	private String interest;//兴趣爱好
	private String registeredType;//户口类型：1-农业户口；2-非农业户口；
	private String emergencyContactsRelishion;//紧急联系人称谓
	private String emergencyContactsProfession;//紧急联系人所从事职业
	private String emergencyContactsWorkAddr;//紧急联系人工作单位
	private String otherEnglishLevel;//英语水平：1-四级；2-六级；3-其他；
	private String englishLevel;//外语水平其他
	private String computerSkill;//计算机技能：1-精通；2-熟练；3-一般；
	private String drivingLicense;//是否有驾照：1-有；0-没有；
	private String drivingLicenseName;//驾照名称
	private String otherCredential;//其他证书
	private String ifUpdateHeadImg;//是否更新头像
	private String state;//入职申请说明
	/**
	 * @return 员工编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 员工编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 在职状态
	 */
	public String getOpSts() {
	 	return opSts;
	}
	/**
	 * @设置 在职状态
	 * @param opSts
	 */
	public void setOpSts(String opSts) {
	 	this.opSts = opSts;
	}
	/**
	 * @return 职级（经理，主任等）
	 */
	public String getPosition() {
	 	return position;
	}
	/**
	 * @设置 职级（经理，主任等）
	 * @param position
	 */
	public void setPosition(String position) {
	 	this.position = position;
	}
	/**
	 * @return 登记此员工信息的管理员
	 */
	public String getRegisterName() {
	 	return registerName;
	}
	/**
	 * @设置 登记此员工信息的管理员
	 * @param registerName
	 */
	public void setRegisterName(String registerName) {
	 	this.registerName = registerName;
	}
	/**
	 * @return 添加此人员操作员编号
	 */
	public String getRegisterNo() {
	 	return registerNo;
	}
	/**
	 * @设置 添加此人员操作员编号
	 * @param registerNo
	 */
	public void setRegisterNo(String registerNo) {
	 	this.registerNo = registerNo;
	}
	/**
	 * @return 出生日期
	 */
	public String getBirthday() {
	 	return birthday;
	}
	/**
	 * @设置 出生日期
	 * @param birthday
	 */
	public void setBirthday(String birthday) {
	 	this.birthday = birthday;
	}
	/**
	 * @return 身体状况（数据字典）
	 */
	public String getHealthSts() {
	 	return healthSts;
	}
	/**
	 * @设置 身体状况（数据字典）
	 * @param healthSts
	 */
	public void setHealthSts(String healthSts) {
	 	this.healthSts = healthSts;
	}
	/**
	 * @return 婚姻状态
	 */
	public String getMarriageSts() {
	 	return marriageSts;
	}
	/**
	 * @设置 婚姻状态
	 * @param marriageSts
	 */
	public void setMarriageSts(String marriageSts) {
	 	this.marriageSts = marriageSts;
	}
	/**
	 * @return 性别
	 */
	public String getSex() {
	 	return sex;
	}
	/**
	 * @设置 性别
	 * @param sex
	 */
	public void setSex(String sex) {
	 	this.sex = sex;
	}
	/**
	 * @return 民族
	 */
	public String getNation() {
	 	return nation;
	}
	/**
	 * @设置 民族
	 * @param nation
	 */
	public void setNation(String nation) {
	 	this.nation = nation;
	}
	/**
	 * @return 英文名
	 */
	public String getEnglishName() {
	 	return englishName;
	}
	/**
	 * @设置 英文名
	 * @param englishName
	 */
	public void setEnglishName(String englishName) {
	 	this.englishName = englishName;
	}
	/**
	 * @return 籍贯
	 */
	public String getNativePlace() {
	 	return nativePlace;
	}
	/**
	 * @设置 籍贯
	 * @param nativePlace
	 */
	public void setNativePlace(String nativePlace) {
	 	this.nativePlace = nativePlace;
	}
	/**
	 * @return 职称
	 */
	public String getJobTitle() {
	 	return jobTitle;
	}
	/**
	 * @设置 职称
	 * @param jobTitle
	 */
	public void setJobTitle(String jobTitle) {
	 	this.jobTitle = jobTitle;
	}
	/**
	 * @return 政治面貌(团员党员群众)
	 */
	public String getIfParty() {
	 	return ifParty;
	}
	/**
	 * @设置 政治面貌(团员党员群众)
	 * @param ifParty
	 */
	public void setIfParty(String ifParty) {
	 	this.ifParty = ifParty;
	}
	/**
	 * @return 参加工作时间
	 */
	public String getWorkDate() {
	 	return workDate;
	}
	/**
	 * @设置 参加工作时间
	 * @param workDate
	 */
	public void setWorkDate(String workDate) {
	 	this.workDate = workDate;
	}
	/**
	 * @return 文化程度
	 */
	public String getEducation() {
	 	return education;
	}
	/**
	 * @设置 文化程度
	 * @param education
	 */
	public void setEducation(String education) {
	 	this.education = education;
	}
	/**
	 * @return 户口地址
	 */
	public String getRegisteredAddr() {
	 	return registeredAddr;
	}
	/**
	 * @设置 户口地址
	 * @param registeredAddr
	 */
	public void setRegisteredAddr(String registeredAddr) {
	 	this.registeredAddr = registeredAddr;
	}
	/**
	 * @return 身份证号
	 */
	public String getIdNum() {
	 	return idNum;
	}
	/**
	 * @设置 身份证号
	 * @param idNum
	 */
	public void setIdNum(String idNum) {
	 	this.idNum = idNum;
	}
	/**
	 * @return 是否交过社保
	 */
	public String getSocialSecurity() {
	 	return socialSecurity;
	}
	/**
	 * @设置 是否交过社保
	 * @param socialSecurity
	 */
	public void setSocialSecurity(String socialSecurity) {
	 	this.socialSecurity = socialSecurity;
	}
	/**
	 * @return 家庭电话
	 */
	public String getHomeTel() {
	 	return homeTel;
	}
	/**
	 * @设置 家庭电话
	 * @param homeTel
	 */
	public void setHomeTel(String homeTel) {
	 	this.homeTel = homeTel;
	}
	/**
	 * @return 手机
	 */
	public String getTel() {
	 	return tel;
	}
	/**
	 * @设置 手机
	 * @param tel
	 */
	public void setTel(String tel) {
	 	this.tel = tel;
	}
	/**
	 * @return 保险所在地
	 */
	public String getInsuranceAddr() {
	 	return insuranceAddr;
	}
	/**
	 * @设置 保险所在地
	 * @param insuranceAddr
	 */
	public void setInsuranceAddr(String insuranceAddr) {
	 	this.insuranceAddr = insuranceAddr;
	}
	/**
	 * @return 档案所在地
	 */
	public String getArchiveAddr() {
	 	return archiveAddr;
	}
	/**
	 * @设置 档案所在地
	 * @param archiveAddr
	 */
	public void setArchiveAddr(String archiveAddr) {
	 	this.archiveAddr = archiveAddr;
	}
	/**
	 * @return 是否有就业证
	 */
	public String getWorkCartificate() {
	 	return workCartificate;
	}
	/**
	 * @设置 是否有就业证
	 * @param workCartificate
	 */
	public void setWorkCartificate(String workCartificate) {
	 	this.workCartificate = workCartificate;
	}
	/**
	 * @return 是否有居住证
	 */
	public String getLiveCartificate() {
	 	return liveCartificate;
	}
	/**
	 * @设置 是否有居住证
	 * @param liveCartificate
	 */
	public void setLiveCartificate(String liveCartificate) {
	 	this.liveCartificate = liveCartificate;
	}
	/**
	 * @return 紧急联系人
	 */
	public String getEmergencyContacts() {
	 	return emergencyContacts;
	}
	/**
	 * @设置 紧急联系人
	 * @param emergencyContacts
	 */
	public void setEmergencyContacts(String emergencyContacts) {
	 	this.emergencyContacts = emergencyContacts;
	}
	/**
	 * @return 紧急联系人电话
	 */
	public String getEmergencyContactsPlane() {
	 	return emergencyContactsPlane;
	}
	/**
	 * @设置 紧急联系人电话
	 * @param emergencyContactsPlane
	 */
	public void setEmergencyContactsPlane(String emergencyContactsPlane) {
	 	this.emergencyContactsPlane = emergencyContactsPlane;
	}
	/**
	 * @return 紧急联系人手机
	 */
	public String getEmergencyContactsTel() {
	 	return emergencyContactsTel;
	}
	/**
	 * @设置 紧急联系人手机
	 * @param emergencyContactsTel
	 */
	public void setEmergencyContactsTel(String emergencyContactsTel) {
	 	this.emergencyContactsTel = emergencyContactsTel;
	}
	/**
	 * @return 入职时间
	 */
	public String getHireDate() {
	 	return hireDate;
	}
	/**
	 * @设置 入职时间
	 * @param hireDate
	 */
	public void setHireDate(String hireDate) {
	 	this.hireDate = hireDate;
	}
	/**
	 * @return 是否缴纳五险一金
	 */
	public String getInseranceFund() {
	 	return inseranceFund;
	}
	/**
	 * @设置 是否缴纳五险一金
	 * @param inseranceFund
	 */
	public void setInseranceFund(String inseranceFund) {
	 	this.inseranceFund = inseranceFund;
	}
	/**
	 * @return 目前居住情况
	 */
	public String getLiveCondition() {
	 	return liveCondition;
	}
	/**
	 * @设置 目前居住情况
	 * @param liveCondition
	 */
	public void setLiveCondition(String liveCondition) {
	 	this.liveCondition = liveCondition;
	}
	/**
	 * @return 地址
	 */
	public String getAddr() {
	 	return addr;
	}
	/**
	 * @设置 地址
	 * @param addr
	 */
	public void setAddr(String addr) {
	 	this.addr = addr;
	}
	/**
	 * @return 目前薪水
	 */
	public String getNowSalary() {
	 	return nowSalary;
	}
	/**
	 * @设置 目前薪水
	 * @param nowSalary
	 */
	public void setNowSalary(String nowSalary) {
	 	this.nowSalary = nowSalary;
	}
	/**
	 * @return 上次调薪时间
	 */
	public String getUpdalaryDate() {
	 	return updalaryDate;
	}
	/**
	 * @设置 上次调薪时间
	 * @param updalaryDate
	 */
	public void setUpdalaryDate(String updalaryDate) {
	 	this.updalaryDate = updalaryDate;
	}
	/**
	 * @return 是否有意在本地买房
	 */
	public String getLocalPurchase() {
	 	return localPurchase;
	}
	/**
	 * @设置 是否有意在本地买房
	 * @param localPurchase
	 */
	public void setLocalPurchase(String localPurchase) {
	 	this.localPurchase = localPurchase;
	}
	/**
	 * @return 地址
	 */
	public String getHouseAddr() {
	 	return houseAddr;
	}
	/**
	 * @设置 地址
	 * @param houseAddr
	 */
	public void setHouseAddr(String houseAddr) {
	 	this.houseAddr = houseAddr;
	}
	/**
	 * @return 是否单身
	 */
	public String getSingle() {
	 	return single;
	}
	/**
	 * @设置 是否单身
	 * @param single
	 */
	public void setSingle(String single) {
	 	this.single = single;
	}
	/**
	 * @return 恋人姓名
	 */
	public String getLoverName() {
	 	return loverName;
	}
	/**
	 * @设置 恋人姓名
	 * @param loverName
	 */
	public void setLoverName(String loverName) {
	 	this.loverName = loverName;
	}
	/**
	 * @return 恋人收入
	 */
	public Double getLoverIncome() {
	 	return loverIncome;
	}
	/**
	 * @设置 恋人收入
	 * @param loverIncome
	 */
	public void setLoverIncome(Double loverIncome) {
	 	this.loverIncome = loverIncome;
	}
	/**
	 * @return 恋人地址
	 */
	public String getLoverAddr() {
	 	return loverAddr;
	}
	/**
	 * @设置 恋人地址
	 * @param loverAddr
	 */
	public void setLoverAddr(String loverAddr) {
	 	this.loverAddr = loverAddr;
	}
	/**
	 * @return 恋人备注
	 */
	public String getLoverRemark() {
	 	return loverRemark;
	}
	/**
	 * @设置 恋人备注
	 * @param loverRemark
	 */
	public void setLoverRemark(String loverRemark) {
	 	this.loverRemark = loverRemark;
	}
	/**
	 * @return 父亲情况
	 */
	public String getFatherCondition() {
	 	return fatherCondition;
	}
	/**
	 * @设置 父亲情况
	 * @param fatherCondition
	 */
	public void setFatherCondition(String fatherCondition) {
	 	this.fatherCondition = fatherCondition;
	}
	/**
	 * @return 母亲情况
	 */
	public String getMotherCondition() {
	 	return motherCondition;
	}
	/**
	 * @设置 母亲情况
	 * @param motherCondition
	 */
	public void setMotherCondition(String motherCondition) {
	 	this.motherCondition = motherCondition;
	}
	/**
	 * @return 家庭情况
	 */
	public String getHomeCondition() {
	 	return homeCondition;
	}
	/**
	 * @设置 家庭情况
	 * @param homeCondition
	 */
	public void setHomeCondition(String homeCondition) {
	 	this.homeCondition = homeCondition;
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
	 * @return 最后一次修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后一次修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getIfUploadHead() {
		return ifUploadHead;
	}
	public void setIfUploadHead(String ifUploadHead) {
		this.ifUploadHead = ifUploadHead;
	}
	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return the homeAddr
	 */
	public String getHomeAddr() {
		return homeAddr;
	}
	/**
	 * @param homeAddr the homeAddr to set
	 */
	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}
	/**
	 * @return the applyChannel
	 */
	public String getApplyChannel() {
		return applyChannel;
	}
	/**
	 * @param applyChannel the applyChannel to set
	 */
	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}
	/**
	 * @return the major
	 */
	public String getMajor() {
		return major;
	}
	/**
	 * @param major the major to set
	 */
	public void setMajor(String major) {
		this.major = major;
	}
	/**
	 * @return the graduateSchool
	 */
	public String getGraduateSchool() {
		return graduateSchool;
	}
	/**
	 * @param graduateSchool the graduateSchool to set
	 */
	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}
	/**
	 * @return the constellation
	 */
	public String getConstellation() {
		return constellation;
	}
	/**
	 * @param constellation the constellation to set
	 */
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	/**
	 * @return the bloodType
	 */
	public String getBloodType() {
		return bloodType;
	}
	/**
	 * @param bloodType the bloodType to set
	 */
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}
	/**
	 * @return the referrer
	 */
	public String getReferrer() {
		return referrer;
	}
	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}
	/**
	 * @return the wage
	 */
	public Double getWage() {
		return wage;
	}
	/**
	 * @param wage the wage to set
	 */
	public void setWage(Double wage) {
		this.wage = wage;
	}
	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}
	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * @return the employeeType
	 */
	public String getEmployeeType() {
		return employeeType;
	}
	/**
	 * @param employeeType the employeeType to set
	 */
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	/**
	 * @return the employeeTypeUpdateDate
	 */
	public String getEmployeeTypeUpdateDate() {
		return employeeTypeUpdateDate;
	}
	/**
	 * @param employeeTypeUpdateDate the employeeTypeUpdateDate to set
	 */
	public void setEmployeeTypeUpdateDate(String employeeTypeUpdateDate) {
		this.employeeTypeUpdateDate = employeeTypeUpdateDate;
	}
	/**
	 * @return the isRelation
	 */
	public String getIsRelation() {
		return isRelation;
	}
	/**
	 * @param isRelation the isRelation to set
	 */
	public void setIsRelation(String isRelation) {
		this.isRelation = isRelation;
	}
	/**
	 * @return the relationName
	 */
	public String getRelationName() {
		return relationName;
	}
	/**
	 * @param relationName the relationName to set
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	/**
	 * @return the relationBrName
	 */
	public String getRelationBrName() {
		return relationBrName;
	}
	/**
	 * @param relationBrName the relationBrName to set
	 */
	public void setRelationBrName(String relationBrName) {
		this.relationBrName = relationBrName;
	}
	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * @return the relationPhone
	 */
	public String getRelationPhone() {
		return relationPhone;
	}
	/**
	 * @param relationPhone the relationPhone to set
	 */
	public void setRelationPhone(String relationPhone) {
		this.relationPhone = relationPhone;
	}
	/**
	 * @return the relationBrNo
	 */
	public String getRelationBrNo() {
		return relationBrNo;
	}
	/**
	 * @param relationBrNo the relationBrNo to set
	 */
	public void setRelationBrNo(String relationBrNo) {
		this.relationBrNo = relationBrNo;
	}
	/**
	 * @return the interest
	 */
	public String getInterest() {
		return interest;
	}
	/**
	 * @param interest the interest to set
	 */
	public void setInterest(String interest) {
		this.interest = interest;
	}
	/**
	 * @return the probationaryWage
	 */
	public Double getProbationaryWage() {
		return probationaryWage;
	}
	/**
	 * @param probationaryWage the probationaryWage to set
	 */
	public void setProbationaryWage(Double probationaryWage) {
		this.probationaryWage = probationaryWage;
	}
	public String getRegisteredType() {
		return registeredType;
	}
	public void setRegisteredType(String registeredType) {
		this.registeredType = registeredType;
	}
	public String getEmergencyContactsRelishion() {
		return emergencyContactsRelishion;
	}
	public void setEmergencyContactsRelishion(String emergencyContactsRelishion) {
		this.emergencyContactsRelishion = emergencyContactsRelishion;
	}
	public String getEmergencyContactsProfession() {
		return emergencyContactsProfession;
	}
	public void setEmergencyContactsProfession(String emergencyContactsProfession) {
		this.emergencyContactsProfession = emergencyContactsProfession;
	}
	public String getEmergencyContactsWorkAddr() {
		return emergencyContactsWorkAddr;
	}
	public void setEmergencyContactsWorkAddr(String emergencyContactsWorkAddr) {
		this.emergencyContactsWorkAddr = emergencyContactsWorkAddr;
	}
	public String getEnglishLevel() {
		return englishLevel;
	}
	public void setEnglishLevel(String englishLevel) {
		this.englishLevel = englishLevel;
	}
	public String getComputerSkill() {
		return computerSkill;
	}
	public void setComputerSkill(String computerSkill) {
		this.computerSkill = computerSkill;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public String getBaseId() {
		return baseId;
	}
	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	public String getIfUpdateHeadImg() {
		return ifUpdateHeadImg;
	}
	public void setIfUpdateHeadImg(String ifUpdateHeadImg) {
		this.ifUpdateHeadImg = ifUpdateHeadImg;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getPositionShow() {
		return positionShow;
	}
	public void setPositionShow(String positionShow) {
		this.positionShow = positionShow;
	}
	public String getSexShow() {
		return sexShow;
	}
	public void setSexShow(String sexShow) {
		this.sexShow = sexShow;
	}
	public String getOtherCredential() {
		return otherCredential;
	}
	public void setOtherCredential(String otherCredential) {
		this.otherCredential = otherCredential;
	}
	public String getOtherEnglishLevel() {
		return otherEnglishLevel;
	}
	public void setOtherEnglishLevel(String otherEnglishLevel) {
		this.otherEnglishLevel = otherEnglishLevel;
	}
	public String getDrivingLicenseName() {
		return drivingLicenseName;
	}
	public void setDrivingLicenseName(String drivingLicenseName) {
		this.drivingLicenseName = drivingLicenseName;
	}
	
}