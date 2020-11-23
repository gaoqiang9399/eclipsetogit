package app.component.risk.entity;

import java.util.List;

import app.component.nmd.entity.ParmDic;
import app.component.nmd.entity.ParmKey;

public class RiskPrevent {
	
	private String scNo;  //业务场景编号
	
	private String scName; //业务场景名称
	
	private String itemSqlDesc;
	
	private String formularDesc;
	
	private String dimeChnName; //维度组合中文名称 如没有维度，则为默认的 名称为默认
	
	private String dimeParmKeysStr;
	
	private String dicJavaNamesStr;
	
	
	
	private List<ParmKey> dimeParmKeys; //维度字典项组合
	
	private List<ParmDic> dimeParmDics; //维度字典项KEY组合
	
	
	private List<String> dicJavaNames; //维度对应的java变量名
	
	private List<String> dimes;//维度组合字符串
	
	private List<String> dimeVals;//维度组合值
	
	private String relNo;
	
	private String sceDimeUseFlag;//场景维度是否启用，同一场景只能有一组维度生效
	
	private String genNo;//场景维度细分项编号
	
	private String isGenDefault;//是否为场景的默认细分项
	
	private String itemNo;//风险拦截项编号
	
	private String itemName;//风险拦截项名称
	
	private String itemDesc; //风险拦截项目描述
	
	private String riskLevel;//风险级别
	
	private String itemUseInd;//风险拦截使用状态
	
	
	private String threadHoldName; //阀值名称
	
	private String threadHoldVal; //阀值
	
	private String riskPreventClassStr; //拦截项类别
	
	private String riskLevelName; //风险级别名称
	
	private String itemNameSub; //风险拦截项名称截取
	
	private String itemDescSub; //风险拦截项目描述截取
	
	private String pageStr;
	
	private List<RiskItemThreashode> riskItemThreashodes; //阀值

	
	
	public String getPageStr() {
		return pageStr;
	}

	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}

	public String getScNo() {
		return scNo;
	}

	public void setScNo(String scNo) {
		this.scNo = scNo;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	
	public List<ParmKey> getDimeParmKeys() {
		return dimeParmKeys;
	}

	public void setDimeParmKeys(List<ParmKey> dimeParmKeys) {
		this.dimeParmKeys = dimeParmKeys;
	}

	public List<String> getDicJavaNames() {
		return dicJavaNames;
	}

	public void setDicJavaNames(List<String> dicJavaNames) {
		this.dicJavaNames = dicJavaNames;
	}

	public List<String> getDimes() {
		return dimes;
	}

	public void setDimes(List<String> dimes) {
		this.dimes = dimes;
	}

	public List<String> getDimeVals() {
		return dimeVals;
	}

	public void setDimeVals(List<String> dimeVals) {
		this.dimeVals = dimeVals;
	}

	public String getSceDimeUseFlag() {
		return sceDimeUseFlag;
	}

	public void setSceDimeUseFlag(String sceDimeUseFlag) {
		this.sceDimeUseFlag = sceDimeUseFlag;
	}

	public String getGenNo() {
		return genNo;
	}

	public void setGenNo(String genNo) {
		this.genNo = genNo;
	}

	public String getIsGenDefault() {
		return isGenDefault;
	}

	public void setIsGenDefault(String isGenDefault) {
		this.isGenDefault = isGenDefault;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getItemUseInd() {
		return itemUseInd;
	}

	public void setItemUseInd(String itemUseInd) {
		this.itemUseInd = itemUseInd;
	}

	public List<ParmDic> getDimeParmDics() {
		return dimeParmDics;
	}

	public void setDimeParmDics(List<ParmDic> dimeParmDics) {
		this.dimeParmDics = dimeParmDics;
	}

	public List<RiskItemThreashode> getRiskItemThreashodes() {
		return riskItemThreashodes;
	}

	public void setRiskItemThreashodes(List<RiskItemThreashode> riskItemThreashodes) {
		this.riskItemThreashodes = riskItemThreashodes;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDimeParmKeysStr() {
		return dimeParmKeysStr;
	}

	public void setDimeParmKeysStr(String dimeParmKeysStr) {
		this.dimeParmKeysStr = dimeParmKeysStr;
	}

	public String getDicJavaNamesStr() {
		return dicJavaNamesStr;
	}

	public void setDicJavaNamesStr(String dicJavaNamesStr) {
		this.dicJavaNamesStr = dicJavaNamesStr;
	}

	public String getDimeChnName() {
		return dimeChnName;
	}

	public void setDimeChnName(String dimeChnName) {
		this.dimeChnName = dimeChnName;
	}

	public String getRelNo() {
		return relNo;
	}

	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}

	public String getItemSqlDesc() {
		return itemSqlDesc;
	}

	public void setItemSqlDesc(String itemSqlDesc) {
		this.itemSqlDesc = itemSqlDesc;
	}

	public String getThreadHoldName() {
		return threadHoldName;
	}

	public void setThreadHoldName(String threadHoldName) {
		this.threadHoldName = threadHoldName;
	}

	public String getThreadHoldVal() {
		return threadHoldVal;
	}

	public void setThreadHoldVal(String threadHoldVal) {
		this.threadHoldVal = threadHoldVal;
	}

	public String getFormularDesc() {
		return formularDesc;
	}

	public void setFormularDesc(String formularDesc) {
		this.formularDesc = formularDesc;
	}

	public String getRiskPreventClassStr() {
		return riskPreventClassStr;
	}

	public void setRiskPreventClassStr(String riskPreventClassStr) {
		this.riskPreventClassStr = riskPreventClassStr;
	}


	public String getRiskLevelName() {
		return riskLevelName;
	}

	public void setRiskLevelName(String riskLevelName) {
		this.riskLevelName = riskLevelName;
	}

	public String getItemNameSub() {
		return itemNameSub;
	}

	public void setItemNameSub(String itemNameSub) {
		this.itemNameSub = itemNameSub;
	}

	public String getItemDescSub() {
		return itemDescSub;
	}

	public void setItemDescSub(String itemDescSub) {
		this.itemDescSub = itemDescSub;
	}
	
	
	
	
	


}
