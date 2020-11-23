package  app.component.oa.expense.bo;

import app.base.ServiceException;
import app.component.oa.expense.entity.MfOaExpenseList;
import app.util.toolkit.Ipage;

/**
* Title: MfOaExpenseListBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 15 10:29:56 CST 2017
**/

public interface MfOaExpenseListBo {
	
	public void insert(MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	public void delete(MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	public void update(MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	public MfOaExpenseList getById(MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaExpenseList mfOaExpenseList) throws ServiceException;
	
}
