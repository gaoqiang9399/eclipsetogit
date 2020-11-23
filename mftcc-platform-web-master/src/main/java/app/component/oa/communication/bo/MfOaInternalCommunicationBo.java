package  app.component.oa.communication.bo;

import app.component.oa.communication.entity.MfOaInternalCommunication;
import app.util.toolkit.Ipage;

/**
* Title: MfOaInternalCommunicationBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Sep 29 10:25:41 CST 2017
**/

public interface MfOaInternalCommunicationBo {
	
	public void insert(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	/**
	 * 
	 * 方法描述： 回复新增保存
	 * @param mfOaInternalCommunication
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-29 下午4:09:28
	 */
	public void recoveryInsert(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	public void delete(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	public void update(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	public MfOaInternalCommunication getById(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
	public Ipage findByPage(Ipage ipage,MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	/**
	 * 
	 * 方法描述： 获得消息总数
	 * @param mfOaInternalCommunication
	 * @return
	 * @throws Exception
	 * Integer
	 * @author 沈浩兵
	 * @date 2017-9-29 下午8:00:32
	 */
	public Integer getCount(MfOaInternalCommunication mfOaInternalCommunication) throws Exception;
	
}
