package app.component.nmd.entity;
import app.base.BaseDomain;
/**
* Title: ParmKey.java
* Description:
* @author：jiangyunxin@dhcc.com.cn
* @Thu Apr 10 09:10:06 GMT 2014
* @version：1.0
**/
public class ParmKey extends BaseDomain {
	private String keyName;//关键字
	private String keyChnName;//关键字中文名
	private Integer id;//id
	private String ifEdit;//是否允许编辑
	private String ifSts;//是否启用

	/**
	 * @return 关键字
	 */
	public String getKeyName() {
	 	return keyName;
	}
	/**
	 * @设置 关键字
	 * @param keyName
	 */
	public void setKeyName(String keyName) {
	 	this.keyName = keyName;
	}
	/**
	 * @return 关键字中文名
	 */
	public String getKeyChnName() {
	 	return keyChnName;
	}
	/**
	 * @设置 关键字中文名
	 * @param keyChnName
	 */
	public void setKeyChnName(String keyChnName) {
	 	this.keyChnName = keyChnName;
	}
	/**
	 * @return id
	 */
	public Integer getId() {
	 	return id;
	}
	/**
	 * @设置 id
	 * @param id
	 */
	public void setId(Integer id) {
	 	this.id = id;
	}
	/**
	 * @return 是否允许编辑
	 */
	public String getIfEdit() {
	 	return ifEdit;
	}
	/**
	 * @设置 是否允许编辑
	 * @param ifEdit
	 */
	public void setIfEdit(String ifEdit) {
	 	this.ifEdit = ifEdit;
	}
	/**
	 * @return 是否启用
	 */
	public String getIfSts() {
	 	return ifSts;
	}
	/**
	 * @设置 是否启用
	 * @param ifSts
	 */
	public void setIfSts(String ifSts) {
	 	this.ifSts = ifSts;
	}
}