package app.base;

import java.util.List;

/**
 * Mybatis条件查询映射类
 * @author jzh
 * @2015-12-12 11:00:00
 */
public class Criteria {
	/**
	 * 字段名称
	 */
	private String condition;
	/**
	 * 查询类型
	 */
	private int type;
	/**
	 * 值1
	 */
	private Object value;
	/**
	 * 值2
	 */
	private String secondValue;
	/**
	 * 是否有值
	 */
	private boolean noValue = false;
	/**
	 * 是否一个值
	 */
	private boolean singleValue = false;
	/**
	 * 是否是between
	 */
	private boolean betweenValue = false;
	/**
	 * 是否list
	 */
	private boolean listValue = false;
	/**
	 * 是否迷糊查询
	 */
	private boolean likeValue = false;
	/**
	 * 是否权限控制
	 */
	private boolean roleValue = false;
	/**
	 * 是否选中有效
	 */
	private boolean checked;
	/**
	 *组内是并/或关系
	 */
	private String andOr;
	/**
	 * 是否是分组标识
	 */
	private boolean group;
	/**
	 * 是否是组开始
	 */
	private boolean groupStart;
	/**
	 * 是否是组结束
	 */
	private boolean groupEnd;
	
	private List<CustomSort> customSorts;
	/**
	 * geter seter
	 */
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getSecondValue() {
		return secondValue;
	}
	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}
	public boolean isNoValue() {
		return noValue;
	}
	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}
	public boolean isSingleValue() {
		return singleValue;
	}
	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}
	public boolean isBetweenValue() {
		return betweenValue;
	}
	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}
	public boolean isListValue() {
		return listValue;
	}
	public void setListValue(boolean listValue) {
		this.listValue = listValue;
	}
	public boolean isLikeValue() {
		return likeValue;
	}
	public void setLikeValue(boolean likeValue) {
		this.likeValue = likeValue;
	}
	public boolean isRoleValue() {
		return roleValue;
	}
	public void setRoleValue(boolean roleValue) {
		this.roleValue = roleValue;
	}
	public String getAndOr() {
		return andOr;
	}
	public void setAndOr(String andOr) {
		this.andOr = andOr;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isGroup() {
		return group;
	}
	public void setGroup(boolean isGroup) {
		this.group = isGroup;
	}
	public boolean isGroupStart() {
		return groupStart;
	}
	public void setGroupStart(boolean groupStart) {
		this.groupStart = groupStart;
	}
	public boolean isGroupEnd() {
		return groupEnd;
	}
	public void setGroupEnd(boolean groupEnd) {
		this.groupEnd = groupEnd;
	}
	public List<CustomSort> getCustomSorts() {
		return customSorts;
	}
	public void setCustomSorts(List<CustomSort> customSorts) {
		this.customSorts = customSorts;
	}
}
