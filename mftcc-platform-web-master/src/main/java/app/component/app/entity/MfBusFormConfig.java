/**
 * Copyright (C)  版权所有
 * 文件名： MfBusFormConfig.java
 * 包名： app.component.app.entity
 * 说明：
 * @author YuShuai
 * @date 2017-6-5 下午5:03:46
 * @version V1.0
 */
package app.component.app.entity;

/**
 * 类名： MfBusFormConfig 描述：
 * 
 * @author YuShuai
 * @date 2017-6-5 下午5:03:46
 * 
 * 
 */
public class MfBusFormConfig {
	// 流水号
	private String id;
	// 表单中文名（表中文名）
	private String tableDes;
	// 表单英文名（表名）
	private String tableName;
	// 配置表单类型（产品种类 kindno）
	private String kindNo;
	
	private String cusType;
	
	private String busType;
	// apply：申请阶段 putout：放款阶段
	private String formStage;
	//
	private String entity;
	// 是否启用0-备用1-启用
	private String useFlag;
	// 是否基础表单0-否1-是
	private String isBase;
	// 是否必填
	private String isMust;
	// 展示方式1表单2列表
	private String showType;
	//
	private String sort;
	//
	private String addModel;
	//
	private String addModelDef;
	//
	private String showModel;
	//
	private String showModelDef;
	//
	private String ext1;
	//
	private String ext2;
	//
	private String ext3;

	/**
	 * 流水号
	 */
	public String getId() {
		return id;
	}

	/**
	 * 流水号
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 表单中文名（表中文名）
	 */
	public String getTableDes() {
		return tableDes;
	}

	/**
	 * 表单中文名（表中文名）
	 */
	public void setTableDes(String tableDes) {
		this.tableDes = tableDes;
	}

	/**
	 * 表单英文名（表名）
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 表单英文名（表名）
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	/**
	 * apply：申请阶段 putout：放款阶段
	 */
	public String getFormStage() {
		return formStage;
	}

	/**
	 * apply：申请阶段 putout：放款阶段
	 */
	public void setFormStage(String formStage) {
		this.formStage = formStage;
	}

	/**
	  *
	  */
	public String getEntity() {
		return entity;
	}

	/**
	*	
	  */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * 是否启用0-备用1-启用
	 */
	public String getUseFlag() {
		return useFlag;
	}

	/**
	 * 是否启用0-备用1-启用
	 */
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * 是否基础表单0-否1-是
	 */
	public String getIsBase() {
		return isBase;
	}

	/**
	 * 是否基础表单0-否1-是
	 */
	public void setIsBase(String isBase) {
		this.isBase = isBase;
	}

	/**
	 * 是否必填
	 */
	public String getIsMust() {
		return isMust;
	}

	/**
	 * 是否必填
	 */
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}


	/**
	 * 展示方式1表单2列表
	 */
	public String getShowType() {
		return showType;
	}

	/**
	 * 展示方式1表单2列表
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}

	/**
	  *
	  */
	public String getSort() {
		return sort;
	}

	/**
	*	
	  */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	  *
	  */
	public String getAddModel() {
		return addModel;
	}

	/**
	*	
	  */
	public void setAddModel(String addModel) {
		this.addModel = addModel;
	}

	/**
	  *
	  */
	public String getAddModelDef() {
		return addModelDef;
	}

	/**
	*	
	  */
	public void setAddModelDef(String addModelDef) {
		this.addModelDef = addModelDef;
	}

	/**
	  *
	  */
	public String getShowModel() {
		return showModel;
	}

	/**
	*	
	  */
	public void setShowModel(String showModel) {
		this.showModel = showModel;
	}

	/**
	  *
	  */
	public String getShowModelDef() {
		return showModelDef;
	}

	/**
	*	
	  */
	public void setShowModelDef(String showModelDef) {
		this.showModelDef = showModelDef;
	}

	/**
	  *
	  */
	public String getExt1() {
		return ext1;
	}

	/**
	*	
	  */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	  *
	  */
	public String getExt2() {
		return ext2;
	}

	/**
	*	
	  */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	  *
	  */
	public String getExt3() {
		return ext3;
	}

	/**
	*	
	  */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getKindNo() {
		return kindNo;
	}

	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}

	public String getCusType() {
		return cusType;
	}

	public void setCusType(String cusType) {
		this.cusType = cusType;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}



}
