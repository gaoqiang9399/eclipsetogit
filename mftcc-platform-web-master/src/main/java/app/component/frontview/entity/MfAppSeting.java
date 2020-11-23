package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfAppSeting.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Oct 24 12:56:14 CST 2017
* @version：1.0
**/
public class MfAppSeting extends BaseDomain {
	private String id;//流水号
	private String modelType;//配置类型
	private String modelName;//配置名称
	private String modelValue;//值

	/**
	 * @return 流水号
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 流水号
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 配置类型
	 */
	public String getModelType() {
	 	return modelType;
	}
	/**
	 * @设置 配置类型
	 * @param modelType
	 */
	public void setModelType(String modelType) {
	 	this.modelType = modelType;
	}
	/**
	 * @return 配置名称
	 */
	public String getModelName() {
	 	return modelName;
	}
	/**
	 * @设置 配置名称
	 * @param modelName
	 */
	public void setModelName(String modelName) {
	 	this.modelName = modelName;
	}
	/**
	 * @return 值
	 */
	public String getModelValue() {
	 	return modelValue;
	}
	/**
	 * @设置 值
	 * @param modelValue
	 */
	public void setModelValue(String modelValue) {
	 	this.modelValue = modelValue;
	}
}