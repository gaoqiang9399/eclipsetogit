package app.component.report.entity;

import app.base.BaseDomain;

public class MfReportFilter extends BaseDomain{
	private String id; // 唯一id
	private String opNo;// 操作员编号
	private String queryClass;//查询类别
	private String queryName;//查询名称
	private String queryDescript;//查询描述
	private String queryContent;//查询条件
	private String queryId;// 报表uuid
	private String useFlag;//启用标志 0禁用 1启用
	private String lstModTime;//最后修改时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpNo() {
		return opNo;
	}
	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}
	public String getQueryClass() {
		return queryClass;
	}
	public void setQueryClass(String queryClass) {
		this.queryClass = queryClass;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getQueryDescript() {
		return queryDescript;
	}
	public void setQueryDescript(String queryDescript) {
		this.queryDescript = queryDescript;
	}
	public String getQueryContent() {
		return queryContent;
	}
	public void setQueryContent(String queryContent) {
		this.queryContent = queryContent;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getLstModTime() {
		return lstModTime;
	}
	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}
	
}
