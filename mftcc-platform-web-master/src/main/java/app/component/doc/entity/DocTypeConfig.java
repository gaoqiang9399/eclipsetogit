package app.component.doc.entity;
import app.base.BaseDomain;
/**
* Title: DocTypeConfig.java
* Description:
* @author：@dhcc.com.cn
* @Sat Jan 16 09:13:29 GMT 2016
* @version：1.0
**/
public class DocTypeConfig extends BaseDomain {
	private String docType;//文档类型大类
    private String creditDocType;//信贷文档类型 1-权证类,2-要件类,3-管理类,4-保全类,5-综合类,6-征信报告类,9-其他类,10-身份资料,11-客户资料,12-申请资料,13-评级资料
    private String docSplitNo;//文档编号
	private String docSplitName;//文档名称
	private String baseFormNo;//预览页面右侧表基础表单编号
	private String formNo;//预览页面右侧子表单编号
	private String docDesc;//文档描述
	private String useFlag;//启用禁用标志1-启用，0-禁用
	
	//这三个属性是为了生成zTree结构才需要的
	private String id;//
	private String pId;//
	private String name;//
	/**
	 * @return 
	 */
	public String getDocType() {
	 	return docType;
	}
	/**
	 * @设置 
	 * @param docType
	 */
	public void setDocType(String docType) {
	 	this.docType = docType;
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
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBaseFormNo() {
		return baseFormNo;
	}
	public void setBaseFormNo(String baseFormNo) {
		this.baseFormNo = baseFormNo;
	}

    public String getCreditDocType() {
        return creditDocType;
    }

    public void setCreditDocType(String creditDocType) {
        this.creditDocType = creditDocType;
    }
}