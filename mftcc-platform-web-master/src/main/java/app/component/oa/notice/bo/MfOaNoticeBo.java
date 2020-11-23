package  app.component.oa.notice.bo;

import java.util.List;

import app.base.ServiceException;
import app.component.oa.notice.entity.MfOaNotice;
import app.component.oa.notice.entity.MfOaNoticeLook;
import app.util.toolkit.Ipage;

/**
* Title: MfOaNoticeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Dec 09 16:16:55 CST 2016
**/

public interface MfOaNoticeBo {
	
	public void insert(MfOaNotice mfOaNotice) throws ServiceException;
	
	public void delete(MfOaNotice mfOaNotice) throws ServiceException;
	
	public void update(MfOaNotice mfOaNotice) throws ServiceException;
	
	public void updateisTop(MfOaNotice mfOaNotice) throws ServiceException;
	
	public void updateNoticeSts(MfOaNotice mfOaNotice) throws Exception;
	
	public MfOaNotice getById(MfOaNotice mfOaNotice) throws ServiceException;
	
	public Ipage findByPage(Ipage ipage,MfOaNotice mfOaNotice) throws ServiceException;
	
	public List<MfOaNotice> getByStartDate(MfOaNotice mfOaNotice) throws ServiceException;
	
	public List<MfOaNotice> getByEndDate(MfOaNotice mfOaNotice) throws ServiceException;
	
	public int getNoticeCount(MfOaNotice mfOaNotice) throws ServiceException;
	
	public List<MfOaNotice> getAllList(MfOaNotice mfOaNotice) throws ServiceException;
	
	public Ipage getLooking(Ipage ipage,MfOaNoticeLook mfOaNoticeLook) throws Exception;
	public Ipage findMfOaNoticeByOpNo(Ipage ipage,MfOaNotice mfOaNotice) throws Exception;
	
}
