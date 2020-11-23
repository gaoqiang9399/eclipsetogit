package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamineDetail.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Feb 15 08:40:52 CST 2017
* @version：1.0
**/
public class MfExamineDetail extends BaseDomain {
	private String detailId;//详情编号
	private String relateBussId;//关联业务编号
	private String examHisId;//检查历史编号
	private String indexId;//检查指标项
	private String indexValue;//指标值
	private String picId;//数据字典
	private String riskLevel;//风险级别
	private String regNo;//操作人编号
	private String regName;//操作人姓名
	private String regTime;//登记时间

	private String pasMinNo;//检查主体
	private String isOverdue;//是否逾期
	private String fiveClass;//五级分类
	private String examineSts;//检查状态
	private String amtRest;//贷款余额
	private String beginDate;//检查日期

	/**
	 * @return 详情编号
	 */
	public String getDetailId() {
	 	return detailId;
	}
	/**
	 * @设置 详情编号
	 * @param detailId
	 */
	public void setDetailId(String detailId) {
	 	this.detailId = detailId;
	}
	/**
	 * @return 关联业务编号
	 */
	public String getRelateBussId() {
	 	return relateBussId;
	}
	/**
	 * @设置 关联业务编号
	 * @param relateBussId
	 */
	public void setRelateBussId(String relateBussId) {
	 	this.relateBussId = relateBussId;
	}
	/**
	 * @return 检查历史编号
	 */
	public String getExamHisId() {
	 	return examHisId;
	}
	/**
	 * @设置 检查历史编号
	 * @param examHisId
	 */
	public void setExamHisId(String examHisId) {
	 	this.examHisId = examHisId;
	}
	/**
	 * @return 检查指标项
	 */
	public String getIndexId() {
	 	return indexId;
	}
	/**
	 * @设置 检查指标项
	 * @param indexId
	 */
	public void setIndexId(String indexId) {
	 	this.indexId = indexId;
	}
	/**
	 * @return 指标值
	 */
	public String getIndexValue() {
	 	return indexValue;
	}
	/**
	 * @设置 指标值
	 * @param indexValue
	 */
	public void setIndexValue(String indexValue) {
	 	this.indexValue = indexValue;
	}
	/**
	 * @return 数据字典
	 */
	public String getPicId() {
	 	return picId;
	}
	/**
	 * @设置 数据字典
	 * @param picId
	 */
	public void setPicId(String picId) {
	 	this.picId = picId;
	}
	/**
	 * @return 风险级别
	 */
	public String getRiskLevel() {
	 	return riskLevel;
	}
	/**
	 * @设置 风险级别
	 * @param riakLevel
	 */
	public void setRiskLevel(String riskLevel) {
	 	this.riskLevel = riskLevel;
	}

	public String getPasMinNo() {
		return pasMinNo;
	}

	public void setPasMinNo(String pasMinNo) {
		this.pasMinNo = pasMinNo;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getFiveClass() {
		return fiveClass;
	}

	public void setFiveClass(String fiveClass) {
		this.fiveClass = fiveClass;
	}

	public String getExamineSts() {
		return examineSts;
	}

	public void setExamineSts(String examineSts) {
		this.examineSts = examineSts;
	}

	public String getAmtRest() {
		return amtRest;
	}

	public void setAmtRest(String amtRest) {
		this.amtRest = amtRest;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
}