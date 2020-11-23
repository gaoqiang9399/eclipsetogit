package app.component.doc.entity;

import app.base.BaseDomain;

/**
 * Title: DocCollect.java Description:<br>
 * 
 * doc_collect(要件集合表)，要件模型doc_model表与此表关联，决定此模型下各个场景使用哪个集合。<br>
 * doc_biz_sce_config表添加集合编号(collect_no)以确定集合下配置的要件。<br>
 * doc_biz_manage表添加集合编号(collect_no)以确定集合下配置的要件是否初始化。<br>
 * 
 * @author：kaifa@dhcc.com.cn
 * @Mon Jun 05 09:05:24 CST 2017
 * @version：1.0
 **/
public class DocCollect extends BaseDomain {
	private String docCollectId;// 主键
	private String docModelNo;// 要件模型
	private String scNo;// 场景编号
	private String collectNo;// 集合编号

	/**
	 * @return 主键
	 */
	public String getDocCollectId() {
		return docCollectId;
	}

	/**
	 * @设置 主键
	 * @param docCollectId
	 */
	public void setDocCollectId(String docCollectId) {
		this.docCollectId = docCollectId;
	}

	/**
	 * @return 要件模型
	 */
	public String getDocModelNo() {
		return docModelNo;
	}

	/**
	 * @设置 要件模型
	 * @param docModelNo
	 */
	public void setDocModelNo(String docModelNo) {
		this.docModelNo = docModelNo;
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
	 * @return 集合编号
	 */
	public String getCollectNo() {
		return collectNo;
	}

	/**
	 * @设置 集合编号
	 * @param collectNo
	 */
	public void setCollectNo(String collectNo) {
		this.collectNo = collectNo;
	}
}