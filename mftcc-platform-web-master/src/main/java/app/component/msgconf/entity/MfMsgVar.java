package app.component.msgconf.entity;
import app.base.BaseDomain;
/**
* Title: MfMsgVar.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Wed Jul 05 15:23:19 CST 2017
* @version：1.0
**/
public class MfMsgVar extends BaseDomain {
	private String varId;//变量ID
	private String varName;//变量名
	private String varTb;//来源表
	private String varCol;//来源字段
	private String varUsage;//变量用途
	private String tbPk;//表主键
	private String flag;//是否启用
	private String varType;//变量类型
	private String javaclass;//用于标签支持java类

	public String getJavaclass() {
		return javaclass;
	}

	public void setJavaclass(String javaclass) {
		this.javaclass = javaclass;
	}

	/**
	 * @return 变量ID
	 */
	public String getVarId() {
	 	return varId;
	}
	/**
	 * @设置 变量ID
	 * @param varId
	 */
	public void setVarId(String varId) {
	 	this.varId = varId;
	}
	/**
	 * @return 变量名
	 */
	public String getVarName() {
	 	return varName;
	}
	/**
	 * @设置 变量名
	 * @param varName
	 */
	public void setVarName(String varName) {
	 	this.varName = varName;
	}
	/**
	 * @return 来源表
	 */
	public String getVarTb() {
	 	return varTb;
	}
	/**
	 * @设置 来源表
	 * @param varTb
	 */
	public void setVarTb(String varTb) {
	 	this.varTb = varTb;
	}
	/**
	 * @return 来源字段
	 */
	public String getVarCol() {
	 	return varCol;
	}
	/**
	 * @设置 来源字段
	 * @param varCol
	 */
	public void setVarCol(String varCol) {
	 	this.varCol = varCol;
	}
	/**
	 * @return 变量用途
	 */
	public String getVarUsage() {
	 	return varUsage;
	}
	/**
	 * @设置 变量用途
	 * @param varUsage
	 */
	public void setVarUsage(String varUsage) {
	 	this.varUsage = varUsage;
	}
	/**
	 * @return 表主键
	 */
	public String getTbPk() {
	 	return tbPk;
	}
	/**
	 * @设置 表主键
	 * @param tbPk
	 */
	public void setTbPk(String tbPk) {
	 	this.tbPk = tbPk;
	}
	/**
	 * @return 是否启用
	 */
	public String getFlag() {
	 	return flag;
	}
	/**
	 * @设置 是否启用
	 * @param flag
	 */
	public void setFlag(String flag) {
	 	this.flag = flag;
	}
	/**
	 * @return 变量类型
	 */
	public String getVarType() {
		return varType;
	}
	/**
	 * @设置 变量类型
	 * @param varType
	 */
	public void setVarType(String varType) {
		this.varType = varType;
	}
	
}