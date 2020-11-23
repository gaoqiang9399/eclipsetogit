package app.component.archives.entity;
import app.base.BaseDomain;
import cn.mftcc.util.StringUtil;

/**
* Title: ArchiveInfoDetail.java
* Description:归档明细信息
* @author：yudongwei@mftcc.cn
* @Fri Apr 07 15:04:12 CST 2017
* @version：1.0
**/
public class ArchiveInfoDetail extends BaseDomain {
	private String archiveDetailNo;//档案明细编号
	private String archiveMainNo;//档案编号
	private String docNo;//文件编号
	private String docName;//文件名称
	private Double docSize;//文件大小
	private String archiveDocAttribute;//归档文件属性（01-文档 02-图片 03-音频 04-视频 05-压缩 09-其它）
	private String archivePactStatus;//归档阶段（01-授信阶段 02-单笔阶段）
	private String docAddr;//文件地址
	private String docEncryptAddr;//文件加密地址
	private String archiveAddr;//归档地址
	private String docType;//文档类型编号
	private String docTypeName;//文档类型名称
	private String docSplitNo;//文档细分类型编号
	private String docSplitName;//文档细分类型名称
	private String scNo;//场景编号
	private String docBizNo;//业务场景编号
	private String archiveDocStatus;//归档文件状态（01-归档待确认 02-在库 03-已打回 04...
	private Integer archiveNum;//归档次数
	private Integer borrowInNum;//借阅次数
	private Integer borrowOutNum;//借出次数
	private Integer returnNum;//归还次数
	private String isLend;//是否借出（0-否 1-是）
	private String isPaper;//是否存在纸制文档（0-否 1-是）
	private String paperAddr;//纸制文件存放位置
	private String paperNo;//纸制文件编号
	private String paperKeeperNo;//纸制文件保管人编号
	private String paperKeeperName;//纸制文件保管人名称
	private String archiveDocSource;//归档文件来源（01-业务归档 02-归档补充）
	private String updateOpNo;//更新人编号
	private String updateOpName;//更新人名称
	private String updateBrNo;//更新人部门编号
	private String updateBrName;//更新人部门名称
	private String updateDate;//更新日期
	private String formId;//表单id
	private String regDate;//上传时间
	
	private String isLendString;//是否借出文字描述
	
	private String isTemplate;//是否是模板文档（0-否 1-是）

	//文档/模板/凭证 单独字段
	private String templateType;//模板类型
	private String templateNum;//合同份数
	private String voucherType;//凭证类型（他项凭证/原始凭证）
	private String voucherOtherNo;//他项 凭证号
	private String voucherOther;//他项凭证名称
	private String voucherOriginalNo;//原始凭证号
	private String voucherOriginal;//原始凭证名称
	private String voucherHolderNo;
	private String voucherHolder;//权属人

	private String cusNo;//客户号
	private String cusName;//客户名称
	private String pactNo;//合同号
	private String pactId;//合同号
	private String appId;//申请号
	private String appName;//项目名称
	private String creditAppId;//授信申请id
	private String creditAppNo;
	private String kindNo;//产品号
	private String kindName;//产品名称
	private String dataSource;//数据来源  1-docManage表  2-mf_template_biz_config表
	private String corpName;//非系统生成合同名称
	private String corpNo;//非系统生成合同号

	public String getArchivePactStatus() {
		return archivePactStatus;
	}

	public void setArchivePactStatus(String archivePactStatus) {
		this.archivePactStatus = archivePactStatus;
	}

	public String getCreditAppNo() {
		return creditAppNo;
	}

	public void setCreditAppNo(String creditAppNo) {
		this.creditAppNo = creditAppNo;
	}

	public String getCreditAppId() {
		return creditAppId;
	}

