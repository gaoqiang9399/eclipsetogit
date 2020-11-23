package  app.component.oa.consumable.bo;

import java.util.List;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaConsClass;
import app.util.toolkit.Ipage;

/**
* Title: MfOaConsClassBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 24 11:58:00 CST 2016
**/

public interface MfOaConsClassBo {
	
	public MfOaConsClass insert(MfOaConsClass mfOaConsClass, String isChild) throws ServiceException;
	
	public void delete(MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public void update(MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public MfOaConsClass getById(MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public List<MfOaConsClass> getConsClassList(MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public List<MfOaConsClass> getChildClassList(MfOaConsClass mfOaConsClass) throws ServiceException;
	
	public List<MfOaConsClass> getSuperClass() throws ServiceException;

	public List<MfOaConsClass> getAll(MfOaConsClass mfOaConsClass)  throws ServiceException;
}
