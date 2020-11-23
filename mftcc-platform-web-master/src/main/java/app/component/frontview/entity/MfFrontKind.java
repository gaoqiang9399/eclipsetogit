package app.component.frontview.entity;
import app.base.BaseDomain;
/**
* Title: MfFrontKind.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Fri Jun 23 17:08:22 CST 2017
* @version：1.0
**/
public class MfFrontKind extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6995615561567362519L;
	private String kindNo;//产品编号
	private String mobileUse;//移动端是否使用该产品，0不使用，1使用
	private String pcUse;//pc交易端是否使用该产品，0不使用，1使用
	private String kindDesc;//交易端产品描述信息
	private String kindIconPath;//产品图标路径 目前在upload.properties的uploadFilePath下vw目录中
	private String kindFormId;//pc产品表单id
	private Integer putoutCountDay;//当天放款次数控制
	private Double putoutAmtDay;//当天放款金额控制
	private Integer putoutCountMonth;//当月放款次数控制
	private Double putoutAmtMonth;//当月放款金额控制
	/****冗余字段，供前端展示****/
	private String kindName;//产品名称
	private String fileType;//kindIconFileType
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
	 * @return 移动端是否使用该产品，0不使用，1使用
	 */
	public String getMobileUse() {
	 	return mobileUse;
	}
	/**
	 * @设置 移动端是否使用该产品，0不使用，1使用
	 * @param mobileUse
	 */
	public void setMobileUse(String mobileUse) {
	 	this.mobileUse = mobileUse;
	}
	/**
	 * @return pc交易端是否使用该产品，0不使用，1使用
	 */
	public String getPcUse() {
	 	return pcUse;
	}
	/**
	 * @设置 pc交易端是否使用该产品，0不使用，1使用
	 * @param pcUse
	 */
	public void setPcUse(String pcUse) {
	 	this.pcUse = pcUse;
	}
	/**
	 * @return 交易端产品描述信息
	 */
	public String getKindDesc() {
	 	return kindDesc;
	}
	/**
	 * @设置 交易端产品描述信息
	 * @param kindDesc
	 */
	public void setKindDesc(String kindDesc) {
	 	this.kindDesc = kindDesc;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getKindIconPath() {
		return kindIconPath;
	}
	public void setKindIconPath(String kindIconPath) {
		this.kindIconPath = kindIconPath;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getKindFormId() {
		return kindFormId;
	}
	public void setKindFormId(String kindFormId) {
		this.kindFormId = kindFormId;
	}
	public Integer getPutoutCountDay() {
		return putoutCountDay;
	}
	public void setPutoutCountDay(Integer putoutCountDay) {
		this.putoutCountDay = putoutCountDay;
	}
	public Double getPutoutAmtDay() {
		return putoutAmtDay;
	}
	public void setPutoutAmtDay(Double putoutAmtDay) {
		this.putoutAmtDay = putoutAmtDay;
	}
	public Integer getPutoutCountMonth() {
		return putoutCountMonth;
	}
	public void setPutoutCountMonth(Integer putoutCountMonth) {
		this.putoutCountMonth = putoutCountMonth;
	}
	public Double getPutoutAmtMonth() {
		return putoutAmtMonth;
	}
	public void setPutoutAmtMonth(Double putoutAmtMonth) {
		this.putoutAmtMonth = putoutAmtMonth;
	}
	
	
}