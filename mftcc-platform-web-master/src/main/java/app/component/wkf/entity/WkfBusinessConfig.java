package app.component.wkf.entity;
import app.base.BaseDomain;
/**
* Title: WkfBusinessConfig.java
* Description:
* @author��renyongxian@dhcc.com.cn
* @Thu Feb 28 12:59:54 GMT 2013
* @version��1.0
**/
public class WkfBusinessConfig extends BaseDomain {
	private String objName;//�������
	private String objAttributeName;//�����������
	private String des;//��������
	private String attributeType;//��������
	private String sts;//�Ƿ�����
	private Integer priority;//˳��

	/**
	 * @return �������
	 */
	 public String getObjName() {
	 	return objName;
	 }
	 /**
	 * @���� �������
	 * @param objName
	 */
	 public void setObjName(String objName) {
	 	this.objName = objName;
	 }
	/**
	 * @return �����������
	 */
	 public String getObjAttributeName() {
	 	return objAttributeName;
	 }
	 /**
	 * @���� �����������
	 * @param objAttributeName
	 */
	 public void setObjAttributeName(String objAttributeName) {
	 	this.objAttributeName = objAttributeName;
	 }
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
}