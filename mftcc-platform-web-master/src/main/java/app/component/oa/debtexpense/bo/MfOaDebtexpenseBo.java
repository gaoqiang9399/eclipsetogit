package  app.component.oa.debtexpense.bo;

import app.base.ServiceException;
import app.component.oa.debtexpense.entity.MfOaDebtexpense;
import app.util.toolkit.Ipage;

/**
* Title: MfOaDebtexpenseBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Jan 22 10:12:02 CST 2017
**/

public interface MfOaDebtexpenseBo {
	
	public void insert(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
	public void delete(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
	public void deleteByRelId(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
	public void update(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
	public MfOaDebtexpense getById(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	public MfOaDebtexpense getByRelId(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaDebtexpense mfOaDebtexpense) throws ServiceException;

	public int getOaDebtexpenseCount(MfOaDebtexpense mfOaDebtexpense) throws ServiceException;
	
}
