/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfOaNoticeLook.java
 * 包名： app.component.oa.notice.entity
 * 说明：
 * @author 谢静霞
 * @date 2017-6-8 下午8:04:09
 * @version V1.0
 */ 
package app.component.oa.notice.entity;
/**
 * 类名： MfOaNoticeLook
 * 描述：通知公共查阅情况增加展示字段
 * @author 谢静霞
 * @date 2017-6-8 下午8:04:09
 *
 *
 */
public class MfOaNoticeLook extends MfOaNotice{
	
	private String userNo;
	private String userName;
	private String pasSts;
	private String lookTime;//查看时间
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPasSts() {
		return pasSts;
	}
	public void setPasSts(String pasSts) {
		this.pasSts = pasSts;
	}
	public String getLookTime() {
		return lookTime;
	}
	public void setLookTime(String lookTime) {
		this.lookTime = lookTime;
	}

}
