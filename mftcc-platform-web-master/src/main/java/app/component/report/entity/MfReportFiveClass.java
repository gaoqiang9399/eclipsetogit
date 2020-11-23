/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfReportFiveClass.java
 * 包名： app.component.report.entity
 * 说明：
 * @author 谢静霞
 * @date 2017-6-16 下午4:25:28
 * @version V1.0
 */ 
package app.component.report.entity;
import app.base.BaseDomain;
/**
 * 类名： MfReportFiveClass
 * 描述：
 * @author 谢静霞
 * @date 2017-6-16 下午4:25:28
 *
 *
 */
public class MfReportFiveClass extends BaseDomain{
	
	private static final long serialVersionUID = -1368002758704338413L;
	
	private String brNo;//部门编号
	private String brName;//部门名称
	private String kindNo;//产品编号
	private String kindName;//产品名称
	private double putoutAmt;//贷款金额
	private double pacpBal;//贷款余额
	private int fiveclass;//五级分类
	private int countApp;//贷款笔数
	private int normCount;//正常类笔数
	private double normRate;//正常类占比
	private int gzCount;//关注类笔数
	private double gzRate;//关注类占比
	private int cjCount;//次级类笔数
	private double cjRate;//次级类占比
	private int kyCount;//可疑类笔数
	private double kyRate;//可疑类占比
	private int ssCount;//可疑类笔数
	private double ssRate;//可疑类占比
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBrName() {
		return brName;
	}
	public void setBrName(String brName) {
		this.brName = brName;
	}
	public String getKindNo() {
		return kindNo;
	}
	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public double getPutoutAmt() {
		return putoutAmt;
	}
	public void setPutoutAmt(double putoutAmt) {
		this.putoutAmt = putoutAmt;
	}
	public double getPacpBal() {
		return pacpBal;
	}
	public void setPacpBal(double pacpBal) {
		this.pacpBal = pacpBal;
	}
	public int getFiveclass() {
		return fiveclass;
	}
	public void setFiveclass(int fiveclass) {
		this.fiveclass = fiveclass;
	}
	public int getCountApp() {
		return countApp;
	}
	public void setCountApp(int countApp) {
		this.countApp = countApp;
	}
	public int getNormCount() {
		return normCount;
	}
	public void setNormCount(int normCount) {
		this.normCount = normCount;
	}
	public double getNormRate() {
		return normRate;
	}
	public void setNormRate(double normRate) {
		this.normRate = normRate;
	}
	public int getGzCount() {
		return gzCount;
	}
	public void setGzCount(int gzCount) {
		this.gzCount = gzCount;
	}
	public double getGzRate() {
		return gzRate;
	}
	public void setGzRate(double gzRate) {
		this.gzRate = gzRate;
	}
	public int getCjCount() {
		return cjCount;
	}
	public void setCjCount(int cjCount) {
		this.cjCount = cjCount;
	}
	public double getCjRate() {
		return cjRate;
	}
	public void setCjRate(double cjRate) {
		this.cjRate = cjRate;
	}
	public int getKyCount() {
		return kyCount;
	}
	public void setKyCount(int kyCount) {
		this.kyCount = kyCount;
	}
	public double getKyRate() {
		return kyRate;
	}
	public void setKyRate(double kyRate) {
		this.kyRate = kyRate;
	}
	public int getSsCount() {
		return ssCount;
	}
	public void setSsCount(int ssCount) {
		this.ssCount = ssCount;
	}
	public double getSsRate() {
		return ssRate;
	}
	public void setSsRate(double ssRate) {
		this.ssRate = ssRate;
	}
	
}
