package app.component.collateral.entity;

public class CollateralWarningJudge {
	//价值重估周期
	private int valueRange;
	//押品检查周期
	private int checkRange;
	//押品类型
	private String collateralType;
	//评估差值
	private Double collateralValueMin;
	//上次评估价值
	private Double collateralLastValue;
	//押品检查预警结果
	private String reCheckResult;
	//押品价值波动预警结果
	private String valueChgResult;
	//押品重估预警结果
	private String reValueResult;

	public int getValueRange() {
		return valueRange;
	}

	public void setValueRange(int valueRange) {
		this.valueRange = valueRange;
	}

	public int getCheckRange() {
		return checkRange;
	}

	public void setCheckRange(int checkRange) {
		this.checkRange = checkRange;
	}

	public String getReValueResult() {
		return reValueResult;
	}

	public void setReValueResult(String reValueResult) {
		this.reValueResult = reValueResult;
	}

	public String getCollateralType() {
		return collateralType;
	}

	public void setCollateralType(String collateralType) {
		this.collateralType = collateralType;
	}

	public Double getCollateralValueMin() {
		return collateralValueMin;
	}

	public void setCollateralValueMin(Double collateralValueMin) {
		this.collateralValueMin = collateralValueMin;
	}

	public Double getCollateralLastValue() {
		return collateralLastValue;
	}

	public void setCollateralLastValue(Double collateralLastValue) {
		this.collateralLastValue = collateralLastValue;
	}

	public String getReCheckResult() {
		return reCheckResult;
	}

	public void setReCheckResult(String reCheckResult) {
		this.reCheckResult = reCheckResult;
	}

	public String getValueChgResult() {
		return valueChgResult;
	}

	public void setValueChgResult(String valueChgResult) {
		this.valueChgResult = valueChgResult;
	}
	
}
