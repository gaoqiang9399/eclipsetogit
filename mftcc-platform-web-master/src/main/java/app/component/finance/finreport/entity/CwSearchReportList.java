package app.component.finance.finreport.entity;
import app.base.BaseDomain;

/**
 * 类名： CwSearchReportList
 * 描述：
 * @author Javelin
 * @date 2017-1-22 上午10:41:20
 */
public class CwSearchReportList extends BaseDomain {
	private String reportItemId;// 报表项标识
	private String showName;// 显示名称
	private String operationType;// 运算类型0：空白项；1：查询项，2：运算项
	private String exp;// 报表项等级
	private String showStyle;// 显示样式0：正常；1：加粗
	private String itemAAmt;// 显示左列金额
	private String itemBAmt;// 显示右列金额
	
	// 资产负债表使用
	private String reportItemId2;// 报表项标识
	private String showName2;// 显示名称
	private String operationType2;// 运算类型0：空白项；1：查询项，2：运算项
	private String exp2;// 报表项等级
	private String showStyle2;// 显示样式0：正常；1：加粗
	private String itemAAmt2;// 显示左列金额
	private String itemBAmt2;// 显示右列金额
	
	private String zcTr;//资产行次
	private String fzTr;//负债行次
	private String lrTr;//利润行次
	private String xjllTr;//现金流量行次
	
	//以下字段是下载时使用，数据库中不存在这些个字段,下载到Excel
	private String eshowName;
	private String ezcTr;
	private String eitemBAmt;
	private String eitemAAmt;
	private String eshowName2;
	private String efzTr;
	private String eitemBAmt2;
	private String eitemAAmt2;
	
	private String elrTr;
	private String exjllTr;
	
	private String bbShow;
	

	/**
	 * @return 报表项标识
	 */
	public String getReportItemId() {
		return reportItemId;
	}
	/**
	 * @设置 报表项标识
	 * @param reportItemId
	 */
	public void setReportItemId(String reportItemId) {
		this.reportItemId = reportItemId;
	}

	/**
	 * @return 显示名称
	 */
	public String getShowName() {
		return showName;
	}
	/**
	 * @设置 显示名称
	 * @param showName
	 */
	public void setShowName(String showName) {
		this.showName = showName;
	}

	/**
	 * @return 运算类型0：空白项；1：查询项，2：运算项
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * @设置 运算类型0：空白项；1：查询项，2：运算项
	 * @param operationType
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return 显示样式0：正常；1：加粗
	 */
	public String getShowStyle() {
		return showStyle;
	}
	/**
	 * @设置 显示样式0：正常；1：加粗
	 * @param showStyle
	 */
	public void setShowStyle(String showStyle) {
		this.showStyle = showStyle;
	}

	/**
	 * @return 报表项等级
	 */
	public String getExp() {
		return exp;
	}
	/**
	 * @设置 报表项等级
	 * @param exp
	 */
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getItemAAmt() {
		return itemAAmt;
	}
	public void setItemAAmt(String itemAAmt) {
		this.itemAAmt = itemAAmt;
	}
	public String getItemBAmt() {
		return itemBAmt;
	}
	public void setItemBAmt(String itemBAmt) {
		this.itemBAmt = itemBAmt;
	}
	public String getReportItemId2() {
		return reportItemId2;
	}
	public void setReportItemId2(String reportItemId2) {
		this.reportItemId2 = reportItemId2;
	}
	public String getShowName2() {
		return showName2;
	}
	public void setShowName2(String showName2) {
		this.showName2 = showName2;
	}
	public String getOperationType2() {
		return operationType2;
	}
	public void setOperationType2(String operationType2) {
		this.operationType2 = operationType2;
	}
	public String getExp2() {
		return exp2;
	}
	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}
	public String getShowStyle2() {
		return showStyle2;
	}
	public void setShowStyle2(String showStyle2) {
		this.showStyle2 = showStyle2;
	}
	public String getItemAAmt2() {
		return itemAAmt2;
	}
	public void setItemAAmt2(String itemAAmt2) {
		this.itemAAmt2 = itemAAmt2;
	}
	public String getItemBAmt2() {
		return itemBAmt2;
	}
	public void setItemBAmt2(String itemBAmt2) {
		this.itemBAmt2 = itemBAmt2;
	}
	public String getZcTr() {
		return zcTr;
	}
	public void setZcTr(String zcTr) {
		this.zcTr = zcTr;
	}
	public String getFzTr() {
		return fzTr;
	}
	public void setFzTr(String fzTr) {
		this.fzTr = fzTr;
	}
	public String getLrTr() {
		return lrTr;
	}
	public void setLrTr(String lrTr) {
		this.lrTr = lrTr;
	}
	public String getXjllTr() {
		return xjllTr;
	}
	public void setXjllTr(String xjllTr) {
		this.xjllTr = xjllTr;
	}
	public String getEshowName() {
		return eshowName;
	}
	public void setEshowName(String eshowName) {
		this.eshowName = eshowName;
	}
	public String getEzcTr() {
		return ezcTr;
	}
	public void setEzcTr(String ezcTr) {
		this.ezcTr = ezcTr;
	}
	public String getEitemBAmt() {
		return eitemBAmt;
	}
	public void setEitemBAmt(String eitemBAmt) {
		this.eitemBAmt = eitemBAmt;
	}
	public String getEitemAAmt() {
		return eitemAAmt;
	}
	public void setEitemAAmt(String eitemAAmt) {
		this.eitemAAmt = eitemAAmt;
	}
	public String getEshowName2() {
		return eshowName2;
	}
	public void setEshowName2(String eshowName2) {
		this.eshowName2 = eshowName2;
	}
	public String getEfzTr() {
		return efzTr;
	}
	public void setEfzTr(String efzTr) {
		this.efzTr = efzTr;
	}
	public String getEitemBAmt2() {
		return eitemBAmt2;
	}
	public void setEitemBAmt2(String eitemBAmt2) {
		this.eitemBAmt2 = eitemBAmt2;
	}
	public String getEitemAAmt2() {
		return eitemAAmt2;
	}
	public void setEitemAAmt2(String eitemAAmt2) {
		this.eitemAAmt2 = eitemAAmt2;
	}
	public String getBbShow() {
		return bbShow;
	}
	public void setBbShow(String bbShow) {
		this.bbShow = bbShow;
	}
	public String getElrTr() {
		return elrTr;
	}
	public void setElrTr(String elrTr) {
		this.elrTr = elrTr;
	}
	public String getExjllTr() {
		return exjllTr;
	}
	public void setExjllTr(String exjllTr) {
		this.exjllTr = exjllTr;
	}
	
	
}