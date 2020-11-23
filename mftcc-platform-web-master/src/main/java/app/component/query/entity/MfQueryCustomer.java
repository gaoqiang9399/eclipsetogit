package app.component.query.entity;

import app.base.BaseDomain;

public class MfQueryCustomer extends BaseDomain {
		private String cusNo;//客户号
		private String cusName;//客户名称
		private String cusType;//客户类型 1-个人/2-企业/3-合作机构
		private String idType;//证件类型（个人：身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 企业：营业执照,组织机构代码,机构信用代码）
		private String isThreeToOne;//是否三证合一(0-否、1-是)
		private String idNum;//证件号码
		private String infIntegrity;//资料完整度（00000000.....每一位对应客户每个模块信息，0-未填1-已填）
		private String cusLevelId;//客户信用等级ID（字典项）
		private String cusLevelName;//客户信用等级名称
		private String cusTypeId;//客户分类ID（字典项）
		private String cusTypeName;//客户分类名称
		private String classifyType;//客户分类：1-黑名单/2-优质客户 3-普通客户
		private String cusMark;//评分
		private String cusBussType;//客户业务类型
		private String cusTel;//客户手机号码
		private String contactsTel;//联系人手机号
		private String contactsName;//联系人姓名
		private String commAddress;//通讯地址
		private String otherContactWay;//其他联系方式 QQ、微信、邮箱等
		private String opNo;//登记人编号
		private String opName;//登记人姓名
		private String brNo;//登记部门编号
		private String brName;//登记部门名称
		private String regTime;//登记时间
		private String lstModTime;//最后修改时间
		private String ext1;//预留字段
		private String ext2;//
		private String ext3;//
		private String ext4;//
		private String ext5;//
		private String ext6;//
		private String ext7;//
		private String headImg;//头像文件名称
		private String ifUploadHead;//是否上传头像
		
