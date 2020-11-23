package app.component.prdct.entity;
import app.base.BaseDomain;
/**
* Title: MfKindNodeTemplate.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Jul 04 12:30:02 CST 2017
* @version：1.0
**/
public class MfKindNodeTemplate extends BaseDomain {
	private String kindNodeTemplateId;//唯一编号
	private String kindNo;//产品编号
	private String busModel;//业务模式
	private String modelType;//是否业务模式基础数据 normal：正常；base：基础；
	private String nodeNo;//节点编号
	private String nodeName;//节点名称
	private String templateNo;//模板编号
	private Integer sort;//排序
	private String optPower;//操作权限 1：查；2：签/改/保存
	private String ifMustInput;//是否必填 0-不必填 1-必填
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String regTime;//登记时间 格式：yyyyMMdd HH:mm:ss
	private String lstModTime;//最后修改时间 格式：yyyyMMdd HH:mm:ss
	private String ext1;//
	private String ext2;//
	private String ext3;//
	private String ext4;//
	private String ext5;//
	private String ext6;//
	private String ext7;//
	private String ext8;//
	private String ext9;//
	private String ext10;//
	private String templateType;// 模板类型 1其他文档模板, 2保证从合同模板, 3抵押从合同模板, 4质押从合同模板, 5转让从合同模板, 6功能按钮模板
	private String templateSuffix;//1-word 2-excel
	/**
	 * @return 唯一编号
	 */
	public String getKindNodeTemplateId() {
	 	return kindNodeTemplateId;
	}
	/**
	 * @设置 唯一编号
	 * @param kindNodeTemplateId
	 */
	public void setKindNodeTemplateId(String kindNodeTemplateId) {
	 	this.kindNodeTemplateId = kindNodeTemplateId;
	}
	/**
	 * @return 产品编号
	 */
	public String getKindNo() {
	 	return kindNo;
	}
	/**
	 * @设置 产品编号
	 * @param kindNo
	 */
	public void setKindNo(String kindNo) {
	 	this.kindNo = kindNo;
	}
	/**
	 * @return 业务模式
	 */
	public String getBusModel() {
	 	return busModel;
	}
	/**
	 * @设置 业务模式
	 * @param busModel
	 */
	public void setBusModel(String busModel) {
	 	this.busModel = busModel;
	}
	/**
	 * @return 是否业务模式基础数据 normal：正常；base：基础；
	 */
	public String getModelType() {
	 	return modelType;
	}
	/**
	 * @设置 是否业务模式基础数据 normal：正常；base：基础；
	 * @param modelType
	 */
	public void setModelType(String modelType) {
	 	this.modelType = modelType;
	}
	/**
	 * @return 节点编号
	 */
	public String getNodeNo() {
	 	return nodeNo;
	}
	/**
	 * @设置 节点编号
	 * @param nodeNo
	 */
	public void setNodeNo(String nodeNo) {
	 	this.nodeNo = nodeNo;
	}
	/**
	 * @return 节点名称
	 */
	public String getNodeName() {
	 	return nodeName;
	}
	/**
	 * @设置 节点名称
	 * @param nodeName
	 */
	public void setNodeName(String nodeName) {
	 	this.nodeName = nodeName;
	}
	/**
	 * @return 模板编号
	 */
	public String getTemplateNo() {
	 	return templateNo;
	}
	/**
	 * @设置 模板编号
	 * @param templateNo
	 */
	public void setTemplateNo(String templateNo) {
	 	this.templateNo = templateNo;
	}
	/**
	 * @return 排序
	 */
	public Integer getSort() {
	 	return sort;
	}
	/**
	 * @设置 排序
	 * @param sort
	 */
	public void setSort(Integer sort) {
	 	this.sort = sort;
	}
	/**
	 * @return 操作权限 1：查；2：签/改/保存
	 */
	public String getOptPower() {
	 	return optPower;
	}
	/**
	 * @设置 操作权限 1：查；2：签/改/保存
	 * @param optPower
	 */
	public void setOptPower(String optPower) {
	 	this.optPower = optPower;
	}
	/**
	 * @return 是否必填 0-不必填 1-必填
	 */
	public String getIfMustInput() {
	 	return ifMustInput;
	}
	/**
	 * @设置 是否必填 0-不必填 1-必填
	 * @param ifMustInput
	 */
	public void setIfMustInput(String ifMustInput) {
	 	this.ifMustInput = ifMustInput;
	}
	/**
	 * @return 操作员编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 操作员编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 操作员名称
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 操作员名称
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间 格式：yyyyMMdd HH:mm:ss
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间 格式：yyyyMMdd HH:mm:ss
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间 格式：yyyyMMdd HH:mm:ss
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间 格式：yyyyMMdd HH:mm:ss
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getTemplateSuffix() {
		return templateSuffix;
	}
	public void setTemplateSuffix(String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}
}