/**
 * 
 */
package app.component.pact.entity;

/**
 * @author QiuZhao
 * 
 */
public class MfBusFollowPact {
	// id
	private String id;
	// 从合同编号
	private String followPactNo;
	// 从合同展示号
	private String followPactNoShow;
	// 担保方式
	private String vouType;
	// 担保方式名称
	private String vouTypeName;
	// 担保金额
	private Double collateralAmt;
	// 担保显示金额
	private String collateralAmtShow;
	// 担保比例
	private Double collateralRate;
	// 保证人/押品名称
	private String pledgeOrAssureName;
	
	/**
	 * @return the followPactNo
	 */
	public String getFollowPactNo() {
		return followPactNo;
	}
	/**
	 * @param followPactNo the followPactNo to set
	 */
	public void setFollowPactNo(String followPactNo) {
		this.followPactNo = followPactNo;
	}
	/**
	 * @return the vouType
	 */
	public String getVouType() {
		return vouType;
	}
	/**
	 * @param vouType the vouType to set
	 */
	public void setVouType(String vouType) {
		this.vouType = vouType;
	}
	/**
	 * @return the vouTypeName
	 */
	public String getVouTypeName() {
		return vouTypeName;
	}
	/**
	 * @param vouTypeName the vouTypeName to set
	 */
	public void setVouTypeName(String vouTypeName) {
		this.vouTypeName = vouTypeName;
	}
	/**
	 * @return the collateralAmt
	 */
	public Double getCollateralAmt() {
		return collateralAmt;
	}
	/**
	 * @param collateralAmt the collateralAmt to set
	 */
	public void setCollateralAmt(Double collateralAmt) {
		this.collateralAmt = collateralAmt;
	}
	/**
	 * @return the collateralRate
	 */
	public Double getCollateralRate() {
		return collateralRate;
	}
	/**
	 * @param collateralRate the collateralRate to set
	 */
	public void setCollateralRate(Double collateralRate) {
		this.collateralRate = collateralRate;
	}
	/**
	 * @return the pledgeOrAssureName
	 */
	public String getPledgeOrAssureName() {
		return pledgeOrAssureName;
	}
	/**
	 * @param pledgeOrAssureName the pledgeOrAssureName to set
	 */
	public void setPledgeOrAssureName(String pledgeOrAssureName) {
		this.pledgeOrAssureName = pledgeOrAssureName;
	}
	public String getCollateralAmtShow() {
		return collateralAmtShow;
	}
	public void setCollateralAmtShow(String collateralAmtShow) {
		this.collateralAmtShow = collateralAmtShow;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFollowPactNoShow() {
		return followPactNoShow;
	}

	public void setFollowPactNoShow(String followPactNoShow) {
		this.followPactNoShow = followPactNoShow;
	}
}