		/**
		 * @return 客户号
		 */
		public String getCusNo() {
		 	return cusNo;
		}
		/**
		 * @设置 客户号
		 * @param cusNo
		 */
		public void setCusNo(String cusNo) {
		 	this.cusNo = cusNo;
		}
		/**
		 * @return 客户名称
		 */
		public String getCusName() {
		 	return cusName;
		}
		/**
		 * @设置 客户名称
		 * @param cusName
		 */
		public void setCusName(String cusName) {
		 	this.cusName = cusName;
		}
		/**
		 * @return 客户类型 1-个人/2-企业/3-合作机构
		 */
		public String getCusType() {
		 	return cusType;
		}
		/**
		 * @设置 客户类型 1-个人/2-企业/3-合作机构
		 * @param cusType
		 */
		public void setCusType(String cusType) {
		 	this.cusType = cusType;
		}
		/**
		 * @return 证件类型（个人：身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 企业：营业执照,组织机构代码,机构信用代码）
		 */
		public String getIdType() {
		 	return idType;
		}
		/**
		 * @设置 证件类型（个人：身份证,户口薄,护照,军官证,士兵证,港澳居民来往内地通行证,台湾同胞来往内地通行证,临时身份证,外国人居留证, 警官证,其他证件 企业：营业执照,组织机构代码,机构信用代码）
		 * @param idType
		 */
		public void setIdType(String idType) {
		 	this.idType = idType;
		}
		/**
		 * @return 是否三证合一(0-否、1-是)
		 */
		public String getIsThreeToOne() {
		 	return isThreeToOne;
		}
		/**
		 * @设置 是否三证合一(0-否、1-是)
		 * @param isThreeToOne
		 */
		public void setIsThreeToOne(String isThreeToOne) {
		 	this.isThreeToOne = isThreeToOne;
		}
		/**
		 * @return 证件号码
		 */
		public String getIdNum() {
		 	return idNum;
		}
		/**
		 * @设置 证件号码
		 * @param idNum
		 */
		public void setIdNum(String idNum) {
		 	this.idNum = idNum;
		}
		/**
		 * @return 资料完整度（00000000.....每一位对应客户每个模块信息，0-未填1-已填）
		 */
		public String getInfIntegrity() {
		 	return infIntegrity;
		}
		/**
		 * @设置 资料完整度（00000000.....每一位对应客户每个模块信息，0-未填1-已填）
		 * @param infIntegrity
		 */
		public void setInfIntegrity(String infIntegrity) {
		 	this.infIntegrity = infIntegrity;
		}
		/**
		 * @return 客户信用等级ID（字典项）
		 */
		public String getCusLevelId() {
		 	return cusLevelId;
		}
		/**
		 * @设置 客户信用等级ID（字典项）
		 * @param cusLevelId
		 */
		public void setCusLevelId(String cusLevelId) {
		 	this.cusLevelId = cusLevelId;
		}
		/**
		 * @return 客户信用等级名称
		 */
		public String getCusLevelName() {
		 	return cusLevelName;
		}
		/**
		 * @设置 客户信用等级名称
		 * @param cusLevelName
		 */
		public void setCusLevelName(String cusLevelName) {
		 	this.cusLevelName = cusLevelName;
		}
		/**
		 * @return 客户分类ID（字典项）
		 */
		public String getCusTypeId() {
		 	return cusTypeId;
		}
		/**
		 * @设置 客户分类ID（字典项）
		 * @param cusTypeId
		 */
		public void setCusTypeId(String cusTypeId) {
		 	this.cusTypeId = cusTypeId;
		}
		/**
		 * @return 客户分类名称
		 */
		public String getCusTypeName() {
		 	return cusTypeName;
		}
		/**
		 * @设置 客户分类名称
		 * @param cusTypeName
		 */
		public void setCusTypeName(String cusTypeName) {
		 	this.cusTypeName = cusTypeName;
		}
		/**
		 * @return 评分
		 */
		public String getCusMark() {
		 	return cusMark;
		}
		/**
		 * @设置 评分
		 * @param cusMark
		 */
		public void setCusMark(String cusMark) {
		 	this.cusMark = cusMark;
		}
		/**
		 * @return 客户业务类型
		 */
		public String getCusBussType() {
		 	return cusBussType;
		}
		/**
		 * @设置 客户业务类型
		 * @param cusBussType
		 */
		public void setCusBussType(String cusBussType) {
		 	this.cusBussType = cusBussType;
		}
		/**
		 * @return 客户手机号码
		 */
		public String getCusTel() {
		 	return cusTel;
		}
		/**
		 * @设置 客户手机号码
		 * @param cusTel
		 */
		public void setCusTel(String cusTel) {
		 	this.cusTel = cusTel;
		}
		/**
		 * @return 其他联系方式 QQ、微信、邮箱等
		 */
		public String getOtherContactWay() {
		 	return otherContactWay;
		}
		/**
		 * @设置 其他联系方式 QQ、微信、邮箱等
		 * @param otherContactWay
		 */
		public void setOtherContactWay(String otherContactWay) {
		 	this.otherContactWay = otherContactWay;
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
		 * @return 预留字段
		 */
		public String getExt1() {
		 	return ext1;
		}
		/**
		 * @设置 预留字段
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
		public String getContactsTel() {
			return contactsTel;
		}
		public void setContactsTel(String contactsTel) {
			this.contactsTel = contactsTel;
		}
		public String getContactsName() {
			return contactsName;
		}
		public void setContactsName(String contactsName) {
			this.contactsName = contactsName;
		}
		public String getCommAddress() {
			return commAddress;
		}
		public void setCommAddress(String commAddress) {
			this.commAddress = commAddress;
		}
		public String getClassifyType() {
			return classifyType;
		}
		public void setClassifyType(String classifyType) {
			this.classifyType = classifyType;
		}
		public String getHeadImg() {
			return headImg;
		}
		public void setHeadImg(String headImg) {
			this.headImg = headImg;
		}
		public String getIfUploadHead() {
			return ifUploadHead;
		}
		public void setIfUploadHead(String ifUploadHead) {
			this.ifUploadHead = ifUploadHead;
		}
}
