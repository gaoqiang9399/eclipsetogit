package app.component.model.entity;
import app.base.BaseDomain;
/**
* Title: MfTemplateModelRel.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Tue Nov 22 11:17:10 CST 2016
* @version：1.0
**/
public class MfTemplateModelRel extends BaseDomain {
	private String serialNo;//记录编号
	private String relNo;//关联编号
	private String modelNo;//模板模型编号
	private String templateNo;//模板编号
	private String generatePhase;//生成阶段
	private String templateType;//模板文档类型
	private String templateNameZh;//模板文档名称
	private String templateNameEn;//模板英文名
	private String opNo;//登记人编号
	private String opName;//登记人姓名
	private String brNo;//登记部门编号
	private String brName;//登记部门名称
	private String regTime;//登记时间
	private String lstModTime;//最后修改时间
	private String docFilePath;//文档存储路径
	private String docFileName;//保存后的文件名称
	private String saveSts;//文件保存状态 0-未保存 1-已保存

	/**
	 * @return 记录编号
	 */
	public String getSerialNo() {
	 	return serialNo;
	}
	/**
	 * @设置 记录编号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
	 	this.serialNo = serialNo;
	}
	/**
	 * @return 关联编号
	 */
	public String getRelNo() {
	 	return relNo;
	}
	/**
	 * @设置 关联编号
	 * @param relNo
	 */
	public void setRelNo(String relNo) {
	 	this.relNo = relNo;
	}
	/**
	 * @return 模板模型编号
	 */
	public String getModelNo() {
	 	return modelNo;
	}
	/**
	 * @设置 模板模型编号
	 * @param modelNo
	 */
	public void setModelNo(String modelNo) {
	 	this.modelNo = modelNo;
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
	 * @return 生成阶段
	 */
	public String getGeneratePhase() {
	 	return generatePhase;
	}
	/**
	 * @设置 生成阶段
	 * @param generatePhase
	 */
	public void setGeneratePhase(String generatePhase) {
	 	this.generatePhase = generatePhase;
	}
	/**
	 * @return 模板文档类型
	 */
	public String getTemplateType() {
	 	return templateType;
	}
	/**
	 * @设置 模板文档类型
	 * @param templateType
	 */
	public void setTemplateType(String templateType) {
	 	this.templateType = templateType;
	}
	/**
	 * @return 模板文档名称
	 */
	public String getTemplateNameZh() {
	 	return templateNameZh;
	}
	/**
	 * @设置 模板文档名称
	 * @param templateNameZh
	 */
	public void setTemplateNameZh(String templateNameZh) {
	 	this.templateNameZh = templateNameZh;
	}
	/**
	 * @return 模板英文名
	 */
	public String getTemplateNameEn() {
	 	return templateNameEn;
	}
	/**
	 * @设置 模板英文名
	 * @param templateNameEn
	 */
	public void setTemplateNameEn(String templateNameEn) {
	 	this.templateNameEn = templateNameEn;
	}
	/**
	 * @return 登记人编号
	 */
	public String getOpNo() {
	 	return opNo;
	}
	/**
	 * @设置 登记人编号
	 * @param opNo
	 */
	public void setOpNo(String opNo) {
	 	this.opNo = opNo;
	}
	/**
	 * @return 登记人姓名
	 */
	public String getOpName() {
	 	return opName;
	}
	/**
	 * @设置 登记人姓名
	 * @param opName
	 */
	public void setOpName(String opName) {
	 	this.opName = opName;
	}
	/**
	 * @return 登记部门编号
	 */
	public String getBrNo() {
	 	return brNo;
	}
	/**
	 * @设置 登记部门编号
	 * @param brNo
	 */
	public void setBrNo(String brNo) {
	 	this.brNo = brNo;
	}
	/**
	 * @return 登记部门名称
	 */
	public String getBrName() {
	 	return brName;
	}
	/**
	 * @设置 登记部门名称
	 * @param brName
	 */
	public void setBrName(String brName) {
	 	this.brName = brName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
	/**
	 * @return 文档存储路径
	 */
	public String getDocFilePath() {
	 	return docFilePath;
	}
	/**
	 * @设置 文档存储路径
	 * @param docFilePath
	 */
	public void setDocFilePath(String docFilePath) {
	 	this.docFilePath = docFilePath;
	}
	/**
	 * @return 保存后的文件名称
	 */
	public String getDocFileName() {
	 	return docFileName;
	}
	/**
	 * @设置 保存后的文件名称
	 * @param docFileName
	 */
	public void setDocFileName(String docFileName) {
	 	this.docFileName = docFileName;
	}
	/**
	 * @return 文件保存状态 0-未保存 1-已保存
	 */
	public String getSaveSts() {
	 	return saveSts;
	}
	/**
	 * @设置 文件保存状态 0-未保存 1-已保存
	 * @param saveSts
	 */
	public void setSaveSts(String saveSts) {
	 	this.saveSts = saveSts;
	}
}