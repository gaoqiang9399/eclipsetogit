package app.component.interfaces.mobileinterface.entity;
import app.base.BaseDomain;
/**
* Title: VmCifLineReg.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 05 10:14:21 CST 2017
* @version：1.0
**/
public class VmCifLineReg extends BaseDomain {
	private String id;//ID
	private String openid;//微信账户标识
	private String cifNo;//客户号
	private String cifAccount;//账号
	private String cusTel;//客户手机号
	private String cifEmail;//email
	private String cifNickname;//昵称
	private String cusType;//客户类型
	private String activeFlag;//激活标志：1激活，0未激活
	private String cifPassword;//登陆密码
	private String tradersPassword;//交易密码
	private String cifSalt;//密钥 用于登录验证
	private String regOrigin;//注册来源 0 手机 1  pc 2 微信
	private String cifOrigin;//客户来源 默认1
	private String onlineFlag;//线上/线下标记 0 线上 1 线下
	private String accountFlag;//开户标记 0 未开户 1 开户 默认1
	private String cifHead;//客户头像
	private String lastIp;//最后登陆ip
	private String lastTime;//最近登陆时间
	private String regTime;//注册时间
	private String regIp;//注册ip
	private String occTime;//时间戳
	private String recommender;//推荐人
	private String recommendedCode;//推荐码(用于推荐他人的)
	private String registrationCode;//注册码（注册时填写的）
	private String integral;//剩余积分
	private String usedIntegral;//已用积分
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