	public void setCreditAppId(String creditAppId) {
		this.creditAppId = creditAppId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpNo() {
		return corpNo;
	}

	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getVoucherHolderNo() {
		return voucherHolderNo;
	}

	public void setVoucherHolderNo(String voucherHolderNo) {
		this.voucherHolderNo = voucherHolderNo;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPactNo() {
		return pactNo;
	}

	public void setPactNo(String pactNo) {
		this.pactNo = pactNo;
	}

	public Integer getArchiveNum() {
		return archiveNum;
	}

	public void setArchiveNum(Integer archiveNum) {
		this.archiveNum = archiveNum;
	}

	public Integer getBorrowInNum() {
		return borrowInNum;
	}

	public void setBorrowInNum(Integer borrowInNum) {
		this.borrowInNum = borrowInNum;
	}

	public Integer getBorrowOutNum() {
		return borrowOutNum;
	}

	public void setBorrowOutNum(Integer borrowOutNum) {
		this.borrowOutNum = borrowOutNum;
	}

	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public String getVoucherOtherNo() {
		return voucherOtherNo;
	}

	public void setVoucherOtherNo(String voucherOtherNo) {
		this.voucherOtherNo = voucherOtherNo;
	}

	public String getVoucherOther() {
		return voucherOther;
	}

	public void setVoucherOther(String voucherOther) {
		this.voucherOther = voucherOther;
	}

	public String getVoucherOriginalNo() {
		return voucherOriginalNo;
	}

	public void setVoucherOriginalNo(String voucherOriginalNo) {
		this.voucherOriginalNo = voucherOriginalNo;
	}

	public String getVoucherOriginal() {
		return voucherOriginal;
	}

	public void setVoucherOriginal(String voucherOriginal) {
		this.voucherOriginal = voucherOriginal;
	}

	public String getVoucherHolder() {
		return voucherHolder;
	}

	public void setVoucherHolder(String voucherHolder) {
		this.voucherHolder = voucherHolder;
	}

	/**
	 * @return 档案明细编号
	 */
	public String getArchiveDetailNo() {
	 	return archiveDetailNo;
	}
	/**
	 * @设置 档案明细编号
	 * @param archiveDetailNo
	 */
	public void setArchiveDetailNo(String archiveDetailNo) {
	 	this.archiveDetailNo = archiveDetailNo;
	}
	/**
	 * @return 档案编号
	 */
	public String getArchiveMainNo() {
	 	return archiveMainNo;
	}
	/**
	 * @设置 档案编号
	 * @param archiveMainNo
	 */
	public void setArchiveMainNo(String archiveMainNo) {
	 	this.archiveMainNo = archiveMainNo;
	}
	/**
	 * @return 文件编号
	 */
	public String getDocNo() {
	 	return docNo;
	}
	/**
	 * @设置 文件编号
	 * @param docNo
	 */
	public void setDocNo(String docNo) {
	 	this.docNo = docNo;
	}
	/**
	 * @return 文件名称
	 */
	public String getDocName() {
	 	return docName;
	}
	/**
	 * @设置 文件名称
	 * @param docName
	 */
	public void setDocName(String docName) {
	 	this.docName = docName;
	}
	/**
	 * @return 文件大小
	 */
	public Double getDocSize() {
	 	return docSize;
	}
	/**
	 * @设置 文件大小
	 * @param docSize
	 */
	public void setDocSize(Double docSize) {
	 	this.docSize = docSize;
	}
	/**
	 * @return 归档文件属性（01-文档 02-图片 03-音频 04-视频  05-压缩 09-其它）
	 */
	public String getArchiveDocAttribute() {
	 	return archiveDocAttribute;
	}
	/**
	 * @设置 归档文件属性（01-文档 02-图片 03-音频 04-视频  05-压缩 09-其它）
	 * @param archiveDocAttribute
	 */
	public void setArchiveDocAttribute(String archiveDocAttribute) {
	 	this.archiveDocAttribute = archiveDocAttribute;
	}
	/**
	 * @return 文件地址
	 */
	public String getDocAddr() {
	 	return docAddr;
	}
	/**
	 * @设置 文件地址
	 * @param docAddr
	 */
	public void setDocAddr(String docAddr) {
	 	this.docAddr = docAddr;
	}
	/**
	 * @return 文件加密地址
	 */
	public String getDocEncryptAddr() {
	 	return docEncryptAddr;
	}
	/**
	 * @设置 文件加密地址
	 * @param docEncryptAddr
	 */
	public void setDocEncryptAddr(String docEncryptAddr) {
	 	this.docEncryptAddr = docEncryptAddr;
	}
	/**
	 * @return 归档地址
	 */
	public String getArchiveAddr() {
	 	return archiveAddr;
	}
	/**
	 * @设置 归档地址
	 * @param archiveAddr
	 */
	public void setArchiveAddr(String archiveAddr) {
	 	this.archiveAddr = archiveAddr;
	}
	/**
	 * @return 文档类型编号
	 */
	public String getDocType() {
	 	return docType;
	}
	/**
	 * @设置 文档类型编号
	 * @param docType
	 */
	public void setDocType(String docType) {
	 	this.docType = docType;
	}
	/**
	 * @return 文档类型名称
	 */
	public String getDocTypeName() {
	 	return docTypeName;
	}
	/**
	 * @设置 文档类型名称
	 * @param docTypeName
	 */
	public void setDocTypeName(String docTypeName) {
	 	this.docTypeName = docTypeName;
	}
	/**
	 * @return 文档细分类型编号
	 */
	public String getDocSplitNo() {
	 	return docSplitNo;
	}
	/**
	 * @设置 文档细分类型编号
	 * @param docSplitNo
	 */
	public void setDocSplitNo(String docSplitNo) {
	 	this.docSplitNo = docSplitNo;
	}
	/**
	 * @return 文档细分类型名称
	 */
	public String getDocSplitName() {
	 	return docSplitName;
	}
	/**
	 * @设置 文档细分类型名称
	 * @param docSplitName
	 */
	public void setDocSplitName(String docSplitName) {
	 	this.docSplitName = docSplitName;
	}
	/**
	 * @return 场景编号
	 */
	public String getScNo() {
	 	return scNo;
	}
	/**
	 * @设置 场景编号
	 * @param scNo
	 */
	public void setScNo(String scNo) {
	 	this.scNo = scNo;
	}
	/**
	 * @return 业务场景编号
	 */
	public String getDocBizNo() {
	 	return docBizNo;
	}
	/**
	 * @设置 业务场景编号
	 * @param docBizNo
	 */
	public void setDocBizNo(String docBizNo) {
	 	this.docBizNo = docBizNo;
	}
	/**
	 * @return 归档文件状态（01-正常 02-删除）
	 */
	public String getArchiveDocStatus() {
	 	return archiveDocStatus;
	}
	/**
	 * @设置 归档文件状态（01-正常 02-删除）
	 */
	public void setArchiveDocStatus(String archiveDocStatus) {
	 	this.archiveDocStatus = archiveDocStatus;
	}
	/**
	 * @return 是否借出（0-否 1-是）
	 */
	public String getIsLend() {
	 	return isLend;
	}
	/**
	 * @设置 是否借出（0-否 1-是）
	 * @param isLend
	 */
	public void setIsLend(String isLend) {
	 	this.isLend = isLend;
	}
	/**
	 * @return 是否存在纸制文档（0-否 1-是）
	 */
	public String getIsPaper() {
	 	return isPaper;
	}
	/**
	 * @设置 是否存在纸制文档（0-否 1-是）
	 * @param isPaper
	 */
	public void setIsPaper(String isPaper) {
	 	this.isPaper = isPaper;
	}
	/**
	 * @return 纸制文件存放位置
	 */
	public String getPaperAddr() {
	 	return paperAddr;
	}
	/**
	 * @设置 纸制文件存放位置
	 * @param paperAddr
	 */
	public void setPaperAddr(String paperAddr) {
	 	this.paperAddr = paperAddr;
	}
	/**
	 * @return 纸制文件编号
	 */
	public String getPaperNo() {
	 	return paperNo;
	}
	/**
	 * @设置 纸制文件编号
	 * @param paperNo
	 */
	public void setPaperNo(String paperNo) {
	 	this.paperNo = paperNo;
	}
	/**
	 * @return 纸制文件保管人编号
	 */
	public String getPaperKeeperNo() {
	 	return paperKeeperNo;
	}
	/**
	 * @设置 纸制文件保管人编号
	 * @param paperKeeperNo
	 */
	public void setPaperKeeperNo(String paperKeeperNo) {
	 	this.paperKeeperNo = paperKeeperNo;
	}
	/**
	 * @return 纸制文件保管人姓名
	 */
	public String getPaperKeeperName() {
	 	return paperKeeperName;
	}
	/**
	 * @设置 纸制文件保管人姓名
	 * @param paperKeeperName
	 */
	public void setPaperKeeperName(String paperKeeperName) {
	 	this.paperKeeperName = paperKeeperName;
	}
	/**
	 * @return 归档文件来源（01-业务归档 02-归档补充）
	 */
	public String getArchiveDocSource() {
	 	return archiveDocSource;
	}
	/**
	 * @设置 归档文件来源（01-业务归档 02-归档补充）
	 */
	public void setArchiveDocSource(String archiveDocSource) {
	 	this.archiveDocSource = archiveDocSource;
	}
	/**
	 * @return 更新人编号
	 */
	public String getUpdateOpNo() {
	 	return updateOpNo;
	}
	/**
	 * @设置 更新人编号
	 * @param updateOpNo
	 */
	public void setUpdateOpNo(String updateOpNo) {
	 	this.updateOpNo = updateOpNo;
	}
	/**
	 * @return 更新人名称
	 */
	public String getUpdateOpName() {
	 	return updateOpName;
	}
	/**
	 * @设置 更新人名称
	 * @param updateOpName
	 */
	public void setUpdateOpName(String updateOpName) {
	 	this.updateOpName = updateOpName;
	}
	/**
	 * @return 更新人部门编号
	 */
	public String getUpdateBrNo() {
	 	return updateBrNo;
	}
	/**
	 * @设置 更新人部门编号
	 * @param updateBrNo
	 */
	public void setUpdateBrNo(String updateBrNo) {
	 	this.updateBrNo = updateBrNo;
	}
	/**
	 * @return 更新人部门名称
	 */
	public String getUpdateBrName() {
	 	return updateBrName;
	}
	/**
	 * @设置 更新人部门名称
	 * @param updateBrName
	 */
	public void setUpdateBrName(String updateBrName) {
	 	this.updateBrName = updateBrName;
	}
	/**
	 * @return 更新日期
	 */
	public String getUpdateDate() {
	 	return updateDate;
	}
	/**
	 * @设置 更新日期
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) {
	 	this.updateDate = updateDate;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getIsLendString() {
		return isLendString;
	}
	public void setIsLendString(String isLendString) {
		this.isLendString = isLendString;
	}
	/**
	 * @return 是否是模板文档（0-否 1-是）
	 */
	public String getIsTemplate() {
		return isTemplate;
	}
	/**
	 * @设置 是否是模板文档（0-否 1-是）
	 * @param isTemplate
	 */
	public void setIsTemplate(String isTemplate) {
		this.isTemplate = isTemplate;
	}

	public String getCusNo() {
		return cusNo;
	}

	public void setCusNo(String cusNo) {
		this.cusNo = cusNo;
	}

	public String getPactId() {
		return pactId;
	}

	public void setPactId(String pactId) {
		this.pactId = pactId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getKindNo() {
		return kindNo;
	}

	public void setKindNo(String kindNo) {
		this.kindNo = kindNo;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(StringUtil.isEmpty(archiveDetailNo))
		{
			return false;
		}

		return super.equals(obj);
	}
}