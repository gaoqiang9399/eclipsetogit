package app.component.doc.entity;

import java.util.List;

import app.base.BaseDomain;

/**
 * Title: DocBizManage.java Description:
 * 
 * @author：@dhcc.com.cn
 * @Tue Jan 26 11:15:50 GMT 2016
 * @version：1.0
 **/
public class DocBizManage extends BaseDomain {
	private static final long serialVersionUID = 6152178123617071797L;
	private String docBizManageId;//唯一主键
	private String docBizNo;//
	private String scNo;//
	private String docType;//
	private String dime1;//
	private String docSplitNo;//
	private String cusNo;//
	private String cusName;//
	private String isUp;//
	private String regDate;//
	private String regNo;//
	private String orgNo;//
	private Double docSizeLimit;//
	private String ifMustRead;//
	private String ifMustInput;//
	private String relNo;//
	private String docSplitName;//
	private String docTypeName;//
	private List<DocManage> docManages;
	private Integer sort;//
	private String docRegTime;//

	private String scName;// 场景名称（页面显示用）
	private List<DocBizManage> subList;// 子集（页面显示用）

	private List<String> relNoList;// 用于where查询条件
	private List<String> docBizNoList;// 用于where查询条件

	public List<DocManage> getDocManages() {
		return docManages;
	}

	public void setDocManages(List<DocManage> docManages) {
		this.docManages = docManages;
	}

	/**
	 * @return
	 */
	public String getDocBizNo() {
		return docBizNo;
	}

	/**
	 * @设置
	 * @param docBizNo
	 */
	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}

	/**
	 * @return
	 */
	public String getScNo() {
		return scNo;
	}

	/**
	 * @设置
	 * @param scNo
	 */
	public void setScNo(String scNo) {
		this.scNo = scNo;
	}

	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @设置
	 * @param dogType
	 */
	public void setDocType(String dogType) {
		this.docType = dogType;
	}

	/**
	 * @return
	 */
	public String getDime1() {
		return dime1;
	}

	/**
	 * @设置
	 * @param dime1
	 */
	public void setDime1(String dime1) {
		this.dime1 = dime1;
	}

	/**
	 * @return
	 */
	public String getDocSplitNo() {
		return docSplitNo;
	}

	/**
	 * @设置
	 * @param docSplitNo
	 */
	public void setDocSplitNo(String docSplitNo) {
		this.docSplitNo = docSplitNo;
	}

	/**
	 * @return
	 */
	public String getCusNo() {
		return cusNo;
	}

	/**
	 * @设置
	 * @param cusNo
	 */
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	/**
	 * @return
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * @设置
	 * @param cusName
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	/**
	 * @return
	 */
	public String getIsUp() {
		return isUp;
	}

	/**
	 * @设置
	 * @param isUp
	 */
	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}

	/**
	 * @return
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @设置
	 * @param regDate
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return
	 */
	public String getRegNo() {
		return regNo;
	}

	/**
	 * @设置
	 * @param regNo
	 */
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	/**
	 * @return
	 */
	public String getOrgNo() {
		return orgNo;
	}

	/**
	 * @设置
	 * @param orgNo
	 */
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	/**
	 * @return
	 */
	public Double getDocSizeLimit() {
		return docSizeLimit;
	}

	/**
	 * @设置
	 * @param docSizeLimit
	 */
	public void setDocSizeLimit(Double docSizeLimit) {
		this.docSizeLimit = docSizeLimit;
	}

	/**
	 * @return
	 */
	public String getIfMustRead() {
		return ifMustRead;
	}

	/**
	 * @设置
	 * @param ifMustRead
	 */
	public void setIfMustRead(String ifMustRead) {
		this.ifMustRead = ifMustRead;
	}

	/**
	 * @return
	 */
	public String getIfMustInput() {
		return ifMustInput;
	}

	/**
	 * @设置
	 * @param ifMustInput
	 */
	public void setIfMustInput(String ifMustInput) {
		this.ifMustInput = ifMustInput;
	}

	/**
	 * @return
	 */
	public String getRelNo() {
		return relNo;
	}

	/**
	 * @设置
	 * @param relNo
	 */
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}

	/**
	 * @return
	 */
	public String getDocSplitName() {
		return docSplitName;
	}

	/**
	 * @设置
	 * @param docSplitName
	 */
	public void setDocSplitName(String docSplitName) {
		this.docSplitName = docSplitName;
	}

	/**
	 * @return
	 */
	public String getDocTypeName() {
		return docTypeName;
	}

	/**
	 * @设置
	 * @param docTypeName
	 */
	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public List<DocBizManage> getSubList() {
		return subList;
	}

	public void setSubList(List<DocBizManage> subList) {
		this.subList = subList;
	}

	public String getDocBizManageId() {
		return docBizManageId;
	}

	public void setDocBizManageId(String docBizManageId) {
		this.docBizManageId = docBizManageId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public List<String> getRelNoList() {
		return relNoList;
	}

	public void setRelNoList(List<String> relNoList) {
		this.relNoList = relNoList;
	}

	public List<String> getDocBizNoList() {
		return docBizNoList;
	}

	public void setDocBizNoList(List<String> docBizNoList) {
		this.docBizNoList = docBizNoList;
	}
	public String getDocRegTime() {
		return docRegTime;
	}

	public void setDocRegTime(String docRegTime) {
		this.docRegTime = docRegTime;
	}
}