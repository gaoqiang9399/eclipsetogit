package app.component.report.base;

import java.util.List;

/***
 * 类描述：
 * @author 李伟
 * @date 2017-8-24 下午2:32:49
 */
public class MfReportBaseBean {
	private List<String> brNoList;//部门List
	private List<String> opNoList;//操作员List
	
	public List<String> getBrNoList() {
		return brNoList;
	}
	public void setBrNoList(List<String> brNoList) {
		this.brNoList = brNoList;
	}
	public List<String> getOpNoList() {
		return opNoList;
	}
	public void setOpNoList(List<String> opNoList) {
		this.opNoList = opNoList;
	}
 
}
