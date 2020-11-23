package app.component.pss.utils.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelDefinitionBean {
	/** ID,必须 */
	private String id;

	/** 全类名,必须 */
	private String className;
	
	/** Field属性的全部定义 */
	private List<FieldValueBean> fieldValues = new ArrayList<FieldValueBean>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<FieldValueBean> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldValueBean> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
	
}
