package app.component.doc.entity;

import java.util.List;

public class DocDisplay {
	
	public String docBizNo;
	
	public String docType;
	
	public String docTypeName;
	
	public String docSplitNo;
	
	public String docDesc;

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getDocSplitNo() {
		return docSplitNo;
	}

	public void setDocSplitNo(String docSplitNo) {
		this.docSplitNo = docSplitNo;
	}
	public List<DocBizManage> docBizManages;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocTypeName() {
		return docTypeName;
	}

	public void setDocTypeName(String docTypeName) {
		this.docTypeName = docTypeName;
	}

	public List<DocBizManage> getDocBizManages() {
		return docBizManages;
	}

	public void setDocBizManages(List<DocBizManage> docBizManages) {
		this.docBizManages = docBizManages;
	}

	public String getDocBizNo() {
		return docBizNo;
	}

	public void setDocBizNo(String docBizNo) {
		this.docBizNo = docBizNo;
	}

	
	
	
	
	
	
	

}
