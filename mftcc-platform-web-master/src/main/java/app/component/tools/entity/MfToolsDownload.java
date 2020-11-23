package app.component.tools.entity;
import app.base.BaseDomain;
/**
* Title: MfToolsDownload.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Oct 17 18:29:27 CST 2017
* @version：1.0
**/
public class MfToolsDownload extends BaseDomain {
	private String serialNo;//序号
	private String toolName;//工具名称
	private String version;//版本
	private String instructions;//使用说明
	private String address;//

	/**
	 * @return 序号
	 */
	public String getSerialNo() {
	 	return serialNo;
	}
	/**
	 * @设置 序号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
	 	this.serialNo = serialNo;
	}
	/**
	 * @return 工具名称
	 */
	public String getToolName() {
	 	return toolName;
	}
	/**
	 * @设置 工具名称
	 * @param toolName
	 */
	public void setToolName(String toolName) {
	 	this.toolName = toolName;
	}
	/**
	 * @return 版本
	 */
	public String getVersion() {
	 	return version;
	}
	/**
	 * @设置 版本
	 * @param version
	 */
	public void setVersion(String version) {
	 	this.version = version;
	}
	/**
	 * @return 使用说明
	 */
	public String getInstructions() {
	 	return instructions;
	}
	/**
	 * @设置 使用说明
	 * @param instructions
	 */
	public void setInstructions(String instructions) {
	 	this.instructions = instructions;
	}
	/**
	 * @return 
	 */
	public String getAddress() {
	 	return address;
	}
	/**
	 * @设置 
	 * @param address
	 */
	public void setAddress(String address) {
	 	this.address = address;
	}
}