package app.component.nmd.entity;
import app.base.BaseDomain;
/**
* Title: MfSysRateBase.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue May 09 10:15:21 CST 2017
* @version：1.0
**/
public class MfSysRateBase extends BaseDomain {
	private String rateno;//编号
	private String begindate;//生效时间
	private String ratetype;//利率类型
	private Double rate1;//6个月以内（含）
	private Double rate2;//6个月 - 1年（含）
	private Double rate3;//1年 - 3年（含）
	private Double rate4;//3年 - 5年（含）
	private Double rate5;//5年以上
	private String ext1;//
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
	 * @return 编号
	 */
	public String getRateno() {
	 	return rateno;
	}
	/**
	 * @设置 编号
	 * @param rateno
	 */
	public void setRateno(String rateno) {
	 	this.rateno = rateno;
	}
	/**
	 * @return 生效时间
	 */
	public String getBegindate() {
	 	return begindate;
	}
	/**
	 * @设置 生效时间
	 * @param begindate
	 */
	public void setBegindate(String begindate) {
	 	this.begindate = begindate;
	}
	/**
	 * @return 利率类型
	 */
	public String getRatetype() {
	 	return ratetype;
	}
	/**
	 * @设置 利率类型
	 * @param ratetype
	 */
	public void setRatetype(String ratetype) {
	 	this.ratetype = ratetype;
	}
	/**
	 * @return 6个月以内（含）
	 */
	public Double getRate1() {
	 	return rate1;
	}
	/**
	 * @设置 6个月以内（含）
	 * @param rate1
	 */
	public void setRate1(Double rate1) {
	 	this.rate1 = rate1;
	}
	/**
	 * @return 6个月 - 1年（含）
	 */
	public Double getRate2() {
	 	return rate2;
	}
	/**
	 * @设置 6个月 - 1年（含）
	 * @param rate2
	 */
	public void setRate2(Double rate2) {
	 	this.rate2 = rate2;
	}
	/**
	 * @return 1年 - 3年（含）
	 */
	public Double getRate3() {
	 	return rate3;
	}
	/**
	 * @设置 1年 - 3年（含）
	 * @param rate3
	 */
	public void setRate3(Double rate3) {
	 	this.rate3 = rate3;
	}
	/**
	 * @return 3年 - 5年（含）
	 */
	public Double getRate4() {
	 	return rate4;
	}
	/**
	 * @设置 3年 - 5年（含）
	 * @param rate4
	 */
	public void setRate4(Double rate4) {
	 	this.rate4 = rate4;
	}
	/**
	 * @return 5年以上
	 */
	public Double getRate5() {
	 	return rate5;
	}
	/**
	 * @设置 5年以上
	 * @param rate5
	 */
	public void setRate5(Double rate5) {
	 	this.rate5 = rate5;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
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
	
	
	public void addCustomQueryByString(String str) throws Exception{
		super.getCustomQuery().add(str);
	}
	
	
	
}