	/**
	 * @return ID
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 ID
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 微信账户标识
	 */
	public String getOpenid() {
	 	return openid;
	}
	/**
	 * @设置 微信账户标识
	 * @param openid
	 */
	public void setOpenid(String openid) {
	 	this.openid = openid;
	}
	/**
	 * @return 客户号
	 */
	public String getCifNo() {
	 	return cifNo;
	}
	/**
	 * @设置 客户号
	 * @param cifNo
	 */
	public void setCifNo(String cifNo) {
	 	this.cifNo = cifNo;
	}
	/**
	 * @return 账号
	 */
	public String getCifAccount() {
	 	return cifAccount;
	}
	/**
	 * @设置 账号
	 * @param cifAccount
	 */
	public void setCifAccount(String cifAccount) {
	 	this.cifAccount = cifAccount;
	}
	/**
	 * @return 客户手机号
	 */
	public String getCusTel() {
	 	return cusTel;
	}
	/**
	 * @设置 客户手机号
	 * @param cusTel
	 */
	public void setCusTel(String cusTel) {
	 	this.cusTel = cusTel;
	}
	/**
	 * @return email
	 */
	public String getCifEmail() {
	 	return cifEmail;
	}
	/**
	 * @设置 email
	 * @param cifEmail
	 */
	public void setCifEmail(String cifEmail) {
	 	this.cifEmail = cifEmail;
	}
	/**
	 * @return 昵称
	 */
	public String getCifNickname() {
	 	return cifNickname;
	}
	/**
	 * @设置 昵称
	 * @param cifNickname
	 */
	public void setCifNickname(String cifNickname) {
	 	this.cifNickname = cifNickname;
	}
	/**
	 * @return 客户类型
	 */
	public String getCusType() {
	 	return cusType;
	}
	/**
	 * @设置 客户类型
	 * @param cusType
	 */
	public void setCusType(String cusType) {
	 	this.cusType = cusType;
	}
	/**
	 * @return 激活标志：1激活，0未激活
	 */
	public String getActiveFlag() {
	 	return activeFlag;
	}
	/**
	 * @设置 激活标志：1激活，0未激活
	 * @param activeFlag
	 */
	public void setActiveFlag(String activeFlag) {
	 	this.activeFlag = activeFlag;
	}
	/**
	 * @return 登陆密码
	 */
	public String getCifPassword() {
	 	return cifPassword;
	}
	/**
	 * @设置 登陆密码
	 * @param cifPassword
	 */
	public void setCifPassword(String cifPassword) {
	 	this.cifPassword = cifPassword;
	}
	/**
	 * @return 交易密码
	 */
	public String getTradersPassword() {
	 	return tradersPassword;
	}
	/**
	 * @设置 交易密码
	 * @param tradersPassword
	 */
	public void setTradersPassword(String tradersPassword) {
	 	this.tradersPassword = tradersPassword;
	}
	/**
	 * @return 密钥 用于登录验证
	 */
	public String getCifSalt() {
	 	return cifSalt;
	}
	/**
	 * @设置 密钥 用于登录验证
	 * @param cifSalt
	 */
	public void setCifSalt(String cifSalt) {
	 	this.cifSalt = cifSalt;
	}
	/**
	 * @return 注册来源 0 手机 1  pc 2 微信
	 */
	public String getRegOrigin() {
	 	return regOrigin;
	}
	/**
	 * @设置 注册来源 0 手机 1  pc 2 微信
	 * @param regOrigin
	 */
	public void setRegOrigin(String regOrigin) {
	 	this.regOrigin = regOrigin;
	}
	/**
	 * @return 客户来源 默认1
	 */
	public String getCifOrigin() {
	 	return cifOrigin;
	}
	/**
	 * @设置 客户来源 默认1
	 * @param cifOrigin
	 */
	public void setCifOrigin(String cifOrigin) {
	 	this.cifOrigin = cifOrigin;
	}
	/**
	 * @return 线上/线下标记 0 线上 1 线下
	 */
	public String getOnlineFlag() {
	 	return onlineFlag;
	}
	/**
	 * @设置 线上/线下标记 0 线上 1 线下
	 * @param onlineFlag
	 */
	public void setOnlineFlag(String onlineFlag) {
	 	this.onlineFlag = onlineFlag;
	}
	/**
	 * @return 开户标记 0 未开户 1 开户 默认1
	 */
	public String getAccountFlag() {
	 	return accountFlag;
	}
	/**
	 * @设置 开户标记 0 未开户 1 开户 默认1
	 * @param accountFlag
	 */
	public void setAccountFlag(String accountFlag) {
	 	this.accountFlag = accountFlag;
	}
	/**
	 * @return 客户头像
	 */
	public String getCifHead() {
	 	return cifHead;
	}
	/**
	 * @设置 客户头像
	 * @param cifHead
	 */
	public void setCifHead(String cifHead) {
	 	this.cifHead = cifHead;
	}
	/**
	 * @return 最后登陆ip
	 */
	public String getLastIp() {
	 	return lastIp;
	}
	/**
	 * @设置 最后登陆ip
	 * @param lastIp
	 */
	public void setLastIp(String lastIp) {
	 	this.lastIp = lastIp;
	}
	/**
	 * @return 最近登陆时间
	 */
	public String getLastTime() {
	 	return lastTime;
	}
	/**
	 * @设置 最近登陆时间
	 * @param lastTime
	 */
	public void setLastTime(String lastTime) {
	 	this.lastTime = lastTime;
	}
	/**
	 * @return 注册时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 注册时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 注册ip
	 */
	public String getRegIp() {
	 	return regIp;
	}
	/**
	 * @设置 注册ip
	 * @param regIp
	 */
	public void setRegIp(String regIp) {
	 	this.regIp = regIp;
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
	/**
	 * @return 推荐人
	 */
	public String getRecommender() {
	 	return recommender;
	}
	/**
	 * @设置 推荐人
	 * @param recommender
	 */
	public void setRecommender(String recommender) {
	 	this.recommender = recommender;
	}
	/**
	 * @return 推荐码(用于推荐他人的)
	 */
	public String getRecommendedCode() {
	 	return recommendedCode;
	}
	/**
	 * @设置 推荐码(用于推荐他人的)
	 * @param recommendedCode
	 */
	public void setRecommendedCode(String recommendedCode) {
	 	this.recommendedCode = recommendedCode;
	}
	/**
	 * @return 注册码（注册时填写的）
	 */
	public String getRegistrationCode() {
	 	return registrationCode;
	}
	/**
	 * @设置 注册码（注册时填写的）
	 * @param registrationCode
	 */
	public void setRegistrationCode(String registrationCode) {
	 	this.registrationCode = registrationCode;
	}
	/**
	 * @return 剩余积分
	 */
	public String getIntegral() {
	 	return integral;
	}
	/**
	 * @设置 剩余积分
	 * @param integral
	 */
	public void setIntegral(String integral) {
	 	this.integral = integral;
	}
	/**
	 * @return 已用积分
	 */
	public String getUsedIntegral() {
	 	return usedIntegral;
	}
	/**
	 * @设置 已用积分
	 * @param usedIntegral
	 */
	public void setUsedIntegral(String usedIntegral) {
	 	this.usedIntegral = usedIntegral;
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
}