/**
 * Copyright (C)  版权所有
 * 文件名： AccesBigBean.java
 * 包名： app.component.rules.entity
 * 说明：
 * @author YuShuai
 * @date 2017-5-10 上午9:05:04
 * @version V1.0
 */ 
package app.component.rules.entity;

import java.util.Map;

/**
 * 类名： AccesBigBean
 * 描述：
 * @author YuShuai
 * @date 2017-5-10 上午9:05:04
 *
 *
 */
public class AccesBigBean {
	private String cusNo;
	private String mustInputCnt1;//营业执照个数
	private String mustInputCnt2;//组织机构个数
	private String mustInputCnt3;//国/地税税务登记证个数
	private String mustInputCnt4;//最新验资报告个数
	private String mustInputCnt5;//公司章程或合伙企业的合伙协议个数
	private String mustInputCnt6;//法定代表人身份证明及个人信息个数
	private String mustInputCnt7;//征信报告个数
	private String mustInputCnt8;//员工证件个数
	private String mustInputCnt9;//开户许可证个数
	private String cusLevelId;//信用等级
	private String rulesNo;//规则引擎编号
	private String nowDate;
	private Map<String,Object> resultMap;//规则引擎返回的结果
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getMustInputCnt1() {
		return mustInputCnt1;
	}
	public void setMustInputCnt1(String mustInputCnt1) {
		this.mustInputCnt1 = mustInputCnt1;
	}
	public String getMustInputCnt2() {
		return mustInputCnt2;
	}
	public void setMustInputCnt2(String mustInputCnt2) {
		this.mustInputCnt2 = mustInputCnt2;
	}
	public String getMustInputCnt3() {
		return mustInputCnt3;
	}
	public void setMustInputCnt3(String mustInputCnt3) {
		this.mustInputCnt3 = mustInputCnt3;
	}
	public String getMustInputCnt4() {
		return mustInputCnt4;
	}
	public void setMustInputCnt4(String mustInputCnt4) {
		this.mustInputCnt4 = mustInputCnt4;
	}
	public String getMustInputCnt5() {
		return mustInputCnt5;
	}
	public void setMustInputCnt5(String mustInputCnt5) {
		this.mustInputCnt5 = mustInputCnt5;
	}
	public String getMustInputCnt6() {
		return mustInputCnt6;
	}
	public void setMustInputCnt6(String mustInputCnt6) {
		this.mustInputCnt6 = mustInputCnt6;
	}
	public String getMustInputCnt7() {
		return mustInputCnt7;
	}
	public void setMustInputCnt7(String mustInputCnt7) {
		this.mustInputCnt7 = mustInputCnt7;
	}
	public String getMustInputCnt8() {
		return mustInputCnt8;
	}
	public void setMustInputCnt8(String mustInputCnt8) {
		this.mustInputCnt8 = mustInputCnt8;
	}
	public String getMustInputCnt9() {
		return mustInputCnt9;
	}
	public void setMustInputCnt9(String mustInputCnt9) {
		this.mustInputCnt9 = mustInputCnt9;
	}
	public String getCusLevelId() {
		return cusLevelId;
	}
	public void setCusLevelId(String cusLevelId) {
		this.cusLevelId = cusLevelId;
	}
	public String getRulesNo() {
		return rulesNo;
	}
	public void setRulesNo(String rulesNo) {
		this.rulesNo = rulesNo;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	
	
}
