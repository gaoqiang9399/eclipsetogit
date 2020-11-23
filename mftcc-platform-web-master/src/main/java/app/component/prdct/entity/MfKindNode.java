package app.component.prdct.entity;
import app.base.BaseDomain;
/**
* Title: MfKindNode.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Sun Jul 02 16:48:46 CST 2017
* @version：1.0
**/
public class MfKindNode extends BaseDomain {
	private  String kindNodeId;	//唯一编号
	private  String kindNo;//产品编号
	private  String busModel;//业务模式
	private  String modelType;//是否业务模式基础数据 normal：正常；base：基础；
	private  String flowId;//流程唯一编号
	private  String nodeNo;//节点编号
	private  String nodeName;//节点名称
	private  String optPower;	//操作权限 1：改；2：查；3：收,跟节点走 or 跟费用项走？
	private  String docFlag;//是否挂载要件 1：是；0：否；
	private  String docJson;//要件数据 json格式
	private  String feeFlag;//是否挂载费用1：是；0：否；
	private  String feeJson;//费用数据 json格式
	private  String templateFlag;//是否挂载模板 1：是；0：否；
	private  String templateJson;	//模板数据
	private  String interceptFlag;//是否挂载拦截项
	private  String interceptJson;	//拦截项数据
	private  String formFlag;//是否是多表单 1：是；0：否；
	private  String formJson;//表单数据
	private  Integer sort;//表单数据
	private  String ext1;//扩展字段
	private  String ext2;//扩展字段
	private  String ext3;//扩展字段
	private  String ext4;//扩展字段
	private  String ext5;//扩展字段
	private  String ext6;//扩展字段
	private  String ext7;//扩展字段
	private  String ext8;//扩展字段
	private  String ext9;//扩展字段
	private  String ext10;//扩展字段
	private  String configType;//配置类型
    private String mainApprove;//该节点是主流程节点还是子流程节点  0是主流程  1是子流程


    public String getMainApprove() {
        return mainApprove;
    }

