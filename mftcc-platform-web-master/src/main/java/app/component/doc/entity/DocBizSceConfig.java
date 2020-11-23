package app.component.doc.entity;

import app.base.BaseDomain;

/**
 * Title: DocBizSceConfig.java Description:
 * 
 * @author：@dhcc.com.cn
 * @Sat Jan 16 06:27:40 GMT 2016
 * @version：1.0
 **/
public class DocBizSceConfig extends BaseDomain {
	private static final long serialVersionUID = -8811968086947290829L;
	private String scNo;// 场景编号
	private String scName;// 场景名称
	private String docType;// 要件主类型
	private String dime1;// 客户类型号/产品号
	private String docSplitNo;// 要件细分类型编号
	private String ifMustInput;//
	private Double docSizeLimit;//
	private String ifMustRead;
	private String docSplitName;// 要件细分类型名称
	private String formId;// 表单编号

	private String docBizSceConfigId;// 唯一编号
	private String category;// 类别 1：业务；2：功能点
	private String busModel;// 业务模式
	private String modelType;// 是否业务模式基础数据，normal：正常；base：基础；
	private String opNo;// 操作员编号
	private String opName;// 操作员名称
	private String brNo;// 部门编号
	private String brName;// 部门名称
	private String regTime;// 登记时间
	private String lstModTime;// 最后修改时间
	private String docBizNo;// 业务编号

	// ---------- set get ----------

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

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDime1() {
		return dime1;
	}

	public void setDime1(String dime1) {
		this.dime1 = dime1;
	}

	public String getDocSplitNo() {
		return docSplitNo;
	}

	public void setDocSplitNo(String docSplitNo) {
		this.docSplitNo = docSplitNo;
	}

	public String getIfMustInput() {
		return ifMustInput;
	}

	public void setIfMustInput(String ifMustInput) {
		this.ifMustInput = ifMustInput;
	}

	public Double getDocSizeLimit() {
		return docSizeLimit;
	}

	public void setDocSizeLimit(Double docSizeLimit) {
		this.docSizeLimit = docSizeLimit;
	}

	public String getIfMustRead() {
		return ifMustRead;
	}

	public void setIfMustRead(String ifMustRead) {
		this.ifMustRead = ifMustRead;
	}

	public String getDocSplitName() {
		return docSplitName;
	}

	public void setDocSplitName(String docSplitName) {
		this.docSplitName = docSplitName;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getDocBizSceConfigId() {
		return docBizSceConfigId;
	}

	public void setDocBizSceConfigId(String docBizSceConfigId) {
		this.docBizSceConfigId = docBizSceConfigId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBusModel() {
		return busModel;
	}

	public void setBusModel(String busModel) {
		this.busModel = busModel;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getOpNo() {
		return opNo;
	}

	public void setOpNo(String opNo) {
		this.opNo = opNo;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getRegTime() {
		return regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getLstModTime() {
		return lstModTime;
	}

	public void setLstModTime(String lstModTime) {
		this.lstModTime = lstModTime;
	}

	public String getDocBizNo() {
		return docBizNo;
	}

	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}
}