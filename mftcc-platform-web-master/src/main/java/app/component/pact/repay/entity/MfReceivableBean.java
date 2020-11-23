/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfReceivableBean.java
 * 包名： app.component.pact.repay.entity
 * 说明：
 * @author zhs
 * @date 2016-8-18 上午11:07:43
 * @version V1.0
 */ 
package app.component.pact.repay.entity;
/**
 * 类名： MfReceivableBean
 * 描述：应收信息
 * @author zhs
 * @date 2016-8-18 上午11:07:43
 *
 *
 */
public class MfReceivableBean {
	//还款计划表id
	private String returnPlanId;
	// 应收本金  本次还款收到的金额
	private Double yingShouBenJin;
	// 应收本金的利息   本次还款收到的金额
	private Double yingShouLiXi;//如果 利率浮动值部分存储  OVERCMPDFLTSAVEFLAG1 （本金利息=正常利息+yingShouLiXiOverPart）
	private Double yingShouLiXiNormPart;//正常利息  利率浮动值部分存储 OVERCMPDFLTSAVEFLAG1 时使用 
	private Double yingShouLiXiOverPart;//逾期利息（为 1 计算出的值）  
	// 应收罚息=应收复利利息+应收逾期利息
	private Double yingShouFaXi;
	//应收逾期利息
	private Double yingShouYuQiLiXi;
	//应收复利利息
	private Double yingShouFuLiLiXi;
	private Double yingShouYuQiLiXiPart;//应收逾期利息上浮的值
	private Double yingShouFuLiLiXiPart;//应收复利利率上浮的值
	// 应收费用   本次还款收到的金额
	private Double yingShouFeiYong;
	private Double yingShouFeiYongFaXi;//应还费用罚息
	// 应收逾期时收取的违约金   本次还款收到的金额
	private Double yingShouYuQiWeiYueJin;
	// 优惠金额   本次还款收到的金额
	private Double youHuiJine;
	// 本期应收总计   本次还款收到的金额
	private Double benQiYingShouZongJi;
	// 本期实际收到的金额   本次还款收到的金额
	private Double benQiShiShouZongJi;
	// 期号
	private int termNum;
	// 借据号
	private String fincId;
	// 合同号
	private String pactId;
	// 还款前 还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款
	private String outFlag;
	
	//还款后 还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款   用于还款后更新每笔还款计划状态使用
	private String afterRepayOutFlag;
	//最多优惠多少钱
	private Double maxYouhuiAmt;
	//是否 足额还款标记  0  足额还款  1 部分还款
	private String ifFullAmount;
	
	private String planBeginDate;//本期还款计划开始时间
	
	private String planEndDate;//本期还款计划结束时间
	
	//实际应该还的金额
	private Double yingHuanBenjin;//应还本金
	private Double yingHuanLixi;//应还利息
	private Double yingHuanLiXiNormPart;//应还正常利息  利率浮动值部分存储 OVERCMPDFLTSAVEFLAG1 时使用 
	private Double yingHuanLiXiOverPart;//应还逾期利息（为 1 计算出的值）  
	private Double yingHuanFeiYong;//应还费用 
	private Double yingHuanYuQiWeiYueJin;//应还违约金
	private Double yingHuanFaXi;//应还罚息
	private Double yingHuanYuQiLiXi;//应还逾期利息
	private Double yingHuanFuLiLiXi;//应还复利利息
	private Double yingHuanYuQiLiXiPart;//应还逾期利息上浮的值
	private Double yingHuanFuLiLiXiPart;//应还复利利率上浮的值
	private Double yingHuanFeiYongFaXi;//应还费用罚息
	
	// 应收本金  本次还款收到的金额
	private String yingShouBenJinFormat;
	// 应收本金的利息   本次还款收到的金额
	private String yingShouLiXiFormat;
	// 应收罚息=应收复利利息+应收逾期利息
	private String yingShouFaXiFormat;	
	// 应收逾期利息
	private String yingShouYuQiLiXiFormat;
	//应收复利利息
	private String yingShouFuLiLiXiFormat;
	//应收逾期利息上浮的值
	private String yingShouYuQiLiXiPartFormat;
	//应收复利利率上浮的值
	private String yingShouFuLiLiXiPartFormat;
	//应还费用罚息
	private String yingShouFeiYongFaXiFormat;
	// 应收费用   本次还款收到的金额
	private String yingShouFeiYongFormat;
	// 应收逾期时收取的违约金   本次还款收到的金额
	private String yingShouYuQiWeiYueJinFormat;
		// 优惠金额   本次还款收到的金额
	private String youHuiJineFormat;
	// 本期应收总计   本次还款收到的金额
	private String benQiYingShouZongJiFormat;
	// 本期实际收到的金额   本次还款收到的金额
	private String benQiShiShouZongJiFormat;
	
