package app.component.report.entity;

/**
 * 类描述：查询条件弹出层中放大镜数据
 * @author 李伟
 * @date 2017-8-27 上午10:09:47
 */
public class ReportSqlListBean {
	private String txType;//选项编号 1-部门 2-操作员 3-客户类型 4-业务品种 5-押品类别 6-信息来源
	private String txCode;//编号
	private String txName;//名称
	
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getTxName() {
		return txName;
	}
	public void setTxName(String txName) {
		this.txName = txName;
	}
}
