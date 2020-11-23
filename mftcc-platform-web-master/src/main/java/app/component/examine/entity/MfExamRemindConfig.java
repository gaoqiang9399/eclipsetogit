package app.component.examine.entity;
import app.base.BaseDomain;
/**
* Title: MfExamRemindConfig.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Feb 10 14:50:22 CST 2017
* @version：1.0
**/
public class MfExamRemindConfig extends BaseDomain {
	private String remindId;//检查提醒编号
	private String remindName;//检查提醒名称
	private String busModel;//业务模式
	private String examObj;//检查对象1合同2买方3合作伙伴
	private String examType;//检查类型 1一次性2周期3专项
	private Integer aheadWarnTime;//提前预警天数
	private String examDate;//检查日期
	private Integer laonAfterTime;//放款后天数
	private String pattern;//模式 1按自然月2按期3指定天数
	private Integer remindTime;//提醒天数
	private String useFlag;//启用标识0禁用1启用
	private String remark;//检查提醒说明
	private String regTime;//登记时间

	/**
	 * @return 检查提醒编号
	 */
	public String getRemindId() {
	 	return remindId;
	}
	/**
	 * @设置 检查提醒编号
	 * @param remindId
	 */
	public void setRemindId(String remindId) {
	 	this.remindId = remindId;
	}
	/**
	 * @return 检查提醒名称
	 */
	public String getRemindName() {
	 	return remindName;
	}
	/**
	 * @设置 检查提醒名称
	 * @param remindName
	 */
	public void setRemindName(String remindName) {
	 	this.remindName = remindName;
	}
	/**
	 * @return 业务模式
	 */
	public String getBusModel() {
	 	return busModel;
	}
	/**
	 * @设置 业务模式
	 * @param busModel
	 */
	public void setBusModel(String busModel) {
	 	this.busModel = busModel;
	}
	/**
	 * @return 检查对象1合同2买方3合作伙伴
	 */
	public String getExamObj() {
	 	return examObj;
	}
	/**
	 * @设置 检查对象1合同2买方3合作伙伴
	 * @param examObj
	 */
	public void setExamObj(String examObj) {
	 	this.examObj = examObj;
	}
	/**
	 * @return 检查类型 1一次性2周期3专项
	 */
	public String getExamType() {
	 	return examType;
	}
	/**
	 * @设置 检查类型 1一次性2周期3专项
	 * @param examType
	 */
	public void setExamType(String examType) {
	 	this.examType = examType;
	}
	/**
	 * @return 提前预警天数
	 */
	public Integer getAheadWarnTime() {
	 	return aheadWarnTime;
	}
	/**
	 * @设置 提前预警天数
	 * @param aheadWarnTime
	 */
	public void setAheadWarnTime(Integer aheadWarnTime) {
	 	this.aheadWarnTime = aheadWarnTime;
	}
	/**
	 * @return 检查日期
	 */
	public String getExamDate() {
	 	return examDate;
	}
	/**
	 * @设置 检查日期
	 * @param examDate
	 */
	public void setExamDate(String examDate) {
	 	this.examDate = examDate;
	}
	/**
	 * @return 放款后天数
	 */
	public Integer getLaonAfterTime() {
	 	return laonAfterTime;
	}
	/**
	 * @设置 放款后天数
	 * @param laonAfterTime
	 */
	public void setLaonAfterTime(Integer laonAfterTime) {
	 	this.laonAfterTime = laonAfterTime;
	}
	/**
	 * @return 模式 1按自然月2按期3指定天数
	 */
	public String getPattern() {
	 	return pattern;
	}
	/**
	 * @设置 模式 1按自然月2按期3指定天数
	 * @param pattern
	 */
	public void setPattern(String pattern) {
	 	this.pattern = pattern;
	}
	/**
	 * @return 提醒天数
	 */
	public Integer getRemindTime() {
	 	return remindTime;
	}
	/**
	 * @设置 提醒天数
	 * @param remindTime
	 */
	public void setRemindTime(Integer remindTime) {
	 	this.remindTime = remindTime;
	}
	/**
	 * @return 启用标识0禁用1启用
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 启用标识0禁用1启用
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
	}
	/**
	 * @return 检查提醒说明
	 */
	public String getRemark() {
	 	return remark;
	}
	/**
	 * @设置 检查提醒说明
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
}