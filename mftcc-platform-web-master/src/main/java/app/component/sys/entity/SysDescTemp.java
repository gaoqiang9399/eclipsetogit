package app.component.sys.entity;
import app.base.BaseDomain;
/**
* Title: SysDescTemp.java
* Description:
* @author：@dhcc.com.cn
* @Wed Jan 23 11:38:31 CST 2013
* @version：1.0
**/

public class SysDescTemp extends BaseDomain {
	private String  desctempNo;//模板编号
	private String  desctempName;//模板名称
	private String  desctempContent;//模板公式
	private String  desctempSts;//启动标志
	private String  viewpointNo;//视角编号
	private String  viewpointName;//视角名称
	public String getDesctempNo() {
		return desctempNo;
	}
	public void setDesctempNo(String desctempNo) {
		this.desctempNo = desctempNo;
	}
	public String getDesctempName() {
		return desctempName;
	}
	public void setDesctempName(String desctempName) {
		this.desctempName = desctempName;
	}
	public String getDesctempContent() {
		return desctempContent;
	}
	public void setDesctempContent(String desctempContent) {
		this.desctempContent = desctempContent;
	}
	public String getDesctempSts() {
		return desctempSts;
	}
	public void setDesctempSts(String desctempSts) {
		this.desctempSts = desctempSts;
	}
	public String getViewpointNo() {
		return viewpointNo;
	}
	public void setViewpointNo(String viewpointNo) {
		this.viewpointNo = viewpointNo;
	}
	public String getViewpointName() {
		return viewpointName;
	}
	public void setViewpointName(String viewpointName) {
		this.viewpointName = viewpointName;
	}
	
	
}
