package app.component.prdct.entity;
import java.util.List;

import app.base.BaseDomain;
/**
* Title: MfKindFlow.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 30 11:02:44 CST 2017
* @version：1.0
**/
public class MfKindFlow extends BaseDomain {
	private String kindFlowId;//唯一编号
	private String kindNo;//产品编号
	private String busModel;//业务模式
	private String modelType;//是否业务模式基础数据 normal：正常；base：基础；
	private String category;//1：业务主流程；2：业务审批流程；3：押品审批流程；4:贷后审批流程 5：客户审批流程
	private String flowApprovalNo;//审批流程类型编号 (apply_flow 业务主流程 apply_approval 业务审批contract_approval 合同审批 putout_approval 放款审批) 
	private String flowApprovalName;//审批流程类型名称
	private String flowId;//流程唯一编号
	private String flowRemark;//流程描述
	private Integer sort;//排序
	private String useFlag;//是否启用 0：否；1：是；
	private String opNo;//操作员编号
	private String opName;//操作员名称
	private String brNo;//部门编号
	private String brName;//部门名称
	private String regTime;//登记时间 格式：yyyyMMdd HH:mm:ss
	private String lstModTime;//最后修改时间 格式：yyyyMMdd HH:mm:ss
	private String ext1;//扩展字段
	private String ext2;//扩展字段
	private String ext3;//扩展字段
	private String ext4;//扩展字段
	private String ext5;//扩展字段
	private String ext6;//扩展字段
	private String ext7;//扩展字段
	private String ext8;//扩展字段
	private String ext9;//扩展字段
	private String ext10;//扩展字段
	private String delFlag;//软删除标记
	
	private List<String> list;//用于查询多个业务模式

	/**
	 * @return 唯一编号
	 */
	public String getKindFlowId() {
	 	return kindFlowId;
	}
	/**
	 * @设置 唯一编号
	 * @param kindFlowId
	 */
	public void setKindFlowId(String kindFlowId) {
	 	this.kindFlowId = kindFlowId;
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
	 * @return 1：业务主流程；2：业务审批流程；3：押品审批流程；...
	 */
	public String getCategory() {
	 	return category;
	}
	/**
	 * @设置 1：业务主流程；2：业务审批流程；3：押品审批流程；...
	 * @param category
	 */
	public void setCategory(String category) {
	 	this.category = category;
	}
	/**
	 * @return 审批流程类型编号 (apply_flow 业务主流程 apply_approval 业务审批contract_approval 合同审批 putout_approval 放款审批) 
	 */
	public String getFlowApprovalNo() {
	 	return flowApprovalNo;
	}
	/**
	 * @设置 审批流程类型编号 (apply_flow 业务主流程 apply_approval 业务审批contract_approval 合同审批 putout_approval 放款审批) 
	 * @param flowApprovalNo
	 */
	public void setFlowApprovalNo(String flowApprovalNo) {
	 	this.flowApprovalNo = flowApprovalNo;
	}
	/**
	 * @return 审批流程类型名称
	 */
	public String getFlowApprovalName() {
	 	return flowApprovalName;
	}
	/**
	 * @设置 审批流程类型名称
	 * @param flowApprovalName
	 */
	public void setFlowApprovalName(String flowApprovalName) {
	 	this.flowApprovalName = flowApprovalName;
	}
	/**
	 * @return 流程唯一编号
	 */
	public String getFlowId() {
	 	return flowId;
	}
	/**
	 * @设置 流程唯一编号
	 * @param flowId
	 */
	public void setFlowId(String flowId) {
	 	this.flowId = flowId;
	}
	/**
	 * @return 流程描述
	 */
	public String getFlowRemark() {
	 	return flowRemark;
	}
	/**
	 * @设置 流程描述
	 * @param flowRemark
	 */
	public void setFlowRemark(String flowRemark) {
	 	this.flowRemark = flowRemark;
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
	 * @return 是否启用 0：否；1：是；
	 */
	public String getUseFlag() {
	 	return useFlag;
	}
	/**
	 * @设置 是否启用 0：否；1：是；
	 * @param useFlag
	 */
	public void setUseFlag(String useFlag) {
	 	this.useFlag = useFlag;
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
	 * @return 扩展字段
	 */
	public String getExt1() {
	 	return ext1;
	}
	/**
	 * @设置 扩展字段
	 * @param ext1
	 */
	public void setExt1(String ext1) {
	 	this.ext1 = ext1;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt2() {
	 	return ext2;
	}
	/**
	 * @设置 扩展字段
	 * @param ext2
	 */
	public void setExt2(String ext2) {
	 	this.ext2 = ext2;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt3() {
	 	return ext3;
	}
	/**
	 * @设置 扩展字段
	 * @param ext3
	 */
	public void setExt3(String ext3) {
	 	this.ext3 = ext3;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt4() {
	 	return ext4;
	}
	/**
	 * @设置 扩展字段
	 * @param ext4
	 */
	public void setExt4(String ext4) {
	 	this.ext4 = ext4;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt5() {
	 	return ext5;
	}
	/**
	 * @设置 扩展字段
	 * @param ext5
	 */
	public void setExt5(String ext5) {
	 	this.ext5 = ext5;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt6() {
	 	return ext6;
	}
	/**
	 * @设置 扩展字段
	 * @param ext6
	 */
	public void setExt6(String ext6) {
	 	this.ext6 = ext6;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt7() {
	 	return ext7;
	}
	/**
	 * @设置 扩展字段
	 * @param ext7
	 */
	public void setExt7(String ext7) {
	 	this.ext7 = ext7;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt8() {
	 	return ext8;
	}
	/**
	 * @设置 扩展字段
	 * @param ext8
	 */
	public void setExt8(String ext8) {
	 	this.ext8 = ext8;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt9() {
	 	return ext9;
	}
	/**
	 * @设置 扩展字段
	 * @param ext9
	 */
	public void setExt9(String ext9) {
	 	this.ext9 = ext9;
	}
	/**
	 * @return 扩展字段
	 */
	public String getExt10() {
	 	return ext10;
	}
	/**
	 * @设置 扩展字段
	 * @param ext10
	 */
	public void setExt10(String ext10) {
	 	this.ext10 = ext10;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}