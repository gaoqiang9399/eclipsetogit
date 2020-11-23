package app.component.tools.charge.entity;

public class BasicCifAccount {
	
	//安装码
	private String code;
	// 唯一编号
	private String serialno;
	// 公司名称
	private String companyname;
	// 账户名称
	private String cifAccount;
	// 账户密码
	private String cifPassword;
	// 营业执照号
	private String licenseNo;
	// 客户联系人姓名
	private String contactName;
	// 联系人电话
	private String contactTel;
	// 电子邮箱
	private String eMail;
	// 公司地址
	private String companyAds;
	// 账户余额
	private String balance;
	// 累计充值金额
	private String totalAmt;
	// 累计赠送金额
	private String giveAmt;
	// 累计消费金额
	private String conAmt;
	// 累计扣费金额
	private String buckAmt;
	//（0：正常，1：禁用，2：删除，3：审核中，4：审核失败）'
	private String cusState;
	// 备注
	private String remarks;
	// 创建人编号
	private String tlrno;
	// 创建人名称
	private String tlrname;
	// 创建日期
	private String createDate;
	// 时间戳
	private String occTime;
	// 备用01
	private String spare01;
	// 备用02
	private String spare02;
	// 备用03
	private String spare03;
	// 备用04
	private String spare04;
	// 备用05
	private String spare05;
	//发票抬头
	private String invoiceUp;
	//开户方式（1：手动开通，2：审核开通）
	private String openState;
	//图片保存地址
	private String imgAds;
	//开户备注说明
	private String remarkState;
	//申请时间
	private String appTime;
	//审核时间
	private String auditDate;
	//审核说明
	private String auditState;
	//审核人
	private String auditer;
	//base64文件字符
	private String base64File;
	//文件类型
	private String fileType;
	//表单设计器要求
	private String fileName;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getBase64File() {
		return base64File;
	}

	public void setBase64File(String base64File) {
		this.base64File = base64File;
	}

	public String getRemarkState() {
		return remarkState;
	}

	public void setRemarkState(String remarkState) {
		this.remarkState = remarkState;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}
	
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}

	public String getAuditer() {
		return auditer;
	}

	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}

	public String getOpenState() {
		return openState;
	}

	public void setOpenState(String openState) {
		this.openState = openState;
	}

	public String getImgAds() {
		return imgAds;
	}

	public void setImgAds(String imgAds) {
		this.imgAds = imgAds;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getInvoiceUp() {
		return invoiceUp;
	}

	public void setInvoiceUp(String invoiceUp) {
		this.invoiceUp = invoiceUp;
	}

	/**
	 * 唯一编号
	 */
	public String getSerialno() {
		return serialno;
	}

	/**
	 * 唯一编号
	 */
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	/**
	 * 公司名称
	 */
	public String getCompanyname() {
		return companyname;
	}

	/**
	 * 公司名称
	 */
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	/**
	 * 账户名称
	 */
	public String getCifAccount() {
		return cifAccount;
	}

	/**
	 * 账户名称
	 */
	public void setCifAccount(String cifAccount) {
		this.cifAccount = cifAccount;
	}

	/**
	 * 账户密码
	 */
	public String getCifPassword() {
		return cifPassword;
	}

	/**
	 * 账户密码
	 */
	public void setCifPassword(String cifPassword) {
		this.cifPassword = cifPassword;
	}

	/**
	 * 营业执照号
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 营业执照号
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 客户联系人姓名
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * 客户联系人姓名
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * 联系人电话
	 */
	public String getContactTel() {
		return contactTel;
	}

	/**
	 * 联系人电话
	 */
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	/**
	 * 电子邮箱
	 */
	public String getEMail() {
		return eMail;
	}

	/**
	 * 电子邮箱
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * 公司地址
	 */
	public String getCompanyAds() {
		return companyAds;
	}

	/**
	 * 公司地址
	 */
	public void setCompanyAds(String companyAds) {
		this.companyAds = companyAds;
	}

	/**
	 * 账户余额
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * 账户余额
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * 累计充值金额
	 */
	public String getTotalAmt() {
		return totalAmt;
	}

	/**
	 * 累计充值金额
	 */
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	/**
	 * 累计赠送金额
	 */
	public String getGiveAmt() {
		return giveAmt;
	}

	/**
	 * 累计赠送金额
	 */
	public void setGiveAmt(String giveAmt) {
		this.giveAmt = giveAmt;
	}

	/**
	 * 累计消费金额
	 */
	public String getConAmt() {
		return conAmt;
	}

	/**
	 * 累计消费金额
	 */
	public void setConAmt(String conAmt) {
		this.conAmt = conAmt;
	}

	/**
	 * 累计扣费金额
	 */
	public String getBuckAmt() {
		return buckAmt;
	}

	/**
	 * 累计扣费金额
	 */
	public void setBuckAmt(String buckAmt) {
		this.buckAmt = buckAmt;
	}

	/**
	 * 账户状态（0：正常，1：禁用，2：删除）
	 */
	public String getCusState() {
		return cusState;
	}

	/**
	 * 账户状态（0：正常，1：禁用，2：删除）
	 */
	public void setCusState(String cusState) {
		this.cusState = cusState;
	}

	/**
	 * 备注
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * 创建人编号
	 */
	public String getTlrno() {
		return tlrno;
	}

	/**
	 * 创建人编号
	 */
	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}

	/**
	 * 创建人名称
	 */
	public String getTlrname() {
		return tlrname;
	}

	/**
	 * 创建人名称
	 */
	public void setTlrname(String tlrname) {
		this.tlrname = tlrname;
	}

	/**
	 * 创建日期
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * 创建日期
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 时间戳
	 */
	public String getOccTime() {
		return occTime;
	}

	/**
	 * 时间戳
	 */
	public void setOccTime(String occTime) {
		this.occTime = occTime;
	}

	/**
	 * 备用01
	 */
	public String getSpare01() {
		return spare01;
	}

	/**
	 * 备用01
	 */
	public void setSpare01(String spare01) {
		this.spare01 = spare01;
	}

	/**
	 * 备用02
	 */
	public String getSpare02() {
		return spare02;
	}

	/**
	 * 备用02
	 */
	public void setSpare02(String spare02) {
		this.spare02 = spare02;
	}

	/**
	 * 备用03
	 */
	public String getSpare03() {
		return spare03;
	}

	/**
	 * 备用03
	 */
	public void setSpare03(String spare03) {
		this.spare03 = spare03;
	}

	/**
	 * 备用04
	 */
	public String getSpare04() {
		return spare04;
	}

	/**
	 * 备用04
	 */
	public void setSpare04(String spare04) {
		this.spare04 = spare04;
	}

	/**
	 * 备用05
	 */
	public String getSpare05() {
		return spare05;
	}

	/**
	 * 备用05
	 */
	public void setSpare05(String spare05) {
		this.spare05 = spare05;
	}
}
