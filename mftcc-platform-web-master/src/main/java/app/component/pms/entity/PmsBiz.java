package app.component.pms.entity;

import app.base.BaseDomain;

public class PmsBiz extends BaseDomain{
	private String pmsSerno;//功能定义流水号（PK）
	private String bizType;//功能类型 1-分类；2-功能点（只有功能点的时候，pms_biz_no才值）
	private String pmsBizNo;//功能权限编号（可多个功能用一个NO）
	private String pmsBizName;//功能描述/分类描述
	private String parentPmsSerno;//上级功能主键
	private String bizState;//状态 1-生效；2-失效
	private String id;
	private String name;
	private String pId;
	private String[] pmsSernoArr;
	
	
	public String getPmsSerno() {
		return pmsSerno;
	}
	public void setPmsSerno(String pmsSerno) {
		this.pmsSerno = pmsSerno;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getPmsBizNo() {
		return pmsBizNo;
	}
	public void setPmsBizNo(String pmsBizNo) {
		this.pmsBizNo = pmsBizNo;
	}
	public String getPmsBizName() {
		return pmsBizName;
	}
	public void setPmsBizName(String pmsBizName) {
		this.pmsBizName = pmsBizName;
	}
	public String getParentPmsSerno() {
		return parentPmsSerno;
	}
	public void setParentPmsSerno(String parentPmsSerno) {
		this.parentPmsSerno = parentPmsSerno;
	}
	public String getBizState() {
		return bizState;
	}
	public void setBizState(String bizState) {
		this.bizState = bizState;
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
	public String[] getPmsSernoArr() {
		return pmsSernoArr;
	}
	public void setPmsSernoArr(String[] pmsSernoArr) {
		this.pmsSernoArr = pmsSernoArr;
	}
}
