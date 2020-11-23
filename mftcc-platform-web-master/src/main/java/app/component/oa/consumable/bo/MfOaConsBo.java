package  app.component.oa.consumable.bo;

import java.util.List;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaCons;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.util.toolkit.Ipage;

/**
* Title: MfOaConsBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 24 11:56:30 CST 2016
**/

public interface MfOaConsBo {
	
	public void insert(MfOaCons mfOaCons , MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	public void delete(MfOaCons mfOaCons) throws ServiceException;
	
	public void update(MfOaCons mfOaCons) throws ServiceException;
	
	public MfOaCons getById(MfOaCons mfOaCons) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaCons mfOaCons) throws ServiceException;
	
	public List<MfOaCons> getByClass(MfOaCons mfOaCons) throws ServiceException;
	
	public void update(MfOaCons mfOaCons,MfOaConsOperate mfOaConsOperate) throws ServiceException;

	public Ipage findByPageAndClass(Ipage ipage, MfOaCons mfOaCons) throws ServiceException;
	
	public String  getAppNumByOpNo (String opNo) throws ServiceException;

	public Object getOaConsCount(MfOaCons mfOaCons) throws ServiceException;
}
