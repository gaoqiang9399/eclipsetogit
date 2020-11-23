package  app.component.oa.debt.bo;

import app.base.ServiceException;
import app.component.oa.debt.entity.MfOaDebtReturnHis;
import app.util.toolkit.Ipage;

/**
* Title: MfOaDebtReturnHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Dec 22 10:32:09 CST 2016
**/

public interface MfOaDebtReturnHisBo {
	
	public void insert(MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;
	
	public void delete(MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;
	
	public void update(MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;
	
	public MfOaDebtReturnHis getById(MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaDebtReturnHis mfOaDebtReturnHis) throws ServiceException;

	void insert(MfOaDebtReturnHis mfOaDebtReturnHis, String opNo) throws ServiceException;
}