    public void setMainApprove(String mainApprove) {
        this.mainApprove = mainApprove;
    }
	  /**
	  *唯一编号
	  */
	  public String getKindNodeId(){
	    return kindNodeId;
	  }
	  /**
	*	唯一编号
	  */
	  public void setKindNodeId(String kindNodeId){
	    this.kindNodeId=kindNodeId;
	  }
	  /**
	  *产品编号
	  */
	  public String getKindNo(){
	    return kindNo;
	  }
	  /**
	*	产品编号
	  */
	  public void setKindNo(String kindNo){
	    this.kindNo=kindNo;
	  }
	  /**
	  *业务模式
	  */
	  public String getBusModel(){
	    return busModel;
	  }
	  /**
	*	业务模式
	  */
	  public void setBusModel(String busModel){
	    this.busModel=busModel;
	  }
	  /**
	  *是否业务模式基础数据 normal：正常；base：基础；
	  */
	  public String getModelType(){
	    return modelType;
	  }
	  /**
	*	是否业务模式基础数据 normal：正常；base：基础；
	  */
	  public void setModelType(String modelType){
	    this.modelType=modelType;
	  }
	  /**
	  *流程唯一编号
	  */
	  public String getFlowId(){
	    return flowId;
	  }
	  /**
	*	流程唯一编号
	  */
	  public void setFlowId(String flowId){
	    this.flowId=flowId;
	  }
	  /**
	  *节点编号
	  */
	  public String getNodeNo(){
	    return nodeNo;
	  }
	  /**
	*	节点编号
	  */
	  public void setNodeNo(String nodeNo){
	    this.nodeNo=nodeNo;
	  }
	  /**
	  *节点名称
	  */
	  public String getNodeName(){
	    return nodeName;
	  }
	  /**
	*	节点名称
	  */
	  public void setNodeName(String nodeName){
	    this.nodeName=nodeName;
	  }
	  /**
	  *是否挂载要件 1：是；0：否；
	  */
	  public String getDocFlag(){
	    return docFlag;
	  }
	  /**
	*	是否挂载要件 1：是；0：否；
	  */
	  public void setDocFlag(String docFlag){
	    this.docFlag=docFlag;
	  }
	  /**
	  *要件数据 json格式
	  */
	  public String getDocJson(){
	    return docJson;
	  }
	  /**
	*	要件数据 json格式
	  */
	  public void setDocJson(String docJson){
	    this.docJson=docJson;
	  }
	  /**
	  *是否挂载费用1：是；0：否；
	  */
	  public String getFeeFlag(){
	    return feeFlag;
	  }
	  /**
	*	是否挂载费用1：是；0：否；
	  */
	  public void setFeeFlag(String feeFlag){
	    this.feeFlag=feeFlag;
	  }
	  /**
	  *操作权限 1：改；2：查；3：收,跟节点走 or 跟费用项走？
	  */
	  public String getOptPower(){
	    return optPower;
	  }
	  /**
	*	操作权限 1：改；2：查；3：收,跟节点走 or 跟费用项走？
	  */
	  public void setOptPower(String optPower){
	    this.optPower=optPower;
	  }
	  /**
	  *费用数据 json格式
	  */
	  public String getFeeJson(){
	    return feeJson;
	  }
	  /**
	*	费用数据 json格式
	  */
	  public void setFeeJson(String feeJson){
	    this.feeJson=feeJson;
	  }
	  /**
	  *是否挂载模板 1：是；0：否；
	  */
	  public String getTemplateFlag(){
	    return templateFlag;
	  }
	  /**
	*	是否挂载模板 1：是；0：否；
	  */
	  public void setTemplateFlag(String templateFlag){
	    this.templateFlag=templateFlag;
	  }
	  /**
	  *模板数据
	  */
	  public String getTemplateJson(){
	    return templateJson;
	  }
	  /**
	*	模板数据
	  */
	  public void setTemplateJson(String templateJson){
	    this.templateJson=templateJson;
	  }
	  /**
	  *是否挂载拦截项
	  */
	  public String getInterceptFlag(){
	    return interceptFlag;
	  }
	  /**
	*	是否挂载拦截项
	  */
	  public void setInterceptFlag(String interceptFlag){
	    this.interceptFlag=interceptFlag;
	  }
	  /**
	  *拦截项数据
	  */
	  public String getInterceptJson(){
	    return interceptJson;
	  }
	  /**
	*	拦截项数据
	  */
	  public void setInterceptJson(String interceptJson){
	    this.interceptJson=interceptJson;
	  }
	  /**
	  *是否是多表单 1：是；0：否；
	  */
	  public String getFormFlag(){
	    return formFlag;
	  }
	  /**
	*	是否是多表单 1：是；0：否；
	  */
	  public void setFormFlag(String formFlag){
	    this.formFlag=formFlag;
	  }
	  /**
	  *表单数据
	  */
	  public String getFormJson(){
	    return formJson;
	  }
	  /**
	*	表单数据
	  */
	  public void setFormJson(String formJson){
	    this.formJson=formJson;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt1(){
	    return ext1;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt1(String ext1){
	    this.ext1=ext1;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt2(){
	    return ext2;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt2(String ext2){
	    this.ext2=ext2;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt3(){
	    return ext3;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt3(String ext3){
	    this.ext3=ext3;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt4(){
	    return ext4;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt4(String ext4){
	    this.ext4=ext4;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt5(){
	    return ext5;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt5(String ext5){
	    this.ext5=ext5;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt6(){
	    return ext6;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt6(String ext6){
	    this.ext6=ext6;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt7(){
	    return ext7;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt7(String ext7){
	    this.ext7=ext7;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt8(){
	    return ext8;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt8(String ext8){
	    this.ext8=ext8;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt9(){
	    return ext9;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt9(String ext9){
	    this.ext9=ext9;
	  }
	  /**
	  *扩展字段
	  */
	  public String getExt10(){
	    return ext10;
	  }
	  /**
	*	扩展字段
	  */
	  public void setExt10(String ext10){
	    this.ext10=ext10;
	  }
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
}