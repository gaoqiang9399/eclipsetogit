package app.component.pms.entity;

import java.util.List;

import app.base.BaseDomain;

/**
 * 数据权限定义实体类
 * @ClassName: pmsDataRang
 */
public class PmsDataRang  extends BaseDomain{

	private String funNo;//业务功能编号

	private String url;//功能对应URL

	private String funDisc;//业务功能描述
	
	private String funMethod;//业务功能对应的
	
	private List<PmsDataSub> pmsDataSubList;
	
	public String getFunNo() {
		return funNo;
	}
	public void setFunNo(String funNo) {
		this.funNo = funNo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFunDisc() {
		return funDisc;
	}
	public void setFunDisc(String funDisc) {
		this.funDisc = funDisc;
	}
	public List<PmsDataSub> getPmsDataSubList() {
		return pmsDataSubList;
	}
	public void setPmsDataSubList(List<PmsDataSub> pmsDataSubList) {
		this.pmsDataSubList = pmsDataSubList;
	}
	public String getFunMethod() {
		return funMethod;
	}
	public void setFunMethod(String funMethod) {
		this.funMethod = funMethod;
	}
}