	public Double getYingShouBenJin() {
		return yingShouBenJin;
	}
	public void setYingShouBenJin(Double yingShouBenJin) {
		this.yingShouBenJin = yingShouBenJin;
	}
	public Double getYingShouLiXi() {
		return yingShouLiXi;
	}
	public void setYingShouLiXi(Double yingShouLiXi) {
		this.yingShouLiXi = yingShouLiXi;
	}
	public Double getYingShouFaXi() {
		return yingShouFaXi;
	}
	public void setYingShouFaXi(Double yingShouFaXi) {
		this.yingShouFaXi = yingShouFaXi;
	}
	public Double getYingShouFeiYong() {
		return yingShouFeiYong;
	}
	public void setYingShouFeiYong(Double yingShouFeiYong) {
		this.yingShouFeiYong = yingShouFeiYong;
	}
	public Double getYingShouYuQiWeiYueJin() {
		return yingShouYuQiWeiYueJin;
	}
	public void setYingShouYuQiWeiYueJin(Double yingShouYuQiWeiYueJin) {
		this.yingShouYuQiWeiYueJin = yingShouYuQiWeiYueJin;
	}
	public Double getYouHuiJine() {
		return youHuiJine;
	}
	public void setYouHuiJine(Double youHuiJine) {
		this.youHuiJine = youHuiJine;
	}
	public Double getBenQiYingShouZongJi() {
		return benQiYingShouZongJi;
	}
	public void setBenQiYingShouZongJi(Double benQiYingShouZongJi) {
		this.benQiYingShouZongJi = benQiYingShouZongJi;
	}
	public int getTermNum() {
		return termNum;
	}
	public void setTermNum(int termNum) {
		this.termNum = termNum;
	}
	public String getFincId() {
		return fincId;
	}
	public void setFincId(String fincId) {
		this.fincId = fincId;
	}
	public String getPactId() {
		return pactId;
	}
	public void setPactId(String pactId) {
		this.pactId = pactId;
	}
	/**
	 * 方法描述：还款前  还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款
	 * @return
	 * String
	 * @author 栾好威
	 * @date 2017-11-29 下午2:00:22
	 */
	public String getOutFlag() {
		return outFlag;
	}
	/**
	 * 方法描述：还款前  还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款
	 * @param outFlag
	 * void
	 * @author 栾好威
	 * @date 2017-11-29 下午2:00:31
	 */
	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}
	public String getReturnPlanId() {
		return returnPlanId;
	}
	public void setReturnPlanId(String returnPlanId) {
		this.returnPlanId = returnPlanId;
	}
	/**
	 * 还款后 还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款   用于还款后更新每笔还款计划状态使用
	 * @return
	 */
	public String getAfterRepayOutFlag() {
		return afterRepayOutFlag;
	}
	/**
	 * 还款后 还款计划状态0-未还款，1-已还款，2-部分还款，3-欠款   用于还款后更新每笔还款计划状态使用
	 * @param afterRepayOutFlag
	 */
	public void setAfterRepayOutFlag(String afterRepayOutFlag) {
		this.afterRepayOutFlag = afterRepayOutFlag;
	}
	public Double getMaxYouhuiAmt() {
		return maxYouhuiAmt;
	}
	public void setMaxYouhuiAmt(Double maxYouhuiAmt) {
		this.maxYouhuiAmt = maxYouhuiAmt;
	}
	public Double getBenQiShiShouZongJi() {
		return benQiShiShouZongJi;
	}
	public void setBenQiShiShouZongJi(Double benQiShiShouZongJi) {
		this.benQiShiShouZongJi = benQiShiShouZongJi;
	}
	/**
	 * 0 足额还款
	 * 1 部分还款
	 * @param ifFullAmount
	 */
	public String getIfFullAmount() {
		return ifFullAmount;
	}
	/**
	 * 0 足额还款
	 * 1 部分还款
	 * @param ifFullAmount
	 */
	public void setIfFullAmount(String ifFullAmount) {
		this.ifFullAmount = ifFullAmount;
	}
	/**
	 * 本期还款计划开始时间
	 * @return
	 */
	public String getPlanBeginDate() {
		return planBeginDate;
	}
	/**
	 * 本期还款计划开始时间
	 * @return
	 */
	public void setPlanBeginDate(String planBeginDate) {
		this.planBeginDate = planBeginDate;
	}
	/**
	 * 本期还款计划结束时间
	 * @return
	 */
	public String getPlanEndDate() {
		return planEndDate;
	}
	/**
	 * 本期还款计划结束时间
	 * @return
	 */
	public void setPlanEndDate(String planEndDate) {
		this.planEndDate = planEndDate;
	}
	public Double getYingHuanBenjin() {
		return yingHuanBenjin;
	}
	public void setYingHuanBenjin(Double yingHuanBenjin) {
		this.yingHuanBenjin = yingHuanBenjin;
	}
	public Double getYingHuanLixi() {
		return yingHuanLixi;
	}
	public void setYingHuanLixi(Double yingHuanLixi) {
		this.yingHuanLixi = yingHuanLixi;
	}
	public Double getYingHuanFeiYong() {
		return yingHuanFeiYong;
	}
	public void setYingHuanFeiYong(Double yingHuanFeiYong) {
		this.yingHuanFeiYong = yingHuanFeiYong;
	}
	public Double getYingHuanYuQiWeiYueJin() {
		return yingHuanYuQiWeiYueJin;
	}
	public void setYingHuanYuQiWeiYueJin(Double yingHuanYuQiWeiYueJin) {
		this.yingHuanYuQiWeiYueJin = yingHuanYuQiWeiYueJin;
	}
	public Double getYingHuanFaXi() {
		return yingHuanFaXi;
	}
	public void setYingHuanFaXi(Double yingHuanFaXi) {
		this.yingHuanFaXi = yingHuanFaXi;
	}
	public String getYingShouBenJinFormat() {
		return yingShouBenJinFormat;
	}
	public void setYingShouBenJinFormat(String yingShouBenJinFormat) {
		this.yingShouBenJinFormat = yingShouBenJinFormat;
	}
	public String getYingShouLiXiFormat() {
		return yingShouLiXiFormat;
	}
	public void setYingShouLiXiFormat(String yingShouLiXiFormat) {
		this.yingShouLiXiFormat = yingShouLiXiFormat;
	}
	public String getYingShouFaXiFormat() {
		return yingShouFaXiFormat;
	}
	public void setYingShouFaXiFormat(String yingShouFaXiFormat) {
		this.yingShouFaXiFormat = yingShouFaXiFormat;
	}
	public String getYingShouFeiYongFormat() {
		return yingShouFeiYongFormat;
	}
	public void setYingShouFeiYongFormat(String yingShouFeiYongFormat) {
		this.yingShouFeiYongFormat = yingShouFeiYongFormat;
	}
	public String getYingShouYuQiWeiYueJinFormat() {
		return yingShouYuQiWeiYueJinFormat;
	}
	public void setYingShouYuQiWeiYueJinFormat(String yingShouYuQiWeiYueJinFormat) {
		this.yingShouYuQiWeiYueJinFormat = yingShouYuQiWeiYueJinFormat;
	}
	public String getYouHuiJineFormat() {
		return youHuiJineFormat;
	}
	public void setYouHuiJineFormat(String youHuiJineFormat) {
		this.youHuiJineFormat = youHuiJineFormat;
	}
	public String getBenQiYingShouZongJiFormat() {
		return benQiYingShouZongJiFormat;
	}
	public void setBenQiYingShouZongJiFormat(String benQiYingShouZongJiFormat) {
		this.benQiYingShouZongJiFormat = benQiYingShouZongJiFormat;
	}
	public String getBenQiShiShouZongJiFormat() {
		return benQiShiShouZongJiFormat;
	}
	public void setBenQiShiShouZongJiFormat(String benQiShiShouZongJiFormat) {
		this.benQiShiShouZongJiFormat = benQiShiShouZongJiFormat;
	}
	public Double getYingShouYuQiLiXi() {
		return yingShouYuQiLiXi;
	}
	public void setYingShouYuQiLiXi(Double yingShouYuQiLiXi) {
		this.yingShouYuQiLiXi = yingShouYuQiLiXi;
	}
	public Double getYingShouFuLiLiXi() {
		return yingShouFuLiLiXi;
	}
	public void setYingShouFuLiLiXi(Double yingShouFuLiLiXi) {
		this.yingShouFuLiLiXi = yingShouFuLiLiXi;
	}
	public Double getYingHuanYuQiLiXi() {
		return yingHuanYuQiLiXi;
	}
	public void setYingHuanYuQiLiXi(Double yingHuanYuQiLiXi) {
		this.yingHuanYuQiLiXi = yingHuanYuQiLiXi;
	}
	public Double getYingHuanFuLiLiXi() {
		return yingHuanFuLiLiXi;
	}
	public void setYingHuanFuLiLiXi(Double yingHuanFuLiLiXi) {
		this.yingHuanFuLiLiXi = yingHuanFuLiLiXi;
	}
	public String getYingShouYuQiLiXiFormat() {
		return yingShouYuQiLiXiFormat;
	}
	public void setYingShouYuQiLiXiFormat(String yingShouYuQiLiXiFormat) {
		this.yingShouYuQiLiXiFormat = yingShouYuQiLiXiFormat;
	}
	public String getYingShouFuLiLiXiFormat() {
		return yingShouFuLiLiXiFormat;
	}
	public void setYingShouFuLiLiXiFormat(String yingShouFuLiLiXiFormat) {
		this.yingShouFuLiLiXiFormat = yingShouFuLiLiXiFormat;
	}
	public Double getYingShouLiXiNormPart() {
		return yingShouLiXiNormPart;
	}
	public void setYingShouLiXiNormPart(Double yingShouLiXiNormPart) {
		this.yingShouLiXiNormPart = yingShouLiXiNormPart;
	}
	public Double getYingShouYuQiLiXiPart() {
		return yingShouYuQiLiXiPart;
	}
	public void setYingShouYuQiLiXiPart(Double yingShouYuQiLiXiPart) {
		this.yingShouYuQiLiXiPart = yingShouYuQiLiXiPart;
	}
	public Double getYingShouFuLiLiXiPart() {
		return yingShouFuLiLiXiPart;
	}
	public void setYingShouFuLiLiXiPart(Double yingShouFuLiLiXiPart) {
		this.yingShouFuLiLiXiPart = yingShouFuLiLiXiPart;
	}
	public Double getYingHuanYuQiLiXiPart() {
		return yingHuanYuQiLiXiPart;
	}
	public void setYingHuanYuQiLiXiPart(Double yingHuanYuQiLiXiPart) {
		this.yingHuanYuQiLiXiPart = yingHuanYuQiLiXiPart;
	}
	public Double getYingHuanFuLiLiXiPart() {
		return yingHuanFuLiLiXiPart;
	}
	public void setYingHuanFuLiLiXiPart(Double yingHuanFuLiLiXiPart) {
		this.yingHuanFuLiLiXiPart = yingHuanFuLiLiXiPart;
	}
	public String getYingShouYuQiLiXiPartFormat() {
		return yingShouYuQiLiXiPartFormat;
	}
	public void setYingShouYuQiLiXiPartFormat(String yingShouYuQiLiXiPartFormat) {
		this.yingShouYuQiLiXiPartFormat = yingShouYuQiLiXiPartFormat;
	}
	public String getYingShouFuLiLiXiPartFormat() {
		return yingShouFuLiLiXiPartFormat;
	}
	public void setYingShouFuLiLiXiPartFormat(String yingShouFuLiLiXiPartFormat) {
		this.yingShouFuLiLiXiPartFormat = yingShouFuLiLiXiPartFormat;
	}
	public Double getYingShouLiXiOverPart() {
		return yingShouLiXiOverPart;
	}
	public void setYingShouLiXiOverPart(Double yingShouLiXiOverPart) {
		this.yingShouLiXiOverPart = yingShouLiXiOverPart;
	}
	public Double getYingHuanLiXiNormPart() {
		return yingHuanLiXiNormPart;
	}
	public void setYingHuanLiXiNormPart(Double yingHuanLiXiNormPart) {
		this.yingHuanLiXiNormPart = yingHuanLiXiNormPart;
	}
	public Double getYingHuanLiXiOverPart() {
		return yingHuanLiXiOverPart;
	}
	public void setYingHuanLiXiOverPart(Double yingHuanLiXiOverPart) {
		this.yingHuanLiXiOverPart = yingHuanLiXiOverPart;
	}
	public Double getYingShouFeiYongFaXi() {
		return yingShouFeiYongFaXi;
	}
	public void setYingShouFeiYongFaXi(Double yingShouFeiYongFaXi) {
		this.yingShouFeiYongFaXi = yingShouFeiYongFaXi;
	}
	public Double getYingHuanFeiYongFaXi() {
		return yingHuanFeiYongFaXi;
	}
	public void setYingHuanFeiYongFaXi(Double yingHuanFeiYongFaXi) {
		this.yingHuanFeiYongFaXi = yingHuanFeiYongFaXi;
	}
	public String getYingShouFeiYongFaXiFormat() {
		return yingShouFeiYongFaXiFormat;
	}
	public void setYingShouFeiYongFaXiFormat(String yingShouFeiYongFaXiFormat) {
		this.yingShouFeiYongFaXiFormat = yingShouFeiYongFaXiFormat;
	}
}
