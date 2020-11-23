package app.component.nmd.entity;
import app.base.BaseDomain;
/**
* Title: ParLoanuse.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Feb 11 15:08:40 CST 2018
* @version：1.0
**/
public class ParLoanuse extends BaseDomain {
	
	private String useNo;//用途编号
	private String useName;//用途名称
	private String useDescribe;//用途描述
	private String lev;//所属级别
	private String uplev;//上级级别
	private String useFlag;//启用禁用标记
	private String category;// 门类, 用于查询
	private String id;
	private String name;
	private String pId;

	public String getUseNo() {
		return useNo;
	}
	public void setUseNo(String useNo) {
		this.useNo = useNo;
	}
	public String getUseName() {
		return useName;
	}
	public void setUseName(String useName) {
		this.useName = useName;
	}
	public String getUseDescribe() {
		return useDescribe;
	}
	public void setUseDescribe(String useDescribe) {
		this.useDescribe = useDescribe;
	}
	public String getLev() {
		return lev;
	}
	public void setLev(String lev) {
		this.lev = lev;
	}
	public String getUplev() {
		return uplev;
	}
	public void setUplev(String uplev) {
		this.uplev = uplev;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
}