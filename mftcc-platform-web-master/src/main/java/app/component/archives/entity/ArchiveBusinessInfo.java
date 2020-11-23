package app.component.archives.entity;
import app.base.BaseDomain;
/**
* Title: ArchiveBusinessInfo.java
* Description:归档业务信息
* @author：yudongwei@mftcc.cn
* @Fri Apr 07 13:45:47 CST 2017
* @version：1.0
**/
public class ArchiveBusinessInfo extends BaseDomain {
	private String archivePactStatus;//合同状态（01-合同签订 02-合同完结）
	private String cusNo;//客户号
	private String pactId;//合同号
	private String appId;//申请号
	private String pleId;//押品编号
	private String paperAddr;//纸质文件存储位置
	private String paperNo;//纸质文件编号
	private String paperKeeperName;//保管人名称
	private String paperKeeperNo;//保管人名称
	/**
	 * @return 合同状态（01-合同签订 02-合同完结）
	 */
	public String getArchivePactStatus() {
		return archivePactStatus;
	}
	/**
	 * @设置 合同状态（01-合同签订 02-合同完结）
	 * @param archivePactStatus
	 */
	public void setArchivePactStatus(String archivePactStatus) {
		this.archivePactStatus = archivePactStatus;
	}
	/**
	 * @return 客户号
	 */
	public String getCusNo() {
	 	return cusNo;
	}
	/**
	 * @设置 客户号
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
	 	this.cusNo = cusNo;
	}
	/**
	 * @return 合同号
	 */
	public String getPactId() {
	 	return pactId;
	}
	/**
	 * @设置 合同号
	 * @param pactId
	 */
	public void setPactId(String pactId) {
	 	this.pactId = pactId;
	}
	/**
	 * @return 申请号
	 */
	public String getAppId() {
	 	return appId;
	}
	/**
	 * @设置 申请号
	 * @param appId
	 */
	public void setAppId(String appId) {
	 	this.appId = appId;
	}
	/**
	 * @return 押品编号
	 */
	public String getPleId() {
		return pleId;
	}
	/**
	 * @设置 押品编号
	 * @param appId
	 */
	public void setPleId(String pleId) {
		this.pleId = pleId;
	}
	public String getPaperAddr() {
		return paperAddr;
	}
	public void setPaperAddr(String paperAddr) {
		this.paperAddr = paperAddr;
	}
	public String getPaperNo() {
		return paperNo;
	}
	public void setPaperNo(String paperNo) {
		this.paperNo = paperNo;
	}
	public String getPaperKeeperName() {
		return paperKeeperName;
	}
	public void setPaperKeeperName(String paperKeeperName) {
		this.paperKeeperName = paperKeeperName;
	}
	public String getPaperKeeperNo() {
		return paperKeeperNo;
	}
	public void setPaperKeeperNo(String paperKeeperNo) {
		this.paperKeeperNo = paperKeeperNo;
	}
	
}