package  app.component.oa.banknote.bo;

import java.util.Map;

import app.base.ServiceException;
import app.component.oa.banknote.entity.MfOaBankNote;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfOaBankNoteBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 13 10:05:32 CST 2017
**/

public interface MfOaBankNoteBo {
	
	public void insert(MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public void delete(MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public void update(MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public MfOaBankNote getById(MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public Map<String, Object> insertForSubmit(MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public Result doSubmit(String taskId, String billId, String opinionType, String approvalOpinion, String transition, String regNo, String nextUser, MfOaBankNote mfOaBankNote) throws ServiceException;
	
	public int getCount() throws ServiceException;
}
