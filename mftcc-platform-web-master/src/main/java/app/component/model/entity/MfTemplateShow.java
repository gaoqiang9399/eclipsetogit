package app.component.model.entity;

import app.base.BaseDomain;

/**
 * Title: MfTemplateShow.java Description:
 * 
 * @author：kaifa@dhcc.com.cn
 * @Mon Jul 31 15:30:57 CST 2017
 * @version：1.0
 **/
public class MfTemplateShow extends BaseDomain {
	private String templateShowId;// 主键
	private String nodeNo;// 节点编号
	private String showType;// 功能点/展示方式/处理方式
	private String templateNo;// 文档编号

	/**
	 * @return 主键
	 */
	public String getTemplateShowId() {
		return templateShowId;
	}

	/**
	 * @设置 主键
	 * @param templateShowId
	 */
	public void setTemplateShowId(String templateShowId) {
		this.templateShowId = templateShowId;
	}

	/**
	 * @return 节点编号
	 */
	public String getNodeNo() {
		return nodeNo;
	}

	/**
	 * @设置 节点编号
	 * @param nodeNo
	 */
	public void setNodeNo(String nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * @return 功能点/展示方式/处理方式
	 */
	public String getShowType() {
		return showType;
	}

	/**
	 * @设置 功能点/展示方式/处理方式
	 * @param showType
	 */
	public void setShowType(String showType) {
		this.showType = showType;
	}

	/**
	 * @return 文档编号
	 */
	public String getTemplateNo() {
		return templateNo;
	}

	/**
	 * @设置 文档编号
	 * @param templateNo
	 */
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
}