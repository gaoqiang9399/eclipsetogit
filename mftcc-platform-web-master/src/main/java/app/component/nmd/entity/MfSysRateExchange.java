package app.component.nmd.entity;
import app.base.BaseDomain;
/**
* Title: MfSysRateExchange.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri May 05 20:50:06 CST 2017
* @version：1.0
**/
public class MfSysRateExchange extends BaseDomain {
	private String rateno;//编号
	private String curno;//币种
	private String exchangeRate;//汇率
	private String editdate;//更新日期
	private String edittime;//更新时间
	private String edituser;//更新时间
	private String curshot;//缩写
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
	
	private String desc;
	
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
	 * @return 币种
	 */
	public String getCurno() {
	 	return curno;
	}
	/**
	 * @设置 币种
	 * @param curno
	 */
	public void setCurno(String curno) {
	 	this.curno = curno;
	}
	/**
	 * @return 汇率
	 */
	public String getExchangeRate() {
	 	return exchangeRate;
	}
	/**
	 * @设置 汇率
	 * @param exchangeRate
	 */
	public void setExchangeRate(String exchangeRate) {
	 	this.exchangeRate = exchangeRate;
	}
	/**
	 * @return 更新时间
	 */
	public String getEditdate() {
	 	return editdate;
	}
	/**
	 * @设置 更新时间
	 * @param editdate
	 */
	public void setEditdate(String editdate) {
	 	this.editdate = editdate;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	public String getEdittime() {
		return edittime;
	}
	public void setEdittime(String edittime) {
		this.edittime = edittime;
	}
	public String getEdituser() {
		return edituser;
	}
	public void setEdituser(String edituser) {
		this.edituser = edituser;
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
	public String getCurshot() {
		return curshot;
	}
	public void setCurshot(String curshot) {
		this.curshot = curshot;
	}
	
	
	
	
	
	public String getDesc() {
		return "1 " + curno + " = "+exchangeRate+" 人民币(CNY)";
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}