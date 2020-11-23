package app.component.interfaces.mobileinterface.entity;

public class AppDocManageDTO {
	private String type;//判断1、身份证信息	2、授信申请附件	3、融资申请附件
	private String docBizNo;//  业务场景编号
	private String docSplitNo;//细分类型编号
	private String docNo;//文件编号
	private String docName;//文件名称
	private Double docSize;//文件大小
	private String docAddr;//存放地址
	private String docEncryptAddr;//加密存放地址
	private String isRead;//
	private String ifMustRead;//
	private String regDate;//
	private String regNo;//
	private String orgNo;//
	private String docType;//文档类型
	private String relNo;//业务编号
	private String cusNo;//客户号
	public String getDocBizNo() {
		return docBizNo;
	}
	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}
	public String getDocSplitNo() {
		return docSplitNo;
	}
	public void setDocSplitNo(String docSplitNo) {
		this.docSplitNo = docSplitNo;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public Double getDocSize() {
		return docSize;
	}
	public void setDocSize(Double docSize) {
		this.docSize = docSize;
	}
	public String getDocAddr() {
		return docAddr;
	}
	public void setDocAddr(String docAddr) {
		this.docAddr = docAddr;
	}
	public String getDocEncryptAddr() {
		return docEncryptAddr;
	}
	public void setDocEncryptAddr(String docEncryptAddr) {
		this.docEncryptAddr = docEncryptAddr;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getIfMustRead() {
		return ifMustRead;
	}
	public void setIfMustRead(String ifMustRead) {
		this.ifMustRead = ifMustRead;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getRelNo() {
		return relNo;
	}
	public void setRelNo(String relNo) {
		this.relNo = relNo;
	}
	public String getCusNo() {
		return cusNo;
	}
	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
