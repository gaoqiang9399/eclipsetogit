package  app.component.oa.consumable.bo;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.component.oa.consumable.entity.OpUserCons;
import app.util.toolkit.Ipage;

/**
* Title: MfOaConsOperateBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 24 12:00:15 CST 2016
**/

public interface MfOaConsOperateBo {
	
	public void insert(MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	public void delete(MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	public void update(MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	public MfOaConsOperate getById(MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaConsOperate mfOaConsOperate) throws ServiceException;

	public Ipage findAppByPage(Ipage ipage, MfOaConsOperate mfOaConsOperate) throws ServiceException;

	/**
	 * 获取当前操作员的资产申领信息
	 * @return
	 * @throws ServiceException
	 */
	public OpUserCons getOpUserCons() throws ServiceException;

	public Integer getOaConsAppCount(MfOaConsOperate mfOaConsOperate)throws ServiceException;
	
}
