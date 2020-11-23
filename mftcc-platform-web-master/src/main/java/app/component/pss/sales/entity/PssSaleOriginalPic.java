package app.component.pss.sales.entity;
import app.base.BaseDomain;
/**
* Title: PssSaleOriginalPic.java
* Description:
* @author：kaifa@dhcc.com.cn
* @Mon Sep 11 14:42:40 CST 2017
* @version：1.0
**/
public class PssSaleOriginalPic extends BaseDomain {
	private String id;//主键
	private String picId;//图片ID
	private String picName;//图片名称
	private String saleBill;//销货单编号
	private String flag;//是否生成销货单
	private String picPath;//图片存放路径
	private String uploadDate;//上传日期
	private String regOpNo;//登记人编号
	private String regOpName;//登记人名称
	private String regBrNo;//登记人机构编号
	private String regBrName;//登记人机构名称
	private String regTime;//登记时间
	private String lstModOpNo;//最后修改人编号
	private String lstModOpName;//最后修改人名称
	private String lstModBrNo;//最后修改人机构编号
	private String lstModBrName;//最后修改人机构名称
	private String lstModTime;//最后修改时间

	/**
	 * @return 主键
	 */
	public String getId() {
	 	return id;
	}
	/**
	 * @设置 主键
	 * @param id
	 */
	public void setId(String id) {
	 	this.id = id;
	}
	/**
	 * @return 图片ID
	 */
	public String getPicId() {
	 	return picId;
	}
	/**
	 * @设置 图片ID
	 * @param picId
	 */
	public void setPicId(String picId) {
	 	this.picId = picId;
	}
	/**
	 * @return 图片名称
	 */
	public String getPicName() {
	 	return picName;
	}
	/**
	 * @设置 图片名称
	 * @param picName
	 */
	public void setPicName(String picName) {
	 	this.picName = picName;
	}
	/**
	 * @return 销货单编号
	 */
	public String getSaleBill() {
	 	return saleBill;
	}
	/**
	 * @设置 销货单编号
	 * @param saleBill
	 */
	public void setSaleBill(String saleBill) {
	 	this.saleBill = saleBill;
	}
	/**
	 * @return 是否生成销货单
	 */
	public String getFlag() {
	 	return flag;
	}
	/**
	 * @设置 是否生成销货单
	 * @param flag
	 */
	public void setFlag(String flag) {
	 	this.flag = flag;
	}
	/**
	 * @return 图片存放路径
	 */
	public String getPicPath() {
	 	return picPath;
	}
	/**
	 * @设置 图片存放路径
	 * @param picPath
	 */
	public void setPicPath(String picPath) {
	 	this.picPath = picPath;
	}
	/**
	 * @return 上传日期
	 */
	public String getUploadDate() {
	 	return uploadDate;
	}
	/**
	 * @设置 上传日期
	 * @param uploadDate
	 */
	public void setUploadDate(String uploadDate) {
	 	this.uploadDate = uploadDate;
	}
	/**
	 * @return 登记人编号
	 */
	public String getRegOpNo() {
	 	return regOpNo;
	}
	/**
	 * @设置 登记人编号
	 * @param regOpNo
	 */
	public void setRegOpNo(String regOpNo) {
	 	this.regOpNo = regOpNo;
	}
	/**
	 * @return 登记人名称
	 */
	public String getRegOpName() {
	 	return regOpName;
	}
	/**
	 * @设置 登记人名称
	 * @param regOpName
	 */
	public void setRegOpName(String regOpName) {
	 	this.regOpName = regOpName;
	}
	/**
	 * @return 登记人机构编号
	 */
	public String getRegBrNo() {
	 	return regBrNo;
	}
	/**
	 * @设置 登记人机构编号
	 * @param regBrNo
	 */
	public void setRegBrNo(String regBrNo) {
	 	this.regBrNo = regBrNo;
	}
	/**
	 * @return 登记人机构名称
	 */
	public String getRegBrName() {
	 	return regBrName;
	}
	/**
	 * @设置 登记人机构名称
	 * @param regBrName
	 */
	public void setRegBrName(String regBrName) {
	 	this.regBrName = regBrName;
	}
	/**
	 * @return 登记时间
	 */
	public String getRegTime() {
	 	return regTime;
	}
	/**
	 * @设置 登记时间
	 * @param regTime
	 */
	public void setRegTime(String regTime) {
	 	this.regTime = regTime;
	}
	/**
	 * @return 最后修改人编号
	 */
	public String getLstModOpNo() {
	 	return lstModOpNo;
	}
	/**
	 * @设置 最后修改人编号
	 * @param lstModOpNo
	 */
	public void setLstModOpNo(String lstModOpNo) {
	 	this.lstModOpNo = lstModOpNo;
	}
	/**
	 * @return 最后修改人名称
	 */
	public String getLstModOpName() {
	 	return lstModOpName;
	}
	/**
	 * @设置 最后修改人名称
	 * @param lstModOpName
	 */
	public void setLstModOpName(String lstModOpName) {
	 	this.lstModOpName = lstModOpName;
	}
	/**
	 * @return 最后修改人机构编号
	 */
	public String getLstModBrNo() {
	 	return lstModBrNo;
	}
	/**
	 * @设置 最后修改人机构编号
	 * @param lstModBrNo
	 */
	public void setLstModBrNo(String lstModBrNo) {
	 	this.lstModBrNo = lstModBrNo;
	}
	/**
	 * @return 最后修改人机构名称
	 */
	public String getLstModBrName() {
	 	return lstModBrName;
	}
	/**
	 * @设置 最后修改人机构名称
	 * @param lstModBrName
	 */
	public void setLstModBrName(String lstModBrName) {
	 	this.lstModBrName = lstModBrName;
	}
	/**
	 * @return 最后修改时间
	 */
	public String getLstModTime() {
	 	return lstModTime;
	}
	/**
	 * @设置 最后修改时间
	 * @param lstModTime
	 */
	public void setLstModTime(String lstModTime) {
	 	this.lstModTime = lstModTime;
	}
}