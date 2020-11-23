package app.base.cache.entity;

import java.io.Serializable;
/**
 * 这个实体是服务端放入redis，如果没有这个类在web端取出的时候会报错
 * @author Administrator
 *
 */
public class CacheBase implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7459602310879962191L;
	private String keyName;
	private String optCode;
	private String optName;
	private String seqn;
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getOptCode() {
		return optCode;
	}
	public void setOptCode(String optCode) {
		this.optCode = optCode;
	}
	public String getOptName() {
		return optName;
	}
	public void setOptName(String optName) {
		this.optName = optName;
	}
	public String getSeqn() {
		return seqn;
	}
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
