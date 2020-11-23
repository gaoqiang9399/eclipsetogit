package app.component.finance.vchout.entity;
import app.base.BaseDomain;
/**
* Title: CwVchOutSoftver.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sat Dec 23 14:02:32 CST 2017
* @version：1.0
**/
public class CwVchOutSoftver extends BaseDomain {
	private String softId;//唯一编号
	private String softName;//软件名称
	private String version;//版本号
	private String fileType;//文件类型（0 excel格式；1 xml格式 ）
	private String softType;//软件类型（0 系统；1 外部）
	private String useAuto;//默认使用（0 禁用；1 默认启用）
	private String headersName;//导出的表头 excel使用
	private String headersColum;//导出的表头字段 excel使用
	private String ext1;//备用1
	private String ext2;//备用2
	private String ext3;//备用3
	private String ext4;//备用4
	private String ext5;//备用5
	private String ext6;//备用6
	private String ext7;//备用7
	private String ext8;//备用8
	private String ext9;//备用9
	private String ext10;//备用10
	private String operation;//操作

	/**
	 * @return 唯一编号
	 */
	public String getSoftId() {
	 	return softId;
	}
	/**
	 * @设置 唯一编号
	 * @param softId
	 */
	public void setSoftId(String softId) {
	 	this.softId = softId;
	}
	/**
	 * @return 软件名称
	 */
	public String getSoftName() {
	 	return softName;
	}
	/**
	 * @设置 软件名称
	 * @param softName
	 */
	public void setSoftName(String softName) {
	 	this.softName = softName;
	}
	/**
	 * @return 版本号
	 */
	public String getVersion() {
	 	return version;
	}
	/**
	 * @设置 版本号
	 * @param version
	 */
	public void setVersion(String version) {
	 	this.version = version;
	}
	/**
	 * @return 文件类型（0 excel格式；1 xml格式）
	 */
	public String getFileType() {
	 	return fileType;
	}
	/**
	 * @设置 文件类型（0 excel格式；1 xml格式）
	 * @param fileType
	 */
	public void setFileType(String fileType) {
	 	this.fileType = fileType;
	}
	/**
	 * @return 软件类型（0 系统；1 外部）
	 */
	public String getSoftType() {
	 	return softType;
	}
	/**
	 * @设置 软件类型（0 系统；1 外部）
	 * @param softType
	 */
	public void setSoftType(String softType) {
	 	this.softType = softType;
	}
	/**
	 * @return 默认使用（0 禁用；1 默认启用）
	 */
	public String getUseAuto() {
	 	return useAuto;
	}
	/**
	 * @设置 默认使用（0 禁用；1 默认启用）
	 * @param useAuto
	 */
	public void setUseAuto(String useAuto) {
	 	this.useAuto = useAuto;
	}
	/**
	 * @return 导出的表头 excel使用
	 */
	public String getHeadersName() {
	 	return headersName;
	}
	/**
	 * @设置 导出的表头 excel使用
	 * @param headersName
	 */
	public void setHeadersName(String headersName) {
	 	this.headersName = headersName;
	}
	/**
	 * @return 导出的表头字段 excel使用
	 */
	public String getHeadersColum() {
	 	return headersColum;
	}
	/**
	 * @设置 导出的表头字段 excel使用
	 * @param headersColum
	 */
	public void setHeadersColum(String headersColum) {
	 	this.headersColum = headersColum;
	}
	/**
	 * @return 备用1
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 备用1
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 备用2
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 备用2
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 备用3
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 备用3
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 备用4
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 备用4
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 备用5
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 备用5
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 备用6
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 备用6
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 备用7
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 备用7
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 备用8
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 备用8
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 备用9
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 备用9
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 备用10
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 备用10
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
}