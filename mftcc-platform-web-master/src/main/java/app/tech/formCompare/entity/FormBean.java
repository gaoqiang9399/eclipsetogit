package app.tech.formCompare.entity;

public class FormBean {
	private String formId;//表单id
	private String fieldName;//字段名称
	private int type;//1-新增字段，2-更新,9-删除
	private int forceUpdate;//0-不强更新，1-强制更新
	private String property;//属性
	private String beforeValue;//更新之前值
	private String currValue;//当前值
	private String updateSts;//更新状态 0-未更新 ，1- 已更新，9-更新失败
	private String updateDate;//更新日期
	private String updateBatch;//更新批次
	private String anchor;//锚点，等于节点fieldName属性
	private int position;//新节点添加位置；0-上，1-下，2-左，3-右

	
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getForceUpdate() {
		return forceUpdate;
	}
	public void setForceUpdate(int forceUpdate) {
		this.forceUpdate = forceUpdate;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getBeforeValue() {
		return beforeValue;
	}
	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}
	public String getCurrValue() {
		return currValue;
	}
	public void setCurrValue(String currValue) {
		this.currValue = currValue;
	}
	public String getUpdateSts() {
		return updateSts;
	}
	public void setUpdateSts(String updateSts) {
		this.updateSts = updateSts;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBatch() {
		return updateBatch;
	}
	public void setUpdateBatch(String updateBatch) {
		this.updateBatch = updateBatch;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}
